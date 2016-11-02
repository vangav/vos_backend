# exceptions

### Types Of Exceptions

+ [VangavException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java) is the parent class for exceptions. And also holds [ExceptionClass](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L86) types.
  + [BadRequestException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/BadRequestException.java) reflects HTTP_BAD_REQUEST (400) status code exceptions.
  + [CodeException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/CodeException.java) reflects HTTP_INTERNAL_SERVER_ERROR (500) status code exceptions.
  + [DefaultException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/DefaultException.java) also reflects HTTP_INTERNAL_SERVER_ERROR (500) status code exceptions and gets thrown when the exception's type can't be identified.
  
### [handlers](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/exceptions/handlers)

+ Has inline-classes for checking for common exceptions like checking methods' arguments as in [ArgumentsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/handlers/ArgumentsInl.java).
