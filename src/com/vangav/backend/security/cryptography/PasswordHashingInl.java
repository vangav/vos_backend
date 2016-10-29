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

package com.vangav.backend.security.cryptography;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.vangav.backend.data_structures_and_algorithms.tuple.Pair;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * PasswordHashingInl: has multiple inline static methods providing multiple
 *                       options for hashing strings - usually used for one-way
 *                       encryption to secure things like user passwords
 * Called after passwords since passwords are the most common use for these 
 *   algorithms but it's not restricted to passwords
 * */
public class PasswordHashingInl {

  // disable default instantiation
  private PasswordHashingInl () {}

  /**
   * simpleMd5
   * 128-bit (16-byte) hash value
   * maximum speed minimum security
   * prone to brute-force and dictionary attacks
   * not collision resistant
   * @param text to hash (e.g.: a password)
   * @return hashed text
   * @throws Exception
   */
  public static String simpleMd5 (String text) throws Exception {
    
    // Create MessageDigest instance for MD5
    MessageDigest md = MessageDigest.getInstance("MD5");
    
    // Add text bytes to digest
    md.update(text.getBytes() );
    
    // Get the hash's bytes - decimal format
    byte[] bytes = md.digest();
    
    //Convert it to hexadecimal format
    StringBuilder sb = new StringBuilder();
    
    for (int i = 0; i < bytes.length ; i ++) {
      
      sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1) );
    }
    
    // Get complete hashed text in hex format
    return sb.toString();
  }
  
  /**
   * getSalt
   * helper method
   * salt is used to make a hashing more secure
   * @return random salt
   * @throws Exception
   */
  public static byte[] getSalt () throws Exception {
    
    //Always use a SecureRandom generator
    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
    
    //Create array for salt
    byte[] salt = new byte[16];
    
    //Get a random salt
    sr.nextBytes(salt);
    
    //return salt
    return salt;
  }
  
  /**
   * saltedMd5
   * similar to simpleMd5 but adds salt to param text before hashing it for
   *   added security
   * NOTE: save the returned salt with the hashed text, must use the the exact
   *         pair of text and salt to regenerated the same hashed text
   * @param text to hash (e.g.: a password)
   * @return <hashed text, used salt>
   * @throws Exception
   */
  public static Pair<String, byte[]> saltedMd5 (
    String text,
    byte[] salt) throws Exception {

    // Create MessageDigest instance for MD5
    MessageDigest md = MessageDigest.getInstance("MD5");
    
    //Add password bytes to digest
    md.update(salt);
    
    //Get the hash's bytes - decimal format
    byte[] bytes = md.digest(text.getBytes() );

    //Convert it to hexadecimal format
    StringBuilder sb = new StringBuilder();
    
    for (int i = 0; i < bytes.length ; i ++) {
      
      sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1) );
    }
    
    //Get complete hashed password in hex format
    return new Pair<String, byte[]>(sb.toString(), salt);
  }
  
  // SHA algorithms names
  public static final String kSha160Bits = "SHA-1";
  public static final String kSha256Bits = "SHA-256";
  public static final String kSha384Bits = "SHA-384";
  public static final String kSha512Bits = "SHA-512";

  // SHA algorithms samples
  private static final String kSha160Sample =
    "e4c53afeaa7a08b1f27022abd443688c37981bc4";
  private static final String kSha256Sample =
    "87adfd14a7a89b201bf6d99105b417287db6581d8aee989076bb7f86154e8f32";
  private static final String kSha384Sample =
    "bc5914fe3896ae8a2c43a4513f2a0d716974cc305733847e3d49e1ea52d1ca50e2a9d0ac1"
    + "92acd43facfb422bb5ace88";
  private static final String kSha512Sample =
    "529211542985b8f7af61994670d03d25d55cc9cd1cff8d57bb799c4b586891e112b197530"
    + "c76744bcd7ef135b58d47d65a0bec221eb5d77793956cf2709dd012";
  
  /**
   * getShaTypeFromHashedString
   * helper method
   * @param hashedText
   * @return sha type based on param hashedText's length
   * @throws Exception
   */
  public static String getShaNameFromHashedString (
    String hashedText) throws Exception {
    
    if (hashedText.length() == kSha160Sample.length() ) {
      
      return kSha160Bits;
    }
    
    if (hashedText.length() == kSha256Sample.length() ) {
      
      return kSha256Bits;
    }
    
    if (hashedText.length() == kSha384Sample.length() ) {
      
      return kSha384Bits;
    }
    
    if (hashedText.length() == kSha512Sample.length() ) {
      
      return kSha512Bits;
    }
    
    throw new CodeException(
      "invalid SHA algorithm type, the length of param hashed text doesn't"
      + "match any of SHA algorithm types",
      ExceptionClass.TYPE);
  }
  
  /**
   * sha
   * sha generates more secure hashes than md5
   * not collision resistant - but collisions are very rare (can be ignored)
   * the higher the number of bits of the sha algorithm, the stronger it is
   * @param shaName (e.g.: SHA-1, SHA-512, etc ...)
   * @param text
   * @param text to hash (e.g.: a password)
   * @return <hashed text, used salt>
   * @throws Exception
   */
  public static Pair<String, byte[]> sha (
    String shaName,
    String text,
    byte[] salt) throws Exception {
    
    MessageDigest md = MessageDigest.getInstance(shaName);
    md.update(salt);
    byte[] bytes = md.digest(text.getBytes() ) ;
    StringBuilder sb = new StringBuilder();
    
    for (int i = 0; i < bytes.length ; i ++) {
      
      sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1) );
    }
    
    return new Pair<String, byte[]>(sb.toString(), salt);
  }
  
  /**
   * toHex
   * helper method
   * @param array (decimal format)
   * @return hex format as a String
   * @throws Exception
   */
  public static String toHex (byte[] array) throws Exception {
    
    BigInteger bi = new BigInteger(1, array);
    String hex = bi.toString(16);
    int paddingLength = (array.length * 2) - hex.length();
    
    if (paddingLength > 0) {
      
      return String.format("%0" + paddingLength + "d", 0) + hex;
    } else {
      
      return hex;
    }
  }
  
  /**
   * fromHex
   * helper method
   * @param hex
   * @return decimal format as an array
   * @throws Exception
   */
  public static byte[] fromHex (String hex) throws Exception {
    
    byte[] bytes = new byte[hex.length() / 2];
    
    for (int i = 0; i < bytes.length; i ++) {
      
      bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
    }
    return bytes;
  }
  
  /**
   * generatePbkdf2
   * generates a secured hashed text using PBKDF1 algorithm
   * PBKDF2 has variable security depending on the value of (iterations)
   *   the higher the value of (iterations) the more secure it's but also
   *   the slower it's.
   * it's more secure that SHA but slower
   * basically it's more secure because it's slower (as it slows down
   *   brute-force attacks)
   * @param text to hash (e.g.: a password)
   * @return hashed text corresponding to param text, embedded in the hash is
   *           the number of iterations and the used salt separated by ":" as
   *           iterations:salt:hashed_text
   * @throws Exception
   */
  public static String generatePbkdf2 (String text) throws Exception {
    
    int iterations = 1000;
    char[] chars = text.toCharArray();
    byte[] salt = getSalt();
     
    PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] hash = skf.generateSecret(spec).getEncoded();
    
    return iterations + ":" + toHex(salt) + ":" + toHex(hash);
  }
  
  /**
   * validatePbkdf2
   * valides that param hashed text is the PBKDF2 hash of param original text
   * @param originalText
   * @param hashedText
   * @return true for a match and false otherwise
   * @throws Exception
   */
  public static boolean validatePbkdf2 (
    String originalText,
    String hashedText) throws Exception {
    
    String[] parts = hashedText.split(":");
    int iterations = Integer.parseInt(parts[0] );
    byte[] salt = fromHex(parts[1] );
    byte[] hash = fromHex(parts[2] );

    PBEKeySpec spec =
      new PBEKeySpec(
        originalText.toCharArray(),
        salt,
        iterations,
        hash.length * 8);
    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    byte[] testHash = skf.generateSecret(spec).getEncoded();

    int diff = hash.length ^ testHash.length;
    
    for (int i = 0; i < hash.length && i < testHash.length; i ++) {
      
      diff |= hash[i] ^ testHash[i];
    }
    
    return diff == 0;
  }
}
