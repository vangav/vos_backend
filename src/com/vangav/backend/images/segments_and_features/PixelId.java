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
 * PixelId represents an image's pixel's ID
 * Can act as a key (e.g.: for a hash map)
 * */
public class PixelId {

  private static final String kIdSeparator = ":";
  private String id;
  private int x, y;
  
  /**
   * Constructor PixelId
   * @param x: pixel x coordinate
   * @param y: pixel y coordinate
   * @return new PixelId Object
   * */
  public PixelId (int x, int y) {
    
    this.id = "" + x + kIdSeparator + y;
    this.x = x;
    this.y = y;
  }
  
  /**
   * Constructor PixelId
   * @param id: x:y coordinates
   * @return new PixelId Object
   * @throws Exception
   */
  public PixelId (String id) throws Exception {
    
    this.id = id;
    
    String[] split = id.split(kIdSeparator);
    this.x = Integer.parseInt(split[0] );
    this.y = Integer.parseInt(split[1] );
  }
  
  /**
   * getId
   * @return String representation of the pixel's id
   */
  public final String getId () {
    
    return this.id;
  }
  
  /**
   * getX
   * @return int x-coordinate
   */
  public final int getX () {
    
    return this.x;
  }
  
  /**
   * getY
   * @return int y-coordinate
   */
  public final int getY () {
    
    return this.y;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + x;
    result = prime * result + y;
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PixelId other = (PixelId) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (x != other.x)
      return false;
    if (y != other.y)
      return false;
    return true;
  }
  
  @Override
  public String toString () {
    
    return
      "pixel id("
      + this.x
      + ", "
      + this.y
      + ")";
  }
}
