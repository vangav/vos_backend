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

package com.vangav.backend.backend_client_java.param;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ParamType is used to represent varios param types
 * */
public enum ParamType {

  BOOLEAN,
  
  SHORT,
  INT,
  LONG,
  FLOAT,
  DOUBLE,

  LATITUDE,
  LONGITUDE,
  ALTITUDE,

  ALPHA_NUMERIC,
  ALPHA_NUMERIC_SPACE,
  ALPHA_NUMERIC_DASH,
  ALPHA_NUMERIC_UNDERSCORE,
  ALPHA_NUMERIC_SPACE_DASH,
  ALPHA_NUMERIC_SPACE_UNDERSCORE,
  ALPHA_NUMERIC_DASH_UNDERSCORE,
  ALPHA_NUMERIC_SPACE_DASH_UNDERSCORE,
  NAME,
  USER_NAME,
  DATE,
  UUID,

  AUTH_CODE,
  ACCESS_TOKEN,
  REFRESH_TOKEN,

  EMAIL,
  PASSWORD,
  PHONE_NUMBER,

  FB_ID,
  FB_ACCESS_TOKEN,
  
  DEVICE_TOKEN,
  ANDROID_DEVICE_TOKEN,
  IOS_DEVICE_TOKEN,

  PHOTO,
  CAPTION,
  CHAT_MSG,

  TRACKING_ID,
  
  /**
   * NOTE: USE WITH EXTREME CAUTION
   * */
  NO_VALIDATION_TYPE;
  
  /**
   * getPrimitiveType
   * @param paramTypeString
   * @return primitive type string of param paramTypeString
   * @throws Exception
   */
  public static String getPrimitiveType (
    String paramTypeString) throws Exception {
    
    ParamType paramType = ParamType.valueOf(paramTypeString);
    
    return paramType.getPrimitiveType();
  }
  
  /**
   * getPrimitiveType
   * @return primitive type string of this ParamType
   * @throws Exception
   */
  public String getPrimitiveType () throws Exception {
    
    switch (this) {
      
      case BOOLEAN:
        return "boolean";
      
      case SHORT:
        return "short";
      case INT:
        return "int";
      case LONG:
        return "long";
      case FLOAT:
        return "float";
      case DOUBLE:
        return "double";

      case LATITUDE:
      case LONGITUDE:
      case ALTITUDE:
        return "double";

      case ALPHA_NUMERIC:
      case ALPHA_NUMERIC_SPACE:
      case ALPHA_NUMERIC_DASH:
      case ALPHA_NUMERIC_UNDERSCORE:
      case ALPHA_NUMERIC_SPACE_DASH:
      case ALPHA_NUMERIC_SPACE_UNDERSCORE:
      case ALPHA_NUMERIC_DASH_UNDERSCORE:
      case ALPHA_NUMERIC_SPACE_DASH_UNDERSCORE:
      case NAME:
      case USER_NAME:
      case DATE:
      case UUID:
        return "String";

      case AUTH_CODE:
      case ACCESS_TOKEN:
      case REFRESH_TOKEN:
        return "String";

      case EMAIL:
      case PASSWORD:
      case PHONE_NUMBER:
        return "String";

      case FB_ID:
      case FB_ACCESS_TOKEN:
        return "String";
        
      case DEVICE_TOKEN:
      case ANDROID_DEVICE_TOKEN:
      case IOS_DEVICE_TOKEN:
        return "String";

      case PHOTO:
      case CAPTION:
      case CHAT_MSG:
        return "String";

      case TRACKING_ID:
        return "String";
        
      case NO_VALIDATION_TYPE:
        return "String";

      default:
        throw new CodeException(
          11,
          9,
          "Unhandled type ["
            + this.toString()
            + "]",
          ExceptionClass.TYPE);
    }
  }
}
