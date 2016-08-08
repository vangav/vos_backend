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
 * ResponseParamJson is the json representation of a controller's response
 *   param
 * e.g.:
 * - String name
 * - int count
 * - etc ...
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseParamJson {

  /**
   * name represents the param name
   */
  @JsonProperty
  public String name;
  /**
   * type represents the param type (e.g.: String, int, double, etc ...)
   */
  @JsonProperty
  public String type;
  /**
   * is_array defines is this param is an array or not
   */
  @JsonProperty
  public boolean is_array;
}
