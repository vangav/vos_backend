
# vangav backend templates
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
| [instagram jobs](https://github.com/vangav/vos_instagram_jobs) |  | it handles self heal as some post-response-success jobs like `dispense` record themselves in the db until success, this service fetches incomplete/failed jobs and retries executing them; this service also handles dated and recurrent jobs (e.g.: rank top users/posts everyday, send a notification to each member on their birthday, send a notification to new members after 7 days, ...) |
| [instagram dash board](https://github.com/vangav/vos_instagram_dash_board) |  | handles fetching analytics like members demographics, counts, activity, ... |
| [instagram test](https://github.com/vangav/vos_instagram_test) |  | used to test all the instagram services above end to end and maintains a session log for each test run including success/failure info as well as request-to-response times |
| [instagram bots](https://github.com/vangav/vos_instagram_bots) |  | will be deployed on vangav servers at a later stage to simulate elevating usage of the instagram services above for a year or more |

# share

[![facebook share](https://www.prekindle.com/images/social/facebook.png)](https://www.facebook.com/sharer/sharer.php?u=https%3A//github.com/vangav/vos_backend)  [![twitter share](http://www.howickbaptist.org.nz/wordpress/media/twitter-64-black.png)](https://twitter.com/home?status=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster%20%7C%20https%3A//github.com/vangav/vos_backend)  [![pinterest share](http://d7ab823tjbf2qywyt3grgq63.wpengine.netdna-cdn.com/wp-content/themes/velominati/images/share_icons/pinterest-black.png)](https://pinterest.com/pin/create/button/?url=https%3A//github.com/vangav/vos_backend&media=https%3A//scontent-mad1-1.xx.fbcdn.net/v/t31.0-8/20645143_1969408006608176_5289565717021239224_o.png?oh=acf20113a3673409d238924cfec648d2%26oe=5A3414B5&description=)  [![google plus share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/google.png)](https://plus.google.com/share?url=https%3A//github.com/vangav/vos_backend)  [![linkedin share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/linkedin.png)](https://www.linkedin.com/shareArticle?mini=true&url=https%3A//github.com/vangav/vos_backend&title=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster&summary=&source=)

# free consulting

[![vangav's consultant](http://www.footballhighlights247.com/images/mobile-share/fb-messenger-64x64.png)](https://www.facebook.com/mustapha.abdallah)
