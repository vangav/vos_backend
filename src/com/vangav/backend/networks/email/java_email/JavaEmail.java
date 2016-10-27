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

package com.vangav.backend.networks.email.java_email;

import java.io.Serializable;

import com.vangav.backend.content.formatting.SerializationInl;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;
import com.vangav.backend.networks.email.java_email.dispatch_message.JavaEmailDispatchable;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * JavaEmail represents an e-mail that can be sent using JavaMail
 *   http://www.oracle.com/technetwork/java/javamail/index.html
 * */
public class JavaEmail implements Serializable {

  /**
   * Generated serial version ID
   */
  private static final long serialVersionUID = -3647792402944134161L;

  private static final String kEmailsSeparator = ",";
  
  private String fromName;
  private String fromEmail;
  private String toEmails;
  private String ccEmails;
  private String bccEmails;
  private String subject;
  private String bodyText;
  
  /**
   * Enum SslType
   */
  public enum SslType {
    
    WITHOUT_SSL,
    WITH_SSL
  }
  
  private SslType sslType;
  
  /**
   * Constructor JavaEmail
   * @param fromName
   * @param fromEmail
   * @param subject
   * @param bodyText
   * @param sslType
   * @return new JavaEmail Object
   * @throws Exception
   */
  public JavaEmail (
    String fromName,
    String fromEmail,
    String subject,
    String bodyText,
    SslType sslType) throws Exception {
    
    this(fromName, fromEmail, null, subject, bodyText, sslType);
  }
  
  /**
   * Constructor JavaEmail
   * @param fromName
   * @param fromEmail
   * @param toEmail
   * @param subject
   * @param bodyText
   * @param sslType
   * @throws Exception
   */
  public JavaEmail (
    String fromName,
    String fromEmail,
    String toEmail,
    String subject,
    String bodyText,
    SslType sslType) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "from email",
      fromEmail,
      ExceptionType.CODE_EXCEPTION);
    
    this.fromName = fromName;
    this.fromEmail = fromEmail;
    
    if (toEmail != null && toEmail.length() > 0) {
      
      this.toEmails = toEmail;
    } else {
      
      this.toEmails = "";
    }
    
    this.ccEmails = "";
    this.bccEmails = "";
    this.subject = subject;
    this.bodyText = bodyText;
    this.sslType = sslType;
  }
  
  /**
   * fromJavaEmailDispatchable
   * usually used on the worker instance side where dispatch messages are
   *   received then gets executed
   * @param javaEmailDispatchable is a JSON Object with a serialized
   *          version of a JavaEmail Object
   * @return the deserialized version of the JavaEmail Object
   * @throws Exception
   */
  public static JavaEmail fromJavaEmailDispatchable (
    JavaEmailDispatchable javaEmailDispatchable) throws Exception {
    
    return
      SerializationInl.<JavaEmail>deserializeObject(
        javaEmailDispatchable.serialized_message);
  }
  
  /**
   * getFromName
   * @return email's from-name
   * @throws Exception
   */
  public String getFromName () throws Exception {
    
    return this.fromName;
  }
  
  /**
   * getFromEmail
   * @return email's from-email
   * @throws Exception
   */
  public String getFromEmail () throws Exception {
    
    return this.fromEmail;
  }
  
  /**
   * addToEmails
   * @param toEmails
   * @return to-emails comma-separated (ready for sending)
   * @throws Exception
   */
  public String addToEmails (String... toEmails) throws Exception {
    
    if (toEmails == null || toEmails.length == 0) {
      
      return this.toEmails;
    }
    
    int index = 0;
    
    if (this.toEmails.length() == 0) {
      
      this.toEmails = toEmails[0];
      index = 1;
    }
    
    for (int i = index; i < toEmails.length; i ++) {
      
      this.toEmails += kEmailsSeparator;
      this.toEmails += toEmails[i];
    }
    
    return this.toEmails;
  }
  
  /**
   * getToEmails
   * @return to-emails comma-separated
   * @throws Exception
   */
  public String getToEmails () throws Exception {
    
    return this.toEmails;
  }
  
  /**
   * addCcEmails
   * @param ccEmails
   * @return cc-emails comma-separated
   * @throws Exception
   */
  public String addCcEmails (String... ccEmails) throws Exception {
    
    if (ccEmails == null || ccEmails.length == 0) {
      
      return this.ccEmails;
    }
    
    int index = 0;
    
    if (this.ccEmails .length() == 0) {
      
      this.ccEmails = ccEmails[0];
      index = 1;
    }
    
    for (int i = index; i < ccEmails.length; i ++) {
      
      this.ccEmails += kEmailsSeparator;
      this.ccEmails += ccEmails[i];
    }
    
    return this.ccEmails;
  }
  
  /**
   * getCcEmails
   * @return cc-emails comma-separated
   * @throws Exception
   */
  public String getCcEmails () throws Exception {
    
    return this.ccEmails;
  }
  
  /**
   * addBccEmails
   * @param bccEmails
   * @return bcc-emails comma-separated
   * @throws Exception
   */
  public String addBccEmails (String... bccEmails) throws Exception {
    
    if (bccEmails == null || bccEmails.length == 0) {
      
      return this.bccEmails;
    }
    
    int index = 0;
    
    if (this.bccEmails .length() == 0) {
      
      this.bccEmails = bccEmails[0];
      index = 1;
    }
    
    for (int i = index; i < bccEmails.length; i ++) {
      
      this.bccEmails += kEmailsSeparator;
      this.bccEmails += bccEmails[i];
    }
    
    return this.bccEmails;
  }
  
  /**
   * getBccEmails
   * @return bcc-emails comma-separated
   * @throws Exception
   */
  public String getBccEmails () throws Exception {
    
    return this.bccEmails;
  }
  
  /**
   * getSubject
   * @return email's subject
   * @throws Exception
   */
  public String getSubject () throws Exception {
    
    return this.subject;
  }
  
  /**
   * getBodyText
   * @return email's body-text
   * @throws Exception
   */
  public String getBodyText () throws Exception {
    
    return this.bodyText;
  }
  
  /**
   * getSslType
   * @return email's ssl-type
   * @throws Exception
   */
  public SslType getSslType () throws Exception {
    
    return this.sslType;
  }
  
  @Override
  public String toString () {
    
    return
      "Java Email:"
      + "\nfrom name ("
      + this.fromName
      + ")\nfrom email ("
      + this.fromEmail
      + ")\nto emails ("
      + this.toEmails
      + ")\ncc emails ("
      + this.ccEmails
      + ")\nbcc emails ("
      + this.bccEmails
      + ")\nsubject ("
      + this.subject
      + ")\nbody text ("
      + this.bodyText
      + ")";
  }
}
