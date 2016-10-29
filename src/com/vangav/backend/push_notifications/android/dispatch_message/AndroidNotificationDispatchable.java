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
