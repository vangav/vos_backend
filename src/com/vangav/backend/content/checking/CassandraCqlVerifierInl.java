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

package com.vangav.backend.content.checking;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * CassandraCqlVerifierInl has inline static methods for verifying the
 *   correctness of Cassandra Cql
 * */
public class CassandraCqlVerifierInl {

  // disable default instantiation
  private CassandraCqlVerifierInl () {}
  
  /**
   * verifyName
   * throws an exception if name is:
   * - null
   * - empty
   * - longer than 32 characters
   * - starts with something other than a lower case letter, an upper case
   *     letter or an under score
   * - has characters other than lower case letters, upper case letters,
   *     underscores and digits
   * @param name
   * @throws Exception
   */
  public static void verifyName (String name) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "Cassandra CQL name",
      name,
      ExceptionType.CODE_EXCEPTION);
    
    if (name.length() > 32) {
      
      throw new CodeException(
        "a name in Cassandra CQL must have at least one char and not more than"
        + "32 chars, current name["
        + name
        + "] has ["
        + name.length()
        + "] chars",
        ExceptionClass.FORMATTING);
    }
    
    if (CharVerifierInl.isOneOfChar(
          name.charAt(0),
          CharVerifierInl.CharType.LOWER_CASE,
          CharVerifierInl.CharType.UPPER_CASE,
          CharVerifierInl.CharType.UNDER_SCORE) == false) {
      
      throw new CodeException(
        "a name in Cassandra CQL must start with a lower case letter, "
        + "an upper case letter or an under score; invalid name ["
        + name
        + "] starts with ["
        + name.charAt(0)
        + "]",
        ExceptionClass.FORMATTING);
    }
    
    for (int i = 1; i < name.length(); i ++) {
      
      if (CharVerifierInl.isOneOfChar(
            name.charAt(i),
            CharVerifierInl.CharType.LOWER_CASE,
            CharVerifierInl.CharType.UPPER_CASE,
            CharVerifierInl.CharType.UNDER_SCORE,
            CharVerifierInl.CharType.DIGIT) == false) {
        
        throw new CodeException(
          "a name in Cassandra CQL can only contain a lower case letter, "
          + "an upper case letter, an under score or a digit; invalid name ["
          + name
          + "] has ["
          + name.charAt(i)
          + "]",
          ExceptionClass.FORMATTING);
      }
    }
  }
  
  /**
   * verifyColumnType
   * throws an exception is param type is an invalid Cassandra CQL column type
   * @param type
   * @throws Exception
   */
  public static void verifyColumnType (
    String type) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "Cassandra CQL column type",
      type,
      ExceptionType.CODE_EXCEPTION);
    
    StringVerifierInl.isOneOfString(
      "Cassandra CQL column type",
      type,
      true,
      "ascii",
      "bigint",
      "blob",
      "boolean",
      "counter",
      "decimal",
      "double",
      "float",
      "frozen",
      "inet",
      "int",
      "text",
      "timestamp",
      "timeuuid",
      "tuple",
      "uuid",
      "varchar",
      "varint",

      "list<ascii>",
      "list<bigint>",
      "list<boolean>",
      "list<decimal>",
      "list<double>",
      "list<float>",
      "list<inet>",
      "list<int>",
      "list<text>",
      "list<timestamp>",
      "list<timeuuid>",
      "list<uuid>",
      "list<varchar>",
      "list<varint>",

      "map<ascii>",
      "map<bigint>",
      "map<boolean>",
      "map<decimal>",
      "map<double>",
      "map<float>",
      "map<inet>",
      "map<int>",
      "map<text>",
      "map<timestamp>",
      "map<timeuuid>",
      "map<uuid>",
      "map<varchar>",
      "map<varint>",

      "set<ascii>",
      "set<bigint>",
      "set<boolean>",
      "set<decimal>",
      "set<double>",
      "set<float>",
      "set<inet>",
      "set<int>",
      "set<text>",
      "set<timestamp>",
      "set<timeuuid>",
      "set<uuid>",
      "set<varchar>",
      "set<varint>");
  }
  
  /**
   * verifyCaching
   * throws an exception if param cachingType is an invalid Cassandra CQL
   *   caching type
   * @param cachingType
   * @throws Exception
   */
  public static void verifyCaching (
    String cachingType) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "Cassandra CQL table caching type",
      cachingType,
      ExceptionType.CODE_EXCEPTION);
    
    StringVerifierInl.isOneOfString(
      "Cassandra CQL table caching type",
      cachingType,
      true,
      "ALL",
      "KEYS_ONLY",
      "NONE");
  }
  
  /**
   * verifyOrderByType
   * throws an exception if param orderByType is an invalid Cassandra CQL
   *   order by type
   * @param orderByType
   * @throws Exception
   */
  public static void verifyOrderByType (
    String orderByType) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "Cassandra CQL table order by type",
      orderByType,
      ExceptionType.CODE_EXCEPTION);
    
    StringVerifierInl.isOneOfString(
      "Cassandra CQL table order by type",
      orderByType,
      true,
      "ASC",
      "DESC");
  }
}
