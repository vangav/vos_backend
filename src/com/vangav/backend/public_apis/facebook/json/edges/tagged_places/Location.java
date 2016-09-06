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

package com.vangav.backend.public_apis.facebook.json.edges.tagged_places;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Location represents a data sub-element of Facebook's graph API edge
 *   tagged_places
 * 
 * Reference:
 * https://developers.facebook.com/docs/graph-api/reference/v2.7/user
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

  @JsonProperty
  public String city;
  @JsonProperty
  public int city_id;
  @JsonProperty
  public String country;
  @JsonProperty
  public String country_code;
  @JsonProperty
  public float latitude;
  @JsonProperty
  public float longitude;
  @JsonProperty
  public String name;
  @JsonProperty
  public String region;
  @JsonProperty
  public int region_id;
  @JsonProperty
  public String state;
  @JsonProperty
  public String street;
  @JsonProperty
  public String zip;
}
