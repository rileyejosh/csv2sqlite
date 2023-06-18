package com.jer.app.utilities;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionManager {
static Connection conn = null;
  /**
   * This helper method facilitates connections to a SQlite database.
   *
   * @return
   */
  public static Connection connect() {

    String url = "jdbc:sqlite:C:/sqlite/db/" + "csv.db";
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(url);

      if (conn != null) {
        // DatabaseMetaData meta = conn.getMetaData();
        // System.out.println("The driver name is: " + meta.getDriverName());
        System.out.println("Connection to " + url + " has been established.");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());

    }
    return conn;
  }

  public static void close()  {
    try {
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
