# [Vangav Backend](http://www.vangav.com/)
### [back-the-end]

[vangav.com](http://www.vangav.com/)

Vangav Backend is an open source license-free backend.
+ **90+% less code:** Instead of writing so much code, Vangav Backend has built-in service code generator. Just write a minimal JSON definition of a new service's entry points (controllers) and database's tables/queries. Then using one command line `java -jar backend_generator.jar new my_new_service_name` Vangav Backend takes care of generating 90+% of the code needed for that new service.
+ **10-% of hello-world-logic:** The generated service adds TODOs where the user should add the service's logic. Usually few method calls with few if-conditions and/or loops.
+ **eclipse-ready:** The generated service's JAVA project is ready for import in eclipse.
+ **post-generation-config:** Modify the service's database config (add/remove/edit) then run one command line `java -jar cassandra_keyspaces_updater.jar`.
+ **built-in dispatcher/worker:** With dispatchable queries and push notifications.
+ **key built-in utilities:**
  + [compression](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/compression) algorithms
  + [content](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content) formatting/checking/generation (CQL, JAVA, Phriction, ...)
  + [data structures and algorithms](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/data_structures_and_algorithms)
  + [exceptions](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/exceptions)
  + [file-operations](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/files)
  + [geo](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo) (geo grids, lite reverse geo coding and geo hashing)
  + [IDs](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/ids) (sequential ids, twitter snow flake and uuid operations)
  + [math/geometry](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/math)
  + [metrics](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/metrics) (distance/time)
  + **[email clients](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks)** (JAVA and mailgun), **[REST client](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks)** (sync/async)
  + **[public apis](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/public_apis)** (Facebook Graph API and car2go public API)
  + **[push notifications](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications)** (android and ios)
  + **[authentication](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security)** (Facebook, Google, OAuth2 and transaction tokens) **[cryptography](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security)** (asymmetric, password hashing and two-way encryption)
  + build-in configurable [threadpools](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/thread_pool) and latch threads
  + **Vangav M** deeply integrated

When using Vangav Backend, one doesn't start by writing code. Instead the service's entry points (controllers) and databse tables/queries are defined in minimal json files and Vangav Backend's generators takes care of adding the majority of the code a service needs, while maintaining the highest performance and design quality we know how to do.

# Contents

+ [apache-cassandra-2.1.2](https://github.com/vangav/vos_backend/tree/master/apache-cassandra-2.1.2) is the cassandra's version used by Vangav Backend. Services generated using Vangav Backend automatically use this cassandra on dev mode. For prod mode refer to the "Deployement" section below.
+ [data](https://github.com/vangav/vos_backend/tree/master/data) hold's the data files included with Vangav Backend for optional use like the data for reverse geo coding.
+ [dist](https://github.com/vangav/vos_backend/tree/master/dist) contains Vangav Backend's lib (gets automatically copied into new services upon generation).
+ [lib](https://github.com/vangav/vos_backend/tree/master/lib) contains all the third-party libraries used by Vangav Backend.
+ [play-2.2.6](https://github.com/vangav/vos_backend/tree/master/play-2.2.6) is the play framework's version used by Vangav Backend. Each generated service comes with _scripts (_run, _compile, etc ...) that point to this directory for play framework. No need to do anything here.
+ [prop](https://github.com/vangav/vos_backend/tree/master/prop) contains all the mandatory/optional properties files used by services generated using Vangav Backend. Relevant properties files get automatically copied into newely generated service; optionally copy more properties files from here if needed for an additional optional feature.
+ **[src/com/vangav/backend](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend)** contains all Vangav Backend's source code. The whole code is license-free with minor exceptions put under packages called "third_party" like geo hashing.
+ **[tools_bin](https://github.com/vangav/vos_backend/tree/master/tools_bin)** contains `backend_generator.jar` which the exec responsible for generating new Vangav Backend services using the command `java -jar backend_generator.jar new my_new_service_name`.

+ **[vangav_backend_templates](https://github.com/vangav/vos_backend/tree/master/vangav_backend_templates)** contains functional templates for Vangav Backend's services.

# System Requirements

+ unix operating system (e.g.: mac os, ubuntu, etc ...)
+ JAVA 8
+ python 2.6 (only for using cassandra)

# Prerequisites

+ Basic JAVA
+ Basic Cassandra/CQL (optional for using cassandra)

# Quick Start Example (vos_geo_server)

### Init
1. create a workspace directory "**my_services**" - this is the directory to contain both of vos_backend and all the services generated using it
2. download this (**vos_backend.zip**) project inside the workspace directory and unzip it
3. **rename** downloaded vos_backend-master to vos_backend

### Generate a new service
4. create a new directory "**my_services/vos_geo_server**"
5. **copy** **controllers.json** and **gs_top.keyspace** from vos_backend/vangav_backend_templates/vos_geo_server/ to the directory vos_geo_server created in (4)
6. open a terminal session and **cd** to my_services/vos_backend/tools/bin
7. execute the command **`java -jar backend_generator.jar new vos_geo_server`**
8. enter **`Y`** for using the config directory
9. enter **`Y`** to generate an eclipse-compatible project
10. enter **`N`** for generating a worker service

### Init the service's cassandra database
11. **cd** to my_services/vos_geo_server/cassandra/cql/
12. execute the command **`./_start_cassandra.sh`** - to start cassandra
13. **cd** to my_services/vos_geo_server/cassandra/cql/drop_and_create/
14. execute the command **`./_execute_cql.sh gs_top_dev.cql`**

### Init service's data

11. cd to my_services/vos_geo_server
12. execute the command **`./_run.sh`**
13. open an internet browser page and type 

# Community

[Facebook Group: Vangav Open Source - Backend](http://www.fb.com/groups/575834775932682/)

[Facebook Page: Vangav](http://www.fb.com/vangav.f)


Third party communities for Vangav Backend
- play framework
- cassandra
- datastax

# Dependencies

- [play framework](http://www.playframework.com) for web framework
- [Apache Cassandra](http://www.cassandra.apache.org/) for database
- [datastax](http://www.datastax.com/) for JAVA cassandra driver


Have fun!
