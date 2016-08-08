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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ReplicationJson represents a Cassandra keyspace's replication
 * e.g.:
 * - 'class': 'NetworkTopologyStrategy', 'DC1' : 1, 'DC2' : 1
 * - 'class': 'SimpleStrategy', 'replication_factor' : 1
 * In production there's only one replication method for a keyspace, support
 *   for multiple replication methods per keyspace in vangav_backend is useful
 *   for cases like (in dev one wants to deploy a single replica keyspace while
 *   in production the keyspace has 2 replicas with two data centers per
 *   replica)
 * CQL keyspace Generator generates a script per replica method
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicationJson {

  /**
   * description explains the use of this replication method
   *   e.g.:
   *   - used for single replica dev local deployment
   *   - used for multiple replica prod deployment
   */
  @JsonProperty
  public String description;
  /**
   * name used as an identifier for this replication method
   */
  @JsonProperty
  public String name;
  /**
   * replication method
   * e.g.:
   * - 'class': 'NetworkTopologyStrategy', 'DC1' : 1, 'DC2' : 1
   * - 'class': 'SimpleStrategy', 'replication_factor' : 1
   * */
  @JsonProperty
  public String replication;
}
