
> **why?** every software service needs instrumentation (logging, analytics, ...), one of the limitiations of some types of instrumentation is that they may cause a dramatic increase in service's execution time; this tutorial presents vangav backend's simple solution for this problem through isolating user-experience-related operations from instrumentation-related operations using worker services

# using [dispatcher](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java) - [worker(s)](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/worker/ParentWorkerHandler.java)

### what's a worker service and why use it

+ if you are unfamiliar with workers, think of it as a private secondary-backend service accessible only by the primary-backend service
+ generally, there are two types of operations, blocking and non-blocking; in this context blocking is any operation that doesn't finish rapidly (e.g.: network operations, disk operations, ...) and non-blocking is any operation that finishes rapidly (e.g.: in-memory calculations)
+ vangav backend has the following built-in [Dispatchable Operations](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/DispatchMessage.java#L81)

  | dispatchable | explanation |
  | ------------ | ----------- |
  | [QueryDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/keyspaces/dispatch_message/QueryDispatchable.java) | cassandra queries (insert, update and delete) *select queries aren't dispatchable* |
  | [AndroidNotificationDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/android/dispatch_message/AndroidNotificationDispatchable.java) | android push notifications |
  | [AppleNotificationDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/apple/dispatch_message/AppleNotificationDispatchable.java) | apple push notifications |
  | [JavaEmailDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/java_email/dispatch_message/JavaEmailDispatchable.java) | java's built-in emails |
  | [MailGunEmailDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/mail_gun_email/dispatch_message/MailGunEmailDispatchable.java) | [mailgun](https://www.mailgun.com/) emails - mailgun is one of the recommended email services by google cloud |
  | [TwilioSmsDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/dispatch_message/TwilioSmsDispatchable.java) | [twilio](https://www.twilio.com/) sms |
  | [TwilioMmsDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/dispatch_message/TwilioMmsDispatchable.java) | [twilio](https://www.twilio.com/) mms |

+ in most services (e.g.: facebook, instagram, snapchat, ...), there are two sets of blocking operations: real-time-needed and not-real-time-needed; let's say one posts a status update to Facebook:
  + a real-time-needed blocking operation would be to write the status update in the database before returning the request's response
  + not-real-time-needed blocking operations would be to write logs, update analytics, send push notifications to friends tagged in the status update, ...
+ separating the primary-backend from the worker (secondary-backend) enables allocating more resources (memory, instances, ...) to the primary-backend to ensure the fastest possible request-to-response time which leads to a great user experience

### how to use dispatcher-worker(s)

1. when generating a new service using vangav backend (e.g.: [calculate sum](https://github.com/vangav/vos_backend#generate-a-new-service)), the last step says `Generate worker [vos_calculate_sum_worker] for new project [vos_calculate_sum] ?: [Y/N]`; enter `Y` to generate a worker service; generated workers has built-in support for cassandra queries and push notifications:
    + cassandra queries are added to the generated worker *if the new service's config had one of more keyspace_name.keyspace config files*
    + push notifications are added to the generated worker, *if the new service's config has [notifications](https://github.com/vangav/vos_calculate_sum/blob/master/generator_config/controllers.json#L10) set to `true` in the controllers.json config file*
    + manually add twilio/email by adding their properties files ([java_email_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/java_email_properties.prop), [mail_gun_email_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/mail_gun_email_properties.prop), [twilio_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/twilio_properties.prop)) and their jars from [lib](https://github.com/vangav/vos_backend/tree/master/lib) to the generated worker service's `conf/prop` and `lib` directories respectively
2. in the primary-backend-instance, set [`workers_topology`](https://github.com/vangav/vos_backend/blob/master/prop/dispatcher_properties.prop#L53) to the ip(s)/port(s) of the worker(s)
3. at any point during request-processing (before-or-after response) you can enqueue a [DispatchMessage](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/DispatchMessage.java) into the request's [Dispatcher](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L210) as shown in the usage examples below; add as many dispatch messages as needed to the request's dispatcher and all these messages get automatically dispatched to the worker(s) at the end of the request's processing
4. make sure to start the worker service (using its `_run.sh` script) before starting the primary service
5. you don't need to write a single line of code in the worker - it just works :)); just double check that every thing is correct in its properties files

### usage examples

+ examples from [instagram: HandlerComment](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/comment/HandlerComment.java)

+ [dispatching a query](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/comment/HandlerComment.java#L234)

```java
  request.getDispatcher().addDispatchMessage(
    CountPerWeek.i().getQueryDispatchableIncrementCommentsReceivedCount(
      postOwnerUserId.toString()
      + Constants.kCassandraIdConcat
      + CalendarFormatterInl.concatCalendarFields(
        request.getStartCalendar(),
        Calendar.YEAR,
        Calendar.WEEK_OF_YEAR) ) );
```

+ [dispatching an apple push notification](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/comment/HandlerComment.java#L283)

```java
  request.getDispatcher().addDispatchMessage(
    new AppleNotificationDispatchable(
      new AppleNotificationBuilder(deviceToken)
        .alertBody(commenterName + " commented on your photo")
        .badgeNumber(1)
        .build() ) );
```

+ [dispatching an android push notification](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/comment/HandlerComment.java#L292)

```java
  request.getDispatcher().addDispatchMessage(
    new AndroidNotificationDispatchable(
      new AndroidNotification(
        new Message.Builder()
          .collapseKey(commenterName + " commented on your photo")
          .build(),
        deviceToken) ) );
```

# exercise
> when would you use a worker service?

## next tutorial -> [debugging](https://github.com/vangav/vos_backend/blob/master/README/08_debug.md)
> explains how to start and use the debugger
