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
          "Unhandled type ["
            + this.toString()
            + "]",
          ExceptionClass.TYPE);
    }
  }
}
