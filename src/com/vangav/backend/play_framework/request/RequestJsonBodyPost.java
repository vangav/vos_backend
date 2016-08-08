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

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * RequestJsonBodyPost is the parent class for POST requests
 *   children classes define the request's structure
 * */
public abstract class RequestJsonBodyPost extends RequestJsonBody {

  // NOTE: only exists to defeat override by POST requests since this method
  //         is only used by GET requests
  @Override
  @JsonIgnore
  final protected RequestJsonBody fromQueryString (
    Map<String, String[]> query) throws Exception {
    
    throw new CodeException(
      "this is a GET request method called for what should be a POST request",
      ExceptionClass.UNAUTHORIZED);
  }
  
  @Override
  @JsonIgnore
  final protected RequestJsonBody fromJsonString (
    String json) throws Exception {
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    return objectMapper.readValue(json, this.getThis().getClass() );
  }
}
