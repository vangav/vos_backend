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

package com.vangav.backend.networks.rest_client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;

import com.vangav.backend.networks.DownloadInl;
import com.vangav.backend.thread_pool.LatchThread;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * RestAsync handles REST calls (GET/POST) with JSON/DOWNLOAD responses
 *   asynchronously
 * */
public class RestAsync extends LatchThread {

  /**
   * enum RestCallType defines the types of HTTP requests this class handles
   */
  private enum RestCallType {
    
    POST,
    GET
  }
  
  /**
   * enum RestCallResponseType defines the types of HTTP responses this class
   *   handles
   */
  private enum RestCallResponseType {
    
    JSON,
    DOWNLOAD
  }
  
  private final RestCallType restCallType;
  private final RestCallResponseType restCallResponseType;
  private final String requestString;
  
  private String url;
  
  private static final int kInvalidRestResponseStatus = -1;
  
  private int restResponseHttpStatusCode;
  private RestResponseJsonGroup restResponseJsonGroup;
  private RestResponseJson restResponseJson;
  
  private boolean gotMatchingJsonResponse;
  private String rawResponseString;
  
  /**
   * Constructor RestAsync
   * @param countDownLatch - automatically ticks down on request completion;
   *          used to execute multiple requests asynchronously in parallel then
   *          this CountDownLatch is notified after all requests finish
   *          execution
   * @param restRequestPostJson - POST Request Object
   * @param url - e.g.: https://graph.facebook.com/v2.7/me
   * @param restResponseJsonGroup
   * @return new RestAsync Object
   * @throws Exception
   */
  public RestAsync (
    final CountDownLatch countDownLatch,
    final RestRequestPostJson restRequestPostJson,
    String url,
    RestResponseJsonGroup restResponseJsonGroup) throws Exception {
    
    super(countDownLatch);
    
    this.restCallType = RestCallType.POST;
    this.restCallResponseType = RestCallResponseType.JSON;
    this.requestString = restRequestPostJson.getAsString();
    
    this.url = url;
    
    this.restResponseHttpStatusCode = kInvalidRestResponseStatus;
    this.restResponseJsonGroup = restResponseJsonGroup;
    this.restResponseJson = null;
    
    this.gotMatchingJsonResponse = false;
    this.rawResponseString = null;
  }
  
  /**
   * Constructor RestAsync
   * @param countDownLatch - automatically ticks down on request completion;
   *          used to execute multiple requests asynchronously in parallel then
   *          this CountDownLatch is notified after all requests finish
   *          execution
   * @param restRequestGetQuery - GET Request Object
   * @param url - e.g.: https://graph.facebook.com/v2.7/me
   * @param restResponseJsonGroup - a sub-class of RestResponseJson to hold
   *          this request's response
   * @return new RestAsync Object
   * @throws Exception
   */
  public RestAsync (
    final CountDownLatch countDownLatch,
    final RestRequestGetQuery restRequestGetQuery,
    String url,
    RestResponseJsonGroup restResponseJsonGroup) throws Exception {
    
    super(countDownLatch);
    
    this.restCallType = RestCallType.GET;
    this.restCallResponseType = RestCallResponseType.JSON;
    this.requestString = restRequestGetQuery.getQuery();
    
    this.url = url + "?" + this.requestString;
    
    this.restResponseHttpStatusCode = kInvalidRestResponseStatus;
    this.restResponseJsonGroup = restResponseJsonGroup;
    this.restResponseJson = null;
    
    this.gotMatchingJsonResponse = false;
    this.rawResponseString = null;
  }
  
  /**
   * Constructor RestAsync
   * @param countDownLatch - automatically ticks down on request completion;
   *          used to execute multiple requests asynchronously in parallel then
   *          this CountDownLatch is notified after all requests finish
   *          execution
   * @param url - full url (including GET parameters, if any)
   *          e.g.: https://graph.facebook.com/v2.7/123/friends?access_token=ab
   * @param restResponseJsonGroup
   * @throws Exception
   */
  public RestAsync (
    final CountDownLatch countDownLatch,
    final String url,
    RestResponseJsonGroup restResponseJsonGroup) throws Exception {
    
    super(countDownLatch);
    
    this.restCallType = RestCallType.GET;
    this.restCallResponseType = RestCallResponseType.JSON;
    this.requestString = "";
    
    this.url = url;
    
    this.restResponseHttpStatusCode = kInvalidRestResponseStatus;
    this.restResponseJsonGroup = restResponseJsonGroup;
    this.restResponseJson = null;
    
    this.gotMatchingJsonResponse = false;
    this.rawResponseString = null;
  }
  
