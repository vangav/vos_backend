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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.vangav.backend.backend_client_java.clients_generator.json.ClientJson;
import com.vangav.backend.backend_client_java.clients_generator.json.ControllerJson;
import com.vangav.backend.backend_client_java.clients_generator.json.ErrorResponseJson;
import com.vangav.backend.backend_client_java.clients_generator.json.RequestParamJson;
import com.vangav.backend.backend_client_java.clients_generator.json.ResponseParamJson;
import com.vangav.backend.backend_client_java.param.ParamType;
import com.vangav.backend.content.checking.StringVerifierInl;
import com.vangav.backend.content.formatting.CodeIdentifiersFormatterInl;
import com.vangav.backend.content.formatting.JavaFormatterInl;
import com.vangav.backend.files.FileLoaderInl;
import com.vangav.backend.files.FileWriterInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ClientsGeneratorInl generates controllers' classes for a client
 * For each client it generates:
 *   - clients_properties.prop
 *   - ClientsProperties.java
 *   - Constants.java
 * And for each client it generates:
 *   - Error responses (if any)
 * And for each client's controller it generates:
 *   - ControllerCall.java which handles calling a client's backend controller
 *   - Request.java which maps the controller's GET/POST request
 *   - Response.java which represents the controller's JSON response
 */
public class ClientsGeneratorInl {
  
  // disable default instantiation
  private ClientsGeneratorInl () {}
  
  /**
   * generateClients
   * handles generating all needed code/properties files for all clients
   * @param configDirPath
   * @param projectDirPath
   * @param projectName
   * @param rootPackage
   * @throws Exception
   */
  public static void generateClients (
    final String configDirPath,
    final String projectDirPath,
    final String projectName,
    final String rootPackage) throws Exception {
    
    // load clients' config files
    Map<String, String> clientsJsonRaw =
      FileLoaderInl.loadTextFilesWithoutComments(
        ClientsGeneratorConstantsInl.kClientCommentPrefix,
        configDirPath,
        ClientsGeneratorConstantsInl.kClientConfigExt);
    
    // no clients config files?
    if (clientsJsonRaw == null ||
        clientsJsonRaw.isEmpty() == true) {
      
      return;
    }
    
    // get path to to-be-generated properties file
    String pathToProperties =
      projectDirPath
      + "/conf/prop";
    
    // get path to to-be-generated code files
    String pathToPackage =
      JavaFormatterInl.getPathToPackage(
        projectDirPath,
        JavaFormatterInl.kPlaySrcDirName,
        rootPackage,
        projectName,
        ClientsGeneratorConstantsInl.kClientsPackageName);
    
    // make directory
    FileWriterInl.mkdirs(pathToPackage, false);
    
    // get package name of to-be-generated code files
    String packageName =
      JavaFormatterInl.getPackageName(
        rootPackage,
        projectName,
        ClientsGeneratorConstantsInl.kClientsPackageName);
    
    // get clients' names
    
    ArrayList<String> clientsNames = new ArrayList<String>();
    
    for (String configFileName : clientsJsonRaw.keySet() ) {
      
      // removing extension (.client_java in this case)
      clientsNames.add(
        configFileName.replaceFirst("[.][^.]+$", "") );
    }
    
    // generate clients_properties.prop
    generateClientsProperties(
      clientsNames,
      pathToProperties);
    
    // generate ClientsProperties.java
    generateClientsPropertiesJava(
      clientsNames,
      packageName,
      pathToPackage);
    
    // generate Constants.java
    generateConstants(
      clientsNames,
      packageName,
      pathToPackage);
    
    // generate clients' classes
    generateClients(
      clientsJsonRaw,
      projectDirPath,
      projectName,
      rootPackage);
  }
  
  private static final String kClientsPropertiesFileName =
    "clients_properties.prop";
  private static final String kClientsPropertielsFileFormat =
    // %s: properties file header
    "%s"
    + "# Generated using ClientsGeneratorInl\n\n"
    + "# Clients properties\n\n"
    // kClientsPropertiesClientBackendUrlFormat
    // %s: clients' backends URLs
    + "%s";
  private static final String kClientsPropertiesClientBackendUrlFormat =
    // %s: client's name (lower_under)
    "%s=http://localhost:9000/\n";
  /**
   * generateClientsProperties
   * generates clients_properties.prop
   * @param clientsNames
   * @param pathToProperties
   * @throws Exception
   */
  private static void generateClientsProperties (
    ArrayList<String> clientsNames,
    final String pathToProperties) throws Exception {
    
    // format url per-client
    
    StringBuffer urlsPropertiesBuffer = new StringBuffer();
    
    for (String clientName : clientsNames) {
      
      urlsPropertiesBuffer.append(
        String.format(
          kClientsPropertiesClientBackendUrlFormat,
          CodeIdentifiersFormatterInl.lowerUnder(clientName) ) );
    }
    
    // format properties file
    String clientsProperties =
      String.format(
        kClientsPropertielsFileFormat,
        ClientsGeneratorConstantsInl.kPropertiesFileHeader,
        urlsPropertiesBuffer.toString() );
    
    // write properties file
    FileWriterInl.writeTextFile(
      clientsProperties,
      pathToProperties
        + "/"
        + kClientsPropertiesFileName,
      false);
  }
  
  private static final String kClientsPropertiesJavaFileName =
    "ClientsProperties.java";
  private static final String kClientsPropertiesJavaFileFormat =
    // %s: java file header
    "%s"
    // %s: package name
    + "package %s;\n\n"
    
    + "import java.util.HashMap;\n"
    + "import java.util.Map;\n\n"
    
    + "import com.vangav.backend.properties.PropertiesFile;\n"
    + "import com.vangav.backend.properties.PropertiesLoader;\n\n"
    
    + "/**\n"
    + " * Generated using ClientsGeneratorInl\n"
    + " */\n"
    + "/**\n"
    + " * ClientsProperties maps clients_properties.prop properties file which\n"
    + " *   primarily has links to this service's clients\n"
    + " */\n"
    + "public class ClientsProperties extends PropertiesFile {\n\n"
    
    + "  private static ClientsProperties instance = null;\n\n"
    
    + "  // disable default instantiation\n"
    + "  private ClientsProperties () {}\n\n"
    
    + "  /**\n"
    + "   * i\n"
    + "   * singleton instance method\n"
    + "   * @return ClientsProperties' singleton instance\n"
    + "   */\n"
    + "  public static ClientsProperties i () {\n\n"
    
