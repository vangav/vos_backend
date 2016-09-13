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

package com.vangav.backend.vangav_m.json_client;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.vangav.backend.exceptions.VangavException;
import com.vangav.backend.files.FileLoaderInl;
import com.vangav.backend.files.FileWriterInl;
import com.vangav.backend.networks.rest_client.RestSyncInl;
import com.vangav.backend.system.CommandLineInl;
import com.vangav.backend.vangav_m.json_client.json.VangavMSolutionJson;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * IMPORTANT: this client is tuned to work with generated Vangav Backends
 *              and Workers
 *            for a more generic client check out www.vangav.com
 * This client is compiled into a jar under
 *   tools_bin/assets/vangav_m_json_client.jar
 *   and is automatically added to newly generated Vangav Backends and Workers
 * VangavMJsonClientMain handles generating Vangav M solutions through the
 *   following steps
 * 1- load available solutions
 * 2- verify loaded solutions
 * 3- clear old generated solutions
 * 4- generate solutions
 * 5- extract generated solutions
 * 6- clear old class path links in .classpath
 * 7- link solutions to .classpath
 * 8- clean
 * */
public class VangavMJsonClientMain {
  
  private static final String kVangavMApiLink =
    "http://mapi.vangav.com/Solution";
  
  private static final char kCommentLinePrefix =
    '#';
  private static final String kSolutionsDir =
    "solutions";
  private static final String kGeneratedSolutions =
    "solutions/generated_solutions";
  private static final String kGeneratedSolutionsTmp =
    "solutions/tmp_generated_solutions";
  private static final String kMlangExt =
    ".mlang";
  private static final String kSolutionPrefix =
    "vangav_m_";
  private static final String kZipExt =
    ".zip";
  private static final String kClassPathFilePath =
    "../.classpath";
  private static final String kExtractionScriptFormat =
    "#!/bin/bash\n\n"
    + "cd "
    + kGeneratedSolutions
    + "\n\n"
    + "unzip %s\n";
  private static final String kExtractionScriptPath =
    kGeneratedSolutions
    + "/solution_extraction.sh";
  private static final String kJarExt =
    ".jar";
  private static final String kLibsPath =
    "../lib";
  private static final String kClassPathEntryFormat =
    "  <classpathentry kind=\"lib\" path=\"lib/vangav_m_%s.jar\"/>\n";
  
