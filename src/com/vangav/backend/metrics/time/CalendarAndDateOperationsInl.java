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

import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * CalendarOperationsInl has inline static methods for java.util.Calendar and
 *   java.util.Date
 * */
public class CalendarAndDateOperationsInl {

  // disable default instantiation
  private CalendarAndDateOperationsInl () {}
  
  // CALENDAR OPERATIONS
  
  /**
   * getCurrentCalendar
   * gets a Calendar object that corresponds to the current time/date
   * @return new Calendar Object representing the current time/date
   * @throws Exception
   */
  public static Calendar getCurrentCalendar (
    ) throws Exception {
    
    return getCalendarFromUnixTime(System.currentTimeMillis() );
  }
  
  /**
   * unixTimeToCalendar
   * converts a unix time to a Calendar
   * @param time: unix time
   * @return new Calendar Object reflecting the passed unix time
   * @throws Exception
   */
  public static Calendar getCalendarFromUnixTime (
    final long time) throws Exception {
    
    Calendar calendar = Calendar.getInstance();
    
    calendar.setTimeInMillis(time);
    
    return calendar;
  }
  
  /**
   * getCalendarsFromUnixTimes
   * converts unix times to Calendars
   * @param times
   * @return an array list of Calendar Objects reflecting param unix times
   * @throws Exception
   */
  public static ArrayList<Calendar> getCalendarsFromUnixTimes (
    final long... times) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "Unix Times",
      times,
      ExceptionType.CODE_EXCEPTION);
    
    ArrayList<Calendar> result = new ArrayList<Calendar>();
    
    for (long time : times) {
      
      result.add(getCalendarFromUnixTime(time) );
    }
    
    return result;
  }
  
  /**
   * getCalendarFromDate
   * @param date
   * @return a Calendar Object reflecting param date
   * @throws Exception
   */
  public static Calendar getCalendarFromDate (
    final Date date) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "Date",
      date,
      ExceptionType.CODE_EXCEPTION);
    
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    
    return calendar;
  }
  
  /**
   * getCalendarsFromDates
   * @param dates
   * @return an array list of Calendar Objects reflecting param dates
   * @throws Exception
   */
  public static ArrayList<Calendar> getCalendarsFromDates (
    final Date... dates) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "Dates",
      dates,
      ExceptionType.CODE_EXCEPTION);
    
    ArrayList<Calendar> result = new ArrayList<Calendar>();
    
    for (Date date : dates) {
      
      result.add(getCalendarFromDate(date) );
    }
    
    return result;
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
    final Calendar fromCalendar,
    final Calendar toCalendar) throws Exception {

    ArgumentsInl.checkNotNull(
      "From Calendar",
      fromCalendar,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "To Calendar",
      toCalendar,
      ExceptionType.CODE_EXCEPTION);
    
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
  
  // DATE OPERATIONS
  
  /**
   * getDateFromCalendar
   * @param calendar
   * @return a Date Object reflecting param calendar
   * @throws Exception
   */
  public static Date getDateFromCalendar (
    final Calendar calendar) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "Calendar",
      calendar,
      ExceptionType.CODE_EXCEPTION);
    
    return calendar.getTime();
  }
  
  /**
   * getDatesFromCalendars
   * @param calendars
   * @return array list of Date Objects reflecting param calendars
   * @throws Exception
   */
  public static ArrayList<Date> getDatesFromCalendars (
    final Calendar... calendars) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "Calendars",
      calendars,
      ExceptionType.CODE_EXCEPTION);
    
    ArrayList<Date> result = new ArrayList<Date>();
    
    for (Calendar calendar : calendars) {
      
      result.add(getDateFromCalendar(calendar) );
    }
    
    return result;
  }

  /**
   * getDatesFromTo
   * @param fromDate
   * @param toDate
   * @return a list of dates from param fromDate till param toDate inclusive
   *           e.g.: fromDate = 29/09/2016, toDate 03/10/2016 will produce the
   *           following result list
   *           <29/09/2016, 30/09/2016, 01/10/2016, 02/10/2016, 03/10/2016>
   * @throws Exception
   */
  public static ArrayList<Date> getDatesFromTo (
    final Date fromDate,
    final Date toDate) throws Exception {

    ArgumentsInl.checkNotNull(
      "From Date",
      fromDate,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "To Date",
      toDate,
      ExceptionType.CODE_EXCEPTION);
    
    ArrayList<Date> dates = new ArrayList<Date>();
    
    // one day in milliseconds
    long interval = 24 * 1000 * 60 * 60;

    long currTime = fromDate.getTime();
    long toTime = toDate.getTime();
    
    while (currTime <= toTime) {
    
      dates.add(new Date(currTime) );
      currTime += interval;
    }
    
    return dates;
  }
}
