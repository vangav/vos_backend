# Using [Dispatcher](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java) - [Worker(s)](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/worker/ParentWorkerHandler.java)

### The Purpose Of Using Workers

+ If you are unfamiliar with workers, think of it as a private secondary-backend service accessible only by the primary-backend service.
+ Generally, there are two types of operations, blocking and non-blocking. In this context blocking is any operation that doesn't finish rapidly (e.g.: network operations, disk operations, ...) and non-blocking is any operation that finishes rapidly (e.g.: in-memory calculations).
+ Vangav Backend has the following built-in [Dispatchable Operations](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/DispatchMessage.java#L81)
  + [QueryDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/keyspaces/dispatch_message/QueryDispatchable.java): cassandra queries (insert, update and delete).
  + [AndroidNotificationDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/android/dispatch_message/AndroidNotificationDispatchable.java): android push notifications.
  + [AppleNotificationDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/apple/dispatch_message/AppleNotificationDispatchable.java): apple push notifications.
  + [JavaEmailDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/java_email/dispatch_message/JavaEmailDispatchable.java): JAVA emails.
  + [MailGunEmailDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/mail_gun_email/dispatch_message/MailGunEmailDispatchable.java): MailGun emails.
  + [TwilioSmsDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/dispatch_message/TwilioSmsDispatchable.java): Twilio SMSs.
  + [TwilioMmsDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/dispatch_message/TwilioMmsDispatchable.java): Twilio MMSs.
+ In most services (e.g.: Facebook), there are two sets of blocking operations: real-time-needed and not-real-time-needed. Let's say one posts a status update to Facebook:
  + a real-time-needed blocking operation would be to write the status update in the database before returning the request's response.
  + not-real-time-needed blocking operations would be to write logs, update analytics, send push notifications to friends tagged in the status update, ...
+ Separating the primary-backend from the worker (secondary-backend) allows:
  + Starting more primary-backend instances than worker instances (i.e.: allocating more processing power to primary-backend instances).
  + Allocating more resources (e.g.: memory) to the primary-backend instances.
+ As a result primary-backend instances stay as light as possible and ensures shortest request-to-response time which is essential for a great user-experience.

### How To

1. When generating a new service using Vangav Backend (e.g.: [vos_calculate_sum](https://github.com/vangav/vos_backend#generate-a-new-service)), the last step says `Generate worker [vos_calculate_sum_worker] for new project [vos_calculate_sum] ?: [Y/N]`. Enter **`Y`** to generate a worker service. Vangav Backend workers has built-in support for Cassandra operations and push notifications:
  + Cassandra operations are added to the generated worker **if the new service's config had one of more keyspace_name.keyspace config files**.
  + Push notifications are added to the generated worker **if the new service's config has [notifications](https://github.com/vangav/vos_calculate_sum/blob/master/generator_config/controllers.json#L10) set to `true` in the controllers.json config file**.
  + Manually add Twilio/Email by adding their properties files ([java_email_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/java_email_properties.prop), [mail_gun_email_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/mail_gun_email_properties.prop), [twilio_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/twilio_properties.prop)) and their jars from [lib](https://github.com/vangav/vos_backend/tree/master/lib).
2. In the primary-backend-instance, set [`workers_topology`](https://github.com/vangav/vos_backend/blob/master/prop/dispatcher_properties.prop#L53) to the IP(s)/Port(s) of the worker(s).
3. At any point during request-processing (before-or-after response) you can enqueue a [DispatchMessage](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/DispatchMessage.java) into the request's [Dispatcher](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L210):
  + Adding a [QueryDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/keyspaces/dispatch_message/QueryDispatchable.java) example (vos_geo_server [UpdateCounterValue](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L221)):
  ```java
    request.getDispatcher().addDispatchMessage(
      Continents.i().getQueryDispatchableUpdateCounterValue(
        "Europe") );
  ```
  + Adding an [AppleNotificationDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/apple/dispatch_message/AppleNotificationDispatchable.java) exmple:
  ```java
    request.getDispatcher().addDispatchMessage(
      new AppleNotificationDispatchable(appleNotification) );
  ```
  + Add as many dispatch messages as needed to the request's dispatcher and all these messages gets automatically dispatched to the worker(s) at the end of the request's processing.
4. Make sure to start the worker service (using its `_run.sh` script) before starting the primary service.
5. Yes, you don't need to write a single line of code in the worker - it just works :)). Just double check that every thing is correct in its properties files.
