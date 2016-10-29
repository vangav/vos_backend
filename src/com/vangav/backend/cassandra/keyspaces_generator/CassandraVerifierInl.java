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

package com.vangav.backend.cassandra.keyspaces_generator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.vangav.backend.cassandra.keyspaces_generator.json.ColumnJson;
import com.vangav.backend.cassandra.keyspaces_generator.json.KeyspaceJson;
import com.vangav.backend.cassandra.keyspaces_generator.json.OrderByJson;
import com.vangav.backend.cassandra.keyspaces_generator.json.TableJson;
import com.vangav.backend.content.checking.CassandraCqlVerifierInl;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;
import com.vangav.backend.files.FileLoaderInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * CassandraVerifierInl has inline static methods for verifying keyspaces
 *   config files [*.keyspace]
 * */
public class CassandraVerifierInl {

  // disable default instantiation
  private CassandraVerifierInl () {}
  
  /**
   * verifyKeyspacesConfig
   * throws an exception if a keyspace's config is invalid
   * IMPORTANT: this method doesn't verify table's queries
   * @param configDirPath
   * @throws Exception
   */
  public static void verifyKeyspacesConfig (
    final String configDirPath) throws Exception {

    // load config files
    
    Map<String, String> keyspacesConfigFiles =
      FileLoaderInl.loadTextFilesWithoutComments(
        CassandraGeneratorConstantsInl.kCassandraCommentPrefix,
        configDirPath,
        CassandraGeneratorConstantsInl.kCassandraKeyspaceExt);
    
    if (keyspacesConfigFiles.isEmpty() == true) {
      
      return;
    }
    
    KeyspaceJson currKeyspaceJson;
    Set<String> keyspacesNames = new HashSet<String>();
    Set<String> keyspaceTablesNames;
    
    // verify keyspaces each keyspace's table
    for (String configFileName : keyspacesConfigFiles.keySet() ) {
      
      currKeyspaceJson =
        KeyspaceJson.fromJsonString(
          keyspacesConfigFiles.get(configFileName) );
      
      // verify keyspace's name
      CassandraCqlVerifierInl.verifyName(currKeyspaceJson.name);
      
      // check for duplicates
      if (keyspacesNames.contains(currKeyspaceJson.name) == true) {
        
        throw new CodeException(
          "Can't have more than one keyspace with the same name ["
          + currKeyspaceJson.name
          + "]",
          ExceptionClass.JSON);
      }
      
      keyspacesNames.add(currKeyspaceJson.name);
      
      // verify replications
      if (currKeyspaceJson.replications == null ||
          currKeyspaceJson.replications.length <= 0) {
        
        throw new CodeException(
          "Keyspace ["
          + currKeyspaceJson.name
          + "] must have at least one replication",
          ExceptionClass.JSON);
      }
      
      if (currKeyspaceJson.tables != null) {
        
        keyspaceTablesNames = new HashSet<String>();
        
        // verify keyspace's tables
        for (TableJson tableJson : currKeyspaceJson.tables) {
          
          // verify table's name
          CassandraCqlVerifierInl.verifyName(tableJson.name);
          
          // check for duplicates
          if (keyspaceTablesNames.contains(tableJson.name) == true) {
            
            throw new CodeException(
              "Can't have more than one table with the same name ["
              + tableJson.name
              + "], from keyspace ["
              + currKeyspaceJson.name
              + "]",
              ExceptionClass.JSON);
          }
          
          keyspaceTablesNames.add(tableJson.name);
          
          verifyTableConfig(currKeyspaceJson.name, tableJson);
        }
      }
    }
  }
  
