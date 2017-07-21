
> **why [cassandra](cassandra.apache.org)?** the best nosql database for high performance, high availability, scalability and master-master replication; proven by apple's deployment with 75,000 nodes storing over 10 PB of data, netfllix's 2,500 nodes storing 420 TB with over 1 trillion requests per day and more than 1500 other companies like github, instagram, ebay, ...

# cassandra

### package structure

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

> in this section [whatsapp](https://github.com/vangav/vos_whatsapp) template service is used as a reference; yet it's the same for every generated service



















