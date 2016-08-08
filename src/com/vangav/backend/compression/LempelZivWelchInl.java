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

package com.vangav.backend.compression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
public class LempelZivWelchInl {
  
  // disable default instantiation
  private LempelZivWelchInl () {}

  /**
   * compress
   * @param toCompress
   * @return compressed version of param toCompress using LZW algorithm
   * @throws Exception
   */
  public static List<Integer> compress (String toCompress) throws Exception {
    
    // Build the dictionary.
    int dictSize = 256;
    Map<String, Integer> dictionary = new HashMap<String, Integer>();

    for (int i = 0; i < 256; i ++) {
     
      dictionary.put("" + (char) i, i);
    }

    String w = "";
    List<Integer> result = new ArrayList<Integer>();
    
    for (char c : toCompress.toCharArray() ) {
      
      String wc = w + c;
      
      if (dictionary.containsKey(wc) ) {
        
        w = wc;
      } else {
        
        result.add(dictionary.get(w) );
        
        // Add wc to the dictionary.
        dictionary.put(wc, dictSize++);
        w = "" + c;
      }
    }

    // Output the code for w.
    if (!w.equals("") ) {
     
      result.add(dictionary.get(w) );
    }
    
    return result;
  }

  /**
   * decompress
   * @param toDecompress
   * @return decompressed string using LZW algorithm
   * @throws Exception
   */
  public static String decompress (
    List<Integer> toDecompress) throws Exception {
    
    // Build the dictionary.
    int dictSize = 256;
    Map<Integer, String> dictionary = new HashMap<Integer, String>();
    
    for (int i = 0; i < 256; i ++) {
     
      dictionary.put(i, "" + (char) i);
    }

    String w = "" + (char) (int) toDecompress.remove(0);
    StringBuffer result = new StringBuffer(w);
    
    for (int k : toDecompress) {
    
      String entry;
      
      if (dictionary.containsKey(k) ) {
        
        entry = dictionary.get(k);
      } else if (k == dictSize) {
        
        entry = w + w.charAt(0);
      } else {
       
        throw new CodeException(
          "invalid compression ["
          + toDecompress.toString()
          + "]",
          ExceptionClass.ARGUMENT);
      }
      
      result.append(entry);

      // Add w+entry[0] to the dictionary.
      dictionary.put(dictSize++, w + entry.charAt(0) );

      w = entry;
    }
    
    return result.toString();
  }
}
