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

package com.vangav.backend.networks.rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;

import com.vangav.backend.thread_pool.LatchThread;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * RestAsync handles REST calls (GET/POST) with JSON response asynchronously
 * */
public class RestAsync extends LatchThread {

  /**
   * enum RestCallType defines the types of HTTP requests this class handles
   */
  private enum RestCallType {
    
    POST,
    GET
  }
  
  private final RestCallType restCallType;
  private final String requestString;
  
  private String url;
  
  private static final int kInvalidRestResponseStatus = -1;
  
  private int restResponseStatus;
  private RestResponseJson restResponseJson;
  
  /**
   * Constructor RestAsync
   * @param countDownLatch - automatically ticks down on request completion;
   *          used to execute multiple requests asynchronously in parallel then
   *          this CountDownLatch is notified after all requests finish
   *          execution
   * @param restRequestPostJson - POST Request Object
   * @param url - e.g.: https://graph.facebook.com/v2.7/me
   * @param restResponseJson - a sub-class of RestResponseJson to hold this
   *          request's response
   * @return new RestAsync Object
   * @throws Exception
   */
  public RestAsync (
    final CountDownLatch countDownLatch,
    final RestRequestPostJson restRequestPostJson,
    String url,
    RestResponseJson restResponseJson) throws Exception {
    
    super(countDownLatch);
    
    this.restCallType = RestCallType.POST;
    this.requestString = restRequestPostJson.getAsString();
    
    this.url = url;
    
    this.restResponseStatus = kInvalidRestResponseStatus;
    this.restResponseJson = restResponseJson;
  }
  
  /**
   * Constructor RestAsync
   * @param countDownLatch - automatically ticks down on request completion;
   *          used to execute multiple requests asynchronously in parallel then
   *          this CountDownLatch is notified after all requests finish
   *          execution
   * @param restRequestGetQuery - GET Request Object
   * @param url - e.g.: https://graph.facebook.com/v2.7/me
   * @param restResponseJson - a sub-class of RestResponseJson to hold this
   *          request's response
   * @return new RestAsync Object
   * @throws Exception
   */
  public RestAsync (
    final CountDownLatch countDownLatch,
    final RestRequestGetQuery restRequestGetQuery,
    String url,
    RestResponseJson restResponseJson) throws Exception {
    
    super(countDownLatch);
    
    this.restCallType = RestCallType.GET;
    this.requestString = restRequestGetQuery.getQuery();
    
    this.url = url + "?" + this.requestString;
    
    this.restResponseStatus = kInvalidRestResponseStatus;
    this.restResponseJson = restResponseJson;
  }
  
  /**
   * isResponseStatusSuccess
   * @return true if response's status is 200 HTTP_OK and false otherwise
   * @throws Exception
   */
  public boolean isResponseStatusSuccess () throws Exception {
    
    if (this.restResponseStatus == HttpURLConnection.HTTP_OK) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * getResponseStatusCode
   * @return response's status code
   *           e.g.: 200 OK
   *                 400 BAD_REQUEST
   *                 500 INTERNAL_SERVER_ERROR
   *                 etc ...
   * @throws Exception
   */
  public int getResponseStatusCode () throws Exception {
    
    return this.restResponseStatus;
  }
  
  /**
   * getRestResponseJson
   * @return this request's response JSON Object
   * @throws Exception
   */
  public RestResponseJson getRestResponseJson () throws Exception {
    
    return this.restResponseJson;
  }

  @Override
  protected void execute () throws Exception {

    // initiate connection
    URLConnection urlConnection = new URL(url).openConnection();
    urlConnection.setRequestProperty(
      "Accept-Charset",
      "UTF-8");
    
    // POST request?
    if (this.restCallType == RestCallType.POST) {
      
      // add POST request JSON content to the request
      urlConnection.setDoOutput(true);
      urlConnection.setRequestProperty(
        "Content-Type",
        "text/json");
      
      // send request and close output stream
      OutputStream outputStream = urlConnection.getOutputStream();
      outputStream.write(this.requestString.getBytes("UTF-8") );
      outputStream.close();
    }

    // get response's status
    this.restResponseStatus =
      ((HttpURLConnection) urlConnection).getResponseCode();

    // to store response's content
    InputStream responseInputStream = null;

    if (this.restResponseStatus == HttpURLConnection.HTTP_OK) {

      responseInputStream = urlConnection.getInputStream();
    } else {

      responseInputStream =
        ((HttpURLConnection) urlConnection).getErrorStream();
    }

    final String contentType = urlConnection.getHeaderField("Content-Type");

    String responseCharSet = null;

    for (String param : contentType.replace(" ", "").split(";") ) {

      if (param.startsWith("charset=") ) {

        responseCharSet = param.split("=", 2)[1];
        break;
      }
    }
    
    // read incoming response's content into a String
    
    BufferedReader reader =
      new BufferedReader(
        new InputStreamReader(
          responseInputStream,
          responseCharSet) );
    
    String responseStr = "";
    
    for (String line; (line = reader.readLine())  != null;) {

      responseStr += line;
    }
    
    reader.close();
    
    // set the response's JSON content
    this.restResponseJson = this.restResponseJson.fromJsonString(responseStr);
  }
}
