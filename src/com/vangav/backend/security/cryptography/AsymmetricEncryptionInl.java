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

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import com.vangav.backend.data_structures_and_algorithms.tuple.Pair;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * AsymmetricEncryptionInl: is a group of inline static methods that provide
 *                            some options for asymmetric-encryption
 * public/private key encryption (e.g.: RSA, etc ...)
 * */
public class AsymmetricEncryptionInl {

  // disable default instantiation
  private AsymmetricEncryptionInl () {}

  /**
   * generateRsaKey
   * generate a new 1024 bits RSA key
   * @return a pair of public key and private key
   * @throws Exception
   */
  public static Pair<PublicKey, PrivateKey> generateRsaKey (
    ) throws Exception {
    
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
    keyGen.initialize(1024);
    KeyPair key = keyGen.generateKeyPair();
    
    return new Pair<PublicKey, PrivateKey>(key.getPublic(), key.getPrivate() );
  }
  
  /**
   * encryptRsa
   * @param toEncrypt: text to be encrypted
   * @param key: public key
   * @return byte[]: encrypted text
   * @throws Exception
   */
  public static byte[] encryptRsa (
    String toEncrypt,
    PublicKey publicKey) throws Exception {

    // get an RSA cipher object and print the provider
    final Cipher cipher = Cipher.getInstance("RSA");
    
    // encrypt the plain text using the public key
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    
    return cipher.doFinal(toEncrypt.getBytes() );
  }

  /**
   * decryptRsa
   * @param toDecrypt
   * @param privateKey
   * @return original text (decrypted) in string format
   * @throws Exception
   */
  public static String decryptRsa (
    byte[] toDecrypt,
    PrivateKey privateKey) throws Exception {

    // get an RSA cipher object and print the provider
    final Cipher cipher = Cipher.getInstance("RSA");

    // decrypt the text using the private key
    cipher.init(Cipher.DECRYPT_MODE, privateKey);

    return new String(cipher.doFinal(toDecrypt) );
  }

}
