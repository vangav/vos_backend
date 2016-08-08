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

package com.vangav.backend.ids;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * UuidOperationsInl is for inline static methods uuid-related
 * */
public class UuidOperationsInl {

  // disable default instantiation
  private UuidOperationsInl () {}
  
  /**
   * strListToUuidArr
   * @param list of UUID-Strings
   * @return corresponding UUID array
   * @throws Exception
   */
  public static UUID[] strListToUuidArr (
    ArrayList<String> list) throws Exception {
    
    if (list == null) {
      
      return new UUID[0];
    }
    
    UUID[] result = new UUID[list.size()];
    
    for (int i = 0; i < list.size(); i ++) {
      
      result[i] = UUID.fromString(list.get(i) );
    }
    
    return result;
  }
  
  /**
   * uuidListToStrArr
   * @param list of UUIDs
   * @return corresponding UUID-Strings array
   * @throws Exception
   */
  public static String[] uuidListToStrArr (
    ArrayList<UUID> list) throws Exception {
    
    if (list == null) {
      
      return new String[0];
    }
    
    String[] result = new String[list.size()];
    
    for (int i = 0; i < list.size(); i ++) {
      
      result[i] = list.get(i).toString();
    }
    
    return result;
  }
}
