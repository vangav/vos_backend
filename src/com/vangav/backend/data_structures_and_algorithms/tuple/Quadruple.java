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

package com.vangav.backend.data_structures_and_algorithms.tuple;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Quadruple - can act as a hash key (e.g.: in a hash map)
 * */
public class Quadruple<A, B, C, D> {

  private A first;
  private B second;
  private C third;
  private D fourth;
  
  /**
   * Constructor Quadruple
   * @param first
   * @param second
   * @param third
   * @param fourth
   * @return new Quadruple Object
   */
  public Quadruple (A first, B second, C third, D fourth) {
    
    this.first = first;
    this.second = second;
    this.third = third;
    this.fourth = fourth;
  }
  
  /**
   * getFirst
   * @return quadruple's first
   */
  public A getFirst () {
    
    return this.first;
  }
  
  /**
   * getSecond
   * @return quadruple's second
   */
  public B getSecond () {
    
    return this.second;
  }
  
  /**
   * getThird
   * @return quadruple's third
   */
  public C getThird () {
    
    return this.third;
  }
  
  /**
   * getFourth
   * @return quadruple's fourth
   */
  public D getFourth () {
    
    return this.fourth;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((first == null) ? 0 : first.hashCode());
    result = prime * result + ((fourth == null) ? 0 : fourth.hashCode());
    result = prime * result + ((second == null) ? 0 : second.hashCode());
    result = prime * result + ((third == null) ? 0 : third.hashCode());
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
    if (!(obj instanceof Quadruple)) {
      return false;
    }
    Quadruple<?, ?, ?, ?> other = (Quadruple<?, ?, ?, ?>) obj;
    if (first == null) {
      if (other.first != null) {
        return false;
      }
    } else if (!first.equals(other.first)) {
      return false;
    }
    if (fourth == null) {
      if (other.fourth != null) {
        return false;
      }
    } else if (!fourth.equals(other.fourth)) {
      return false;
    }
    if (second == null) {
      if (other.second != null) {
        return false;
      }
    } else if (!second.equals(other.second)) {
      return false;
    }
    if (third == null) {
      if (other.third != null) {
        return false;
      }
    } else if (!third.equals(other.third)) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString () {
    
    return
      "Quadruple ("
      + this.first.toString()
      + ", "
      + this.second.toString()
      + ", "
      + this.third.toString()
      + ", "
      + this.fourth.toString()
      + ")";
  }
}
