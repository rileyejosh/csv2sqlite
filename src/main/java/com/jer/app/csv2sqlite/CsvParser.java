package com.jer.app.csv2sqlite;

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

        // add records that are less than the column count to a list for invalid data
        badData = new ArrayList<String>();
        List<String> tempList = new ArrayList<String>();
        String tempStr = "";

        // verify columns of each record match the column count
        int[] countOfRecordCol = new int[data.size()];
        int counter = 0;
        for(int i = 0; i < data.size(); i++ ) {

            for (int j = 0; j < data.get(i).size(); j++) {

                if (!data.get(i).get(j).isEmpty())
                    counter++;

            }
            countOfRecordCol[i] = counter;
            counter = 0;
        }

        // for columns of each record that does not match the column count, add them to a list for invalid data
        for (int i = 0; i < data.size(); i++) {

            if (countOfRecordCol[i] < numOfCol) {

                for (int j = 0; j < data.get(i).size(); j++) {

                    if (data.get(i).get(j).equals(data.get(i).get(data.get(i).size() - 1))) {
                        tempStr += data.get(i).get(j);
                    } else
                        tempStr += data.get(i).get(j) + ",";
                }
                badData.add(tempStr);
                data.remove(i);
                tempStr = "";

            }

        }

        // write bad data to a CSV file
        writeBadDataToFile(badData);

        // write statistics to a log file
        printStatistics(recordsReceived, recordsSuccessful, recordsFailed);

        return data;

    }

    /**
     * Write invalid data to a CSV file
     */
    private void writeBadDataToFile(List<String> invalidData) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmm'.csv'").format(new Date());

        FileWriter csvFileWriter = new FileWriter(
                "C:\\Users\\tkd_s\\eclipse-workspace\\csv2sqlite\\bad_csv_data\\bad-data-"
                        + timeStamp);

        System.out.println("Writing bad data to " + "bad-data-" + timeStamp);

        String temp = " ";
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
    private static void printStatistics(int recReceived, int recSuccess, int recFailed ) {

        System.out.println("Printing statistics to a log file...");
        System.out.println("Number of records received: " + recReceived);
        System.out.println("Number of records successful: " + recSuccess);
        System.out.println("Number of records failed: " + recFailed);


    }


    public static void main(String[] args) throws IOException {


        CsvParser parser = new CsvParser(new File("D:\\data1.csv"));
        parser.parseCsvFile();

    }
}


