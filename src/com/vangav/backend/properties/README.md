
> **why?** using properties files is useful in many ways like: easily configure how a service works without having to understand/edit code, reconfigure a live service (e.g.: changing a helper service's ip) without having to stop it to edit its code

# properties

## structure

| class | explanation |
| ----- | ----------- |
| [PropertiesFile](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/properties/PropertiesFile.java) | is the parent class for all properties files' mapping classes like [RequestProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestProperties.java) |
| [PropertiesLoader](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/properties/PropertiesLoader.java) | loads all properties files under `conf/prop`; here's an example of initializing it in [geo server / Global: `beforeStart`](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java#L84) |

## [vangav backend properties](https://github.com/vangav/vos_backend/tree/master/prop)

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

## adding a new properties file

1. add properties file under `conf/prop`, e.g.: [constants_properties](https://github.com/vangav/vos_instagram/blob/master/conf/prop/constants_properties.prop)

2. add a mapping class that inherits from [PropertiesFile](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/properties/PropertiesFile.java), e.g.: [ConstantsProperties](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/common/properties/ConstantsProperties.java)

# next tutorial -> [public apis](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/public_apis)
> handles fetching data from facebook graph api and car2go api

# share

[![facebook share](https://www.prekindle.com/images/social/facebook.png)](https://www.facebook.com/sharer/sharer.php?u=https%3A//github.com/vangav/vos_backend)  [![twitter share](http://www.howickbaptist.org.nz/wordpress/media/twitter-64-black.png)](https://twitter.com/home?status=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster%20%7C%20https%3A//github.com/vangav/vos_backend)  [![pinterest share](http://d7ab823tjbf2qywyt3grgq63.wpengine.netdna-cdn.com/wp-content/themes/velominati/images/share_icons/pinterest-black.png)](https://pinterest.com/pin/create/button/?url=https%3A//github.com/vangav/vos_backend&media=https%3A//scontent-mad1-1.xx.fbcdn.net/v/t31.0-8/20645143_1969408006608176_5289565717021239224_o.png?oh=acf20113a3673409d238924cfec648d2%26oe=5A3414B5&description=)  [![google plus share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/google.png)](https://plus.google.com/share?url=https%3A//github.com/vangav/vos_backend)  [![linkedin share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/linkedin.png)](https://www.linkedin.com/shareArticle?mini=true&url=https%3A//github.com/vangav/vos_backend&title=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster&summary=&source=)

# free consulting

[![vangav's consultant](http://www.footballhighlights247.com/images/mobile-share/fb-messenger-64x64.png)](https://www.facebook.com/mustapha.abdallah)
