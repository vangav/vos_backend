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

package com.vangav.backend.play_framework.controllers_generator;

import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;
import com.vangav.backend.play_framework.controllers_generator.json.ControllersJson;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ControllersParserInl has inline static methods for parsing controllers
 *   config [controllers.json]
 * */
public class ControllersParserInl {
  
  // disable default instantiation
  private ControllersParserInl () {}

  /**
   * getJavaPackageName
   * @param controllersJson
   * @return java_package member from param controllersJson
   * @throws Exception
   */
  public static String getJavaPackageName (
    final ControllersJson controllersJson) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "controllers json",
      controllersJson,
      ExceptionType.CODE_EXCEPTION);
    
    return controllersJson.java_package;
  }
  
  /**
   * getHasNotifications
   * @param controllersJson
   * @return notifications member from param controllersJson
   * @throws Exception
   */
  public static boolean getHasNotifications (
    final ControllersJson controllersJson) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "controllers json",
      controllersJson,
      ExceptionType.CODE_EXCEPTION);
    
    return controllersJson.notifications;
  }
}
