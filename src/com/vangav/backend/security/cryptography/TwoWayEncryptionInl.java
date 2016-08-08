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

package com.vangav.backend.security.cryptography;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * TwoWayEncryptionInl: is a group of inline static methods that provide
 *                        some options for two-way-encryption
 * given a secret key (e.g.: user password), data can be encrypted/decrypted
 */
public class TwoWayEncryptionInl {

  // disable default instantiation
  private TwoWayEncryptionInl () {}

  /**
   * getSecretKeySpecAes
   * @param key: secret key (e.g.: user password, etc ...)
   * @return corresponding SecretKeySpec to param key based on AES
   *           AES (Advanced Encryption Standard)
   * @throws Exception
   */
  public static SecretKeySpec getSecretKeySpecAes (
    String key) throws Exception {
    
    byte[] keyBytes = key.getBytes("UTF-8");
    MessageDigest sha = MessageDigest.getInstance("SHA-1");
    keyBytes = sha.digest(keyBytes);
    keyBytes = Arrays.copyOf(keyBytes, 16);
    
    return new SecretKeySpec(keyBytes, "AES");
  }
  
  /**
   * encryptAes
   * Overloading encryptAes: simplified by accepting String key
   * @param key: secret key (e.g: password, etc ...)
   * @param toEncrypt: text to be AES encrypted using AES
   * @return encrypted text
   * @throws Exception
   */
  public static String encryptAes (
    String key,
    String toEncrypt) throws Exception {
   
    return encryptAes(getSecretKeySpecAes(key), toEncrypt);
  }
  
  /**
   * encryptAes
   * @param key: secret key (e.g: password, etc ...)
   * @param toEncrypt: text to be encrypted using AES
   * @return encrypted text
   * @throws Exception
   */
  public static String encryptAes (
    SecretKeySpec key,
    String toEncrypt) throws Exception {
    
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, key);
    
    return
      Base64.getEncoder().encodeToString(
        cipher.doFinal(toEncrypt.getBytes("UTF-8") ) );
  }
  
  /**
   * decryptAes
   * Overloading decryptAes: simplified by accepting String key
   * @param key: secret key (e.g.: password, etc ...)
   * @param toDecrypt: text to be decrypted using AES
   * @return decrypted text
   * @throws Exception
   */
  public static String decryptAes (
    String key,
    String toDecrypt) throws Exception {
    
    return decryptAes(getSecretKeySpecAes(key), toDecrypt);
  }
  
  /**
   * decryptAes
   * @param key: secret key (e.g.: password, etc ...)
   * @param toDecrypt: text to be decrypted using AES
   * @return decrypted text
   * @throws Exception
   */
  public static String decryptAes (
    SecretKeySpec kye,
    String toDecrypt) throws Exception {
    
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
    cipher.init(Cipher.DECRYPT_MODE, kye);
    
    return new String(cipher.doFinal(Base64.getDecoder().decode(toDecrypt) ) );
  }
}
