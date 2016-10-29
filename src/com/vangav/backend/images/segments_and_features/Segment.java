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

package com.vangav.backend.images.segments_and_features;

import java.util.HashSet;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Segment represents an image's segment
 * A segment is a part of an image that has one or more feature(s)
 *   (shape, color, ...)
 * */
public class Segment {
  
  private SegmentId segmentId;
  private int segmentSize;
  private int minX, maxX, minY, maxY;
  // pixel id is a concatenation of it's XY coordinates
  private HashSet<PixelId> pixels;
  
  /**
   * Constructor Segment
   * @param segmentId
   * @return new Segment Object
   * @throws Exception
   */
  public Segment (SegmentId segmentId) throws Exception {
    
    this.segmentId = segmentId;
    this.segmentSize = 0;
    this.minX = Integer.MAX_VALUE;
    this.maxX = Integer.MIN_VALUE;
    this.minY = Integer.MAX_VALUE;
    this.maxY = Integer.MIN_VALUE;
    this.pixels = new HashSet<PixelId>();
  }
  
  /**
   * getId
   * @return segment's id
   */
  public final SegmentId getId () {
    
    return this.segmentId;
  }
  
  /**
   * getSegmentSize
   * @return segment's size (number of pixels inside the segment)
   */
  public final int getSegmentSize () {
    
    return this.segmentSize;
  }
  
  /**
   * getMinX
   * @return minimum x-coordinate of this segment within the image
   */
  public final int getMinX () {
    
    return this.minX;
  }
  /**
   * getMaxX
   * @return maximum x-coordinate of this segment within the image
   */
  public final int getMaxX () {
    
    return this.maxX;
  }
  /**
   * getMinY
   * @return minimum y-coordinate of this segment within the image
   */
  public final int getMinY () {
    
    return this.minY;
  }
  /**
   * getMaxY
   * @return minimum y-coordinate of this segment within the image
   */
  public final int getMaxY () {
    
    return this.maxY;
  }
  
  /**
   * getPixels
   * @return segment's pixels (id)
   */
  public final HashSet<PixelId> getPixels () {
    
    return this.pixels;
  }
  
  /**
   * addPixel
   * add a pixel to this segment
   * @param pixel
   * @throws Exception
   */
  public void addPixel (Pixel pixel) throws Exception {
    
    this.segmentSize += 1;
    this.minX = Math.min(this.minX, pixel.getPixelId().getX() );
    this.maxX = Math.max(this.maxX, pixel.getPixelId().getX() );
    this.minY = Math.min(this.minY, pixel.getPixelId().getY() );
    this.maxY = Math.max(this.maxY, pixel.getPixelId().getY() );
    this.pixels.add(pixel.getPixelId());
  }
  
  /**
   * mergeSegment
   * merge a new segment into this segment
   * @param segment
   * @throws Exception
   */
  public void mergeSegment (Segment segment) throws Exception {
    
    if (segment == null) {
      
      return;
    }
    
    this.segmentSize += segment.segmentSize;
    this.minX = Math.min(this.minX, segment.minX);
    this.maxX = Math.max(this.maxX, segment.maxX);
    this.minY = Math.min(this.minY, segment.minY);
    this.maxY = Math.max(this.maxY, segment.maxY);
    this.pixels.addAll(segment.pixels);
  }
  
  @Override
  public String toString () {
    
    return
      "Segment: "
      + this.segmentId.toString()
      + " segment size("
      + this.segmentSize
      + ") min x("
      + this.minX
      + ") max x("
      + this.maxX
      + ") min y("
      + this.minY
      + ") max y("
      + this.maxY
      + ") pixels("
      + this.pixels.toString()
      + ")";
  }
}
