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

package com.vangav.backend.data_structures_and_algorithms.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * SubtractInl: inline static methods for subtracting one container from
 *   another one
 * */
public class SubtractInl {

  // disable default instantiation
  private SubtractInl () {}

  /**
   * subtract
   * Set - Set
   * remove subtractor elements from original and return the a HashSet
   *   containing the remaining elements
   * @param original
   * @param subtractor
   * @return a HashSet containing original elements minus subtractor elements
   * @throws Exception
   */
  public static <T> HashSet<T> subtract (
    Set<T> original,
    Set<T> subtractor) throws Exception {
    
    HashSet<T> result = new HashSet<T>();
    
    if (original == null ||
        subtractor == null) {
      
      return result;
    }
    
    for (T t : original) {
      
      if (subtractor.contains(t) == false) {
        
        result.add(t);
      }
    }
    
    return result;
  }
  
  /**
   * subtract
   * Map - Map
   * remove subtractor elements from original and return the a HashMap
   *   containing the remaining elements
   * @param original
   * @param subtractor
   * @return a HashMap containing original elements minus subtractor elements
   * @throws Exception
   */
  public static <K, V> HashMap<K, V> subtract (
    Map<K, V> original,
    Map<K, V> subtractor) throws Exception {
    
    HashMap<K, V> result = new HashMap<K, V>();
    
    if (original == null ||
        subtractor == null) {
      
      return result;
    }
    
    for (K k : original.keySet() ) {
      
      if (subtractor.containsKey(k) == false) {
        
        result.put(
          k,
          original.get(k) );
      }
    }
    
    return result;
  }
  
  /**
   * subtract
   * Map - Set
   * remove subtractor elements from original and return the a HashMap
   *   containing the remaining elements
   * @param original
   * @param subtractor
   * @return a HashMap containing original elements minus subtractor elements
   * @throws Exception
   */
  public static <K, V> HashMap<K, V> subtract (
    HashMap<K, V> original,
    HashSet<K> subtractor) throws Exception {
    
    if (original == null ||
        subtractor == null) {
      
      return original;
    }
    
    HashMap<K, V> result = new HashMap<K, V>();
    
    for (K k : original.keySet() ) {
      
      if (subtractor.contains(k) == false) {
        
        result.put(
          k,
          original.get(k) );
      }
    }
    
    return result;
  }
}
