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
