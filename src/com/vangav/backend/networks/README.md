# networks

+ [DownloadInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/DownloadInl.java) is used to download files (e.g.: download a user's Facebook profile picture upon signup).

### [email/java_email](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/email/java_email)

+ An email client that sends emails using [JavaMail](http://www.oracle.com/technetwork/java/javamail/index.html).

| Class | Explanation |
| ----- | ----------- |
| [JavaEmail](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/java_email/JavaEmail.java) | Represents an email (from, to, cc, subject, ...). |
| [JavaEmailProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/java_email/JavaEmailProperties.java) | Maps [java_email_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/java_email_properties.prop) properties file. |
| [JavaEmailSenderInl](JavaEmailSenderInl) | Is an inline class that sends [JavaEmail](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/java_email/JavaEmail.java) Objects synchronously. |
| [JavaEmailDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/java_email/dispatch_message/JavaEmailDispatchable.java) | Represents a dispatchable version of [JavaEmail](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/java_email/JavaEmail.java). |

+ Usage example
```java
// init an email
JavaEmail javaEmail =
  new JavaEmail(
    "Lisa",
    "example@eample.com",
    "contact@vangav.com",
    "Hola",
    "This is a usage example body text.",
    SslType.WITH_SSL);
    
// option 1 - send it directly
JavaEmailSenderInl.sendEmail(javaEmail);

// option 2 - enqueue it in the dispatcher to be executed on the worker side
JavaEmailDispatchable javaEmailDispatchable =
  new JavaEmailDispatchable(javaEmail);

request.getDispatcher().addDispatchMessage(javaEmailDispatchable);
```
