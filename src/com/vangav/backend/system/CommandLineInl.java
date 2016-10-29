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

package com.vangav.backend.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * CommandLine static inline methods for command line related method
 * */
public class CommandLineInl {

  // disable default instantiation
  private CommandLineInl () {}
  
  /**
   * executeMultipleCommands
   * executes multiple commands in the command line
   * @param commands
   * @throws Exception
   */
  public static void executeMultipleCommands (
    String... commands) throws Exception {
    
    if (commands == null) {
      
      return;
    }
    
    for (String command : commands) {
      
      executeCommand(command);
    }
  }

  /**
   * executeCommand
   * execute a command in the command line
   * @param command
   * @return result
   * @throws Exception
   */
  public static String executeCommand (
    String command) throws Exception {
 
    Process p;
    
    p = Runtime.getRuntime().exec(command);
    p.waitFor();
      
    BufferedReader reader =
      new BufferedReader(new InputStreamReader(p.getInputStream() ) );
 
    String line = "";
    StringBuffer output = new StringBuffer();
    
    while ((line = reader.readLine() ) != null) {
      
      output.append(line + "\n");
    }
 
    return output.toString();
  }

  /**
   * executeCommand
   * execute a command in the command line
   * @param command
   * @return result
   * @throws Exception
   */
  public static String executeCommand (
    String[] command) throws Exception {
    
    StringBuffer output = new StringBuffer();
 
    Process p;
    
    p = Runtime.getRuntime().exec(command);
    p.waitFor();
      
    BufferedReader reader =
      new BufferedReader(new InputStreamReader(p.getInputStream() ) );
 
    String line = "";
    
    while ((line = reader.readLine() ) != null) {
      
      output.append(line + "\n");
    }
 
    return output.toString();
  }
}
