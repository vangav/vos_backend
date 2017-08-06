
# src

+ contains all of vangav backend's source code
+ each directory inside `src/com/vangav/backend` has its own tutorial (readme file)

### overview

| package | brief |
| ------- | ----- |
| [backend client java](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/backend_client_java) | handles generating java clients and contains vangav backend's client framework |
| [backend generator](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/backend_generator) | manages all vangav backend generators (api, database client, worker and java client) |
| [cassandra](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/cassandra) | handles generating database clients, contains the framework for generated clients and handles all cassandra operations |
| [compression](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/compression) | contains different compression algorithms |
| [content](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content) | handles content (code, phriction-wiki, text, ...) checking, formatting and generation; vangav backend relies on this package to verify generation config and format generated code, scripts, wiki, ... |
| [data structures and algorithms](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/data_structures_and_algorithms) | has data structures (e.g.: heap, kd-tree, tuples, ...) and algorithms (collections, matricies, strings, arrays, ...) |
| [dispatcher](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/dispatcher) | handles generating worker services, contains the framework for dispatchers/workers and handles all of their operations |
| [exceptions](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/exceptions) | vangav exceptions are used to handle `bad request` and `internal error`; those exceptions can be returned to the client and loggable (in database, text files, ...) |
| [files](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/files) | simplifies various types of files needed for vangav backend services like: properties, json config, images, http response files, ommiting comments, directory operations, ... |
| [geo](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo) | handles reverse geo coding, geo grids and geo hashing |
| [ids](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/ids) | handles `uuid` operations, sequential ids and twitter's snow flake ids |
| [images](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/images) | don't use: this utility's functionality is still incomplete |
| [math](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/math) | handles mathematical and geometric operations like: numeric, ranges, circles, line segments, straight lines, ... |
| [metrics](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/metrics) | handles distance, time, date and calendar operations |
| [networks](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks) | has sync/async rest client, rest jobs, email clients, twilio messaging and download utility |
| [play framework](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/play_framework) | handles generating api code and contains the framework for generated vangav backend services; *this package is vangav backend's backbone* |
| [properties](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/properties) | handles loading and extracting data from properties files |
| [public apis](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/public_apis) | handles fetching data from facebook graph api and car2go api |
| [push notifications](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications) | handles building and sending apple and android notifications |
| [security](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security) | handles authentication (facebook, google, oauth 2 and transaction tokens) and cryptography (asymmetric, hashing and two-way encryption) |
| [system](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/system) | handles fetching system info (cpu usage, free ram/disk, os type, number of cores, ...) and console operation (interactive, commands, ...) |
| [thread pool](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/thread_pool) | contains latch threads, periodic jobs and the thread pools responsible for maintaining top performance for vangav backend services (in-memory threads, cassandra, dispatcher and rest client) |
| [vangav mighty](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/vangav_m) | handles generating and binding [vangav mighty](http://vangav.com/) solutions |

# share

[![facebook share](https://www.prekindle.com/images/social/facebook.png)](https://www.facebook.com/sharer/sharer.php?u=https%3A//github.com/vangav/vos_backend)  [![twitter share](http://www.howickbaptist.org.nz/wordpress/media/twitter-64-black.png)](https://twitter.com/home?status=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster%20%7C%20https%3A//github.com/vangav/vos_backend)  [![pinterest share](http://d7ab823tjbf2qywyt3grgq63.wpengine.netdna-cdn.com/wp-content/themes/velominati/images/share_icons/pinterest-black.png)](https://pinterest.com/pin/create/button/?url=https%3A//github.com/vangav/vos_backend&media=https%3A//scontent-mad1-1.xx.fbcdn.net/v/t31.0-8/20645143_1969408006608176_5289565717021239224_o.png?oh=acf20113a3673409d238924cfec648d2%26oe=5A3414B5&description=)  [![google plus share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/google.png)](https://plus.google.com/share?url=https%3A//github.com/vangav/vos_backend)  [![linkedin share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/linkedin.png)](https://www.linkedin.com/shareArticle?mini=true&url=https%3A//github.com/vangav/vos_backend&title=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster&summary=&source=)

# free consulting

[![vangav's consultant](http://www.footballhighlights247.com/images/mobile-share/fb-messenger-64x64.png)](https://www.facebook.com/mustapha.abdallah)
