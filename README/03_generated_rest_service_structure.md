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
+ this directory has a `keyspace_name.phriction` file per database's keyspace with a phriction-styled documentation of the keyspace, paste the contents of these files into a phriction's page to have an up-to-date wiki for your database

### [cassandra/cql](https://github.com/vangav/vos_geo_server/tree/master/cassandra/cql)

+ cql (cassandra query language) is the equivalent of sql for cassandra
+ scripts [_start_cassandra.sh](https://github.com/vangav/vos_geo_server/blob/master/cassandra/cql/_start_cassandra.sh) and [_cassandra_status.sh](https://github.com/vangav/vos_geo_server/blob/master/cassandra/cql/_cassandra_status.sh) are used to start Cassandra and check its status respectively
+ in order to init/update/delete the database the scripts in each of the following directories are used (cassandra has to be already running to be able to run these scripts); the `_execute_cql.sh` script in each of those directories takes the .cql file as an argument to execute it e.g.: `./_execute_cql.sh v_analytics_dev.cql`
  + [create_if_doesnot_exist](https://github.com/vangav/vos_geo_server/tree/master/cassandra/cql/create_if_doesnot_exist) has script(s) per-keyspace to create the keyspace and its table(s) if they don't already exsist
  + [drop](https://github.com/vangav/vos_geo_server/tree/master/cassandra/cql/drop) has script(s) per-keyspace to drop a keyspace and its tables; used primarily to clear a dev machine after testing
  + [drop_and_create](https://github.com/vangav/vos_geo_server/tree/master/cassandra/cql/drop_and_create) has script(s) per-keyspace to drop (if already exsists) and create the keyspace and its table(s)
  
### [cassandra_updater](https://github.com/vangav/vos_geo_server/tree/master/cassandra_updater)

> [geo server](https://github.com/vangav/vos_geo_server) example is used for references in this section, but it's exactly the same steps for any vangav backend generated service

cassandra updater is used after a service is generated to update the database; it updates the java clients, cql scripts and phriction-wiki

#### - in dev mode (when reseting the database's data is okay)
1. open a new terminal session
2. `cd` to [`vos_geo_server/cassandra/cql/drop/`](https://github.com/vangav/vos_geo_server/tree/master/cassandra/cql/drop)
3. reset the database's data: execute `./_execute_cql.sh gs_top_dev.cql`; repeat for every `.cql` file
4. `cd` to [`vos_geo_server/generator_config/`](https://github.com/vangav/vos_geo_server/tree/master/generator_config)
5. add/remove/edit .keyspace files as needed
6. `cd` to [`vos_geo_server/cassandra_updater`](https://github.com/vangav/vos_geo_server/tree/master/cassandra_updater)
7. execute the command `java -jar cassandra_keyspaces_updater.jar`
8. confirm by entering `y`
9. `cd` to [`vos_geo_server/cassandra/cql/drop_and_create/`](https://github.com/vangav/vos_geo_server/tree/master/cassandra/cql/drop_and_create)
10. reinitialize the database: execute `./_execute_cql.sh gs_top_dev.cql`; repeat for every `.cql` file

#### - in prod mode (when reseting the database's data is "not" okay)

+ steps 2, 3 and 10 should be reconsidered depending on the changes done to the database design

### [lib](https://github.com/vangav/vos_geo_server/tree/master/lib)

+ conatins all the third-party libs used by vangav backend
+ all the necessary libs are automatically copied to generated services
+ the optional use of some utilities (e.g.: email. twilio, ...) require manually copying a lib or more to your generated service's lib directory
+ refer to the lib section of the [project's contents tutorial](https://github.com/vangav/vos_backend/blob/master/README/01_project_contents.md#lib) for detailed information

### [generator_config](https://github.com/vangav/vos_geo_server/tree/master/generator_config)

+ generator_config includes the files used to generate a vangav backend service
+ as explained above, [cassandra_updater](https://github.com/vangav/vos_backend/blob/master/README/03_generated_rest_service_structure.md#cassandra_updater) relies on the `.keyspace` files under this directory to update the service's database
+ `controllers.json` file is kept for reference and for future functionalities utilizing it
+ `.client_java` files are also kept for reference and for future functionalities utilizing it, using `.client_java` config files in the next tutorial [service generator config structure](https://github.com/vangav/vos_backend/blob/master/README/04_rest_service_config_structure.md)

### [public](https://github.com/vangav/vos_geo_server/tree/master/public)

+ public directory keeps the public files (images, java scripts and style sheets) needed for web pages (if any) provided by the service

### [app](https://github.com/vangav/vos_geo_server/tree/master/app)

+ app is the directory containing all the services source code
+ [Global.java](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java) extends play framework's `GlobalSettings` to override some functionalities that either run one time per service start/stop or on certain events like `beforeStart`, `onStart`, `onStop`, ...
  + [`beforeStart`](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java#L76) is used by vangav backend to load properties, connect to cassandra and prepare cassandra's perpared statements; that's also where other functionalities like loading geo services data and similar operations should take place
  + [`onStop`](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java#L127) is used by vangav backend to shutdown the thread pools and disconnect from cassandra; that's also where free-resource operations should take place
+ [views](https://github.com/vangav/vos_geo_server/tree/master/app/views) is created by play framework to keep the service's html pages
+ [controllers](https://github.com/vangav/vos_geo_server/tree/master/app/controllers) is created by play framework; not used by vangav backend and to be left as is

### [app/com/vangav/vos_geo_server/cassandra_keyspaces/](https://github.com/vangav/vos_geo_server/tree/master/app/com/vangav/vos_geo_server/cassandra_keyspaces)

+ this directory contains all the generated database clients for cassandra's tables as defined in the `.keyspace` config files (e.g.: [`gs_top.keyspace`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace)), where each keyspace is represented by a directory and each keyspace's table is represented by a class

+ each table's class like [Continents.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java) has the following structure:
  + starts with a [block comment: 57-91](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L57) listing the table's structure and prepared statements
  + then an [initialization block: 92-192](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L92) where the table and its prepared statements are initialized
  + followed by five methods per-query as follows
  
  | query | explanantion |
  | ----- | ------------ |
  | [`getQuery`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L207) | returns the raw query object to be used in any way; useful in case the other four methods aren't serving the inteded functionality  |
  | [`getQueryDispatchable`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L221) | returns a dispatchable version of the query to be added to the service's dispatcher and executed by the service's worker; e.g.: `request.getDispatcher().addDispatchMessage( Continents.i().getQueryDispatchablexxx() );` |
  | [`getBoundStatement`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L238) | returns the query's [`BoundStatement`](http://docs.datastax.com/en/drivers/java/2.1/com/datastax/driver/core/BoundStatement.html) which can be then added to a [`BatchStatement`](http://docs.datastax.com/en/drivers/java/2.1/com/datastax/driver/core/BatchStatement.html) or store few of them in an array and execute them using [`executeSync`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/Cassandra.java#L390) method since it will execute them asynchronously internally faster than executing them synchronously one by one |
  | [`executeAsync`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L253) | executes the query asynchronously and returns a [`ResultSetFuture`](http://docs.datastax.com/en/drivers/java/2.1/com/datastax/driver/core/ResultSetFuture.html) object which holds the future result of executing the query |
  | [`executeSync`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/Continents.java#L269) | is a blocking method that executes the query synchronously then returns a [`ResultSet`](http://docs.datastax.com/en/latest-java-driver-api/com/datastax/driver/core/ResultSet.html) object containing the result of executing the query |
    
### [app/com/vangav/vos_geo_server/controllers/](https://github.com/vangav/vos_geo_server/tree/master/app/com/vangav/vos_geo_server/controllers)

+ this directory contains all the generated controllers (api entry points) as defined in the project's config [controllers.json](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json)

+ [CommonPlayHandler.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java) is the parent class for all the controllers' handlers (e.g.: [HandlerReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java)) where the request-to-response logic is implemented

+ in vagav backend processing a request goes through sequential-optional steps; to enable one or more of these steps, they must be set to true in [request_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/request_properties.prop); [CommonPlayHandler.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java) class provides the ability to implement/override those sequential-optional steps in `CommonPlayHandler.java` or per-controller (e.g.: `HandlerReverseGeoCode.java`) as follows:

| step | explanantion |
| ---- | ------------ |
| [`checkSource`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L62) | is used to check the requests source. e.g.: if the service only accepts requests from mobile clients, a set of mac addresses, ... |
| [`throttle`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L69) | is used to track and prevent spammy behavior |
| [`authenticateRequest`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L76) | is used to authenticate a request using the built-in oauth2, facebook login, google login, ... |
| [`afterProcessing`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L83) | override this method per-controller-handler to do further processing after a request's response is sent back to the client; e.g.: use it for a blocking operation like push notifications which doesn't impact the service's core functionality in case of failure and shouldn't delay sending back the request's response |
| `dispatch...xxx` | methods starting with `dispatch` are processed after a request's response is returned and it's encouraged to dispatch the operations of these methods to the service's worker |
| [`dispatchDefaultOperations`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L90) | implement this method to override a service-wide after-response operation for all of the service's controllers; e.g.: update users' last-active-time |
| [`dispatchPushNotifications`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L97) | override this method per-controller to dispatch/process push notifications |
| [`dispatchAnalysis`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L104) | override this method per-controller to dispatch/process analysis |
| [`dispatchDefaultAnalysis`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L111) | implement to dispatch service-wide analysis for all controllers; e.g.: to keep track of request-to-response time |
| [`dispatchLogging`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L118) | override this method per-controller to dispatch/process logging |
| [`dispatchDefaultLogging`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L125) | implement to dispatch service-wide logging for all controllers; e.g.: log request/response/status-code |
| [`absorbUnhandledExceptions`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L136) | if vangav backend fails to handle an exception, it forwards the exception to this method to be absorbed and dealt with as needed. e.g.: send a notification email to system admin |


  
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
