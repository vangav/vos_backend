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

package com.vangav.backend.play_framework;

import play.libs.F.Function0;
import play.libs.F.Promise;
import play.mvc.Result;
import play.mvc.Results;

import com.vangav.backend.exceptions.BadRequestException;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.DefaultException;
import com.vangav.backend.play_framework.request.Request;
import com.vangav.backend.play_framework.request.RequestJsonBody;
import com.vangav.backend.play_framework.request.RequestProperties;
import com.vangav.backend.play_framework.request.RequestState;
import com.vangav.backend.play_framework.request.response.ResponseBody;
import com.vangav.backend.thread_pool.ThreadPool;


/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ParentPlayHandler is the parent class for all play framework's
 *   REST requests' handlers
 * How it works for a new service:
 *   new_service.controllers
 *     abstract CommonPlayHandler extends ParentPlayHandler
 *       Handler extends CommonHandler
 * CommonPlayHandler overrides the common methods and the methods that service
 *   doesn't need while each Handler class overrides only the methods it needs
 *   to handle the corresponding controller's requests
 * */
public abstract class ParentPlayHandler {
  
  /**
   * getName
   * @return the corresponding controller's name
   */
  protected abstract String getName ();
  
  /**
   * getRequestJson
   * @return the controller's request json structure
   */
  protected abstract RequestJsonBody getRequestJson ();
  
  /**
   * getResponseBody
   * @return the controller's response structure
   */
  protected abstract ResponseBody getResponseBody ();
  
  /**
   * checkSource
   * some controllers are restricted to some device/ip/etc ... and that's the
   *   method to override to implement this security function
   * also for specific-client applications this helps verify the source of
   *   the request through a hashing the request with a secret-phrase or else
   * @param request
   * @throws Exception
   */
  protected abstract void checkSource (
    final Request request) throws Exception;
  
  /**
   * throttle
   * for controllers that need to detect behaviors like spam, DOS, etc ...
   * @param request
   * @throws Exception
   */
  protected abstract void throttle (
    final Request request) throws Exception;
  
  /**
   * authenticateRequest
   * for controllers that needs users to be logged in
   * @param request
   * @throws Exception
   */
  protected abstract void authenticateRequest (
    final Request request) throws Exception;
  
  /**
   * processRequest
   * this is the main method for processing a request into a response
   * @param request
   * @throws Exception
   */
  protected abstract void processRequest (
    final Request request) throws Exception;
  
  /**
   * afterProcessing
   * does the part of processing for a request that doesn't affect what goes
   *   into the response. this happens in a new thread in order to return
   *   the request's response in the shortest possible time
   * @param request
   * @throws Exception
   */
  protected abstract void afterProcessing (
    final Request request) throws Exception;
  
  /**
   * dispatchDefaultOperations
   * an example for a default operation is updating some database values
   *   for the user that issued the request (e.g.: update near history for
   *   detecting spam, DOS, etc ...)
   * @param request
   * @throws Exception
   */
  protected abstract void dispatchDefaultOperations (
    final Request request) throws Exception;
  
  // push notifications, analysis and logging methods
  
  /**
   * dispatchPushNotifications
   * notifications are enqueued into the dispatch buffer to be handled by
   *   a worker service to decrease the load on the main service handling
   *   the time critical operations
   * @param request
   * @throws Exception
   */
  protected abstract void dispatchPushNotifications (
    final Request request) throws Exception;
  
  /**
   * dispatchAnalysis
   * dispatch controller-specific analysis
   * @param request
   * @throws Exception
   */
  protected abstract void dispatchAnalysis (
    final Request request) throws Exception;
  
  /**
   * dispatchDefaultAnalysis
   * dispatch service-wide analysis that are independent of any controller
   * @param request
   * @throws Exception
   */
  protected abstract void dispatchDefaultAnalysis (
    final Request request) throws Exception;
  
  /**
   * dispatchLogging
   * dispatch controller-specific logging
   * @param request
   * @throws Exception
   */
  protected abstract void dispatchLogging (
    final Request request) throws Exception;
  
  /**
   * dispatchDefaultLogging
   * dispatch service-wide logging that are independent of any controller
   * @param request
   * @throws Exception
   */
  protected abstract void dispatchDefaultLogging (
    final Request request) throws Exception;
  
