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

package com.vangav.backend.public_apis.facebook.json.fields.education;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * EducationItem represents and element of the education array sub-field in Facebook's
 *   graph API education field
 * 
 * Reference:
 * https://developers.facebook.com/docs/graph-api/reference/v2.7/user
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EducationItem {

  @JsonProperty
  public Degree degree;
  @JsonProperty
  public School school;
  @JsonProperty
  public String type;
  @JsonProperty
  public Year year;
  @JsonProperty
  public String id;
}
