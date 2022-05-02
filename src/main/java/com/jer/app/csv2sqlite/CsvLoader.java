package com.jer.app.csv2sqlite;

import com.jer.app.utilities.DbConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class CsvLoader {

  static List<List<String>> csvData = null;

  public CsvLoader(List<List<String>> data) {

    csvData = data;
  }

  private static void createTable() {

    int numOfCols = csvData.get(0).size();

    String sqlTable = "CREATE TABLE IF NOT EXISTS CsvData (\n";

    for (int i = 1; i <= numOfCols; i++) {

      if (i == numOfCols) {
        sqlTable += " col" + i + " TEXT \n";
      } else {
        sqlTable += " col" + i + " TEXT,\n";
      }

    }
    sqlTable += "\n);";

    try (Connection conn = DbConnectionManager.connect(); Statement stmt = conn.createStatement()) {
      stmt.execute(sqlTable);

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

  }

  private static List<String> getTableColumns(String tableName, String schemaName)
      throws SQLException {


    ResultSet rs = null;

    ResultSetMetaData rsmd = null;

    PreparedStatement stmt = null;

    List<String> columnNames = null;

    String qualifiedName =
        (schemaName != null && !schemaName.isEmpty()) ? (schemaName + "." + tableName) : tableName;

    try (Connection conn = DbConnectionManager.connect()) {
      stmt = conn.prepareStatement("select * from " + qualifiedName + " where 0=1");
      rs = stmt.executeQuery();// you'll get an empty ResultSet, but you'll still get the metadata
      rsmd = rs.getMetaData();
      columnNames = new ArrayList<String>();

      for (int i = 1; i <= rsmd.getColumnCount(); i++) {
        columnNames.add(rsmd.getColumnLabel(i));
      }
    } catch (SQLException e) {
      throw e;// or log it

    } finally {

      if (rs != null) {

        try {
          rs.close();

        } catch (SQLException e) {

          System.out.println(e.getMessage());
        }
      }

      if (stmt != null) {
        try {
          stmt.close();
        } catch (SQLException e) {

          System.out.println(e.getMessage());
        }
      }
    }
    return columnNames;
  }

  /**
   * This method loads the CSV data to a SQlite database.
   * 
   * @throws SQLException if underlying database connections
   */
  public void loadDataToDb() throws SQLException {

    String url = "jdbc:sqlite:C:/sqlite/db/" + "csv.db";
    Connection conn = null;

    try {
      conn = DriverManager.getConnection(url);

      if (conn != null) {

        System.out.println("Connection to " + url + " has been established.");
      }


      // create database table
      createTable();

      // get the table column names
      List<String> columnNames = getTableColumns("CsvData", "");

      String insertColumns = "";
      String insertValues = "";

      if (columnNames != null && columnNames.size() > 0) {
        insertColumns += columnNames.get(0);
        insertValues += "?";
      }

      for (int i = 1; i < columnNames.size(); i++) {
        insertColumns += ", " + columnNames.get(i);
        insertValues += ", " + "?";
      }


      String insertSql =
          "INSERT INTO CsvData (" + insertColumns + ") " + "VALUES(" + insertValues + ");";

      PreparedStatement ps = conn.prepareStatement(insertSql);

      int index = 1;

      for (int i = 0; i < csvData.size(); i++) {

        for(int j = 0; j < csvData.get(i).size(); j++) {

          ps.setObject(index, csvData.get(i).get(j));
          index++;

        }
        ps.addBatch();

        index = 1;

      }
      ps.executeBatch();

      ps.close();
      conn.close();

    } catch (SQLException e) {

      System.out.println(e.getMessage());
    }
  }

}

