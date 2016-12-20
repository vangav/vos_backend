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
import com.vangav.backend.thread_pool.periodic_jobs.PeriodicJob.Type;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * PeriodicJobRunner handles the processing of a single PeriodicJob
 *   it keeps getting the next PeriodicJob till infinity or the end period
 *   if defined
 */
class PeriodicJobRunner implements Runnable {
  
  private PeriodicJob<?> currPeriodicJob;
  
  /**
   * Constructor - PeriodicJobRunner
   * @param periodicJob
   * @return new PeriodicJobRunner Object
   * @throws Exception
   */
  protected PeriodicJobRunner (
    PeriodicJob<?> periodicJob) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "period job",
      periodicJob,
      ExceptionType.CODE_EXCEPTION);
    
    this.currPeriodicJob = periodicJob;
  }
  
  /**
   * runSync
   * handles processing periodic jobs synchronously - i.e. the next periodic
   *   jobs never starts until the current one finishes processing even if
   *   it took longer than its execution period
   * @throws Exception
   */
  private void runSync () throws Exception {
    
    while (true) {
      
      // finish finite job?
      if (this.currPeriodicJob.isInfinite() == false) {
        
        System.out.println(
          "\n\nTime till end: "
          + (this.currPeriodicJob.getEndTime() - System.currentTimeMillis() )
          + "\n\n");
        
        if (System.currentTimeMillis() > this.currPeriodicJob.getEndTime() ) {
          
          break;
        }
      }
      
      // wait till cycle's planned start time
      Thread.sleep(this.currPeriodicJob.getTimeTillCyclePlannedStartTime() );
    
      // start processing job
      ThreadPool.i().executeInRunnablePool(this.currPeriodicJob);
      
      // block till the current job finishes processing
      this.currPeriodicJob.blockTillFinish();
      
      // get the next job
      this.currPeriodicJob = this.currPeriodicJob.getNextPeriodicJob();
    }
  }
  
  /**
   * runAsync
   * handles processing periodic jobs asynchronously - i.e. the next periodic
   *   jobs whether the current one finished processing or not
   * NOTE: if the execution period set for this periodic job is too little
   *         for its actual needs, this will result in a compounding problem
   *         as new threads gets created for new jobs. an overflow of threads
   *         will happen in the thread pool which will result in the jobs
   *         executing much slower exponentially and may end up crashing the
   *         whole service (which is very unlikely to happen)
   * @throws Exception
   */
  private void runAsync () throws Exception {
    
    // finish finite job?
    while (true) {
      
      if (this.currPeriodicJob.isInfinite() == false) {
        
        if (System.currentTimeMillis() > this.currPeriodicJob.getEndTime() ) {
          
          break;
        }
      }
      
      // wait till the cycle's planned start time
      Thread.sleep(this.currPeriodicJob.getTimeTillCyclePlannedStartTime() );
      
      // start processing job
      ThreadPool.i().executeInRunnablePool(this.currPeriodicJob);
      
      // get the next job
      this.currPeriodicJob = this.currPeriodicJob.getNextPeriodicJob();
    }
  }

  @Override
  public void run () {
  
    try {
      
      if (this.currPeriodicJob.getType() == Type.SYNC) {
        
        this.runSync();
      } else {
        
        this.runAsync();
      }
    } catch (Exception e) {
      
      // any failure here would be cause by the thread pool which won't
      //   let the service start in the first place
    }
  }
  
  @Override
  public String toString () {
    
    return this.currPeriodicJob.toString();
  }
}
