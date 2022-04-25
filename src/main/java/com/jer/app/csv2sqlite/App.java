package com.jer.app.csv2sqlite;

import com.jer.app.utilities.FilePathRetriever;

import java.io.BufferedInputStream;

import java.io.BufferedReader;

import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;
import java.sql.SQLException;
import java.text.Format;

import java.text.SimpleDateFormat;

import java.util.List;

import java.util.Scanner;

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

    /// final Logger logger = LogManager.getLogger("App");

    /*CsvParser parser = new CsvParser(new File("D:\\data.csv"));
    List<String> data = parser.parseCsvFile();
    parser.printStatistics();
    CsvLoader loader = new CsvLoader(data);
    loader.loadDataToDb(); */
    


  }

}
