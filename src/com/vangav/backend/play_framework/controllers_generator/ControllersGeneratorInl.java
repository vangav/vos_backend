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

package com.vangav.backend.play_framework.controllers_generator;

import java.util.ArrayList;

import com.vangav.backend.content.formatting.CodeIdentifiersFormatterInl;
import com.vangav.backend.content.formatting.JavaFormatterInl;
import com.vangav.backend.files.FileLoaderInl;
import com.vangav.backend.files.FileWriterInl;
import com.vangav.backend.play_framework.controllers_generator.json.ControllerJson;
import com.vangav.backend.play_framework.controllers_generator.json.ControllersJson;
import com.vangav.backend.play_framework.controllers_generator.json.RequestParamJson;
import com.vangav.backend.play_framework.controllers_generator.json.ResponseParamJson;
import com.vangav.backend.play_framework.param.ParamType;
import com.vangav.backend.play_framework.request.RequestJsonBodyGet;
import com.vangav.backend.system.CommandLineInl;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * ControllersGeneratorInl generate JAVA controllers' classes for a backend
 * It generats CommonHandler.java and request_properties.prop for
 *   all controllers
 * And for each controller it generates:
 * 1) Controller.java: represents the controller's entry point
 * 2) Handler.java: handles request-to-response processing
 * 3) Request.java: represents the controller's request
 * 4) Response.java: represents the controller's response
 * 
 * note: I don't like the design of this class, while working on it I don't
 *         have the capacity to come up with a better design that's would
 *         ideally be more generic
 *       But it works and saves hours (or months on scale) of work as the
 *         Backend grows while keeping things consistent and error-free
 *       A different technique is used for Vangav M code generation but it may
 *         be tricky to maintain for others! that's why the current technique
 *         is used instead.
 * */
public class ControllersGeneratorInl {
  
  // disable default instantiation
  private ControllersGeneratorInl () {}

  /**
   * generateControllers
   * handles the generation/writing of all controllers' classes along with
   *   the requests' properties file and the common handler
   * @param configDirPath
   * @param projectDirPath
   * @throws Exception
   */
  public static void generateControllers (
    final String configDirPath,
    final String projectDirPath,
    final String projectName) throws Exception {
    
    if (FileLoaderInl.fileExists(
          configDirPath
          + "/"
          + ControllersGeneratorConstantsInl.kControllersConfigFileName) ==
        false) {
      
      return;
    }
    
    String controllersJsonRaw =
      FileLoaderInl.loadTextFileWithoutComments(
        ControllersGeneratorConstantsInl.kControllerCommentPrefix,
        configDirPath
        + "/"
        + ControllersGeneratorConstantsInl.kControllersConfigFileName);
    
    ControllersJson controllersJson =
      ControllersJson.fromJsonString(controllersJsonRaw);

    // make controllers dir
    
    String pathToControllersPackage = JavaFormatterInl.getPathToPackage(
      projectDirPath,
      JavaFormatterInl.kPlaySrcDirName,
      controllersJson.java_package,
      projectName,
      "controllers");
    
    FileWriterInl.mkdirs(pathToControllersPackage, false);
    
    // generate/copy properties, code and conf files

    // notifications? copy properties files
    if (controllersJson.notifications == true) {
      
      CommandLineInl.executeCommand(
        "cp ../prop/android_notification_properties.prop "
        + "../../"
        + projectName
        + "/conf/prop/android_notification_properties.prop");
      CommandLineInl.executeCommand(
        "cp ../prop/apple_notification_properties.prop"
        + "../../"
        + projectName
        + "/conf/prop/apple_notification_properties.prop");
    }
    
    generateRequestProperties(controllersJson, projectDirPath);
    generateCommonHandler(controllersJson, projectDirPath,projectName);
    
    if (controllersJson.controllers != null) {
      
      // generate controllers' classes
      for (ControllerJson controllerJson : controllersJson.controllers) {
        
        generateControllerClass(
          controllersJson,
          projectDirPath,
          controllerJson,
          projectName);
        generateHandlerClass(
          controllersJson,
          projectDirPath,
          controllerJson,
          projectName);
        generateRequestClass(
          controllersJson,
          projectDirPath,
          controllerJson,
          projectName);
        generateResponseClass(
          controllersJson,
          projectDirPath,
          controllerJson,
          projectName);
      }
      
      // update conf/routes file
      
      ArrayList<String> controllersRoutes = new ArrayList<String>();
      controllersRoutes.add("\n\n# Generated routes\n");
      StringBuffer currControllerRoute;
      for (ControllerJson controllerJson : controllersJson.controllers) {
        
        currControllerRoute = new StringBuffer();
        
        if (controllerJson.type.compareToIgnoreCase("GET") == 0) {
          
          currControllerRoute.append("GET   /");
        } else if (controllerJson.type.compareToIgnoreCase("POST") == 0) {
          
          currControllerRoute.append("POST  /");
        }
        
        currControllerRoute.append(
          CodeIdentifiersFormatterInl.lowerUnder(controllerJson.name) );
        
        for (int i = controllerJson.name.length(); i < 30; i ++) {
          
          currControllerRoute.append(" ");
        }
        
        currControllerRoute.append("  ");
        
        currControllerRoute.append(
          JavaFormatterInl.getPackageName(
            controllersJson.java_package,
            projectName,
            "controllers",
            CodeIdentifiersFormatterInl.lowerUnder(controllerJson.name) ) );
        
        currControllerRoute.append(
          ".Controller"
          + CodeIdentifiersFormatterInl.camelCase(
              true,
              controllerJson.name)
          + ".");
        
        if (controllerJson.type.compareToIgnoreCase("GET") == 0) {
          
          currControllerRoute.append("get");
        } else if (controllerJson.type.compareToIgnoreCase("POST") == 0) {
          
          currControllerRoute.append("post");
        }
        
        currControllerRoute.append(
          CodeIdentifiersFormatterInl.camelCase(
            true,
            controllerJson.name) );
        currControllerRoute.append("()\n");
        
        controllersRoutes.add(currControllerRoute.toString() );
      }
      
      ArrayList<String> routes =
        FileLoaderInl.loadTextFileLines(
          projectDirPath
          + "/conf/routes");
      
      routes.addAll(controllersRoutes);
      
      FileWriterInl.writeTextFile(
        routes,
        projectDirPath
        + "/conf/routes",
        false,
        false);
    }
  }
  
