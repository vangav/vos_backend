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

package com.vangav.backend.public_apis.car2go.json.gas_stations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * PlaceMark represents a car2go API's gas station place mark
 * 
 * v2.1
 * 
 * Reference:
 * https://github.com/car2go/openAPI
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceMark {

  @JsonProperty
  public double[] coordinates;
  @JsonProperty
  public String name;
  
  /**
   * getLongitude
   * @return this gas station's longitude
   * @throws Exception
   */
  @JsonIgnore
  public double getLongitude () throws Exception {
    
    return this.coordinates[0];
  }
  
  /**
   * getLatitude
   * @return this gas station's latitude
   * @throws Exception
   */
  @JsonIgnore
  public double getLatitude () throws Exception {
    
    return this.coordinates[1];
  }
  
  /**
   * getAltitude
   * @return this gas station's altitude
   * @throws Exception
   */
  @JsonIgnore
  public double getAltitude () throws Exception {
    
    return this.coordinates[2];
  }
  
  /**
   * getName
   * @return this gas station's name
   * @throws Exception
   */
  @JsonIgnore
  public String getName () throws Exception {
    
    try {
      
      String[] split = this.name.split(",");
      
      return split[0];
    } catch (Exception e) {
      
      return null;
    }
  }
  
  /**
   * getStreet
   * @return this gas station's street name
   * @throws Exception
   */
  @JsonIgnore
  public String getStreet () throws Exception {
    
    try {
      
      String[] split = this.name.split(",");
      
      String[] secondSplit = split[1].split(" ");
      
      return secondSplit[0];
    } catch (Exception e) {
      
      return null;
    }
  }
  
  /**
   * getNumber
   * @return this gas station's street number
   * @throws Exception
   */
  @JsonIgnore
  public int getNumber () throws Exception {
    
    try {
      
      String[] split = this.name.split(",");
      
      String[] secondSplit = split[1].split(" ");
      
      return Integer.parseInt(secondSplit[1]);
    } catch (Exception e) {
      
      return -1;
    }
  }
}
