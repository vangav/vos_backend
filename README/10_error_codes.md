# Error Codes

+ Each package has an error code as follows:

| code | package |
| ---- | ------- |
| 21 | [cassandra](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/cassandra) |
| 22 | [compression](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/compression) |
| 23 | [content](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content) |
| 31 | [data_structures_and_algorithms](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/data_structures_and_algorithms) |
| 32 | [dispatcher](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/dispatcher) |
| 41 | [exceptions](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/exceptions) |
| 51 | [files](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/files) |
| 61 | [geo](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo) |
| 81 | [ids](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/ids) |
| 82 | [images](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/images) |
| 121 | [math](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/math) |
| 122 | [metrics](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/metrics) |
| 131 | [networks](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks) |
| 151 | [play_framework](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/play_framework) |
| 152 | [properties](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/properties) |
| 153 | [public_apis](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/public_apis) |
| 154 | [push_notifications](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications) |
| 181 | [security](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security) |
| 182 | [system](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/system) |
| 191 | [thread_pool](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/thread_pool) |
| 211 | [vangav_m](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/vangav_m) |

| code | sub-code | explanation |
| ---- | -------- | ----------- |
| [cassandra](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/cassandra) | | |
| | [Cassandra](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/Cassandra.java) | |
| 21 | 1 | Couldn't prepare prepared statement |
| 21 | 2 | Couldn't execute query statement |
| | [CassandraProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/CassandraProperties.java) | |
| 21 | 3 | Invalid multi-deployment topology |
| 21 | 4 | Invalid deployment mode |
| | [CassandraVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/keyspaces_generator/CassandraVerifierInl.java) | |
| 21 | 5 | Can't have more than one keyspace with the same name. |
| 21 | 6 | A keyspace must have at least one replication method. |
| 21 | 7 | Can't have more than one table with the same name. |
| 21 | 8 | Duplicate column-name within one table. |
| 21 | 9 | A table's partition key must be one of its columns. |
| 21 | 10 | A table's compound partition-keys can't have duplicate columns. |
| 21 | 11 | A table's secondary key must be one of its columns. |
| 21 | 12 | A table's compound secondary-keys can't have duplicate columns. |
| 21 | 13 | A table can't have order-by without having secondary keys. |
| 21 | 14 | A table's order-by columns must be in the table's secondary keys. |
| 21 | 15 | A table compound order-by can't have duplicate columns. |
| [compression](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/compression) | | |
| | [LempelZivWelchInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/compression/LempelZivWelchInl.java) | |
| 22 | 1 | Invalid compression. |
| [content](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content) | | |
| | [CassandraCqlVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/CassandraCqlVerifierInl.java) | |
| 23 | 1 | A CQL name must have a length between 1 and 32 (inclusive). |
| 23 | 2 | A CQL name must start with a letter or an underscore. |
| 23 | 3 | A CQL name can consist only of letters, digits and underscores. |
| | [JavaCodeVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/JavaCodeVerifierInl.java) | |
| 23 | 4 | A JAVA identifier must start with a letter or an underscore. |
| 23 | 5 | A JAVA identifier can consist only of letters, digits and underscores. |
| 23 | 6 | A JAVA individual package name must start with a letter or an underscore. |
| 23 | 7 | A JAVA package name/path can consist only of letters, digits and underscores. |
| | [StringVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/StringVerifierInl.java) | |
| 23 | 8 | Null String isn't one of the valid values. |
| 23 | 9 | String isn't one of the valid values. |
| 23 | 10 | String isn't one of the enum values. |
| [dispatcher](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/dispatcher) | | |
| | [Dispatcher](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java) | |
| 32 | 1 | Property workers_topology isn't defined in the dispatcher_properties.prop file. |

