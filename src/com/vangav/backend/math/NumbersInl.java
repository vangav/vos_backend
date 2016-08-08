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

package com.vangav.backend.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * NumbersInl handles all basic operations related to numbers
 * inline static methods
 * */
public class NumbersInl {

  // disable default instantiation
  private NumbersInl () {}

  public static final long kTen =
    10;
  public static final long kHundred =
    100;
  public static final long kThousand =
    1000;
  public static final long kTenThousand =
    10000;
  public static final long kHundredThousand =
    100000;
  public static final long kMillion =
    1000000;
  public static final long kTenMillion =
    10000000;
  public static final long kHundredMillion =
    100000000;
  public static final long kBillion =
    1000000000;

  /**
   * trim
   * trims a number if it's less than a minimum or greater than a maximum value
   * @param value
   * @param min
   * @param max
   * @return trimmed value
   * @throws Exception
   */
  public static int trim (
    int value,
    int min,
    int max) throws Exception {
    
    if (value < min) {
      
      return min;
    }
    
    if (value > max) {
      
      return max;
    }
    
    return value;
  }
  
  /**
   * normalize
   * @param value
   * @param min
   * @param max
   * @param normalizedMin
   * @param normalizedMax
   * @return normalized value
   * @throws Exception
   */
  public static double normalize (
    double value,
    double min,
    double max,
    double normalizedMin,
    double normalizedMax) throws Exception {
    
    return
      ((min - max) *
      value - normalizedMax *
      min + max *
      normalizedMin) /
      (normalizedMin - normalizedMax);
  }
  
  /**
   * formatFloatingNumber
   * formats the number of digits after a floating point
   * @param value
   * @param postFloatingPointDigitsCount
   * @return formated double
   * @throws Exception
   */
  public static double formatFloatingPointNumber (
    double value,
    int postFloatingPointDigitsCount) throws Exception {
    
    if (Double.isInfinite(value) == true ||
        Double.isNaN(value) == true) {
      
      return value;
    }
    
    return
      new BigDecimal(value).setScale(
        postFloatingPointDigitsCount,
        RoundingMode.HALF_UP).doubleValue();
  }
  
  /**
   * longToString
   * @param value
   * @param stringLength
   * @return string representation of param value prepended with enough zeros
   *           to make the string's length equal to param stringLength
   *         e.g.: 123, 5 --> "00123"
   * @throws Exception
   */
  public static String longToString (
    long value,
    int stringLength) throws Exception {
    
    String valueStr = "" + value;
    
    if (valueStr.length() > stringLength) {
      
      throw new CodeException(
        "value ["
        + value
        + "] length is greater than stringLength ["
        + stringLength
        + "]",
        ExceptionClass.ARGUMENT);
    }
    
    if (valueStr.length() == stringLength) {
      
      return valueStr;
    }
    
    while (valueStr.length() < stringLength) {
      
      valueStr = "0" + valueStr;
    }
    
    return valueStr;
  }
}