  /**
   * absorbExceptions
   * this method is the final resort when all exception handling mechanisms
   *   fail (e.g.: logging it, etc ...); this method is called so the service
   *   handles that exception and absorbs it
   * IMPORTANT: this method must be implemented within a try and catch block
   *              because any exceptions thrown by this method will terminate
   *              the service instance and disables it from handling other
   *              requests
   * @param exception
   */
  protected abstract void absorbUnhandledExceptions (
    final Exception exception);
  
  /**
   * handleRequestAsync
   * this is where request processing a request starts (gets called from
   *   a controller)
   * @param request (play framework request)
   * @return Result (containing response)
   */
  final public Promise<Result> handleRequestAsync (
    final play.mvc.Http.Request request) {
    
    return Promise.promise(

      new Function0<Result>() {

        @Override
        public Result apply() {
        
          return handleRequest(request);
        }
      }
    );
  }
  
  /**
   * handleRequest
   * manages all the processing from receiving a request till sending out
   *   a response then starts a new thread for after response operations
   * @param playRequest (play framework request)
   * @return Result (containing response)
   */
  private Result handleRequest (play.mvc.Http.Request playRequest) {
    
    Request request = null;
    Result result = null;
    
    try {

      // initialize request
      request =
        new Request(
          playRequest,
          this.getRequestJson().fromPlayRequest(playRequest),
          this.getResponseBody(),
          this.getName() );
      
      // check source?
      if (RequestProperties.i().getBooleanProperty(
            RequestProperties.kCheckSource) == true) {
        
        this.checkSource(request);
      }
      
      // throttle?
      if (RequestProperties.i().getBooleanProperty(
            RequestProperties.kThrottle) == true) {
        
        this.throttle(request);
      }
      
      // validate parameters
      if (RequestProperties.i().getBooleanProperty(
            RequestProperties.kValidateParam) == true) {
        
        request.getRequestJsonBody().validate();
      }
      
      // authenticate
      if (RequestProperties.i().getBooleanProperty(
            RequestProperties.kAuthenticate) == true) {
        
        this.authenticateRequest(request);
      }
      
      // process
      this.processRequest(request);
      
      // get result
      result = request.getResponseBody().getResult();
      
      // finished main processing (set execution time) -- response goes out
      request.endRequest();
    } catch (BadRequestException bre) {
      
      if (request != null) {
        
        try {
          
          request.setState(RequestState.BAD_REQUEST);
          request.addVangavException(bre);
        
          result =
            request.getResponseBody().getBadRequestResult(
              bre,
              request.getRequestId() );
        } catch (Exception e2) {
          
          result = Results.internalServerError();
        }
      } else {
        
        result = Results.internalServerError();
      }
    } catch (CodeException ce) {
      
      if (request != null) {
        
        try {
          
          request.setState(RequestState.INTERNAL_SERVER_ERROR);
          request.addVangavException(ce);
        
          result =
            request.getResponseBody().getInternalServerErrorResult(
              ce,
              request.getRequestId() );
        } catch (Exception e2) {
          
          result = Results.internalServerError();
        }
      } else {
        
        result = Results.internalServerError();
      }
    } catch (Exception e) {
      
      if (request != null) {
        
        try {
          
          request.setState(RequestState.INTERNAL_SERVER_ERROR);
          request.addException(e);
        
          result =
            request.getResponseBody().getInternalServerErrorResult(
              new DefaultException(),
              request.getRequestId() );
        } catch (Exception e2) {
          
          result = Results.internalServerError();
          
          this.absorbUnhandledExceptions(e2);
        }
      } else {
        
        result = Results.internalServerError();
        
        this.absorbUnhandledExceptions(e);
      }
    }

    if (result == null) {
    
      result = Results.internalServerError();
    }
    
    // start after processing in a new thread
    try {
    
      if (RequestProperties.i().getBooleanProperty(
            RequestProperties.kAfterResponse) == true) {
      
        if (request != null) {
          
          final Request afterProcessingRequest = request;
          
          try {
            
            ThreadPool.i().executeInRunnablePool(
              new AfterResponseRunnable(
                this,
                afterProcessingRequest) );
          } catch (Exception e) {
            
            this.absorbUnhandledExceptions(e);
          }
        }
      }
    } catch (Exception e) {
      
      try {

        result = Results.internalServerError();
      } catch (Exception e2) {
        
        this.absorbUnhandledExceptions(e2);
      }
      
      this.absorbUnhandledExceptions(e);
    }
    
    return result;
  }

