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

import java.util.UUID;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.geo.EarthConstantsInl;
import com.vangav.backend.math.Range;
import com.vangav.backend.metrics.distance.Distance;
import com.vangav.backend.metrics.distance.DistanceUnitType;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * GeoGridsConfig represents the configuration for Geographical Grids
 * Configuration includes entities like:
 *   a) map width, b) grid dimension, etc ...
 * Default is Earth while grid dimension must be defined
 * 
 * Grids Layout (the following matrix represent's a map divided into grids)
 * (x, y) --> (row, column), e.g: (1,3) = 9
 *  1   2   3   4   5
 *  6   7   8   9  10
 * 11  12  13  14  15
 * 16  17  18  19  20
 * 21  22  23  24  25
 */
public class GeoGridsConfig {
  
  // INVALID VALUES - used to check which member variables aren't set in order
  //                    to set them to the default values
  
  private static final Distance kInvalidMapWidth =
    new Distance(-1.0, DistanceUnitType.METRE);
  private static final Distance kInvalidMapLength =
    new Distance(-1.0, DistanceUnitType.METRE);
  private static final Range kInvalidLatitudeRange = new Range();
  private static final Range kInvalidLongitudeRange = new Range();
  private static final double kInvalidLatitudeGaps = -1.0;
  private static final double kInvalidLongitudeGaps = -1.0;

  // DEFAULT values for class member variables (reflects Earth's)
  
  // Earth's map dimensions in metres
  private static final Distance kDefaultMapWidth =
    EarthConstantsInl.kMapWidthM;
  private static final Distance kDefaultMapLength =
    EarthConstantsInl.kMapLengthM;
  
  private static final Range kDefaultLatitudeRange =
    EarthConstantsInl.kLatitudeRange;
  private static final Range kDefaultLongitudeRange =
    EarthConstantsInl.kLongitudeRange;
  private static final double kDefaultLatitudeGaps =
    EarthConstantsInl.kLatitudeGaps;
  private static final double kDefaultLongitudeGaps =
    EarthConstantsInl.kLongitudeGaps;

  // CLASS MEMBER VARIABLES

  private String name;
  private String uuid;
  private DistanceUnitType distanceUnit;
  private Distance gridDimension;
  private Distance mapWidth;
  private Distance mapLength;
  private Range latitudeRange;
  private Range longitudeRange;
  private double latitudeGaps;
  private double longitudeGaps;
  private long gridsHorizontalCount;
  private long gridsVerticalCount;
  
  /**
   * scalingFactor is calculated according to the following mathematical
   *   operations
   * 
   * for Earth
   * scalingFactor = mapWidth / 360 (360 is the number of longitudes gaps on
   *                                   a map)
   * 
   * 
   * scalingFactor is used to calculate a grid id according to the following
   *   mathematical operations
   *
   * latitude -= 90
   * latitude *= -1
   * latitude *= scalingFactor
   * latitude /= gridDimension
   * 
   * longitude += 18-
   * longitude *= scaling Factor
   * longitude /= gridDimension
   * 
   * gridId = longitude + (latitude * horizontalGridsCount)
   */
  private Distance scalingFactor;
  
  /**
   * Constructor GeoGridsConfig 
   * sets default values for Earth
   * @param name (this configuration's name)
   * @param gridDimension (e.g.: a 100 metres grid dimension divides the map
   *                               into grids where each grid is a 100 metres *
   *                               100 metres square)
   * @return new GeoGridsConfig Object
   * @throws Exception
   */
  public GeoGridsConfig (
    String name,
    Distance gridDimension) throws Exception {
    
    this.name = name;
    this.uuid = UUID.randomUUID().toString();
    this.distanceUnit = gridDimension.getUnit();
    this.gridDimension = gridDimension;
    
    this.mapWidth = kDefaultMapWidth;
    this.mapLength = kDefaultMapLength;
    this.latitudeRange = kDefaultLatitudeRange;
    this.longitudeRange = kDefaultLongitudeRange;
    this.latitudeGaps = kDefaultLatitudeGaps;
    this.longitudeGaps = kDefaultLongitudeGaps;
    
    this.gridsHorizontalCount =
      (long)Math.ceil(this.mapWidth.divide(this.gridDimension).getValue() );
    this.gridsVerticalCount =
      (long)Math.ceil(this.mapLength.divide(this.gridDimension).getValue() );
    
    this.scalingFactor = this.mapWidth.divide(this.longitudeGaps);
    
    this.validate();
  }
  
  /**
   * Constructor GeoGridsConfig
   * @param geoGridsConfigBuilder
   * @return new GeoGridsConfig Object
   * @throws Exception
   */
  public GeoGridsConfig (
    GeoGridsConfigBuilder geoGridsConfigBuilder) throws Exception {
    
    this.name = geoGridsConfigBuilder.name;
    this.uuid = UUID.randomUUID().toString();
    this.gridDimension = geoGridsConfigBuilder.gridDimension;
    
    if (geoGridsConfigBuilder.mapWidth.equal(kInvalidMapWidth) == false) {
      
      this.mapWidth = geoGridsConfigBuilder.mapWidth;
    } else {

      this.mapWidth = kDefaultMapWidth;
    }
    
    if (geoGridsConfigBuilder.mapLength.equal(kInvalidMapLength) == false) {
      
      this.mapLength = geoGridsConfigBuilder.mapLength;
    } else {

      this.mapLength = kDefaultMapLength;
    }
    
    if (geoGridsConfigBuilder.latitudeRange != kInvalidLatitudeRange) {
      
      this.latitudeRange = geoGridsConfigBuilder.latitudeRange;
    } else {
      
      this.latitudeRange = kDefaultLatitudeRange;
    }
    
    if (geoGridsConfigBuilder.longitudeRange != kInvalidLongitudeRange) {
      
      this.longitudeRange = geoGridsConfigBuilder.longitudeRange;
    } else {
      
      this.longitudeRange = kDefaultLongitudeRange;
    }
    
    if (geoGridsConfigBuilder.latitudeGaps != kInvalidLatitudeGaps) {
      
      this.latitudeGaps = geoGridsConfigBuilder.latitudeGaps;
    } else {
      
      this.latitudeGaps = kDefaultLatitudeGaps;
    }
    
    if (geoGridsConfigBuilder.longitudeGaps != kInvalidLongitudeGaps) {
      
      this.longitudeGaps = geoGridsConfigBuilder.longitudeGaps;
    } else {
      
      this.longitudeGaps = kDefaultLongitudeGaps;
    }
    
    this.scalingFactor = this.mapWidth.divide(this.longitudeGaps);
    
    this.validate();
  }
  
  /**
   * validate
   * validates the values of this GeoGridsConfig
   * @throws Exception
   */
  private void validate () throws Exception {
    
    if (this.gridDimension.equalUnit(this.mapWidth) == false ||
        this.mapWidth.equalUnit(this.mapLength) == false) {
      
      throw new CodeException(
        "gridDimension, mapWidth and mapLength must all have the same unit. "
        + "Grid dimension ["
        + this.gridDimension.toString()
        + "]. Map width ["
        + this.mapWidth.toString()
        + "]. Map length ["
        + this.mapLength.toString()
        + "].",
        ExceptionClass.ARGUMENT);
    }
    
    if (this.gridDimension.greaterThan(this.mapWidth) ||
        this.gridDimension.greaterThan(this.mapLength) ) {
      
      throw new CodeException(
        "Grid dimension can't be greater than map width or map length. "
        + "Grid dimension ["
        + this.gridDimension.toString()
        + "]. Map width ["
        + this.mapWidth.toString()
        + "]. Map length ["
        + this.mapLength.toString()
        + "].",
        ExceptionClass.ARGUMENT);
    }
    
    if (this.latitudeRange.isValid() == false) {
      
      throw new CodeException(
        "latitude range is invalid",
        ExceptionClass.ARGUMENT);
    }
    
    if (this.longitudeRange.isValid() == false) {
      
      throw new CodeException(
        "longitude range is invalid",
        ExceptionClass.ARGUMENT);
    }
    
    if (this.latitudeGaps < 1.0) {
      
      throw new CodeException(
        "Latitdue gaps ["
        + this.latitudeGaps
        + "] can't be smaller than 1",
        ExceptionClass.ARGUMENT);
    }
    
    if (this.longitudeGaps < 1.0) {
      
      throw new CodeException(
        "Longitude gaps ["
        + this.longitudeGaps
        + "] can't be smaller than 1",
        ExceptionClass.ARGUMENT);
    }
  }
  
  /**
   * getName
   * @return name
   */
  public final String getName () {
    
    return this.name;
  }
  
  /**
   * getUuid
   * @return this config's UUID
   */
  public final String getUuid () {
    
    return this.uuid;
  }
  
  /**
   * getDistanceUnitType
   * @return this config's distance unit type (e.g.: metre, mile, etc ...)
   */
  public final DistanceUnitType getDistanceUnitType () {
    
    return this.distanceUnit;
  }
  
  /**
   * getGridDimension
   * @return grid dimension
   */
  public final Distance getGridDimension () {
    
    return this.gridDimension;
  }
  
  /**
   * getGridDimension
   * @return map width
   */
  public final Distance getMapWidth () {
    
    return this.mapWidth;
  }
  
  /**
   * getMapLength
   * @return map length
   */
  public final Distance getMapLength () {
    
    return this.mapLength;
  }
  
  /**
   * getLatitudeRange
   * @return latitude range
   */
  public final Range getLatitudeRange () {

    return this.latitudeRange;
  }
  
  /**
   * getLongitudeRange
   * @return longitude range
   */
  public final Range getLongitudeRange () {
    
    return this.longitudeRange;
  }
  
  /**
   * getMapLength
   * @return latitude gaps
   */
  public final double getLatitudeGaps () {
    
    return this.latitudeGaps;
  }
  
  /**
   * getLongitudeGaps
   * @return longitude gaps
   */
  public final double getLongitudeGaps () {
    
    return this.longitudeGaps;
  }
  
  /**
   * getGridsHorizontalCount
   * @return grids horizontal count (i.e.: number of grids in a single row of
   *                                         grids)
   */
  public final long getGridsHorizontalCount () {
    
    return this.gridsHorizontalCount;
  }
  
  /**
   * getGridsVerticalCount
   * @return grids vertical count (i.e.: number of grids in a single column of
   *                                       grids)
   */
  public final long getGridsVerticalCount () {
    
    return this.gridsVerticalCount;
  }
  
  /**
   * getScalingFactor
   * @return scaling factor
   */
  public final Distance getScalingFactor () {
    
    return this.scalingFactor;
  }
  
  /**
   * equal
   * @param geoGridsConfig
   * @return true if this config and param config are equal,
   *           and false otherwise
   * @throws Exception
   */
  public boolean equal (GeoGridsConfig geoGridsConfig) throws Exception {
    
    if (this.uuid.compareTo(geoGridsConfig.uuid) == 0) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * withinRange
   * @param geoCoordinates
   * @return true if param geoCoordinates is withing this config's range
   *           and false otherwise
   * @throws Exception
   */
  public boolean withinRange (GeoCoordinates geoCoordinates) throws Exception {
    
    if (this.latitudeRange.withinRange(
          geoCoordinates.getLatitude() ) == false) {
      
      return false;
    }
    
    if (this.longitudeRange.withinRange(
          geoCoordinates.getLongitude() ) == false) {
      
      return false;
    }
    
    return true;
  }
  
  @Override
  public String toString () {
    
    return
      "GeoGridsConfig:\n"
      + "Name [" + this.name + "]\n"
      + "UUID [" + this.uuid + "]\n"
      + "Distance Unit [" + this.distanceUnit.toString() + "]\n"
      + "Grid dimension [" + this.gridDimension.toString() + "]\n"
      + "Map width [" + this.mapWidth.toString() + "]\n"
      + "Map length [" + this.mapLength.toString() + "]\n"
      + "Latitude Range [" + this.latitudeRange.toString() + "]\n"
      + "Longitude Range [" + this.longitudeRange.toString() + "]\n"
      + "Latitude gaps [" + this.latitudeGaps + "]\n"
      + "Longitude gaps [" + this.longitudeGaps + "]\n"
      + "Grids Horizontal Count [" + this.gridsHorizontalCount + "]\n"
      + "Grids VerticalCount [" + this.gridsVerticalCount + "]\n"
      + "Scaling factor [" + this.scalingFactor.toString() + "]";
  }
  
  /**
   * OPTIONAL - for default earth dimensions, use GeoGridsConfig constructor
   *              directly
   * GeoGridsConfigBuilder: is a builder for GeoGridsConfig since is has
   *                          multiple class member variables where all of them
   *                          are optional except for gridDimension
   * 
   * Usage example:
   *   GeoGridsConfig geoGridsConfig =
   *     GeoGridsConfig
   *       .GeoGridsConfigBuilder(new Distance(100, DistanceUnitType.METRE) )
   *       .mapWidth(new Distance(40750000, DistanceUnitType.METRE) )
   *       .mapLength(new Distance(20375000, DistanceUnitType.METRE) )
   *       .latitudeGaps(180.0)
   *       .longitudeGaps(360.0)
   *       .build();
   */
  public static class GeoGridsConfigBuilder {

    private final String name;
    private final Distance gridDimension;
    private Distance mapWidth;
    private Distance mapLength;
    private Range latitudeRange;
    private Range longitudeRange;
    private double latitudeGaps;
    private double longitudeGaps;
    
    /**
     * Constructor GeoGridsConfigBuilder
     * @param name: this config's name (e.g.: Mars, Berlin, Cairo, etc ...)
     * @param gridDimension (e.g.: 100 metres, etc ...)
     */
    public GeoGridsConfigBuilder (String name, Distance gridDimension) {
      
      this.name = name;
      this.gridDimension = gridDimension;
      this.mapWidth = kInvalidMapWidth;
      this.mapLength = kInvalidMapLength;
      this.latitudeRange = kInvalidLatitudeRange;
      this.longitudeRange = kInvalidLongitudeRange;
      this.latitudeGaps = kInvalidLatitudeGaps;
      this.longitudeGaps = kInvalidLongitudeGaps;
    }
    
    /**
     * mapWidth
     * sets mapWidth value
     * @param mapWidth
     * @return GeoGridsConfigBuilder with mapWidth value set
     */
    public GeoGridsConfigBuilder mapWidth (Distance mapWidth) {
      
      this.mapWidth = mapWidth;
      
      return this;
    }
    
    /**
     * mapLength
     * sets mapLength value
     * @param mapLength
     * @return GeoGridsConfigBuilder with mapLength set
     */
    public GeoGridsConfigBuilder mapLength (Distance mapLength) {
      
      this.mapLength = mapLength;
      
      return this;
    }
    
    /**
     * latitudeRange
     * sets latitudeRange value
     * @param latitudeRange
     * @return GeoGridsConfigBuilder with latitudeRange set
     */
    public GeoGridsConfigBuilder latitudeRange (Range latitudeRange) {
      
      this.latitudeRange = latitudeRange;
      
      return this;
    }
    
    /**
     * longitudeRange
     * sets longitudeRange value
     * @param longitudeRange
     * @return GeoGridsConfigBuilder with longitudeRange set
     */
    public GeoGridsConfigBuilder longitudeRange (Range longitudeRange) {
      
      this.longitudeRange = longitudeRange;
      
      return this;
    }
    
    /**
     * latitudeGaps
     * sets latitudeGaps value
     * @param latitudeGaps
     * @return GeoGridsConfigBuilder with latitudeGaps set
     */
    public GeoGridsConfigBuilder latitudeGaps (double latitudeGaps) {
      
      this.latitudeGaps = latitudeGaps;
      
      return this;
    }
    
    /**
     * longitudeGaps
     * sets longitudeGaps value
     * @param longitudeGaps
     * @return GeoGridsConfigBuilder with longitudeGaps set
     */
    public GeoGridsConfigBuilder longitudeGaps (double longitudeGaps) {
      
      this.longitudeGaps = longitudeGaps;
      
      return this;
    }
    
    /**
     * build
     * @return new GeoGridsConfig Object configured with this builder's values
     * @throws Exception
     */
    public GeoGridsConfig build () throws Exception {
      
      return new GeoGridsConfig(this);
    }
  }
}
