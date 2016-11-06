# thread_pool

| Class | Explanation |
| ----- | ----------- |
| [LatchThread](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/LatchThread.java) | LatchThread is a normal Runnable class that also uses CountDownLatch so that it does a count-down on the CountDownLatch after it finishes processing. Sub-classes implements their own process() method that gets executed from the thread's run() method. LatchThread is useful when a specific number of blocking operations should be run in parallel where the CountDownLatch is used to wait until all the LatchThread objects finish execution. |
| [ThreadPool](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/ThreadPool.java) | Used by Vangav Backend Services. It has thread pools for runnables, cassandra, dispatcher and rest-client. |
| [ThreadPoolProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/thread_pool/ThreadPoolProperties.java) | Maps [thread_pool_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/thread_pool_properties.prop) properties file. |
