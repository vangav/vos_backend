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

package com.vangav.backend.backend_generator;

import java.io.File;
import java.util.ArrayList;

import com.vangav.backend.backend_client_java.clients_generator.ClientsGeneratorConstantsInl;
import com.vangav.backend.backend_client_java.clients_generator.ClientsGeneratorInl;
import com.vangav.backend.backend_client_java.clients_generator.ClientsVerifierInl;
import com.vangav.backend.cassandra.keyspaces_generator.CassandraGeneratorConstantsInl;
import com.vangav.backend.cassandra.keyspaces_generator.CassandraVerifierInl;
import com.vangav.backend.cassandra.keyspaces_generator.CqlScriptsGeneratorInl;
import com.vangav.backend.cassandra.keyspaces_generator.JavaClientGeneratorInl;
import com.vangav.backend.cassandra.keyspaces_generator.PhrictionGeneratorInl;
import com.vangav.backend.content.checking.JavaCodeVerifierInl;
import com.vangav.backend.content.formatting.CodeIdentifiersFormatterInl;
import com.vangav.backend.dispatcher.worker.worker_generator.WorkerGeneratorInl;
import com.vangav.backend.exceptions.VangavException;
import com.vangav.backend.files.FileLoaderInl;
import com.vangav.backend.files.FileWriterInl;
import com.vangav.backend.play_framework.controllers_generator.ControllersGeneratorConstantsInl;
import com.vangav.backend.play_framework.controllers_generator.ControllersGeneratorInl;
import com.vangav.backend.play_framework.controllers_generator.ControllersParserInl;
import com.vangav.backend.play_framework.controllers_generator.ControllersVerifierInl;
import com.vangav.backend.play_framework.controllers_generator.json.ControllersJson;
import com.vangav.backend.system.CommandLineInl;
import com.vangav.backend.system.InteractiveConsole;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * BackendGenerator handles creating a new backend based on vangav backend
 *   (vos_backend aka vangav open source backend)
 */
public class BackendGeneratorMain {
  
  // disable default instantiation
  private BackendGeneratorMain () {}
  
