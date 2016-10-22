# Contents

+ [apache-cassandra-2.1.2](https://github.com/vangav/vos_backend/tree/master/apache-cassandra-2.1.2) is the cassandra's version used by Vangav Backend. Services generated using Vangav Backend automatically use this cassandra on dev mode. For prod mode refer to the "Deployement" section below.
+ [data](https://github.com/vangav/vos_backend/tree/master/data) hold's the data files included with Vangav Backend for optional use like the data for reverse geo coding.
+ [dist](https://github.com/vangav/vos_backend/tree/master/dist) contains Vangav Backend's lib (gets automatically copied into new services upon generation).
+ [lib](https://github.com/vangav/vos_backend/tree/master/lib) contains all the third-party libraries used by Vangav Backend.
+ [play-2.2.6](https://github.com/vangav/vos_backend/tree/master/play-2.2.6) is the play framework's version used by Vangav Backend. Each generated service comes with _scripts (_run, _compile, etc ...) that point to this directory for play framework. No need to do anything here.
+ [prop](https://github.com/vangav/vos_backend/tree/master/prop) contains all the mandatory/optional properties files used by services generated using Vangav Backend. Relevant properties files get automatically copied into newely generated service; optionally copy more properties files from here if needed for an additional optional feature.
+ **[src/com/vangav/backend](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend)** contains all Vangav Backend's source code. The whole code is license-free with minor exceptions put under packages called "third_party" like geo hashing.
+ **[tools_bin](https://github.com/vangav/vos_backend/tree/master/tools_bin)** contains `backend_generator.jar` which the exec responsible for generating new Vangav Backend services using the command `java -jar backend_generator.jar new my_new_service_name`.
+ **[vangav_backend_templates](https://github.com/vangav/vos_backend/tree/master/vangav_backend_templates)** contains functional templates for Vangav Backend's services.
