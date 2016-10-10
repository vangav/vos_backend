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
⋅⋅+ compression algorithms
⋅⋅+ content formatting/checking/generation (CQL, JAVA, Phriction, ...)
⋅⋅+ data structures and algorithms
⋅⋅+ exceptions
⋅⋅+ file-operations
⋅⋅+ geo (geo grids, lite reverse geo coding and geo hashing)
⋅⋅+ IDs (sequential ids, twitter snow flake and uuid operations)
⋅⋅+ math/geometry
⋅⋅+ metrics (distance/time)
⋅⋅+ **email clients** (JAVA and mailgun), **REST client** (sync/async)
⋅⋅+ **public apis** (Facebook Graph API and car2go public API)
⋅⋅+ **push notifications** (android and ios)
⋅⋅+ **authentication** (Facebook, Google, OAuth2 and transaction tokens) **cryptography** (asymmetric, password hashing and two-way encryption)
⋅⋅+ build-in configurable threadpools and latch threads
⋅⋅+ **Vangav M** deeply integrated

When using Vangav Backend, one doesn't start by writing code. Instead the service's entry points (controllers) and databse tables/queries are defined in minimal json files and Vangav Backend's generators takes car of adding the majority of the code a service's need including while maintaining the highest performance and design quality we know how to do.

# Prerequisites

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
