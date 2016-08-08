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

package com.vangav.backend.networks;

import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * EmailInl: static inline method for sending e-mails
 * */
public class EmailInl {

  // disable default instantiation
  private EmailInl () {}

  /**
   * sendEmail
   * sends an e-mail
   * @param fromEmail
   * @param fromName
   * @param toEmails
   * @param ccEmails - can be null
   * @param bccEmails - can be null
   * @param subject
   * @param body
   * @return true if sending e-mail is a success and false otherwise
   * @throws Exception
   */
  public static boolean sendEmail (
    String fromEmail,
    String fromName,
    Map<String, String> toEmails,
    Map<String, String> ccEmails,
    Map<String, String> bccEmails,
    String subject,
    String body) throws Exception {
    
    try {
      
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
      
      return true;
    } catch (Exception e) {
      
      return false;
    }
  }
}
