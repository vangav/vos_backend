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

import com.vangav.backend.data_structures_and_algorithms.tuple.Pair;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Circle represents a circle
 * */
public class Circle {

  private Point centerPoint;
  private double radius;
  private double circumference;
  private double area;
  
  /**
   * Constructor Circle
   * @param centerPoint
   * @param radius
   * @return new Circle Object
   * @throws Exception
   */
  public Circle (Point centerPoint, double radius) throws Exception {
    
    this.centerPoint = centerPoint;
    this.radius = radius;
    this.circumference = 2.0 * Math.PI * this.radius;
    this.area = Math.PI * this.radius * this.radius;
  }
  
  /**
   * getCenterPoint
   * @return this circle's center point
   */
  public Point getCenterPoint () {
    
    return this.centerPoint;
  }
  
  /**
   * getRadius
   * @return this circle's radius
   */
  public double getRadius () {
    
    return this.radius;
  }
  
  /**
   * getCircumference
   * @return this circle's circumference
   */
  public double getCircumference () {
    
    return this.circumference;
  }
  
  /**
   * getArea
   * @return this circle's area
   */
  public double getArea () {
    
    return this.area;
  }
  
  /**
   * equal
   * @param circle
   * @return true if both circles have the same center and radius and false
   *           otherwise
   * @throws Exception
   */
  public boolean equal (Circle circle) throws Exception {
    
    if (this.centerPoint.equal(circle.centerPoint) &&
        this.radius == circle.radius) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * isPointInside
   * @param point
   * @return true if param point is inside this circle and false otherwise
   * @throws Exception
   */
  public boolean isPointInside (Point point) throws Exception {
    
    if (point.getDistance(this.centerPoint) < this.radius) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * isIntersecting
   * @param circle
   * @return true if this circle and param circle intersects and false
   *           otherwise
   * @throws Exception
   */
  public boolean isIntersecting (Circle circle) throws Exception {
    
    if ((this.centerPoint.getDistance(circle.centerPoint) ) >=
        (this.radius + circle.radius) ) {
      
      return false;
    }
    
    return true;
  }
  
  /**
   * getIntersectionPoints
   * @param circle
   * @return a pair of intersection points between this circle and param
   *           circle - and null if those circles aren't intersecting
   * @throws Exception
   */
  public Pair<Point, Point> getIntersectionPoints (
    Circle circle) throws Exception {
    
    if (this.isIntersecting(circle) == false) {
      
      return null;
    }

    // first calculate distance between two centers circles P0 and P1.
    double distanceBetweenCenters =
      this.centerPoint.getDistance(circle.centerPoint);

    // normalize differences
    double dx = circle.centerPoint.getX() - this.centerPoint.getX();
    double dy = circle.centerPoint.getY() - this.centerPoint.getY();
    dx /= distanceBetweenCenters;
    dy /= distanceBetweenCenters;

    double a =
      (this.radius * this.radius +
       distanceBetweenCenters * distanceBetweenCenters -
       circle.radius * circle.radius) /
      (2.0 * distanceBetweenCenters);

    // h is then a^2 + h^2 = r0^2 ==> h = sqrt( r0^2 - a^2 )
    double arg = this.radius * this.radius - a * a;
    double h = (arg > 0.0) ? Math.sqrt(arg) : 0.0;

    double pointX = this.centerPoint.getX() + a * dx;
    double pointY = this.centerPoint.getY() + a * dy;

    // set two intersection points
    
    double x = pointX - h * dy;
    double y = pointY + h * dx;
    Point pointOne = new Point( x, y );
    
    x = pointX + h * dy;
    y = pointY - h * dx;
    Point pointTwo = new Point( x, y );
    
    return new Pair<Point, Point>(pointOne, pointTwo);
  }
  
  @Override
  public String toString () {
    
    return
      "Circle: center point("
      + this.centerPoint.toString()
      + ") radius("
      + this.radius
      + ")";
  }
}
