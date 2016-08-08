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

package com.vangav.backend.math.geometry;

import java.awt.geom.Point2D;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Point represents a Point(double x, double y)
 * */
public class Point extends Point2D.Double {

  /**
   * generated serialization id
   */
  private static final long serialVersionUID = 5712464956292689899L;
  private double x;
  private double y;
  
  /**
   * Constructor Point
   * @param x
   * @param y
   * @return new Point Object
   */
  public Point (double x, double y) {
    
    this.x = x;
    this.y = y;
  }

  @Override
  public void setLocation(double x, double y) {
    
    this.x = x;
    this.y = y;
  }
  
  /**
   * setX
   * updates this point's x value
   * @param x
   * @throws Exception
   */
  public void setX (double x) throws Exception {
    
    this.x = x;
  }
  
  @Override
  public double getX () {
    
    return this.x;
  }
  
  /**
   * setY
   * updates this point's y value
   * @param y
   * @throws Exception
   */
  public void setY (double y) throws Exception {
    
    this.y = y;
  }
  
  @Override
  public double getY () {
    
    return this.y;
  }
  
  /**
   * equal
   * checks if a point has the same value as this point
   * @param point: the point to compare against
   * @return true if point and this point are equal, false otherwise
   * @throws Exception
   */
  public boolean equal (Point point) throws Exception {
    
    if (this.x == point.x &&
        this.y == point.y) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * getDistance
   * gets the distance between this point and the param point
   * @param point
   * @return the double distance between both points
   * @throws Exception
   */
  public double getDistance (Point point) throws Exception {
    
    return Math.sqrt(
      Math.pow(this.x - point.x, 2) +
      Math.pow(this.y - point.y, 2) );
  }
  
  /**
   * getInverse
   * @return new Point Object with inverse values of x, y
   *           (e.g.: (-2.0, 5.5) --> (2.0, -5.5) )
   * @throws Exception
   */
  public Point getInverse () throws Exception {
    
    return new Point(this.x * -1.0, this.y * -1.0);
  }
  
  /**
   * plus
   * @param value
   * @return new Point Object whose x and y are greater than this
   *           point by value
   * @throws Exception
   */
  public Point plus (double value) throws Exception {
    
    return new Point(this.x + value, this.y + value);
  }
  
  /**
   * plus
   * @param point
   * @return new Point Object whose x and y are the addition of this point's
   *           and point's x and y
   * @throws Exception
   */
  public Point plus (Point point) throws Exception {
    
    return new Point(this.x + point.x, this.y + point.y);
  }
  
  /**
   * minus
   * @param value
   * @return new Point Object whose x and y are lesser than this
   *           point by value
   * @throws Exception
   */
  public Point minus (double value) throws Exception {
    
    return new Point(this.x - value, this.y - value);
  }
  
  /**
   * minus
   * @param point
   * @return new Point Object whose x and y are the subtraction of this point's
   *           and point's x and y
   * @throws Exception
   */
  public Point minus (Point point) throws Exception {
    
    return new Point(this.x - point.x, this.y - point.y);
  }
  
  /**
   * multiply
   * @param value
   * @return new Point Object whose x and y are multiplied by value
   * @throws Exception
   */
  public Point multiply (double value) throws Exception {
    
    return new Point(this.x * value, this.y * value);
  }
  
  /**
   * multiply
   * @param point
   * @return new Point Object whose x and y are the multiplication of this
   *           point's and point's x and y
   * @throws Exception
   */
  public Point multiply (Point point) throws Exception {
    
    return new Point(this.x * point.x, this.y * point.y);
  }
  
  /**
   * divide
   * @param value
   * @return new Point Object whose x and y are divided by value
   * @throws Exception
   */
  public Point divide (double value) throws Exception {
    
    return new Point(this.x / value, this.y / value);
  }
  
  /**
   * divide
   * @param point
   * @return new Point Object whose x and y are the division of this
   *           point's and point's x and y
   * @throws Exception
   */
  public Point divide (Point point) throws Exception {
    
    return new Point(this.x / point.x, this.y / point.y);
  }
  
  @Override
  public String toString () {
    
    return
      "Point ("
      + this.x
      + ", "
      + this.y
      + ")";
  }
}
