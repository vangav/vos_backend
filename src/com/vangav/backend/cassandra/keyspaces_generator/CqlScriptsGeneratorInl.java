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

import java.util.ArrayList;
import java.util.Map;

import com.vangav.backend.cassandra.keyspaces_generator.json.ColumnJson;
import com.vangav.backend.cassandra.keyspaces_generator.json.KeyspaceJson;
import com.vangav.backend.cassandra.keyspaces_generator.json.OrderByJson;
import com.vangav.backend.cassandra.keyspaces_generator.json.ReplicationJson;
import com.vangav.backend.cassandra.keyspaces_generator.json.TableJson;
import com.vangav.backend.data_structures_and_algorithms.tuple.Pair;
import com.vangav.backend.files.FileLoaderInl;
import com.vangav.backend.files.FileWriterInl;
import com.vangav.backend.system.CommandLineInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * PhrictionGeneratorInl generates CQL scripts for creating Cassandra's
 *   keyspaces and tables.
 * For each keyspace, two scripts are generated per replication strategy,
 *   one that overwrites present keyspaces/tables and one that doesn't
 *   overwrite.
 * IMPORTANT: CQL should be used with high caution, for example using
 *              an overwrite script on a Cassandra instance in production will
 *              wipe out all data from all Cassandra instances in production.
 *              Overwrite/drop scripts are usually used in dev mode for
 *              testing-like operations.
 * */
public class CqlScriptsGeneratorInl {
  
  // disable default instantiation
  private CqlScriptsGeneratorInl () {}

  private static final String kCassandraKeyspacesCqlCreateIfDoesnotExistDir =
    "/cql/create_if_doesnot_exist";
  private static final String kCassandraKeyspacesCqlDropAndCreateDir =
    "/cql/drop_and_create";
  private static final String kCassandraKeyspacesCqlDropDir =
    "/cql/drop";
  private static final String kCassandraKeyspaceCqlExt = ".cql";

  /**
   * generateCassandraKeyspacesCqlScripts
   * handles generation for all CQL scripts for creating/dropping keyspaces
   *   with defined replications and all of their tables
   * @param configDirPath
   * @param projectDirPath
   * @throws Exception
   */
  public static void generateCassandraKeyspacesCqlScripts (
    final String configDirPath,
    final String projectDirPath) throws Exception {
    
    // load config files
    Map<String, String> keyspaces =
      FileLoaderInl.loadTextFilesWithoutComments(
        CassandraGeneratorConstantsInl.kCassandraCommentPrefix,
        configDirPath,
        CassandraGeneratorConstantsInl.kCassandraKeyspaceExt);
    
    if (keyspaces.isEmpty() == true) {

      return;
    }
    
    // create directories

    String cassandraAssetsPath =
      projectDirPath
      + "/"
      + CassandraGeneratorConstantsInl.kCassandraAssetsDirName;
    
    String createIfDoesnotExistPath =
      cassandraAssetsPath
      + kCassandraKeyspacesCqlCreateIfDoesnotExistDir;
    String dropAndCreatePath =
      cassandraAssetsPath
      + kCassandraKeyspacesCqlDropAndCreateDir;
    String dropPath =
      cassandraAssetsPath
      + kCassandraKeyspacesCqlDropDir;
    
    FileWriterInl.mkdirs(
      createIfDoesnotExistPath,
      false);
    FileWriterInl.mkdirs(
      dropAndCreatePath,
      false);
    FileWriterInl.mkdirs(
      dropPath,
      false);
    
    // add scripts
    addCassandraScripts(
      cassandraAssetsPath,
      createIfDoesnotExistPath,
      dropAndCreatePath,
      dropPath);
    
    // generate cassandra CQL scripts
    for (String fileName : keyspaces.keySet() ) {
      
      generateCassandraKeyspaceCqlScripts(
        keyspaces.get(fileName),
        createIfDoesnotExistPath,
        dropAndCreatePath,
        dropPath);
    }
  }
  
  private static final String kStartCassandraScript =
    "#!/bin/bash\n\n"
    
    + "# used to start cassandra service\n\n"

    + "# To stop the service:\n"
    + "# 1- ps auwx | grep cassandra\n"
    + "# 2- kill -9 PID\n\n"
    
    + "../../../vos_backend/apache-cassandra-2.1.2/bin/cassandra &> /dev/null\n";
  private static final String kCassandraStatusScript =
    "#!/bin/bash\n\n"
    
    + "# used to check current cassandra's status\n\n"
    
    + "../../../vos_backend/apache-cassandra-2.1.2/bin/nodetool status\n";
  private static final String kExecuteCqlScript =
    "#!/bin/bash\n\n"
    
