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

package com.vangav.backend.data_structures_and_algorithms.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * StringsAndArraysInl: some algorithms related to strings and arrays
 *                         (inline static methods)
 * */
public class StringsAndArraysInl {

  // disable default instantiation
  private StringsAndArraysInl () {}

  /**
   * getLevenshteinDistance
   * @param a
   * @param b
   * @return the distance between Strings a and b
   * @throws Exception
   */
  public static int getLevenshteinDistance (
    String a,
    String b) throws Exception {
  
    // d is a table with m+1 rows and n+1 columns
    char[] s = (a).toCharArray();
    char[] t = (b).toCharArray();

    int m = s.length;
    int n = t.length;

    int[][] d = new int [m + 1][n + 1];

    int i;
    int j;

    for (i = 0; i < (m + 1); i ++) {

      d[i][0] = i; //deletion
    }

    for(j = 0; j < (n + 1); j ++) {

      d[0][j] = j; //insertion
    }

    for (j = 1; j < (n + 1); j ++) {

      for (i = 1; i < (m + 1); i ++) {

        if (s[i-1] == t[j - 1]) {

          d[i][j] = d[i - 1][j - 1];
        } else {
          
          d[i][j] =
            Math.min((d[i - 1][j] + 1), //deletion
            (Math.min((d[i][j - 1] + 1), //insertion
            (d[i - 1][j - 1] + 1) ) ) ); //substitution
        }
      }
    }

    return d[m][n];
  }

  private static final String kOperators = "+-*/";
  /**
   * evalReversePolishNotation
   * @param tokens
   *          (e.g.: tokens = new String[] { "2", "1", "+", "3", "*" }; )
   * @return resulting double
   * @throws Exception
   */
  public static double evalReversePolishNotation (
    String[] tokens) throws Exception {

    Stack<String> stack = new Stack<String>();

    for (String t : tokens) {
      
      if (kOperators.contains(t) == false) {
        
        stack.push(t);
      } else {
        
        double a = Double.valueOf(stack.pop() );
        double b = Double.valueOf(stack.pop() );
        
        int index = kOperators.indexOf(t);
        
        switch (index) {
          case 0:
            stack.push(String.valueOf(a + b) );
            break;
          case 1:
            stack.push(String.valueOf(b - a) );
            break;
          case 2:
            stack.push(String.valueOf(a * b) );
            break;
          case 3:
            stack.push(String.valueOf(b / a) );
            break;
        }
      }
    }

    return Double.valueOf(stack.pop() );
  }
  
  /**
   * areIsomorphic
   * e.g.: "egg" and "add" are isomorphic, "foo" and "bar" are not
   * @param s
   * @param t
   * @return true if s and t are isomorphic and false otherwise
   * @throws Exception
   */
  public static boolean areIsomorphic (String s, String t) throws Exception {
    
    if (s == null || t == null) {
     
      return false;
    }

    if (s.length() != t.length() ) {
     
      return false;
    }

    HashMap<Character, Character> map = new HashMap<Character, Character>();

    for (int i = 0; i < s.length(); i ++) {
      
      char c1 = s.charAt(i);
      char c2 = t.charAt(i);

      if (map.containsKey(c1) ) {
        
        // if not consistent with previous ones
        if (map.get(c1) != c2) {
          
          return false;
        }
      } else {
        
        // if c2 is already being mapped
        if (map.containsValue(c2) ) {
         
          return false;
        }
        map.put(c1, c2);
      }
    }

    return true;
  }

  /**
   * isValidPalindrome
   * @param s
   * @return true if s is a palindrome and false otherwise
   * @throws Exception
   */
  public static boolean isValidPalindrome (String s) throws Exception {
    
    if (s == null || s.length() == 0) {
     
      return false;
    }

    s = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

    for (int i = 0; i < s.length(); i ++) {
      
      if (s.charAt(i) != s.charAt(s.length() - 1 - i) ) {
        
        return false;
      }
    }

    return true;
  }
  
  /**
   * addBinary
   * e.g.: 11 + 1 = 100
   * @param a
   * @param b
   * @return binary addition of a and b
   * @throws Exception
   */
  public static String addBinary (String a, String b) throws Exception {
    
    if (a == null || a.length() == 0) {
     
      return b;
    }
    
    if (b == null || b.length() == 0) {
     
      return a;
    }

    int pa = a.length() - 1;
    int pb = b.length() - 1;

    int flag = 0;
    StringBuilder sb = new StringBuilder();
    
    while (pa >= 0 || pb >= 0) {
    
      int va = 0;
      int vb = 0;

      if (pa >= 0) {
        
        va = a.charAt(pa) == '0' ? 0 : 1;
        pa--;
      }
      
      if (pb >= 0) {
      
        vb = b.charAt(pb) == '0' ? 0 : 1;
        pb--;
      }

      int sum = va + vb + flag;
      
      if (sum >= 2) {
        
        sb.append(String.valueOf(sum - 2) );
        flag = 1;
      } else {
        
        flag = 0;
        sb.append(String.valueOf(sum) );
      }
    }

    if (flag == 1) {
      
      sb.append("1");
    }

    return sb.reverse().toString();
  }
  
