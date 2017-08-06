
> **why [cassandra](cassandra.apache.org)?** the best nosql database for high performance, high availability, scalability and master-master replication; proven by apple's deployment with 75,000 nodes storing over 10 PB of data, netfllix's 2,500 nodes storing 420 TB with over 1,000,000,000,000 requests per day and more than 1500 other companies like github, instagram, ebay, ...

# cassandra

### structure

| class/pkg | explanation |
| ----- | ----------- |
| [Cassandra](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/Cassandra.java) | maintains session with one cassandra node and sends execution/preparing requests over that session |
| [CassandraNode](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/CassandraNode.java) | represents a cassandra-node as name/ip |
| [CassandraProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/CassandraProperties.java) | represents cassandra's properties (e.g.: retries, topology, ...), a reflection of [cassandra_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/cassandra_properties.prop) |
| [formatting](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/cassandra/formatting) | has utilities for formatting data in/out of cassandra (e.g.: formatting a date into a String) |
| [keyspaces](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/cassandra/keyspaces) | has the classes representing cassandra's tables, queries and dispatchable queries |
| [keyspaces_generator](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/cassandra/keyspaces_generator) | this sub-package is responsible for verifying Cassandra's config files then using them to generate java clients, cql scripts and phriction wiki files |

+ [Cassandra](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/Cassandra.java) provides the following methods

