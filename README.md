# vangav backend
## build big tech 10x faster

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
| and more ... | periodic jobs, thread pools, facebook graph api, snow flake ids, compression, client generator, exceptions, geometry, metrics, deep vangav mighty integration, ... |

### [great templates](https://github.com/vangav)
| template | services | features | db tables | code: total | code: generated |
| -------- | -------- | -------- | --------- | ----------- | --------------- |
| instagram | 7 | 47 | 52 | 67,715 + 190,996 | 238,465 **(92.17%)** |
| analytics | 2 | 9 | 4 | 67,715 + 10,252 | 74,552 **(95.62%)** |
| whatsapp | 3 | 7 | 7 | 67,715 + 7,834 | 74,408 **(98.49%)** |
| geo_server | 1 | 3 | 3 | 67,715 + 2,691 | 70,300 **(99.85%)** |
| calculate_sum | 1 | 1 | 0 | 67,715 + 544 | 68,258 **(99.99%)** |

### roadmap
| time | effect |
| ---- | ------ |
| 5 min | first service [(vos_calculate_sum)](https://github.com/vangav/vos_calculate_sum) generated and running |
| 20 min | second service [(vos_geo_server)](https://github.com/vangav/vos_geo_server) generated and running |
| 5-7 days | finished all templates: command of all utilities, multi-service backends |
| 1-2 weeks | full command of modifying/extending the underlying backend |
| thereafter | best-in-class-level in implementing top class backend services, finishing a year's worth of work every month |

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
+ basic cassandra (optional for using cassandra)

# quick start example: [vos_calculate_sum](https://github.com/vangav/vos_calculate_sum)

vos_calculate_sum is a service that takes a two floats (a and b) request and returns a double (c) response representing the summation of a and b

### init
1. create a workspace directory `my_services` - this is the directory to contain both of vos_backend and all the services generated using it
2. download this `vos_backend.zip` project (from the green `clone or download` button up there) inside the workspace directory created in (1) and unzip it
3. **rename** downloaded vos_backend-master to vos_backend

### generate a new service
1. create a new directory `my_services/vos_calculate_sum`
2. copy `controllers.json` from `vos_backend/vangav_backend_templates/vos_calculate_sum/` to the directory `vos_calculate_sum` created in (1)
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
1. `cd` to my_services/vos_calculate_sum
2. execute the command `./_run.sh`

### try it out
1. open an internet browser page and type [`http://localhost:9000/calculate_sum?a=1.2&b=2.3`](http://localhost:9000/calculate_sum?a=1.2&b=2.3) - this returns **3.5**
2. play with `a` and `b` values in the request string in (1)
3. try issuing an invalid request (e.g.: set `a` to "xyz", don't set `b`, ...) to get a sense of how the default error response looks like ([error responses](https://github.com/vangav/vos_backend/blob/master/README/06_error_response.md) are explained in depth in a separate section)

### stop the service
in the terminal session where you started the service press `control + d`

## expand "calculate sum" to "calculator"

based on vos_calculate_sum, the following steps show how to expand the service to a more generic "calculator service"

### generate a new service
1. create a new directory `my_services/calculator`
2. copy `controllers.json` from `vos_backend/vangav_backend_templates/vos_calculate_sum/` to the directory `calculator` created in (1)
3. add as many features as desired by editing `my_services/calculator/controllers.json`; for example after adding a multiplication feature the `controllers` part of `my_services/calculator/controllers.json` will be as follows
> note: in vangav backend, inline comments are supported in json files

```json
  "controllers": [

    # CalculateSum
    {
      "is_preset": false,
      "name": "CalculateSum",
      "type": "GET",
      "request_params": [
        {
          "name": "a",
          "type": "FLOAT",
          "is_array": false,
          "optionality": "MANDATORY"
        },
        {
          "name": "b",
          "type": "FLOAT",
          "is_array": false,
          "optionality": "MANDATORY"
        }
      ],
      "response_type": "JSON",
      "response_params": [
        {
          "name": "c",
          "type": "double",
          "is_array": false
        }
      ]
    },

    # CalculateMultiplication
    {
      "is_preset": false,
      "name": "CalculateMultiplication",
      "type": "GET",
      "request_params": [
        {
          "name": "a",
          "type": "FLOAT",
          "is_array": false,
          "optionality": "MANDATORY"
        },
        {
          "name": "b",
          "type": "FLOAT",
          "is_array": false,
          "optionality": "MANDATORY"
        }
      ],
      "response_type": "JSON",
      "response_params": [
        {
          "name": "c",
          "type": "double",
          "is_array": false
        }
      ]
    }

  ]
```

4. open a terminal session and `cd` to `my_services/vos_backend/tools_bin`
5. execute the command `java -jar backend_generator.jar new calculator` to generate the service
6. enter `y` for using the config directory in order to use `controllers.json` for generating
7. enter `n` for generating a worker service (using workers is explained in a separate section)

### writing the service's logic code
+ repeat all the steps in the [writing the service's logic code](https://github.com/vangav/vos_backend/blob/master/README.md#writing-the-services-logic-code) section above then add to them the following steps to implement the multiplication feature's logic
+ open class `HandlerCalculateMultiplication.java` under package `com.vangav.vos_calculate_sum.controllers.calculate_multiplication`, method `processRequest` should be as follows in order to complete the request-to-response logic
```java
  @Override
  protected void processRequest (final Request request) throws Exception {

    // use the following request Object to process the request and set
    //   the response to be returned
    RequestCalculateMultiplication requestCalculateMultiplication =
      (RequestCalculateMultiplication)request.getRequestJsonBody();
    
    // set response's value
    ((ResponseCalculateMultiplication)request.getResponseBody() ).set(
      requestCalculateMultiplication.a * requestCalculateMultiplication.b);
  }
```

### start the service
1. `cd` to my_services/calculator
2. execute the command `./_run.sh`

### try it out
1. test sum: open an internet browser page and type [`http://localhost:9000/calculate_sum?a=1.2&b=2.3`](http://localhost:9000/calculate_sum?a=1.2&b=2.3) - this returns **3.5**
2. test multiplication: open an internet browser page and type [`http://localhost:9000/calculate_multiplication?a=1.2&b=2.3`](http://localhost:9000/calculate_sum?a=1.2&b=2.3) - this returns **2.76**

### stop the service
in the terminal session where you started the service press `control + d`

# next steps

1. [project's contents](https://github.com/vangav/vos_backend/blob/master/README/01_project_contents.md)
2. [next example with database (vos_geo_server)](https://github.com/vangav/vos_backend/blob/master/README/02_intermediate_example_vos_geo_server.md)
3. [generated service structure](https://github.com/vangav/vos_backend/blob/master/README/03_generated_rest_service_structure.md)
4. [service generator config structure](https://github.com/vangav/vos_backend/blob/master/README/04_rest_service_config_structure.md)
5. [request structure](https://github.com/vangav/vos_backend/blob/master/README/05_request_structure.md)
6. [error response](https://github.com/vangav/vos_backend/blob/master/README/06_error_response.md)
7. [usingd dispatcher - worker(s)](https://github.com/vangav/vos_backend/blob/master/README/07_dispatcher_worker.md)
8. [debugging](https://github.com/vangav/vos_backend/blob/master/README/08_debug.md)
9. [deploy](https://github.com/vangav/vos_backend/blob/master/README/09_deploy.md)
10. [error codes](https://github.com/vangav/vos_backend/blob/master/README/10_error_codes.md)

# other readme index

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
  
  
