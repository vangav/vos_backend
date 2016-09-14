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

package com.vangav.backend.public_apis.car2go;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vangav.backend.networks.rest_client.RestResponseJson;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ErrorResponse represents Car2Go's graph API responses other than Success
 *   200
 * 
 * v2.1
 * 
 * Reference:
 * https://github.com/car2go/openAPI
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse extends RestResponseJson {

  @Override
  @JsonIgnore
  protected String getName () throws Exception {
    
    return "car2go_api_error_response";
  }
  
  @Override
  @JsonIgnore
  protected ErrorResponse getThis () throws Exception {
    
    return this;
  }
  
  /**
   * Constructor ErrorResponse
   * @param rawResponse
   * @return new ErrorResponse Object
   * @throws Exception
   */
  public ErrorResponse (String rawResponse) throws Exception {
    
    this.raw_response = rawResponse;
  } 

  @JsonProperty
  public String raw_response;
}
