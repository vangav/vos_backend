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

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * OrderByJson represents a cassandra table's OrderBy part
 *   (e.g.: CLUSTERING ORDER BY (comment_time DESC) )
 *   OrderBy column must by part of TableJson > secondary_keys
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderByJson {

  /**
   * column's name (e.g.: user_id)
   */
  @JsonProperty
  public String column_name;
  /**
   * order_type
   * possible values: ASC, DESC
   */
  @JsonProperty
  public String order_type;
  
  @Override
  @JsonIgnore
  public String toString () {
    
    return this.column_name + " : " + this.order_type;
  }
}