  private static final String kRequestPropertiesFormat =
    "# Generated using ControllersGeneratorMain.java\n"
    + "# Request properties\n\n"

    + "# Every request-to-response operation passes by the following steps where\n"
    + "#   all steps are optional except for steps preceded by an *\n"
    + "# pre processing\n"
    + "# 1- check device\n"
    + "# 2- throttle request\n"
    + "# 3- validate request parameters\n"
    + "# 4- authenticate request\n"
    + "# processing\n"
    + "# 5- * process request and make response\n"
    + "# post processing (after response is sent)\n"
    + "# default operations (after every response regardless of the request type)\n"
    + "# 6- send push notifications (for mobile applications)\n"
    + "# 7- analysis\n"
    + "# 8- logging\n"
    + "# The following properties are set to true/false to enable/disable optional\n"
    + "#   steps - all values are false (disabled) by default\n\n"

    + "# Check Source is used to check the request source for device-specific\n"
    + "#   operations (e.g.: if a mobile app backend wants to reject requests coming\n"
    + "#   from computers to potentially try to cause some harm)\n"
    + "# Also for specific-client applications (like mobile apps) client-verification\n"
    + "#   happens here (using request hashing using a secret phrase or else)\n"
    + "# Default: false\n"
    + "check_source=%s\n\n"

    + "# Throttle is a common need for mobile applications and websites to ban\n"
    + "#   behavior like spam, DOS, etc ...\n"
    + "# Default: false\n"
    + "throttle=%s\n\n"

    + "# Validate param is used to validate a request (e.g.: validate e-mail format,\n"
    + "#   photo size, etc ...)\n"
    + "# Default: false\n"
    + "validate_param=%s\n\n"

    + "# Authenticate is used to authenticate a request (e.g.: OAuth 2,\n"
    + "#   Facebook Login, etc ...)\n"
    + "# Default: false\n"
    + "authenticate=%s\n\n"

    + "# NOTE: all of the following operations happen after a response is sent back\n"
    + "#         for a request\n\n"

    + "# After response is used to switch on/off all after response operations\n"
    + "#   (each of those operation has a switch property whose value only matters\n"
    + "#   if this property's value is true)\n"
    + "# Default: false\n"
    + "after_response=%s\n\n"

    + "# After processing is used to enable doing further processing for a request\n"
    + "#   after a response is sent. (e.g.: let's say someone shared a public post\n"
    + "#   on Facebook, before a response is sent this post must be made to appear\n"
    + "#   for that user's friends which is critical for a successful post; then\n"
    + "#   in after processing the post gets assigned to more non-friend users which\n"
    + "#   if failed doesn't affect the success of the request from the\n"
    + "#   user-experience point of view)\n"
    + "# Default: false\n"
    + "after_processing=%s\n\n"

    + "# Default operations are used to execute operations that happen after each\n"
    + "#   request regardless of the type of that request (e.g.: in a mobile app\n"
    + "#   default operations can be used to update the user's notifications'\n"
    + "#   badge number; for example to reset it on the backend side since a request\n"
    + "#   means that the user opened the app)\n"
    + "# Default: false\n"
    + "default_operations=%s\n\n"

    + "# Notifications is used to enable sending push notifications\n"
    + "# Default: false\n"
    + "notifications=%s\n\n"

    + "# Analysis is used to enable doing analysis after requests\n"
    + "# Default: false\n"
    + "analysis=%s\n\n"

    + "# Logging is used to enable logging requests, responses, exceptions, etc ...\n"
    + "# Default: false\n"
    + "logging=%s\n";
  /**
   * generateRequestProperties
   * generates and writes the backend's request properties file
   * @param controllersJson
   * @throws Exception
   */
  private static void generateRequestProperties (
    final ControllersJson controllersJson,
    final String projectDirPath) throws Exception {
    
    String requestProperties =
      String.format(
        kRequestPropertiesFormat,
        "" + controllersJson.check_source,
        "" + controllersJson.throttle,
        "" + controllersJson.validate_param,
        "" + controllersJson.authenticate,
        "" + controllersJson.after_response,
        "" + controllersJson.after_processing,
        "" + controllersJson.default_operations,
        "" + controllersJson.notifications,
        "" + controllersJson.analysis,
        "" + controllersJson.logging);
    
    FileWriterInl.writeTextFile(
      requestProperties,
      projectDirPath
      + "/conf/prop/request_properties.prop",
      false);
  }
  
  private static final String kCommonHandlerStartCommentFormat =
    "/**\n"
    + " * \"First, solve the problem. Then, write the code. -John Johnson\"\n"
    + " * \"Or use Vangav M\"\n"
    + " * www.vangav.com\n"
    + " * */\n\n"
    
    + "/**\n"
    + " * no license, I know you already got more than enough to worry about\n"
    + " * keep going, never give up\n"
    + " * */\n\n"
    
