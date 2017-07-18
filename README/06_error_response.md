# [error response](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java)

+ normally a vangav backend service will return the expected response (`json`, `html` or `file`) as defined per-controller with a status code of 200 (`HTTP_OK`)
+ other than 200 (`HTTP_OK`), vangav backend may also return 400 (`HTTP_BAD_REQUEST`) and 500 (`HTTP_INTERNAL_SERVER_ERROR`)
  + 400 (`HTTP_BAD_REQUEST`) in case the problem came from the client's request; e.g.: expired access token, wrong login, missing/invalid mandatory request param, ...
  + 500 (`HTTP_INTERNAL_SERVER_ERROR`) in case of a problem within the service itself; e.g.: database unreachable, a utility method invoked using bad arguments, ...
+ returned error response (for both 400 (`BAD_REQUEST_EXCEPTION`) and 500 (`CODE_EXCEPTION`/`DEFAULT_EXCEPTION`)) has the following json structure; by default all elements are returned to the client, each element can be switched on/off from [response_error_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop)

  | element_name | element_type | explanation |
  | ------------ | ------------ | ----------- |
  | [`error_type`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L65) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L215) | [`BAD_REQUEST_EXCEPTION`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L70), [`CODE_EXCEPTION`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L71) or [`DEFAULT_EXCEPTION`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L72); `DEFAULT_EXCEPTION` indicates a non-vangav-exception, i.e. an exception coming from the user's implemented logic code that should have been handled properley |
  | [`error_code`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L69) | [`int`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L217) | optionally set this value while throwing an exception; *values up to `299` are reserved for use by vangav backend functionalities/utilities* |
  | [`error_sub_code`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L73) | [`int`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L219) | optionally set this value while throwing an exception |
  | [`error_message`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L77) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L221) | exception's message; explains what caused the error |
  | [`error_class`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L81) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L223) | one of [`ExceptionClass`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L86) enum values, as defined per-thrown [VangavException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java) |
  | [`error_stack_trace`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L85) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L225) | exception's stack trace |
  | [`error_trace_id`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L89) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L227) | binds the error's exception with the request causing it through the `request_id`; so the request causing the error can be pulled from the logs |
  
+ while implementing your vangav backend service's logic, throw a [BadRequestException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/BadRequestException.java) to return an error response with status code 400 (HTTP_BAD_REQUEST) and throw a [CodeException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/CodeException.java) to return an error response with status code 500 (HTTP_INTERNAL_SERVER_ERROR)
+ all request-processing (before and after response) exceptions are caught and handled in [ParentPlayHandler](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java) where request processing starts at [`handleRequestAsync`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L232) which gets called from controller's `Controller` class (e.g.: [`handlerReverseGeoCode.handleRequestAsync(request() );`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ControllerReverseGeoCode.java#L68))

+ an example for throwing a bad request exception can be found [here](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L1074)

```java
throw new BadRequestException(
  151,
  7,
  "Invalid param ["
    + name
    + "]",
  ExceptionClass.INVALID);
```

+ an example for throwing a code exception can be found [here](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java#L124)

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

## next tutorial -> [using dispatcher - worker(s)](https://github.com/vangav/vos_backend/blob/master/README/07_dispatcher_worker.md)
> shows why and how to us the dispatcher with one or more worker service(s)
