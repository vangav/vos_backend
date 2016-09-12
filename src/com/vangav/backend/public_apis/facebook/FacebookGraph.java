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

package com.vangav.backend.public_apis.facebook;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import com.vangav.backend.exceptions.BadRequestException;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;
import com.vangav.backend.networks.rest.RestAsync;
import com.vangav.backend.networks.rest.RestResponseJsonGroup;
import com.vangav.backend.public_apis.facebook.json.BadRequestResponse;
import com.vangav.backend.public_apis.facebook.json.fields.Id;
import com.vangav.backend.thread_pool.ThreadPool;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * FacebookGraph handles fetching user's data
 *   fields/edges through Facebook Graph API verions 2.1 to 2.7
 *   
 * Using Facebook Graph API's version v2.7 is highly recommended
 * 
 * Reference:
 * https://developers.facebook.com/docs/graph-api/reference/v2.7/user
 */
public class FacebookGraph {
  
  /**
   * enum Version represents supported Facebook Graph API's versions
   * */
  public enum Version {
    V_2_1,
    V_2_2,
    V_2_3,
    V_2_4,
    V_2_5,
    V_2_6,
    V_2_7;
    
    private String getVersionString () throws Exception {
      
      String[] versionSplit = this.toString().split("_");
      
      return
        versionSplit[0].toLowerCase()
        + versionSplit[1]
        + "."
        + versionSplit[2];
    }
  }
  
  private final String version;
  private final String accessToken;
  private final String userId;

  // format:
  //   String version,
  //   String access_token
  private static final String kGetUserIdFormat =
    "https://graph.facebook.com/%s/me?fields=id&access_token=%s";
  
  /**
   * FutureResponse is used to hold future response of asynchronous requests
   */
  private class FutureResponse {
    
    private static final String kSingletonEntryKey =
      "00000000-0000-1000-0000-000000000000";
    
    private CountDownLatch countDownLatch;
    private Map<Object, RestAsync> entries;
    
    /**
     * Constructor FutureResponse
     * for singular requests
     * @param countDownLatch
     * @param restAsync
     * @return new FutureResponse Object
     * @throws Exception
     */
    private FutureResponse (
      CountDownLatch countDownLatch,
      RestAsync restAsync) throws Exception {
      
      this.countDownLatch = countDownLatch;
      this.entries = new HashMap<Object, RestAsync>();
      
      this.entries.put(
        kSingletonEntryKey,
        restAsync);
    }
    
    /**
     * Constructor FutureResponse
     * for multiple requests
     * @param countDownLatch
     * @param entries
     * @return new FutureResponse Object
     * @throws Exception
     */
    private FutureResponse (
      CountDownLatch countDownLatch,
      Map<Object, RestAsync> entries) throws Exception {
      
      this.countDownLatch = countDownLatch;
      this.entries = entries;
    }
    
    /**
     * get
     * gets RestAsnc Object for single request FutureResponse Objects
     * @return RestAsync Object
     * @throws Exception
     */
    private RestAsync get () throws Exception {
      
      this.countDownLatch.await();
      
      return this.entries.get(kSingletonEntryKey);
    }
    
    /**
     * getAll
     * gets all RestAsync Objects for multi-request FutureResponse Objects
     * @return all RestAsync Objects
     * @throws Exception
     */
    private Map<Object, RestAsync> getAll () throws Exception {
      
      this.countDownLatch.await();
      
      return this.entries;
    }
  }
  
  private Map<String, FutureResponse> futureResponses;

