
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
+ this sub-package is responsible for verifying `.client_java`

### generated clients structure

# using generated clients
