# vangav backend
## build big tech 10x faster

### built on top tech
| tech | ref |
| ------------- | ------------- |
| [cassandra](http://cassandra.apache.org/) | apple's cassandra deployment has over 75,000 nodes storing over 10PB of data |
| [play framework](https://www.playframework.com/) | play framework powers linkedin's 500,000,000 members backend |
| [akka](http://akka.io/) | akka helps power e-shopping for hundreds of millions of amazon members |
| [datastax drivers](http://www.datastax.com/) | datastax processes over 1,000,000,000,000 cassandra requests for netflix per day |

### fully-loaded
| utility | options |
| ------------- | ------------- |
| **backend generator** | design in json and generate **90+%** of the code for a service + several utilities |
| [security](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security/) | facebook/google auth, oauth 2, transaction tokens, encryption, ... |
| [networks](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/) | twilio, mail gun, rest client, rest jobs, ... |
| [push notifications](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications/) | ios, android, ... |
| [geo services](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo/) | geo grids, reverse geo coding, geo hashing, ... |
| [dispatcher/worker](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/dispatcher/) | dispatchable queries, push notifications, emails, sms, ... |
| and more ... | periodic jobs, thread pools, facebook graph api, snow flake ids, compression, client generator, exceptions, geometry, metrics, deep vangav mighty integration, ... |

### [great templates](https://github.com/vangav)
| template | services | features | db tables | code: total | code: generated |
| -------- | -------- | -------- | --------- | ----------- | --------------- |
| instagram_* | 7 | 47 | 52 | 67,715 + 190,996 | 238,465 **(92.17%)** |
| analytics | 2 | 9 | 4 | 67,715 + 10,252 | 74,552 **(95.62%)** |
| whatsapp | 3 | 7 | 7 | 67,715 + 7,834 | 74,408 **(98.49%)** |
| geo_server | 1 | 3 | 3 | 67,715 + 2,691 | 70,300 **(99.85%)** |
| calculate_sum | 1 | 1 | 0 | 67,715 + 544 | 68,258 **(99.99%)** |

# system requirements

+ unix operating system (e.g.: mac os, ubuntu, etc ...)
+ java 8
+ python 2.7.10 (only for using cassandra)
    + to check python version type in the following command in a terminal session `python2.7 -V` and the output should be `Python 2.7.10`
+ installing python on mac os
  + [download python for Mac](https://www.python.org/ftp/python/2.7.10/python-2.7.10-macosx10.6.pkg)
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
+ basic cassandra (optional for using cassandra)

# quick start example [(vos_calculate_sum)](https://github.com/vangav/vos_calculate_sum) service

vos_calculate_sum is a service that takes a two floats (a and b) request and returns a double (c) response representing the summation of a and b

### init
1. create a workspace directory "**my_services**" - this is the directory to contain both of vos_backend and all the services generated using it
2. download this **vos_backend.zip** project (from the green "clone or download button up there") inside the workspace directory created in (1) and unzip it
3. **rename** downloaded vos_backend-master to vos_backend

### generate a new service
1. create a new directory "**my_services/vos_calculate_sum**"
2. **copy** **controllers.json** from vos_backend/vangav_backend_templates/vos_calculate_sum/ to the directory vos_calculate_sum created in (1)
3. open a terminal session and **`cd`** to my_services/vos_backend/tools_bin
4. execute the command **`java -jar backend_generator.jar new vos_calculate_sum`** to generate the service
5. enter **`Y`** for using the config directory in order to use **controllers.json** for generating
6. enter **`N`** for generating a worker service (using workers is explained in a separate section)

### Writing the service's logic code
+ Open eclipse and **import** vos_calculate_sum project. File > import > General > Existing Projects into Workspace > Next > set "Select root directory" to my_services > under Projects make sure that vos_calculate_sum is selected > Finish.
+ Double check the java version used for compiling the project. right click the project > properties > Java Compiler > Enable project specific settings > Compiler compliance level > 1.7 or 1.8.
+ Open class **[HandlerCalculateSum.java](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/HandlerCalculateSum.java)** under package `com.vangav.vos_calculate_sum.controllers.calculate_sum`, method **[`processRequest`](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/HandlerCalculateSum.java#L86)** should be as follows in order to complete the request-to-response logic.
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

### Start the service
1. **cd** to my_services/vos_calculate_sum
2. execute the command **`./_run.sh`**

### Try it out
1. open an internet browser page and type **[`http://localhost:9000/calculate_sum?a=1.2&b=2.3`](http://localhost:9000/calculate_sum?a=1.2&b=2.3)** - this returns 3.5
2. play with `a` and `b` values in the request string in (1)
3. try issuing an invalid request (e.g.: set `a` to "xyz", don't set `b`, ...) to get a sense of how the default error response looks like. [Error responses](https://github.com/vangav/vos_backend/blob/master/README/06_error_response.md) are explained in depth in a separate section.

### Stop the service
1. in the terminal session where you started the service press **`control + d`**

> **Voila, it took a simple json file [controllers.json](https://github.com/vangav/vos_calculate_sum/blob/master/generator_config/controllers.json), one command line, few lines of code and the calculate_sum's backend is up and running in just few minutes. That's Vangav Backend.**

# Community

[Facebook Group: Vangav Open Source - Backend](http://www.fb.com/groups/575834775932682/)

[Facebook Page: Vangav](http://www.fb.com/vangav.f)

Third party communities for Vangav Backend
- play framework
- cassandra
- datastax

> Tag your question online (e.g.: stack overflow, etc ...) with **#vangav_backend** to easier find questions/answers online.

# Hassle-Free

Vangav Backend is open source because we know that starting a startup or developing a new service is already mission-impossible. You already got more than enough to worry about, Vangav Backend doesn't want to be one of those things you worry about.
> "Keep going, never give up".

Have fun!


# Next

1. [Project Contents](https://github.com/vangav/vos_backend/blob/master/README/01_project_contents.md)
2. [Intermediate Example (vos_geo_server)](https://github.com/vangav/vos_backend/blob/master/README/02_intermediate_example_vos_geo_server.md)
3. [Generated REST Service Structure](https://github.com/vangav/vos_backend/blob/master/README/03_generated_rest_service_structure.md)
4. [Generated REST Service Config Structure](https://github.com/vangav/vos_backend/blob/master/README/04_rest_service_config_structure.md)
5. [Request Structure](https://github.com/vangav/vos_backend/blob/master/README/05_request_structure.md)
6. [Error Response](https://github.com/vangav/vos_backend/blob/master/README/06_error_response.md)
7. [Using Dispatcher - Worker(s)](https://github.com/vangav/vos_backend/blob/master/README/07_dispatcher_worker.md)
8. [Debugging](https://github.com/vangav/vos_backend/blob/master/README/08_debug.md)
9. [Deploy](https://github.com/vangav/vos_backend/blob/master/README/09_deploy.md)
10. [Error Codes](https://github.com/vangav/vos_backend/blob/master/README/10_error_codes.md)

# README index

+ [data/README.md](https://github.com/vangav/vos_backend/blob/master/data)
+ [dist/README.md](https://github.com/vangav/vos_backend/tree/master/dist)
+ [doc/README.md](https://github.com/vangav/vos_backend/tree/master/doc)
+ [lib/README.md](https://github.com/vangav/vos_backend/tree/master/lib)
+ [prop/README.md](https://github.com/vangav/vos_backend/tree/master/prop)
+ [src/com/vangav/backend/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend)
+ src/com/vangav/backend/:
  + [backend_generator/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/backend_generator)
  + [cassandra/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/cassandra)
  + [compression/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/compression)
  + [content/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content)
  + [data_structures_and_algorithms/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/data_structures_and_algorithms)
  + [dispatcher/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/dispatcher)
  + [exceptions/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/exceptions)
  + [files/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/files)
  + [geo/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo)
  + [ids/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/ids)
  + [math/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/math)
  + [metrics/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/metrics)
  + [networks/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks)
  + [play_framework/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/play_framework)
  + [properties/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/properties)
  + [public_apis/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/public_apis)
  + [push_notifications/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications)
  + [security/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security)
  + [system/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/system)
  + [thread_pool/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/thread_pool)
  + [vangav_m/README.md](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/vangav_m)
+ [tools_bin/README.md](https://github.com/vangav/vos_backend/tree/master/tools_bin)
+ [vangav_backend_templates/README.md](https://github.com/vangav/vos_backend/tree/master/vangav_backend_templates)
  
  
