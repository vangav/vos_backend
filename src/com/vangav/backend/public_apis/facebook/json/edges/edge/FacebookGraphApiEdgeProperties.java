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

package com.vangav.backend.public_apis.facebook.json.edges.edge;

import java.util.HashMap;
import java.util.Map;

import com.vangav.backend.properties.PropertiesFile;
import com.vangav.backend.properties.PropertiesLoader;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
public class FacebookGraphApiEdgeProperties extends PropertiesFile {

  private static FacebookGraphApiEdgeProperties instance = null;
  
  // disable default instantiation
  private FacebookGraphApiEdgeProperties () {}
  
  /**
   * static singleton getter
   * @return static singleton instance
   * @throws Exception
   */
  public static FacebookGraphApiEdgeProperties i () throws Exception {
    
    if (instance == null) {
      
      instance = new FacebookGraphApiEdgeProperties();
    }
    
    return null;
  }
  
  private static final String kName = "facebook_graph_api_edge_properties";
  
  @Override
  public String getName () {
    
    return kName;
  }
  
  // properties names
  public static final String kPageLimit = "page_limit";
  public static final String kGetAllPages = "get_all_pages";
  
  // property name -> property default value
  private static final Map<String, String> kProperties;
  static {
    
    kProperties = new HashMap<String, String>();
    
    kProperties.put(
      kPageLimit,
      "5000");
    kProperties.put(
      kGetAllPages,
      "true");
  }
  
  @Override
  protected String getProperty (String name) throws Exception {
    
    return PropertiesLoader.i().getProperty(
      this.getName(),
      name,
      kProperties.get(name) );
  }
}
