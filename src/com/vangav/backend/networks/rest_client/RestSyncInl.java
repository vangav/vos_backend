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
import java.util.Map;

import com.vangav.backend.data_structures_and_algorithms.tuple.Pair;
import com.vangav.backend.files.FileWriterInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * RestSyncInl has inline static methods for handling REST calls of different
 *   types synchronously
 * */
public class RestSyncInl {

  // disable default instantiation
  private RestSyncInl () {}
  
  public enum RestCallType {
    
    POST,
    GET
  }
  
  /**
   * restCall
   * short cut for REST calls using RestRequestGetQuery
   * @param url
   * @param getQuery
   * @return URLConnection Object containing the request's response
   * @throws Exception
   */
  public static URLConnection restCall (
    String url,
    RestRequestGetQuery getQuery) throws Exception {
    
    if (getQuery.hasHeaders() == true) {
    
      return
        restCall(
          url,
          RestCallType.GET,
          getQuery.getQuery(),
          getQuery.getHeaders() );
    } else {
    
      return
        restCall(
          url,
          RestCallType.GET,
          getQuery.getQuery(),
          null);
    }
  }
  
  /**
   * restCall
   * short cut for REST calls using RestRequestPostJson
   * @param url
   * @param postJson
   * @return URLConnection Object containing the request's response
   * @throws Exception
   */
  public static URLConnection restCall (
    String url,
    RestRequestPostJson postJson) throws Exception {
    
    if (postJson.hasHeaders() == true) {
    
      return
        restCall(
          url,
          RestCallType.POST,
          postJson.getAsString(),
          postJson.getHeaders() );
    } else {
    
      return
        restCall(
          url,
          RestCallType.POST,
          postJson.getAsString(),
          null);
    }
  }
  
  /**
   * @param url
   * @param type
   * @param request
   * @return URLConnection Object containing the request's response
   * @throws Exception
   */
  public static URLConnection restCall (
    String url,
    RestCallType type,
    String request) throws Exception {
    
    return restCall(url, type, request, null);
  }

  /**
   * @param url
   * @param type
   * @param request
   * @param requestHeaders
   * @return URLConnection Object containing the request's response
   * @throws Exception
   */
  public static URLConnection restCall (
    String url,
    RestCallType type,
    String request,
    Map<String, String> requestHeaders) throws Exception {
    
    if (type == RestCallType.GET) {
      
      url = url + "?" + request;
    }

    URLConnection urlConnection = new URL(url).openConnection();
    
    if (requestHeaders == null) {
    
      urlConnection.setRequestProperty(
        "Accept-Charset",
        "UTF-8");
    } else {
      
      for (String key : requestHeaders.keySet() ) {
        
        urlConnection.setRequestProperty(
          key,
          requestHeaders.get(key) );
      }
    }
    
    if (type == RestCallType.POST) {
      
      urlConnection.setDoOutput(true);
      
      if (requestHeaders == null) {
      
        urlConnection.setRequestProperty(
          "Content-Type",
          "text/json");
      }
      
      OutputStream outputStream = urlConnection.getOutputStream();
      outputStream.write(request.getBytes("UTF-8") );
      outputStream.close();
    }

    return urlConnection;
  }
  
  /**
   * getResponseStatus
   * @param urlConnection
   * @return status code (e.g.: 200 for HTTP_OK, 400 for BAD REQUEST, etc ...)
   * @throws Exception
   */
  public static int getResponseStatus (
    URLConnection urlConnection) throws Exception {
    
    return ((HttpURLConnection)urlConnection).getResponseCode();
  }
  
  /**
   * isResponseStatusSuccess
   * @param urlConnection
   * @return true is response status code is HTTP_OK (200) and false otherwise
   * @throws Exception
   */
  public static boolean isResponseStatusSuccess (
    URLConnection urlConnection) throws Exception {
    
    if (((HttpURLConnection)urlConnection).getResponseCode() ==
          HttpURLConnection.HTTP_OK) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * getRestResponseJson
   * @param restResponseJson
   * @param urlConnection
   * @return RestResponseJson representing the JSON response
   * @throws Exception
   */
  public static RestResponseJson getRestResponseJson (
    RestResponseJson restResponseJson,
    URLConnection urlConnection) throws Exception {
    
    return
      restResponseJson.fromJsonString(
        getResponseString(urlConnection).getSecond() );
  }
  
  /**
   * getRestResponseJson
   * @param restResponseJsonGroup
   * @param urlConnection
   * @return RestResponseJson representing the JSON response and null if param
   *           restResponseJsonGroup doesn't have a RestResponseJson Object
   *           for param urlConnection's HTTP Status Code
   * @throws Exception
   */
  public static RestResponseJson getRestResponseJson (
    RestResponseJsonGroup restResponseJsonGroup,
    URLConnection urlConnection) throws Exception {
    
    RestResponseJson restResponseJson =
      restResponseJsonGroup.getRestResponseJson(
        getResponseStatus(urlConnection) );
    
    if (restResponseJson == null) {
   
      return null;
    }
    
    return getRestResponseJson(restResponseJson, urlConnection);
  }
  
  /**
   * getResponseString
   * NOTE: doesn't work for binary file responses
   * @param urlConnection
   * @return extracts the status code and string response from a request's
   *           response
   * @throws Exception
   */
  public static Pair<Integer, String> getResponseString (
    URLConnection urlConnection) throws Exception {

    int status = ((HttpURLConnection) urlConnection).getResponseCode();

    InputStream response = null;

    if (status == HttpURLConnection.HTTP_OK) {

      response = urlConnection.getInputStream();
    } else {

      response = ((HttpURLConnection) urlConnection).getErrorStream();
    }

    final String contentType = urlConnection.getHeaderField("Content-Type");

    String responseCharSet = null;

    for (String param : contentType.replace(" ", "").split(";") ) {

      if (param.startsWith("charset=") ) {

        responseCharSet = param.split("=", 2)[1];
        break;
      }
    }
    
    BufferedReader reader =
      new BufferedReader(
        new InputStreamReader(
          response,
          responseCharSet) );
    
    String responseStr = "";
    
    for (String line; (line = reader.readLine())  != null;) {

      responseStr += line;
    }
    
    reader.close();
    
    return new Pair<Integer, String>(status, responseStr);
  }
  
  /**
   * writeResponseFile
   * used when an http request's response is a file, this method writes
   *   the received file on disk in param filePath
   * @param filePath
   * @param urlConnection
   * @param mkdirs
   * @return filePath
   * @throws Exception
   */
  public static String writeResponseFile (
    String filePath,
    URLConnection urlConnection,
    boolean mkdirs) throws Exception {
    
    return FileWriterInl.writeHttpResponseFile(filePath, urlConnection, mkdirs);
  }
}
