
> **why?** at any point in the code where a problem is detected (e.g.: user's wrong request, server-side error, ...), throwing a vangav exception is the easiest way to handle these problems as they automatically result in a 400 bad request response or 500 internal error response (depending on the exception type); all exceptions also get logged in the request object for ease of using them at the end of the request processing (e.g.: write them to the database)

# exceptions

### types of exceptions

+ [VangavException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java) is the parent class for exceptions, and also holds [ExceptionClass](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L86) types

| exception type | explanation |
| -------------- | ----------- |
| [BadRequestException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/BadRequestException.java) | represents HTTP_BAD_REQUEST (400) status code exceptions |
| [CodeException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/CodeException.java) | represents HTTP_INTERNAL_SERVER_ERROR (500) status code exceptions |
| [DefaultException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/DefaultException.java) | also represents HTTP_INTERNAL_SERVER_ERROR (500) status code exceptions and gets thrown when the exception's type can't be identified |

+ error response (for both 400 (`BAD_REQUEST_EXCEPTION`) and 500 (`CODE_EXCEPTION`/`DEFAULT_EXCEPTION`)) has the following json structure; by default all elements are returned to the client, each element can be switched on/off from [response_error_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop)

| element_name | element_type | explanation |
| ------------ | ------------ | ----------- |
| [`error_type`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L65) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L215) | [`BAD_REQUEST_EXCEPTION`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L70), [`CODE_EXCEPTION`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L71) or [`DEFAULT_EXCEPTION`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L72); `DEFAULT_EXCEPTION` indicates a non-vangav-exception, i.e. an exception coming from the user's implemented logic code that should have been handled properley |
| [`error_code`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L69) | [`int`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L217) | optionally set this value while throwing an exception; *values up to `299` are reserved for use by vangav backend functionalities/utilities* |
| [`error_sub_code`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L73) | [`int`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L219) | optionally set this value while throwing an exception |
| [`error_message`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L77) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L221) | exception's message; explains what caused the error |
| [`error_class`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L81) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L223) | one of [`ExceptionClass`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L86) enum values, as defined per-thrown [VangavException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java) |
| [`error_stack_trace`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L85) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L225) | exception's stack trace |
| [`error_trace_id`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L89) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L227) | binds the error's exception with the request causing it through the `request_id`; so the request causing the error can be pulled from the logs |
  
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
