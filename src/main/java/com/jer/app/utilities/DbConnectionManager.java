package com.jer.app.utilities;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionManager {

  static Connection conn;
  static String url = "jdbc:sqlite::resource:csv.db";

  /**
   * This helper method facilitates connections to a SQlite database.
   *
   * @return
   */
  public static Connection connect() {

    try {

      conn = DriverManager.getConnection(url);

      if (conn != null) {

        System.out.println("Connection to " + url + " has been established.");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());

    }
    return conn;
  }

  public static void close() {
    try {
      if(conn != null) {
        conn.close();
        System.out.println("The connection was closed.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
