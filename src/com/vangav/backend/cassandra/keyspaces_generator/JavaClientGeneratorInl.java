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

package com.vangav.backend.cassandra.keyspaces_generator;

import java.util.ArrayList;
import java.util.Map;

import com.vangav.backend.cassandra.keyspaces_generator.json.ColumnJson;
import com.vangav.backend.cassandra.keyspaces_generator.json.KeyspaceJson;
import com.vangav.backend.cassandra.keyspaces_generator.json.OrderByJson;
import com.vangav.backend.cassandra.keyspaces_generator.json.QueryJson;
import com.vangav.backend.cassandra.keyspaces_generator.json.TableJson;
import com.vangav.backend.content.formatting.CodeIdentifiersFormatterInl;
import com.vangav.backend.content.formatting.JavaFormatterInl;
import com.vangav.backend.files.FileLoaderInl;
import com.vangav.backend.files.FileWriterInl;
import com.vangav.backend.system.CommandLineInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * JavaClientGeneratorMain generates JAVA clients for Cassandra. One client
 *   per table where each client is a singleton class with bind/get methods per
 *   query. Refer to TableExample.java for an example.
 * EXAMPLE:TableExample.java (example of a generated JAVA client)
 * 
 * note: I don't like the design of this class, while working on it I don't
 *         have the capacity to come up with a better design that's would
 *         ideally be more generic
 *       But it works and saves hours (or months on scale) of work as the
 *         Database grows while keeping things consistent and error-free
 *       A different technique is used for Vangav M code generation but it may
 *         be tricky to maintain for others! that's why the current technique
 *         is used instead.
 * */
public class JavaClientGeneratorInl {
  
  // disable default instantiation
  private JavaClientGeneratorInl () {}

  /**
   * generateCassandraJavaClient
   * entry point from main for generate Cassandra's JAVA Clients for all the
   *   keyspaces/tables for Cassandra and writes their .java files
   * @param packageName
   * @throws Exception
   */
  public static void generateCassandraJavaClient (
    final String configDirPath,
    final String projectDirPath,
    final String projectName,
    final String rootPackage,
    final boolean isUpdate) throws Exception {
    
    Map<String, String> keyspaces =
      FileLoaderInl.loadTextFilesWithoutComments(
        CassandraGeneratorConstantsInl.kCassandraCommentPrefix,
        configDirPath,
        CassandraGeneratorConstantsInl.kCassandraKeyspaceExt);
    
    if (keyspaces.isEmpty() == true) {
      
      return;
    }
    
    String pathToPackage =
      JavaFormatterInl.getPathToPackage(
        projectDirPath,
        JavaFormatterInl.kPlaySrcDirName,
        rootPackage,
        projectName,
        CassandraGeneratorConstantsInl.kCassandraPackageName);
    
    String packageName =
      JavaFormatterInl.getPackageName(
        rootPackage,
        projectName,
        CassandraGeneratorConstantsInl.kCassandraPackageName);
    
    FileWriterInl.mkdirs(pathToPackage, false);
    
    if (isUpdate == false) {
      
      // copy properties file
      CommandLineInl.executeCommand(
        "cp ../prop/cassandra_properties.prop "
        + "../../"
        + projectName
        + "/conf/prop/cassandra_properties.prop");
    } else {
      
      if (FileLoaderInl.fileExists(
            "../conf/prop/cassandra_properties.prop") == false) {
        
        // copy properties file
        CommandLineInl.executeCommand(
          "cp ../../vos_backend/prop/cassandra_properties.prop "
          + "../conf/prop/cassandra_properties.prop");
      }
    }
    
    // generate keyspaces' classes
    for (String fileName : keyspaces.keySet() ) {
      
      generateKeySpace(
        pathToPackage,
        packageName,
        keyspaces.get(fileName) );
    }
    
    // update global class to connect cassandra and load tables
    updateGlobalClass(
      configDirPath,
      projectDirPath,
      projectName,
      rootPackage,
      isUpdate);
    
    //metrics-core-3.0.2.jar
    
    if (isUpdate == false ||
        FileLoaderInl.fileExists(
          "../lib/cassandra-driver-core-2.1.4.jar") == false ||
        FileLoaderInl.fileExists(
          "../lib/metrics-core-3.0.2.jar") == false) {
    
      if (isUpdate == false) {
      
        // copy driver core lib
        CommandLineInl.executeCommand(
          "cp ../lib/cassandra-driver-core-2.1.4.jar "
          + "../../"
          + projectName
          + "/lib/cassandra-driver-core-2.1.4.jar");
      
        // copy metrics core lib
        CommandLineInl.executeCommand(
          "cp ../lib/metrics-core-3.0.2.jar "
          + "../../"
          + projectName
          + "/lib/metrics-core-3.0.2.jar");
      } else {
      
        // copy driver core lib
        CommandLineInl.executeCommand(
          "cp ../../vos_backend/lib/cassandra-driver-core-2.1.4.jar "
          + "../lib/cassandra-driver-core-2.1.4.jar");
      
        // copy metrics core lib
        CommandLineInl.executeCommand(
          "cp ../../vos_backend/lib/metrics-core-3.0.2.jar "
          + "../lib/metrics-core-3.0.2.jar");
      }
      
      // update class path with driver lib
      ArrayList<String> classPathLines =
        FileLoaderInl.loadTextFileLines(
          projectDirPath
          + "/.classpath");
      
      for (int i = 0; i < classPathLines.size(); i ++) {
        
        if (classPathLines.get(i).startsWith(
              "  <classpathentry kind=\"lib\" "
              + "path=\"./lib/vos_backend.jar\"/>") == true) {
          
          classPathLines.add(
            i + 1,
            "  <classpathentry kind=\"lib\" "
              + "path=\"./lib/cassandra-driver-core-2.1.4.jar\"/>\n"
              + "  <classpathentry kind=\"lib\" "
              + "path=\"./lib/metrics-core-3.0.2.jar\"/>\n");
          
          break;
        }
      }
      
      FileWriterInl.writeTextFile(
        classPathLines,
        projectDirPath
        + "/.classpath",
        false,
        false);
    }
  }
  
