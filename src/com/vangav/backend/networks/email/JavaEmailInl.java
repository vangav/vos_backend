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

import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
   * sendEmail
   * sends an e-mail
   * Maps for email receivers has pairs of (email, name)
   * @param fromEmail
   * @param fromName
   * @param toEmails
   * @param ccEmails - can be null
   * @param bccEmails - can be null
   * @param subject
   * @param body
   * @throws Exception
   */
  public static void sendEmail (
    String fromEmail,
    String fromName,
    Map<String, String> toEmails,
    Map<String, String> ccEmails,
    Map<String, String> bccEmails,
    String subject,
    String body) throws Exception {
    
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
      body,
      ExceptionType.CODE_EXCEPTION);
      
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);

    Message msg = new MimeMessage(session);

    msg.setFrom(
      new InternetAddress(
        fromEmail,
        fromName) );

    for (Map.Entry<String, String> entry : toEmails.entrySet() ) {
  
      msg.addRecipient(
        Message.RecipientType.TO,
        new InternetAddress(
          entry.getKey(),
          entry.getValue() ) );
    }

    if (ccEmails != null) {
      
      for (Map.Entry<String, String> entry : ccEmails.entrySet() ) {
    
        msg.addRecipient(
          Message.RecipientType.CC,
          new InternetAddress(
            entry.getKey(),
            entry.getValue() ) );
      }
    }

    if (bccEmails != null) {
      
      for (Map.Entry<String, String> entry : bccEmails.entrySet() ) {
    
        msg.addRecipient(
          Message.RecipientType.BCC,
          new InternetAddress(
            entry.getKey(),
            entry.getValue() ) );
      }
    }

    msg.setSubject(subject);

    msg.setText(body);

    Transport.send(msg);
  }
}
