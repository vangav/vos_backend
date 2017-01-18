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

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;

import com.vangav.backend.thread_pool.ThreadPool;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * BackendClientSession represents a session where multiple controller calls
 *   can be made sync or async, together or individually or in groups
 * As a session it keeps track of all made calls in order with the
 *   ControllerCallLog for each of those controllers' calls
 */
public class BackendClientSession {

  private String name;
  private boolean withControllerCallLog;
  
  private boolean totalSuccess;
  private int requestsCount;
  private int succeededRequestsCount;
  private int failedRequestsCount;
  private int totalRequestToResponseTimeInMilliSeconds;
  private int averageRequestToResponseTimeInMilliSeconds;
  private ArrayList<ControllerCallLog> controllersCallsLogs;
  
  /**
   * Constructor - BackendClientSession
   * @param withControllerCallLog
   * @return new BackendClientSession Object
   */
  public BackendClientSession (boolean withControllerCallLog) {
    
    this("", withControllerCallLog);
  }
  
  /**
   * Constructor - BackendClientSession
   * @param name
   * @param withControllerCallLog
   * @return new BackendClientSession Object
   */
  public BackendClientSession (String name, boolean withControllerCallLog) {
    
    this.name = name;
    this.withControllerCallLog = withControllerCallLog;
    
    this.totalSuccess = true;
    this.requestsCount = 0;
    this.succeededRequestsCount = 0;
    this.failedRequestsCount = 0;
    this.totalRequestToResponseTimeInMilliSeconds = 0;
    this.averageRequestToResponseTimeInMilliSeconds = 0;
    this.controllersCallsLogs = new ArrayList<ControllerCallLog>();
  }
  
  /**
   * merge
   * NOTE: if some BackendClientSession Objects has withControllerCallLog set
   *         to true while others are set to false, the merge will be kind of
   *         a mess (mixing actual logging records with default ones)
   * @param backendClientSessions
   * @return a new instance of BackendClientSession representing the merge of
   *           this BackendClientSession instance with param
   *           backendClientSessions
   */
  public BackendClientSession merge (
    BackendClientSession... backendClientSessions) {
    
    // create a copy of this BackendClientSession
    
    BackendClientSession backendClientSession =
      new BackendClientSession(
        this.name,
        this.withControllerCallLog);
    
    backendClientSession.totalSuccess =
      this.totalSuccess;
    backendClientSession.requestsCount =
      this.requestsCount;
    backendClientSession.succeededRequestsCount =
      this.succeededRequestsCount;
    backendClientSession.failedRequestsCount =
      this.failedRequestsCount;
    backendClientSession.totalRequestToResponseTimeInMilliSeconds =
      this.totalRequestToResponseTimeInMilliSeconds;
    backendClientSession.averageRequestToResponseTimeInMilliSeconds =
      this.averageRequestToResponseTimeInMilliSeconds;
    
    for (ControllerCallLog controllerCallLog : this.controllersCallsLogs) {
      
      backendClientSession.controllersCallsLogs.add(controllerCallLog);
    }
    
    // nothing to merge? return copy
    if (backendClientSessions == null || backendClientSessions.length == 0) {
      
      return backendClientSession;
    }
    
    // merge param backendClientSessions to the copy of this
    //   BackendClientSession
    for (BackendClientSession currBackendClientSession :
         backendClientSessions) {
      
      backendClientSession.totalSuccess &=
        currBackendClientSession.totalSuccess;
      backendClientSession.requestsCount +=
        currBackendClientSession.requestsCount;
      backendClientSession.succeededRequestsCount +=
        currBackendClientSession.succeededRequestsCount;
      backendClientSession.failedRequestsCount +=
        currBackendClientSession.failedRequestsCount;
      backendClientSession.totalRequestToResponseTimeInMilliSeconds +=
        currBackendClientSession.totalRequestToResponseTimeInMilliSeconds;
      backendClientSession.averageRequestToResponseTimeInMilliSeconds =
        backendClientSession.totalRequestToResponseTimeInMilliSeconds /
        backendClientSession.requestsCount;
      
      for (ControllerCallLog controllerCallLog :
           currBackendClientSession.controllersCallsLogs) {
        
        backendClientSession.controllersCallsLogs.add(controllerCallLog);
      }
    }
    
    return backendClientSession;
  }
  
  /**
   * getName
   * @return this backend client session's name
   */
  public String getName () {
    
    return this.name;
  }
  
  /**
   * enum RestCallsType is used to identify whether to execute controllers
   *   calls sync or async
   * sync: no call starts executing until the previous one has returned
   *         a response
   * async: all calls starts executing regardless of the state of the previous
   *          call
   */
  public enum RestCallsType {
    
