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

package com.vangav.backend.backend_client_java.clients_generator;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.vangav.backend.backend_client_java.clients_generator.json.ControllerJson;
import com.vangav.backend.backend_client_java.clients_generator.json.ClientJson;
import com.vangav.backend.backend_client_java.clients_generator.json.ErrorResponseJson;
import com.vangav.backend.backend_client_java.clients_generator.json.RequestParamJson;
import com.vangav.backend.backend_client_java.clients_generator.json.ResponseParamJson;
import com.vangav.backend.backend_client_java.param.ParamOptionality;
import com.vangav.backend.backend_client_java.param.ParamType;
import com.vangav.backend.content.checking.JavaCodeVerifierInl;
import com.vangav.backend.content.checking.StringVerifierInl;
import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.files.FileLoaderInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ClientsVerifierInl has inline static methods for verifying clients' config
 *   files [*.client_java]
 */
public class ClientsVerifierInl {
  
  // disable default instantiation
  private ClientsVerifierInl () {}
  
  /**
   * verifyClientsJson
   * throws an exception if a client's config json file is invalid
   * @param configDirPath
   * @throws Exception
   */
  public static void verifyClientsJson (
    final String configDirPath) throws Exception {
    
    // load clients' config files
    Map<String, String> clientsJsonRaw =
      FileLoaderInl.loadTextFilesWithoutComments(
        ClientsGeneratorConstantsInl.kClientCommentPrefix,
        configDirPath,
        ClientsGeneratorConstantsInl.kClientConfigExt);
    
    // no clients config files to verify?
    if (clientsJsonRaw == null ||
        clientsJsonRaw.isEmpty() == true) {
      
      return;
    }
    
    // for each client's config file
    for (String jsonFileName : clientsJsonRaw.keySet() ) {
      
      verifyClientJson(
        jsonFileName,
        clientsJsonRaw.get(jsonFileName) );
    }
  }
  
  /**
   * verifyClientJson
   * verifies a single client's json config file
   * @param jsonFileName
   * @param rawJson
   * @throws Exception
   */
  private static void verifyClientJson (
    String jsonFileName,
    String rawJson) throws Exception {
    
    ClientJson clientJson =
      ClientJson.fromJsonString(rawJson);
    
    if (clientJson.controllers != null &&
        clientJson.controllers.length > 0) {
      
      verifyClientControllers(jsonFileName, clientJson);
    }
    
    if (clientJson.error_responses != null &&
        clientJson.error_responses.length > 0) {
      
      verifyClientErrorResponses(jsonFileName, clientJson);
    }
  }
  
  /**
   * verifyClientControllers
   * verifies a client's controllers
   * @param jsonFileName
   * @param clientJson
   * @throws Exception
   */
  private static void verifyClientControllers (
    String jsonFileName,
    ClientJson clientJson) throws Exception {
    
    Set<String> controllersNames = new HashSet<String>();
    Set<String> requestParamNames;
    Set<String> responseParamNames;
    
    for (ControllerJson controllerJson : clientJson.controllers) {
      
      // verify name
      JavaCodeVerifierInl.verifyIdentifier(
        jsonFileName + ": controller name",
        controllerJson.name);
      
      // check for duplicates
      if (controllersNames.contains(controllerJson.name) == true) {
        
        throw new CodeException(
          11,
          1,
          "From client json ["
            + jsonFileName
            + "]. Can't have more than one controller with the same name ["
            + controllerJson.name
            + "].",
          ExceptionClass.JSON);
      }
      
      controllersNames.add(controllerJson.name);
      
      // verify type
      StringVerifierInl.isOneOfString(
        jsonFileName
          + " | "
          + controllerJson.name
          + ": controller type",
        controllerJson.type,
        true,
        "GET",
        "POST");
      
      // verify request params?
      if (controllerJson.request_params != null) {
        
        requestParamNames = new HashSet<String>();
        
        // verify request params
        for (RequestParamJson requestParamJson :
             controllerJson.request_params) {
          
          // verify request param's name
          JavaCodeVerifierInl.verifyIdentifier(
            jsonFileName
              + " | "
              + controllerJson.name
              + ": request param name",
            requestParamJson.name);
          
          // duplicate request param's name?
          if (requestParamNames.contains(requestParamJson.name) == true) {
            
            throw new CodeException(
              11,
              2,
              "From client json ["
                + jsonFileName
                + "] controller ["
                + controllerJson.name
                + "]. Can't have more than one request param with the same "
                + "name ["
                + requestParamJson.name
                + "].",
              ExceptionClass.JSON);
          }
          
          requestParamNames.add(requestParamJson.name);
          
          // verify request param's type
          try {
            
            StringVerifierInl.isOneOfString(
              jsonFileName
                + " | "
                + controllerJson.name
                + " | "
                + requestParamJson.name
                + ": request param type",
              requestParamJson.type,
              false,
              "boolean",
              "short",
              "int",
              "long",
              "float",
              "double",
              "String");
          } catch (Exception e) {

            StringVerifierInl.belongsToEnum(
              jsonFileName
                + " | "
                + controllerJson.name
                + " | "
                + requestParamJson.name
                + ": request param type",
              requestParamJson.type,
              ParamType.class);
          }
          
          // verify request param's optionality
          StringVerifierInl.belongsToEnum(
            jsonFileName
              + " | "
              + controllerJson.name
              + " | "
              + requestParamJson.name
              + ": request param optionality",
            requestParamJson.optionality,
            ParamOptionality.class);
        }
      }
      
      // verify response params?
      if (controllerJson.response_params != null) {
        
        responseParamNames = new HashSet<String>();
        
        // verify response params
        for (ResponseParamJson responseParamJson :
             controllerJson.response_params) {
          
          // verify response param's name
          JavaCodeVerifierInl.verifyIdentifier(
            jsonFileName
              + " | "
              + controllerJson.name
              + ": response param name",
            responseParamJson.name);
          
          // duplicate response param's name?
          if (responseParamNames.contains(responseParamJson.name) == true) {
            
            throw new CodeException(
              11,
              3,
              "From client json ["
                + jsonFileName
                + "] controller ["
                + controllerJson.name
                + "]. Can't have more than one response param with the same "
                + "name ["
                + responseParamJson.name
                + "].",
              ExceptionClass.JSON);
          }
          
          responseParamNames.add(responseParamJson.name);
          
          // verify response param's type
          StringVerifierInl.isOneOfString(
            jsonFileName
              + " | "
              + controllerJson.name
              + " | "
              + responseParamJson.name
              + ": response param type",
            responseParamJson.type,
            false,
            "boolean",
            "short",
            "int",
            "long",
            "float",
            "double",
            "String");
        }
      }
    }
  }
  
