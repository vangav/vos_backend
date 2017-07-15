# [geo server](https://github.com/vangav/vos_geo_server)

geo server is a service that takes a latitude/longitude request and returns the hash code and reverse geo code (continent, country, major city, city); it also keeps track of queried continents/countries to provide lists of sorted top queried continents and countries

### init
> skip this section if you already did it in [init](https://github.com/vangav/vos_backend#init) of the calculate sum example
1. create a workspace directory `my_services` - this is the directory to contain both of vos_backend and all the services generated using it
2. download this `vos_backend.zip` project (from the green `clone or download` button up there) inside the workspace directory created in (1) and unzip it
3. **rename** downloaded vos_backend-master to vos_backend

### generate a new service

1. create a new directory `my_services/vos_geo_server`
2. copy `controllers.json` and `gs_top.keyspace` from `vos_backend/vangav_backend_templates/vos_geo_server/` to the directory `my_services/vos_geo_server` created in (1)
3. open a terminal session and `cd` to `my_services/vos_backend/tools_bin`
4. execute the command `java -jar backend_generator.jar new vos_geo_server` to generate the service
5. enter `y` for using the config directory in order to use `controllers.json` and `gs_top.keyspace` for generating
6. enter `n` for generating a worker service (using workers is explained in a separate section)

### init the service's cassandra database
1. `cd` to `my_services/vos_geo_server/cassandra/cql/`
2. execute the command `./_start_cassandra.sh` to start cassandra
3. `cd` to `my_services/vos_geo_server/cassandra/cql/drop_and_create/`
4. execute the command `./_execute_cql.sh gs_top_dev.cql` to initialize the service's database tables

### init service's data
1. `copy` the contents of the directory `my_services/vos_backend/data/geo/reverse_geo_coding/` to `my_services/vos_geo_server/conf/data/geo/reverse_geo_coding/`

### start the service
1. `cd` to `my_services/vos_geo_server`
2. execute the command `./_run.sh`

### preliminary testing
1. open an internet browser page and type [`http://localhost:9000/reverse_geo_code?latitude=49&longitude=11`](http://localhost:9000/reverse_geo_code?latitude=49&longitude=11), this returns an empty response

### stop the service
1. in the terminal session where you started the service press `control + d`

### writing the service's logic code
+ optionally for eclipse users: open eclipse and import vos_geo_server project
  + file **>** import **>** general **>** existing projects into workspace **>** next **>** set "select root directory" to my_services **>** under projects make sure that vos_geo_server is selected **>** finish
  + double check the java version used for compiling the project: right click the project **>** properties **>** java compiler **>** enable project specific settings **>** compiler compliance level **>** 1.7 or 1.8
#### index initialization
+ under package `com.vangav.vos_geo_server` add a new package `[common](https://github.com/vangav/vos_geo_server/tree/master/app/com/vangav/vos_geo_server/common)`
+ in the created package in the previous step add a new class [InitIndexInl](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/common/InitIndexInl.java) with the following implementation:
```java
package com.vangav.vos_geo_server.common;

import com.datastax.driver.core.ResultSet;
import com.vangav.vos_geo_server.cassandra_keyspaces.gs_top.NameIndex;

/**
 * InitIndexInl has an inline static method to init Cassandra's gs_top.index
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
#### load reverse geo coding data and initialize index on service start
+ in [default_package/Global.java](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java) after the [following line](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java#L94)
```java
Countries.loadTable();
```
add the [following lines](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java#L98)
```java
ReverseGeoCoding.load();
InitIndexInl.initIndex();
```
#### processing reverse geo coding
+ open class [HandlerReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java), method [`processRequest`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L96) should be as follows
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
#### update top queried locations data in after-processing
+ then add the following [`method`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L130) in class [HandlerReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java)
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
#### complete the top queried continents' response structure (nested json)
+ under package [com.vangav.vos_geo_server.controllers.top_continents](https://github.com/vangav/vos_geo_server/tree/master/app/com/vangav/vos_geo_server/controllers/top_continents) add class [ResponseTopContinent](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/top_continents/response_json/ResponseTopContinent.java).java with the following code
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
+ modify class [ResponseTopContinents.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/top_continents/ResponseTopContinents.java) to be as follows:
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
#### processing top queried continents
+ in class **[HandlerTopContinents.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/top_continents/HandlerTopContinents.java)** method **[`processRequest`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/top_continents/HandlerTopContinents.java#L97)** should be as follows:
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
+ now repeat the last 3 steps for countries, for reference check the [finished version](https://github.com/vangav/vos_geo_server)

### start the service
1. `cd` to `my_services/vos_geo_server`
2. execute the command `./_run.sh`

### Try it out
+ open an internet browser page and type any of
  + [`http://localhost:9000/reverse_geo_code?latitude=49&longitude=11`](http://localhost:9000/reverse_geo_code?latitude=49&longitude=11) - play around with the latitude and longitude values
  + [`http://localhost:9000/top_continents`](http://localhost:9000/top_continents)
  + [`http://localhost:9000/top_countries`](http://localhost:9000/top_countries)

### stop the service
1. in the terminal session where you started the service press `control + d`
