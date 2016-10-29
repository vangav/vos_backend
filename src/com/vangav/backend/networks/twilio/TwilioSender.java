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

import com.google.common.util.concurrent.ListenableFuture;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * TwilioSender handles sending Twilio's SMSs and MMSs
 * */
public class TwilioSender {

  private TwilioSender () throws Exception {
    
    Twilio.init(
      TwilioProperties.i().getStringPropterty(TwilioProperties.kUsername),
      TwilioProperties.i().getStringPropterty(TwilioProperties.kPassword),
      TwilioProperties.i().getStringPropterty(TwilioProperties.kAccountSid) );
  }
  
  private static TwilioSender instance = null;

  /**
   * i
   * @return singleton instance of TwilioSender
   * @throws Exception
   */
  public static TwilioSender i () throws Exception {
    
    if (instance == null) {
      
      instance = new TwilioSender();
    }
    
    return instance;
  }
  
  /**
   * sendSync
   * blocking method
   * @param twilioSms
   * @return sent sms's sid
   * @throws Exception
   */
  public String sendSync (TwilioSms twilioSms) throws Exception {
    
    Message message =
      Message
        .creator(
          new PhoneNumber(twilioSms.getToPhoneNumber() ),
          new PhoneNumber(twilioSms.getFromPhoneNumber() ),
          twilioSms.getMessage() )
        .create();
    
    return message.getSid();
  }
  
  /**
   * sendAsync
   * non-blocking method
   * @param twilioSms
   * @return ListenableFuture<Message> of the sms being sent asynchronously
   * @throws Exception
   */
  public ListenableFuture<Message> sendAsync (
    TwilioSms twilioSms) throws Exception {
    
    return 
      Message
        .creator(
          new PhoneNumber(twilioSms.getToPhoneNumber() ),
          new PhoneNumber(twilioSms.getFromPhoneNumber() ),
          twilioSms.getMessage() )
        .createAsync();
  }
  
  /**
   * sendSync
   * blocking method
   * @param twilioMms
   * @return sent mms's sid
   * @throws Exception
   */
  public String sendSync (TwilioMms twilioMms) throws Exception {
    
    Message message =
      Message
        .creator(
          new PhoneNumber(twilioMms.getToPhoneNumber() ),
          new PhoneNumber(twilioMms.getFromPhoneNumber() ),
          twilioMms.getMessage() )
        .setMediaUrl(twilioMms.getMediaUrl() )
        .create();
    
    return message.getSid();
  }
  
  /**
   * sendAsync
   * non-blocking method
   * @param twilioMms
   * @return ListenableFuture<Message> of the mms being sent asynchronously
   * @throws Exception
   */
  public ListenableFuture<Message> sendAsync (
    TwilioMms twilioMms) throws Exception {

    return
      Message
        .creator(
          new PhoneNumber(twilioMms.getToPhoneNumber() ),
          new PhoneNumber(twilioMms.getFromPhoneNumber() ),
          twilioMms.getMessage() )
        .setMediaUrl(twilioMms.getMediaUrl() )
        .createAsync();
  }
}
