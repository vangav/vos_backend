
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
