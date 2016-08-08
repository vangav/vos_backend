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

import java.io.FileInputStream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;

import play.mvc.Content;
import play.mvc.Result;
import play.mvc.Results;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ResponseBody is the parent class for a Response Body to be returned
 *   for a request
 * */
public abstract class ResponseBody {
  
  /**
   * getName
   * @return the name of the child class inheriting from this class
   * @throws Exception
   */
  @JsonIgnore
  protected abstract String getName () throws Exception;
  
  /**
   * getThis
   * @return the child instance inheriting from this class
   * @throws Exception
   */
  @JsonIgnore
  protected abstract ResponseBody getThis () throws Exception;

  /**
   * enum ResponseType
   */
  public enum ResponseType {
    
    JSON,
    FILE,
    HTML
  }
  
  /**
   * getType
   * used to format the response's string the right way (e.g.: in case of
   *   a json response, the string represents the response's content. in
   *   case of file response, the string represents path to the file, etc ...)
   * @return response body type (json, file, etc ...)
   */
  @JsonIgnore
  protected abstract ResponseType getType ();
  
  /**
   * getAsString
   * @return string representation of the response in String format
   * @throws Exception
   */
  @JsonIgnore
  protected abstract Object getContent () throws Exception;
  
  /**
   * getResult
   * @return response's body as a play framework Result
   * @throws Exception
   */
  @JsonIgnore
  public Result getResult () throws Exception {
    
    if (this.getType() == ResponseType.JSON) {
    
      return Results.ok((String)this.getContent() );
    } else if (this.getType() == ResponseType.FILE) {
    
      return Results.ok(new FileInputStream((String)this.getContent() ) );
    } else if (this.getType() == ResponseType.HTML) {
      
      return Results.ok((Content)this.getContent() );
    }
    
    throw new CodeException(
      "Unhandled ResponseBody Type ["
      + this.getType().toString()
      + "]",
      ExceptionClass.TYPE);
  }
  
  /**
   * getBadRequestResult
   * NOTE: override this method to alter its behavior (e.g.: add info about
   *         the reasons for returning a bad request)
   * @return default bad request response
   * @throws Exception
   */
  @JsonIgnore
  public Result getBadRequestResult () throws Exception {
    
    return Results.badRequest();
  }
  
  /**
   * getInternalServerErrorResult
   * NOTE: override this method to alter its behavior (e.g.: add info about
   *         the reasons for returning an internal server error)
   * @return default internal server error response
   * @throws Exception
   */
  @JsonIgnore
  public Result getInternalServerErrorResult () throws Exception {
    
    return Results.internalServerError();
  }
  
  @Override
  @JsonIgnore
  public String toString () {
    
    try {
    
      if (this.getType() == ResponseType.JSON ||
          this.getType() == ResponseType.FILE) {
        return
          "Response Body ["
          + this.getName()
          + "]:\n"
          + (String)this.getContent();
      } else if (this.getThis().getType() == ResponseType.HTML) {
        
        return
          "Response Body ["
          + this.getName()
          + "]: HTML Content";
      } else {
        
        return
          "Response Body: Exception, unknown response type ["
          + this.getThis().toString()
          + "]";
      }
    } catch (Exception e) {
      
      return
        "Response Body: threw an Exception!";
    }
  }
}
