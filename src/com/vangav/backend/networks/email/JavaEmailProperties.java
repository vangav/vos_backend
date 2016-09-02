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

import java.util.HashMap;
import java.util.Map;

import com.vangav.backend.properties.PropertiesFile;
import com.vangav.backend.properties.PropertiesLoader;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * JavaEmailProperties is the properties file for java email attributes
 *   in order to use java email, you must have a valid
 *   java_email_properties.prop properties file
 * */
public class JavaEmailProperties extends PropertiesFile {

  private static JavaEmailProperties instance = null;
  
  // disable default instantiation
  private JavaEmailProperties () {}
  
  public static JavaEmailProperties i () throws Exception {
    
    if (instance == null) {
      
      instance = new JavaEmailProperties();
    }
    
    return instance;
  }
  
  private static final String kName = "java_email_properties.prop";
  
  @Override
  public String getName () {
    
    return kName;
  }
  
  // properties names
  public static final String kSmtpUserName = "mail_gun_api_key";
  public static final String kSmtpPassword = "mail_gun_domain_name";
  public static final String kSmtpHost = "mail_gun_domain_name";
  public static final String kSmtpNoSslPort = "mail_gun_domain_name";
  public static final String kSmtpSslPort = "mail_gun_domain_name";
  
  // property name -> property default value
  private static final Map<String, String> kProperties;
  static {
    
    kProperties = new HashMap<String, String>();
    
    kProperties.put(
      kSmtpUserName,
      "");
    kProperties.put(
      kSmtpPassword,
      "");
    kProperties.put(
      kSmtpHost,
      "");
    kProperties.put(
      kSmtpNoSslPort,
      "");
    kProperties.put(
      kSmtpSslPort,
      "");
  }
  
  @Override
  protected String getProperty (String name) throws Exception {
    
    return PropertiesLoader.i().getProperty(
      this.getName(),
      name,
      kProperties.get(name) );
  }
}
