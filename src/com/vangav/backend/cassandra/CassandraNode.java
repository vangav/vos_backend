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
