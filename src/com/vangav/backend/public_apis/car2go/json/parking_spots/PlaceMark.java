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
  public float[] coordinates;
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
  public float getLongitude () throws Exception {
    
    return this.coordinates[0];
  }
  
  /**
   * getLatitude
   * @return this parking spot's latitude
   * @throws Exception
   */
  @JsonIgnore
  public float getLatitude () throws Exception {
    
    return this.coordinates[1];
  }
  
  /**
   * getAltitude
   * @return this parking spot's altitude
   * @throws Exception
   */
  @JsonIgnore
  public float getAltitude () throws Exception {
    
    return this.coordinates[2];
  }
  
  /**
   * chargingPole
   * @return the number of free parking places in this parking spot
   * @throws Exception
   */
  @JsonIgnore
  public int getFreeSpots () throws Exception {
    
    return this.totalCapacity - this.usedCapacity;
  }
}
