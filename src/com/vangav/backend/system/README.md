
> **why?** this package offers fetching information about the system on which a service is running as well as interacting with that system through the commandline
> + fetching information is important for various reasons like: configuring the service depending on the avaialable resources (ram, cpu, disk, ...), alerting the service's admin when the system hits resources' limits (e.g.: 90+% cpu, ram, ...)
> + interacting with the system is important for various reasons like: getting user input, executing console commands (e.g.: mv, rm, cp, ...), ...

# system

### structure

| class | explanation |
| ----- | ----------- |
| [CommandLineInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/system/CommandLineInl.java) | has inline static methods for executing command-line commands |
| [InteractiveConsole](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/system/InteractiveConsole.java) | simplifies interactive-console operations |
| [SystemInfoInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/system/SystemInfoInl.java) | has inline static methods for getting system information like os-type, mac-address, cpu-usage, free-ram, free-disk-space, ... |

### [CommandLineInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/system/CommandLineInl.java) usage example

+ in [BackendGeneratorMain: `generateBackend`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_generator/BackendGeneratorMain.java#L228)

```java
  // 1- make an executable script
  CommandLineInl.executeCommand(
    "chmod +x ../../"
    + projectName
    + "/compile_project.sh");

  // 2- execute the script
  CommandLineInl.executeCommand(
    "../../"
    + projectName
    + "/compile_project.sh");

  // 3- delete the script
  CommandLineInl.executeCommand(
    "rm ../../"
    + projectName
    + "/compile_project.sh");
```

### [InteractiveConsole](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/system/InteractiveConsole.java) usage example

+ in [BackendGeneratorMain: `generateBackend`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_generator/BackendGeneratorMain.java#L250)

```java
  // add a worker project?
  if (InteractiveConsole.i().confirm(
        "Generate worker ["
        + projectName
        + "_worker] for new project ["
        + projectName
        + "] ?") == true) {

    generateWorker(projectName);
  }
```

### [SystemInfoInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/system/SystemInfoInl.java) features

| method | explanation |
| ------ | ----------- |
| [`getOsType`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/system/SystemInfoInl.java#L90) | mac, unix, ... |
| [`getUserName`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/system/SystemInfoInl.java#L118) | server's user name |
| [`getMacAddress`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/system/SystemInfoInl.java#L138) | server's mac address, e.g.: `00-26-B9-9B-61-BF` |
| [`getProcessorCoresCount`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/system/SystemInfoInl.java#L165) | e.g.: vangav backend services use this feature to dynamically set the size of each `thread pool` for optimal perfomance |
| [`getCpuUsage`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/system/SystemInfoInl.java#L177) | servers cpu usage (e.g.: `90.8`) |
| [`getFreeRam`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/system/SystemInfoInl.java#L267) | e.g.: `1 GB`, `234 MB`, `95749 KB`, ... |
| [`getUsableDisk`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/system/SystemInfoInl.java#L330) | because usable disk space can be more or less than the free disk space (because of things like low-priority cache buffers, ...) |

# next tutorial -> [thread pool](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/thread_pool)
> contains latch threads, periodic jobs and the thread pools responsible for maintaining top performance for vangav backend services (in-memory threads, cassandra, dispatcher and rest client)

# share

[![facebook share](https://www.prekindle.com/images/social/facebook.png)](https://www.facebook.com/sharer/sharer.php?u=https%3A//github.com/vangav/vos_backend)  [![twitter share](http://www.howickbaptist.org.nz/wordpress/media/twitter-64-black.png)](https://twitter.com/home?status=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster%20%7C%20https%3A//github.com/vangav/vos_backend)  [![pinterest share](http://d7ab823tjbf2qywyt3grgq63.wpengine.netdna-cdn.com/wp-content/themes/velominati/images/share_icons/pinterest-black.png)](https://pinterest.com/pin/create/button/?url=https%3A//github.com/vangav/vos_backend&media=https%3A//scontent-mad1-1.xx.fbcdn.net/v/t31.0-8/20645143_1969408006608176_5289565717021239224_o.png?oh=acf20113a3673409d238924cfec648d2%26oe=5A3414B5&description=)  [![google plus share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/google.png)](https://plus.google.com/share?url=https%3A//github.com/vangav/vos_backend)  [![linkedin share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/linkedin.png)](https://www.linkedin.com/shareArticle?mini=true&url=https%3A//github.com/vangav/vos_backend&title=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster&summary=&source=)

# free consulting

[![vangav's consultant](http://www.footballhighlights247.com/images/mobile-share/fb-messenger-64x64.png)](https://www.facebook.com/mustapha.abdallah)
