package com.jer.app.csv2sqlite;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.List;
import java.util.Scanner;

import com.jer.app.utilities.DbConnectionManager;
import com.jer.app.utilities.FilePathRetriever;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




/**
 * Entry point to the csv2sqlite app.
 *
 * @author jer
 *
 */
public class App {

  /**
   * Main method.
   *
   * @param args command-line arguments
   * @throws IOException for file i/o-related issues.
   * @throws SQLException
   */
  public static void main(String[] args){


    String dbName = "csv.db";
    String dbPath = FilePathRetriever.getPath() + "\\src\\main\\resources\\" + dbName;
    File f = null;
    List<List<String>> data = null;
    CsvParser parser = null;




    System.out.println("Checking if database exists...");
    if(!(new File(dbPath).exists())) {
      System.out.println("Database does not exist.");
      System.out.println("Initializing database...");
      try(Connection conn = DbConnectionManager.connect()) {
        System.out.println("Initialization complete.");

      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
    System.out.println("Processing CSV file...");
    if (args.length > 0) {
        try {

          f = FilePathRetriever.getFile(args[0]);
          if(!(FilePathRetriever.getFileExtension(f).equals("csv"))) {
            System.out.println("Wrong file type; it must be csv.");
            return;
          }

          try {
            parser = new CsvParser(f);
            data = parser.parseCsvFile();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        } catch(FileNotFoundException e) {
          e.getStackTrace();
        } catch( Exception e ) {
          e.getStackTrace();
        }
    }
    else {
      // load the data.csv file as a resource
      InputStream inputStream = App.class.getClassLoader().getResourceAsStream("data.csv");
      if(inputStream != null) {
        try {
          parser = new CsvParser(inputStream);
        } catch (InstantiationException e) {
          throw new RuntimeException(e);
        }
        try {
          data = parser.parseCsvFile();
        } catch (IOException e) {
          System.out.println(e.getMessage());
          throw new RuntimeException(e);
        }
      }

    }

    CsvLoader loader = new CsvLoader(data);
    try {
      loader.loadDataToDb();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }
}
