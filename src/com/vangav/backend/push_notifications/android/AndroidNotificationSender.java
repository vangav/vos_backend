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

package com.vangav.backend.push_notifications.android;

import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * AndroidNotificationSender handles sending AndroidNotification Objects to
 *   Android devices through Google Cloud Messaging Server
 * Handles one app per instance
 * Built on top of Google's gcm library -->
 *   https://github.com/google/gcm/tree/master/client-libraries/java/
 *                      rest-client/src/com/google/android/gcm/server
 * */
public class AndroidNotificationSender {

  private String apiKey;
  private final Sender sender;
  
  /**
   * NOTE: in case values in android_notification_properties.prop are set and
   *         should be used, then use the singleton method i () instead
   * Constructor AndroidNotificationSender
   * @param apiKey: API key obtained through the Google API Console
   * @return new AndroidNotificationSender Object
   * @throws Exception
   */
  public AndroidNotificationSender (String apiKey) throws Exception {

    this.apiKey = apiKey;
    this.sender = new Sender(this.apiKey);
  }
  
  private static AndroidNotificationSender instance;
  /**
   * i
   * @return static singleton instance of AndroidNotificationSender defined
   *           using the values in android_notification_properties.prop
   * @throws Exception
   */
  public static AndroidNotificationSender i () throws Exception {
    
    if (instance == null) {
      
      if (AndroidNotificationProperties.i().isDefined() == false) {
        
        throw new CodeException(
          154,
          1,
          "values in ["
          + AndroidNotificationProperties.i().getName()
          + ".prop] aren't defined, either define them or use the normal "
          + "constructor instead",
          ExceptionClass.PROPERTIES);
      }
      
      instance =
        new AndroidNotificationSender(
          AndroidNotificationProperties.i().getStringPropterty(
            AndroidNotificationProperties.kApiKey) );
    }
    
    return instance;
  }
  
  /**
   * sendNotification
   * sends an android notification to and android device through google
   *   cloud messaging server
   * @param androidNotification
   * @return Result or NULL in case of failure
   * @throws Exception
   * consumes Exceptions since notifications's failure are generally a
   *   low-business impact that shouldn't stop processing the rest of
   *   the request
   */
  public Result sendNotification (
    AndroidNotification androidNotification) throws Exception {
    
    try {
      
      return this.sender.send(
        androidNotification.getMessage(),
        androidNotification.getTo(),
        androidNotification.getRetries() );
    } catch (Exception e) {
      
      return null;
    }
  }
  
  @Override
  public String toString () {
    
    return
      "AndroidNotificationSender API key ["
      + this.apiKey
      + "]";
  }
}