    + "# used to execute a cassandra CQL script\n"
    + "# IMPORTANT: CQL script file name must be passed as an argument\n\n"
    
    + "if [ \"$1\" -eq 0 ]\n"
    + "then\n"
    + "  echo \"CQL script file name must be passed as an argument\"\n"
    + "  echo \"try again with a valid argument\"\n"
    + "fi\n\n"
    
    + "echo \"executing CQL script $1\"\n\n"

    + "../../../../vos_backend/apache-cassandra-2.1.2/bin/cqlsh -f $1\n";
  /**
   * cassandraScripts
   * adds scripts for starting cassandra, checking status,
   *   executing CQL scripts, etc ...
   * @param cassandraAssetsPath
   * @param createIfDoesnotExistPath
   * @param dropAndCreatePath
   * @param dropPath
   * @throws Exception
   */
  private static void addCassandraScripts (
    final String cassandraAssetsPath,
    final String createIfDoesnotExistPath,
    final String dropAndCreatePath,
    final String dropPath) throws Exception {
    
    FileWriterInl.writeTextFile(
      kStartCassandraScript,
      cassandraAssetsPath
        + "/cql/_start_cassandra.sh",
      false);
    CommandLineInl.executeCommand(
      "chmod +x "
      + cassandraAssetsPath
      + "/cql/_start_cassandra.sh");
    
    FileWriterInl.writeTextFile(
      kCassandraStatusScript,
      cassandraAssetsPath
        + "/cql/_cassandra_status.sh",
      false);
    CommandLineInl.executeCommand(
      "chmod +x "
      + cassandraAssetsPath
      + "/cql/_cassandra_status.sh");
    
    FileWriterInl.writeTextFile(
      kExecuteCqlScript,
      createIfDoesnotExistPath
        + "/_execute_cql.sh",
      false);
    CommandLineInl.executeCommand(
      "chmod +x "
      + createIfDoesnotExistPath
      + "/_execute_cql.sh");

    FileWriterInl.writeTextFile(
      kExecuteCqlScript,
      dropAndCreatePath
        + "/_execute_cql.sh",
      false);
    CommandLineInl.executeCommand(
      "chmod +x "
      + dropAndCreatePath
      + "/_execute_cql.sh");

    FileWriterInl.writeTextFile(
      kExecuteCqlScript,
      dropPath
        + "/_execute_cql.sh",
      false);
    CommandLineInl.executeCommand(
      "chmod +x "
      + dropPath
      + "/_execute_cql.sh");
  }

  /**
   * generateCassandraKeyspaceCqlScripts
   * handles the generation of all CQL scripts for creating/dropping
   *   a keyspace with its replications and its tables
   * @param jsonContent
   * @param createIfDoesnotExistPath
   * @param dropAndCreatePath
   * @param dropPath
   * @throws Exception
   */
  private static void generateCassandraKeyspaceCqlScripts (
    final String jsonContent,
    final String createIfDoesnotExistPath,
    final String dropAndCreatePath,
    final String dropPath) throws Exception {
    
    KeyspaceJson keyspaceJson = KeyspaceJson.fromJsonString(jsonContent);
    
    // array lists of string buffers, one buffer per keyspace replication
    //   each pair <string, stringBuffer> has the replication name and its
    //   string buffer
    
    ArrayList<Pair<String, StringBuffer> > createIfDoesnotExistStringBuffers =
      new ArrayList<Pair<String, StringBuffer> >();
    ArrayList<Pair<String, StringBuffer> > dropAndCreateStringBuffers =
      new ArrayList<Pair<String, StringBuffer> >();
    ArrayList<Pair<String, StringBuffer> > dropStringBuffers =
      new ArrayList<Pair<String, StringBuffer> >();
    
    // initialize string buffers and generate keyspace creation/dropping
    //   CQL script
    for (ReplicationJson replicationJson : keyspaceJson.replications) {
      
      createIfDoesnotExistStringBuffers.add(
        new Pair<String, StringBuffer>(
          keyspaceJson.name
          + "_"
          + replicationJson.name
          + kCassandraKeyspaceCqlExt,
          new StringBuffer(
            getKeyspaceCreateIfNotExists(
              keyspaceJson.name,
              replicationJson.replication) ) ) );
      
      dropAndCreateStringBuffers.add(
        new Pair<String, StringBuffer>(
          keyspaceJson.name
          + "_"
          + replicationJson.name
          + kCassandraKeyspaceCqlExt,
          new StringBuffer(
            getKeyspaceDropAndCreate(
              keyspaceJson.name,
              replicationJson.replication) ) ) );
      
      dropStringBuffers.add(
        new Pair<String, StringBuffer>(
          keyspaceJson.name
          + "_"
          + replicationJson.name
          + kCassandraKeyspaceCqlExt,
          new StringBuffer(
            getKeyspaceDrop(
              keyspaceJson.name) ) ) );
    }
    
    // add table creation/dropping CQL script
    for (TableJson tableJson : keyspaceJson.tables) {
      
      for (int i = 0; i < keyspaceJson.replications.length; i ++) {
        
        createIfDoesnotExistStringBuffers.get(i).getSecond().append(
          getTableCreateIfNotExists(tableJson.name) );
        createIfDoesnotExistStringBuffers.get(i).getSecond().append(
            getTableDefinition(tableJson) );
        
        dropAndCreateStringBuffers.get(i).getSecond().append(
          getTableDropAndCreate(tableJson.name) );
        dropAndCreateStringBuffers.get(i).getSecond().append(
            getTableDefinition(tableJson) );
      }
    }
    
    // write generated CQL script files
    for (int i = 0; i < keyspaceJson.replications.length; i ++) {
      
      FileWriterInl.writeTextFile(
        createIfDoesnotExistStringBuffers.get(i).getSecond().toString(),
        createIfDoesnotExistPath
        + "/"
        + createIfDoesnotExistStringBuffers.get(i).getFirst(),
        false);
      
      FileWriterInl.writeTextFile(
        dropAndCreateStringBuffers.get(i).getSecond().toString(),
        dropAndCreatePath
        + "/"
        + dropAndCreateStringBuffers.get(i).getFirst(),
        false);
      
      FileWriterInl.writeTextFile(
        dropStringBuffers.get(i).getSecond().toString(),
        dropPath
        + "/"
        + dropStringBuffers.get(i).getFirst(),
        false);
    }
  }
  
