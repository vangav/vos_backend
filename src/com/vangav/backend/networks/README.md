
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

## [java email](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/email/java_email)

+ an email client that sends emails using [JavaMail](http://www.oracle.com/technetwork/java/javamail/index.html)

### structure

| class | explanation |
| ----- | ----------- |
| [JavaEmail](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/java_email/JavaEmail.java) | represents an email (from, to, cc, subject, ssl type, ...) |
| [JavaEmailProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/java_email/JavaEmailProperties.java) | maps [java_email_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/java_email_properties.prop) properties file defining [smtp](https://en.wikipedia.org/wiki/Simple_Mail_Transfer_Protocol) values (host, port, ...) |
| [JavaEmailSenderInl](JavaEmailSenderInl) | is an inline class that sends [JavaEmail](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/java_email/JavaEmail.java) objects synchronously |
| [JavaEmailDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/java_email/dispatch_message/JavaEmailDispatchable.java) | represents a dispatchable version of [JavaEmail](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/java_email/JavaEmail.java) |

### usage template

```java
  // init an email
  JavaEmail javaEmail =
    new JavaEmail(
      "Lisa Sender",
      "lisa_sender@example.com",
      "receiver@example.com",
      "This is a usage template subject text.",
      "This is a usage template body text.",
      SslType.WITH_SSL);
    
  // option 1 - send it directly
  JavaEmailSenderInl.sendEmail(javaEmail);

  // option 2 - enqueue it in the dispatcher to be executed on the worker service side
  
  JavaEmailDispatchable javaEmailDispatchable =
    new JavaEmailDispatchable(javaEmail);

  request.getDispatcher().addDispatchMessage(javaEmailDispatchable);
```

## [mail gun email](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/email/mail_gun_email)

+ an email client that sends emails using [mailgun](http://www.mailgun.com/)

### structure

| class | explanation |
| ----- | ----------- |
| [MailGunEmail](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/mail_gun_email/MailGunEmail.java) | represents an email (from, to, cc, subject, ...) |
| [MailGunEmailProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/mail_gun_email/MailGunEmailProperties.java) | maps [mail_gun_email_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/mail_gun_email_properties.prop) properties file defining api key and mailgun domain name |
| [MailGunEmailSenderInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/mail_gun_email/MailGunEmailSenderInl.java) | is an inline class that sends [MailGunEmail](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/mail_gun_email/MailGunEmail.java) objects synchronously |
| [MailGunEmailDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/mail_gun_email/dispatch_message/MailGunEmailDispatchable.java) | represents a dispatchable version of [MailGunEmail](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/mail_gun_email/MailGunEmail.java) |

### usage template

```java
  MailGunEmail mailGunEmail =
    new MailGunEmail (
      "Lisa Sender",
      "lisa_sender",
      "This is a usage template subject text.",
      "This is a usage template body text.",
      "receiver_1@example.com",
      "receiver_2@example.com");
    
  // option 1 - send it directly
  MailGunEmailSenderInl.sendEmail(mailGunEmail);

  // option 2 - enqueue it in the dispatcher to be executed on the worker side

  MailGunEmailDispatchable mailGunEmailDispatchable =
    new MailGunEmailDispatchable(mailGunEmail);

  request.getDispatcher().addDispatchMessage(mailGunEmailDispatchable);
```

## [twilio](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/twilio)

+ a client that sends sms and mms using [twilio](https://www.twilio.com/)

### structure

| class | explanation |
| ----- | ----------- |
| [TwilioSms](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/TwilioSms.java) | represents an sms (from number, to number and message) |
| [TwilioMms](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/TwilioMms.java) | represents an mms (from number, to number, message and media url) |
| [TwilioProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/TwilioProperties.java) | maps [twilio_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/twilio_properties.prop) properties file defining account id, username and password |
| [TwilioSender](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/TwilioSender.java) | handles sending [TwilioSms](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/TwilioSms.java) and [TwilioMms](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/TwilioMms.java) objects synchronously and asynchronously |
| [TwilioSmsDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/dispatch_message/TwilioSmsDispatchable.java) | represents a dispatchable version of [TwilioSms](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/TwilioSms.java) |
| [TwilioMmsDispatchable](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/dispatch_message/TwilioMmsDispatchable.java) | represents a dispatchable version of [TwilioMms](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/twilio/TwilioMms.java) |

### usage teamplate

```java
  // SMS example

  // init SMS
  TwilioSms twilioSms =
    new TwilioSms (
      "12345", // to phone number
      "67890", // from phone number
      "This is a usage template message.");
    
  // option 1 - send synchronously
  String smsSid = TwilioSender.i().sendSync(twilioSms);

  // option 2 - send asynchronously
  ListenableFuture<Message> futureMessage =
    TwilioSender.i().sendAsync(twilioSms);

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
      "This is a usage template message.",
      "example.com/media.png"); // media url
    
  // option 1 - send synchronously
  String mmsSid = TwilioSender.i().sendSync(twilioMms);

  // option 2 - send asynchronously
  ListenableFuture<Message> futureMessage =
    TwilioSender.i().sendAsync(twilioMms);

  // option 3 - dispatch to worker

  TwilioMmsDispatchable twilioMmsDispatchable =
    new TwilioMmsDispatchable(twilioMms);
  
  request.getDispatcher().addDispatchMessage(twilioMmsDispatchable);
```

## [DownloadInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/DownloadInl.java)

+ inline static methods for downloading files by urls

### usage example

+ from [facebook api / Cover: `downloadCoverPhoto`](https://github.com/vangav/vos_backend/blob/a32cbc1c7159ddb04db11b33808e1c4f1bec5e74/src/com/vangav/backend/public_apis/facebook/json/fields/cover/Cover.java#L105)

```java
  /**
   * downloadCoverPhoto
   * @return the facebook user's cover photo as a string, and returns null or
   *           throws an exception on failure
   * @throws Exception
   */
  @JsonIgnore
  public String downloadCoverPhoto () throws Exception {
    
    return DownloadInl.downloadFileAsString(this.cover.source);
  }
```

# exercise

> when would one use a [rest job](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/jobs)?
