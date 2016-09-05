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

package com.vangav.backend.public_apis.facebook.json.fields.cover;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vangav.backend.networks.DownloadInl;
import com.vangav.backend.public_apis.facebook.json.fields.FacebookGraphApiField;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Cover represents Facebook's graph API cover field
 * 
 * Reference:
 * https://developers.facebook.com/docs/graph-api/reference/v2.7/user
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cover extends FacebookGraphApiField {

  @Override
  @JsonIgnore
  protected String getName () throws Exception {
    
    return "cover";
  }
  
  @Override
  @JsonIgnore
  protected Cover getThis () throws Exception {
    
    return this;
  }

  @Override
  @JsonIgnore
  public String getFieldName () throws Exception {
    
    return "cover";
  }

  @JsonProperty
  public CoverElement cover;

  @JsonProperty
  public String id;
  
  /**
   * downloadCoverPhoto
   * @return the facebook user's cover photo as a string, and returns null or
   *           throws an exception on failure
   * @throws Exception
   */
  @JsonIgnore
  public String downloadCoverPhoto () throws Exception {
    
    return DownloadInl.downloadFileAsString(this.cover.source);
  }
}
