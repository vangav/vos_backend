# tools_bin

+ tools_bin has all the executable jars provided by Vangav Backend.
+ `backend_generator.jar` is used to generate new Vangav Backend services and workers using the command **`java -jar backend_generator.jar new my_new_service_name`** as explained [**here**](https://github.com/vangav/vos_backend#generate-a-new-service).
+ [assets](https://github.com/vangav/vos_backend/tree/master/tools_bin/assets) has other executables that get added to generated service/workers.
  + `cassandra_keyspaces_updater.jar` is used to update cassandra's JAVA client, CQL scripts and phriction wiki after a service/worker has already been generated as explaing [**here**](https://github.com/vangav/vos_backend/blob/master/README/03_generated_rest_service_structure.md#cassandra_updater).
  + `vangav_m_json_client.jar` is used to add/remove/edit [**Vangav M**](http://vangav.com/) solutions as explained [**here**](https://github.com/vangav/vos_backend/blob/master/README/03_generated_rest_service_structure.md#vangav_m).
