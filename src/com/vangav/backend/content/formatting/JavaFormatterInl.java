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

package com.vangav.backend.content.formatting;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * JavaFormatterInl has inline static methods for formatting content into
 *   valid JAVA code
 * */
public class JavaFormatterInl {
  
  // disable default instantiation
  private JavaFormatterInl () {}
  
  public static final String kDefaultSrcDirName = "src";
  public static final String kPlaySrcDirName = "app";
  
  /**
   * getPathToPackage
   * @param projectDirPath
   * @param srcDirName
   * @param rootPackage
   * @param projectName
   * @param morePackages
   * @return relative dir path
   * @throws Exception
   */
  public static final String getPathToPackage (
    String projectDirPath,
    String srcDirName,
    String rootPackage,
    String projectName,
    String... morePackages) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer();
    
    stringBuffer.append(
      projectDirPath
      + "/"
      + srcDirName
      + "/");
    
    if (rootPackage != null && rootPackage.length() > 0) {
      
      String[] javaPackageSplit = rootPackage.split("\\.");
      
      for (String subPackage : javaPackageSplit) {
        
        stringBuffer.append(
          subPackage
          + "/");
      }
    }
    
    stringBuffer.append(projectName);
    
    if (morePackages != null) {
      
      for (String morePackage : morePackages) {
        
        stringBuffer.append(
          "/"
          + morePackage);
      }
    }
    
    return stringBuffer.toString();
  }
  
  /**
   * getPackageName
   * @param rootPackage
   * @param projectName
   * @param morePackages
   * @return java formatted package name (e.g.: com.vangav.backend)
   * @throws Exception
   */
  public static final String getPackageName (
    String rootPackage,
    String projectName,
    String... morePackages) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer();
    
    if (rootPackage != null && rootPackage.length() > 0) {
    
      stringBuffer.append(rootPackage + ".");
    }
    
    stringBuffer.append(projectName);
    
    if (morePackages != null) {
      
      for (String morePackage : morePackages) {
        
        stringBuffer.append(
          "."
          + morePackage);
      }
    }
    
    return stringBuffer.toString();
  }
  
  private static final int kDefaultMaxLineLength = 75;
  /**
   * formatStringLength
   * divides param string into a multi-line String with a maximum length of
   *   kDefaultMaxLineLength
   * @param string
   * @param newLinePrefix
   * @param isStringVariable
   * @return multi-line String
   * @throws Exception
   */
  public static final String formatStringLength (
    String string,
    String newLinePrefix,
    boolean isStringVariable) throws Exception {
    
    return
      formatStringLength(
        string,
        kDefaultMaxLineLength,
        newLinePrefix,
        isStringVariable);
  }
  
  /**
   * formatStringLength
   * divides param string into a multi-line String with a maximum length of
   *   param maxLineLength
   * @param string
   * @param maxLineLength
   * @param newLinePrefix
   * @param isStringVariable
   * @return multi-line String
   * @throws Exception
   */
  public static final String formatStringLength (
    String string,
    int maxLineLength,
    String newLinePrefix,
    boolean isStringVariable) throws Exception {
    
    if (newLinePrefix.length() > (maxLineLength / 2) ) {
      
      return string;
    }
    
    StringBuffer stringBuffer = new StringBuffer();
    
    String[] spaceSplit = string.split("\\s+");
    
    String currLine = "";
    
    for (int i = 0; i < spaceSplit.length; i ++) {
      
      if ((newLinePrefix.length()
           + currLine.length()
           + spaceSplit[i].length()
           + 1) > maxLineLength) {
        
        if (isStringVariable == true) {
        
          stringBuffer.append(currLine + "\"\n");
        } else {
        
          stringBuffer.append(currLine + "\n");
        }
        
        if (isStringVariable == true) {
        
          currLine = newLinePrefix + "+ \"" + spaceSplit[i] + " ";
        } else {
        
          currLine = newLinePrefix + spaceSplit[i] + " ";
        }
        
        if (i == (spaceSplit.length - 1) ) {
        
          stringBuffer.append(currLine);
        }
      } else {
        
        currLine += spaceSplit[i] + " ";
        
        if (i == (spaceSplit.length - 1) ) {
        
          stringBuffer.append(currLine);
        }
      }
    }
    
    return stringBuffer.toString();
  }
}
