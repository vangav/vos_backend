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

package com.vangav.backend.backend_generator.defaults;

import com.vangav.backend.play_framework.ParentPlayHandler;
import com.vangav.backend.play_framework.request.Request;

/**
 * GENERATED using BackendGeneratorMain.java
 * This the default CommonPlayHandler since there's now config for this new
 *   project
 */
/**
 * CommonHandler represents the parent handler for all controllers
 * */
public abstract class CommonPlayHandler extends ParentPlayHandler {

  @Override
  protected void checkSource (
    final Request request) throws Exception {

    // Disabled functionality, set to true in request_properties.prop to enable
    // implement here and/or override per Controller's Handler
  }
  
  @Override
  protected void throttle (
    final Request request) throws Exception {

    // Disabled functionality, set to true in request_properties.prop to enable
    // implement here and/or override per Controller's Handler
  }

  @Override
  protected void authenticateRequest (
    final Request request) throws Exception {

    // Disabled functionality, set to true in request_properties.prop to enable
    // implement here and/or override per Controller's Handler
  }

  @Override
  protected void afterProcessing (
    final Request request) throws Exception {

    // Disabled functionality, set to true in request_properties.prop to enable
    // implement here and/or override per Controller's Handler
  }

  @Override
  protected void dispatchDefaultOperations (
    final Request request) throws Exception {

    // Disabled functionality, set to true in request_properties.prop to enable
    // implement here and/or override per Controller's Handler
  }

  @Override
  protected void dispatchPushNotifications (
    final Request request) throws Exception {

    // Disabled functionality, set to true in request_properties.prop to enable
    // implement here and/or override per Controller's Handler
  }

  @Override
  protected void dispatchAnalysis (
    final Request request) throws Exception {

    // Disabled functionality, set to true in request_properties.prop to enable
    // implement here and/or override per Controller's Handler
  }

  @Override
  protected void dispatchDefaultAnalysis (
    final Request request) throws Exception {

    // Disabled functionality, set to true in request_properties.prop to enable
    // implement here and/or override per Controller's Handler
  }

  @Override
  protected void dispatchLogging (
    final Request request) throws Exception {

    // Disabled functionality, set to true in request_properties.prop to enable
    // implement here and/or override per Controller's Handler
  }

  @Override
  protected void dispatchDefaultLogging (
    final Request request) throws Exception {

    // Disabled functionality, set to true in request_properties.prop to enable
    // implement here and/or override per Controller's Handler
  }

  //IMPORTANT: this method must be implemented within a try and catch block
  //             because any exceptions thrown by this method will terminate
  //             the service instance and disables it from handling other
  //             requests
  @Override
  protected void absorbUnhandledExceptions (
    final Exception exception) {
    
    // TODO: implement here or override per-controller's handler
  }
}
