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

package com.vangav.backend.thread_pool;

import java.util.concurrent.CountDownLatch;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * LatchThread is a normal Runnable class that also uses CountDownLatch
 *   so that it does a count-down on the CountDownLatch after it finishes
 *   processing
 * 
 * Sub-classes implements their own process() method that gets executed
 *   from the thread's run() method
 *   
 * LatchThread is useful when a specific number of blocking operations should
 *   be run in parallel where the CountDownLatch is used to wait until all the
 *   LatchThread objects finish execution
 * */
public abstract class LatchThread implements Runnable {
  
  private final CountDownLatch countDownLatch;
  private boolean succeeded;
  private boolean finished;
  private Exception exception;
  
  /**
   * Constructor LatchThread
   * @param countDownLatch shared by the group of LatchThread Objects that
   *          share an operation (e.g.: multiple REST calls to a public API)
   * @return new LatchThread Object
   * @throws Exception
   */
  public LatchThread (final CountDownLatch countDownLatch) throws Exception {
    
    this.countDownLatch = countDownLatch;
    this.succeeded = false;
    this.finished = false;
    this.exception = null;
  }

  /**
   * execute
   * executes the needed functionality per LatchThread sub-class
   * @throws Exception
   */
  protected abstract void execute () throws Exception;
  
  /**
   * await
   * blocks on this latch thread's count down latch, if this latch is shared
   *   by multiple threads then this method will wait for all of those threads
   *   to finish
   * @throws Exception
   */
  final public void await () throws Exception {
    
    this.countDownLatch.await();
  }
  
  /**
   * succeeded
   * @return true if this LatchThread's execution was successful and false
   *           if an exception was thrown during processing
   * @throws Exception
   */
  final public boolean succeeded () throws Exception {
    
    return this.succeeded;
  }
  
  /**
   * finished
   * @return true if executing this thread finished and false otherwise
   * @throws Exception
   */
  final public boolean finished () throws Exception {
    
    return this.finished;
  }
  
  /**
   * getException
   * in case an exception got thrown during executing this thread, this method
   *   is used to get that thrown exception
   * if method succeeded returned false, then this method can be used to get the
   *   exception that caused this thread's execution to fail
   * @return thrown exception during this thread's execution
   * @throws Exception
   */
  final public Exception getException () throws Exception {
    
    return this.exception;
  }
  
  @Override
  final public void run () {
    
    try {
      
      this.execute();
      this.succeeded = true;
    } catch (Exception e) {

      this.succeeded = false;
      this.exception = e;
    }
    
    this.countDownLatch.countDown();
    this.finished = true;
  }
}
