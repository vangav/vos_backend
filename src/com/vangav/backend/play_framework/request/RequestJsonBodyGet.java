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

package com.vangav.backend.play_framework.request;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vangav.backend.data_structures_and_algorithms.tuple.Pair;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * RequestJsonBodyGet is the parent class for GET requests
 *   children classes define the request's structure
 * */
public abstract class RequestJsonBodyGet extends RequestJsonBody {

  // NOTE: only exists to defeat override by GET requests since this method
  //         is only used by POST requests
  @Override
  @JsonIgnore
  final protected RequestJsonBody fromJsonString (
    String json) throws Exception {
    
    throw new CodeException(
      151,
      9,
      "this is a POST request method called for what should be a GET request",
      ExceptionClass.UNAUTHORIZED);
  }
  
  // helper methods for extracting data from query map
  // add more methods as needed
  
  /**
   * getBoolean
   * @param key
   * @param query
   * @return boolean value corresponding to param key
   * @throws Exception
   */
  @JsonIgnore
  final protected boolean getBoolean (
    String key,
    Map<String, String[]> query) throws Exception {
    
    try {
      
      return Boolean.parseBoolean(query.get(key)[0] );
    } catch (Exception e) {
      
      return false;
    }
  }
  
  /**
   * getBooleanArray
   * @param key
   * @param query
   * @return boolean array of values corresponding to param key
   * @throws Exception
   */
  @JsonIgnore
  final protected boolean[] getBooleanArray (
    String key,
    Map<String, String[]> query) throws Exception {
    
    try {
    
      String[] strArr = this.splitStringParam(query.get(key)[0] );
      boolean[] booleanArr = new boolean[strArr.length];
      
      for (int i = 0; i < strArr.length; i ++) {
        
        try {
          
          booleanArr[i] = Boolean.parseBoolean(strArr[i] );
        } catch (Exception e) {
          
          booleanArr[i] = false;
        }
      }
      
      return booleanArr;
    } catch (Exception e) {
      
      return null;
    }
  }
  
  /**
   * getShort
   * @param name
   * @param query
   * @return short value corresponding to param key
   * @throws Exception
   */
  @JsonIgnore
  final protected short getShort (
    String key,
    Map<String, String[]> query) throws Exception {
    
    try {
      
      return Short.parseShort(query.get(key)[0] );
    } catch (Exception e) {
      
      return Short.MIN_VALUE;
    }
  }
  
  /**
   * getShortArray
   * @param key
   * @param query
   * @return short array of values corresponding to param key
   * @throws Exception
   */
  @JsonIgnore
  final protected short[] getShortArray (
    String key,
    Map<String, String[]> query) throws Exception {
    
    try {
    
      String[] strArr = this.splitStringParam(query.get(key)[0] );
      short[] shortArr = new short[strArr.length];
      
      for (int i = 0; i < strArr.length; i ++) {
        
        try {
          
          shortArr[i] = Short.parseShort(strArr[i] );
        } catch (Exception e) {

          shortArr[i] = Short.MIN_VALUE;
        }
      }
      
      return shortArr;
    } catch (Exception e) {
      
      return null;
    }
  }
  
  /**
   * getInt
   * @param name
   * @param query
   * @return int value corresponding to param key
   * @throws Exception
   */
  @JsonIgnore
  final protected int getInt (
    String key,
    Map<String, String[]> query) throws Exception {
    
    try {
      
      return Integer.parseInt(query.get(key)[0] );
    } catch (Exception e) {
      
      return Integer.MIN_VALUE;
    }
  }
  
  /**
   * getIntArray
   * @param key
   * @param query
   * @return int array of values corresponding to param key
   * @throws Exception
   */
  @JsonIgnore
  final protected int[] getIntArray (
    String key,
    Map<String, String[]> query) throws Exception {
    
    try {
    
      String[] strArr = this.splitStringParam(query.get(key)[0] );
      int[] intArr = new int[strArr.length];
      
      for (int i = 0; i < strArr.length; i ++) {
        
        try {
          
          intArr[i] = Integer.parseInt(strArr[i] );
        } catch (Exception e) {

          intArr[i] = Integer.MIN_VALUE;
        }
      }
      
      return intArr;
    } catch (Exception e) {
      
      return null;
    }
  }
  
  /**
   * getLong
   * @param name
   * @param query
   * @return long value corresponding to param key
   * @throws Exception
   */
  @JsonIgnore
  final protected long getLong (
    String key,
    Map<String, String[]> query) throws Exception {
    
    try {
      
      return Long.parseLong(query.get(key)[0] );
    } catch (Exception e) {
      
      return Long.MIN_VALUE;
    }
  }
  
  /**
   * getLongArray
   * @param key
   * @param query
   * @return long array of values corresponding to param key
   * @throws Exception
   */
  @JsonIgnore
  final protected long[] getLongArray (
    String key,
    Map<String, String[]> query) throws Exception {
    
    try {
    
      String[] strArr = this.splitStringParam(query.get(key)[0] );
      long[] longArr = new long[strArr.length];
      
      for (int i = 0; i < strArr.length; i ++) {
        
        try {
          
          longArr[i] = Long.parseLong(strArr[i] );
        } catch (Exception e) {

          longArr[i] = Long.MIN_VALUE;
        }
      }
      
      return longArr;
    } catch (Exception e) {
      
      return null;
    }
  }
  
