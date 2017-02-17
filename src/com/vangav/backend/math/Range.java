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

import java.util.ArrayList;

import com.vangav.backend.content.generation.RandomGeneratorInl;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

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
        121,
        2,
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
   * getUnion
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
  public Range getUnion (Range range) throws Exception {
    
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
  
  /**
   * enum SamplingFunction represents the type of function to be used when
   *   sampling a range
   * default: UNIFORM
   * 
   * UNIFORM: the delta between any two consecutive samples is the same
   * GAUSSIAN: more samples towards the mid-point of the range
   * INVERSE_GAUSSIAN: more samples towards the min/max of the range
   */
  public enum SamplingFunction {
    
    UNIFORM,
    GAUSSIAN,
    INVERSE_GAUSSIAN
  }
  
  /**
   * enum SamplingType represents how the sampling is done
   * default: EXACT
   * 
   * EXACT: returns the exact calculated sample
   * RANDOM: after calculating a sample, it picks a random value around the
   *           the calculated sample so that the random value falls within the
   *           range and doesn't overlap with the random range of any of the
   *           neighboring samples
   */
  public enum SamplingType {
    
    EXACT,
    RANDOM
  }
  
  /**
   * sampleRange - overload
   * samples this range
   * @param samplesCount
   * @return the calculated samples
   * @throws Exception
   */
  public ArrayList<Double> sampleRange (
    int samplesCount) throws Exception {
    
    return
      this.sampleRange(
        samplesCount,
        SamplingFunction.UNIFORM,
        SamplingType.EXACT);
  }
  
  /**
   * sampleRange - overload
   * samples this range
   * @param samplesCount
   * @param samplingFunction
   * @return the calculated samples
   * @throws Exception
   */
  public ArrayList<Double> sampleRange (
    int samplesCount,
    SamplingFunction samplingFunction) throws Exception {
    
    return
      this.sampleRange(
        samplesCount,
        samplingFunction,
        SamplingType.EXACT);
  }
  
  /**
   * sampleRange - overload
   * samples this range
   * @param samplesCount
   * @param samplingType
   * @return the calculated samples
   * @throws Exception
   */
  public ArrayList<Double> sampleRange (
    int samplesCount,
    SamplingType samplingType) throws Exception {
    
    return
      this.sampleRange(
        samplesCount,
        SamplingFunction.UNIFORM,
        samplingType);
  }
  
  /**
   * sampleRange
   * samples this range
   * @param samplesCount
   * @param samplingFunction
   * @param samplingType
   * @return the calculated samples
   * @throws Exception
   */
  public ArrayList<Double> sampleRange (
    int samplesCount,
    SamplingFunction samplingFunction,
    SamplingType samplingType) throws Exception {
    
    ArgumentsInl.checkIntGreaterThanOrEqual(
      "samples count",
      samplesCount,
      1,
      ExceptionType.CODE_EXCEPTION);
    
    if (samplingFunction == SamplingFunction.UNIFORM) {
      
      return this.sampleRangeUniform(samplesCount, samplingType);
    } else if (samplingFunction == SamplingFunction.GAUSSIAN) {
      
      return this.sampleRangeGaussian(samplesCount, samplingType);
    } else if (samplingFunction == SamplingFunction.INVERSE_GAUSSIAN) {
      
      return this.sampleRangeInverseGaussian(samplesCount, samplingType);
    }
    
    return null;
  }
  
  /**
   * sampleRangeUniform
   * samples this range using the uniform sampling function
   * @param samplesCount
   * @param samplingType
   * @return the calculated samples
   * @throws Exception
   */
  private ArrayList<Double> sampleRangeUniform (
    int samplesCount,
    SamplingType samplingType) throws Exception {
    
    ArrayList<Double> result = new ArrayList<Double>();
    
    double minimum = this.min.doubleValue();
    double maximum = this.max.doubleValue();
    // get the delta between each two consecutive samples
    double delta = ((maximum - minimum) / (samplesCount + 1) );
    double currSample;
    
    for (int i = 1; i <= samplesCount; i ++) {
      
      currSample = (minimum + (delta * i) );
      
      if (samplingType == SamplingType.RANDOM) {
        
        // get a random value within this sample's range
        result.add(
          RandomGeneratorInl.generateRandomDouble(
            (currSample - (delta / 2.0) ),
            (currSample + (delta / 2.0) )) );
      } else {
        
        result.add(currSample);
      }
    }
    
    return result;
  }
  
  /**
   * sampleRangeGaussian
   * samples this range using the gaussian sampling function
   * @param samplesCount
   * @param samplingType
   * @return the calculated samples
   * @throws Exception
   */
  private ArrayList<Double> sampleRangeGaussian (
    int samplesCount,
    SamplingType samplingType) throws Exception {
    
    ArrayList<Double> result = new ArrayList<Double>();
    
    double minimum = this.min.doubleValue();
    double maximum = this.max.doubleValue();
    double midPoint = ((maximum + minimum) / 2.0);
    
    int halfSamples = (samplesCount / 2);
    double halfDistance = ((midPoint - minimum) / 2.0);
    double currDelta;
    double currSample;
    
    // for the first half of the samples
    for (int i = 1; i <= halfSamples; i ++) {
      
      // get sample's value
      currSample =
        (minimum + (halfDistance - (halfDistance / Math.pow(2, i) ) ) );
      
      if (samplingType == SamplingType.RANDOM) {
        
        // get sample's range
        currDelta =
          (((halfDistance - (halfDistance / Math.pow(2, i) ) ) -
           (halfDistance - (halfDistance / Math.pow(2, (i - 1) ) ) ) ) /
           2.0);
        
        // get a random value within this sample's range
        result.add(
          RandomGeneratorInl.generateRandomDouble(
            (currSample - currDelta),
            (currSample + currDelta) ) );
      } else {
        
        result.add(currSample);
      }
    }
    
    // get a middle sample
    if (samplesCount %2 == 1) {
      
      if (samplingType == SamplingType.RANDOM) {
        
        // get sample's range
        currDelta =
          (((halfDistance - (halfDistance / Math.pow(2, halfSamples + 1) ) ) -
           (halfDistance - (halfDistance / Math.pow(2, halfSamples) ) ) ) /
           2.0);
        
        // get a random value within this sample's range
        result.add(
          RandomGeneratorInl.generateRandomDouble(
            (midPoint - currDelta),
            (midPoint + currDelta) ) );
      } else {
        
        result.add(midPoint);
      }
    }
    
    // for the second hald of the samples
    for (int i = halfSamples; i >= 1; i --) {
      
      // get sample's value
      currSample =
        (halfDistance + (halfDistance - (halfDistance / Math.pow(2, i) ) ) );
      
      if (samplingType == SamplingType.RANDOM) {
        
        // get sample's range
        currDelta =
          (((halfDistance - (halfDistance / Math.pow(2, i) ) ) -
           (halfDistance - (halfDistance / Math.pow(2, (i - 1) ) ) ) ) /
           2.0);
        
        // get a random value within this sample's range
        result.add(
          RandomGeneratorInl.generateRandomDouble(
            (currSample - currDelta),
            (currSample + currDelta) ) );
      } else {
        
        result.add(currSample);
      }
    }
    
    return result;
  }
  
  /**
   * sampleRangeInverseGaussian
   * samples this range using the inverse gaussian sampling function
   * @param samplesCount
   * @param samplingType
   * @return the calculated samples
   * @throws Exception
   */
  private ArrayList<Double> sampleRangeInverseGaussian (
    int samplesCount,
    SamplingType samplingType) throws Exception {
    
    ArrayList<Double> result = new ArrayList<Double>();
    
    double minimum = this.min.doubleValue();
    double maximum = this.max.doubleValue();
    double midPoint = ((maximum + minimum) / 2.0);
    
    int halfSamples = (samplesCount / 2);
    double halfDistance = ((midPoint - minimum) / 2.0);
    double currDelta;
    double currSample;
    
    // for the first half of the samples
    for (int i = halfSamples; i >= 1; i --) {
      
      // get sample's value
      currSample =
        (minimum + (halfDistance - (halfDistance / Math.pow(2, i) ) ) );
      
      if (samplingType == SamplingType.RANDOM) {
        
        // get sample's range
        currDelta =
          (((halfDistance - (halfDistance / Math.pow(2, i) ) ) -
           (halfDistance - (halfDistance / Math.pow(2, (i - 1) ) ) ) ) /
           2.0);
        
        // get a random value within this sample's range
        result.add(
          RandomGeneratorInl.generateRandomDouble(
            (currSample - currDelta),
            (currSample + currDelta) ) );
      } else {
        
        result.add(currSample);
      }
    }
    
    // get a middle sample
    if (samplesCount %2 == 1) {
      
      if (samplingType == SamplingType.RANDOM) {
        
        // get sample's range
        currDelta = halfDistance / 4.0;
        
        // get a random value within this sample's range
        result.add(
          RandomGeneratorInl.generateRandomDouble(
            (midPoint - currDelta),
            (midPoint + currDelta) ) );
      } else {
        
        result.add(midPoint);
      }
    }
    
    // for the second hald of the samples
    for (int i = 1; i <= halfSamples; i ++) {
      
      // get sample's value
      currSample =
        (halfDistance + (halfDistance - (halfDistance / Math.pow(2, i) ) ) );
      
      if (samplingType == SamplingType.RANDOM) {
        
        // get sample's range
        currDelta =
          (((halfDistance - (halfDistance / Math.pow(2, i) ) ) -
           (halfDistance - (halfDistance / Math.pow(2, (i - 1) ) ) ) ) /
           2.0);
        
        // get a random value within this sample's range
        result.add(
          RandomGeneratorInl.generateRandomDouble(
            (currSample - currDelta),
            (currSample + currDelta) ) );
      } else {
        
        result.add(currSample);
      }
    }
    
    return result;
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
