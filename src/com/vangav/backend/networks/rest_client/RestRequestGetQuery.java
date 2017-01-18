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

package com.vangav.backend.networks.rest_client;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * GetQuery represents GET REST call's query
 *   (e.g.: id=123&name=john)
 * */
public class RestRequestGetQuery extends RestRequest {
  
  private static final String kDefaultArraySeparator = ",";
  private String arraySeparator;

  private Map<String, String> params;
  
  /**
   * Constructor GetQuery
   * @return new GetQuery Object
   */
  public RestRequestGetQuery () {
    
    this.arraySeparator = kDefaultArraySeparator;
    this.params = new HashMap<String, String>();
  }
  
  /**
   * Constructor GetQuery
   * constructs params map and adds key/value to it
   * useful as a shortcut for one-key-value GET queries
   * @param key
   * @param value
   * @throws Exception
   */
  public RestRequestGetQuery (String key, String value) throws Exception {
    
    this.arraySeparator = kDefaultArraySeparator;
    this.params = new HashMap<String, String>();
    
    this.addParam(key, value);
  }
  
  /**
   * setArraySeparator
   * sets an alternate array separator to ,
   * array separator is used to format an array into a string in order to fit
   *   into a GET query.
   *   e.g.:
   *   request bool array: true false true
   *   formatted: true,false,true
   * @param arraySeparator
   * @throws Exception
   */
  public void setArraySeparator (String arraySeparator) throws Exception {
    
    this.arraySeparator = arraySeparator;
  }
  
  /**
   * addParam
   * adds a param (key/value pair) to the GET query
   * @param key
   * @param value
   * @throws Exception
   */
  public void addParam (String key, String value) throws Exception {
    
    this.params.put(key, value);
  }
  
  /**
   * addParam
   * adds a param (key/value pair) to the GET query
   * @param key
   * @param value
   * @throws Exception
   */
  public void addParam (String key, boolean value) throws Exception {
    
    this.params.put(key, "" + value);
  }
  
  /**
   * addParam
   * adds a param (key/value pair) to the GET query
   * @param key
   * @param value
   * @throws Exception
   */
  public void addParam (String key, short value) throws Exception {
    
    this.params.put(key, "" + value);
  }
  
  /**
   * addParam
   * adds a param (key/value pair) to the GET query
   * @param key
   * @param value
   * @throws Exception
   */
  public void addParam (String key, int value) throws Exception {
    
    this.params.put(key, "" + value);
  }
  
  /**
   * addParam
   * adds a param (key/value pair) to the GET query
   * @param key
   * @param value
   * @throws Exception
   */
  public void addParam (String key, long value) throws Exception {
    
    this.params.put(key, "" + value);
  }
  
  /**
   * addParam
   * adds a param (key/value pair) to the GET query
   * @param key
   * @param value
   * @throws Exception
   */
  public void addParam (String key, float value) throws Exception {
    
    this.params.put(key, "" + value);
  }
  
  /**
   * addParam
   * adds a param (key/value pair) to the GET query
   * @param key
   * @param value
   * @throws Exception
   */
  public void addParam (String key, double value) throws Exception {
    
    this.params.put(key, "" + value);
  }

  /**
   * addParam
   * formats values into a value string then
   *   adds a param (key/value pair) to the GET query
   * @param key
   * @param values
   * @throws Exception
   */
  public void addParam (String key, String[] values) throws Exception {
    
    String value = "";
    
    if (values != null) {
      
      for (int i = 0; i < values.length; i ++) {
        
        value += values[i];
        
        if (i < (values.length - 1) ) {
          
          value += this.arraySeparator;
        }
      }
    }
    
    this.params.put(key, value);
  }
  
  /**
   * addParam
   * formats values into a value string then
   *   adds a param (key/value pair) to the GET query
   * @param key
   * @param values
   * @throws Exception
   */
  public void addParam (String key, boolean[] values) throws Exception {
    
    String value = "";
    
    if (values != null) {
      
      for (int i = 0; i < values.length; i ++) {
        
        value += values[i];
        
        if (i < (values.length - 1) ) {
          
          value += this.arraySeparator;
        }
      }
    }
    
    this.params.put(key, value);
  }
  
