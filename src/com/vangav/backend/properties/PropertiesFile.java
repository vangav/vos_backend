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
