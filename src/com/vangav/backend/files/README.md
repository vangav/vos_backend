# files

+ This package provide all the funtionalities related to file-loading/writing, for all file types (text, images, HTTP response files, Object files, etc ...).

+ [FileLoaderInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/files/FileLoaderInl.java) usage [example](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_generator/BackendGeneratorMain.java#L286):
```java
File[] keyspaceConfigFiles =
  FileLoaderInl.loadFiles(
    dirPath,
    CassandraGeneratorConstantsInl.kCassandraKeyspaceExt);
```

+ [FileWriterInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/files/FileWriterInl.java) usage [example](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/backend_generator/BackendGeneratorMain.java#L532):
```java
FileWriterInl.mkdirs(
  "../../"
  + projectName
  + "/vangav_m/solutions",
  false);
```
