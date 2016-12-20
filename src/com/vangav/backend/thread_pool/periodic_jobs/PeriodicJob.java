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

import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.metrics.time.CalendarAndDateOperationsInl;
import com.vangav.backend.metrics.time.Period;
import com.vangav.backend.metrics.time.TimeUnitType;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * PeriodicJob is the parent class for jobs that happen periodically
 * e.g.:
 *   - send a news mail to the whole user base every day
 *   - send a happy birthday notification to a user every year
 *   - monitor a service's health every five minutes
 *   - send service state email every day
 *   - etc ....
 */
public abstract class PeriodicJob
  <T extends PeriodicJob<T> >
  implements Runnable {
  
  /**
   * SYNC:
   *   Means that this periodic job's next cycle won't start until the
   *   current cycle finishes processing even if it's processing time
   *   exceeded this periodic job's execution period
   * ASYNC:
   *   Means that this periodic job's next cycle will start on time
   *   regardless of the current cycle's state (finish or still processing)
   */
  protected enum Type {
    
    SYNC,
    ASYNC
  }

  private String name;
  private Type type;
  private Calendar startCalendar;
  private long startTime;
  private Calendar endCalendar;
  private long endTime;
  private Period execPeriod;
  private long executionPeriod;
  private CycleTicker cycleTicker;
  private long currCycle;
  private long currCycleCount;
  private CycleLog cycleLog;
  private AtomicBoolean createdNextPeriodicJob;
  private CountDownLatch lock;
  
  /**
   * Constructor - PeriodicJob
   * @param name
   * @param type
   * @param startTime
   * @param executionPeriod
   * @return new PeriodicJob Object
   * @throws Exception
   */
  protected PeriodicJob (
    String name,
    Type type,
    Calendar startTime,
    Period executionPeriod) throws Exception {
    
    this (
      name,
      type,
      startTime,
      executionPeriod,
      new CycleTicker(),
      new CycleTicker().getInitialCycle(),
      1L);
  }
  
  /**
   * Constructor - PeriodicJob
   * @param name
   * @param type
   * @param startTime
   * @param executionPeriod
   * @param cycleTicker
   * @return new PeriodicJob Object
   * @throws Exception
   */
  protected PeriodicJob (
    String name,
    Type type,
    Calendar startTime,
    Period executionPeriod,
    CycleTicker cycleTicker) throws Exception {
    
    this (
      name,
      type,
      startTime,
      executionPeriod,
      cycleTicker,
      cycleTicker.getInitialCycle(),
      1L);
  }
  
  /**
   * Constructor - PeriodicJob
   * @param name
   * @param type
   * @param startTime
   * @param executionPeriod
   * @param cycleTicker
   * @param currCycle
   * @param currCycleCount
   * @return new PeriodicJob Object
   * @throws Exception
   */
  private PeriodicJob (
    String name,
    Type type,
    Calendar startTime,
    Period executionPeriod,
    CycleTicker cycleTicker,
    long currCycle,
    long currCycleCount) throws Exception {
    
    this.name = name;
    this.type = type;
    this.startCalendar = startTime;
    this.startTime = startTime.getTimeInMillis();
    
    if (cycleTicker.isInfinite() == true) {
      
      this.endCalendar = null;
      this.endTime = -1L;
    } else {
      
      this.endCalendar =
        CalendarAndDateOperationsInl.getCalendarFromUnixTime(
          this.startTime +
          (long)cycleTicker.getRunFor().getAs(
            TimeUnitType.MILLISECOND).getValue() );
      
      this.endTime =
        this.startTime +
        (long)cycleTicker.getRunFor().getAs(
          TimeUnitType.MILLISECOND).getValue();
    }
    
    this.execPeriod = executionPeriod;
    this.executionPeriod =
      (long)executionPeriod.getAs(TimeUnitType.MILLISECOND).getValue();
    this.cycleTicker = cycleTicker;
    this.currCycle = currCycle;
    this.currCycleCount = currCycleCount;
    this.createdNextPeriodicJob = new AtomicBoolean(false);
    this.lock = new CountDownLatch(1);
    this.cycleLog = null;
  }
  
  /**
   * getName
   * @return this period job's name
   * @throws Exception
   */
  final protected String getName () throws Exception {
    
    return this.name;
  }
  
  /**
   * getType
   * @return this period job's type
   */
  final protected Type getType () {
    
    return this.type;
  }
  
  /**
   * getStartCalendar
   * @return this periodic job's start calendar
   * @throws Exception
   */
  final protected Calendar getStartCalendar () throws Exception {
    
    return this.startCalendar;
  }
  
  /**
   * getStartTime
   * @return this periodic job's start unix time in milli seconds
   */
  final protected long getStartTime () {
    
    return this.startTime;
  }
  
  /**
   * getEndCalendar
   * @return for infinite periodic jobs it returns null
   *         otherwise it returns this periodic job's end calendar
   * @throws Exception
   */
  final protected Calendar getEndCalendar () throws Exception {
    
    return this.endCalendar;
  }
  
  /**
   * getEndTime
   * @return for infinite periodic jobs it returns -1L
   *         otherwise it returns this period job's end unix time in milli
   *           seconds
   */
  final protected long getEndTime () {
    
    return this.endTime;
  }
  
  /**
   * isInfinite
   * @return true if this periodic job is infinite and false otherwise
   */
  final protected boolean isInfinite () {
    
    if (this.endCalendar == null) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * getCyclePlannedStartTime
   * @return this period job's current cycle's planned start time
   */
  final protected long getCyclePlannedStartTime () {
    
    return
      this.startTime +
      (this.executionPeriod * (this.currCycleCount - 1L) );
  }
  
  /**
   * getTimeTillCyclePlannedStartTime
   * @return milli seconds till the next planned start time (minimum of 0L)
   */
  final protected long getTimeTillCyclePlannedStartTime () {
    
    if (this.getCyclePlannedStartTime() <= System.currentTimeMillis() ) {
      
      return 0L;
    }
    
    return this.getCyclePlannedStartTime() - System.currentTimeMillis();
  }
  
  /**
   * getCyclePlannedEndTime
   * @return this period job's current cycle's planned end time
   */
  final protected long getCyclePlannedEndTime () {
    
    return this.getCyclePlannedStartTime() + this.executionPeriod;
  }
  
  /**
   * getTimeTillCyclePlannedEndTime
   * @return milli seconds till the next planned end time (minimum of 0L)
   */
  final protected long getTimeTillCyclePlannedEndTime () {
    
    if (this.getCyclePlannedEndTime() <= System.currentTimeMillis() ) {
      
      return 0L;
    }
    
    return this.getCyclePlannedEndTime() - System.currentTimeMillis();
  }
  
  /**
   * getExecPeriod
   * @return this periodic job's execution period
   * @throws Exception
   */
  final protected Period getExecPeriod () throws Exception {
    
    return this.execPeriod;
  }
  
  /**
   * getExecutionPeriod
   * @return this periodic job's execution period in milli seconds
   */
  final protected long getExecutionPeriod () {
    
    return this.executionPeriod;
  }
  
  /**
   * getCycleTicker
   * @return this periodic job's cycle ticker
   * @throws Exception
   */
  final protected CycleTicker getCycleTicker () throws Exception {
    
    return this.cycleTicker;
  }
  
  /**
   * getCurrentCycle
   * @return this periodic job's current cycle
   * @throws Exception
   */
  final protected long getCurrentCycle () throws Exception {
    
    return this.currCycle;
  }
  
  /**
   * getCurrentCycleCount
   * @return this periodic job's current cycle count
   * @throws Exception
   */
  final protected long getCurrentCycleCount () throws Exception {
    
    return this.currCycleCount;
  }
  
  /**
   * createdNextPeriodicJob
   * @return true if this periodic job's next instance is already created and
   *           false otherwise
   * @throws Exception
   */
  final protected boolean createdNextPeriodicJob () throws Exception {
    
    return this.createdNextPeriodicJob.get();
  }
  
  /**
   * deepCopy
   * @return a deep copy of Periodic Job's child
   * @throws Exception
   */
  protected abstract PeriodicJob<T> deepCopy () throws Exception;
  
  /**
   * getNextPeriodicJob
   * @return the PeriodicJob's next cycle's PeriodicJob
   * @throws Exception
   */
  final protected synchronized PeriodicJob<T> getNextPeriodicJob (
    ) throws Exception {
    
    // a PeriodicJob can clone a next PeriodicJob relative to self one time
    //   only
    if (this.createdNextPeriodicJob.compareAndSet(false, true) == false) {
      
      throw new CodeException(
        191,
        1,
        "Can't get next PeriodicJob more than one time on the same "
          + "PeriodicJob Object. Exception came from PeriodicJob Object:\n"
          + this.toString(),
        ExceptionClass.UNAUTHORIZED);
    }
    
    // get an identical copy of this PeriodicJob
    PeriodicJob<T> getNextPeriodicJob = this.deepCopy();
    
    // set the next cycle's values
    getNextPeriodicJob.name =
      this.name;
    getNextPeriodicJob.type =
      this.type;
    getNextPeriodicJob.startCalendar =
      (Calendar)this.startCalendar.clone();
    getNextPeriodicJob.startTime =
      this.startTime;
    getNextPeriodicJob.endCalendar =
      (Calendar)this.endCalendar.clone();
    getNextPeriodicJob.endTime =
      this.endTime;
    getNextPeriodicJob.execPeriod =
      new Period(this.execPeriod);
    getNextPeriodicJob.executionPeriod =
      this.executionPeriod;
    getNextPeriodicJob.cycleTicker =
      new CycleTicker(this.cycleTicker);
    getNextPeriodicJob.currCycle =
      this.currCycle + this.cycleTicker.getCycleStep();
    getNextPeriodicJob.currCycleCount =
      this.currCycleCount + 1L;
    getNextPeriodicJob.createdNextPeriodicJob =
      new AtomicBoolean(false);
    getNextPeriodicJob.lock =
      new CountDownLatch(1);
    getNextPeriodicJob.cycleLog =
      null;
    
    return getNextPeriodicJob;
  }
  
  /**
   * process
   * handles the processing of this periodic job's cycle
   * e.g.:
   *   - do the database update
   *   - send the push notification(s)
   *   - send the email
   *   - etc ...
   * @param cycleLog
   * @throws Exception
   */
  protected abstract void process (CycleLog cycleLog) throws Exception;
  
  /**
   * postProcess
   * gets called automatically after process method finishes execution with
   *   an updated cycle's CycleLog
   * NOTE: although this method throws exception for crash-avoidance, it
   *         should be implemented with try/catch and should NEVER throw any
   *         exception as those exceptions will just get consumed without being
   *         logged anywhere
   * @param cycleLog
   * @throws Exception
   */
  protected abstract void postProcess (CycleLog cycleLog) throws Exception;
  
  /**
   * finished
   * @return true if this cycle finished processing and false otherwise
   *           NOTE: returns true for processing without taking post processing
   *                   into consideration
   */
  protected boolean finished () {
    
    if (this.cycleLog == null) {
      
      return false;
    }
    
    return this.cycleLog.finished();
  }
  
  /**
   * blockTillFinish
   * blocks till this cycle finishes processing
   * NOTE: finishes before post processing starts
   * @return null if block was successful and InterruptedException otherwise
   */
  protected InterruptedException blockTillFinish () {
    
    try {
      
      this.lock.await();
      
      return null;
    } catch (InterruptedException e) {
      
      return e;
    }
  }
  
  @Override
  final public void run () {
    
    // init a cycle log
    
    long plannedStartTime =
      this.startTime + (this.executionPeriod * (this.currCycleCount - 1L) );
    long actualStartTime =
      System.currentTimeMillis();
    long plannedEndTime =
      plannedStartTime + this.executionPeriod;
    
    this.cycleLog =
      new CycleLog(plannedStartTime, actualStartTime, plannedEndTime);
    
    try {
      
      // process cycle
      this.process(this.cycleLog);
      this.cycleLog.setSuccess();
    } catch (Exception e) {
      
      this.cycleLog.setFailureException(e);
    }
    
    // finished
    this.cycleLog.endCycle();
    this.lock.countDown();
    
    try {
      
      // start post processing
      this.postProcess(this.cycleLog);
    } catch (Exception e) {
      
      // this is not supposed to happen, catching only for crash-avoidance
    }
  }
  
  @Override
  public String toString () {
    
    return
      "Periodic Job:\n"
      + "name: "
      + this.name
      + "\ntype: "
      + this.type.toString()
      + "\nstart time: "
      + this.startCalendar.getTime().toString()
      + "\nexecution period: "
      + this.execPeriod.toString()
      + "\n cycle ticker: "
      + this.cycleTicker.toString()
      + "\n current cycle: "
      + this.currCycle
      + "\n current cycle count: "
      + this.currCycleCount
      + "\n created next periodic job: "
      + this.createdNextPeriodicJob.get();
  }
}
