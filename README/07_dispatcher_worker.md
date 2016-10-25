# Using [Dispatcher](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java) - [Worker(s)](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/worker/ParentWorkerHandler.java)

### Purpose of using workers:

+ If you are unfamiliar with workers, think of it as a private secondary-backend service accessible only by the primary-backend service.
+ Generally, there are two types of operations, blocking and non-blocking. In this context blocking is any operation that doesn't finish rapidly (e.g.: network operations, disk operations, ...) and non-blocking is any operation that finishes rapidly (e.g.: in-memory calculations).
+ In Vangav Backend, two of the main blocking operations are Cassandra (insert, update and delete) and push notifications.
+ In most services (e.g.: Facebook), there are two sets of blocking operations: real-time-needed and not-real-time-needed. Let's say one posts a status update to Facebook:
  + a real-time-needed blocking operation would be to write the status update in the database before returning the request's response.
  + not-real-time-needed blocking operations would be to write logs, update analytics, send push notifications to friends tagged in the status update, ...
+ Separating the primary-backend from the worker (secondary-backend) allows:
  + Starting more primary-backend instances than worker instances (i.e.: allocating more processing power to primary-backend instances).
  + Allocating more resources (e.g.: memory) to the primary-backend instances.
+ As a result primary-backend instances stay as light as possible and ensures shortest request-to-response time which is essential for a great user-experience.