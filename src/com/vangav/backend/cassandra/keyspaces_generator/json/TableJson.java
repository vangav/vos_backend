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
