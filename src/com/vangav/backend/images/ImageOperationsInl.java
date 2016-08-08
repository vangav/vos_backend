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

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ImageOperationsInl is a class with inline static methods for image-related
 *   operations
 * */
public class ImageOperationsInl {

  // disable default instantiation
  private ImageOperationsInl () {}

  /**
   * scaleImage
   * @param srcImage: source image
   * @param scaleWidth: output width
   * @param scaleHeight: output height
   * @return new BufferedImage Object with the same content as srcImage and
   *           new width=scaleWidth and new height=scaleHeight
   * @throws Exception
   */
  public static BufferedImage scaleImage (
    BufferedImage srcImage,
    int scaleWidth,
    int scaleHeight) throws Exception {
    
    BufferedImage destImage =
      new BufferedImage(
        scaleWidth,
        scaleHeight,
        BufferedImage.TYPE_INT_RGB);
    
    Graphics2D destGraphics = destImage.createGraphics();
    
    AffineTransform affineTransform =
      AffineTransform.getScaleInstance(
        (double) scaleWidth / srcImage.getWidth(),
        (double) scaleHeight / srcImage.getHeight() );
    
    destGraphics.drawRenderedImage(srcImage, affineTransform);
    
    destGraphics.dispose();
    
    return destImage;
  }
  
  /**
   * imageToString
   * @param image
   * @param type: e.g.: png, tiff, etc ...
   * @return String representation of the input image
   * @throws Exception
   */
  public static String imageToString (
    BufferedImage image,
    String type) throws Exception {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    
    ImageIO.write(image, type, baos);
    baos.flush();
    byte[] imageAsRawBytes = baos.toByteArray();
    
    baos.close();

    return new String(imageAsRawBytes);
  }
  
  /**
   * stringToImage
   * @param imageStr image in string format
   * @return BufferedImage representation of the input image string
   * @throws Exception
   */
  public static BufferedImage stringToImage (
    String imageString) throws Exception {
    
    ByteArrayInputStream bais =
      new ByteArrayInputStream(imageString.getBytes() );
    
    return  ImageIO.read(bais);
  }
}
