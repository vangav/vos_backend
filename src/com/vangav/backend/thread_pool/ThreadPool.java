/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * no license, I know you already got more than enough to worry about
 * keep going, never give up
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
 * - Blocking thread pool is used for blocking operations like
 *     Database operations
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
  private ExecutorService blockingPool;
  private ExecutorService dispatcherPool;
  
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
    this.blockingPool =
      Executors.newFixedThreadPool(
        ThreadPoolProperties.i().getIntProperty(
          ThreadPoolProperties.kBlockingPoolSize) );
    this.dispatcherPool =
      Executors.newFixedThreadPool(
        ThreadPoolProperties.i().getIntProperty(
          ThreadPoolProperties.kDispatcherPoolSize) );
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
   * executeInBlockingPool
   * executes a listenable future in the blocking thread pool
   * @param listenableFuture: object to execute
   * @return result when ready (sleeps meanwhile)
   * @throws Exception
   */
  public <T> void executeInBlockingPool (
    ListenableFuture<T> listenableFuture) throws Exception {
    
    WaitObject waitObject = new WaitObject();
    
    listenableFuture.addListener(
      new NotifyRunnable(waitObject),
      this.blockingPool);
    
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
}
