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
import com.vangav.backend.networks.twilio.TwilioMms;
import com.vangav.backend.networks.twilio.TwilioSender;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * TwilioMmsDispatchable represents a dispatchable version of TwilioMms in the
 *   form of a JSON Object
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwilioMmsDispatchable extends DispatchMessage {

  public TwilioMmsDispatchable () {
    
  }
  
  @Override
  @JsonIgnore
  protected String getName () throws Exception {
    
    return "TwilioMmsDispatchable";
  }
  
  @Override
  @JsonIgnore
  protected DispatchMessage getThis () throws Exception {
    
    return this;
  }
  
  @Override
  @JsonIgnore
  public void execute () throws Exception {
    
    TwilioMms twilioMms = TwilioMms.fromTwilioMmsDispatchable(this);
    
    TwilioSender.i().sendAsync(twilioMms);
  }
  
  /**
   * Constructor TwilioMmsDispatchable
   * initializes through serializing a TwilioMms Object
   * @param twilioMms
   * @return new TwilioMmsDispatchable Object
   * @throws Exception
   */
  @JsonIgnore
  public TwilioMmsDispatchable (TwilioMms twilioMms) throws Exception {
    
    this.type = "twilio_mms";
    
    this.serialized_message =
      SerializationInl.serializeObject(twilioMms);
  }
}
