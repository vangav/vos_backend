# push_notifications

### [android](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications/android)

+ This package is responsible for sending push notifications to Android devices.
+ Usage example
```java
// init Android push notification
AndroidNotification androidNotification =
  new AndroidNotification(
    message, // message to be sent, including the device's registration id
    to); // registration token, notification key, or topic where the message will be sent
    
// option 1 - send it synchronously
AndroidNotificationSender.i().sendNotification(androidNotification);

// option 2 - dispatch it to a worker instance to execute it

AndroidNotificationDispatchable androidNotificationDispatchable =
  new AndroidNotificationDispatchable(androidNotification);
  
request.getDispatcher().addDispatchMessage(androidNotificationDispatchable);
```

| Class | Explanation |
| ----- | ----------- |
| [AndroidNotification](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/android/AndroidNotification.java) | Represents an Android push notification (to, alert, sound, etc ...) |
| [AndroidNotificationSender](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/android/AndroidNotificationSender.java) | Handles sending Android push notification through GCM (Google Cloud Messaging). |
| [AndroidNotificationProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/android/AndroidNotificationProperties.java) | Maps [android_notification_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/android_notification_properties.prop) properties file. |
| [AndroidNotificationDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/android/dispatch_message/AndroidNotificationDispatchable.java) | Is the dispatchable version of [AndroidNotification](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/android/AndroidNotification.java). |



