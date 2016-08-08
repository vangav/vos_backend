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

package com.vangav.backend.cassandra.keyspaces_generator.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * KeyspaceJson represents a Cassandra's keyspace
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyspaceJson {

  /**
   * description explains the purpose of this keyspace, used to:
   * - generate comments for keyspace's generated client code
   * - generate description for phriction wiki keyspace entry
   */
  @JsonProperty
  public String description;
  /**
   * keyspace's name (e.g.: analysis)
   */
  @JsonProperty
  public String name;
  /**
   * replications
   * different replication methods for this keyspace (e.g.: single node
   *   and one replica for dev mode and multi-node multi-replica for prod
   *   mode)
   */
  @JsonProperty
  public ReplicationJson[] replications;
  /**
   * keyspace's tables
   */
  @JsonProperty
  public TableJson[] tables;
  
  /**
   * fromJsonString
   * @param json
   * @return KeyspaceJson Object reflecting param json String
   * @throws Exception
   */
  @JsonIgnore
  public static KeyspaceJson fromJsonString (String json) throws Exception {
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    return objectMapper.readValue(json, KeyspaceJson.class);
  }
  
  /**
   * getAsString
   * @return String representation of this JSON Object
   * @throws Exception
   */
  @JsonIgnore
  public String getAsString () throws Exception {
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    return
      objectMapper.writerWithDefaultPrettyPrinter(
        ).writeValueAsString(this);
  }
  
  @Override
  @JsonIgnore
  public String toString () {
    
    try {
      
      return
        "Cassandra Keyspace:\n"
        + this.getAsString();
    } catch (Exception e) {
      
      return
        "Cassandra Keyspace: threw an Exception!";
    }
  }
}
