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

### Structure

| Class | Explanation |
| ----- | ----------- |
| [DispatchMessage](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/DispatchMessage.java) | Is the parent class for all dispatchable messages which get dispatched to the worker service then executed there. |
| [DispatchMessages](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/DispatchMessages.java) | Used by the [Dispatcher](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java) to collect all [DispatchMessage](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/DispatchMessage.java) Objects together and send them once at the end of each request to the worker instead of sending them individually. |
| [Dispatcher](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java) | Is the main class to be used for enqueuing DispatchMessage Objects as shown in the usage example above. |
| [DispatcherProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/DispatcherProperties.java) | Holds the dispatcher's properties (e.g.: worker nodes topology). |

| Class | Explanation |
| ----- | ----------- |
| [ParentWorkerHandler](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/worker/ParentWorkerHandler.java) | Is used on the worker-service side to handle the execution of incoming dispatcher-messages. |
| [WorkerGeneratorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/worker/worker_generator/WorkerGeneratorInl.java) | Is used to generate a worker service for a new RESTful service getting generated using Vangav Backend. |
| [defaults](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/dispatcher/worker/worker_generator/defaults) | Is used by [WorkerGeneratorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/worker/worker_generator/WorkerGeneratorInl.java) when generating a new worker service. |
