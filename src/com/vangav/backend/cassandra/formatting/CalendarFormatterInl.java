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

package com.vangav.backend.cassandra.formatting;

import java.util.ArrayList;
import java.util.Calendar;

import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * CalendarFormatterInl has inline methods for formatting java.util.Calendar
 *   Objects into cassandra-valid-types
 * */
public class CalendarFormatterInl {

  // disable default instantiation
  private CalendarFormatterInl () {}
  
  private static final String kDefaultConcatString = "_";
  
  /**
   * concatCalendarsFields
   * @param calendars
   * @param fields
   * @return array list of strings corresponding to param calendars where each
   *           string represents the calendar's param fields concatenated using
   *           underscores (_)
   * @throws Exception
   */
  public static ArrayList<String> concatCalendarsFields (
    ArrayList<Calendar> calendars,
    int... fields) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "Calendars",
      calendars,
      ExceptionType.CODE_EXCEPTION);
    
    ArrayList<String> result = new ArrayList<String>();
    
    for (Calendar calendar : calendars) {
      
      result.add(concatCalendarFields(calendar, fields) );
    }
    
    return result;
  }
  
  /**
   * concatCalendarFields
   * @param calendar
   * @param fields
   * @return a string corresponding to param calendar representing the
   *           calendar's param fields concatenated using underscores (_)
   * @throws Exception
   */
  public static String concatCalendarFields (
    Calendar calendar,
    int... fields) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "Calendar",
      calendar,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "fields",
      fields,
      ExceptionType.CODE_EXCEPTION);
    
    StringBuffer stringBuffer = new StringBuffer();
    
    for (int i = 0; i < fields.length; i ++) {
      
      stringBuffer.append(calendar.get(fields[i] ) );
      
      if (i < (fields.length - 1) ) {
        
        stringBuffer.append(kDefaultConcatString);
      }
    }
    
    return stringBuffer.toString();
  }
}
