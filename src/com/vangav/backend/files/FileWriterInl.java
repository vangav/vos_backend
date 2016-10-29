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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;

import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * WriterInl is a group of inline static methods handling writing files to disk
 * */
public class FileWriterInl {
  
  // disable default instantiation
  private FileWriterInl () {}
  
  /**
   * mkdirs
   * creates directories (if doesn't exist) for path
   * @param path: to create directories for
   * @param isFilePath: true if param path refers to a file and false if it
   *                      refers to a directory
   * @return true on success and false on failure
   * @throws Exception
   */
  public static void mkdirs (
    String path,
    boolean isFilePath) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "path",
      path,
      ExceptionType.CODE_EXCEPTION);
    
    if (isFilePath == true) {
    
      new File(path).getParentFile().mkdirs();
    } else {
    
      new File(path).mkdirs();
    }
  }
  
  /**
   * writeTextFile
   * @param lines
   * @param filePath
   * @param appendNewLine
   * @param mkdirs: true if directories should be created and false otherwise
   * @return filePath
   * @throws Exception
   */
  public static String writeTextFile (
    ArrayList<String> lines,
    String filePath,
    boolean appendNewLine,
    boolean mkdirs) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "lines",
      lines,
      ExceptionType.CODE_EXCEPTION);
    
    StringBuffer stringBuffer = new StringBuffer();
    
    if (appendNewLine == true) {
      
      for (String line : lines) {
        
        stringBuffer.append(line);
        stringBuffer.append(System.lineSeparator() );
      }
    } else {
      
      for (String line : lines) {
        
        stringBuffer.append(line);
      }
    }
    
    return writeTextFile(
      stringBuffer.toString(),
      filePath,
      mkdirs);
  }

  /**
   * writeTextFile
   * @param content: files content (e.g.: "Hello World")
   * @param filePath: e.g.: ./text_files/new_text_file.txt
   * @param mkdirs: true if directories should be created and false otherwise
   * @return filePath
   * @throws Exception
   */
  public static String writeTextFile (
    String content,
    String filePath,
    boolean mkdirs) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "file path",
      filePath,
      ExceptionType.CODE_EXCEPTION);
    
    if (mkdirs == true) {
      
      mkdirs(filePath, true);
    }
    
    File file = new File(filePath);
    
    FileWriter fw = new FileWriter(file.getAbsolutePath() );
    BufferedWriter bw = new BufferedWriter(fw);
   
    bw.write(content);
    bw.close();
    
    return filePath;
  }
  
  /**
   * writeObjectFile
   * @param content: Object to be written to the file
   * @param filePath: e.g.: ./object_files/new_file.ext
   * @return filePath
   * @throws Exception
   */
  public static String writeObjectFile (
    Object content,
    String filePath,
    boolean mkdirs) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "content",
      content,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "file path",
      filePath,
      ExceptionType.CODE_EXCEPTION);
    
    if (mkdirs == true) {
      
      mkdirs(filePath, true);
    }
    
    File file = new File(filePath);
    file.createNewFile();
    
    ObjectOutputStream objectOutputStream =
      new ObjectOutputStream(new FileOutputStream(file) );
    objectOutputStream.writeObject(content);
    objectOutputStream.close();

    return filePath;
  }
  
  /**
   * writeHttpResponseFile
   * used when an http request's response is a file, this method writes
   *   the received file on disk in param filePath
   * @param filePath
   * @param urlConnection
   * @param mkdirs
   * @return filePath
   * @throws Exception
   */
  public static String writeHttpResponseFile (
    String filePath,
    URLConnection urlConnection,
    boolean mkdirs) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "file path",
      filePath,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "url connection",
      urlConnection,
      ExceptionType.CODE_EXCEPTION);
    
    if (mkdirs == true) {
      
      mkdirs(filePath, true);
    }
    
    InputStream response = urlConnection.getInputStream();
    
    File targetFile = new File(filePath);
    OutputStream outStream = new FileOutputStream(targetFile);
 
    byte[] buffer = new byte[8 * 1024];
    int bytesRead;
    
    while ((bytesRead = response.read(buffer) ) != -1) {
      
      outStream.write(buffer, 0, bytesRead);
    }
    
    response.close();
    outStream.close();
    
    return filePath;
  }
}
