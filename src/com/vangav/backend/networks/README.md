
> **why?** this package offers essential networks utilities like:
  > + sync/async rest client (e.g.: to communicate with public apis like facebook graph api, communicating with other backend services in a [service oriented architecture](https://en.wikipedia.org/wiki/Service-oriented_architecture)
  > + rest jobs provide the ability to serialize http requests then forward them to other services to handle them and/or store them in the database (e.g.: store a rest request in the database that triggers a happy birthday notification to a member every year)
  > + sending emails (e.g.: sending news letters or password reset emails to members)
  > + sending sms/mms (e.g.: sending verification code sms to a new member upon signing up with her/his phone number)
  > + downloading files from other services (e.g.: download a member's facebook profile picture)

# networks

## structure

| class/pkg | explanation |
| --------- | ----------- |
| [rest client](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/rest_client) | sync/async rest client (e.g.: to communicate with public apis like facebook graph api, communicating with other backend services in a [service oriented architecture](https://en.wikipedia.org/wiki/Service-oriented_architecture) |
| [jobs](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/jobs) | rest jobs provide the ability to serialize http requests then forward them to other services to handle them and/or store them in the database (e.g.: store a rest request in the database that triggers a happy birthday notification to a member every year) |
| [java email](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/email/java_email) | handles constructing and sending [java email](http://docs.oracle.com/javaee/6/api/javax/mail/Message.html); constructed emails are also [dispatchable](https://github.com/vangav/vos_backend/blob/master/README/07_dispatcher_worker.md) |
| [mail gun email](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/email/mail_gun_email) | handles constructing and sending [mail gun](https://www.mailgun.com/) emails; constructed emails are also [dispatchable](https://github.com/vangav/vos_backend/blob/master/README/07_dispatcher_worker.md) |
| [twilio](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/twilio) | handles constructing and sending [twilio](https://www.twilio.com/) sms and mms; constructed messages are also [dispatchable](https://github.com/vangav/vos_backend/blob/master/README/07_dispatcher_worker.md) |
| [DonwloadInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/DownloadInl.java) | handles downloading files from other services (e.g.: download a member's facebook profile picture) |

## [rest client](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/rest_client)

### structure

| class | explanation |
| ----- | ----------- |
| [RestRequest](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestRequest.java) | is the parent class for all http request types (`GET`, `POST`, ...) and provides the option to set the request's header |
| [RestRequestGetQuery](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestRequestGetQuery.java) | represents a `GET` http request, inherits from [RestRequest](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestRequest.java); provides setting single/array params and formats finished request |
| [RestRequestPostJson](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestRequestPostJson.java) | is the parent class representing `POST` http requests, inherits from [RestRequest](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestRequest.java) |
| [RestResponseJson](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestResponseJson.java) | is the parent class for all `json` http responses |
| [RestResponseJsonGroup](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestResponseJsonGroup.java) | represents a group of [RestResponseJson](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestResponseJson.java) responses where each response corresponds to an http status code (e.g.: `200`, `400`, `500`, ...) |
| [FutureResponse](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/FutureResponse.java) | holds the future reponse for async http requests |
| [RestSyncInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestSyncInl.java) | has inline static methods for handling http request calls of different types synchronously; also provides the ability to check a requet's status and extract its response (raw or formatted) |
| [RestAsync](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestAsync.java) | handles asynchronous http requests, provides the ability to check the request's status and extracts its response (raw or formatted) |

### usage example

+ in [FacebookGraph: `getFields`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/FacebookGraph.java#L762)

```java
  CountDownLatch countDownLatch = new CountDownLatch(1);
  
  // initialize request
  RestAsync currRestAsync =
    new RestAsync(
      countDownLatch,
      getRequestUrl,
      new RestResponseJsonGroup(
        field.getNewFieldInstance(), // for 200 success response
        new BadRequestResponse() ) );
        
  // execute request
  ThreadPool.i().executeInRestClientPool(currRestAsync);
  
  // sync mode example (wait for response)
  countDownLatch.await();
  
  // extract response
  
  if (currRestAsync.gotMatchingJsonResponse() == true) {
    
    System.out.println(
      "http status code: "
      + currRestAsync.getResponseStatusCode() );
    System.out.println(
      "json response: "
      + currRestAsync.getRestResponseJson().toString() );
  } else {
    
    System.out.println(
      "http status code: "
      + currRestAsync.getResponseStatusCode() );
    System.out.println(
      "raw response: "
      + currRestAsync.getRawResponseString() );
  }
```

## [jobs](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/jobs)

### structure

| class | explanation |
| ----- | ----------- |
| [Job](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/jobs/Job.java) | is basically a serializable `GET` http request with optional `job_id` and `job_time` for traceability  |
| [JobsExecutorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/jobs/JobsExecutorInl.java) | has inline static methods for executing [Job](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/jobs/Job.java) objects (i.e.: sending the `GET` http request) asynchronously or synchronously |

### usage example

+ the following example shows the multi-step process of:
1. create a job and store it in the database
2. deliver the job
3. receive the job, execute it and delete it from the database
4. iterate on failed jobs in the databse and retry them

+ here's the step by step using `instagram`, `instagram dispense` and `instagram jobs` services

1. create a job and store it in the database in [instagram / HandlerPostPhoto: `processRequest`](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/post_photo/HandlerPostPhoto.java#L143)

```java
  // create dispense job using request's uuid and request's time
    
  Job job =
    new Job(
      DispenseProperties.i().getStringPropterty(
        DispenseProperties.kPostPhotoToFollowers) );

  job.addParam("user_id", requestPostPhoto.getUserId().toString() );
  job.addParam("post_id", postId.toString() );
  job.addParam("photo_id", postId.toString() );
  job.addParam("post_time", "" + request.getStartTime() );
  job.addParam("job_id", request.getRequestId().toString() );

  // serialize job
  String jobSerialized =
    SerializationInl.serializeObject(job);

  ByteBuffer jobByteBuffer =
    EncodingInl.encodeStringIntoByteBuffer(jobSerialized);

  // insert into ig_jobs
  // all queries must succeed
  BatchStatement batchStatement = new BatchStatement(Type.LOGGED);

  // insert into ig_jobs.current_jobs
  batchStatement.add(
    CurrentJobs.i().getBoundStatementInsert(
      request.getRequestId(),
      request.getStartTime(),
      jobByteBuffer) );

  // insert into ig_jobs.hourly_current_jobs
  batchStatement.add(
    HourlyCurrentJobs.i().getBoundStatementInsert(
      CalendarFormatterInl.concatCalendarFields(
        request.getStartCalendar(),
        Calendar.YEAR,
        Calendar.MONTH,
        Calendar.DAY_OF_MONTH,
        Calendar.HOUR_OF_DAY),
      request.getStartTime(),
      request.getRequestId() ) );

  // execute batch statement
  Cassandra.i().executeSync(batchStatement);
```

2. deliver the job in [instagram / HandlerPostPhoto: `processRequest`](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/post_photo/HandlerPostPhoto.java#L143)

```java
  // pass job to dispense service
  try {

    // non-fatal on failure, jobs service will retry executing the job
    JobsExecutorInl.executeJobsAsync(job);
  } catch (BadRequestException | CodeException ve) {

    request.addVangavException(ve);
  } catch (Exception e) {

    request.addException(e);
  }
```

3. receive the job, execute it and delete it from the database in [instagram dispense / HandlerPostPhotoToFollowers: `afterProcessing`](https://github.com/vangav/vos_instagram_dispense/blob/master/app/com/vangav/vos_instagram_dispense/controllers/post_photo_to_followers/HandlerPostPhotoToFollowers.java#L141)

```java
  // finished processing successfully - delete job
    
  UUID jobId = UUID.fromString(requestPostPhotoToFollowers.job_id);

  // all queries must succeed
  BatchStatement batchStatement = new BatchStatement(Type.LOGGED);

  batchStatement.add(
    CurrentJobs.i().getBoundStatementDelete(jobId) );

  batchStatement.add(
    HourlyCurrentJobs.i().getBoundStatementDelete(
      CalendarFormatterInl.concatCalendarFields(
        CalendarAndDateOperationsInl.getCalendarFromUnixTime(
          requestPostPhotoToFollowers.post_time),
        Calendar.YEAR,
        Calendar.MONTH,
        Calendar.DAY_OF_MONTH,
        Calendar.HOUR_OF_DAY),
      requestPostPhotoToFollowers.post_time,
      jobId) );

  // execute batch statement
  Cassandra.i().executeSync(batchStatement);
```

4. iterate on failed jobs in the databse and retry them in [instagram jobs / RestJobs: `process`](https://github.com/vangav/vos_instagram_jobs/blob/master/app/com/vangav/vos_instagram_jobs/periodic_jobs/rest_jobs/RestJobs.java#L115)

```java
  // query all jobs within cycle's hour
  ResultSet resultSet =
    HourlyCurrentJobs.i().executeSyncSelect(
      CalendarFormatterInl.concatCalendarFields(
        plannedStartCalendar,
        Calendar.YEAR,
        Calendar.MONTH,
        Calendar.DAY_OF_MONTH,
        Calendar.HOUR_OF_DAY) );

  // to to fetch each job
  ResultSet currJobResultSet;

  String currSerializedJob;
  Job currJob;

  // retry executing every found job (failed to execute job)
  for (Row row : resultSet) {

    if (resultSet.getAvailableWithoutFetching() <=
        Constants.kCassandraPrefetchLimit &&
        resultSet.isFullyFetched() == false) {

      // this is asynchronous
      resultSet.fetchMoreResults();
    }

    // select job
    currJobResultSet =
      CurrentJobs.i().executeSyncSelect(
        row.getUUID(HourlyCurrentJobs.kJobIdColumnName) );

    // get serialized job
    currSerializedJob =
      EncodingInl.decodeStringFromByteBuffer(
        currJobResultSet.one().getBytes(
          CurrentJobs.kJobColumnName) );

    // deserialize
    currJob = SerializationInl.<Job>deserializeObject(currSerializedJob);

    // execute job (retry)
    JobsExecutorInl.executeJobsAsync(currJob);
  }
```
















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

### [jobs](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/jobs)

+ Job class is a simplified and serializable version of HTTP GET requests.
  + A main service can dispatch a job to a helper service.
  + Can be serialized and stored in the database for tracking and retrying in case of failure.
  + Can be stored in the database and get executed at a later point in time.
  + etc ...
+ JobExecutorInl executes jobs by issuing the jobs HTTP GET request asynchronously.

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
| [RestAsync](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestAsync.java) | Represents an asynchronous REST GET/POST requests with JSON/FILE responses. This class inherits from the Runnable [LatchThread](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/LatchThread.java) so that it gets executed in [`executeInRestClientPool (RestAsync restAsync)`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/ThreadPool.java#L228) as in [FacebookGraph](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/FacebookGraph.java#L800). |
| [FutureResponse](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/FutureResponse.java) | Used to hold future response of [RestAsync](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestAsync.java) requests. Here's one usage example from [FacebookGraph](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/FacebookGraph.java#L854). |

+ [Usage example](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/car2go/Car2GoApi.java#L325) from Car2GoApi.

### [twilio](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/twilio)

+ A client that sends SMSs and MMSs using [twilio](https://www.twilio.com/).

| Class | Explanation |
| ----- | ----------- |
| [TwilioSms](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/TwilioSms.java) | Represents an SMS (from number, to number and message). |
| [TwilioMms](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/TwilioMms.java) | Represents an MMS (from number, to number, message and media url). |
| [TwilioProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/TwilioProperties.java) | Maps [twilio_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/twilio_properties.prop) properties file. |
| [TwilioSender](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/TwilioSender.java) | Handles sending [TwilioSms](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/TwilioSms.java) and [TwilioMms](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/TwilioMms.java) Objects synchronously and asynchronously. |
| [TwilioSmsDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/dispatch_message/TwilioSmsDispatchable.java) | Represents a dispatchable version of [TwilioSms](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/TwilioSms.java). |
| [TwilioMmsDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/dispatch_message/TwilioMmsDispatchable.java) | Represents a dispatchable version of [TwilioMms](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/TwilioMms.java). |

+ Usage example
```java
// SMS example
// init SMS
TwilioSms twilioSms =
  new TwilioSms (
    "12345", // to phone number
    "67890", // from phone number
    "This is a usage example message.");
    
// option 1 - send synchronously
String smsSid = TwilioSender.i().sendSync(twilioSms);

// option 2 - send asynchronously
ListenableFuture<Message> futureMessage = TwilioSender.i().sendAsync(twilioSms);

// option 3 - dispatch to worker
TwilioSmsDispatchable twilioSmsDispatchable =
  new TwilioSmsDispatchable(twilioSms);
  
request.getDispatcher().addDispatchMessage(twilioSmsDispatchable);

// MMS example
// init MMS
TwilioMms twilioMms =
  new TwilioMms (
    "12345", // to phone number
    "67890", // from phone number
    "This is a usage example message.",
    "example.com/media.png"); // media url
    
// option 1 - send synchronously
String mmsSid = TwilioSender.i().sendSync(twilioMms);

// option 2 - send asynchronously
ListenableFuture<Message> futureMessage = TwilioSender.i().sendAsync(twilioMms);

// option 3 - dispatch to worker
TwilioMmsDispatchable twilioMmsDispatchable =
  new TwilioMmsDispatchable(twilioMms);
  
request.getDispatcher().addDispatchMessage(twilioMmsDispatchable);
```

