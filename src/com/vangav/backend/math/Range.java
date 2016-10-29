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

package com.vangav.backend.math;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Range represents a numerical range from min to max inclusive [min ... max]
 */
public class Range {

  private Number min;
  private Number max;
  private Number magnitude;
  
  /**
   * Constructor Range
   * @return new Range Object
   * @throws Exception
   */
  public Range () {
    
    this.min = null;
    this.max = null;
    this.magnitude = null;
  }
  
  /**
   * Constructor Range
   * @param min
   * @param max
   * @return new Range Object
   * @throws Exception
   */
  public Range (Number min, Number max) throws Exception {
    
    if (min == null || max == null) {
      
      this.min = min;
      this.max = max;
      this.magnitude = null;
      
      return;
    }
    
    if (max.doubleValue() < min.doubleValue() ) {
      
      throw new CodeException(
        "max can't be smaller than min. values: min ["
        + min.doubleValue()
        + "] max ["
        + max.doubleValue()
        + "]",
        ExceptionClass.ARGUMENT);
    }
    
    this.min = min;
    this.max = max;
    this.magnitude = this.max.doubleValue() - this.min.doubleValue();
  }
  
  /**
   * getMin
   * @return this range's min value
   */
  public Number getMin () {
    
    return this.min;
  }
  
  /**
   * getMax
   * @return this range's max value
   */
  public Number getMax () {
    
    return this.max;
  }
  
  /**
   * getMagnitude
   * @return this range's magnitude (e.g.: magnitude of 10 ... 30 range is 20)
   */
  public Number getMagnitude () {
    
    return this.magnitude;
  }
  
  /**
   * isValid
   * a Range is valid if neither its min nor its max is equal to null
   * @return true if this Range is valid and false otherwise
   */
  public boolean isValid () {
    
    if (this.magnitude == null) {
      
      return false;
    }
    
    return true;
  }
  
  /**
   * equal
   * @param range
   * @return true if this range is equal to param range and false otherwise
   */
  public boolean equal (Range range) {
    
    if (this.isValid() == false ||
        range.isValid() == false) {
      
      return false;
    }
    
    if (this.min.doubleValue() == range.min.doubleValue() &&
        this.max.doubleValue() == range.max.doubleValue() ) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * withinRange
   * @param number
   * @return true if param number falls within this range's range (inclusive)
   * @throws Exception
   */
  public boolean withinRange (Number number) throws Exception {
    
    if (number.doubleValue() >= this.min.doubleValue() &&
        number.doubleValue() <= this.max.doubleValue() ) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * shiftToZeroStart
   * @return a new Range Object with min = 0.0 and the same magnitude as
   *           this range
   * @throws Exception
   */
  public Range shiftToZeroStart () throws Exception {
    
    return new Range (0.0, this.magnitude);
  }
  
  /**
   * shiftValueToZeroStart
   * @param number
   * @return corresponding value of param number if this range is shifted
   *           to start at zero
   * @throws Exception
   */
  public Number shiftValueToZeroStart (Number number) throws Exception {

    double shiftValue = 0.0 - this.min.doubleValue();
    
    return number.doubleValue() + shiftValue;
  }

  /**
   * areOverlapping
   * @param range
   * @return if this range and param range overlap and false otherwise
   * @throws Exception
   */
  public boolean areOverlapping (Range range) throws Exception {
    
    if (this.withinRange(range.min) ||
        this.withinRange(range.max) ) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * getIntersection
   * @param range
   * @return a Range representing the intersection of this range and param
   *           range
   *         - if both ranges are invalid
   *             return an invalid range
   *         - if one range is invalid
   *             return the other valid range
   *         - if both ranges don't overlap
   *             return an invalid range
   *         - else
   *             return a new Range Object reflecting the intersection of
   *               this range and param range
   * @throws Exception
   */
  public Range getIntersection (Range range) throws Exception {
    
    if (this.isValid() == false || range.isValid() == false) {
      
      if (this.isValid() == false && range.isValid() == false) {
        
        return new Range();
      }
      
      if (this.isValid() == false) {
        
        return range;
      }
      
      return this;
    }
    
    if (this.areOverlapping(range) == false) {
      
      return new Range();
    }
    
    Number intersectionMin =
      Math.max(this.min.doubleValue(), range.min.doubleValue() );
    Number intersectionMax =
      Math.min(this.max.doubleValue(), range.max.doubleValue() );
    
    return new Range(intersectionMin, intersectionMax);
  }
  
  /**
   * getUniion
   * @param range
   * @return a Range representing the union of this range and param range
   *         - if both ranges are invalid
   *             return an invalid range
   *         - if one range is invalid
   *             return the other valid range
   *         - else
   *             return a new Range Object reflecting the union of
   *               this range and param range
   * @throws Exception
   */
  public Range getUniion (Range range) throws Exception {
    
    if (this.isValid() == false || range.isValid() == false) {
      
      if (this.isValid() == false && range.isValid() == false) {
        
        return new Range();
      }
      
      if (this.isValid() == false) {
        
        return range;
      }
      
      return this;
    }
    
    Number intersectionMin =
      Math.min(this.min.doubleValue(), range.min.doubleValue() );
    Number intersectionMax =
      Math.max(this.max.doubleValue(), range.max.doubleValue() );
    
    return new Range(intersectionMin, intersectionMax);
  }
  
  @Override
  public String toString () {
    
    try {
    
      return
        "Range ["
        + this.min.doubleValue()
        + ", "
        + this.max.doubleValue()
        + "] magnitude ["
        + this.magnitude
        + "]";
    } catch (Exception e) {
      
      return
        "Range [invalid range, either min or max is equal to null]";
    }
  }
}
