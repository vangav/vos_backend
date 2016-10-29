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

import java.util.Map;

import com.vangav.backend.cassandra.keyspaces_generator.json.ColumnJson;
import com.vangav.backend.cassandra.keyspaces_generator.json.KeyspaceJson;
import com.vangav.backend.cassandra.keyspaces_generator.json.QueryJson;
import com.vangav.backend.cassandra.keyspaces_generator.json.ReplicationJson;
import com.vangav.backend.cassandra.keyspaces_generator.json.TableJson;
import com.vangav.backend.content.formatting.PhrictionFormatterInl;
import com.vangav.backend.files.FileLoaderInl;
import com.vangav.backend.files.FileWriterInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * PhrictionGeneratorInl generates phabricator phriction's (wiki) formatted
 *   text for Cassandra's keyspaces, tables, columns, queries, descriptions,
 *   etc ... as defined in keyspaces' JSON definition files
 * */
public class PhrictionGeneratorInl {
  
  // disable default instantiation
  private PhrictionGeneratorInl () {}

  private static final String kCassandraPhrictionDir = "/phriction";
  private static final String kCassandraPhrictionExt = ".phriction";

  /**
   * generateCassandraKeyspacesPhriction
   * handles generation for all keyspaces, uses helper method
   *   generateCassandraKeyspacePhriction per-keyspace
   * @throws Exception
   */
  public static void generateCassandraKeyspacesPhriction (
    final String configDirPath,
    final String projectDirPath) throws Exception {
    
    Map<String, String> keyspaces =
      FileLoaderInl.loadTextFilesWithoutComments(
        CassandraGeneratorConstantsInl.kCassandraCommentPrefix,
        configDirPath,
        CassandraGeneratorConstantsInl.kCassandraKeyspaceExt);
    
    if (keyspaces.isEmpty() == true) {
      
      return;
    }
    
    String currPath;
    
    for (String fileName : keyspaces.keySet() ) {
      
      currPath =
        projectDirPath
        + "/"
        + CassandraGeneratorConstantsInl.kCassandraAssetsDirName
        + kCassandraPhrictionDir
        + "/"
        + fileName
        + kCassandraPhrictionExt;
      
      generateCassandraKeyspacePhriction(
        keyspaces.get(fileName),
        currPath);
    }
  }
  