    + "    if (instance == null) {\n\n"
    
    + "      instance = new ClientsProperties();\n"
    + "    }\n\n"
    
    + "    return instance;\n"
    + "  }\n\n"
    
    + "  private static final String kName = \"clients_properties\";\n\n"
    
    + "  @Override\n"
    + "  public String getName () {\n\n"
    
    + "    return kName;\n"
    + "  }\n"
    
    + "  // properties names\n"
    // kClientsPropertiesJavaPropertyNameFormat
    // %s: properties names
    + "%s\n"
    + "  // property name -> property default value\n"
    + "  private static final Map<String, String> kProperties;\n"
    + "  static {\n\n"
    
    + "    kProperties = new HashMap<String, String>();\n\n"
    
    // kClientsPropertiesJavaPropertyValueFormat
    // %s: properties default values
    + "%s"
    + "  }\n\n"
    
    + "  @Override\n"
    + "  protected String getProperty (String name) throws Exception {\n\n"
    
    + "    return PropertiesLoader.i().getProperty(\n"
    + "      this.getName(),\n"
    + "      name,\n"
    + "      kProperties.get(name) );\n"
    + "  }\n"
    + "}\n";
  private static final String kClientsPropertiesJavaPropertyNameFormat =
    // %s: client name (CamelCase)
    // %s: client name (lower_under)
    "  public static final String k%s = \"%s\";\n";
  private static final String kClientsPropertiesJavaPropertyValueFormat =
    // %s: client name (CamelCase)
    "    kProperties.put(\n"
    + "      k%s,\n"
    + "      \"http://localhost:9000/\");\n";
  /**
   * generateClientsPropertiesJava
   * generates ClientsProperties.java
   * @param clientsNames
   * @param packageName
   * @param pathToPackage
   * @throws Exception
   */
  private static void generateClientsPropertiesJava (
    ArrayList<String> clientsNames,
    String packageName,
    String pathToPackage) throws Exception {
    
    // format property name per-client
    
    StringBuffer namesBuffer = new StringBuffer();
    
    for (String clientName : clientsNames) {
      
      namesBuffer.append(
        String.format(
          kClientsPropertiesJavaPropertyNameFormat,
          CodeIdentifiersFormatterInl.camelCase(
            true,
            clientName),
          CodeIdentifiersFormatterInl.lowerUnder(
            clientName) ) );
    }
    
    // format property default value per-client
    
    StringBuffer valuesBuffer = new StringBuffer();
    
    for (String clientName : clientsNames) {
      
      valuesBuffer.append(
        String.format(
          kClientsPropertiesJavaPropertyValueFormat,
          CodeIdentifiersFormatterInl.camelCase(
            true,
            clientName) ) );
    }
    
    // format properties java file
    String clientsPropertiesJava =
      String.format(
        kClientsPropertiesJavaFileFormat,
        ClientsGeneratorConstantsInl.kJavaFileHeader,
        packageName,
        namesBuffer.toString(),
        valuesBuffer.toString() );
    
    // write properties file
    FileWriterInl.writeTextFile(
      clientsPropertiesJava,
      pathToPackage
        + "/"
        + kClientsPropertiesJavaFileName,
      false);
  }
  
  private static final String kConstantsFileName =
    "Constants.java";
  private static final String kConstantsFormat =
    // %s: java file header
    "%s"
    // %s: package name
    + "package %s;\n\n"
    
    + "import com.vangav.backend.exceptions.CodeException;\n"
    + "import com.vangav.backend.exceptions.VangavException.ExceptionClass;\n\n"
    
    + "/**\n"
    + " * Generated using ClientsGeneratorInl\n"
    + " */\n"
    + "/**\n"
    + " * Constants represents this service's clients' constants\n"
    + " *   primarily has links to this service's clients\n"
    + " */\n"
    + "public class Constants {\n\n"
    
    // kConstantsUrlFormat
    // %s: clients' urls
    + "%s"
    
    + "}\n";
  private static final String kConstantsUrlFormat =
    // %s: client name (CamelCase)
    "  public static final String k%sUrl;\n"
    + "  static {\n\n"
    
    + "    try {\n\n"
    
    // %s: client name (CamelCase)
    + "      k%sUrl =\n"
    + "        ClientsProperties.i().getStringPropterty(\n"
    // %s: client name (CamelCase)
    + "          ClientsProperties.k%s);\n"
    + "    } catch (Exception e) {\n\n"
    
    + "      throw new CodeException(\n"
    + "        1000,\n"
    // %d: client count (int) starting from 1
    + "        %d,\n"
    // %s: client name (CamelCase)
    + "        \"Couldn't initialize k%sUrl\",\n"
    + "        ExceptionClass.INITIALIZATION);\n"
    + "    }\n"
    + "  }\n\n";
  /**
   * generateConstants
   * generates Constants.java
   * @param clientsNames
   * @param packageName
   * @param pathToPackage
   * @throws Exception
   */
  private static void generateConstants (
    ArrayList<String> clientsNames,
    String packageName,
    String pathToPackage) throws Exception {
    
    // format clients' urls
    
    StringBuffer urlsBuffer = new StringBuffer();
    
    String currClientCamelCase;
    
    for (int i = 0; i < clientsNames.size(); i ++) {
      
      currClientCamelCase =
        CodeIdentifiersFormatterInl.camelCase(
          true,
          clientsNames.get(i) );
      
      urlsBuffer.append(
        String.format(
          kConstantsUrlFormat,
          currClientCamelCase,
          currClientCamelCase,
          currClientCamelCase,
          (i + 1),
          currClientCamelCase) );
    }
    
    // format constants java file
    String constantsJava =
      String.format(
        kConstantsFormat,
        ClientsGeneratorConstantsInl.kJavaFileHeader,
        packageName,
        urlsBuffer.toString() );
    
    // write properties file
    FileWriterInl.writeTextFile(
      constantsJava,
      pathToPackage
        + "/"
        + kConstantsFileName,
      false);
  }
  
  private static ControllerJson rewriteControllerJson (
    ControllerJson controllerJson) throws Exception {
    
    if (controllerJson.request_params != null) {
      
      for (RequestParamJson requestParamJson : controllerJson.request_params) {
        
        try {

          StringVerifierInl.belongsToEnum(
            "",
            requestParamJson.type,
            ParamType.class);
          
          requestParamJson.type =
            ParamType.getPrimitiveType(requestParamJson.type);
        } catch (Exception e) {
          
        }
      }
    }
    
    return controllerJson;
  }
  
