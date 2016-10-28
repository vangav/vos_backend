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

package com.vangav.backend.networks.rest_client;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
public abstract class RestRequest {

  @JsonIgnore
  private Map<String, String> headers = new HashMap<String, String>();
  
  /**
   * hasHeaders
   * @return true if this RestRequest Object has specific headers and false
   *           otherwise
   * @throws Exception
   */
  @JsonIgnore
  public boolean hasHeaders () throws Exception {
    
    if (this.headers.isEmpty() == true) {
      
      return false;
    }
    
    return true;
  }
  
  /**
   * addHeader
   * @param key
   * @param value
   * @throws Exception
   */
  @JsonIgnore
  public void addHeader (String key, String value) throws Exception {
    
    this.headers.put(key, value);
  }
  
  /**
   * addHeaders
   * @param headers
   * @throws Exception
   */
  @JsonIgnore
  public void addHeaders (Map<String, String> headers) throws Exception {
    
    this.headers.putAll(headers);
  }
  
  /**
   * getHeaders
   * @return this RestRequest Object's specific headers
   * @throws Exception
   */
  @JsonIgnore
  public Map<String, String> getHeaders () throws Exception {
    
    return this.headers;
  }
}
