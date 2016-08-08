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
 * ColumnJson represents a Cassandra table's column (name and type)
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColumnJson {

  /**
   * name is the cassandra's column name
   * (e.g.: user_name, phone_number, etc ...)
   */
  @JsonProperty
  public String name;
  /**
   * type of data to be stored in a column
   * possible values -->
   * http://docs.datastax.com/en/cql/3.1/cql/cql_reference/cql_data_types_c.html
   * ascii
   * bigint
   * blob
   * boolean
   * counter
   * decimal
   * double
   * float
   * frozen
   * inet
   * int
   * list
   * map
   * set
   * text
   * timestamp
   * timeuuid
   * tuple
   * uuid
   * varchar
   * varint
   */
  @JsonProperty
  public String type;
  
  @Override
  @JsonIgnore
  public String toString () {
    
    return this.type + " : " + this.name;
  }
}
