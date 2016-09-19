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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * CalendarOperationsInl has inline static methods for java.util.Calendar
 * */
public class CalendarOperationsInl {

  // disable default instantiation
  private CalendarOperationsInl () {}
  
  /**
   * getCalendarFromDate
   * @param date
   * @return a Calendar Object reflecting param date
   * @throws Exception
   */
  public static Calendar getCalendarFromDate (Date date) throws Exception {
    
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    
    return calendar;
  }
  
  /**
   * getCalendarsFromTo
   * @param fromCalendar
   * @param toCalendar
   * @return a list of calendars from param fromCalendar till param toCalendar
   *           inclusive, e.g.: fromCalendar = 29/09/2016,
   *           toCalendar 03/10/2016 will produce the following result list
   *           <29/09/2016, 30/09/2016, 01/10/2016, 02/10/2016, 03/10/2016>
   * @throws Exception
   */
  public static ArrayList<Calendar> getCalendarsFromTo (
    Calendar fromCalendar,
    Calendar toCalendar) throws Exception {
    
    Date fromDate = fromCalendar.getTime();
    Date toDate = toCalendar.getTime();
    
    ArrayList<Calendar> calendars = new ArrayList<Calendar>();
    
    // one day in milliseconds
    long interval = 24 * 1000 * 60 * 60;

    long currTime = fromDate.getTime();
    long toTime = toDate.getTime();
    
    while (currTime <= toTime) {
    
      calendars.add(getCalendarFromDate(new Date(currTime) ) );
      currTime += interval;
    }
    
    return calendars;
  }
}
