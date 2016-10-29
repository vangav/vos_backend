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

package com.vangav.backend.dispatcher;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vangav.backend.cassandra.keyspaces.dispatch_message.QueryDispatchable;
import com.vangav.backend.networks.email.java_email.dispatch_message.JavaEmailDispatchable;
import com.vangav.backend.networks.email.mail_gun_email.dispatch_message.MailGunEmailDispatchable;
import com.vangav.backend.networks.twilio.dispatch_message.TwilioMmsDispatchable;
import com.vangav.backend.networks.twilio.dispatch_message.TwilioSmsDispatchable;
import com.vangav.backend.push_notifications.android.dispatch_message.AndroidNotificationDispatchable;
import com.vangav.backend.push_notifications.apple.dispatch_message.AppleNotificationDispatchable;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * DispatchMessage format should be JSON
 * DispatchMessage is the parent class for all messages to be passed
 *   to worker services through the dispatcher
 * e.g.: push notifications, analysis/logging queries, etc ...
 * Usually operations that aren't time sensitive
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @Type(value = QueryDispatchable.class, name = "cassandra_query"),
  @Type(value = AndroidNotificationDispatchable.class, name = "android_notification"),
  @Type(value = AppleNotificationDispatchable.class, name = "apple_notification"),
  @Type(value = JavaEmailDispatchable.class, name = "java_email"),
  @Type(value = MailGunEmailDispatchable.class, name = "mail_gun_email"),
  @Type(value = TwilioSmsDispatchable.class, name = "twilio_sms"),
  @Type(value = TwilioMmsDispatchable.class, name = "twilio_mms") } )
public abstract class DispatchMessage {
  
  @JsonProperty
  public String type;

  @JsonProperty
  public String serialized_message;
  
  /**
   * getName
   * @return the name of the child class inheriting from this class
   * @throws Exception
   */
  @JsonIgnore
  protected abstract String getName () throws Exception;
  
  /**
   * getThis
   * @return the child instance inheriting from this class
   * @throws Exception
   */
  @JsonIgnore
  protected abstract DispatchMessage getThis () throws Exception;
  
  /**
   * execute
   * executes the dispatch message
   * @throws Exception
   */
  public abstract void execute () throws Exception;
  
  /**
   * getAsString
   * @return string representation of the request in JSON format
   * @throws Exception
   */
  @JsonIgnore
  final protected String getAsString () throws Exception {
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    return
      objectMapper.writerWithDefaultPrettyPrinter(
        ).writeValueAsString(this.getThis() );
  }
  
  @Override
  @JsonIgnore
  public String toString () {
    
    try {
      
      return
        "Dispatch Message ["
        + this.getName()
        + "]:\n"
        + this.getAsString();
    } catch (Exception e) {
      
      return
        "DispatchMessage: threw Exception!";
    }
  }
}
