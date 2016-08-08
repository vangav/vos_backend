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

package com.vangav.backend.geo.reverse_geo_coding;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.vangav.backend.data_structures_and_algorithms.third_party.kd_tree.KDTree;
import com.vangav.backend.geo.reverse_geo_coding.third_party.CityAndCountryCode;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ReverseGeoCoding is the entry point for calculating a reverse geo code
 *   for a specific latitude and longitude
 * */
public class ReverseGeoCoding {
  
  private static ReverseGeoCoding instance = null;

  /**
   * load
   * loads reverse geo coding data into memory
   * @throws Exception
   */
  public static void load () throws Exception {
    
    if (instance == null) {
      
      instance = new ReverseGeoCoding();
    }
  }
  
  /**
   * i
   * singleton instance getter
   * @return static instance of ReverseGeoCoding
   * @throws Exception
   */
  public static ReverseGeoCoding i () throws Exception {
    
    if (instance == null) {
      
      instance = new ReverseGeoCoding();
    }
    
    return instance;
  }

  private static final String kCitiesPath =
    "conf/data/geo/reverse_geo_coding/cities1000.txt";
  private static final String kMajorCitiesPath =
    "conf/data/geo/reverse_geo_coding/major_cities.txt";
  
  private KDTree<CityAndCountryCode> citiesKdTree;
  private KDTree<MajorCity> majorCitiesKdTree;
  
  private ReverseGeoCoding () throws Exception {
    
    this.init();
  }
  
  private void init () throws Exception {

    // init cities and country codes
    
    FileInputStream fileInputStream = new FileInputStream(kCitiesPath);
    BufferedReader bufferedReader =
      new BufferedReader(new InputStreamReader(fileInputStream) );
    String line;
    
    ArrayList<CityAndCountryCode> cities = new ArrayList<CityAndCountryCode>();
    
    while ((line = bufferedReader.readLine() ) != null) {
      
      cities.add(new CityAndCountryCode(line) );
    }
    
    bufferedReader.close();
    
    this.citiesKdTree = new KDTree<CityAndCountryCode>(cities);
    
    // init major cities
    
    fileInputStream = new FileInputStream(kMajorCitiesPath);
    bufferedReader =
      new BufferedReader(new InputStreamReader(fileInputStream) );
    
    ArrayList<MajorCity> majorCities = new ArrayList<MajorCity>();
    
    while ((line = bufferedReader.readLine() ) != null) {
      
      majorCities.add(new MajorCity(line) );
    }
    
    bufferedReader.close();
    
    this.majorCitiesKdTree = new KDTree<MajorCity>(majorCities);
  }
  
  /**
   * getReverseGeoCode
   * @param latitude
   * @param longitude
   * @return ReverseGeoCode Object containing
   *           - latitude
   *           - longitude
   *           - city name
   *           - nearest major city name
   *           - country code
   *           - country name
   *           - continent code
   *           - continent name
   * @throws Exception
   */
  public ReverseGeoCode getReverseGeoCode (
    final double latitude,
    final double longitude) throws Exception {
    
    CityAndCountryCode cityAndCountryCode =
      this.citiesKdTree.findNearest(
        new CityAndCountryCode(latitude, longitude) );
    
    MajorCity majorCity =
      this.majorCitiesKdTree.findNearest(
        new MajorCity(latitude, longitude) );
    
    String countryName =
      CountriesAndContinents.i().getCountryName(
        cityAndCountryCode.getCountryCode() );
    
    String continentCode =
      CountriesAndContinents.i().getContinentCode(
        cityAndCountryCode.getCountryCode() );
    
    String continentName =
      CountriesAndContinents.i().getContinentName(continentCode);
    
    return
      new ReverseGeoCode(
        latitude,
        longitude,
        cityAndCountryCode.getCity(),
        majorCity.getMajorCity(),
        cityAndCountryCode.getCountryCode(),
        countryName,
        continentCode,
        continentName);
  }
}