  /**
   * generateVangavMSolutions
   * @throws Exception
   */
  private static void generateVangavMSolutions () throws Exception {
    
    // 1/2- load and verify available solutions
    
    // file name --> file content
    Map<String, String> solutionsFiles =
      FileLoaderInl.loadTextFilesWithoutComments(
        kCommentLinePrefix,
        kSolutionsDir,
        kMlangExt);
    
    // solution name --> solution json
    Map<String, VangavMSolutionJson> solutions =
      new HashMap<String, VangavMSolutionJson>();
    // solution name --> solution file name
    Map<String, String> solutionsFileNames =
      new HashMap<String, String>();
    
    VangavMSolutionJson currSolution;
    
    System.out.println(
      "Loading and verifying solutions ...\n");
    
    for (String fileName : solutionsFiles.keySet() ) {
      
      // parse solution into JSON Object
      currSolution =
        (VangavMSolutionJson)new VangavMSolutionJson().fromJsonString(
          solutionsFiles.get(fileName) );
      
      try {
        
        // verify solution's json
        currSolution.verify();
      } catch (Exception e) {
        
        try {
          
          VangavException vangavException = (VangavException)e;
          
          System.err.println(
            "Solution file ["
            + fileName
            + "] is invalid because of:\n"
            + vangavException.toString() );
        } catch (Exception e2) {}
        
        System.err.println(
          "\nCancelling Vangav M solution's generation, fix solution file ["
          + fileName
          + "] and try again ....\n");
        System.exit(0);
      }
      
      // duplicate solution name?
      if (solutions.containsKey(currSolution.name) == true) {
        
        System.err.println(
          "Solution name ["
          + currSolution.name
          + "] is duplicate in solutions files ["
          + solutionsFileNames.get(currSolution.name)
          + "] and ["
          + fileName
          + "]. Solutions names must be unique per solution, fix that and "
          + "try again ....\n");
        System.exit(0);
      }
      
      // solution verified, store it in memory
      solutions.put(currSolution.name, currSolution);
      solutionsFileNames.put(currSolution.name, fileName);
    }

    // 3- clear old generated solutions
    
    if (FileLoaderInl.fileExists(kGeneratedSolutions) == false) {
      
      FileWriterInl.mkdirs(
        kGeneratedSolutions,
        false);
    }
    
    if (FileLoaderInl.fileExists(kGeneratedSolutionsTmp) == false) {
      
      FileWriterInl.mkdirs(
        kGeneratedSolutionsTmp,
        false);
    }
    
    CommandLineInl.executeCommand(
      "rm -r -f "
      + kGeneratedSolutionsTmp);
    CommandLineInl.executeCommand(
      "mv "
      + kGeneratedSolutions
      + " "
      + kGeneratedSolutionsTmp);
    
    // 4- generate solutions
    
    // make generated solutions directory
    FileWriterInl.mkdirs(
      kGeneratedSolutions,
      false);
    
    URLConnection currURLConnection;
    String currSolutionPath;
    
    for (String solutionName : solutions.keySet() ) {
      
      System.out.println(
        "Generating solution ["
        + solutionName
        + "] @["
        + solutionsFileNames.get(solutionName)
        + "] ...");
      
      currURLConnection =
        RestSyncInl.restCall(
          kVangavMApiLink,
          solutions.get(solutionName) );
      
      if (RestSyncInl.isResponseStatusSuccess(currURLConnection) == false) {
        
        resetSolutionsGeneration();
        
        System.err.println(
          "Failed to generate solution [name: "
          + solutionName
          + "] from solution file ["
          + solutionsFileNames.get(solutionName)
          + "], API returned response status code ["
          + RestSyncInl.getResponseStatus(currURLConnection)
          + "]. Cancelling Vangav M solution's generation, fix problematic "
          + "solution and try again ....\n");
        System.exit(0);
      }
      
      currSolutionPath =
        kGeneratedSolutions
        + "/"
        + kSolutionPrefix
        + solutionName
        + kZipExt;
      
      try {
        
        RestSyncInl.writeResponseFile(
          currSolutionPath,
          currURLConnection,
          false);
      } catch (Exception e) {
        
        resetSolutionsGeneration();
        
        System.err.println(
          "Failed to generate solution [name: "
          + solutionName
          + "] from solution file ["
          + solutionsFileNames.get(solutionName)
          + "], failed to write solution file ["
          + currSolutionPath
          + "]. Cancelling Vangav M solution's generation, fix problematic "
          + "solution and try again ....\n\n"
          + "Exception stack trace:\n"
          + VangavException.getExceptionStackTrace(e) );
        System.exit(0);
      }
    }
    
    // 5- extract generated solutions
    
    String currSolutionZipFileName;
    String currSolutionZipFilePath;
    String currSolutionJarPath;
    String currSolutionJarDestPath;

    // delete current vangav m solutions' lib jars
    
    File[] libFiles =
      FileLoaderInl.loadFiles(
        kLibsPath,
        kJarExt);
    
    if (libFiles != null) {
      
      for (File libFile : libFiles) {
        
        if (libFile.getName().contains(kSolutionPrefix) == true) {
          
          libFile.delete();
        }
      }
    }
    
    // extract solutions and copy jars to lib directory
    for (String solutionName : solutions.keySet() ) {
      
      currSolutionZipFileName =
        kSolutionPrefix
        + solutionName
        + kZipExt;
      
      // write extraction script
      FileWriterInl.writeTextFile(
        String.format(
          kExtractionScriptFormat,
          currSolutionZipFileName),
        kExtractionScriptPath,
        false);
      
      // make extraction script executable
      CommandLineInl.executeCommand(
        "chmod +x "
        + kExtractionScriptPath);
      
      // execute extraction script
      CommandLineInl.executeCommand(kExtractionScriptPath);
      
      // delete script file
      CommandLineInl.executeCommand(
        "rm "
        + kExtractionScriptPath);
      
      currSolutionZipFilePath =
        kGeneratedSolutions
        + "/"
        + kSolutionPrefix
        + solutionName
        + kZipExt;
      
      // delete solution's zip file
      CommandLineInl.executeCommand(
        "rm "
        + currSolutionZipFilePath);
      
      // copy solution's jar to project's lib directory
      
      currSolutionJarPath =
        kGeneratedSolutions
        + "/"
        + kSolutionPrefix
        + solutionName
        + "/"
        + kSolutionPrefix
        + solutionName
        + kJarExt;
      
      currSolutionJarDestPath =
        kLibsPath
        + "/"
        + kSolutionPrefix
        + solutionName
        + kJarExt;
      
      CommandLineInl.executeCommand(
        "cp "
        + currSolutionJarPath
        + " "
        + currSolutionJarDestPath);
    }
    
    // 6- clear old class path links in .classpath
    
    ArrayList<String> classPathLines =
      FileLoaderInl.loadTextFileLines(kClassPathFilePath);
    
    ArrayList<String> classPathLinesNew = new ArrayList<String>();
    
    for (int i = 0; i < classPathLines.size(); i ++) {
      
      if (classPathLines.get(i).contains(
            "path=\"lib/vangav_m_") == false) {
        
        classPathLinesNew.add(classPathLines.get(i) );
      }
    }
    
    // 7- link solutions to .classpath
    
    System.out.println(
      "\nLinking solutions ...");
    
    ArrayList<String> classPathNewLinks = new ArrayList<String>();
    
    for (String solutionName : solutions.keySet() ) {
      
      classPathNewLinks.add(
        String.format(
          kClassPathEntryFormat,
          solutionName) );
    }
    
    for (int i = 0; i < classPathLinesNew.size(); i ++) {
      
      if (classPathLinesNew.get(i).contains(
            "kind=\"src\" output=\".target\" path=\"test\"/>") == true) {
        
        classPathLinesNew.addAll(
          i + 1,
          classPathNewLinks);
        
        break;
      }
    }
    
    FileWriterInl.writeTextFile(
      classPathLinesNew,
      kClassPathFilePath,
      false,
      false);
    
    // 8- clean
    CommandLineInl.executeCommand(
      "rm -r -f "
      + kGeneratedSolutionsTmp);
    
    System.out.println(
      "\nFinished, have fun!\n");
  }
  
