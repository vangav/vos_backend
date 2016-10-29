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

import java.util.ArrayList;
import java.util.List;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Motion2DInl: is static inline methods relation to 2D motion
 * */
public class Motion2DInl {

  
  private enum LinearMotionType {
    
    SAME_POINT,
    HORIZONTAL,
    VERTICAL,
    OTHER
  }
  private static LinearMotionType getLinearMotionType (
    Point start,
    Point end) throws Exception {
    
    if (start.getX() == end.getX() && start.getY() == end.getY() ) {
      
      return LinearMotionType.SAME_POINT;
    } else if (start.getX() == end.getX() ) {
      
      return LinearMotionType.HORIZONTAL;
    } else if (start.getY() == end.getY() ) {
      
      return LinearMotionType.VERTICAL;
    }
    
    return LinearMotionType.OTHER;
  }
  private enum MotionDirection {
    
    INCREMENTAL,
    DECREMENTAL
  }
  private static MotionDirection getMotionDicrection (
    double start,
    double end) throws Exception {
    
    if (start <= end) {
      
      return MotionDirection.INCREMENTAL;
    }
    
    return MotionDirection.DECREMENTAL;
  }
  /**
   * getLinearMotionPath
   * finds a list of points that linearly starts with start point and ends with
   *   end point
   * @param start
   * @param end
   * @param step: distance between each two points on the path
   * @return list of points representing the path from start to end
   * @throws Exception
   */
  public static List<Point> getLinearMotionPath (
    Point start,
    Point end,
    double step) throws Exception {
    
    List<Point> motionPath = new ArrayList<>();
    
    LinearMotionType motionType = getLinearMotionType(start, end);
    
    Point currPoint;

    currPoint = start;
    
    motionPath.add(currPoint);
    
    if (motionType == LinearMotionType.SAME_POINT) {
      
      return motionPath;
    } else if (motionType == LinearMotionType.HORIZONTAL) {
      
      if (getMotionDicrection(start.getX(), end.getX() ) ==
          MotionDirection.INCREMENTAL) {
        
        while (currPoint.getX() < end.getX() ) {
          
          if ((currPoint.getX() + step) <= end.getX() ) {
            
            currPoint =
              (Point)new Point.Double(
                (currPoint.getX() ) + step, currPoint.getY() );
          } else {
            
            currPoint =
              (Point)new Point.Double(end.getX(), currPoint.getY() );
          }
          
          motionPath.add(currPoint);
        }
      } else {
        
        if ((currPoint.getX() + step) >= end.getX() ) {
          
          currPoint =
            (Point)new Point.Double(
              (currPoint.getX() ) - step, currPoint.getY() );
        } else {
          
          currPoint =
            (Point)new Point.Double(end.getX(), currPoint.getY() );
        }
        
        motionPath.add(currPoint);
      }
    } else if (motionType == LinearMotionType.VERTICAL) {
      
      if (getMotionDicrection(start.getY(), end.getY() ) ==
          MotionDirection.INCREMENTAL) {
        
        while (currPoint.getY() < end.getY() ) {
          
          if ((currPoint.getY() + step) <= end.getY() ) {
            
            currPoint =
              (Point)new Point.Double(
                currPoint.getX(), (currPoint.getY() ) + step);
          } else {
            
            currPoint =
              (Point)new Point.Double(currPoint.getX(), end.getY() );
          }
          
          motionPath.add(currPoint);
        }
      } else {
        
        if ((currPoint.getY() + step) >= end.getY() ) {
          
          currPoint =
            (Point)new Point.Double(
              currPoint.getX(), (currPoint.getY() ) - step);
        } else {
          
          currPoint =
            (Point)new Point.Double(currPoint.getX(), end.getY() );
        }
        
        motionPath.add(currPoint);
      }
    } else if (motionType == LinearMotionType.OTHER) {
      
      double slope =
        (((double)(start.getY() ) ) -
        ((double)(end.getY() ) ) ) /
        (((double)(start.getX() ) ) -
        ((double)(end.getX() ) ) );
      
      double c =
        (((double)start.getY() ) - ((double)start.getX() ) ) * slope;
      
      if (Math.abs(Math.abs(end.getX() ) -
                   Math.abs(end.getX() ) ) >
          Math.abs(Math.abs(end.getY() ) -
                   Math.abs(start.getY() ) ) ) {
        
        if (getMotionDicrection(start.getX(), end.getX() ) ==
            MotionDirection.INCREMENTAL) {
          
          while (end.getX() > currPoint.getX() ) {
            
            currPoint =
              (Point)new Point.Double(
                currPoint.getX() + step,
                (slope * (currPoint.getX() + step) ) + c);
            
            if (currPoint.getX() > end.getX() ) {
              
              currPoint =
                (Point)new Point.Double(
                  end.getX(),
                  (slope * end.getX() ) + c);
            }
            
            motionPath.add(currPoint);
          }
        } else {
          
          while (end.getX() < currPoint.getX() ) {
            
            currPoint =
              (Point)new Point.Double(
                currPoint.getX() - step,
                (slope * (currPoint.getX() - step) ) + c);
            
            if (currPoint.getX() < end.getX() ) {
              
              currPoint =
                (Point)new Point.Double(
                  end.getX(),
                  (slope * end.getX() ) + c);
            }
            
            motionPath.add(currPoint);
          }
        }
      } else {
        
        if (getMotionDicrection(start.getY(), end.getY() ) ==
            MotionDirection.INCREMENTAL) {
          
          while (end.getY() > currPoint.getY() ) {
            
            currPoint =
              (Point)new Point.Double(
                  (currPoint.getY() + step - c) / slope,
                  currPoint.getY() + step);
            
            if (currPoint.getY() > end.getY() ) {
              
              currPoint =
                (Point)new Point.Double(
                  (end.getY() - c) / slope,
                  end.getY() );
            }

            motionPath.add(currPoint);
          }
        } else {
          
          while (end.getY() < currPoint.getY() ) {
            
            currPoint =
              (Point)new Point.Double(
                (currPoint.getY() - step - c) / slope,
                currPoint.getY() - step);
            
            if (currPoint.getY() < end.getY() ) {
              
              currPoint =
                (Point)new Point.Double(
                  (end.getY() - c) / slope,
                  end.getY() );
            }
            
            motionPath.add(currPoint);
          }
        }
      }
    }
    
    return motionPath;
  }
}
