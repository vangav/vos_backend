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

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.vangav.backend.cassandra.Cassandra;
import com.vangav.backend.cassandra.CassandraProperties;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Query represents a cassandra table's query
 * */
public class Query {

  public enum Type {
    
    INSERT,
    SELECT,
    UPDATE,
    DELETE
  }
  private final Type type;

  private final ConsistencyLevel consistencyLevel;

  private final String description;
  private final String name;
  private final String preparedStatementString;
  private final PreparedStatement preparedStatement;
  private final Object[] binds;
  
  /**
   * Constructor Query
   * @param description
   * @param name
   * @param preparedStatementString
   * @return new Query Object
   * @throws Exception
   */
  public Query (
    final String description,
    final String name,
    final String preparedStatementString) throws Exception {
    
    this(description, name, preparedStatementString, (Object[])null);
  }
  
  /**
   * Constructor Query
   * @param query
   * @param binds
   * @return new Query Object
   * @throws Exception
   */
  public Query (
    final Query query,
    Object... binds) throws Exception {
    
    this(query.description, query.name, query.preparedStatementString, binds);
  }
  
  /**
   * Constructor Query
   * @param description
   * @param name
   * @param preparedStatementString
   * @param binds
   * @return new Query Object
   * @throws Exception
   */
  public Query (
    final String description,
    final String name,
    final String preparedStatementString,
    Object... binds) throws Exception {
    
    String psString = preparedStatementString.trim();
    String[] psSplit = psString.split(" ", 2);
    this.type = Type.valueOf(psSplit[0].toUpperCase() );
    
    ArgumentsInl.checkNotNull(
      "Query Type",
      this.type,
      ExceptionType.CODE_EXCEPTION);
    
    this.description = description;
    this.name = name;
    this.preparedStatementString = preparedStatementString;
    
    this.preparedStatement =
      Cassandra.i().makePreparedStatementSync(this.preparedStatementString);
    
    if (this.type == Type.SELECT) {
      
      this.preparedStatement.setConsistencyLevel(
        ConsistencyLevel.valueOf(
          CassandraProperties.i().getStringPropterty(
            CassandraProperties.kReadConsistencyLevel) ) );
      this.consistencyLevel =
        ConsistencyLevel.valueOf(
          CassandraProperties.i().getStringPropterty(
            CassandraProperties.kReadConsistencyLevel) );
    } else {
      
      this.preparedStatement.setConsistencyLevel(
        ConsistencyLevel.valueOf(
          CassandraProperties.i().getStringPropterty(
            CassandraProperties.kWriteConsistencyLevel) ) );
      this.consistencyLevel =
        ConsistencyLevel.valueOf(
          CassandraProperties.i().getStringPropterty(
            CassandraProperties.kWriteConsistencyLevel) );
    }
    
    this.binds = binds;
  }
  
  /**
   * getType
   * @return Query's type
   */
  public final Type getType () {
    
    return this.type;
  }
  
  /**
   * getConsistencyLevel
   * @return Query's consistency level
   */
  public final ConsistencyLevel getConsistencyLevel () {
    
    return this.consistencyLevel;
  }
  
  /**
   * getDescription
   * @return Query's description
   */
  public final String getDescription () {
    
    return this.description;
  }
  
  /**
   * getName
   * @return Query's name
   */
  public final String getName () {
    
    return this.name;
  }
  
  /**
   * getPreparedStatementString
   * @return Query's string representation of the prepared statement
   */
  public final String getPreparedStatementString () {
    
    return this.preparedStatementString;
  }
  
  /**
   * getPreparedStatement
   * @return Query's prepared statement
   */
  public PreparedStatement getPreparedStatement () {
    
    return this.preparedStatement;
  }
  
  /**
   * getConsistencyLevel
   * @return Query's binds
   */
  public Object[] getBinds () {
    
    return this.binds;
  }
  
  /**
   * getBoundStatement
   * @param binds
   * @return BoundStatement of this Query's PreparedStatement binded to
   *           param binds
   * @throws Exception
   */
  public BoundStatement getBoundStatement (Object... binds) throws Exception {
    
    return (BoundStatement)this.preparedStatement.bind(binds);
  }
  
  /**
   * getBoundStatement
   * @return BoundStatement of this Query's PreparedStatement binded to
   *           member variable binds
   * @throws Exception
   */
  public BoundStatement getBoundStatement () throws Exception {
    
    if (this.binds != null) {
    
      return (BoundStatement)this.preparedStatement.bind(this.binds);
    } else {
    
      return (BoundStatement)this.preparedStatement.bind();
    }
  }
  
  /**
   * executeAsync
   * executes this Query asynchronously
   * @param binds
   * @return a Future Object that will be set to the query's result after
   *           the query execution is finished
   * @throws Exception
   */
  public ResultSetFuture executeAsync (Object... binds) throws Exception {
    
    return
      (ResultSetFuture)Cassandra.i().executeAsync(
        this.getBoundStatement(binds) );
  }
  
  /**
   * executeAsync
   * executes this Query asynchronously
   * @return a Future Object that will be set to the query's result after
   *           the query execution is finished
   * @throws Exception
   */
  public ResultSetFuture executeAsync () throws Exception {
    
    if (this.binds != null) {
    
      return
        (ResultSetFuture)Cassandra.i().executeAsync(
          this.getBoundStatement(this.binds) );
    } else {
    
      return
        (ResultSetFuture)Cassandra.i().executeAsync(this.getBoundStatement() );
    }
  }
  
  /**
   * executeSync
   * executes this Query synchronously
   * @param binds
   * @return waits till the query execution is done then returns a ResultSet
   *           Object with this Query's result
   * @throws Exception
   */
  public ResultSet executeSync (Object... binds) throws Exception {
    
    return
      (ResultSet)Cassandra.i().executeSync(this.getBoundStatement(binds) );
  }
  
  /**
   * executeSync
   * executes this Query synchronously
   * @return waits till the query execution is done then returns a ResultSet
   *           Object with this Query's result
   * @throws Exception
   */
  public ResultSet executeSync () throws Exception {
    
    if (this.binds != null) {
    
      return
        (ResultSet)Cassandra.i().executeSync(
          this.getBoundStatement(this.binds) );
    } else {
    
      return (ResultSet)Cassandra.i().executeSync(this.getBoundStatement() );
    }
  }
  
  @Override
  public String toString () {
    
    return
      "Query:"
      + "\nDescription ["
      + this.description
      + "]\nName ["
      + this.name
      + "]\nPrepared Statement ["
      + this.preparedStatementString
      + "]";
  }
}
