# dispatcher

### Primary Usage Example

+ In any of the request processing methods (e.g.: [processRequest](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L96)) in a Vangav Backend generated RESTful service, one can use the dispatcher as follows.

+ At any point during request-processing (before-or-after response) you can enqueue a [DispatchMessage](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/DispatchMessage.java) into the request's [Dispatcher](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L210):
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
  + Add as many dispatch messages as needed to the request's dispatcher and all these messages gets automatically dispatched
  
+ All dispatchable types inherit from the abstract class [DispatchMessage](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/DispatchMessage.java#L82).