  /**
   * generateCassandraKeyspacePhriction
   * handles generation for one keyspace
   * @param jsonContent
   * @param destPath
   * @throws Exception
   */
  private static void generateCassandraKeyspacePhriction (
    final String jsonContent,
    final String destPath) throws Exception {
    
    KeyspaceJson keyspaceJson = KeyspaceJson.fromJsonString(jsonContent);
    
    StringBuffer phrictionBuffer = new StringBuffer();
    
    // keyspace name
    phrictionBuffer.append(
      PhrictionFormatterInl.largeHeader(
        PhrictionFormatterInl.bold(
          "Keyspace: " + keyspaceJson.name) ) );
    phrictionBuffer.append(System.lineSeparator() );
    
    // keyspace description
    if (keyspaceJson.description != null) {
      
      phrictionBuffer.append(
        "Description: " + keyspaceJson.description);
      phrictionBuffer.append(System.lineSeparator() );
    }
    
    // keyspace replications
    
    phrictionBuffer.append(
      PhrictionFormatterInl.verySmallHeader("Replications") );
    phrictionBuffer.append(System.lineSeparator() );
    
    phrictionBuffer.append(
      PhrictionFormatterInl.tableHeader(
        "Name", "Replication", "Description") );
    phrictionBuffer.append(System.lineSeparator() );
    
    for (ReplicationJson replicationJson : keyspaceJson.replications) {
      
      phrictionBuffer.append(
        PhrictionFormatterInl.tableRow(
            replicationJson.name,
            replicationJson.replication,
            replicationJson.description) );
      phrictionBuffer.append(System.lineSeparator() );
    }
    phrictionBuffer.append(System.lineSeparator() );
    
    // tables
    
    StringBuffer tempBuffer;
    
    for (TableJson tableJson : keyspaceJson.tables) {
      
      // table name
      phrictionBuffer.append(
        PhrictionFormatterInl.smallerHeader(
          "Table: " + tableJson.name) );
      phrictionBuffer.append(System.lineSeparator() );
      
      // table description
      if (tableJson.description != null) {
        
        phrictionBuffer.append(
          "Description: " + tableJson.description);
        phrictionBuffer.append(System.lineSeparator() );
      }
      
      // columns
      
      phrictionBuffer.append(
        PhrictionFormatterInl.verySmallHeader("Columns") );
      phrictionBuffer.append(System.lineSeparator() );
      
      phrictionBuffer.append(
        PhrictionFormatterInl.tableHeader(
          "Name", "Type") );
      phrictionBuffer.append(System.lineSeparator() );
      
      for (ColumnJson columnJson : tableJson.columns) {
        
        phrictionBuffer.append(
          PhrictionFormatterInl.tableRow(
              columnJson.name,
              columnJson.type) );
        phrictionBuffer.append(System.lineSeparator() );
      }
      
      // partition keys
      
      tempBuffer = new StringBuffer("Partition Keys: ");
      
      for (int i = 0; i < tableJson.partition_keys.length; i ++) {
        
        tempBuffer.append(tableJson.partition_keys[i] );
        
        if (i < (tableJson.partition_keys.length - 1) ) {
          
          tempBuffer.append(", ");
        }
      }
      
      phrictionBuffer.append(
        PhrictionFormatterInl.monospaced(
          tempBuffer.toString() ) );
      phrictionBuffer.append(System.lineSeparator() );
      
      // secondary keys
      if (tableJson.secondary_keys != null &&
          tableJson.secondary_keys.length > 0) {
        
        tempBuffer = new StringBuffer("Secondary Keys: ");
        
        for (int i = 0; i < tableJson.secondary_keys.length; i ++) {
          
          tempBuffer.append(tableJson.secondary_keys[i] );
          
          if (i < (tableJson.secondary_keys.length - 1) ) {
            
            tempBuffer.append(", ");
          }
        }
        
        phrictionBuffer.append(
          PhrictionFormatterInl.monospaced(
            tempBuffer.toString() ) );
        phrictionBuffer.append(System.lineSeparator() );
      }
      
      // caching
      phrictionBuffer.append(
        PhrictionFormatterInl.monospaced(
          "Caching: " + tableJson.caching) );
      phrictionBuffer.append(System.lineSeparator() );
      
      // order by
      if (tableJson.order_by != null && tableJson.order_by.length > 0) {
        
        tempBuffer = new StringBuffer("Order By: ");
        
        for (int i = 0; i < tableJson.order_by.length; i ++) {
          
          tempBuffer.append(
            tableJson.order_by[i].column_name
            + " : "
            + tableJson.order_by[i].order_type);
          
          if (i < (tableJson.order_by.length - 1) ) {
            
            tempBuffer.append(", ");
          }
        }
        
        phrictionBuffer.append(
          PhrictionFormatterInl.monospaced(
            tempBuffer.toString() ) );
        phrictionBuffer.append(System.lineSeparator() );
      }
      
      // queries
      if (tableJson.queries != null && tableJson.queries.length > 0) {

        phrictionBuffer.append(System.lineSeparator() );
        phrictionBuffer.append(
          PhrictionFormatterInl.verySmallHeader("Queries:") );
        phrictionBuffer.append(System.lineSeparator() );
        phrictionBuffer.append(System.lineSeparator() );
        
        for (QueryJson queryJson : tableJson.queries) {
          
          phrictionBuffer.append(
            PhrictionFormatterInl.bold(
              "Query: "
              + queryJson.name) );
          phrictionBuffer.append(System.lineSeparator() );
          
          phrictionBuffer.append(queryJson.description);
          phrictionBuffer.append(System.lineSeparator() );
          
          phrictionBuffer.append(
            PhrictionFormatterInl.monospaced(
              queryJson.prepared_statement) );
          phrictionBuffer.append(System.lineSeparator() );
        }
      }
      
      phrictionBuffer.append(System.lineSeparator() );
      phrictionBuffer.append(System.lineSeparator() );
    }
    
    FileWriterInl.writeTextFile(
      phrictionBuffer.toString(),
      destPath,
      true);
  }
}
