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
}
