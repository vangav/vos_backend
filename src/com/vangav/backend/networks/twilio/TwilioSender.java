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
