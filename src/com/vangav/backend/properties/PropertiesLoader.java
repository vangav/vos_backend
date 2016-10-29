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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.files.FileLoaderInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * PropertiesLoader Loads all properties files and stores
 *   key:value pairs in memory
 * */
public class PropertiesLoader {

  private static PropertiesLoader instance = null;
  
  /**
   * propertiesFiles holds all properties files (with .prop extension)
   *   under ./conf/prop/
   */
  private Map<String, Properties> propertiesFiles;
  
  protected PropertiesLoader () {
    
    this.propertiesFiles = new HashMap<String, Properties>();
    
    this.loadProperties();
  }

  /**
   * load
   * OPTIONAL method to load properties files ahead of using them to save
   *   time on the first time use
   * @throws Exception
   */
  public static void load () throws Exception {
    
    if (instance == null) {
      
      instance = new PropertiesLoader();
    }
  }
  
  /**
   * i
   * singleton method
   * @return static instance of PropertiesLoader
   * @throws Exception
   */
  public static PropertiesLoader i () throws Exception {
    
    if (instance == null) {
      
      instance = new PropertiesLoader();
    }
    
    return instance;
  }
  
  private static final String kPropPath = "conf/prop";
  private static final String kPropExt = ".prop";

  /**
   * loadProperties
   * loads all properties files (with extension .prop) from ./conf/prop
   */
  private void loadProperties() {
    
    try {
      
      propertiesFiles.clear();
      
      File [] propFiles =
        FileLoaderInl.loadFiles(
          kPropPath,
          kPropExt);
  
      InputStream input;
      Properties prop;
      
      for (File propFile : propFiles) {
      
        try {
          
          input = new FileInputStream(propFile.getAbsolutePath() );
          
          prop = new Properties();
          prop.load(input);
          
          propertiesFiles.put(
            propFile.getName().substring(
              0,
              propFile.getName().length() - kPropExt.length() ),
            prop);
        } catch (Exception e) {
          
        }
      }
    } catch (Exception e) {
      
    }
  }
  
  /**
   * getProperty
   * @param fileName: properties file's name
   * @param propName: property's name
   * @param defaultValue: default value to return if not found here
   * @return string value of the property
   * @throws Exception
   */
  public String getProperty (
    String fileName,
    String propName,
    String defaultValue) throws Exception {
    
    if (defaultValue == null) {
      
      throw new CodeException(
        152,
        1,
        "Invalid property, properties file ["
        + fileName
        + "] doesn't have property ["
        + propName +
        "]",
        ExceptionClass.MISSING_ITEM);
    }
    
    Properties prop =
      propertiesFiles.get(fileName);
    
    if (prop == null) {
      
      return defaultValue;
    }
    
    return
      prop.getProperty(
        propName,
        defaultValue);
  }
}
