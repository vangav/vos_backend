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

package com.vangav.backend.metrics.time;

import java.util.Calendar;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * RoundedOffCalendarInl has inline static method to:
 *   - check if a calendar is rounded off to a specific factor
 *   - round off a calendar to a specific factor:
 *     - nearest
 *     - future
 *     - past
 */
public class RoundedOffCalendarInl {

  /**
   * enum RoundingType defines how to round a calendar
   */
  public enum RoundingType {
    
    NEAREST,
    FUTURE,
    PAST;
    
    /**
     * getDefault
     * @return the default rounding type: NEAREST
     */
    public static RoundingType getDefault () {
      
      return NEAREST;
    }
  }
  
  /**
   * enum RoundingFactor defines the factors according to which a calendar can
   *   be rounded
   * i.e.: exact start of a year, month, minute, ...
   */
  public enum RoundingFactor {
    
    YEAR,
    MONTH,
    DAY_OF_MONTH,
    HOUR_OF_DAY,
    MINUTE,
    SECOND;
    
    /**
     * getPrevMidPoint
     * @return the mid point of the previous factor
     *         e.g.:
     *           YEAR: 6 months
     *           MONTH: 15 days
     *           DAY: 12 hours
     *           HOUR: 30 minutes
     *           MINUTE: 30 seconds
     *           SECOND: 500 milli seconds
     * @throws Exception
     */
    private int getPrevMidPoint () throws Exception {
      
      switch (this) {
        
        case YEAR:
          return 6;
        case MONTH:
          return 15;
        case DAY_OF_MONTH:
          return 12;
        case HOUR_OF_DAY:
          return 30;
        case MINUTE:
          return 30;
        case SECOND:
          return 500;
        default:
          throw new CodeException(
            122,
            11,
            "Unhandled RoundingFactor type ["
              + this.toString()
              + "]",
            ExceptionClass.TYPE);  
      }
    }
    
    /**
     * getCalendarIndex
     * @return the calendar index of this factor
     * @throws Exception
     */
    private int getCalendarIndex () throws Exception {
      
      switch (this) {
        
        case YEAR:
          return Calendar.YEAR;
        case MONTH:
          return Calendar.MONTH;
        case DAY_OF_MONTH:
          return Calendar.DAY_OF_MONTH;
        case HOUR_OF_DAY:
          return Calendar.HOUR_OF_DAY;
        case MINUTE:
          return Calendar.MINUTE;
        case SECOND:
          return Calendar.SECOND;
        default:
          throw new CodeException(
            122,
            11,
            "Unhandled RoundingFactor type ["
              + this.toString()
              + "]",
            ExceptionClass.TYPE);  
      }
    }
    
    /**
     * getPrevCalendarIndex
     * @return the calendar index of the factor preceding this factor
     * @throws Exception
     */
    private int getPrevCalendarIndex () throws Exception {
      
      switch (this) {
        
        case YEAR:
          return Calendar.MONTH;
        case MONTH:
          return Calendar.DAY_OF_MONTH;
        case DAY_OF_MONTH:
          return Calendar.HOUR_OF_DAY;
        case HOUR_OF_DAY:
          return Calendar.MINUTE;
        case MINUTE:
          return Calendar.SECOND;
        case SECOND:
          return Calendar.MILLISECOND;
        default:
          throw new CodeException(
            122,
            12,
            "Unhandled RoundingFactor type ["
              + this.toString()
              + "]",
            ExceptionClass.TYPE);  
      }
    }
    
