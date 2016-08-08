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

package com.vangav.backend.images;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;
import com.vangav.backend.files.FileLoaderInl;
import com.vangav.backend.images.color.ColorRange;
import com.vangav.backend.images.color.rgb.Rgba;
import com.vangav.backend.images.segments_and_features.Feature;
import com.vangav.backend.images.segments_and_features.Pixel;
import com.vangav.backend.images.segments_and_features.PixelId;
import com.vangav.backend.images.segments_and_features.Segment;
import com.vangav.backend.images.segments_and_features.SegmentId;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Image represents an image and performs operations like
 *   segmentation, feature extraction, ... etc
 * */
public class Image {
  
  /**
   * enum SegmentationType
   */
  public enum SegmentationType {
   
    FOUR_WAY
  }
  
  private static final int kGlobalMinImageDimension = 1;
  
  private static int globalMinImageWidth = 1;
  
  /**
   * setGlobalMinImageWidth
   * sets the global minimum width for all images
   * @param globalMinImageWidth
   * @throws Exception
   */
  public static void setGlobalMinImageWidth (
    int globalMinImageWidth) throws Exception {
    
    ArgumentsInl.checkIntGreaterThanOrEqual(
      "Global min image width",
      globalMinImageWidth,
      Image.kGlobalMinImageDimension,
      ExceptionType.CODE_EXCEPTION);
    
    Image.globalMinImageWidth = globalMinImageWidth;
  }
  
  private static int globalMinImageHeight = 1;
  
  /**
   * setGlobalMinImageHeight
   * sets the global minimum height for all images
   * @param globalMinImageHeight
   * @throws Exception
   */
  public static void setGlobalMinImageHeight (
    int globalMinImageHeight) throws Exception {
    
    ArgumentsInl.checkIntGreaterThanOrEqual(
      "Global min image height",
      globalMinImageHeight,
      Image.kGlobalMinImageDimension,
      ExceptionType.CODE_EXCEPTION);
    
    Image.globalMinImageHeight = globalMinImageHeight;
  }
  
  private static final int kDefaultPixelStep = 1;

  private String imagePath;
  private BufferedImage image;
  private int pixelStep;
  
  /**
   * reverse-index for pixel id to segment id
   * */
  private Map<PixelId, SegmentId> pixels;
  /**
   * Each segment has an integer ID
   * */
  private int currSegmentIdCounter;
  private Map<Integer, Segment> segments;
  /**
   * Segment ID -> <Feature Name (ID) -> Feature>
   * Each segment can have multiple segments applied to it
   * */
  private Map<Integer, Map<String, Feature> > features;
  
  public Image (String imagePath) throws Exception {
    
    this(imagePath, kDefaultPixelStep);
  }
  
  /**
   * Constructor Image
   * @param imagePath: path to the image's file
   * @param pixelStep: default = 1 (inspects all image's pixels)
   *                     for values > 1 pixels are skipped
   * @return new Image Object
   * @throws ExceptionS
   * */
  public Image (
    String imagePath,
    int pixelStep) throws Exception {
    
    this.imagePath = imagePath;
    this.image = FileLoaderInl.loadImageFile(imagePath);
    
    // check parameters
 
    ArgumentsInl.checkNotNull(
      "Image ["
      + imagePath
      + "] ",
      this.image,
      ExceptionType.CODE_EXCEPTION);
    
    ArgumentsInl.checkIntGreaterThanOrEqual(
      "Image ["
      + imagePath
      + "] width",
      this.image.getWidth(),
      Image.globalMinImageWidth,
      ExceptionType.CODE_EXCEPTION);
    
    ArgumentsInl.checkIntGreaterThanOrEqual(
      "Image ["
      + imagePath
      + "] height",
      this.image.getHeight(),
      Image.globalMinImageHeight,
      ExceptionType.CODE_EXCEPTION);
    
    ArgumentsInl.checkIntWithinRange(
      "Pixel Step for image ["
      + imagePath
      + "] with width ["
      + this.image.getWidth()
      + "] and height ["
      + this.image.getHeight()
      + "] ",
      pixelStep,
      1,
      Math.min(this.image.getWidth() / 2, this.image.getHeight() / 2),
      ExceptionType.CODE_EXCEPTION);
    
    this.pixelStep = pixelStep;
    
    this.segments = new HashMap<Integer, Segment>();
    this.features = new HashMap<Integer, Map<String, Feature> >();
  }
  
  private void resetSegmentation () throws Exception {
    
    this.pixels.clear();
    this.currSegmentIdCounter = 0;
    this.segments.clear();
    this.features.clear();
  }

  public void segmentSingleColorRange (
    ColorRange colorRange,
    SegmentationType type) throws Exception {
    
    this.resetSegmentation();
    
    if (type == SegmentationType.FOUR_WAY) {
      
      this.segmentSingleColorRangeFourWay(colorRange);
    }
  }
  
