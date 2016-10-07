# [Vangav Backend](http://www.vangav.com/)
### [back-the-end]

[vangav.com](http://www.vangav.com/)

Vangav Backend is an open source license-free backend.
It relies on:
- [play framework](http://www.playframework.com) for web framework
- [Apache Cassandra](http://www.cassandra.apache.org/) for database
- [datastax](http://www.datastax.com/) for JAVA cassandra driver

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

[Facebook Group: Vangav Open Source - Backend](fb.com/groups/575834775932682/)

[Facebook Page: Vangav](fb.com/vangav.f)


Third party communities for Vangav Backend
- play framework
- cassandra
- datastax


Have fun!