  /**
   * generateKeySpace
   * generates JAVA Clients for all the tables of a Cassandra Keysapce and
   *   writes their .java files
   * @param packageName
   * @param jsonContent
   * @throws Exception
   */
  private static void generateKeySpace (
    String pathToPackage,
    String packageName,
    final String jsonContent) throws Exception {
    
    
    KeyspaceJson keyspaceJson = KeyspaceJson.fromJsonString(jsonContent);
    
    packageName +=
      "."
      + CodeIdentifiersFormatterInl.lowerUnder(keyspaceJson.name);
    pathToPackage +=
      "/"
      + CodeIdentifiersFormatterInl.lowerUnder(keyspaceJson.name);
    
    FileWriterInl.mkdirs(pathToPackage, false);
    
    for (TableJson tableJson : keyspaceJson.tables) {
      
      generateTable(
        pathToPackage,
        packageName,
        keyspaceJson.name,
        tableJson);
    }
  }

  private static final String kTableStartCommentFormat =
    "/**\n"
    + " * \"First, solve the problem. Then, write the code. -John Johnson\"\n"
    + " * \"Or use Vangav M\"\n"
    + " * www.vangav.com\n"
    + " * */\n\n"
    
    + "/**\n"
    + " * no license, I know you already got more than enough to worry about\n"
    + " * keep going, never give up\n"
    + " * */\n\n"
    
