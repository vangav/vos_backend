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

package com.vangav.backend.cassandra;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * CassandraNode represents a single Cassandra node
 * e.g.: in a multi-deployment topology with 4 Cassandra nodes,
 *         each of those 4 nodes will be represented by an Object of this class
 */
public class CassandraNode {

  private final String name;
  private final String ip;
  
  /**
   * Constructor CassandraNode
   * @param name
   * @param ip
   * @return new CassandraNode Object
   * @throws Exception
   */
  public CassandraNode (
    final String name,
    final String ip) throws Exception {
    
    this.name = name;
    this.ip = ip;
  }
  
  /**
   * getName
   * @return this node's name
   */
  public final String getName () {
    
    return this.name;
  }
  
  /**
   * getIp
   * @return this node's name
   */
  public final String getIp () {
    
    return this.ip;
  }
  
  @Override
  public String toString () {
    
    return
      "Cassandra node, name ["
      + this.name
      + "] ip ["
      + this.ip
      + "]";
  }
}
