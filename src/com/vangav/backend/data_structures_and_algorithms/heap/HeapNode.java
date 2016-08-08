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

package com.vangav.backend.data_structures_and_algorithms.heap;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * HeapNode represents the parent class for any heap node
 * */
public abstract class HeapNode {

  /**
   * setKey
   * @param key: new node's value
   */
  public abstract void setValue (double key);
  
  /**
   * getKey
   * @return node's value
   */
  public abstract double getValue ();
}