    + "/**\n"
    + " * Community\n"
    + " * Facebook Group: Vangav Open Source - Backend\n"
    + " *   fb.com/groups/575834775932682/\n"
    + " * Facebook Page: Vangav\n"
    + " *   fb.com/vangav.f\n"
    + " * \n"
    + " * Third party communities for Vangav Backend\n"
    + " *   - play framework\n"
    + " *   - cassandra\n"
    + " *   - datastax\n"
    + " *   \n"
    + " * Tag your question online (e.g.: stack overflow, etc ...) with\n"
    + " *   #vangav_backend\n"
    + " *   to easier find questions/answers online\n"
    + " * */\n\n";
  private static final String kTablePackageFormat =
    "package %s;\n\n";
  private static final String kTableImportsFormat =
    "import com.datastax.driver.core.BoundStatement;\n"
    + "import com.datastax.driver.core.ResultSet;\n"
    + "import com.datastax.driver.core.ResultSetFuture;\n"
    + "import com.vangav.backend.cassandra.keyspaces.Query;\n"
    + "import com.vangav.backend.cassandra.keyspaces.Table;\n"
    + "import com.vangav.backend.cassandra.keyspaces.dispatch_message.QueryDispatchable;\n\n";
  private static final String kTableAuthorFormat =
    "/**\n"
    + " * GENERATED using JavaClientGeneratorMain.java\n"
    + " */\n";
  private static final String kTableClassCommentColumnFormat =
    " *   %s : %s\n";
  private static final String kTableClassCommentOrderByFormat =
    " *   %s : %s\n";
  private static final String kTableClassCommentQueryFormat =
    " *   - Name: %s\n"
    + " *   Description:\n"
    + " *     %s\n"
    + " *   Prepared Statement:\n"
    + " *     %s\n";
  private static final String kTableClassCommentFormat =
    "/**\n"
    + " * %s represents\n"
    + " *   Table [%s]\n"
    + " *   in Keyspace [%s]\n"
    + " * \n"
    + " * Name: %s\n"
    + " * Description:\n"
    + " *   %s\n"
    + " * \n"
    + " * Columns:\n"
    + "%s\n"
    + " * Partition Keys: %s\n"
    + " * Secondary Keys: %s\n"
    + " * Caching: %s\n"
    + " * Order By:\n"
    + "%s\n"
    + " * Queries:\n"
    + "%s"
    + " * */\n";
  private static final String kTableClassStartFormat =
    "public class %s extends Table {\n\n";
  private static final String kTableQueryMemberVariableFormat =
    "  /**\n"
    + "   * Query:\n"
    + "   * Name: %s\n"
    + "   * Description:\n"
    + "   *   %s\n"
    + "   * Prepared Statement:\n"
    + "   *   %s\n"
    + "   */\n"
    + "  private static final String k%sName =\n"
    + "    \"%s\";\n"
    + "  private static final String k%sDescription =\n"
    + "    \"%s\";\n"
    + "  private static final String k%sPreparedStatement =\n"
    + "    \"%s\";\n\n";
  private static final String kTableMemberVariablesFormat =
    "  private static final String kKeySpaceName =\n"
    + "    \"%s\";\n"
    + "  private static final String kTableName =\n"
    + "    \"%s\";\n\n"
    
    + "%s";
  private static final String kTableConstructorQueryArgumentFormat =
    ",\n      new Query (\n"
    + "        k%sDescription,\n"
    + "        k%sName,\n"
    + "        k%sPreparedStatement)";
  private static final String kTableConstructorFormat =
    "  /**\n"
    + "   * Constructor %s\n"
    + "   * @return new %s Object\n"
    + "   * @throws Exception\n"
    + "   */\n"
    + "  private %s () throws Exception {\n\n"
    
    + "    super (\n"
    + "      kKeySpaceName,\n"
    + "      kTableName%s);\n"
    + "  }\n\n";
  private static final String kTableInstanceVariableFormat =
    "  private static %s instance = null;\n\n";
  private static final String kTableLoadMethodFormat =
    "  /**\n"
    + "   * loadTable\n"
    + "   * OPTIONAL method\n"
    + "   * instance is created either upon calling this method or upon the first call\n"
    + "   *   to singleton instance method i\n"
    + "   * this method is useful for loading upon program start instead of loading\n"
    + "   *   it upon the first use since there's a small time overhead for loading\n"
    + "   *   since all queries are prepared synchronously in a blocking network\n"
    + "   *   operation with Cassandra's server\n"
    + "   * @throws Exception\n"
    + "   */\n"
    + "  public static void loadTable () throws Exception {\n\n"
    
    + "    if (instance == null) {\n\n"
    
    + "      instance = new %s();\n"
    + "    }\n"
    + "  }\n\n";
  private static final String kTableInstanceMethodFormat =
    "  /**\n"
    + "   * i\n"
    + "   * @return singleton static instance of %s\n"
    + "   * @throws Exception\n"
    + "   */\n"
    + "  public static %s i () throws Exception {\n\n"

    + "    if (instance == null) {\n\n"

    + "      instance = new %s();\n"
    + "    }\n\n"

