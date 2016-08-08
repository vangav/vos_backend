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

import java.util.Calendar;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * TimeOperationsInl is a class with inline static methods for time-related
 *   operations
 * */
public class TimeOperationsInl {
  
  /**
   * getCurrentCalendar
   * gets a Calendar object that corresponds to the current time/date
   * @return new Calendar Object representing the current time/date
   * @throws Exception
   */
  public static Calendar getCurrentCalendar (
    ) throws Exception {
    
    return unixTimeToCalendar(
      System.currentTimeMillis() );
  }
  
  /**
   * unixTimeToCalendar
   * converts a unix time to a Calendar
   * @param time: unix time
   * @return new Calendar Object reflecting the passed unix time
   * @throws Exception
   */
  public static Calendar unixTimeToCalendar (
    final long time) throws Exception {
    
    Calendar calendar = Calendar.getInstance();
    
    calendar.setTimeInMillis(time);
    
    return calendar;
  }
  
  /**
   * getElapsedUnixTime
   * given a unix start time, it returns the elapsed time in a specific
   *   time unit
   * @param time: start unix time
   * @param unit: unit to return elapsed time in
   * @return elapsed time in unit (default is milliseconds)
   * @throws Exception
   */
  public static long getElapsedUnixTime (
    final long time,
    TimeUnitType unit) throws Exception {
    
    long diff;
    
    diff = (System.currentTimeMillis() ) - time;
    
    if (unit == TimeUnitType.MILLISECOND) {
      
      return diff;
    }
    
    if (unit == TimeUnitType.SECOND) {
      
      return diff / 1000L;
    }
    
    if (unit == TimeUnitType.MINUTE) {
      
      return (diff / (1000L * 60L) );
    }
    
    if (unit == TimeUnitType.HOUR) {
      
      return (diff / (1000L * 60L * 60L) );
    }
    
    if (unit == TimeUnitType.DAY) {
      
      return (diff / (1000L * 60L * 60L * 24L) );
    }
    
    if (unit == TimeUnitType.WEEK) {
      
      return (diff / (1000L * 60L * 60L * 24L * 7L) );
    }
    
    return diff;
  }
}
