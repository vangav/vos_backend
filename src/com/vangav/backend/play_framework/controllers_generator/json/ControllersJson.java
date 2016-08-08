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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ControllersJson represents all controllers of a backend
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ControllersJson {

  /**
   * java_package is usually the reverse of the project's domain name
   *   (e.g.: com.vangav is the reverse of vangav.com)
   * it serves as a unique identifier for the set of projects related to that
   *   domain name in order to avoid conflicts with other libraries
   */
  @JsonProperty
  public String java_package;
  /**
   * check_source is used to check the request source for device-specific
   *   operations (e.g.: if a mobile app backend wants to reject requests
   *   coming from computers to potentially try to cause some harm)
   * also used for specific-client applications (like mobile apps) to verify
   *   client (using request hashing with a secret phrase or else)
   */
  @JsonProperty
  public boolean check_source;
  /**
   * throttle  is a common need for mobile applications and websites to ban
   *   behavior like spam, DOS, etc ...
   */
  @JsonProperty
  public boolean throttle;
  /**
   * validate_param is used to validate a request (e.g.: validate e-mail
   *   format, photo size, etc ...)
   */
  @JsonProperty
  public boolean validate_param;
  /**
   * authenticate is used to authenticate a request (e.g.: OAuth 2,
   *   Facebook Login, etc ...)
   */
  @JsonProperty
  public boolean authenticate;
  /**
   * after_response is used to switch on/off all after response operations
   *   (each of those operation has a switch property whose value only matters
   *   if this property's value is true)
   */
  @JsonProperty
  public boolean after_response;
  /**
   * after_processing is used to enable doing further processing for a request
   *   after a response is sent. (e.g.: let's say someone shared a public post
   *   on Facebook, before a response is sent this post must be made to appear
   *   for that user's friends which is critical for a successful post; then
   *   in after processing the post gets assigned to more non-friend users
   *   which if failed doesn't affect the success of the request from the
   *   user-experience point of view)
   */
  @JsonProperty
  public boolean after_processing;
  /**
   * default_operations are used to execute operations that happen after each
   *   request regardless of the type of that request (e.g.: in a mobile app
   *   default operations can be used to update the user's notifications'
   *   badge number; for example to reset it on the backend side since
   *   a request means that the user opened the app)
   */
  @JsonProperty
  public boolean default_operations;
  /**
   * notifications is used to enable sending push notifications
   */
  @JsonProperty
  public boolean notifications;
  /**
   * analysis is used to enable doing analysis after requests
   */
  @JsonProperty
  public boolean analysis;
  /**
   * logging is used to enable logging requests, responses, exceptions, etc ...
   */
  @JsonProperty
  public boolean logging;
  /**
   * controllers reprsents all backend controllers
   */
  @JsonProperty
  public ControllerJson[] controllers;
  
  /**
   * fromJsonString
   * @param json
   * @return ControllersJson Object reflecting param json String
   * @throws Exception
   */
  @JsonIgnore
  public static ControllersJson fromJsonString (String json) throws Exception {
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    return objectMapper.readValue(json, ControllersJson.class);
  }
  
  /**
   * getAsString
   * @return String representation of this JSON Object
   * @throws Exception
   */
  @JsonIgnore
  public String getAsString () throws Exception {
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    return
      objectMapper.writerWithDefaultPrettyPrinter(
        ).writeValueAsString(this);
  }
  
  @Override
  @JsonIgnore
  public String toString () {
    
    try {
      
      return
        "ControllersJson:\n"
        + this.getAsString();
    } catch (Exception e) {
      
      return
        "ControllersJson: threw and Exception!";
    }
  }
}
