package com.jer.app.utilities;

import java.io.File;
import java.io.FileNotFoundException;

public class FilePathRetriever  {

  static String fileSeparator = File.separator;
  static String path = System.getProperty("user.dir") + fileSeparator;

  public static File getFile(String filePath) throws FileNotFoundException {

    return new File(filePath);

  }

  public static String getFileExtension(File file) {
    String extension = null;
    String fileName = file.getName();
    int dotIndex = fileName.lastIndexOf('.');

    if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
      extension = fileName.substring(dotIndex + 1).toLowerCase();
    }

    return extension;
  }


  /**
   * Creates directory if it does not exist.
   *
   * @param dir Directory to be created/checked
   */
  public static void createDirectory(String dir) {
    File fileDir = new File(dir);
    boolean result = false;

    if (!fileDir.exists()) {

      System.out.println("Creating directory " + fileDir.getName());

      try {

        fileDir.mkdir();
        result = true;

      } catch (SecurityException se) {

        System.out.println(se.getMessage());
      }
    }

  }

  public static String getPath() {
    return path;
  }

}
