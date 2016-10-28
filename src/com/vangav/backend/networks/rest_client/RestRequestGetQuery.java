/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * no license, I know you already got more than enough to worry about
 * keep going, never give up
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

package com.vangav.backend.networks.rest_client;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * GetQuery represents GET REST call's query
 *   (e.g.: id=123&name=john)
 * */
public class RestRequestGetQuery extends RestRequest {

  private Map<String, String> params;
  
  /**
   * Constructor GetQuery
   * @return new GetQuery Object
   */
  public RestRequestGetQuery () {
    
    params = new HashMap<String, String>();
  }
  
  /**
   * Constructor GetQuery
   * constructs params map and adds key/value to it
   * useful as a shortcut for one-key-value GET queries
   * @param key
   * @param value
   * @throws Exception
   */
  public RestRequestGetQuery (String key, String value) throws Exception {
    
    this.params = new HashMap<String, String>();
    
    this.addParam(key, value);
  }
  
  /**
   * addParam
   * adds a param (key/value pair) to the GET query
   * @param key
   * @param value
   * @throws Exception
   */
  public void addParam (String key, String value) throws Exception {
    
    this.params.put(key, value);
  }
  
  /**
   * addParams
   * adds multiple params (key/value pairs) to the GET query
   * @param mapParams
   * @throws Exception
   */
  public void addParams (Map<String, String> mapParams) throws Exception {
    
    this.params.putAll(mapParams);
  }
  
  /**
   * getQuery
   * @return string representation of the GET query, to be appended to the URL
   * @throws Exception
   */
  public String getQuery () throws Exception {

    String query = "";

    if (this.params.isEmpty() == true) {

      return query;
    }

    int index = 0;
    String currKey;
    String currValue;

    for (Map.Entry<String, String> entry : this.params.entrySet() ) {

      currKey = entry.getKey();

      if (currKey == null) {
        
        continue;
      }

      if (index ++ > 0) {
        
        query += "&";
      }

      query += currKey + "=";

      currValue = entry.getValue();

      if (currValue != null) {
        
        query += URLEncoder.encode(currValue, "UTF-8");
      }
    }

    return query;
  }
  
  @Override
  public String toString () {
    
    try {
    
      return
        "GetQuery: "
        + this.getQuery();
    } catch (Exception e) {
      
      return
        "GetQuery: threw an Exception";
    }
  }
}
