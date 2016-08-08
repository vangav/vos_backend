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

import java.util.Arrays;

import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * |--A--|--B--|--C--|
 * 1-----------------4
 * -------------------
 * 2--D--|--E--|--F--5
 * -------------------
 * 3-----------------6
 * |--G--|--H--|--I--|
 * 
 * FeatureGridSpaces gets the percentage of empty spaces along the lines
 *   of a segment's grid
 * For example if the segment has the letter O the following will approximately
 *   represent this feature according to the drawing above:
 *   A: 40%
 *   B: 0%
 *   C: 40%
 *   
 *   D: 0%
 *   E: 80%
 *   F: 0%
 *   
 *   G: 40%
 *   H: 0%
 *   I: 40%
 *   
 *   1: 40%
 *   2: 0%
 *   3: 40%
 *   
 *   4: 40%
 *   5: 0%
 *   6: 40%
 * */
public class FeatureGridSpaces extends Feature {

  private int
    horizontalSpaces,
    verticalSpaces;
  
  private int
    horizontalFactor,
    verticalFactor;
  
  private double spaceSize;
  
  private double [][] spacesPercentages;
  
  private boolean [][] segmentGrid;
  
  /**
   * Constructor FeatureGridSpaces
   * @param segment: the segment to extract the feature from
   * @param horizontalSpaces: number of horizontal spaces to inspect
   * @param verticalSpaces: number of vertical spaces to inspect
   * @return new FeatureGridSpaces Object
   * @throws Exception
   * */
  public FeatureGridSpaces (
    int horizontalSpaces,
    int verticalSpaces) throws Exception {
    
    super();
    
    this.horizontalSpaces = horizontalSpaces;
    this.verticalSpaces = verticalSpaces;
  }
  
  private static final String kName = "FeatureGridSpaces";
  
  @Override
  public String getName () {
   
    return kName;
  }
  
  @Override
  public Feature cloneFeature () throws Exception {
    
    FeatureGridSpaces featureGridSpaces =
      new FeatureGridSpaces(this.horizontalSpaces, this.verticalSpaces);
    
    return featureGridSpaces;
  }
  
  @Override
  protected void init () throws Exception {

    // check arguments
    ArgumentsInl.checkNotNull(
      "Segment",
      this.segment,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkIntWithinRange(
      "Horizontal Spaces",
      this.horizontalSpaces,
      1,
      this.segmentWidth,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkIntWithinRange(
      "Vertical Spaces",
      this.verticalSpaces,
      1,
      this.segmentHeight,
      ExceptionType.CODE_EXCEPTION);
    
    this.horizontalFactor = this.segmentWidth / this.horizontalSpaces;
    this.verticalFactor = this.segmentHeight / this.verticalSpaces;
    
    this.spaceSize = (double) (this.horizontalFactor * this.verticalFactor);
    this.spaceSize /= ((double) this.pixelStep);
    
    this.spacesPercentages =
      new double[this.verticalSpaces][this.horizontalSpaces];
    
    this.segmentGrid = new boolean [this.segmentHeight] [this.segmentWidth];
    
    for (PixelId pixelId : this.segment.getPixels() ) {
      
      this.segmentGrid[pixelId.getX() - this.segment.getMinX() ]
                      [pixelId.getY() - this.segment.getMinY() ] = true;
    }
  }
  
  @Override
  protected void process () throws Exception {
    
    double segementPixelsCount = 0.0;
    
    for (int v = 0; v < this.verticalSpaces; v ++) {
      
      for (int h = 0; h < this.horizontalSpaces; h ++) {
        
        segementPixelsCount = 0.0;
        
        for (int i = (v * this.verticalFactor);
             i < ((v + 1) * this.verticalFactor);
             i ++) {
          
          for (int j = (h * this.horizontalFactor);
               j < ((h + 1) * this.horizontalFactor);
               j ++) {
            
            if (this.segmentGrid[i][j] == true) {
              
              segementPixelsCount += 1.0;
            }
          }
        }
        
        this.spacesPercentages[v][h] =
          ((segementPixelsCount / this.spaceSize) * 100.0);
      }
    }
  }
  
  /**
   * getSpacesPercentages
   * get all gird spaces percentages for this featur's segment
   * @return double[][] representing corresponding grid spaces percentages
   * @throws Exception
   */
  public double[][] getSpacesPercentages () throws Exception {
    
    return this.spacesPercentages;
  }
  
  /**
   * getSpacePercentage
   * get a specific grid space percentage
   * @param verticalIndex
   * @param horizontalIndex
   * @return double representation of the grid space percentage
   * @throws Exception
   */
  public double getSpacePercentage (
    int verticalIndex,
    int horizontalIndex) throws Exception {
    
    return this.spacesPercentages[verticalIndex][horizontalIndex];
  }
  
  @Override
  public String toString () {
    
    return
      super.toString()
      + "\n Feature Grid Spaces: "
      + "horizontal spaces("
      + this.horizontalSpaces
      + ") vertical spaces("
      + this.verticalSpaces
      + ") space size("
      + this.spaceSize
      + ") spaces percentages("
      + Arrays.toString(this.spacesPercentages)
      + ") segment grid("
      + Arrays.toString(this.segmentGrid)
      + ")";
  }
}
