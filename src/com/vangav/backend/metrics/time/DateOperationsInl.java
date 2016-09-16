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
import java.util.Date;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * DateOperationsInl has inine static methods for java.util.Date
 * */
public class DateOperationsInl {

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
  public ArrayList<Date> getDatesFromTo (
    Date fromDate,
    Date toDate) throws Exception {
    
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
