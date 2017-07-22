
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

+ [CodeIdentifiersFormatterInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/CodeIdentifiersFormatterInl.java) also provides [`lowerCamelCase`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/CodeIdentifiersFormatterInl.java#L238), [`kCamelCase`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/CodeIdentifiersFormatterInl.java#L281), [`UPPER_UNDER`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/CodeIdentifiersFormatterInl.java#L311) and [`UPPER`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/CodeIdentifiersFormatterInl.java#L342)





### [generation](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content/generation)

| class | explanation |
| ----- | ----------- |
| [RandomGeneratorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/generation/RandomGeneratorInl.java) | generates random strings, emails, ...; one usage is for generating random user data when stress-testing a service with a larger number of simulated users |