  private static final String kKeyspaceCreateIfNotExistsFormat =
    "// GENERATED using CqlScriptsGeneratorMain.java\n"
    + "// Keyspace: %s\n\n"

    + "CREATE KEYSPACE IF NOT EXISTS %s\n"
    + "  WITH replication = {\n"
    + "    %s\n"
    + "};\n\n"
    
    + "USE %s;\n\n\n";
  /**
   * getKeyspaceCreateIfNotExists
   * @param keyspaceName
   * @param keyspaceReplication
   * @return formatted CQL script string for creating a keyspace if it doesn't
   *           already exist
   * @throws Exception
   */
  private static final String getKeyspaceCreateIfNotExists (
    final String keyspaceName,
    final String keyspaceReplication) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer();
    
    stringBuffer.append(
      String.format(
        kKeyspaceCreateIfNotExistsFormat,
        keyspaceName,
        keyspaceName,
        keyspaceReplication,
        keyspaceName) );
    
    return stringBuffer.toString();
  }
  
  private static final String kKeyspaceDropAndCreateFormat =
    "// Keyspace: %s\n\n"

    + "DROP KEYSPACE IF EXISTS %s;\n\n"
    
    + "CREATE KEYSPACE %s\n"
    + "  WITH replication = {\n"
    + "    %s\n"
    + "};\n\n\n"
    
    + "USE %s;\n\n";
  /**
   * getKeyspaceDropAndCreate
   * IMPORTANT: this deletes the keyspace, its tables along with all the data
   *              stored in these tables from ALL replicas
   * @param keyspaceName
   * @param keyspaceReplication
   * @return formatted CQL script string for dropping a keyspace then creating
   *           it again
   * @throws Exception
   */
  private static final String getKeyspaceDropAndCreate (
    final String keyspaceName,
    final String keyspaceReplication) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer();
    
    stringBuffer.append(
      String.format(
        kKeyspaceDropAndCreateFormat,
        keyspaceName,
        keyspaceName,
        keyspaceName,
        keyspaceReplication,
        keyspaceName) );
    
    return stringBuffer.toString();
  }
  
  private static final String kKeyspaceDropFormat =
    "DROP KEYSPACE IF EXISTS %s;\n\n";
  /**
   * getKeyspaceDrop
   * IMPORTANT: this deletes the keyspace, its tables along with all the data
   *              stored in these tables from ALL replicas
   * @param keyspaceName
   * @return formatted CQL script string for dropping a keyspace
   * @throws Exception
   */
  private static final String getKeyspaceDrop (
    final String keyspaceName) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer();
    
    stringBuffer.append(
      String.format(
        kKeyspaceDropFormat,
        keyspaceName) );
    
    return stringBuffer.toString();
  }
  
  private static final String kTableCreateIfNotExistsFormat =
    "CREATE TABLE IF NOT EXISTS %s (\n";
  /**
   * getTableCreateIfNotExists
   * @param tableName
   * @return formatted CQL script part for table creation if it doesn't exist
   * @throws Exception
   */
  private static final String getTableCreateIfNotExists (
    String tableName) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer();
    
    stringBuffer.append(
      String.format(
        kTableCreateIfNotExistsFormat,
        tableName) );
    
    return stringBuffer.toString();
  }
  
  private static final String kTableDropAndCreateFormat =
    "DROP TABLE IF EXISTS %s;\n\n"

    + "CREATE TABLE %s (\n";
  /**
   * getTableDropAndCreate
   * IMPORTANT: if that table already exists, all its data will be deleted from
   *              ALL replicas
   * @param tableName
   * @return formatted CQL script part for table dropping and creation
   * @throws Exception
   */
  private static final String getTableDropAndCreate (
    String tableName) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer();
    
    stringBuffer.append(
      String.format(
        kTableDropAndCreateFormat,
        tableName,
        tableName) );
    
    return stringBuffer.toString();
  }
  
  private static final String kTableColumnFormat =
    "  %s %s,\n";
  private static final String kTablePrimaryKeyAndSecondaryKeyFormat =
    "  PRIMARY KEY ((%s), %s) )\n";
  private static final String kTablePrimaryKeyFormat =
    "  PRIMARY KEY (%s) )\n";
  private static final String kTableCachingFormat =
    "  WITH CACHING = '%s'";
  private static final String kTableEndFormat =
    ";\n\n\n";
  /**
   * getTableDefinition
   * @param tableJson
   * @return formatted string of table creating CQL script for columns, keys,
   *           caching and order by
   * @throws Exception
   */
  private static final String getTableDefinition (
    TableJson tableJson) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer();
    
    for (ColumnJson columnJson : tableJson.columns) {
      
      stringBuffer.append(
        String.format(
          kTableColumnFormat,
          columnJson.name,
          columnJson.type) );
    }
    
    if (tableJson.secondary_keys != null &&
        tableJson.secondary_keys.length > 0) {
      
      stringBuffer.append(
        String.format(
          kTablePrimaryKeyAndSecondaryKeyFormat,
          getTableKeys(tableJson.partition_keys),
          getTableKeys(tableJson.secondary_keys) ) );
    } else {
      
      stringBuffer.append(
        String.format(
          kTablePrimaryKeyFormat,
          getTableKeys(tableJson.partition_keys) ) );
    }
    
    if (tableJson.caching != null && tableJson.caching.length() > 0) {
      
      stringBuffer.append(
        String.format(
          kTableCachingFormat,
          tableJson.caching) );
    }
    
    if (tableJson.order_by != null && tableJson.order_by.length > 0) {
      
      if (tableJson.caching != null && tableJson.caching.length() > 0) {
        
        stringBuffer.append(" AND\n");
      }
      
      stringBuffer.append(
        getTableOrderBy(tableJson.order_by) );
    }
    
    stringBuffer.append(kTableEndFormat);
    
    return stringBuffer.toString();
  }
  
  /**
   * getTableKeys
   * @param keys
   * @return formatted string of table keys (partition and secondary keys) for
   *           table creation CQL script
   * @throws Exception
   */
  private static final String getTableKeys (
    String[] keys) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer();
    
    for (int i = 0; i < keys.length; i ++) {
      
      stringBuffer.append(keys[i] );
      
      if (i < (keys.length - 1) ) {
        
        stringBuffer.append(", ");
      }
    }
    
    return stringBuffer.toString();
  }
  
  private static final String kTableOrderByStartFormat =
    "  CLUSTERING ORDER BY (";
  private static final String kTableOrderByClosingFormat =
    ")";
  /**
   * getTableOrderBy
   * @param orderByJsonArr
   * @return CLUSTERING ORDER BY formatted part of table creation CQL script
   * @throws Exception
   */
  private static final String getTableOrderBy (
    OrderByJson[] orderByJsonArr) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer();
    
    stringBuffer.append(kTableOrderByStartFormat);
    
    for (int i = 0; i < orderByJsonArr.length; i ++) {
      
      stringBuffer.append(
        orderByJsonArr[i].column_name
        + " "
        + orderByJsonArr[i].order_type);
      
      if (i < (orderByJsonArr.length - 1) ) {
        
        stringBuffer.append(", ");
      }
    }

    stringBuffer.append(kTableOrderByClosingFormat);
    
    return stringBuffer.toString();
  }
}
