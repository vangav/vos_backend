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

import com.vangav.backend.metrics.time.Period;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * CycleTicker can be optionally configured to set a PeriodicJob's:
 *   - initial cycle
 *   - cycle step
 * This is useful in various ways like:
 *   - When multiple PeriodicJobs in the same or in different services are to
 *       share the processing of a specific job. Let's say a job should rank
 *       instagram's photos every hour. Then two jobs can share (odd hours and
 *       even hours) and use the current cycle to define whether to process
 *       odd or even hours.
 *   - After a service's down time, initial cycle can be set to adjust the
 *       service's behavior on restart.
 *   - When a job should only run for a specific period of time, like an ads
 *       campaign job for the holidays season that should run for 2 weeks.
 */
public final class CycleTicker {

  private final long initialCycle;
  private final long cycleStep;
  private final Period runFor;
  
  /**
   * Constructor - CycleTicker
   * @return default new CycleTicker Object
   * @throws Exception
   */
  public CycleTicker () throws Exception {
    
    this.initialCycle = 1L;
    this.cycleStep = 1L;
    this.runFor = null;
  }
  
  /**
   * Constructor - CycleTicker
   * @param cycleTickerBuilder
   * @return new CycleTicker Object
   * @throws Exception
   */
  public CycleTicker (CycleTickerBuilder cycleTickerBuilder) throws Exception {
    
    this.initialCycle = cycleTickerBuilder.initialCycle;
    this.cycleStep = cycleTickerBuilder.cycleStep;
    this.runFor = cycleTickerBuilder.runFor;
  }
  
  /**
   * Copy Constructor - CycleTicker
   * @param cycleTicker
   * @return new CycleTicker Object identical to param cycleTicker
   * @throws Exception
   */
  public CycleTicker (CycleTicker cycleTicker) throws Exception {
    
    this.initialCycle = cycleTicker.initialCycle;
    this.cycleStep = cycleTicker.cycleStep;
    this.runFor = new Period(cycleTicker.runFor);
  }
  
  /**
   * getInitialCycle
   * @return the initial cycle's value
   * @throws Exception
   */
  public long getInitialCycle () throws Exception {
    
    return this.initialCycle;
  }
  
  /**
   * getCycleStep
   * @return cycle ticker's step
   * @throws Exception
   */
  public long getCycleStep () throws Exception {
    
    return this.cycleStep;
  }
  
  /**
   * isInfinite
   * @return true if this cycle ticker should run forever and false otherwise
   * @throws Exception
   */
  public boolean isInfinite () {
    
    if (this.runFor == null) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * getRunFor
   * @return if this cycle ticker is set to end after some period of time, it
   *           returns that period of time, and it returns null otherwise
   * @throws Exception
   */
  public Period getRunFor () throws Exception {
    
    return this.runFor;
  }
  
  @Override
  public String toString () {
    
    return
      " [initial cycle: "
      + this.initialCycle
      + "] [cycle step: "
      + this.cycleStep
      + "] [run for: "
      + this.runFor == null? "null" : this.runFor.toString()
      + "]";
  }
  
  /**
   * CycleTickerBuilder is used to build a CycleTicker
   */
  public final static class CycleTickerBuilder {

    private long initialCycle;
    private long cycleStep;
    private Period runFor;
    
    /**
     * Constructor - CycleTickerBuilder
     * @return new CycleTickerBuilder Object
     * @throws Exception
     */
    public CycleTickerBuilder () throws Exception {
      
      this.initialCycle = 1L;
      this.cycleStep = 1L;
      this.runFor = null;
    }
    
    /**
     * initialCycle
     * @param initialCycle
     * @return sets the initial cycle's value
     * @throws Exception
     */
    public CycleTickerBuilder initialCycle (
      long initialCycle) throws Exception {
      
      this.initialCycle = initialCycle;
      
      return this;
    }
    
    /**
     * cycleStep
     * @param cycleStep
     * @return sets the cycle ticker's step value
     * @throws Exception
     */
    public CycleTickerBuilder cycleStep (
      long cycleStep) throws Exception {
      
      this.cycleStep = cycleStep;
      
      return this;
    }
    
    /**
     * runFor
     * @param runFor
     * @return sets the cycle ticker's run for value
     * @throws Exception
     */
    public CycleTickerBuilder runFor (
      Period runFor) throws Exception {
      
      this.runFor = runFor;
      
      return this;
    }
    
    /**
     * build
     * @return return the new built CycleTicker Object
     * @throws Exception
     */
    public CycleTicker build () throws Exception {
      
      return new CycleTicker(this);
    }
  }
}