  /**
   * generateClients
   * handles the generation of all classes needed for all clients
   *   - classes per-controller (ControllerCall, Request and Response classes)
   *   - error responses per-client (if any)
   * @param clientsJsonRaw
   * @param projectDirPath
   * @param projectName
   * @param rootPackage
   * @throws Exception
   */
  private static void generateClients (
    Map<String, String> clientsJsonRaw,
    final String projectDirPath,
    final String projectName,
    final String rootPackage) throws Exception {
    
    // get clients' names -> clients' json objects map
    
    Map<String, ClientJson> clientsJson = new HashMap<String, ClientJson>();
    
    for (String clientFileName : clientsJsonRaw.keySet() ) {
      
      clientsJson.put(
        clientFileName.replaceFirst("[.][^.]+$", ""),
        ClientJson.fromJsonString(clientsJsonRaw.get(clientFileName) ) );
    }
    
    // generate clients' classes
    
    ClientJson currClientJson;
    
    String clientsPackageName =
      JavaFormatterInl.getPackageName(
        rootPackage,
        projectName,
        ClientsGeneratorConstantsInl.kClientsPackageName);
    String currPackageName;
    String currPathToPackage;
    
    for (String clientName : clientsJson.keySet() ) {
      
      currClientJson = clientsJson.get(clientName);
      
      // generate controllers' classes
      
      if (currClientJson.controllers != null) {
        
        ControllerJson controllerJson;
        
        for (ControllerJson currControllerJson : currClientJson.controllers) {
          
          controllerJson = rewriteControllerJson(currControllerJson);
          
          currPackageName =
            JavaFormatterInl.getPackageName(
              rootPackage,
              projectName,
              ClientsGeneratorConstantsInl.kClientsPackageName,
              CodeIdentifiersFormatterInl.lowerUnder(
                clientName),
              CodeIdentifiersFormatterInl.lowerUnder(
                controllerJson.name) );
          
          currPathToPackage =
            JavaFormatterInl.getPathToPackage(
              projectDirPath,
              JavaFormatterInl.kPlaySrcDirName,
              rootPackage,
              projectName,
              ClientsGeneratorConstantsInl.kClientsPackageName,
              CodeIdentifiersFormatterInl.lowerUnder(
                clientName),
              CodeIdentifiersFormatterInl.lowerUnder(
                controllerJson.name) );
          
          // make directory
          FileWriterInl.mkdirs(currPathToPackage, false);
          
          generateControllerCall(
            clientName,
            controllerJson.name,
            currPackageName,
            clientsPackageName,
            currPathToPackage);
          
          if (controllerJson.type.compareToIgnoreCase("GET") == 0) {
            
            generateGetRequest(
              controllerJson.name,
              controllerJson.request_params,
              currPackageName,
              currPathToPackage);
          } else {
            
            generatePostRequest(
              controllerJson.name,
              controllerJson.request_params,
              currPackageName,
              currPathToPackage);
          }
          
          generateResponse(
            controllerJson.name,
            controllerJson.response_params,
            currPackageName,
            currPathToPackage);
        }
      }
      
      // generate error responses' classes
      
      if (currClientJson.error_responses != null) {
        
        for (ErrorResponseJson errorResponseJson :
             currClientJson.error_responses) {
          
          currPackageName =
            JavaFormatterInl.getPackageName(
              rootPackage,
              projectName,
              ClientsGeneratorConstantsInl.kClientsPackageName,
              CodeIdentifiersFormatterInl.lowerUnder(
                clientName),
              ClientsGeneratorConstantsInl.kClientErrorResponsesPackageName);
          
          currPathToPackage =
            JavaFormatterInl.getPathToPackage(
              projectDirPath,
              JavaFormatterInl.kPlaySrcDirName,
              rootPackage,
              projectName,
              ClientsGeneratorConstantsInl.kClientsPackageName,
              CodeIdentifiersFormatterInl.lowerUnder(
                clientName),
              ClientsGeneratorConstantsInl.kClientErrorResponsesPackageName);
          
          // make directory
          FileWriterInl.mkdirs(currPathToPackage, false);
          
          generateClientErrorResponse(
            errorResponseJson,
            currPackageName,
            currPathToPackage);
        }
      }
    }
  }
  
  private static final String kControllerCallFormat =
    // %s: java file header
    "%s"
    // %s: package name
    + "package %s;\n\n"
    
    + "import com.vangav.backend.backend_client_java.ControllerCall;\n"
    // %s: clients package name
    + "import %s.Constants;\n\n"
    
    + "/**\n"
    + " * Generated using ClientsGeneratorInl\n"
    + " */\n"
    + "/**\n"
    // %s: controller name (CamelCase)
    + " * ControllerCall%s is used to make one call\n"
    // %s: controller name (CamelCase)
    + " *   per instance to %s controller\n"
    + " */\n"
    // %s: controller name (CamelCase)
    + "public class ControllerCall%s extends ControllerCall {\n\n"
    
    + "  /**\n"
    // %s: controller name (CamelCase)
    + "   * Constructor - ControllerCall%s\n"
    // %s: controller name (CamelCase)
    + "   * @param request%s\n"
    // %s: controller name (CamelCase)
    + "   * @return new ControllerCall%s Object\n"
    + "   * @throws Exception\n"
    + "   */\n"
    // %s: controller name (CamelCase)
    + "  public ControllerCall%s (\n"
    // %s: controller name (CamelCase)
    // %s: controller name (CamelCase)
    + "    Request%s request%s) throws Exception {\n\n"
    
    // %s: controller name (CamelCase)
    + "    super(request%s);\n"
    + "  }\n\n"
    
    + "  @Override\n"
    + "  protected String getControllerName() {\n\n"
    
    // %s: controller name (lower_under)
    + "    return \"%s\";\n"
    + "  }\n\n"
    
    + "  @Override\n"
    + "  protected String getUrl() {\n\n"
    
    + "    return\n"
    // %s: client name (CamelCase)
    + "      Constants.k%sUrl\n"
    + "      + this.getControllerName();\n"
    + "  }\n\n"
    
    + "  @Override\n"
    // %s: controller name (CamelCase)
    + "  protected Response%s getRestResponseJson() {\n\n"
    
