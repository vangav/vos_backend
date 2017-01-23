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

package com.vangav.backend.backend_client_java;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vangav.backend.networks.rest_client.RestResponseJson;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ErrorResponse represents an error response's format for both of
 *   400 BAD_REQUEST and 500 INTERNAL_ERROR responses
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse extends RestResponseJson {

  @Override
  @JsonIgnore
  protected String getName () throws Exception {
    
    return "vos_backend_error_response";
  }
  
  @Override
  @JsonIgnore
  protected ErrorResponse getThis () throws Exception {
    
    return this;
  }
  
  @JsonIgnore
  public static ErrorResponse getDefaultErrorResponse () {
    
    ErrorResponse defaultErrorResponse = new ErrorResponse();
    
    defaultErrorResponse.error_type = "";
    defaultErrorResponse.error_code = -2;
    defaultErrorResponse.error_sub_code = -2;
    defaultErrorResponse.error_message = "";
    defaultErrorResponse.error_class = "";
    defaultErrorResponse.error_stack_trace = "";
    defaultErrorResponse.error_trace_id =
      "00000000-0000-1000-0000-000000000000";
    
    return defaultErrorResponse;
  }

  @JsonProperty
  public String error_type;
  @JsonProperty
  public int error_code;
  @JsonProperty
  public int error_sub_code;
  @JsonProperty
  public String error_message;
  @JsonProperty
  public String error_class;
  @JsonProperty
  public String error_stack_trace;
  @JsonProperty
  public String error_trace_id;
}
