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

package com.vangav.backend.cassandra.keyspaces.dispatch_message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vangav.backend.cassandra.keyspaces.Query;
import com.vangav.backend.content.formatting.SerializationInl;
import com.vangav.backend.dispatcher.DispatchMessage;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * QueryDispatchable represents a dispatchable version of Query in the form
 *   of a JSON Object
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryDispatchable extends DispatchMessage {
  
  public QueryDispatchable () {
    
  }

  @Override
  @JsonIgnore
  protected String getName () throws Exception {
    
    return "QueryDispatchable";
  }
  
  @Override
  @JsonIgnore
  protected DispatchMessage getThis () throws Exception {
    
    return this;
  }
  
  @Override
  @JsonIgnore
  public void execute () throws Exception {
    
    WorkerQuery workerQuery = WorkerQuery.fromQueryDispatchable(this);
    
    WorkerExecutor.i().executeAsync(workerQuery);
  }
  
  /**
   * Constructor QueryDispatchable
   * initializes through serializing a Query
   * @param query
   * @return new QueryDispatchable Object
   * @throws Exception
   */
  @JsonIgnore
  public QueryDispatchable (Query query) throws Exception {
    
    this.type = "cassandra_query";

    this.serialized_message =
      SerializationInl.serializeObject(new WorkerQuery(query) );
  }
}
