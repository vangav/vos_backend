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

package com.vangav.backend.play_framework.request;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import com.vangav.backend.dispatcher.Dispatcher;
import com.vangav.backend.exceptions.VangavException;
import com.vangav.backend.metrics.time.TimeOperationsInl;
import com.vangav.backend.play_framework.request.response.ResponseBody;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Request contains the request, its response, dispatcher buffer, exception,
 *   and everything about a request-response pair. A new object of Request is
 *   created for each request and moved around for the life-time of that
 *   request till response and after-response operations are finished.
 * */
public class Request {
  
  private long startTime;
  private Calendar startCalendar;
  private long endTime;
  private int execTime;
  
  private RequestHeader header;
  private RequestJsonBody body;
  private ResponseBody response;
  private Dispatcher dispatcher;
  
  private String controllerName;
  private UUID userId;
  private boolean isThrottled;
  
  private RequestState state;
  private ArrayList<VangavException> vangavExceptions;
  private ArrayList<Exception> exceptions;
  
  /**
   * Constructor Request
   * @param request (play framework request)
   * @param requestJsonBody
   * @param responseBody
   * @param controllerName
   * @return new Request Object
   * @throws Exception
   */
  public Request (
    play.mvc.Http.Request request,
    RequestJsonBody requestJsonBody,
    ResponseBody responseBody,
    String controllerName) throws Exception {
    
    this.startTime = System.currentTimeMillis();
    this.startCalendar = TimeOperationsInl.unixTimeToCalendar(this.startTime);
    this.endTime = this.startTime;
    this.execTime = 0;
    
    this.header = new RequestHeader(request);
    this.body = requestJsonBody;
    this.response = responseBody;
    this.dispatcher = new Dispatcher();
    
    this.controllerName = controllerName;
    this.userId = this.body.getUserId();
    this.isThrottled = false;
    
    this.state = RequestState.OK;
    this.vangavExceptions = new ArrayList<VangavException>();
    this.exceptions = new ArrayList<Exception>();
  }

  /**
   * getStartTime
   * @return unix time when request is received
   */
  public long getStartTime () {
	
    return this.startTime;
  }
  
  /**
   * getStartCalendar
   * @return calendar when request is received
   */
  public Calendar getStartCalendar () {

    return this.startCalendar;
  }
  
  /**
   * endRequest
   * called right before sending out a response to set the time it took
   *   between receiving the request and sending out the response for it
   * @throws Exception
   */
  public void endRequest () throws Exception {
	  
  	this.endTime = System.currentTimeMillis();
  	this.execTime = ((int)(this.endTime - this.startTime) );
  }
  
  /**
   * getEndTime
   * @return request's end time
   */
  public long getEndTime () {
	  
    return this.endTime;
  }
  
  /**
   * getExecTime
   * @return request's execution time
   */
  public int getExecTime () {
    
    return this.execTime;
  }
  
  /**
   * getRequestHeader
   * @return request's header
   */
  public RequestHeader getRequestHeader () {
    
    return this.header;
  }
  
  /**
   * getRequestJsonBody
   * @return request's body in json format
   */
  public RequestJsonBody getRequestJsonBody () {
    
    return this.body;
  }
  
  /**
   * getResponseBody
   * @return request's response body
   */
  public ResponseBody getResponseBody () {
    
    return this.response;
  }
  
  /**
   * getDispatcher
   * @return request's dispatcher
   */
  public Dispatcher getDispatcher () {
    
    return this.dispatcher;
  }
  
  /**
   * getControllerName
   * @return controller's name
   */
  public String getControllerName () {
    
    return this.controllerName;
  }
  
  /**
   * setUserId
   * used when user id isn't part of the request's body (e.g.: on Facebook
   *   Login, user id is looked up in the database using the user's
   *   Facebook ID, etc ...)
   * @param userId
   */
  public void setUserId (UUID userId) {
    
    this.userId = userId;
  }
  
  /**
   * getUserId
   * @return user id for the user who sent the request (useful for services
   *           like mobile applications)
   */
  public UUID getUserId () {
    
    return this.userId;
  }
  
  /**
   * setIsThrottled
   * @param isThrottled: (true for throttled request and false otherwise)
   */
  public void setIsThrottled (boolean isThrottled) {
    
    this.isThrottled = isThrottled;
  }
  
  /**
   * getIsThrottled
   * @return true is request is throttled and false otherwise
   */
  public boolean getIsThrottled () {
    
    return this.isThrottled;
  }
  
  /**
   * setState
   * @param state: (e.g.: OK, BAD_REQUEST, INTERNAL_SERVER_ERROR, etc ...)
   */
  public void setState (RequestState state) {
    
    this.state = state;
  }
  
  /**
   * getState
   * @return (e.g.: OK, BAD_REQUEST, INTERNAL_SERVER_ERROR, etc ...)
   */
  public RequestState getState () {
    
    return this.state;
  }
  
  /**
   * addVangavException
   * when exceptions happen they are added to the request object to be used
   *   for response, analysis, logging, etc ...
   * @param vangavException
   */
  public void addVangavException (VangavException vangavException) {
    
    this.vangavExceptions.add(vangavException);
  }
  
  /**
   * getVangavExceptions
   * @return arraylist of Vangav Exceptions that occured during the processing
   *           of this request
   */
  public ArrayList<VangavException> getVangavExceptions () {
    
    return this.vangavExceptions;
  }
  
  /**
   * addException
   * when exceptions happen they are added to the request object to be used
   *   for response, analysis, logging, etc ...
   * @param exception
   */
  public void addException (Exception exception) {
    
    this.exceptions.add(exception);
  }
  
  /**
   * getExceptions
   * @return arraylist of Exceptions that occured during the processing
   *           of this request
   */
  public ArrayList<Exception> getExceptions () {
    
    return this.exceptions;
  }
  
  @Override
  public String toString () {
    
    return
      "Request:"
      + "\nstart time("
      + this.startTime
      + ")\nstart calendar("
      + this.startCalendar
      + ")\nend time("
      + this.endTime
      + ")\nexecution time("
      + this.execTime
      + ")\n"
      + this.header.toString()
      + this.body.toString()
      + this.response.toString()
      + this.dispatcher.toString()
      + "controller name("
      + this.controllerName
      + ")\nuser id("
      + this.userId.toString()
      + ")\nis throttled("
      + this.isThrottled
      + ")\nstate("
      + this.state.toString()
      + ")\nvangav exceptions("
      + this.vangavExceptions.toString()
      + ")\nexceptions("
      + this.exceptions.toString()
      + ")\n";
  }
}
