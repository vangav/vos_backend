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

import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * TimeOperationsInl is a class with inline static methods for time-related
 *   operations
 * */
public class TimeOperationsInl {
  
  // disable default instantiation
  private TimeOperationsInl () {}
  
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
    
    ArgumentsInl.checkNotNull(
      "Time Unit Type",
      unit,
      ExceptionType.CODE_EXCEPTION);
    
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
