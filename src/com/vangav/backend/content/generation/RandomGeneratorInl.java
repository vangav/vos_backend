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

package com.vangav.backend.content.generation;

import java.util.Random;

import com.vangav.backend.math.NumbersInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * RandomGeneratorInl has inline static method for generating random stuff
 *   (e.g: string, e-mail, number, etc ...)
 * */
public class RandomGeneratorInl {

  // disable default instantiation
  private RandomGeneratorInl () {}
  
  /**
   * randomGenerator is used by all generator methods
   */
  private static Random randomGenerator = new Random();

  private static final String kLowerCaseString =
    "qwertyuiopasdfghjklzxcvbnm";
  /**
   * generateRandomLowerCaseString
   * generate a random string consisting of lower case letters and
   *   has length equal to passed length
   * @param length
   * @return a randomly generated string
   * @throws Exception
   */
  public static String generateRandomLowerCaseString (
    final int length) throws Exception {
    
    return generateRandomString (kLowerCaseString, length);
  }
  
  private static final String kUpperCaseString =
    "QWERTYUIOPASDFGHJKLZXCVBNM";
  /**
   * generateRandomUpperCaseString
   * generate a random string consisting of upper case letters and
   *   has length equal to passed length
   * @param length
   * @return a randomly generated string
   * @throws Exception
   */
  public static String generateRandomUpperCaseString (
    final int length) throws Exception {
    
    return generateRandomString (kUpperCaseString, length);
  }
  
  private static final String kAlphaString =
    "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
  /**
   * generateRandomAlphaString
   * generate a random string consisting of upper and lower case letters and
   *   has length equal to passed length
   * @param length
   * @return a randomly generated string
   * @throws Exception
   */
  public static String generateRandomAlphaString (
    final int length) throws Exception {
    
    return generateRandomString (kAlphaString, length);
  }
  
  private static final String kNumericString =
    "0123456789";
  /**
   * generateRandomNumericString
   * generate a random string consisting of digits and
   *   has length equal to passed length
   * @param length
   * @return a randomly generated string
   * @throws Exception
   */
  public static String generateRandomNumericString (
    final int length) throws Exception {
    
    return generateRandomString (kNumericString, length);
  }
  
  private static final String kAlphaNumericString =
    "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
  /**
   * generateRandomAlphaNumericString
   * generate a random string consisting of digits and lower/upper case letters
   *   and has length equal to passed length
   * @param length
   * @return a randomly generated string
   * @throws Exception
   */
  public static String generateRandomAlphaNumericString (
    final int length) throws Exception {
    
    return generateRandomString (kAlphaNumericString, length);
  }
  
  private static final String kAlphaNumericSpecialCharactersString =
    "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM"
    + " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
  /**
   * generateRandomAlphaNumericString
   * generate a random string consisting of digits, lower/upper case letters
   *   and special characters and has length equal to passed length
   * @param length
   * @return a randomly generated string
   * @throws Exception
   */
  public static String generateRandomAlphaNumericSpecialCharactersString (
    final int length) throws Exception {
    
    return generateRandomString (kAlphaNumericSpecialCharactersString, length);
  }
  
  private static final int kEmailNameLength = 8;
  private static final int kEmailAddressLength = 5;
  private static final int kEmailDomainLength = 3;
  /**
   * generateRandomEmail
   * @return random e-mail address string(at)string.string
   * @throws Exception
   */
  public static String generateRandomEmail () throws Exception {
    
    return
      generateRandomAlphaString(kEmailNameLength)
      + "@"
      + generateRandomAlphaString(kEmailAddressLength)
      + "."
      + generateRandomAlphaString(kEmailDomainLength);
  }
  
  /**
   * generateRandomString
   * generates all types of random strings
   * @param generationSet
   * @param length
   * @return
   * @throws Exception
   */
  private static String generateRandomString (
    final String generationSet,
    final int length) throws Exception {

    StringBuffer result = new StringBuffer();

    for (int i = 0; i < length; i ++) {

      result.append(generationSet.charAt(
        randomGenerator.nextInt(generationSet.length() ) ) );
    }
    
    return result.toString();
  }
  
  /**
   * generateRandomBoolean
   * generates a random boolean
   * @return randomly true or false
   * @throws Exception
   */
  public static boolean generateRandomBoolean () throws Exception {
    
    return randomGenerator.nextBoolean();
  }
  
  /**
   * generateRandomInteger
   * generates an int d where d >= minLimit and d <= maxLimit
   * @param minLimit
   * @param maxLimit
   * @return random int
   * @throws Exception
   */
  public static int generateRandomInteger (
    int minLimit,
    int maxLimit) throws Exception {
    
    return
      (int)NumbersInl.normalize(
        randomGenerator.nextInt(),
        Integer.MIN_VALUE,
        Integer.MAX_VALUE,
        minLimit,
        maxLimit);
  }
  
  /**
   * generateRandomLong
   * generates a long d where d >= minLimit and d <= maxLimit
   * @param minLimit
   * @param maxLimit
   * @return random long
   * @throws Exception
   */
  public static long generateRandomLong (
    long minLimit,
    long maxLimit) throws Exception {
    
    return
      (long)NumbersInl.normalize(
        randomGenerator.nextLong(),
        Long.MIN_VALUE,
        Long.MAX_VALUE,
        minLimit,
        maxLimit);
  }
  
  /**
   * generateRandomDouble
   * generates a double d where d >= minLimit and d <= maxLimit
   * @param minLimit
   * @param maxLimit
   * @return random double
   * @throws Exception
   */
  public static double generateRandomDouble (
    double minLimit,
    double maxLimit) throws Exception {
    
    return
      NumbersInl.normalize(
        randomGenerator.nextDouble(),
        Double.MIN_VALUE,
        Double.MAX_VALUE,
        minLimit,
        maxLimit);
  }
}
