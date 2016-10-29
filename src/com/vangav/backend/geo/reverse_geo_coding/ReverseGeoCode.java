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

package com.vangav.backend.geo.reverse_geo_coding;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ReverseGeoCode represents a reverse geo code for a specific
 *   latitude and longitude
 * */
public class ReverseGeoCode {

  private double latitude;
  private double longitude;
  
  private String city;
  private String majorCity;
  private String countryCode;
  private String country;
  private String continentCode;
  private String continent;
  
  /**
   * Constructor ReverseGeoCode
   * invoked from ReverseGeoCoding class
   * @param latitude
   * @param longitude
   * @param city
   * @param majorCity
   * @param countryCode
   * @param country
   * @param continentCode
   * @param continent
   * @return new ReverseGeoCode Object
   */
  public ReverseGeoCode (
    double latitude,
    double longitude,
    String city,
    String majorCity,
    String countryCode,
    String country,
    String continentCode,
    String continent) {
    
    this.latitude = latitude;
    this.longitude = longitude;
    
    this.city = city;
    this.majorCity = majorCity;
    this.countryCode = countryCode;
    this.country = country;
    this.continentCode = continentCode;
    this.continent = continent;
  }

  /**
   * getLatitude
   * @return the latitude
   */
  public double getLatitude () {
    
    return latitude;
  }

  /**
   * getLongitude
   * @return the longitude
   */
  public double getLongitude () {
    
    return longitude;
  }

  /**
   * getCity
   * @return the city
   */
  public String getCity () {
    
    return city;
  }

  /**
   * getMajorCity
   * @return the majorCity
   */
  public String getMajorCity () {
    
    return majorCity;
  }

  /**
   * getCountryCode
   * @return the countryCode
   */
  public String getCountryCode () {
    
    return countryCode;
  }

  /**
   * getCountry
   * @return the country
   */
  public String getCountry () {
    
    return country;
  }

  /**
   * getContinentCode
   * @return the continentCode
   */
  public String getContinentCode () {
    
    return continentCode;
  }

  /**
   * getContinent
   * @return the continent
   */
  public String getContinent () {
    
    return continent;
  }
  
  @Override
  public String toString () {
    
    return
      "city ("
      + this.city
      + ") major city("
      + this.majorCity
      + ") country code("
      + this.countryCode
      + ") country("
      + this.country
      + ") continent code("
      + this.continentCode
      + ") continent("
      + this.continent
      + ")";
  }
}
