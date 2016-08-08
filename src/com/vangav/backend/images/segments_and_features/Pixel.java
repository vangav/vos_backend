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

package com.vangav.backend.images.segments_and_features;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Pixel represents an image's pixel
 * */
public class Pixel {

  private PixelId pixelId;
  
  /**
   * Constructor Pixel
   * @param pixelId
   * @return new Pixel Object
   */
  public Pixel (PixelId pixelId) {
    
    this.pixelId = pixelId;
  }
  
  /**
   * getPixelId
   * @return pixel's id
   */
  public final PixelId getPixelId () {
    
    return this.pixelId;
  }
  
  @Override
  public String toString () {
    
    return
      "Pixel: "
      + this.pixelId.toString();
  }
}
