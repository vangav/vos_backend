# Vangav Backend

Use Vangav Backend to generate backends for services like:
+ Social
+ Media sharing
+ Chat
+ Websites
+ Transportation
+ etc ... (any stateless service)

Vangav Backend generates services that are:
+ Light-weight
+ Stateless
+ Highly-scalable
+ Robust
+ SOA-ready
+ With a built-in dispatcher and a worker service
+ Eclipse-ready
+ based on [Play Framework](https://www.playframework.com/documentation/2.2.6/Home) (JAVA) and [Cassandra](https://archive.apache.org/dist/cassandra/2.1.2/)
+ Loaded with utilities like:
  + [Authentication](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security/authentication) (Facebook, Google, OAuth2 and transaction tokens)
  + [Facebook Graph API integration](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/public_apis)
  + [Push notifications](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications) (iOS and Android)
  + [Email clients](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/email)
  + [Twilio](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/twilio)
  + [REST client](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks/rest_client)
  + [Geo-services](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo)
  + [Compression](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/compression)
  + [Cryptography](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security/cryptography) (asymmetric, password hashing and two-way encryption)
  + [Math/geometry](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/math)
  + **[Vangav M](http://www.vangav.com/)**
+ **Vangav Backend generates most of the code needed**, you just connect the logic dots by putting in few lines of method calls.

Alternatively one can use Vangav Backend as a commons lib for any JAVA project using the main jar [vos_backend.jar](https://github.com/vangav/vos_backend/tree/master/dist). And [lib](https://github.com/vangav/vos_backend/tree/master/lib) contains more jars that are needed for using some of those utilities.

# System Requirements

+ unix operating system (e.g.: mac os, ubuntu, etc ...)
+ JAVA 8
+ python 2.7.10 (only for using cassandra)
    + to check python version type in the following command in a terminal session `python -V` and the output should be `Python 2.7.10`
    + then to install cassandra's python driver type the following command in a terminal session `pip install cassandra-driver==2.1.2`
+ installing python on mac os
  + [download python for Mac](https://www.python.org/ftp/python/2.7.10/python-2.7.10-macosx10.6.pkg)
  + open python-2.7.10-macosx10.6.pkg and follow the installer steps
+ installing python on ubuntu
  + `cd /usr/src`
  + `wget https://www.python.org/ftp/python/2.7.10/Python-2.7.10.tgz`
  + `tar xzf Python-2.7.10.tgz`
  + `cd Python-2.7.10`
  + `sudo ./configure`
  + `sudo make altinstall`

# Prerequisites

+ Basic JAVA
+ Basic Play Framework (optional for using Vangav Backend to build services)
+ Basic Cassandra/CQL (optional for using cassandra)

# Quick Start Example (vos_calculate_sum)

vos_calculate_sum is a service that takes two floats (a and b) request and returns a double (c) response representing the summation of a and b.

> Check out a finished version of this service at **https://github.com/vangav/vos_calculate_sum**

### Init
1. create a workspace directory "**my_services**" - this is the directory to contain both of vos_backend and all the services generated using it
2. download this (**vos_backend.zip**) project inside the workspace directory created in (1) and unzip it
3. **rename** downloaded vos_backend-master to vos_backend

### Generate a new service
1. Create a new directory "**my_services/vos_calculate_sum**".
2. **Copy** **controllers.json** from vos_backend/vangav_backend_templates/vos_calculate_sum/ to the directory vos_calculate_sum created in (1).
3. Open a terminal session and **cd** to my_services/vos_backend/tools_bin.
4. Execute the command **`java -jar backend_generator.jar new vos_calculate_sum`** to generate the Vangav Backend Service.
5. Enter **`Y`** for using the config directory in order to use **controllers.json** for generating the new service.
6. Enter **`Y`** to generate an eclipse-compatible project.
7. Enter **`N`** for generating a worker service. Using workers is explained in a separate section.

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
  
  
