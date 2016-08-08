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
