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

package com.vangav.backend.networks.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * JavaEmailInl has a static inline method for sending e-mails using JavaMail
 *   http://www.oracle.com/technetwork/java/javamail/index.html
 * */
public class JavaEmailInl {

  // disable default instantiation
  private JavaEmailInl () {}
  
  /**
   * Enum SslType
   */
  public enum SslType {
    
    WITHOUT_SSL,
    WITH_SSL
  }
  
  /**
   * sendEmail
   * @param fromName
   * @param fromEmail
   * @param toEmails (comma separated e-mail addresses)
   * @param ccEmails can be null - (comma separated e-mail addresses)
   * @param bccEmails can be null - (comma separated e-mail addresses)
   * @param subject
   * @param bodyText
   * @param sslType
   * @throws Exception
   */
  public static void sendEmail (
    String fromName,
    String fromEmail,
    String toEmails,
    String ccEmails,
    String bccEmails,
    String subject,
    String bodyText,
    SslType sslType) throws Exception {
    
    // check arguments
    ArgumentsInl.checkNotEmpty(
      "from-email",
      fromEmail,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "from-name",
      fromName,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "to-emails",
      toEmails,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "subject",
      subject,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "body",
      bodyText,
      ExceptionType.CODE_EXCEPTION);
    
    // set session properties

    Properties props = new Properties();

    if (sslType == SslType.WITHOUT_SSL) {

      props.put(
        "mail.smtp.host",
        JavaEmailProperties.i().getStringPropterty(
          JavaEmailProperties.kSmtpHost));
      props.put(
        "mail.smtp.starttls.enable",
        "true");
      props.put(
        "mail.smtp.auth",
        "true");
      props.put(
        "mail.smtp.port",
        JavaEmailProperties.i().getStringPropterty(
          JavaEmailProperties.kSmtpNoSslPort) );
    } else if (sslType == SslType.WITH_SSL) {
      
      props.put(
        "mail.smtp.host",
        JavaEmailProperties.i().getStringPropterty(
          JavaEmailProperties.kSmtpHost) );
      props.put(
        "mail.smtp.socketFactory.port",
        JavaEmailProperties.i().getStringPropterty(
          JavaEmailProperties.kSmtpSslPort) );
      props.put(
        "mail.smtp.socketFactory.class",
        "javax.net.ssl.SSLSocketFactory");
      props.put(
        "mail.smtp.auth",
        "true");
      props.put(
        "mail.smtp.port",
        JavaEmailProperties.i().getStringPropterty(
          JavaEmailProperties.kSmtpSslPort) );
    } else {
      
      throw new CodeException(
        "Unknown SslType ["
          + sslType.toString()
          + "] exepcting WITH_SSL or WITHOUT_SSL",
        ExceptionClass.TYPE);
    }
    
    // create session
    
    Session session = null;

    if (sslType == SslType.WITHOUT_SSL) {

      session =
        Session.getInstance(
          props,
          new javax.mail.Authenticator() {
        
            protected PasswordAuthentication getPasswordAuthentication() {
          
              try {
                
                return
                  new PasswordAuthentication(
                    JavaEmailProperties.i().getStringPropterty(
                      JavaEmailProperties.kSmtpUserName),
                    JavaEmailProperties.i().getStringPropterty(
                      JavaEmailProperties.kSmtpPassword) );
              } catch (Exception e) {
                
                return null;
              }
            }
          }
        );
    } else if (sslType == SslType.WITH_SSL) {
     
      session =
        Session.getDefaultInstance(
          props,
          new javax.mail.Authenticator() {
            
            protected PasswordAuthentication getPasswordAuthentication() {
          
              try {
              
                return
                  new PasswordAuthentication(
                    JavaEmailProperties.i().getStringPropterty(
                      JavaEmailProperties.kSmtpUserName),
                    JavaEmailProperties.i().getStringPropterty(
                      JavaEmailProperties.kSmtpPassword) );
              } catch (Exception e) {
                
                return null;
              }
            }
          }
        );
    }
    
    // create message
    
    Message message = new MimeMessage(session);
    
    message.setFrom(new InternetAddress(fromEmail, fromName) );
    message.addRecipients(
      Message.RecipientType.TO,
      InternetAddress.parse(toEmails) );
    if (ccEmails != null && ccEmails.length() > 0) {

      message.addRecipients(
        Message.RecipientType.CC,
        InternetAddress.parse(ccEmails) );
    }
    if (bccEmails != null && bccEmails.length() > 0) {

      message.addRecipients(
        Message.RecipientType.BCC,
        InternetAddress.parse(bccEmails) );
    }
    message.setSubject(subject);
    message.setText(bodyText);

    // send email
    Transport.send(message);
  }
}
