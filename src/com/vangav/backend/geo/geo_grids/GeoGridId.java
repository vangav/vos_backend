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

package com.vangav.backend.geo.geo_grids;

import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * GeoGridId represents a Geographical Grid ID
 * Can be used as a hashing key (e.g.: in a HashMap, etc ...)
 * 
 * Grids Layout (the following matrix represent's a map divided into grids)
 * (x, y) --> (row, column), e.g: (1,3) = 9
 *  1   2   3   4   5
 *  6   7   8   9  10
 * 11  12  13  14  15
 * 16  17  18  19  20
 * 21  22  23  24  25
 */
public class GeoGridId {

  private static final long kInvalidId = -1L;
  private static final long kMinValidId = 1L;
  
  private long id;

  /**
   * Constructor GeoGridId
   * @return new invalid GeoGridId Object
   */
  public GeoGridId () {
    
    this.id = kInvalidId;
  }
  
  /**
   * Constructor GeoGridId
   * @param id
   * @return new GeoGridId Object
   */
  public GeoGridId (long id) throws Exception {
    
    ArgumentsInl.checkLongGreaterThanOrEqual(
      "GeoGridId id",
      id,
      kMinValidId,
      ExceptionType.CODE_EXCEPTION);
    
    this.id = id;
  }
  
  /**
   * isValid
   * @return true if this GeoGridId is valid and false otherwise
   */
  public boolean isValid () {
    
    if (this.id == kInvalidId) {
      
      return false;
    }
    
    return true;
  }
  
  /**
   * getId
   * @return id long value
   */
  public long getId () {
    
    return this.id;
  }
  
  /**
   * equal
   * @param geoGridId
   * @return false if either this GeoGridId or param GeoGridId is invalid or
   *           if they don't have the same id value and true if they are both
   *           valid and have the same id value
   */
  public boolean equal (GeoGridId geoGridId) {
    
    if (this.isValid() == false || geoGridId.isValid() == false) {
      
      return false;
    }
    
    if (this.id != geoGridId.id) {
      
      return false;
    }
    
    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
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
    if (!(obj instanceof GeoGridId)) {
      return false;
    }
    GeoGridId other = (GeoGridId) obj;
    if (id != other.id) {
      return false;
    }
    return true;
  }
  
  @Override
  public String toString () {
    
    return
      "GeoGridId ["
      + this.id
      + "]";
  }
}
