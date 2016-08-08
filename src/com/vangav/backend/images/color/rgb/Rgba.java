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
