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

package com.vangav.backend.play_framework.param;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ParamType is used to validate parameters and store them in the right format
 *   in the database
 * */
public enum ParamType {

  SHORT,
  INT,
  LONG,
  FLOAT,
  DOUBLE,

  LATITUDE,
  LONGITUDE,
  ALTITUDE,

  ALPHA_NUMERIC,
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

  PHOTO,
  CAPTION,
  CHAT_MSG,

  TRACKING_ID;
  
  /**
   * getPrimitiveType
   * @return primitive type string of this ParamType
   * @throws Exception
   */
  public String getPrimitiveType () throws Exception {
    
    switch (this) {
      
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
      case NAME:
      case USER_NAME:
      case DATE:
      case UUID:
      case AUTH_CODE:
      case ACCESS_TOKEN:
      case REFRESH_TOKEN:
      case EMAIL:
      case PASSWORD:
      case PHONE_NUMBER:
      case FB_ID:
      case FB_ACCESS_TOKEN:
      case PHOTO:
      case CAPTION:
      case CHAT_MSG:
      case TRACKING_ID:
        return "String";

      default:
        throw new CodeException(
          "Unhandled type ["
            + this.toString()
            + "]",
          ExceptionClass.TYPE);
    }
  }
}
