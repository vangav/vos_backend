# cassandra

### Optional Usage

+ RESTful service generated using Vangav Backend provide access methods per-prepared-statement. Invoking these generated methods are the highly recommended way for interacting with Cassandra as explained [here](https://github.com/vangav/vos_backend/blob/master/README/03_generated_rest_service_structure.md#appcomvangavvos_geo_servercassandra_keyspaces). Optionally (not-recommended) one can directly use [Cassandra](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/Cassandra.java) mainly by invoking one of the following methods:
  + [ResultSetFuture executeAsync (Statement query)](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/Cassandra.java#L330)
  + [ArrayList<ResultSetFuture> executeAsync (Statement... queries)](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/Cassandra.java#L343)
  + [ResultSet executeSync (Statement query)](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/Cassandra.java#L364)
  + [ArrayList<ResultSet> executeSync (Statement... queries)](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/Cassandra.java#L382)

### Structure

| Class | Explanation |
| ----- | ----------- |
| [Cassandra](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/Cassandra.java) | Maintains session with one cassandra node and sends execution/preparing requests over that session. |
| [CassandraNode](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/CassandraNode.java) | Represents a Cassandra-node as name/ip. |
| [CassandraProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/CassandraProperties.java) | Represents Cassandra's properties (i.e.: retries, topology, ...), a reflection of [cassandra_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/cassandra_properties.prop). |
| [formatting](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/cassandra/formatting) | Has utilities for formatting data in/out of cassandra (e.g.: formatting a date into a String). |
| [keyspaces](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/cassandra/keyspaces) | Has classes representing Cassandra's tables, queries and dispatchable queries. |
| [keyspaces_generator](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/cassandra/keyspaces_generator) | This sub-package is responsible for verifying Cassandra's config files then using them to generate JAVA cliens, CQL scripts and phriction wikis. |
