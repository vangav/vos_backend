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

# Content

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

- unix operating systems (e.g.: mac os, ubuntu, etc ...)

- JAVA 8

# Quick Start

1. create a workspace directory (e.g.: my_services)

2. download this (vos_backend.zip) project inside the workspace and unzip it

3. rename downloaded vos_backend-master to vos_backend

4. optional - in the workspace directory add a new directory (e.g.: my_new_service) and put the new servie's config files inside that directory

5. from the terminal cd to vos_backend/tools_bin

6. type > java -jar backend_generator.jar new my_new_service

7. in case of using the optional config files for controllers, the generated backend will have a Handler class for each controller where the controller's logic should be implemented

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