    + "/**\n"
    + " * Community\n"
    + " * Facebook Group: Vangav Open Source - Backend\n"
    + " *   fb.com/groups/575834775932682/\n"
    + " * Facebook Page: Vangav\n"
    + " *   fb.com/vangav.f\n"
    + " * \n"
    + " * Third party communities for Vangav Backend\n"
    + " *   - play framework\n"
    + " *   - cassandra\n"
    + " *   - datastax\n"
    + " *   \n"
    + " * Tag your question online (e.g.: stack overflow, etc ...) with\n"
    + " *   #vangav_backend\n"
    + " *   to easier find questions/answers online\n"
    + " * */\n\n";
  private static final String kCommonHandlerPackageFormat =
    "package %s;\n\n";
  private static final String kCommonHandlerImportsFormat =
    "import com.vangav.backend.play_framework.ParentPlayHandler;\n"
    + "import com.vangav.backend.play_framework.request.Request;\n\n";
  private static final String kCommonHandlerAuthorFormat =
    "/**\n"
    + " * GENERATED using ControllersGeneratorMain.java\n"
    + " */\n";
  private static final String kCommonHandlerClassCommentFormat =
    "/**\n"
    + " * CommonPlayHandler represents the parent handler for all controllers\n"
    + " * */\n";
  private static final String kCommonHandlerClassStartFormat =
    "public abstract class CommonPlayHandler extends ParentPlayHandler {\n\n";
  private static final String kCheckSourceMethodNameFormat =
    "checkSource";
  private static final String kThrottleMethodNameFormat =
    "throttle";
  private static final String kAuthenticateRequestMethodNameFormat =
    "authenticateRequest";
  private static final String kAfterProcessingMethodNameFormat =
    "afterProcessing";
  private static final String kDispatchDefaultOperationsMethodNameFormat =
    "dispatchDefaultOperations";
  private static final String kDispatchPushNotificationsMethodNameFormat =
    "dispatchPushNotifications";
  private static final String kDispatchAnalysisMethodNameFormat =
    "dispatchAnalysis";
  private static final String kDispatchDefaultAnalysisMethodNameFormat =
    "dispatchDefaultAnalysis";
  private static final String kDispatchLoggingMethodNameFormat =
    "dispatchLogging";
  private static final String kDispatchDefaultLoggingMethodNameFormat =
    "dispatchDefaultLogging";
  private static final String kToDoImplementFormat =
    "// TODO: to be implemented";
  private static final String kOverrideToImplementFormat =
    "// Override in a Controller's Handler to implement";
  private static final String kImplementAndOrOverrideFormat =
    "// implement here and/or override per Controller's Handler";
  private static final String kDisabledFormat =
    "// Disabled functionality, set to true in request_properties.prop to enable";
  private static final String kMethodFormat =
    "  @Override\n"
    + "  protected void %s (\n"
    + "    final Request request) throws Exception {\n\n"

    + "     %s\n"
    + "  }\n\n";
  private static final String kFinalMethodFormat =
    "  @Override\n"
    + "  final protected void %s (\n"
    + "    final Request request) throws Exception {\n\n"

    + "     %s\n"
    + "  }\n\n";
  private static final String kAbsorbUnhadledExceptionsMethodFormat =
    "  // IMPORTANT: this method must be implemented within a try and catch block\n"
    + "  //             because any exceptions thrown by this method will terminate\n"
    + "  //             the service instance and disables it from handling other\n"
    + "  //             requests\n"
    + "  @Override\n"
    + "  protected void absorbUnhandledExceptions (\n"
    + "    final Exception exception) {\n\n"

