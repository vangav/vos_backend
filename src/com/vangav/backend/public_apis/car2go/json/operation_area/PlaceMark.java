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

package com.vangav.backend.public_apis.car2go.json.operation_area;

import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * PlaceMark represents a car2go API's operation area place mark
 * 
 * v2.1
 * 
 * Reference:
 * https://github.com/car2go/openAPI
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceMark {

  @JsonProperty
  public float[] coordinates;
  @JsonProperty
  public String name;
  @JsonProperty
  public String zoneType;

  /**
   * enum ZoneType represents an operation area's zone type
   * */
  public enum ZoneType {
    
    /**
     * zones included in the operating area
     * */
    INCLUDED,
    /**
     * zones excluded from the operating area (no parking allowed)
     * */
    EXCLUDED,
    /**
     * zone is a dedicated car2go parking spot
     * */
    PARKING
  }
  
  /**
   * getZoneType
   * @return ZoneType enum of this operation area
   * @throws Exception
   */
  @JsonIgnore
  public ZoneType getZoneType () throws Exception {

    return ZoneType.valueOf(this.zoneType.toUpperCase(Locale.ENGLISH) );
  }
}
