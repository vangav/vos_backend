/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * no license, I know you already got more than enough to worry about
 * keep going, never give up
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

package com.vangav.backend.dispatcher.worker;

import play.libs.F.Function0;
import play.libs.F.Promise;
import play.mvc.Result;
import play.mvc.Results;

import com.vangav.backend.dispatcher.DispatchMessage;
import com.vangav.backend.dispatcher.DispatchMessages;
import com.vangav.backend.thread_pool.ThreadPool;


/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ParentWorkerHandler is the parent class for all play framework workers'
 *   REST requests' handlers
 * How it works for a new service:
 *   new_service.controllers
 *     abstract CommonWorkerHandler extends ParentWorkerHandler
 *       Handler extends CommonHandler
 * CommonWorkerHandler overrides the common methods and the methods that service
 *   doesn't need while each Handler class overrides only the methods it needs
 *   to handle the corresponding controller's requests
 * */
public abstract class ParentWorkerHandler {
  
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
  private Result handleRequest (final play.mvc.Http.Request playRequest) {
    
    // start processing in a new thread
    try {
      
      ThreadPool.i().executeInRunnablePool(
        new ProcessRunnable(
          this,
          playRequest) );
    } catch (Exception e) {
      
      this.absorbUnhandledExceptions(e);
    }
    
    return Results.ok();
  }

  /**
   * AfterResponseRunnable
   * responsible for executing operations done after
   *   a response is sent for a request. E.g.: logging, analysis,
   *   push notifications, dispatching jobs, etc ...
   * */
  private class ProcessRunnable implements Runnable {
    
    private ParentWorkerHandler handler;
    private play.mvc.Http.Request playRequest;
    
    /**
     * constructor AfterResponseRunnable
     * @param handler: the handler responsible for handling the controller's
     *                   request
     * @param request: the request being processed
     */
    public ProcessRunnable (
      ParentWorkerHandler handler,
      play.mvc.Http.Request playRequest) {
      
      this.handler = handler;
      this.playRequest = playRequest;
    }
    
    @Override
    public void run () {
      
      try {
        
        DispatchMessages dispatchMessages = new DispatchMessages();

        dispatchMessages =
          dispatchMessages.fromJsonString(
            this.playRequest.body().asJson().toString() );
        
        this.handler.processRequest(dispatchMessages);
      } catch (Exception e) {

        this.handler.absorbUnhandledExceptions(e);
      }
    }
  }
  
  /**
   * processRequest
   * this is the main method for processing a request into a response
   * @param request
   * @throws Exception
   */
  protected void processRequest (
    final DispatchMessages dispatchMessages) throws Exception {
    
    try {
    
      for (DispatchMessage dispatchMessage :
           dispatchMessages.dispatch_messages) {
        
        try {
          
          dispatchMessage.execute();
        } catch (Exception e) {
          
          this.absorbUnhandledExceptions(e);
        }
      }
    } catch (Exception e) {
      
      this.absorbUnhandledExceptions(e);
    }
  }
}
