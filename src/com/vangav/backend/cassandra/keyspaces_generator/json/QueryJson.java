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
 * QueryJson represents a query
 * Every database table is created to put/get data through a specific
 *   set of queries
 * A json query Object is used to generate two methods execute/get for that
 *   query. Execute executes the query sync/async and returns the result set
 *   and get returns the bound statement of the binded query.
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryJson {

  /**
   * description explains the purpose of this query, included in:
   * - generated code comments
   * - generated phriction wiki
   */
  @JsonProperty
  public String description;
  /**
   * UNIQUE per table
   * e.g.:
   * - selectNameAndPrice
   * - insertPhotoComment
   * - etc ...
   * name doubles as:
   * - ID for the query within the table scope
   * - name of the method used to access this query
   */
  @JsonProperty
  public String name;
  /**
   * prepared_statement represents the PreparedStatement for this query
   * e.g.:
   * "insert into store.products (sku, price) values (:sku, :price);"
   * NOTE: use named parameters (:name) instead of anonymous (?)
   * then named parameters are used to generated a binding method this query
   * NOTE: write full query (including keyspace_name.table_name)
   * NOTE: must start with INSERT, SELECT, UPDATE or DELETE
   */
  @JsonProperty
  public String prepared_statement;
}