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

package com.vangav.backend.public_apis.facebook.json.fields.work;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * CoverElement represent the cover sub-field in Facebook's graph API
 *   cover field
 * 
 * Reference:
 * https://developers.facebook.com/docs/graph-api/reference/v2.7/user
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkElement {

  @JsonProperty
  public String id;
  @JsonProperty
  public String description;
  @JsonProperty
  public Employer employer;
  @JsonProperty
  public String end_date;
  @JsonProperty
  public Location location;
  @JsonProperty
  public Position position;
  @JsonProperty
  public String start_date;
  
  @JsonIgnore
  private static final String kLocationSeparator = ",";
  
  /**
   * getLocationCity
   * @return work location's city
   * @throws Exception
   */
  @JsonIgnore
  public String getLocationCity () throws Exception {
    
    String [] locationSplit = this.location.name.split(kLocationSeparator);
    
    return locationSplit[0].trim();
  }
  
  /**
   * getLocationCountry
   * @return work location's country
   * @throws Exception
   */
  @JsonIgnore
  public String getLocationCountry () throws Exception {
    
    String [] locationSplit = this.location.name.split(kLocationSeparator);
    
    return locationSplit[1].trim();
  }

  @JsonIgnore
  private static final String kDateSeparator = "-";
  
  /**
   * getEndDateYear
   * @return work end date's year
   * @throws Exception
   */
  @JsonIgnore
  public int getEndDateYear () throws Exception {
    
    String [] dateSplit = this.end_date.split(kDateSeparator);
    
    return Integer.parseInt(dateSplit[0] );
  }
  
  /**
   * getEndDateMonth
   * @return work end date's month
   * @throws Exception
   */
  @JsonIgnore
  public int getEndDateMonth () throws Exception {
    
    String [] dateSplit = this.end_date.split(kDateSeparator);
    
    return Integer.parseInt(dateSplit[1] );
  }
  
  /**
   * getEndDateDay
   * @return work end date's day
   * @throws Exception
   */
  @JsonIgnore
  public int getEndDateDay () throws Exception {
    
    String [] dateSplit = this.end_date.split(kDateSeparator);
    
    return Integer.parseInt(dateSplit[2] );
  }
  
  /**
   * getStartDateYear
   * @return work start date's year
   * @throws Exception
   */
  @JsonIgnore
  public int getStartDateYear () throws Exception {
    
    String [] dateSplit = this.start_date.split(kDateSeparator);
    
    return Integer.parseInt(dateSplit[0] );
  }
  
  /**
   * getStartDateMonth
   * @return work start date's month
   * @throws Exception
   */
  @JsonIgnore
  public int getStartDateMonth () throws Exception {
    
    String [] dateSplit = this.start_date.split(kDateSeparator);
    
    return Integer.parseInt(dateSplit[1] );
  }
  
  /**
   * getStartDateDay
   * @return work start date's day
   * @throws Exception
   */
  @JsonIgnore
  public int getStartDateDay () throws Exception {
    
    String [] dateSplit = this.start_date.split(kDateSeparator);
    
    return Integer.parseInt(dateSplit[2] );
  }
}
