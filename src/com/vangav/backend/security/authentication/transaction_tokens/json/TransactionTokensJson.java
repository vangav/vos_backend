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

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * TransactionTokensJson represents a set of Transaction Tokens
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionTokensJson {

  @JsonProperty
  public TransactionTokenJson[] transaction_tokens;
  
  /**
   * Constructor TransactionTokensJson
   * @param tokensMap
   * @return new TransactionTokensJson Object
   * @throws Exception
   */
  @JsonIgnore
  public TransactionTokensJson (
    Map<String, String> tokensMap) throws Exception {
    
    this.transaction_tokens = new TransactionTokenJson[tokensMap.size() ];
    
    int index = 0;
    for (String serverToken : tokensMap.keySet() ) {
      
      this.transaction_tokens[index] =
        new TransactionTokenJson(
          serverToken,
          tokensMap.get(serverToken) );
      
      index += 1;
    }
  }
  
  /**
   * fromJsonString
   * @param json
   * @return new TransactionTokensJson Object reflecting param json
   * @throws Exception
   */
  @JsonIgnore
  public static TransactionTokensJson fromJsonString (
    String json) throws Exception {
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    return objectMapper.readValue(json, TransactionTokensJson.class);
  }
  
  /**
   * getAsString
   * @return formatted json string reflecting this json Object
   * @throws Exception
   */
  @JsonIgnore
  public String getAsString () throws Exception {
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    return
      objectMapper.writerWithDefaultPrettyPrinter(
        ).writeValueAsString(this);
  }
  
  @Override
  public String toString () {
    
    try {
      
      return
        "Transaction Tokens:\n"
        + this.getAsString();
    } catch (Exception e) {
      
      return
        "Transaction Tokens: threw an Exception!";
    }
  }
}
