# push_notifications

### [android](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications/android)

+ This sub-package is responsible for sending push notifications to Android devices through GCM (Google Cloud Messaging).
+ Usage example
```java
// init Android push notification
AndroidNotification androidNotification =
  new AndroidNotification(
    message, // message to be sent, including the device's registration id
    to); // registration token, notification key, or topic where the message will be sent
    
// option 1 - send it synchronously
Result result =
  AndroidNotificationSender.i().sendNotification(androidNotification);

// option 2 - dispatch it to a worker instance to execute it

AndroidNotificationDispatchable androidNotificationDispatchable =
  new AndroidNotificationDispatchable(androidNotification);
  
request.getDispatcher().addDispatchMessage(androidNotificationDispatchable);
```

| Class | Explanation |
| ----- | ----------- |
| [AndroidNotification](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/android/AndroidNotification.java) | Represents an Android push notification (to, alert, sound, etc ...). |
| [AndroidNotificationSender](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/android/AndroidNotificationSender.java) | Handles sending Android push notifications through GCM (Google Cloud Messaging). |
| [AndroidNotificationProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/android/AndroidNotificationProperties.java) | Maps [android_notification_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/android_notification_properties.prop) properties file. |
| [AndroidNotificationDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/android/dispatch_message/AndroidNotificationDispatchable.java) | Is the dispatchable version of [AndroidNotification](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/android/AndroidNotification.java). |

### [apple](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications/apple)

+ This sub-package is responsible for sending push notifications to Apple devices through APNS (Apple Push Notification Service).
+ Usage example
```java
// init Apple push notification
AppleNotification appleNotification =
  new AppleNotification(
    appleNotificationBuilder); // init through using AppleNotificationBuilder
    
// option 1 - send it synchronously
Pair<NotificationStatus, String> result =
  AppleNotificationSender.i().sendNotification(appleNotification);

// option 2 - dispatch it to a worker instance to execute it

AppleNotificationDispatchable appleNotificationDispatchable =
  new AppleNotificationDispatchable(appleNotification);
  
request.getDispatcher().addDispatchMessage(appleNotificationDispatchable);
```

| Class | Explanation |
| ----- | ----------- |
| [AppleNotification](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/apple/AppleNotification.java) | Represents an Apple push notification (to, alert, sound, etc ...). |
| [AppleNotificationSender](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/apple/AppleNotificationSender.java) | Handles sending Apple push notifications through APNS (Apple Push Notification Service). |
| [AppleNotificationProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/apple/AppleNotificationProperties.java) | Maps [apple_notification_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/apple_notification_properties.prop) properties file. |
| [AppleNotificationDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/apple/dispatch_message/AppleNotificationDispatchable.java) | Is the dispatchable version of [AppleNotification](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/apple/AppleNotification.java). |

