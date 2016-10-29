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
