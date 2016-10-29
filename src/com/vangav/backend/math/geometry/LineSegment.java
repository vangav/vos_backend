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

package com.vangav.backend.math.geometry;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * LineSegment represents a line segment
 * */
public class LineSegment {
  
  private Point start;
  private Point end;
  private double dX;
  private double dY;
  
  /**
   * Constructor LineSegment
   * @param start point
   * @param end point
   * @return new LineSegment Object
   */
  public LineSegment (Point start, Point end) throws Exception {
    
    this.start = start;
    this.end = end;
    this.dX = this.start.getX() - this.end.getX();
    this.dY = this.start.getY() - this.end.getY();
  }

  /**
   * getStart
   * @return this line's start point
   * @throws Exception
   */
  public Point getStart () throws Exception {
    
    return this.start;
  }
  
  /**
   * getEnd
   * @return this line's end point
   * @throws Exception
   */
  public Point getEnd () throws Exception {
    
    return this.end;
  }
  
  /**
   * equal
   * @param line
   * @return true if lines are equivalent and false otherwise
   * @throws Exception
   */
  public boolean equal (LineSegment line) throws Exception {
    
    if (this.start.equal(line.start) == true &&
        this.end.equal(line.end) == true) {
      
      return true;
    }
    
    if (this.start.equal(line.end) == true &&
        this.end.equal(line.start) == true) {
      
      return true;
    }
    
    return false;
  }
  
  /**
   * getLength
   * @return the line segment's length
   * @throws Exception
   */
  public double getLength () throws Exception {
    
    return this.start.getDistance(this.end);
  }
  
  /**
   * getSlope
   * @return this line's slope
   * @throws Exception
   */
  public double getSlope () throws Exception {
    
    return
      (this.start.getY() - this.end.getY() ) /
      (this.start.getX() - this.end.getX() );
  }
  
  /**
   * getDirection
   * @return this line's direction [-180 ... 180]
   * @throws Exception
   */
  public double getDirection () throws Exception {
    
    return Math.toDegrees(Math.atan2(
        this.end.getX() - this.start.getX(),
        this.end.getY() - this.start.getY() ) );
  }
  
  // 0 to 180
  /**
   * getDirectionDelta
   * @param line
   * @return the delta direction between this line and the param line
   * @throws Exception
   */
  public double getDirectionDelta (LineSegment line) throws Exception {
    
    double deltaDir = Math.abs(this.getDirection() - line.getDirection() );
    
    if (deltaDir > 180) {
      
      deltaDir = 360 - deltaDir;
    }
    
    return deltaDir;
  }
  
  /**
   * isPointOnLine
   * @param point
   * @return true if param point is on this line and false otherwise
   * @throws Exception
   */
  public boolean isPointOnLine (Point point) throws Exception {
    
    if (this.start.equal(point) == true ||
        this.end.equal(point) == true) {
      
      return true;
    }
    
    if (!(point.getX() <= Math.max(this.start.getX(), this.end.getX() ) &&
          point.getX() >= Math.min(this.start.getX(), this.end.getX() ) ) ) {

      return false;
    }
    
    if (!(point.getY() <= Math.max(this.start.getY(), this.end.getY() ) &&
          point.getY() >= Math.min(this.start.getY(), this.end.getY() ) ) ) {
    
      return false;
    }
    
    LineSegment testLine = new LineSegment(point, this.start);
    
    if (this.getSlope() != testLine.getSlope() ) {
      
      return false;
    }
    
    return true;
  }
  
  /**
   * getNearestPointOnLine
   * @param point
   * @return nearest point on this line to param point
   * @throws Exception
   */
  public Point getNearestPointOnLine (Point point) throws Exception {
    
    if (this.isPointOnLine(point) == true) {
      
      return point;
    }
    
    double u =
      ((point.getX() - this.end.getX() ) *
       this.dX + (point.getY() - this.end.getY() ) * this.dY) /
      (this.dX * this.dX + this.dY * this.dY);
    
    if (u < 0.0) {
      
      return this.end;
    } else if (u > 1.0) {
      
      return this.start;
    } else {

      return new Point (
        this.end.getX() + u * this.dX,
        this.end.getY() + u * this.dY);
    }
  }
  
  /**
   * getMinDistance
   * @param point
   * @return minimum distance between param point and this line
   * @throws Exception
   */
  public double getMinDistance (Point point) throws Exception {
    
    return point.getDistance(this.getNearestPointOnLine(point) );
  }
  
  /**
   * getIntersectionPoint
   * @param line
   * @return returns the point where this line and param line intersects and
   *           null otherwise
   * @throws Exception
   */
  public Point getIntersectionPoint (LineSegment line) throws Exception {
    
    StraightLine lineOne = new StraightLine(line);
    StraightLine lineTwo = new StraightLine(this);
    
    Point intersectionPoint = lineOne.getIntersectionPoint(lineTwo);
    
    if (intersectionPoint == null) {
      
      return null;
    }
    
    if (this.isPointOnLine(intersectionPoint) == true) {
      
      return intersectionPoint;
    }
    
    return null;
  }
  
  /**
   * projectionInRange
   * @param point
   * @return true if the point's projection intersects with this line and
   *           false otherwise
   * @throws Exception
   */
  public boolean pointProjectionInRange (Point point) throws Exception {
    
    double dx = this.end.getX() - this.start.getX();
    double dy = this.end.getY() - this.start.getY();
    
    double innerProduct =
      (point.getX() - this.start.getX() ) *
      dx +
      (point.getY() - this.start.getY() ) *
      dy;
    
    return 0 <= innerProduct && innerProduct <= dx * dx + dy * dy;
  }
  
  @Override
  public String toString () {
    
    return
      "LineSegment: Start: "
      + this.start.toString()
      + " End: "
      + this.end.toString();
  }
}
