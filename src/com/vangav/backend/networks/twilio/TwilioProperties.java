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

package com.vangav.backend.networks.twilio;

import java.util.HashMap;
import java.util.Map;

import com.vangav.backend.properties.PropertiesFile;
import com.vangav.backend.properties.PropertiesLoader;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * TwilioProperties is the properties file for twilio. In order to use Twilio
 *   you must have a valid twilio_properties.prop properties file
 * */
public class TwilioProperties extends PropertiesFile {

  private static TwilioProperties instance = null;
  
  // disable defaul instantiation
  private TwilioProperties () {}
  
  public static TwilioProperties i () throws Exception {
    
    if (instance == null) {
      
      instance = new TwilioProperties();
    }
    
    return instance;
  }
  
  private static final String kName = "twilio_properties.prop";
  
  @Override
  public String getName () {
    
    return kName;
  }
  
  // properties names
  public static final String kUsername = "username";
  public static final String kPassword = "password";
  public static final String kAccountSid = "account_sid";
  
  // property name -> property default value
  private static final Map<String, String> kProperties;
  static {
    
    kProperties = new HashMap<String, String>();
    
    kProperties.put(
      kUsername,
      "");
    kProperties.put(
      kPassword,
      "");
    kProperties.put(
      kAccountSid,
      "");
  }
  
  @Override
  protected String getProperty (String name) throws Exception {
    
    return PropertiesLoader.i().getProperty(
      this.getName(),
      name,
      kProperties.get(name) );
  }
}