  /**
   * reverseWords
   * @param s
   * @return e.g.: "the sky is blue", return "blue is sky the"
   * @throws Exception
   */
  public static String reverseWords (String s) throws Exception {
    
    if (s == null || s.length() == 0) {
    
      return "";
    }

    // split to words by space
    String[] arr = s.split(" ");
    StringBuilder sb = new StringBuilder();
    
    for (int i = arr.length - 1; i >= 0; i --) {
      
      if (arr[i].equals("") == false) {
        
        sb.append(arr[i]).append(" ");
      }
    }
    
    return sb.length() == 0 ? "" : sb.substring(0, sb.length() - 1);
  }
  
  /**
   * bullsAndCows
   * @param secret
   * @param guess
   * @return hint based on secret and guess
   * @throws Exception
   */
  public static String bullsAndCows (
    String secret,
    String guess) throws Exception {
    
    int countBull = 0;
    int countCow = 0;
    
    int[] arr1 = new int[10];
    int[] arr2 = new int[10];

    for (int i = 0; i < secret.length(); i++) {
      
      char c1 = secret.charAt(i);
      char c2 = guess.charAt(i);

      if (c1 == c2) {
       
        countBull++;
      } else {
        
        arr1[c1 - '0']++;
        arr2[c2 - '0']++;
      }
    }

    for (int i = 0; i < 10; i ++) {
      
      countCow += Math.min(arr1[i], arr2[i]);
    }

    return countBull + "A" + countCow + "B";
  }
  
  /**
   * longestCommonPrefix
   * @param strs
   * @return the longest common prefix for the strings in strs
   * @throws Exception
   */
  public static String longestCommonPrefix (String[] strs) throws Exception {
    
    if (strs == null || strs.length == 0) {
    
      return "";
    }

    if (strs.length == 1) {
     
      return strs[0];
    }

    int minLen = strs.length + 1;

    for (String str : strs) {
      
      if (minLen > str.length() ) {
        
        minLen = str.length();
      }
    }

    for (int i = 0; i < minLen; i ++) {
      
      for (int j = 0; j < strs.length - 1; j ++) {
        
        String s1 = strs[j];
        String s2 = strs[j + 1];
        
        if (s1.charAt(i) != s2.charAt(i) ) {
          
          return s1.substring(0, i);
        }
      }
    }

    return strs[0].substring(0, minLen);
  }
  
  /**
   * largestNumber
   * @param nums
   * @return e,g,: [3, 30, 34, 5, 9] --> 9534330
   * @throws Exception
   */
  public static String largestNumber (int[] nums) throws Exception {
    
    String[] strs = new String[nums.length];
    
    for (int i = 0; i < nums.length; i ++) {
    
      strs[i] = String.valueOf(nums[i] );
    }

    Arrays.sort(strs, new Comparator<String>() {
      
      public int compare (String s1, String s2) {
        
        String leftRight = s1 + s2;
        String rightLeft = s2 + s1;
        
        return -leftRight.compareTo(rightLeft);
      }
    });

    StringBuilder sb = new StringBuilder();
    
    for (String s : strs) {
      
      sb.append(s);
    }

    while (sb.charAt(0) == '0' && sb.length() > 1) {
      
      sb.deleteCharAt(0);
    }

    return sb.toString();
  }
  
  /**
   * simplifyPath
   * e.g.:
   * "/home/", => "/home"
   * "/a/./b/../../c/", => "/c"
   * "/../", => "/"
   * "/home//foo/", => "/home/foo"
   * @param path
   * @return simplified path
   * @throws Exception
   */
  public static String simplifyPath (String path) throws Exception {
    
    Stack<String> stack = new Stack<String>();

    while (path.length() > 0 && path.charAt(path.length() - 1) == '/') {
      
      path = path.substring(0, path.length() - 1);
    }

    int start = 0;
    
    for (int i = 1; i < path.length(); i ++) {
      
      if (path.charAt(i) == '/') {
      
        stack.push(path.substring(start, i) );
        start = i;
      } else if (i == path.length() - 1) {
        
        stack.push(path.substring(start) );
      }
    }

    LinkedList<String> result = new LinkedList<String>();
    
    int back = 0;
    
    while (!stack.isEmpty() ) {
      
      String top = stack.pop();

      if (top.equals("/.") || top.equals("/") ) {
        
        // nothing
      } else if (top.equals("/..") ) {
        
        back++;
      } else {
        
        if (back > 0) {
        
          back--;
        } else {
          
          result.push(top);
        }
      }
    }

    // if empty, return "/"
    if (result.isEmpty() ) {
      
      return "/";
    }

    StringBuilder sb = new StringBuilder();
    
    while (!result.isEmpty() ) {
      
      String s = result.pop();
      sb.append(s);
    }

    return sb.toString();
  }
  
