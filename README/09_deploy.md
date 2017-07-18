# deploy on a production server

+ skip cassandra/cql-related steps in case the service to be deployed doesn't use cassandra

### initializing the server
1. recommended operating system is [ubuntu server 16.04 LTS](https://www.ubuntu.com/server); alternatively any ubuntu server lts version or another unix os version should suffice
2. install java 8 on the server; follow the following steps for ubuntu
    + check if java is already installed: `java -version`, if that prints `1.8.xx` skip this step (2)
    + `sudo apt-get update`
    + `sudo apt-get install default-jre`
    + optionally also install the jdk `sudo apt-get install default-jdk`
    + check installation: `java -version`, should print `1.8.xx`
3. if the service to be deployed uses cassandra, then python is also needed; the following steps show how to install python on ubuntu
    + check if the needed python version is already installed: `python2.7 --version`, if that prints `Python 2.7.10` skip this step (3)
    + `cd ~/Downloads`
    + `wget https://www.python.org/ftp/python/2.7.10/Python-2.7.10.tgz`
    + `tar -zxvf Python-2.7.10.tgz`
    + `sudo apt-get install build-essential checkinstall`
    + `sudo apt-get install libreadline-gplv2-dev libncursesw5-dev libgdbm-dev libc6-dev libbz2-dev libsqlite3-dev tk-dev libssl-dev`
    + `cd ~/Downloads/Python-2.7.10/`
    + `sudo ./configure`
    + `sudo make altinstall`
    + check installation: `python2.7 --version`, should print `Python 2.7.10`
4. install a web-server/load-balancer if you don't have one already; we recommend [nginx](https://www.nginx.com/), or [apache](https://httpd.apache.org/)
    + [nginx installation tutorial](https://www.digitalocean.com/community/tutorials/how-to-install-nginx-on-ubuntu-16-04)

### setting the server up for vangav backend

1. go to the home directory: `cd ~`
2. make your services directory, e.g.: `mkdir my_services`
3. `cd my_services`
4. `mkdir vos_backend` - must be named vos_backend
5. make a copy of [`apache-cassandra-2.1.2`](https://github.com/vangav/vos_backend/tree/master/apache-cassandra-2.1.2) on your local machine and **make sure** that it's clear of data by deleting the `data` and `logs` directories inside it
6. compress the cassandra copied in (5) to `apache-cassandra-2.1.2.zip`
7. copy cassandra from (6) to the server's vos_backend directory created in (4): `scp apache-cassandra-2.1.2.zip your_username@you_server_ip_or_domain_name:/path/to/vos_backend`
8. start cassandra on the server `cd ~/my_services/vos_backend/` **->** `unzip apache-cassandra-2.1.2.zip` **->** `cd apache-cassandra-2.1.2/bin` **->** `./cassandra`

### Per-service deployment
5. Under **`my_services`** (created in (1)) make a new empty directory called **`my_service_name`** to hold the services binaries and CQL scripts.
6. **`scp [cassandra](https://github.com/vangav/vos_geo_server/tree/master/cassandra)`** into the **`my_services/my_service_name`** (created in (5)) in order to have the service's CQL scripts on the server.
7. From the **`cassandra`** directory copied in (6) run the needed script(s) to initialize/update the service's database. As explained in the [vos_geo_server](https://github.com/vangav/vos_backend/blob/master/README/02_intermediate_example_vos_geo_server.md#init-the-services-cassandra-database) example.
8. Execute the [_dist.sh](https://github.com/vangav/vos_geo_server/blob/master/_dist.sh) script. From a terminal session execute **`./_dist.sh`**. This creates the production executable under `my_service_name/target/universal/my_service_name-1.0-SNAPSHOT.zip`.
9. **`scp my_service_name-1.0-SNAPSHOT.zip`** generated in (8) into **`my_services/my_service_name`**, and unzip it.
10. **`cd`** to **`scp my_service_name-1.0-SNAPSHOT`** (copied and unzipped in (9)) then execute the command **`./bin/my_service_name -Dhttp.port=9000 &`**. Change the `9000` port to the desired port per instance.
11. To run multiple instances of the service/worker, just make multiple copies of **`scp my_service_name-1.0-SNAPSHOT.zip`** and repeat step (10) with a different port per instance then configure these port numbers in a load balancer.

> Recommended load balancer is [nginx](https://www.nginx.com/).
