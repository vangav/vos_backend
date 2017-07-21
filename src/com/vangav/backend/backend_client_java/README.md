
> **why?** we need client services to do things like thoroughly test backend services, communicate with public apis like facebook graph, ...; this package saves 90+% of the times needed to develop clients by generating most of the code as well as providing the ability to use generated clients sync/async, brust calls (for stress testing) along with the ability to optionally log everything (requests, responses, success/failure, request-to-response times, ...) - generated clients have a client/server duality, along with aggregate/merge functionalities to provide flexibility and scale

# backend_client_java

+ this tutorial is divided into two parts
  + first it explains how to generate a client, and the structure of generated clients
  + then it explains how to use generated clients
+ this tutorial uses [instagram test](https://github.com/vangav/vos_instagram_test) service as a reference, yet the same applies for every generated client

# client generator

### generator config
+ to add a client or more to a vangav backend generator config, just add a `.client_java` config file per client like [vos_instagram_dash_board.client_java](https://github.com/vangav/vos_instagram_test/blob/master/generator_config/vos_instagram_dash_board.client_java); that file is exactly the same as [controllers.json](https://github.com/vangav/vos_instagram_dash_board/blob/master/generator_config/controllers.json) config file used to generate [instagram dash board](https://github.com/vangav/vos_instagram_dash_board) service, just a different extension `.client_java` for vangav backend service generator to detect that it should generate a java client
+ refer to the [service generator config structure](https://github.com/vangav/vos_backend/blob/master/README/04_rest_service_config_structure.md#controllersjson-structure) tutorial for an in depth explaination of the building blocks of these config files

### [clients_generator](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/backend_client_java/clients_generator)
+ this sub-package is responsible for verifying `.client_java` config files as well as generating client(s) for a vangav backend service

### generated clients structure
+ [clients_properties.prop](https://github.com/vangav/vos_instagram_test/blob/master/conf/prop/clients_properties.prop) properties file is generated to hold the url for each client's server - after generation, those values must be set by the user
+ [clients](https://github.com/vangav/vos_instagram_test/tree/master/app/com/vangav/vos_instagram_test/clients) package is added to hold all of the clients' source files, where each client has its own sub-package in addition to the following two classes:
  + [ClientsProperties](https://github.com/vangav/vos_instagram_test/blob/master/app/com/vangav/vos_instagram_test/clients/ClientsProperties.java) represents the [clients_properties.prop](https://github.com/vangav/vos_instagram_test/blob/master/conf/prop/clients_properties.prop) properties file
  + [Constants](https://github.com/vangav/vos_instagram_test/blob/master/app/com/vangav/vos_instagram_test/clients/Constants.java) stores the values from [ClientsProperties](https://github.com/vangav/vos_instagram_test/blob/master/app/com/vangav/vos_instagram_test/clients/ClientsProperties.java) into global constants
+ for each client's controller (api entry point), the following three classes are generated

| class | explanation |
| ----- | ----------- |
| [Requestxxx](https://github.com/vangav/vos_instagram_test/blob/master/app/com/vangav/vos_instagram_test/clients/vos_instagram/login_email/RequestLoginEmail.java) | represents the request and provides: a [mandatory params constructor](https://github.com/vangav/vos_instagram_test/blob/master/app/com/vangav/vos_instagram_test/clients/vos_instagram/login_email/RequestLoginEmail.java#L75), a [mandatory + optional params constructor](https://github.com/vangav/vos_instagram_test/blob/master/app/com/vangav/vos_instagram_test/clients/vos_instagram/login_email/RequestLoginEmail.java#L99) and a [per-optional param setter](https://github.com/vangav/vos_instagram_test/blob/master/app/com/vangav/vos_instagram_test/clients/vos_instagram/login_email/RequestLoginEmail.java#L148) |
| [Responsexxx](https://github.com/vangav/vos_instagram_test/blob/master/app/com/vangav/vos_instagram_test/clients/vos_instagram/login_email/ResponseLoginEmail.java) | represents the response and holds a response's value(s) upon making a client call |
| [ControllerCallxxx](https://github.com/vangav/vos_instagram_test/blob/master/app/com/vangav/vos_instagram_test/clients/vos_instagram/login_email/ControllerCallLoginEmail.java) | inherits from [ControllerCall](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_client_java/ControllerCall.java); represents a client's controller-call, and takes a [Requestxxx](https://github.com/vangav/vos_instagram_test/blob/master/app/com/vangav/vos_instagram_test/clients/vos_instagram/login_email/RequestLoginEmail.java) object as a constructor argument - using this class to make a client call is explained below in this tutorial |

# using generated clients

### vangav backend side
> this sub-section explains the available client-features

| pkg/class | explanation |
| --------- | ----------- |
| [ErrorResponse](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_client_java/ErrorResponse.java) | represents the default error response (vangav backend's) for a client's backend service; otherwise define your own [ErrorResponseJson](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_client_java/clients_generator/json/ErrorResponseJson.java) in the `.client_java` config file before generating a client |
| [ControllerCallLog](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_client_java/ControllerCallLog.java) | represents a the log for a single client's controller call with [attributes](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_client_java/ControllerCallLog.java#L63) like `requestToResponseTimeInMilliSeconds`, `response`, exceptions (if any), ... - using `ControllerCallLog` is optional as shown below |
| [ControllerCall](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_client_java/ControllerCall.java) | is an abstract parent class for all controller call classes like [ControllerCallLoginEmail](https://github.com/vangav/vos_instagram_test/blob/master/app/com/vangav/vos_instagram_test/clients/vos_instagram/login_email/ControllerCallLoginEmail.java) where each object represents a single client call optionally holding its [ControllerCallLog](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_client_java/ControllerCallLog.java) object |
| [BackendClientSession](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_client_java/BackendClientSession.java) | represents a session where any number of controller calls can be made to one or more clients and provides the functionalities listed below this table |
| [server_json](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/backend_client_java/json_response/server_json) |  |
| []() |  |

the following are the functionalities provided through [BackendClientSession](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_client_java/BackendClientSession.java)
+ [construct](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_client_java/BackendClientSession.java#L95) with or without logging
+ [merge](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_client_java/BackendClientSession.java#L119) multiple sessions; useful for things like merging the results of multiple client sessions
+ [execute controller calls](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_client_java/BackendClientSession.java#L217) executes one or more controller calls (e.g.: [ControllerCallLoginEmail](https://github.com/vangav/vos_instagram_test/blob/master/app/com/vangav/vos_instagram_test/clients/vos_instagram/login_email/ControllerCallLoginEmail.java)) for one or more clients sync or async; if logs are set to true it adds them to the session's log as well as returning this subset of logs
+ [execute burst controller calls](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_client_java/BackendClientSession.java#L312) repeats the passed controller calls the number of times in the methods param (e.g.: 100,000) without logging or waiting for response; mainly to stress test the server side where everything should be logged on the server side
+ in addition to getter methods for general session logs as well as logs per-controller-call

### generated service side
> this sub-section explains how to use the client-features explained above
