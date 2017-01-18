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

package com.vangav.backend.backend_client_java.json_response.server_json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vangav.backend.backend_client_java.ControllerCallLog;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ServerJsonControllerCallLog represents a single controller call's log
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerJsonControllerCallLog {

  @JsonProperty
  public String controller_name;
  @JsonProperty
  public String controller_url;
  @JsonProperty
  public int request_to_response_time_in_milli_seconds;
  @JsonProperty
  public int response_http_status_code;
  @JsonProperty
  public String request;
  @JsonProperty
  public String response;
  @JsonProperty
  public boolean threw_exception_during_call;
  @JsonProperty
  public String thrown_exception;

  /**
   * Constructor - ServerJsonControllerCallLog
   * @param controllerCallLog
   * @return new ServerJsonControllerCallLog Object
   * @throws Exception
   */
  @JsonIgnore
  public ServerJsonControllerCallLog (
    ControllerCallLog controllerCallLog) throws Exception {
    
    this.controller_name =
      controllerCallLog.getControllerName();
    this.controller_url =
      controllerCallLog.getControllerUrl();
    this.request_to_response_time_in_milli_seconds =
      controllerCallLog.getRequestToResponseTimeInMilliSeconds();
    this.response_http_status_code =
      controllerCallLog.getResponseHttpStatusCode();
    this.request =
      controllerCallLog.getRequestAsString();
    this.response =
      controllerCallLog.getResponseAsString();
    this.threw_exception_during_call =
      controllerCallLog.getThrewExceptionDuringCall();
    this.thrown_exception =
      controllerCallLog.getThrownException();
  }
}
