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

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * SubtractInl: inline static methods for duplicate-related operations on
 *   data structures
 * */
public class DuplicatesInl {

  // disable default instantiation
  private DuplicatesInl () {}
  
  /**
   * hasDuplicates
   * @param array to check
   * @return true if the array has duplicates and false otherwise
   * @throws Exception
   */
  public static <T> boolean hasDuplicates (
    T[] array) throws Exception {
    
    if (array == null) {
      
      return false;
    }
    
    HashSet<T> set = new HashSet<T>();
    
    for (int i = 0; i < array.length; i ++) {
      
      if (set.contains(array[i]) == true) {
        
        return true;
      }
      
      set.add(array[i]);
    }
      
    return false;
  }
  
  /**
   * hasDuplicates
   * @param array to check
   * @return true if the array has duplicates and false otherwise
   * @throws Exception
   */
  public static <T> boolean hasDuplicates (
    ArrayList<T> list) throws Exception {
    
    if (list == null) {
      
      return false;
    }
    
    HashSet<T> set = new HashSet<T>();
    
    for (int i = 0; i < list.size(); i ++) {
      
      if (set.contains(list.get(i) ) == true) {
        
        return true;
      }
      
      set.add(list.get(i) );
    }
      
    return false;
  }

  /**
   * removeDuplicates
   * @param list to remove duplicates from
   * @return list with removed duplicates while order is kept
   * @throws Exception
   */
  public static <T> ArrayList<T> removeDuplicates (
    ArrayList<T> list) throws Exception {
    
    if (list == null) {
      
      return list;
    }
    
    ArrayList<T> result = new ArrayList<T>();
    
    HashSet<T> set = new HashSet<T>();
    
    for (int i = 0; i < list.size(); i ++) {
      
      if (set.contains(list.get(i) ) == false) {
        
        result.add(list.get(i) );
      }
      
      set.add(list.get(i) );
    }
    
    return result;
  }
}
