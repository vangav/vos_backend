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

import java.util.Properties;

import com.vangav.backend.content.formatting.JavaFormatterInl;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;
import com.vangav.backend.files.FileLoaderInl;
import com.vangav.backend.system.CommandLineInl;
import com.vangav.backend.system.InteractiveConsole;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * KeyspacesUpdaterMain is used to update a backend's database (JAVA clients,
 *   CQL scripts and phriction wiki).
 * This is useful when a backend is mid-way and the database needs to be
 *   updated (new queries, rows, tables, etc ...); then only the keyspaces
 *   config json files needs to be updated and this updated takes care of
 *   update the java client, cql scripts and phriction wiki while keeping
 *   the rest of the backend's project as it's
 * */
public class KeyspacesUpdaterMain {

  private static final String kPropertiesFilePath =
    "./cassandra_updater_properties.prop";
  private static final String kConfigDirPathProperty = "config_dir_path";
  private static final String kProjectDirPathProperty = "project_dir_path";
  private static final String kProjectNameProperty = "project_name";
  private static final String kRootPackageProperty = "root_package";
  
  /**
   * updateCassandraKeyspaces
   * handles loading the updater properties file and verifying it
   * @throws Exception
   */
  private static void updateCassandraKeyspaces () throws Exception {
    
    // load properties
    
    Properties updaterProperties =
      FileLoaderInl.loadPropertiesFile(kPropertiesFilePath);
    
    String configDirPath =
      updaterProperties.getProperty(kConfigDirPathProperty, "");
    String projectDirPath =
      updaterProperties.getProperty(kProjectDirPathProperty, "");
    String projectName =
      updaterProperties.getProperty(kProjectNameProperty, "");
    String rootPackage =
      updaterProperties.getProperty(kRootPackageProperty, "");
    
    // verify properties
    
    ArgumentsInl.checkNotEmpty(
      "Properties file ["
      + kPropertiesFilePath
      + "] property ["
      + kConfigDirPathProperty
      + "]",
      configDirPath,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "Properties file ["
      + kPropertiesFilePath
      + "] property ["
      + kProjectDirPathProperty
      + "]",
      projectDirPath,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "Properties file ["
      + kPropertiesFilePath
      + "] property ["
      + kProjectNameProperty
      + "]",
      projectName,
      ExceptionType.CODE_EXCEPTION);
    

    // update cassandra keyspaces
    updateCassandraKeyspaces(
      configDirPath,
      projectDirPath,
      projectName,
      rootPackage);
  }

  /**
   * updateCassandraKeyspaces
   * handles updating the backend project's cassandra
   * @param configDirPath
   * @param projectDirPath
   * @param projectName
   * @param rootPackage
   * @throws Exception
   */
  private static void updateCassandraKeyspaces (
    final String configDirPath,
    final String projectDirPath,
    final String projectName,
    final String rootPackage) throws Exception {
    
    // verify cassandra json config files
    CassandraVerifierInl.verifyKeyspacesConfig(configDirPath);
    
    // delete current cassandra java clients, cql scripts and phriction wiki
    
    String pathToPackage =
      JavaFormatterInl.getPathToPackage(
        projectDirPath,
        JavaFormatterInl.kPlaySrcDirName,
        rootPackage,
        projectName,
        CassandraGeneratorConstantsInl.kCassandraPackageName);
    
    String cassandraAssetsPath =
      projectDirPath
      + "/"
      + CassandraGeneratorConstantsInl.kCassandraAssetsDirName;
    
    if (InteractiveConsole.i().confirm(
          "Confirm [Y] to start updating process to replace current "
          + "clients, cql scripts and phriction wiki at the following directories\n"
          + "["
          + pathToPackage
          + "]\n"
          + "["
          + cassandraAssetsPath
          + "]\n"
          + "(can't be undone!)") == false) {
      
      System.out.println(
        "\nUpdate Cassandra keyspaces cancelled\n\n"
        + "Have a great day!\n");
      
      System.exit(0);
    }
    
    CommandLineInl.executeCommand(
      "rm -r -f "
      + pathToPackage);
    CommandLineInl.executeCommand(
      "rm -r -f "
      + cassandraAssetsPath);
    
    // start updating
    
    System.out.println(
      "\nUpdating cassandra, it will take about 1 minute to complete ...\n");
    
    JavaClientGeneratorInl.generateCassandraJavaClient(
      configDirPath,
      projectDirPath,
      projectName,
      rootPackage,
      true);
    
    CqlScriptsGeneratorInl.generateCassandraKeyspacesCqlScripts(
      configDirPath,
      projectDirPath);
    
    PhrictionGeneratorInl.generateCassandraKeyspacesPhriction(
      configDirPath,
      projectDirPath);
    
    // finished
    System.out.println(
      "\nDone, have fun!\n");
  }
  
  /**
   * main
   * @param args - no arguments needed
   * @throws Exception
   */
  public static void main (String[] args) throws Exception {
    
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
      "Vangav Cassandra Updater\n"
      + "www.vangav.com\n\n"
      + "Vangav Backend\n"
      + "[back-the-end]\n\n");
    
    updateCassandraKeyspaces();
  }
}
