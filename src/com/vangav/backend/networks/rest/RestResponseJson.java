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

package com.vangav.backend.networks.rest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * RestResponseJson is the parent class for a REST response's JSON
 * */
public abstract class RestResponseJson {
  
  /**
   * getName
   * @return the name of the child class inheriting from this class
   * @throws Exception
   */
  @JsonIgnore
  protected abstract String getName () throws Exception;
  
  /**
   * getThis
   * @return the child instance inheriting from this class
   * @throws Exception
   */
  @JsonIgnore
  protected abstract RestResponseJson getThis () throws Exception;
  
  /**
   * fromJsonString
   * @param json representation of a REST's response
   * @return  json object representation of the response
   * @throws Exception
   */
  @JsonIgnore
  final protected RestResponseJson fromJsonString (
    String json) throws Exception {
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    return
      (RestResponseJson)
      (objectMapper.readValue(json, this.getThis().getClass() ) );
  }
  
  /**
   * getAsString
   * @return string representation of the REST response JSON
   * @throws Exception
   */
  @JsonIgnore
  final protected String getAsString () throws Exception {
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    return
      objectMapper.writerWithDefaultPrettyPrinter(
        ).writeValueAsString(this.getThis() );
  }
  
  @Override
  @JsonIgnore
  public String toString () {
    
    try {
    
      return
        "REST Request POST JSON ["
        + this.getName()
        + "]:\n"
        + this.getAsString();
    } catch (Exception e) {
      
      return
        "REST Request POST JSON: threw an Exception!";
    }
  }
}
