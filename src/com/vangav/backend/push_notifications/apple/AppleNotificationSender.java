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

package com.vangav.backend.push_notifications.apple;

import io.netty.util.concurrent.Future;

import java.io.File;
import java.util.HashMap;

import com.relayrides.pushy.apns.ApnsClient;
import com.relayrides.pushy.apns.ClientNotConnectedException;
import com.relayrides.pushy.apns.PushNotificationResponse;
import com.relayrides.pushy.apns.util.ApnsPayloadBuilder;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import com.relayrides.pushy.apns.util.TokenUtil;
import com.vangav.backend.data_structures_and_algorithms.tuple.Pair;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * AppleNotificationSender handles sending AppleNotification Objects to
 *   Apple devices through Apple Push Notification Server
 * Handles one app per instance
 * Uses pushy lib to handle sending/response operations -->
 *   https://github.com/relayrides/pushy
 * */
public class AppleNotificationSender {

  public enum ClientType {
    
    /**
     * DEVELOPMENT
     * used for the dev version of an app (has a separate certificate provided
     *   by Apple)
     */
    DEVELOPMENT,
    /**
     * PRODUCTION
     * used for the prod version of an app (has a separate certificate provided
     *   by Apple)
     */
    PRODUCTION
  }
  
  private final ApnsClient<SimpleApnsPushNotification> apnsClient;
  private final String topic;
  
  /**
   * NOTE: in case values in apple_notification_properties.prop are set and
   *         should be used, then use the singleton method i () instead
   * Constructor AppleNotificationSender
   * @param certificatePath (relative path to the certificate file.p12
   *          provided by Apple)
   * @param certificatePassword
   * @param clientType
   * @param topic: is the value of UID in the subject your APNs certificate.
   *                 It begin's with com.apple.mgmt.
   * @return new AppleNotificationSender Object
   * @throws Exception
   */
  public AppleNotificationSender (
    String certificatePath,
    String certificatePassword,
    ClientType clientType,
    String topic) throws Exception {
    
    this.apnsClient =
      new ApnsClient<>(new File(certificatePath), certificatePassword);
      
    if (clientType == ClientType.DEVELOPMENT) {

      final Future<Void> connectFuture =
        this.apnsClient.connect(ApnsClient.DEVELOPMENT_APNS_HOST);
      
      connectFuture.await();
    } else if (clientType == ClientType.PRODUCTION) {

      final Future<Void> connectFuture =
        this.apnsClient.connect(ApnsClient.PRODUCTION_APNS_HOST);
      
      connectFuture.await();
    } else {
      
      throw new CodeException(
        "invalid ClientType value ["
        + clientType.toString()
        + "], set it to one of the valid enum values",
        ExceptionClass.TYPE);
    }
    
    this.topic = topic;
  }
  
  private static AppleNotificationSender instance;
  /**
   * i
   * @return static singleton instance of AppleNotificationSender defined
   *           using the values in apple_notification_properties.prop
   * @throws Exception
   */
  public static AppleNotificationSender i () throws Exception {
    
    if (instance == null) {
      
      if (AppleNotificationProperties.i().isDefined() == false) {
        
        throw new CodeException(
          "values in ["
          + AppleNotificationProperties.i().getName()
          + ".prop] aren't defined, either define them or use the normal "
          + "constructor instead",
          ExceptionClass.PROPERTIES);
      }
      
      instance =
        new AppleNotificationSender(
          AppleNotificationProperties.i().getStringPropterty(
            AppleNotificationProperties.kCertificatePath),
          AppleNotificationProperties.i().getStringPropterty(
            AppleNotificationProperties.kCertificatePassword),
          ClientType.valueOf(
            AppleNotificationProperties.i().getStringPropterty(
              AppleNotificationProperties.kClientType).toUpperCase() ),
          AppleNotificationProperties.i().getStringPropterty(
            AppleNotificationProperties.kTopic) );
    }
    
    return instance;
  }
  
  /**
   * NotificationStatus is used to tell the status of sending an Apple's
   *   push notification
   */
  public enum NotificationStatus {
    
