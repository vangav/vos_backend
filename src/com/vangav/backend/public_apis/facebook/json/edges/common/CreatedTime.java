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
