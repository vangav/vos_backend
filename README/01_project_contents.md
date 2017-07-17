# project's contents

> 5-10 min: this tutorial explains the building blocks of vangav backend

following is a brief explanantion for the contents of each of the vangav backend project's top level directories

### [apache-cassandra-2.1.2](https://github.com/vangav/vos_backend/tree/master/apache-cassandra-2.1.2)
+ this is the cassandra used by vangav backend
+ in dev mode, it's automatically used by all the services generated using vangav backend
+ for prod mode, refer to the [deploy](https://github.com/vangav/vos_backend/blob/master/README/09_deploy.md) tutorial

### [data](https://github.com/vangav/vos_backend/tree/master/data)
+ contains the data files needed for using some of the built-in utilities like reverse geo coding
+ [vos_geo_server](https://github.com/vangav/vos_geo_server) is one example that utilizes these data

### [dist](https://github.com/vangav/vos_backend/tree/master/dist)
+ contains `vos_backend.jar` that includes both of the underlying backend for all generated services as well as all of the provided utilities
+ the jar above is automatically included with all generated services
+ alternatively one case use it as a lib for any java project

### [doc](https://github.com/vangav/vos_backend/tree/master/doc)
+ contains the java docs for vangav backend's source code

### [lib](https://github.com/vangav/vos_backend/tree/master/lib)
+ conatins all the third-party libs used by vangav backend
+ all the necessary libs are automatically copied to generated services
+ the optional use of some utilities (e.g.: email. twilio, ...) require manually copying a lib or more to your generated service's lib directory
+ following is the explanation for each of the libs included

| jar | explanation |
| --- | ----------- |
| [alpn-boot-8.1.3.v20150130.jar](https://www.eclipse.org/jetty/documentation/9.3.x/alpn-chapter.html) | used for application layer protocol negotiation |
| [cassandra-driver-core-2.1.4.jar](http://www.datastax.com/) | cassandra's datastax java driver |
| [gcm-server-1.0.2.jar](https://developers.google.com/cloud-messaging/) | google cloud messaging library, used for sending android push notifications |
| [gson-2.5.jar](https://github.com/google/gson) | a java serialization/deserialization library, converts java objects into json and vice versa |
| [guava-16.0.jar](https://github.com/google/guava) | guava is a set of core libraries that includes new collection types (such as multimap and multiset), immutable collections, a graph library, functional types, an in-memory cache, and apis/utilities for concurrency, i/o, hashing, primitives, reflection, string processing, ... |
| [jackson-annotations-2.4.0.jar, jackson-core-2.4.4.jar, jackson-databind-2.4.4.jar](https://github.com/FasterXML/jackson) | jackson is a multi-purpose java library for processing json data format |
| [javax.mail.jar](http://www.oracle.com/technetwork/java/javamail/index.html) | java mail api provides a platform-independent and protocol-independent framework to build mail and messaging applications |
| javax.ws.rs-api-2.0.jar | java api for restful web services |
| [jersey-bundle-1.19.1.jar](https://jersey.java.net/download.html) | jersey provides it’s own api that extend the jax-rs toolkit with additional features and utilities to further simplify restful service and client development |
| metrics-core-3.0.2.jar | metrics is a java library which gives you unparalleled insight into what your code does in production |
| [netty-all-4.1.1.Final.jar](http://netty.io/index.html) | netty is an asynchronous event-driven network application framework for rapid development of maintainable high performance protocol servers & clients |
| org.apache.oltu.oauth2.authzserver-0.31.jar, org.apache.oltu.oauth2.common-0.31.jar | used for oauth 2.0 |
| play-iteratees_2.11-2.4-2014-08-19-5fd9847.jar, play-json_2.11-2.4-2014-08-19-5fd9847.jar, play_2.10-2.2.6.jar | used for play framework integration |
| [pushy-0.7.3.jar](https://github.com/relayrides/pushy) | pushy is a java library for sending apns (iOS, OS X, and Safari) push notifications, written and maintained by the engineers at [turo](https://turo.com/) |
| scala-library-2.10.2-rc1.jar | used for play framework integration |
| [slf4j-api-1.7.6.jar](http://www.slf4j.org/) | the simple logging facade for java (slf4j) serves as a simple facade or abstraction for various logging frameworks (e.g. java.util.logging, logback, log4j) allowing the end user to plug in the desired logging framework at deployment time |
| [twilio-7.1.0-jar-with-dependencies.jar](https://github.com/twilio/twilio-java) | used for sending sms and mms through [twilio](https://www.twilio.com/) |

### [play-2.2.6](https://github.com/vangav/vos_backend/tree/master/play-2.2.6)
+ this is the play framework used by vangav backend
+ each generated service comes with _scripts.sh (_run.sh, _compile.sh, etc ...) that point to this directory for play framework

### [prop](https://github.com/vangav/vos_backend/tree/master/prop)
+ holds all vangav backend's properties files
+ generated services autoamtically get a copy of the necessary properties files depending on the project's config
+ optionally copy additional properties files to the generated service to use the corresponding utility
+ following is the explanation of each of the properties files, inside each properties file is a detailed explanation of exactly how to set/use each property

| properties file | explanantion |
| --------------- | ------------ |
| [android_notification](https://github.com/vangav/vos_backend/blob/master/prop/android_notification_properties.prop) | anrdoid push notifications |
| [apple_notification](https://github.com/vangav/vos_backend/blob/master/prop/apple_notification_properties.prop) | apple push notifications |
| [cassandra](https://github.com/vangav/vos_backend/blob/master/prop/cassandra_properties.prop) | cassandra's api, topology, ... |
| [dispatcher](https://github.com/vangav/vos_backend/blob/master/prop/dispatcher_properties.prop) | dispatcher's workers topology |
| [facebook_graph_api_edge](https://github.com/vangav/vos_backend/blob/master/prop/facebook_graph_api_edge_properties.prop) | paging, page_limit, ... |
| [java_email](https://github.com/vangav/vos_backend/blob/master/prop/java_email_properties.prop) | smtp properties |
| [mail_gun_email](https://github.com/vangav/vos_backend/blob/master/prop/mail_gun_email_properties.prop) | mailgun api access values |
| [param_validator](https://github.com/vangav/vos_backend/blob/master/prop/param_validator_properties.prop) | defines some of the request's params max_size like photos, captions, chat messages, ... |
| [request](https://github.com/vangav/vos_backend/blob/master/prop/request_properties.prop) | switches on/off the sequential steps of a request's processing: check source device, throttle request, validate params, authenticate, after-response processing, default operations, notifications, analysis, logging; this makes it simple to control how a request is processed without touching the code |
| [response_error](https://github.com/vangav/vos_backend/blob/master/prop/response_error_properties.prop) | switches on/off what to send back to the client in case of an error response: type, code, sub-code, message, class, stack-trace, trace-id |
| [thread_pool](https://github.com/vangav/vos_backend/blob/master/prop/thread_pool_properties.prop) | vangav backend has a thread pool per type of operation with an optimal default size (per machine's specs) for handling big scale; only set values for this properties file if you are sure |
| [twilio](https://github.com/vangav/vos_backend/blob/master/prop/twilio_properties.prop) | twilio api access values |

### [src](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend)
+ contains all of vangav backend's source code
+ each directory inside `src` has its own readme file

### [tools_bin](https://github.com/vangav/vos_backend/tree/master/tools_bin)
+ contains `backend_generator.jar` responsible for generating new vangav backend services using the command `java -jar backend_generator.jar new my_new_service_name`
+ inside it the `assets` directory has utilities that gets added to generated services like the `cassandra updater` and the `vangav mighty` solutions generator

### [vangav_backend_templates](https://github.com/vangav/vos_backend/tree/master/vangav_backend_templates)
+ contains the generator config files used to generate the template services
+ all the template services has a complete version (with the 10-% of logic code added to them) on github as follows

| service | level | explanation |
| ------- | ----- | ----------- |
| [calculate sum](https://github.com/vangav/vos_calculate_sum) | 1 | simple quick start service that calculates the sum of two numbers |
| [geo server](https://github.com/vangav/vos_geo_server) | 2 | given a lat and long it returns the geo hash, city, country and continent info, it also keeps track of the top queried continents and countries |
| **whatsapp** | 3 |  |
| [whatsapp](https://github.com/vangav/vos_whatsapp) |  | the main whatsapp service handling authentication, user info and messaging |
| [whatsapp worker](https://github.com/vangav/vos_whatsapp_worker) |  | non-user-experience-critical operations like analytics are handed over from the main whatsapp service to the worker service to handle them |
| [whatsapp analytics](https://github.com/vangav/vos_whatsapp_analytics) |  | handles fetching service's analytics like messages count within certain dates, ... |
| **analytics** | 3 |  |
| [vangav analytics writer](https://github.com/vangav/vos_vangav_analytics_writer) |  | handles writing analytics data to the database in a non blocking manner in after-response processing |
| [vangav analytics reader](https://github.com/vangav/vos_vangav_analytics_reader) |  | handles fetching analytics data in various ways (daily, monthly, ...) |
| **instagram** | 4 |  |
| [instagram](https://github.com/vangav/vos_instagram) |  | the main instagram service handling authentication, posting, insteraction, search, explore, ... |
| [instagram worker](https://github.com/vangav/vos_instagram_worker) |  | non-user-experience-critical operations like analytics are handed over from the main instagram service to the worker service to handle them |
| [instagram dispense](https://github.com/vangav/vos_instagram_dispense) |  | handles distributing a new post to followers (a separate service since a single post can be delivered to tens of millions of followers) |
| [instagram jobs](https://github.com/vangav/vos_instagram_jobs) |  | it handles self heal as some post-response-success jobs like `dispense` record themselves in the db until success, this service fetches incomplete/failed jobs and retries executing them; this service also handles dated and recurrent jobs (e.g.: send a notification to each member on their birthday, send a notification to new members after 7 days, ...) |
| [instagram dash board](https://github.com/vangav/vos_instagram_dash_board) |  | handles fetching analytics like members demographics, counts, activity, ... |
| [instagram test](https://github.com/vangav/vos_instagram_test) |  | used to test all the instagram services above end to end and maintains a session log for each test run including success/failure info as well as request-to-response times |
| [instagram bots](https://github.com/vangav/vos_instagram_bots) |  | will be deployed on vangav servers at a later stage to simulate elevating usage of the instagram services above for a year or more |
