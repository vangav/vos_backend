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

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * MailGunEmailSenderInl has inline static methods for send e-mails using
 *   mailgun in order to use mailgun, first register at mailgun.com
 * */
public class MailGunEmailSenderInl {

  /**
   * sendSimpleEmail
   * @param mailGunEmail
   * @return mail gun's client response
   * @throws Exception
   */
  public static ClientResponse sendSimpleEmail (
    MailGunEmail mailGunEmail) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "from-name",
      mailGunEmail.getFromName(),
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "from-email",
      mailGunEmail.getFromEmail(),
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "subject",
      mailGunEmail.getSubject(),
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "body-text",
      mailGunEmail.getBodyText(),
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "to-emails",
      mailGunEmail.getToEmails(),
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "to-email",
      mailGunEmail.getToEmails()[0],
      ExceptionType.CODE_EXCEPTION);
   
    Client client = Client.create();
    client.addFilter(new HTTPBasicAuthFilter(
      "api",
      MailGunEmailProperties.i().getStringPropterty(
        MailGunEmailProperties.kMailGunApiKey) ) );
    
    WebResource webResource =
      client.resource(
        "https://api.mailgun.net/v3/"
        + MailGunEmailProperties.i().getStringPropterty(
            MailGunEmailProperties.kMailGunDomainName)
        + "/messages");
    
    MultivaluedMapImpl formData = new MultivaluedMapImpl();
    formData.add(
      "from",
      mailGunEmail.getFromName()
        + " <"
        + mailGunEmail.getFromEmail()
        + "@"
        + MailGunEmailProperties.i().getStringPropterty(
            MailGunEmailProperties.kMailGunDomainName)
        + ">");
    
    for (String toEmail : mailGunEmail.getToEmails() ) {
    
      formData.add("to", toEmail);
    }

    formData.add("subject", mailGunEmail.getSubject() );
    formData.add("text", mailGunEmail.getBodyText() );
    
    return
      webResource.type(
        MediaType.APPLICATION_FORM_URLENCODED).post(
          ClientResponse.class,
          formData);
  }
}
