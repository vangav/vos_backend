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

package com.vangav.backend.cassandra.keyspaces;

import java.util.HashMap;
import java.util.Map;

import com.vangav.backend.cassandra.keyspaces.dispatch_message.QueryDispatchable;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Table represents the parent class for all cassandra's tables
 */
public class Table {

  private final String keyspaceName;
  private final String tableName;
  private Map<String, Query> queries;
  
  /**
   * Constructor Table
   * @param keyspaceName
   * @param tableName
   * @param queries
   * @return new Table Object
   * @throws Exception
   */
  protected Table (
    final String keyspaceName,
    final String tableName,
    Query... queries) throws Exception {
    
    this.keyspaceName = keyspaceName;
    this.tableName = tableName;
    this.queries = new HashMap<String, Query>();
    
    for (Query query : queries) {
      
      this.queries.put(query.getName(), query);
    }
  }
  
  /**
   * getQuery
   * @param name
   * @return Query named param name
   * @throws Exception
   */
  final protected Query getQuery (String name) throws Exception {
    
    return this.queries.get(name);
  }
  
  /**
   * getQueryDispatchable
   * @param name
   * @return new QueryDispatchable Object containing the corresponding
   *           Query to param name
   * @throws Exception
   */
  final protected QueryDispatchable getQueryDispatchable (
    String name) throws Exception {
    
    return new QueryDispatchable(this.queries.get(name) );
  }
  
  /**
   * getQueryDispatchable
   * @param name
   * @param binds
   * @return new QueryDispatchable Object containing the corresponding
   *           Query to param name binded with param binds
   * @throws Exception
   */
  final protected QueryDispatchable getQueryDispatchable (
    String name,
    Object... binds) throws Exception {
    
    Query copyQuery = this.queries.get(name);
    Query query = new Query(copyQuery, binds);

    return new QueryDispatchable(query);
  }
  
  @Override
  public String toString () {
    
    return
      "Table:"
      + "\nKeyspace Name ["
      + this.keyspaceName
      + "]\nTable Name ["
      + this.tableName
      + "]\n  Queries:\n"
      + this.queries.toString();
  }
}
