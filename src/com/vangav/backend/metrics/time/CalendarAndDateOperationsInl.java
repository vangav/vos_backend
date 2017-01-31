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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.vangav.backend.data_structures_and_algorithms.tuple.Pair;
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
  
  private static final DateFormat kDefaultDateFormat =
    new SimpleDateFormat("dd/MM/yyyy");
  
  /**
   * getFormattedDate
   * @param calendar
   * @return e.g: 22/10/1987
   * @throws Exception
   */
  public static String getFormattedDate (
    final Calendar calendar) throws Exception {
    
    return getFormattedDate(kDefaultDateFormat, calendar);
  }
  
  /**
   * getFormattedDate
   * @param dateFormat
   * @param calendar
   * @return e.g: 22/10/1987
   * @throws Exception
   */
  public static String getFormattedDate (
    final DateFormat dateFormat,
    final Calendar calendar) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "Date Format",
      dateFormat,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "Calendar",
      calendar,
      ExceptionType.CODE_EXCEPTION);
    
    return dateFormat.format(getDateFromCalendar(calendar) );
  }
  
  /**
   * getFormattedDates
   * @param calendars
   * @return e.g.: 22/10/1987, 06/12/2013, ...
   * @throws Exception
   */
  public static ArrayList<String> getFormattedDates (
    final Calendar... calendars) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "Calendars",
      calendars,
      ExceptionType.CODE_EXCEPTION);
    
    ArrayList<String> formattedDates = new ArrayList<String>();
    
    for (Calendar calendar : calendars) {
      
      formattedDates.add(getFormattedDate(calendar) );
    }
    
    return formattedDates;
  }
  
  /**
   * getFormattedDates
   * @param calendars
   * @return e.g.: 22/10/1987, 06/12/2013, ...
   * @throws Exception
   */
  public static ArrayList<String> getFormattedDates (
    final DateFormat dateFormat,
    final Calendar... calendars) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "Date Format",
      dateFormat,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "Calendars",
      calendars,
      ExceptionType.CODE_EXCEPTION);
    
    ArrayList<String> formattedDates = new ArrayList<String>();
    
    for (Calendar calendar : calendars) {
      
      formattedDates.add(getFormattedDate(dateFormat, calendar) );
    }
    
    return formattedDates;
  }
  
  private static final DateFormat kDefaultTimeFormat =
    new SimpleDateFormat("HH:mm");
  
  /**
   * getFormattedTime
   * @param calendar
   * @return e.g: 02:00
   * @throws Exception
   */
  public static String getFormattedTime (
    final Calendar calendar) throws Exception {
    
    return getFormattedTime(kDefaultTimeFormat, calendar);
  }
  
  /**
   * getFormattedTime
   * @param timeFormat
   * @param calendar
   * @return e.g: 02:00
   * @throws Exception
   */
  public static String getFormattedTime (
    final DateFormat timeFormat,
    final Calendar calendar) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "Time Format",
      timeFormat,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "Calendar",
      calendar,
      ExceptionType.CODE_EXCEPTION);
    
    return timeFormat.format(getDateFromCalendar(calendar) );
  }
  
  /**
   * getFormattedTimes
   * @param calendars
   * @return e.g.: 02:00, 22:00, ...
   * @throws Exception
   */
  public static ArrayList<String> getFormattedTimes (
    final Calendar... calendars) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "Calendars",
      calendars,
      ExceptionType.CODE_EXCEPTION);
    
    ArrayList<String> formattedTimes = new ArrayList<String>();
    
    for (Calendar calendar : calendars) {
      
      formattedTimes.add(getFormattedTime(calendar) );
    }
    
    return formattedTimes;
  }
  
  /**
   * getFormattedTimes
   * @param calendars
   * @return e.g.: 02:00, 22:00, ...
   * @throws Exception
   */
  public static ArrayList<String> getFormattedTimes (
    final DateFormat timeFormat,
    final Calendar... calendars) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "Time Format",
      timeFormat,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "Calendars",
      calendars,
      ExceptionType.CODE_EXCEPTION);
    
    ArrayList<String> formattedTimes = new ArrayList<String>();
    
    for (Calendar calendar : calendars) {
      
      formattedTimes.add(getFormattedTime(timeFormat, calendar) );
    }
    
    return formattedTimes;
  }
  
  /**
   * getWeekCalendarRange
   * @param calendar
   * @return a pair representing:
   *           - first: a Calendar Object representing the first day of param
   *               calendar's week
   *           - second: a Calendar Object representing the last day of param
   *               calendar's week
   *           both first and last day calendars get their hour, minute,
   *             second and millisecond values reset to 0
   * @throws Exception
   */
  public static Pair<Calendar, Calendar> getWeekCalendarRange (
    final Calendar calendar) throws Exception {

    ArgumentsInl.checkNotNull(
      "Calendar",
      calendar,
      ExceptionType.CODE_EXCEPTION);
    
    Calendar startCalendar = (Calendar)calendar.clone();
    
    // reset calendar's day
    startCalendar.set(Calendar.HOUR_OF_DAY, 0);
    startCalendar.set(Calendar.MINUTE, 0);
    startCalendar.set(Calendar.SECOND, 0);
    startCalendar.set(Calendar.MILLISECOND, 0);
    
    // get start of week
    startCalendar.add(
      Calendar.DAY_OF_WEEK, 
      startCalendar.getFirstDayOfWeek()
      - startCalendar.get(Calendar.DAY_OF_WEEK) );
    
    Calendar endCalendar = (Calendar)startCalendar.clone();
    
    // get end of week
    endCalendar.add(Calendar.DAY_OF_YEAR, 6);
    
    return new Pair<Calendar, Calendar>(startCalendar, endCalendar);
  }
  
  /**
   * getWeekCalendarsRanges
   * @param fromCalendar
   * @param toCalendar
   * @return an array list of pairs representing:
   *           - first: a Calendar Object representing the first day of param
   *               calendar's week
   *           - second: a Calendar Object representing the last day of param
   *               calendar's week
   *           both first and last day calendars get their hour, minute,
   *             second and millisecond values reset to 0
   *           Omits duplicate weeks
   * @throws Exception
   */
  public static ArrayList<Pair<Calendar, Calendar> > getWeekCalendarsRanges (
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
    
    ArrayList<Calendar> calendars =
      getCalendarsFromTo(fromCalendar, toCalendar);
    
    ArrayList<Pair<Calendar, Calendar> > ranges =
      new ArrayList<Pair<Calendar, Calendar> >();
    
    Pair<Calendar, Calendar> currRange;
    
    int index = 0;
    
    for (int i = 0; i < calendars.size(); i ++) {
      
      // get week's range (start and end dates)
      currRange = getWeekCalendarRange(calendars.get(i) );
      
      if (i > 0) {
        
        // remove duplicate week
        if ((currRange.getFirst().get(Calendar.YEAR)
              == ranges.get(index - 1).getFirst().get(
                   Calendar.YEAR) ) &&
            (currRange.getFirst().get(Calendar.WEEK_OF_YEAR)
              == ranges.get(index - 1).getFirst().get(
                   Calendar.WEEK_OF_YEAR) ) ) {
          
          continue;
        }
      }
      
      index += 1;
      
      ranges.add(currRange);
    }
    
    return ranges;
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
  
  /**
   * getCalendarWeeksFromTo
   * @param fromCalendar
   * @param toCalendar
   * @return a list of calendars from param fromCalendar till param toCalendar
   *           inclusive representing the first day of each week between both
   *           calendars where each calendar get its hour, minute, second and
   *           millisecond values reset to 0
   * @throws Exception
   */
  public static ArrayList<Calendar> getCalendarWeeksFromTo (
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
    
    ArrayList<Pair<Calendar, Calendar> > weekCalendarsRanges =
      getWeekCalendarsRanges(fromCalendar, toCalendar);
    
    ArrayList<Calendar> calendars = new ArrayList<Calendar>();
    
    for (Pair<Calendar, Calendar> calendarRange : weekCalendarsRanges) {
      
      calendars.add(calendarRange.getFirst() );
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