    + "    // TODO: implement here or override per-controller\n"
    + "  }\n\n";
  private static final String kCommonHandlerClassEndFormat =
    "}\n";
  /**
   * generateCommonHandler
   * generates and writes the common handler class for all controllers
   * @param packageName
   * @param controllersJson
   * @throws Exception
   */
  private static void generateCommonHandler (
    final ControllersJson controllersJson,
    final String projectDirPath,
    final String projectName) throws Exception {
    
    StringBuffer stringBuffer = new StringBuffer();
    
    stringBuffer.append(kCommonHandlerStartCommentFormat);
    stringBuffer.append(
      String.format(
        kCommonHandlerPackageFormat,
        JavaFormatterInl.getPackageName(
          controllersJson.java_package,
          projectName,
          "controllers") ) );
    stringBuffer.append(kCommonHandlerImportsFormat);
    stringBuffer.append(kCommonHandlerAuthorFormat);
    stringBuffer.append(kCommonHandlerClassCommentFormat);
    stringBuffer.append(kCommonHandlerClassStartFormat);
    
    if (controllersJson.check_source == true) {
      
      stringBuffer.append(
        String.format(
          kMethodFormat,
          kCheckSourceMethodNameFormat,
          kImplementAndOrOverrideFormat) );
    } else {
      
      stringBuffer.append(
        String.format(
          kFinalMethodFormat,
          kCheckSourceMethodNameFormat,
          kDisabledFormat) );
    }
    
    if (controllersJson.throttle == true) {
      
      stringBuffer.append(
        String.format(
          kMethodFormat,
          kThrottleMethodNameFormat,
          kImplementAndOrOverrideFormat) );
    } else {
      
      stringBuffer.append(
        String.format(
          kFinalMethodFormat,
          kThrottleMethodNameFormat,
          kDisabledFormat) );
    }
    
    if (controllersJson.authenticate == true) {
      
      stringBuffer.append(
        String.format(
          kMethodFormat,
          kAuthenticateRequestMethodNameFormat,
          kImplementAndOrOverrideFormat) );
    } else {
      
      stringBuffer.append(
        String.format(
          kFinalMethodFormat,
          kAuthenticateRequestMethodNameFormat,
          kDisabledFormat) );
    }
    
    if (controllersJson.after_processing == true) {
      
      stringBuffer.append(
        String.format(
          kMethodFormat,
          kAfterProcessingMethodNameFormat,
          kOverrideToImplementFormat) );
    } else {
      
      stringBuffer.append(
        String.format(
          kFinalMethodFormat,
          kAfterProcessingMethodNameFormat,
          kDisabledFormat) );
    }
    
    if (controllersJson.default_operations == true) {
      
      stringBuffer.append(
        String.format(
          kMethodFormat,
          kDispatchDefaultOperationsMethodNameFormat,
          kImplementAndOrOverrideFormat) );
    } else {
      
      stringBuffer.append(
        String.format(
          kFinalMethodFormat,
          kDispatchDefaultOperationsMethodNameFormat,
          kDisabledFormat) );
    }
    
    if (controllersJson.notifications == true) {
      
      stringBuffer.append(
        String.format(
          kMethodFormat,
          kDispatchPushNotificationsMethodNameFormat,
          kOverrideToImplementFormat) );
    } else {
      
      stringBuffer.append(
        String.format(
          kFinalMethodFormat,
          kDispatchPushNotificationsMethodNameFormat,
          kDisabledFormat) );
    }
    
    if (controllersJson.analysis == true) {
      
      stringBuffer.append(
        String.format(
          kMethodFormat,
          kDispatchAnalysisMethodNameFormat,
          kOverrideToImplementFormat) );
      stringBuffer.append(
        String.format(
          kFinalMethodFormat,
          kDispatchDefaultAnalysisMethodNameFormat,
          kToDoImplementFormat) );
    } else {
      
      stringBuffer.append(
        String.format(
          kFinalMethodFormat,
          kDispatchAnalysisMethodNameFormat,
          kDisabledFormat) );
      stringBuffer.append(
        String.format(
          kFinalMethodFormat,
          kDispatchDefaultAnalysisMethodNameFormat,
          kDisabledFormat) );
    }
    
    if (controllersJson.analysis == true) {
      
      stringBuffer.append(
        String.format(
          kMethodFormat,
          kDispatchLoggingMethodNameFormat,
          kOverrideToImplementFormat) );
      stringBuffer.append(
        String.format(
          kFinalMethodFormat,
          kDispatchDefaultLoggingMethodNameFormat,
          kToDoImplementFormat) );
    } else {
      
      stringBuffer.append(
        String.format(
          kFinalMethodFormat,
          kDispatchLoggingMethodNameFormat,
          kDisabledFormat) );
      stringBuffer.append(
        String.format(
          kFinalMethodFormat,
          kDispatchDefaultLoggingMethodNameFormat,
          kDisabledFormat) );
    }
    
    stringBuffer.append(kAbsorbUnhadledExceptionsMethodFormat);
    
    stringBuffer.append(kCommonHandlerClassEndFormat);
    
    String commonHandler = stringBuffer.toString();
    
    String destDirPath =
      JavaFormatterInl.getPathToPackage(
        projectDirPath,
        JavaFormatterInl.kPlaySrcDirName,
        controllersJson.java_package,
        projectName,
        "controllers");
    
    FileWriterInl.writeTextFile(
      commonHandler,
      destDirPath
      + "/CommonPlayHandler.java",
      false);
  }
  
  private static final String kControllerStartCommentFormat =
    "/**\n"
    + " * \"First, solve the problem. Then, write the code. -John Johnson\"\n"
    + " * \"Or use Vangav M\"\n"
    + " * www.vangav.com\n"
    + " * */\n\n"
    
    + "/**\n"
    + " * no license, I know you already got more than enough to worry about\n"
    + " * keep going, never give up\n"
    + " * */\n\n"
    
    + "/**\n"
    + " * Community\n"
    + " * Facebook Group: Vangav Open Source - Backend\n"
    + " *   fb.com/groups/575834775932682/\n"
    + " * Facebook Page: Vangav\n"
    + " *   fb.com/vangav.f\n"
    + " * \n"
    + " * Third party communities for Vangav Backend\n"
    + " *   - play framework\n"
    + " *   - cassandra\n"
    + " *   - datastax\n"
    + " *   \n"
    + " * Tag your question online (e.g.: stack overflow, etc ...) with\n"
    + " *   #vangav_backend\n"
    + " *   to easier find questions/answers online\n"
    + " * */\n\n";
  private static final String kControllerPackageFormat =
    "package %s;\n\n";
  private static final String kControllerImportsFormat =
    "import play.libs.F.Promise;\n"
    + "import play.mvc.Controller;\n"
    + "import play.mvc.Result;\n\n";
  private static final String kControllerAuthorFormat =
    "/**\n"
    + " * GENERATED using ControllersGeneratorMain.java\n"
    + " */\n";
  private static final String kControllerClassCommentFormat =
    "/**\n"
    + " * Controller%s\n"
    + " *   is the entry point for %s\n"
    + " * */\n";
  private static final String kControllerClassFormat =
    "public class Controller%s extends Controller {\n\n"

    + "  public static Promise<Result> %s%s () {\n\n"

    + "    final Handler%s handler%s =\n"
    + "      new Handler%s();\n\n"

    + "    return handler%s.handleRequestAsync(request() );\n"
    + "  }\n"
    + "}\n";
  /**
   * generateControllerClass
   * generates and writes the controller's controller class
   * @param packageName
   * @param controllerJson
   * @throws Exception
   */
  private static void generateControllerClass (
    final ControllersJson controllersJson,
    final String projectDirPath,
    final ControllerJson controllerJson,
    final String projectName) throws Exception {
    
    String nameCamelCase =
      CodeIdentifiersFormatterInl.camelCase(true, controllerJson.name);
    String nameLowerUnder =
      CodeIdentifiersFormatterInl.lowerUnder(controllerJson.name);
    String typeLowerCase =
      controllerJson.type.compareToIgnoreCase("GET") == 0?
        "get" : "post";
    
    StringBuffer stringBuffer = new StringBuffer();
    
    stringBuffer.append(kControllerStartCommentFormat);
    stringBuffer.append(
      String.format(
        kControllerPackageFormat,
        JavaFormatterInl.getPackageName(
          controllersJson.java_package,
          projectName,
          "controllers",
          nameLowerUnder) ) );
    stringBuffer.append(kControllerImportsFormat);
    stringBuffer.append(kControllerAuthorFormat);
    
    stringBuffer.append(
      String.format(
        kControllerClassCommentFormat,
        nameCamelCase,
        nameCamelCase ) );
    stringBuffer.append(
      String.format(
        kControllerClassFormat,
        nameCamelCase,
        typeLowerCase,
        nameCamelCase,
        nameCamelCase,
        nameCamelCase,
        nameCamelCase,
        nameCamelCase ) );
    
    String controllerClass = stringBuffer.toString();
    
    String destDirPath =
      JavaFormatterInl.getPathToPackage(
        projectDirPath,
        JavaFormatterInl.kPlaySrcDirName,
        controllersJson.java_package,
        projectName,
        "controllers",
        nameLowerUnder);
    
    FileWriterInl.writeTextFile(
      controllerClass,
      destDirPath
      + "/Controller"
      + nameCamelCase
      + ".java",
      true);
  }
  
