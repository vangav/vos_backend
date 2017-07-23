
> **why?** at any point in the code where a problem is detected (e.g.: user's wrong request, server-side error, ...), throwing a vangav exception is the easiest way to handle these problems as they automatically result in a 400 bad request response or 500 internal error response (depending on the exception type); all exceptions also get logged in the request object for ease of using them at the end of the request processing (e.g.: write them to the database)

# exceptions

### vangav backend catching points

+ when implementing a new method in a generated vangav backend service make sure it throws exceptions so whenever a problem happens, vangav backend can deal with it; the following table lists where each type of exception is caught

| exception |
| --------- |
| [`processRequest`: BadRequestException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L306) |
| [`processRequest`: CodeException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L327) |
| [`processRequest`: Exception](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L348) |
| [failure to init `afterResponse` thread](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L402) |
| [failure to start `afterResponse` thread](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L396) |
| [all `afterResponse` steps](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L471) |

### types of exceptions

+ [VangavException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java) is the parent class for exceptions, and also holds [ExceptionClass](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L86) types

| exception type | explanation |
| -------------- | ----------- |
| [BadRequestException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/BadRequestException.java) | represents HTTP_BAD_REQUEST (400) status code exceptions |
| [CodeException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/CodeException.java) | represents HTTP_INTERNAL_SERVER_ERROR (500) status code exceptions |
| [DefaultException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/DefaultException.java) | also represents HTTP_INTERNAL_SERVER_ERROR (500) status code exceptions and gets thrown when the exception's type can't be identified |
  
+ example: throwing a bad request exception from [ParamValidatorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L1074)

```java
  throw new BadRequestException(
    151,
    7,
    "Invalid param ["
      + name
      + "]",
    ExceptionClass.INVALID);
```

+ example: throwing a bad request exception from [instagram/HandlerLikePost: `checkRequest`](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/like_post/HandlerLikePost.java#L142)

```java
  if (CheckersInl.postExists(postId) == false) {
      
    throw new BadRequestException(
      420,
      1,
      "Can't like a post that doesn't exist, post_id ["
        + requestLikePost.post_id
        + "]. Request issued by user_id ["
        + requestLikePost.user_id
        + "] from device_token ["
        + requestLikePost.device_token
        + "]",
      ExceptionClass.INVALID);
  }
```

+ example: throwing a code exception (internal server error) from [Dispatcher](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java#L124)

```java
  throw new CodeException(
    32,
    1,
    "propterty ["
      + DispatcherProperties.kWorkersTopology
      + "] isn't defined in properties file ["
      + DispatcherProperties.i().getName()
      + "]",
    ExceptionClass.PROPERTIES);
```

+ example: throwing a code exception (internal server error) that **gets logged but doesn't cause the request to fail** from [instagram/HandlerLikePost: `dispatchPushNotifications`](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/like_post/HandlerLikePost.java#L313)

```java
  // non-fatal exception
  request.addVangavException(
    new CodeException(
      420,
      3,
      "Invalid device type ["
        + deviceTokens.get(deviceToken)
        + "] for user_id ["
        + postOwnerUserId.toString()
        + "] and device_token ["
        + deviceToken
        + "]",
      ExceptionClass.INVALID));
```

+ all exception codes up to `299` are reserved for vangav backend, use codes `300` and up for your generated service's exceptions; [error codes](https://github.com/vangav/vos_backend/blob/master/README/10_error_codes.md) has a list of all the exceptions thrown by vangav backend
  
### [handlers](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/exceptions/handlers)

+ has inline-classes for checking for common exceptions like checking methods' arguments as in [ArgumentsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/handlers/ArgumentsInl.java)

+ example for using [ArgumentsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/handlers/ArgumentsInl.java) in [FacebookGraph](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/FacebookGraph.java#L162)

```java
  ArgumentsInl.checkNotNull(
    "Facebook Graph API user's access token",
    accessToken,
    ExceptionType.CODE_EXCEPTION);
```
