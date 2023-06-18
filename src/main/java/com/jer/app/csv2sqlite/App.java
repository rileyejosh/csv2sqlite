package com.jer.app.csv2sqlite;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import java.util.List;

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
  public static void main(String[] args) throws IOException, SQLException{

    final Logger logger = LogManager.getLogger("CSV2SQLite App");
    File f = null;
    if (args.length > 0) {
        try {
          f = FilePathRetriever.getFile(args[0]);
          if(!(FilePathRetriever.getFileExtension(f).equals("csv"))) {
            System.out.println("Wrong file type; it must be csv.");
            return;
          }
        } catch(FileNotFoundException e) {
          e.getStackTrace();
        } catch( Exception e ) {
          e.getStackTrace();
        }
    }
    else {
      System.out.println("File not provided.");
      return;
    }

    CsvParser parser = new CsvParser(f);
    List<List<String>> data = parser.parseCsvFile();
    CsvLoader loader = new CsvLoader(data);
    loader.loadDataToDb();

  }
}
