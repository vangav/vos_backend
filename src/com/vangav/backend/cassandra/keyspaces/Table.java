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
