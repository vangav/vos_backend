# content

### [checking](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content/checking)

| Class | Explanation |
| ----- | ----------- |
| [CassandraCqlVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/CassandraCqlVerifierInl.java) | Verifies the correctness of Cannasandra's CQL. |
| [CharVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/CharVerifierInl.java) | Verifies character value. |
| [JavaCodeVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/JavaCodeVerifierInl.java) | Varifies JAVA code (e.g.: identifier, package name, ...). |
| [StringVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/StringVerifierInl.java) | Verifies a String's value (e.g.: belongs to an enum). |

### [formatting](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content/formatting)

| Class | Explanation |
| ----- | ----------- |
| [CodeIdentifiersFormatterInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/CodeIdentifiersFormatterInl.java) | Formats code identifiers (e.g.: CamelCase, lower_under, ...). |
| [HashingInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/HashingInl.java) | Formats an Object or a group of Objects into their hash value. |
| [JavaFormatterInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/JavaFormatterInl.java) | Does operations like formatting package names/paths and reformats code/comment line length to maintain coding legend (e.g.: that's how generated service's code line's length doesn't exceed 80 characters). |
| [PhrictionFormatterInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/PhrictionFormatterInl.java) | Formats Strings according to [phriction remarkup](https://secure.phabricator.com/book/phabricator/article/remarkup/) (e.g.: bold, header, table, link, ...). Used to generate Cassandra's phriction wiki. |
| [SerializationInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/formatting/SerializationInl.java) | Handles serializing/deserializing Objects (e.g.: used by the built-in dispatcher-worker to serialize Objects on the dispatcher side then deserialize them on the worker side for execution). |

### [generation](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content/generation)

| Class | Explanation |
| ----- | ----------- |
| [RandomGeneratorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/generation/RandomGeneratorInl.java) | Generates random strings, emails, .... One usage is for generating random user data when stress-testing a service with a larger number of simulated users. |
