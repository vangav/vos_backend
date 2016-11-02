# exceptions

### Types Of Exceptions

+ [VangavException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java) is the parent class for exceptions. And also holds [ExceptionClass](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L86) types.
  + [BadRequestException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/BadRequestException.java) reflects HTTP_BAD_REQUEST (400) status code exceptions.
  + [CodeException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/CodeException.java) reflects HTTP_INTERNAL_SERVER_ERROR (500) status code exceptions.
  + [DefaultException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/DefaultException.java) also reflects HTTP_INTERNAL_SERVER_ERROR (500) status code exceptions and gets thrown when the exception's type can't be identified.
  
+ An example for throwing a bad request exception can be found [here](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L1074)

```java
throw new BadRequestException(
  151,
  7,
  "Invalid param ["
    + name
    + "]",
  ExceptionClass.INVALID);
```

+ An example for throwing a code exception can be found [here](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java#L124)

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

  
### [handlers](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/exceptions/handlers)

+ Has inline-classes for checking for common exceptions like checking methods' arguments as in [ArgumentsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/handlers/ArgumentsInl.java).

+ An example for using [ArgumentsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/handlers/ArgumentsInl.java) can be found [here](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/FacebookGraph.java#L162)

```java
ArgumentsInl.checkNotNull(
  "Facebook Graph API user's access token",
  accessToken,
  ExceptionType.CODE_EXCEPTION);
```
