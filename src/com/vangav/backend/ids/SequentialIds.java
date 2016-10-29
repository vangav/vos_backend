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

package com.vangav.backend.ids;

import java.time.Instant;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.math.NumbersInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * SequentialIds
 *   generates sequential IDs based on unix time and a counter
 *   generates up to 100 million unique IDs per second
 *   throws an exception on collision (collision resistant)
 *   generates unique IDs only per-instance
 *   works well until year 4857
 *   uses JAVA Long (19 digits)
 *   11 digits for unix time and 8 digits for counter
 *   e.g.: 0146619060100000017
 *         --> unix time = 1466190601 and counter = 17
 * NOTE: this class depends on system time, a change in the system time
 *         affects the generated ids and can potentially result in
 *         out-of-sequence ids for a period of time
 */
public class SequentialIds {
  
  private volatile long lastTimeStamp;
  private volatile long counter;
  
  private static final long kInitialCounter = 0;
  private static final long kMaxCounter = 99999999;
  
  /**
   * Constructor SequentialIds
   * @return new SequentialIds Object
   * NOTE: optional, alternatively use the singleton method i()
   */
  public SequentialIds () {
    
    this.lastTimeStamp = Instant.now().getEpochSecond();
    this.counter = kInitialCounter;
  }
  
  private static SequentialIds instance = null;
  /**
   * i
   * @return singleton instance of SequentialIds
   */
  public static SequentialIds i () {
    
    if (instance == null) {
      
      instance = new SequentialIds();
    }
    
    return instance;
  }
  
  private final Object lock = new Object();

  private final ThreadLocal<StringBuffer> kThreadLocalStringBuffer =
    new ThreadLocal<StringBuffer>();

  private static final int kIdTimeStampLength = 11;
  private static final int kIdCounterLength = 8;
  
  /**
   * getNewIdAsLong
   * @return a new unique sequential ID (unique per instance)
   * @throws Exception in case of trying to generate more than 100 million
   *                     new IDs within one second
   */
  public long getNewIdAsLong () throws Exception {
    
    return Long.parseLong(this.getNewId() );
  }
  
  /**
   * getNewId
   * @return a new unique sequential ID (unique per instance)
   * @throws Exception in case of trying to generate more than 100 million
   *                     new IDs within one second
   */
  public String getNewId () throws Exception {
    
    if (this.counter == (kMaxCounter - 1L) ) {
      
      throw new CodeException(
        81,
        1,
        "trying to generate more than 100 million new IDs during one second",
        ExceptionClass.THROTTLING);
    }
    
    long timeStamp;
    
    synchronized (this.lock) {
      
      timeStamp = Instant.now().getEpochSecond();
      
      if (timeStamp != this.lastTimeStamp) {
        
        this.lastTimeStamp = timeStamp;
        this.counter = kInitialCounter;
      }
      
      this.counter ++;

      StringBuffer stringBuffer = kThreadLocalStringBuffer.get();
      
      stringBuffer.append(
        NumbersInl.longToString(timeStamp, kIdTimeStampLength) );
      stringBuffer.append(
        NumbersInl.longToString(this.counter, kIdCounterLength) );
      
      return stringBuffer.toString();
    }
  }
}
