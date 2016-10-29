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

package com.vangav.backend.images.color.rgb;

import com.vangav.backend.images.color.ColorRange;
import com.vangav.backend.math.NumbersInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ColorRangeRgba is the parent for representing color ranges in RGBA
 * */
public abstract class ColorRangeRgba extends ColorRange {
  
	protected static final int kMinValue = 0;
	protected static final int kMaxValue = 255;
	  
	protected int r, g, b, a;

  /**
   * ColorRangeRgba
   * @param rgbaValue
   * @return new ColorRangeRgba Object
   * @throws Exception
   */
  protected ColorRangeRgba (Rgba rgbaValue) throws Exception {
	    
    this(
      (rgbaValue.getRed()   >> 16) & 0xFF,
      (rgbaValue.getGreen() >>  8) & 0xFF,
      (rgbaValue.getBlue()  >>  0) & 0xFF,
      (rgbaValue.getAlpha() >> 24) & 0xFF);
  }
  
  /**
   * ColorRangeRgba
   * @param r
   * @param g
   * @param b
   * @param a
   * @return new ColorRangeRgba Object
   * @throws Exception
   */
  protected ColorRangeRgba (int r, int g, int b, int a) throws Exception {
	  
	  this.r = NumbersInl.trim(r, kMinValue, kMaxValue);
	  this.g = NumbersInl.trim(g, kMinValue, kMaxValue);
	  this.b = NumbersInl.trim(b, kMinValue, kMaxValue);
	  this.a = NumbersInl.trim(a, kMinValue, kMaxValue);
  }
  
  @Override
  public String toString () {
    
    return
      "rgba("
      + this.r
      + ", "
      + this.g
      + ", "
      + this.b
      + ", "
      + this.a
      + ")";
  }
}
