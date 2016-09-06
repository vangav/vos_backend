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

package com.vangav.backend.public_apis.facebook.json.edges.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * CreatedTime represents Facebook's Graph API created_time element
 *   e.g.: 2016-08-26T19:23:21+0000
 *   
 * CreatedTime is in UTC time zone
 * 
 * Reference:
 * https://developers.facebook.com/docs/graph-api/reference/v2.7/user
 * */
public class CreatedTime {

  private String createdTime;
  private Date date;
  
  private static final String kDateFormat = "yyyy-MM-dd'T'HH:mm:ss+SSSS";
  
  /**
   * Constructor CreatedTime
   * @param createdTime - created_time element from Facebook's Graph API
   * @throws Exception
   */
  public CreatedTime (String createdTime) throws Exception {
    
    this.createdTime = createdTime;
    
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(kDateFormat);
    
    this.date = simpleDateFormat.parse(this.createdTime);
  }
  
  /**
   * getCreatedTime
   * @return the original created_time element by Facebook's Graph API
   * @throws Exception
   */
  public String getCreatedTime () throws Exception {
    
    return this.createdTime;
  }
  
  /**
   * getDate
   * @return the parsed date from created_time
   * @throws Exception
   */
  public Date getDate () throws Exception {
    
    return this.date;
  }
}
