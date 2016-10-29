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

import java.io.Serializable;
import java.util.Arrays;

import com.datastax.driver.core.ConsistencyLevel;
import com.vangav.backend.cassandra.keyspaces.Query;
import com.vangav.backend.content.formatting.SerializationInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * WorkerQuery represents a minimal version of a cassandra tabler's query
 * NOTE: this variant of Query class is intended for use by the
 *         Dispatcher/Worker
 * */
public class WorkerQuery implements Serializable {

  /**
   * Generated serial version ID
   */
  private static final long serialVersionUID = -4103653338520431583L;

  private final ConsistencyLevel consistencyLevel;
  private final String name;
  private final String preparedStatementString;
  private final Object[] binds;
  
  /**
   * Constructor WorkerQuery
   * @param query
   * @return new WorkerQuery Object
   * @throws Exception
   */
  public WorkerQuery (
    Query query) throws Exception {
    
    this.consistencyLevel = query.getConsistencyLevel();
    this.name = query.getName();
    this.preparedStatementString = query.getPreparedStatementString();
    this.binds = query.getBinds();
  }
  
  /**
   * fromQueryDispatchable
   * @param queryDispatchable
   * @return new WorkerQuery Object reflecting param queryDispatchable
   * @throws Exception
   */
  public static WorkerQuery fromQueryDispatchable (
    QueryDispatchable queryDispatchable) throws Exception {
    
    return
      SerializationInl.<WorkerQuery>deserializeObject(
        queryDispatchable.serialized_message);
  }
  
  /**
   * getConsistencyLevel
   * @return consistency level
   * @throws Exception
   */
  public final ConsistencyLevel getConsistencyLevel () throws Exception {
    
    return this.consistencyLevel;
  }
  
  /**
   * getName
   * @return name
   * @throws Exception
   */
  public final String getName () throws Exception {
    
    return this.name;
  }
  
  /**
   * getPreparedStatementString
   * @return prepared statement string
   * @throws Exception
   */
  public final String getPreparedStatementString () throws Exception {
    
    return this.preparedStatementString;
  }
  
  /**
   * getBinds
   * @return binds
   * @throws Exception
   */
  public final Object[] getBinds () throws Exception {
    
    return this.binds;
  }
  
  @Override
  public String toString () {
    
    return
      "Worker Query:\n"
      + "Consistency level ["
      + this.consistencyLevel.toString()
      + "]\nName ["
      + this.name
      + "]\nPrepared statement ["
      + this.preparedStatementString
      + "]\nBinds ["
      + Arrays.toString(this.binds)
      + "]";
  }
}
