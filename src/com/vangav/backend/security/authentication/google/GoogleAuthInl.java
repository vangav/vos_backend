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

package com.vangav.backend.security.authentication.google;

import java.net.HttpURLConnection;
import java.net.URLConnection;

import com.vangav.backend.exceptions.BadRequestException;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.networks.rest.RestSyncInl;
import com.vangav.backend.networks.rest.RestRequestGetQuery;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * GoogleAuthInl handles Google login
 * */
public class GoogleAuthInl {

  // disable default instantiation
  private GoogleAuthInl () {}
  
  private static final String kGoogleApiAppLink =
    "https://www.googleapis.com/oauth2/v3/tokeninfo";
  
  /**
   * validateGoogleIdToken
   * Google login (authentication) through a user-app's google id token and
   *   Google app's id
   * sending the id token to Google should return the app's id
   * @param googleIdToken
   * @param googleAppId
   * @return user app's id (retrieved from google id's token)
   * @throws Exception
   */
  public static String validateGoogleIdToken (
    String googleIdToken,
    String googleAppId) throws Exception {
    
    try {
      
      // build google's GET request
      RestRequestGetQuery getQuery =
        new RestRequestGetQuery("id_token", googleIdToken);
      
      // send auth request to Google
      URLConnection urlConnection =
        RestSyncInl.restCall(kGoogleApiAppLink, getQuery);
      
      // check if response's status is OK (200)
      if (RestSyncInl.getResponseStatus(urlConnection) !=
          HttpURLConnection.HTTP_OK) {
        
        // internal server error?
        if (RestSyncInl.getResponseStatus(urlConnection) ==
            HttpURLConnection.HTTP_INTERNAL_ERROR) {
          
          throw new CodeException(
            "Error while communicating with google, google_id_token ["
            + googleIdToken
            + "]",
            ExceptionClass.COMMUNICATION);
        }
        
        throw new BadRequestException(
          "invalid google_id_token ["
          + googleIdToken
          + "]",
          ExceptionClass.COMMUNICATION);
      }
      
      // get Google's response content (App id and user's Google ID)
      ResponseGoogleAuth responseGoogleAuth =
        (ResponseGoogleAuth)RestSyncInl.getRestResponseJson(
          new ResponseGoogleAuth(),
          urlConnection);
      
      // null app id?
      if (responseGoogleAuth.aud == null) {
        
        throw new BadRequestException(
          "Google Auth returned null app id",
          ExceptionClass.AUTHENTICATION);
      }
      
      // correct app id? --> SUCCESS
      if (responseGoogleAuth.aud.compareTo(googleAppId) == 0) {
        
        return responseGoogleAuth.sub;
      }
      
      // wrong app id
      throw new CodeException(
        "Google Auth [wrong app id], got ["
        + responseGoogleAuth.aud
        + "] and expected ["
        + googleAppId
        + "]",
        ExceptionClass.AUTHENTICATION);
    } catch (Exception e) {
      
      throw new CodeException(
        "Error while communicating with google, google_id_token ["
        + googleIdToken
        + "]",
        ExceptionClass.COMMUNICATION);
    }
  }
}
