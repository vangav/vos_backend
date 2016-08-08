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

package com.vangav.backend.push_notifications.android.dispatch_message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vangav.backend.content.formatting.SerializationInl;
import com.vangav.backend.dispatcher.DispatchMessage;
import com.vangav.backend.push_notifications.android.AndroidNotification;
import com.vangav.backend.push_notifications.android.AndroidNotificationSender;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * AndroidNotificationDispatchable represents a dispatchable version of
 *   AndroidNotification in the form of a JSON Object
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AndroidNotificationDispatchable extends DispatchMessage {
  
  public AndroidNotificationDispatchable () {
    
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
    
    AndroidNotification androidNotification =
      AndroidNotification.fromAndroidNotificationDispatchable(this);
    
    AndroidNotificationSender.i().sendNotification(androidNotification);
  }
  
  /**
   * Constructor AndroidNotificationDispatchable
   * initialized through serializing an AndroidNotification
   * @param androidNotification
   * @return new AndroidNotificationDispatchable Object
   * @throws Exception
   */
  @JsonIgnore
  public AndroidNotificationDispatchable (
    AndroidNotification androidNotification) throws Exception {
    
    this.type = "android_notification";
    
    this.serialized_message =
      SerializationInl.serializeObject(androidNotification);
  }
}
