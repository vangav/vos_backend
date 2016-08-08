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

import java.util.Map;

import play.mvc.Http.Request;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * RequestHeader represents a request's header
 * */
public class RequestHeader {

  private String remoteAddress;
  private RequestType requestType;
  private String userAgent;
  private String httpAccept;
  private Map<String, String[]> headers;

  /**
   * Constructor RequestHeader
   * @param request
   * @return new RequestHeader Object
   * @throws Exception
   */
  public RequestHeader (Request request) throws Exception {

    this.remoteAddress = request.remoteAddress();
    this.requestType = RequestType.valueOf(request.method().toUpperCase() );
    this.userAgent = request.getHeader("User-Agent");
    this.httpAccept = request.getHeader("Accept");
    this.headers = request.headers();
  }
  
  /**
   * getRemoteAddress
   * @return request source's internet address (IP)
   */
  public final String getRemoteAddress () {
    
    return this.remoteAddress;
  }
  
  /**
   * getRequestType
   * @return request's type (POST, GET, etc ...)
   */
  public final RequestType getRequestType () {
    
    return this.requestType;
  }
  
  /**
   * getUserAgent
   * @return request's User-Agent
   */
  public final String getUserAgent () {
    
    return this.userAgent;
  }
  
  /**
   * getHttpAccept
   * @return request's Accept
   */
  public final String getHttpAccept () {
    
    return this.httpAccept;
  }
  
  /**
   * getHeaders
   * @return request's headers' map
   */
  public final Map<String, String[]> getHeaders () {
    
    return this.headers;
  }
  
  @Override
  public String toString () {
    
    return
      "Request Header:\n"
      + "remote address("
      + this.remoteAddress
      + ")\nrequest type("
      + this.requestType.toString()
      + ")\nuser agent("
      + this.userAgent
      + ")\nhttp accept("
      + this.httpAccept
      + ")\n headers("
      + this.headers.toString()
      + ")";
  }
}
