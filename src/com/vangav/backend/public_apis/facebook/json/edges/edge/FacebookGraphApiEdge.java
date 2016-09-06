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

package com.vangav.backend.public_apis.facebook.json.edges.edge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vangav.backend.networks.rest.RestResponseJson;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * FacebookGraphApiEdge is the parent class for all Facebook Graph API edges
 * 
 * Reference:
 * https://developers.facebook.com/docs/graph-api/reference/v2.7/user
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class FacebookGraphApiEdge extends RestResponseJson {

  @JsonIgnore
  public abstract String getEdgeName () throws Exception;

  /**
   * getPageLimit
   * @return limit that restricts the number of objects to get per page
   *           default is 100
   */
  @JsonIgnore
  public int getPageLimit () throws Exception {
    
    return
      FacebookGraphApiEdgeProperties.i().getIntProperty(
        FacebookGraphApiEdgeProperties.kPageLimit);
  }

  /**
   * getAllPages
   * @return true if this edge should get all its pages and false if this edge
   *           should only get the first page
   *           default is true (get all edge's pages)
   * @throws Exception
   */
  @JsonIgnore
  public boolean getAllPages () throws Exception {
    
    return
      FacebookGraphApiEdgeProperties.i().getBooleanProperty(
        FacebookGraphApiEdgeProperties.kGetAllPages);
  }
  
  @JsonProperty
  public Paging paging;
  @JsonProperty
  public Summary summary;
  
  /**
   * hasPaging
   * @return true if this edge has paging and false otherwise
   * @throws Exception
   */
  @JsonIgnore
  final public boolean hasPaging () throws Exception {
    
    if (this.paging == null) {
      
      return false;
    }
    
    return true;
  }
  
  /**
   * hasCursorBefore
   * @return true if this edge has a before cursor and false otherwise
   * @throws Exception
   */
  @JsonIgnore
  final public boolean hasCursorBefore () throws Exception {
    
    if (this.hasPaging() == false) {
      
      return false;
    }
    
    if (this.paging.cursors == null) {
      
      return false;
    }
    
    if (this.paging.cursors.before == null) {
      
      return false;
    }
    
    return true;
  }
  
  /**
   * getCursorBefore
   * @return this edge's cursor-before if exists and null otherwise
   * @throws Exception
   */
  @JsonIgnore
  final public String getCursorBefore () throws Exception {
    
    try {
      
      return this.paging.cursors.before;
    } catch (Exception e) {
      
      return null;
    }
  }
  
  /**
   * hasCursorAfter
   * @return true if this edge has an after cursor and false otherwise
   * @throws Exception
   */
  @JsonIgnore
  final public boolean hasCursorAfter () throws Exception {
    
    if (this.hasPaging() == false) {
      
      return false;
    }
    
    if (this.paging.cursors == null) {
      
      return false;
    }
    
    if (this.paging.cursors.after == null) {
      
      return false;
    }
    
    return true;
  }
  
  /**
   * getCursorAfter
   * @return this edge's cursor-after if exists and null otherwise
   * @throws Exception
   */
  @JsonIgnore
  final public String getCursorAfter () throws Exception {
    
    try {
      
      return this.paging.cursors.after;
    } catch (Exception e) {
      
      return null;
    }
  }
  
  /**
   * hasNextPage
   * @return true if this edge has a next page and false otherwise
   * @throws Exception
   */
  @JsonIgnore
  final public boolean hasNextPage () throws Exception {
    
    if (this.hasPaging() == false) {
      
      return false;
    }
    
    if (this.paging.next == null) {
      
      return false;
    }
    
    return true;
  }
  
  /**
   * getNextPage
   * @return this edge's next page if exists and null otherwise
   * @throws Exception
   */
  @JsonIgnore
  final public String getNextPage () throws Exception {
    
    try {
      
      return this.paging.next;
    } catch (Exception e) {
      
      return null;
    }
  }
  
  /**
   * hasPreviousPage
   * @return true if this edge has a previous page and false otherwise
   * @throws Exception
   */
  @JsonIgnore
  final public boolean hasPreviousPage () throws Exception {
    
    if (this.hasPaging() == false) {
      
      return false;
    }
    
    if (this.paging.previous == null) {
      
      return false;
    }
    
    return true;
  }
  
  /**
   * getPreviousPage
   * @return this edge's previous page if exists and null otherwise
   * @throws Exception
   */
  @JsonIgnore
  final public String getPreviousPage () throws Exception {
    
    try {
      
      return this.paging.previous;
    } catch (Exception e) {
      
      return null;
    }
  }

  @JsonIgnore
  private static final int kInvalidTotalCount = -1;
  
  /**
   * hasTotalCount
   * @return true if this edge has a total_count and false otherwise
   * @throws Exception
   */
  @JsonIgnore
  final public boolean hasTotalCount () throws Exception {
    
    try {
      
      if (this.getTotalCount() != kInvalidTotalCount) {
        
        return true;
      }
      
      return false;
      
    } catch (Exception e) {
      
      return false;
    }
  }
  
  /**
   * getTotalCount
   * @return this edge's total_count and -1 in case of an invalid total_count
   * @throws Exception
   */
  @JsonIgnore
  final public int getTotalCount () throws Exception {
    
    try {
      
      return this.summary.total_count;
    } catch (Exception e) {
      
      return kInvalidTotalCount;
    }
  }
}
