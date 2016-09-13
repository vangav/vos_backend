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

package com.vangav.backend.public_apis.car2go.json.operation_area;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vangav.backend.networks.rest_client.RestResponseJson;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * OperationAreas represents car2go API's operation areas in a given location
 * 
 * v2.1
 * 
 * Reference:
 * https://github.com/car2go/openAPI
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationAreas extends RestResponseJson {

  @Override
  @JsonIgnore
  public String getName () throws Exception {
    
    return "operation_areas";
  }
  
  @Override
  @JsonIgnore
  public OperationAreas getThis () throws Exception {
    
    return getThis();
  }
  
  @JsonProperty
  public PlaceMark[] placemarks;
}
