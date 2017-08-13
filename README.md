
### [![YouTube Play Icon](http://extensiondl.maxthon.com/skinpack/842028/1404121512/icons/icon_32.png)](https://www.youtube.com/watch?v=1uFcNgj3nRk&list=PLTcKayTjao6rOj02gtRdiVhvzB1SWGyhv) learn more on [YouTube](https://www.youtube.com/watch?v=1uFcNgj3nRk&list=PLTcKayTjao6rOj02gtRdiVhvzB1SWGyhv)

# vangav backend

![vangav backend](https://scontent-mad1-1.xx.fbcdn.net/v/t31.0-8/20645143_1969408006608176_5289565717021239224_o.png?oh=acf20113a3673409d238924cfec648d2&oe=5A3414B5)

![illustration](https://scontent-mad1-1.xx.fbcdn.net/v/t31.0-8/20748060_1972622799620030_8862065064707862972_o.png?oh=6d5e001708c907fb1c1117d2f2c15a6b&oe=5A2C84BA)

### built on top tech
| tech | ref |
| ------------- | ------------- |
| [cassandra](http://cassandra.apache.org/) | **apple**'s cassandra deployment has over 75,000 nodes storing over 10PB of data |
| [play framework](https://www.playframework.com/) | play framework powers **linkedin**'s 500,000,000 members backend |
| [akka](http://akka.io/) | akka helps power e-shopping for hundreds of millions of **amazon** members |
| [datastax drivers](http://www.datastax.com/) | datastax processes over 1,000,000,000,000 cassandra requests for **netflix** per day |

### fully-loaded
| utility | options |
| ------------- | ------------- |
| **backend generator** | design in json and generate **90+%** of the code for a service + several utilities |
| [security](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security/) | facebook/google auth, oauth 2, transaction tokens, encryption, ... |
| [networks](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/) | twilio, mail gun, rest client, rest jobs, ... |
| [push notifications](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications/) | ios, android, ... |
| [geo services](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo/) | geo grids, reverse geo coding, geo hashing, ... |
| [dispatcher/worker](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/dispatcher/) | dispatchable queries, push notifications, emails, sms, ... |
| and more ... | periodic jobs, thread pools, facebook graph api, snow flake ids (twitter's ids), compression, client generator, exceptions, geometry, metrics, deep vangav mighty integration, ... |

### [great templates](https://github.com/vangav)
| template | services | features | db tables | code: total | code: generated |
| -------- | -------- | -------- | --------- | ----------- | --------------- |
| [instagram](https://github.com/vangav/vos_instagram) | 7 | 47 | 52 | 67,715 + 190,996 | 238,465 **(92.17%)** |
| analytics [writer](https://github.com/vangav/vos_vangav_analytics_writer) & [reader](https://github.com/vangav/vos_vangav_analytics_reader) | 2 | 9 | 4 | 67,715 + 10,252 | 74,552 **(95.62%)** |
| [whatsapp](https://github.com/vangav/vos_whatsapp) | 3 | 7 | 7 | 67,715 + 7,834 | 74,408 **(98.49%)** |
| [geo server](https://github.com/vangav/vos_geo_server) | 1 | 3 | 3 | 67,715 + 2,691 | 70,300 **(99.85%)** |
| [calculate sum](https://github.com/vangav/vos_calculate_sum) | 1 | 1 | 0 | 67,715 + 544 | 68,258 **(99.99%)** |

### roadmap
| time | effect |
| ---- | ------ |
| 5 min | first service [(vos_calculate_sum)](https://github.com/vangav/vos_calculate_sum) generated and running |
| 20 min | second service [(vos_geo_server)](https://github.com/vangav/vos_geo_server) generated and running |
| 5-7 days | finished all templates: command of all utilities, multi-service backends |
| 1-2 weeks | full command of modifying/extending the underlying backend |
| thereafter | best-in-class-level in implementing top class backend services, finishing a year's worth of work every month |

# in a nutshell

![vangav backend generator](https://scontent-mad1-1.xx.fbcdn.net/v/t31.0-8/p720x720/20690308_1970492503166393_1992568418424374307_o.png?oh=6e5ae9894bf979b1827a768b5b74f227&oe=5A379F4A)

# system requirements

+ unix operating system (e.g.: mac os, ubuntu, etc ...)
+ java 8
+ python 2.7.10 (only for using cassandra)
    + to check python version type in the following command in a terminal session `python2.7 -V` and the output should be `Python 2.7.10`
  + installing python on mac os
    + [download python for mac os](https://www.python.org/ftp/python/2.7.10/python-2.7.10-macosx10.6.pkg)
    + open python-2.7.10-macosx10.6.pkg and follow the installer steps
  + installing python on ubuntu
    + `cd ~/Downloads`
    + `wget https://www.python.org/ftp/python/2.7.10/Python-2.7.10.tgz`
    + `tar -zxvf  Python-2.7.10.tgz`
    + `sudo apt-get install build-essential checkinstall`
    + `sudo apt-get install libreadline-gplv2-dev libncursesw5-dev libgdbm-dev libc6-dev libbz2-dev  libsqlite3-dev tk-dev libssl-dev`
    + `cd ~/Downloads/Python-2.7.10/`
    + `sudo ./configure`
    + `sudo make altinstall`
    + `python2.7 --version`

# prerequisites

+ basic java
+ basic cassandra (optional for using cassandra database)

# quick start example: [calculate sum](https://github.com/vangav/vos_calculate_sum)

### [![YouTube Play Icon](http://extensiondl.maxthon.com/skinpack/842028/1404121512/icons/icon_32.png)](https://www.youtube.com/watch?v=1uFcNgj3nRk&list=PLTcKayTjao6rOj02gtRdiVhvzB1SWGyhv) learn more on [YouTube](https://www.youtube.com/watch?v=1uFcNgj3nRk&list=PLTcKayTjao6rOj02gtRdiVhvzB1SWGyhv)

> 5-10 min: this tutorial explains how to generate and use the first vangav backend service

vos_calculate_sum is a service that takes a two floats (a and b) request and returns a double (c) response representing the summation of a and b

### init
1. create a workspace directory `my_services` - this is the directory to contain both of vos_backend and all the services generated using it
2. download this `vos_backend.zip` project (from the green `clone or download` button up there) inside the workspace directory created in (1) and unzip it
3. **rename** downloaded `vos_backend-master` to `vos_backend`

### generate a new service
1. create a new directory `my_services/vos_calculate_sum`
2. copy `controllers.json` from `vos_backend/vangav_backend_templates/vos_calculate_sum/` to the directory `my_services/vos_calculate_sum` created in (1)
3. open a terminal session and `cd` to `my_services/vos_backend/tools_bin`
4. execute the command `java -jar backend_generator.jar new vos_calculate_sum` to generate the service
5. enter `y` for using the config directory in order to use `controllers.json` for generating
6. enter `n` for generating a worker service (using workers is explained in a separate section)

### writing the service's logic code
+ optionally for eclipse users: open eclipse and import vos_calculate_sum project
  + file **>** import **>** general **>** existing projects into workspace **>** next **>** set "select root directory" to my_services **>** under projects make sure that vos_calculate_sum is selected **>** finish
  + double check the java version used for compiling the project: right click the project **>** properties **>** java compiler **>** enable project specific settings **>** compiler compliance level **>** 1.7 or 1.8
+ open class [HandlerCalculateSum.java](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/HandlerCalculateSum.java) under package `com.vangav.vos_calculate_sum.controllers.calculate_sum`, method [`processRequest`](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/HandlerCalculateSum.java#L86) should be as follows in order to complete the request-to-response logic
```java
  @Override
  protected void processRequest (final Request request) throws Exception {

    // use the following request Object to process the request and set
    //   the response to be returned
    RequestCalculateSum requestCalculateSum =
      (RequestCalculateSum)request.getRequestJsonBody();
    
    // set response's value
    ((ResponseCalculateSum)request.getResponseBody() ).set(
      requestCalculateSum.a + requestCalculateSum.b);
  }
```

### start the service
1. `cd` to `my_services/vos_calculate_sum`
2. execute the command `./_run.sh`

### try it out
1. open an internet browser page and type [`http://localhost:9000/calculate_sum?a=1.2&b=2.3`](http://localhost:9000/calculate_sum?a=1.2&b=2.3) - this returns **3.5**
2. play with `a` and `b` values in the request string in (1)
3. try issuing an invalid request (e.g.: set `a` to "xyz", don't set `b`, ...) to get a sense of how the default error response looks like ([error responses](https://github.com/vangav/vos_backend/blob/master/README/06_error_response.md) are explained in depth in a separate section)

### stop the service
in the terminal session where you started the service press `control + d`

# vangav backend tutorials

### 1. [expand calculate sum to calculator](https://github.com/vangav/vos_backend/blob/master/README/00_expanding_calculate_sum_example.md)
#### [![YouTube Play Icon](http://extensiondl.maxthon.com/skinpack/842028/1404121512/icons/icon_32.png)](https://www.youtube.com/watch?v=v3PxKDMehvY&index=2&list=PLTcKayTjao6rOj02gtRdiVhvzB1SWGyhv) learn more on [YouTube](https://www.youtube.com/watch?v=v3PxKDMehvY&index=2&list=PLTcKayTjao6rOj02gtRdiVhvzB1SWGyhv)
+ shows how to add controllers (api entry points) before and after service generation

### 2. [project's contents](https://github.com/vangav/vos_backend/blob/master/README/01_project_contents.md)
+ explains the building blocks of vangav backend
### 3. [next example with database (geo server)](https://github.com/vangav/vos_backend/blob/master/README/02_intermediate_example_vos_geo_server.md)
+ generates a service that has a backend database and also uses the geo services utility
### 4. [generated service structure](https://github.com/vangav/vos_backend/blob/master/README/03_generated_rest_service_structure.md)
+ explains the building blocks of a generated service
### 5. [service generator config structure](https://github.com/vangav/vos_backend/blob/master/README/04_rest_service_config_structure.md)
+ explains the building blocks of the config used to generate a service
### 6. [request object](https://github.com/vangav/vos_backend/blob/master/README/05_request_structure.md)
+ when implementing a service's logic, the passed request object keeps all the request's information from start to finish; this tutorial shows how to use that object
### 7. [error response](https://github.com/vangav/vos_backend/blob/master/README/06_error_response.md)
+ explains what happens whenever an error happens during request processing and how to manually return various types of error response
### 8. [using dispatcher - worker(s)](https://github.com/vangav/vos_backend/blob/master/README/07_dispatcher_worker.md)
+ shows why and how to us the dispatcher with one or more worker service(s)
### 9. [debugging](https://github.com/vangav/vos_backend/blob/master/README/08_debug.md)
+ explains how to start and use the debugger
### 10. [deploy on a production server](https://github.com/vangav/vos_backend/blob/master/README/09_deploy.md)
+ once your service is ready for release, this tutorial has the step-by-step process till your service is up and running on a production server - as well as how to scale it up
### 11. [vangav backend error codes](https://github.com/vangav/vos_backend/blob/master/README/10_error_codes.md)
+ vangav backend detects various types of errors (e.g.: invalid request param, invalid generator config, wrong utility method arguments, unauthorized third-party authentication, invalid vangav mighty solution, ...); this tutorial lists all of vangav backend's error codes/sub-codes with reference to the code producing them for ease of tracing if you get one

# utilities, design and src code tutorials

### [java clients](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/backend_client_java) 
+ handles generating java clients and contains vangav backend's client framework

### [backend generator](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/backend_generator)
+ manages all vangav backend generators (api, database client, worker and java client)

### [cassandra](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/cassandra)
+ handles generating database clients, contains the framework for generated clients and handles all cassandra operations

### [compression](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/compression)
+ contains different compression algorithms

### [content: verifiction/formatting/generation](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content)
+ handles content (code, phriction-wiki, text, ...) checking, formatting and generation; vangav backend relies on this package to verify generation config and format generated code, scripts, wiki, ...

### [data structures and algorithms](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/data_structures_and_algorithms)
+ has data structures (e.g.: heap, kd-tree, tuples, ...) and algorithms (collections, matricies, strings, arrays, ...)

### [dispatcher - worker](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/dispatcher)
+ handles generating worker services, contains the framework for dispatchers/workers and handles all of their operations

### [exceptions](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/exceptions)
+ vangav exceptions are used to handle `bad request` and `internal error`; those exceptions can be returned to the client and loggable (in database, text files, ...)

### [files](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/files)
+ simplifies various types of files needed for vangav backend services like: properties, json config, images, http response files, ommiting comments, directory operations, ...

### [geo services](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo)
+ handles reverse geo coding, geo grids and geo hashing

### [ids](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/ids)
+ handles uuid operations, sequential ids and twitter's snow flake ids

### [math](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/math)
+ handles mathematical and geometric operations like: numeric, ranges, circles, line segments, straight lines, ...

### [metrics](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/metrics)
+ handles distance, time, date and calendar operations

### [networks](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks)
+ has sync/async rest client, rest jobs, email clients, twilio messaging and download utility

### [play framework](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/play_framework)
+ handles generating api code and contains the framework for generated vangav backend services; *vangav backend services' backbone*

### [properties](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/properties)
+ handles loading and extracting data from properties files

### [public apis](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/public_apis)
+ handles fetching data from facebook graph api and car2go api

### [push notifications](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications)
+ handles building and sending apple and android notifications

### [security](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security)
+ handles authentication (facebook, google, oauth 2 and transaction tokens) and cryptography (asymmetric, hashing and two-way encryption)

### [system](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/system)
+ handles fetching system info (cpu usage, free ram/disk, os type, number of cores, ...) and console operation (interactive, commands, ...)

### [thread pool](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/thread_pool)
+ contains latch threads, periodic jobs and the thread pools responsible for maintaining top performance for vangav backend services (in-memory threads, cassandra, dispatcher and rest client)

### [vangav mighty](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/vangav_m)
+ handles generating and binding [vangav mighty](http://vangav.com/) solutions

# templates

### [calculate sum](https://github.com/vangav/vos_calculate_sum)
+ simple service

### [geo server](https://github.com/vangav/vos_geo_server)
+ a more advanced service with a databse and uses geo services utility

### [whatsapp](https://github.com/vangav/vos_whatsapp)
+ service oriented architecture (main + worker + analytics), multi-keyspace database and basic authentication

### [vangav analytics](https://github.com/vangav/vos_vangav_analytics_writer)
+ service oriented architecture (writer + reader) and generic service design (handles any type of analytics)

### [instagram](https://github.com/vangav/vos_instagram)
#### covered topics
+ service oriented architecture (main + dispense + jobs + worker + dash board)
+ multi-keyspace database
+ oauth 2 and facebook authentication
+ facebook graph api
+ rest jobs
+ periodic jobs
+ vangav mighty
+ push notifications
+ logging
+ analytics
+ client generator
+ test and bots services

# share

[![facebook share](https://www.prekindle.com/images/social/facebook.png)](https://www.facebook.com/sharer/sharer.php?u=https%3A//github.com/vangav/vos_backend)  [![twitter share](http://www.howickbaptist.org.nz/wordpress/media/twitter-64-black.png)](https://twitter.com/home?status=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster%20%7C%20https%3A//github.com/vangav/vos_backend)  [![pinterest share](http://d7ab823tjbf2qywyt3grgq63.wpengine.netdna-cdn.com/wp-content/themes/velominati/images/share_icons/pinterest-black.png)](https://pinterest.com/pin/create/button/?url=https%3A//github.com/vangav/vos_backend&media=https%3A//scontent-mad1-1.xx.fbcdn.net/v/t31.0-8/20645143_1969408006608176_5289565717021239224_o.png?oh=acf20113a3673409d238924cfec648d2%26oe=5A3414B5&description=)  [![google plus share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/google.png)](https://plus.google.com/share?url=https%3A//github.com/vangav/vos_backend)  [![linkedin share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/linkedin.png)](https://www.linkedin.com/shareArticle?mini=true&url=https%3A//github.com/vangav/vos_backend&title=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster&summary=&source=)

# free consulting

[![vangav's consultant](http://www.footballhighlights247.com/images/mobile-share/fb-messenger-64x64.png)](https://www.facebook.com/mustapha.abdallah)