  /**
   * AfterResponseRunnable
   * responsible for executing operations done after
   *   a response is sent for a request. E.g.: logging, analysis,
   *   push notifications, dispatching jobs, etc ...
   * */
  private class AfterResponseRunnable implements Runnable {
    
    private ParentPlayHandler handler;
    private Request request;
    
    /**
     * constructor AfterResponseRunnable
     * @param handler: the handler responsible for handling the controller's
     *                   request
     * @param request: the request being processed
     */
    public AfterResponseRunnable (
      ParentPlayHandler handler,
      Request request) {
      
      this.handler = handler;
      this.request = request;
    }
    
    @Override
    public void run () {
      
      try {
        
        this.handler.afterResponse(this.request);
      } catch (Exception e) {

        try {
          
          // try to dispatch stuff that already went into the dispatcher buffer
          this.request.getDispatcher().dispatchMessages();
        } catch (Exception e2) {
          
          this.handler.absorbUnhandledExceptions(e2);
        }
      }
    }
  }
  
  /**
   * afterResponse
   * manages all request operations after a response is sent
   * operations that doesn't affect the response like analysis, logging,
   *   etc ...
   * @param request
   * @throws Exception
   */
  private void afterResponse (final Request request) throws Exception {
    
    // after processing
    try {
      
      if (request.getState() == RequestState.OK &&
          RequestProperties.i().getBooleanProperty(
            RequestProperties.kAfterProcessing) == true) {
        
        this.afterProcessing(request);
      }
    } catch (Exception e) {
      
      try {
      
        request.addException(e);
      } catch (Exception e2) {
        
        this.absorbUnhandledExceptions(e2);
      }
    }
    
    // dispatch default operations
    try {
      
      if (request.getState() == RequestState.OK &&
          RequestProperties.i().getBooleanProperty(
            RequestProperties.kDefaultOperations) == true) {

        this.dispatchDefaultOperations(request);
      }
    } catch (Exception e) {
      
      try {
      
        request.addException(e);
      } catch (Exception e2) {
        
        this.absorbUnhandledExceptions(e2);
      }
    }
    
    // dispatch push notifications
    try {
     
      if (RequestProperties.i().getBooleanProperty(
            RequestProperties.kPushNotifications) == true) {
        
        this.dispatchPushNotifications(request);
      }
    } catch (Exception e) {
      
      try {
      
        request.addException(e);
      } catch (Exception e2) {
        
        this.absorbUnhandledExceptions(e2);
      }
    }

    // dispatch analysis
    try {
     
      if (RequestProperties.i().getBooleanProperty(
            RequestProperties.kAnalysis) == true) {
        
        this.dispatchAnalysis(request);
      }
    } catch (Exception e) {
      
      try {
      
        request.addException(e);
      } catch (Exception e2) {
        
        this.absorbUnhandledExceptions(e2);
      }
    }
    
    // dispatch default analysis
    try {
     
      if (RequestProperties.i().getBooleanProperty(
            RequestProperties.kAnalysis) == true) {
        
        this.dispatchDefaultAnalysis(request);
      }
    } catch (Exception e) {
      
      try {
      
        request.addException(e);
      } catch (Exception e2) {
        
        this.absorbUnhandledExceptions(e2);
      }
    }
    
    // dispatch logging
    try {
     
      if (RequestProperties.i().getBooleanProperty(
            RequestProperties.kLogging) == true) {
        
        this.dispatchLogging(request);
      }
    } catch (Exception e) {
      
      try {
      
        request.addException(e);
      } catch (Exception e2) {
        
        this.absorbUnhandledExceptions(e2);
      }
    }
    
    // dispatch default logging
    try {
     
      if (RequestProperties.i().getBooleanProperty(
            RequestProperties.kLogging) == true) {
        
        this.dispatchDefaultLogging(request);
      }
    } catch (Exception e) {
      
      try {
      
        request.addException(e);
      } catch (Exception e2) {
        
        this.absorbUnhandledExceptions(e2);
      }
    }
    
    // dispatch messages in the dispatcher

    try {

      request.getDispatcher().dispatchMessages();
    } catch (Exception e) {
      
      try {
      
        request.addException(e);
      } catch (Exception e2) {
        
        this.absorbUnhandledExceptions(e2);
      }
    }
  }
  
  @Override
  public String toString () {
    
    return
      "Controller's Handler:"
      + "\nname("
      + this.getName()
      + ")\nrequest("
      + this.getRequestJson().toString()
      + ")\nresponse("
      + this.getResponseBody().toString()
      + ")";
  }
}
