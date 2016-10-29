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
      "true");
    kProperties.put(
      kThrottle,
      "true");
    kProperties.put(
      kValidateParam,
      "true");
    kProperties.put(
      kAuthenticate,
      "true");
    kProperties.put(
      kAfterResponse,
      "true");
    kProperties.put(
      kAfterProcessing,
      "true");
    kProperties.put(
      kDefaultOperations,
      "true");
    kProperties.put(
      kPushNotifications,
      "true");
    kProperties.put(
      kAnalysis,
      "true");
    kProperties.put(
      kLogging,
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
