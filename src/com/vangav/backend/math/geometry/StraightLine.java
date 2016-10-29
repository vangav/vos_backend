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
 * StraightLine represents an open ended line
 * */

public class StraightLine {

  // coefficients
  private double a;
  private double b;
  private double c;
  
  private boolean definedSlope;
  private double slope;
  
  /**
   * Constructor StraightLine
   * @param a coefficient
   * @param b coefficient
   * @param c coefficient
   * @return new StraightLine Object
   * @throws Exception
   */
  public StraightLine (double a, double b, double c) throws Exception {
    
    this.a = a;
    this.b = b;
    this.c = c;

    if (this.a != 0) {
      
      this.definedSlope = true;
      this.slope = - this.b / this.a;
    } else {
      
      this.definedSlope = false;
      this.slope = Double.NaN;
    }
  }
  
  /**
   * Constructor StraightLine
   * @param line
   * @return new StraightLine Object
   * @throws Exception
   */
  public StraightLine (LineSegment line) throws Exception {
    
    this(line.getStart(), line.getEnd() );
  }
  
  /**
   * Constructor StraightLine
   * @param pointOne
   * @param pointTwo
   * @return new StraightLine Object
   * @throws Exception
   */
  public StraightLine (Point pointOne, Point pointTwo) throws Exception {
    
    if ((pointOne.getX() - pointTwo.getX() ) != 0) {
      
      this.slope =
        (pointOne.getY() - pointTwo.getY() ) /
        (pointOne.getX() - pointTwo.getX() );
      
      this.a =  1.0;
      this.b = -1.0 * this.slope;
      this.c = -1.0 * (pointOne.getY() - this.slope * pointOne.getX() );
      
      this.definedSlope = true;
    } else {
      
      this.a =  1.0;
      this.b = 0.0;
      this.c = -1 * (pointOne.getY() );
      
      this.definedSlope = false;
      this.slope = Double.NaN;
    }
  }
  
  /**
   * getA
   * @return a coefficient
   */
  public double getA () {
    
    return this.a;
  }
  
  /**
   * getB
   * @return b coefficient
   */
  public double getB () {
    
    return this.b;
  }
  
  /**
   * getC
   * @return c coefficient
   */
  public double getC () {
    
    return this.c;
  }
  
  /**
   * isSlopeDefined
   * @return true if slope is defined and false otherwise
   */
  public boolean isSlopeDefined () {
    
    return this.definedSlope;
  }
  
  /**
   * getSlope
   * @return this line's slope
   */
  public double getSlope () {
    
    return this.slope;
  }
  
  /**
   * getXFromY
   * @param y
   * @return x value so that point (x, y) is on this line and it
   *           returns Double.NaN if that's not possible
   * @throws Exception
   */
  public double getXFromY (double y) throws Exception {
    
    if (this.b == 0) {
      
      return Double.NaN;
    }
    
    return -(this.a * y + this.c) / this.b;
  }
  
  /**
   * getYFromX
   * @param x
   * @return y value so that point (x, y) is on this line and it
   *           returns Double.NaN if that's not possible
   * @throws Exception
   */
  public double getYFromX (double x) throws Exception {
    
    if (this.a == 0) {
      
      return Double.NaN;
    }
    
    return -(this.b * x + this.c) / this.a;
  }
  
  /**
   * getIntersectionPoint
   * @param line
   * @return the intersection point between this line and param line
   *           and null otherwise
   * @throws Exception
   */
  public Point getIntersectionPoint (StraightLine line) throws Exception {
    
    // lines are parallel, no intersection
    if ((this.a / this.b) ==  (line.getA() / line.getB() ) ) {
      
      return null; 
    }

    double x, y;
    
    if (this.a == 0 ) {
      
      x = -this.c/this.b;
      y = line.getYFromX(x);
    } else if (line.getA() == 0) {
      
     x = -line.getC()/line.getB();
     y = getYFromX(x);
    } else {

      x = (this.a * line.getC() - line.getA() * this.c) /
          (line.getA() * this.b - this.a * line.getB() );
      y = getYFromX(x);
    }

    return new Point(x, y);
  }
  
  /**
   * getPointProjection
   * @param point
   * @return a straight line that passes by param point and is perpendicular on
   *           this straight line
   * @throws Exception
   */
  public StraightLine getPointProjection (Point point) throws Exception {

    return
      new StraightLine(
        this.b,
        -this.a,
        this.a * point.getX() - this.b * point.getY() );
  }
  
  /**
   * getNearestPointOnLine
   * @param point
   * @return nearest point on this line to param point and null otherwise
   * @throws Exception
   */
  public Point getNearestPointOnLine (Point point) throws Exception {
    
    StraightLine projectionLine = this.getPointProjection(point);
    
    return this.getIntersectionPoint(projectionLine);
  }
  
  /**
   * getDistanceToPoint
   * @param point
   * @return shortest distance between this line and param point
   * @throws Exception
   */
  public double getDistanceToPoint (Point point) throws Exception {
    
    return point.getDistance(this.getNearestPointOnLine(point) );
  }
  
  @Override
  public String toString () {
    
    return
      "StraightLine: a("
      + this.a
      + ") b("
      + this.b
      + ") c("
      + this.c
      + ") slope("
      + this.slope
      + ")";
  }
}
