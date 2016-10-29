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

package com.vangav.backend.networks;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Download: inline static methods for downloading files by URLs
 * */
public class DownloadInl {

  // disable default instantiation
  private DownloadInl () {}
  
  /**
   * downloadFileAsByteArray
   * @param urlStr: file's url
   * @return byte array containing the file's content
   * @throws Exception
   */
  public static byte[] downloadFileAsByteArray (
    String urlStr) throws Exception {
    
    try {

      return downloadFile(urlStr).toByteArray();
    } catch (Exception e) {
      
      return null;
    }
  }
  
  /**
   * downloadFileAsString
   * @param urlStr: file's url
   * @return string containing the file's content
   * @throws Exception
   */
  public static String downloadFileAsString (
    String urlStr) throws Exception {
    
    try {

      return downloadFile(urlStr).toString();
    } catch (Exception e) {
      
      return null;
    }
  }
  
  /**
   * downloadFile
   * @param urlStr: file's url
   * @return byte array output stream containing the file's content
   * @throws Exception
   */
  private static ByteArrayOutputStream downloadFile (
    String urlStr) throws Exception {
    
    InputStream in = null;
    ByteArrayOutputStream out = null;
    
    try {
      
      URL url = new URL(urlStr);
      
      in = new BufferedInputStream(url.openStream() );
      
      out = new ByteArrayOutputStream();
      
      byte[] buf = new byte[1024];
      
      int n = 0;
      
      while (-1 != (n = in.read(buf) ) ) {
        
         out.write(buf, 0, n);
      }
      
      out.close();
      in.close();
      
      return out;
      
    } catch (Exception eLevelOne) {
      
      try {
        
        out.close();
        in.close();
      } catch (Exception eLevelTwo) {
        
      }
      
      return null;
    }
  }
}
