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

package com.vangav.backend.security.authentication.transaction_tokens.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * TransactionTokenJson represents a pair of Transaction Tokens
 *   <server token, client token>
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionTokenJson {

  @JsonProperty
  public String server_token;
  @JsonProperty
  public String client_token;
  
  /**
   * Constructor TransactionTokenJson
   * @param serverToken
   * @param clientToken
   * @return new TransactionTokenJson Object
   * @throws Exception
   */
  @JsonIgnore
  public TransactionTokenJson (
    String serverToken,
    String clientToken) throws Exception {
    
    this.server_token = serverToken;
    this.client_token = clientToken;
  }
}
