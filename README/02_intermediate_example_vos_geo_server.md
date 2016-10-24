# Intermediate Example (vos_geo_server)

vos_geo_server is a service that takes a latitude/longitude request and returns the reverse geo code (continent, country, major city, city). It also keeps track of queried continents/countries to also provide lists of sorted top queried continents/countries.

> Check out a finished version of this service at **https://github.com/vangav/vos_geo_server**

### Init
1. create a workspace directory "**my_services**" - this is the directory to contain both of vos_backend and all the services generated using it
2. download this (**vos_backend.zip**) project inside the workspace directory and unzip it
3. **rename** downloaded vos_backend-master to vos_backend

### Generate a new service
1. Create a new directory "**my_services/vos_geo_server**".
2. **Copy** **controllers.json** and **gs_top.keyspace** from vos_backend/vangav_backend_templates/vos_geo_server/ to the directory vos_geo_server created in (1).
3. Open a terminal session and **cd** to my_services/vos_backend/tools_bin.
4. Execute the command **`java -jar backend_generator.jar new vos_geo_server`** to generate the Vangav Backend Service.
5. Enter **`Y`** for using the config directory.
6. Enter **`Y`** to generate an eclipse-compatible project.
7. Enter **`N`** for generating a worker service. Using workers is explained in a separate section.

### Init the service's cassandra database
1. **cd** to my_services/vos_geo_server/cassandra/cql/
2. execute the command **`./_start_cassandra.sh`** - to start cassandra
3. **cd** to my_services/vos_geo_server/cassandra/cql/drop_and_create/
4. execute the command **`./_execute_cql.sh gs_top_dev.cql`** to initialize the service's database tables.

### Init service's data
1. **copy** the contents of the directory my_services/vos_backend/data/geo/reverse_geo_coding/ to my_services/vos_geo_server/conf/data/geo/reverse_geo_coding/

### Start the service
1. **cd** to my_services/vos_geo_server
2. execute the command **`./_run.sh`**

### Preliminary testing
1. open an internet browser page and type **[`http://localhost:9000/reverse_geo_code?latitude=49&longitude=11`](http://localhost:9000/reverse_geo_code?latitude=49&longitude=11)** - this returns an empty response

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
  + **[`http://localhost:9000/reverse_geo_code?latitude=49&longitude=11`](http://localhost:9000/reverse_geo_code?latitude=49&longitude=11)** - play around with the latitude and longitude values
  + **[`http://localhost:9000/top_continents`](http://localhost:9000/top_continents)**
  + **[`http://localhost:9000/top_countries`](http://localhost:9000/top_countries)**
+ stop the service as explained above

> **Voila, few minutes, few lines of code and the geo server's backend is up and running.**
