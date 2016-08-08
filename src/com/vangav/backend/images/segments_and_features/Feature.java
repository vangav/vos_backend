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
 * Feature is a parent for all Features extracted from Segments
 * */
public abstract class Feature {

  protected Segment segment;
  protected int pixelStep;

  protected int segmentHeight;
  protected int segmentWidth;
  
  /**
   * Constructor Feature
   * @return new Feature Object
   * @throws Exception
   */
  public Feature () throws Exception {
    
    this.segment = null;
  }
  
  /**
   * getName
   * @return feature's name
   */
  public abstract String getName ();
  
  /**
   * getSegment
   * @return feature's segment
   */
  public final Segment getSegment () {
    
    return this.segment;
  }

  /**
   * cloneFeature
   * @return new Feature Object (clone)
   * @throws Exception
   */
  public abstract Feature cloneFeature () throws Exception;
  
  /**
   * initAndProcess
   * initialize the Feature and process it on the given segment
   * @param segment
   * @param pixelStep: 1 -> process all pixels, >1 skip pixels
   * @throws Exception
   */
  public void initAndProcess (
    Segment segment,
    int pixelStep) throws Exception {
    
    this.init(segment, pixelStep);
    this.init();
    this.process();
  }
  
  private void init (Segment segment, int pixelStep) throws Exception {
    
    this.segment = segment;
    this.pixelStep = pixelStep;
    
    this.segmentHeight = this.segment.getMaxX() - this.segment.getMinX() + 1;
    this.segmentWidth = this.segment.getMaxY() - this.segment.getMinY() + 1;
    
    this.init();
  }
 
  protected abstract void init () throws Exception;
  protected abstract void process () throws Exception;
  
  @Override
  public String toString () {
    
    return
      "Feature: segment("
      + this.segment.toString()
      + ") pixel step("
      + this.pixelStep
      + ") segment height("
      + this.segmentHeight
      + ") segment width("
      + this.segmentWidth
      + ")";
  }
}
