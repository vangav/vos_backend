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

package com.vangav.backend.dispatcher.worker.worker_generator;

import java.util.ArrayList;

import com.vangav.backend.content.formatting.JavaFormatterInl;
import com.vangav.backend.files.FileLoaderInl;
import com.vangav.backend.files.FileWriterInl;
import com.vangav.backend.system.CommandLineInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * BackendGenerator handles creating a new backend based on vangav backend
 *   (vos_backend aka vangav open source backend)
 */
public class WorkerGeneratorInl {
  
  //disable default instantiation
  private WorkerGeneratorInl () {}
  
  private static final String kWorkerProjectSuffix = "_worker";
  
  /**
   * generateBackend
   * 1- creates play java project
   * 2- adds vos_backend.jar to the project's lib
   * * if config json files are present for controllers and cassandra?
   * *3- generates controllers code and properties files
   * *4- generates cassandra client code and properties files
   * 5- adds default classes/properties
   * 6- generates scripts for run, compile, etc ...
   * @param projectName
   * @throws Exception
   */
  public static void generateWorker (
    String projectName,
    final String javaPackageName,
    final boolean isEclipseProject,
    final boolean addNotifications,
    final boolean addCassandra) throws Exception {
    
    projectName += kWorkerProjectSuffix;
    
    // write project generation script and execute it
    generateNewPlayProject(
      projectName,
      isEclipseProject);
    
    // modify play generated content (add comments, suppress warnings, etc...)
    modifyPlayGeneratedContent(projectName);
    
    // add vangav m client
    addVangavMClient(projectName);
    
    // add default classes/properties that should be in all play projects
    addDefaultsToPlayProject(
      projectName,
      javaPackageName,
      addNotifications,
      addCassandra);
    
    // write run, compile, debug, clean and dist scripts
    addPlayProjectScripts(projectName);
    
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
    
    // finished
    System.out.println(
      "\nFinished creating worker project ["
      + projectName
      + "]\n");
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
   * @throws Exception
   */
  private static void generateNewPlayProject (
    final String projectName,
    final boolean isEclipseProject) throws Exception {
    
    // write project generation script and execute it
    
    String playProjectCreationScript =
      String.format(
        kPlayProjectCreationScriptFormat,
        projectName,
        projectName,
        projectName);
    
    if (isEclipseProject == true) {
      
      playProjectCreationScript += kMakeEclipseProject;
    }
    
    System.out.println(
      "Creating worker project ["
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
   * addDefaultsToPlayProject
   * add default classes/properties like Global.java, etc ...
   * @param projectName
   * @throws Exception
   */
  private static void addDefaultsToPlayProject (
    final String projectName,
    final String javaPackageName,
    final boolean addNotifications,
    final boolean addCassandra) throws Exception {
    
    // add default Global class
    
    ArrayList<String> defaultGlobalLines =
      FileLoaderInl.loadTextFileLines(
        "../src/com/vangav/backend/"
        + "dispatcher/worker/worker_generator/defaults/Global.java");
    
    for (int i = 0; i < defaultGlobalLines.size(); i ++) {
      
      if (defaultGlobalLines.get(i).startsWith("package") == true) {
        
        defaultGlobalLines.remove(i);
        
        break;
      }
    }
    
    if (addCassandra == true) {
      
      for (int i = 0; i < defaultGlobalLines.size(); i ++) {
        
        if (defaultGlobalLines.get(i).startsWith(
              "import com.vangav.backend.properties") == true) {
          
          defaultGlobalLines.add(
            i + 1,
            "import com.vangav.backend.cassandra.Cassandra;\n");
        } else if (defaultGlobalLines.get(i).contains(
                     "// cassandra connection") == true) {
          
          defaultGlobalLines.add(
            i + 1,
            "\n        Cassandra.connect();\n");
        } else if (defaultGlobalLines.get(i).contains(
                    "// e.g.: disconnect") == true) {
                  
          defaultGlobalLines.add(
            i + 1,
            "      Cassandra.i().disconnect();\n");
        }
      }
    }
    
    FileWriterInl.writeTextFile(
      defaultGlobalLines,
      "../../"
        + projectName
        + "/app/Global.java",
      false,
      false);
    
    // add worker controller directory
    
    String workerControllerDirPath =
      JavaFormatterInl.getPathToPackage(
        "../../"
          + projectName,
        JavaFormatterInl.kPlaySrcDirName,
        javaPackageName,
        projectName,
        "controllers",
        "worker");
    
    FileWriterInl.mkdirs(
      workerControllerDirPath,
      false);
    
    String workerControllerPackageName =
      JavaFormatterInl.getPackageName(
        javaPackageName,
        projectName,
        "controllers",
        "worker");
    
    // add controller class
    
    ArrayList<String> controllerLines =
      FileLoaderInl.loadTextFileLines(
        "../src/com/vangav/backend/"
        + "dispatcher/worker/worker_generator/defaults/ControllerWorker.java");
    
    for (int i = 0; i < controllerLines.size(); i ++) {
      
      if (controllerLines.get(i).startsWith("package") == true) {
        
        controllerLines.remove(i);
        controllerLines.add(
          i,
          "package "
            + workerControllerPackageName
            + ";\n");
        
        break;
      }
    }
    
    FileWriterInl.writeTextFile(
      controllerLines,
      workerControllerDirPath
        + "/ControllerWorker.java",
      false,
      false);
    
    // add handler class
    
    ArrayList<String> handlerLines =
      FileLoaderInl.loadTextFileLines(
        "../src/com/vangav/backend/"
        + "dispatcher/worker/worker_generator/defaults/HandlerWorker.java");
    
    for (int i = 0; i < handlerLines.size(); i ++) {
      
      if (handlerLines.get(i).startsWith("package") == true) {
        
        handlerLines.remove(i);
        handlerLines.add(
          i,
          "package "
            + workerControllerPackageName
            + ";\n");
        
        break;
      }
    }
    
    FileWriterInl.writeTextFile(
      handlerLines,
      workerControllerDirPath
        + "/HandlerWorker.java",
      false,
      false);
    
    // modify routes
    
    ArrayList<String> confRoutesLines =
      FileLoaderInl.loadTextFileLines(
        "../../"
        + projectName
        + "/conf/routes");

    confRoutesLines.add(
      "\n\n"
      + "# Worker route\n"
      + "POST /Worker       "
      + workerControllerPackageName
      + ".ControllerWorker.postWorker()\n");
    
    FileWriterInl.writeTextFile(
      confRoutesLines,
      "../../"
        + projectName
        + "/conf/routes",
      false,
      false);

    // copy lib files
    if (addCassandra == true) {
      
      CommandLineInl.executeCommand(
        "cp ../lib/cassandra-driver-core-2.1.4.jar "
        + "../../"
        + projectName
        + "/lib/cassandra-driver-core-2.1.4.jar");
      
      CommandLineInl.executeCommand(
        "cp ../lib/metrics-core-3.0.2.jar "
        + "../../"
        + projectName
        + "/lib/metrics-core-3.0.2.jar");
    }
    
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
    
    if (addNotifications == true) {

      CommandLineInl.executeCommand(
        "cp ../prop/android_notification_properties.prop ../../"
        + projectName
        + "/conf/prop/");
      CommandLineInl.executeCommand(
        "cp ../prop/apple_notification_properties.prop ../../"
        + projectName
        + "/conf/prop/");
    }
    
    if (addCassandra == true) {

      CommandLineInl.executeCommand(
        "cp ../prop/cassandra_properties.prop ../../"
        + projectName
        + "/conf/prop/");
    }
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
        + "# used to run the service in dev mode on port 8000 or arg port\n"
        + "#   with live compiling on code change/save\n"
        + "#   for production, use dist.sh\n\n"
        
        + "port=8000\n\n"
        
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
}
