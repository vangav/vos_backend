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

package com.vangav.backend.public_apis.facebook.json.fields;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Birthday represents Facebook's graph API birthday field
 * 
 * Reference:
 * https://developers.facebook.com/docs/graph-api/reference/v2.7/user
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Birthday extends FacebookGraphApiField {

  @Override
  @JsonIgnore
  protected String getName () throws Exception {
    
    return "birthday";
  }
  
  @Override
  @JsonIgnore
  protected Birthday getThis () throws Exception {
    
    return this;
  }

  @Override
  @JsonIgnore
  public String getFieldName () throws Exception {
    
    return "birthday";
  }

  @JsonProperty
  public String birthday;

  @JsonProperty
  public String id;
  
  @JsonIgnore
  private static final String kDateSeparator = "/";
  
  /**
   * getMonth
   * @return the birthday's month number
   * @throws Exception
   */
  @JsonIgnore
  public int getMonth () throws Exception {
    
    String[] birthdaySplit = this.birthday.split(kDateSeparator);
    
    return Integer.parseInt(birthdaySplit[0] );
  }
  
  /**
   * getDay
   * @return the birthday's day number
   * @throws Exception
   */
  @JsonIgnore
  public int getDay () throws Exception {
    
    String[] birthdaySplit = this.birthday.split(kDateSeparator);
    
    return Integer.parseInt(birthdaySplit[1] );
  }
  
  /**
   * getYear
   * @return the birthday's year number
   * @throws Exception
   */
  @JsonIgnore
  public int getYear () throws Exception {
    
    String[] birthdaySplit = this.birthday.split(kDateSeparator);
    
    return Integer.parseInt(birthdaySplit[2] );
  }
}
