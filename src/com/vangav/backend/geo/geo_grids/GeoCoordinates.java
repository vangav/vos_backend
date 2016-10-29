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

package com.vangav.backend.geo.geo_grids;

import com.vangav.backend.geo.EarthConstantsInl;
import com.vangav.backend.math.NumbersInl;
import com.vangav.backend.metrics.distance.Distance;
import com.vangav.backend.metrics.distance.DistanceUnitType;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * GeoCoordinates represents geo coordinates (latitude, longitude, altitude)
 * */
public class GeoCoordinates {
  
  private double latitude;
  private double longitude;
  private double altitude;
  
  // disable default instantiation
  @SuppressWarnings("unused")
  private GeoCoordinates () {}

  private static final double kDefaultAltitude = 0.0;
  /**
   * Constructor GeoCoordinates
   * @param latitude
   * @param longitude
   * @return new Coordinates Object
   */
  public GeoCoordinates (
    double latitude,
    double longitude) {
    
    this(latitude, longitude, kDefaultAltitude);
  }
  
  /**
   * Constructor GeoCoordinates
   * @param latitude
   * @param longitude
   * @param altitude
   * @return new Coordinates Object
   */
  public GeoCoordinates (
    double latitude,
    double longitude,
    double altitude) {
    
    this.latitude = latitude;
    this.longitude = longitude;
    this.altitude = altitude;
  }
  
  /**
   * getLatitude
   * @return coordinates' latitude
   */
  public double getLatitude () {
    
    return this.latitude;
  }
  
  /**
   * getLongitude
   * @return coordinates' longitude
   */
  public double getLongitude () {
    
    return this.longitude;
  }
  
  /**
   * getAltitude
   * @return coordinates' altitude
   */
  public double getAltitude () {
    
    return this.altitude;
  }

  /**
   * getDistance
   * @param coordinates
   * @param distanceUnit
   * @return the distance between this Coordinates and param coordinates in
   *           param distanceUnit
   * @throws Exception
   */
  public Distance getDistance (
    GeoCoordinates coordinates,
    DistanceUnitType distanceUnit) throws Exception {

    Double latDistance =
      Math.toRadians(coordinates.getLatitude() - this.getLatitude() );

    Double lonDistance =
      Math.toRadians(coordinates.getLongitude() - this.getLongitude() );

    Double a =
      Math.sin(latDistance / 2) *
      Math.sin(latDistance / 2) +
      Math.cos(Math.toRadians(this.getLatitude() ) ) *
      Math.cos(Math.toRadians(coordinates.getLatitude() ) ) *
      Math.sin(lonDistance / 2) *
      Math.sin(lonDistance / 2);

    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a) );

    // convert to meters
    double distance = EarthConstantsInl.kRadiusKm.getValue() * c * 1000;

    double height = this.getAltitude() - coordinates.getAltitude();

    distance = Math.pow(distance, 2) + Math.pow(height, 2);

    double distanceValue = Math.sqrt(distance);

    Distance metreDistance = new Distance(distanceValue, DistanceUnitType.METRE);

    return metreDistance.getAs(distanceUnit);
  }
  
  /**
   * getGridCenterGeoCoordinates
   * @param geoGrid
   * @return the GeoCoordinates representing the center of param geoGrid
   * @throws Exception
   */
  public static GeoCoordinates getGridCenterGeoCoordinates (
    GeoGrid geoGrid) throws Exception {
    
    GeoGridIndex2D geoGridIndex2D = geoGrid.getGeoGridIndex2D();
    
    double normalized;
    double scaled;
    
    // calculate latitude
    
    normalized =
      NumbersInl.normalize(
        (double)geoGridIndex2D.getX(),
        0.0,
        (double)geoGrid.getGeoGridsConfig().getGridsVerticalCount(),
        0.0,
        geoGrid.getGeoGridsConfig().getMapLength().getValue() );
    
    scaled =
      normalized /
      geoGrid.getGeoGridsConfig().getScalingFactor().getValue();
    
    double latitude =
      (scaled * -1) +
      geoGrid.getGeoGridsConfig().getLatitudeRange().getMax().doubleValue();
    
    // calculate longitude
    
    normalized =
      NumbersInl.normalize(
        (double)geoGridIndex2D.getY(),
        0.0,
        (double)geoGrid.getGeoGridsConfig().getGridsHorizontalCount(),
        0.0,
        geoGrid.getGeoGridsConfig().getMapWidth().getValue() );
    
    scaled =
      normalized /
      geoGrid.getGeoGridsConfig().getScalingFactor().getValue();
    
    double longitude =
      scaled -
      geoGrid.getGeoGridsConfig().getLongitudeRange().getMax().doubleValue();
    
    return new GeoCoordinates(latitude, longitude);
  }
  
  @Override
  public String toString () {
    
    return
      "GeoCoordinates: latitude ["
      + this.latitude
      + "] longitude ["
      + this.longitude
      + "] altitide ["
      + this.altitude
      + "]";
  }
}
