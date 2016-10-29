/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * MIT License
 *
 * Copyright (c) 2016 Vangav
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 * */

/**
 * Community
 * Facebook Group: Vangav Open Source - Backend
 *   fb.com/groups/575834775932682/
 * Facebook Page: Vangav
 *   fb.com/vangav.f
 * 
 * Third party communities for Vangav Backend
 *   - play framework
 *   - cassandra
 *   - datastax
 *   
 * Tag your question online (e.g.: stack overflow, etc ...) with
 *   #vangav_backend
 *   to easier find questions/answers online
 * */

package com.vangav.backend.public_apis.car2go;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import com.vangav.backend.data_structures_and_algorithms.tuple.Pair;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.exceptions.VangavException.ExceptionType;
import com.vangav.backend.exceptions.handlers.ArgumentsInl;
import com.vangav.backend.networks.rest_client.FutureResponse;
import com.vangav.backend.networks.rest_client.RestAsync;
import com.vangav.backend.networks.rest_client.RestResponseJson;
import com.vangav.backend.networks.rest_client.RestResponseJsonGroup;
import com.vangav.backend.public_apis.car2go.json.gas_stations.GasStations;
import com.vangav.backend.public_apis.car2go.json.operation_area.OperationAreas;
import com.vangav.backend.public_apis.car2go.json.parking_spots.ParkingSpots;
import com.vangav.backend.public_apis.car2go.json.vehicles.Vehicles;
import com.vangav.backend.thread_pool.ThreadPool;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Car2GoApi handles fetching data through car2go public API version v2.1
 * 
 * Reference:
 * https://github.com/car2go/openAPI
 * */
public class Car2GoApi {

  private final String oauthConsumerKey;
  
  private Map<String, FutureResponse<LocationType> > futureResponses;
  
  /**
   * Constructor Car2GoApi
   * @param oauthConsumerKey - provided by car2go per consumer
   * @return new Car2GoApi Object
   * @throws Exception
   */
  public Car2GoApi (String oauthConsumerKey) throws Exception {
   
    this.oauthConsumerKey = oauthConsumerKey;
    
    this.futureResponses =
      new HashMap<String, FutureResponse<LocationType> >();
  }
  
  /**
   * enum RequestType is used to distinguish between sync and async processing
   *   in the private common method that does the processing for both modes
   * */
  private enum RequestType {
    
    SYNC,
    ASYNC
  }
  
  /**
   * enum EdgeType is used to identify which public API edge to use to fetch
   *   data
   */
  public enum EdgeType {
    
    GAS_STATIONS,
    OPERATION_AREA,
    PARKING_SPOTS,
    VEHICLES;
    
    /**
     * getName
     * @return the url format String for each each
     * @throws Exception
     */
    private String getName () throws Exception {
      
      switch (this) {
        case GAS_STATIONS:
          return "gasstations";
        case OPERATION_AREA:
          return "operationareas";
        case PARKING_SPOTS:
          return "parkingspots";
        case VEHICLES:
          return "vehicles";
        default:
          throw new CodeException(
            153,
            1,
            "Invalid EdgeType ["
              + this.toString()
              + "]",
            ExceptionClass.TYPE);
      }
    }
    
    /**
     * getRestResponseJson
     * @return a new RestResponseJson Object per edge for storing the API's
     *           fetched data
     * @throws Exception
     */
    private RestResponseJson getRestResponseJson () throws Exception {
      
      switch (this) {
        case GAS_STATIONS:
          return new GasStations();
        case OPERATION_AREA:
          return new OperationAreas();
        case PARKING_SPOTS:
          return new ParkingSpots();
        case VEHICLES:
          return new Vehicles();
        default:
          throw new CodeException(
            153,
            2,
            "Invalid EdgeType ["
              + this.toString()
              + "]",
            ExceptionClass.TYPE);
      }
    }
  }
  
  // format:
  //   String edge (e.g.: vehicles, parkingspots, etc ...)
  //   String location (e.g.: Roma, Berlin, etc ...)
  //   String oauth_consumer_key (get one from car2go)
  private static final String kApiGetRequest =
    "https://www.car2go.com/api/v2.1/%s?loc=%s&oauth_consumer_key=%s&format=json";
  
  /**
   * getEdgeSync
   * BLOCKING method
   * gets an edge's data (from param edgeType) for param locations
   * @param edgeType (e.g: GAS_STATIONS, VEHICLES, etc ...)
   * @param locations (e.g.: ROMA, BERLIN, etc ...)
   * @return Map<LocationType, Pair<Boolean, RestResponseJson> >
   *           key: LocationType (e.g.: Roma, Berlin, etc ...)
   *           Pair-Boolean: true if the response's Http Status Code was
   *             200 Success and false otherwise
   *           Pair-RestResponseJson: the corresponding RestResponseJson Object
   *             for param edgeType for 200 Success response or
   *             an ErrorResponse Object containing the raw response String
   *             in case the response's status code was anything other than
   *             200 Success
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public Map<LocationType, Pair<Boolean, RestResponseJson> > getEdgeSync (
    EdgeType edgeType,
    LocationType... locations) throws Exception {
    
    return
      (Map<LocationType, Pair<Boolean, RestResponseJson> >)this.getEdge(
        RequestType.SYNC,
        edgeType,
        locations);
  }
  
  /**
   * getEdgeAsync
   * NON-BLOCKING method
   * issues an async request to car2go API and returns a tracking id for the
   *   request to get the response at a later time
   * @param edgeType (e.g: GAS_STATIONS, VEHICLES, etc ...)
   * @param locations (e.g.: ROMA, BERLIN, etc ...)
   * @return the tracking id to be used at a later time to get this async
   *           request's response
   * @throws Exception
   */
  public String getEdgeAsync (
    EdgeType edgeType,
    LocationType... locations) throws Exception {
    
    return (String)this.getEdge(RequestType.ASYNC, edgeType, locations);
  }
  
