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

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ColorRgba represents RGBA colors
 * */
public class ColorRangeGreyLimit extends ColorRangeRgba {
  
  /**
   * Constructor ColorRangeGreyLimit
   * @param regbaValue: an int containing values for R, G, B and A (alpha)
   *                      e.g.: int rgba = BufferedImage.getRGB();
   * @return new ColorRangeGreyLimit Object
   * @throws Exception
   * */
  public ColorRangeGreyLimit (Rgba rgbaValue) throws Exception {
	  
	  super(rgbaValue);
  }
  
  /**
   * Constructor ColorRangeGreyLimit
   * @param greyValue: [0 ... 255] value for RGBA channels
   * @return new ColorRangeGreyLimit Object
   * @throws Exception
   * */
  public ColorRangeGreyLimit (int greyValue) throws Exception {
    
	  super(greyValue, greyValue, greyValue, greyValue);
  }

  /**
   * Constructor ColorRangeGreyLimit
   * @param r: red value [0 ... 255]
   * @param g: green value [0 ... 255]
   * @param b: blue value [0 ... 255]
   * @param a: alpha value [0 ... 255]
   * @return new ColorRangeGreyLimit Object
   * @throws Exception
   */
  private ColorRangeGreyLimit (int r, int g, int b, int a) throws Exception {
    
  	super(r, g, b, a);
  }

  /**
   * withinRange
   * Checks if all the r, g, b, a values are greater than color
   * @param color: the ColorRgba object to compare against
   * @return boolean: (true for within range and false otherwise)
   * @throws Exception
   * */
  @Override
  public boolean withinRange (Rgba rgbaColor) throws Exception {
  
    if (this.r > rgbaColor.getRed() &&
        this.g > rgbaColor.getGreen() &&
        this.b > rgbaColor.getBlue() &&
        this.a > rgbaColor.getAlpha() ) {
      
      return true;
    }
    
    return false;
  }
}
