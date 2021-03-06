
> **why?** [SequentialIds](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SequentialIds.java) and twitter's [SnowFlake](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SnowFlake.java) are useful whenever a system design requires globally sorted unique ids

> **at vangav?** at vangav most of the time we prefer using unsorted [`UUID`](http://docs.oracle.com/javase/7/docs/api/java/util/UUID.html#randomUUID()); whenever sorting is needed we use partitioning first (e.g.: per-user, per-day, ...) then we sort each partition by a secondary key (like a milli-second time stamp) using cassandra's built-in sorting - using `UUID` also ommits the need for a dedicated id-creation-service

> **vangav sorting example:** [sorting chat-messages](https://github.com/vangav/vos_whatsapp/blob/master/generator_config/wa_chat.keyspace#L93) in `whatsapp` template by message time where partition is done per-user

# ids

### [SequentialIds](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SequentialIds.java)

+ generates sequential ids based on unix time and a counter
+ generates up to 100 million unique ids per second
+ collision resistant: throws an exception on collision
+ generates unique ids per-instance (i.e.: can be distributed only in a master-slave way)
+ works until year 4857
+ uses java `long` (19 digits); 11 digits for unix time and 8 digits for counter (e.g.: 0146619060100000017 -> unix time = 1466190601 and counter = 00000017)
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

+ developed by twitter as a way to have distributed (master-master distribution) universal sequential id generation; *thanks twitter*
+ generated ids are 64-bit positive numbers (java `long`)
+ the id's 64-bits are divided as follows:
  + 41 bits for time stamp; works till 2080 with milli-second precision
  + 10 bits for server id (server's mac address); up to 1024 servers
  + 12 bits for sequence; sequence is used for ids generated within the same milli-second on the same server - allows for 4096 ids per-milli-second per-server
+ can be distributed on multiple servers with the following constrains:
  1. one instance per server; when using server's mac address as instance id, otherwise modify the way the instance's id is set by modifying [`getMachineId`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SnowFlake.java#L201) method to allow for multiple instances per server
  2. make sure all the servers have their time in sync using [ntp: network time protocol](https://en.wikipedia.org/wiki/Network_Time_Protocol)
+ collision resistant: in case of generating all possible 4096 ids within one millisecond on one server, it waits till the next milli-second to generate the next id

+ usage example

```java
  long id = SnowFlake.i().getNewId();
  
  System.out.println("snow flake id: " + id);
```

# exercise

> when is it nexcessary to use [SequentialIds](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SequentialIds.java) or [SnowFlake](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SnowFlake.java)?

> what's the difference between [SequentialIds](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SequentialIds.java) and [SnowFlake](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SnowFlake.java)?

# next tutorial -> [math](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/math)
> handles mathematical and geometric operations like: numeric, ranges, circles, line segments, straight lines, ...

# share

[![facebook share](https://www.prekindle.com/images/social/facebook.png)](https://www.facebook.com/sharer/sharer.php?u=https%3A//github.com/vangav/vos_backend)  [![twitter share](http://www.howickbaptist.org.nz/wordpress/media/twitter-64-black.png)](https://twitter.com/home?status=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster%20%7C%20https%3A//github.com/vangav/vos_backend)  [![pinterest share](http://d7ab823tjbf2qywyt3grgq63.wpengine.netdna-cdn.com/wp-content/themes/velominati/images/share_icons/pinterest-black.png)](https://pinterest.com/pin/create/button/?url=https%3A//github.com/vangav/vos_backend&media=https%3A//scontent-mad1-1.xx.fbcdn.net/v/t31.0-8/20645143_1969408006608176_5289565717021239224_o.png?oh=acf20113a3673409d238924cfec648d2%26oe=5A3414B5&description=)  [![google plus share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/google.png)](https://plus.google.com/share?url=https%3A//github.com/vangav/vos_backend)  [![linkedin share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/linkedin.png)](https://www.linkedin.com/shareArticle?mini=true&url=https%3A//github.com/vangav/vos_backend&title=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster&summary=&source=)

# free consulting

[![vangav's consultant](http://www.footballhighlights247.com/images/mobile-share/fb-messenger-64x64.png)](https://www.facebook.com/mustapha.abdallah)
