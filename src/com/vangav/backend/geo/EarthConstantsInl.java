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

package com.vangav.backend.geo;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.math.Range;
import com.vangav.backend.metrics.distance.Distance;
import com.vangav.backend.metrics.distance.DistanceUnitType;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * EarthConstantsInl has Earth's measurement constants
 * */
public class EarthConstantsInl {
  
  // disable default instantiation
  private EarthConstantsInl () {}

  // radius
  /**
   * kRadiusM: Earth's radius in metres [6371000]
   */
  public static final Distance kRadiusM =
    new Distance(6371000, DistanceUnitType.METRE);
  /**
   * kRadiusKm: Earth's radius in kilometres [6371]
   */
  public static final Distance kRadiusKm =
    new Distance(6371, DistanceUnitType.KILOMETRE);
  
  // map width
  /**
   * kMapWidthM: Earth's map width in metres [40750000]
   */
  public static final Distance kMapWidthM =
    new Distance(40750000, DistanceUnitType.METRE);
  /**
   * kMapWidthKm: Earth's map width in kilometres [40750]
   */
  public static final Distance kMapWidthKm =
    new Distance(40750, DistanceUnitType.KILOMETRE);
  
  // map length
  /**
   * kMapLengthM: Earth's map length in metres [20375000]
   */
  public static final Distance kMapLengthM =
    new Distance(20375000, DistanceUnitType.METRE);
  /**
   * kMapLengthKm: Earth's map length in metres [20375]
   */
  public static final Distance kMapLengthKm =
    new Distance(20375, DistanceUnitType.KILOMETRE);
  
  /**
   * kLatitudeRange: Earth's latitude range
   */
  public static final Range kLatitudeRange;
  static {
    
    try {
      
      kLatitudeRange = new Range(-90, 90);
    } catch (Exception e) {
      
      throw new CodeException(
        61,
        1,
        "couldn't initialize Earth's latitude range (-90, 90)",
        ExceptionClass.INITIALIZATION);
    }
  }
  /**
   * kLongitudeRange: Earth's longitude range
   */
  public static final Range kLongitudeRange;
  static {
    
    try {
      
      kLongitudeRange = new Range(-180, 180);
    } catch (Exception e) {
      
      throw new CodeException(
        61,
        2,
        "couldn't initialize Earth's longitude range (-180, 180)",
        ExceptionClass.INITIALIZATION);
    }
  }
  
  /**
   * kLatitudeGaps: Earth's latitude gaps [180]
   */
  public static final double kLatitudeGaps = 180.0;
  /**
   * kLatitudeGaps: Earth's longitude gaps [360]
   */
  public static final double kLongitudeGaps = 360.0;
}
