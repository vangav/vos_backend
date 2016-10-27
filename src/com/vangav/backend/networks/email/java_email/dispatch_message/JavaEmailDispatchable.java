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

package com.vangav.backend.networks.email.java_email.dispatch_message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vangav.backend.content.formatting.SerializationInl;
import com.vangav.backend.dispatcher.DispatchMessage;
import com.vangav.backend.networks.email.java_email.JavaEmail;
import com.vangav.backend.networks.email.java_email.JavaEmailSenderInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * JavaEmailDispatchable represents a dispatchable version of JavaEmail in the
 *   form of a JSON Object
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JavaEmailDispatchable extends DispatchMessage {

  public JavaEmailDispatchable () {
    
  }
  
  @Override
  @JsonIgnore
  protected String getName () throws Exception {
    
    return "JavaEmailDispatchable";
  }
  
  @Override
  @JsonIgnore
  protected DispatchMessage getThis () throws Exception {
    
    return this;
  }
  
  @Override
  @JsonIgnore
  public void execute () throws Exception {
    
    JavaEmail javaEmail = JavaEmail.fromJavaEmailDispatchable(this);
    
    JavaEmailSenderInl.sendEmail(javaEmail);
  }
  
  /**
   * Constructor JavaEmailDispatchable
   * initializes through serializing a JavaEmail Object
   * @param javaEmail
   * @return new JavaEmailDispatchable Object
   * @throws Exception
   */
  @JsonIgnore
  public JavaEmailDispatchable (
    JavaEmail javaEmail) throws Exception {
    
    this.type = "java_email";
    
    this.serialized_message =
      SerializationInl.serializeObject(javaEmail);
  }
}
