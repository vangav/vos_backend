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

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.vangav.backend.cassandra.keyspaces.Query;
import com.vangav.backend.cassandra.keyspaces.Table;
import com.vangav.backend.cassandra.keyspaces.dispatch_message.QueryDispatchable;

/**
 * IMPORTANT: this is just a NON-FUNCTIONAL example that shows how table code
 *              is structured upon generation using
 *              JavaClientGeneratorMain.java
 *              
 * GENERATED using JavaClientGeneratorMain.java
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * IMPORTANT: this is just a NON-FUNCTIONAL example that shows how table code
 *              is structured upon generation using
 *              JavaClientGeneratorMain.java
 *              
 * TableExample represents
 *   Table [per_photo_counts]
 *   in Keyspace [data_photos]
 * 
 * Name: per_photo_counts
 * Description:
 *   photos counters
 * 
 * Columns:
 *   photo_id : uuid
 *   sent_to_count : counter
 *   seen_by_count : counter
 * Partition Keys: photo_id
 * Secondary Keys:
 * Caching: KEYS_ONLY
 * Order By:
 *   seen_range : ASC
 *   photo_time : DESC
 * 
 * Queries:
 *   - Name: photoCounters
 *   Description:
 *     select counters for a photo
 *   Prepared Statement:
 *     SELECT * FROM per_photo_counts;
 *   - Name: insertPhotoCounters
 *   Description:
 *     insert counters for a photo
 *   Prepared Statement:
 *     INSERT INTO data_photos.per_photo_counts (photo_id) VALUES (:photoId);
 *   
 * IMPORTANT: this is just a NON-FUNCTIONAL example that shows how table code
 *              is structured upon generation using
 *              JavaClientGeneratorMain.java
 * */
public class TableExample extends Table {

  private static final String kKeySpaceName =
    "data_photos";
  private static final String kTableName =
    "per_photo_counts";

  /**
   * Query:
   * Name: photoCounters
   * Description:
   *   select counters for a photo
   * Prepared Statement:
   *   SELECT * FROM per_photo_counts;
   */
  private static final String kPhotoCountersName =
    "photoCounters";
  private static final String kPhotoCountersDescription =
    "select counters for a photo";
  private static final String kPhotoCountersPreparedStatement =
    "SELECT * FROM per_photo_counts;";

  /**
   * Query:
   * Name: insertPhotoCounters
   * Description:
   *   select counters for a photo
   * Prepared Statement:
   *   INSERT INTO data_photos.per_photo_counts (photo_id) VALUES (:photoId);
   */
  private static final String kInsertPhotoCountersName =
    "insertPhotoCounters";
  private static final String kPhotoCounterDescription =
    "insert counters for a photo";
  private static final String kPhotoCounterPreparedStatement =
    "INSERT INTO data_photos.per_photo_counts (photo_id) VALUES (:photoId);";
  
  /**
   * Constructor TableExample
   * @return new TableExample Object
   * @throws Exception
   */
  private TableExample () throws Exception {
    
    super (
      kKeySpaceName,
      kTableName,
      new Query (
        kPhotoCountersDescription,
        kPhotoCountersName,
        kPhotoCountersPreparedStatement),
      new Query (
        kPhotoCounterDescription,
        kInsertPhotoCountersName,
        kPhotoCounterPreparedStatement) );
  }
  
  private static TableExample instance = null;
  
  /**
   * loadTable
   * OPTIONAL method
   * instance is created either upon calling this method or upon the first call
   *   to singleton instance method i
   * this method is useful for loading upon program start instead of loading
   *   it upon the first use since there's a small time overhead for loading
   *   since all queries are prepared synchronously in a blocking network
   *   operation with Cassandra's server
   * @throws Exception
   */
  public static void loadTable () throws Exception {
    
    if (instance == null) {
      
      instance = new TableExample();
    }
  }
  
  /**
   * i
   * @return singleton static instance of TableExample
   * @throws Exception
   */
  public static TableExample i () throws Exception {
    
    if (instance == null) {
      
      instance = new TableExample();
    }
    
    return instance;
  }
  
  // Query: photoCounters
  // Description:
  //   insert counters for a photo
  // Parepared Statement:
  //   INSERT INTO data_photos.per_photo_counts (photo_id) VALUES (:photoId);

