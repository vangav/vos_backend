/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * no license, I know you already got more than enough to worry about
 * keep going, never give up
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
        name
        + " Invalid IP V4 ["
        + ip
        + "]",
        exceptionType,
        ExceptionClass.ARGUMENT);
    }
  }
}
