# Deploy

+ Ignore cassandra/cql-related steps in case the service to be deployed doesn't use cassandra.

### One time per-server
1. On the server where the service should be deployed, create a new directory **`my_services`**.
2. Under **`my_services`** (created in (1)) make a new empty directory called **`vos_backend`**.
3. **`scp [apache-cassandra-2.1.2](https://github.com/vangav/vos_backend/tree/master/apache-cassandra-2.1.2)`** into the **`my_services/vos_backend`** directory (created in (2)) to have cassandra on the server. Make sure that data and logs directories are empty in `apache-cassandra-2.1.2`.
4. **`cd`** into the **`apache-cassandra-2.1.2/bin`** copied in (3) and execute the command **`./cassandra`** to start cassandra.

### Per-service deployment
5. Under **`my_services`** (created in (1)) make a new empty directory called **`my_service_name`** to hold the services binaries and CQL scripts.
6. **`scp [cassandra](https://github.com/vangav/vos_geo_server/tree/master/cassandra)`** into the **`my_services/my_service_name`** (created in (5)) in order to have the service's CQL scripts on the server.
7. From the **`cassandra`** directory copied in (6) run the needed script(s) to initialize/update the service's database. As explained in the [vos_geo_server](https://github.com/vangav/vos_backend/blob/master/README/02_intermediate_example_vos_geo_server.md#init-the-services-cassandra-database) example.
8. Execute the [_dist.sh](https://github.com/vangav/vos_geo_server/blob/master/_dist.sh) script. From a terminal session execute **`./_dist.sh`**. This creates the production executable under `my_service_name/target/universal/my_service_name-1.0-SNAPSHOT.zip`.
9. **`scp my_service_name-1.0-SNAPSHOT.zip`** generated in (8) into **`my_services/my_service_name`**, and unzip it.
10. **`cd`** to **`scp my_service_name-1.0-SNAPSHOT`** (copied and unzipped in (9)) then execute the command **`./bin/my_service_name -Dhttp.port=9000 &`**. Change the `9000` port to the desired port per instance.
11. To run multiple instances of the service/worker, just make multiple copies of **`scp my_service_name-1.0-SNAPSHOT.zip`** and repeat step (10) with a different port per instance then configure these port numbers in a load balancer.

> Recommended load balancer is [nginx](https://www.nginx.com/).