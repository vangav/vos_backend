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

import java.util.concurrent.CountDownLatch;

import com.vangav.backend.networks.rest_client.RestAsync;
import com.vangav.backend.networks.rest_client.RestRequest;
import com.vangav.backend.networks.rest_client.RestRequestGetQuery;
import com.vangav.backend.networks.rest_client.RestRequestPostJson;
import com.vangav.backend.networks.rest_client.RestResponseJson;
import com.vangav.backend.networks.rest_client.RestResponseJsonGroup;
import com.vangav.backend.thread_pool.LatchThread;
import com.vangav.backend.thread_pool.ThreadPool;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ControllerCall is a parent class that get inherited per-controller then
 *   a new instance is created per-controller-call
 * This class handles making a controller call and creating a ControllerCallLog
 *   object for that call
 * NOTE: use a new Object for each call
 */
public abstract class ControllerCall extends LatchThread {
  
  private final CountDownLatch countDownLatch;
  private final RestRequest request;
  private RestAsync restAsyncRequest;
  private boolean addControllerCallLog;
  private ControllerCallLog controllerCallLog;
  
  /**
   * getControllerName
   * @return this inheriting controller's name
   */
  protected abstract String getControllerName ();
  
  /**
   * getUrl
   * @return this inheriting controller's url (complete url)
   *           e.g.: http://myservice.com/my_controller_name
   */
  protected abstract String getUrl ();
  
  /**
   * getRestResponseJson
   * @return a mapping-class that maps the json response for this inheriting
   *           controller
   */
  protected abstract RestResponseJson getRestResponseJson ();
  
  /**
   * getRestResponseJsonGroup
   * optional alternative to getRestResponseJson with a higher priority
   *   if not returning null
   * in case this is a client for a vangav backend then getRestResponseJson
   *   is sufficient and error response is automatically added
   * @return this controller's response json group
   */
  protected RestResponseJsonGroup getRestResponseJsonGroup () {
    
    return null;
  }
  
  /**
   * Constructor - ControllerCall
   * @param restRequestPostJson
   * @return new ControllerCall Object
   * @throws Exception
   */
  protected ControllerCall (
    final RestRequestPostJson restRequestPostJson) throws Exception {
    
    super (new CountDownLatch(1) );
    
    RestResponseJsonGroup restResponseJsonGroup;
    
    if (this.getRestResponseJsonGroup() == null) {
     
      restResponseJsonGroup =
        new RestResponseJsonGroup(
          this.getRestResponseJson(),
          new ErrorResponse(),
          new ErrorResponse() );
    } else {
      
      restResponseJsonGroup = this.getRestResponseJsonGroup();
    }
    
    this.countDownLatch = new CountDownLatch(1);
    
    this.request = restRequestPostJson;
    
    this.restAsyncRequest =
      new RestAsync(
        this.countDownLatch,
        restRequestPostJson,
        this.getUrl(),
        restResponseJsonGroup);
    
    this.addControllerCallLog = false;
    this.controllerCallLog = null;
  }
  
  /**
   * Constructor - ControllerCall
   * @param restRequestGetQuery
   * @return new ControllerCall Object
   * @throws Exception
   */
  protected ControllerCall (
    final RestRequestGetQuery restRequestGetQuery) throws Exception {
    
    super (new CountDownLatch(1) );
    
    RestResponseJsonGroup restResponseJsonGroup;
    
    if (this.getRestResponseJsonGroup() == null) {
     
      restResponseJsonGroup =
        new RestResponseJsonGroup(
        this.getRestResponseJson(),
        new ErrorResponse(),
        new ErrorResponse() );
    } else {
      
      restResponseJsonGroup = this.getRestResponseJsonGroup();
    }
    
    this.countDownLatch = new CountDownLatch(1);
    
    this.request = restRequestGetQuery;
    
    this.restAsyncRequest =
      new RestAsync(
        this.countDownLatch,
        restRequestGetQuery,
        this.getUrl(),
        restResponseJsonGroup);
    
    this.addControllerCallLog = false;
    this.controllerCallLog = null;
  }
  
  /**
   * getRestRequest
   * @return this controller's call rest request
   */
  protected RestRequest getRestRequest () {
    
    return this.request;
  }
  
  /**
   * setControllerCallLog
   * addControllerCallLog is false by default, this method sets it to true
   */
  protected void setControllerCallLog () {
    
    this.addControllerCallLog = true;
  }
  
  /**
   * getControllerCallLog
   * NOTE: wait for this thread to finish executing before calling this method
   * @return this controller's call-log
   * @throws Exception
   */
  protected ControllerCallLog getControllerCallLog () throws Exception {
    
    return this.controllerCallLog;
  }
  
  @Override
  final public void execute () {
    
    // initialize the controller call log?
    if (this.addControllerCallLog == true) {
    
      this.controllerCallLog =
        new ControllerCallLog(
          this.getControllerName(),
          this.getUrl(),
          this.request);
    }
    
    try {
    
      // process the controller's call
      ThreadPool.i().executeInRestClientPool(this.restAsyncRequest);
      
      // wait for response
      this.countDownLatch.await();
      
      // complete the controller call log?
      if (this.addControllerCallLog == true) {
      
        this.controllerCallLog.endCall(
          this.restAsyncRequest.getResponseStatusCode(),
          this.restAsyncRequest.getRestResponseJson() );
      }
    } catch (Exception e) {
      
      // complete the controller call log?
      if (this.addControllerCallLog == true) {
      
        this.controllerCallLog.endCallWithExceptions(e);
      }
    }
  }
  
  @Override
  public String toString () {
    
    String toString =
      "Controller call:"
      + "\nController name ["
      + this.getControllerName()
      + "]\nUrl ["
      + this.getUrl()
      + "]\nRest request: ["
      + this.request.toString()
      + "]\nRest async request ["
      + this.restAsyncRequest.toString()
      + "]\nAdd controller call log ["
      + this.addControllerCallLog
      + "]";
    
    if (this.controllerCallLog == null) {
      
      toString +=
        "\nController call log [null]";
    } else {
      
      toString +=
        "\nController call log ["
        + this.controllerCallLog.toString()
        + "]";
    }
    
    return toString;
  }
}
