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

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * JavaCodeVerifierInl has inline static methods for verifying the correctness
 *   of JAVA code
 * */
public class JavaCodeVerifierInl {
  
  // disable default instantiation
  private JavaCodeVerifierInl () {}

  /**
   * verifyIdentifier
   * throws an exception if java code identifier is invalid
   * @param identifierName
   * @param identifier
   * @throws Exception
   */
  public static void verifyIdentifier (
    String identifierName,
    String identifier) throws Exception {

    ArgumentsInl.checkNotEmpty(
      "identifier ["
        + identifier
        + "]",
      identifier,
      ExceptionType.CODE_EXCEPTION);
      
    if (CharVerifierInl.isOneOfChar(
          identifier.charAt(0),
          CharVerifierInl.CharType.LOWER_CASE,
          CharVerifierInl.CharType.UPPER_CASE,
          CharVerifierInl.CharType.UNDER_SCORE) == false) {
      
      throw new CodeException(
        23,
        4,
        "["
        + identifierName
        + "] "
        + "Java code identifier must start with a letter or an underscore ["
        + identifier
        + "]",
        ExceptionClass.FORMATTING);
    }
    
    for (int i = 1; i < identifier.length(); i ++) {
      
      if (CharVerifierInl.isOneOfChar(
          identifier.charAt(0),
          CharVerifierInl.CharType.LOWER_CASE,
          CharVerifierInl.CharType.UPPER_CASE,
          CharVerifierInl.CharType.UNDER_SCORE,
          CharVerifierInl.CharType.DIGIT) == false) {
        
        throw new CodeException(
          23,
          5,
          "["
          + identifierName
          + "] "
          + "Java code identifier must contain only letters, underscores and "
          + "digits ["
          + identifier
          + "]",
          ExceptionClass.FORMATTING);
      }
    }
  }
  
  /**
   * verifyPackageName
   * throws an exception if param packageName doesn't have a valid format
   * @param packageName
   * @throws Exception
   */
  public static void verifyPackageName (
    String packageName) throws Exception {

    ArgumentsInl.checkNotEmpty(
      "package name",
      packageName,
      ExceptionType.CODE_EXCEPTION);
    
    for (int i = 0; i < packageName.length(); i ++) {
      
      if (CharVerifierInl.isOneOfChar(
          packageName.charAt(i),
          CharVerifierInl.CharType.LOWER_CASE,
          CharVerifierInl.CharType.UPPER_CASE,
          CharVerifierInl.CharType.UNDER_SCORE) == false) {
        
        throw new CodeException(
          23,
          6,
          "invalid java package name ["
          + packageName
          + "]",
          ExceptionClass.FORMATTING);
      }
      
      for (int j = i; j < (packageName.length() - 1); j ++, i ++) {
        
        if (CharVerifierInl.isOneOfChar(
            packageName.charAt(j),
            CharVerifierInl.CharType.LOWER_CASE,
            CharVerifierInl.CharType.UPPER_CASE,
            CharVerifierInl.CharType.UNDER_SCORE,
            CharVerifierInl.CharType.DIGIT,
            CharVerifierInl.CharType.DOT) == false) {
          
          throw new CodeException(
            23,
            7,
            "invalid java package name ["
            + packageName
            + "]",
            ExceptionClass.FORMATTING);
        }

        if (CharVerifierInl.isDotChar(packageName.charAt(j) ) == true) {
          
          break;
        }
      }
    }
  }
}
