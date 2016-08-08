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

package com.vangav.backend.dispatcher;

import java.util.HashMap;
import java.util.Map;

import com.vangav.backend.properties.PropertiesFile;
import com.vangav.backend.properties.PropertiesLoader;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * DispatcherProperties is the properties file with items like
 *   workers' ips/ports, etc ...
 * */
public class DispatcherProperties extends PropertiesFile {

  private static DispatcherProperties instance = null;
  
  //disable default instantiation
  private DispatcherProperties () {}
  
  /**
   * i
   * singleton instance method
   * @return a static instance of DispatcherProperties
   * @throws Exception
   */
  public static DispatcherProperties i () {
    
    if (instance == null) {
      
      instance = new DispatcherProperties();
    }
    
    return instance;
  }
  
  private static final String kName = "dispatcher_properties";
  
  @Override
  public String getName () {
    
    return kName;
  }
  
  // properties names
  public static final String kWorkersTopology = "workers_topology";
  
  // property name -> property default value
  private static final Map<String, String> kProperties;
  static {
    
    kProperties = new HashMap<String, String>();
    
    kProperties.put(
      kWorkersTopology,
      "http://127.0.0.1:8000");
  }
  
  @Override
  protected String getProperty (String name) throws Exception {
    
    return PropertiesLoader.i().getProperty(
      this.getName(),
      name,
      kProperties.get(name) );
  }
}
