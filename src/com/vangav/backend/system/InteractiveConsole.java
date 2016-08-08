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

package com.vangav.backend.system;

import java.util.Scanner;

import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * InteractiveConsole provides methods for interactive console operations
 *   can either instantiate a new instance or use the singleton method
 * */
public class InteractiveConsole {

  private Scanner scanner;
  
  /**
   * Constructor InteractiveConsole
   * @return new InteractiveConsole Object
   * @throws Exception
   */
  public InteractiveConsole () throws Exception {
    
    this.scanner = new Scanner(System.in);
  }
  
  private static InteractiveConsole instance = null;
  /**
   * Singleton method i
   * @return static singleton instance of InteractiveConsole
   * @throws Exception
   */
  public static InteractiveConsole i () throws Exception {
    
    if (instance == null) {
      
      instance = new InteractiveConsole();
    }
    
    return instance;
  }
  
  /**
   * confirm
   * appends [Y/N] (yes/no) options to the message and asks the user
   *   to input either Y(yes) or N(no) to confirm the message or not
   * @param message
   * @return true if param message is confirmed and false if not
   * @throws Exception
   */
  public boolean confirm (String message) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "message",
      message,
      ExceptionType.CODE_EXCEPTION);
    
    message += ": [Y/N]\n> ";
    
    String userInput;
    
    while (true) {
      
      System.out.print(message);
      
      try {
        
        userInput = this.scanner.next();
        
        if (userInput.charAt(0) == 'Y' ||
            userInput.charAt(0) == 'y') {
          
          return true;
        } else if (userInput.charAt(0) == 'N' ||
                   userInput.charAt(0) == 'n') {
          
          return false;
        }
        
      } catch (Exception e) {
        
      }
    }
  }
}