  private static final String kHandlerStartCommentFormat =
    "/**\n"
    + " * \"First, solve the problem. Then, write the code. -John Johnson\"\n"
    + " * \"Or use Vangav M\"\n"
    + " * www.vangav.com\n"
    + " * */\n\n"
    
    + "/**\n"
    + " * no license, I know you already got more than enough to worry about\n"
    + " * keep going, never give up\n"
    + " * */\n\n"
    
    + "/**\n"
    + " * Community\n"
    + " * Facebook Group: Vangav Open Source - Backend\n"
    + " *   fb.com/groups/575834775932682/\n"
    + " * Facebook Page: Vangav\n"
    + " *   fb.com/vangav.f\n"
    + " * \n"
    + " * Third party communities for Vangav Backend\n"
    + " *   - play framework\n"
    + " *   - cassandra\n"
    + " *   - datastax\n"
    + " *   \n"
    + " * Tag your question online (e.g.: stack overflow, etc ...) with\n"
    + " *   #vangav_backend\n"
    + " *   to easier find questions/answers online\n"
    + " * */\n\n";
  private static final String kHandlerPackageFormat =
    "package %s;\n\n";
  private static final String kHandlerImportsFormat =
    "import com.vangav.backend.play_framework.request.Request;\n"
    + "import com.vangav.backend.play_framework.request.RequestJsonBody;\n"
    + "import com.vangav.backend.play_framework.request.response.ResponseBody;\n"
    + "import %s.CommonPlayHandler;\n\n";
  private static final String kHandlerAuthorFormat =
    "/**\n"
    + " * GENERATED using ControllersGeneratorMain.java\n"
    + " */\n";
  private static final String kHandlerClassCommentFormat =
    "/**\n"
    + " * Handler%s\n"
    + " *   handles request-to-response processing\n"
    + " *   also handles after response processing (if any)\n"
    + " * */\n";
  private static final String kHandlerClassFormat =
    "public class Handler%s extends CommonPlayHandler {\n\n"

    + "  private static final String kName = \"%s\";\n\n"

    + "  @Override\n"
    + "  protected String getName () {\n\n"

    + "    return kName;\n"
    + "  }\n\n"

    + "  @Override\n"
    + "  protected RequestJsonBody getRequestJson () {\n\n"

    + "    return new Request%s();\n"
    + "  }\n\n"

    + "  @Override\n"
    + "  protected ResponseBody getResponseBody () {\n\n"

    + "    return new Response%s();\n"
    + "  }\n\n"

    + "  @Override\n"
    + "  protected void processRequest (final Request request) throws Exception {\n\n"

    + "    // use the following request Object to process the request and set\n"
    + "    //   the response to be returned\n"
    + "    Request%s request%s =\n"
    + "      (Request%s)request.getRequestJsonBody();\n\n"

    + "    // TODO: request-to-response logic goes here\n"
    + "    // EXAMPLE: setting the response is the last step to be done\n"
    + "    //   in this method similar to the following example\n"
    + "    // ((Response%s)request.getResponseBody() ).set(\n"
    + "    //   response values go here);\n"
    + "  }\n"
    + "}\n";
  /**
   * generateHandlerClass
   * generates and writes the controller's handler class
   * @param packageName
   * @param controllerJson
   * @throws Exception
   */
  private static void generateHandlerClass (
    final ControllersJson controllersJson,
    final String projectDirPath,
    final ControllerJson controllerJson,
    final String projectName) throws Exception {
    
    String nameCamelCase =
      CodeIdentifiersFormatterInl.camelCase(true, controllerJson.name);
    String nameLowerUnder =
      CodeIdentifiersFormatterInl.lowerUnder(controllerJson.name);
    
    StringBuffer stringBuffer = new StringBuffer();
    
    stringBuffer.append(kHandlerStartCommentFormat);
    stringBuffer.append(
      String.format(
        kHandlerPackageFormat,
        JavaFormatterInl.getPackageName(
          controllersJson.java_package,
          projectName,
          "controllers",
          nameLowerUnder) ) );

    stringBuffer.append(
      String.format(
        kHandlerImportsFormat,
        JavaFormatterInl.getPackageName(
          controllersJson.java_package,
          projectName,
          "controllers") ) );
    
    stringBuffer.append(kHandlerAuthorFormat);

    stringBuffer.append(
      String.format(
        kHandlerClassCommentFormat,
        nameCamelCase) );
    stringBuffer.append(
      String.format(
        kHandlerClassFormat,
        nameCamelCase,
        nameCamelCase,
        nameCamelCase,
        nameCamelCase,
        nameCamelCase,
        nameCamelCase,
        nameCamelCase,
        nameCamelCase) );
    
    String handlerClass = stringBuffer.toString();
    
    String destDirPath =
      JavaFormatterInl.getPathToPackage(
        projectDirPath,
        JavaFormatterInl.kPlaySrcDirName,
        controllersJson.java_package,
        projectName,
        "controllers",
        nameLowerUnder);
    
    FileWriterInl.writeTextFile(
      handlerClass,
      destDirPath
      + "/Handler"
      + nameCamelCase
      + ".java",
      false);
  }
  
