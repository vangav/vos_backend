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

package com.vangav.backend.dispatcher;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * DispatchMessages is a json structure that holds multiple dispatch messages
 *   sharing the same worker service url
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DispatchMessages {
  
  @JsonProperty
  public DispatchMessage[] dispatch_messages;
  
  /**
   * Constructor DispatchMessages
   * default constructor
   */
  public DispatchMessages () {
    
    this.dispatch_messages = null;
  }
  
  /**
   * Constructor DispatchMessages
   * @param dispatchMessages
   * @return new DispatchMessages Object
   * @throws Exception
   */
  @JsonIgnore
  public DispatchMessages (
    ArrayList<DispatchMessage> dispatchMessages) throws Exception {
    
    if (dispatchMessages == null) {
      
      this.dispatch_messages = null;
      
      return;
    }
    
    this.dispatch_messages = dispatchMessages.toArray(new DispatchMessage[0] );
  }
  
  /**
   * fromJsonString
   * constructs a DispatchMessages json object from a json string
   *   representation
   * @param json: string representation of the json object
   * @return new DispatchMessages Object reflecting the passed string
   *           representation
   * @throws Exception
   */
  @JsonIgnore
  public DispatchMessages fromJsonString (String json) throws Exception {

    ObjectMapper objectMapper = new ObjectMapper();
    
    return objectMapper.readValue(
      json,
      DispatchMessages.class);
  }
  
  /**
   * toJsonString
   * converts this DispatchMessages object to a json String representation
   * @return json's string representation
   * @throws Exception
   */
  @JsonIgnore
  public String toJsonString () throws Exception {
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    return
      objectMapper.writerWithDefaultPrettyPrinter(
        ).writeValueAsString(this);
  }
  
  @Override
  public String toString () {
    
    try {
    
      return this.toJsonString();
    } catch (Exception e) {
      
      return
        "DispatchMessages: threw an Exception on toString operation";
    }
  }
}
