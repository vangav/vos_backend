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

import java.util.ArrayList;

import com.vangav.backend.exceptions.VangavException;
import com.vangav.backend.metrics.time.Period;
import com.vangav.backend.metrics.time.TimeUnitType;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * CycleLog keeps a log for each executed cycle of a PeriodicJob
 */
public class CycleLog {
  
  private long plannedStartTime;
  private long actualStartTime;
  private long plannedEndTime;
  private long actualEndTime;
  
  private ArrayList<VangavException> nonFatalVangavExceptions;
  private ArrayList<Exception> nonFatalExceptions;
  
  private boolean finished;
  private boolean succeeded;
  private Exception failureException;
  
  /**
   * Constructor - CycleLog
   * @param plannedStartTime
   * @param actualStartTime
   * @param plannedEndTime
   * @return new CycleLog Object
   */
  protected CycleLog (
    long plannedStartTime,
    long actualStartTime,
    long plannedEndTime) {
    
    this.plannedStartTime = plannedStartTime;
    this.actualStartTime = actualStartTime;
    this.plannedEndTime = plannedEndTime;
    this.actualEndTime = this.actualStartTime;
    
    this.nonFatalVangavExceptions = new ArrayList<VangavException>();
    this.nonFatalExceptions = new ArrayList<Exception>();
    
    this.finished = false;
    this.succeeded = false;
    this.failureException = null;
  }
  
  /**
   * addNonFatalVangavException
   * used to add a VangavException that occurs during processing a cycle but
   *   doesn't cause it to fail
   * @param ve
   * @throws Exception
   */
  public void addNonFatalVangavException (
    VangavException ve) throws Exception {
    
    this.nonFatalVangavExceptions.add(ve);
  }
  
  /**
   * addNonFatalException
   * used to add an Exception that occurs during processing a cycle but
   *   doesn't cause it to fail
   * @param e
   * @throws Exception
   */
  public void addNonFatalException (
    Exception e) throws Exception {
    
    this.nonFatalExceptions.add(e);
  }
  
  /**
   * setFinished
   * used to mark this cycle's processing as finished
   * set to true before post processing the cycle
   */
  private void setFinished () {
    
    this.finished = true;
  }
  
  /**
   * setSuccess
   * used to mark this cycle's processing as a success (no exceptions)
   * set to true before post processing the cycle
   */
  protected void setSuccess () {
    
    this.succeeded = true;
  }
  
  /**
   * endCycle
   * gets called after processing a cycle is finished to mark it as finished
   *   and sets the cycle's actual end time
   * invoked before post processing the cycle
   */
  protected void endCycle () {
    
    this.setFinished();
    this.actualEndTime = System.currentTimeMillis();
  }
  
  /**
   * setFailureException
   * sets an exception that caused this cycle to fail
   * @param e
   */
  protected void setFailureException (Exception e) {
    
    this.failureException = e;
  }
  
  /**
   * getPlannedStartTime
   * @return this cycle's planned start unix time in milli seconds
   * @throws Exception
   */
  public long getPlannedStartTime () throws Exception {
    
    return this.plannedStartTime;
  }
  
  /**
   * getActualStartTime
   * @return this cycle's actual start unix time in milli seconds
   * @throws Exception
   */
  public long getActualStartTime () throws Exception {
    
    return this.actualStartTime;
  }
  
