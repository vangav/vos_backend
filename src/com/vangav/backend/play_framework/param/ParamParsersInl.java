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

package com.vangav.backend.play_framework.param;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ParamParsersInl has static inline methods for parsing params
 * */
public class ParamParsersInl {
  
  // disable default instantiation
  private ParamParsersInl () {}
  
  private static final ArrayList<DateFormat> kDateFormats =
    new ArrayList<DateFormat>() {
    
      /**
       * Generated serial version ID
       */
      private static final long serialVersionUID = 229999665119627316L;

      {
        add(new SimpleDateFormat("dd/MM/yyyy") );
        add(new SimpleDateFormat("d/MM/yyyy") );
        add(new SimpleDateFormat("dd/M/yyyy") );
        add(new SimpleDateFormat("dd/MM/yy") );
        add(new SimpleDateFormat("d/M/yyyy") );
        add(new SimpleDateFormat("dd/M/yy") );
        add(new SimpleDateFormat("d/M/yy") );
      }
    };
  /**
   * parseDate
   * @param dateString
   * @return A Date Object reflecting the param's String date
   * @throws Exception
   */
  public static Date parseDate (String dateString) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "Date String",
      dateString,
      ExceptionType.CODE_EXCEPTION);
    
    for (DateFormat dateFormat : kDateFormats) {
     
      try {
        
        return (Date)dateFormat.parse(dateString);
        
      } catch (Exception e) {
        
      }
    }
    
    throw new CodeException(
      "Date ["
        + dateString
        + "] doesn't follow any of the supported formats.",
      ExceptionClass.FORMATTING);
  }
  
  /**
   * parseCalendar
   * @param dateString
   * @return A Calendar Object reflecting the param's String date
   * @throws Exception
   */
  public static Calendar parseCalendar (String dateString) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "Date String",
      dateString,
      ExceptionType.CODE_EXCEPTION);
    
    Date date = parseDate(dateString);
    
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    
    return calendar;
  }
}
