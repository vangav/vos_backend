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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.google.common.util.concurrent.ListenableFuture;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;
import com.vangav.backend.thread_pool.ThreadPool;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Cassandra represents the connection point to cassandra database, it handles:
 *   1- connecting to the database
 *   2- preparing/re-preparing prepared statements sync/async
 *   2- executing queries sync/async
 *   3- disconnecting
 * */
public class Cassandra {
  
  // properties
/**
   * topology represents the topology (ips) of the Cassandra nodes to which
   *   this instance should try to connect to
   * if there's a Cassandra node which is local to this node it will be give
   *   priority
   * having multiple nodes available for connection ensures that there isn't a
   *   single point of failure
   */
  private ArrayList<CassandraNode> topology;
  private int connectRetries;
  private int queryRetries;

  /**
   * preparedStatements keeps track of prepared statements that get prepared
   *   through this connection to reprepare them in case of a reconnection
   */
  private Set<String> preparedStatements;
  
  /**
   * Constructor Cassandra
   * @return new Cassandra Object
   * @throws Exception
   */
  private Cassandra () throws Exception {
    
    this.topology = CassandraProperties.i().getTopology();
    this.connectRetries =
      CassandraProperties.i().getIntProperty(
        CassandraProperties.kConnectionRetries);
    this.queryRetries =
      CassandraProperties.i().getIntProperty(
        CassandraProperties.kQueryRetries);
    
    this.preparedStatements = new HashSet<String>();
    
    this.initConnection();
  }

  private static Cassandra instance = null;
  
  /**
   * connect
   * OPTIONAL method
   * normally connection happens upon the first call to this class's singleton
   *   instance method (i), otherwise this method can be used to avoid any
   *   lag on executing the first query
   * @throws Exception
   */
  public static void connect () throws Exception {
    
    if (instance == null) {
      
      instance = new Cassandra();
    }
  }
  
  /**
   * i
   * @return static singleton instance of Cassandra
   * @throws Exception
   */
  public static Cassandra i () throws Exception {
    
    if (instance == null) {
      
      instance = new Cassandra();
    }
    
    return instance;
  }
  
  private Cluster cluster;
  private Session session;
  /**
   * reconnectFlag ensures that no more than one reconnection try happens
   *   at one time
   */
  private static AtomicBoolean reconnectFlag = new AtomicBoolean(false);
  
  /**
   * reconnect
   * reconnects then re-prepares prepared statements
   * @throws Exception
   */
  private synchronized void reconnect () throws Exception {
    
    // skip if a connection is already taking place
    if (reconnectFlag.compareAndSet(false, true) == false) {
      
      return;
    }

    this.initConnection();
    this.rePreparePreparedStatements();
  }
  
  /**
   * initConnection
   * tries connect to one of cassandra instances defined in CassandraProperties
   * @throws Exception
   */
  private synchronized void initConnection () throws Exception {
    
    for (int i = 0; i < this.topology.size(); i ++) {
      
      for (int j = 0; j < this.connectRetries; j ++) {
        
        try {
          
          this.cluster =
            Cluster.builder().addContactPoint(
              this.topology.get(i).getIp() ).build();
          this.cluster.init();
          this.session = this.cluster.connect();
          
          reconnectFlag.set(false);
          
          return;
        } catch (Exception e) {
          
          if (i == (this.topology.size() - 1) &&
              j == (this.connectRetries - 1) ) {
            
            reconnectFlag.set(false);
            
            throw e;
          }
        }
      }
    }
  }
  
  /**
   * rePreparePreparedStatements
   * re-prepares prepared statements
   * @throws Exception
   */
  private synchronized void rePreparePreparedStatements () throws Exception {
    
    this.makePreparedStatementsSync(
      this.preparedStatements.toArray(new String[0] ) );
  }

  /**
   * getSession
   * @return cassandra connection's session
   * @throws Exception
   */
  public final Session getSession () throws Exception {
    
    return this.session;
  }
  
  /**
   * makePreparedStatementAsync
   * @param preparedStatement
   * @return PreparedStatementFuture which is
   *           a ListenableFuture of a PreparedStatement which holds the future
   *           PreparedStatement representation of
   *           param String preparedStatement
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public ListenableFuture<PreparedStatement> makePreparedStatementAsync (
    String preparedStatement) throws Exception {
    
    return
      (ListenableFuture<PreparedStatement>)this.preparedStatement(
        preparedStatement,
        ExecType.ASYNC);
  }
  
  /**
   * makePreparedStatementsAsync
   * @param preparedStatements
   * @return an array list of PreparedStatementFuture Objects that hold the
   *           future PreparedStatement representations of param
   *           String... preparedStatements
   * @throws Exception
   */
  public ArrayList<ListenableFuture<PreparedStatement> >
    makePreparedStatementsAsync (
      String... preparedStatements) throws Exception {
    
    ArrayList<ListenableFuture<PreparedStatement> > result =
      new ArrayList<ListenableFuture<PreparedStatement> >();
    
    for (String preparedStatement : preparedStatements) {
      
      result.add(this.makePreparedStatementAsync(preparedStatement) );
    }
    
    return result;
  }
  
