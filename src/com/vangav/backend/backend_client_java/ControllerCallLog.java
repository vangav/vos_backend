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

import com.vangav.backend.exceptions.VangavException;
import com.vangav.backend.networks.rest_client.RestRequest;
import com.vangav.backend.networks.rest_client.RestResponseJson;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ControllerCallLog is used to represent the log of one controller's call log
 */
public class ControllerCallLog {

  private String controllerName;
  private String controllerUrl;
  private long startTime;
  private int requestToResponseTimeInMilliSeconds;
  private int responseHttpStatusCode;
  private RestRequest request;
  private RestResponseJson response;
  private boolean threwExceptionsDuringCall;
  private String thrownException;
  
  /**
   * Constructor - ControllerCallLog
   * @param controllerName
   * @param controllerUrl
   * @param request
   * @return new ControllerCallLog Object
   */
  protected ControllerCallLog (
    String controllerName,
    String controllerUrl,
    RestRequest request) {
    
    this.controllerName = controllerName;
    this.controllerUrl = controllerUrl;
    this.startTime = System.currentTimeMillis();
    this.requestToResponseTimeInMilliSeconds = 0;
    this.responseHttpStatusCode = -2;
    this.request = request;
    this.response = null;
    this.threwExceptionsDuringCall = false;
    this.thrownException = null;
  }
  
  /**
   * endCall
   * invoked at the end of each call from ControllerCall's run method to
   *   complete a controller's call log
   * @param responseHttpStatusCode
   * @param response
   */
  protected void endCall (
    int responseHttpStatusCode,
    RestResponseJson response) {
    
    this.requestToResponseTimeInMilliSeconds =
      (int) (System.currentTimeMillis() - this.startTime);
    
    this.responseHttpStatusCode = responseHttpStatusCode;
    this.response = response;
  }
  
  /**
   * endCallWithExceptions
   * used instead of endCall in case an exception got thrown during processing
   * invoked at the end of each call from ControllerCall's run method to
   *   complete a controller's call log
   * @param e
   */
  protected void endCallWithExceptions (Exception e) {
    
    this.requestToResponseTimeInMilliSeconds =
      (int) (System.currentTimeMillis() - this.startTime);
    
    this.responseHttpStatusCode = -2;
    this.response = new ErrorResponse();
    
    this.threwExceptionsDuringCall = true;
    this.thrownException = VangavException.getExceptionStackTrace(e);
  }
  
  /**
   * getControllerName
   * @return this log's controller name
   */
  public String getControllerName () {
    
    return this.controllerName;
  }
  
  /**
   * getControllerUrl
   * @return this log's controller's url
   */
  public String getControllerUrl () {
    
    return this.controllerUrl;
  }
  
  /**
   * getRequestToResponseTimeInMilliSeconds
   * @return the milli seconds it took from issuing the request till getting
   *           a response
   */
  public int getRequestToResponseTimeInMilliSeconds () {
    
    return this.requestToResponseTimeInMilliSeconds;
  }
  
  /**
   * getResponseHttpStatusCode
   * @return the http status code returned from the call and -2 in case
   *           trying to make the controller's call threw an exception
   */
  public int getResponseHttpStatusCode () {
  
    return this.responseHttpStatusCode;
  }
  
  /**
   * getRequest
   * @return this log's request Object
   */
  public RestRequest getRequest () {
    
    return this.request;
  }
  
  /**
   * getRequestAsString
   * @return this log's request as a string
   */
  public String getRequestAsString () {
    
    if (this.request != null) {
    
      return this.request.toString();
    }
    
    return "null";
  }
  
  /**
   * getResponse
   * @return this log's response Object
   */
  public RestResponseJson getResponse () {
    
    return this.response;
  }
  
  /**
   * getResponseAsString
   * @return this log's response as a string
   */
  public String getResponseAsString () {
    
    if (this.response != null) {
    
      return this.response.toString();
    }
    
    return "null";
  }
  
  /**
   * getThrewExceptionDuringCall
   * @return true if this controller's call threw an exception during the call
   *           and false if the request-to-response process went through
   */
  public boolean getThrewExceptionDuringCall () {
    
    return this.threwExceptionsDuringCall;
  }
  
  /**
   * getThrownException
   * @return null if no exceptions were thrown during the controller's call
   *           and the thrown exception otherwise
   */
  public String getThrownException () {
    
    if (this.thrownException != null) {
    
      return this.thrownException;
    }
    
    return "null";
  }
  
  @Override
  public String toString () {
    
    String toString =
      "Controller call log:"
      + "\nController name ["
      + this.controllerName
      + "]\nController url ["
      + this.controllerUrl
      + "]\nRequest to response time in milli seconds ["
      + this.requestToResponseTimeInMilliSeconds
      + "]\nResponse http status code ["
      + this.responseHttpStatusCode
      + "]\nRequest ["
      + this.request.toString()
      + "]";
    
    if (this.response == null) {
      
      toString +=
        "\nResponse [null]";
    } else {
      
      toString +=
        "\nResponse ["
        + this.response.toString()
        + "]";
    }
    
    toString +=
      "\nThrew exception during call ["
      + this.threwExceptionsDuringCall
      + "]";
    
    if (this.threwExceptionsDuringCall == false) {
      
      toString +=
        "\nThrown exception [null]";
    } else {
      
      toString +=
        "\nThrown exception ["
        + this.thrownException
        + "]";
    }
    
    return toString;
  }
}
