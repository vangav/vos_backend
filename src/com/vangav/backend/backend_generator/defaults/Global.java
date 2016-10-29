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

import com.vangav.backend.exceptions.VangavException;
import com.vangav.backend.properties.PropertiesLoader;
import com.vangav.backend.thread_pool.ThreadPool;

import play.Application;
import play.GlobalSettings;

/**
 * GENERATED using BackendGeneratorMain.java
 * This the default CommonHandler since there's now config for this new
 *   project
 */
/**
 * Global handles some app-wide operations inherited from play's GlobalSettings
 * like before start, on stop, etc ...
 * */
public class Global extends GlobalSettings {

  @Override
  public void beforeStart(Application app) {

    try {

      // Before Start: this method gets executed between starting this
      //   service and opening the port for receiving requests.
      //   e.g.: execute on-time time consuming loading operations.
      
      PropertiesLoader.load();

      try {
        
        // cassandra connection and tables loadings happens here
      } catch (Exception e) {
      
        System.err.println(
          "Failed to connect to cassandra, start cassandra "
          + "  before starting this backend instance.\n\n"
          + "Exception:\n"
          + VangavException.getExceptionStackTrace(e)
          + "\n\nTerminating ...");

        System.exit(0);
      }
    } catch (Exception e) {

    }
  }

  @Override
  public void onStart(Application app) {

    try {

    } catch (Exception e) {

    }
  }

  @Override
  public void onStop(Application app) {

    try {
      
      ThreadPool.i().shutdown(true);

      // On Stop: this method gets executed between quitting this
      //   service and actual termination.
      // e.g.: disconnect from the data base
    } catch (Exception e) {

    }
  }
}
