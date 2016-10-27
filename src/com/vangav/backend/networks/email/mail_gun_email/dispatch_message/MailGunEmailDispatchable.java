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

package com.vangav.backend.networks.email.mail_gun_email.dispatch_message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vangav.backend.content.formatting.SerializationInl;
import com.vangav.backend.dispatcher.DispatchMessage;
import com.vangav.backend.networks.email.mail_gun_email.MailGunEmail;
import com.vangav.backend.networks.email.mail_gun_email.MailGunEmailSenderInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * MailGunEmailDispatchable represents a dispatchable version of MailGunEmail
 *   in the form of a JSON Object
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailGunEmailDispatchable extends DispatchMessage {

  public MailGunEmailDispatchable () {
    
  }
  
  @Override
  @JsonIgnore
  protected String getName () throws Exception {
    
    return "MailGunEmailDispatchable";
  }
  
  @Override
  @JsonIgnore
  protected DispatchMessage getThis () throws Exception {
    
    return this;
  }
  
  @Override
  @JsonIgnore
  public void execute () throws Exception {
    
    MailGunEmail mailGunEmail =
      MailGunEmail.fromMailGunEmailDispatchable(this);
    
    MailGunEmailSenderInl.sendSimpleEmail(mailGunEmail);
  }
  
  /**
   * Constructor MailGunEmailDispatchable
   * initializes through serializing a MailGunEmail Object
   * @param mailGunEmail
   * @return new MailGunEmailDispatchable Object
   * @throws Exception
   */
  @JsonIgnore
  public MailGunEmailDispatchable (
    MailGunEmail mailGunEmail) throws Exception {
    
    this.type = "mail_gun_email";
    
    this.serialized_message =
      SerializationInl.serializeObject(mailGunEmail);
  }
}
