/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * MIT License
 *
 * Copyright (c) 2016 Vangav
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
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

package com.vangav.backend.backend_client_java.clients_generator.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ClientJson represents all controllers and error responses of a client's
 *   backend
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientJson {

  /**
   * controllers represents all backend controllers
   */
  @JsonProperty
  public ControllerJson[] controllers;
  /**
   * error_responses is an optional element that represents a client's error
   *   responses used to construct RestResponseJsonGroup for services other
   *   than Vangav Backend services
   */
  @JsonProperty
  public ErrorResponseJson[] error_responses;
  
  /**
   * fromJsonString
   * @param json
   * @return ControllersJson Object reflecting param json String
   * @throws Exception
   */
  @JsonIgnore
  public static ClientJson fromJsonString (String json) throws Exception {
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    return objectMapper.readValue(json, ClientJson.class);
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
