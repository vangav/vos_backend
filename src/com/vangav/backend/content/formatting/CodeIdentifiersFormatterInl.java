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

package com.vangav.backend.content.formatting;

import java.util.ArrayList;

import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * CodeIdentifiersFormatterInl has static inline methods for formating
 *   code identifiers (e.g.: CamelCase, lowerCamelCase, etc ...)
 * */
public class CodeIdentifiersFormatterInl {

  /**
   * lowerUnder
   * used for identifiers like package name
   * @param identifiers
   * @return e.g.: Hello World --> hello_world
   * @throws Exception
   */
  public static String lowerUnder (String... identifiers) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "identifiers",
      identifiers,
      ExceptionType.CODE_EXCEPTION);
    
    StringBuffer stringBuffer = new StringBuffer();
    
    // split a CamelCase String into multiple strings?
    if (identifiers.length == 1) {
      
      if (identifiers[0] != null && identifiers[0].length() > 4) {
      
        int lowerCaseCount = 0;
        int upperCaseCount = 0;
        
        // Check if it's a CamelCase String
        for (int i = 0; i < identifiers[0].length(); i ++) {
          
          if (Character.isUpperCase(identifiers[0].charAt(i) ) == true) {
            
            upperCaseCount += 1;
          } else if (Character.isLowerCase(identifiers[0].charAt(i) ) ==
                     true) {
            
            lowerCaseCount += 1;
          }
        }
        
        if (lowerCaseCount > 0 &&
            upperCaseCount > 1 &&
            lowerCaseCount >= upperCaseCount) {
          
          ArrayList<String> identifiersList = new ArrayList<String>();
          String currIdentifier = "";
          
          // Split CamelCase String into multiple Strings
          for (int i = 0; i < identifiers[0].length(); i ++) {
            
            currIdentifier = "" + identifiers[0].charAt(i);
            
            for (int j = i + 1; j < identifiers[0].length(); j ++) {
              
              if (j == (identifiers[0].length() - 1) ) {
                
                currIdentifier += identifiers[0].charAt(j);
                identifiersList.add(currIdentifier);
                
                currIdentifier = "";
                i = j;
                break;
              }
              
              if (Character.isUpperCase(identifiers[0].charAt(j) ) == false) {
                
                currIdentifier += identifiers[0].charAt(j);
              } else {
                
                identifiersList.add(currIdentifier);
                
                if (j < (identifiers[0].length() - 1) ) {
                  
                  i = j - 1;
                } else {
                  
                  i = j;
                }
                break;
              }
            }
          }
          
          if (currIdentifier.length() > 0) {
            
            identifiersList.add(currIdentifier);
          }
          
          identifiers = identifiersList.toArray(new String[0]);
        }
      }
    }
    
    // do the lower_under part
    for (int i = 0; i < identifiers.length; i ++) {
      
      identifiers[i] = identifiers[i].replaceAll("\\s+","");
      
      stringBuffer.append(identifiers[i].toLowerCase() );
      
      if (i < (identifiers.length - 1) ) {
        
        stringBuffer.append("_");
      }
    }
    
    return stringBuffer.toString();
  }

  /**
   * camelCase
   * used for identifiers like Class name
   * @param identifiers
   * @return e.g.: hello world --> HelloWorld
   * @throws Exception
   */
  public static String camelCase (
    boolean removeUnderscores,
    String... identifiers) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "identifiers",
      identifiers,
      ExceptionType.CODE_EXCEPTION);
    
    StringBuffer stringBuffer = new StringBuffer();
    
    if (identifiers.length == 1 && removeUnderscores == true) {
      
      identifiers = identifiers[0].split("_");
    }
    
    for (int i = 0; i < identifiers.length; i ++) {
      
      ArgumentsInl.checkNotNull(
        "identifier",
        identifiers[i],
        ExceptionType.CODE_EXCEPTION);
      
      if (identifiers[i].length() == 0) {
        
        continue;
      }
      
      identifiers[i] = identifiers[i].replaceAll("\\s+","");
      
      if (removeUnderscores == true) {
        
        identifiers[i] = identifiers[i].replaceAll("_","");
      }
      
      if (identifiers[i].length() == 1) {
        
        stringBuffer.append(identifiers[i].toUpperCase() );
      } else if (identifiers[i].toUpperCase().compareTo(identifiers[i] ) == 0) {

        stringBuffer.append(identifiers[i].substring(0, 1).toUpperCase() );
        stringBuffer.append(identifiers[i].substring(1).toLowerCase() );
      } else {

        stringBuffer.append(identifiers[i].substring(0, 1).toUpperCase() );
        stringBuffer.append(identifiers[i].substring(1) );
        
      }
    }
    
    return stringBuffer.toString();
  }

  /**
   * lowerCamelCase
   * used for identifiers like member variable name and method name
   * @param identifiers
   * @return e.g.: Hello world --> helloWorld
   * @throws Exception
   */
  public static String lowerCamelCase (
    boolean removeUnderscores,
    String... identifiers) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "identifiers",
      identifiers,
      ExceptionType.CODE_EXCEPTION);
    
    StringBuffer stringBuffer = new StringBuffer();
    
    if (identifiers.length == 0) {
      
      return stringBuffer.toString();
    }
    
    identifiers[0] = identifiers[0].replaceAll("\\s+","");
    
    if (removeUnderscores == true) {
      
      identifiers[0] = identifiers[0].replaceAll("_","");
    }
    
    stringBuffer.append(identifiers[0].toLowerCase() );
    
    for (int i = 1; i < identifiers.length; i ++) {
      
      stringBuffer.append(
        camelCase(
          removeUnderscores,
          identifiers[i]) );
    }
    
    return stringBuffer.toString();
  }

  /**
   * kCamelCase
   * used for identifiers like final constants
   * @param identifiers
   * @return e.g.: hello world --> kHelloWorld
   * @throws Exception
   */
  public static String kCamelCase (
    boolean removeUnderscores,
    String... identifiers) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "identifiers",
      identifiers,
      ExceptionType.CODE_EXCEPTION);
    
    StringBuffer stringBuffer = new StringBuffer();
    
    if (identifiers.length == 0) {
      
      return stringBuffer.toString();
    }
    
    stringBuffer.append("k");
    
    stringBuffer.append(camelCase(removeUnderscores, identifiers) );
    
    return stringBuffer.toString();
  }

  /**
   * upperUnder
   * used for identifiers like enums
   * @param identifiers
   * @return e.g.: hello world --> HELLO_WORLD
   * @throws Exception
   */
  public static String upperUnder (String... identifiers) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "identifiers",
      identifiers,
      ExceptionType.CODE_EXCEPTION);
    
    StringBuffer stringBuffer = new StringBuffer();
    
    for (int i = 0; i < identifiers.length; i ++) {
      
      identifiers[i] = identifiers[i].replaceAll("\\s+","");
      
      stringBuffer.append(identifiers[i].toUpperCase() );
      
      if (i < (identifiers.length - 1) ) {
        
        stringBuffer.append("_");
      }
    }
    
    return stringBuffer.toString();
  }

  /**
   * upper
   * used for identifiers like generic types
   * @param identifier
   * @return e.g.: hello --> HELLO
   * @throws Exception
   */
  public static String upper (String identifier) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "identifier",
      identifier,
      ExceptionType.CODE_EXCEPTION);
    
    identifier = identifier.replaceAll("\\s+","");

    return identifier.toUpperCase();
  }
}