  /**
   * getEdgeAsync
   * BLOCKING method
   * gets the response of a previously issued async request
   * @param requestTrackingUuid
   * @return Map<LocationType, Pair<Boolean, RestResponseJson> >
   *           key: LocationType (e.g.: Roma, Berlin, etc ...)
   *           Pair-Boolean: true if the response's Http Status Code was
   *             200 Success and false otherwise
   *           Pair-RestResponseJson: the corresponding RestResponseJson Object
   *             for param edgeType for 200 Success response or
   *             an ErrorResponse Object containing the raw response String
   *             in case the response's status code was anything other than
   *             200 Success
   * @throws Exception
   */
  public Map<LocationType, Pair<Boolean, RestResponseJson> > getEdgeAsync (
    String requestTrackingUuid) throws Exception {
    
    if (this.futureResponses.containsKey(requestTrackingUuid) == false) {

      throw new CodeException(
        153,
        3,
        "Invalid request tracking id ["
        + requestTrackingUuid
        + "]",
        ExceptionClass.INVALID);
    }
    
    Map<LocationType, RestAsync> requests =
      this.futureResponses.remove(requestTrackingUuid).getAll();

    RestAsync currRestAsync;
    
    Map<LocationType, Pair<Boolean, RestResponseJson> > result =
      new HashMap<LocationType, Pair<Boolean, RestResponseJson> >();
    
    for (LocationType locationType : requests.keySet() ) {
      
      currRestAsync = requests.get(locationType);
      
      if (currRestAsync.isResponseStatusSuccess() == true) {
        
        result.put(
          locationType,
          new Pair<Boolean, RestResponseJson>(
            true,
            currRestAsync.getRestResponseJson() ) );
      } else {
        
        result.put(
          locationType,
          new Pair<Boolean, RestResponseJson>(
            false,
            new ErrorResponse(currRestAsync.getRawResponseString() ) ) );
      }
    }
    
    return result;
  }
  
  /**
   * getEdge
   * @param requestType - SYNC or ASYNC
   * @param edgeType
   * @param locations
   * @return car2go API's response for SYNC requests and requests tracking uuid
   *           String for ASYNC requests
   * @throws Exception
   */
  private Object getEdge (
    RequestType requestType,
    EdgeType edgeType,
    LocationType... locations) throws Exception {
    
    ArgumentsInl.checkNotNull(
      "EdgeType",
      edgeType,
      ExceptionType.CODE_EXCEPTION);
    ArgumentsInl.checkNotEmpty(
      "Locations",
      locations,
      ExceptionType.CODE_EXCEPTION);
    
    CountDownLatch countDownLatch = new CountDownLatch(locations.length);
    
    Map<LocationType, RestAsync> requests =
      new HashMap<LocationType, RestAsync>();

    RestAsync currRestAsync;
    
    for (LocationType locationType : locations) {
      
      currRestAsync =
        new RestAsync(
          countDownLatch,
          String.format(
            kApiGetRequest,
            edgeType.getName(),
            locationType.getName(),
            this.oauthConsumerKey),
          new RestResponseJsonGroup(edgeType.getRestResponseJson() ) );

      requests.put(
        locationType,
        currRestAsync);

      ThreadPool.i().executeInRestClientPool(currRestAsync);
    }
    
    if (requestType == RequestType.SYNC) {
      
      countDownLatch.await();
      
      Map<LocationType, Pair<Boolean, RestResponseJson> > result =
        new HashMap<LocationType, Pair<Boolean, RestResponseJson> >();
      
      for (LocationType locationType : locations) {
        
        currRestAsync = requests.get(locationType);
        
        if (currRestAsync.isResponseStatusSuccess() == true) {
          
          result.put(
            locationType,
            new Pair<Boolean, RestResponseJson>(
              true,
              currRestAsync.getRestResponseJson() ) );
        } else {
          
          result.put(
            locationType,
            new Pair<Boolean, RestResponseJson>(
              false,
              new ErrorResponse(currRestAsync.getRawResponseString() ) ) );
        }
      }
      
      return result;
    } else if (requestType == RequestType.ASYNC) {

      String uuid = UUID.randomUUID().toString();
      
      this.futureResponses.put(
        uuid,
        new FutureResponse<LocationType>(
          countDownLatch,
          requests) );
      
      return uuid;
    }

    throw new CodeException(
      153,
      4,
      "Unhandled RequestType ["
        + requestType.toString()
        + "]",
      ExceptionClass.TYPE);
  }
  
  @Override
  public String toString () {
    
    return
      "Car2GoApi:\n"
      + "oauth_consumer_key ["
      + this.oauthConsumerKey
      + "]";
  }
}
