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

package com.vangav.backend.security.authentication.transaction_tokens;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.vangav.backend.content.generation.RandomGeneratorInl;
import com.vangav.backend.data_structures_and_algorithms.tuple.Pair;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;
import com.vangav.backend.security.authentication.transaction_tokens.json.TransactionTokensJson;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * TransactionTokensGeneratorInl has inline static methods for generating
 *   transaction token pairs
 * Pairs of <server token, client token> are generated where both the server
 *   and authentic client sides keep a copy of those pairs. A client token can
 *   be viewed as a one-time-use password. Upon authentication (past the first
 *   layer authentication using a user-password) the server sends an unused
 *   server token and the client replies with the corresponding client token.
 *   Upon exhausting all tokens, the server generates a new set of tokens and
 *   securely give the client a copy of those newly issued tokens.
 * Transaction Tokens are used as a second layer of authentication usually
 *   for accessing highly sensitive tools/information like:
 *   - money transfer (some online banking systems use a form of transaction
 *       tokens called TAN Transaction Authentication Number for online
 *       transfer operations)
 */
public class TransactionTokensGeneratorInl {
  
  // disable default instantiation
  private TransactionTokensGeneratorInl () {}

  /**
   * TokenType
   * Special characters are:
   *   ! "#$%&'()*+,-./:;<=>?@[\]^_`{|}~
   * */
  public enum TokenType {
    
    UUID,
    DIGITS_8,
    DIGITS_16,
    ALPHA_NUMERIC_8,
    ALPHA_NUMERIC_16,
    ALPHA_NUMERIC_32,
    ALPHA_NUMERIC_64,
    ALPHA_NUMERIC_SPECIAL_CHARACTERS_8,
    ALPHA_NUMERIC_SPECIAL_CHARACTERS_16,
    ALPHA_NUMERIC_SPECIAL_CHARACTERS_32,
    ALPHA_NUMERIC_SPECIAL_CHARACTERS_64
  }
  
  /**
   * generateTransactionTokensJson
   * @param tokenType
   * @param tokensCount
   * @return TransactionTokensJson JSON Object containing (pram tokensCount)
   *           randomly generated Transaction Token Pairs with
   *           (param tokenType type)
   * @throws Exception
   */
  public static TransactionTokensJson generateTransactionTokensJson (
    final TokenType tokenType,
    final int tokensCount) throws Exception {
    
    return
      new TransactionTokensJson(
        generateTransactionTokensMap(tokenType, tokensCount) );
  }
  
  /**
   * generateTransactionTokensMap
   * @param tokenType
   * @param tokensCount
   * @return a <String, String> Map containing (pram tokensCount)
   *           randomly generated Transaction Token Pairs with
   *           (param tokenType type)
   * @throws Exception
   */
  public static Map<String, String> generateTransactionTokensMap (
    final TokenType tokenType,
    final int tokensCount) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "Token Type",
      tokenType,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkIntGreaterThanOrEqual(
      "Tokens Count",
      tokensCount,
      1,
      ExceptionType.CODE_EXCEPTION);
    
    Map<String, String> result = new HashMap<String, String>();
    
    Pair<String, String> currTokenPair;
    
    for (int i = 0; i < tokensCount; i ++) {
      
      currTokenPair = generateTransactionTokensPair(tokenType);
      
      result.put(currTokenPair.getFirst(), currTokenPair.getSecond() );
    }
    
    return result;
  }
  
  /**
   * generateTransactionTokensPair
   * @param tokenType
   * @return a pair of Transaction Tokens (server token and client token) with
   *           param tokenType type
   * @throws Exception
   */
  private static Pair<String, String> generateTransactionTokensPair (
    final TokenType tokenType) throws Exception {
    
    switch (tokenType) {
      
      case UUID: {
        return new Pair<String, String> (
          UUID.randomUUID().toString(),
          UUID.randomUUID().toString() );
      }
      case DIGITS_8: {
        return new Pair<String, String> (
          RandomGeneratorInl.generateRandomNumericString(8),
          RandomGeneratorInl.generateRandomNumericString(8) );
      }
      case DIGITS_16: {
        return new Pair<String, String> (
          RandomGeneratorInl.generateRandomNumericString(16),
          RandomGeneratorInl.generateRandomNumericString(16) );
      }
      case ALPHA_NUMERIC_8: {
        return new Pair<String, String> (
          RandomGeneratorInl.generateRandomAlphaNumericString(8),
          RandomGeneratorInl.generateRandomAlphaNumericString(8) );
      }
      case ALPHA_NUMERIC_16: {
        return new Pair<String, String> (
          RandomGeneratorInl.generateRandomAlphaNumericString(16),
          RandomGeneratorInl.generateRandomAlphaNumericString(16) );
      }
      case ALPHA_NUMERIC_32: {
        return new Pair<String, String> (
          RandomGeneratorInl.generateRandomAlphaNumericString(32),
          RandomGeneratorInl.generateRandomAlphaNumericString(32) );
      }
      case ALPHA_NUMERIC_64: {
        return new Pair<String, String> (
          RandomGeneratorInl.generateRandomAlphaNumericString(64),
          RandomGeneratorInl.generateRandomAlphaNumericString(64) );
      }
      case ALPHA_NUMERIC_SPECIAL_CHARACTERS_8: {
        return new Pair<String, String> (
          RandomGeneratorInl
            .generateRandomAlphaNumericSpecialCharactersString(8),
          RandomGeneratorInl
            .generateRandomAlphaNumericSpecialCharactersString(8) );
      }
      case ALPHA_NUMERIC_SPECIAL_CHARACTERS_16: {
        return new Pair<String, String> (
          RandomGeneratorInl
            .generateRandomAlphaNumericSpecialCharactersString(16),
          RandomGeneratorInl
            .generateRandomAlphaNumericSpecialCharactersString(16) );
      }
      case ALPHA_NUMERIC_SPECIAL_CHARACTERS_32: {
        return new Pair<String, String> (
          RandomGeneratorInl
            .generateRandomAlphaNumericSpecialCharactersString(32),
          RandomGeneratorInl
            .generateRandomAlphaNumericSpecialCharactersString(32) );
      }
      case ALPHA_NUMERIC_SPECIAL_CHARACTERS_64: {
        return new Pair<String, String> (
          RandomGeneratorInl
            .generateRandomAlphaNumericSpecialCharactersString(64),
          RandomGeneratorInl
            .generateRandomAlphaNumericSpecialCharactersString(64) );
      }
      
      default:
        throw new CodeException(
          "Unhandled TokenType ["
          + tokenType.toString()
          + "]",
          ExceptionClass.TYPE);
    }
  }
}