  /**
   * generateBackend
   * 1- creates play java project
   * 2- adds vos_backend.jar to the project's lib
   * * if config json files are present for controllers and cassandra?
   * *3- generates controllers code and properties files
   * *4- generates cassandra client code and properties files
   * 5- adds default classes/properties
   * 6- generates scripts for run, compile, etc ...
   * 7- optionally add a worker project
   * @param projectName
   * @throws Exception
   */
  private static void generateBackend (
    final String projectName) throws Exception {
    
    // check if a config directory exsits to rename it, to allow the creating
    //   of a new play project in a directory under the same name
    
    boolean hasConfig = false;
    
    if (FileLoaderInl.fileExists("../../" + projectName) == true) {
      
      if (InteractiveConsole.i().confirm(
            "Directory named ["
            + projectName
            + "] already exists,\n"
            + "confirm that this is the config directory for this "
            + "new project") == false) {
        
        System.out.println(
          "\nCancelled new project creation,\n"
          + "  can't create more than one project with the same name");
        System.exit(0);
      }
      
      if (FileLoaderInl.fileExists("../../config_" + projectName) == true) {
        
        if (InteractiveConsole.i().confirm(
              "Directory [config_"
              + projectName
              + "] already exists, overwite it?") == false) {
          
          System.out.println(
            "\nCancelled new project creation");
          System.exit(0);
        }
      }
      
      if (isConfigDir("../../" + projectName) == false) {
        
        System.err.println(
          "Invalid config directory [../../"
          + projectName
          + "]\n"
          + "a valid config directory contains 0 or 1 file named ["
          + ControllersGeneratorConstantsInl.kControllersConfigFileName
          + "] for controllers config\n"
          + "and 0 or more files named [*.keyspace] for keyspaces config\n"
          + "and 0 or more files named [*.client_java] for clients config\n"
          + "and no other files\n\n"
          
          + "Cancelling new project creation, fix the config directory and "
          + "try again ...\n");
        System.exit(0);
      }
      
      CommandLineInl.executeCommand(
        "mv ../../" + projectName + " ../../config_" + projectName);
      
      hasConfig = true;
    }
    
    // if has config is true, verify config files
    if (hasConfig == true) {
      
      verifyConfigFiles(projectName);
    }
    
    // write project generation script and execute it
    boolean isEclipseProject = generateNewPlayProject(projectName);
    
    // modify play generated content (add comments, suppress warnings, etc...)
    modifyPlayGeneratedContent(projectName);
    
    // add vangav m client
    addVangavMClient(projectName);
    
    // add cassandra keyspace updater
    
    String rootPackage;

    try {
      
      String controllersJsonRaw =
        FileLoaderInl.loadTextFileWithoutComments(
          ControllersGeneratorConstantsInl.kControllerCommentPrefix,
          "../../config_"
          + projectName
          + "/"
          + ControllersGeneratorConstantsInl.kControllersConfigFileName);
      
      ControllersJson controllersJson =
        ControllersJson.fromJsonString(controllersJsonRaw);
      
      rootPackage = ControllersParserInl.getJavaPackageName(controllersJson);
    } catch (Exception e) {
      
      rootPackage = null;
    }
    
    // add cassandra updater tool
    addCassandraKeyspacesUpdater(projectName, rootPackage);
    
    // add default classes/properties that should be in all play projects
    addDefaultsToPlayProject(projectName);
    
    // write run, compile, debug, clean and dist scripts
    addPlayProjectScripts(projectName);
    
    // check if this new project has a config and continue creation accordingly
    if (hasConfig == true) {
      
      applyNewPlayProjectConfig(projectName);
    } else {
      
      applyNewPlayProjectDefaultConfig(projectName);
    }
    
    // compile project
    FileWriterInl.writeTextFile(
      "#!/bin/bash\n\n"
        + "cd ../../"
        + projectName
        + "\n"
        + "../vos_backend/play-2.2.6/play compile &> /dev/null\n",
      "../../"
        + projectName
        + "/compile_project.sh",
      false);
    CommandLineInl.executeCommand(
      "chmod +x ../../"
      + projectName
      + "/compile_project.sh");
    
    CommandLineInl.executeCommand(
      "../../"
      + projectName
      + "/compile_project.sh");
    
    CommandLineInl.executeCommand(
      "rm ../../"
      + projectName
      + "/compile_project.sh");
    
    // finished creating project
    System.out.println(
      "\nFinished creating project ["
      + projectName
      + "]\n");
    
    // add a worker project?
    if (InteractiveConsole.i().confirm(
          "Generate worker ["
          + projectName
          + "_worker] for new project ["
          + projectName
          + "] ?") == true) {
      
      generateWorker(projectName, isEclipseProject);
    }
    
    // finished
    System.out.println(
      "\nDone, have fun!\n");
  }

