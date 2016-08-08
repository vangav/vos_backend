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
 * ControllerJson is the json representation of a controller
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ControllerJson {

  /**
   * is_preset is true if this controller is a preset and false otherwise
   *   preset controllers provide known functionalities (e.g.: Facebook login,
   *   Google login, etc ...)
   */
  @JsonProperty
  public boolean is_preset;
  /**
   * name represents the controller's name (e.g.: Login, Comment, Tweet,
   *   etc ...)
   */
  @JsonProperty
  public String name;
  /**
   * type represents the controller's type (GET or POST)
   */
  @JsonProperty
  public String type;
  /**
   * request_params represents the controller's request params
   */
  @JsonProperty
  public RequestParamJson[] request_params;
  /**
   * response_type represents the controller's response type
   *   (JSON, FILE or HTML)
   */
  @JsonProperty
  public String response_type;
  /**
   * response_params represents the controller's response params for a JSON
   *   type response
   */
  @JsonProperty
  public ResponseParamJson[] response_params;
}
