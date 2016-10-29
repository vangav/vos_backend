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

package com.vangav.backend.security.authentication.google;

import java.net.HttpURLConnection;
import java.net.URLConnection;

import com.vangav.backend.exceptions.BadRequestException;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.networks.rest_client.RestRequestGetQuery;
import com.vangav.backend.networks.rest_client.RestSyncInl;

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
