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

package com.vangav.backend.exceptions.handlers;

import java.util.Collection;
import java.util.Map;

import com.vangav.backend.exceptions.VangavException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ArgumentsInl handles throwing exceptions for illegal arguments
 * */
public class ArgumentsInl {
  
  /**
   * checkInstanceOf
   * @param object: the object to be checked
   * @param type: expected class type
   * @param exceptionType: exception type to throw
   *          (bad request or code exception)
   * @throws VangavException
   * */
  public static void checkInstanceOf (
    Object object,
    Class<?> type,
    ExceptionType exceptionType) throws Exception {
    
    if (object.getClass().equals(type) == false) {
      
      throw VangavException.exceptionFactory(
        41,
        1,
        "Wrong class type ["
        + object.getClass().getName()
        + "] expecting ["
        + type.getName()
        + "]",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }

  /**
   * checkNotNull
   * @param name: name of the object to be checked
   * @param object: object to be checked
   * @param exceptionType: exception type to throw
   *          (bad request or code exception)
   * @throws VangavException
   * */
  public static void checkNotNull (
    String name,
    Object object,
    ExceptionType exceptionType) throws Exception {
    
    if (object == null) {
      
      throw VangavException.exceptionFactory(
        41,
        2,
        name
        + " can't be null",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }
  
  /**
   * checkNotEmpty
   * @param name
   * @param string
   * @param exceptionType
   * @throws Exception if param string is null or empty
   */
  public static void checkNotEmpty (
    String name,
    String string,
    ExceptionType exceptionType) throws Exception {
    
    checkNotNull(name, string, exceptionType);
    
    if (string.length() == 0) {
      
      throw VangavException.exceptionFactory(
        41,
        3,
        name
        + " can't be empty",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }
  
  /**
   * checkNotEmpty
   * @param name
   * @param array
   * @param exceptionType
   * @throws Exception if param array is null or empty
   */
  public static void checkNotEmpty (
    String name,
    Object[] array,
    ExceptionType exceptionType) throws Exception {
    
    checkNotNull(name, array, exceptionType);
    
    if (array.length == 0) {
      
      throw VangavException.exceptionFactory(
        41,
        4,
        name
        + " can't be empty",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }
  
  /**
   * checkNotEmpty
   * @param name
   * @param array
   * @param exceptionType
   * @throws Exception if param array is null or empty
   */
  public static void checkNotEmpty (
    String name,
    short[] array,
    ExceptionType exceptionType) throws Exception {
    
    checkNotNull(name, array, exceptionType);
    
    if (array.length == 0) {
      
      throw VangavException.exceptionFactory(
        41,
        5,
        name
        + " can't be empty",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }
  
  /**
   * checkNotEmpty
   * @param name
   * @param array
   * @param exceptionType
   * @throws Exception if param array is null or empty
   */
  public static void checkNotEmpty (
    String name,
    int[] array,
    ExceptionType exceptionType) throws Exception {
    
    checkNotNull(name, array, exceptionType);
    
    if (array.length == 0) {
      
      throw VangavException.exceptionFactory(
        41,
        6,
        name
        + " can't be empty",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }
  
  /**
   * checkNotEmpty
   * @param name
   * @param array
   * @param exceptionType
   * @throws Exception if param array is null or empty
   */
  public static void checkNotEmpty (
    String name,
    long[] array,
    ExceptionType exceptionType) throws Exception {
    
    checkNotNull(name, array, exceptionType);
    
    if (array.length == 0) {
      
      throw VangavException.exceptionFactory(
        41,
        7,
        name
        + " can't be empty",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }
  
  /**
   * checkNotEmpty
   * @param name
   * @param array
   * @param exceptionType
   * @throws Exception if param array is null or empty
   */
  public static void checkNotEmpty (
    String name,
    float[] array,
    ExceptionType exceptionType) throws Exception {
    
    checkNotNull(name, array, exceptionType);
    
    if (array.length == 0) {
      
      throw VangavException.exceptionFactory(
        41,
        8,
        name
        + " can't be empty",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }
  
  /**
   * checkNotEmpty
   * @param name
   * @param array
   * @param exceptionType
   * @throws Exception if param array is null or empty
   */
  public static void checkNotEmpty (
    String name,
    double[] array,
    ExceptionType exceptionType) throws Exception {
    
    checkNotNull(name, array, exceptionType);
    
    if (array.length == 0) {
      
      throw VangavException.exceptionFactory(
        41,
        9,
        name
        + " can't be empty",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }
  
  /**
   * checkNotEmpty
   * @param name
   * @param collection
   * @param exceptionType
   * @throws Exception if param collection is null or empty
   */
  public static void checkNotEmpty (
    String name,
    Collection<?> collection,
    ExceptionType exceptionType) throws Exception {
    
    checkNotNull(name, collection, exceptionType);
    
    if (collection.size() == 0) {
      
      throw VangavException.exceptionFactory(
        41,
        10,
        name
        + " can't be empty",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }
  
  /**
   * checkNotEmpty
   * @param name
   * @param map
   * @param exceptionType
   * @throws Exception if param map is null or empty
   */
  public static void checkNotEmpty (
    String name,
    Map<?, ?> map,
    ExceptionType exceptionType) throws Exception {
    
    checkNotNull(name, map, exceptionType);
    
    if (map.size() == 0) {
      
      throw VangavException.exceptionFactory(
        41,
        11,
        name
        + " can't be empty",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }
  
  /**
   * checkIntWithinRange
   * @param name: name of the integer to be checked
   * @param value: value of the integer to be checked
   * @param min: minimum valid value
   * @param max: maximum valid value
   * @param exceptionType: exception type to throw
   *          (bad request or code exception)
   * @throws VangavException
   * */
  public static void checkIntWithinRange (
    String name,
    int value,
    int min,
    int max,
    ExceptionType exceptionType) throws Exception {
    
    if (value < min || value > max) {
      
      throw VangavException.exceptionFactory(
        41,
        12,
        name
        + " value ["
        + value
        + "] min ["
        + min
        + "] max ["
        + max
        + "] is out of range",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }
  
  /**
   * checkIntGreaterThanOrEqual
   * @param name: name of the integer to be checked
   * @param value: value of the integer to be checked
   * @param minLimit: minimum valid value
   * @param exceptionType: exception type to throw
   *          (bad request or code exception)
   * @throws VangavException
   * */
  public static void checkIntGreaterThanOrEqual (
    String name,
    int value,
    int minLimit,
    ExceptionType exceptionType) throws Exception {
    
    if (value < minLimit) {
      
      throw VangavException.exceptionFactory(
        41,
        13,
        name
        + " value ["
        + value
        + "] min limit ["
        + minLimit
        + "] is less than the minimum limit",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }
  
  /**
   * checkLongGreaterThanOrEqual
   * @param name: name of the long to be checked
   * @param value: value of the long to be checked
   * @param minLimit: minimum valid value
   * @param exceptionType: exception type to throw
   *          (bad request or code exception)
   * @throws VangavException
   * */
  public static void checkLongGreaterThanOrEqual (
    String name,
    long value,
    long minLimit,
    ExceptionType exceptionType) throws Exception {
    
    if (value < minLimit) {
      
      throw VangavException.exceptionFactory(
        41,
        14,
        name
        + " value ["
        + value
        + "] min limit ["
        + minLimit
        + "] is less than the minimum limit",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }
  
  /**
   * checkDoubleHasValue
   * throws an exception is param value is NAN or infinite
   * @param name
   * @param value
   * @param exceptionType
   * @throws Exception
   */
  public static void checkDoubleHasValue (
    String name,
    double value,
    ExceptionType exceptionType) throws Exception {
    
    if (Double.isNaN(value) || Double.isInfinite(value) ) {
     
      throw VangavException.exceptionFactory(
        41,
        15,
        name
        + " double ["
        + value
        + "] has no value",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }
  
  /**
   * checkIpV4
   * throws an exception if param ip isn't a valid IP V4
   * @param name
   * @param ip
   * @param exceptionType
   * @throws Exception
   */
  public static void checkIpV4 (
    String name,
    String ip,
    ExceptionType exceptionType) throws Exception {
    
    try {
      
      String [] ipSplit = ip.split("\\.");
      int currValue;
      
      for (int i = 0; i < 4; i ++) {
        
        currValue = Integer.parseInt(ipSplit[i] );
        
        checkIntWithinRange(
          "",
          currValue,
          0,
          255,
          exceptionType);
      }
    } catch (Exception e) {
      
      throw VangavException.exceptionFactory(
        41,
        16,
        name
        + " Invalid IP V4 ["
        + ip
        + "]",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }
}