  /**
   * addParam
   * formats values into a value string then
   *   adds a param (key/value pair) to the GET query
   * @param key
   * @param values
   * @throws Exception
   */
  public void addParam (String key, short[] values) throws Exception {
    
    String value = "";
    
    if (values != null) {
      
      for (int i = 0; i < values.length; i ++) {
        
        value += values[i];
        
        if (i < (values.length - 1) ) {
          
          value += this.arraySeparator;
        }
      }
    }
    
    this.params.put(key, value);
  }
  
  /**
   * addParam
   * formats values into a value string then
   *   adds a param (key/value pair) to the GET query
   * @param key
   * @param values
   * @throws Exception
   */
  public void addParam (String key, int[] values) throws Exception {
    
    String value = "";
    
    if (values != null) {
      
      for (int i = 0; i < values.length; i ++) {
        
        value += values[i];
        
        if (i < (values.length - 1) ) {
          
          value += this.arraySeparator;
        }
      }
    }
    
    this.params.put(key, value);
  }
  
  /**
   * addParam
   * formats values into a value string then
   *   adds a param (key/value pair) to the GET query
   * @param key
   * @param values
   * @throws Exception
   */
  public void addParam (String key, long[] values) throws Exception {
    
    String value = "";
    
    if (values != null) {
      
      for (int i = 0; i < values.length; i ++) {
        
        value += values[i];
        
        if (i < (values.length - 1) ) {
          
          value += this.arraySeparator;
        }
      }
    }
    
    this.params.put(key, value);
  }
  
  /**
   * addParam
   * formats values into a value string then
   *   adds a param (key/value pair) to the GET query
   * @param key
   * @param values
   * @throws Exception
   */
  public void addParam (String key, float[] values) throws Exception {
    
    String value = "";
    
    if (values != null) {
      
      for (int i = 0; i < values.length; i ++) {
        
        value += values[i];
        
        if (i < (values.length - 1) ) {
          
          value += this.arraySeparator;
        }
      }
    }
    
    this.params.put(key, value);
  }
  
  /**
   * addParam
   * formats values into a value string then
   *   adds a param (key/value pair) to the GET query
   * @param key
   * @param values
   * @throws Exception
   */
  public void addParam (String key, double[] values) throws Exception {
    
    String value = "";
    
    if (values != null) {
      
      for (int i = 0; i < values.length; i ++) {
        
        value += values[i];
        
        if (i < (values.length - 1) ) {
          
          value += this.arraySeparator;
        }
      }
    }
    
    this.params.put(key, value);
  }
  
  /**
   * addParams
   * adds multiple params (key/value pairs) to the GET query
   * @param mapParams
   * @throws Exception
   */
  public void addParams (Map<String, String> mapParams) throws Exception {
    
    this.params.putAll(mapParams);
  }
  
  /**
   * getQuery
   * @return string representation of the GET query, to be appended to the URL
   * @throws Exception
   */
  public String getQuery () throws Exception {

    String query = "";

    if (this.params.isEmpty() == true) {

      return query;
    }

    int index = 0;
    String currKey;
    String currValue;

    for (Map.Entry<String, String> entry : this.params.entrySet() ) {

      currKey = entry.getKey();

      if (currKey == null) {
        
        continue;
      }

      if (index ++ > 0) {
        
        query += "&";
      }

      query += currKey + "=";

      currValue = entry.getValue();

      if (currValue != null) {
        
        query += URLEncoder.encode(currValue, "UTF-8");
      }
    }

    return query;
  }
  
  @Override
  public String toString () {
    
    try {
    
      return
        "GetQuery: "
        + this.getQuery();
    } catch (Exception e) {
      
      return
        "GetQuery: threw an Exception";
    }
  }
}
