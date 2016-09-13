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

package com.vangav.backend.security.authentication.facebook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vangav.backend.networks.rest_client.RestResponseJson;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ResponseFacebookAuth: JSON class representing Facebook's authentication
 *                         response
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseFacebookAuth extends RestResponseJson {

  @Override
  @JsonIgnore
  protected String getName () throws Exception {
    
    return "FacebookAuth";
  }
  
  @Override
  @JsonIgnore
  protected ResponseFacebookAuth getThis () throws Exception {
    
    return this;
  }
  
  /**
   * id: the Facebook app's ID (i.e.: your app's Facebook ID, provided by
   *       Facebook for each registered app; mobile app, web app, etc ...)
   */
  @JsonProperty
  public String id;
}
