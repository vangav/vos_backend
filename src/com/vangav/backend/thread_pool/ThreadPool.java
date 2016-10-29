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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.util.concurrent.ListenableFuture;
import com.vangav.backend.networks.rest_client.RestAsync;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ThreadPool represents the global thread pool for a service
 * - Runnable thread pool is used for non-blocking operations
 *     like starting a new thread for in-memory operation, this thread pool
 *     is important to limit the number of running threads to avoid memory
 *     failure and processing congestion
 * - Cassandra thread pool is used for cassandra's blocking operations
 * - Dispatcher thread pool is used by the dispatcher to dispatch messages
 *     to worker services
 * */
public class ThreadPool {
  
  /**
   * WaitObject
   * used for waiting till the thread goes into the thread pool
   *   and finishes execution
   * */
  private class WaitObject {
  
    /**
     * notified is initially set to false then gets set to true by the notifier
     *   when the result is ready
     */
    private AtomicBoolean notified = new AtomicBoolean(false);

    /**
     * Constructor WaitObject
     */
    private WaitObject () {
      
      this.notified.set(false);
    }
  }
  
  /**
   * NotifyRunnable
   * waits till the executing thread takes its turn into the
   *   thread pool and finishes execution then it notifies the wait object
   * */
  private class NotifyRunnable implements Runnable {
  
    private WaitObject waitObject;

    private NotifyRunnable () {
      
      // to defeat default instantiation
    }
    
    private NotifyRunnable (
      WaitObject waitObject) {
      
      this.waitObject = waitObject;
    }
    
    public void run () {
      
      synchronized (this.waitObject) {
        
        this.waitObject.notified.set(true);
        
        this.waitObject.notify();
      }
    }
  }

  private ExecutorService runnablePool;
  private ExecutorService cassandraPool;
  private ExecutorService dispatcherPool;
  private ExecutorService restClientPool;
  
  /**
   * Constructor ThreadPool
   * @return new ThreadPool Object
   * @throws Exception
   */
  protected ThreadPool () throws Exception {

    this.runnablePool =
      Executors.newFixedThreadPool(
        ThreadPoolProperties.i().getIntProperty(
          ThreadPoolProperties.kRunnablePoolSize) );
    this.cassandraPool =
      Executors.newFixedThreadPool(
        ThreadPoolProperties.i().getIntProperty(
          ThreadPoolProperties.kCassandraPoolSize) );
    this.dispatcherPool =
      Executors.newFixedThreadPool(
        ThreadPoolProperties.i().getIntProperty(
          ThreadPoolProperties.kDispatcherPoolSize) );
    this.restClientPool =
      Executors.newFixedThreadPool(
        ThreadPoolProperties.i().getIntProperty(
          ThreadPoolProperties.kRestClientPoolSize) );
  }
  
  private static ThreadPool instance = null;
  
  /**
   * i
   * singleton instance method
   * @return static ThreadPool Object
   * @throws Exception
   */
  public static ThreadPool i () throws Exception {
    
    if (instance == null) {
      
      instance = new ThreadPool();
    }
    
    return instance;
  }

  /**
   * executeInRunnablePool
   * executes a runnable in the runnable thread pool
   * @param runnable: the runnable object to execute
   * @throws Exception
   */
  public void executeInRunnablePool (Runnable runnable) throws Exception {
    
    this.runnablePool.execute(runnable);
  }
  
  /**
   * executeInCassandraPool
   * executes a listenable future in the blocking thread pool
   * @param listenableFuture: object to execute
   * @return result when ready (sleeps meanwhile)
   * @throws Exception
   */
  public <T> void executeInCassandraPool (
    ListenableFuture<T> listenableFuture) throws Exception {
    
    WaitObject waitObject = new WaitObject();
    
    listenableFuture.addListener(
      new NotifyRunnable(waitObject),
      this.cassandraPool);
    
    synchronized (waitObject) {
  
      if (waitObject.notified.get() == false) {
      
        waitObject.wait();
      }
    }
  }
  
  /**
   * executeInDispatcherPool
   * executes a dispatcher runnable in the dispatcher thread pool
   * @param runnable: runnable object to execute
   * @throws Exception
   */
  public void executeInDispatcherPool (Runnable runnable) throws Exception {
    
    this.dispatcherPool.execute(runnable);
  }
  
  /**
   * executeInRestClientPool
   * executes a REST asynchronous request's runnable LatchThread in the
   *   REST Client thread pool
   * @param restAsync - RestAsync Latch Thread object to execute
   * @throws Exception
   */
  public void executeInRestClientPool (RestAsync restAsync) throws Exception {
    
    this.restClientPool.execute(restAsync);
  }
  
  /**
   * isShutdown
   * @return true if all thread pools are shutdown and false if one of more
   *           thread pools aren't shutdown
   * @throws Exception
   */
  public boolean isShutdown () throws Exception {
    
    if (this.runnablePool.isShutdown() == false) {
      
      return false;
    }
    
    if (this.cassandraPool.isShutdown() == false) {
      
      return false;
    }
    
    if (this.dispatcherPool.isShutdown() == false) {
      
      return false;
    }
    
    if (this.restClientPool.isShutdown() == false) {
      
      return false;
    }
    
    return true;
  }
  
  /**
   * shutDown
   * @param forceShutDown true to use shutdownNow and false to use shutdown
   * @throws Exception
   */
  public void shutdown (boolean forceShutDown) throws Exception {

    if (forceShutDown == true) {
    
      this.runnablePool.shutdownNow();
      this.cassandraPool.shutdownNow();
      this.dispatcherPool.shutdownNow();
      this.restClientPool.shutdownNow();
    } else {

      this.runnablePool.shutdown();
      this.cassandraPool.shutdown();
      this.dispatcherPool.shutdown();
      this.restClientPool.shutdown();
    }
  }
}
