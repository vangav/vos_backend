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

package com.vangav.backend.properties;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * PropertiesFile is the parent class for all properties files
 * */
public abstract class PropertiesFile {

  /**
   * getName
   * @return a string representing the properties file's name
   *           NOTE: withOUT path and withOUT extension
   *           e.g.: the name of /conf/prop/sample.prop would be "sample"
   * @throws Exception
   */
  public abstract String getName();
  
  /**
   * getProperty
   * @param name: proerty's name
   * @return a string representing the property's value
   * @throws Exception
   */
  protected abstract String getProperty(String name) throws Exception;
  
  /**
   * NOTE: implement as needed
   * each of the following function parses a property's value in a specific
   *   format
   * */
  
  /**
   * getBooleanProperty
   * @param name: property's name
   * @return boolean value
   * @throws Exception
   */
  public boolean getBooleanProperty (String name) throws Exception {
    
    return Boolean.parseBoolean(this.getProperty(name) );
  }
  
  /**
   * getShorttProperty
   * @param name: property's name
   * @return short value
   * @throws Exception
   */
  public short getShortProperty (String name) throws Exception {
    
    return Short.parseShort(this.getProperty(name) );
  }
  
  /**
   * getIntProperty
   * @param name: property's name
   * @return int value
   * @throws Exception
   */
  public int getIntProperty (String name) throws Exception {
    
    return Integer.parseInt(this.getProperty(name) );
  }
  
  /**
   * getLongProperty
   * @param name: property's name
   * @return Long value
   * @throws Exception
   */
  public long getLongProperty (String name) throws Exception {
    
    return Long.parseLong(this.getProperty(name) );
  }
  
  /**
   * getFloatProperty
   * @param name: property's name
   * @return float value
   * @throws Exception
   */
  public float getFloatProperty (String name) throws Exception {
    
    return Float.parseFloat(this.getProperty(name) );
  }
  
  /**
   * getDoubleProperty
   * @param name: property's name
   * @return double value
   * @throws Exception
   */
  public double getDoubleProperty (String name) throws Exception {
    
    return Double.parseDouble(this.getProperty(name) );
  }
  
  /**
   * getStringPropterty
   * @param name: property's name
   * @return string value
   * @throws Exception
   */
  public String getStringPropterty (String name) throws Exception {
    
    return this.getProperty(name);
  }
}
