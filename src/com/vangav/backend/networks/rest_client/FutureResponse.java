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

package com.vangav.backend.networks.rest_client;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * FutureResponse is used to hold future response of asynchronous requests
 */
public class FutureResponse<T> {
  
  private static final String kSingletonEntryKey =
    "00000000-0000-1000-0000-000000000000";
  
  private CountDownLatch countDownLatch;
  private Map<T, RestAsync> entries;
  
  /**
   * Constructor FutureResponse
   * for singular requests
   * @param countDownLatch
   * @param restAsync
   * @return new FutureResponse Object
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public FutureResponse (
    CountDownLatch countDownLatch,
    RestAsync restAsync) throws Exception {
    
    this.countDownLatch = countDownLatch;
    this.entries = new HashMap<T, RestAsync>();
    
    this.entries.put(
      (T)kSingletonEntryKey,
      restAsync);
  }
  
  /**
   * Constructor FutureResponse
   * for multiple requests
   * @param countDownLatch
   * @param entries
   * @return new FutureResponse Object
   * @throws Exception
   */
  public FutureResponse (
    CountDownLatch countDownLatch,
    Map<T, RestAsync> entries) throws Exception {
    
    this.countDownLatch = countDownLatch;
    this.entries = entries;
  }
  
  /**
   * get
   * gets RestAsnc Object for single request FutureResponse Objects
   * @return RestAsync Object
   * @throws Exception
   */
  public RestAsync get () throws Exception {
    
    this.countDownLatch.await();
    
    return this.entries.get(kSingletonEntryKey);
  }
  
  /**
   * getAll
   * gets all RestAsync Objects for multi-request FutureResponse Objects
   * @return all RestAsync Objects
   * @throws Exception
   */
  public Map<T, RestAsync> getAll () throws Exception {
    
    this.countDownLatch.await();
    
    return this.entries;
  }
}
