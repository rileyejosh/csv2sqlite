package com.jer.app.csv2sqlite;

import java.io.File;

import java.io.IOException;
import java.sql.SQLException;

import java.util.List;

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
  public static void main(String[] args) throws IOException, SQLException {

    final Logger logger = LogManager.getLogger("CSV2SQLite App");

    CsvParser parser = new CsvParser(new File("D:\\data1.csv"));
    List<List<String>> data = parser.parseCsvFile();
    CsvLoader loader = new CsvLoader(data);
    loader.loadDataToDb();
    


  }

}