  /**
   * makePreparedStatementSync
   * blocks till statement is prepared
   * @param preparedStatement
   * @return PreparedStatement representation of param String preparedStatement
   * @throws Exception
   */
  public PreparedStatement makePreparedStatementSync (
    String preparedStatement) throws Exception {
    
    return
      (PreparedStatement)this.preparedStatement(
        preparedStatement,
        ExecType.SYNC);
  }
  
  /**
   * makePreparedStatementsSync
   * makes prepared statements synchronously
   * NOTE: using this method is more efficient than using
   *         makePreparedStatementSync (String preparedStatement) multiple
   *         times since this method makes all prepared statements
   *         asynchronously then waits for for them all at the end instead
   *         of waiting for each prepared statement to finish preparing before
   *         preparing the next one
   * @param preparedStatements
   * @return an array list of PreparedStatements representation of param
   *           String... preparedStatements
   * @throws Exception
   */
  public ArrayList<PreparedStatement> makePreparedStatementsSync (
    String... preparedStatements) throws Exception {
    
    ArrayList<ListenableFuture<PreparedStatement> > futures =
      this.makePreparedStatementsAsync(preparedStatements);
    
    return
      this.<PreparedStatement,
            ListenableFuture<PreparedStatement> >waitForFutures(futures);
  }
  
  /**
   * executeAsync
   * executes param query asynchronously
   * @param query
   * @return a Future Object that will be set to the query's result after
   *           the query execution is finished
   * @throws Exception
   */
  public ResultSetFuture executeAsync (Statement query) throws Exception {
    
    return (ResultSetFuture)this.execute(query, ExecType.ASYNC);
  }
  
  /**
   * executeAsync
   * executes param queries asynchronously
   * @param queries
   * @return an array list of  Future Objects that will be set to the queries'
   *           results after the queries execution is finished
   * @throws Exception
   */
  public ArrayList<ResultSetFuture> executeAsync (
    Statement... queries) throws Exception {
    
    ArrayList<ResultSetFuture> result = new ArrayList<ResultSetFuture>();
    
    for (Statement query : queries) {
      
      result.add(this.executeAsync(query) );
    }
    
    return result;
  }
  
  /**
   * executeSync
   * executes param query synchronously
   * @param query
   * @return waits till the query execution is done then returns a ResultSet
   *           Object with the param query's result
   * @throws Exception
   */
  public ResultSet executeSync (Statement query) throws Exception {
    
    return (ResultSet)this.execute(query, ExecType.SYNC);
  }

  /**
   * executeSync
   * executes param queries synchronously
   * NOTE: using this method is more efficient than using
   *         executeSync (Statement query) multiple times since this method
   *         executes all queries asynchronously then waits for for them all
   *         at the end instead of waiting for each query to finish before
   *         executing the next one
   * @param queries
   * @return waits till the queries execution is done then returns an array
   *           list of ResultSet Objects with the param queries' results
   * @throws Exception
   */
  public ArrayList<ResultSet> executeSync (
    Statement... queries) throws Exception {
    
    ArrayList<ResultSetFuture> futures = this.executeAsync(queries);
    
    return this.<ResultSet, ResultSetFuture>waitForFutures(futures);
  }
  
  private enum ExecType {

    ASYNC,
    SYNC
  }
  private static final Object mutex = new Object();
  private static AtomicInteger healthCount = new AtomicInteger(0);
  private static int kMinHealth = 0;
  private static int kHealthThreshold = 5;

