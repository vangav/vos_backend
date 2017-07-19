
> **why?**

> for every vangav backend's service(s), there needs to be a client service that thoroughly tests it before production deployment - those client services sometimes need tens of thousands of lines of boring api code; so we went ahead and developed this client generator, because who wants to write boring code ;-)

> this client generator can also be used to generate clients for existing services (e.g.: a facebook graph api, ...)

> then we added a client for the generated api code that works sync, async as well as doing burst calls to stress test a service in dev; with the option to log everything (requests, responses, success/failure, request-to-response times, ...)

> to top it off, we designed generated clients to have server/client duality; means you can deploy multiple client services managed by another service which can then aggregate the results from all the client services using the built-in merge feature - e.g.: imagine testing your backend service from ten servers simultaneously or crawling a service using multiple instances/servers then periodically aggregating all the results to other service(s) responsible for storing/analyzing the aggregated data

# backend_client_java

# client generator

# using the generated client