| method | explanation |
| ------ | ----------- |
| [executeAsync](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/Cassandra.java#L351) | exeutes one or more `Statement` objects and returns corresponding `ResultSetFuture` objects |
| [executeSync](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/Cassandra.java#L390) | executes one or more `Statement` objects and returns corresponding `ResultSet` objects; when this method gets more than one `Statement` it executes them async then wait at the end for all of them to utilize all available resources saving the total execution time |

### generated database client structure

> in this section [geo server / keyspaces](https://github.com/vangav/vos_geo_server/tree/master/app/com/vangav/vos_geo_server/cassandra_keyspaces) package is used as a reference; yet it's the same for all generated database clients

+ the generated package contains all the generated database clients for cassandra's tables as defined in the `.keyspace` config files (e.g.: [`gs_top.keyspace`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace)), where each keyspace is represented by a directory and each keyspace's table is represented by a class

+ each table's class like [Continents.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java) has the following structure:
  + starts with a [block comment: 57-91](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L57) listing the table's structure and prepared statements
  + then an [initialization block: 92-192](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L92) where the table and its prepared statements are initialized
  + followed by five methods per-query as follows
  
  | query | explanantion |
  | ----- | ------------ |
  | [`getQuery`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L207) | returns the raw query object to be used in any way; useful in case the other four methods aren't serving the inteded functionality  |
  | [`getQueryDispatchable`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L221) | returns a dispatchable version of the query to be added to the service's dispatcher and executed by the service's worker; e.g.: `request.getDispatcher().addDispatchMessage( Continents.i().getQueryDispatchablexxx() );` |
  | [`getBoundStatement`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L238) | returns the query's [`BoundStatement`](http://docs.datastax.com/en/drivers/java/2.1/com/datastax/driver/core/BoundStatement.html) which can be then added to a [`BatchStatement`](http://docs.datastax.com/en/drivers/java/2.1/com/datastax/driver/core/BatchStatement.html) or store few of them in an array and execute them using [`executeSync`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/Cassandra.java#L390) method since it will execute them asynchronously internally faster than executing them synchronously one by one |
  | [`executeAsync`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L253) | executes the query asynchronously and returns a [`ResultSetFuture`](http://docs.datastax.com/en/drivers/java/2.1/com/datastax/driver/core/ResultSetFuture.html) object which holds the future result of executing the query |
  | [`executeSync`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L269) | is a blocking method that executes the query synchronously then returns a [`ResultSet`](http://docs.datastax.com/en/latest-java-driver-api/com/datastax/driver/core/ResultSet.html) object containing the result of executing the query |
  
### usage examples

+ [**`executeSyncxxx`**](https://github.com/vangav/vos_whatsapp/blob/master/app/com/vangav/vos_whatsapp/controllers/send_message/HandlerSendMessage.java#L140) example from the `SendMessage` handler of `whatsapp` template

```java
  ResultSet resultSet = AuthCreds.i().executeSyncSelect(toUserId);
```

+ [**`executeAsyncxxx`**](https://github.com/vangav/vos_vangav_analytics_writer/blob/master/app/com/vangav/vos_vangav_analytics_writer/controllers/record_action/HandlerRecordAction.java#L113) example from the `RecordAction` handler of `vangav analytics writer` template

```java
  AnnualActionCounters.i().executeAsyncIncrement(
    CalendarFormatterInl.concatCalendarFields(
      request.getStartCalendar(),
      Calendar.YEAR)
    + "_"
    + action);
```

> if you want to wait and fetch the `ResultSet` from the async query above, one way is to do the following; but it's much better to use a `sync` execution method instead like [`executeSyncxxx`](https://github.com/vangav/vos_whatsapp/blob/master/app/com/vangav/vos_whatsapp/controllers/send_message/HandlerSendMessage.java#L140)

```java
  ResultSetFuture resultSetFuture =
    AnnualActionCounters.i().executeAsyncIncrement(
      CalendarFormatterInl.concatCalendarFields(
        request.getStartCalendar(),
        Calendar.YEAR)
      + "_"
      + action);
    
  ThreadPool.i().<ResultSet>executeInCassandraPool(resultSetFuture);
  
  ResultSet resultSet =
    result.getUninterruptibly(20, TimeUnit.SECONDS);
```

+ [**`getBoundStatementxxx`**](https://github.com/vangav/vos_whatsapp/blob/master/app/com/vangav/vos_whatsapp/controllers/send_message/HandlerSendMessage.java#L172) example from the `SendMessage` handler of `whatsapp` template; used to execute multiple queries

1. [initialize](https://github.com/vangav/vos_whatsapp/blob/master/app/com/vangav/vos_whatsapp/controllers/send_message/HandlerSendMessage.java#L167) a list of `BoundStatement`

```java
  ArrayList<BoundStatement> boundStatements =
    new ArrayList<BoundStatement>();
```

2. [add](https://github.com/vangav/vos_whatsapp/blob/master/app/com/vangav/vos_whatsapp/controllers/send_message/HandlerSendMessage.java#L171) as many `BoundStatement` objects as needed

```java
  // insert into messages
  boundStatements.add(
    Messages.i().getBoundStatementInsert(
      messageId,
      messageByteBuffer) );
```

3. [execute](https://github.com/vangav/vos_whatsapp/blob/master/app/com/vangav/vos_whatsapp/controllers/send_message/HandlerSendMessage.java#L195) all the `BoundStatement` objects

```java
  // execute bound statements
  Cassandra.i().executeSync(
    boundStatements.toArray(new BoundStatement[0] ) );
```

+ [**`BatchStatement`**](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/post_photo/HandlerPostPhoto.java#L163) example from the `PostPhoto` handler of `instagram` template; one common use for batch statements is when multiple queries must succeed together and the failure of one query cancels the execution of all the other queries

1. [initialize](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/post_photo/HandlerPostPhoto.java#L163)

```java
  // insert into ig_jobs
  // all queries must succeed
  BatchStatement batchStatement = new BatchStatement(Type.LOGGED);
```

2. [add](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/post_photo/HandlerPostPhoto.java#L166) as many bound statements as needed to the initialized batch statement

```java
  // insert into ig_jobs.current_jobs
  batchStatement.add(
    CurrentJobs.i().getBoundStatementInsert(
      request.getRequestId(),
      request.getStartTime(),
      jobByteBuffer) );
```

3. [execute](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/post_photo/HandlerPostPhoto.java#L185) the batch statement (sync or async)

```java
  // execute batch statement
  Cassandra.i().executeSync(batchStatement);
```

+ [**`getQueryDispatchablexxx`**](https://github.com/vangav/vos_whatsapp/blob/master/app/com/vangav/vos_whatsapp/controllers/send_message/HandlerSendMessage.java#L215) example from `SendMessage` handler of `whatsapp` template; used whenever a query should be execute in the worker service

```java
  // dispatch analysis
  request.getDispatcher().addDispatchMessage(
    MessagesCount.i().getQueryDispatchableIncrement(
      CalendarFormatterInl.concatCalendarFields(
        request.getStartCalendar(),
        Calendar.YEAR,
        Calendar.MONTH,
        Calendar.DAY_OF_MONTH) ) );
```

# exercise

> when would one use [**`BatchStatement`**](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/post_photo/HandlerPostPhoto.java#L163) vs [**`getBoundStatementxxx`**](https://github.com/vangav/vos_whatsapp/blob/master/app/com/vangav/vos_whatsapp/controllers/send_message/HandlerSendMessage.java#L172)?

# next tutorial -> [compression](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/compression)
> contains different compression algorithms

# share

[![facebook share](https://www.prekindle.com/images/social/facebook.png)](https://www.facebook.com/sharer/sharer.php?u=https%3A//github.com/vangav/vos_backend)  [![twitter share](http://www.howickbaptist.org.nz/wordpress/media/twitter-64-black.png)](https://twitter.com/home?status=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster%20%7C%20https%3A//github.com/vangav/vos_backend)  [![pinterest share](http://d7ab823tjbf2qywyt3grgq63.wpengine.netdna-cdn.com/wp-content/themes/velominati/images/share_icons/pinterest-black.png)](https://pinterest.com/pin/create/button/?url=https%3A//github.com/vangav/vos_backend&media=https%3A//scontent-mad1-1.xx.fbcdn.net/v/t31.0-8/20645143_1969408006608176_5289565717021239224_o.png?oh=acf20113a3673409d238924cfec648d2%26oe=5A3414B5&description=)  [![google plus share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/google.png)](https://plus.google.com/share?url=https%3A//github.com/vangav/vos_backend)  [![linkedin share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/linkedin.png)](https://www.linkedin.com/shareArticle?mini=true&url=https%3A//github.com/vangav/vos_backend&title=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster&summary=&source=)

# free consulting

[![vangav's consultant](http://www.footballhighlights247.com/images/mobile-share/fb-messenger-64x64.png)](https://www.facebook.com/mustapha.abdallah)
