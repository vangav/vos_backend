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

package com.vangav.backend.ids;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * UuidOperationsInl is for inline static methods uuid-related
 * */
public class UuidOperationsInl {

  // disable default instantiation
  private UuidOperationsInl () {}
  
  /**
   * strListToUuidArr
   * @param list of UUID-Strings
   * @return corresponding UUID array
   * @throws Exception
   */
  public static UUID[] strListToUuidArr (
    ArrayList<String> list) throws Exception {
    
    if (list == null) {
      
      return new UUID[0];
    }
    
    UUID[] result = new UUID[list.size()];
    
    for (int i = 0; i < list.size(); i ++) {
      
      result[i] = UUID.fromString(list.get(i) );
    }
    
    return result;
  }
  
  /**
   * uuidListToStrArr
   * @param list of UUIDs
   * @return corresponding UUID-Strings array
   * @throws Exception
   */
  public static String[] uuidListToStrArr (
    ArrayList<UUID> list) throws Exception {
    
    if (list == null) {
      
      return new String[0];
    }
    
    String[] result = new String[list.size()];
    
    for (int i = 0; i < list.size(); i ++) {
      
      result[i] = list.get(i).toString();
    }
    
    return result;
  }
}
