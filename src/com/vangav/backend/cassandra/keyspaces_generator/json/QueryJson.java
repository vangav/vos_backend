/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * MIT License
 *
 * Copyright (c) 2016 Vangav
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
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