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
