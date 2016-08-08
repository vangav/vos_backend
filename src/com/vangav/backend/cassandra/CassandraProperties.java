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

package com.vangav.backend.cassandra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;
import com.vangav.backend.properties.PropertiesFile;
import com.vangav.backend.properties.PropertiesLoader;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * CassandraProperties defines how this backend instance deals with Cassandra
 *   e.g.:
 *   - Cassandra's deployement topology
 *   - connection/query retries
 *   - mode (dev, beta, prod, ...)
 *   - etc ...
 * */
public class CassandraProperties extends PropertiesFile {

  private static CassandraProperties instance = null;
  
  /**
   * Constructor CassandraProperties
   * @return new CassandraProperties Object
   * @throws Exception
   */
  private CassandraProperties () throws Exception {
    
    this.initDeploymentTopology();
  }
  
  /**
   * i
   * @return static singleton instance of CassandraProperties
   * @throws Exception
   */
  public static CassandraProperties i () throws Exception {
    
    if (instance == null) {
      
      instance = new CassandraProperties();
    }
    
    return instance;
  }
  
  private static final String kName = "cassandra_properties";
  
  @Override
  public String getName () {
    
    return kName;
  }
  
  // properties names
  public static final String kConnectionRetries = "connection_retries";
  public static final String kQueryRetries = "query_retries";
  public static final String kWriteConsistencyLevel =
    "write_consistency_level";
  public static final String kReadConsistencyLevel =
    "read_consistency_level";
  public static final String kDeploymentMode = "deployment_mode";
  public static final String kSingleDeploymentIp = "single_deployment_ip";
  public static final String kMultiDeploymentTopology =
    "multi_deployment_topology";
  
  private static final String kSingleDeploymentMode = "single_deployment";
  private static final String kMultiDeploymentMode = "multi_deployment";
  private static final String kMultiDeploymentDelimiter = ",";
  
  private static ArrayList<CassandraNode> kTopology =
    new ArrayList<CassandraNode>();
  
  /**
   * initDeploymentTopology
   * initializes cassandra's deployment topology by reading nodes' IPs to make
   *   them available for an instance to connect to Cassandra
   * @throws Exception
   */
  private void initDeploymentTopology () throws Exception {
    
    // check deployment mode
    String deploymentMode = this.getProperty(kDeploymentMode);
    
    String currNodeIp;
    
    if (deploymentMode.compareTo(kSingleDeploymentMode) == 0) {
      
      currNodeIp = this.getProperty(kSingleDeploymentIp);
      
      ArgumentsInl.checkIpV4(
        "cassandra node ip",
        currNodeIp,
        ExceptionType.CODE_EXCEPTION);
      
      kTopology.add(
        new CassandraNode(
          kSingleDeploymentIp,
          currNodeIp) );
    } else if (deploymentMode.compareTo(kMultiDeploymentMode) == 0) {
      
      String multiDeploymentTopology =
        this.getProperty(kMultiDeploymentTopology);
      
      String[] cassandraNodesNames =
        multiDeploymentTopology.split(kMultiDeploymentDelimiter);
      
      if (cassandraNodesNames == null || cassandraNodesNames.length == 0) {
        
        throw new CodeException(
          "Invalid multi deployment topology ["
          + multiDeploymentTopology
          + "] in properties file ["
          + this.getName()
          + ".prop] correct format is: "
          + "node_name_1, node_name_2, ... node_name_n",
          ExceptionClass.PROPERTIES);
      }

      for (int i = 0; i < cassandraNodesNames.length; i ++) {
        
        currNodeIp = this.getProperty(cassandraNodesNames[i] );
        
        ArgumentsInl.checkIpV4(
          "cassandra node ip",
          currNodeIp,
          ExceptionType.CODE_EXCEPTION);
        
        kTopology.add(
          new CassandraNode(
            cassandraNodesNames[i],
            currNodeIp) );
        
        // prioritize local node for fast connection
        if (currNodeIp.compareTo("127.0.0.1") == 0) {
         
          Collections.swap(kTopology, 0, i);
        }
      }
    } else {
      
      throw new CodeException(
        "Invalid ["
        + kDeploymentMode
        + " = "
        + deploymentMode
        + "] in properties file ["
        + this.getName()
        + ".prop] change it to one of the valid values ("
        + kSingleDeploymentMode
        + ", "
        + kMultiDeploymentMode
        + ")",
        ExceptionClass.PROPERTIES);
    }
  }
  
  /**
   * getTopology
   * @return an array list with available cassandra nodes
   * @throws Exception
   */
  public final ArrayList<CassandraNode> getTopology () throws Exception {
    
    return kTopology;
  }
  
  // property name -> property default value
  private static final Map<String, String> kProperties;
  static {
    
    kProperties = new HashMap<String, String>();
    
    kProperties.put(
      kConnectionRetries,
      "3");
    kProperties.put(
      kQueryRetries,
      "3");
    kProperties.put(
      kWriteConsistencyLevel,
      "ONE");
    kProperties.put(
      kReadConsistencyLevel,
      "ONE");
    kProperties.put(
      kDeploymentMode,
      kSingleDeploymentMode);
    kProperties.put(
      kSingleDeploymentIp,
      "127.0.0.1");
    kProperties.put(
      kMultiDeploymentTopology,
      "dc1_rack1_node1");
  }
  
  @Override
  protected String getProperty (String name) throws Exception {
    
    return PropertiesLoader.i().getProperty(
      this.getName(),
      name,
      kProperties.get(name) );
  }
}
