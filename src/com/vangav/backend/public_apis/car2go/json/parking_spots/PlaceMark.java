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

package com.vangav.backend.public_apis.car2go.json.parking_spots;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * PlaceMark represents a car2go API's parking spot place mark
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
  @JsonProperty
  public int totalCapacity;
  @JsonProperty
  public int usedCapacity;
  @JsonProperty
  public boolean chargingPole;
  
  /**
   * getLongitude
   * @return this parking spot's longitude
   * @throws Exception
   */
  @JsonIgnore
  public double getLongitude () throws Exception {
    
    return this.coordinates[0];
  }
  
  /**
   * getLatitude
   * @return this parking spot's latitude
   * @throws Exception
   */
  @JsonIgnore
  public double getLatitude () throws Exception {
    
    return this.coordinates[1];
  }
  
  /**
   * getAltitude
   * @return this parking spot's altitude
   * @throws Exception
   */
  @JsonIgnore
  public double getAltitude () throws Exception {
    
    return this.coordinates[2];
  }
  
  /**
   * chargingPole
   * @return the number of free parking places in this parking spot
   * @throws Exception
   */
  public int getFreeSpots () throws Exception {
    
    return this.totalCapacity - this.usedCapacity;
  }
}
