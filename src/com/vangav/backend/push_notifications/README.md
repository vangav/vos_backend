
> **why?** a push notification is the only way for a backend service to deliver info/data to users and/or its client app; otherwise the backend service would have to wait till it receives a request from a user to deliver that info/data

# push notifications

## [apple](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications/apple)

+ enables sending push notifications to apple devices (iPhone, iPad, apple watch, ...)

### structure

| class | explanation |
| ----- | ----------- |
| [AppleNotification](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/apple/AppleNotification.java) | represents an apple push notification (to, alert, sound, ...) |
| [AppleNotificationSender](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/apple/AppleNotificationSender.java) | handles sending apple push notifications |
| [AppleNotificationProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/apple/AppleNotificationProperties.java) | maps [apple_notification_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/apple_notification_properties.prop) properties file |
| [AppleNotificationDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/apple/dispatch_message/AppleNotificationDispatchable.java) | is the dispatchable version of [AppleNotification](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/apple/AppleNotification.java); used to handle notification sending in the worker service |

### usage template

```java
  // init Apple push notification
  AppleNotification appleNotification =
    new AppleNotificationBuilder(deviceToken)
      .alertBody("usage template notification")
      .badgeNumber(1)
      .build();
    
  // option 1 - send it synchronously
  Pair<NotificationStatus, String> result =
    AppleNotificationSender.i().sendNotification(
      appleNotification);

  // option 2 - dispatch it to a worker instance to execute it

  AppleNotificationDispatchable appleNotificationDispatchable =
    new AppleNotificationDispatchable(appleNotification);
  
  request.getDispatcher().addDispatchMessage(appleNotificationDispatchable);
```

## [android](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications/android)

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
