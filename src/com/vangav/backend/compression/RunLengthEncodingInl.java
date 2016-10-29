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

package com.vangav.backend.compression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * RunLengthEncodingInl has inline static methods for compression and
 *   decompression using Run-Length-Encoding algorithm
 * e.g.: original [aaaaPPPrrrrr] --> compressed [4a3P5r]
 * NOTE: works only with Alpha Strings [a-z A-Z]
 */
public class RunLengthEncodingInl {

  // disable default instantiation
  private RunLengthEncodingInl () {}
  
  /**
   * compress
   * @param toCompress
   * @return compressed version of param toCompress
   * @throws Exception
   */
  public static String compress (String toCompress) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "toCompress",
      toCompress,
      ExceptionType.CODE_EXCEPTION);

    StringBuffer compressed = new StringBuffer();
    
    int runLength;
    
    for (int i = 0; i < toCompress.length(); i++) {
      
      runLength = 1;
      
      while (i + 1 < toCompress.length() &&
             toCompress.charAt(i) == toCompress.charAt(i + 1) ) {
        
        runLength++;
        i++;
      }
      
      compressed.append(runLength);
      compressed.append(toCompress.charAt(i) );
    }
    
    return compressed.toString();
  }
  
  /**
   * decompress
   * @param toDecompress
   * @return decompressed version of param toDecompress
   * @throws Exception
   */
  public static String decompress (String toDecompress) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "toDecompress",
      toDecompress,
      ExceptionType.CODE_EXCEPTION);

    StringBuffer decompressed = new StringBuffer();
    
    Pattern pattern = Pattern.compile("[0-9]+|[a-zA-Z]");
    Matcher matcher = pattern.matcher(toDecompress);
    
    int number;
    
    while (matcher.find() ) {
      
      number = Integer.parseInt(matcher.group() );
      matcher.find();
      
      while (number -- != 0) {
        
        decompressed.append(matcher.group() );
      }
    }
    
    return decompressed.toString();
  }
}
