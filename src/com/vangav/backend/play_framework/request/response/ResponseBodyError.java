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

package com.vangav.backend.play_framework.request.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vangav.backend.exceptions.DefaultException;
import com.vangav.backend.exceptions.VangavException;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ResponseBodyError represents an error response's format for both of
 *   400 BAD_REQUEST and 500 INTERNAL_ERROR responses
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseBodyError extends ResponseBodyJson {

  @Override
  @JsonIgnore
  protected String getName() throws Exception {

    return "error_json_response";
  }

  @Override
  @JsonIgnore
  protected ResponseBodyError getThis() throws Exception {

    return this;
  }
  
  @JsonIgnore
  private static final String kDefaultErrorType = "";
  @JsonIgnore
  private static final int kDefaultErrorCode = -1;
  @JsonIgnore
  private static final int kDefaultErrorSubCode = -1;
  @JsonIgnore
  private static final String kDefaultErrorMessage = "";
  @JsonIgnore
  private static final String kDefaultErrorClass = "";
  @JsonIgnore
  private static final String kDefaultErrorStackTrace = "";
  @JsonIgnore
  private static final String kDefaultErrorTraceId =
    "00000000-0000-1000-0000-000000000000";
  
  /**
   * Constructor ResponseBodyError
   * @return new ResponseBodyError Object
   * @throws Exception
   */
  public ResponseBodyError () throws Exception {
    
    this(new DefaultException(), UUID.fromString(kDefaultErrorTraceId) );
  }
  
  /**
   * Constructor ResponseBodyError
   * @param requestId
   * @return new ResponseBodyError Object
   * @throws Exception
   */
  public ResponseBodyError (UUID requestId) throws Exception {
    
    this(new DefaultException(), requestId);
  }
  
  /**
   * Constructor ResponseBodyError
   * @param vangavException
   * @return new ResponseBodyError Object
   * @throws Exception
   */
  public ResponseBodyError (VangavException vangavException) throws Exception {
    
    this(vangavException, UUID.fromString(kDefaultErrorTraceId) );
  }

  /**
   * Constructor ResponseBodyError
   * @param vangavException
   * @param requestId
   * @return new ResponseBodyError Object
   * @throws Exception
   */
  @JsonIgnore
  public ResponseBodyError (
    VangavException vangavException,
    UUID requestId) throws Exception {
    
    // error_type
    if (ResponseBodyErrorProperties.i().getBooleanProperty(
          ResponseBodyErrorProperties.kErrorType) == true) {

      this.error_type = vangavException.getExceptionType().toString();
    } else {
      
      this.error_type = kDefaultErrorType;
    }
    
    // error_code
    if (ResponseBodyErrorProperties.i().getBooleanProperty(
          ResponseBodyErrorProperties.kErrorCode) == true) {

      this.error_code = vangavException.getCode();
    } else {
      
      this.error_code = kDefaultErrorCode;
    }
    
    // error_sub_code
    if (ResponseBodyErrorProperties.i().getBooleanProperty(
          ResponseBodyErrorProperties.kErrorSubCode) == true) {

      this.error_sub_code = vangavException.getSubCode();
    } else {
      
      this.error_sub_code = kDefaultErrorSubCode;
    }
    
    // error message
    if (ResponseBodyErrorProperties.i().getBooleanProperty(
          ResponseBodyErrorProperties.kErrorMessage) == true) {

      this.error_message = vangavException.getMessage();
    } else {
      
      this.error_message = kDefaultErrorMessage;
    }
    
    // error_class
    if (ResponseBodyErrorProperties.i().getBooleanProperty(
          ResponseBodyErrorProperties.kErrorClass) == true) {

      this.error_class = vangavException.getExceptionClass().toString();
    } else {
      
      this.error_class = kDefaultErrorClass;
    }
    
    // error_stack_trace
    if (ResponseBodyErrorProperties.i().getBooleanProperty(
          ResponseBodyErrorProperties.kErrorStackTrace) == true) {

      this.error_stack_trace = vangavException.getExceptionStackTrace();
    } else {
      
      this.error_stack_trace = kDefaultErrorStackTrace;
    }
    
    // error_trace_id
    if (ResponseBodyErrorProperties.i().getBooleanProperty(
          ResponseBodyErrorProperties.kErrorTraceId) == true) {

      this.error_trace_id = requestId.toString();
    } else {
      
      this.error_trace_id = kDefaultErrorTraceId;
    }
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
