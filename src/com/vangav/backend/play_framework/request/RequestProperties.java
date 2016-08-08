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

package com.vangav.backend.play_framework.request;

import java.util.HashMap;
import java.util.Map;

import com.vangav.backend.properties.PropertiesFile;
import com.vangav.backend.properties.PropertiesLoader;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * RequestProperties is the properties file with boolean switches that controls
 *   the processing sequence of a request
 * */
public class RequestProperties extends PropertiesFile {

  private static RequestProperties instance = null;
  
  //disable default instantiation
  private RequestProperties () {}
  
  /**
   * i
   * singleton instance method
   * @return a static instance of RequestProperties
   * @throws Exception
   */
  public static RequestProperties i () throws Exception {
    
    if (instance == null) {
      
      instance = new RequestProperties();
    }
    
    return instance;
  }
  
  private static final String kName = "request_properties";
  
  @Override
  public String getName () {
    
    return kName;
  }
  
  // properties names
  public static final String kCheckSource = "check_source";
  public static final String kThrottle = "throttle";
  public static final String kValidateParam = "validate_param";
  public static final String kAuthenticate = "authenticate";
  public static final String kAfterResponse = "after_response";
  public static final String kAfterProcessing = "after_processing";
  public static final String kDefaultOperations = "default_operations";
  public static final String kPushNotifications = "notifications";
  public static final String kAnalysis = "analysis";
  public static final String kLogging = "logging";
  
  // property name -> property default value
  private static final Map<String, String> kProperties;
  static {
    
    kProperties = new HashMap<String, String>();
    
    kProperties.put(
      kCheckSource,
      "false");
    kProperties.put(
      kThrottle,
      "false");
    kProperties.put(
      kValidateParam,
      "true");
    kProperties.put(
      kAuthenticate,
      "false");
    kProperties.put(
      kAfterResponse,
      "false");
    kProperties.put(
      kAfterProcessing,
      "false");
    kProperties.put(
      kDefaultOperations,
      "false");
    kProperties.put(
      kPushNotifications,
      "false");
    kProperties.put(
      kAnalysis,
      "false");
    kProperties.put(
      kLogging,
      "false");
  }
  
  @Override
  protected String getProperty (String name) throws Exception {
    
    return PropertiesLoader.i().getProperty(
      this.getName(),
      name,
      kProperties.get(name) );
  }
}
