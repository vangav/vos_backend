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

package com.vangav.backend.metrics.time;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Period: e.g.: 2 seconds, 3 hours, 2 months, 5 weeks, etc ...
 * */
public class Period {

  private double value;
  private TimeUnitType unit;
  
  /**
   * Constructor Period
   * @param value: number of units (e.g.: 5 seconds)
   * @param unit: e.g.: second, hour, week, etc ...
   * @return new Period Object
   * @throws Exception
   */
  public Period (
    double value,
    TimeUnitType unit) {
    
    if (value < 0.0) {
      
      throw new CodeException(
        "Period value ["
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
        "Period value ["
        + value
        + "] can't be negative",
        ExceptionClass.INVALID);
    }
    
    this.value = value;
  }
  
  /**
   * getValue
   * @return period's value
   */
  public final double getValue () {
    
    return this.value;
  }
  
  /**
   * getUnit
   * @return period's unit
   */
  public final TimeUnitType getUnit () {
    
    return this.unit;
  }
  
  /**
   * getAs
   * gets a new Period with the new unit having the same period span as this
   *   Period
   * @param toUnit
   * @return new Period with a similar span and the new unit
   * @throws Exception
   */
  public Period getAs (TimeUnitType toUnit) throws Exception {

    double newValue =
      this.value *
      TimeConversionFactorInl.getConversionFactor(this.unit, toUnit);
    
    return new Period(newValue, toUnit);
  }
  
  /**
   * equal
   * @param Period
   * @return true if this Period and param Period are equal in both value
   *           and unit, and false otherwise
   * @throws Exception
   */
  public boolean equal (Period Period) throws Exception {
    
    if (this.value == Period.value &&
        this.unit == Period.unit) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * equalUnit
   * @param Period
   * @return true if this Period and param Period has the same unit
   *           and false otherwise
   * @throws Exception
   */
  public boolean equalUnit (Period Period) throws Exception {
    
    if (this.unit == Period.unit) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * smallerThan
   * @param Period
   * @return true if this Period is smaller than param Period and false
   *           otherwise
   * @throws Exception
   */
  public boolean smallerThan (Period Period) throws Exception {
    
    Period convertedPeriod = Period.getAs(this.unit);
    
    if (this.value < convertedPeriod.value) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * smallerThanOrEqual
   * @param Period
   * @return true if this Period is smaller or equal than param Period and
   *           false otherwise
   * @throws Exception
   */
  public boolean smallerThanOrEqual (Period Period) throws Exception {
    
    Period convertedPeriod = Period.getAs(this.unit);
    
    if (this.value <= convertedPeriod.value) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * greaterThan
   * @param Period
   * @return true if this Period is greater than param Period and false
   *           otherwise
   * @throws Exception
   */
  public boolean greaterThan (Period Period) throws Exception {
    
    Period convertedPeriod = Period.getAs(this.unit);
    
    if (this.value > convertedPeriod.value) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * greaterThanOrEqual
   * @param Period
   * @return true if this Period is greater than or equal then param Period
   *           and false otherwise
   * @throws Exception
   */
  public boolean greaterThanOrEqual (Period Period) throws Exception {
    
    Period convertedPeriod = Period.getAs(this.unit);
    
    if (this.value >= convertedPeriod.value) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * plus
   * @param value
   * @return a new Period Object with the added Period of this Period
   *           and param value with this Period's unit
   * @throws Exception
   */
  public Period plus (double value) throws Exception {
    
    return new Period(this.value + value, this.unit);
  }
  
  /**
   * plus
   * @param value
   * @return a new Period Object with the added Period of this Period
   *           and param Period's value with this Period's unit
   * @throws Exception
   */
  public Period plus (Period Period) throws Exception {
    
    Period convertedPeriod = Period.getAs(this.unit);
    
    return new Period (this.value + convertedPeriod.value, this.unit);
  }
  
  /**
   * minus
   * @param value
   * @return a new Period Object with this Period's unit and
   *           new value = this value - param value
   * @throws Exception
   */
  public Period minus (double value) throws Exception {
    
    if (value > this.value) {
      
      throw new CodeException(
        "Period minus operation will lead to a negative Period, that's an "
        + "invalid operation, minus value must be smaller than or equal to "
        + "current value. Current value ["
        + this.value
        + "] minus value ["
        + value
        + "].",
        ExceptionClass.INVALID);
    }
    
    return new Period(this.value - value, this.unit);
  }
  
  /**
   * minus
   * @param value
   * @return a new Period Object with this Period's unit and
   *           new value = this value - param Period's value
   * @throws Exception
   */
  public Period minus (Period Period) throws Exception {
    
    Period convertedPeriod = Period.getAs(this.unit);
    
    if (convertedPeriod.value > this.value) {
      
      throw new CodeException(
        "Period minus operation will lead to a negative Period, that's an "
        + "invalid operation, minus value must be smaller than or equal to "
        + "current value. Current value ["
        + this.value
        + "] minus value ["
        + convertedPeriod.value
        + "].",
        ExceptionClass.INVALID);
    }
    
    return new Period (this.value - convertedPeriod.value, this.unit);
  }
  
  /**
   * multiply
   * @param value
   * @return a new Period Object with this Period's unit and
   *           a new value = this value * param value
   * @throws Exception
   */
  public Period multiply (double value) throws Exception {
    
    return new Period (this.value * value, this.unit);
  }
  
  /**
   * multiply
   * @param value
   * @return a new Period Object with this Period's unit and
   *           a new value = this value * param Period's value
   * @throws Exception
   */
  public Period multiply (Period Period) throws Exception {
    
    Period convertedPeriod = Period.getAs(Period.unit);
    
    return new Period (this.value * convertedPeriod.value, this.unit);
  }
  
  /**
   * divide
   * @param value
   * @return a new Period Object with this Period's unit and
   *           a new value = this value / param value
   * @throws Exception
   */
  public Period divide (double value) throws Exception {
    
    return new Period(this.value / value, this.unit);
  }
  
  /**
   * divide
   * @param value
   * @return a new Period Object with this Period's unit and
   *           a new value = this value / param Period's value
   * @throws Exception
   */
  public Period divide (Period Period) throws Exception {
    
    return new Period(this.value / Period.value, this.unit);
  }
  
  @Override
  public String toString () {
    
    return
      "Period ("
      + this.value
      + " "
      + this.unit.toString()
      + ")";
  }
}