| .. | . | .......... | .... |
| ~~ | ~ | ~~~~~~~~~~ | ~~~~ |
| 41 | 1 | ArgumentsInl | Wrong class type. |
| 41 | 2 | ArgumentsInl | Object can't be null. |
| 41 | 3 | ArgumentsInl | String can't be empty. |
| 41 | 4 | ArgumentsInl | Object array can't be empty. |
| 41 | 5 | ArgumentsInl | Short array can't be empty. |
| 41 | 6 | ArgumentsInl | Int array can't be empty. |
| 41 | 7 | ArgumentsInl | Long array can't be empty. |
| 41 | 8 | ArgumentsInl | Float array can't be empty. |
| 41 | 9 | ArgumentsInl | Double array can't be empty. |
| 41 | 10 | ArgumentsInl | Generic collection can't be empty. |
| 41 | 11 | ArgumentsInl | Generic map can't be empty. |
| 41 | 12 | ArgumentsInl | Int can't be out of range. |
| 41 | 13 | ArgumentsInl | Int can't be less than minimum limit. |
| 41 | 14 | ArgumentsInl | Long can't be less than minimum limit. |
| 41 | 15 | ArgumentsInl | Double can't be NaN or infinite. |
| 41 | 16 | ArgumentsInl | Invalid IpV4. |
| .. | .. | ............ | .... |
| ~~ | ~~ | ~~~~~~~~~~~~ | ~~~~ |
| 61 | 1 | EarthConstantsInl | Can't initialize Earth's latitude range. |
| 61 | 2 | EarthConstantsInl | Can't initialize Earth's longitude range. |
| .. | . | ................. | .... |
| 61 | 3 | GeoGrid | Can't get distance between two GeoGrid Objects with different configuration. |
| 61 | 4 | GeoGrid | Can't get center-distance between two GeoGrid Objects with different configuration. |
| 61 | 5 | GeoGrid | Can't get the line segment connecting two GeoGrid Objects with different configuration. |
| 61 | 6 | GeoGrid | Can't get surrounding grids for an invalid grid. |
| .. | . | ....... | .... |
| 61 | 7 | GeoGridsConfig | Grid dimension, map width and map length must all have the same unit. |
| 61 | 8 | GeoGridsConfig | Grid dimension can't be greater than the map's width or length. |
| 61 | 9 | GeoGridsConfig | Invalid latitude range. |
| 61 | 10 | GeoGridsConfig | Invalid longitude range. |
| 61 | 11 | GeoGridsConfig | Latitude gaps can't be smaller than 1. |
| 61 | 12 | GeoGridsConfig | Longitude gaps can't be smaller than 1. |
| .. | .. | .............. | .... |
| ~~ | ~~ | ~~~~~~~~~~~~~~ | ~~~~ |
| 81 | 1 | SequentialIds | Can't generate more than 100 million new IDs within one second. |
| .. | . | ............. | .... |
| 81 | 2 | SnowFlake | machineId can't be greater than maxMachineId. |
| 81 | 3 | SnowFlake | Can't generate a new id because the clock moved backwards which would result in an out of sequence id. |
| .. | . | ......... | .... |
| ~~ | ~ | ~~~~~~~~~ | ~~~~ |
| 82 | 1 | Image | Can't apply features, segment the image first. |
| .. | . | ..... | .... |
| ~~ | ~ | ~~~~~ | ~~~~ |
| 121 | 1 | NumbersInl | The number of digits in the input long exceeds the length of the output string. |
| ... | . | .......... | .... |
| 121 | 2 | Range | A range's max can't be smaller than its min. |
| ... | . | ..... | .... |
| ~~~ | ~ | ~~~~~ | ~~~~ |
| 122 | 1 | Distance | Invalid: subtracting double value from Distance will lead to a negative Distance. |
| 122 | 2 | Distance | Invalid: subtracting two Distance Objects will lead to a negative Distance. |
| ... | . | ........ | .... |
| 122 | 3 | DistanceConversionFactorInl | Invalid enum DistanceUnitType. |
| 122 | 4 | DistanceConversionFactorInl | Invalid enum DistanceUnitType, no conversion factor. |
| ... | . | .................... | .... |
| 122 | 5 | Period | A period can't have a negative value. |
| 122 | 6 | Period | Can't set a period with a negative value. |
| 122 | 7 | Period | Invalid: subtracting double from period will lead to a negative period. |
| 122 | 8 | Period | Invalid: subtracting two periods will lead to a negative period. |
| 122 | 9 | TimeConversionFactorInl | Invalid enum TimeUnitType. |
| 122 | 10 | TimeConversionFactorInl | Invalid enum TimeUnitType, no conversion factor. |
| ... | .. | ................ | .... |
| ~~~ | ~~ | ~~~~~~~~~~~~~~~~ | ~~~~ |
| 131 | 1 | JavaEmailSenderInl | Unknown SslType. |
| ... | . | .................. | .... |
| ~~~ | ~ | ~~~~~~~~~~~~~~~~~~ | ~~~~ |
| 151 | 1 | ControllersVerifierInl | Can't have more than one controller with the same name. |
| 151 | 2 | ControllersVerifierInl | A controller can't have more than one request param with the same name. |
| 151 | 3 | ControllersVerifierInl | A controller can't have more than one response param with the same name. |
| ... | . | ...................... | .... |
| 151 | 4 | ParamParsersInl | Date doesn't follow any of the supported formats. |
| ... | . | ............... | .... |
| 151 | 5 | ParamType | Unhandled ParamType. |
| ... | . | ......... | .... |
| 151 | 6 | ParamValidatorInl | Unhandled ParamType. |
| 151 | 7 | ParamValidatorInl | Invalid param. |
| ... | . | ................. | .... |
| 151 | 8 | RequestJsonBody | Unhandled request type. |
| ... | . | ............... | .... |
| 151 | 9 | RequestJsonBodyGet | Invalid call for fromJsonString. |
| 151 | 10 | RequestJsonBodyGet | Unhandled primitive type. |
| ... | .. | .................. | .... |
| 151 | 11 | RequestJsonBodyPost | Invalid call for fromQueryString. |
| ... | .. | ................... | .... |
| 151 | 12 | ResponseBody | Unhandled ResponseBody type. |
| ... | .. | ............ | .... |
| ~~~ | ~~ | ~~~~~~~~~~~~ | ~~~~ |
| 152 | 1 | PropertiesLoader | Missing properties-file's value. |
| ... | . | ................ | .... |
| ~~~ | ~ | ~~~~~~~~~~~~~~~~ | ~~~~ |
| 153 | 1 | Car2GoApi | Invalid EdgeType. |
| 153 | 2 | Car2GoApi | Invalid EdgeType. |
| 153 | 3 | Car2GoApi | Invalid request-tracking-id. |
| 153 | 4 | Car2GoApi | Unhandled RequestType. |
| ... | . | ......... | .... |
| 153 | 5 | LocationType | Invalid LocationType. |
| ... | . | ............ | .... |
| 153 | 6 | FacebookGraph | Wrong facebook access token. |
| 153 | 7 | FacebookGraph | Facebook Graph API returned 400 BAD_REQUEST. |
| 153 | 8 | FacebookGraph | Invalid request-tracking-id. |
| 153 | 9 | FacebookGraph | Couldn't get user's profile picture, async. |
| 153 | 10 | FacebookGraph | Couldn't get user's profile picture, sync. |
| 153 | 11 | FacebookGraph | Unhandled RequestType for getting user's profile picture. |
| 153 | 12 | FacebookGraph | Invalid request-tracking-id. |
| 153 | 13 | FacebookGraph | Unhandled RequestType for getting user's pictures. |
| 153 | 14 | FacebookGraph | Invalid request-tracking-id. |
| 153 | 15 | FacebookGraph | Unhandled RequestType for get-fields. |
| 153 | 16 | FacebookGraph | Invalid request-tracking-id. |
| 153 | 17 | FacebookGraph | Unhandled RequestType for get-edges. |
| ... | .. | ............. | .... |
| 153 | 18 | FacebookGraphApiFieldType | Unhandled field type. |
| ... | .. | ......................... | .... |
| 153 | 19 | FacebookGraphApiEdgeType | Unhandled edge type. |
| ... | .. | ........................ | .... |
| ~~~ | ~~ | ~~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~ |
| 154 | 1 | AndroidNotificationSender | Singleton instance can't be used because the values in android_notification_properties.prop aren't defined. |
| ... | . | ......................... | .... |
| 154 | 2 | AppleNotificationSender | Invalid ClientType. |
| 154 | 3 | AppleNotificationSender | Singleton instance can't be used because the values in apple_notification_properties.prop aren't defined. |
| ... | . | ....................... | .... |
| ~~~ | ~ | ~~~~~~~~~~~~~~~~~~~~~~~ | ~~~~ |
| 181 | 1 | FacebookAuthInl | Error while communicating with Facebook Graph API. Didn't return 200 HTTP_OK. |
| 181 | 2 | FacebookAuthInl | Facebook-auth returned null app id. |
| 181 | 3 | FacebookAuthInl | Facebook-auth returned different app id. |
| 181 | 4 | FacebookAuthInl | Error while communicating with Facebook Graph API. |
| ... | . | ............... | .... |
| 181 | 5 | GoogleAuthInl | Error while communicating with Google API. Got 500 INTERNAL_ERROR. |
| 181 | 6 | GoogleAuthInl | Invalid google-id-token. |
| 181 | 7 | GoogleAuthInl | Google-auth return null app id. |
| 181 | 8 | GoogleAuthInl | Google-auth returned different app id. |
| 181 | 9 | GoogleAuthInl | Error while communicating with Google API. |
| ... | . | ............. | .... |
| 181 | 10 | TransactionTokensGeneratorInl | Unhandled TokenType. |
| ... | .. | ............................. | .... |
| 181 | 11 | PasswordHashingInl | Invalid SHA algorithm type. |
| ... | .. | .................. | .... |
| ~~~ | ~~ | ~~~~~~~~~~~~~~~~~~ | ~~~~ |
| 211 | 1 | VangavMSolutionJson | Vangav M client built-in in Vangav Backend supports JAVA_JAR solutions' type only. Refer for other clients on [vangav](www.vangav.com) for clients that support other languages. |
| 211 | 2 | VangavMSolutionJson | Can't have duplicate multi-range-input prefix. |
| 211 | 3 | VangavMSolutionJson | Duplicate input name. |
| 211 | 4 | VangavMSolutionJson | Input's range is too narrow. |
| 211 | 5 | VangavMSolutionJson | Multi-range-inputs have no corresponding input-names. |
| 211 | 6 | VangavMSolutionJson | Finite input doesn't belong to solution's inputs. |
| 211 | 7 | VangavMSolutionJson | Can't have duplicate multi-range-output prefix. |
| 211 | 8 | VangavMSolutionJson | Duplicate output name. |
| 211 | 9 | VangavMSolutionJson | Output's range is too narrow. |
| 211 | 10 | VangavMSolutionJson | Multi-range-outputs have no corresponding output-names. |
| 211 | 11 | VangavMSolutionJson | Relation's input name doesn't belong to the solution's inputs. |
| 211 | 12 | VangavMSolutionJson | Relation's output name doesn't belong to the solution's outputs. |
| 211 | 13 | VangavMSolutionJson | Relation's relative-weight is greater than the maximum possible value. |
| 211 | 14 | VangavMSolutionJson | Relative relation's input doesn't belong to the solution's inputs. |
| 211 | 15 | VangavMSolutionJson | A relative relation can't have the same key for max_output and min_output. |
| 211 | 16 | VangavMSolutionJson | Relative relation's max_output doesn't belong to any of the solution's outputs. |
| 211 | 17 | VangavMSolutionJson | Relative relation's min_output doesn't belong to any of the solution's outputs. |