  private static final String kRequestStartCommentFormat =
    "/**\n"
    + " * \"First, solve the problem. Then, write the code. -John Johnson\"\n"
    + " * \"Or use Vangav M\"\n"
    + " * www.vangav.com\n"
    + " * */\n\n"
    
    + "/**\n"
    + " * no license, I know you already got more than enough to worry about\n"
    + " * keep going, never give up\n"
    + " * */\n\n"
    
    + "/**\n"
    + " * Community\n"
    + " * Facebook Group: Vangav Open Source - Backend\n"
    + " *   fb.com/groups/575834775932682/\n"
    + " * Facebook Page: Vangav\n"
    + " *   fb.com/vangav.f\n"
    + " * \n"
    + " * Third party communities for Vangav Backend\n"
    + " *   - play framework\n"
    + " *   - cassandra\n"
    + " *   - datastax\n"
    + " *   \n"
    + " * Tag your question online (e.g.: stack overflow, etc ...) with\n"
    + " *   #vangav_backend\n"
    + " *   to easier find questions/answers online\n"
    + " * */\n\n";
  private static final String kRequestPackageFormat =
    "package %s;\n\n";
  private static final String kRequestGetFirstImportFormat =
    "import java.util.Map;\n\n";
  private static final String kRequestImportsFormat =
    "import com.fasterxml.jackson.annotation.JsonIgnore;\n"
    + "import com.fasterxml.jackson.annotation.JsonIgnoreProperties;\n"
    + "import com.fasterxml.jackson.annotation.JsonProperty;\n";
  private static final String kRequestParamImportsFormat =
    "import com.vangav.backend.play_framework.param.ParamOptionality;\n"
    + "import com.vangav.backend.play_framework.param.ParamType;\n";
  private static final String kRequestLastImportFormat =
    "import com.vangav.backend.play_framework.request.RequestJsonBody%s;\n\n";
  private static final String kRequestAuthorFormat =
    "/**\n"
    + " * GENERATED using ControllersGeneratorMain.java\n"
    + " */\n";
  private static final String kRequestClassCommentFormat =
    "/**\n"
    + " * Request%s represents the request's structure\n"
    + " * */\n";
  private static final String kRequestClassStartFormat =
    "@JsonIgnoreProperties(ignoreUnknown = true)\n"
    + "public class Request%s extends RequestJsonBody%s {\n\n";
  private static final String kRequestGetNameMethodFormat =
    "  @Override\n"
    + "  @JsonIgnore\n"
    + "  protected String getName () throws Exception {\n\n"

    + "    return \"%s\";\n"
    + "  }\n\n";
  private static final String kRequestGetThisMethodFormat =
    "  @Override\n"
    + "  @JsonIgnore\n"
    + "  protected Request%s getThis () throws Exception {\n\n"

    + "    return this;\n"
    + "  }\n\n";
  private static final String kRequestParamFormat =
    "  @JsonIgnore\n"
    + "  public static final String k%sName = \"%s\";\n"
    + "  @JsonProperty\n"
    + "  public %s%s %s;\n\n";
  private static final String kRequestGetGetterFormat =
    "    this.%s = this.%s(k%sName, query);\n";
  private static final String kRequestFromQueryStringMethodFormat =
    "  @Override\n"
    + "  @JsonIgnore\n"
    + "  public Request%s fromQueryString (\n"
    + "    Map<String, String[]> query) throws Exception {\n\n"
    
    + "%s\n"
    
    + "    return this;\n"
    + "  }\n\n";
  private static final String kRequestValidateParamFormat =
    "    this.validate(\n"
    + "      k%sName,\n"
    + "      this.%s,\n"
    + "      ParamType.%s,\n"
    + "      ParamOptionality.%s);\n\n";
  private static final String kRequestValidateMethodFormat =
    "  @Override\n"
    + "  @JsonIgnore\n"
    + "  public void validate () throws Exception {\n\n"

