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

package com.vangav.backend.networks.email.mail_gun_email;

import java.io.Serializable;
import java.util.Arrays;

import com.vangav.backend.content.formatting.SerializationInl;
import com.vangav.backend.networks.email.mail_gun_email.dispatch_message.MailGunEmailDispatchable;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * MailGunEmail represents an e-mail that can be sent using mailgun
 * */
public class MailGunEmail implements Serializable {

  /**
   * Generated serial version ID
   */
  private static final long serialVersionUID = 6274594800916049188L;
  private String fromName;
  private String fromEmail;
  private String subject;
  private String bodyText;
  private String[] toEmails;
  
  /**
   * Constructor MailGunEmail
   * @param fromName
   * @param fromEmail - just the email name part of it
   *          e.g.: tom@example.com would be just tom
   * @param subject
   * @param bodyText
   * @param toEmails
   * @return new MailGunEmail Object
   * @throws Exception
   */
  public MailGunEmail (
    String fromName,
    String fromEmail,
    String subject,
    String bodyText,
    String... toEmails) throws Exception {
    
    this.fromName = fromName;
    this.fromEmail = fromEmail;
    this.subject = subject;
    this.bodyText = bodyText;
    this.toEmails = toEmails;
  }
  
  /**
   * fromMailGunEmailDispatchable
   * usually used on the worker instance side where dispatch messages are
   *   received and gets executed
   * @param mailGunEmailDispatchable is a JSON Object with a serialized
   *          version of a MailGunEmail Object
   * @return the deserialized version of the MailGunEmail Object
   * @throws Exception
   */
  public static MailGunEmail fromMailGunEmailDispatchable (
    MailGunEmailDispatchable mailGunEmailDispatchable) throws Exception {
    
    return
      SerializationInl.<MailGunEmail>deserializeObject(
        mailGunEmailDispatchable.serialized_message);
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
   * getToEmails
   * @return email's to-emails
   * @throws Exception
   */
  public String[] getToEmails () throws Exception {
    
    return this.toEmails;
  }
  
  @Override
  public String toString () {
    
    return
      "Mail Gun Email:"
      + "\nfrom name ("
      + this.fromName
      + ")\nfrom email ("
      + this.fromEmail
      + ")\nsubject ("
      + this.subject
      + ")\nbody text ("
      + this.bodyText
      + ")\nto emails ("
      + Arrays.toString(this.toEmails)
      + ")";
  }
}
