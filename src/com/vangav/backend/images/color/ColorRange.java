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

package com.vangav.backend.images.color;

import com.vangav.backend.images.color.rgb.Rgba;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ColorRange is the parent class for representing color ranges
 *   from all color spaces
 * */
public abstract class ColorRange {
  
  public abstract boolean withinRange (
    Rgba rgbaColor) throws Exception;
}
