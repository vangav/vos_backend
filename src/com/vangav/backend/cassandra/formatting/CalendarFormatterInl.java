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
