
> **why?** as services get larger, every now and then you come across a mind-boggling error; in this case a debugger comes in handy to help identify the problem

# debugging

1. execute the [_debug.sh](https://github.com/vangav/vos_geo_server/blob/master/_debug.sh) script; in a terminal session execute `./_debug.sh`
2. in eclipse: right-click the project **>** debug as **>** debug configurations... **>** double-click 'remote java application' **>** change the port number to `9999` **>** ok
3. add the needed break-points in the code
4. in the terminal session where you started the debugger execute `run` to run on port 9000 or `run different_port_number` 
5. from the browser/client send the problematic request that will trigger your first break point and you go from there
6. to stop the service from the terminal press `control + d`, then execute `exit` to stop the debugger

# next tutorial -> [deploy on a production server](https://github.com/vangav/vos_backend/blob/master/README/09_deploy.md)
> once your service is ready for release, this tutorial has the step-by-step process till your service is up and running on a production server - as well as how to scale it up

# share

[![facebook share](https://www.prekindle.com/images/social/facebook.png)](https://www.facebook.com/sharer/sharer.php?u=https%3A//github.com/vangav/vos_backend)  [![twitter share](http://www.howickbaptist.org.nz/wordpress/media/twitter-64-black.png)](https://twitter.com/home?status=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster%20%7C%20https%3A//github.com/vangav/vos_backend)  [![pinterest share](http://d7ab823tjbf2qywyt3grgq63.wpengine.netdna-cdn.com/wp-content/themes/velominati/images/share_icons/pinterest-black.png)](https://pinterest.com/pin/create/button/?url=https%3A//github.com/vangav/vos_backend&media=https%3A//scontent-mad1-1.xx.fbcdn.net/v/t31.0-8/20645143_1969408006608176_5289565717021239224_o.png?oh=acf20113a3673409d238924cfec648d2%26oe=5A3414B5&description=)  [![google plus share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/google.png)](https://plus.google.com/share?url=https%3A//github.com/vangav/vos_backend)  [![linkedin share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/linkedin.png)](https://www.linkedin.com/shareArticle?mini=true&url=https%3A//github.com/vangav/vos_backend&title=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster&summary=&source=)

# free consulting

[![vangav's consultant](http://www.footballhighlights247.com/images/mobile-share/fb-messenger-64x64.png)](https://www.facebook.com/mustapha.abdallah)
