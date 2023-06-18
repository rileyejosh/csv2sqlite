package com.jer.app.csv2sqlite;

import com.jer.app.utilities.FilePathRetriever;

import java.io.File;
import java.io.FileNotFoundException;
// import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CsvParser {

    File csvFile = null;
    List<String> badData;
    int recordsReceived = 0;
    int recordsSuccessful = 0;
    int recordsFailed = 0;
    static String csvInfoDir = "csv_info";


    /**
     * This constructor initializes the CSV file reference.
     *
     * @param csvFile representing the input CSV file
     */
    public CsvParser(File csvFile) {

        this.csvFile = csvFile;

    }

    /**
     * Parse CSV files and returns list of CSV data.
     *
     * @return List of CSV data
     * @throws IOException - throw exception if the file reference is null.
     */
    public List<List<String>> parseCsvFile() throws IOException {


        // open CSV file
        Scanner parser = new Scanner(csvFile).useDelimiter(",");

        // parse through csv file and add each record to the list
        List<String> records = new ArrayList<String>();
        List<String> validRecords = new ArrayList<String>();
        List<List<String>> data = new ArrayList<List<String>>();
        while (parser.hasNext()) {

            records.add(parser.nextLine());

        }

        parser.close();

        String otherThanQuote = " [^\"] ";
        String quotedString = String.format(" \" %s* \" ", otherThanQuote);
        String regex = String.format("(?x) " + // enable comments, ignore white spaces
                        ",                         " + // match a comma
                        "(?=                       " + // start positive look ahead
                        "  (?:                     " + // start non-capturing group 1
                        "    %s*                   " + // match 'otherThanQuote' zero or more times
                        "    %s                    " + // match 'quotedString'
                        "  )*                      " + // end group 1 and repeat it zero or more times
                        "  %s*                     " + // match 'otherThanQuote'
                        "  $                       " + // match the end of the string
                        ")                         ", // stop positive look ahead
                otherThanQuote, quotedString, otherThanQuote);

        // add data to a list for verification purposes
        for (int i = 0; i < records.size(); i++) {

            data.add(new ArrayList<>(Arrays.asList(records.get(i).split(regex, -1))));

        }


        recordsReceived = data.size();

        int numOfCol = 0;
        numOfCol = data.remove(0).size(); // remove first record to use as baseline for correct number of columns


        badData = new ArrayList<String>();
        List<String> tempList = new ArrayList<String>();
        StringBuilder tempStr = new StringBuilder();

        // verify total # of columns of each record match the column count
        int counter = 0;
        for (int i = 0; i < data.size(); i++) {

            for (int j = 0; j < data.get(i).size(); j++) {

                if (!data.get(i).get(j).isEmpty())
                    counter++;
            }
            // add records that are less than the column count to a list for invalid data
            if (counter < numOfCol)
                badData.add(printCsvString(data.remove(i)));

            counter = 0;
        }

        // write bad data to a CSV file
        writeBadDataToFile(badData);

        recordsSuccessful = data.size();
        recordsFailed = badData.size();

        // write statistics to a log file
        printStatistics(recordsReceived, recordsSuccessful, recordsFailed);

        return data;

    }

    private String printCsvString(List<String> csv) {

        String tempStr = "";

        for (int i = 0; i < csv.size(); i++) {

            if (i == csv.size() - 1)
                tempStr += csv.get(i);
            else
                tempStr += csv.get(i) + ",";

        }
        return tempStr;
    }

    private void createDirForCsvData() {



    }
    /**
     * Write invalid data to a CSV file
     */
    private void writeBadDataToFile(List<String> invalidData) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmm'.csv'").format(new Date());

        FilePathRetriever.createDirectory(csvInfoDir);
        FileWriter csvFileWriter = new FileWriter(
                csvInfoDir + "\\bad-data-"
                        + timeStamp);

        System.out.println("Writing bad data to " + "bad-data-" + timeStamp);

        String temp = "";
        for (int i = 0; i < invalidData.size(); i++) {

            csvFileWriter.append(invalidData.get(i));
            csvFileWriter.append("\n");
        }

        csvFileWriter.flush();
        csvFileWriter.close();
    }

    /**
     * Print CSV data statistics to a log file.
     */

    private static void printStatistics(int recReceived, int recSuccess, int recFailed) {

        Logger logger = Logger.getLogger("CSV2SQLiteLog");
        FileHandler fh;

        try {

            FilePathRetriever.createDirectory(csvInfoDir);
            // Create the file in the "csv_info" directory if it doesn't exist
            File logFile = new File(csvInfoDir, "csv2sqlite.log");
            if (!logFile.exists()) {
                if (logFile.createNewFile()) {
                    System.out.println("Log file created successfully.");
                } else {
                    System.out.println("Failed to create log file.");
                }
            } else {
                System.out.println("Log file already exists.");
            }

            // This block configures the logger with handler and formatter
            fh = new FileHandler("D://csv2sqlite.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);





            System.out.println("Printing statistics to a log file...");

            // the following statement is used to log any messages
            logger.info("Number of records received: " + recReceived);
            logger.info("Number of records successful: " + recSuccess);
            logger.info("Number of records failed: " + recFailed);


        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}


