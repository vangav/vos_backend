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
