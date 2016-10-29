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

package com.vangav.backend.play_framework.request;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vangav.backend.exceptions.BadRequestException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.play_framework.param.ParamOptionality;
import com.vangav.backend.play_framework.param.ParamType;
import com.vangav.backend.play_framework.param.ParamValidatorInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * RequestJsonBody is the parent class for a controller's json body
 *   with POST and GET children which act as sub-parents for defining
 *   the json structure of the request
 * */
public abstract class RequestJsonBody {
  
  /**
   * getName
   * @return the name of the child class inheriting from this class
   * @throws Exception
   */
  @JsonIgnore
  protected abstract String getName () throws Exception;
  
  /**
   * getThis
   * @return the child instance inheriting from this class
   * @throws Exception
   */
  @JsonIgnore
  protected abstract RequestJsonBody getThis () throws Exception;

  /**
   * kDefaultUuid is used for services that aren't user-oriented
   */
  @JsonIgnore
  public static final String kDefaultUuid =
    "00000000-0000-1000-0000-000000000000";
  
  /**
   * getUserId
   * override for controllers that has a user-id in their json request
   * @return request's user id
   */
  @JsonIgnore
  public UUID getUserId () {
    
    return UUID.fromString(kDefaultUuid);
  }
  
  /**
   * fromPlayRequest
   * converts a play request's body to a new RequestJsonBody Object
   * @param playRequest
   * @return new RequestJsonBody Object
   * @throws Exception
   */
  @JsonIgnore
  final public RequestJsonBody fromPlayRequest (
    play.mvc.Http.Request playRequest) throws Exception {
    
    if (RequestType.valueOf(playRequest.method().toUpperCase() ) ==
          RequestType.POST) {
      
      return this.fromJsonString(playRequest.body().asJson().toString() );
    } else if (RequestType.valueOf(playRequest.method().toUpperCase() ) ==
        RequestType.GET) {
      
      return this.fromQueryString(playRequest.queryString() );
    }
    
    throw new BadRequestException(
      "unhandled request type, only handles POST and GET request types",
      ExceptionClass.TYPE);
  }
  
  /**
   * fromJsonString
   * @param json representation of a POST request
   * @return json object representation of the request
   * @throws Exception
   */
  @JsonIgnore
  protected abstract RequestJsonBody fromJsonString (
    String json) throws Exception;
  
  /**
   * fromQueryString
   * @param map representation of a GET request
   * @return json object representation of the request
   * @throws Exception
   */
  @JsonIgnore
  protected abstract RequestJsonBody fromQueryString (
    Map<String, String[]> query) throws Exception;

  /**
   * validate
   * Override to implement the validation of the json request depending
   *   on the type and optionality
   * @throws Exception
   */
  @JsonIgnore
  public abstract void validate () throws Exception;
  
  /**
   * invalidParams stores names of invalid optional params
   */
  @JsonIgnore
  private Set<String> invalidParams = new HashSet<String>();
  
  /**
   * validate
   * param validator wrapper to keep track of invalid optional params for
   *   a request
   * @param name
   * @param value
   * @param type
   * @param optionality
   * @throws Exception
   */
  @JsonIgnore
  protected void validate (
    String name,
    Object value,
    ParamType type,
    ParamOptionality optionality) throws Exception {
    
    if (ParamValidatorInl.validate(name, value, type, optionality) == false) {
      
      this.invalidParams.add(name);
    }
  }
  
  /**
   * validate
   * param validator wrapper to keep track of invalid optional params for
   *   a request
   * @param name
   * @param values
   * @param type
   * @param optionality
   * @throws Exception
   */
  @JsonIgnore
  protected void validate (
    String name,
    Object[] values,
    ParamType type,
    ParamOptionality optionality) throws Exception {
    
    if (ParamValidatorInl.validate(name, values, type, optionality) == false) {
      
      this.invalidParams.add(name);
    }
  }
  
  /**
   * isValidParam
   * @param name
   * @return true if param is valid and false otherwise
   * @throws Exception
   */
  @JsonIgnore
  public boolean isValidParam (String name) throws Exception {
    
    if (this.invalidParams.contains(name) == true) {
      
      return false;
    }
    
    return true;
  }
  
  /**
   * getAsString
   * @return string representation of the request in JSON format
   * @throws Exception
   */
  @JsonIgnore
  final protected String getAsString () throws Exception {
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    return
      objectMapper.writerWithDefaultPrettyPrinter(
        ).writeValueAsString(this.getThis() );
  }
  
  @Override
  @JsonIgnore
  public String toString () {
    
    try {
      
      return
        "Request Json Body ["
        + this.getName()
        + "]:\n"
        + this.getAsString();
    } catch (Exception e) {
      
      return
        "Request Json Body: threw an Exception!";
    }
  }
}
