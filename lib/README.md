# lib

+ This directory contains all the third_party libraries (jars) used for the utilities provided by Vangav Backend.
+ When generating a new RESTful Service using Vangav Backend, needed libs are automatically added to the generated service/worker. Using some utilities (e.g.: email, twilio, ...) require manually copying one or more of the jars in this directory into the generated service/worker.
+ Following is the explanation for each of these jars:

| Jar | Explanation |
| --- | ----------- |
| [alpn-boot-8.1.3.v20150130.jar](https://www.eclipse.org/jetty/documentation/9.3.x/alpn-chapter.html) | The development of new web protocols such as HTTP/2 raised the need of protocol negotiation within a Transport Layer Security (TLS) handshake. A protocol negotiation called ALPN (Application Layer Protocol Negotiation) RFC7301 has been defined to accomplish this. |
| [cassandra-driver-core-2.1.4.jar](http://www.datastax.com/) | Cassandra JAVA driver from DataStax. |
| [gcm-server-1.0.2.jar](https://developers.google.com/cloud-messaging/) | Google Cloud Messaging library used for sending Android push notifications. |
| [gson-2.5.jar](https://github.com/google/gson) | A Java serialization/deserialization library that can convert Java Objects into JSON and back. |
| [guava-16.0.jar](https://github.com/google/guava) | Guava is a set of core libraries that includes new collection types (such as multimap and multiset), immutable collections, a graph library, functional types, an in-memory cache, and APIs/utilities for concurrency, I/O, hashing, primitives, reflection, string processing, and much more! |
| [jackson-annotations-2.4.0.jar, jackson-core-2.4.4.jar, jackson-databind-2.4.4.jar](https://github.com/FasterXML/jackson) | Jackson is a multi-purpose Java library for processing JSON data format. |
| [javax.mail.jar](http://www.oracle.com/technetwork/java/javamail/index.html) | The JavaMail API provides a platform-independent and protocol-independent framework to build mail and messaging applications. |
| javax.ws.rs-api-2.0.jar | JAVA API for RESTful Web Services. |
| [jersey-bundle-1.19.1.jar](https://jersey.java.net/download.html) | Jersey provides it’s own API that extend the JAX-RS toolkit with additional features and utilities to further simplify RESTful service and client development. |
| metrics-core-3.0.2.jar | Metrics is a Java library which gives you unparalleled insight into what your code does in production. |
| [netty-all-4.1.1.Final.jar](http://netty.io/index.html) | Netty is an asynchronous event-driven network application framework for rapid development of maintainable high performance protocol servers & clients. |
| org.apache.oltu.oauth2.authzserver-0.31.jar, org.apache.oltu.oauth2.common-0.31.jar | Used for OAuth 2.0 |
| play-iteratees_2.11-2.4-2014-08-19-5fd9847.jar, play-json_2.11-2.4-2014-08-19-5fd9847.jar, play_2.10-2.2.6.jar | Used for play framework integration. |
| [pushy-0.7.3.jar](https://github.com/relayrides/pushy) | Pushy is a Java library for sending APNs (iOS, OS X, and Safari) push notifications. It is written and maintained by the engineers at [Turo](https://turo.com/). |
| scala-library-2.10.2-rc1.jar | Used for play framework integration. |
| [slf4j-api-1.7.6.jar](http://www.slf4j.org/) | The Simple Logging Facade for Java (SLF4J) serves as a simple facade or abstraction for various logging frameworks (e.g. java.util.logging, logback, log4j) allowing the end user to plug in the desired logging framework at deployment time. |
| [twilio-7.1.0-jar-with-dependencies.jar](https://github.com/twilio/twilio-java) | Used for sending SMSs and MMSs through [Twilio](https://www.twilio.com/). |
