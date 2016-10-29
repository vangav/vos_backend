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

package com.vangav.backend.content.formatting;

import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * PhrictionFormatterInl has static inline methods for generating text in
 *   phabricator phriction's (wiki) format
 * Reference -->
 *   https://secure.phabricator.com/book/phabricator/article/remarkup/
 * */
public class PhrictionFormatterInl {

  /**
   * bold
   * @param text
   * @return bold version of param text
   * @throws Exception
   */
  public static String bold (String text) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "text",
      text,
      ExceptionType.CODE_EXCEPTION);
    
    return "**" + text + "**";
  }

  /**
   * italic
   * @param text
   * @return italic version of param text
   * @throws Exception
   */
  public static String italic (String text) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "text",
      text,
      ExceptionType.CODE_EXCEPTION);
    
    return "//" + text + "//";
  }

  /**
   * monospaced
   * @param text
   * @return monospaced version of param text
   * @throws Exception
   */
  public static String monospaced (String text) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "text",
      text,
      ExceptionType.CODE_EXCEPTION);
    
    return "`" + text + "`";
  }

  /**
   * deleted
   * @param text
   * @return deleted version of param text
   * @throws Exception
   */
  public static String deleted (String text) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "text",
      text,
      ExceptionType.CODE_EXCEPTION);
    
    return "~~" + text + "~~";
  }

  /**
   * underlined
   * @param text
   * @return underlined version of param text
   * @throws Exception
   */
  public static String underlined (String text) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "text",
      text,
      ExceptionType.CODE_EXCEPTION);
    
    return "__" + text + "__";
  }

  /**
   * highlighted
   * @param text
   * @return highlighted of param text
   * @throws Exception
   */
  public static String highlighted (String text) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "text",
      text,
      ExceptionType.CODE_EXCEPTION);
    
    return "!!" + text + "!!";
  }

  /**
   * quoted
   * @param text
   * @return quoted version of param text
   * @throws Exception
   */
  public static String quoted (String text) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "text",
      text,
      ExceptionType.CODE_EXCEPTION);
    
    return "> " + text;
  }
  
  /**
   * link
   * @param link
   * @param name
   * @return link so that in phriction param name will show and be clickable
   *           with a link that points to param link
   * @throws Exception
   */
  public static String link (String link, String name) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "link",
      link,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotNull(
      "name",
      name,
      ExceptionType.CODE_EXCEPTION);
    
    return "[[" + link + " | " + name + "]]";
  }
  
  /**
   * largeHeader
   * @param header
   * @return large header version of param header
   * @throws Exception
   */
  public static String largeHeader (String header) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "header",
      header,
      ExceptionType.CODE_EXCEPTION);
    
    return "= " + header + " =";
  }
  
  /**
   * smallerHeader
   * @param header
   * @return smaller header version of param header
   * @throws Exception
   */
  public static String smallerHeader (String header) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "header",
      header,
      ExceptionType.CODE_EXCEPTION);
    
    return "== " + header + " ==";
  }
  
  /**
   * verySmallHeader
   * @param header
   * @return very small header version of param header
   * @throws Exception
   */
  public static String verySmallHeader (String header) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "header",
      header,
      ExceptionType.CODE_EXCEPTION);
    
    return "===== " + header + " =====";
  }
  
  /**
   * bulletList
   * @param item
   * @return bullet item version of param bullet
   * @throws Exception
   */
  public static String bulletList (String item) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "item",
      item,
      ExceptionType.CODE_EXCEPTION);
    
    return "- " + item;
  }
  
  /**
   * numberedList
   * @param item
   * @return numbered item version of param item
   * @throws Exception
   */
  public static String numberedList (String item) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "item",
      item,
      ExceptionType.CODE_EXCEPTION);
    
    return "# " + item;
  }

  /**
   * note
   * @param text
   * @return note version of param text
   * @throws Exception
   */
  public static String note (String text) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "text",
      text,
      ExceptionType.CODE_EXCEPTION);
    
    return "(NOTE) " + text;
  }

  /**
   * warning
   * @param text
   * @return warning version of param text
   * @throws Exception
   */
  public static String warning (String text) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "text",
      text,
      ExceptionType.CODE_EXCEPTION);
    
    return "(WARNING) " + text;
  }

  /**
   * important
   * @param text
   * @return important version of param text
   * @throws Exception
   */
  public static String important (String text) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "text",
      text,
      ExceptionType.CODE_EXCEPTION);
    
    return "(IMPORTANT) " + text;
  }
  
  /**
   * tableHeader
   * @param headers
   * @return table header row for param headers
   * @throws Exception
   */
  public static String tableHeader (String... headers) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "headers",
      headers,
      ExceptionType.CODE_EXCEPTION);
    
    StringBuffer stringBuffer = new StringBuffer ("| ");
    
    for (int i = 0; i < headers.length; i ++) {
      
      stringBuffer.append(bold(headers[i] ) );
      
      if (i < (headers.length - 1) ) {
        
        stringBuffer.append(" | ");
      }
    }
    
    return stringBuffer.toString();
  }
  
  /**
   * tableRow
   * @param cells
   * @return table row for param cells
   * @throws Exception
   */
  public static String tableRow (String... cells) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "cells",
      cells,
      ExceptionType.CODE_EXCEPTION);
    
    StringBuffer stringBuffer = new StringBuffer ("| ");
    
    for (int i = 0; i < cells.length; i ++) {
      
      stringBuffer.append(cells[i] );
      
      if (i < (cells.length - 1) ) {
        
        stringBuffer.append(" | ");
      }
    }
    
    return stringBuffer.toString();
  }
}
