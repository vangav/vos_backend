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

package com.vangav.backend.security.authentication.o_auth_2;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * OAuth2Tokens represents a set of OAuth2 Tokens:
 *   - Authorization Code
 *   - Access Token
 *   - Refresh Token
 */
public class OAuth2Tokens {

  private final String authorizationCode;
  private final String accessToken;
  private final String refreshToken;
  
  /**
   * Generate Constructor
   * @return new OAuth2Tokens Object with newly generated tokens
   * @throws Exception
   */
  public OAuth2Tokens () throws Exception {

    OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator() );
    
    this.authorizationCode = oauthIssuerImpl.authorizationCode();
    this.accessToken = oauthIssuerImpl.accessToken();
    this.refreshToken = oauthIssuerImpl.refreshToken();
  }
  
  /**
   * Constructor OAuth2Tokens
   * @param authorizationCode
   * @param accessToken
   * @param refreshtoken
   * @return new OAuth2Tokens Object
   */
  public OAuth2Tokens (
    final String authorizationCode,
    final String accessToken,
    final String refreshtoken) {
    
    this.authorizationCode = authorizationCode;
    this.accessToken = accessToken;
    this.refreshToken = refreshtoken;
  }
  
  /**
   * Copy Constructor OAuth2Tokens
   * @param oAuth2Tokens
   * @return new OAuth2Tokens Object equivalent to param oAuth2Tokens
   */
  public OAuth2Tokens (
    final OAuth2Tokens oAuth2Tokens) {
    
    this.authorizationCode = oAuth2Tokens.authorizationCode;
    this.accessToken = oAuth2Tokens.accessToken;
    this.refreshToken = oAuth2Tokens.refreshToken;
  }
  
  /**
   * getAuthorizationCode
   * @return authorization code
   */
  public final String getAuthorizationCode () {
    
    return this.authorizationCode;
  }
  
  /**
   * getAccessToken
   * @return access token
   */
  public final String getAccessToken () {
    
    return this.accessToken;
  }
  
  /**
   * getRefreshToken
   * @return refresh token
   */
  public final String getRefreshToken () {
    
    return this.refreshToken;
  }
  
  @Override
  public String toString () {
    
    return
      "OAuth2Tokens:\n"
      + "Authorization Code ["
      + this.authorizationCode
      + "]\n"
      + "Access Token ["
      + this.accessToken
      + "]\n"
      + "Refresh Token ["
      + this.refreshToken
      + "]";
  }
}
