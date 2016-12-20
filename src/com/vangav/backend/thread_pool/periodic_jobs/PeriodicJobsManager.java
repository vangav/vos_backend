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

package com.vangav.backend.thread_pool.periodic_jobs;

import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;
import com.vangav.backend.thread_pool.ThreadPool;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * PeriodicJobsManager handles the processing of multiple PeriodicJob instances
 */
public class PeriodicJobsManager {

  private static final String kDefaultName = "Singleton Periodic Jobs Manager";
  private String name;
  
  /**
   * Constructor - PeriodicJobsManager
   * optionally use the singleton instance method
   * @param name
   * @throws Exception
   */
  public PeriodicJobsManager (String name) throws Exception {
    
    this.name = name;
  }
  
  private static PeriodicJobsManager instance = null;
  
  /**
   * i
   * optional singleton instance getter
   * @return PeriodicJobsManager's singleton instance
   * @throws Exception
   */
  public static PeriodicJobsManager i () throws Exception {
    
    if (instance == null) {
      
      instance = new PeriodicJobsManager(kDefaultName);
    }
    
    return instance;
  }
  
  /**
   * registerNewPeriodicJob
   * starts running the param periodicJob in a new PeriodicJobRunner
   * @param periodicJob
   * @throws Exception
   */
  public void registerNewPeriodicJob (
    PeriodicJob<?> periodicJob) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "period job",
      periodicJob,
      ExceptionType.CODE_EXCEPTION);
    
    ThreadPool.i().executeInRunnablePool(new PeriodicJobRunner(periodicJob) );
  }
  
  @Override
  public String toString () {
    
    return "PeriodicJobsManager: " + this.name;
  }
}
