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

package com.vangav.backend.networks.rest_client;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * RestResponseJsonGroup holds pairs of
 *   (HTTP Status Code, RestResponseJson)
 *   in order to assign the correct response's holding JSON Class per
 *   response's HTTP Status Code
 * */
public class RestResponseJsonGroup {

  private Map<Integer, RestResponseJson> restResponseJsonMap;
  
  /**
   * Constructor RestResponseJsonGroup
   * @return new RestResponseJsonGroup Object with no responses defined
   * @throws Exception
   */
  public RestResponseJsonGroup () throws Exception {
    
    this.restResponseJsonMap = new HashMap<Integer, RestResponseJson>();
  }
  
  /**
   * Constructor RestResponseJsonGroup
   * simplified constructor in case user only wants to define a response for
   *   SUCCESS 200 requests and null otherwise
   * @param restResponseJsonSuccess
   * @return new RestResponseJsonGroup Object
   * @throws Exception
   */
  public RestResponseJsonGroup (
    RestResponseJson restResponseJsonSuccess) throws Exception {
    
    this.restResponseJsonMap = new HashMap<Integer, RestResponseJson>();
    
    this.restResponseJsonMap.put(
      HttpURLConnection.HTTP_OK,
      restResponseJsonSuccess);
  }
  
  /**
   * Constructor RestResponseJsonGroup
   * simplified constructor in case user only wants to define a response for
   *   SUCCESS 200 and BAD_REQUEST 400 requests and null otherwise
   * @param restResponseJsonSuccess
   * @param restResponseJsonBadRequest
   * @return new RestResponseJsonGroup Object
   * @throws Exception
   */
  public RestResponseJsonGroup (
    RestResponseJson restResponseJsonSuccess,
    RestResponseJson restResponseJsonBadRequest) throws Exception {
    
    this.restResponseJsonMap = new HashMap<Integer, RestResponseJson>();
    
    this.restResponseJsonMap.put(
      HttpURLConnection.HTTP_OK,
      restResponseJsonSuccess);
    this.restResponseJsonMap.put(
      HttpURLConnection.HTTP_BAD_REQUEST,
      restResponseJsonBadRequest);
  }
  
  /**
   * Constructor RestResponseJsonGroup
   * simplified constructor in case user only wants to define a response for
   *   SUCCESS 200, BAD_REQUEST 400 and INTERNAL_ERROR 500 requests and null
   *   otherwise
   * @param restResponseJsonSuccess
   * @param restResponseJsonBadRequest
   * @param restResponseJsonInternalError
   * @return new RestResponseJsonGroup Object
   * @throws Exception
   */
  public RestResponseJsonGroup (
    RestResponseJson restResponseJsonSuccess,
    RestResponseJson restResponseJsonBadRequest,
    RestResponseJson restResponseJsonInternalError) throws Exception {
    
    this.restResponseJsonMap = new HashMap<Integer, RestResponseJson>();
    
    this.restResponseJsonMap.put(
      HttpURLConnection.HTTP_OK,
      restResponseJsonSuccess);
    this.restResponseJsonMap.put(
      HttpURLConnection.HTTP_BAD_REQUEST,
      restResponseJsonBadRequest);
    this.restResponseJsonMap.put(
      HttpURLConnection.HTTP_INTERNAL_ERROR,
      restResponseJsonInternalError);
  }
  
  /**
   * addRestResponseJson
   * adds/updates a pair of http status code and the corresponding
   *   RestResponseJson Object
   * @param httpStatusCode
   * @param restResponseJson
   * @throws Exception
   */
  public void addRestResponseJson (
    final int httpStatusCode,
    RestResponseJson restResponseJson) throws Exception {
    
    this.restResponseJsonMap.put(httpStatusCode,restResponseJson);
  }
  
  /**
   * getRestResponseJson
   * @param httpStatusCode
   * @return the RestResponseJson Object that corresponds to param
   *           httpStatusCode and null if param httpStatusCode doesn't belong
   *           to this RestResponseJsonGroup Object
   * @throws Exception
   */
  public RestResponseJson getRestResponseJson (
    final int httpStatusCode) throws Exception {
    
    return this.restResponseJsonMap.get(httpStatusCode);
  }
  
  /**
   * Constructor RestResponseJsonGroup
   * private since it's exclusively used buy the Builder class
   *   RestResponseJsonGroupBuilder
   * @param restResponseJsonGroupBuilder
   * @return new RestResponseJsonGroup Object
   * @throws Exception
   */
  private RestResponseJsonGroup (
    RestResponseJsonGroupBuilder
      restResponseJsonGroupBuilder) throws Exception {
    
    this.restResponseJsonMap =
      restResponseJsonGroupBuilder.restResponseJsonMap;
  }
  
  /**
   * OPTIONAL builder pattern class for RestResponseJsonGroup to be used if
   *   a user wants to define responses for HTTP status codes other than the
   *   ones provided by the simplified constructors of RestResponseJsonGroup
   *   class (200 SUCCESS, 400 BAD_REQUEST and 500 INTERNAL_ERROR)
   */
  public static class RestResponseJsonGroupBuilder {

    private Map<Integer, RestResponseJson> restResponseJsonMap;

    /**
     * Constructor RestResponseJsonGroupBuilder
     * @return new RestResponseJsonGroupBuilder Object
     * @throws Exception
     */
    public RestResponseJsonGroupBuilder () throws Exception {
      
      this.restResponseJsonMap = new HashMap<Integer, RestResponseJson>();
    }
    
    /**
     * addRestResponseJson
     * used to add as many RestResponseJson Objects as needed for the HTTP
     *   Status Codes to be handled
     * @param httpStatusCode
     * @param restResponseJson
     * @return current state of this RestResponseJsonGroupBuilder Object
     * @throws Exception
     */
    public RestResponseJsonGroupBuilder addRestResponseJson (
      final int httpStatusCode,
      RestResponseJson restResponseJson) throws Exception {
      
      this.restResponseJsonMap.put(
        httpStatusCode,
        restResponseJson);
      
      return this;
    }
    
    /**
     * build
     * the last method to invoke after calling addRestResponseJson as many
     *   times as needed - then this method creates an RestResponseJsonGroup
     *   Object and returns it
     * @return the built RestResponseJsonGroup Object
     * @throws Exception
     */
    public RestResponseJsonGroup build () throws Exception {
      
      return new RestResponseJsonGroup(this);
    }
  }
}