  /**
   * resetSolutionsGeneration
   * used when solutions generation fails midway
   * @throws Exception
   */
  private static void resetSolutionsGeneration () throws Exception {

    CommandLineInl.executeCommand(
      "rm -r -f "
      + kGeneratedSolutions);
    CommandLineInl.executeCommand(
      "mv "
      + kGeneratedSolutionsTmp
      + " "
      + kGeneratedSolutions);
  }

  /**
   * main
   * @param args - no arguments needed
   * @throws Exception
   */
  public static void main (String[] args) throws Exception {
    
    System.out.println(
      "\n\no     o                                     o     o \n"
      + "8     8                                     8b   d8 \n"
      + "8     8 .oPYo. odYo. .oPYo. .oPYo. o    o   8`b d'8 \n"
      + "`b   d' .oooo8 8' `8 8    8 .oooo8 Y.  .P   8 `o' 8 \n"
      + " `b d'  8    8 8   8 8    8 8    8 `b..d'   8     8 \n"
      + "  `8'   `YooP8 8   8 `YooP8 `YooP8  `YP'    8     8 \n"
      + ":::..::::.....:..::..:....8 :.....:::...::::..::::..\n"
      + ":::::::::::::::::::::::ooP'.::::::::::::::::::::::::\n"
      + ":::::::::::::::::::::::...::::::::::::::::::::::::::\n\n");
    
    System.out.println(
      "Vangav M Generator\n"
      + "www.vangav.com\n\n"
      + "Vangav M\n"
      + "Code. Mighty.\n\n");
    
    generateVangavMSolutions();
  }
}
