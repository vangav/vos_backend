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

package com.vangav.backend.content.formatting;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * SerializationInl has static inline methods for serializing/deserializing
 *   Objects
 * */
public class SerializationInl {

  /**
   * serializeObject
   * @param object to serialize
   * @return String representation of the Base64 serialized param object
   * @throws Exception
   */
  public static String serializeObject (Serializable object) throws Exception {
    
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    ObjectOutputStream objectOutputStream =
      new ObjectOutputStream(byteArrayOutputStream);
    
    objectOutputStream.writeObject(object);
    objectOutputStream.close();
    
    return
      Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray() );
  }
  
  /**
   * deserializeObject
   * @param serializedObject: string representation of the Base64 serialization
   *                            of an Object
   * @return deserialized Object
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserializeObject (
    String serializedObject) throws Exception {

    byte[] data = Base64.getDecoder().decode(serializedObject);
    ObjectInputStream objectInputStream =
      new ObjectInputStream(new ByteArrayInputStream(data) );
    
    Object object = objectInputStream.readObject();
    objectInputStream.close();
    
    return (T)object;
  }
}
