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

package com.vangav.backend.play_framework.request.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ResponseBodyFile file type of response body
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ResponseBodyFile extends ResponseBody {

  @Override
  @JsonIgnore
  final protected ResponseType getType () {
    
    return ResponseType.FILE;
  }
  
  /**
   * filePath: response File path
   */
  @JsonIgnore
  private String filePath;

  /**
   * setFilePath
   * alternative to overriding the method below
   *   setFilePath (Object... args)
   *   this method can be directly called instead
   * @param filePath
   * @throws Exception
   */
  @JsonIgnore
  final public void setFilePath (String filePath) throws Exception {
    
    this.filePath = filePath;
  }
  
  @Override
  @JsonIgnore
  final protected Object getContent () throws Exception {
    
    return this.filePath;
  }
}