  /**
   * getQueryPhotoCounters
   * @return photoCounters Query in the form of
   *           a Query Object
   * @throws Exception
   */
  public Query getQueryPhotoCounters (
    ) throws Exception {
    
    return this.getQuery(kPhotoCountersName);
  }
  
  /**
   * getQueryDispatchablePhotoCounters
   * @return photoCounters Query in the form of
   *           a QueryDisbatchable Object
   *           (e.g.: to be passed on to a worker instance)
   * @throws Exception
   */
  public QueryDispatchable getQueryDispatchablePhotoCounters (
    ) throws Exception {
    
    return this.getQueryDispatchable(kPhotoCountersName);
  }
  
  /**
   * getPhotoCounters
   * @return photoCounters Query in the form of
   *           a BoundStatement ready for execution or to be added to
   *           a BatchStatement
   * @throws Exception
   */
  public BoundStatement getBoundStatementPhotoCounters (
    ) throws Exception {
    
    return this.getQuery(kPhotoCountersName).getBoundStatement();
  }
  
  /**
   * executeAsyncPhotoCounters
   * executes photoCounters Query asynchronously
   * @return ResultSetFuture
   * @throws Exception
   */
  public ResultSetFuture executeAsyncPhotoCounters (
    ) throws Exception {
    
    return this.getQuery(kPhotoCountersName).executeAsync();
  }
  
  /**
   * executeSyncPhotoCounters
   * BLOCKING-METHOD: blocks till the ResultSet is ready
   * executes photoCounters Query synchronously
   * @return ResultSet
   * @throws Exception
   */
  public ResultSet executeSyncPhotoCounters (
    ) throws Exception {
    
    return this.getQuery(kPhotoCountersName).executeSync();
  }
 
  // Query: InsertPhotoCounters
  // Description:
  //   insert counters for a photo
  // Parepared Statement:
  //   INSERT INTO data_photos.per_photo_counts (photo_id) VALUES (:photoId);
  
  /**
   * getQueryInserPhotoCounters
   * @return insertPhotoCounters Query in the form of
   *           a Query Object
   * @throws Exception
   */
  public Query getQueryInserPhotoCounters (
    ) throws Exception {
    
    return this.getQuery(kInsertPhotoCountersName);
  }
  
  /**
   * getQueryDispatchableInserPhotoCounters
   * @param photoId
   * @return insertPhotoCounters Query in the form of
   *           a QueryDisbatchable Object
   *           (e.g.: to be passed on to a worker instance)
   * @throws Exception
   */
  public QueryDispatchable getQueryDispatchableInserPhotoCounters (
    Object photoId) throws Exception {
    
    return
      this.getQueryDispatchable(
        kInsertPhotoCountersName,
        photoId);
  }
  
  /**
   * getBoundStatementInsertPhotoCounters
   * @param photoId
   * @return insertPhotoCounters Query in the form of
   *           a BoundStatement ready for execution or to be added to
   *           a BatchStatement
   * @throws Exception
   */
  public BoundStatement getBoundStatementInsertPhotoCounters (
    Object photoId) throws Exception {
    
    return
      this.getQuery(kInsertPhotoCountersName).getBoundStatement(
        photoId);
  }
  
  /**
   * executeAsyncInsertPhotoCounters
   * executes insertPhotoCounters Query asynchronously
   * @param photoId
   * @return ResultSetFuture
   * @throws Exception
   */
  public ResultSetFuture executeAsyncInsertPhotoCounters (
    Object photoId) throws Exception {
    
    return
      this.getQuery(kInsertPhotoCountersName).executeAsync(
        photoId);
  }
  
  /**
   * executeSyncInsertPhotoCounters
   * BLOCKING-METHOD: blocks till the ResultSet is ready
   * executes insertPhotoCounters Query synchronously
   * @param photoId
   * @return ResultSet
   * @throws Exception
   */
  public ResultSet executeSyncInsertPhotoCounters (
    Object photoId) throws Exception {
    
    return
      this.getQuery(kInsertPhotoCountersName).executeSync(
        photoId);
  }
}

/**
 * IMPORTANT: this is just a NON-FUNCTIONAL example that shows how table code
 *              is structured upon generation using
 *              JavaClientGeneratorMain.java
 * */