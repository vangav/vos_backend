
> **why?** once you are done with a development phase of a service, you want to bring it to life by deploying it on a server; this tutorial gives you a detailed step-by-step process from making a production version of your service till running it on your server(s)

# deploy on a production server

> skip cassandra/cql-related steps in case the service to be deployed doesn't use cassandra

### initializing the server
1. recommended operating system is [ubuntu server 16.04 LTS](https://www.ubuntu.com/server); alternatively any ubuntu server LTS (long term support) version or another unix os version should suffice
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
8. start cassandra on the server:
    + `cd ~/my_services/vos_backend/`
    + `unzip apache-cassandra-2.1.2.zip`
    + `cd apache-cassandra-2.1.2/bin`
    + `./cassandra`

### deploy a vangav backend service

1. on the server: create a directory that will hold the service's binaries and cql scripts
    + `cd ~/my_services`
    + `mkdir my_service_name` e.g.: `mkdir vos_geo_server`
2. copy the service's cassandra directory (e.g.: [cassandra](https://github.com/vangav/vos_geo_server/tree/master/cassandra)) into the directory created in (1) in order to have the service's cql scripts on the server e.g.:
    + `scp vos_geo_server/cassandra.zip your_username@you_server_ip_or_domain_name:/path/to/my_services/vos_geo_server`
3. decompress on the server, e.g.:
    + `cd ~/my_services/vos_geo_server`
    + `unzip cassandra.zip`
4. initialize the service's database on the server **note: only do this step once per-service per-server**
    + `cd ~/my_services/vos_geo_server/cassandra/cql/drop_and_create/`
    + `./_execute_cql.sh gs_top_dev.cql`
5. create a production executable on the local dev machine under `my_service_name/target/universal/my_service_name-1.0-SNAPSHOT.zip`
    + `cd path_to_my_service`
    + `./_dist.sh`
6. copy the executable created in (5) to the server
    +`scp my_service_name/target/universal/my_service_name-1.0-SNAPSHOT.zip your_username@you_server_ip_or_domain_name:/path/to/my_services/my_service_name`
    + then unzip it on the server
    + `cd ~/my_services/my_service_name`
    + `unzip my_service_name-1.0-SNAPSHOT.zip`
7. start the service
    + go to the executable unzipped in (6) `cd my_service_name-1.0-SNAPSHOT`
    + then execute `./bin/my_service_name -Dhttp.port=9000 &`, change the `9000` port number to the desired port number
8. configure the web-server installed earlier to forward incoming traffic to your service's port number; e.g.: forward port `80` traffic coming on `my_service_name.com` domain to port `9000`
9. open an internet browser on your dev machine and test your service (or use an extension like postman on google chrome)

> to deploy a worker service, follow the same steps above (1) to (7) unless the worker service needs to be accessed from another production server then the web-server will also be configured like in step (8)

> to scale up and deploy multiple instances per service/worker, just make multiple copies of the executable created in steps (5) and (6) on the server (all directly under the `~/my_services/my_service_name` directory and repeat step (7) per copy while giving each a unique port number then configure those port numbers as explained in step (8)

> to get the ports in use `sudo lsof -i -P -n | grep LISTEN`

### how to stop a service's instance
1. check the instance's pid (process id) `vim ~/my_services/my_service_name/my_service_name-1.0-SNAPSHOT/RUNNING_PID`
2. `kill -9 (pid)` (pid) got from step (1)

### how to stop cassandra
1. in a terminal session execute `ps auwx | grep  cassandra`; this shows cassandra's `pid` (process id)
2. execute `kill -9 (pid)` (pid) got from step (1)
3. repeat step (1) to make sure you get one (pid) only - that's the pid for the `grep command`

## next tutorial -> [vangav backend error codes](https://github.com/vangav/vos_backend/blob/master/README/10_error_codes.md)
> vangav backend detects various types of errors (e.g.: invalid request param, invalid generator config, wrong utility method arguments, unauthorized third-party authentication, invalid vangav mighty solution, ...); this tutorial lists all of vangav backend's error codes/sub-codes with reference to the code producing them for ease of tracing if you get one
