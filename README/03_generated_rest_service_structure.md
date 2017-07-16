# generated service structure

> ~10 min: this tutorial explains the building blocks a vangav backend generated service; it uses the [geo server](https://github.com/vangav/vos_geo_server) example, yet it's the same structure for all services generated using vangav backend

### [scripts](https://github.com/vangav/vos_geo_server)

the following scripts are generated for every vangav backend service

| script | explanation |
| ------ | ----------- |
| [_run.sh](https://github.com/vangav/vos_geo_server/blob/master/_run.sh) | runs the service in dev mode; by default it runs on port 9000, to run on a different port use `./_run.sh a_different_port_number` e.g.: `./_run.sh 8765` |
| [_clean.sh](https://github.com/vangav/vos_geo_server/blob/master/_clean.sh) | cleans the service's build |
| [_compile.sh](https://github.com/vangav/vos_geo_server/blob/master/_compile.sh) | compiles the service |
| [_debug.sh](https://github.com/vangav/vos_geo_server/blob/master/_debug.sh) | runs the service in debug mode; debugging the code can happen in eclipse as explained in the [debugging](https://github.com/vangav/vos_backend/blob/master/README/08_debug.md) tutorial |
| [_dist.sh](https://github.com/vangav/vos_geo_server/blob/master/_dist.sh) | generates a production executable for the service to deploy it on the production server(s); deoployment is explained in the [deploy](https://github.com/vangav/vos_backend/blob/master/README/09_deploy.md) tutorial |
| [_eclipsify.sh](https://github.com/vangav/vos_geo_server/blob/master/_eclipsify.sh) | makes the service eclipse-ready; all generated services are eclipse-ready by default |

### [conf](https://github.com/vangav/vos_geo_server/tree/master/conf)

+ the conf directory contains all the service's configuration files
+ this directory gets packaged with the productions version after using the [_dist.sh](https://github.com/vangav/vos_geo_server/blob/master/_dist.sh) script
+ any additional config/data files must go under this directory

#### - [routes](https://github.com/vangav/vos_geo_server/blob/master/conf/routes) and [application.conf](https://github.com/vangav/vos_geo_server/blob/master/conf/application.conf)
+ used by play framework to define the servie's entry points and the service's configuration respectively
+ vangav backend's generated services automatically sets the values for these files

#### - [prop](https://github.com/vangav/vos_geo_server/tree/master/conf/prop)
+ holds all vangav backend's properties files
+ generated services autoamtically get a copy of the necessary properties files depending on the project's config
+ optionally copy additional properties files to the generated service to use the corresponding utility
+ following is the explanation of each of the properties files, inside each properties file is a detailed explanation of exactly how to set/use each property

| properties file | used in geo server | explanantion |
| --------------- | ------------------ | ------------ |
| [android_notification](https://github.com/vangav/vos_backend/blob/master/prop/android_notification_properties.prop) | no | anrdoid push notifications |
| [apple_notification](https://github.com/vangav/vos_backend/blob/master/prop/apple_notification_properties.prop) | no | apple push notifications |
| [cassandra](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/cassandra_properties.prop) | yes | cassandra's api, topology, ... |
| [dispatcher](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/dispatcher_properties.prop) | yes | dispatcher's workers topology |
| [facebook_graph_api_edge](https://github.com/vangav/vos_backend/blob/master/prop/facebook_graph_api_edge_properties.prop) | no | paging, page_limit, ... |
| [java_email](https://github.com/vangav/vos_backend/blob/master/prop/java_email_properties.prop) | no | smtp properties |
| [mail_gun_email](https://github.com/vangav/vos_backend/blob/master/prop/mail_gun_email_properties.prop) | no | mailgun api access values |
| [param_validator](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/param_validator_properties.prop) | yes | defines some of the request's params max_size like photos, captions, chat messages, ... |
| [request](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/request_properties.prop) | yes | switches on/off the sequential steps of a request's processing: check source device, throttle request, validate params, authenticate, after-response processing, default operations, notifications, analysis, logging; this makes it simple to control how a request is processed without touching the code |
| [response_error](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop) | yes | switches on/off what to send back to the client in case of an error response: type, code, sub-code, message, class, stack-trace, trace-id |
| [thread_pool](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/thread_pool_properties.prop) | yes | vangav backend has a thread pool per type of operation with an optimal default size (per machine's specs) for handling big scale; only set values for this properties file if you are sure |
| [twilio](https://github.com/vangav/vos_backend/blob/master/prop/twilio_properties.prop) | no | twilio api access values |
  
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
  4. execute the command **`java -jar cassandra_keyspaces_updater.jar`**
  5. confirm by entering **`Y`**
+ Check out [cassandra_updater_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/cassandra_updater/cassandra_updater_properties.prop) and only edit it if needed (usually never needed).
+ More steps are sometimes needed depending on the changes done to the database configuration:
  + Deleting a table(s)/keyspace(s). Either run the CQL drop script before executing `cassandra_keyspaces_updater.jar` (deletes all the data, good only for dev mode where reseting the data doesn't do any harm), or manually run the drop commands on these table(s)/keyspace(s) from CQL. If drop is used, then execute the `create_if_doesnot_exist` CQL scripts after executing `cassandra_keyspaces_updater.jar`.
  + Adding table(s)/keyspace(s). Just execute the `create_if_doesnot_exist` CQL scripts after executing `cassandra_keyspaces_updater.jar`.
  + Editing table's columns. Either execute the `drop_and_create` CQL scripts (deletes all the data, good only for dev mode where reseting the data doesn't do any harm), or manually run the CQL commands to specifically do these edits.
  + Adding/removing/editing queries/description. No CQL needed, just execute `cassandra_keyspaces_updater.jar`.

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
  + [`beforeStart`](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java#L76) is used by Vangav Backend to load properties, connect to cassandra and prepare cassandra's perpared statements.
  + [`onStop`](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java#L127) is used by Vangav Backend to shutdown the thread pools and disconnect from cassandra
+ [views](https://github.com/vangav/vos_geo_server/tree/master/app/views) is created by play framework to keep the service's html pages
+ [controllers](https://github.com/vangav/vos_geo_server/tree/master/app/controllers) is created by play framework. Not used by Vangav Backend and to be left as is.

### [app/com/vangav/vos_geo_server/cassandra_keyspaces/](https://github.com/vangav/vos_geo_server/tree/master/app/com/vangav/vos_geo_server/cassandra_keyspaces)

+ This directory contains all the generated database clients for cassandra's tables, where each keyspace is represented by a directory and each keyspace's table is represented by a class.

+ Each table's class like [Continents.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java) has the following structure:
  + [Lines (57-91)](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L57): starts with a block comment listing the table's structure and prepared statements
  + [Lines (92-192)](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L92): is used by Vangav Backend to initialize the table and prepare its prepared statements
  + [Then](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L175) for each of the table's prepared statements the following five methods are provided
    + [**`getQuery`**](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L207) returns the raw Query Object to use it however you like.
    + [**`getQueryDispatchable`**](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L221) returns a dispatchable version of the query to be added to the service's worker dispatcher. e.g.: `request.getDispatcher().addDispatchMessage(getQueryDispatchable() );`.
    + [**`getBoundStatement`**](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L238) returns a the query's BoundStatement. Usually used for:
      1. Add it to a BatchStatement
      2. Execute multiple BoundStatements synchronously since it's faster than executing those statements sequentially. Since internally all these statements get executed asynchronously.
    + [**`executeAsync`**](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L253) executes the query asynchronously and returns a ResultSetFuture Object which holds the future result of executing the query.
    + [**`executeSync`**](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L269) is a blocking method that executes the query synchronously then returns a ResultSet Object containing the result of executing the query.
    
### [app/com/vangav/vos_geo_server/controllers/](https://github.com/vangav/vos_geo_server/tree/master/app/com/vangav/vos_geo_server/controllers)

+ This directory contains all the generated controllers (entry points) as defined in the project's config [controllers.json](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json)

+ [CommonPlayHandler.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java) is the parent class for all the controllers' handlers. A controller's handler is the class the implements the controller's request-to-response logic. In Vagav Backend processing a request goes through sequential-optional steps. To enable one or more of these steps, they must be set to true in [request_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/request_properties.prop) as explained above. This class provides the ability to implement/override those sequential-optional steps as follows:
  + [`checkSource`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L62) is used to check the requests source. e.g.: if the service only accepts requests from mobile clients, a set of mac addresses, etc ...
  + [`throttle`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L69) is used to detect and prevent spammy behavior.
  + [`authenticateRequest`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L76) is used to authenticate a request. e.g.: using the build-in OAuth2, Facebook Login, Google Login, etc ...
  + [`afterProcessing`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L83): override this method per-controller-handler to do further processing after a request's response is sent back to the client. e.g.: use it for a blocking operation like push notifications which doesn't impact the service's core functionality in case of failure and shouldn't delay sending back the request's response.
  + `dispatch....`: methods starting with dispatch are processed after a request's response is returned and it's encouraged to dispatch the operations of these method to the service's worker.
  + [`dispatchDefaultOperations`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L90): implement this method to override a service-wide after-response operation for all of the service's controllers. e.g.: update user's last-active-time.
  + [`dispatchPushNotifications`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L97): override this method per-controller to dispatch/process push notifications.
  + [`dispatchAnalysis`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L104): override this method per-controller to dispatch/process analysis.
  + [`dispatchDefaultAnalysis`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L111): implement to dispatch service-wide analysis for all controllers. e.g.: to keep track of request-to-response time.
  + [`dispatchLogging`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L118): override this method per-controller to dispatch/process logging.
  + [`dispatchDefaultLogging`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L125): implement to dispatch service-wide logging for all controllers. e.g.: log request/response/status-code.
  + [`absorbUnhandledExceptions`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L136): if Vangav Backend fails to handle an exception, it forwards the exception to this method to be absorbed and dealt with as needed. e.g.: send a notification email.
  
+ For each controller, Vangav Backend generates a directory with four classes. e.g.: [reverse_geo_code](https://github.com/vangav/vos_geo_server/tree/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code)
  + [ControllerReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ControllerReverseGeoCode.java) represents the entry point for the controller. The [routes](https://github.com/vangav/vos_geo_server/blob/master/conf/routes#L14) config points to [getReverseGeoCoe](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ControllerReverseGeoCode.java#L63) method in this class. No need to edit this class.
  + [RequestReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/RequestReverseGeoCode.java) represents the controller's request. This class's parent class provides the method [`isValidParam`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java#L224) to check if an OPTIONAL param is valid or not.
  + [ResponseReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ResponseReverseGeoCode.java) represents the controller's response. Each response class provides a [`set`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ResponseReverseGeoCode.java#L98) method to set the response's content at the [end](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L117) of the handler's processRequest method.
  + [HandlerReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java) is the class where a controller's request-to-response logic should be implemented. Generated services include TODO comments where the request-to-response logic should be implemented to make it easier to find :) [`processRequest`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L96) method is the main part where the controller's logic should be implemented, ending with calling the response's set method as explained above. Optionally override more methods from [CommonPlayHandler.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java) as explained above.
  
+ In order to add a new controller, just copy an exsisting one and modify it. Then add it to the [routes](https://github.com/vangav/vos_geo_server/blob/master/conf/routes) file.
  
### [vangav_m](https://github.com/vangav/vos_geo_server/tree/master/vangav_m)

+ Follow the following steps to use [Vangav M](http://vangav.com/) within a Vangav Backend Service
  1. Under `vangav_m/solutions` add/remove/edit .mlang files for Vangav M JAVA solutions. You can find many examples on http://vangav.com/.
  2. **cd** to `my_services/vos_geo_server/vangav_m`
  3. Execute **`java -jar vangav_m_json_client.jar`**. This updates the service's Vangav M solutions and link them.
  4. In any of the service's source files (e.g. [HandlerReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java)) start using your Vangav M solutions as explained on [Vangav M](http://vangav.com/) website (set, process, get).
