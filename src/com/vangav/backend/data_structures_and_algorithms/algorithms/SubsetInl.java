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

package com.vangav.backend.data_structures_and_algorithms.algorithms;

import java.util.Set;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * SubsetInl: inline static methods for data structures subset-related
 *   operations
 * */
public class SubsetInl {

  // disable default instantiation
  private SubsetInl () {}
  
  /**
   * isSubset
   * checks if a Set is a subset of another Set
   * @param set: main Set
   * @param subset: check is is Set is a subset of "set"
   * @return true if subset is a sub-set of set and false otherwise
   * @throws Exception
   */
  public static <T> boolean isSubset (
    Set<T> set,
    Set<T> subset) throws Exception {
    
    if (subset != null && set == null) {
      
      return false;
    }
    
    if (subset == null || set == null) {
      
      return true;
    }
    
    if (subset.isEmpty() == false && set.isEmpty() == true) {
      
      return false;
    }
    
    if (subset.isEmpty() == true || set.isEmpty() == true) {
      
      return true;
    }
    
    for (T t : subset) {
      
      if (set.contains(t) == false) {
        
        return false;
      }
    }
    
    return true;
  }
}
