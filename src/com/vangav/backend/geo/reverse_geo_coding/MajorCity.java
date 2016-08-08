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

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import java.util.Comparator;

import com.vangav.backend.data_structures_and_algorithms.third_party.kd_tree.KDNodeComparator;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * MajorCity represents the 754 major cities around the world
 * */
public class MajorCity extends KDNodeComparator<MajorCity> {
  
  private String name;
  private double latitude;
  private double longitude;
  private double point[] = new double[3];

  /**
   * Constructor MajorCity
   * @param data: major city name : longitude : latitude
   * @return new MajorCity Object
   * @throws Exception
   */
  public MajorCity (String data) throws Exception {
    
    String[] parts = data.split("\t");
    this.name = parts[0];
    this.longitude = Double.parseDouble(parts[1] );
    this.latitude = Double.parseDouble(parts[2] );
    this.setPoint();
  }
  
  /**
   * Constructor MajorCity
   * @param latitude
   * @param longitude
   * @return new MajorCity Object
   * @throws Exception
   */
  public MajorCity (Double latitude, Double longitude) throws Exception {
    
    name = "Search";
    this.latitude = latitude;
    this.longitude = longitude;
    this.setPoint();
  }
  
  /**
   * getMajorCity
   * @return major city's name
   */
  public String getMajorCity () {
    
    return this.name;
  }
  
  private void setPoint() {
    
    point[0] = cos(toRadians(latitude) ) * cos(toRadians(longitude) );
    point[1] = cos(toRadians(latitude) ) * sin(toRadians(longitude) );
    point[2] = sin(toRadians(latitude) );
  }

  @Override
  protected double squaredDistance(MajorCity other) {
    
    double x = this.point[0] - other.point[0];
    double y = this.point[1] - other.point[1];
    double z = this.point[2] - other.point[2];
    
    return (x * x) + (y * y) + (z * z);
  }

  @Override
  protected double axisSquaredDistance(MajorCity other, int axis) {
    
    double distance = point[axis] - other.point[axis];
    
    return distance * distance;
  }

  @Override
  protected Comparator<MajorCity> getComparator(int axis) {
    
    return MajorCityComparator.values()[axis];
  }

  protected static enum MajorCityComparator implements Comparator<MajorCity> {
    x {
      @Override
      public int compare(MajorCity a, MajorCity b) {
        
        return Double.compare(a.point[0], b.point[0] );
      }
    },
    y {
      @Override
      public int compare(MajorCity a, MajorCity b) {
        
        return Double.compare(a.point[1], b.point[1] );
      }
    },
    z {
      @Override
      public int compare(MajorCity a, MajorCity b) {
        
        return Double.compare(a.point[2], b.point[2] );
      }
    };
  }
  
  @Override
  public String toString () {
    
    return
      "name("
      + this.name
      + ") latitude("
      + this.latitude
      + ") longitude("
      + this.longitude;
  }
}