  /**
   * verifyClientErrorResponses
   * verifies a client's error responses
   * @param jsonFileName
   * @param controllersJson
   * @throws Exception
   */
  private static void verifyClientErrorResponses (
    String jsonFileName,
    ClientJson controllersJson) throws Exception {
    
    Set<String> errorResponsesNames = new HashSet<String>();
    Set<Integer> httpStatusCodes = new HashSet<Integer>();
    Set<String> responseParamNames;
    
    for (ErrorResponseJson errorResponseJson :
         controllersJson.error_responses) {
      
      // verify name
      JavaCodeVerifierInl.verifyIdentifier(
        jsonFileName + ": error response name",
        errorResponseJson.name);
      
      // check for duplicates
      if (errorResponsesNames.contains(errorResponseJson.name) == true) {
        
        throw new CodeException(
          11,
          4,
          "From client json ["
            + jsonFileName
            + "]. Can't have more than one error response with the same name ["
            + errorResponseJson.name
            + "].",
          ExceptionClass.JSON);
      }
      
      errorResponsesNames.add(errorResponseJson.name);
      
      // verify http status codes
      
      // no http status code(s)?
      if (errorResponseJson.http_status_codes == null ||
          errorResponseJson.http_status_codes.length < 1) {
        
        throw new CodeException(
          11,
          5,
          "From client json ["
            + jsonFileName
            + "]. An error response must have at least one http status code.",
          ExceptionClass.JSON);
      }
      
      for (int httpStatusCode : errorResponseJson.http_status_codes) {
        
        // HTTP_OK http status code for an error response?
        if (httpStatusCode == 200) {
          
          throw new CodeException(
            11,
            6,
            "From client json ["
              + jsonFileName
              + "]. An error response http status codes can't have a 200 http "
              + "status code.",
            ExceptionClass.JSON);
        }
        
        // duplicate http status code?
        if (httpStatusCodes.contains(httpStatusCode) == true) {
          
          throw new CodeException(
            11,
            7,
            "From client json ["
              + jsonFileName
              + "]. Duplicate http status code ["
              + httpStatusCode
              + "] within one or more error responses.",
            ExceptionClass.JSON);
        }
      }
      
      // verify response params?
      if (errorResponseJson.response_params != null) {
        
        responseParamNames = new HashSet<String>();
        
        // verify response params
        for (ResponseParamJson responseParamJson :
             errorResponseJson.response_params) {
          
          // verify response param's name
          JavaCodeVerifierInl.verifyIdentifier(
            jsonFileName
              + " | error response"
              + ": response param name",
            responseParamJson.name);
          
          // duplicate response param's name?
          if (responseParamNames.contains(responseParamJson.name) == true) {
            
            throw new CodeException(
              11,
              8,
              "From client json ["
                + jsonFileName
                + "] error response. Can't have more than one response param "
                + "with the same name ["
                + responseParamJson.name
                + "].",
              ExceptionClass.JSON);
          }
          
          responseParamNames.add(responseParamJson.name);
          
          // verify response param's type
          StringVerifierInl.isOneOfString(
            jsonFileName
              + " | error response"
              + " | "
              + responseParamJson.name
              + ": response param type",
            responseParamJson.type,
            false,
            "boolean",
            "short",
            "int",
            "long",
            "float",
            "double",
            "String");
        }
      }
    }
  }
}
