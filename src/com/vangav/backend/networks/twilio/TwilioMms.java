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

package com.vangav.backend.networks.twilio;

import java.io.Serializable;

import com.vangav.backend.content.formatting.SerializationInl;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;
import com.vangav.backend.networks.twilio.dispatch_message.TwilioMmsDispatchable;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * TwilioMms represents a Twilio's MMS message
 * */
public class TwilioMms implements Serializable {
  
  /**
   * Generated serial version ID
   */
  private static final long serialVersionUID = -3941228685812951746L;

  private String toPhoneNumber;
  private String fromPhoneNumber;
  private String message;
  private String mediaUrl;
  
  private static String defaultFromPhoneNumber = null;
  
  /**
   * setDefaultFromPhoneNumber
   * use this method to set one static from-phone-number for all Twilio MMSs
   * @param defaultFromPhoneNumber
   * @throws Exception
   */
  public static void setDefaultFromPhoneNumber (
    String defaultFromPhoneNumber) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "default-from-phone-number",
      defaultFromPhoneNumber,
      ExceptionType.CODE_EXCEPTION);
    
    TwilioMms.defaultFromPhoneNumber = defaultFromPhoneNumber;
  }
  
  /**
   * Constructor TwilioMms
   * use this constructor if you already used the setDefaultFromPhoneNumber
   *   method to set a default-from-phone-number
   * @param toPhoneNumber
   * @param message
   * @param mediaUrl
   * @return new TwilioMms Object
   * @throws Exception
   */
  public TwilioMms (
    String toPhoneNumber,
    String message,
    String mediaUrl) throws Exception {

    this(toPhoneNumber, TwilioMms.defaultFromPhoneNumber, message, mediaUrl);
  }
  
  /**
   * Constructor TwilioMms
   * use this constructor if you already used the setDefaultFromPhoneNumber
   *   method to set a default-from-phone-number
   * @param toPhoneNumber
   * @param mediaUrl
   * @return new TwilioMms Object
   * @throws Exception
   */
  public TwilioMms (
    String toPhoneNumber,
    String mediaUrl) throws Exception {
    
    this(toPhoneNumber, TwilioMms.defaultFromPhoneNumber, "", mediaUrl);
  }

  /**
   * Constructor TwilioMms
   * @param toPhoneNumber
   * @param fromPhoneNumber
   * @param message
   * @param mediaUrl
   * @return new TwilioMms Object
   * @throws Exception
   */
  public TwilioMms (
    String toPhoneNumber,
    String fromPhoneNumber,
    String message,
    String mediaUrl) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "to-phone-number",
      toPhoneNumber,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "from-phone-number",
      fromPhoneNumber,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "media-url",
      mediaUrl,
      ExceptionType.CODE_EXCEPTION);
    
    this.toPhoneNumber = toPhoneNumber;
    this.fromPhoneNumber = fromPhoneNumber;
    this.message = message;
    this.mediaUrl = mediaUrl;
  }
  
  /**
   * fromTwilioMmsDispatchable
   * usually used on the worker instance side where dispatch messages are
   *   received then gets executed
   * @param twilioMmsDispatchable is a JSON Object with a serialized
   *          version of a TwilioMms Object
   * @return the deserialized version of the TwilioMms Object
   * @throws Exception
   */
  public static TwilioMms fromTwilioMmsDispatchable (
    TwilioMmsDispatchable twilioMmsDispatchable) throws Exception {
    
    return
      SerializationInl.<TwilioMms>deserializeObject(
        twilioMmsDispatchable.serialized_message);
  }
  
  /**
   * getToPhoneNumber
   * @return this twilio's mms to-phone-number
   * @throws Exception
   */
  public String getToPhoneNumber () throws Exception {
    
    return this.toPhoneNumber;
  }
  
  /**
   * getFromPhoneNumber
   * @return this twilio's mms from-phone-number
   * @throws Exception
   */
  public String getFromPhoneNumber () throws Exception {
    
    return this.fromPhoneNumber;
  }
  
  /**
   * getMessage
   * @return this twilio's mms message
   * @throws Exception
   */
  public String getMessage () throws Exception {
    
    return this.message;
  }
  
  /**
   * getMediaUrl
   * @return this twilio's mms media url
   * @throws Exception
   */
  public String getMediaUrl () throws Exception {
    
    return this.mediaUrl;
  }
  
  @Override
  public String toString () {
    
    return
      "Twilio Mms:"
      + "\nfrom-phone-number ("
      + this.fromPhoneNumber
      + ")\nto-phone-number ("
      + this.toPhoneNumber
      + ")\nmessage ("
      + this.message
      + ")\nmedia-url ("
      + this.mediaUrl
      + ")";
  }
}