  /**
   * preparedStatement
   * prepares param preparedStatement
   * @param query
   * @return if execType == ASYNC
   *           returns a Future Object that will be set to the
   *           PreparedStatement representation of param preparedStatement
   *         else if execType == SYNC
   *           waits till preparing param preparedStatement is complete
   *           then returns its PreparedStatement representation
   * @throws Exception
   */
  private Object preparedStatement (
    String preparedStatement,
    ExecType execType) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "Prepared statement",
      preparedStatement,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "Exec Type",
      execType,
      ExceptionType.CODE_EXCEPTION);
    
    while (reconnectFlag.get() == true) {
      
    }
    
    for (int i = 0; i < this.queryRetries; i ++) {
      
      try {
        
        ListenableFuture<PreparedStatement> result =
          this.session.prepareAsync(
            preparedStatement);

        if (healthCount.get() > kMinHealth) {
          
          healthCount.decrementAndGet();
        }
        
        if (execType == ExecType.SYNC) {
          
          ThreadPool.i().<PreparedStatement>executeInCassandraPool(result);
          
          PreparedStatement preparedStatementResult =
            result.get(
              kDefaultTimeout,
              kDefaultTimeunit);
          
          this.preparedStatements.add(preparedStatement);
          
          return preparedStatementResult;
        } else if (execType == ExecType.ASYNC) {
          
          this.preparedStatements.add(preparedStatement);
          
          return result;
        }
      } catch (NoHostAvailableException nhae) {
        
        try {
          
          if (reconnectFlag.get() == false) {
          
            synchronized (mutex) {
              
              if (healthCount.incrementAndGet() >= kHealthThreshold) {

                this.reconnect();
                
                healthCount.set(kMinHealth);
              }
            }
          }
        } catch (Exception e) {
          
          if (i == (this.queryRetries - 1) ) {
            
            throw e;
          }
        }
      } catch (Exception e) {
        
        if (i == (this.queryRetries - 1) ) {
          
          throw e;
        }
      }
    }
    
    throw new CodeException(
      "Couldn't prepare prepared statement ["
      + preparedStatement
      + "]",
      ExceptionClass.CASSANDRA);
  }
  /**
   * execute
   * executes param query
   * @param query
   * @return if execType == ASYNC
   *           returns a Future Object that will be set to the query's result
   *           after the query processing is finished
   *         else if execType == SYNC
   *           waits till the query processing is done then returns a ResultSet
   *           Object with the param the query's result
   * @throws Exception
   */
  private Object execute (
    Statement query,
    ExecType execType) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "Query",
      query,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "Exec Type",
      execType,
      ExceptionType.CODE_EXCEPTION);
    
    while (reconnectFlag.get() == true) {
      
    }
    
    for (int i = 0; i < this.queryRetries; i ++) {
      
      try {
        
        ResultSetFuture result = this.session.executeAsync(query);

        if (healthCount.get() > kMinHealth) {
          
          healthCount.decrementAndGet();
        }
        
        if (execType == ExecType.SYNC) {
          
          ThreadPool.i().<ResultSet>executeInCassandraPool(result);
          
          return
            result.getUninterruptibly(
              kDefaultTimeout,
              kDefaultTimeunit);
        } else if (execType == ExecType.ASYNC) {
          
          return result;
        }
      } catch (NoHostAvailableException nhae) {
        
        try {
          
          if (reconnectFlag.get() == false) {
          
            synchronized (mutex) {
              
              if (healthCount.incrementAndGet() >= kHealthThreshold) {

                this.reconnect();
                
                healthCount.set(kMinHealth);
              }
            }
          }
        } catch (Exception e) {
          
          if (i == (this.queryRetries - 1) ) {
            
            throw e;
          }
        }
      } catch (Exception e) {
        
        if (i == (this.queryRetries - 1) ) {
          
          throw e;
        }
      }
    }
    
    throw new CodeException(
      "Couldn't execute query statement ["
      + query.toString()
      + "]",
      ExceptionClass.CASSANDRA);
  }

  private static final long kDefaultTimeout = 20;
  private static final TimeUnit kDefaultTimeunit = TimeUnit.SECONDS;

  /**
   * waitForFutures
   * used when multiple sync operations are being processed, they get processed
   *   asynchronously then blocks till results are returned as a more efficient
   *   way than blocking for each process to complete sequentially
   * @param listenableFutures
   * @return An array list with future results
   * @throws Exception
   */
  private <T, P extends ListenableFuture<T> > ArrayList<T> waitForFutures (
    ArrayList<P> listenableFutures) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "Listenable futures",
      listenableFutures,
      ExceptionType.CODE_EXCEPTION);
    
    ArrayList<T> result = new ArrayList<T>();
    
    for (ListenableFuture<T> listenableFuture : listenableFutures) {

      for (int i = 0; i < this.queryRetries; i ++) {
        
        try {

          if (healthCount.get() > kMinHealth) {
            
            healthCount.decrementAndGet();
          }

          result.add((T)listenableFuture.get(kDefaultTimeout, kDefaultTimeunit) );
        } catch (NoHostAvailableException nhae) {
          
          try {
            
            if (reconnectFlag.get() == false) {
            
              synchronized (mutex) {
                
                if (healthCount.incrementAndGet() >= kHealthThreshold) {

                  this.reconnect();
                  
                  healthCount.set(kMinHealth);
                }
              }
            }
          } catch (Exception e) {
            
            if (i == (this.queryRetries - 1) ) {
              
              throw e;
            }
          }
        } catch (Exception e) {
          
          if (i == (this.queryRetries - 1) ) {
            
            throw e;
          }
        }
      }
    }

    return result;
  }
  
  /**
   * disconnect
   * call this method before terminating this instance to disconnect it from
   *   cassandra
   * @throws Exception
   */
  public synchronized void disconnect () throws Exception {
    
    for (int i = 0; i < this.connectRetries; i ++) {
      
      try {
        
        this.cluster.close();
      } catch (Exception e) {
        
        if (i == (this.connectRetries - 1) ) {
          
          throw e;
        }
      }
    }
  }
  
  @Override
  public String toString () {
    
    return
      "Cassandra:"
      + "\nCluster ["
      + this.cluster.toString()
      + "]\nSession ["
      + this.session.toString()
      + "]\nReconnect Flag ["
      + reconnectFlag.get()
      + "]\nTopoology ["
      + Arrays.toString(this.topology.toArray(new CassandraNode[0] ) )
      + "]\nConnect Retries ["
      + this.connectRetries
      + "]\nQuery Retries ["
      + this.queryRetries
      + "]\nHealth Count ["
      + healthCount.get()
      + "]";
  }
}