    /**
     * ACCEPTED
     * the push notification was successfully accepted by APNS
     */
    ACCEPTED,
    /**
     * INVALID_TOKEN
     * APNS rejected the notification because of an invalid device token
     */
    INVALID_TOKEN,
    /**
     * REJECTED
     * APNS rejected the notification for a reason other than an invalid
     *   device token
     */
    REJECTED,
    /**
     * RETRY
     * APNS Client was disconnected (reconnection done) - try again
     */
    RETRY,
    /**
     * FAILED
     * Last resort, stack trace of the exception that resulted to failure
     *   is attached to the return
     */
    FAILED
  }
  
  /**
   * sendNotification
   * @param appleNotification Object to be sent
   * @return a pair of NotificationStatus and an explanation String
   * @throws Exception
   */
  public Pair<NotificationStatus, String> sendNotification (
    AppleNotification appleNotification) throws Exception {
    
    try {

      // make token
      String token =
        TokenUtil.sanitizeTokenString(appleNotification.getDeviceToken() );
      
      // build the notification (badge, alert, sound, etc ...)
      
      ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();
      
      if (appleNotification.isValidBadgeNumber() == true) {
        
        payloadBuilder.setBadgeNumber(appleNotification.getBadgeNumber() );
      }
      
      if (appleNotification.getSound() != null) {
        
        payloadBuilder.setSoundFileName(appleNotification.getSound() );
      }
      
      payloadBuilder.setAlertBody(appleNotification.getAlertBody() );
      
      if (appleNotification.hasCustomProperties() == true) {
      
        HashMap<String, String> customProperties =
          appleNotification.getCustomProperties();
        
        for (String key : customProperties.keySet() ) {
          
          payloadBuilder.addCustomProperty(key, customProperties.get(key) );
        }
      }
      
      String payload = payloadBuilder.buildWithDefaultMaximumLength();
      
      SimpleApnsPushNotification pushNotification =
        new SimpleApnsPushNotification(token, this.topic, payload);
        
      // send the notification
      Future<PushNotificationResponse<
        SimpleApnsPushNotification> > sendNotificationFuture =
          this.apnsClient.sendNotification(pushNotification);
      
      // check for success, failure, etc ...
      try {
        
        PushNotificationResponse<
          SimpleApnsPushNotification> pushNotificationResponse =
            sendNotificationFuture.get();

        if (pushNotificationResponse.isAccepted() ) {
          
          return new Pair<NotificationStatus, String>(
            NotificationStatus.ACCEPTED,
            "Push notification accepted by APNs gateway");
        } else {

          if (pushNotificationResponse.getTokenInvalidationTimestamp() !=
              null) {
            
            return new Pair<NotificationStatus, String>(
              NotificationStatus.INVALID_TOKEN,
              "Token is invalid as of ["
              + pushNotificationResponse.getTokenInvalidationTimestamp()
              + "]");
          }
          
          return new Pair<NotificationStatus, String>(
            NotificationStatus.REJECTED,
            "Push notification rejected by APNs gateway");
        }
      } catch (Exception e) {

        if (e.getCause() instanceof ClientNotConnectedException) {
          
          // reconnect
          apnsClient.getReconnectionFuture().await();
          
          return new Pair<NotificationStatus, String>(
            NotificationStatus.RETRY,
            "APNS Client was disconnected, try again");
        }
        
        return new Pair<NotificationStatus, String>(
          NotificationStatus.FAILED,
          "Failed to send push notification, cause ["
          + VangavException.getExceptionStackTrace(e)
          + "]");
      }
    } catch (Exception e) {

      if (e.getCause() instanceof ClientNotConnectedException) {
        
        // reconnect
        apnsClient.getReconnectionFuture().await();
        
        return new Pair<NotificationStatus, String>(
          NotificationStatus.RETRY,
          "APNS Client was disconnected, try again");
      }
      
      return new Pair<NotificationStatus, String>(
        NotificationStatus.FAILED,
        "Failed to send push notification, cause ["
        + VangavException.getExceptionStackTrace(e)
        + "]");
    }
  }
  
  /**
   * quit
   * call this method after using this instance is done to disconnect the
   *   APNS client
   * @throws Exception
   */
  public void quit () throws Exception {

    Future<Void> disconnectFuture = apnsClient.disconnect();
    disconnectFuture.await();
  }
  
  @Override
  public String toString () {
    
    return
      "AppleNotificationSender topic ["
      + this.topic
      + "]";
  }
}