  /**
   * isConfigDir
   * @param dirPath
   * @return true is param dirPath points to a valid config directory and
   *           false otherwise
   * @throws Exception
   */
  private static boolean isConfigDir (
    final String dirPath) throws Exception {
    
    // load controllers config files
    
    File[] controllerConfigFiles =
      FileLoaderInl.loadFiles(
        dirPath,
        ControllersGeneratorConstantsInl.kControllerConfigExt);
    
    if (controllerConfigFiles == null) {
      
      controllerConfigFiles = new File[0];
    }
    
    if (controllerConfigFiles.length > 1) {
      
      return false;
    }
    
    // load keyspaces config files
    
    File[] keyspaceConfigFiles =
      FileLoaderInl.loadFiles(
        dirPath,
        CassandraGeneratorConstantsInl.kCassandraKeyspaceExt);
    
    if (keyspaceConfigFiles == null) {
      
      keyspaceConfigFiles = new File[0];
    }
    
    // load clients config files
    
    File[] clientsConfigFiles =
      FileLoaderInl.loadFiles(
        dirPath,
        ClientsGeneratorConstantsInl.kClientConfigExt);
    
    if (clientsConfigFiles == null) {
      
      clientsConfigFiles = new File[0];
    }
    
    // check if a DS_Store file exists
    
    boolean hasDsStoreFile = false;
    
    if (FileLoaderInl.fileExists(dirPath + "/.DS_Store") == true) {
      
      hasDsStoreFile = true;
    }
    
    // load all files
    
    File[] allFiles = FileLoaderInl.loadFiles(dirPath);
    
    if (allFiles == null) {
      
      allFiles = new File[0];
    }
    
    if (hasDsStoreFile == false) {
    
      if (allFiles.length !=
          (controllerConfigFiles.length
           + keyspaceConfigFiles.length
           + clientsConfigFiles.length) ) {
        
        return false;
      }
    } else {
    
      if (allFiles.length !=
          (controllerConfigFiles.length
           + keyspaceConfigFiles.length
           + clientsConfigFiles.length
           + 1) ) {
        
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * verifyConfigFiles
   * verifies config files before generating a new project
   * @param projectName
   * @throws Exception
   */
  private static void verifyConfigFiles (
    final String projectName) throws Exception {
      
    // verify controllers.json
    try {
    
      ControllersVerifierInl.verifyControllersJson(
        "../../config_" + projectName,
        projectName);
    } catch (Exception e) {
      
      try {
    
        VangavException vangavException = (VangavException)e;
        
        System.err.println(
          "Config file [../../config_"
          + projectName
          + "/"
          + ControllersGeneratorConstantsInl.kControllersConfigFileName
          + "] is invalid because of:\n"
          + vangavException.toString() );
      } catch (Exception e2) {}

      System.err.println(
        "\nCancelling new project creation, fix the config file ["
        + ControllersGeneratorConstantsInl.kControllersConfigFileName
        + "] and try again ...\n\n"
        + "Stack trace:\n"
        + VangavException.getExceptionStackTrace(e) );
      System.exit(0);
    }
    
    // verify keyspaces config files *.keyspace
    try {
      
      CassandraVerifierInl.verifyKeyspacesConfig(
        "../../config_" + projectName);
    } catch (Exception e) {
      
      try {
      
        VangavException vangavException = (VangavException)e;
        
        System.err.println(
          "keyspace config is invalid because of:\n"
          + vangavException.toString() );
      } catch (Exception e2) {}

      System.err.println(
        "\nCancelling new project creation, fix keyspace config "
        + "and try again ...\n\n"
        + "Stack trace:\n"
        + VangavException.getExceptionStackTrace(e) );
      System.exit(0);
    }
    
    // verify clients config files *.client_java
    try {
      
      ClientsVerifierInl.verifyClientsJson(
        "../../config_" + projectName);
    } catch (Exception e) {
      
      try {
      
        VangavException vangavException = (VangavException)e;
        
        System.err.println(
          "client config is invalid because of:\n"
          + vangavException.toString() );
      } catch (Exception e2) {}

      System.err.println(
        "\nCancelling new project creation, fix client config "
        + "and try again ...\n\n"
        + "Stack trace:\n"
        + VangavException.getExceptionStackTrace(e) );
      System.exit(0);
    }
  }
  
  private static final String kPlayProjectCreationScriptFormat =
    "#!/bin/bash\n"
    + "cd ../..\n"
    + "echo -e \"%s\\n2\\n\" | ./vos_backend/play-2.2.6/play new %s &> /dev/null\n"
    + "cd %s\n"
    + "mkdir lib\n"
    + "cp ../vos_backend/dist/vos_backend.jar ./lib\n";
  private static final String kMakeEclipseProject =
    "../vos_backend/play-2.2.6/play eclipse &> /dev/null\n";
  /**
   * generateNewPlayProject
   * creates a new play project
   * @param projectName
   * @return true if it's an eclipse project and false otherwise
   * @throws Exception
   */
  private static boolean generateNewPlayProject (
    final String projectName) throws Exception {
    
    boolean isEclipseProject = false;
    
    // write project generation script and execute it
    
    String playProjectCreationScript =
      String.format(
        kPlayProjectCreationScriptFormat,
        projectName,
        projectName,
        projectName);
    
    if (InteractiveConsole.i().confirm(
          "Make project Eclipse compatible?") == true) {
      
      playProjectCreationScript += kMakeEclipseProject;
      
      isEclipseProject = true;
    }
    
    System.out.println(
      "Creating project ["
      + projectName
      + "], it will take 1-3 minutes to complete ...");
    
    FileWriterInl.writeTextFile(
      playProjectCreationScript,
      "./_vangav_play_project_creation_script.sh",
      false);
    
    CommandLineInl.executeMultipleCommands(
      "chmod +x _vangav_play_project_creation_script.sh",
      "./_vangav_play_project_creation_script.sh",
      "rm _vangav_play_project_creation_script.sh");
    
    return isEclipseProject;
  }
  
  /**
   * modifyPlayGeneratedContent
   * modify play generated content (add comments, suppress warnings, etc...)
   * @param projectName
   * @throws Exception
   */
  private static void modifyPlayGeneratedContent (
    final String projectName) throws Exception {
    
    // comment out the default route generated by play in conf/routes
    
    ArrayList<String> confRoutesLines =
      FileLoaderInl.loadTextFileLines(
        "../../"
        + projectName
        + "/conf/routes");
    
    String currLine;
    for (int i = 0; i < confRoutesLines.size(); i++) {
      
      currLine = confRoutesLines.get(i).trim();
      
      if (currLine.startsWith("GET") == true) {
        
        confRoutesLines.remove(i);
        confRoutesLines.add(i, "# " + currLine + "\n");
        confRoutesLines.add(i, "# Commented out route generated by play\n");
        
        break;
      }
    }
    
    FileWriterInl.writeTextFile(
      confRoutesLines,
      "../../"
        + projectName
        + "/conf/routes",
      false,
      false);

    // modifdy Application.java generated by play to emphasize that it's
    //   generated and not used
    
    ArrayList<String> applicationLines =
      FileLoaderInl.loadTextFileLines(
        "../../"
        + projectName
        + "/app/controllers/Application.java");
    
    for (int i = 0; i < applicationLines.size(); i ++) {
      
      if (applicationLines.get(i).startsWith("public") == true) {
        
        applicationLines.add(i,
          "/**\n"
          + " * This is the default controller generated by play\n"
          + " *   and is disabled in the conf/routes files\n"
          + " * Note: unless you know what you are doing, keep it here and\n"
          + " *         don't edit or use it\n"
          + " * */\n\n"

          + "@SuppressWarnings(\"unused\")\n");
        
        break;
      }
    }
    
    FileWriterInl.writeTextFile(
      applicationLines,
      "../../"
        + projectName
        + "/app/controllers/Application.java",
      false,
      false);
  }
  
  /**
   * addVangavMClient
   * adds vangav m client to newly generated project
   * @param projectName
   * @throws Exception
   */
  private static void addVangavMClient (
    final String projectName) throws Exception {
    
    FileWriterInl.mkdirs(
      "../../"
      + projectName
      + "/vangav_m/solutions",
      false);
    
    CommandLineInl.executeCommand(
      "cp assets/vangav_m_json_client.jar "
      + "../../"
      + projectName
      + "/vangav_m/vangav_m_json_client.jar");
  }
  
  /**
   * addCassandraKeyspacesUpdater
   * add cassandra keyspace updater to newly generated project
   * @param projectName
   * @param rootPackage
   * @throws Exception
   */
  private static void addCassandraKeyspacesUpdater (
    final String projectName,
    final String rootPackage) throws Exception {
    
    FileWriterInl.mkdirs(
      "../../"
      + projectName
      + "/cassandra_updater",
      false);
    
    CommandLineInl.executeCommand(
      "cp assets/cassandra_keyspaces_updater.jar "
      + "../../"
      + projectName
      + "/cassandra_updater/cassandra_keyspaces_updater.jar");
    
    ArrayList<String> propertiesFileLines =
      FileLoaderInl.loadTextFileLines(
        "assets/cassandra_updater_properties.prop");
    
    ArrayList<String> newPropertiesFileLines = new ArrayList<String>();
    
    for (int i = 0; i < propertiesFileLines.size(); i ++) {
      
      if (propertiesFileLines.get(i).contains("project_name=") == true) {
        
        newPropertiesFileLines.add("project_name=" + projectName);
      } else if (propertiesFileLines.get(i).contains("root_package") == true) {
        
        try {
          
          newPropertiesFileLines.add("root_package=" + rootPackage);
        } catch (Exception e) {
          
        }
      } else {
        
        newPropertiesFileLines.add(propertiesFileLines.get(i) );
      }
    }
    
    FileWriterInl.writeTextFile(
      newPropertiesFileLines,
      "../../"
        + projectName
        + "/cassandra_updater/cassandra_updater_properties.prop",
      false,
      false);
  }
  
  /**
   * addDefaultsToPlayProject
   * add default classes/properties like Global.java, etc ...
   * @param projectName
   * @throws Exception
   */
  private static void addDefaultsToPlayProject (
    final String projectName) throws Exception {
    
    // add default Global class
    
    ArrayList<String> defaultGlobalLines =
      FileLoaderInl.loadTextFileLines(
        "../src/com/vangav/backend/"
        + "backend_generator/defaults/Global.java");
    
    for (int i = 0; i < defaultGlobalLines.size(); i ++) {
      
      if (defaultGlobalLines.get(i).startsWith("package") == true) {
        
        defaultGlobalLines.remove(i);
        
        break;
      }
    }
    
    FileWriterInl.writeTextFile(
      defaultGlobalLines,
      "../../"
        + projectName
        + "/app/Global.java",
      false,
      false);
    
    // copy properties files
    
    FileWriterInl.mkdirs(
      "../../"
        + projectName
        + "/conf/prop",
      false);

    CommandLineInl.executeCommand(
      "cp ../prop/thread_pool_properties.prop ../../"
      + projectName
      + "/conf/prop/");
    CommandLineInl.executeCommand(
      "cp ../prop/param_validator_properties.prop ../../"
      + projectName
      + "/conf/prop/");
    CommandLineInl.executeCommand(
      "cp ../prop/dispatcher_properties.prop ../../"
      + projectName
      + "/conf/prop/");
    CommandLineInl.executeCommand(
      "cp ../prop/response_error_properties.prop ../../"
      + projectName
      + "/conf/prop/");
  }
  
  /**
   * addPlayProjectScripts
   * write run, compile, debug, clean and dist scripts
   * @param projectName
   * @throws Exception
   */
  private static void addPlayProjectScripts (
    final String projectName) throws Exception {
    
    // write run, compile, debug, clean and dist scripts
    
    // run
    FileWriterInl.writeTextFile(
      "#!/bin/bash\n\n"
        + "# used to run the service in dev mode on port 9000 or arg port\n"
        + "#   with live compiling on code change/save\n"
        + "#   for production, use dist.sh\n\n"
        
        + "port=9000\n\n"
        
        + "if [ \"$1\" -ne 0 ]\n"
        + "then\n"
        + "  port=\"$1\"\n"
        + "fi\n\n"
        
        + "echo \"using port $port\"\n\n"
        
        + "../vos_backend/play-2.2.6/play \"run $port\"\n",
      "../../"
        + projectName
        + "/_run.sh",
      false);
    CommandLineInl.executeCommand(
      "chmod +x ../../"
      + projectName
      + "/_run.sh");
    
    // compile
    FileWriterInl.writeTextFile(
      "#!/bin/bash\n\n"
        + "# used to compile the service\n"
        + "../vos_backend/play-2.2.6/play compile\n",
      "../../"
        + projectName
        + "/_compile.sh",
      false);
    CommandLineInl.executeCommand(
      "chmod +x ../../"
      + projectName
      + "/_compile.sh");
    
    // debug
    FileWriterInl.writeTextFile(
      "#!/bin/bash\n\n"
        + "# used to run the service in debug mode\n\n"
        + "../vos_backend/play-2.2.6/play debug\n",
      "../../"
        + projectName
        + "/_debug.sh",
      false);
    CommandLineInl.executeCommand(
      "chmod +x ../../"
      + projectName
      + "/_debug.sh");
    
    // clean
    FileWriterInl.writeTextFile(
      "#!/bin/bash\n\n"
          + "# used to clean the project's build\n\n"
        + "../vos_backend/play-2.2.6/play clean\n",
      "../../"
        + projectName
        + "/_clean.sh",
      false);
    CommandLineInl.executeCommand(
      "chmod +x ../../"
      + projectName
      + "/_clean.sh");
    
    // dist
    FileWriterInl.writeTextFile(
      "#!/bin/bash\n\n"
          + "# used to generate the production version\n# "
          + projectName + "-1.0-SNAPSHOT.zip\n"
          + "# @target/universal/ \n\n"
        + "../vos_backend/play-2.2.6/play dist\n",
      "../../"
        + projectName
        + "/_dist.sh",
      false);
    CommandLineInl.executeCommand(
      "chmod +x ../../"
      + projectName
      + "/_dist.sh");
    
    // eclipsify
    FileWriterInl.writeTextFile(
      "#!/bin/bash\n\n"
          + "# used to make the project eclipse-ready\n"
          + "  # (useful when cloning the project from a repo,\n"
          + "  # or when entering N for making the project\n"
          + "  #   eclipse-ready on generation)\n\n"
        + "../vos_backend/play-2.2.6/play eclipse\n",
      "../../"
        + projectName
        + "/_eclipsify.sh",
      false);
    CommandLineInl.executeCommand(
      "chmod +x ../../"
      + projectName
      + "/_eclipsify.sh");
  }
  
  /**
   * applyNewPlayProjectConfig
   * @param projectName
   * @throws Exception
   */
  private static void applyNewPlayProjectConfig (
    final String projectName) throws Exception {
    
    String configDirPath = "../../config_" + projectName;
    String projectDirPath = "../../" + projectName;
    
    // generate controllers
    ControllersGeneratorInl.generateControllers(
      configDirPath ,
      projectDirPath,
      projectName);
    
    // generate keyspaces' java clients
    
    String rootPackage;

    try {
      
      String controllersJsonRaw =
        FileLoaderInl.loadTextFileWithoutComments(
          ControllersGeneratorConstantsInl.kControllerCommentPrefix,
          configDirPath
          + "/"
          + ControllersGeneratorConstantsInl.kControllersConfigFileName);
      
      ControllersJson controllersJson =
        ControllersJson.fromJsonString(controllersJsonRaw);
      
      rootPackage = ControllersParserInl.getJavaPackageName(controllersJson);
    } catch (Exception e) {
      
      rootPackage = null;
    }
    
    JavaClientGeneratorInl.generateCassandraJavaClient(
      configDirPath,
      projectDirPath,
      projectName,
      rootPackage,
      false);
    
    // generate keyspaces' cql scripts
    CqlScriptsGeneratorInl.generateCassandraKeyspacesCqlScripts(
      configDirPath,
      projectDirPath);
    
    // generate keyspaces' phriction
    PhrictionGeneratorInl.generateCassandraKeyspacesPhriction(
      configDirPath,
      projectDirPath);
    
    // generate clients
    ClientsGeneratorInl.generateClients(
      configDirPath,
      projectDirPath,
      projectName,
      rootPackage);
    
    // move config into project
    CommandLineInl.executeCommand(
      "mv " + configDirPath
      + " ../../" + projectName + "/generator_config");
  }
  
  /**
   * applyNewPlayProjectDefaultConfig
   * @param projectName
   * @throws Exception
   */
  private static void applyNewPlayProjectDefaultConfig (
    final String projectName) throws Exception {

    // add default request properties file
    CommandLineInl.executeCommand(
      "cp ../prop/request_properties.prop ../../"
      + projectName
      + "/conf/prop/");
    
    String rootPackageName =
      CodeIdentifiersFormatterInl.lowerUnder(projectName);
    
    // add controllers directory
    FileWriterInl.mkdirs(
      "../../"
        + projectName
        + "/app/"
        + rootPackageName
        + "/controllers",
      false);
    
    // add default Common Handler
    
    ArrayList<String> defaultCommonHandlerLines =
      FileLoaderInl.loadTextFileLines(
        "../src/com/vangav/backend/"
        + "backend_generator/defaults/CommonPlayHandler.java");
    
    for (int i = 0; i < defaultCommonHandlerLines.size(); i ++) {
      
      if (defaultCommonHandlerLines.get(i).startsWith("package") == true) {
        
        defaultCommonHandlerLines.remove(i);
        defaultCommonHandlerLines.add(
          i,
          "package "
            + rootPackageName
            + ".controllers;\n");
        
        break;
      }
    }
    
    FileWriterInl.writeTextFile(
      defaultCommonHandlerLines,
      "../../"
        + projectName
        + "/app/"
        + rootPackageName
        + "/controllers/CommonHandler.java",
      false,
      false);
  }
  
  /**
   * generateWorker
   * generates a worker project
   * @param projectName
   * @param isEclipseProject
   * @throws Exception
   */
  private static void generateWorker (
    final String projectName,
    final boolean isEclipseProject) throws Exception {
    
    if (FileLoaderInl.fileExists("../../" + projectName + "_worker") == true) {
      
      if (InteractiveConsole.i().confirm(
            "Directory named ["
            + projectName
            + "_worker"
            + "] already exists,\n"
            + "Overwite it? (can't be undone)") == false) {
        
        System.out.println(
          "\nCancelled new worker project creation.");
        
        return;
      }
    }
    
    String javaPackageName;
    boolean addNotifications;

    try {
      
      String controllersJsonRaw =
        FileLoaderInl.loadTextFileWithoutComments(
          ControllersGeneratorConstantsInl.kControllerCommentPrefix,
          "../../"
          + projectName
          + "/generator_config/"
          + ControllersGeneratorConstantsInl.kControllersConfigFileName);
      
      ControllersJson controllersJson =
        ControllersJson.fromJsonString(controllersJsonRaw);
      
      javaPackageName =
        ControllersParserInl.getJavaPackageName(controllersJson);
      addNotifications =
        ControllersParserInl.getHasNotifications(controllersJson);
    } catch (Exception e) {
      
      javaPackageName = null;
      addNotifications = false;
    }
    
    boolean addCassandra;
    
    try {
      
      File[] keyspacesConfigFiles =
        FileLoaderInl.loadFiles(
          "../../"
          + projectName
          + "/generator_config/",
          CassandraGeneratorConstantsInl.kCassandraKeyspaceExt);
      
      if (keyspacesConfigFiles == null || keyspacesConfigFiles.length <= 0) {
        
        addCassandra = false;
      } else {
        
        addCassandra = true;
      }
    } catch (Exception e) {
      
      addCassandra = false;
    }
    
    WorkerGeneratorInl.generateWorker(
      projectName,
      javaPackageName,
      isEclipseProject,
      addNotifications,
      addCassandra);
  }

  private static final String kHelp =
    "\nUse `java -jar backend_generator.jar new project_name` to create\n"
    + "a new service's backend\n\n"
    
    + "  (e.g.: new social_network, new_geo_server, etc ...)\n"
    + "  in case of having json files describing the new service\n"
    + "  backend's controllers and/or database, those files\n"
    + "  should be put into a directory named project_name\n"
    + "  on the same level as vos_backend in the same workspace\n"
    + "  directory\n\n"
    
    + "Check out README.md and http://www.vangav.com for more info\n";
  /**
   * main
   * @param args: new project_name
   *                (e.g.: new social_network, new geo_server, etc ...)
   *                in case of having json files describing the new service
   *                backend's controllers and/or database, those files
   *                should be put into a directory named project_name
   *                on the same level as vos_backend in the same workspace
   *                directory
   * @throws Exception
   */
  public static void main (String[] args) throws Exception {
    
    if (args == null || args.length < 2) {
      
      System.err.println(kHelp);
      System.exit(0);
    }
    
    if (args[0].compareToIgnoreCase("new") != 0) {
      
      System.err.println(kHelp);
      System.exit(0);
    }
    
    System.out.println(
      "\n\n   o     o                                   \n"
      + "   8     8                                   \n"
      + "   8     8 .oPYo. odYo. .oPYo. .oPYo. o    o \n"
      + "   `b   d' .oooo8 8' `8 8    8 .oooo8 Y.  .P \n"
      + "    `b d'  8    8 8   8 8    8 8    8 `b..d' \n"
      + "     `8'   `YooP8 8   8 `YooP8 `YooP8  `YP'  \n"
      + "   :::..::::.....:..::..:....8 :.....:::...::\n"
      + "   :::::::::::::::::::::::ooP'.::::::::::::::\n"
      + "   :::::::::::::::::::::::...::::::::::::::::\n\n\n"
      
      + " .oPYo.               8                        8 \n"
      + " 8   `8               8                        8 \n"
      + "o8YooP' .oPYo. .oPYo. 8  .o  .oPYo. odYo. .oPYo8 \n"
      + " 8   `b .oooo8 8    ' 8oP'   8oooo8 8' `8 8    8 \n"
      + " 8    8 8    8 8    . 8 `b.  8.     8   8 8    8 \n"
      + " 8oooP' `YooP8 `YooP' 8  `o. `Yooo' 8   8 `YooP' \n"
      + ":......::.....::.....:..::...:.....:..::..:.....:\n"
      + ":::::::::::::::::::::::::::::::::::::::::::::::::\n"
      + ":::::::::::::::::::::::::::::::::::::::::::::::::\n\n\n");
    
    System.out.println(
      "Vangav Backend Generator\n"
      + "www.vangav.com\n\n"
      + "Vangav Backend\n"
      + "[back-the-end]\n\n");
    
    JavaCodeVerifierInl.verifyIdentifier("project name", args[1]);
    
    generateBackend(args[1] );
  }
}