  /**
   * Constructor RestAsync - FOR DOWNLOAD REQUESTS
   * @param countDownLatch - automatically ticks down on request completion;
   *          used to execute multiple requests asynchronously in parallel then
   *          this CountDownLatch is notified after all requests finish
   *          execution
   * @param url - full url (including GET parameters, if any)
   *          e.g.: https://graph.facebook.com/v2.7/123/picture?width=500
   * @throws Exception
   */
  public RestAsync (
    final CountDownLatch countDownLatch,
    final String url) throws Exception {
    
    super(countDownLatch);
    
    this.restCallType = RestCallType.GET;
    this.restCallResponseType = RestCallResponseType.DOWNLOAD;
    this.requestString = "";
    
    this.url = url;
    
    this.restResponseHttpStatusCode = kInvalidRestResponseStatus;
    this.restResponseJsonGroup = null;
    this.restResponseJson = null;
    
    this.gotMatchingJsonResponse = false;
    this.rawResponseString = null;
  }
  
  /**
   * isResponseStatusSuccess
   * @return true if response's status is 200 HTTP_OK and false otherwise
   * @throws Exception
   */
  public boolean isResponseStatusSuccess () throws Exception {
    
    if (this.restResponseHttpStatusCode == HttpURLConnection.HTTP_OK) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * isResponseStatusBadRequest
   * @return true if response's status is 400 HTTP_BAD_REQUEST and false
   *           otherwise
   * @throws Exception
   */
  public boolean isResponseStatusBadRequest () throws Exception {
    
    if (this.restResponseHttpStatusCode ==
          HttpURLConnection.HTTP_BAD_REQUEST) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * isResponseStatusInternalError
   * @return true if response's status is 500 HTTP_INTERNAL_ERROR and false
   *           otherwise
   * @throws Exception
   */
  public boolean isResponseStatusInternalError () throws Exception {
    
    if (this.restResponseHttpStatusCode ==
          HttpURLConnection.HTTP_INTERNAL_ERROR) {
      
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
    
    return this.restResponseHttpStatusCode;
  }
  
  /**
   * getRestResponseJson
   * @return this request's response JSON Object and null if this Object's
   *           RestResponseJsonGroup doesn't include a RestResponseJson Object
   *           for this request's response's HTTP Status Code
   * @throws Exception
   */
  public RestResponseJson getRestResponseJson () throws Exception {
    
    return this.restResponseJson;
  }
  
  /**
   * gotMatchingJsonResponse
   * @return true if the returned status matched one of those in the
   *           RestResponseJsonGroup and false otherwise
   * @throws Exception
   */
  public boolean gotMatchingJsonResponse () throws Exception {
    
    return this.gotMatchingJsonResponse;
  }
  
  /**
   * getRawResponseString
   * @return raw response's String (used when method gotMatchingJsonResponse
   *           returns false and when response's type is DOWNLOAD)
   * @throws Exception
   */
  public String getRawResponseString () throws Exception {
    
    return this.rawResponseString;
  }

  @Override
  protected void execute () throws Exception {
    
    if (this.restCallResponseType == RestCallResponseType.JSON) {
      
      this.executeJsonResponse();
    } else if (this.restCallResponseType == RestCallResponseType.DOWNLOAD) {
      
      this.executeDownloadResponse();
    }
  }
  
  /**
   * executeJsonResponse
   * executes requests with JSON response
   * @throws Exception
   */
  private void executeJsonResponse () throws Exception {

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
    this.restResponseHttpStatusCode =
      ((HttpURLConnection) urlConnection).getResponseCode();

    // to store response's content
    InputStream responseInputStream = null;

    if (this.restResponseHttpStatusCode == HttpURLConnection.HTTP_OK) {

      responseInputStream = urlConnection.getInputStream();
    } else {

      responseInputStream =
        ((HttpURLConnection) urlConnection).getErrorStream();
    }

    final String contentType = urlConnection.getHeaderField("Content-Type");

    String responseCharSet = "UTF-8";

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
    
    // set raw response String
    this.rawResponseString = responseStr;
    
    // set the response's JSON content
    this.restResponseJson =
      this.restResponseJsonGroup.getRestResponseJson(
        this.restResponseHttpStatusCode);
    
    // got a RestResponseJson Object for the current Http Status Code?
    if (this.restResponseJson != null) {
    
      this.gotMatchingJsonResponse = true;
      
      this.restResponseJson =
        this.restResponseJson.fromJsonString(responseStr);
    }
  }
  
  /**
   * executeDownloadResponse
   * executes download requests
   * @throws Exception
   */
  private void executeDownloadResponse () throws Exception {
    
    String downloadedData = DownloadInl.downloadFileAsString(this.url);
    
    if (downloadedData == null) {
      
      this.restResponseHttpStatusCode = HttpURLConnection.HTTP_BAD_REQUEST;
    } else {
      
      this.restResponseHttpStatusCode = HttpURLConnection.HTTP_OK;
      this.rawResponseString = downloadedData;
    }
  }
}
