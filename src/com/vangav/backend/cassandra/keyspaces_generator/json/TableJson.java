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
 * TableJson represents a Cassandra keyspace's table (AKA Column Family)
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableJson {

  /**
   * description explains the purpose of this table, used to:
   * - generate comments for tables' generated client code
   * - generate description for phriction wiki table entry
   */
  @JsonProperty
  public String description;
  /**
   * table's name (e.g.: user_info)
   */
  @JsonProperty
  public String name;
  /**
   * table's columns
   */
  @JsonProperty
  public ColumnJson[] columns;
  /**
   * partition_keys (at least one column that defines the table's partition
   *   key)
   * SELECT statement's WHERE must define the partition key
   * NOTE: design it in a way so that a single partition never grows in size
   *   beyond one machine's disk capacity)
   */
  @JsonProperty
  public String[] partition_keys;
  /**
   * OPTIONAL
   * secondary keys are used for sorting data (OrderBy)
   */
  @JsonProperty
  public String[] secondary_keys;
  /**
   * OPTIONAL
   * caching defines how Cassandra should cache this table
   * possible values: ALL, KEYS_ONLY, NONE
   */
  @JsonProperty
  public String caching;
  /**
   * OPTIONAL
   * order_by
   * order table by secondary_keys (ASC or DESC)
   */
  @JsonProperty
  public OrderByJson[] order_by;
  /**
   * queries
   * queries used for this table (e.g.: SELECT, INSERT, UPDATE, DELETE)
   * */
  @JsonProperty
  public QueryJson[] queries;
}
