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

import com.vangav.backend.data_structures_and_algorithms.tuple.Pair;
import com.vangav.backend.exceptions.BadRequestException;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;
import com.vangav.backend.networks.rest.RestAsync;
import com.vangav.backend.networks.rest.RestResponseJson;
import com.vangav.backend.networks.rest.RestResponseJsonGroup;
import com.vangav.backend.public_apis.facebook.json.BadRequestResponse;
import com.vangav.backend.public_apis.facebook.json.ErrorResponse;
import com.vangav.backend.public_apis.facebook.json.edges.FacebookGraphApiEdgeType;
import com.vangav.backend.public_apis.facebook.json.fields.FacebookGraphApiFieldType;
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
  private class FutureResponse<T> {
    
    private static final String kSingletonEntryKey =
      "00000000-0000-1000-0000-000000000000";
    
    private CountDownLatch countDownLatch;
    private Map<T, RestAsync> entries;
    
    /**
     * Constructor FutureResponse
     * for singular requests
     * @param countDownLatch
     * @param restAsync
     * @return new FutureResponse Object
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    private FutureResponse (
      CountDownLatch countDownLatch,
      RestAsync restAsync) throws Exception {
      
      this.countDownLatch = countDownLatch;
      this.entries = new HashMap<T, RestAsync>();
      
      this.entries.put(
        (T)kSingletonEntryKey,
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
      Map<T, RestAsync> entries) throws Exception {
      
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
    private Map<T, RestAsync> getAll () throws Exception {
      
      this.countDownLatch.await();
      
      return this.entries;
    }
  }
  
  private Map<String, FutureResponse<String> > futureDownloadResponses;
  private
    Map<
      String,
      FutureResponse<FacebookGraphApiFieldType> > futureFieldResponses;
  private
    Map<
      String,
      FutureResponse<FacebookGraphApiEdgeType> > futureEdgeResponses;

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
    
    this.futureDownloadResponses =
      new HashMap<String, FutureResponse<String> >();
    this.futureFieldResponses =
      new HashMap<String, FutureResponse<FacebookGraphApiFieldType> >();
    this.futureEdgeResponses =
      new HashMap<String, FutureResponse<FacebookGraphApiEdgeType> >();
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
   * @return the tracking id to be used at a later time to get this async
   *           request's response
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
    
    if (this.futureDownloadResponses.containsKey(
          requestTrackingUuid) == false) {
      
      throw new CodeException(
        "Invalid request tracking id ["
        + requestTrackingUuid
        + "]",
        ExceptionClass.INVALID);
    }
    
    RestAsync restAsync =
      this.futureDownloadResponses.remove(requestTrackingUuid).get();
    
    if (restAsync.isResponseStatusSuccess() == true) {
      
      return restAsync.getRawResponseString();
    } else {
      
      throw new BadRequestException(
        "Couldn't get profile picture for user wit facebook user id ["
          + this.userId
          + "] and fb_access_token ["
          + this.accessToken
          + "], got http status code ["
          + restAsync.getResponseStatusCode()
          + "] and raw response ["
          + restAsync.getRawResponseString()
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
      
      this.futureDownloadResponses.put(
        uuid,
        new FutureResponse<String>(countDownLatch, restAsync) );
      
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
  
  /**
   * getPicturesSync
   * BLOCKING method
   * @param pictureIds - of all the picture to be fetched
   * @return Map<String, Pair<Boolean, String> >
   *          key is the picture_id
   *          pair-Boolean is true for success response HTTP_OK (200)
   *            and false otherwise
   *          pair-String for the raw response String (the picture in case of a
   *            success response)
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public Map<String, Pair<Boolean, String> > getPicturesSync (
    String... pictureIds) throws Exception {
   
    return
      (Map<String, Pair<Boolean, String> >)this.getPictures(
        RequestType.SYNC,
        pictureIds);
  }
  
  /**
   * getPicturesAsync
   * NON-BLOCKING method
   * @param pictureIds - of all the pictures to be fetched
   * @return the tracking id to be used at a later time to get this async
   *           request's response
   * @throws Exception
   */
  public String getPicturesAsync (
    String... pictureIds) throws Exception {
    
    return (String)this.getPictures(RequestType.ASYNC, pictureIds);
  }
  
  /**
   * getPicturesAsync
   * BLOCKING method
   * @param requestTrackingUuid
   * @return Map<String, Pair<Boolean, String> >
   *          key is the picture_id
   *          pair-Boolean is true for success response HTTP_OK (200)
   *            and false otherwise
   *          pair-String for the raw response String (the picture in case of a
   *            success response)
   * @throws Exception
   */
  public Map<String, Pair<Boolean, String> > getPicturesAsync (
    String requestTrackingUuid) throws Exception {

    if (this.futureDownloadResponses.containsKey(
          requestTrackingUuid) == false) {

      throw new CodeException(
        "Invalid request tracking id ["
        + requestTrackingUuid
        + "]",
        ExceptionClass.INVALID);
    }
    
    Map<String, RestAsync> requests =
      this.futureDownloadResponses.remove(requestTrackingUuid).getAll();
    
    RestAsync currRestAsync;
    
    Map<String, Pair<Boolean, String> > result =
      new HashMap<String, Pair<Boolean, String> >();
    
    for (String pictureId : requests.keySet() ) {
      
      currRestAsync = requests.get(pictureId);
      
      if (currRestAsync.isResponseStatusSuccess() == true) {
        
        result.put(
          pictureId,
          new Pair<Boolean, String>(
            true,
            currRestAsync.getRawResponseString() ) );
      } else {
        
        result.put(
          pictureId,
          new Pair<Boolean, String>(
            false,
            currRestAsync.getRawResponseString() ) );
      }
    }
    
    return result;
  }
  
  /**
   * getPictures
   * @param requestType SYNC or ASYNC
   * @param pictureIds
   * @return
   *        SYNC: Map<String, Pair<Boolean, String> >
   *          key is the picture_id
   *          pair-Boolean is true for success response HTTP_OK (200)
   *            and false otherwise
   *          pair-String for the raw response String (the picture in case of a
   *            success response)
   *        ASYNC: String representing the request-tracking-uuid
   * @throws Exception
   */
  private Object getPictures (
    RequestType requestType,
    String... pictureIds) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "picture ids",
      pictureIds,
      ExceptionType.CODE_EXCEPTION);
    
    CountDownLatch countDownLatch = new CountDownLatch(pictureIds.length);
    
    Map<String, RestAsync> requests = new HashMap<String, RestAsync>();
    
    RestAsync currRestAsync;
    
    for (String pictureId : pictureIds) {
      
      currRestAsync =
        new RestAsync(
          countDownLatch,
          String.format(
            kGetUserPicture,
            this.version,
            pictureId,
            this.accessToken) );
      
      requests.put(
        pictureId,
         currRestAsync);
      
      ThreadPool.i().executeInRestClientPool(currRestAsync);
    }
    
    if (requestType == RequestType.SYNC) {
      
      countDownLatch.await();
      
      Map<String, Pair<Boolean, String> > result =
        new HashMap<String, Pair<Boolean, String> >();
      
      for (String pictureId : pictureIds) {
        
        currRestAsync = requests.get(pictureId);
        
        if (currRestAsync.isResponseStatusSuccess() == true) {
          
          result.put(
            pictureId,
            new Pair<Boolean, String>(
              true,
              currRestAsync.getRawResponseString() ) );
        } else {
          
          result.put(
            pictureId,
            new Pair<Boolean, String>(
              false,
              currRestAsync.getRawResponseString() ) );
        }
      }
      
      return result;
    } else if (requestType == RequestType.ASYNC) {
      
      String uuid = UUID.randomUUID().toString();
      
      this.futureDownloadResponses.put(
        uuid,
        new FutureResponse<String>(countDownLatch, requests) );
      
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
  //   String fb_user_id,
  //   String field,
  //   String access_token
  private static final String kGetField =
    "https://graph.facebook.com/%s/%s?fields=%s&access_token=%s";
  
  private Object getFields (
    RequestType requestType,
    FacebookGraphApiFieldType... fields) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "fields",
      fields,
      ExceptionType.CODE_EXCEPTION);
    
    CountDownLatch countDownLatch = new CountDownLatch(fields.length);
    
    Map<FacebookGraphApiFieldType, RestAsync> requests =
      new HashMap<FacebookGraphApiFieldType, RestAsync>();

    RestAsync currRestAsync;
    
    for (FacebookGraphApiFieldType field : fields) {
      
      currRestAsync =
        new RestAsync(
          countDownLatch,
          String.format(
            kGetField,
            this.version,
            this.userId,
            field.getName(),
            this.accessToken),
          new RestResponseJsonGroup(
            field.getNewFieldInstance(),
            new BadRequestResponse() ) );
      
      requests.put(
        field,
        currRestAsync);

      ThreadPool.i().executeInRestClientPool(currRestAsync);
    }
    
    if (requestType == RequestType.SYNC) {
      
      countDownLatch.await();

      Map<FacebookGraphApiFieldType, Pair<Integer, RestResponseJson> > result =
        new HashMap<
          FacebookGraphApiFieldType,
          Pair<Integer, RestResponseJson> >();
      
      for (FacebookGraphApiFieldType field : fields) {
        
        currRestAsync = requests.get(field);
        
        if (currRestAsync.gotMatchingJsonResponse() == true) {
          
          result.put(
            field,
            new Pair<Integer, RestResponseJson>(
              currRestAsync.getResponseStatusCode(),
              currRestAsync.getRestResponseJson() ) );
        } else {
          
          result.put(
            field,
            new Pair<Integer, RestResponseJson>(
              currRestAsync.getResponseStatusCode(),
              new ErrorResponse(currRestAsync.getRawResponseString() ) ) );
        }
      }
      
      return result;
    } else if (requestType == RequestType.ASYNC) {

      String uuid = UUID.randomUUID().toString();
      
      this.futureFieldResponses.put(
        uuid,
        new FutureResponse<FacebookGraphApiFieldType>(
          countDownLatch,
          requests) );
      
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