  /**
   * Constructor FacebookGraph - BLOCKING
   * makes a Facebook Graph API request to get the user's ID and throws
   *   exceptions on failure
   * @param version
   * @param accessToken
   * @return new FacebookGraph Object
   * @throws Exception
   */
  public FacebookGraph (
    Version version,
    String accessToken) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "Facebook Graph API version",
      version,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "Facebook Graph API user's access token",
      accessToken,
      ExceptionType.CODE_EXCEPTION);
    
    this.version = version.getVersionString();
    this.accessToken = accessToken;
    
    String getUserIdUrl =
      String.format(
        kGetUserIdFormat,
        this.version,
        this.accessToken);
    
    CountDownLatch countDownLatch = new CountDownLatch(1);
    
    RestAsync restAsync =
      new RestAsync(
        countDownLatch,
        getUserIdUrl,
        new RestResponseJsonGroup(
          new Id(),
          new BadRequestResponse() ) );
    
    countDownLatch.await();
    
    // got a response status other than 200 (success) and 400 (bad_request)
    if (restAsync.gotMatchingJsonResponse() == false) {
      
      throw new BadRequestException(
        "Wrong facebook access token ["
          + this.accessToken
          + "], response's HTTP Status Code ["
          + restAsync.getResponseStatusCode()
          + "], raw response String ["
          + restAsync.getRawResponseString()
          + "]",
        ExceptionClass.AUTHENTICATION);
    }
    
    // not a 200 success http status code
    if (restAsync.isResponseStatusSuccess() == false) {
      
      throw new BadRequestException(
        "Got a 400 BAD_REQUEST Http Status Code response from Facebook "
          + "Graph API for access token ["
          + this.accessToken
          + "], Error Response ["
          + ((BadRequestResponse)restAsync.getRestResponseJson()).toString()
          + "]",
        ExceptionClass.AUTHENTICATION);
    }
    
    Id id = (Id)restAsync.getRestResponseJson();
    
    this.userId = id.id;
    
    this.futureResponses = new HashMap<String, FutureResponse>();
  }
  
  private enum RequestType {
    
    SYNC,
    ASYNC
  }
  
  // format:
  //   String version
  //   String fb_user_id,
  //   int picture_width
  private static final String kGetUserProfilePicture =
    "https://graph.facebook.com/%s/%s/picture?width=%d";
  
  /**
   * getProfilePictureSync
   * BLOCKING method
   * gets a facebook user's profile picture
   * @param pictureWidth (e.g.: 500 pixels)
   * @return user's facebook profile picture in String format
   * @throws Exception
   */
  public String getProfilePictureSync (int pictureWidth) throws Exception {
    
    return this.getProfilePicture(RequestType.SYNC, pictureWidth);
  }
  
  /**
   * getProfilePictureAsync
   * NON-BLOCKING method
   * issues an async request to a facebook user's profile picture and returns
   *   a tracking id for the request to get the response at a later time
   * @param pictureWidth
   * @return
   * @throws Exception
   */
  public String getProfilePictureAsync (int pictureWidth) throws Exception {
    
    return this.getProfilePicture(RequestType.ASYNC, pictureWidth);
  }
  
  /**
   * getProfilePictureAsync
   * BLOCKING method
   * gets a facebook user's profile picture from a previously issued async
   *   request
   * @param requestTrackingUuid
   * @return user's facebook profile picture in String format
   * @throws Exception
   */
  public String getProfilePictureAsync (
    String requestTrackingUuid) throws Exception {
    
    if (this.futureResponses.containsKey(requestTrackingUuid) == false) {
      
      throw new CodeException(
        "Invalid request tracking id ["
        + requestTrackingUuid
        + "]",
        ExceptionClass.INVALID);
    }
    
    RestAsync restAsync =
      this.futureResponses.remove(requestTrackingUuid).get();
    
    if (restAsync.isResponseStatusSuccess() == true) {
      
      return restAsync.getRawResponseString();
    } else {
      
      throw new BadRequestException(
        "Couldn't get profile picture for user wit facebook user id ["
          + this.userId
          + "]",
        ExceptionClass.COMMUNICATION);
    }
  }
  
  /**
   * getProfilePicture
   * @param requestType - SYNC pr ASYNC
   * @param pictureWidth
   * @return profile picture for SYNC requests and future response tracking id
   *           for ASYNC requests
   * @throws Exception
   */
  private String getProfilePicture (
    RequestType requestType,
    int pictureWidth) throws Exception {
    
    CountDownLatch countDownLatch = new CountDownLatch(1);
    
    RestAsync restAsync =
      new RestAsync(
        countDownLatch,
        String.format(
          kGetUserProfilePicture,
          this.version,
          this.userId,
          pictureWidth) );
    
    ThreadPool.i().executeInRestClientPool(restAsync);
    
    if (requestType == RequestType.SYNC) {
      
      countDownLatch.await();
      
      if (restAsync.isResponseStatusSuccess() == true) {
        
        return restAsync.getRawResponseString();
      } else {
        
        throw new BadRequestException(
          "Couldn't get profile picture for user wit facebook user id ["
            + this.userId
            + "]",
          ExceptionClass.COMMUNICATION);
      }
    } else if (requestType == RequestType.ASYNC) {
      
      String uuid = UUID.randomUUID().toString();
      
      this.futureResponses.put(
        uuid,
        new FutureResponse(countDownLatch, restAsync) );
      
      return uuid;
    }
    
    throw new CodeException(
      "Unhandled RequestType ["
        + requestType.toString()
        + "]",
      ExceptionClass.TYPE);
  }
  
  // format:
  //   String version,
  //   String fb_picture_id,
  //   String access_token
  private static final String kGetUserPicture =
    "https://graph.facebook.com/%s/%s/picture?access_token=%s";
  // format:
  //   String version,
  //   String fb_user_id,
  //   String field,
  //   String access_token
  private static final String kGetField =
    "https://graph.facebook.com/%s/%s?fields=%s&access_token=%s";
  // format:
  //   String version,
  //   String fb_user_id,
  //   String edge,
  //   int limit,
  //   String access_token
  private static final String kGetEdge =
    "https://graph.facebook.com/%s/%s/%s?limit=%d&access_token=%s";
  
  @Override
  public String toString () {
    
    return
      "FacebookGraph:\n"
      + "version ["
      + this.version
      + "]\nfb_user_id ["
      + this.userId
      + "]\nfb_user_access_token ["
      + this.accessToken
      + "]";
  }
}