  private void segmentSingleColorRangeFourWay (
    ColorRange colorRange) throws Exception {
    
    SegmentId currSegmentId;
    
    // 1- segment top-left corner
    if (colorRange.withinRange(new Rgba(this.image.getRGB(0, 0) ) ) == true) {
      
      currSegmentId = new SegmentId(this.currSegmentIdCounter);
      
      this.pixels.put(
        new PixelId(0, 0),
        currSegmentId);
      this.segments.put(
        currSegmentId.getId(),
        new Segment(currSegmentId) );
      
      this.currSegmentIdCounter += 1;
    }
    
    // 2- segment left and top edges
    Pixel leftPixel, topPixel, currPixel;
    
    for (int i = this.pixelStep;
         i < this.image.getHeight();
         i+= this.pixelStep) {
      
      if (colorRange.withinRange(
            new Rgba(this.image.getRGB(i, 0) ) ) == false) {
        
        continue;
      }
        
      topPixel = new Pixel(new PixelId(i - this.pixelStep, 0) );
      currPixel = new Pixel(new PixelId(i, 0) );
      
      if (this.pixels.containsKey(topPixel.getPixelId() ) == true) {
        
        this.pixels.put(
          currPixel.getPixelId(),
          this.pixels.get(topPixel) );
        this.segments.get(this.pixels.get(
          topPixel.getPixelId() ).getId() ).addPixel(currPixel);
      } else {
        
        currSegmentId = new SegmentId(this.currSegmentIdCounter);
        
        this.pixels.put(
          currPixel.getPixelId(),
          currSegmentId);
        this.segments.put(
          currSegmentId.getId(),
          new Segment(currSegmentId) );
        
        this.currSegmentIdCounter += 1;
      }
    }
    
    for (int i = this.pixelStep;
         i < this.image.getWidth();
         i+= this.pixelStep) {
      
      if (colorRange.withinRange(
            new Rgba(this.image.getRGB(0, i) ) ) == false) {
        
        continue;
      }
        
      leftPixel = new Pixel(new PixelId(0, i - this.pixelStep) );
      currPixel = new Pixel(new PixelId(0, i) );
      
      if (this.pixels.containsKey(leftPixel.getPixelId() ) == true) {
        
        this.pixels.put(
          currPixel.getPixelId(),
          this.pixels.get(leftPixel) );
        this.segments.get(this.pixels.get(
          leftPixel.getPixelId() ).getId() ).addPixel(currPixel);
      } else {
        
        currSegmentId = new SegmentId(this.currSegmentIdCounter);
        
        this.pixels.put(
          currPixel.getPixelId(),
          currSegmentId);
        this.segments.put(
          currSegmentId.getId(),
          new Segment(currSegmentId) );
        
        this.currSegmentIdCounter += 1;
      }
    }
    
    // 3- segment the rest of the image
    for (int i = this.pixelStep;
         i < this.image.getHeight();
         i += this.pixelStep) {
      
      for (int j = this.pixelStep;
           j < this.image.getWidth();
           j ++) {
        
        if (colorRange.withinRange(
              new Rgba(this.image.getRGB(i, j) ) ) == false) {
          
          continue;
        }
        
        topPixel = new Pixel(new PixelId(i - this.pixelStep, j) );
        leftPixel = new Pixel(new PixelId(i, j - this.pixelStep) );
        currPixel = new Pixel(new PixelId(i, j) );

        if (this.pixels.containsKey(topPixel.getPixelId() ) == true &&
            this.pixels.containsKey(leftPixel.getPixelId() ) == true) {
          
          if (this.pixels.get(topPixel.getPixelId() ).getId() !=
              this.pixels.get(leftPixel.getPixelId() ).getId() ) {
            
            // - merge left pixel's segment into top pixel's segment
            // 1- alter the segment's id
            this.pixels.get(leftPixel.getPixelId() ).setId(
              this.pixels.get(topPixel.getPixelId() ).getId() );
            // 2- merge left segment into top segment
            this.segments.get(this.pixels.get(
              topPixel.getPixelId() ).getId() ).mergeSegment(
                this.segments.get(this.pixels.get(
                  leftPixel.getPixelId() ).getId() ) );
            // 3- delete left segment
            this.segments.remove(this.pixels.get(
              leftPixel.getPixelId() ).getId() );
          }
        }
        
        if (this.pixels.containsKey(topPixel.getPixelId() ) == true) {
          
          this.pixels.put(
            currPixel.getPixelId(),
            this.pixels.get(topPixel) );
          this.segments.get(this.pixels.get(
            topPixel.getPixelId() ).getId() ).addPixel(currPixel);
        } else if (this.pixels.containsKey(leftPixel.getPixelId() ) == true) {
          
          this.pixels.put(
            currPixel.getPixelId(),
            this.pixels.get(leftPixel) );
          this.segments.get(this.pixels.get(
            leftPixel.getPixelId() ).getId() ).addPixel(currPixel);
        } else {
          
          currSegmentId = new SegmentId(this.currSegmentIdCounter);
          
          this.pixels.put(
            currPixel.getPixelId(),
            currSegmentId);
          this.segments.put(
            currSegmentId.getId(),
            new Segment(currSegmentId) );
          
          this.currSegmentIdCounter += 1;
        }
      }
    }
  }
  
  /**
   * applyFeature
   * applies a feature to the image's segments
   * @param feature
   * @throws Exception
   */
  public void applyFeature (Feature feature) throws Exception {
    
    if (this.segments.isEmpty() == true) {
      
      throw new CodeException (
        "Image ["
        + this.imagePath
        + "] isn't segmented, segment image before applying features",
        ExceptionClass.MISSING_STEP);
    }
    
    Feature currFeature;
    
    for (int segmentId : this.segments.keySet() ) {
      
      currFeature = feature.cloneFeature();
      currFeature.initAndProcess(this.segments.get(segmentId), this.pixelStep);
      
      if (this.features.containsKey(segmentId) == false) {
        
        this.features.put(
          segmentId,
          new HashMap<String, Feature>() );
      }
      
      this.features.get(segmentId).put(
        currFeature.getName(),
        currFeature);
    }
  }
  
  @Override
  public String toString () {
    
    return
      "Image: path("
      + this.imagePath
      + ") pixel step("
      + this.pixelStep
      + ") pixels("
      + this.pixels.toString()
      + ") segments("
      + this.segments.toString()
      + ") features("
      + this.features.toString()
      + ")";
  }
}
