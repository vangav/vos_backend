
> **why content checking?** this sub-package is needed for verifying vangav backend generator config files and vangav mighty solution files

> **why content formatting?** this sub-package is needed for formatting generated java code, cql scripts and phriction wiki files; for example a controller's name would be `lower_under` as a package name, `CamelCase` as a class name, `lowerCamelCase` as a variable name, ...

> **why content generation?** generating random content (strings, numbers, emails, ...) are useful in various cases like generating test data, security tokens, ...

# content

### [checking](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content/checking)

| class | explanation |
| ----- | ----------- |
| [CassandraCqlVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/CassandraCqlVerifierInl.java) | verifies the correctness of cannasandra's cql |
| [CharVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/CharVerifierInl.java) | verifies characters' values |
| [JavaCodeVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/JavaCodeVerifierInl.java) | varifies java code (e.g.: identifier, package name, ...) |
| [StringVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/StringVerifierInl.java) | verifies a string's value (e.g.: belongs to an enum) |

#### [CassandraCqlVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/CassandraCqlVerifierInl.java) usage examples

+ using [`verifyName`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/CassandraCqlVerifierInl.java#L81) in [CassandraVerifierInl: `verifyKeyspacesConfig`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/keyspaces_generator/CassandraVerifierInl.java#L114)

```java
  // verify keyspace's name
  CassandraCqlVerifierInl.verifyName(currKeyspaceJson.name);
```

+ using [`verifyColumnType`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/CassandraCqlVerifierInl.java#L149) in [CassandraVerifierInl: `verifyTableConfig`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/keyspaces_generator/CassandraVerifierInl.java#L203)

```java
  CassandraCqlVerifierInl.verifyColumnType(columnJson.type);
```

+ using [`verifyCaching`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/CassandraCqlVerifierInl.java#L428) in [CassandraVerifierInl: `verifyTableConfig`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/keyspaces_generator/CassandraVerifierInl.java#L321)

```java
  CassandraCqlVerifierInl.verifyCaching(tableJson.caching);
```

#### [CharVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/CharVerifierInl.java) usage example

+ using [`isOneOfChar`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/CharVerifierInl.java#L122) in [JavaCodeVerifierInl: `verifyIdentifier`](https://github.com/vangav/vos_backend/blob/b6d137a78c036e2d79dacdaf0b3356e1760a7a74/src/com/vangav/backend/content/checking/JavaCodeVerifierInl.java#L86)

```java
  if (CharVerifierInl.isOneOfChar(
        identifier.charAt(0),
        CharVerifierInl.CharType.LOWER_CASE,
        CharVerifierInl.CharType.UPPER_CASE,
        CharVerifierInl.CharType.UNDER_SCORE) == false) {
    
    // ...
  }
```

#### [JavaCodeVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/JavaCodeVerifierInl.java) usage examples

+ using [`verifyPackageName`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/JavaCodeVerifierInl.java#L134) in [ControllersVerifierInl: `verifyControllersJson`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/controllers_generator/ControllersVerifierInl.java#L108)

```java
  JavaCodeVerifierInl.verifyPackageName(controllersJson.java_package);
```

+ using [`verifyIdentifier`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/JavaCodeVerifierInl.java#L75) in [ControllersVerifierInl: `verifyControllersJson`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/controllers_generator/ControllersVerifierInl.java#L109)

```java
  JavaCodeVerifierInl.verifyIdentifier(
    "project name",
    projectName);
```

#### [StringVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/StringVerifierInl.java) usage examples

+ using [`isOneOfString`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/StringVerifierInl.java#L78) in [ControllersVerifierInl: `verifyControllersJson`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/controllers_generator/ControllersVerifierInl.java#L144)

```java
  // verify type
  StringVerifierInl.isOneOfString(
    "controller type",
    controllerJson.type,
    true,
    "GET",
    "POST");
```

+ using [`belongsToEnum`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/StringVerifierInl.java#L151) in [ControllersVerifierInl: `verifyControllersJson`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/controllers_generator/ControllersVerifierInl.java#L191)

```java
  // verify response type
  StringVerifierInl.belongsToEnum(
    "response type",
    controllerJson.response_type,
    ResponseBody.ResponseType.class);
```

### [formatting](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content/formatting)

| class | explanation |
| ----- | ----------- |
| [CodeIdentifiersFormatterInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/CodeIdentifiersFormatterInl.java) | formats code identifiers (e.g.: CamelCase, lower_under, ...) |
| [EncodingInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/EncodingInl.java) | handles encoding/decoding data (e.g.: used to store/get blobs into/from cassandra) |
| [HashingInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/HashingInl.java) | formats an object or a group of objects into their hash value |
| [JavaFormatterInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/JavaFormatterInl.java) | does operations like formatting package names/paths and reformats code/comment line length to maintain coding legend (e.g.: that's how generated service's code line's length doesn't exceed 80 characters) |
| [PhrictionFormatterInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/PhrictionFormatterInl.java) | formats strings according to [phriction remarkup](https://secure.phabricator.com/book/phabricator/article/remarkup/) (e.g.: bold, header, table, link, ...); used to generate cassandra's phriction wiki |
| [SerializationInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/SerializationInl.java) | handles serializing/deserializing objects (e.g.: used by the built-in dispatcher-worker to serialize objects on the dispatcher side then deserialize them on the worker side for execution) |

#### [CodeIdentifiersFormatterInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/CodeIdentifiersFormatterInl.java) usage examples

+ using [`camelCase`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/CodeIdentifiersFormatterInl.java#L178) in [ControllersGeneratorInl: `generateControllerClass`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/controllers_generator/ControllersGeneratorInl.java#L769)

```java
  String nameCamelCase =
    CodeIdentifiersFormatterInl.camelCase(true, controllerJson.name);
  // first param `true` to remove underscores (if any)
```

+ using [`lower_under`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/CodeIdentifiersFormatterInl.java#L72) in [ControllersGeneratorInl: `generateControllerClass`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/controllers_generator/ControllersGeneratorInl.java#L771)

```java
  String nameLowerUnder =
    CodeIdentifiersFormatterInl.lowerUnder(controllerJson.name);
```

+ [CodeIdentifiersFormatterInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/CodeIdentifiersFormatterInl.java) also provides [`lowerCamelCase`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/CodeIdentifiersFormatterInl.java#L238), [`kCamelCase`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/CodeIdentifiersFormatterInl.java#L281), [`UPPER_UNDER`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/CodeIdentifiersFormatterInl.java#L311) and [`UPPER`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/CodeIdentifiersFormatterInl.java#L342) formatting methods

#### [EncodingInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/EncodingInl.java) usage examples

+ using [`encodeStringIntoByteBuffer`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/EncodingInl.java#L71) in [HandlerPostPhoto: `processRequest`](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/post_photo/HandlerPostPhoto.java#L192)

```java
  ByteBuffer photoByteBuffer =
    EncodingInl.encodeStringIntoByteBuffer(requestPostPhoto.photo);
```

+ using [`decodeStringFromByteBuffer`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/EncodingInl.java#L86) in [HandlerGetPhoto: `processRequest`](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/get_photo/HandlerGetPhoto.java#L140)

```java
  // decode photo
  String photoString =
    EncodingInl.decodeStringFromByteBuffer(photoByteBuffer);
```

#### [JavaFormatterInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/JavaFormatterInl.java) usage examples

+ using [`getPathToPackage`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/JavaFormatterInl.java#L76) in [JavaClientGeneratorInl: `generateCassandraJavaClient`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/keyspaces_generator/JavaClientGeneratorInl.java#L114)

```java
  String pathToPackage =
    JavaFormatterInl.getPathToPackage(
      projectDirPath,
      JavaFormatterInl.kPlaySrcDirName,
      rootPackage,
      projectName,
      CassandraGeneratorConstantsInl.kCassandraPackageName);
  
  // the last param represents `String... morePackages`
```

+ using [`getPackageName`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/JavaFormatterInl.java#L126) in [JavaClientGeneratorInl: `generateCassandraJavaClient`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/keyspaces_generator/JavaClientGeneratorInl.java#L122)

```java
  String packageName =
    JavaFormatterInl.getPackageName(
      rootPackage,
      projectName,
      CassandraGeneratorConstantsInl.kCassandraPackageName);
      
  // the last param represents `String... morePackages`
```

+ using [`formatStringLength`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/JavaFormatterInl.java#L164) in [JavaClientGeneratorInl: `generateTable`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/keyspaces_generator/JavaClientGeneratorInl.java#L488)

```java
  stringBuffer.append(
    String.format(
      kTableClassCommentFormat,
      tableNameCamelCase,
      tableJson.name,
      keyspaceName,
      tableJson.name,
      JavaFormatterInl.formatStringLength( // <<
        tableJson.description, // <<
        " *   ", // << new line prefix
        false), // << not a String-variable
      getTableColumnsComment(tableJson),
      getTablePartitionKeysComment(tableJson),
      getTableSecondaryKeysComment(tableJson),
      CodeIdentifiersFormatterInl.upper(tableJson.caching),
      getTableOrderByComment(tableJson),
      getTableQueryComment(tableJson) ) );
```

#### [PhrictionFormatterInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/PhrictionFormatterInl.java) usage examples

+ using [`largeHeader`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/PhrictionFormatterInl.java#L205) and [`bold`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/PhrictionFormatterInl.java#L71) in [PhrictionGeneratorInl: `generateCassandraKeyspacePhriction`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/keyspaces_generator/PhrictionGeneratorInl.java#L135)

```java
  // keyspace name
  phrictionBuffer.append(
    PhrictionFormatterInl.largeHeader(
      PhrictionFormatterInl.bold(
        "Keyspace: " + keyspaceJson.name) ) );
```

+ [PhrictionFormatterInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/PhrictionFormatterInl.java) also provides [`link`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/PhrictionFormatterInl.java#L185), [`bulletList`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/PhrictionFormatterInl.java#L253), [`tableRow`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/PhrictionFormatterInl.java#L361) and 13 other phiriction formatting methods

#### [SerializationInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/SerializationInl.java) usage examples

+ using [`serializeObject`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/SerializationInl.java#L73) in [HandlerPostPhoto: `processRequest`](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/post_photo/HandlerPostPhoto.java#L156)

```java
  // serialize job
  String jobSerialized =
    SerializationInl.serializeObject(job);
```

+ using [`deserializeObject`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/SerializationInl.java#L94) in [RestJobs: `process`](https://github.com/vangav/vos_instagram_jobs/blob/master/app/com/vangav/vos_instagram_jobs/periodic_jobs/rest_jobs/RestJobs.java#L162)

```java
  // deserialize
  currJob = SerializationInl.<Job>deserializeObject(currSerializedJob);
```

### [generation](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content/generation)

| class | explanation |
| ----- | ----------- |
| [RandomGeneratorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/generation/RandomGeneratorInl.java) | generates random strings, emails, ...; one usage is for generating random user data when stress-testing a service with a larger number of simulated users |

# next tutorial -> [data structures and algorithms](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/data_structures_and_algorithms)
> has data structures (e.g.: heap, kd-tree, tuples, ...) and algorithms (collections, matricies, strings, arrays, ...)

# share

[![facebook share](https://www.prekindle.com/images/social/facebook.png)](https://www.facebook.com/sharer/sharer.php?u=https%3A//github.com/vangav/vos_backend)  [![twitter share](http://www.howickbaptist.org.nz/wordpress/media/twitter-64-black.png)](https://twitter.com/home?status=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster%20%7C%20https%3A//github.com/vangav/vos_backend)  [![pinterest share](http://d7ab823tjbf2qywyt3grgq63.wpengine.netdna-cdn.com/wp-content/themes/velominati/images/share_icons/pinterest-black.png)](https://pinterest.com/pin/create/button/?url=https%3A//github.com/vangav/vos_backend&media=https%3A//scontent-mad1-1.xx.fbcdn.net/v/t31.0-8/20645143_1969408006608176_5289565717021239224_o.png?oh=acf20113a3673409d238924cfec648d2%26oe=5A3414B5&description=)  [![google plus share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/google.png)](https://plus.google.com/share?url=https%3A//github.com/vangav/vos_backend)  [![linkedin share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/linkedin.png)](https://www.linkedin.com/shareArticle?mini=true&url=https%3A//github.com/vangav/vos_backend&title=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster&summary=&source=)

# free consulting

[![vangav's consultant](http://www.footballhighlights247.com/images/mobile-share/fb-messenger-64x64.png)](https://www.facebook.com/mustapha.abdallah)
