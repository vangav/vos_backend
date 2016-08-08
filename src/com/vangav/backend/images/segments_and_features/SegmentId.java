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
 * SegmentId represents an image's segment's ID
 * Can be a hash id (e.g.: in a hash map)
 * */
public class SegmentId {

  private int id;
  
  /**
   * Constructor SegmentId
   * @param id
   * @return new SegmentId Object
   */
  public SegmentId (int id) {
    
    this.id = id;
  }
  
  /**
   * getId
   * @return id's value
   */
  public final int getId () {
    
    return this.id;
  }
  
  /**
   * setId
   * @param id
   */
  public void setId (int id) {
    
    this.id = id;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    SegmentId other = (SegmentId) obj;
    if (id != other.id) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString () {
    
    return
      "segment id("
      + this.id
      + ")";
  }
}
