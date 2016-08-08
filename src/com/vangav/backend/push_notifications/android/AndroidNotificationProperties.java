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
  
  private AndroidNotificationProperties () {}
  
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