    // %s: controller name (CamelCase)
    + "    return new Response%s();\n"
    + "  }\n"
    + "}\n";
  /**
   * generateControllerCall
   * generates client_name/controller_name/ControllerCall.java
   * @param clientName
   * @param controllerName
   * @param packageName
   * @param clientsPackageName
   * @param pathToPackage
   * @throws Exception
   */
  private static void generateControllerCall (
    String clientName,
    String controllerName,
    String packageName,
    String clientsPackageName,
    String pathToPackage) throws Exception {
    
    String controllerNameCamelCase =
      CodeIdentifiersFormatterInl.camelCase(
        true,
        controllerName);
    
    // format controller call java file
    String controllerCallJava =
      String.format(
        kControllerCallFormat,
        ClientsGeneratorConstantsInl.kJavaFileHeader,
        packageName,
        clientsPackageName,
        controllerNameCamelCase,
        controllerNameCamelCase,
        controllerNameCamelCase,
        controllerNameCamelCase,
        controllerNameCamelCase,
        controllerNameCamelCase,
        controllerNameCamelCase,
        controllerNameCamelCase,
        controllerNameCamelCase,
        controllerNameCamelCase,
        CodeIdentifiersFormatterInl.lowerUnder(
          controllerName),
        CodeIdentifiersFormatterInl.camelCase(
          true,
          clientName),
        controllerNameCamelCase,
        controllerNameCamelCase);
    
    // write controller call java file
    FileWriterInl.writeTextFile(
      controllerCallJava,
      pathToPackage
        + "/ControllerCall"
        + controllerNameCamelCase
        + ".java",
      false);
  }
  
  private static final String kGetRequestFormat =
    // %s: java file header
    "%s"
    // %s: package name
    + "package %s;\n\n"
    
    + "import com.vangav.backend.networks.rest_client.RestRequestGetQuery;\n\n"
    
    + "/**\n"
    + " * Generated using ClientsGeneratorInl\n"
    + " */\n"
    + "/**\n"
    // %s: controller name (CamelCase)
    + " * Request%s represents the request to\n"
    // %s: controller name (CamelCase)
    + " *   be sent to %s controller\n"
    + " */\n"
    // %s: controller name (CamelCase)
    + "public class Request%s extends RestRequestGetQuery {\n\n"
    
    // kGetRequestConstructorFormat
    // %s: one or two constructors (if there are optional params)
    + "%s"
    
    // kGetRequestOptionalParamsSettersHeaderComment
    // kGetRequestOptionalParamSetterFormat
    // optional params setters (if any)
    + "%s}\n";
  private static final String kGetRequestConstructorFormat =
    "  /**\n"
    // %s: controller name (CamelCase)
    + "   * Constructor - Request%s\n"
    // kGetRequestConstructorMandatory
    // kGetRequestConstructorOptional
    // constructor type (if applicable)
    + "%s"
    // kGetRequestConstructorCommentParamFormat
    // %s: comment params
    + "%s"
    // %s: controller name (CamelCase)
    + "   * @return new Request%s Object\n"
    + "   * @throws Exception\n"
    + "   */\n"
    // %s: controller name (CamelCase)
    + "  public Request%s ("
    // kGetRequestConstructorArgParamFormat
    // %s: args params
    + "%s"
    + ") throws Exception {\n\n"
    
    // kGetRequestConstructorValueParamFormat
    // %s: assign values to params
    + "%s}\n\n";
  private static final String kGetRequestConstructorMandatory =
    "   * this is the constructor for mandatory request's params only\n";
  private static final String kGetRequestConstructorOptional =
    "   * this is the constructor for all request's params (mandatory and optional)\n";
  private static final String kGetRequestConstructorCommentParamFormat =
    // %s: param name (lowerCamelCase)
    "   * @param %s\n";
  private static final String kGetRequestConstructorArgParamFormat =
    // %s: param type (String, int, ...)
    // %s: param name (lowerCamelCase)
    "\n    %s %s";
  private static final String kGetRequestConstructorValueParamFormat =
    "    this.addParam(\n"
    // %s: param name (lower_under)
    + "      \"%s\",\n"
    // %s: param name (lowerCamelCase)
    + "      %s);\n\n";
  private static final String kGetRequestOptionalParamsSettersHeaderComment =
    "  /*\n"
    + "   * Following are individual setters per-optional-param\n"
    + "   * */\n";
  private static final String kGetRequestOptionalParamSetterFormat =
    "\n  /**\n"
    // %s: param name (CamelCase)
    + "   * set%s\n"
    // %s: param name (lowerCamelCase)
    + "   * @param %s\n"
    + "   * @throws Exception\n"
    + "   */\n"
    // %s: param name (CamelCase)
    + "  public void set%s (\n"
    // %s: param type (String, int, ...)
    // %s: param name (lowerCamelCase)
    + "    %s %s) throws Exception {\n\n"
    
    + "    this.addParam(\n"
    // %s: param name (lower_under)
    + "      \"%s\",\n"
    // %s: param name (lowerCamelCase)
    + "      %s);\n"
    + "  }\n";
  /**
   * generateGetRequest
   * generates client_name/controller_name/Request.java
   * @param controllerName
   * @param requestParamsJson
   * @param packageName
   * @param pathToPackage
   * @throws Exception
   */
  private static void generateGetRequest (
    String controllerName,
    RequestParamJson[] requestParamsJson,
    String packageName,
    String pathToPackage) throws Exception {
    
    String controllerNameCamelCase =
      CodeIdentifiersFormatterInl.camelCase(
        true,
        controllerName);
    
    // separate mandatory params from optional params
    
    ArrayList<RequestParamJson> mandatoryParams =
      new ArrayList<RequestParamJson>();
    ArrayList<RequestParamJson> optionalParams =
      new ArrayList<RequestParamJson>();
    
    if (requestParamsJson != null) {
      
      for (RequestParamJson requestParamJson : requestParamsJson) {
        
        if (requestParamJson.optionality.compareToIgnoreCase(
              "MANDATORY") == 0) {
          
          mandatoryParams.add(requestParamJson);
        } else {
          
          optionalParams.add(requestParamJson);
        }
      }
    }
    
    // format constructor(s)
    
    StringBuffer constructorsBuffer = new StringBuffer();
    
    StringBuffer constructorCommentParamsBuffer = new StringBuffer();    
    StringBuffer constructorArgsParamsBuffer = new StringBuffer();    
    StringBuffer constructorValuesParamsBuffer = new StringBuffer();
    
    RequestParamJson requestParamJson;
    
    for (int i = 0; i < mandatoryParams.size(); i ++) {
      
      requestParamJson = mandatoryParams.get(i);
      
      constructorCommentParamsBuffer.append(
        String.format(
          kGetRequestConstructorCommentParamFormat,
          CodeIdentifiersFormatterInl.lowerCamelCase(
            true,
            requestParamJson.name) ) );
      
      if (requestParamJson.is_array == true) {
        
        constructorArgsParamsBuffer.append(
          String.format(
            kGetRequestConstructorArgParamFormat,
            requestParamJson.type
              + "[]",
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              requestParamJson.name) ) );
      } else {
        
        constructorArgsParamsBuffer.append(
          String.format(
            kGetRequestConstructorArgParamFormat,
            requestParamJson.type,
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              requestParamJson.name) ) );
      }
      
