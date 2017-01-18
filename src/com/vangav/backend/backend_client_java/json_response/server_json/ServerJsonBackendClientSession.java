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

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vangav.backend.backend_client_java.BackendClientSession;
import com.vangav.backend.backend_client_java.ControllerCallLog;
import com.vangav.backend.play_framework.request.response.ResponseBodyJson;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ServerJsonBackendClientSession represents controllers calls' logs for
 *   a backend client session
 * An example where this JSON-mapping class would be useful is when the
 *   backend-client is a service that gets called from another client
 *   then the backend-client can call the backend controller's then return
 *   a log-report to the calling client.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerJsonBackendClientSession extends ResponseBodyJson {

  @Override
  @JsonIgnore
  protected String getName () throws Exception {

    return "server_json_backend_client_session";
  }

  @Override
  @JsonIgnore
  protected ServerJsonBackendClientSession getThis () throws Exception {
    
    return this;
  }
  
  @JsonProperty
  public String name;
  @JsonProperty
  public boolean with_controller_call_log;
  @JsonProperty
  public boolean total_success;
  @JsonProperty
  public int requests_count;
  @JsonProperty
  public int succeeded_requests_count;
  @JsonProperty
  public int failed_requests_count;
  @JsonProperty
  public int total_request_to_response_time_in_milli_seconds;
  @JsonProperty
  public int average_request_to_response_time_in_milli_seconds;
  @JsonProperty
  public ServerJsonControllerCallLog[] controllers_calls_logs;
  
  @JsonIgnore
  public void set (
    BackendClientSession backendClientSession) throws Exception {
    
    this.name =
      backendClientSession.getName();
    this.with_controller_call_log =
      backendClientSession.getWithControllerCallLog();
    this.total_success =
      backendClientSession.getTotalSuccess();
    this.requests_count =
      backendClientSession.getRequestsCount();
    this.succeeded_requests_count =
      backendClientSession.getSucceededRequestsCount();
    this.failed_requests_count =
      backendClientSession.getFailedRequestsCount();
    this.total_request_to_response_time_in_milli_seconds =
      backendClientSession.getTotalRequetToResponseTimeInMilliSeconds();
    this.average_request_to_response_time_in_milli_seconds =
      backendClientSession.getAverageRequestToResponseTimeInMilliSeconds();
    
    ArrayList<ControllerCallLog> controllersCallsLogs =
      backendClientSession.getControllersCallsLogs();
    
    this.controllers_calls_logs =
      new ServerJsonControllerCallLog[controllersCallsLogs.size() ];
    
    for (int i = 0; i < this.controllers_calls_logs.length; i ++) {
      
      this.controllers_calls_logs[i] =
        new ServerJsonControllerCallLog(controllersCallsLogs.get(i) );
    }
  }
}
