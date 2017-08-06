
> **why?** using threads imporves a service's performance, but without regulating them they can degrade performance and potentically cause a service to crash; so vangav backend offers:
> different thread pools for different types of operations with dynamic sizing (based on server's specs) to maintain the highest possible performance
> different types of threads to serve their relevant operations

# thread pool

## structure

| pkg/class | explanation |
| ----- | ----------- |
| [ThreadPool](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/ThreadPool.java) | regulates the following operations service-wide (in-memory runnables, cassandra, dispatcher and rest client operations) |
| [ThreadPoolProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/ThreadPoolProperties.java) | maps [thread_pool_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/thread_pool_properties.prop) properties file |
| [LatchThread](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/LatchThread.java) | logs thread's processing success/exceptions/... then performs a count down on the assigned [count down latch](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CountDownLatch.html); useful for operations involving running multiple threads then waiting till all of them finish execution |
| [periodic jobs](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/thread_pool/periodic_jobs) | a periodic job runs periodically (e.g.: every minute, day, ...) for a specific time (e.g.: for a year) or for forever; offers logging, post-process, load-distribution, sync/async, ... - periodic jobs are explained in detail later in this tutorial |

## [ThreadPool](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/ThreadPool.java)

+ vangav backend service's framework handles using [ThreadPool](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/ThreadPool.java) so that user's don't have to worry about it; here are some usage examples just in case

### usage examples

+ using [`executeInCassandraPool`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/ThreadPool.java#L192) in [Cassandra: `execute`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/Cassandra.java#L550)

```java
  ResultSetFuture result = this.session.executeAsync(query);
  
  ThreadPool.i().<ResultSet>executeInCassandraPool(result);
  
  return
    result.getUninterruptibly(
      kDefaultTimeout,
      kDefaultTimeunit);
```

+ using [`executeInDispatcherPool`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/ThreadPool.java#L216) in [Dispatcher: `dispatchMessages`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java#L212)

```java
  ThreadPool.i().executeInDispatcherPool(
    new DispatcherRunnable(
      new DispatchMessages(this.dispatchMessages).toJsonString() ) );
```

+ using [`executeInRestClientPool`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/ThreadPool.java#L228) in [JobsExecutorInl: `executeJobsSync`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/jobs/JobsExecutorInl.java#L126)

```java
  CountDownLatch countDownLatch =
    new CountDownLatch(jobs.length);

  RestAsync currRestAsync;
  RestResponseJsonGroup restResponseJsonGroup =
    new RestResponseJsonGroup();

  for (Job job : jobs) {

    currRestAsync =
      new RestAsync(
        countDownLatch,
        job.getRequest(),
        restResponseJsonGroup);

    ThreadPool.i().executeInRestClientPool(currRestAsync);
  }

  countDownLatch.await();
```

+ using [`executeInRunnablePool`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/ThreadPool.java#L180) in [ParentPlayHandler: `handleRequest`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L392)

```java
  ThreadPool.i().executeInRunnablePool(
    new AfterResponseRunnable(
      this,
      afterProcessingRequest) );
```

## [LatchThread](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/LatchThread.java)

+ [RestAsync](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/rest_client/RestAsync.java) is a great example for utilizing `latch threads`; here's a usage example in [JobsExecutorInl: `executeJobsSync`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/jobs/JobsExecutorInl.java#L113)

```java
  CountDownLatch countDownLatch =
    new CountDownLatch(jobs.length);

  RestAsync currRestAsync;
  RestResponseJsonGroup restResponseJsonGroup =
    new RestResponseJsonGroup();

  for (Job job : jobs) {

    currRestAsync =
      new RestAsync(
        countDownLatch,
        job.getRequest(),
        restResponseJsonGroup);

    ThreadPool.i().executeInRestClientPool(currRestAsync);
  }

  countDownLatch.await();
```

## [periodic jobs](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/thread_pool/periodic_jobs)

+ periodic jobs are useful for various applications like:
  + sending weekly newsletters to a service's members
  + crawling a third-party service (e.g.: crawl cars/parking-spots from car2go, uber, drive now, ...)
  + monitoring a service's health and notifying system admins on service failure(s)
  + ...

### structure

| class | explanation |
| ----- | ----------- |
| [PeriodicJob](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/periodic_jobs/PeriodicJob.java) | is the parent class for all `periodic jobs`, handles get a job's next cycle and offers the api for fetching a job's status, logs, ... |
| [CycleLog](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/periodic_jobs/CycleLog.java) | handles setting/getting each periodic job cycle's logs (info, status, errors, ...); child classes get each cycle's `CycleLog` object in their `process` and `postProcess` methods |
| [CycleTicker](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/periodic_jobs/CycleTicker.java) | optionally used during a initialization of a `PeriodicJob` offering: defining a load for a job (e.g.: 25%, 50%, 10%, ...) for load distribution between multiple jobs per-task, starting a job in the past/future and defining a periodic job's life-time (e.g.: run for a year then self-terminate) |
| [PeriodicJobRunner](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/periodic_jobs/PeriodicJobRunner.java) | handles processing a single `PeriodicJob`; keeps executing cycles at the defined time forever or till an optionally defined life-time for the periodic job; *this a protected class for use by the `PeriodicJobsManager`, you won't need to directly use it* |
| [PeriodicJobsManager](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/periodic_jobs/PeriodicJobsManager.java) | is the class where `PeriodicJob` objects are registered to start processing |

### usage example from [instagram jobs](https://github.com/vangav/vos_instagram_jobs)

+ jobs are initialized in [Global](https://github.com/vangav/vos_instagram_jobs/blob/master/app/Global.java#L145); `posts rank` and `users rank` are initialized four times since each job handles 25% of the load

```java
  // start periodic jobs
        
  PeriodicJobsManager.i().registerNewPeriodicJob(new PostsRank() );
  // ...

  PeriodicJobsManager.i().registerNewPeriodicJob(new RestJobs() );

  PeriodicJobsManager.i().registerNewPeriodicJob(new UsersRank() );
  // ...
```

+ [RestJobs](https://github.com/vangav/vos_instagram_jobs/blob/master/app/com/vangav/vos_instagram_jobs/periodic_jobs/rest_jobs/RestJobs.java#L78) is one of the `PeriodicJob` classes

```java
  /**
   * RestJobs this job crawls hourly unfinished jobs
   */
  public class RestJobs extends PeriodicJob<RestJobs> {
    //...
  }
```

+ [constructing](https://github.com/vangav/vos_instagram_jobs/blob/master/app/com/vangav/vos_instagram_jobs/periodic_jobs/rest_jobs/RestJobs.java#L87) the `PeriodicJob`

```java
  /**
   * Constructor - RestJobs
   * @return new RestJobs Object
   * @throws Exception
   */
  public RestJobs () throws Exception {

    super(
      // job's name
      "rest_jobs",
      // start next cycle "on time" even if the previous one is still running
      PeriodicJob.Type.ASYNC,
      // start from the exact past hour (e.g.: 14:00:00)
      RoundedOffCalendarInl.getRoundedCalendar(
        RoundingType.PAST,
        RoundingFactor.HOUR_OF_DAY),
      // start a new cycle every 1 hour
      new Period(
        1.0,
        TimeUnitType.HOUR) );
  }
```

+ [getting data](https://github.com/vangav/vos_instagram_jobs/blob/master/app/com/vangav/vos_instagram_jobs/periodic_jobs/rest_jobs/RestJobs.java#L110) from the `CycleLog`

```java
  // get cycle's planned start calendar
  Calendar plannedStartCalendar =
    CalendarAndDateOperationsInl.getCalendarFromUnixTime(
      cycleLog.getPlannedStartTime() );
```

# exercise

> what's the difference between threads, thread pools, latch threads and periodic jobs?

# next tutorial -> [vangav mighty](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/vangav_m)
> handles generating and binding [vangav mighty](http://vangav.com/) solutions

# share

[![facebook share](https://www.prekindle.com/images/social/facebook.png)](https://www.facebook.com/sharer/sharer.php?u=https%3A//github.com/vangav/vos_backend)  [![twitter share](http://www.howickbaptist.org.nz/wordpress/media/twitter-64-black.png)](https://twitter.com/home?status=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster%20%7C%20https%3A//github.com/vangav/vos_backend)  [![pinterest share](http://d7ab823tjbf2qywyt3grgq63.wpengine.netdna-cdn.com/wp-content/themes/velominati/images/share_icons/pinterest-black.png)](https://pinterest.com/pin/create/button/?url=https%3A//github.com/vangav/vos_backend&media=https%3A//scontent-mad1-1.xx.fbcdn.net/v/t31.0-8/20645143_1969408006608176_5289565717021239224_o.png?oh=acf20113a3673409d238924cfec648d2%26oe=5A3414B5&description=)  [![google plus share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/google.png)](https://plus.google.com/share?url=https%3A//github.com/vangav/vos_backend)  [![linkedin share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/linkedin.png)](https://www.linkedin.com/shareArticle?mini=true&url=https%3A//github.com/vangav/vos_backend&title=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster&summary=&source=)

# free consulting

[![vangav's consultant](http://www.footballhighlights247.com/images/mobile-share/fb-messenger-64x64.png)](https://www.facebook.com/mustapha.abdallah)