  /**
   * verifyTableConfig
   * throws and exception if the table's config is invalid
   * IMPORTANT: this method doesn't verify table's queries
   * @param keyspaceName
   * @param tableJson
   * @throws Exception
   */
  private static void verifyTableConfig (
    final String keyspaceName,
    final TableJson tableJson) throws Exception {
    
    // verify table's columns
    
    ArgumentsInl.checkNotEmpty(
      "keyspace ["
      + keyspaceName
      + "] table ["
      + tableJson.name
      + "] columns",
      tableJson.columns,
      ExceptionType.CODE_EXCEPTION);
    
    Map<String, Integer> columns = new HashMap<String, Integer>();
    
    for (ColumnJson columnJson : tableJson.columns) {
      
      CassandraCqlVerifierInl.verifyName(columnJson.name);
      CassandraCqlVerifierInl.verifyColumnType(columnJson.type);
      
      if (columns.containsKey(columnJson.name) == true) {
        
        throw new CodeException(
          "keyspace ["
          + keyspaceName
          + "] table ["
          + tableJson.name
          + " ] column name ["
          + columnJson.name
          + "] is a duplicate, a column name can only be used once per table",
          ExceptionClass.JSON);
      }

      columns.put(columnJson.name, 0);
    }
    
    // verify table's partition keys
    
    ArgumentsInl.checkNotEmpty(
      "keyspace ["
      + keyspaceName
      + "] table ["
      + tableJson.name
      + "] partition keys",
      tableJson.partition_keys,
      ExceptionType.CODE_EXCEPTION);
    
    for (String partitionKey : tableJson.partition_keys) {
      
      if (columns.containsKey(partitionKey) == false) {
        
        throw new CodeException(
          "keyspace ["
          + keyspaceName
          + "] table ["
          + tableJson.name
          + " ] parition key ["
          + partitionKey
          + "] isn't one of the table's columns: "
          + Arrays.toString(tableJson.columns),
          ExceptionClass.JSON);
      }
      
      if (columns.get(partitionKey) > 0) {
        
        throw new CodeException(
          "keyspace ["
          + keyspaceName
          + "] table ["
          + tableJson.name
          + " ] parition key ["
          + partitionKey
          + "] is a duplicate, a partition key can only be used once",
          ExceptionClass.JSON);
      }

      columns.put(partitionKey, 1);
    }
    
    // verify table's secondary keys
    
    Map<String, Integer> secondaryKeys = new HashMap<String, Integer>();
    
    if (tableJson.secondary_keys != null) {
      
      for (String secondaryKey : tableJson.secondary_keys) {
        
        if (columns.containsKey(secondaryKey) == false) {
          
          throw new CodeException(
            "keyspace ["
            + keyspaceName
            + "] table ["
            + tableJson.name
            + " ] secondary key ["
            + secondaryKey
            + "] isn't one of the table's columns: "
            + Arrays.toString(tableJson.columns),
            ExceptionClass.JSON);
        }
        
        if (columns.get(secondaryKey) > 0) {
          
          throw new CodeException(
            "keyspace ["
            + keyspaceName
            + "] table ["
            + tableJson.name
            + " ] secondary key ["
            + secondaryKey
            + "] is a duplicate, a secondary key can only be used once and "
            + "can't be already used as a primary key",
            ExceptionClass.JSON);
        }

        columns.put(secondaryKey, 1);
        secondaryKeys.put(secondaryKey, 0);
      }
    }
    
    // verify table's caching
    if (tableJson.caching != null) {
      
      CassandraCqlVerifierInl.verifyCaching(tableJson.caching);
    }
    
    // verify table's order by
    if (tableJson.order_by != null && tableJson.order_by.length > 0) {
      
      if (tableJson.secondary_keys == null ||
          tableJson.secondary_keys.length == 0) {
        
        throw new CodeException(
          "keyspace ["
          + keyspaceName
          + "] table ["
          + tableJson.name
          + " ] can't have order by ["
          + Arrays.toString(tableJson.order_by)
          + "] since it has no secondary keys and an order by is only allowed "
          + "on secondary key columns",
          ExceptionClass.JSON);
      }
      
      for (OrderByJson orderByJson : tableJson.order_by) {
        
        if (secondaryKeys.containsKey(orderByJson.column_name) == false) {
          
          throw new CodeException(
            "keyspace ["
            + keyspaceName
            + "] table ["
            + tableJson.name
            + " ] order by column name ["
            + orderByJson.column_name
            + "] isn't one of the table's seconday keys: "
            + Arrays.toString(tableJson.secondary_keys),
            ExceptionClass.JSON);
        }
        
        if (secondaryKeys.get(orderByJson.column_name) > 0) {
          
          throw new CodeException(
            "keyspace ["
            + keyspaceName
            + "] table ["
            + tableJson.name
            + " ] order by column name ["
            + orderByJson.column_name
            + "] is a duplicate, a secondary key can only be used once per "
            + "table's order by",
            ExceptionClass.JSON);
        }
        
        CassandraCqlVerifierInl.verifyOrderByType(orderByJson.order_type);

        secondaryKeys.put(orderByJson.column_name, 1);
      }
    }
  }
}
