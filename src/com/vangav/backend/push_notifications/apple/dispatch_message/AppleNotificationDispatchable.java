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

package com.vangav.backend.push_notifications.apple.dispatch_message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vangav.backend.content.formatting.SerializationInl;
import com.vangav.backend.dispatcher.DispatchMessage;
import com.vangav.backend.push_notifications.apple.AppleNotification;
import com.vangav.backend.push_notifications.apple.AppleNotificationSender;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * AppleNotificationDispatchable represents a dispatchable version of
 *   AppleNotification in the form of a JSON Object
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppleNotificationDispatchable extends DispatchMessage {
  
  public AppleNotificationDispatchable () {
    
  }
  
  @Override
  @JsonIgnore
  protected String getName () throws Exception {
    
    return "AppleNotificationDispatchable";
  }
  
  @Override
  @JsonIgnore
  protected DispatchMessage getThis () throws Exception {
    
    return this;
  }
  
  @Override
  @JsonIgnore
  public void execute () throws Exception {
    
    AppleNotification appleNotification =
      AppleNotification.fromAppleNotificationDispatchable(this);
    
    AppleNotificationSender.i().sendNotification(appleNotification);
  }
  
  /**
   * Constructor AppleNotificationDispatchable
   * initializes through serializing an AppleNotification
   * @param appleNotification
   * @return new AppleNotificationDispatchable Object
   * @throws Exception
   */
  @JsonIgnore
  public AppleNotificationDispatchable (
    AppleNotification appleNotification) throws Exception {
    
    this.type = "apple_notification";
    
    this.serialized_message =
      SerializationInl.serializeObject(appleNotification);
  }
}
