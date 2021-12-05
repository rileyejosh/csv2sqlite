package com.jer.app.csv2sqlite;

import java.io.File;
import java.io.FileNotFoundException;
// import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CsvParser {

  File csvFile = null;
  List<String> badData;

  /**
   * This constructor initializes the CSV file reference.
   * @param csvFile representing the input CSV file
   */
  public CsvParser(File csvFile) {
      
    this.csvFile = csvFile;

  }

  /**
   * Parse CSV files and returns CSV data.
   * 
   * @return List of CSV data
   * @throws IOException - throw exception if the file reference is null.
   */
  public List<String> parseCsvFile() throws IOException {

    // open CSV file 
    Scanner parser = new Scanner(csvFile).useDelimiter(",");

    // parse through file and put each record in a list
    List<String> listOfRecords = new ArrayList<String>();
    List<String> data = new ArrayList<String>();
    while (parser.hasNext()) {

      listOfRecords.add(parser.nextLine());

    }
    
    parser.close();
    
    // verify then add data to a list
    for (String dataToVerify : listOfRecords) {
      data.add(dataToVerify.replaceAll(",", " "));

    }

    // verify data elements of each record matches column count
    int numOfCol = 0;
    numOfCol = data.get(0).replace(" ", "").length();
    badData = new ArrayList<String>();
    for (int i = 1; i < data.size(); i++) {
      String[] elements = data.get(i).split("\\s+"); // matches sequence of one or more whitespace characters.
      // System.out.println(elements.length);
      if (elements.length < numOfCol) {
        // add records that don't match column count,
        // to a list for bad data
        badData.add(listOfRecords.get(i));

        data.remove(i); // remove bad data from list
      }
    }
    
    //TODO perform final data verification for word spacing issue
    //for(String s : )
        
    // write bad data to a CSV file
    String timeStamp = new SimpleDateFormat("yyyyMMddHHmm'.csv'").format(new Date());

    FileWriter csvFileWriter = new FileWriter(
            "C:\\Users\\tkd_s\\eclipse-workspace\\csv2sqlite\\bad_csv_data\\bad-data-"
            + timeStamp);
    System.out.println("Writing bad data to " + "bad-data-" + timeStamp);

    for (String record : badData) {
      csvFileWriter.append(record);
      csvFileWriter.append("\n");
    }
    csvFileWriter.flush();
    csvFileWriter.close();

    return data;

  }



  //TODO
  /**
   * Print CSV file statistics to a log file.
   * 
   */
  public void printStatistics() {

    System.out.println("Printing statistics to a log file...");

  }
}