    SYNC,
    ASYNC
  }
  /**
   * executeControllersCalls
   * synchronized method so that executing a set of controllers calls never
   *   start till the previous set has finished executing, since the global
   *   variables modified during processing controllers calls aren't
   *   thread-safe
   * @param restCallsType
   * @param controllerCalls
   * @return An array list of controller call logs just for this method call
   *           rather than the whole session so far. It returns and empty
   *           array list in case withControllerCallLog is set to false.
   * @throws Exception
   */
  public synchronized ArrayList<ControllerCallLog> executeControllersCalls (
    RestCallsType restCallsType,
    ControllerCall... controllerCalls) throws Exception {
    
    ArrayList<ControllerCallLog> result = new ArrayList<ControllerCallLog>();
    
    // nothing to exeute?
    if (controllerCalls == null || controllerCalls.length == 0) {
      
      return result;
    }
    
    // log controller's calls?
    if (this.withControllerCallLog == true) {
      
      for (ControllerCall controllerCall : controllerCalls) {
        
        controllerCall.setControllerCallLog();
      }
    }
    
    // execute controllers' calls
    for (ControllerCall controllerCall : controllerCalls) {
      
      ThreadPool.i().executeInRunnablePool(controllerCall);
      
      // sync wait?
      if (restCallsType == RestCallsType.SYNC) {
        
        controllerCall.await();
      }
    }
    
    // async? wait for all requests to finish
    if (restCallsType == RestCallsType.ASYNC) {
      
      for (ControllerCall controllerCall : controllerCalls) {
        
        controllerCall.await();
      }
    }
    
    // collect logs?
    if (this.withControllerCallLog == true) {
      
      for (ControllerCall controllerCall : controllerCalls) {
        
        ControllerCallLog controllerCallLog =
          controllerCall.getControllerCallLog();
        
        if (controllerCallLog.getResponseHttpStatusCode() !=
            HttpURLConnection.HTTP_OK) {
          
          this.totalSuccess = false;
        }
        
        this.requestsCount += 1;
        
        if (controllerCallLog.getResponseHttpStatusCode() ==
            HttpURLConnection.HTTP_OK) {
          
          this.succeededRequestsCount += 1;
        } else {
          
          this.failedRequestsCount += 1;
        }
        
        this.totalRequestToResponseTimeInMilliSeconds +=
          controllerCallLog.getRequestToResponseTimeInMilliSeconds();
       
        this.controllersCallsLogs.add(controllerCallLog);
        result.add(controllerCallLog);
      }
      
      this.averageRequestToResponseTimeInMilliSeconds =
        this.totalRequestToResponseTimeInMilliSeconds /
        this.requestsCount;
    }
    
    return result;
  }
  
  /**
   * getWithControllerCallLog
   * @return this backend client sessions "with controller call log" value
   */
  public boolean getWithControllerCallLog () {
    
    return this.withControllerCallLog;
  }
  
  /**
   * getTotalSuccess
   * @return total success's value, true if no calls have been execute or if
   *           all executed calls resulted in a 200 (HTTP_OK) success response
   *           http status code and false otherwise
   */
  public boolean getTotalSuccess () {
    
    return this.totalSuccess;
  }
  
  /**
   * getRequestsCount
   * @return the count of all requests executed through this session
   */
  public int getRequestsCount () {
    
    return this.requestsCount;
  }
  
  /**
   * getSucceededRequestsCount
   * @return the count of successful requests (returning HTTP status code 200)
   */
  public int getSucceededRequestsCount () {
    
    return this.succeededRequestsCount;
  }
  
  /**
   * getFailedRequestsCount
   * @return the count of failed requests (returning any HTTP status code other
   *           than 200)
   */
  public int getFailedRequestsCount () {
    
    return this.failedRequestsCount;
  }
  
  /**
   * getTotalRequetToResponseTimeInMilliSeconds
   * @return total request-to-response-time it took to execute all
   *           requests through this session so far.
   *         if requests A and B got exeuted async taking a total time of
   *           10 but each of them took 7 then the total time will be
   *           7 + 7 = 14
   */
  public int getTotalRequetToResponseTimeInMilliSeconds () {
    
    return this.totalRequestToResponseTimeInMilliSeconds;
  }
  
  /**
   * getAverageRequestToResponseTimeInMilliSeconds
   * @return total time / requests count
   */
  public int getAverageRequestToResponseTimeInMilliSeconds () {
    
    return this.averageRequestToResponseTimeInMilliSeconds;
  }
  
  /**
   * getControllersCallsLogs
   * @return controllers calls's logs (empty array list if logging is turned
   *           off or no requests has been executed so far through this
   *           session)
   */
  public ArrayList<ControllerCallLog> getControllersCallsLogs () {
    
    return this.controllersCallsLogs;
  }
  
  @Override
  public String toString () {
    
    String toString =
      "Backend client session:"
      + "\nName ["
      + this.name
      + "]\nWith controller call log ["
      + this.withControllerCallLog
      + "]\nTotal success ["
      + this.totalSuccess
      + "]\nRequests count ["
      + this.requestsCount
      + "]\nSucceeded requests count ["
      + this.succeededRequestsCount
      + "]\nFailed requests count ["
      + this.failedRequestsCount
      + "]\nTotal request to response time in milli seconds ["
      + this.totalRequestToResponseTimeInMilliSeconds
      + "]\nAverage request to response time in milli seconds ["
      + this.averageRequestToResponseTimeInMilliSeconds
      + "]\nControllers calls logs ["
      + Arrays.toString(
          this.controllersCallsLogs.toArray(new ControllerCallLog[0] ) )
      + "]";
    
    return toString;
  }
}
