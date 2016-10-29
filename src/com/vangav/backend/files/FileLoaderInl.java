/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * MIT License
 *
 * Copyright (c) 2016 Vangav
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 * */

/**
 * Community
 * Facebook Group: Vangav Open Source - Backend
 *   fb.com/groups/575834775932682/
 * Facebook Page: Vangav
 *   fb.com/vangav.f
 * 
 * Third party communities for Vangav Backend
 *   - play framework
 *   - cassandra
 *   - datastax
 *   
 * Tag your question online (e.g.: stack overflow, etc ...) with
 *   #vangav_backend
 *   to easier find questions/answers online
 * */

package com.vangav.backend.files;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * LoaderInl handles loading various file types through inline static methods
 * */
public class FileLoaderInl {
  
  // disable default instantiation
  private FileLoaderInl () {}
  
  /**
   * fileExists
   * @param filePath
   * @return true if a File at param filePath exists and false otherwise
   * @throws Exception
   */
  public static boolean fileExists (String filePath) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "file path",
      filePath,
      ExceptionType.CODE_EXCEPTION);
    
    File file = new File (filePath);
    
    return file.exists();
  }
  
  /**
   * loadPropertiesFile
   * loads a properties file
   * @param filePath
   * @return Properties Object representing the properties file
   * @throws Exception
   */
  public static Properties loadPropertiesFile (
    String filePath) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "file path",
      filePath,
      ExceptionType.CODE_EXCEPTION);
    
    InputStream inputStream = new FileInputStream(filePath);
    
    Properties properties = new Properties();
    properties.load(inputStream);
    
    return properties;
  }
  
  /**
   * loadTextFile
   * load text file to String
   * @param textFilePath (relative to execution or absolute)
   * @return string content of the text file
   * @throws Exception
   * */
  public static String loadTextFile (
    final String textFilePath) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "file path",
      textFilePath,
      ExceptionType.CODE_EXCEPTION);
    
	  BufferedReader bufferedReader =
			new BufferedReader(new FileReader(textFilePath) );
	  
	  StringBuilder stringBuilder = new StringBuilder();
    String line = bufferedReader.readLine();

    while (line != null) {
    	
      stringBuilder.append(line);
      stringBuilder.append(System.lineSeparator() );
      line = bufferedReader.readLine();
    }
    
    bufferedReader.close();
    
    return stringBuilder.toString();
  }
  
  /**
   * loadTextFileLines
   * load text file to an array list of its lines
   * @param textFilePath (relative to execution or absolute)
   * @return content of the text file as an array list of lines
   * @throws Exception
   */
  public static ArrayList<String> loadTextFileLines (
    final String textFilePath) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "file path",
      textFilePath,
      ExceptionType.CODE_EXCEPTION);
    
    BufferedReader bufferedReader =
      new BufferedReader(new FileReader(textFilePath) );
    
    ArrayList<String> lines = new ArrayList<String>();
    String line = bufferedReader.readLine();

    while (line != null) {
      
      lines.add(line + System.lineSeparator() );
      line = bufferedReader.readLine();
    }
    
    bufferedReader.close();
    
    return lines;
  }

  /**
   * loadTextFiles
   * load text files with some extension from a directory
   * @param dirPath: path to the directory containing the text files
   * @param filesExtension: extension of text files to be loaded
   * @return a map where keys represent file names and values represent
   *           a file's content
   * @throws Exception
   * */
  public static Map<String, String> loadTextFiles (
		final String dirPath,
		final String filesExtension) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "dir path",
      dirPath,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "files extension",
      filesExtension,
      ExceptionType.CODE_EXCEPTION);
	    
    File dir = new File(dirPath);
    
    File[] files =
      dir.listFiles(new FilenameFilter() {
      public boolean accept(File dir, String name) {
        
        return name.endsWith(filesExtension);
      }
    });
    
    Map<String, String> result = new HashMap<String, String>();
    
    for (File file : files) {
     
      result.put(
        file.getName(),
        loadTextFile(file.getAbsolutePath() ) );
    }
    
    return result;
  }
  
  /**
   * loadTextFileWithoutComments
   * @param commentLinePrefix
   * @param textFilePath
   * @return string content of the file without lines starting with
   *           param char commentLinePrefix
   * @throws Exception
   */
  public static String loadTextFileWithoutComments (
    final char commentLinePrefix,
    final String textFilePath) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "comment line prefix",
      commentLinePrefix,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "file path",
      textFilePath,
      ExceptionType.CODE_EXCEPTION);
    
    BufferedReader bufferedReader =
      new BufferedReader(new FileReader(textFilePath) );
    
    StringBuilder stringBuilder = new StringBuilder();
    String line = bufferedReader.readLine();
    String currLine;

    while (line != null) {
      
      if (line.length() > 0) {
      
        currLine = line.trim();
        // skip commented lines
        if (currLine.length() > 0 &&
            currLine.charAt(0) == commentLinePrefix) {

          line = bufferedReader.readLine();

          continue;
        }
      }
      
      stringBuilder.append(line);
      stringBuilder.append(System.lineSeparator() );
      line = bufferedReader.readLine();
    }
    
    bufferedReader.close();
    
    return stringBuilder.toString();
  }
  
  /**
   * loadTextFilesWithoutComments
   * @param commentLinePrefix
   * @param dirPath
   * @param filesExtension
   * @return map of (file_name, string file content) pairs while lines
   *           starting with param char commentLinePrefix are omitted
   * @throws Exception
   */
  public static Map<String, String> loadTextFilesWithoutComments (
    final char commentLinePrefix,
    final String dirPath,
    final String filesExtension) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "dir path",
      dirPath,
      ExceptionType.CODE_EXCEPTION);
    
    File dir = new File(dirPath);

    File[] files = dir.listFiles(new FilenameFilter() {
      public boolean accept(File dir, String name) {

        return name.endsWith(filesExtension);
      }
    });

    Map<String, String> result = new HashMap<String, String>();

    for (File file : files) {

      result.put(
        file.getName(),
        loadTextFileWithoutComments(
          commentLinePrefix,
          file.getAbsolutePath() ) );
    }

    return result;
  }

  /**
   * loadFiles
   * load files with some extension from a directory
   * @param dirPath: path to the directory containing the files
   * @param filesExtension: extension of text files to be loaded
   * @return file array for files with extension (filesExtension) inside
   *           dirPath
   * @throws Exception
   * */
  public static File[] loadFiles (
    final String dirPath,
    final String filesExtension) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "dir path",
      dirPath,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "files extenstion",
      filesExtension,
      ExceptionType.CODE_EXCEPTION);
    
    File dir = new File(dirPath);
    
    return dir.listFiles(new FilenameFilter() {
      public boolean accept(File dir, String name) {
        
        return name.endsWith(filesExtension);
      }
    });
  }
  
  /**
   * loadFiles
   * load all files from a directory
   * @param dirPath
   * @return file array for files inside param dirPath
   * @throws Exception
   */
  public static File[] loadFiles (
    final String dirPath) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "dir path",
      dirPath,
      ExceptionType.CODE_EXCEPTION);
    
    File dir = new File(dirPath);
    
    return dir.listFiles();
  }

  /**
   * loadImageFile
   * Load image file to BufferedImage
   * @param imageFilePath (relative to execution or absolute)
   * @return BufferedImage
   * @throws Exception
   * */
  public static BufferedImage loadImageFile (
    final String imageFilePath) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "image file path",
      imageFilePath,
      ExceptionType.CODE_EXCEPTION);
    
    return ImageIO.read(new File(imageFilePath) );
  }
  
  /**
   * loadObjectFile
   * @param filePath
   * @return Object read from File(param filePath)
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public static <T> T loadObjectFile (
    final String filePath) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "file path",
      filePath,
      ExceptionType.CODE_EXCEPTION);
    
    ObjectInputStream objectInputStream =
      new ObjectInputStream(new FileInputStream(filePath) );
    
    Object fileContent = objectInputStream.readObject();
    objectInputStream.close();
    
    return (T)fileContent;
  }
}
