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

package com.vangav.backend.networks.jobs;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Job, a job is a simplified serializable HTTP GET request (url and query)
 *   useful for:
 *   - transferring a job to a helper service to process it
 *   - serializing a job to keep track of it in a database then:
 *     - execute it at a later point in time
 *     - keep track of a job's success/failure
 *     - ...
 *   - ...
 */
public class Job implements Serializable {

  /**
   * Generated serialVersionUID
   */
  private static final long serialVersionUID = -8879966973136153654L;
  
  private String url;
  /**
   * kJobUuidKey optionally helps standardizing having a job's uuid
   */
  public static final String kJobUuidKey = "job_uuid";
  /**
   * kJobUuidKey optionally helps standardizing having a job's time (unix time)
   */
  public static final String kJobTimeKey = "job_time";
  private Map<String, String> params;
  
  /**
   * Constructor - Job
   * @param url
   * @return new Job Object
   * @throws Exception
   */
  public Job (String url) throws Exception {
    
    this.url = url;
    this.params = new HashMap<String, String>();
  }
  
  /**
   * getUrl
   * @return this job's url
   * @throws Exception
   */
  public String getUrl () throws Exception {
    
    return this.url;
  }
  
  /**
   * setJobUuid
   * @param jobUuid
   * @throws Exception
   */
  public void setJobUuid (String jobUuid) throws Exception {
    
    this.params.put(kJobUuidKey, jobUuid);
  }
  
  /**
   * getJobUuid
   * @return job's uuid if exists and null otherwise
   * @throws Exception
   */
  public String getJobUuid () throws Exception {
    
    return this.params.get(kJobUuidKey);
  }
  
  /**
   * setJobTime
   * @param jobTime
   * @throws Exception
   */
  public void setJobTime (long jobTime) throws Exception {
    
    this.params.put(kJobTimeKey, "" + jobTime);
  }
  
  /**
   * addParam
   * @return job's time if exists and null otherwise
   * @throws Exception
   */
  public String getJobTime () throws Exception {
    
    return this.params.get(kJobTimeKey);
  }
  
  /**
   * addParam
   * @param key
   * @param value
   * @throws Exception
   */
  public void addParam (String key, String value) throws Exception {
    
    this.params.put(key, value);
  }
  
  /**
   * addParams
   * @param mapParams
   * @throws Exception
   */
  public void addParams (Map<String, String> mapParams) throws Exception {
    
    this.params.putAll(mapParams);
  }
  
  /**
   * getParam
   * @param key
   * @return specific param's value and null if key doesn't exist
   * @throws Exception
   */
  public String getParam (String key) throws Exception {
    
    return this.params.get(key);
  }
  
  /**
   * getParams
   * @return this job's params
   * @throws Exception
   */
  public Map<String, String> getParams () throws Exception {
    
    return this.params;
  }
  
  /**
   * getQuery
   * @return the query part of the GET request and empty String is the request
   *           has no params
   * @throws Exception
   */
  public String getQuery () throws Exception {

    String query = "";

    if (this.params.isEmpty() == true) {

      return query;
    }

    int index = 0;

    for (Map.Entry<String, String> entry : this.params.entrySet() ) {

      if (entry.getKey() == null || entry.getValue() == null) {
        
        continue;
      }

      if (index ++ > 0) {
        
        query += "&";
      }

      query += entry.getKey() + "=" + entry.getValue();
    }

    return query;
  }
  
  /**
   * getRequest
   * @return full ready-to-send job request
   * @throws Exception
   */
  public String getRequest () throws Exception {
    
    String request = url;
    
    if (this.params.isEmpty() == false) {
      
      request += "?" + this.getQuery();
    }
    
    return request;
  }
  
  @Override
  public String toString () {
    
    try {
      
      return
        "Job request: "
        + this.getRequest();
    } catch (Exception e) {
      
      return "Job request: threw and exception!";
    }
  }
}