  /**
   * getFloat
   * @param name
   * @param query
   * @return float value corresponding to param key
   * @throws Exception
   */
  @JsonIgnore
  final protected float getFloat (
    String key,
    Map<String, String[]> query) throws Exception {
    
    try {
      
      return Float.parseFloat(query.get(key)[0] );
    } catch (Exception e) {
      
      return Float.NaN;
    }
  }
  
  /**
   * getFloatArray
   * @param key
   * @param query
   * @return float array of values corresponding to param key
   * @throws Exception
   */
  @JsonIgnore
  final protected float[] getFloatArray (
    String key,
    Map<String, String[]> query) throws Exception {
    
    try {
    
      String[] strArr = this.splitStringParam(query.get(key)[0] );
      float[] floatArr = new float[strArr.length];
      
      for (int i = 0; i < strArr.length; i ++) {
        
        try {
          
          floatArr[i] = Float.parseFloat(strArr[i] );
        } catch (Exception e) {

          floatArr[i] = Float.NaN;
        }
      }
      
      return floatArr;
    } catch (Exception e) {
      
      return null;
    }
  }
  
  /**
   * getDouble
   * @param name
   * @param query
   * @return double value corresponding to param key
   * @throws Exception
   */
  @JsonIgnore
  final protected double getDouble (
    String key,
    Map<String, String[]> query) throws Exception {
    
    try {
      
      return Double.parseDouble(query.get(key)[0] );
    } catch (Exception e) {
      
      return Double.NaN;
    }
  }
  
  /**
   * getDoubleArray
   * @param key
   * @param query
   * @return double array of values corresponding to param key
   * @throws Exception
   */
  @JsonIgnore
  final protected double[] getDoubleArray (
    String key,
    Map<String, String[]> query) throws Exception {
    
    try {
    
      String[] strArr = this.splitStringParam(query.get(key)[0] );
      double[] doubleArr = new double[strArr.length];
      
      for (int i = 0; i < strArr.length; i ++) {
        
        try {
          
          doubleArr[i] = Double.parseDouble(strArr[i] );
        } catch (Exception e) {

          doubleArr[i] = Double.NaN;
        }
      }
      
      return doubleArr;
    } catch (Exception e) {
      
      return null;
    }
  }
  
  /**
   * getString
   * @param name
   * @param query
   * @return String value corresponding to param key
   * @throws Exception
   */
  @JsonIgnore
  final protected String getString (
    String key,
    Map<String, String[]> query) throws Exception {
    
    try {
      
      return query.get(key)[0];
    } catch (Exception e) {
      
      return null;
    }
  }
  
  /**
   * getStringArray
   * @param key
   * @param query
   * @return String array of values corresponding to param key
   * @throws Exception
   */
  @JsonIgnore
  final protected String[] getStringArray (
    String key,
    Map<String, String[]> query) throws Exception {
    
    try {
    
      return this.splitStringParam(query.get(key)[0] );
    } catch (Exception e) {
      
      return null;
    }
  }
  
  @JsonIgnore
  private static final String kArrayParamSeparator = ",";
  /**
   * splitStringParam
   * @param param
   * @return splited GET array-param
   * @throws Exception
   */
  @JsonIgnore
  private String[] splitStringParam (String param) throws Exception {
    
    if (param == null) {
      
      return null;
    } else {
      
      return param.split(kArrayParamSeparator);
    }
  }
  
  // primitive type --> <method name, array methodName>
  @JsonIgnore
  private static final Map<String, Pair<String, String> >
    kPrimitiveTypeToMethodName;
  static {
    
    kPrimitiveTypeToMethodName = new HashMap<String, Pair<String,String> >();

    kPrimitiveTypeToMethodName.put(
      "boolean",
      new Pair<String, String>("getBoolean", "getBooleanArray") );
    kPrimitiveTypeToMethodName.put(
      "short",
      new Pair<String, String>("getShort", "getShortArray") );
    kPrimitiveTypeToMethodName.put(
      "int",
      new Pair<String, String>("getInt", "getIntArray") );
    kPrimitiveTypeToMethodName.put(
      "long",
      new Pair<String, String>("getLong", "getLongArray") );
    kPrimitiveTypeToMethodName.put(
      "float",
      new Pair<String, String>("getFloat", "getFloatArray") );
    kPrimitiveTypeToMethodName.put(
      "double",
      new Pair<String, String>("getDouble", "getDoubleArray") );
    kPrimitiveTypeToMethodName.put(
      "String",
      new Pair<String, String>("getString", "getStringArray") );
  }
  /**
   * primitiveTypeToMethodName
   * @param primitiveType
   * @param isArray
   * @return return method name for parsing param primitiveTime
   * @throws Exception
   */
  @JsonIgnore
  public static final String primitiveTypeToMethodName (
    String primitiveType,
    boolean isArray) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "Primitive Type",
      primitiveType,
      ExceptionType.CODE_EXCEPTION);
    
    Pair<String, String> methodNames =
      kPrimitiveTypeToMethodName.get(primitiveType);
    
    if (methodNames == null) {
      
      throw new CodeException(
        151,
        10,
        "Unhandled primitive type ["
        + primitiveType
        + "]",
        ExceptionClass.TYPE);
    }
    
    if (isArray == false) {
      
      return methodNames.getFirst();
    } else {
      
      return methodNames.getSecond();
    }
  }
}