    /**
     * getPrevCalendarIndexes
     * @return all the previous indexes of the factors preceding this factor
     * @throws Exception
     */
    private int[] getPrevCalendarIndexes () throws Exception {
      
      switch (this) {
        
        case YEAR:
          return
            new int [] {
              Calendar.MONTH,
              Calendar.DAY_OF_MONTH,
              Calendar.HOUR_OF_DAY,
              Calendar.MINUTE,
              Calendar.SECOND,
              Calendar.MILLISECOND
            };
        case MONTH:
          return
            new int [] {
              Calendar.DAY_OF_MONTH,
              Calendar.HOUR_OF_DAY,
              Calendar.MINUTE,
              Calendar.SECOND,
              Calendar.MILLISECOND
            };
        case DAY_OF_MONTH:
          return
            new int [] {
              Calendar.HOUR_OF_DAY,
              Calendar.MINUTE,
              Calendar.SECOND,
              Calendar.MILLISECOND
            };
        case HOUR_OF_DAY:
          return
            new int [] {
              Calendar.MINUTE,
              Calendar.SECOND,
              Calendar.MILLISECOND
            };
        case MINUTE:
          return
            new int [] {
              Calendar.SECOND,
              Calendar.MILLISECOND
            };
        case SECOND:
          return
            new int [] {
              Calendar.MILLISECOND
            };
        default:
          throw new CodeException(
            122,
            13,
            "Unhandled RoundingFactor type ["
              + this.toString()
              + "]",
            ExceptionClass.TYPE);  
      }
    }
  }
  
  /**
   * isCalendarRounded
   * @param calendar
   * @param roundingFactor
   * @return true if param calendar is already rounded off to param
   *           roundingFactor
   * @throws Exception
   */
  public static boolean isCalendarRounded (
    Calendar calendar,
    RoundingFactor roundingFactor) throws Exception {
    
    int[] prevIndexes =
      roundingFactor.getPrevCalendarIndexes();
    
    for (int prevFactor : prevIndexes) {
      
      if (calendar.get(prevFactor) != 0) {
        
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * getRoundedCalendar
   * overload
   * @param roundingType
   * @param roundingFactor
   * @return gets the current calendar and round it off
   * @throws Exception
   */
  public static Calendar getRoundedCalendar (
    RoundingType roundingType,
    RoundingFactor roundingFactor) throws Exception {
    
    return getRoundedCalendar(
      CalendarAndDateOperationsInl.getCurrentCalendar(),
      roundingType,
      roundingFactor);
  }

    
  /**
   * getRoundedCalendar
   * overload
   * @param unixTimeMillis
   * @param roundingType
   * @param roundingFactor
   * @return get a calendar from param unixTimeMillis and round it off
   * @throws Exception
   */
  public static Calendar getRoundedCalendar (
    long unixTimeMillis,
    RoundingType roundingType,
    RoundingFactor roundingFactor) throws Exception {
    
    return getRoundedCalendar(
      CalendarAndDateOperationsInl.getCalendarFromUnixTime(unixTimeMillis),
      roundingType,
      roundingFactor);
  }
  
  /**
   * getRoundedCalendar
   * @param calendar
   * @param roundingType
   * @param roundingFactor
   * @return round param calendar off to param roundingFactor using param
   *           roundingType
   * @throws Exception
   */
  public static Calendar getRoundedCalendar (
    Calendar calendar,
    RoundingType roundingType,
    RoundingFactor roundingFactor) throws Exception {
    
    // check method's arguments
    
    ArgumentsInl.checkNotNull(
      "calendar",
      calendar,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "roundingType",
      roundingType,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "roundingFactor",
      roundingFactor,
      ExceptionType.CODE_EXCEPTION);
    
    // calendar rounded off already?
    if (isCalendarRounded(calendar, roundingFactor) == true) {
      
      return calendar;
    }
    
    // round the calendar off
    if (roundingType == RoundingType.NEAREST) {
      
      if (calendar.get(roundingFactor.getPrevCalendarIndex() ) >
          roundingFactor.getPrevMidPoint() ) {
        
        calendar.add(
          roundingFactor.getCalendarIndex(),
          1);
      }
    } else if (roundingType == RoundingType.FUTURE) {
      
      calendar.add(
        roundingFactor.getCalendarIndex(),
        1);
    } else if (roundingType == RoundingType.PAST) {
      
      calendar.add(
        roundingFactor.getCalendarIndex(),
        -1);
    } else {

      throw new CodeException(
        122,
        14,
        "Unhandled RoundingType type ["
          + roundingType.toString()
          + "]",
        ExceptionClass.TYPE);  
    }
    
    // reset the smaller factors to 0 to complete rounding
    int [] prevIndexes =
      roundingFactor.getPrevCalendarIndexes();
    
    for (int prevIndex : prevIndexes) {
      
      calendar.set(prevIndex, 0);
    }
    
    return calendar;
  }
}
