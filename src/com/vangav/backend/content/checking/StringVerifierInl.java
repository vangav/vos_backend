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

package com.vangav.backend.content.checking;

import java.util.Arrays;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * StringVerifierInl has inline static methods for verifying a string
 * */
public class StringVerifierInl {

  // disable default instantiation
  private StringVerifierInl () {}
  
  /**
   * isOneOfString
   * throws an exception if param value isn't one of param validValues
   * @param name
   * @param value
   * @param ignoreCase
   * @param validValues
   * @throws Exception
   */
  public static void isOneOfString (
    String name,
    String value,
    boolean ignoreCase,
    String... validValues) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "valid values",
      validValues,
      ExceptionType.CODE_EXCEPTION);
    
    if (value == null) {
      
      for (String validValue : validValues) {
        
        if (validValue == null) {
          
          return;
        }
      }
      
      throw new CodeException(
        "Invalid ["
        + name
        + "] value [NULL] must be one of the valid values --> "
        + Arrays.toString(validValues),
        ExceptionClass.INVALID);
    }
    
    for (String validValue : validValues) {
      
      if (validValue == null) {
        
        continue;
      }
      
      if (ignoreCase == true) {
        
        if (value.compareToIgnoreCase(validValue) == 0) {
          
          return;
        }
      } else {
        
        if (value.compareTo(validValue) == 0) {
          
          return;
        }
      }
    }
    
    throw new CodeException(
      "Invalid ["
      + name
      + "] value ["
      + value
      + "] must be one of the valid values --> "
      + Arrays.toString(validValues),
      ExceptionClass.INVALID);
  }
  
  /**
   * belongsToEnum
   * throws an exception param value isn't one of param validValuesEnum
   * @param name
   * @param value
   * @param validValuesEnum
   * @throws Exception
   */
  public static <E extends Enum<E> > void belongsToEnum (
    String name,
    String value,
    Class<E> validValuesEnum) throws Exception {

    ArgumentsInl.checkNotNull(
      "value of ["
      + name
      + "]",
      value,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "valid values enum",
      validValuesEnum,
      ExceptionType.CODE_EXCEPTION);
    
    for (Enum<E> validValue : validValuesEnum.getEnumConstants() ) {
      
      if (value.compareTo(validValue.name() ) == 0) {
        
        return;
      }
    }
    
    throw new CodeException(
      "Invalid ["
      + name
      + "] value ["
      + value
      + "] must be one of the valid values --> "
      + Arrays.toString(validValuesEnum.getEnumConstants() ),
      ExceptionClass.INVALID);
  }
}
