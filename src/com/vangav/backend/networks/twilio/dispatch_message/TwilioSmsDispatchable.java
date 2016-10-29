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

package com.vangav.backend.networks.twilio.dispatch_message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vangav.backend.content.formatting.SerializationInl;
import com.vangav.backend.dispatcher.DispatchMessage;
import com.vangav.backend.networks.twilio.TwilioSender;
import com.vangav.backend.networks.twilio.TwilioSms;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * TwilioSmsDispatchable represents a dispatchable version of TwilioSms in the
 *   form of a JSON Object
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwilioSmsDispatchable extends DispatchMessage {

  public TwilioSmsDispatchable () {
    
  }
  
  @Override
  @JsonIgnore
  protected String getName () throws Exception {
    
    return "TwilioSmsDispatchable";
  }
  
  @Override
  @JsonIgnore
  protected DispatchMessage getThis () throws Exception {
    
    return this;
  }
  
  @Override
  @JsonIgnore
  public void execute () throws Exception {
   
    TwilioSms twilioSms = TwilioSms.fromTwilioSmsDispatchable(this);
    
    TwilioSender.i().sendAsync(twilioSms);
  }
  
  /**
   * Constructor TwilioSmsDispatchable
   * initializes through serializing a TwilioSms Object
   * @param twilioSms
   * @return new TwilioSmsDispatchable Object
   * @throws Exception
   */
  @JsonIgnore
  public TwilioSmsDispatchable (TwilioSms twilioSms) throws Exception {
    
    this.type = "twilio_sms";
    
    this.serialized_message =
      SerializationInl.serializeObject(twilioSms);
  }
}
