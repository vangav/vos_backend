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

package com.vangav.backend.networks.twilio;

import java.io.Serializable;

import com.vangav.backend.content.formatting.SerializationInl;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;
import com.vangav.backend.networks.twilio.dispatch_message.TwilioSmsDispatchable;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * TwilioSms represents a Twilio's SMS message
 * */
public class TwilioSms implements Serializable {

  /**
   * Generated serial version ID
   */
  private static final long serialVersionUID = -7540751023286569997L;

  private String toPhoneNumber;
  private String fromPhoneNumber;
  private String message;
  
  private static String defaultFromPhoneNumber = null;
  
  /**
   * setDefaultFromPhoneNumber
   * use this method to set one static from-phone-number for all Twilio SMSs
   * @param defaultFromPhoneNumber
   * @throws Exception
   */
  public static void setDefaultFromPhoneNumber (
    String defaultFromPhoneNumber) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "default-from-phone-number",
      defaultFromPhoneNumber,
      ExceptionType.CODE_EXCEPTION);
    
    TwilioSms.defaultFromPhoneNumber = defaultFromPhoneNumber;
  }
  
  /**
   * Constructor TwilioSms
   * use this constructor if you already used the setDefaultFromPhoneNumber
   *   method to set a default-from-phone-number
   * @param toPhoneNumber
   * @param message
   * @return new TwilioSms Object
   * @throws Exception
   */
  public TwilioSms (
    String toPhoneNumber,
    String message) throws Exception {
    
    this(toPhoneNumber, TwilioSms.defaultFromPhoneNumber, message);
  }
  
  /**
   * Constructor TwilioSms
   * @param toPhoneNumber
   * @param fromPhoneNumber
   * @param message
   * @return new TwilioSms Object
   * @throws Exception
   */
  public TwilioSms (
    String toPhoneNumber,
    String fromPhoneNumber,
    String message) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "to-phone-number",
      toPhoneNumber,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "from-phone-number",
      fromPhoneNumber,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "message",
      message,
      ExceptionType.CODE_EXCEPTION);
    
    this.toPhoneNumber = toPhoneNumber;
    this.fromPhoneNumber = fromPhoneNumber;
    this.message = message;
  }
  
  /**
   * fromTwilioSmsDispatchable
   * usually used on the worker instance side where dispatch messages are
   *   received then gets executed
   * @param twilioSmsDispatchable is a JSON Object with a serialized
   *          version of a TwilioSms Object
   * @return the deserialized version of the TwilioSms Object
   * @throws Exception
   */
  public static TwilioSms fromTwilioSmsDispatchable (
    TwilioSmsDispatchable twilioSmsDispatchable) throws Exception {
    
    return
      SerializationInl.<TwilioSms>deserializeObject(
        twilioSmsDispatchable.serialized_message);
  }
  
  /**
   * getToPhoneNumber
   * @return this twilio's sms to-phone-number
   * @throws Exception
   */
  public String getToPhoneNumber () throws Exception {
    
    return this.toPhoneNumber;
  }
  
  /**
   * getFromPhoneNumber
   * @return this twilio's sms from-phone-number
   * @throws Exception
   */
  public String getFromPhoneNumber () throws Exception {
    
    return this.fromPhoneNumber;
  }
  
  /**
   * getMessage
   * @return this twilio's sms message
   * @throws Exception
   */
  public String getMessage () throws Exception {
    
    return this.message;
  }
  
  @Override
  public String toString () {
    
    return
      "Twilio Sms:"
      + "\nfrom-phone-number ("
      + this.fromPhoneNumber
      + ")\nto-phone-number ("
      + this.toPhoneNumber
      + ")\nmessage ("
      + this.message
      + ")";
  }
}
