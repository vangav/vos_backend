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

package com.vangav.backend.security.authentication.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vangav.backend.networks.rest_client.RestResponseJson;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ResponseGoogleAuth: JSON class representing Google's authentication
 *                       response
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseGoogleAuth extends RestResponseJson {
  
  /**
   * aud: the Google's app's ID (i.e.: your app's Google ID, provided by
   *        Google for each registered app; mobile app, web app, etc ...)
   */
  @JsonProperty
  public String aud;
  
  /**
   * sub: your app user's Google ID
   */
  @JsonProperty
  public String sub;

  @Override
  protected String getName () throws Exception {
    
    return "GoogleAuth";
  }
  
  @Override
  protected RestResponseJson getThis () throws Exception {
    
    return this;
  }
}