    + "%s"
    + "  }\n";
  private static final String kRequestClassEndFormat =
    "}\n";
  /**
   * generateRequestClass
   * generates and writes the controller's response class
   * @param packageName
   * @param controllerJson
   * @throws Exception
   */
  private static void generateRequestClass (
    final ControllersJson controllersJson,
    final String projectDirPath,
    final ControllerJson controllerJson,
    final String projectName) throws Exception {
    
    String nameCamelCase =
      CodeIdentifiersFormatterInl.camelCase(true, controllerJson.name);
    String nameLowerUnder =
      CodeIdentifiersFormatterInl.lowerUnder(controllerJson.name);
    
    StringBuffer stringBuffer = new StringBuffer();
    
    stringBuffer.append(kRequestStartCommentFormat);
    stringBuffer.append(
      String.format(
        kRequestPackageFormat,
        JavaFormatterInl.getPackageName(
          controllersJson.java_package,
          projectName,
          "controllers",
          nameLowerUnder) ) );
    
    if (controllerJson.type.compareToIgnoreCase("GET") == 0 &&
        controllerJson.request_params != null &&
        controllerJson.request_params.length > 0) {
      
      stringBuffer.append(kRequestGetFirstImportFormat);
    }
    
    stringBuffer.append(kRequestImportsFormat);
    
    if (controllerJson.request_params != null &&
        controllerJson.request_params.length > 0) {
      
      stringBuffer.append(kRequestParamImportsFormat);
    }
    
    if (controllerJson.type.compareToIgnoreCase("GET") == 0) {
      
      stringBuffer.append(
        String.format(
          kRequestLastImportFormat,
          "Get") );
    } else if (controllerJson.type.compareToIgnoreCase("POST") == 0) {
      
      stringBuffer.append(
        String.format(
          kRequestLastImportFormat,
          "Post") );
    }
    
    stringBuffer.append(kRequestAuthorFormat);
    
    stringBuffer.append(
      String.format(
        kRequestClassCommentFormat,
        nameCamelCase) );
    stringBuffer.append(
      String.format(
        kRequestClassStartFormat,
        nameCamelCase,
        controllerJson.type.compareToIgnoreCase("GET") == 0?
          "Get" : "Post") );
    
    stringBuffer.append(
      String.format(
        kRequestGetNameMethodFormat,
        nameCamelCase) );
    
    stringBuffer.append(
      String.format(
        kRequestGetThisMethodFormat,
        nameCamelCase) );
    
    if (controllerJson.request_params != null &&
        controllerJson.request_params.length > 0) {
      
      for (RequestParamJson requestParam : controllerJson.request_params) {
        
        stringBuffer.append(
          String.format(
            kRequestParamFormat,
            CodeIdentifiersFormatterInl.camelCase(true, requestParam.name),
            requestParam.name,
            ParamType.valueOf(requestParam.type).getPrimitiveType(),
            requestParam.is_array == true? "[]" : "",
            requestParam.name) );
      }
    }
    
    if (controllerJson.type.compareToIgnoreCase("GET") == 0) {
      
      StringBuffer tempStringBuffer = new StringBuffer();
      
      if (controllerJson.request_params != null &&
          controllerJson.request_params.length > 0) {
      
        for (RequestParamJson requestParam : controllerJson.request_params) {
          
          tempStringBuffer.append(
            String.format(
              kRequestGetGetterFormat,
              requestParam.name,
              RequestJsonBodyGet.primitiveTypeToMethodName(
                ParamType.valueOf(requestParam.type).getPrimitiveType(),
                requestParam.is_array),
              CodeIdentifiersFormatterInl.camelCase(
                true,
                requestParam.name) ) );
        }
      }
      
      stringBuffer.append(
        String.format(
          kRequestFromQueryStringMethodFormat,
          nameCamelCase,
          tempStringBuffer.toString() ) );
    }
    
    if (controllerJson.request_params != null &&
        controllerJson.request_params.length > 0) {
      
      StringBuffer tempStringBuffer = new StringBuffer();
      
      for (RequestParamJson requestParam : controllerJson.request_params) {
        
        tempStringBuffer.append(
          String.format(
            kRequestValidateParamFormat,
            CodeIdentifiersFormatterInl.camelCase(
              true,
              requestParam.name),
            requestParam.name,
            requestParam.type,
            requestParam.optionality) );
      }
      
      stringBuffer.append(
        String.format(
          kRequestValidateMethodFormat,
          tempStringBuffer.toString() ) );
      
      stringBuffer.append(kRequestClassEndFormat);
    }
    
    
    String requestClass = stringBuffer.toString();
    
    String destDirPath =
      JavaFormatterInl.getPathToPackage(
        projectDirPath,
        JavaFormatterInl.kPlaySrcDirName,
        controllersJson.java_package,
        projectName,
        "controllers",
        nameLowerUnder);
    
    FileWriterInl.writeTextFile(
      requestClass,
      destDirPath
      + "/Request"
      + nameCamelCase
      + ".java",
      false);
  }
  
  private static final String kResponseStartCommentFormat =
    "/**\n"
    + " * \"First, solve the problem. Then, write the code. -John Johnson\"\n"
    + " * \"Or use Vangav M\"\n"
    + " * www.vangav.com\n"
    + " * */\n\n"
    
    + "/**\n"
    + " * no license, I know you already got more than enough to worry about\n"
    + " * keep going, never give up\n"
    + " * */\n\n"
    
    + "/**\n"
    + " * Community\n"
    + " * Facebook Group: Vangav Open Source - Backend\n"
    + " *   fb.com/groups/575834775932682/\n"
    + " * Facebook Page: Vangav\n"
    + " *   fb.com/vangav.f\n"
    + " * \n"
    + " * Third party communities for Vangav Backend\n"
    + " *   - play framework\n"
    + " *   - cassandra\n"
    + " *   - datastax\n"
    + " *   \n"
    + " * Tag your question online (e.g.: stack overflow, etc ...) with\n"
    + " *   #vangav_backend\n"
    + " *   to easier find questions/answers online\n"
    + " * */\n\n";
  private static final String kResponsePackageFormat =
    "package %s;\n\n";
  private static final String kResponseImportsFormat =
    "import com.fasterxml.jackson.annotation.JsonIgnore;\n"
    + "import com.fasterxml.jackson.annotation.JsonIgnoreProperties;\n"
    + "import com.fasterxml.jackson.annotation.JsonProperty;\n"
    + "import com.vangav.backend.play_framework.request.response.ResponseBody%s;\n\n";
  private static final String kResponseAuthorFormat =
    "/**\n"
    + " * GENERATED using ControllersGeneratorMain.java\n"
    + " */\n";
  private static final String kResponseClassCommentFormat =
    "/**\n"
    + " * Response%s represents the response's structure\n"
    + " * */\n";
  private static final String kResponseClassStartFormat =
    "@JsonIgnoreProperties(ignoreUnknown = true)\n"
    + "public class Response%s extends ResponseBody%s {\n\n";
  
  private static final String kResponseGetNameMethodFormat =
    "  @Override\n"
    + "  @JsonIgnore\n"
    + "  protected String getName () throws Exception {\n\n"

    + "    return \"%s\";\n"
    + "  }\n\n";
  private static final String kResponseGetThisMethodFormat =
    "  @Override\n"
    + "  @JsonIgnore\n"
    + "  protected Response%s getThis () throws Exception {\n\n"

