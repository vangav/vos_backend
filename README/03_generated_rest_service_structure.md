# Generated REST Service Structure

> This section uses the [vos_geo_server](https://github.com/vangav/vos_geo_server) example, yet it's the same structure for all REST services generated using Vangav Backend.

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
