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

package com.vangav.backend.play_framework.controllers_generator.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * RequestParamJson is the json representation of a controller's request param
 * e.g.:
 * name: latitude
 * type: LATITUDE (double)
 * is_array: false
 * optionality: MANDATORY
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestParamJson {

  /**
   * name represents the param name
   */
  @JsonProperty
  public String name;
  /**
   * type represents the param type (e.g.: FB_ID, ALTITUDE, E_MAIL, etc ...)
   */
  @JsonProperty
  public String type;
  /**
   * is_array is true if this param is an array and false otherwise
   */
  @JsonProperty
  public boolean is_array;
  /**
   * optionality is MANDATORY or OPTIONAL, MANDATORY params stop the request
   *   and return BadRequest 400 code if the param isn't valid while OPTIONAL
   *   params don't stop the request if they are invalid
   */
  @JsonProperty
  public String optionality;
}
