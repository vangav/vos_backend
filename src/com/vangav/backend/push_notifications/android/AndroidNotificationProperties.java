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

import java.util.HashMap;
import java.util.Map;

import com.vangav.backend.properties.PropertiesFile;
import com.vangav.backend.properties.PropertiesLoader;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * OPTIONAL
 * AndroidNotificationProperties
 * In the common case of a backend for one application, this properties file
 *   is a handy way to skip instantiating an AndroidNotificationSender and use
 *   the singleton instance instead which uses values from this properties
 *   file
 * NOTE: values must be defined in the .prop file, default values are invalid
 *         values in that case of AndroidNotificationProperties
 * */
public class AndroidNotificationProperties extends PropertiesFile {

  private static AndroidNotificationProperties instance = null;
  
  //disable default instantiation
  private AndroidNotificationProperties () {}
  
  /**
   * static singleton getter
   * @return static singleton instance
   * @throws Exception
   */
  public static AndroidNotificationProperties i () throws Exception {
    
    if (instance == null) {
      
      instance = new AndroidNotificationProperties();
    }
    
    return instance;
  }
  
  private static final String kName = "android_notification_properties";
  
  @Override
  public String getName () {
    
    return kName;
  }
  
  // properties names
  public static final String kApiKey = "api_key";
  
  private static final String kInvalidValue = "InvalidValue";
  
  /**
   * isDefined
   * @return true if values in android_notification_properties.prop are defined
   *           and false otherwise
   */
  public boolean isDefined () {
    
    for (String key : kProperties.keySet() ) {
      
      if (kProperties.get(key).compareTo(kInvalidValue) == 0) {
        
        return false;
      }
    }
    
    return true;
  }
  
  // property name -> property default value
  private static final Map<String, String> kProperties;
  static {
    
    kProperties = new HashMap<String, String>();
    
    kProperties.put(
      kApiKey,
      kInvalidValue);
  }
  
  @Override
  protected String getProperty (String name) throws Exception {
    
    return PropertiesLoader.i().getProperty(
      this.getName(),
      name,
      kProperties.get(name) );
  }
}