    + "    return this;\n"
    + "  }\n\n";
  private static final String kResponseFileCallCommentFormat =
    "  // call setFilePath (String filePath) in the Handler class\n"
    + "  //    so File @ filePath is returned in the response\n\n";
  private static final String kResponseHtmlCallCommentFormat =
    "  // call setHtmlContent (Content htmlContent) in the Handler class\n"
    + "  //    so htmlContent is returned in the response\n"
    + "  // create Content example: Content html = views.html.HomePage.render();\n\n";
  private static final String kResponseParamFormat =
    "  @JsonProperty\n"
    + "  public %s%s %s;\n";
  private static final String kResponseParamSetterMethodArgFormat =
    "    %s%s %s";
  private static final String kResponseParamSetterMethodImplFormat =
    "    this.%s = %s;\n";
  private static final String kResponseParamSetterMethodFormat =
    "\n  @JsonIgnore\n"
    + "  public void set (\n"
    + "%s) {\n\n"

    + "%s"
    + "  }\n";
  private static final String kResponseClassEndFormat =
    "}\n";
  /**
   * generateResponseClass
   * generates and writes the controller's response class
   * @param packageName
   * @param controllerJson
   * @throws Exception
   */
  private static void generateResponseClass (
    final ControllersJson controllersJson,
    final String projectDirPath,
    final ControllerJson controllerJson,
    final String projectName) throws Exception {
    
    String nameCamelCase =
      CodeIdentifiersFormatterInl.camelCase(true, controllerJson.name);
    String nameLowerUnder =
      CodeIdentifiersFormatterInl.lowerUnder(controllerJson.name);
    
    StringBuffer stringBuffer = new StringBuffer();
    
    stringBuffer.append(kResponseStartCommentFormat);
    stringBuffer.append(
      String.format(
        kResponsePackageFormat,
        JavaFormatterInl.getPackageName(
          controllersJson.java_package,
          projectName,
          "controllers",
          nameLowerUnder) ) );
    
    if (controllerJson.response_type.compareToIgnoreCase("JSON") == 0) {
      
      stringBuffer.append(
        String.format(
          kResponseImportsFormat,
          "Json") );
    } else if (controllerJson.response_type.compareToIgnoreCase("FILE") == 0) {
      
      stringBuffer.append(
        String.format(
          kResponseImportsFormat,
          "File") );
    } else if (controllerJson.response_type.compareToIgnoreCase("HTML") == 0) {
      
      stringBuffer.append(
        String.format(
          kResponseImportsFormat,
          "Html") );
    }
    
    stringBuffer.append(kResponseAuthorFormat);
    
    stringBuffer.append(
      String.format(
        kResponseClassCommentFormat,
        nameCamelCase) );
    
    if (controllerJson.response_type.compareToIgnoreCase("JSON") == 0) {
      
      stringBuffer.append(
        String.format(
          kResponseClassStartFormat,
          nameCamelCase,
          "Json") );
    } else if (controllerJson.response_type.compareToIgnoreCase("FILE") == 0) {
      
      stringBuffer.append(
        String.format(
          kResponseClassStartFormat,
          nameCamelCase,
          "File") );
    } else if (controllerJson.response_type.compareToIgnoreCase("HTML") == 0) {
      
      stringBuffer.append(
        String.format(
          kResponseClassStartFormat,
          nameCamelCase,
          "Html") );
    }
    
    stringBuffer.append(
      String.format(
        kResponseGetNameMethodFormat,
        nameCamelCase) );
    stringBuffer.append(
      String.format(
        kResponseGetThisMethodFormat,
        nameCamelCase) );
    
    if (controllerJson.response_type.compareToIgnoreCase("JSON") == 0 &&
        controllerJson.response_params != null &&
        controllerJson.response_params.length > 0) {
      
      for (ResponseParamJson responseParam : controllerJson.response_params) {
        
        stringBuffer.append(
          String.format(
            kResponseParamFormat,
            responseParam.type,
            responseParam.is_array == true? "[]" : "",
            responseParam.name) );
      }
      
      StringBuffer argsStringBuffer = new StringBuffer();
      StringBuffer setterStringBuffer = new StringBuffer();
      
      for (int i = 0; i < controllerJson.response_params.length; i ++) {
        
        argsStringBuffer.append(
          String.format(
            kResponseParamSetterMethodArgFormat,
            controllerJson.response_params[i].type,
            controllerJson.response_params[i].is_array == true? "[]" : "",
            controllerJson.response_params[i].name) );
        
        if (i < (controllerJson.response_params.length - 1) ) {
          
          argsStringBuffer.append(",\n");
        }
        
        setterStringBuffer.append(
          String.format(
            kResponseParamSetterMethodImplFormat,
            controllerJson.response_params[i].name,
            controllerJson.response_params[i].name) );
      }
      
      stringBuffer.append(
        String.format(
          kResponseParamSetterMethodFormat,
          argsStringBuffer.toString(),
          setterStringBuffer.toString() ) );
    } else if (controllerJson.response_type.compareToIgnoreCase("FILE") == 0) {
      
      stringBuffer.append(kResponseFileCallCommentFormat);
    } else if (controllerJson.response_type.compareToIgnoreCase("HTML") == 0) {
      
      stringBuffer.append(kResponseHtmlCallCommentFormat);
    }
    
    stringBuffer.append(kResponseClassEndFormat);
    
    String responseClass = stringBuffer.toString();
    
    String destDirPath =
      JavaFormatterInl.getPathToPackage(
        projectDirPath,
        JavaFormatterInl.kPlaySrcDirName,
        controllersJson.java_package,
        projectName,
        "controllers",
        nameLowerUnder);
    
    FileWriterInl.writeTextFile(
      responseClass,
      destDirPath
      + "/Response"
      + nameCamelCase
      + ".java",
      false);
  }
}