      if (i < (mandatoryParams.size() - 1 ) ) {
        
        constructorArgsParamsBuffer.append(",");
      }
      
      constructorValuesParamsBuffer.append(
        String.format(
          kGetRequestConstructorValueParamFormat,
          CodeIdentifiersFormatterInl.lowerUnder(
            requestParamJson.name),
          CodeIdentifiersFormatterInl.lowerCamelCase(
            true,
            requestParamJson.name) ) );
    }
    
    String mainConstructor =
      String.format(
        kGetRequestConstructorFormat,
        controllerNameCamelCase,
        kGetRequestConstructorMandatory,
        constructorCommentParamsBuffer.toString(),
        controllerNameCamelCase,
        controllerNameCamelCase,
        constructorArgsParamsBuffer.toString(),
        constructorValuesParamsBuffer.toString() );
    
    constructorsBuffer.append(mainConstructor);
    
    if (optionalParams.isEmpty() == false) {
      
      if (mandatoryParams.isEmpty() == false) {
        
        constructorArgsParamsBuffer.append(",");
      }
      
      for (int i = 0; i < optionalParams.size(); i ++) {
        
        requestParamJson = optionalParams.get(i);
        
        constructorCommentParamsBuffer.append(
          String.format(
            kGetRequestConstructorCommentParamFormat,
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              requestParamJson.name) ) );
        
        if (requestParamJson.is_array == true) {
          
          constructorArgsParamsBuffer.append(
            String.format(
              kGetRequestConstructorArgParamFormat,
              requestParamJson.type
                + "[]",
              CodeIdentifiersFormatterInl.lowerCamelCase(
                true,
                requestParamJson.name) ) );
        } else {
          
          constructorArgsParamsBuffer.append(
            String.format(
              kGetRequestConstructorArgParamFormat,
              requestParamJson.type,
              CodeIdentifiersFormatterInl.lowerCamelCase(
                true,
                requestParamJson.name) ) );
        }
        
        if (i < (optionalParams.size() - 1 ) ) {
          
          constructorArgsParamsBuffer.append(",");
        }
        
        constructorValuesParamsBuffer.append(
          String.format(
            kGetRequestConstructorValueParamFormat,
            CodeIdentifiersFormatterInl.lowerUnder(
              requestParamJson.name),
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              requestParamJson.name) ) );
      }
      
      String optionalConstructor =
        String.format(
          kGetRequestConstructorFormat,
          controllerNameCamelCase,
          kGetRequestConstructorOptional,
          constructorCommentParamsBuffer.toString(),
          controllerNameCamelCase,
          controllerNameCamelCase,
          constructorArgsParamsBuffer.toString(),
          constructorValuesParamsBuffer.toString() );
      
      constructorsBuffer.append(optionalConstructor);
    }
    
    // format optional params setters
    
    StringBuffer optionalParamsSettersBuffer = new StringBuffer();
    
    if (optionalParams.isEmpty() == false) {
      
      optionalParamsSettersBuffer.append(
        kGetRequestOptionalParamsSettersHeaderComment);
    }
    
    for (RequestParamJson currRequestParamJson : optionalParams) {
      
      if (currRequestParamJson.is_array == true) {
      
        optionalParamsSettersBuffer.append(
          String.format(
            kGetRequestOptionalParamSetterFormat,
            CodeIdentifiersFormatterInl.camelCase(
              true,
              currRequestParamJson.name),
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              currRequestParamJson.name),
            CodeIdentifiersFormatterInl.camelCase(
              true,
              currRequestParamJson.name),
            currRequestParamJson.type
              + "[]",
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              currRequestParamJson.name),
            CodeIdentifiersFormatterInl.lowerUnder(
              currRequestParamJson.name),
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              currRequestParamJson.name) ) );
      } else {
      
        optionalParamsSettersBuffer.append(
          String.format(
            kGetRequestOptionalParamSetterFormat,
            CodeIdentifiersFormatterInl.camelCase(
              true,
              currRequestParamJson.name),
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              currRequestParamJson.name),
            CodeIdentifiersFormatterInl.camelCase(
              true,
              currRequestParamJson.name),
            currRequestParamJson.type,
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              currRequestParamJson.name),
            CodeIdentifiersFormatterInl.lowerUnder(
              currRequestParamJson.name),
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              currRequestParamJson.name) ) );
      }
    }
    
    // format get request java file
    String getRequestJava =
      String.format(
        kGetRequestFormat,
        ClientsGeneratorConstantsInl.kJavaFileHeader,
        packageName,
        controllerNameCamelCase,
        controllerNameCamelCase,
        controllerNameCamelCase,
        constructorsBuffer.toString(),
        optionalParamsSettersBuffer.toString() );
    
    // write get request java file
    FileWriterInl.writeTextFile(
      getRequestJava,
      pathToPackage
        + "/Request"
        + controllerNameCamelCase
        + ".java",
      false);
  }
  
  private static final String kPostRequestFormat =
    // %s: java file header
    "%s"
    // %s: package name
    + "package %s;\n\n"
    
    + "import com.fasterxml.jackson.annotation.JsonIgnore;\n"
    + "import com.fasterxml.jackson.annotation.JsonIgnoreProperties;\n"
    + "import com.fasterxml.jackson.annotation.JsonProperty;\n"
    + "import com.vangav.backend.networks.rest_client.RestRequestPostJson;\n\n"
    
    + "/**\n"
    + " * Generated using ClientsGeneratorInl\n"
    + " */\n"
    + "/**\n"
    // %s: controller name (CamelCase)
    + " * Request%s represents the request to\n"
    // %s: controller name (CamelCase)
    + " *   be sent to %s controller\n"
    + " * */\n"
    + "@JsonIgnoreProperties(ignoreUnknown = true)\n"
    // %s: controller name (CamelCase)
    + "public class Request%s extends RestRequestPostJson {\n\n"
    
    // kPostRequestConstructorFormat
    // %s: one or two constructors (if there are optional params)
    + "%s"
    
    + "  @Override\n"
    + "  @JsonIgnore\n"
    + "  protected String getName() throws Exception {\n\n"
    
    // %s: controller name (lower_under)
    + "    return \"%s\";\n"
    + "  }\n\n"
    
    + "  @Override\n"
    + "  @JsonIgnore\n"
    // %s: controller name (CamelCase)
    + "  protected Request%s getThis() throws Exception {\n\n"
    
    + "    return this;\n"
    + "  }\n\n"
    
    // kPostRequestJsonPropertyFormat
    // %s: json properties
    + "%s"
    
    // kPostRequestOptionalParamsSettersHeaderComment
    // kPostRequestOptionalParamSetterFormat
    // optional params setters (if any)
    + "\n%s}\n";
  private static final String kPostRequestConstructorFormat =
    "  /**\n"
    // %s: controller name (CamelCase)
    + "   * Constructor - Request%s\n"
    // kPostRequestConstructorMandatory
    // kPostRequestConstructorOptional
    // constructor type (if applicable)
    + "%s"
    // kPostRequestConstructorCommentParamFormat
    // %s: comment params
    + "%s"
    // %s: controller name (CamelCase)
    + "   * @return new Request%s Object\n"
    + "   * @throws Exception\n"
    + "   */\n"
    + "  @JsonIgnore\n"
    // %s: controller name (CamelCase)
    + "  public Request%s ("
    // kPostRequestConstructorArgParamFormat
    // %s: args params
    + "%s"
    + ") throws Exception {\n\n"
    
    // kPostRequestConstructorValueParamFormat
    // %s: assign values to params
    + "%s}\n\n";
  private static final String kPostRequestConstructorMandatory =
    "   * this is the constructor for mandatory request's params only\n";
  private static final String kPostRequestConstructorOptional =
    "   * this is the constructor for all request's params (mandatory and optional)\n";
  private static final String kPostRequestConstructorCommentParamFormat =
    // %s: param name (lowerCamelCase)
    "   * @param %s\n";
  private static final String kPostRequestConstructorArgParamFormat =
    // %s: param type (String, int, ...)
    // %s: param name (lowerCamelCase)
    "\n    %s %s";
  private static final String kPostRequestConstructorValueParamFormat =
    // %s: param name (lower_under)
    "    this.%s =\n"
    // %s: param name (lowerCamelCase)
    + "      %s;\n";
  private static final String kPostRequestJsonPropertyFormat =
    "  @JsonProperty\n"
    // %s: param type (String, int, ...)
    // %s: param name (lower_under)
    + "  public %s %s;\n";
  private static final String kPostRequestOptionalParamsSettersHeaderComment =
    "  /*\n"
    + "   * Following are individual setters per-optional-param\n"
    + "   * */\n";
  private static final String kPostRequestOptionalParamSetterFormat =
    "\n  /**\n"
    // %s: param name (CamelCase)
    + "   * set%s\n"
    // %s: param name (lowerCamelCase)
    + "   * @param %s\n"
    + "   * @throws Exception\n"
    + "   */\n"
    + "  @JsonIgnore\n"
    // %s: param name (CamelCase)
    + "  public void set%s (\n"
    // %s: param type (String, int, ...)
    // %s: param name (lowerCamelCase)
    + "    %s %s) throws Exception {\n\n"
    
    // %s: param name (lower_under)
    // %s: param name (lowerCamelCase)
    + "    this.%s = %s;\n"
    + "  }\n";
  /**
   * generateResponse
   * generates client_name/controller_name/Request.java
   * @param controllerName
   * @param requestParamsJson
   * @param packageName
   * @param pathToPackage
   * @throws Exception
   */
  private static void generatePostRequest (
    String controllerName,
    RequestParamJson[] requestParamsJson,
    String packageName,
    String pathToPackage) throws Exception {
    
    String controllerNameCamelCase =
      CodeIdentifiersFormatterInl.camelCase(
        true,
        controllerName);
    
    // separate mandatory params from optional params
    
    ArrayList<RequestParamJson> mandatoryParams =
      new ArrayList<RequestParamJson>();
    ArrayList<RequestParamJson> optionalParams =
      new ArrayList<RequestParamJson>();
    
    if (requestParamsJson != null) {
      
      for (RequestParamJson requestParamJson : requestParamsJson) {
        
        if (requestParamJson.optionality.compareToIgnoreCase(
              "MANDATORY") == 0) {
          
          mandatoryParams.add(requestParamJson);
        } else {
          
          optionalParams.add(requestParamJson);
        }
      }
    }
    
    // format constructor(s)
    
    StringBuffer constructorsBuffer = new StringBuffer();
    
    StringBuffer constructorCommentParamsBuffer = new StringBuffer();    
    StringBuffer constructorArgsParamsBuffer = new StringBuffer();    
    StringBuffer constructorValuesParamsBuffer = new StringBuffer();
    
    RequestParamJson requestParamJson;

    for (int i = 0; i < mandatoryParams.size(); i ++) {
      
      requestParamJson = mandatoryParams.get(i);
      
      constructorCommentParamsBuffer.append(
        String.format(
          kPostRequestConstructorCommentParamFormat,
          CodeIdentifiersFormatterInl.lowerCamelCase(
            true,
            requestParamJson.name) ) );
      
      if (requestParamJson.is_array == true) {
        
        constructorArgsParamsBuffer.append(
          String.format(
            kPostRequestConstructorArgParamFormat,
            requestParamJson.type
              + "[]",
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              requestParamJson.name) ) );
      } else {
        
        constructorArgsParamsBuffer.append(
          String.format(
            kPostRequestConstructorArgParamFormat,
            requestParamJson.type,
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              requestParamJson.name) ) );
      }
      
      if (i < (mandatoryParams.size() - 1 ) ) {
        
        constructorArgsParamsBuffer.append(",");
      }
      
      constructorValuesParamsBuffer.append(
        String.format(
          kPostRequestConstructorValueParamFormat,
          CodeIdentifiersFormatterInl.lowerUnder(
            requestParamJson.name),
          CodeIdentifiersFormatterInl.lowerCamelCase(
            true,
            requestParamJson.name) ) );
    }
    
    String mainConstructor =
      String.format(
        kPostRequestConstructorFormat,
        controllerNameCamelCase,
        kPostRequestConstructorMandatory,
        constructorCommentParamsBuffer.toString(),
        controllerNameCamelCase,
        controllerNameCamelCase,
        constructorArgsParamsBuffer.toString(),
        constructorValuesParamsBuffer.toString() );
    
    constructorsBuffer.append(mainConstructor);
    
    if (optionalParams.isEmpty() == false) {
      
      if (mandatoryParams.isEmpty() == false) {
        
        constructorArgsParamsBuffer.append(",");
      }
      
      for (int i = 0; i < optionalParams.size(); i ++) {
        
        requestParamJson = optionalParams.get(i);
        
        constructorCommentParamsBuffer.append(
          String.format(
            kPostRequestConstructorCommentParamFormat,
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              requestParamJson.name) ) );
        
        if (requestParamJson.is_array == true) {
          
          constructorArgsParamsBuffer.append(
            String.format(
              kPostRequestConstructorArgParamFormat,
              requestParamJson.type
                + "[]",
              CodeIdentifiersFormatterInl.lowerCamelCase(
                true,
                requestParamJson.name) ) );
        } else {
          
          constructorArgsParamsBuffer.append(
            String.format(
              kPostRequestConstructorArgParamFormat,
              requestParamJson.type,
              CodeIdentifiersFormatterInl.lowerCamelCase(
                true,
                requestParamJson.name) ) );
        }
        
        if (i < (optionalParams.size() - 1 ) ) {
          
          constructorArgsParamsBuffer.append(",");
        }
        
        constructorValuesParamsBuffer.append(
          String.format(
            kPostRequestConstructorValueParamFormat,
            CodeIdentifiersFormatterInl.lowerUnder(
              requestParamJson.name),
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              requestParamJson.name) ) );
      }
      
      String optionalConstructor =
        String.format(
          kPostRequestConstructorFormat,
          controllerNameCamelCase,
          kPostRequestConstructorOptional,
          constructorCommentParamsBuffer.toString(),
          controllerNameCamelCase,
          controllerNameCamelCase,
          constructorArgsParamsBuffer.toString(),
          constructorValuesParamsBuffer.toString() );
      
      constructorsBuffer.append(optionalConstructor);
    }
    
    // format json properties
    
    StringBuffer jsonPropertiesBuffer = new StringBuffer();
    
    for (RequestParamJson currRequestParamJson : mandatoryParams) {
      
      jsonPropertiesBuffer.append(
        String.format(
          kPostRequestJsonPropertyFormat,
          currRequestParamJson.type,
          CodeIdentifiersFormatterInl.lowerUnder(
            currRequestParamJson.name) ) );
    }
    
    for (RequestParamJson currRequestParamJson : optionalParams) {
      
      jsonPropertiesBuffer.append(
        String.format(
          kPostRequestJsonPropertyFormat,
          currRequestParamJson.type,
          CodeIdentifiersFormatterInl.lowerUnder(
            currRequestParamJson.name) ) );
    }
    
    // format optional params setters
    
    StringBuffer optionalParamsSettersBuffer = new StringBuffer();
    
    if (optionalParams.isEmpty() == false) {
      
      optionalParamsSettersBuffer.append(
        kPostRequestOptionalParamsSettersHeaderComment);
    }
    
    for (RequestParamJson currRequestParamJson : optionalParams) {
      
      if (currRequestParamJson.is_array == true) {
        
        optionalParamsSettersBuffer.append(
          String.format(
            kPostRequestOptionalParamSetterFormat,
            CodeIdentifiersFormatterInl.camelCase(
              true,
              currRequestParamJson.name),
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              currRequestParamJson.name),
            CodeIdentifiersFormatterInl.camelCase(
              true,
              currRequestParamJson.name),
            currRequestParamJson.type
              + "[]",
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              currRequestParamJson.name),
            CodeIdentifiersFormatterInl.lowerUnder(
              currRequestParamJson.name),
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              currRequestParamJson.name) ) );
      } else {
      
        optionalParamsSettersBuffer.append(
          String.format(
            kPostRequestOptionalParamSetterFormat,
            CodeIdentifiersFormatterInl.camelCase(
              true,
              currRequestParamJson.name),
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              currRequestParamJson.name),
            CodeIdentifiersFormatterInl.camelCase(
              true,
              currRequestParamJson.name),
            currRequestParamJson.type,
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              currRequestParamJson.name),
            CodeIdentifiersFormatterInl.lowerUnder(
              currRequestParamJson.name),
            CodeIdentifiersFormatterInl.lowerCamelCase(
              true,
              currRequestParamJson.name) ) );
      }
    }
    
    // format post request java file
    String postRequestJava =
      String.format(
        kPostRequestFormat,
        ClientsGeneratorConstantsInl.kJavaFileHeader,
        packageName,
        controllerNameCamelCase,
        controllerNameCamelCase,
        controllerNameCamelCase,
        constructorsBuffer.toString(),
        CodeIdentifiersFormatterInl.lowerUnder(
          controllerName),
        controllerNameCamelCase,
        jsonPropertiesBuffer.toString(),
        optionalParamsSettersBuffer.toString() );
    
    // write post request java file
    FileWriterInl.writeTextFile(
      postRequestJava,
      pathToPackage
        + "/Request"
        + controllerNameCamelCase
        + ".java",
      false);
  }
  
  private static final String kResponseFormat =
    // %s: java file header
    "%s"
    // %s: package name
    + "package %s;\n\n"
    
    + "import com.fasterxml.jackson.annotation.JsonIgnore;\n"
    + "import com.fasterxml.jackson.annotation.JsonIgnoreProperties;\n"
    + "import com.fasterxml.jackson.annotation.JsonProperty;\n"
    + "import com.vangav.backend.networks.rest_client.RestResponseJson;\n\n"
    
    + "/**\n"
    + " * Generated using ClientsGeneratorInl\n"
    + " */\n"
    + "/**\n"
    // %s: controller name (CamelCase)
    + " * Response%s represents the JSON response\n"
    // %s: controller name (CamelCase)
    + " *   to be received from %s controller\n"
    + " * */\n"
    + "@JsonIgnoreProperties(ignoreUnknown = true)\n"
    // %s: controller name (CamelCase)
    + "public class Response%s extends RestResponseJson {\n\n"
    
    + "  @Override\n"
    + "  @JsonIgnore\n"
    + "  protected String getName () throws Exception {\n\n"
    
    // %s: controller name (lower_under)
    + "    return \"%s\";\n"
    + "  }\n\n"
    
    + "  @Override\n"
    + "  @JsonIgnore\n"
    // %s: controller name (CamelCase)
    + "  protected Response%s getThis () throws Exception {\n\n"
    
    + "    return this;\n"
    + "  }\n\n"
    
    // kResponseJsonPropertyFormat
    // %s: json properties
    + "%s}\n";
  private static final String kResponseJsonPropertyFormat =
    "  @JsonProperty\n"
    // %s: param type (String, int, ...)
    // %s: param name (lower_under)
    + "  public %s %s;\n";
  /**
   * generateResponse
   * generates client_name/controller_name/Response.java
   * @param controllerName
   * @param responseParamsJson
   * @param packageName
   * @param pathToPackage
   * @throws Exception
   */
  private static void generateResponse (
    String controllerName,
    ResponseParamJson[] responseParamsJson,
    String packageName,
    String pathToPackage) throws Exception {
    
    // format response params
    
    StringBuffer responseParamsBuffer = new StringBuffer();
    
    if (responseParamsJson != null) {
      
      for (ResponseParamJson responseParamJson : responseParamsJson) {
        
        if (responseParamJson.is_array == true) {
        
          responseParamsBuffer.append(
            String.format(
              kResponseJsonPropertyFormat,
              responseParamJson.type
                + "[]",
              CodeIdentifiersFormatterInl.lowerUnder(
                responseParamJson.name) ) );
        } else {
        
          responseParamsBuffer.append(
            String.format(
              kResponseJsonPropertyFormat,
              responseParamJson.type,
              CodeIdentifiersFormatterInl.lowerUnder(
                responseParamJson.name) ) );
        }
      }
    }
    
    // format response java file
    
    String controllerNameCamelCase =
      CodeIdentifiersFormatterInl.camelCase(
        true,
        controllerName);
    
    String responseJava =
      String.format(
        kResponseFormat,
        ClientsGeneratorConstantsInl.kJavaFileHeader,
        packageName,
        controllerNameCamelCase,
        controllerNameCamelCase,
        controllerNameCamelCase,
        CodeIdentifiersFormatterInl.lowerUnder(controllerName),
        controllerNameCamelCase,
        responseParamsBuffer.toString() );
    
    // write response java file
    FileWriterInl.writeTextFile(
      responseJava,
      pathToPackage
        + "/Response"
        + controllerNameCamelCase
        + ".java",
      false);
  }
  
  private static final String kErrorResponseFormat =
    // %s: java file header
    "%s"
    // %s: package name
    + "package %s;\n\n"
    
    + "import com.fasterxml.jackson.annotation.JsonIgnore;\n"
    + "import com.fasterxml.jackson.annotation.JsonIgnoreProperties;\n"
    + "import com.fasterxml.jackson.annotation.JsonProperty;\n"
    + "import com.vangav.backend.networks.rest_client.RestResponseJson;\n\n"
    
    + "/**\n"
    + " * Generated using ClientsGeneratorInl\n"
    + " */\n"
    + "/**\n"
    // %s: error response name (CamelCase)
    + " * %s represents an error response's format for\n"
    // %s: http status codes (e.g.: [400, 500])
    + " *   %s http status codes\n"
    + " * */\n"
    + "@JsonIgnoreProperties(ignoreUnknown = true)\n"
    // %s: error response name (CamelCase)
    + "public class %s extends RestResponseJson {\n\n"
    
    + "  @Override\n"
    + "  @JsonIgnore\n"
    + "  protected String getName () throws Exception {\n\n"
    
    // %s: error response name (lower_under)
    + "    return \"%s\";\n"
    + "  }\n\n"
    
    + "  @Override\n"
    + "  @JsonIgnore\n"
    // %s: error response name (CamelCase)
    + "  protected %s getThis () throws Exception {\n\n"
    
    + "    return this;\n"
    + "  }\n\n"
    
    // kErrorResponseJsonPropertyFormat
    // %s: json properties
    + "%s}\n";
  private static final String kErrorResponseJsonPropertyFormat =
    "  @JsonProperty\n"
    // %s: param type (String, int, ...)
    // %s: param name (lower_under)
    + "  public %s %s;\n";
  /**
   * generateClientErrorResponse
   * generates client_name/error_responses/ErrorResponse.java
   * @param errorResponseJson
   * @param packageName
   * @param pathToPackage
   * @throws Exception
   */
  private static void generateClientErrorResponse (
    ErrorResponseJson errorResponseJson,
    String packageName,
    String pathToPackage) throws Exception {
    
    // format response params
    
    StringBuffer responseParamsBuffer = new StringBuffer();
    
    if (errorResponseJson.response_params != null) {
      
      for (ResponseParamJson responseParamJson :
           errorResponseJson.response_params) {
        
        if (responseParamJson.is_array == true) {
        
          responseParamsBuffer.append(
            String.format(
              kErrorResponseJsonPropertyFormat,
              responseParamJson.type
                + "[]",
              CodeIdentifiersFormatterInl.lowerUnder(
                responseParamJson.name) ) );
        } else {
        
          responseParamsBuffer.append(
            String.format(
              kErrorResponseJsonPropertyFormat,
              responseParamJson.type,
              CodeIdentifiersFormatterInl.lowerUnder(
                responseParamJson.name) ) );
        }
      }
    }
    
    // format error response java file
    
    String errorResponseNameCamelCase =
      CodeIdentifiersFormatterInl.camelCase(
        true,
        errorResponseJson.name);
    
    String errorResponseJava =
      String.format(
        kErrorResponseFormat,
        ClientsGeneratorConstantsInl.kJavaFileHeader,
        packageName,
        errorResponseNameCamelCase,
        Arrays.toString(errorResponseJson.http_status_codes),
        errorResponseNameCamelCase,
        CodeIdentifiersFormatterInl.lowerUnder(errorResponseJson.name),
        errorResponseNameCamelCase,
        responseParamsBuffer.toString() );
    
    // write error response java file
    FileWriterInl.writeTextFile(
      errorResponseJava,
      pathToPackage
        + "/"
        + errorResponseNameCamelCase
        + ".java",
      false);
  }
}
