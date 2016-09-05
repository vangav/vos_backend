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

package com.vangav.backend.public_apis.facebook.json.fields.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vangav.backend.public_apis.facebook.json.fields.FacebookGraphApiField;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Location represents Facebook's graph API location field
 * 
 * Reference:
 * https://developers.facebook.com/docs/graph-api/reference/v2.7/user
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location extends FacebookGraphApiField {

  @Override
  @JsonIgnore
  protected String getName () throws Exception {
    
    return "location";
  }
  
  @Override
  @JsonIgnore
  protected Location getThis () throws Exception {
    
    return this;
  }

  @Override
  @JsonIgnore
  public String getFieldName () throws Exception {
    
    return "location";
  }

  @JsonProperty
  public LocationElement location;

  @JsonProperty
  public String id;

  @JsonIgnore
  private static final String kLocationSeparator = ",";
  
  /**
   * getCityName
   * @return this location's city name
   *           e.g.: for location "Paris, France"
   *           this method will return "Paris"
   * @throws Exception
   */
  @JsonIgnore
  public String getCityName () throws Exception {
    
    String[] locationSplit = this.location.name.split(kLocationSeparator);
    
    return locationSplit[0].trim();
  }
  
  /**
   * getCountryName
   * @return this location's country name
   *           e.g.: for location "Paris, France"
   *           this method will return "France"
   * @throws Exception
   */
  @JsonIgnore
  public String getCountryName () throws Exception {
    
    String[] locationSplit = this.location.name.split(kLocationSeparator);
    
    return locationSplit[1].trim();
  }
}
