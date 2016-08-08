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

package com.vangav.backend.geo.geo_grids;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;
import com.vangav.backend.math.geometry.LineSegment;
import com.vangav.backend.metrics.distance.Distance;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * GeoGrid represents a Geographical grid
 * 
 * Grids Layout (the following matrix represent's a map divided into grids)
 * (x, y) --> (row, column), e.g: (1,3) = 9
 *  1   2   3   4   5
 *  6   7   8   9  10
 * 11  12  13  14  15
 * 16  17  18  19  20
 * 21  22  23  24  25
 * */
public class GeoGrid {

  private GeoGridsConfig geoGridsConfig;
  private GeoCoordinates geoCoordinates;
  private GeoGridId geoGridId;
  private GeoGridIndex2D geoGridIndex2D;
  private GeoCoordinates centerGeoCoordinates;
  
  /**
   * Constructor
   * @return new invalid GeoGrid Object
   */
  public GeoGrid () {
    
    this.geoGridId = new GeoGridId();
  }
  
  /**
   * Constructor GeoGrid
   * @param geoGridsConfig
   * @param geoCoordinates
   * @return new GeoGrid Object
   * @throws Exception
   */
  public GeoGrid (
    GeoGridsConfig geoGridsConfig,
    GeoCoordinates geoCoordinates) throws Exception {
    
    this.geoGridsConfig = geoGridsConfig;
    this.geoCoordinates = geoCoordinates;
    
    double latitude = geoCoordinates.getLatitude();
    double longitude = geoCoordinates.getLongitude();
    
    latitude =
      this.geoGridsConfig.getLatitudeRange()
      .shiftValueToZeroStart(latitude).doubleValue();
    latitude *= this.geoGridsConfig.getScalingFactor().getValue();
    latitude /= this.geoGridsConfig.getGridDimension().getValue();
    
    longitude =
      this.geoGridsConfig.getLongitudeRange()
      .shiftValueToZeroStart(longitude).doubleValue();
    longitude *= this.geoGridsConfig.getScalingFactor().getValue();
    longitude /= this.geoGridsConfig.getGridDimension().getValue();
    
    this.geoGridId =
      new GeoGridId(
        (long)longitude +
        ((long)latitude *
         (long)this.geoGridsConfig.getGridsHorizontalCount() ) );
    
    this.geoGridIndex2D =
      new GeoGridIndex2D(
        ((this.geoGridId.getId() - 1L) /
         this.geoGridsConfig.getGridsHorizontalCount() ),
        ((this.geoGridId.getId() - 1) %
         this.geoGridsConfig.getGridsHorizontalCount() ) );
    
    this.centerGeoCoordinates =
      GeoCoordinates.getGridCenterGeoCoordinates(this);
  }
  
  /**
   * Constructor GeoGrid
   * this constructor is used for things like creating nearby/surrounding
   *   grids to a some other grid
   * @param geoGridsConfig
   * @param geoGridId
   * @return new GeoGrid Object where its coordinates represent the center
   *           coordinates of its grid
   * @throws Exception
   */
  public GeoGrid (
    GeoGridsConfig geoGridsConfig,
    GeoGridId geoGridId) throws Exception {
    
    this.geoGridsConfig = geoGridsConfig;
    this.geoGridId = geoGridId;
    
    this.geoGridIndex2D =
      new GeoGridIndex2D(
        ((this.geoGridId.getId() - 1L) /
         this.geoGridsConfig.getGridsHorizontalCount() ),
        ((this.geoGridId.getId() - 1) %
         this.geoGridsConfig.getGridsHorizontalCount() ) );
    
    this.geoCoordinates =
      GeoCoordinates.getGridCenterGeoCoordinates(this);
    
    this.centerGeoCoordinates =
      this.geoCoordinates;
  }
  
  /**
   * isValid
   * @return true if this GeoGrid is valid and false otherwise
   */
  public boolean isValid () {
    
    return this.geoGridId.isValid();
  }
  
  /**
   * getGeoGridsConfig
   * @return use grids configuration
   */
  public final GeoGridsConfig getGeoGridsConfig () {
    
    return this.geoGridsConfig;
  }
  
  /**
   * getGeoCoordinates
   * @return corresponding coordinates
   */
  public final GeoCoordinates getGeoCoordinates () {
    
    return this.geoCoordinates;
  }
  
  /**
   * getGeoGridId
   * @return grid's id
   */
  public final GeoGridId getGeoGridId () {
    
    return this.geoGridId;
  }
  
  /**
   * getGeoGridIndex2D
   * @return grid's 2D index (x, y)
   */
  public final GeoGridIndex2D getGeoGridIndex2D () {
    
    return this.geoGridIndex2D;
  }
  
  /**
   * getCenterGeoCoordinates
   * @return grid's center coordinates
   */
  public final GeoCoordinates getCenterGeoCoordinates () {
    
    return this.centerGeoCoordinates;
  }
  
  /**
   * equal
   * @param geoGrid
   * @return return true if this GeoGrid is equal to param GeoGrid and false
   *           otherwise
   *         two GeoGrids are equal if
   *           1) both are valid
   *           2) their config instances are equal
   *           3) and their GeoGridIds are equal
   * @throws Exception
   */
  public boolean equal (GeoGrid geoGrid) throws Exception {
    
    if (this.isValid() == false || geoGrid.isValid() == false) {
      
      return false;
    }
    
    if (this.geoGridsConfig.equal(geoGrid.geoGridsConfig) == false) {
      
      return false;
    }
    
    if (this.geoGridId.equal(geoGrid.geoGridId) == false) {
      
      return false;
    }
    
    return true;
  }
  
  /**
   * getDistance
   * @param geoGrid
   * @return returns the distance between this grid's coordinates
   *           and param grid's coordinates
   * @throws Exception
   */
  public Distance getDistance (GeoGrid geoGrid) throws Exception {
    
    if (this.geoGridsConfig.equal(geoGrid.geoGridsConfig) == false) {
      
      throw new CodeException(
        "this GeoGrid and param GeoGrid has different configurations, "
        + "can't calculate distance between them",
        ExceptionClass.ARGUMENT);
    }
    
    return this.geoCoordinates.getDistance(
      geoGrid.geoCoordinates,
      this.geoGridsConfig.getDistanceUnitType() );
  }
  
  /**
   * getCentersDistance
   * @param geoGrid
   * @return returns the distance between this grid's center coordinates
   *           and param grid's center coordinates
   * @throws Exception
   */
  public Distance getCentersDistance (GeoGrid geoGrid) throws Exception {
    
    if (this.geoGridsConfig.equal(geoGrid.geoGridsConfig) == false) {
      
      throw new CodeException(
        "this GeoGrid and param GeoGrid has different configurations, "
        + "can't calculate distance between them",
        ExceptionClass.ARGUMENT);
    }
    
    return this.centerGeoCoordinates.getDistance(
      geoGrid.centerGeoCoordinates,
      this.geoGridsConfig.getDistanceUnitType() );
  }
  
  /**
   * getLine
   * @param geoGrid
   * @return Line segment between this grid's 2D index and param geoGrid's
   *           2D index
   * @throws Exception
   */
  public LineSegment getLine (GeoGrid geoGrid) throws Exception {
    
    if (this.geoGridsConfig.equal(geoGrid.geoGridsConfig) == false) {
      
      throw new CodeException(
        "this GeoGrid and param GeoGrid has different configurations, "
        + "can't calculate LineSegment between them",
        ExceptionClass.ARGUMENT);
    }
    
    return new LineSegment(this.geoGridIndex2D, geoGrid.geoGridIndex2D);
  }

  private static final int kDefaultSurroundingLevels = 1;
  /**
   * getSurroundingGrids
   * overload for getSurroundingGrids(int levels)
   * @return surrounding grids with one level
   * @throws Exception
   */
  public GeoGrid[][] getSurroundingGrids () throws Exception {
    
    return this.getSurroundingGrids(kDefaultSurroundingLevels);
  }
  
  /**
   * getSurroundingGrids
   * @param levels
   * @return a matrix of this grid and its surrounding grids, and adds invalid
   *           grids in case of an overflow (e.g.: using an edge grid)
   * e.g.: for levels equal to 2 with an over flow on the right edge given
   *         that this grid's id is equal to 14 the result would be
   *         x represents an invalid grid (overflow)
   *         in the default case of earth's map, overflow can only happen
   *         somewhere in the Pacific Ocean or at the poles
   *  2   3   4   5  x
   *  7   8   9  10  x
   * 12  13  14  15  x
   * 17  18  19  20  x
   * 22  23  24  25  x
   * @throws Exception
   */
  public GeoGrid[][] getSurroundingGrids (int levels) throws Exception {
    
    if (this.isValid() == false) {
      
      throw new CodeException(
        "can't get surrounding grids for an invalid grid",
        ExceptionClass.ARGUMENT);
    }
    
    ArgumentsInl.checkIntGreaterThanOrEqual(
      "surrounding GeoGrids levels",
      levels,
      1,
      ExceptionType.CODE_EXCEPTION);
    
    GeoGrid [][] result = new GeoGrid [levels * 2 + 1][levels * 2 + 1];
    
    long startI = (long)(this.geoGridId.getId() -
                         (this.geoGridsConfig.getGridsHorizontalCount() *
                          levels) );
    long endI = (long)(this.geoGridId.getId() +
                       (this.geoGridsConfig.getGridsHorizontalCount() *
                        levels) );
    long iIncrement = (long)(this.geoGridsConfig.getGridsHorizontalCount() );
    
    int resultI = 0;
    int resultJ = 0;
    
    GeoGrid currGeoGrid;
    
    for (long i = startI; i <= endI; i += iIncrement, resultI ++) {
      
      for (long j = (long)(levels * -1L); j <= levels; j ++, resultJ ++) {
        
        currGeoGrid = new GeoGrid(this.geoGridsConfig, new GeoGridId(i + j) );
        
        // this grid? add it
        if (this.equal(currGeoGrid) == true) {
          
          result[resultI][resultJ] = currGeoGrid;
        }
        // within the range of this grid config? add it
        else if (this.geoGridsConfig.withinRange(
                     currGeoGrid.centerGeoCoordinates) == true) {
          
          result[resultI][resultJ] = currGeoGrid;
        }
        // out of range grid? add an invalid grid
        else {
          
          result[resultI][resultJ] = new GeoGrid();
        }
      }
      
      resultJ = 0;
    }
    
    return result;
  }
  
  @Override
  public String toString () {
    
    return
      "GeoGrid:-\n"
      + this.geoGridsConfig.toString()
      + "\n"
      + this.geoCoordinates.toString()
      + "\n"
      + this.geoGridId.toString()
      + "\n"
      + this.geoGridIndex2D.toString()
      + "\n"
      + this.centerGeoCoordinates.toString();
  }
}