    + "    return instance;\n"
    + "  }\n\n";
  // queries methods go here
  private static final String kTableClassEndFormat =
    "}\n";
  private static final String kCassandraJavaClientExt = ".java";
  /**
   * generateTable
   * generates a JAVA Client class for a Cassandra Table
   *   and writes its .java file
   * @param packageName
   * @param keyspaceName
   * @param tableJson
   * @throws Exception
   */
  private static void generateTable (
    String pathToPackage,
    String packageName,
    final String keyspaceName,
    final TableJson tableJson) throws Exception {
    
    String[] tableNameSplit = tableJson.name.split("_");
    
    String tableNameCamelCase =
      CodeIdentifiersFormatterInl.camelCase(true, tableNameSplit);
    
    StringBuffer stringBuffer = new StringBuffer();
    
    stringBuffer.append(
      kTableStartCommentFormat);
    
    stringBuffer.append(
      String.format(
        kTablePackageFormat,
        packageName) );
    
    stringBuffer.append(
      kTableImportsFormat);
    
    stringBuffer.append(
      kTableAuthorFormat);
    
    stringBuffer.append(
      String.format(
        kTableClassCommentFormat,
        tableNameCamelCase,
        tableJson.name,
        keyspaceName,
        tableJson.name,
        JavaFormatterInl.formatStringLength(
          tableJson.description,
          " *   ",
          false),
        getTableColumnsComment(tableJson),
        getTablePartitionKeysComment(tableJson),
        getTableSecondaryKeysComment(tableJson),
        CodeIdentifiersFormatterInl.upper(tableJson.caching),
        getTableOrderByComment(tableJson),
        getTableQueryComment(tableJson) ) );
    
    stringBuffer.append(
      String.format(
        kTableClassStartFormat,
        tableNameCamelCase) );
    
    stringBuffer.append(
      String.format(
        kTableMemberVariablesFormat,
        keyspaceName,
        tableJson.name,
        getTableQueriesMemeberVariables(tableJson) ) );
    
    stringBuffer.append(
      String.format(
        kTableConstructorFormat,
        tableNameCamelCase,
        tableNameCamelCase,
        tableNameCamelCase,
        getConstructorQueryArguments(tableJson) ) );
    
    stringBuffer.append(
      String.format(
        kTableInstanceVariableFormat,
        tableNameCamelCase) );
    
    stringBuffer.append(
      String.format(
        kTableLoadMethodFormat,
        tableNameCamelCase) );
    
    stringBuffer.append(
      String.format(
        kTableInstanceMethodFormat,
        tableNameCamelCase,
        tableNameCamelCase,
        tableNameCamelCase) );
    
    if (tableJson.queries != null) {
      
      for (final QueryJson queryJson : tableJson.queries) {
        
        stringBuffer.append(generateQueryMethods(queryJson) );
      }
    }
    
    stringBuffer.append(kTableClassEndFormat);
    
    String filePath =
      pathToPackage
      + "/"
      + tableNameCamelCase
      + kCassandraJavaClientExt;
    
    FileWriterInl.writeTextFile(
      stringBuffer.toString(),
      filePath,
      false);
  }
  
  /**
   * getTableColumnsComment
   * generates the Table class block commnet part for columns
   * @param tableJson
   * @return formatted String
   * @throws Exception
   */
  private static final String getTableColumnsComment (
    final TableJson tableJson) throws Exception {
    
    StringBuffer stringbuffer = new StringBuffer();
    
    for (ColumnJson columnJson : tableJson.columns) {
      
      stringbuffer.append(
        String.format(
          kTableClassCommentColumnFormat,
          columnJson.name,
          columnJson.type) );
    }
    
    return stringbuffer.toString();
  }
  
  /**
   * getTablePartitionKeysComment
   * generates the Table class block comment part for Primary Keys
   * @param tableJson
   * @return formatted String
   * @throws Exception
   */
  private static final String getTablePartitionKeysComment (
    final TableJson tableJson) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer("");
    
    if (tableJson.partition_keys != null) {
      
      for (int i = 0; i < tableJson.partition_keys.length; i ++) {
        
        stringBuffer.append(tableJson.partition_keys[i] );
        
        if (i < (tableJson.partition_keys.length - 1) ) {
          
          stringBuffer.append(", ");
        }
      }
    }
    
