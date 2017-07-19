
> **why?**

> for every vangav backend's service(s), there needs to be a client service that thoroughly tests it before production deployment - those client services sometimes need tens of thousands of lines of boring api code; so we went ahead and developed this client generator, because who wants to write boring code ;-)

> this client generator can also be used to generate clients for existing services (e.g.: a facebook graph api, ...)

> then we added a client for the generated api code that works sync, async as well as doing burst calls to stress test a service in dev

# backend_client_java
