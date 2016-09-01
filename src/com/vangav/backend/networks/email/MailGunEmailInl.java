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
 * MailGunEmailInl has inline static methods for send e-mails using mailgun
 *   in order to use mailgun, first register at mailgun.com
 * */
public class MailGunEmailInl {

  /**
   * sendSimpleEmail
   * @param fromName
   * @param fromEmail: just the email's name part
   *                   e.g.: for email "myemail@mail.com"
   *                         fromEmail would be "myemail"
   * @param subject
   * @param bodyText
   * @param toEmails: include at least one full email
   *                  e.g.: toemail@mail.com
   * @throws Exception
   */
  public static ClientResponse sendSimpleEmail (
    String fromName,
    String fromEmail,
    String subject,
    String bodyText,
    String... toEmails) throws Exception {
    
    ArgumentsInl.checkNotEmpty(
      "from-name",
      fromName,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "from-email",
      fromEmail,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "subject",
      subject,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "body-text",
      bodyText,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "to-emails",
      toEmails,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "to-email",
      toEmails[0],
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
      fromName
        + " <"
        + fromEmail
        + "@"
        + MailGunEmailProperties.i().getStringPropterty(
            MailGunEmailProperties.kMailGunDomainName)
        + ">");
    
    for (String toEmail : toEmails) {
    
      formData.add("to", toEmail);
    }

    formData.add("subject", subject);
    formData.add("text", bodyText);
    
    return
      webResource.type(
        MediaType.APPLICATION_FORM_URLENCODED).post(
          ClientResponse.class,
          formData);
  }
}
