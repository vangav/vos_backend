# [Vangav Backend](http://www.vangav.com/)
### [back-the-end]

[vangav.com](http://www.vangav.com/)

# Index
  + [Intro](https://github.com/vangav/vos_backend/blob/master/README.md#intro)
  + [Contents](https://github.com/vangav/vos_backend/blob/master/README.md#contents)
  + [System Requirements](https://github.com/vangav/vos_backend/blob/master/README.md#system-requirements)
  + [Prerequisites](https://github.com/vangav/vos_backend/blob/master/README.md#prerequisites)
  + [Using Vangav Backend as a commons lib](https://github.com/vangav/vos_backend/blob/master/README.md#using-vangav-backend-as-a-commons-lib)
  + [Quick Start Example (vos_calculate_sum)](https://github.com/vangav/vos_backend/blob/master/README.md#quick-start-example-vos_calculate_sum)
  + [Intermediate Example (vos_geo_server)](https://github.com/vangav/vos_backend/blob/master/README.md#intermediate-example-vos_geo_server)
  + [Generated REST Service Structure](https://github.com/vangav/vos_backend/blob/master/README.md#generated-rest-service-structure)
  + [REST Service Config Structure](https://github.com/vangav/vos_backend/blob/master/README.md#rest-service-config-structure)
  + [Request structure](https://github.com/vangav/vos_backend/blob/master/README.md#request-structure)
  + [Community](https://github.com/vangav/vos_backend/blob/master/README.md#community)
  + [Dependencies](https://github.com/vangav/vos_backend/blob/master/README.md#dependencies)
  + [Hassle-Free](https://github.com/vangav/vos_backend/blob/master/README.md#hassle-free)

# Intro

Vangav Backend is an open source (license-free) backend. vos (in vos_backend) stands for (vangav open source).

**As a REST service generator.** Vangav Backend can be used to generate REST services. When using Vangav Backend, one doesn't start by writing code. Instead the service's entry points (controllers) and databse tables/queries are defined in minimal json files and Vangav Backend's generators takes care of adding the majority of the code a service needs, while maintaining the highest performance and design quality we know how to do. Generated REST service also include Vangav Backend as a commons lib.

**As a commons lib.** Vangav Backend's can also be used as a commons library, so to utilize it built-in utilities (listed below) for any project.

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
  + built-in configurable [threadpools](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/thread_pool) and latch threads
  + **[Vangav M](http://www.vangav.com/)** deeply integrated

# Contents

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

+ unix operating system (e.g.: mac os, ubuntu, etc ...)
+ JAVA 8
+ python 2.6 (only for using cassandra)

# Prerequisites

+ Basic JAVA
+ Basic Play Framework (optional for using Vangav Backend to build services)
+ Basic Cassandra/CQL (optional for using cassandra)

# Using Vangav Backend as a commons lib

+ Download **vos_backend.jar** from https://github.com/vangav/vos_backend/tree/master/dist and add it to your project's dependencies.
+ For some of the built-in utilities (e.g.: [email clients](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks), [push notifications](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications), etc ...) more libraries need to be added to your project's dependencies; those libraries can be found at https://github.com/vangav/vos_backend/tree/master/lib

# Quick Start Example (vos_calculate_sum)

vos_calculate_sum is a service that takes two floats (a and b) request and returns a double (c) response representing the summation of a and b.

> Check out a finished version of this service at **https://github.com/vangav/vos_calculate_sum**

### Init
1. create a workspace directory "**my_services**" - this is the directory to contain both of vos_backend and all the services generated using it
2. download this (**vos_backend.zip**) project inside the workspace directory created in (1) and unzip it
3. **rename** downloaded vos_backend-master to vos_backend

### Generate a new service
1. create a new directory "**my_services/vos_calculate_sum**"
2. **copy** **controllers.json** from vos_backend/vangav_backend_templates/vos_calculate_sum/ to the directory vos_calculate_sum created in (1)
3. open a terminal session and **cd** to my_services/vos_backend/tools/bin
4. execute the command **`java -jar backend_generator.jar new vos_calculate_sum`**
5. enter **`Y`** for using the config directory
6. enter **`Y`** to generate an eclipse-compatible project
7. enter **`N`** for generating a worker service

### Writing the service's logic code
+ Open eclipse and **import** vos_calculate_sum project. File > import > General > Existing Projects into Workspace > Next > set "Select root directory" to my_services > under Projects make sure that vos_calculate_sum is selected > Finish.
+ Double check the java version used for compiling the project. right click the project > properties > Java Compiler > Enable project specific settings > Compiler compliance level > 1.7 or 1.8
+ Open class **[HandlerCalculateSum.java](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/HandlerCalculateSum.java)** under package `com.vangav.vos_calculate_sum.controllers.calculate_sum`, method **[`processRequest`](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/HandlerCalculateSum.java#L67)** should be as follows
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

### Stop the service
1. in the terminal session where you started the service press **`control + d`**

> **Voila, it took a simple json file [controllers.json](https://github.com/vangav/vos_calculate_sum/blob/master/generator_config/controllers.json), one command line, few lines of code and the calculate_sum's backend is up and running in just few minutes. That's Vangav Backend.**

# Intermediate Example (vos_geo_server)

vos_geo_server is a service that takes a latitude/longitude request and returns the reverse geo code (continent, country, major city, city). It also keeps track of queried continents/countries to also provide lists of sorted top queried continents/countries.

> Check out a finished version of this service at **https://github.com/vangav/vos_geo_server**

### Init
1. create a workspace directory "**my_services**" - this is the directory to contain both of vos_backend and all the services generated using it
2. download this (**vos_backend.zip**) project inside the workspace directory and unzip it
3. **rename** downloaded vos_backend-master to vos_backend

### Generate a new service
1. create a new directory "**my_services/vos_geo_server**"
2. **copy** **controllers.json** and **gs_top.keyspace** from vos_backend/vangav_backend_templates/vos_geo_server/ to the directory vos_geo_server created in (1)
3. open a terminal session and **cd** to my_services/vos_backend/tools/bin
4. execute the command **`java -jar backend_generator.jar new vos_geo_server`**
5. enter **`Y`** for using the config directory
6. enter **`Y`** to generate an eclipse-compatible project
7. enter **`N`** for generating a worker service

### Init the service's cassandra database
1. **cd** to my_services/vos_geo_server/cassandra/cql/
2. execute the command **`./_start_cassandra.sh`** - to start cassandra
3. **cd** to my_services/vos_geo_server/cassandra/cql/drop_and_create/
4. execute the command **`./_execute_cql.sh gs_top_dev.cql`**

### Init service's data
1. **copy** the contents of the directory my_services/vos_backend/data/geo/reverse_geo_coding/ to my_services/vos_geo_server/conf/data/geo/reverse_geo_coding/

### Start the service
1. **cd** to my_services/vos_geo_server
2. execute the command **`./_run.sh`**

### Preliminary testing
1. open an internet browser page and type **`http://localhost:9000/reverse_geo_code?latitude=49&longitude=11`** - this returns an empty response

### Stop the service
1. in the terminal session where you started the service press **`control + d`**

### Writing the service's logic code
+ Open eclipse and **import** vos_geo_server project. File > import > General > Existing Projects into Workspace > Next > set "Select root directory" to my_services > under Projects make sure that vos_geo_server is selected > Finish.
+ Double check the java version used for compiling the project. right click the project > properties > Java Compiler > Enable project specific settings > Compiler compliance level > 1.7 or 1.8
+ Under package **com.vangav.vos_geo_server** add a new package **[common](https://github.com/vangav/vos_geo_server/tree/master/app/com/vangav/vos_geo_server/common)**
+ In the created package in the previous step add a new class **[InitIndexInl](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/common/InitIndexInl.java)** with the following implementation:
```java
package com.vangav.vos_geo_server.common;

import com.datastax.driver.core.ResultSet;
import com.vangav.vos_geo_server.cassandra_keyspaces.gs_top.NameIndex;

/**
 * InitIndexInl has inline static method to init Cassandra's gs_top.index
 *   table by inserting index_key values (continents and countries)
 * */
public class InitIndexInl {

  public static final String kContinentsIndexKey = "continents";
  public static final String kCountriesIndexKey = "countries";
  /**
   * initIndex
   * does first-run initialization for gs_top.index table
   * @throws Exception
   */
  public static void initIndex () throws Exception {
    
    ResultSet resultSet = NameIndex.i().executeSyncSelect(kContinentsIndexKey);
    
    if (resultSet.isExhausted() == true) {
      
      NameIndex.i().executeSyncInsert(kContinentsIndexKey);
    }
    
    resultSet = NameIndex.i().executeSyncSelect(kCountriesIndexKey);
    
    if (resultSet.isExhausted() == true) {
      
      NameIndex.i().executeSyncInsert(kCountriesIndexKey);
    }
  }
}
```
+ in **[default_package/Global.java](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java)** after the [following line](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java#L75)
```java
Countries.loadTable();
```
add the [following lines](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java#L79)
```java
ReverseGeoCoding.load();
InitIndexInl.initIndex();
```
+ open class **[HandlerReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java)**, method **[`processRequest`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L77)** should be as follows
```java
  @Override
  protected void processRequest (final Request request) throws Exception {

    // use the following request Object to process the request and set
    //   the response to be returned
    RequestReverseGeoCode requestReverseGeoCode =
      (RequestReverseGeoCode)request.getRequestJsonBody();
    
    // get geo hash
    String geoHash =
      GeoHash.geoHashStringWithCharacterPrecision(
        requestReverseGeoCode.latitude,
        requestReverseGeoCode.longitude,
        12);
    
    // get reverse geo code
    ReverseGeoCode reverseGeoCode =
      ReverseGeoCoding.i().getReverseGeoCode(
        requestReverseGeoCode.latitude,
        requestReverseGeoCode.longitude);
    
    // set response
    ((ResponseReverseGeoCode)request.getResponseBody() ).set(
      requestReverseGeoCode.latitude,
      requestReverseGeoCode.longitude,
      geoHash,
      reverseGeoCode.getCity(),
      reverseGeoCode.getMajorCity(),
      reverseGeoCode.getCountryCode(),
      reverseGeoCode.getCountry(),
      reverseGeoCode.getContinentCode(),
      reverseGeoCode.getContinent() );
  }
```
+ then add the following [`method`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L111) in class **[HandlerReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java)**
```java
  @Override
  protected void afterProcessing (
    final Request request) throws Exception {

    // get request Object
    RequestReverseGeoCode requestReverseGeoCode =
      (RequestReverseGeoCode)request.getRequestJsonBody();
    
    // get reverse geo code
    ReverseGeoCode reverseGeoCode =
      ReverseGeoCoding.i().getReverseGeoCode(
        requestReverseGeoCode.latitude,
        requestReverseGeoCode.longitude);
    
    // update continents index
    NameIndex.i().executeAsyncUpdate(
      new HashSet<String>(Arrays.asList(reverseGeoCode.getContinent() ) ),
      InitIndexInl.kContinentsIndexKey);
    
    // update countries index
    NameIndex.i().executeAsyncUpdate(
      new HashSet<String>(Arrays.asList(reverseGeoCode.getCountry() ) ),
      InitIndexInl.kCountriesIndexKey);
    
    // update continents counter's value
    Continents.i().executeAsyncUpdateCounterValue(
      reverseGeoCode.getContinent() );
    
    // update countries counter's value
    Countries.i().executeAsyncUpdateCounterValue(
      reverseGeoCode.getCountry() );
  }
```
+ under package **[top_continents](https://github.com/vangav/vos_geo_server/tree/master/app/com/vangav/vos_geo_server/controllers/top_continents)** add class **[ResponseTopContinent](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/top_continents/response_json/ResponseTopContinent.java).java** with the following code
```java
package com.vangav.vos_geo_server.controllers.top_continents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ResponseTopContinent represents the response's top-continent
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseTopContinent {
  
  /**
   * Constructor ResponseTopContinent
   * @param continentName
   * @param continentCount
   * @return new ResponseTopContinent Object
   * @throws Exception
   */
  @JsonIgnore
  public ResponseTopContinent (
    String continentName,
    long continentCount) throws Exception {
    
    this.continent_name = continentName;
    this.continent_count = continentCount;
  }

  @JsonProperty
  public String continent_name;
  @JsonProperty
  public long continent_count;
}
```
+ modify class **[ResponseTopContinents.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/top_continents/ResponseTopContinents.java)** to be as follows:
```java
package com.vangav.vos_geo_server.controllers.top_continents;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vangav.backend.play_framework.request.response.ResponseBodyJson;

/**
 * ResponseTopContinents represents the response's structure
 * */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseTopContinents extends ResponseBodyJson {

  @Override
  @JsonIgnore
  protected String getName () throws Exception {

    return "TopContinents";
  }

  @Override
  @JsonIgnore
  protected ResponseTopContinents getThis () throws Exception {

    return this;
  }

  @JsonProperty
  public ResponseTopContinent[] top_continents;
  
  @JsonIgnore
  public void set (ResponseTopContinent[] top_continents) {
    
    this.top_continents = top_continents;
  }
}
```
+ in class **[HandlerTopContinents.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/top_continents/HandlerTopContinents.java)** method **[`processRequest`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/top_continents/HandlerTopContinents.java#L78)** should be as follows:
```java
  @Override
  protected void processRequest (final Request request) throws Exception {
    
    // select continents from gs_top.index
    ResultSet resultSet =
      NameIndex.i().executeSyncSelect(InitIndexInl.kContinentsIndexKey);
    
    // no continents queried before?
    if (resultSet.isExhausted() == true) {
      
      ((ResponseTopContinents)request.getResponseBody() ).set(
        new ResponseTopContinent[0] );
      
      return;
    }
    
    // extract continents index
    Set<String> continentsIndex =
      resultSet.one().getSet(
        NameIndex.kIndexValuesColumnName,
        String.class);
    
    // init top continents
    ArrayList<Pair<String, Long> > topContinents =
      new ArrayList<Pair<String, Long> >();
    
    // for each continent
    for (String continent : continentsIndex) {
      
      // select continent's counter-value
      resultSet = Continents.i().executeSyncSelectCounterValue(continent);
      
      // no data? skip
      if (resultSet.isExhausted() == true) {
        
        continue;
      }
      
      // store continent name-counter pair
      topContinents.add(
        new Pair<String, Long>(
          continent,
          resultSet.one().getLong(Continents.kCounterValueColumnName) ) );
    }
    
    // sort results ascending
    Collections.sort(topContinents, new Comparator<Pair<String, Long> > () {

      @Override
      public int compare (Pair<String, Long> x, Pair<String, Long> y) {

        return Long.compare(x.getSecond(), y.getSecond() );
      }
    } );
    
    // reverse sorted results to put them in a descending order
    Collections.reverse(topContinents);
    
    // fill response array
    
    ResponseTopContinent[] responseArray =
      new ResponseTopContinent[topContinents.size() ];
    
    for (int i = 0; i < topContinents.size(); i ++) {
      
      responseArray[i] =
        new ResponseTopContinent(
          topContinents.get(i).getFirst(),
          topContinents.get(i).getSecond() );
    }
    
    // set response
    ((ResponseTopContinents)request.getResponseBody() ).set(responseArray);
  }
```
+ repeat the last 3 steps for countries

### Try it out
+ start the service as explained above then open an internet browser page and type any of
  + **`http://localhost:9000/reverse_geo_code?latitude=49&longitude=11`** - play around with the latitude and longitude values
  + **`http://localhost:9000/top_continents`**
  + **`http://localhost:9000/top_countries`**
+ stop the service as explained above

> **Voila, few minutes, few lines of code and the geo server's backend is up and running.**

# Generated REST Service Structure

> This section uses the vos_geo_server example, yet it's the same structure for all REST services generated using Vangav Backend.

### [scripts](https://github.com/vangav/vos_geo_server)

The following scripts are generated for every Vangav Backend Service.

+ [_run.sh](https://github.com/vangav/vos_geo_server/blob/master/_run.sh) runs the service in dev mode.
+ [_clean.sh](https://github.com/vangav/vos_geo_server/blob/master/_clean.sh) cleans the service's build.
+ [_compile.sh](https://github.com/vangav/vos_geo_server/blob/master/_compile.sh) compiles the service.
+ [_debug.sh](https://github.com/vangav/vos_geo_server/blob/master/_debug.sh) run the service in debug mode. Debugging the code can happen in eclipse as explained later.
+ [_dist.sh](https://github.com/vangav/vos_geo_server/blob/master/_dist.sh) generates a production executable for the service to deploy it on the production server(s).

### [conf](https://github.com/vangav/vos_geo_server/tree/master/conf)

The conf directory contains all the configuration files needed by the service during run-time.

+ [routes](https://github.com/vangav/vos_geo_server/blob/master/conf/routes) and [application.conf](https://github.com/vangav/vos_geo_server/blob/master/conf/application.conf) are used by play framework to define the servie's entry points and the service's configuration respectively.

+ [prop](https://github.com/vangav/vos_backend/tree/master/prop) contains all the service's properties files, some of these files gets added (and set) for a Vangav Backend generated service. Add more properties files as needed by copying them from Vangav Backend to your service then optionally set its properties' values.
  + [android_notification_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/android_notification_properties.prop) contains the properties needed to use the Android push notifications utility.
  + [apple_notification_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/apple_notification_properties.prop) contains the properties needed to use the iOS push notifications utility.
  + [cassandra_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/cassandra_properties.prop) contains the properties needed to use Cassandra. Like Cassandra's deployment topology, retries, consistency levels, etc...
  + [dispatcher_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/dispatcher_properties.prop) defines the worker-services deployment topology in order to use the service's dispatcher.
  + [facebook_graph_api_edge_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/facebook_graph_api_edge_properties.prop) contains the properties that controls how to fetch Facebook Graph API's edges. In the current state of Vangav Backend, requesting an edges fetches one page only; however all the tools needed to fetch more pages are provided and can be used externally.
  + [java_email_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/java_email_properties.prop) contains the properties needed to use the JAVA email utility.
  + [mail_gun_email_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/mail_gun_email_properties.prop) contains the properties needed to use the mailgun email utility.
  + [param_validator_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/param_validator_properties.prop) contains properties that controls how some of a request's params get validated.
  + [request_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/request_properties.prop). For each request a Vangav Backend Service receives, there are sequential steps that the request's processing can go through. Those steps can be switched on/off from this properties file.
  + [response_error_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/response_error_properties.prop). In case there's a BAD_REQUEST or an INTERNAL_SERVER_ERROR, Vangav Backend's Services return a JSON response containing the problem's details. This properties file controls which item(s) of the problem's details gets sent to the client making the failed request.
  + [thread_pool_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/thread_pool_properties.prop) can define the size of each thread pool used by Vangav Backend. Properties of this file are disabled by default as Vangav Backend **dynamically** sets values for this properties file depending on the machine on which the service is running on. The values being dynamically set are to the best of our knowledge optimal based on testing and on what LinkedIn does on their own backend. This properties file is provided to give users the option to set their own values.
  
### [cassandra/phriction](https://github.com/vangav/vos_geo_server/tree/master/cassandra/phriction)

+ phriction is the wiki tool of [phabricator](https://secure.phabricator.com/book/phabricator/article/installation_guide/)
+ this directory has a `keyspace_name.phriction` file per database's keyspace hace a phriction-styled documentation of the keyspace, paste the contents of these files into a phriction's page to have an up-to-date wiki for your database.

### [cassandra/cql](https://github.com/vangav/vos_geo_server/tree/master/cassandra/cql)

+ CQL (Cassandra Query Language) is the equivalent of SQL for Cassandra.
+ Scripts [_start_cassandra.sh](https://github.com/vangav/vos_geo_server/blob/master/cassandra/cql/_start_cassandra.sh) and [_cassandra_status.sh](https://github.com/vangav/vos_geo_server/blob/master/cassandra/cql/_cassandra_status.sh) are used to start Cassandra and check its status respectively.
+ In order to init/update/delete the database the scripts in each of the following directories are used. Cassandra has to be already running to be able to run these scripts. The **`_execute_cql.sh`** script in each of those directories takes the .cql file as an argument to execute it.
  + [create_if_doesnot_exist](https://github.com/vangav/vos_geo_server/tree/master/cassandra/cql/create_if_doesnot_exist) has script(s) per-keyspace to create the keyspace and its table(s) if they don't already exsist. Used primarily to update Cassandra on keyspace/table levels.
  + [drop](https://github.com/vangav/vos_geo_server/tree/master/cassandra/cql/drop) has script(s) per-keyspace to drop a keyspace and its tables. Used primarily to clear a dev machine after testing.
  + [drop_and_create](https://github.com/vangav/vos_geo_server/tree/master/cassandra/cql/drop_and_create) has script(s) per-keyspace to drop (if already exsists) and create the keyspace and its table(s). Used primarily to initialize the keyspaces and their tables, beware that it overwrites any keyspaces with the same name.
  
### [cassandra_updater](https://github.com/vangav/vos_geo_server/tree/master/cassandra_updater)

+ During the development of a new service as the database evolves, it's becomes complicated and expensive to continuously update all of:
  + JAVA clients
  + CQL scripts
  + phriction-wiki
+ To solve this problem [cassandra_updater](https://github.com/vangav/vos_geo_server/tree/master/cassandra_updater) is provided and can be used as follows:
  1. open a terminal and **cd** to [vos_geo_server/generator_config/](https://github.com/vangav/vos_geo_server/tree/master/generator_config)
  2. add/remove/edit .keyspace files as needed
  3. **cd** to [cassandra_updater](https://github.com/vangav/vos_geo_server/tree/master/cassandra_updater)
  4. execute the command **`java -jar cassandra_keyspaces_updater.jar	`**
  5. confirm by entering **`Y`**
+ Check out [cassandra_updater_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/cassandra_updater/cassandra_updater_properties.prop) and only edit it if needed (usually never needed).

### [lib](https://github.com/vangav/vos_geo_server/tree/master/lib)

+ lib contains the jar files on which a Vangav Backend's service depends. Initially Vangav Backend's generators adds all the jars initially needed for the service to work properly.
+ For some of the built-in utilities (e.g.: [email clients](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks), [push notifications](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications), etc ...) more libraries need to be added to your project's dependencies; those libraries can be found at https://github.com/vangav/vos_backend/tree/master/lib

### [generator_config](https://github.com/vangav/vos_geo_server/tree/master/generator_config)

+ generator_config includes the files used to generate this Vangav Backend Service
+ as explained above, cassandra_updater relies on the .keyspace files to update the service's database
+ controllers.json file is currently just for reference

### [public](https://github.com/vangav/vos_geo_server/tree/master/public)

+ public directory keeps the public files (images, java scripts and style sheets) needed for web pages (if any) provided by the service

### [app](https://github.com/vangav/vos_geo_server/tree/master/app)

+ app is the directory containing all the services source code
+ [Global.java](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java) extends play framework's GlobalSettings to override some functionalities like `beforeStart`, `onStart`, `onStop`, etc ...
  + [`beforeStart`](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java#L57) is used by Vangav Backend to load properties, connect to cassandra and prepare cassandra's perpared statements.
  + [`onStop`](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java#L108) is used by Vangav Backend to shutdown the thread pools and disconnect from cassandra
+ [views](https://github.com/vangav/vos_geo_server/tree/master/app/views) is created by play framework to keep the service's html pages
+ [controllers](https://github.com/vangav/vos_geo_server/tree/master/app/controllers) is created by play framework. Not used by Vangav Backend and to be left as is.

### [app/com/vangav/vos_geo_server/cassandra_keyspaces/](https://github.com/vangav/vos_geo_server/tree/master/app/com/vangav/vos_geo_server/cassandra_keyspaces)

+ This directory contains all the generated database clients for cassandra's tables, where each keyspace is represented by a directory and each keyspace's table is represented by a class.

+ Each table's class like [Continents.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java) has the following structure:
  + [Lines (41-72)](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L41): starts with a block comment listing the table's structure and prepared statements
  + [Lines (73-173)](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L73): is used by Vangav Backend to initialize the table and prepare its prepared statements
  + [Then](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L175) for each of the table's prepared statements the following five methods are provided
    + [**`getQuery`**](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L188) returns the raw Query Object to use it however you like.
    + [**`getQueryDispatchable`**](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L202) returns a dispatchable version of the query to be added to the service's worker dispatcher. e.g.: `request.getDispatcher().addDispatchMessage(getQueryDispatchable() );`.
    + [**`getBoundStatement`**](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L219) returns a the query's BoundStatement. Usually used for:
      1. Add it to a BatchStatement
      2. Execute multiple BoundStatements synchronously since it's faster than executing those statements sequentially. Since internally all these statements get executed asynchronously.
    + [**`executeAsync`**](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L234) executes the query asynchronously and returns a ResultSetFuture Object which holds the future result of executing the query.
    + [**`executeSync`**](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L250) is a blocking method that executes the query synchronously then returns a ResultSet Object containing the result of executing the query.
    
### [app/com/vangav/vos_geo_server/controllers/](https://github.com/vangav/vos_geo_server/tree/master/app/com/vangav/vos_geo_server/controllers)

+ This directory contains all the generated controllers (entry points) as defined in the project's config [controllers.json](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json)

+ [CommonPlayHandler.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java) is the parent class for all the controllers' handlers. A controller's handler is the class the implements the controller's request-to-response logic. In Vagav Backend processing a request goes through sequential-optional steps. To enable one or more of these steps, they must be set to true in [request_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/request_properties.prop) as explained above. This class provides the ability to implement/override those sequential-optional steps as follows:
  + [`checkSource`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L43) is used to check the requests source. e.g.: if the service only accepts requests from mobile clients, a set of mac addresses, etc ...
  + [`throttle`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L50) is used to detect and prevent spammy behavior.
  + [`authenticateRequest`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L57) is used to authenticate a request. e.g.: using the build-in OAuth2, Facebook Login, Google Login, etc ...
  + [`afterProcessing`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L64): override this method per-controller-handler to do further processing after a request's response is sent back to the client. e.g.: use it for a blocking operation like push notifications which doesn't impact the service's core functionality in case of failure and shouldn't delay sending back the request's response.
  + `dispatch....`: methods starting with dispatch are processed after a request's response is returned and it's encouraged to dispatch the operations of these method to the service's worker.
  + [`dispatchDefaultOperations`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L71): implement this method to override a service-wide after-response operation for all of the service's controllers. e.g.: update user's last-active-time.
  + [`dispatchPushNotifications`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L78): override this method per-controller to dispatch/process push notifications.
  + [`dispatchAnalysis`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L85): override this method per-controller to dispatch/process analysis.
  + [`dispatchDefaultAnalysis`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L92): implement to dispatch service-wide analysis for all controllers. e.g.: to keep track of request-to-response time.
  + [`dispatchLogging`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L99): override this method per-controller to dispatch/process logging.
  + [`dispatchDefaultLogging`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L106): implement to dispatch service-wide logging for all controllers. e.g.: log request/response/status-code.
  + [`absorbUnhandledExceptions`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L117): if Vangav Backend fails to handle an exception, it forwards the exception to this method to be absorbed and dealt with as needed. e.g.: send a notification email.
  
+ For each controller, Vangav Backend generates a directory with four classes. e.g.: [reverse_geo_code](https://github.com/vangav/vos_geo_server/tree/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code)
  + [ControllerReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ControllerReverseGeoCode.java) represents the entry point for the controller. The [routes](https://github.com/vangav/vos_geo_server/blob/master/conf/routes#L14) config points to [getReverseGeoCoe](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ControllerReverseGeoCode.java#L44) method in this class. No need to edit this class.
  + [RequestReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/RequestReverseGeoCode.java) represents the controller's request. This class's parent class provides the method [`isValidParam`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java#L203) to check if an OPTIONAL param is valid or not.
  + [ResponseReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ResponseReverseGeoCode.java) represents the controller's response. Each response class provides a [`set`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ResponseReverseGeoCode.java#L79) method to set the response's content at the [end](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L98) of the handler's processRequest method.
  + [HandlerReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java) is the class where a controller's request-to-response logic should be implemented. Generated services include TODO comments where the request-to-response logic should be implemented to make it easier to find :) [`processRequest`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L77) method is the main part where the controller's logic should be implemented, ending with calling the response's set method as explained above. Optionally override more methods from [CommonPlayHandler.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java) as explained above.
  
### [vangav_m](https://github.com/vangav/vos_geo_server/tree/master/vangav_m)

+ Follow the following steps to use [Vangav M](http://vangav.com/) within a Vangav Backend Service
  1. Under `vangav_m/solutions` add/remove/edit .mlang files for Vangav M JAVA solutions. You can find many examples on http://vangav.com/.
  2. **cd** to `my_services/vos_geo_server/vangav_m`
  3. Execute **`java -jar vangav_m_json_client.jar`**. This updates the service's Vangav M solutions and link them.
  4. In any of the service's source files (e.g. [HandlerReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java)) start using your Vangav M solutions as explained on [Vangav M](http://vangav.com/) website (set, process, get).

# REST Service Config Structure

When using Vangav Backend to generate a REST service, adding config files is optional but highly recommended as it saves the majority of the cost needed to implement the REST service. Config consists of one mandatory **[controllers.json](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json)** file and zero-to-many database config files **[keyspace_name.keyspace](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace)** (one file per database keyspace).

+ All config files have a **JSON** structure, however in Vangav Backend JSON supports inline comments. A comment line is any lines starting with zero or more spaces followed by a #. **[Comment Example](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L16)**

### [controllers.json](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json) structure

+ Here's an empty template for controllers.json

```json
{
  "java_package": "",
  "check_source": false,
  "throttle": false,
  "validate_param": true,
  "authenticate": true,
  "after_response": true,
  "after_processing": true,
  "default_operations": false,
  "notifications": true,
  "analysis": false,
  "logging": false,

  "controllers": [
    {
      "is_preset": false,
      "name": "",
      "type": "POST",
      "request_params": [
        {
          "name": "",
          "type": "",
          "is_array": false,
          "optionality": "MANDATORY"
        }
      ],
      "response_type": "JSON",
      "response_params": [
        {
          "name": "",
          "type": "",
          "is_array": false
        }
      ]
    }
  ]
}
```

+ It starts with [`java_package`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L2) where the root java package for your service's code should be defined. e.g.: com.vangav

+ Followed by a set of booleans defining the which steps should request-processing goes through. Implementation of each of these functionalities goes in per-code-ref in the following tables, checking on whether to process each step or not happens in [ParentPlayHandler.java](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java) and these boolean values can be altered anytime from [request_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/request_properties.prop).

| boolean | code ref | definition |
| ------- | -------- | ---------- |
| [`check_source`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L3) | [`ref`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L43) | is used to turn on/off checking requests source. e.g.: if the service only accepts requests from mobile clients, a set of mac addresses, etc ... |
| [`throttle`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L4) | [`ref`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L50) | is used to switch on/off the detection and prevention of spammy behavior |
| [`validate_param`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L5) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L269) | is used to switch on/off validating requests' parameters. invalid mandatory params result in a 400 BAD_REQUEST while invalid optional params gets tracked and can be checked using [`isValidParam`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java#L203)|
| [`authenticate`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L6) | [`ref`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L57) | is used to switch on/off authenticating a request. e.g.: using the build-in OAuth2, Facebook Login, Google Login, etc ... |
| [`after_response`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L7) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L373) | is used as a service-wide on/off switch for all post-response processing (i.e.: all of the next switches are dismissed if this switch is off (false) ) |
| [`after_processing`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L8) | [`ref`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L64) |  is used to switch on/off after processing. Override this method per-controller-handler to do further processing after a request's response is sent back to the client. e.g.: use it for a blocking operation like push notifications which doesn't impact the service's core functionality in case of failure and shouldn't delay sending back the request's response |
| [`default_operations`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L9) | [`ref`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L71) |   is used to switch on/off default operations. Implement this method to override a service-wide after-response operation for all of the service's controllers. e.g.: update user's last-active-time |
| [`notifications`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L10) | [`ref`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L78) | is used to switch on/off sending push notifications |
| [`analysis`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L11) | [`ref`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L85) | is used to switch on/off doing analysis |
| [`logging`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L12) | [`ref`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L99) | is used to switch on/off doing loggin |

+ Followed by [`controllers`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L14) array where each elements defines one of the service's controllers (entry points).

| element | definition |
| ------- | ---------- |
| [`is_preset`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L18) | keep it false, not currently in use. needed for a coming feature that allows adding predefined controllers including logic/database/etc ... (e.g.: user management controllers - login/logout/reset/deactivate/...) |
| [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L19) | controller's name, recommended to be in CamelCase |
| [`type`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L20) | set it to `GET` or `POST` |
| [`request_params`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L21) | optionally add request's params to this array|
| [`response_type`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L35) | set it to `JSON`, `FILE` or `HTML` |
| [`response_params`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L36) | optionally add response's params in case of a `JSON` response_type |

  + Each element in [`request_params`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L21) has [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L23) (recomended to be lowerCamelCase), [`type`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L24) which defines how a param should be validated, [`is_array`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L25) to define if this is an array param or not (arrays in GET requests are comma ',' separated) and [`optionality`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L26) which can either be `MANDATORY` or `OPTIONAL`. The following table lists possible param types from [ParamType.java](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java) and describes how each of them is validated. Any param that's null or empty is invalid regardless of its type.
  
  | type | validation method | validation |
  | ---- | ----------------- | ---------- |
  | [`BOOLEAN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L44) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L281) | valid values (true, false) |
  | [`SHORT`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L46) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L297) | valid short value |
  | [`INT`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L47) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L321) | valid int value |
  | [`LONG`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L48) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L345) | valid long value |
  | [`FLOAT`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L49) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L369) | valid float value |
  | [`DOUBLE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L50) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L393) | valid double value |
  | [`LATITUDE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L52) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L417) | valid double >= -90.0 & <= 90.0 |
  | [`LONGITUDE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L53) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L441) | valid double >= -180.0 & <= 180.0 |
  | [`ALTITUDE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L54) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L465) | valid double |
  | [`ALPHA_NUMERIC`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L56) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L492) | |
  | [`ALPHA_NUMERIC_SPACE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L57) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L508) | |
  | [`ALPHA_NUMERIC_DASH`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L58) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L523) | |
  | [`ALPHA_NUMERIC_UNDERSCORE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L59) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L538) | |
  | [`ALPHA_NUMERIC_SPACE_DASH`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L60) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L553) | |
  | [`ALPHA_NUMERIC_SPACE_UNDERSCORE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L61) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L569) | |
  | [`ALPHA_NUMERIC_DASH_UNDERSCORE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L62) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L585) | |
  | [`ALPHA_NUMERIC_SPACE_DASH_UNDERSCORE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L63) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L601) | |
  | [`NAME`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L64) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L627) | unicode characters, spaces, dots, quotations. lengths >= 1 and <= 100 |
  | [`USER_NAME`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L65) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L655) | a-z, A-Z, 0-9, dots, dashes, underscores. length >= 1 and <= 25 |
  | [`DATE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L66) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L680) | d/m/yy - dd/mm/yyyy |
  | [`UUID`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L67) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L696) | validates java.util.UUID |
  | [`AUTH_CODE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L69) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L725) | alpha-numeric and 32 characters long |
  | [`ACCESS_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L70) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L746) | alpha-numeric and 32 characters long |
  | [`REFRESH_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L71) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L767) | alpha-numeric and 32 characters long |
  | [`EMAIL`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L73) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L786) | valid email address |
  | [`PASSWORD`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L74) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L808) | alpha numeric and at least 1 character long |
  | [`PHONE_NUMBER`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L75) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L829) | valid phone number (optionally including country codes) |
  | [`FB_ID`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L77) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L849) | Facebook's numeric ID with at least one digit |
  | [`FB_ACCESS_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L78) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L870) | alpha-numeric |
  | [`DEVICE_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L80) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L886) | ANDROID_DEVICE_TOKEN or IOS_DEVICE_TOKEN |
  | [`ANDROID_DEVICE_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L81) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L908) | alpha-numeric-dash |
  | [`IOS_DEVICE_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L82) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L921) | alpha-numeric-dash |
  | [`PHOTO`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L84) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L938) | BASE-64 encoded and size smaller than or equal to the one defined in [param_validator_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/param_validator_properties.prop#L11) |
  | [`CAPTION`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L85) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L958) | length smaller than or equal to the one defined in [param_validator_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/param_validator_properties.prop#L15) |
  | [`CHAT_MSG`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L86) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L976) | length smaller than or equal to the one defined in [param_validator_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/param_validator_properties.prop#L19) |
  | [`TRACKING_ID`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L88) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L994) | length smaller than or equal to 200 |
  | [`NO_VALIDATION_TYPE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L93) |  | always valid - use with caution :)) |
  
  + Each element in [`response_params`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L36) has [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L38) (recomended to be lowerCamelCase), [`type`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L39) which defines the primitive type of a response param as one of (`short`, `int`, `long`, `float`, `double`, `String`), [`is_array`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L40) to define if this is an array param or not.
  
### [gs_top.keyspace](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace) structure

+ Here's an empty template for gs_top.keyspace

```json
{
  "description": "",
  "name": "",
  "replications": [
    {
      "description": "used for dev environment",
      "name": "dev",
      "replication": "'class': 'SimpleStrategy', 'replication_factor' : 1"
    }
  ],
  "tables": [
    {
      "description": "",
      "name": "",
      "columns": [
        {
          "name": "",
          "type": ""
        }
      ],
      "partition_keys": [
        ""
      ],
      "secondary_keys": [
      ],
      "caching": "ALL",
      "order_by": [
      ],
      "queries": [
        {
          "description": "",
          "name": "",
          "prepared_statement": ""
        }
      ]
    }
  ]
}
```

+ Each keyspace_name.keyspace config file is structured as follows:
  + [`description`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L2): descripes the purpose of this keyspace. Shows in generated Java clients comment blocks and generated phriction-wiki.
  + [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L3): keyspace's name.
  + [`replications`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L4): is an array of replication methods for this keyspace (e.g.: one replication method for dev with one replica and another for prod with two replicas). CQL scripts (create, drop, ...) are generated per-keyspace per-replication-method. Each replication element is structured as follows:
    + [`description`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L6): descripes the purpose of this replication method (e.g.: for dev, for prod, etc ...). Shows in generated Java clients comment blocks and generated phriction-wiki.
    + [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L7): to identify a replication method and becomes part of the CQL script's file name.
    + [`replication`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L8): a valid Cassandra replication method (use ' instead of " to avoid confusion with JSON's ").
  + [`tables`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L11): is an array of the keyspace's tables. Each table element is structured as follows:
    + [`description`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L15): descripts the purpose of this table. Shows in generated Java clients comment blocks and generated phriction-wiki.
    + [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L16): table's name.
    + [`columns`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L17): is an array defining the table's columns. Each colum element is structured as follows:
      + [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L19): column's name.
      + [`type`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L20): column's type. Detailed list of possible types can be found [**here**](http://docs.datastax.com/en/cql/3.1/cql/cql_reference/cql_data_types_c.html). Here's a list of possible column types:
        + ascii
        + bigint
        + blob
        + boolean
        + counter
        + decimal
        + double
        + float
        + frozen
        + inet
        + int
        + list
        + map
        + set
        + text
        + timestamp
        + timeuuid
        + tuple
        + uuid
        + varchar
        + varint
    + [`partition_keys`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L27): is a String array of the table's partition key(s) (from column names).
    + [`secondary_keys`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L30): is a String array of the table's secondary key(s) (from column names).
    + [`caching`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L32): defines the table's caching as `ALL`, `KEYS_ONLY` or `NONE`.
    + [`order_by`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L33): optinally defines how the table should be ordered.
    + [`queries`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L35): is an array of the queries (prepared statements) to be used on this table. Each query element is structured as follows:
      + [`description`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L37): describes the purpose of this prepared statement. Shows in generated Java clients comment blocks and generated phriction-wiki.
      + [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L38): used to identify a query per-table and becomes part of the methods' names for this query ([`getQueryInsert`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/NameIndex.java#L212), [`getQueryDispatchableInsert`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/NameIndex.java#L226), [`getBoundStatementInsert`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/NameIndex.java#L243), [`executeAsyncInsert`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/NameIndex.java#L258), [`executeSyncInsert`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/NameIndex.java#L274)).
      + [`prepared_statement`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L39): is the actual query's prepared statement. When writing a prepared statement beware to:
        + write keyspace_name.table_name in the query and not only the table_name
        + define the statements variables using `:variable_name` instead of `?`
        
# [Request](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java) structure

+ Request is the primary Object to be dealt with to implement the logic of a generated Vangav Backend REST Service in controllers' Handler classes like in [vos_calculate_sum](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/HandlerCalculateSum.java#L67) and [vos_geo_server](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L77). Here's an index for the public methods provided by [Request](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java) class:

| method | explanation |
| ------ | ----------- |
| [`getStartTime`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L111) | Returns the unix time stamp (milliseconds since epoch) when processing this request started. |
| [`getStartCalendar`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L120) | Returns a Calendar Object reflecting when processing this request started. |
| [`endRequest`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L131) | *Call this method only if you are sure you need it.* This method gets automatically called upon sending back the request's response which sets the request's [`endTime`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L54) and [`execTime`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L55). |
| [`getEndTime`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L141) | Returns the unix time stamp when the request's response was sent. Usually only useful to call in after-response methods such as [`afterProcessing`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L64). |
| [`getExecTime`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L150) | Returns the number of milliseconds it took from receiving the request till sending back the response. |
| [`getRequestId`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L155) | Each request gets a UUID. Can be used in many ways (e.g.: logging, tracing exceptions, etc ...). |
| [`getRequestHeader`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L164) | Returns a [RequestHeader](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java) Object offering [`getRemoteAddress`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java#L69), [`getRequestType`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java#L78), [`getUserAgent`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java#L87), [`getHttpAccept`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java#L96) and [`getHeaders`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java#L105) |
| [`a`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L) | z |
| [`a`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L) | z |
| [`a`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L) | z |
| [`a`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L) | z |
| [`a`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L) | z |
| [`a`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L) | z |
| [`a`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L) | z |
| [`a`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L) | z |
| [`a`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L) | z |

# Community

[Facebook Group: Vangav Open Source - Backend](http://www.fb.com/groups/575834775932682/)

[Facebook Page: Vangav](http://www.fb.com/vangav.f)

Third party communities for Vangav Backend
- play framework
- cassandra
- datastax

> Tag your question online (e.g.: stack overflow, etc ...) with **#vangav_backend** to easier find questions/answers online.

# Dependencies

> These dependencies are already included in Vangav Backend. That's just FYI.

- [play framework](http://www.playframework.com) for web framework
- [Apache Cassandra](http://www.cassandra.apache.org/) for database
- [datastax](http://www.datastax.com/) for JAVA cassandra driver

# Hassle-Free

Vangav Backend is license-free because we know that starting a startup or developing a new service is already mission-impossible. You already got more than enough to worry about, Vangav Backend doesn't want to be one of those things you worry about.
> "Keep going, never give up".

Have fun!
