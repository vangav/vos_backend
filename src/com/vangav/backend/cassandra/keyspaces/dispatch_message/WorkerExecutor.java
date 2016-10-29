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

package com.vangav.backend.cassandra.keyspaces.dispatch_message;

import java.util.HashMap;
import java.util.Map;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.vangav.backend.cassandra.Cassandra;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * WorkerExecutor manages the execution of cassandra queries in
 *   a Vangav Worker instnce
 * */
public class WorkerExecutor {

  // prepared statement string -> PreparedStatement Object
  private Map<String, PreparedStatement> preparedStatements;
  
  /**
   * Constructor WorkerExecutor
   * @return new WorkerExecutor Object
   * @throws Exception
   */
  private WorkerExecutor () throws Exception {
    
    this.preparedStatements = new HashMap<String, PreparedStatement>();
  }
  
  private static WorkerExecutor instance = null;
  /**
   * i
   * singleton instance method
   * @return static WorkerExecutor instance
   * @throws Exception
   */
  public static WorkerExecutor i () throws Exception {
    
    if (instance == null) {
      
      instance = new WorkerExecutor();
    }
    
    return instance;
  }

  /**
   * prepare
   * prepares param workerQuery if it's not already prepared
   * @param workerQuery
   * @throws Exception
   */
  private void prepare (
    WorkerQuery workerQuery) throws Exception {
    
    if (this.preparedStatements.containsKey(
          workerQuery.getPreparedStatementString() ) == true) {
      
      return;
    }

    PreparedStatement preparedStatement =
      Cassandra.i().makePreparedStatementSync(
        workerQuery.getPreparedStatementString() );
    
    preparedStatement.setConsistencyLevel(workerQuery.getConsistencyLevel() );
    
    this.preparedStatements.put(
      workerQuery.getPreparedStatementString(),
      preparedStatement);
  }
  
  /**
   * executeAsync
   * executes param workerQuery asynchronously
   * @param workerQuery
   * @return ResultSetFuture holding the future ResultSet Object resulting from
   *           executing param workerQuery
   * @throws Exception
   */
  public ResultSetFuture executeAsync (
    WorkerQuery workerQuery) throws Exception {
    
    this.prepare(workerQuery);
    
    BoundStatement boundStatement;
    
    if (workerQuery.getBinds() != null) {
     
      boundStatement =
        this.preparedStatements.get(
          workerQuery.getPreparedStatementString() ).bind(
            workerQuery.getBinds() );
    } else {
     
      boundStatement =
        this.preparedStatements.get(
          workerQuery.getPreparedStatementString() ).bind();
    }
    
    return Cassandra.i().executeAsync(boundStatement);
  }
  
  /**
   * executeSync
   * executes param workerQuery synchronously
   * @param workerQuery
   * @return ResultSet Object resulting from executing param workerQuery
   * @throws Exception
   */
  public ResultSet executeSync (
    WorkerQuery workerQuery) throws Exception {
    
    this.prepare(workerQuery);
    
    BoundStatement boundStatement;
    
    if (workerQuery.getBinds() != null) {
     
      boundStatement =
        this.preparedStatements.get(
          workerQuery.getPreparedStatementString() ).bind(
            workerQuery.getBinds() );
    } else {
     
      boundStatement =
        this.preparedStatements.get(
          workerQuery.getPreparedStatementString() ).bind();
    }
    
    return Cassandra.i().executeSync(boundStatement);
  }
}
