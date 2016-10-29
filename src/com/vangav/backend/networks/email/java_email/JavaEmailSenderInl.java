/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * MIT License
 *
 * Copyright (c) 2016 Vangav
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
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
import com.vangav.backend.networks.email.java_email.JavaEmail.SslType;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * JavaEmailSenderInl has a static inline method for sending e-mails using
 *   JavaMail
 *   http://www.oracle.com/technetwork/java/javamail/index.html
 * */
public class JavaEmailSenderInl {

  // disable default instantiation
  private JavaEmailSenderInl () {}

  /**
   * sendEmail
   * @param javaEmail
   * @throws Exception
   */
  public static void sendEmail (JavaEmail javaEmail) throws Exception {
    
    // check arguments
    ArgumentsInl.checkNotEmpty(
      "from-email",
      javaEmail.getFromEmail(),
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "to-emails",
      javaEmail.getToEmails(),
      ExceptionType.CODE_EXCEPTION);
    
    // set session properties

    Properties props = new Properties();

    if (javaEmail.getSslType() == SslType.WITHOUT_SSL) {

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
    } else if (javaEmail.getSslType() == SslType.WITH_SSL) {
      
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
        131,
        1,
        "Unknown SslType ["
          + javaEmail.getSslType().toString()
          + "] exepcting WITH_SSL or WITHOUT_SSL",
        ExceptionClass.TYPE);
    }
    
    // create session
    
    Session session = null;

    if (javaEmail.getSslType() == SslType.WITHOUT_SSL) {

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
    } else if (javaEmail.getSslType() == SslType.WITH_SSL) {
     
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
    
    message.setFrom(
      new InternetAddress(
        javaEmail.getFromEmail(),
        javaEmail.getFromName() ) );
    message.addRecipients(
      Message.RecipientType.TO,
      InternetAddress.parse(javaEmail.getToEmails() ) );
    if (javaEmail.getCcEmails() != null &&
        javaEmail.getCcEmails().length() > 0) {

      message.addRecipients(
        Message.RecipientType.CC,
        InternetAddress.parse(javaEmail.getCcEmails() ) );
    }
    if (javaEmail.getBccEmails() != null &&
        javaEmail.getBccEmails().length() > 0) {

      message.addRecipients(
        Message.RecipientType.BCC,
        InternetAddress.parse(javaEmail.getBccEmails() ) );
    }
    message.setSubject(javaEmail.getSubject() );
    message.setText(javaEmail.getBodyText() );

    // send email
    Transport.send(message);
  }
}
