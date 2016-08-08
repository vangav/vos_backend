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

package com.vangav.backend.metrics.distance;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Distance: e.g.: 1 kilo metre, 3 yards, 56 nautical miles, etc ...
 * */
public class Distance {
  
  private double value;
  private DistanceUnitType unit;
  
  /**
   * Constructor Distance
   * @param value: number of units (e.g.: 5 yards)
   * @param unit: e.g.: metre, mile, yard, etc ...
   * @return new Distance Object
   * @throws Exception
   */
  public Distance (
    double value,
    DistanceUnitType unit) {
    
    if (value < 0.0) {
      
      throw new CodeException(
        "Distance value ["
        + value
        + "] can't be negative",
        ExceptionClass.INVALID);
    }
   
    this.value = value;
    this.unit = unit;
  }

  /**
   * setValue
   * @param value
   */
  public void setValue (double value) {
    
    if (value < 0.0) {
      
      throw new CodeException(
        "Distance value ["
        + value
        + "] can't be negative",
        ExceptionClass.INVALID);
    }
    
    this.value = value;
  }
  
  /**
   * getValue
   * @return distance's value
   */
  public final double getValue () {
    
    return this.value;
  }
  
  /**
   * getUnit
   * @return distance's unit
   */
  public final DistanceUnitType getUnit () {
    
    return this.unit;
  }
  
  /**
   * getAs
   * gets a new Distance with the new unit having the same distance span as
   *   this Distance
   * @param toUnit
   * @return new Distance with a similar span and the new unit
   * @throws Exception
   */
  public Distance getAs (DistanceUnitType toUnit) throws Exception {
    
    double newValue =
      this.value *
      DistanceConversionFactorInl.getConversionFactor(this.unit, toUnit);
    
    return new Distance(newValue, toUnit);
  }
  
  /**
   * equal
   * @param distance
   * @return true if this Distance and param distance are equal in both value
   *           and unit, and false otherwise
   * @throws Exception
   */
  public boolean equal (Distance distance) throws Exception {
    
    if (this.value == distance.value &&
        this.unit == distance.unit) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * equalUnit
   * @param distance
   * @return true if this Distance and param distance has the same unit and
   *           false otherwise
   * @throws Exception
   */
  public boolean equalUnit (Distance distance) throws Exception {
    
    if (this.unit == distance.unit) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * smallerThan
   * @param distance
   * @return true if this Distance is smaller than param distance and false
   *           otherwise
   * @throws Exception
   */
  public boolean smallerThan (Distance distance) throws Exception {
    
    Distance convertedDistance = distance.getAs(this.unit);
    
    if (this.value < convertedDistance.value) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * smallerThanOrEqual
   * @param distance
   * @return true if this Distance is smaller or equal than param distance and
   *           false otherwise
   * @throws Exception
   */
  public boolean smallerThanOrEqual (Distance distance) throws Exception {
    
    Distance convertedDistance = distance.getAs(this.unit);
    
    if (this.value <= convertedDistance.value) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * greaterThan
   * @param distance
   * @return true if this Distance is greater than param distance and false
   *           otherwise
   * @throws Exception
   */
  public boolean greaterThan (Distance distance) throws Exception {
    
    Distance convertedDistance = distance.getAs(this.unit);
    
    if (this.value > convertedDistance.value) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * greaterThanOrEqual
   * @param distance
   * @return true if this Distance is greater than or equal then param distance
   *           and false otherwise
   * @throws Exception
   */
  public boolean greaterThanOrEqual (Distance distance) throws Exception {
    
    Distance convertedDistance = distance.getAs(this.unit);
    
    if (this.value >= convertedDistance.value) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * plus
   * @param value
   * @return a new Distance Object with the added distance of this Distance
   *           and param value with this Distance's unit
   * @throws Exception
   */
  public Distance plus (double value) throws Exception {
    
    return new Distance(this.value + value, this.unit);
  }
  
  /**
   * plus
   * @param value
   * @return a new Distance Object with the added distance of this Distance
   *           and param distance's value with this Distance's unit
   * @throws Exception
   */
  public Distance plus (Distance distance) throws Exception {
    
    Distance convertedDistance = distance.getAs(this.unit);
    
    return new Distance (this.value + convertedDistance.value, this.unit);
  }
  
  /**
   * minus
   * @param value
   * @return a new Distance Object with this Distance's unit and
   *           new value = this value - param value
   * @throws Exception
   */
  public Distance minus (double value) throws Exception {
    
    if (value > this.value) {
      
      throw new CodeException(
        "Distance minus operation will lead to a negative distance, that's an "
        + "invalid operation, minus value must be smaller than or equal to "
        + "current value. Current value ["
        + this.value
        + "] minus value ["
        + value
        + "].",
        ExceptionClass.INVALID);
    }
    
    return new Distance(this.value - value, this.unit);
  }
  
  /**
   * minus
   * @param value
   * @return a new Distance Object with this Distance's unit and
   *           new value = this value - param distance's value
   * @throws Exception
   */
  public Distance minus (Distance distance) throws Exception {
    
    Distance convertedDistance = distance.getAs(this.unit);
    
    if (convertedDistance.value > this.value) {
      
      throw new CodeException(
        "Distance minus operation will lead to a negative distance, that's an "
        + "invalid operation, minus value must be smaller than or equal to "
        + "current value. Current value ["
        + this.value
        + "] minus value ["
        + convertedDistance.value
        + "].",
        ExceptionClass.INVALID);
    }
    
    return new Distance (this.value - convertedDistance.value, this.unit);
  }
  
  /**
   * multiply
   * @param value
   * @return a new Distance Object with this Distance's unit and
   *           a new value = this value * param value
   * @throws Exception
   */
  public Distance multiply (double value) throws Exception {
    
    return new Distance (this.value * value, this.unit);
  }
  
  /**
   * multiply
   * @param value
   * @return a new Distance Object with this Distance's unit and
   *           a new value = this value * param distance's value
   * @throws Exception
   */
  public Distance multiply (Distance distance) throws Exception {
    
    Distance convertedDistance = distance.getAs(distance.unit);
    
    return new Distance (this.value * convertedDistance.value, this.unit);
  }
  
  /**
   * divide
   * @param value
   * @return a new Distance Object with this Distance's unit and
   *           a new value = this value / param value
   * @throws Exception
   */
  public Distance divide (double value) throws Exception {
    
    return new Distance(this.value / value, this.unit);
  }
  
  /**
   * divide
   * @param value
   * @return a new Distance Object with this Distance's unit and
   *           a new value = this value / param distance's value
   * @throws Exception
   */
  public Distance divide (Distance distance) throws Exception {
    
    return new Distance(this.value / distance.value, this.unit);
  }
  
  @Override
  public String toString () {
    
    return
      "Distance ("
      + this.value
      + " "
      + this.unit.toString()
      + ")";
  }
}
