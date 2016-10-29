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

package com.vangav.backend.metrics.distance;

import java.util.HashMap;
import java.util.Map;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * DistanceConversionFactorInl is an inline static method class that gets the
 *   factor (double) needed to convert distance from one unit to another
 *   (e.g.: metre to inch, nautical mile to foot, etc ...)
 * */
public class DistanceConversionFactorInl {
  
  /**
   * getConversionFactor
   * returns the factor to multiply the from-value by to get the to-value with
   *   a to-unit
   * @param fromUnit
   * @param toUnit
   * @return multiplication factor
   * @throws Exception
   */
  public static double getConversionFactor (
    DistanceUnitType fromUnit,
    DistanceUnitType toUnit) throws Exception {
    
    double conversionFactor;
    
    try {
      
      conversionFactor = kConversionTable.get(fromUnit).get(toUnit);
    } catch (Exception e) {
      
      throw new CodeException(
        "invalid DistanceUnitType",
        ExceptionClass.TYPE);
    }
    
    if (Double.isNaN(conversionFactor) == true) {
      
      throw new CodeException(
        "invalid DistanceUnitType",
        ExceptionClass.TYPE);
    }
    
    return conversionFactor;
  }

  private static final Map<DistanceUnitType, Map<DistanceUnitType, Double> >
    kConversionTable;
  static {
    
    kConversionTable =
      new HashMap<DistanceUnitType, Map<DistanceUnitType,Double> >();
    
    // KILOMETRE
    
    kConversionTable.put(
      DistanceUnitType.KILOMETRE,
      new HashMap<DistanceUnitType, Double>() );
    
    kConversionTable.get(DistanceUnitType.KILOMETRE).put(
      DistanceUnitType.KILOMETRE,
      1.0);
    kConversionTable.get(DistanceUnitType.KILOMETRE).put(
      DistanceUnitType.METRE,
      1000.0);
    kConversionTable.get(DistanceUnitType.KILOMETRE).put(
      DistanceUnitType.CENTIMETRE,
      100000.0);
    kConversionTable.get(DistanceUnitType.KILOMETRE).put(
      DistanceUnitType.MILLIMETRE,
      1e+6);
    kConversionTable.get(DistanceUnitType.KILOMETRE).put(
      DistanceUnitType.MICROMETRE,
      1e+9);
    kConversionTable.get(DistanceUnitType.KILOMETRE).put(
      DistanceUnitType.NANOMETRE,
      1e+12);
    kConversionTable.get(DistanceUnitType.KILOMETRE).put(
      DistanceUnitType.MILE,
      0.621371);
    kConversionTable.get(DistanceUnitType.KILOMETRE).put(
      DistanceUnitType.YARD,
      1093.61);
    kConversionTable.get(DistanceUnitType.KILOMETRE).put(
      DistanceUnitType.FOOT,
      3280.84);
    kConversionTable.get(DistanceUnitType.KILOMETRE).put(
      DistanceUnitType.INCH,
      39370.1);
    kConversionTable.get(DistanceUnitType.KILOMETRE).put(
      DistanceUnitType.NAUTICAL_MILE,
      0.539957);
    
    // METRE
    
    kConversionTable.put(
      DistanceUnitType.METRE,
      new HashMap<DistanceUnitType, Double>() );
    
    kConversionTable.get(DistanceUnitType.METRE).put(
      DistanceUnitType.KILOMETRE,
      0.001);
    kConversionTable.get(DistanceUnitType.METRE).put(
      DistanceUnitType.METRE,
      1.0);
    kConversionTable.get(DistanceUnitType.METRE).put(
      DistanceUnitType.CENTIMETRE,
      100.0);
    kConversionTable.get(DistanceUnitType.METRE).put(
      DistanceUnitType.MILLIMETRE,
      1000.0);
    kConversionTable.get(DistanceUnitType.METRE).put(
      DistanceUnitType.MICROMETRE,
      1e+6);
    kConversionTable.get(DistanceUnitType.METRE).put(
      DistanceUnitType.NANOMETRE,
      1e+9);
    kConversionTable.get(DistanceUnitType.METRE).put(
      DistanceUnitType.MILE,
      0.000621371);
    kConversionTable.get(DistanceUnitType.METRE).put(
      DistanceUnitType.YARD,
      1.09361);
    kConversionTable.get(DistanceUnitType.METRE).put(
      DistanceUnitType.FOOT,
      3.28084);
    kConversionTable.get(DistanceUnitType.METRE).put(
      DistanceUnitType.INCH,
      39.3701);
    kConversionTable.get(DistanceUnitType.METRE).put(
      DistanceUnitType.NAUTICAL_MILE,
      0.000539957);
    
    // CENTIMETRE
    
    kConversionTable.put(
      DistanceUnitType.CENTIMETRE,
      new HashMap<DistanceUnitType, Double>() );
    
    kConversionTable.get(DistanceUnitType.CENTIMETRE).put(
      DistanceUnitType.KILOMETRE,
      1e-5);
    kConversionTable.get(DistanceUnitType.CENTIMETRE).put(
      DistanceUnitType.METRE,
      0.01);
    kConversionTable.get(DistanceUnitType.CENTIMETRE).put(
      DistanceUnitType.CENTIMETRE,
      1.0);
    kConversionTable.get(DistanceUnitType.CENTIMETRE).put(
      DistanceUnitType.MILLIMETRE,
      10.0);
    kConversionTable.get(DistanceUnitType.CENTIMETRE).put(
      DistanceUnitType.MICROMETRE,
      10000.0);
    kConversionTable.get(DistanceUnitType.CENTIMETRE).put(
      DistanceUnitType.NANOMETRE,
      1e+7);
    kConversionTable.get(DistanceUnitType.CENTIMETRE).put(
      DistanceUnitType.MILE,
      6.2137e-6);
    kConversionTable.get(DistanceUnitType.CENTIMETRE).put(
      DistanceUnitType.YARD,
      0.0109361);
    kConversionTable.get(DistanceUnitType.CENTIMETRE).put(
      DistanceUnitType.FOOT,
      0.0328084);
    kConversionTable.get(DistanceUnitType.CENTIMETRE).put(
      DistanceUnitType.INCH,
      0.393701);
    kConversionTable.get(DistanceUnitType.CENTIMETRE).put(
      DistanceUnitType.NAUTICAL_MILE,
      5.3996e-6);
    
    // MILLIMETRE
    
    kConversionTable.put(
      DistanceUnitType.MILLIMETRE,
      new HashMap<DistanceUnitType, Double>() );
    
    kConversionTable.get(DistanceUnitType.MILLIMETRE).put(
      DistanceUnitType.KILOMETRE,
      1e-6);
    kConversionTable.get(DistanceUnitType.MILLIMETRE).put(
      DistanceUnitType.METRE,
      0.001);
    kConversionTable.get(DistanceUnitType.MILLIMETRE).put(
      DistanceUnitType.CENTIMETRE,
      0.1);
    kConversionTable.get(DistanceUnitType.MILLIMETRE).put(
      DistanceUnitType.MILLIMETRE,
      1.0);
    kConversionTable.get(DistanceUnitType.MILLIMETRE).put(
      DistanceUnitType.MICROMETRE,
      1000.0);
    kConversionTable.get(DistanceUnitType.MILLIMETRE).put(
      DistanceUnitType.NANOMETRE,
      1e+6);
    kConversionTable.get(DistanceUnitType.MILLIMETRE).put(
      DistanceUnitType.MILE,
      6.2137e-7);
    kConversionTable.get(DistanceUnitType.MILLIMETRE).put(
      DistanceUnitType.YARD,
      0.00109361);
    kConversionTable.get(DistanceUnitType.MILLIMETRE).put(
      DistanceUnitType.FOOT,
      0.00328084);
    kConversionTable.get(DistanceUnitType.MILLIMETRE).put(
      DistanceUnitType.INCH,
      0.0393701);
    kConversionTable.get(DistanceUnitType.MILLIMETRE).put(
      DistanceUnitType.NAUTICAL_MILE,
      5.3996e-7);
    
    // MICROMETRE
    
    kConversionTable.put(
      DistanceUnitType.MICROMETRE,
      new HashMap<DistanceUnitType, Double>() );
    
    kConversionTable.get(DistanceUnitType.MICROMETRE).put(
      DistanceUnitType.KILOMETRE,
      1e-9);
    kConversionTable.get(DistanceUnitType.MICROMETRE).put(
      DistanceUnitType.METRE,
      1e-6);
    kConversionTable.get(DistanceUnitType.MICROMETRE).put(
      DistanceUnitType.CENTIMETRE,
      1e-4);
    kConversionTable.get(DistanceUnitType.MICROMETRE).put(
      DistanceUnitType.MILLIMETRE,
      0.001);
    kConversionTable.get(DistanceUnitType.MICROMETRE).put(
      DistanceUnitType.MICROMETRE,
      1.0);
    kConversionTable.get(DistanceUnitType.MICROMETRE).put(
      DistanceUnitType.NANOMETRE,
      1000.0);
    kConversionTable.get(DistanceUnitType.MICROMETRE).put(
      DistanceUnitType.MILE,
      6.2137e-10);
    kConversionTable.get(DistanceUnitType.MICROMETRE).put(
      DistanceUnitType.YARD,
      1.0936e-6);
    kConversionTable.get(DistanceUnitType.MICROMETRE).put(
      DistanceUnitType.FOOT,
      3.2808e-6);
    kConversionTable.get(DistanceUnitType.MICROMETRE).put(
      DistanceUnitType.INCH,
      3.937e-5);
    kConversionTable.get(DistanceUnitType.MICROMETRE).put(
      DistanceUnitType.NAUTICAL_MILE,
      5.3996e-10);
    
    // NANOMETRE
    
    kConversionTable.put(
      DistanceUnitType.NANOMETRE,
      new HashMap<DistanceUnitType, Double>() );
    
    kConversionTable.get(DistanceUnitType.NANOMETRE).put(
      DistanceUnitType.KILOMETRE,
      1e-12);
    kConversionTable.get(DistanceUnitType.NANOMETRE).put(
      DistanceUnitType.METRE,
      1e-9);
    kConversionTable.get(DistanceUnitType.NANOMETRE).put(
      DistanceUnitType.CENTIMETRE,
      1e-7);
    kConversionTable.get(DistanceUnitType.NANOMETRE).put(
      DistanceUnitType.MILLIMETRE,
      1e-6);
    kConversionTable.get(DistanceUnitType.NANOMETRE).put(
      DistanceUnitType.MICROMETRE,
      0.001);
    kConversionTable.get(DistanceUnitType.NANOMETRE).put(
      DistanceUnitType.NANOMETRE,
      1.0);
    kConversionTable.get(DistanceUnitType.NANOMETRE).put(
      DistanceUnitType.MILE,
      6.2137e-13);
    kConversionTable.get(DistanceUnitType.NANOMETRE).put(
      DistanceUnitType.YARD,
      1.0936e-9);
    kConversionTable.get(DistanceUnitType.NANOMETRE).put(
      DistanceUnitType.FOOT,
      3.2808e-9);
    kConversionTable.get(DistanceUnitType.NANOMETRE).put(
      DistanceUnitType.INCH,
      3.937e-8);
    kConversionTable.get(DistanceUnitType.NANOMETRE).put(
      DistanceUnitType.NAUTICAL_MILE,
      5.3996e-13);
    
    // MILE
    
    kConversionTable.put(
      DistanceUnitType.MILE,
      new HashMap<DistanceUnitType, Double>() );
    
    kConversionTable.get(DistanceUnitType.MILE).put(
      DistanceUnitType.KILOMETRE,
      1.60934);
    kConversionTable.get(DistanceUnitType.MILE).put(
      DistanceUnitType.METRE,
      1609.34);
    kConversionTable.get(DistanceUnitType.MILE).put(
      DistanceUnitType.CENTIMETRE,
      160934.0);
    kConversionTable.get(DistanceUnitType.MILE).put(
      DistanceUnitType.MILLIMETRE,
      1.609e+6);
    kConversionTable.get(DistanceUnitType.MILE).put(
      DistanceUnitType.MICROMETRE,
      1.609e+9);
    kConversionTable.get(DistanceUnitType.MILE).put(
      DistanceUnitType.NANOMETRE,
      1.609e+12);
    kConversionTable.get(DistanceUnitType.MILE).put(
      DistanceUnitType.MILE,
      1.0);
    kConversionTable.get(DistanceUnitType.MILE).put(
      DistanceUnitType.YARD,
      1760.0);
    kConversionTable.get(DistanceUnitType.MILE).put(
      DistanceUnitType.FOOT,
      5280.0);
    kConversionTable.get(DistanceUnitType.MILE).put(
      DistanceUnitType.INCH,
      63360.0);
    kConversionTable.get(DistanceUnitType.MILE).put(
      DistanceUnitType.NAUTICAL_MILE,
      0.868976);
    
    // YARD
    
    kConversionTable.put(
      DistanceUnitType.YARD,
      new HashMap<DistanceUnitType, Double>() );
    
    kConversionTable.get(DistanceUnitType.YARD).put(
      DistanceUnitType.KILOMETRE,
      0.0009144);
    kConversionTable.get(DistanceUnitType.YARD).put(
      DistanceUnitType.METRE,
      0.9144);
    kConversionTable.get(DistanceUnitType.YARD).put(
      DistanceUnitType.CENTIMETRE,
      91.44);
    kConversionTable.get(DistanceUnitType.YARD).put(
      DistanceUnitType.MILLIMETRE,
      914.4);
    kConversionTable.get(DistanceUnitType.YARD).put(
      DistanceUnitType.MICROMETRE,
      914400.0);
    kConversionTable.get(DistanceUnitType.YARD).put(
      DistanceUnitType.NANOMETRE,
      9.144e+8);
    kConversionTable.get(DistanceUnitType.YARD).put(
      DistanceUnitType.MILE,
      0.000568182);
    kConversionTable.get(DistanceUnitType.YARD).put(
      DistanceUnitType.YARD,
      1.0);
    kConversionTable.get(DistanceUnitType.YARD).put(
      DistanceUnitType.FOOT,
      3.0);
    kConversionTable.get(DistanceUnitType.YARD).put(
      DistanceUnitType.INCH,
      36.0);
    kConversionTable.get(DistanceUnitType.YARD).put(
      DistanceUnitType.NAUTICAL_MILE,
      0.000493737);
    
    // FOOT
    
    kConversionTable.put(
      DistanceUnitType.FOOT,
      new HashMap<DistanceUnitType, Double>() );
    
    kConversionTable.get(DistanceUnitType.FOOT).put(
      DistanceUnitType.KILOMETRE,
      0.0003048);
    kConversionTable.get(DistanceUnitType.FOOT).put(
      DistanceUnitType.METRE,
      0.3048);
    kConversionTable.get(DistanceUnitType.FOOT).put(
      DistanceUnitType.CENTIMETRE,
      30.48);
    kConversionTable.get(DistanceUnitType.FOOT).put(
      DistanceUnitType.MILLIMETRE,
      304.8);
    kConversionTable.get(DistanceUnitType.FOOT).put(
      DistanceUnitType.MICROMETRE,
      304800.0);
    kConversionTable.get(DistanceUnitType.FOOT).put(
      DistanceUnitType.NANOMETRE,
      3.048e+8);
    kConversionTable.get(DistanceUnitType.FOOT).put(
      DistanceUnitType.MILE,
      0.000189394);
    kConversionTable.get(DistanceUnitType.FOOT).put(
      DistanceUnitType.YARD,
      0.333333);
    kConversionTable.get(DistanceUnitType.FOOT).put(
      DistanceUnitType.FOOT,
      1.0);
    kConversionTable.get(DistanceUnitType.FOOT).put(
      DistanceUnitType.INCH,
      12.0);
    kConversionTable.get(DistanceUnitType.FOOT).put(
      DistanceUnitType.NAUTICAL_MILE,
      0.000164579);
    
    // INCH
    
    kConversionTable.put(
      DistanceUnitType.INCH,
      new HashMap<DistanceUnitType, Double>() );
    
    kConversionTable.get(DistanceUnitType.INCH).put(
      DistanceUnitType.KILOMETRE,
      2.54e-5);
    kConversionTable.get(DistanceUnitType.INCH).put(
      DistanceUnitType.METRE,
      0.0254);
    kConversionTable.get(DistanceUnitType.INCH).put(
      DistanceUnitType.CENTIMETRE,
      2.54);
    kConversionTable.get(DistanceUnitType.INCH).put(
      DistanceUnitType.MILLIMETRE,
      25.4);
    kConversionTable.get(DistanceUnitType.INCH).put(
      DistanceUnitType.MICROMETRE,
      25400.0);
    kConversionTable.get(DistanceUnitType.INCH).put(
      DistanceUnitType.NANOMETRE,
      2.54e+7);
    kConversionTable.get(DistanceUnitType.INCH).put(
      DistanceUnitType.MILE,
      1.5783e-5);
    kConversionTable.get(DistanceUnitType.INCH).put(
      DistanceUnitType.YARD,
      0.0277778);
    kConversionTable.get(DistanceUnitType.INCH).put(
      DistanceUnitType.FOOT,
      0.0833333);
    kConversionTable.get(DistanceUnitType.INCH).put(
      DistanceUnitType.INCH,
      1.0);
    kConversionTable.get(DistanceUnitType.INCH).put(
      DistanceUnitType.NAUTICAL_MILE,
      1.3715e-5);
    
    // NAUTICAL_MILE
    
    kConversionTable.put(
      DistanceUnitType.NAUTICAL_MILE,
      new HashMap<DistanceUnitType, Double>() );
    
    kConversionTable.get(DistanceUnitType.NAUTICAL_MILE).put(
      DistanceUnitType.KILOMETRE,
      1.852);
    kConversionTable.get(DistanceUnitType.NAUTICAL_MILE).put(
      DistanceUnitType.METRE,
      1852.0);
    kConversionTable.get(DistanceUnitType.NAUTICAL_MILE).put(
      DistanceUnitType.CENTIMETRE,
      185200.0);
    kConversionTable.get(DistanceUnitType.NAUTICAL_MILE).put(
      DistanceUnitType.MILLIMETRE,
      1.852e+6);
    kConversionTable.get(DistanceUnitType.NAUTICAL_MILE).put(
      DistanceUnitType.MICROMETRE,
      1.852e+9);
    kConversionTable.get(DistanceUnitType.NAUTICAL_MILE).put(
      DistanceUnitType.NANOMETRE,
      1.852e+12);
    kConversionTable.get(DistanceUnitType.NAUTICAL_MILE).put(
      DistanceUnitType.MILE,
      1.15078);
    kConversionTable.get(DistanceUnitType.NAUTICAL_MILE).put(
      DistanceUnitType.YARD,
      2025.37);
    kConversionTable.get(DistanceUnitType.NAUTICAL_MILE).put(
      DistanceUnitType.FOOT,
      6076.12);
    kConversionTable.get(DistanceUnitType.NAUTICAL_MILE).put(
      DistanceUnitType.INCH,
      72913.4);
    kConversionTable.get(DistanceUnitType.NAUTICAL_MILE).put(
      DistanceUnitType.NAUTICAL_MILE,
      1.0);
  }
}
