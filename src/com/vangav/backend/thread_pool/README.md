
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
  CountDownLatch countDownLatch = new CountDownLatch(jobs.length);

  RestAsync currRestAsync;
  RestResponseJsonGroup restResponseJsonGroup = new RestResponseJsonGroup();

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
  CountDownLatch countDownLatch = new CountDownLatch(jobs.length);

  RestAsync currRestAsync;
  RestResponseJsonGroup restResponseJsonGroup = new RestResponseJsonGroup();

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

### structure

| class | explanation |
| ----- | ----------- |
| []() |  |
| []() |  |
| []() |  |
| []() |  |
| []() |  |

# exercise

> what's the difference between threads, thread pools, latch threads and periodic jobs?
