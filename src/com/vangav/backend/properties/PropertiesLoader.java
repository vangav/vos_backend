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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

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
 * It's also reload/get safe so a real-time-reload is safe with other threads
 *   getting properties' values
 * */
public class PropertiesLoader {

  private static PropertiesLoader instance = null;
  
  /**
   * propertiesFiles holds all properties files (with .prop extension)
   *   under ./conf/prop/
   */
  private Map<String, Properties> propertiesFiles;
  /**
   * loadLatch is used activated on loading/reloading properties to make new
   *   get requests wait till loading/reloading is finished
   */
  private CountDownLatch loadLatch;
  /**
   * getLatch is used to make loading/reloading requests wait till current
   *   get requests are finished
   */
  private AtomicInteger getLatch;
  
  /**
   * Constructor - PropertiesLoader
   * @return new PropertiesLoader Object
   * @throws Exception
   */
  protected PropertiesLoader () throws Exception {
    
    this.propertiesFiles = new HashMap<String, Properties>();
    this.loadLatch = new CountDownLatch(0);
    this.getLatch = new AtomicInteger(0);
    
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
   * @throws Exception
   */
  private synchronized void loadProperties() throws Exception {
    
    // lock for loading/reloading properties
    this.loadLatch = new CountDownLatch(1);
    
    // wait for in-processing get requests to finish
    while (this.getLatch.get() > 0) {
      
      try {
        
        this.getLatch.wait();
      } catch (InterruptedException e) {
      }
    }
    
    try {

      Map<String, Properties> tempMap = new HashMap<String, Properties>();
      
      File [] propFiles =
        FileLoaderInl.loadFiles(
          kPropPath,
          kPropExt);
  
      InputStream input;
      Properties prop;
      
      for (File propFile : propFiles) {
        
        input = new FileInputStream(propFile.getAbsolutePath() );
        
        prop = new Properties();
        prop.load(input);
        
        tempMap.put(
          propFile.getName().substring(
            0,
            propFile.getName().length() - kPropExt.length() ),
          prop);
      }
      
      this.propertiesFiles.clear();
      this.propertiesFiles.putAll(tempMap);
    } catch (Exception e) {
      
      throw e;
    } finally {
      
      // release loading/reloading lock
      this.loadLatch.countDown();
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
    
    // wait for loading/reloading to finish
    try {
      
      this.loadLatch.await();
    } catch (InterruptedException e) {
    }
    
    // increment get latch
    this.getLatch.incrementAndGet();
    
    try {
      
      Properties prop =
        propertiesFiles.get(fileName);
      
      if (prop == null) {
        
        return defaultValue;
      }
      
      return
        prop.getProperty(
          propName,
          defaultValue);
    } catch (Exception e) {
      
      throw e;
    } finally {
      
      // decrement get latch and notify it
      this.getLatch.decrementAndGet();
      this.getLatch.notify();
    }
  }
  
  /**
   * reload
   * used to reload properties file
   * @throws Exception
   */
  public void reload () throws Exception {
    
    this.loadProperties();
  }
}