    return stringBuffer.toString();
  }
  
  /**
   * getTableSecondaryKeysComment
   * generates the Table class block comment part for Secondary Keys
   * @param tableJson
   * @return formatted String
   * @throws Exception
   */
  private static final String getTableSecondaryKeysComment (
    final TableJson tableJson) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer("");
    
    if (tableJson.secondary_keys != null) {
      
      for (int i = 0; i < tableJson.secondary_keys.length; i ++) {
        
        stringBuffer.append(tableJson.secondary_keys[i] );
        
        if (i < (tableJson.secondary_keys.length - 1) ) {
          
          stringBuffer.append(", ");
        }
      }
    }
    
    return stringBuffer.toString();
  }
  
  /**
   * getTableOrderByComment
   * generates the Table class block comment part for OrderBy
   * @param tableJson
   * @return formatted String
   * @throws Exception
   */
  private static final String getTableOrderByComment (
    final TableJson tableJson) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer();
    
    for (OrderByJson orderByJson : tableJson.order_by) {
      
      stringBuffer.append(
        String.format(
          kTableClassCommentOrderByFormat,
          orderByJson.column_name,
          orderByJson.order_type) );
    }
    
    return stringBuffer.toString();
  }
  
  /**
   * getTableQueryComment
   * generates the Table class block comment part for queries
   * @param tableJson
   * @return formatted String
   * @throws Exception
   */
  private static final String getTableQueryComment (
    final TableJson tableJson) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer();
    
    for (QueryJson queryJson : tableJson.queries) {
      
      stringBuffer.append(
        String.format(
          kTableClassCommentQueryFormat,
          queryJson.name,
          JavaFormatterInl.formatStringLength(
            queryJson.description,
            " *     ",
            false),
          JavaFormatterInl.formatStringLength(
            queryJson.prepared_statement,
            " *     ",
            false) ) );
    }
    
    return stringBuffer.toString();
  }
  
  /**
   * getTableQueriesMemeberVariables
   * generates a Table's Query member variable
   * @param tableJson
   * @return formatted String
   * @throws Exception
   */
  private static final String getTableQueriesMemeberVariables (
    final TableJson tableJson) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer();
    
    String camelCaseName;
    
    for (QueryJson queryJson : tableJson.queries) {
      
      camelCaseName =
        CodeIdentifiersFormatterInl.camelCase(true, queryJson.name);
      
      stringBuffer.append(
        String.format(
          kTableQueryMemberVariableFormat,
          queryJson.name,
          JavaFormatterInl.formatStringLength(
            queryJson.description,
            "   *   ",
            false),
          JavaFormatterInl.formatStringLength(
            queryJson.prepared_statement,
            "   *   ",
            false),
          camelCaseName,
          queryJson.name,
          camelCaseName,
          JavaFormatterInl.formatStringLength(
            queryJson.description,
            "    ",
            true),
          camelCaseName,
          JavaFormatterInl.formatStringLength(
            queryJson.prepared_statement,
            "    ",
            true),
          camelCaseName,
          camelCaseName,
          camelCaseName,
          camelCaseName,
          camelCaseName,
          camelCaseName) );
    }
    
    return stringBuffer.toString();
  }
  
  /**
   * getConstructorQueryArguments
   * generates constructor query arguments
   * @param tableJson
   * @return formatted String
   * @throws Exception
   */
  private static final String getConstructorQueryArguments (
    final TableJson tableJson) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer();
    
    for (QueryJson queryJson : tableJson.queries) {
      
      stringBuffer.append(
        String.format(
          kTableConstructorQueryArgumentFormat,
          CodeIdentifiersFormatterInl.camelCase(true, queryJson.name),
          CodeIdentifiersFormatterInl.camelCase(true, queryJson.name),
          CodeIdentifiersFormatterInl.camelCase(true, queryJson.name) ) );
    }
    
    return stringBuffer.toString();
  }
  
  private static final String kQueryStartCommentFormat =
    "  // Query: %s\n"
    + "  // Description:\n"
    + "  //   %s\n"
    + "  // Parepared Statement:\n"
    + "  //   %s\n\n";
  private static final String kQueryParamCommentFormat =
    "   * @param %s\n";
  private static final String kQueryParamMethodSignatureFormat =
    "Object %s";
  private static final String kQueryParamMethodSignatureSeparatorFormat =
    ",\n    ";
  private static final String kQueryArgumentFormat =
    "\n        %s";
  private static final String kQueryGetQueryMethodFormat =
    "  /**\n"
    + "   * getQuery%s\n"
    + "   * @return %s Query in the form of\n"
    + "   *           a Query Object\n"
    + "   * @throws Exception\n"
    + "   */\n"
    + "  public Query getQuery%s (\n"
    + "    ) throws Exception {\n\n"
    
    + "    return this.getQuery(k%sName);\n"
    + "  }\n\n";
  private static final String kQueryGetQueryDispatchableMethodFormat =
    "  /**\n"
    + "   * getQueryDispatchable%s\n"
    + "%s" // params?
    + "   * @return %s Query in the form of\n"
    + "   *           a QueryDisbatchable Object\n"
    + "   *           (e.g.: to be passed on to a worker instance)\n"
    + "   * @throws Exception\n"
    + "   */\n"
    + "  public QueryDispatchable getQueryDispatchable%s (\n"
    + "    %s) throws Exception {\n\n" // params?

    + "    return\n"
    + "      this.getQueryDispatchable(\n"
    + "        k%sName%s);\n" // name + params?
    + "  }\n\n";
  private static final String kQueryGetBoundStatementMethodFormat =
    "  /**\n"
    + "   * getBoundStatement%s\n"
    + "%s" // params?
    + "   * @return %s Query in the form of\n"
    + "   *           a BoundStatement ready for execution or to be added to\n"
    + "   *           a BatchStatement\n"
    + "   * @throws Exception\n"
    + "   */\n"
    + "  public BoundStatement getBoundStatement%s (\n"
    + "    %s) throws Exception {\n\n" // params?
    
    + "    return\n"
    + "      this.getQuery(k%sName).getBoundStatement(%s);\n" // params?
    + "  }\n\n";
  private static final String kQueryExecuteAsyncMethodFormat =
    "  /**\n"
    + "   * executeAsync%s\n"
    + "   * executes %s Query asynchronously\n"
    + "%s" // params?
    + "   * @return ResultSetFuture\n"
    + "   * @throws Exception\n"
    + "   */\n"
    + "  public ResultSetFuture executeAsync%s (\n"
    + "    %s) throws Exception {\n\n" // params?

    + "    return\n"
    + "      this.getQuery(k%sName).executeAsync(%s);\n" // params?
    + "  }\n\n";
  private static final String kQueryExecuteSyncMethodFormat =
    "  /**\n"
    + "   * executeSync%s\n"
    + "   * BLOCKING-METHOD: blocks till the ResultSet is ready\n"
    + "   * executes %s Query synchronously\n"
    + "%s" // params?
    + "   * @return ResultSet\n"
    + "   * @throws Exception\n"
    + "   */\n"
    + "  public ResultSet executeSync%s (\n"
    + "    %s) throws Exception {\n\n" // params?

    + "    return\n"
    + "      this.getQuery(k%sName).executeSync(%s);\n" // params?
    + "  }\n\n";
  /**
   * generateQueryMethods
   * @param queryJson
   * @return formatted String of JAVA methods for a Query
   * @throws Exception
   */
  private static final String generateQueryMethods (
    final QueryJson queryJson) throws Exception {
    
    final ArrayList<String> params =
      getPreparedStatementParams(queryJson.prepared_statement);
    
    final String queryParamComment =
      getQueryParamComment(params);
    final String queryParamMethodSignature =
      getQueryParamMethodSignature(params);
    final String queryArgumentsGetDispatchable =
      getQueryArguments(params, true);
    final String queryArguments =
      getQueryArguments(params, false);
    
    StringBuffer stringBuffer = new StringBuffer();
    
    String nameCamelCase =
      CodeIdentifiersFormatterInl.camelCase(true, queryJson.name);
    
    stringBuffer.append(
      String.format(
        kQueryStartCommentFormat,
        nameCamelCase,
        JavaFormatterInl.formatStringLength(
          queryJson.description,
          "  //   ",
          false),
        JavaFormatterInl.formatStringLength(
          queryJson.prepared_statement,
          "  //   ",
          false) ) );
    
    stringBuffer.append(
      String.format(
        kQueryGetQueryMethodFormat,
        nameCamelCase,
        nameCamelCase,
        nameCamelCase,
        nameCamelCase ) );
    
    stringBuffer.append(
      String.format(
        kQueryGetQueryDispatchableMethodFormat,
        nameCamelCase,
        queryParamComment,
        nameCamelCase,
        nameCamelCase,
        queryParamMethodSignature,
        nameCamelCase,
        queryArgumentsGetDispatchable) );
    
    stringBuffer.append(
      String.format(
        kQueryGetBoundStatementMethodFormat,
        nameCamelCase,
        queryParamComment,
        nameCamelCase,
        nameCamelCase,
        queryParamMethodSignature,
        nameCamelCase,
        queryArguments) );
    
    stringBuffer.append(
      String.format(
        kQueryExecuteAsyncMethodFormat,
        nameCamelCase,
        nameCamelCase,
        queryParamComment,
        nameCamelCase,
        queryParamMethodSignature,
        nameCamelCase,
        queryArguments) );
    
    stringBuffer.append(
      String.format(
        kQueryExecuteSyncMethodFormat,
        nameCamelCase,
        nameCamelCase,
        queryParamComment,
        nameCamelCase,
        queryParamMethodSignature,
        nameCamelCase,
        queryArguments) );
    
    return stringBuffer.toString();
  }
  
  /**
   * getPreparedStatementParams
   * extracts bind params (names) from a prepared statement
   * e.g.: for the following prepared statement
   *         insert into store.products (sku, price) values (:sku, :price);
   *       params would be
   *         sku and price (extracted from :sku and :price)
   * @param preparedStatement
   * @return formatted String
   * @throws Exception
   */
  private static final ArrayList<String> getPreparedStatementParams (
    String preparedStatement) throws Exception {
    
    ArrayList<String> result = new ArrayList<String>();
    
    if (preparedStatement == null || preparedStatement.length() == 0) {
      
      return result;
    }
    
    int index = 0;
    StringBuffer stringBuffer;
    
    while (index < preparedStatement.length() ) {
      
      if (preparedStatement.charAt(index) == ':' &&
          index < (preparedStatement.length() - 1) &&
          preparedStatement.charAt(index + 1) != ',' &&
          preparedStatement.charAt(index + 1) != ')' &&
          preparedStatement.charAt(index + 1) != ';' &&
          preparedStatement.charAt(index + 1) != ' ') {
        
        index += 1;
        
        stringBuffer = new StringBuffer();
        
        while (index < preparedStatement.length() ) {
          
          if (preparedStatement.charAt(index) == ',' ||
              preparedStatement.charAt(index) == ')' ||
              preparedStatement.charAt(index) == ';' ||
              preparedStatement.charAt(index) == ' ') {
            
            break;
          }
          
          stringBuffer.append(preparedStatement.charAt(index) );
          
          index += 1;
        }
        
        result.add(stringBuffer.toString() );
      }
      
      index += 1;
    }
    
    return result;
  }
  
  /**
   * getQueryParamComment
   * generates the param section of a query's method block comment, those
   *   params represent the bind params for the query's prepared statement
   * @param params
   * @return formatted String
   * @throws Exception
   */
  private static final String getQueryParamComment (
    final ArrayList<String> params) throws Exception {
    
    if (params.isEmpty() == true) {
      
      return "";
    }
    
    StringBuffer stringBuffer = new StringBuffer();
    
    for (String param : params) {
      
      stringBuffer.append(
        String.format(
          kQueryParamCommentFormat,
          CodeIdentifiersFormatterInl.lowerCamelCase(true, param) ) );
    }
    
    return stringBuffer.toString();
  }
  
  /**
   * getQueryParamMethodSignature
   * generates method param signature format for a query (used to define
   *   a query's method params to pass on the values to bind to the query's
   *   prepared statement)
   * @param params
   * @return formatted String
   * @throws Exception
   */
  private static final String getQueryParamMethodSignature (
    final ArrayList<String> params) throws Exception {
    
    if (params.isEmpty() == true) {
      
      return "";
    }
    
    StringBuffer stringBuffer = new StringBuffer();
    
    for (int i = 0; i < params.size(); i ++) {
      
      stringBuffer.append(
        String.format(
          kQueryParamMethodSignatureFormat,
          CodeIdentifiersFormatterInl.lowerCamelCase(true, params.get(i) ) ) );
      
      if (i < (params.size() - 1) ) {
        
        stringBuffer.append(
          kQueryParamMethodSignatureSeparatorFormat);
      }
    }
    
    return stringBuffer.toString();
  }
  
  /**
   * getQueryArguments
   * generates arguments format for a query (used to pass on prepared statement
   *   params as arguments to the Table parent class)
   * @param params
   * @return formatted String
   * @throws Exception
   */
  private static final String getQueryArguments (
    final ArrayList<String> params,
    final boolean isGetDispatchable) throws Exception {
    
    if (params.isEmpty() == true) {
      
      return "";
    }
    
    StringBuffer stringBuffer = new StringBuffer();
    
    if (isGetDispatchable == true) {
      
      stringBuffer.append(",");
    }
    
    for (int i = 0; i < params.size(); i ++) {
      
      stringBuffer.append(
        String.format(
          kQueryArgumentFormat,
          CodeIdentifiersFormatterInl.lowerCamelCase(true, params.get(i) ) ) );
      
      if (i < (params.size() - 1) ) {
        
        stringBuffer.append(",");
      }
    }
    
    return stringBuffer.toString();
  }
  
  private static final String kCassandraImport =
    "\nimport com.vangav.backend.cassandra.Cassandra;\n\n";
  /**
   * updateGlobalClass
   * updates Global.java:beforeStart to connect to cassandra and
   *   load tables (prepare queries)
   * @param configDirPath
   * @param projectDirPath
   * @param projectName
   * @param rootPackage
   * @throws Exception
   */
  private static void updateGlobalClass (
    final String configDirPath,
    final String projectDirPath,
    final String projectName,
    final String rootPackage,
    final boolean isUpdate) throws Exception {
    
    // load keyspaces' files
    Map<String, String> keyspaces =
      FileLoaderInl.loadTextFilesWithoutComments(
        CassandraGeneratorConstantsInl.kCassandraCommentPrefix,
        configDirPath,
        CassandraGeneratorConstantsInl.kCassandraKeyspaceExt);
    
    // no keyspaces? skip updating global class
    if (keyspaces.isEmpty() == true) {
      
      return;
    }

    // init new lines to be added

    ArrayList<String> updateImports = new ArrayList<String>();
    ArrayList<String> updateCodeLines = new ArrayList<String>();
    
      
    updateImports.add(kCassandraImport);
    updateCodeLines.add(
      "\n        Cassandra.connect();\n\n");
    
    String packageName =
      JavaFormatterInl.getPackageName(
        rootPackage,
        projectName,
        CassandraGeneratorConstantsInl.kCassandraPackageName);
    String currPackageName;
    
    KeyspaceJson currKeyspaceJson;

    String[] currTableNameSplit;// = tableJson.name.split("_");
    
    String currTableNameCamelCase;
      //CodeIdentifiersFormatterInl.camelCase(true, tableNameSplit);
    
    for (String keyspaceJsonRaw : keyspaces.values() ) {
      
      currKeyspaceJson = KeyspaceJson.fromJsonString(keyspaceJsonRaw);
      
      if (currKeyspaceJson.tables != null) {
        
        currPackageName =
          packageName
          + "."
          + CodeIdentifiersFormatterInl.lowerUnder(currKeyspaceJson.name)
          + ".*";
        
        // import keyspace
        updateImports.add(
          "import "
          + currPackageName
          + ";\n");
        
        for (TableJson tableJson : currKeyspaceJson.tables) {
          
          currTableNameSplit = tableJson.name.split("_");
          currTableNameCamelCase =
            CodeIdentifiersFormatterInl.camelCase(true, currTableNameSplit);
          
          // load table
          updateCodeLines.add(
            "        "
            + CodeIdentifiersFormatterInl.camelCase(
                true,
                currTableNameCamelCase)
            + ".loadTable();\n");
        }
      }
    }
    
    // load current Global Class (default)
    ArrayList<String> globalClassLines =
      FileLoaderInl.loadTextFileLines(
        projectDirPath
        + "/app/Global.java");
    
    ArrayList<String> newGlobalClassLines = new ArrayList<String>();
    
    // remove old cassandra code
    for (int i = 0; i < globalClassLines.size(); i ++) {
      
      if (globalClassLines.get(i).contains(
            "import com.vangav.backend.cassandra.Cassandra;") == true ||
          globalClassLines.get(i).contains(
            packageName) == true ||
          globalClassLines.get(i).contains(
            "Cassandra.connect()") == true ||
          globalClassLines.get(i).contains(
            ".loadTable();") == true ||
          globalClassLines.get(i).contains(
            "Cassandra.i().disconnect()") == true) {
        
        continue;
      }
      
      newGlobalClassLines.add(globalClassLines.get(i) );
    }
    
    // add imports
    for (int i = 0; i < newGlobalClassLines.size(); i ++) {
      
      if (newGlobalClassLines.get(i).startsWith("import") == true) {
        
        newGlobalClassLines.addAll(
          i + 1,
          updateImports);
        
        break;
      }
    }
    
    // add cassandra connect and load tables
    for (int i = 0; i < newGlobalClassLines.size(); i ++) {
      
      if (newGlobalClassLines.get(i).contains(
            "// cassandra connection") == true) {
        
        newGlobalClassLines.addAll(
          i + 1,
          updateCodeLines);
        
        break;
      }
    }
    
    // add cassandra disconnect
    for (int i = 0; i < newGlobalClassLines.size(); i ++) {
      
      if (newGlobalClassLines.get(i).startsWith(
            "      // e.g.: disconnect") == true) {
        
        newGlobalClassLines.add(
          i + 1,
          "\n      Cassandra.i().disconnect();\n");
        
        break;
      }
    }
    
    // write update Global.java
    FileWriterInl.writeTextFile(
      newGlobalClassLines,
      projectDirPath
      + "/app/Global.java",
      false,
      false);
  }
}