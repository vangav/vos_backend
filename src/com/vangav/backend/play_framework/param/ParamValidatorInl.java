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

import java.util.UUID;
import java.util.regex.Pattern;

import com.vangav.backend.exceptions.BadRequestException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.play_framework.request.RequestProperties;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ParamValidatorInl validates parameters
 * */
public class ParamValidatorInl {
  
  // disable default instantiation
  private ParamValidatorInl () {}
  
  /**
   * validate
   * validates an array of parameters
   * @param name
   * @param values
   * @param type
   * @param optionality
   * @return true if param value is valid, throws an exception if param
   *           value is mandatory and not valid and return false if param
   *           value is optional and not valid
   * @throws Exception
   */
  public static boolean validate (
    String name,
    Object[] values,
    ParamType type,
    ParamOptionality optionality) throws Exception {
    
    for (Object value : values) {
      
      if (validate(name, value, type, optionality) == false) {
        
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * validate
   * validates a single parameter
   * @param name
   * @param value
   * @param type
   * @param optionality
   * @return true if param value is valid, throws an exception if param
   *           value is mandatory and not valid and return false if param
   *           value is optional and not valid
   * @throws Exception
   */
  public static boolean validate (
    String name,
    Object value,
    ParamType type,
    ParamOptionality optionality) throws Exception {
    
    if (RequestProperties.i().getBooleanProperty(
          RequestProperties.kValidateParam) == false) {
      
      return true;
    }
    
    try {
      
      validateParam(name, String.valueOf(value), type);
    } catch (Exception e) {
      
      if (optionality == ParamOptionality.MANDATORY) {
        
        throw e;
      } else {
        
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * validateParam
   * throws an exception if param value is invalid type of param type
   * @param name
   * @param value
   * @param type
   * @throws Exception
   */
  private static void validateParam (
    String name,
    String value,
    ParamType type) throws Exception {

    if (name == null || value == null) {
      
      throwInvalidParam(name);
    }
    
    if (name.length() == 0 || value.length() == 0) {
      
      throwInvalidParam(name);
    }

    switch (type) {
      
      case BOOLEAN:
        validateBoolean(name, value);
        break;

      case SHORT:
        validateShort(name, value);
        break;
      case INT:
        validateInt(name, value);
        break;
      case LONG:
        validateLong(name, value);
        break;
      case FLOAT:
        validateFloat(name, value);
        break;
      case DOUBLE:
        validateDouble(name, value);
        break;
  
      case LATITUDE:
        validateLatitude(name, value);
        break;
      case LONGITUDE:
        validateLongitude(name, value);
        break;
      case ALTITUDE:
        validateAltitude(name, value);
        break;
        
      case ALPHA_NUMERIC:
        validateAlphaNumeric(name, value);
        break;
      case ALPHA_NUMERIC_SPACE:
        validateAlphaNumericSpace(name, value);
        break;
      case ALPHA_NUMERIC_DASH:
        validateAlphaNumericDash(name, value);
        break;
      case ALPHA_NUMERIC_UNDERSCORE:
        validateAlphaNumericUnderscore(name, value);
        break;
      case ALPHA_NUMERIC_SPACE_DASH:
        validateAlphaNumericSpaceDash(name, value);
        break;
      case ALPHA_NUMERIC_SPACE_UNDERSCORE:
        validateAlphaNumericSpaceUnderscore(name, value);
        break;
      case ALPHA_NUMERIC_DASH_UNDERSCORE:
        validateAlphaNumericDashUnderscore(name, value);
        break;
      case ALPHA_NUMERIC_SPACE_DASH_UNDERSCORE:
        validateAlphaNumericSpaceDashUnderscore(name, value);
        break;
      case NAME:
        validateName(name, value);
        break;
      case USER_NAME:
        validateUserName(name, value);
        break;
      case DATE:
        validateDate(name, value);
        break;
      case UUID:
        
        validateUuid(name, value);
        break;
      case AUTH_CODE:
        validateAuthCode(name, value);
        break;
      case ACCESS_TOKEN:
        validateAccessToken(name, value);
        break;
      case REFRESH_TOKEN:
        validateRefreshToken(name, value);
        break;
        
      case EMAIL:
        validateEmail(name, value);
        break;
      case PASSWORD:
        validatePassword(name, value);
        break;
      case PHONE_NUMBER:
        validatePhoneNumber(name, value);
        break;
        
      case FB_ID:
        validateFbId(name, value);
        break;
      case FB_ACCESS_TOKEN:
        validateFbAccessToken(name, value);
        break;
        
      case DEVICE_TOKEN:
        validateDeviceToken(name, value);
        break;
      case ANDROID_DEVICE_TOKEN:
        validateAndroidDeviceToken(name, value);
        break;
      case IOS_DEVICE_TOKEN:
        validateIosDeviceToken(name, value);
        break;
        
      case PHOTO:
        validatePhoto(name, value);
        break;
      case CAPTION:
        validateCaption(name, value);
        break;
      case CHAT_MSG:
        validateChatMsg(name, value);
        break;
        
      case TRACKING_ID:
        validateTrackingId(name, value);
        break;
        
      case NO_VALIDATION_TYPE:
        break;
        
      default:
        throw new BadRequestException(
          "Invalid param ["
          + name
          + "]",
          ExceptionClass.TYPE);
    }
  }
  
  /**
   * validateBoolean
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateBoolean (
    String name,
    String value) throws Exception {
    
    if ((value.compareTo("true") != 0) && (value.compareTo("false") != 0) ) {
      
      throwInvalidParam(name);
    }
  }
  
  /**
   * validateShort
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateShort (
    String name,
    String value) throws Exception {

    try {

      short shortValue = Short.valueOf(value);
      
      if (shortValue == Short.MIN_VALUE) {
        
        throwInvalidParam(name);
      }
    } catch (Exception e) {
      
      throwInvalidParam(name);
    }
  }

  /**
   * validateInt
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateInt (
    String name,
    String value) throws Exception {

    try {

      int intValue = Integer.valueOf(value);
      
      if (intValue == Integer.MIN_VALUE) {
        
        throwInvalidParam(name);
      }
    } catch (Exception e) {
      
      throwInvalidParam(name);
    }
  }

  /**
   * validateLong
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateLong (
    String name,
    String value) throws Exception {

    try {

      long longValue = Long.valueOf(value);
      
      if (longValue == Long.MIN_VALUE) {
        
        throwInvalidParam(name);
      }
    } catch (Exception e) {
      
      throwInvalidParam(name);
    }
  }
  
  /**
   * validateFloat
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateFloat (
    String name,
    String value) throws Exception {
    
    try {

      float floatValue = Float.valueOf(value);
      
      if (Float.isNaN(floatValue) ) {
        
        throwInvalidParam(name);
      }
    } catch (Exception e) {
      
      throwInvalidParam(name);
    }
  }

  /**
   * validateDouble
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateDouble (
    String name,
    String value) throws Exception {

    try {

      double doubleValue = Double.valueOf(value);
      
      if (Double.isNaN(doubleValue) ) {
        
        throwInvalidParam(name);
      }
    } catch (Exception e) {
      
      throwInvalidParam(name);
    }
  }
  
  /**
   * validateLatitude
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateLatitude (
    String name,
    String value) throws Exception {
    
    try {
      
      double doubleValue = Double.valueOf(value);
      
      if (doubleValue > 90.0 || doubleValue < -90.0) {
        
        throwInvalidParam(name);
      }
    } catch (Exception e) {
      
      throwInvalidParam(name);
    }
  }
  
  /**
   * validateLongitude
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateLongitude (
    String name,
    String value) throws Exception {
    
    try {
      
      double doubleValue = Double.valueOf(value);
      
      if (doubleValue > 180.0 || doubleValue < -180.0) {
        
        throwInvalidParam(name);
      }
    } catch (Exception e) {
      
      throwInvalidParam(name);
    }
  }
  
  /**
   * validateAltitude
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateAltitude (
    String name,
    String value) throws Exception {
    
    try {
      
      double doubleValue = Double.valueOf(value);
      
      if (Double.isNaN(doubleValue) ) {
        
        throwInvalidParam(name);
      }
    } catch (Exception e) {
      
      throwInvalidParam(name);
    }
  }

  // alpha numeric and at least 1 character long
  private static final String kStringRegex = "^[a-zA-Z0-9]{1,}$";
  private static final Pattern kStringPattern = Pattern.compile(kStringRegex);
  /**
   * validateAphaNumeric
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateAlphaNumeric (
    String name,
    String value) throws Exception {

    if (kStringPattern.matcher(value).matches() == false) {
      
      throwInvalidParam(name);
    }
  }
  
  /**
   * validateAlphaNumericSpace
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateAlphaNumericSpace (
    String name,
    String value) throws Exception {
    
    value = value.replaceAll("\\s+","");
    
    validateAlphaNumeric(name, value);
  }
  
  /**
   * validateAlphaNumericDash
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateAlphaNumericDash (
    String name,
    String value) throws Exception {
    
    value = value.replaceAll("-","");
    
    validateAlphaNumeric(name, value);
  }
  
  /**
   * validateAlphaNumericUnderscore
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateAlphaNumericUnderscore (
    String name,
    String value) throws Exception {
    
    value = value.replaceAll("_","");
    
    validateAlphaNumeric(name, value);
  }
  
  /**
   * validateAlphaNumericSpaceDash
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateAlphaNumericSpaceDash (
    String name,
    String value) throws Exception {
    
    value = value.replaceAll("\\s+","");
    value = value.replaceAll("-","");
    
    validateAlphaNumeric(name, value);
  }
  
  /**
   * validateAlphaNumericSpaceUnderscore
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateAlphaNumericSpaceUnderscore (
    String name,
    String value) throws Exception {
    
    value = value.replaceAll("\\s+","");
    value = value.replaceAll("_","");
    
    validateAlphaNumeric(name, value);
  }
  
  /**
   * validateAlphaNumericDashUnderscore
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateAlphaNumericDashUnderscore (
    String name,
    String value) throws Exception {
    
    value = value.replaceAll("-","");
    value = value.replaceAll("_","");
    
    validateAlphaNumeric(name, value);
  }
  
  /**
   * validateAlphaNumericSpaceDashUnderscore
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateAlphaNumericSpaceDashUnderscore (
    String name,
    String value) throws Exception {
    
    value = value.replaceAll("\\s+","");
    value = value.replaceAll("-","");
    value = value.replaceAll("_","");
    
    validateAlphaNumeric(name, value);
  }


  // matches uni-code characters in addition to space, dot, quotation and -
  // minimum length is 1 and max is 100
  private static final String kNameRegex = "^[\\p{L} .'-]{1,100}$";
  private static final Pattern kNamePattern = Pattern.compile(kNameRegex);
  // contains at least 1 char
  private static final String kNameCharRegex = ".*[a-zA-Z]+.*";
  private static final Pattern kNameCharPattern =
    Pattern.compile(kNameCharRegex);
  /**
   * validateName
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateName (
    String name,
    String value) throws Exception {

    if (kNamePattern.matcher(value).matches() == false) {
      
      throwInvalidParam(name);
    }
    
    if (kNameCharPattern.matcher(value).matches() == false) {
      
      throwInvalidParam(name);
    }
  }


  // matches a-z, A-Z, 0-9, points, dashes and underscores
  // length 1 - 25 (allows for 13 ^ 39 non-case sensitive)
  // more than thousand billion billion billion billion user names
  private static final String kUserNameRegex = "^[a-zA-Z0-9._-]{1,25}$";
  private static final Pattern kUserNamePattern =
    Pattern.compile(kUserNameRegex);
  /**
   * validateUserName
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateUserName (
    String name,
    String value) throws Exception {
    
    // omit leading and trailing spaces
    value = value.trim();

    if (kUserNamePattern.matcher(value).matches() == false) {
      
      throwInvalidParam(name);
    }
  }


  // Match d/m/yy and dd/mm/yyyy, allowing any combination of one or two
  // digits for the day and month, and two or four digits for the year
  private static final String kDateRegex =
    "^(3[01]|[12][0-9]|0?[1-9])/(1[0-2]|0?[1-9])/(?:[0-9]{2})?[0-9]{2}$";
  private static final Pattern kDatePattern = Pattern.compile(kDateRegex);
  /**
   * validateDate
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateDate (
    String name,
    String value) throws Exception {

    if (kDatePattern.matcher(value).matches() == false) {
      
      throwInvalidParam(name);
    }
  }

  /**
   * validateUuid
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateUuid (
    String name,
    String value) throws Exception {

    try {

      UUID uuid = UUID.fromString(value);

      if (value.compareToIgnoreCase(uuid.toString() ) != 0) {
        
        throwInvalidParam(name);
      }
    } catch (Exception e) {
      
      throwInvalidParam(name);
    }
  }


  // alpha numeric and 32 characters long
  private static final String kAuthCodeRegex = "^[a-zA-Z0-9]{31,33}$";
  private static final Pattern kAuthCodePattern =
    Pattern.compile(kAuthCodeRegex);
  /**
   * validateAuthCode
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateAuthCode (
    String name,
    String value) throws Exception {

    if (kAuthCodePattern.matcher(value).matches() == false) {
      
      throwInvalidParam(name);
    }
  }


  // alpha numeric and 32 characters long
  private static final String kAccessTokenRegex = "^[a-zA-Z0-9]{31,33}$";
  private static final Pattern kAccessTokenPattern =
    Pattern.compile(kAccessTokenRegex);
  /**
   * validateAccessToken
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateAccessToken (
    String name,
    String value) throws Exception {

    if (kAccessTokenPattern.matcher(value).matches() == false) {
      
      throwInvalidParam(name);
    }
  }


  // alpha numeric and 32 characters long
  private static final String kRefreshTokenRegex = "^[a-zA-Z0-9]{31,33}$";
  private static final Pattern kRefreshTokenPattern =
    Pattern.compile(kRefreshTokenRegex);
  /**
   * validateRefreshToken
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateRefreshToken (
    String name,
    String value) throws Exception {

    if (kRefreshTokenPattern.matcher(value).matches() == false) {
      
      throwInvalidParam(name);
    }
  }

  private static final String kEmailRegex =
    "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
  private static final Pattern kEmailPattern = Pattern.compile(kEmailRegex);
  /**
   * validateEmail
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateEmail (
    String name,
    String value) throws Exception {

    if (kEmailPattern.matcher(value).matches() == false) {
      
      throwInvalidParam(name);
    }
  }


  // alpha numeric and at least 1 character long
  private static final String kPasswordRegex = "^[a-zA-Z0-9]{1,}$";
  private static final Pattern kPasswordPattern =
    Pattern.compile(kPasswordRegex);
  // validates hashed password
  /**
   * validatePassword
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validatePassword (
    String name,
    String value) throws Exception {

    if (kPasswordPattern.matcher(value).matches() == false) {
      
      throwInvalidParam(name);
    }
  }


  private static final String kPhoneNumberRegex =
    "^((\\+[1-9]{3,4}|0[1-9]{4}|00[1-9]{3})\\-?)?\\d{8,20}$";
  private static final Pattern kPhoneNumberPattern =
    Pattern.compile(kPhoneNumberRegex);
  /**
   * validatePhoneNumber
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validatePhoneNumber (
    String name,
    String value) throws Exception {

    if (kPhoneNumberPattern.matcher(value).matches() == false) {
      
      throwInvalidParam(name);
    }
  }


  // validates numberic id with at least one digit
  private static final String kFbIdRegex = "^[0-9]+$";
  private static final Pattern kFbIdPattern = Pattern.compile(kFbIdRegex);
  /**
   * validateFbId
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateFbId (
    String name,
    String value) throws Exception {

    if (kFbIdPattern.matcher(value).matches() == false) {
      
      throwInvalidParam(name);
    }
  }


  // alpha numeric and at least 1 character long
  private static final String kFbAccessTokenRegex = "^[a-zA-Z0-9]{1,}$";
  private static final Pattern kFbAccessTokenPattern =
    Pattern.compile(kFbAccessTokenRegex);
  /**
   * validateFbAccessToken
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateFbAccessToken (
    String name,
    String value) throws Exception {

    if (kFbAccessTokenPattern.matcher(value).matches() == false) {
      
      throwInvalidParam(name);
    }
  }
  
  /**
   * validateDeviceToken
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateDeviceToken (
    String name,
    String value) throws Exception {
    
    try {
      
      validateAndroidDeviceToken(name, value);
      
      return;
    } catch (Exception e) {
      
    }
    
    validateIosDeviceToken(name, value);
  }
  
  /**
   * validateAndroidDeviceToken
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateAndroidDeviceToken (
    String name,
    String value) throws Exception {

    validateAlphaNumericDash(name, value);
  }
  
  /**
   * validateIosDeviceToken
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateIosDeviceToken (
    String name,
    String value) throws Exception {

    validateAlphaNumericDash(name, value);
  }

  // BASE 64 encoded
  private static final String kPhotoRegex =
    "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$";
  private static final Pattern kPhotoPattern = Pattern.compile(kPhotoRegex);
  /**
   * validatePhoto
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validatePhoto (
    String name,
    String value) throws Exception {


    if (kPhotoPattern.matcher(value).matches() == false ||
        value.length() >
        ParamValidatorProperties.i().getIntProperty(
          ParamValidatorProperties.kPhotoSize) ) {
      
      throwInvalidParam(name);
    }
  }
  
  /**
   * validateCaption
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateCaption (
    String name,
    String value) throws Exception {

    if (value.length() >
        ParamValidatorProperties.i().getIntProperty(
          ParamValidatorProperties.kCaptionLength) ) {
      
      throwInvalidParam(name);
    }
  }

  /**
   * validateChatMsg
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateChatMsg (
    String name,
    String value) throws Exception {

    if (value.length() >
        ParamValidatorProperties.i().getIntProperty(
          ParamValidatorProperties.kChatMessageLength) ) {
      
      throwInvalidParam(name);
    }
  }
  
  /**
   * validateTrackingId
   * @param name
   * @param value
   * @throws Exception
   */
  private static void validateTrackingId (
    String name,
    String value) throws Exception {

    if (value.length() > 200 || value.length() < 1) {
      
      throwInvalidParam(name);
    }
  }
  
  /**
   * throwInvalidParam
   * short helper method for throwing an invalid param exception
   * @param name
   * @throws Exception
   */
  private static void throwInvalidParam (String name) throws Exception {

    throw new BadRequestException(
      "Invalid param ["
      + name
      + "]",
      ExceptionClass.INVALID);
  }
}