  /**
   * compareVersion
   * e.g.: 0.1 < 1.1 < 1.2 < 13.37
   * @param version1
   * @param version2
   * @return
   *   If version1 > version2 return 1,
   *   if version1 < version2 return -1,
   *   otherwise return 0
   * @throws Exception
   */
  public static int compareVersion (
    String version1,
    String version2) throws Exception {
    
    String[] arr1 = version1.split("\\.");
    String[] arr2 = version2.split("\\.");

    int i = 0;
    
    while (i < arr1.length || i < arr2.length) {
      
      if (i < arr1.length && i < arr2.length) {
      
        if (Integer.parseInt(arr1[i] ) < Integer.parseInt(arr2[i] ) ) {
          
          return -1;
        } else if (Integer.parseInt(arr1[i] ) > Integer.parseInt(arr2[i] ) ) {
          
          return 1;
        }
      } else if (i < arr1.length) {
        
        if (Integer.parseInt(arr1[i]) != 0) {
        
          return 1;
        }
      } else if (i < arr2.length) {
        
        if (Integer.parseInt(arr2[i] ) != 0) {
          
          return -1;
        }
      }

      i++;
    }

    return 0;
  }
  
  /**
   * generatePascalTriangle
   * @param numRows
   * @return
   *   e.g.: for numRows = 5, result is
   *   [
   *        [1],
   *       [1,1],
   *      [1,2,1],
   *     [1,3,3,1],
   *    [1,4,6,4,1]
   *   ]
   * @throws Exception
   */
  public static ArrayList<ArrayList<Integer> > generatePascalTriangle (
    int numRows) throws Exception {
    
    ArrayList<ArrayList<Integer> > result =
      new ArrayList<ArrayList<Integer> >();
    
    if (numRows <= 0) {
     
      return result;
    }

    ArrayList<Integer> pre = new ArrayList<Integer>();
    pre.add(1);
    result.add(pre);

    for (int i = 2; i <= numRows; i ++) {
      
      ArrayList<Integer> cur = new ArrayList<Integer>();

      // first
      cur.add(1);
      
      for (int j = 0; j < pre.size() - 1; j ++) {
        
        // middle
        cur.add(pre.get(j) + pre.get(j + 1) );
      }
      // last
      cur.add(1);

      result.add(cur);
      pre = cur;
    }

    return result;
  }
  
  /**
   * getPascalTriangleRow
   * @param rowIndex
   * @return e.g.: rowIndex = 3 --> [1, 3, 3, 1]
   * @throws Exception
   */
  public static List<Integer> getPascalTriangleRow(
    int rowIndex) throws Exception {
    
    ArrayList<Integer> result = new ArrayList<Integer>();

    if (rowIndex < 0) {
     
      return result;
    }

    result.add(1);
    
    for (int i = 1; i <= rowIndex; i ++) {
      
      for (int j = result.size() - 2; j >= 0; j --) {
        
        result.set(j + 1, result.get(j) + result.get(j + 1) );
      }
      result.add(1);
    }
    
    return result;
  }
  
  /**
   * groupAnagrams
   * @param strs
   * @return anagrams from strs grouped together
   * @throws Exception
   */
  public static List<List<String>> groupAnagrams (
    String[] strs) throws Exception {
    
    List<List<String> > result = new ArrayList<List<String> >();

    HashMap<String, ArrayList<String> > map =
      new HashMap<String, ArrayList<String> >();
    
    for (String str : strs) {
      
      char[] arr = str.toCharArray();
      Arrays.sort(arr);
      String ns = new String(arr);

      if (map.containsKey(ns) ) {
        
        map.get(ns).add(str);
      } else {
        
        ArrayList<String> al = new ArrayList<String>();
        al.add(str);
        map.put(ns, al);
      }
    }

    for (Map.Entry<String, ArrayList<String>> entry : map.entrySet() ) {
      
      Collections.sort(entry.getValue() );
    }

    result.addAll(map.values() );

    return result;
  }
}
