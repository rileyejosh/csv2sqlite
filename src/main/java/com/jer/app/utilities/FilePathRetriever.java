package com.jer.app.utilities;

import java.io.File;
import java.io.FileNotFoundException;

public class FilePathRetriever  {
  
  // TODO: custom exception handling for file open

	
  String fileSeparator = File.separator;
  String path = System.getProperty("user.dir") + fileSeparator;

  /**
   * Creates directory if it does not exist.
   * @param dir Directory to be created/checked
   * @return String with notification of directory creation.
   */
  public String createDirectory(String dir) {
    File fileDir = new File(dir);
    boolean result = false;

    if (!fileDir.exists()) {
      
      System.out.println("Creating directory " + fileDir.getName());
      
      try {
        
        fileDir.mkdir();
        result = true;
        
      } catch (SecurityException se) {
        
        return "Directory could not be created...\n" + se.getMessage();
      }
      if (result) {
        return "Created directory: " + dir + " successfully";
      }
    }
    
    return "Directory " + dir + " exists...";
  }

  public String getPath() {
    return path;
  }

  /**
   * Returns the path for file in UmlSystemTests with "Test.VSDX" as suffix
   * 
   * @param fileName file name to be saved/accessed
   * @return path to file
   */
  public String getPathWithFileDir_ct(String fileName) {
    String localPath = path + "UmlSystemTests" + fileSeparator;
    createDirectory(localPath);
    return localPath  + fileName + "Test.vsdx";
  }
  
  /**
   * Returns the path for file in UmlPerformanceTestExcelOutput
   * with chosen extension to allow for ".xlsx" ans ".xls".
   * 
   * @param fileName file name to be saved/accessed
   * @param extension extension of file being saved
   * @return path to file
   */
  public String getPathWithFileDir_upteo(String fileName, String extension) {
    String localPath = path + "UmlPerformanceTestExcelOutput" + fileSeparator;
    createDirectory(localPath);
    return localPath + fileName + extension;
  }
  
  /**
   * Returns the path for file in UmlPerformanceTestPlaintextOutput with ".txt" as suffix
   * 
   * @param fileName file name to be saved/accessed
   * @return path to file
   */
  public String getPathWithFileDir_uptpo(String fileName) {
    String localPath = path + "UmlPerformanceTestPlaintextOutput" + fileSeparator;
    createDirectory(localPath);
    return localPath + fileName + ".txt";
  }

  /**
   * Returns the file path for masters file in directory UmlparserRequiredFiles.
   * 
   * @param mastersFileName name of masters file
   * @return String to file in directory
   */
  public String getPathForMasters(String mastersFileName) {
    String localPath = path + "UmlParserRequiredFiles" + fileSeparator;
    createDirectory(localPath);
    return localPath + mastersFileName + ".vss";
  }

  /**
   * Returns the file path for masters file in directory UmlParserRequiredFiles.
   * 
   * @return String to file in directory
   */
  public String getPathForLicense() {
    String localPath = path + "UmlParserRequiredFiles" + fileSeparator;
    createDirectory(localPath);
    return localPath + "Aspose.Diagram.lic";
  }
  
  /**
   * Returns the path for file in UmlConverterTestOutput with ".vsdx" as suffix
   * 
   * @param fileName file name to be saved/accessed
   * @return path to file
   */
  public String getPathWithFileDir_ucto(String fileName) {
    String localPath = path + "UmlConverterOutput" + fileSeparator;
    createDirectory(localPath);
    return localPath + fileName + ".vsdx";
  }
}