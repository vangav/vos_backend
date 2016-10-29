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
 * Rgba converts an int containing R, G, B, A values
 *   (e.g.: from BufferedImage.getRGB(); ) and extracts
 *   each value in a new int
 * */
public class Rgba {

  private int r, g, b, a;
  
  /**
   * Constructor Rgba
   * @param rgba
   * @return new Rgba Object
   * @throws Exception
   */
  public Rgba (int rgba) throws Exception {
    
    this.r = (rgba >> 16) & 0xFF;
    this.g = (rgba >>  8) & 0xFF;
    this.b = (rgba >>  0) & 0xFF;
    this.a = (rgba >> 24) & 0xFF;
  }
  
  /**
   * getRed
   * @return int value of red channel
   */
  public int getRed () {
    
    return this.r;
  }
  
  /**
   * getGreen
   * @return int value of green channel
   */
  public int getGreen () {
    
    return this.g;
  }
  
  /**
   * getBlue
   * @return int value of blue channel
   */
  public int getBlue () {
    
    return this.b;
  }
  
  /**
   * getAlpha
   * @return int value of alpha channel
   */
  public int getAlpha () {
    
    return this.a;
  }
  
  @Override
  public String toString () {
    
    return
      "rgba ("
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
