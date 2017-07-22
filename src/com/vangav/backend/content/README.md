
> **why content checking?** this sub-package is needed for verifying vangav backend generator config files and vangav mighty solution files

> **why content formatting?** this sub-package is needed for formatting generated java code, cql scripts and phriction wiki files

> **why content generation?** generating random content (strings, numbers, emails, ...) are useful in various cases like generating test data, security tokens, ...

# content

### [checking](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content/checking)

| class | explanation |
| ----- | ----------- |
| [CassandraCqlVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/CassandraCqlVerifierInl.java) | verifies the correctness of cannasandra's cql |
| [CharVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/CharVerifierInl.java) | verifies characters' values |
| [JavaCodeVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/JavaCodeVerifierInl.java) | varifies java code (e.g.: identifier, package name, ...) |
| [StringVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/StringVerifierInl.java) | verifies a string's value (e.g.: belongs to an enum) |

### [formatting](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content/formatting)

| class | explanation |
| ----- | ----------- |
| [CodeIdentifiersFormatterInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/CodeIdentifiersFormatterInl.java) | formats code identifiers (e.g.: CamelCase, lower_under, ...) |
| [EncodingInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/EncodingInl.java) | handles encoding/decoding data (e.g.: used to store/get blobs into/from cassandra) |
| [HashingInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/HashingInl.java) | formats an object or a group of objects into their hash value |
| [JavaFormatterInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/JavaFormatterInl.java) | does operations like formatting package names/paths and reformats code/comment line length to maintain coding legend (e.g.: that's how generated service's code line's length doesn't exceed 80 characters) |
| [PhrictionFormatterInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/PhrictionFormatterInl.java) | formats strings according to [phriction remarkup](https://secure.phabricator.com/book/phabricator/article/remarkup/) (e.g.: bold, header, table, link, ...); used to generate cassandra's phriction wiki |
| [SerializationInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/SerializationInl.java) | handles serializing/deserializing objects (e.g.: used by the built-in dispatcher-worker to serialize objects on the dispatcher side then deserialize them on the worker side for execution) |

### [generation](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content/generation)

| class | explanation |
| ----- | ----------- |
| [RandomGeneratorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/generation/RandomGeneratorInl.java) | generates random strings, emails, ...; one usage is for generating random user data when stress-testing a service with a larger number of simulated users |
