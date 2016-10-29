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
