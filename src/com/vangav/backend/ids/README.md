
> **why?** [SequentialIds](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SequentialIds.java) and twitter's [SnowFlake](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SnowFlake.java) are useful whenever a system design requires globally sorted unique ids

> **at vangav?** at vangav most of the time we prefer using unsorted [`UUID`](http://docs.oracle.com/javase/7/docs/api/java/util/UUID.html#randomUUID()); whenever sorting is needed we use partitioning first (e.g.: per-user, per-day, ...) then we sort each partition by a secondary key (like a milli-second time stamp) using cassandra's built-in sorting - using `UUID` also ommits the need for a dedicated id-creation-service

# ids

### [SequentialIds](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SequentialIds.java)

+ generates sequential ids based on unix time and a counter
+ generates up to 100 million unique ids per second
+ collision resistant: throws an exception on collision
+ generates unique ids per-instance (i.e.: can be distributed only in a master-slave way)
+ works until year 4857
+ uses java `Long` (19 digits); 11 digits for unix time and 8 digits for counter (e.g.: 0146619060100000017 -> unix time = 1466190601 and counter = 17)
+ depends on system time, a change in the system time affects the generated ids and can potentially result in out-of-sequence ids for a period of time; in case of a master-slave distributed deployment, make sure all the servers have their time in sync using [ntp: network time protocol](https://en.wikipedia.org/wiki/Network_Time_Protocol)
+ usage example

```java

  long newIdLong;
  String newIdStr;
  
  // auto-retry on collision
  while (true) {
  
    try {
      
      newIdLong = SequentialIds.i().getNewIdAsLong();
      newIdStr = SequentialIds.i().getNewId();
      
      break;
    } catch (Exception e) {
    
      // a collision happened, retry
    }
  }

  System.out.println("long id: " + newIdLong);
  System.out.println("str id: " + newIdStr);
```

### [SnowFlake](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SnowFlake.java)

+ SnowFlake was developed by twitter as a way to have distributed universal sequential ID generation - thanks twitter.
+ SnowFlake is a way to generate universally unique sequential IDs represented by 64-bit positive numbers (JAVA long).
+ The ID's 64-bits are divided as follows:
  + 41 bits for time stamp (works well till 2080) (millisecond precision).
  + 10 bits for machine ID (machine's mac address) (up to 1024 instances).
  + - 12 bits for sequence (for IDs generated within the same time stamp).
+ Allows for 4096 IDs per millisecond per instance.
+ Can be distributed on multiple machines with the following constrains:
  + One instance per machine (using machine's mac address as instance ID, otherwise modify the way the instance's ID is set to allow for multiple instances per machine).
  + All machines running SnowFlake must use the same NTP time server so that the time stamp part in IDs stays in sync.
+ Collision resistant. In case of generating all possible 4096 IDs within one millisecond, it waits till the next millisecond to generate the next ID.
