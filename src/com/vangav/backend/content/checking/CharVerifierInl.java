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

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * CharVerifierInl has inline static methods for verifying a char
 * */
public class CharVerifierInl {

  // disable default instantiation
  private CharVerifierInl () {}
  
  /**
   * Enum Char Type (upper case, under score, digit, etc ...)
   */
  public enum CharType {
    
    LOWER_CASE,
    UPPER_CASE,
    DIGIT,
    UNDER_SCORE,
    DOT
  }
  
  /**
   * isOneOfChar
   * @param c
   * @param charTypes
   * @return true if param c's type is one of param charTypes and false
   *           otherwise
   * @throws Exception
   */
  public static boolean isOneOfChar (
    char c,
    CharType... charTypes) throws Exception {
    
    if (charTypes == null) {
      
      return false;
    }
    
    for (CharType charType : charTypes) {
      
      if (charType == CharType.LOWER_CASE) {
        
        if (isLowerCaseLetterChar(c) == true) {
          
          return true;
        }
      } else if (charType == CharType.UPPER_CASE) {
        
        if (isUpperCaseLetterChar(c) == true) {
          
          return true;
        }
      } else if (charType == CharType.DIGIT) {
        
        if (isDigitChar(c) == true) {
          
          return true;
        }
      } else if (charType == CharType.UNDER_SCORE) {
        
        if (isUnderScoreChar(c) == true) {
          
          return true;
        }
      } else if (charType == CharType.DOT) {
        
        if (isDotChar(c) == true) {
          
          return true;
        }
      }
    }
    
    return false;
  }
  
  /**
   * isLowerCaseLetterChar
   * @param c
   * @return true if param c is a lower case letter and false otherwise
   * @throws Exception
   */
  public static boolean isLowerCaseLetterChar (char c) throws Exception {
    
    if (c >= 'a' && c <= 'z') {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * isUpperCaseLetterChar
   * @param c
   * @return true if param c is an upper case letter and false otherwise
   * @throws Exception
   */
  public static boolean isUpperCaseLetterChar (char c) throws Exception {
    
    if (c >= 'A' && c <= 'Z') {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * isDigitChar
   * @param c
   * @return 
   * @throws Exception
   */
  public static boolean isDigitChar (char c) throws Exception {
    
    if (c >= '0' && c <= '9') {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * isUnderScoreChar
   * @param c
   * @return true if param c is an underscore and false otherwise
   * @throws Exception
   */
  public static boolean isUnderScoreChar (char c) throws Exception {
    
    if (c == '_') {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * isDotChar
   * @param c
   * @return true if param c is a dot and false otherwise
   * @throws Exception
   */
  public static boolean isDotChar (char c) throws Exception {
    
    if (c == '.') {
      
      return true;
    }
    
    return false;
  }
}
