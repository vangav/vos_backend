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

import com.vangav.backend.math.geometry.Point;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * GeoGridIndex2D represents the 2D index of a grid on a map
 * 
 * Grids Layout (the following matrix represent's a map divided into grids)
 * index example: (x, y) --> (row, column), e.g: (1,3) = 9
 *  1   2   3   4   5
 *  6   7   8   9  10
 * 11  12  13  14  15
 * 16  17  18  19  20
 * 21  22  23  24  25
 */
public class GeoGridIndex2D extends Point {

  /**
   * generated serialization id
   */
  private static final long serialVersionUID = 7712479968908824197L;
  
  /**
   * Constructor GeoGridIndex2D
   * @param x
   * @param y
   * @return new GeoGridIndex2D Object
   */
  public GeoGridIndex2D (long x, long y) {
    
    super(x, y);
  }
}
