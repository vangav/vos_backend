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
    "example@example.com",
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

### [email/mail_gun_email](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/email/mail_gun_email)

+ An email client that sends emails using [mailgun](http://www.mailgun.com/).

| Class | Explanation |
| ----- | ----------- |
| [MailGunEmail](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/mail_gun_email/MailGunEmail.java) | Represents an email (from, to, cc, subject, ...). |
| [MailGunEmailProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/mail_gun_email/MailGunEmailProperties.java) | Maps [mail_gun_email_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/mail_gun_email_properties.prop) properties file. |
| [MailGunEmailSenderInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/mail_gun_email/MailGunEmailSenderInl.java) | Is an inline class that sends [MailGunEmail](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/mail_gun_email/MailGunEmail.java) Objects synchronously. |
| [MailGunEmailDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/mail_gun_email/dispatch_message/MailGunEmailDispatchable.java) | Represents a dispatchable version of [MailGunEmail](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/mail_gun_email/MailGunEmail.java). |

+ Usage example
```java
MailGunEmail mailGunEmail =
  new MailGunEmail (
    "John",
    "example",
    "Hola",
    "This is a usage example body text.",
    "contact@vangav.com");
    
// option 1 - send it directly
MailGunEmailSenderInl.sendEmail(mailGunEmail);

// option 2 - enqueue it in the dispatcher to be executed on the worker side
MailGunEmailDispatchable mailGunEmailDispatchable =
  new MailGunEmailDispatchable(mailGunEmail);

request.getDispatcher().addDispatchMessage(mailGunEmailDispatchable);
```

### [rest_client](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/rest_client)

+ Having a built-in REST client in a backend service simplifies operations like:
  + Communicating with public APIs (e.g.: Facebook Graph API).
  + Dividing a backend into smaller services (e.g.: one service response for authentication), then those services communicate with each other to serve a response for an incoming request.
  
| Class | Explanation |
| ----- | ----------- |
| [RestRequest](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestRequest.java) | Is the parent class for [RestRequestGetQuery](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestRequestGetQuery.java) and [RestRequestPostJson](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestRequestPostJson.java). This REST client uses default headers for GET and POST REST requests unless the [`addHeader`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestRequest.java#L88) method of this parent class is used to specify the headers to be used. |
| [RestRequestGetQuery](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestRequestGetQuery.java) | Represents a REST GET request. |
| [RestRequestPostJson](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestRequestPostJson.java) | Represents a REST POST request. |
| [RestResponseJson](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestResponseJson.java) | Represents a REST JSON response. |
| [RestResponseJsonGroup](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestResponseJsonGroup.java) | Represents a group of [RestResponseJson](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestResponseJson.java) Objects where each Object maps to an HTTP Status code. Useful when the response's JSON structure differs depending on the HTTP Status code (e.g.: HTTP_OK 200, HTTP_BAD_REQUEST 400, ...). |
| [RestSyncInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestSyncInl.java) | Is an inline class that handles sending GET/POST REST requests synchronously. And provides the ability to check the response's status, maps the response to a [RestResponseJson](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestResponseJson.java), [RestResponseJsonGroup](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestResponseJsonGroup.java), raw response String or write response's file (e.g.: when downloading files). |
| [RestAsync](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestAsync.java) |  |
| []() |  |

