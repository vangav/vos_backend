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

package com.vangav.backend.security.authentication.facebook;

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
        RestSyncInl.restCall(kFacebookGraphApiAppLink, getQuery);
      
      // check if response's status is OK (200)
      if (RestSyncInl.getResponseStatus(urlConnection) !=
          HttpURLConnection.HTTP_OK) {
        
        throw new CodeException(
          181,
          1,
          "Error while communicating with facebook, fb_access_token ["
          + facebookAccessToken
          + "]",
          ExceptionClass.COMMUNICATION);
      }
      
      // get Facebook's response content (App id)
      ResponseFacebookAuth responseFacebookAuth =
        (ResponseFacebookAuth)RestSyncInl.getRestResponseJson(
          new ResponseFacebookAuth(),
          urlConnection);
      
      // null app id?
      if (responseFacebookAuth.id == null) {
        
        throw new BadRequestException(
          181,
          2,
          "Facebook Auth returned null app id",
          ExceptionClass.AUTHENTICATION);
      }
      
      // correct app id? --> SUCCESS
      if (responseFacebookAuth.id.compareTo(facebookAppId) == 0) {
        
        return;
      }
      
      // wrong app id
      throw new CodeException(
        181,
        3,
        "Facebook Auth [wrong app id], got ["
        + responseFacebookAuth.id
        + "] and expected ["
        + facebookAppId
        + "]",
        ExceptionClass.AUTHENTICATION);
    } catch (BadRequestException bre) {
      
      throw bre;
    } catch (CodeException ce) {
      
      throw ce;
    } catch (Exception e) {
      
      throw new CodeException(
        181,
        4,
        "Error while communicating with facebook, fb_access_token ["
        + facebookAccessToken
        + "]",
        ExceptionClass.COMMUNICATION);
    }
  }
}
