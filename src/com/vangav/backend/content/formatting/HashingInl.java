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

package com.vangav.backend.content.formatting;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * HashingInl: static inline methods for hashing objects
 * */
public class HashingInl {

  // disable default instantiation
  private HashingInl () {}

  /**
   * getHashCode
   * hashes an Object or more
   * @param objects: objects to hash
   * @return string representation of the hash code
   * @throws Exception
   */
  public static String getHashCode (
    Object... objects) throws Exception {
    
    String concatObjects = "";
    
    for (Object object : objects) {
      
      concatObjects += object.toString();
    }
    
    return "" + concatObjects.hashCode();
  }
}
