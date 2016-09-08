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

package com.vangav.backend.push_notifications.android;

import java.io.Serializable;

import com.google.android.gcm.server.Message;
import com.vangav.backend.content.formatting.SerializationInl;
import com.vangav.backend.push_notifications.android.dispatch_message.AndroidNotificationDispatchable;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * AndroidNotification class represents an Android Push Notification to be sent
 *   to Android devices through Google Cloud Messaging servers
 * Serializable to be embedded into a JSON object and dispatched to a worker
 *   instance if needed
 * Built on top of Google's gcm library -->
 *   https://github.com/google/gcm/tree/master/client-libraries/java/
 *                      rest-client/src/com/google/android/gcm/server
 * */
public class AndroidNotification implements Serializable {

  /**
   * Generated serial version ID
   */
  private static final long serialVersionUID = -8137373708468532389L;

  private Message message;
  private String to;
  private int retries;
  
  private static final int kDefaultRetries = 5;
  
  /**
   * Constructor AndroidNotification
   * @param message: message to be sent, including the device's registration id
   * @param to: registration token, notification key, or topic where the
   *              message will be sent
   * @return new AndroidNotification Object
   */
  public AndroidNotification (Message message, String to) throws Exception {
    
    this(message, to, kDefaultRetries);
  }
  
  /**
   * Constructor AndroidNotification
   * @param message: message to be sent, including the device's registration id
   * @param to: registration token, notification key, or topic where the
   *              message will be sent
   * @param retries: number of retries in case of service unavailability errors
   * @return new AndroidNotification Object;
   */
  public AndroidNotification (
    Message message,
    String to,
    int retries) throws Exception {
    
    this.message = message;
    this.to = to;
    this.retries = retries;
  }
  
  /**
   * fromAndroidNotificationDispatchable
   * usually used on the worker instance side where dispatch messages are
   *   received then gets executed
   * @param androidNotificationDispatchable us a JSON Object with a serialized
   *          version of an AndroidNotification Object
   * @return the deserialized version of the AndroidNotification Object
   * @throws Exception
   */
  public static AndroidNotification fromAndroidNotificationDispatchable (
    AndroidNotificationDispatchable
      androidNotificationDispatchable) throws Exception {
    
    return SerializationInl.<AndroidNotification>deserializeObject(
      androidNotificationDispatchable.serialized_message);
  }
  
  /**
   * getMessage
   * @return notifications's Message
   */
  public Message getMessage () throws Exception {
    
    return this.message;
  }
  
  /**
   * getTo
   * @return notification's to
   */
  public String getTo () throws Exception {
    
    return this.to;
  }
  
  /**
   * getRetries
   * @return notification's retries
   */
  public int getRetries () throws Exception {
    
    return this.retries;
  }
}
