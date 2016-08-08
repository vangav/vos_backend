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

import java.util.HashMap;
import java.util.Map;

import com.vangav.backend.properties.PropertiesFile;
import com.vangav.backend.properties.PropertiesLoader;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ThreadPoolProperties defines the default values and structure for
 *   ThreadPool properties
 * */
public class ThreadPoolProperties extends PropertiesFile {

  private static ThreadPoolProperties instance = null;
  
  private ThreadPoolProperties () {}
  
  /**
   * i
   * singleton instance method
   * @return static instance of ThreadPoolProperties
   * @throws Exception
   */
  public static ThreadPoolProperties i () throws Exception {
    
    if (instance == null) {
      
      instance = new ThreadPoolProperties();
    }
    
    return instance;
  }
  
  /**
   * kName: properties file's name
   */
  private static final String kName = "thread_pool_properties";
  
  @Override
  public String getName () {
    
    return kName;
  }
  
  // properties names
  public static final String kRunnablePoolSize = "runnable_pool_size";
  public static final String kBlockingPoolSize = "blocking_pool_size";
  public static final String kDispatcherPoolSize = "dispatcher_pool_size";
  
  // property name -> property default value
  private static final Map<String, String> kProperties;
  static {
    
    kProperties = new HashMap<String, String>();

    kProperties.put(
      kRunnablePoolSize,
      ""
      + Math.min(
          ((Runtime.getRuntime().availableProcessors() + 1) * 10),
          1000) );
    kProperties.put(
      kBlockingPoolSize,
      ""
      + Runtime.getRuntime().availableProcessors() + 1);
    kProperties.put(
      kDispatcherPoolSize,
      ""
      + Runtime.getRuntime().availableProcessors() + 1);
  }
  
  @Override
  protected String getProperty (String name) throws Exception {
    
    return PropertiesLoader.i().getProperty(
      this.getName(),
      name,
      kProperties.get(name) );
  }
}
