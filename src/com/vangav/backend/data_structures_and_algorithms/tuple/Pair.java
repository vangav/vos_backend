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

package com.vangav.backend.data_structures_and_algorithms.tuple;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Pair - can act as a hash key (e.g.: in a hash map)
 * */
public class Pair<A, B> {

  private A first;
  private B second;
  
  /**
   * Constructor Pair
   * @param first
   * @param second
   * @return new Pair Object
   */
  public Pair (A first, B second) {
    
    this.first = first;
    this.second = second;
  }
  
  /**
   * getFirst
   * @return pair's first
   */
  public A getFirst () {
    
    return this.first;
  }
  
  /**
   * getSecond
   * @return pair's second
   */
  public B getSecond () {
    
    return this.second;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((first == null) ? 0 : first.hashCode());
    result = prime * result + ((second == null) ? 0 : second.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof Pair)) {
      return false;
    }
    Pair<?, ?> other = (Pair<?, ?>) obj;
    if (first == null) {
      if (other.first != null) {
        return false;
      }
    } else if (!first.equals(other.first)) {
      return false;
    }
    if (second == null) {
      if (other.second != null) {
        return false;
      }
    } else if (!second.equals(other.second)) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString () {
    
    return
      "Pair ("
      + this.first.toString()
      + ", "
      + this.second.toString()
      + ")";
  }
}
