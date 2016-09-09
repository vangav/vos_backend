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

package com.vangav.backend.play_framework.request.response;

import java.util.HashMap;
import java.util.Map;

import com.vangav.backend.properties.PropertiesFile;
import com.vangav.backend.properties.PropertiesLoader;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ResponseBodyErrorProperties is the properties file with boolean switches
 *   that controls which elements gets sent as a part of a ResponseBodyError
 * */
public class ResponseBodyErrorProperties extends PropertiesFile {

  private static ResponseBodyErrorProperties instance = null;
  
  //disable default instantiation
  private ResponseBodyErrorProperties () {}
  
  /**
   * i
   * singleton instance method
   * @return a static instance of ResponseBodyErrorProperties
   * @throws Exception
   */
  public static ResponseBodyErrorProperties i () throws Exception {
    
    if (instance == null) {
      
      instance = new ResponseBodyErrorProperties();
    }
    
    return instance;
  }
  
  private static final String kName = "response_error_properties";
  
  @Override
  public String getName () {
    
    return kName;
  }
  
  // properties names
  public static final String kErrorType = "error_type";
  public static final String kErrorCode = "error_code";
  public static final String kErrorSubCode = "error_sub_code";
  public static final String kErrorMessage = "error_message";
  public static final String kErrorClass = "error_class";
  public static final String kErrorStackTrace = "error_stack_trace";
  public static final String kErrorTraceId = "error_trace_id";
  
  // property name -> property default value
  private static final Map<String, String> kProperties;
  static {
    
    kProperties = new HashMap<String, String>();
    
    kProperties.put(
      kErrorType,
      "true");
    kProperties.put(
      kErrorCode,
      "true");
    kProperties.put(
      kErrorSubCode,
      "true");
    kProperties.put(
      kErrorMessage,
      "true");
    kProperties.put(
      kErrorClass,
      "true");
    kProperties.put(
      kErrorStackTrace,
      "true");
    kProperties.put(
      kErrorTraceId,
      "true");
  }
  
  @Override
  protected String getProperty (String name) throws Exception {
    
    return PropertiesLoader.i().getProperty(
      this.getName(),
      name,
      kProperties.get(name) );
  }
}