  /**
   * startedOnTime
   * @return true if this cycle started exactly on time to
   *           milli-seconds-precision
   * @throws Exception
   */
  public boolean startedOnTime () throws Exception {
    
    if (this.plannedStartTime == this.actualStartTime) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * startedOnTime
   * @param gracePeriod
   * @return true if this cycle's actual start time was within
   *           param gracePeriod of the planned start time and false otherwise
   * @throws Exception
   */
  public boolean startedOnTime (Period gracePeriod) throws Exception {
    
    Period startShiftPeriod =
      new Period(
        Math.abs(this.actualStartTime - this.plannedStartTime),
        TimeUnitType.MILLISECOND);
    
    if (startShiftPeriod.smallerThanOrEqual(gracePeriod) == true) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * getStartTimeDelta
   * @param timeUnitType
   * @return the delta absolute delta between this cycle's planned and actual
   *           start times converted to param timeUnitType
   *           (milli secons, minutes, ...)
   * @throws Exception
   */
  public Period getStartTimeDelta (
    TimeUnitType timeUnitType) throws Exception {
    
    Period startShiftPeriod =
      new Period(
        Math.abs(this.actualStartTime - this.plannedStartTime),
        TimeUnitType.MILLISECOND);
    
    return startShiftPeriod.getAs(timeUnitType);
  }
  
  /**
   * getPlannedEndTime
   * @return this cycle's planned end unix time in milli seconds
   * @throws Exception
   */
  public long getPlannedEndTime () throws Exception {
    
    return this.plannedEndTime;
  }
  
  /**
   * getActualEndTime
   * @return this cycle's actual end unix time in milli seconds, and -1 in case
   *           this cycle didn't finish execution yet
   * @throws Exception
   */
  public long getActualEndTime () throws Exception {
    
    if (this.finished() == false) {
      
      return -1L;
    }
    
    return this.actualEndTime;
  }
  
  /**
   * endedBeforeTime
   * @return true if this cycle's actual end time happened before or at
   *           its planned end time to milli-second-precision
   *         in case this cycle didn't finish execution yet it returns false
   * @throws Exception
   */
  public boolean endedBeforeTime () throws Exception {
    
    if (this.finished() == false) {
      
      return false;
    }
    
    if (this.actualEndTime <= this.plannedEndTime) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * endedOnTime
   * @return true if this cycle's actual end time is the same as its planned
   *           end time to milli-second-precision
   *         in case this cycle didn't finish execution yet it returns false
   * @throws Exception
   */
  public boolean endedOnTime () throws Exception {
    
    if (this.finished() == false) {
      
      return false;
    }
    
    if (this.actualEndTime == this.plannedEndTime) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * getEndTimeDelta
   * @param timeUnitType
   * @return the delta absolute delta between this cycle's planned and actual
   *           end times converted to param timeUnitType
   *           (milli secons, minutes, ...)
   *         in case this cycle didn't finish execution yet, it returns null
   * @throws Exception
   */
  public Period getEndTimeDelta (
    TimeUnitType timeUnitType) throws Exception {
    
    if (this.finished() == false) {
      
      return null;
    }
    
    Period startEndPeriod =
      new Period(
        Math.abs(this.actualEndTime - this.plannedEndTime),
        TimeUnitType.MILLISECOND);
    
    return startEndPeriod.getAs(timeUnitType);
  }
  
  /**
   * executedWithinPeriod
   * @return true if this cycle finished execution within its planned execution
   *           period and false otherwise (exeeded)
   * @throws Exception
   */
  public boolean executedWithinPeriod () throws Exception {
    
    if (this.finished() == false) {
      
      return false;
    }
    
    long actualExecutionTime =
      this.actualEndTime - this.actualStartTime;
    
    long plannedExecutionTime =
      this.plannedEndTime - this.plannedStartTime;
    
    if (actualExecutionTime <= plannedExecutionTime) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * getNonFatalVangavExceptions
   * @return this cycle's non-fatal vangav exceptions
   * @throws Exception
   */
  public ArrayList<VangavException> getNonFatalVangavExceptions (
    ) throws Exception {
    
    return this.nonFatalVangavExceptions;
  }
  
  /**
   * getNonFatalExceptions
   * @return this cycle's non-fatal exceptions
   * @throws Exception
   */
  public ArrayList<Exception> getNonFatalExceptions (
    ) throws Exception {
    
    return this.nonFatalExceptions;
  }
  
  /**
   * finished
   * @return true if this cycle finished processing and false otherwise
   *           NOTE: returns true for processing without taking post processing
   *                   into consideration
   * @throws Exception
   */
  public boolean finished () {
    
    return this.finished;
  }
  
  /**
   * succeeded
   * @return true if this cycle's processing finished without throwing
   *           exceptions
   * @throws Exception
   */
  public boolean succeeded () throws Exception {
  
    return this.succeeded;
  }
  
  /**
   * getFailureException
   * @return this cycle's failure exception and null in case this cycle:
   *           1- didn't finish processing
   *           2- finished processing with success
   * @throws Exception
   */
  public Exception getFailureException () throws Exception {
    
    return this.failureException;
  }
  
  /**
   * getNonFatalExceptionsAsString
   * @return this cycle's non fatal exceptions in string form for the toString
   *           method
   */
  private ArrayList<String> getNonFatalExceptionsAsString () {
    
    ArrayList<String> result = new ArrayList<String>();
    
    try {
      
      for (Exception exception : this.nonFatalExceptions) {
        
        if (exception instanceof VangavException) {
          
          result.add(((VangavException)exception).toString() );
        } else {
          
          result.add(VangavException.getExceptionStackTrace(exception) );
        }
      }
    } catch (Exception e) {
      
      result.add("Failed to continue because of an exception");
    }
    
    return result;
  }
  
  /**
   * getFailureExceptionAsString
   * @return this cycle's failure exception in string form for the toString
   *           method
   */
  private String getFailureExceptionAsString () {
    
    if (this.failureException == null) {
      
      return "no failure exception";
    }
    
    if (this.failureException instanceof VangavException) {
      
      return ((VangavException)this.failureException).toString();
    } else {
      
      return VangavException.getExceptionStackTrace(this.failureException);
    }
  }
  
  @Override
  public String toString () {
    
    return
      "Cycle log:"
      + "\nplanned start time: "
      + this.plannedStartTime
      + "\nactual start time:  "
      + this.actualStartTime
      + "\nplanned end time:   "
      + this.plannedEndTime
      + "\nactual end time:    "
      + this.actualEndTime
      + "\nnon fatal vangav exceptions: "
      + this.nonFatalVangavExceptions.toString()
      + "\nnon fatal exceptions: "
      + this.getNonFatalExceptionsAsString().toString()
      + "\nfinished: "
      + this.finished
      + "\nsucceeded: "
      + this.succeeded
      + "\nfailure exception: "
      + this.getFailureExceptionAsString();
  }
}
