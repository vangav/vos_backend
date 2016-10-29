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
