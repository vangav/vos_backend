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

import java.util.HashMap;
import java.util.Map;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * TimeConversionFactorInl is an inline static method class that gets the
 *   factor (double) needed to convert time from one unit to another
 *   (e.g.: nano seconds to seconds, months to hours, etc ...)
 * */
public class TimeConversionFactorInl {
  
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
    TimeUnitType fromUnit,
    TimeUnitType toUnit) throws Exception {
    
    double conversionFactor;
    
    try {
      
      conversionFactor = kConversionTable.get(fromUnit).get(toUnit);
    } catch (Exception e) {
      
      throw new CodeException(
        "invalid TimeUnitType",
        ExceptionClass.TYPE);
    }
    
    if (Double.isNaN(conversionFactor) == true) {
      
      throw new CodeException(
        "invalid TimeUnitType",
        ExceptionClass.TYPE);
    }
    
    return conversionFactor;
  }

  private static final Map<TimeUnitType, Map<TimeUnitType, Double> >
    kConversionTable;
  static {
    
    kConversionTable = new HashMap<TimeUnitType, Map<TimeUnitType, Double> >();
    
    // NANOSECOND
    
    kConversionTable.put(
      TimeUnitType.NANOSECOND,
      new HashMap<TimeUnitType, Double>() );
    
    kConversionTable.get(TimeUnitType.NANOSECOND).put(
      TimeUnitType.NANOSECOND,
      1.0);
    kConversionTable.get(TimeUnitType.NANOSECOND).put(
      TimeUnitType.MICROSECOND,
      0.001);
    kConversionTable.get(TimeUnitType.NANOSECOND).put(
      TimeUnitType.MILLISECOND,
      1e-6);
    kConversionTable.get(TimeUnitType.NANOSECOND).put(
      TimeUnitType.SECOND,
      1e-9);
    kConversionTable.get(TimeUnitType.NANOSECOND).put(
      TimeUnitType.MINUTE,
      1.6667e-11);
    kConversionTable.get(TimeUnitType.NANOSECOND).put(
      TimeUnitType.HOUR,
      2.7778e-13);
    kConversionTable.get(TimeUnitType.NANOSECOND).put(
      TimeUnitType.DAY,
      1.1574e-14);
    kConversionTable.get(TimeUnitType.NANOSECOND).put(
      TimeUnitType.WEEK,
      1.6534e-15);
    kConversionTable.get(TimeUnitType.NANOSECOND).put(
      TimeUnitType.MONTH,
      3.8052e-16);
    kConversionTable.get(TimeUnitType.NANOSECOND).put(
      TimeUnitType.YEAR,
      3.171e-17);
    kConversionTable.get(TimeUnitType.NANOSECOND).put(
      TimeUnitType.DECADE,
      3.171e-18);
    kConversionTable.get(TimeUnitType.NANOSECOND).put(
      TimeUnitType.CENTURY,
      3.171e-19);
    
    // MICROSECOND
    
    kConversionTable.put(
      TimeUnitType.MICROSECOND,
      new HashMap<TimeUnitType, Double>() );
    
    kConversionTable.get(TimeUnitType.MICROSECOND).put(
      TimeUnitType.NANOSECOND,
      1000.0);
    kConversionTable.get(TimeUnitType.MICROSECOND).put(
      TimeUnitType.MICROSECOND,
      1.0);
    kConversionTable.get(TimeUnitType.MICROSECOND).put(
      TimeUnitType.MILLISECOND,
      0.001);
    kConversionTable.get(TimeUnitType.MICROSECOND).put(
      TimeUnitType.SECOND,
      1e-6);
    kConversionTable.get(TimeUnitType.MICROSECOND).put(
      TimeUnitType.MINUTE,
      1.6667e-8);
    kConversionTable.get(TimeUnitType.MICROSECOND).put(
      TimeUnitType.HOUR,
      2.7778e-10);
    kConversionTable.get(TimeUnitType.MICROSECOND).put(
      TimeUnitType.DAY,
      1.1574e-11);
    kConversionTable.get(TimeUnitType.MICROSECOND).put(
      TimeUnitType.WEEK,
      1.6534e-12);
    kConversionTable.get(TimeUnitType.MICROSECOND).put(
      TimeUnitType.MONTH,
      3.8052e-13);
    kConversionTable.get(TimeUnitType.MICROSECOND).put(
      TimeUnitType.YEAR,
      3.171e-14);
    kConversionTable.get(TimeUnitType.MICROSECOND).put(
      TimeUnitType.DECADE,
      3.171e-15);
    kConversionTable.get(TimeUnitType.MICROSECOND).put(
      TimeUnitType.CENTURY,
      3.171e-16);
    
    // MILLISECOND
    
    kConversionTable.put(
      TimeUnitType.MILLISECOND,
      new HashMap<TimeUnitType, Double>() );
    
    kConversionTable.get(TimeUnitType.MILLISECOND).put(
      TimeUnitType.NANOSECOND,
      1e+6);
    kConversionTable.get(TimeUnitType.MILLISECOND).put(
      TimeUnitType.MICROSECOND,
      1000.0);
    kConversionTable.get(TimeUnitType.MILLISECOND).put(
      TimeUnitType.MILLISECOND,
      1.0);
    kConversionTable.get(TimeUnitType.MILLISECOND).put(
      TimeUnitType.SECOND,
      0.001);
    kConversionTable.get(TimeUnitType.MILLISECOND).put(
      TimeUnitType.MINUTE,
      1.6667e-5);
    kConversionTable.get(TimeUnitType.MILLISECOND).put(
      TimeUnitType.HOUR,
      2.7778e-7);
    kConversionTable.get(TimeUnitType.MILLISECOND).put(
      TimeUnitType.DAY,
      1.1574e-8);
    kConversionTable.get(TimeUnitType.MILLISECOND).put(
      TimeUnitType.WEEK,
      1.6534e-9);
    kConversionTable.get(TimeUnitType.MILLISECOND).put(
      TimeUnitType.MONTH,
      3.8052e-10);
    kConversionTable.get(TimeUnitType.MILLISECOND).put(
      TimeUnitType.YEAR,
      3.171e-11);
    kConversionTable.get(TimeUnitType.MILLISECOND).put(
      TimeUnitType.DECADE,
      3.171e-12);
    kConversionTable.get(TimeUnitType.MILLISECOND).put(
      TimeUnitType.CENTURY,
      3.171e-13);
    
    // SECOND
    
    kConversionTable.put(
      TimeUnitType.SECOND,
      new HashMap<TimeUnitType, Double>() );
    
    kConversionTable.get(TimeUnitType.SECOND).put(
      TimeUnitType.NANOSECOND,
      1e+9);
    kConversionTable.get(TimeUnitType.SECOND).put(
      TimeUnitType.MICROSECOND,
      1e+6);
    kConversionTable.get(TimeUnitType.SECOND).put(
      TimeUnitType.MILLISECOND,
      1000.0);
    kConversionTable.get(TimeUnitType.SECOND).put(
      TimeUnitType.SECOND,
      1.0);
    kConversionTable.get(TimeUnitType.SECOND).put(
      TimeUnitType.MINUTE,
      0.0166667);
    kConversionTable.get(TimeUnitType.SECOND).put(
      TimeUnitType.HOUR,
      0.000277778);
    kConversionTable.get(TimeUnitType.SECOND).put(
      TimeUnitType.DAY,
      1.1574e-5);
    kConversionTable.get(TimeUnitType.SECOND).put(
      TimeUnitType.WEEK,
      1.6534e-6);
    kConversionTable.get(TimeUnitType.SECOND).put(
      TimeUnitType.MONTH,
      3.8052e-7);
    kConversionTable.get(TimeUnitType.SECOND).put(
      TimeUnitType.YEAR,
      3.171e-8);
    kConversionTable.get(TimeUnitType.SECOND).put(
      TimeUnitType.DECADE,
      3.171e-9);
    kConversionTable.get(TimeUnitType.SECOND).put(
      TimeUnitType.CENTURY,
      3.171e-10);
    
    // MINUTE
    
    kConversionTable.put(
      TimeUnitType.MINUTE,
      new HashMap<TimeUnitType, Double>() );
    
    kConversionTable.get(TimeUnitType.MINUTE).put(
      TimeUnitType.NANOSECOND,
      6e+10);
    kConversionTable.get(TimeUnitType.MINUTE).put(
      TimeUnitType.MICROSECOND,
      6e+7);
    kConversionTable.get(TimeUnitType.MINUTE).put(
      TimeUnitType.MILLISECOND,
      60000.0);
    kConversionTable.get(TimeUnitType.MINUTE).put(
      TimeUnitType.SECOND,
      60.0);
    kConversionTable.get(TimeUnitType.MINUTE).put(
      TimeUnitType.MINUTE,
      1.0);
    kConversionTable.get(TimeUnitType.MINUTE).put(
      TimeUnitType.HOUR,
      0.0166667);
    kConversionTable.get(TimeUnitType.MINUTE).put(
      TimeUnitType.DAY,
      0.000694444);
    kConversionTable.get(TimeUnitType.MINUTE).put(
      TimeUnitType.WEEK,
      9.9206e-5);
    kConversionTable.get(TimeUnitType.MINUTE).put(
      TimeUnitType.MONTH,
      2.2831e-5);
    kConversionTable.get(TimeUnitType.MINUTE).put(
      TimeUnitType.YEAR,
      1.9026e-6);
    kConversionTable.get(TimeUnitType.MINUTE).put(
      TimeUnitType.DECADE,
      1.9026e-7);
    kConversionTable.get(TimeUnitType.MINUTE).put(
      TimeUnitType.CENTURY,
      1.9026e-8);
    
    // HOUR
    
    kConversionTable.put(
      TimeUnitType.HOUR,
      new HashMap<TimeUnitType, Double>() );
    
    kConversionTable.get(TimeUnitType.HOUR).put(
      TimeUnitType.NANOSECOND,
      3.6e+12);
    kConversionTable.get(TimeUnitType.HOUR).put(
      TimeUnitType.MICROSECOND,
      3.6e+9);
    kConversionTable.get(TimeUnitType.HOUR).put(
      TimeUnitType.MILLISECOND,
      3.6e+6);
    kConversionTable.get(TimeUnitType.HOUR).put(
      TimeUnitType.SECOND,
      3600.0);
    kConversionTable.get(TimeUnitType.HOUR).put(
      TimeUnitType.MINUTE,
      60.0);
    kConversionTable.get(TimeUnitType.HOUR).put(
      TimeUnitType.HOUR,
      1.0);
    kConversionTable.get(TimeUnitType.HOUR).put(
      TimeUnitType.DAY,
      0.0416667);
    kConversionTable.get(TimeUnitType.HOUR).put(
      TimeUnitType.WEEK,
      0.00595238);
    kConversionTable.get(TimeUnitType.HOUR).put(
      TimeUnitType.MONTH,
      0.00136986);
    kConversionTable.get(TimeUnitType.HOUR).put(
      TimeUnitType.YEAR,
      0.000114155);
    kConversionTable.get(TimeUnitType.HOUR).put(
      TimeUnitType.DECADE,
      1.1416e-5);
    kConversionTable.get(TimeUnitType.HOUR).put(
      TimeUnitType.CENTURY,
      1.1416e-6);
    
    // DAY
    
    kConversionTable.put(
      TimeUnitType.DAY,
      new HashMap<TimeUnitType, Double>() );
    
    kConversionTable.get(TimeUnitType.DAY).put(
      TimeUnitType.NANOSECOND,
      8.64e+13);
    kConversionTable.get(TimeUnitType.DAY).put(
      TimeUnitType.MICROSECOND,
      8.64e+10);
    kConversionTable.get(TimeUnitType.DAY).put(
      TimeUnitType.MILLISECOND,
      8.64e+7);
    kConversionTable.get(TimeUnitType.DAY).put(
      TimeUnitType.SECOND,
      86400.0);
    kConversionTable.get(TimeUnitType.DAY).put(
      TimeUnitType.MINUTE,
      1440.0);
    kConversionTable.get(TimeUnitType.DAY).put(
      TimeUnitType.HOUR,
      24.0);
    kConversionTable.get(TimeUnitType.DAY).put(
      TimeUnitType.DAY,
      1.0);
    kConversionTable.get(TimeUnitType.DAY).put(
      TimeUnitType.WEEK,
      0.142857);
    kConversionTable.get(TimeUnitType.DAY).put(
      TimeUnitType.MONTH,
      0.0328767);
    kConversionTable.get(TimeUnitType.DAY).put(
      TimeUnitType.YEAR,
      0.00273973);
    kConversionTable.get(TimeUnitType.DAY).put(
      TimeUnitType.DECADE,
      0.000273973);
    kConversionTable.get(TimeUnitType.DAY).put(
      TimeUnitType.CENTURY,
      2.7397e-5);
    
    // WEEK
    
    kConversionTable.put(
      TimeUnitType.WEEK,
      new HashMap<TimeUnitType, Double>() );
    
    kConversionTable.get(TimeUnitType.WEEK).put(
      TimeUnitType.NANOSECOND,
      6.048e+14);
    kConversionTable.get(TimeUnitType.WEEK).put(
      TimeUnitType.MICROSECOND,
      6.048e+11);
    kConversionTable.get(TimeUnitType.WEEK).put(
      TimeUnitType.MILLISECOND,
      6.048e+8);
    kConversionTable.get(TimeUnitType.WEEK).put(
      TimeUnitType.SECOND,
      604800.0);
    kConversionTable.get(TimeUnitType.WEEK).put(
      TimeUnitType.MINUTE,
      10080.0);
    kConversionTable.get(TimeUnitType.WEEK).put(
      TimeUnitType.HOUR,
      168.0);
    kConversionTable.get(TimeUnitType.WEEK).put(
      TimeUnitType.DAY,
      7.0);
    kConversionTable.get(TimeUnitType.WEEK).put(
      TimeUnitType.WEEK,
      1.0);
    kConversionTable.get(TimeUnitType.WEEK).put(
      TimeUnitType.MONTH,
      0.230137);
    kConversionTable.get(TimeUnitType.WEEK).put(
      TimeUnitType.YEAR,
      0.0191781);
    kConversionTable.get(TimeUnitType.WEEK).put(
      TimeUnitType.DECADE,
      0.00191781);
    kConversionTable.get(TimeUnitType.WEEK).put(
      TimeUnitType.CENTURY,
      0.000191781);
    
    // MONTH
    
    kConversionTable.put(
      TimeUnitType.MONTH,
      new HashMap<TimeUnitType, Double>() );
    
    kConversionTable.get(TimeUnitType.MONTH).put(
      TimeUnitType.NANOSECOND,
      2.628e+15);
    kConversionTable.get(TimeUnitType.MONTH).put(
      TimeUnitType.MICROSECOND,
      2.628e+12);
    kConversionTable.get(TimeUnitType.MONTH).put(
      TimeUnitType.MILLISECOND,
      2.628e+9);
    kConversionTable.get(TimeUnitType.MONTH).put(
      TimeUnitType.SECOND,
      2.628e+6);
    kConversionTable.get(TimeUnitType.MONTH).put(
      TimeUnitType.MINUTE,
      43800.0);
    kConversionTable.get(TimeUnitType.MONTH).put(
      TimeUnitType.HOUR,
      730.001);
    kConversionTable.get(TimeUnitType.MONTH).put(
      TimeUnitType.DAY,
      30.4167);
    kConversionTable.get(TimeUnitType.MONTH).put(
      TimeUnitType.WEEK,
      4.34524);
    kConversionTable.get(TimeUnitType.MONTH).put(
      TimeUnitType.MONTH,
      1.0);
    kConversionTable.get(TimeUnitType.MONTH).put(
      TimeUnitType.YEAR,
      0.0833334);
    kConversionTable.get(TimeUnitType.MONTH).put(
      TimeUnitType.DECADE,
      0.00833334);
    kConversionTable.get(TimeUnitType.MONTH).put(
      TimeUnitType.CENTURY,
      0.000833334);
    
    // YEAR
    
    kConversionTable.put(
      TimeUnitType.YEAR,
      new HashMap<TimeUnitType, Double>() );
    
    kConversionTable.get(TimeUnitType.YEAR).put(
      TimeUnitType.NANOSECOND,
      3.154e+16);
    kConversionTable.get(TimeUnitType.YEAR).put(
      TimeUnitType.MICROSECOND,
      3.154e+13);
    kConversionTable.get(TimeUnitType.YEAR).put(
      TimeUnitType.MILLISECOND,
      3.154e+10);
    kConversionTable.get(TimeUnitType.YEAR).put(
      TimeUnitType.SECOND,
      3.154e+7);
    kConversionTable.get(TimeUnitType.YEAR).put(
      TimeUnitType.MINUTE,
      525600.0);
    kConversionTable.get(TimeUnitType.YEAR).put(
      TimeUnitType.HOUR,
      8760.0);
    kConversionTable.get(TimeUnitType.YEAR).put(
      TimeUnitType.DAY,
      365.0);
    kConversionTable.get(TimeUnitType.YEAR).put(
      TimeUnitType.WEEK,
      52.1429);
    kConversionTable.get(TimeUnitType.YEAR).put(
      TimeUnitType.MONTH,
      12.0);
    kConversionTable.get(TimeUnitType.YEAR).put(
      TimeUnitType.YEAR,
      1.0);
    kConversionTable.get(TimeUnitType.YEAR).put(
      TimeUnitType.DECADE,
      0.1);
    kConversionTable.get(TimeUnitType.YEAR).put(
      TimeUnitType.CENTURY,
      0.01);
    
    // DECADE
    
    kConversionTable.put(
      TimeUnitType.DECADE,
      new HashMap<TimeUnitType, Double>() );
    
    kConversionTable.get(TimeUnitType.DECADE).put(
      TimeUnitType.NANOSECOND,
      3.154e+17);
    kConversionTable.get(TimeUnitType.DECADE).put(
      TimeUnitType.MICROSECOND,
      3.154e+14);
    kConversionTable.get(TimeUnitType.DECADE).put(
      TimeUnitType.MILLISECOND,
      3.154e+11);
    kConversionTable.get(TimeUnitType.DECADE).put(
      TimeUnitType.SECOND,
      3.154e+8);
    kConversionTable.get(TimeUnitType.DECADE).put(
      TimeUnitType.MINUTE,
      5.256e+6);
    kConversionTable.get(TimeUnitType.DECADE).put(
      TimeUnitType.HOUR,
      87600.0);
    kConversionTable.get(TimeUnitType.DECADE).put(
      TimeUnitType.DAY,
      3650.0);
    kConversionTable.get(TimeUnitType.DECADE).put(
      TimeUnitType.WEEK,
      521.429);
    kConversionTable.get(TimeUnitType.DECADE).put(
      TimeUnitType.MONTH,
      120.0);
    kConversionTable.get(TimeUnitType.DECADE).put(
      TimeUnitType.YEAR,
      10.0);
    kConversionTable.get(TimeUnitType.DECADE).put(
      TimeUnitType.DECADE,
      1.0);
    kConversionTable.get(TimeUnitType.DECADE).put(
      TimeUnitType.CENTURY,
      0.1);
    
    // CENTURY
    
    kConversionTable.put(
      TimeUnitType.CENTURY,
      new HashMap<TimeUnitType, Double>() );
    
    kConversionTable.get(TimeUnitType.CENTURY).put(
      TimeUnitType.NANOSECOND,
      3.154e+18);
    kConversionTable.get(TimeUnitType.CENTURY).put(
      TimeUnitType.MICROSECOND,
      3.154e+15);
    kConversionTable.get(TimeUnitType.CENTURY).put(
      TimeUnitType.MILLISECOND,
      3.154e+12);
    kConversionTable.get(TimeUnitType.CENTURY).put(
      TimeUnitType.SECOND,
      3.154e+9);
    kConversionTable.get(TimeUnitType.CENTURY).put(
      TimeUnitType.MINUTE,
      5.256e+7);
    kConversionTable.get(TimeUnitType.CENTURY).put(
      TimeUnitType.HOUR,
      876000.0);
    kConversionTable.get(TimeUnitType.CENTURY).put(
      TimeUnitType.DAY,
      36500.0);
    kConversionTable.get(TimeUnitType.CENTURY).put(
      TimeUnitType.WEEK,
      5214.29);
    kConversionTable.get(TimeUnitType.CENTURY).put(
      TimeUnitType.MONTH,
      1200.0);
    kConversionTable.get(TimeUnitType.CENTURY).put(
      TimeUnitType.YEAR,
      100.0);
    kConversionTable.get(TimeUnitType.CENTURY).put(
      TimeUnitType.DECADE,
      10.0);
    kConversionTable.get(TimeUnitType.CENTURY).put(
      TimeUnitType.CENTURY,
      1.0);
  }
}
