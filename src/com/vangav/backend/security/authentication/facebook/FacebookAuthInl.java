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

package com.vangav.backend.security.authentication.facebook;

import java.net.HttpURLConnection;
import java.net.URLConnection;

import com.vangav.backend.exceptions.BadRequestException;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.networks.rest.RestRequestGetQuery;
import com.vangav.backend.networks.rest.RestInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * FacebookAuthInl handles facebook login
 */
public class FacebookAuthInl {

  // disable default instantiation
  private FacebookAuthInl () {}
  
  private static final String kFacebookGraphApiAppLink =
    "https://graph.facebook.com/app";
  
  /**
   * validateFacebookAccessToken
   * Facebook login (authentication) through a user-app's facebook access
   *   token and the facebook app's id
   * sending the access token to Facebook should return the app's id
   * @param facebookAccessToken
   * @param facebookAppId
   * @throws Exception
   */
  public static void validateFacebookAccessToken (
    String facebookAccessToken,
    String facebookAppId) throws Exception {
    
    try {
      
      // build facebook's GET request
      RestRequestGetQuery getQuery =
        new RestRequestGetQuery("access_token", facebookAccessToken);
      
      // send auth request to Facebook
      URLConnection urlConnection =
        RestInl.restCall(kFacebookGraphApiAppLink, getQuery);
      
      // check if response's status is OK (200)
      if (RestInl.getResponseStatus(urlConnection) !=
          HttpURLConnection.HTTP_OK) {
        
        throw new CodeException(
          "Error while communicating with facebook, fb_access_token ["
          + facebookAccessToken
          + "]",
          ExceptionClass.COMMUNICATION);
      }
      
      // get Facebook's response content (App id)
      ResponseFacebookAuth responseFacebookAuth =
        (ResponseFacebookAuth)RestInl.getRestResponseJson(
          new ResponseFacebookAuth(),
          urlConnection);
      
      // null app id?
      if (responseFacebookAuth.id == null) {
        
        throw new BadRequestException(
          "Facebook Auth returned null app id",
          ExceptionClass.AUTHENTICATION);
      }
      
      // correct app id? --> SUCCESS
      if (responseFacebookAuth.id.compareTo(facebookAppId) == 0) {
        
        return;
      }
      
      // wrong app id
      throw new CodeException(
        "Facebook Auth [wrong app id], got ["
        + responseFacebookAuth.id
        + "] and expected ["
        + facebookAppId
        + "]",
        ExceptionClass.AUTHENTICATION);
    } catch (Exception e) {
      
      throw new CodeException(
        "Error while communicating with facebook, fb_access_token ["
        + facebookAccessToken
        + "]",
        ExceptionClass.COMMUNICATION);
    }
  }
}
