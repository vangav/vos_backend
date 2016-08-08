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

package com.vangav.backend.play_framework.param;

import java.util.HashMap;
import java.util.Map;

import com.vangav.backend.properties.PropertiesFile;
import com.vangav.backend.properties.PropertiesLoader;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ParamValidatorProperties is the properties file for ParamValidatorInl
 *   used for validation values that vary from one backend service to another
 *   (e.g.: text length, photo size, etc ...)
 * */
public class ParamValidatorProperties extends PropertiesFile {

  private static ParamValidatorProperties instance = null;
  
  // disable default instantiation
  private ParamValidatorProperties () {}
  
  /**
   * i
   * singleton instance method
   * @return a static instance of ParamValidatorProperties
   * @throws Exception
   */
  public static ParamValidatorProperties i () throws Exception {
    
    if (instance == null) {
      
      instance = new ParamValidatorProperties();
    }
    
    return instance;
  }
  
  private static final String kName = "param_validator_properties";
  
  @Override
  public String getName () {
    
    return kName;
  }
  
  // properties names
  public static final String kPhotoSize = "photo_size";
  public static final String kCaptionLength = "caption_length";
  public static final String kChatMessageLength = "chat_message_length";
  
  // property name -> property default value
  private static final Map<String, String> kProperties;
  static {
    
    kProperties = new HashMap<String, String>();
    
    kProperties.put(
      kPhotoSize,
      "5000000");
    kProperties.put(
      kCaptionLength,
      "100");
    kProperties.put(
      kChatMessageLength,
      "10000");
  }
  
  @Override
  protected String getProperty (String name) throws Exception {
    
    return PropertiesLoader.i().getProperty(
      this.getName(),
      name,
      kProperties.get(name) );
  }
}
