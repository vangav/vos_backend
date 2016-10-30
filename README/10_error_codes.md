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

+ Each exception (error) thrown by Vangav Backend has a code and a sub-code as follows:

| code | sub-code | explanation |
| ---- | -------- | ----------- |
| **[cassandra](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/cassandra)** | | |
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
| **[compression](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/compression)** | | |
| | [LempelZivWelchInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/compression/LempelZivWelchInl.java) | |
| 22 | 1 | Invalid compression. |
| **[content](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content)** | | |
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
| **[dispatcher](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/dispatcher)** | | |
| | [Dispatcher](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java) | |
| 32 | 1 | Property workers_topology isn't defined in the dispatcher_properties.prop file. |
| **[exceptions](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/exceptions)** | | |
| | [](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/handlers/ArgumentsInl.java) | |
| 41 | 1 | Wrong class type. |
| 41 | 2 | Object can't be null. |
| 41 | 3 | String can't be empty. |
| 41 | 4 | Object array can't be empty. |
| 41 | 5 | Short array can't be empty. |
| 41 | 6 | Int array can't be empty. |
| 41 | 7 | Long array can't be empty. |
| 41 | 8 | Float array can't be empty. |
| 41 | 9 | Double array can't be empty. |
| 41 | 10 | Generic collection can't be empty. |
| 41 | 11 | Generic map can't be empty. |
| 41 | 12 | Int can't be out of range. |
| 41 | 13 | Int can't be less than minimum limit. |
| 41 | 14 | Long can't be less than minimum limit. |
| 41 | 15 | Double can't be NaN or infinite. |
| 41 | 16 | Invalid IpV4. |
| **[geo](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo)** | | |
| | [EarthConstantsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/EarthConstantsInl.java) | |
| 61 | 1 | Can't initialize Earth's latitude range. |
| 61 | 2 | Can't initialize Earth's longitude range. |
| | [GeoGrid](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java) | |
| 61 | 3 | Can't get distance between two GeoGrid Objects with different configuration. |
| 61 | 4 | Can't get center-distance between two GeoGrid Objects with different configuration. |
| 61 | 5 | Can't get the line segment connecting two GeoGrid Objects with different configuration. |
| 61 | 6 | Can't get surrounding grids for an invalid grid. |
| | [GeoGridsConfig](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGridsConfig.java) | |
| 61 | 7 | Grid dimension, map width and map length must all have the same unit. |
| 61 | 8 | Grid dimension can't be greater than the map's width or length. |
| 61 | 9 | Invalid latitude range. |
| 61 | 10 | Invalid longitude range. |
| 61 | 11 | Latitude gaps can't be smaller than 1. |
| 61 | 12 | Longitude gaps can't be smaller than 1. |
| **[ids](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/ids)** | | |
| | [SequentialIds](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SequentialIds.java) | |
| 81 | 1 | Can't generate more than 100 million new IDs within one second. |
| | [SnowFlake](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SnowFlake.java) | |
| 81 | 2 | machineId can't be greater than maxMachineId. |
| 81 | 3 | Can't generate a new id because the clock moved backwards which would result in an out of sequence id. |
| **[images](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/images)** | | |
| | [Image](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/images/Image.java) | |
| 82 | 1 | Can't apply features, segment the image first. |
| **[math](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/math)** | | |
| | [NumbersInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/math/NumbersInl.java) | |
| 121 | 1 | The number of digits in the input long exceeds the length of the output string. |
| | [Range](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/math/Range.java) | |
| 121 | 2 | A range's max can't be smaller than its min. |
| **[metrics](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/metrics)** | | |
| | [Distance](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/distance/Distance.java) | |
| 122 | 1 | Invalid: subtracting double value from Distance will lead to a negative Distance. |
| 122 | 2 | Invalid: subtracting two Distance Objects will lead to a negative Distance. |
| | [DistanceConversionFactorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/distance/DistanceConversionFactorInl.java) | |
| 122 | 3 | Invalid enum DistanceUnitType. |
| 122 | 4 | Invalid enum DistanceUnitType, no conversion factor. |
| | [Period](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/Period.java) | |
| 122 | 5 | A period can't have a negative value. |
| 122 | 6 | Can't set a period with a negative value. |
| 122 | 7 | Invalid: subtracting double from period will lead to a negative period. |
| 122 | 8 | Invalid: subtracting two periods will lead to a negative period. |
| | [TimeConversionFactorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/TimeConversionFactorInl.java) | |
| 122 | 9 | Invalid enum TimeUnitType. |
| 122 | 10 | Invalid enum TimeUnitType, no conversion factor. |
| **[networks](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks)** | | |
| | [JavaEmailSenderInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/java_email/JavaEmailSenderInl.java) | |
| 131 | 1 | Unknown SslType. |
| **[play_framework](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/play_framework)** | | |
| | [ControllersVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/controllers_generator/ControllersVerifierInl.java) | |
| 151 | 1 | Can't have more than one controller with the same name. |
| 151 | 2 | A controller can't have more than one request param with the same name. |
| 151 | 3 | A controller can't have more than one response param with the same name. |
| | [ParamParsersInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamParsersInl.java) | |
| 151 | 4 | Date doesn't follow any of the supported formats. |
| | [ParamType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java) | |
| 151 | 5 | Unhandled ParamType. |
| | [ParamValidatorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java) | |
| 151 | 6 | Unhandled ParamType. |
| 151 | 7 | Invalid param. |
| | [RequestJsonBody](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java) | |
| 151 | 8 | Unhandled request type. |
| | [RequestJsonBodyGet](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBodyGet.java) | |
| 151 | 9 | Invalid call for fromJsonString. |
| 151 | 10 | Unhandled primitive type. |
| | [RequestJsonBodyPost](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBodyPost.java) | |
| 151 | 11 | Invalid call for fromQueryString. |
| | [ResponseBody](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBody.java) | |
| 151 | 12 | Unhandled ResponseBody type. |
| **[properties](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/properties)** | | |
| | [PropertiesLoader](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/properties/PropertiesLoader.java) | |
| 152 | 1 | Missing properties-file's value. |
| **[public_apis](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/public_apis)** | | |
| | [Car2GoApi](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/car2go/Car2GoApi.java) | |
| 153 | 1 | Invalid EdgeType. |
| 153 | 2 | Invalid EdgeType. |
| 153 | 3 | Invalid request-tracking-id. |
| 153 | 4 | Unhandled RequestType. |
| | [LocationType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/car2go/LocationType.java) | |
| 153 | 5 | Invalid LocationType. |
| | [FacebookGraph](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/FacebookGraph.java) | |
| 153 | 6 | Wrong facebook access token. |
| 153 | 7 | Facebook Graph API returned 400 BAD_REQUEST. |
| 153 | 8 | Invalid request-tracking-id. |
| 153 | 9 | Couldn't get user's profile picture, async. |
| 153 | 10 | Couldn't get user's profile picture, sync. |
| 153 | 11 | Unhandled RequestType for getting user's profile picture. |
| 153 | 12 | Invalid request-tracking-id. |
| 153 | 13 | Unhandled RequestType for getting user's pictures. |
| 153 | 14 | Invalid request-tracking-id. |
| 153 | 15 | Unhandled RequestType for get-fields. |
| 153 | 16 | Invalid request-tracking-id. |
| 153 | 17 | Unhandled RequestType for get-edges. |
| | [FacebookGraphApiFieldType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/json/fields/FacebookGraphApiFieldType.java) | |
| 153 | 18 | Unhandled field type. |
| | [FacebookGraphApiEdgeType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/json/edges/FacebookGraphApiEdgeType.java) | |
| 153 | 19 | Unhandled edge type. |
| **[push_notifications](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications)** | | |
| | [AndroidNotificationSender](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/android/AndroidNotificationSender.java) | |
| 154 | 1 | Singleton instance can't be used because the values in android_notification_properties.prop aren't defined. |
| | [AppleNotificationSender](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/apple/AppleNotificationSender.java) | |
| 154 | 2 | Invalid ClientType. |
| 154 | 3 | Singleton instance can't be used because the values in apple_notification_properties.prop aren't defined. |
| **[security](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security)** | | |
| | [FacebookAuthInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/facebook/FacebookAuthInl.java) | |
| 181 | 1 | Error while communicating with Facebook Graph API. Didn't return 200 HTTP_OK. |
| 181 | 2 | Facebook-auth returned null app id. |
| 181 | 3 | Facebook-auth returned different app id. |
| 181 | 4 | Error while communicating with Facebook Graph API. |
| | [GoogleAuthInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/google/GoogleAuthInl.java) | |
| 181 | 5 | Error while communicating with Google API. Got 500 INTERNAL_ERROR. |
| 181 | 6 | Invalid google-id-token. |
| 181 | 7 | Google-auth return null app id. |
| 181 | 8 | Google-auth returned different app id. |
| 181 | 9 | Error while communicating with Google API. |
| | [TransactionTokensGeneratorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/transaction_tokens/TransactionTokensGeneratorInl.java) | |
| 181 | 10 | Unhandled TokenType. |
| | [PasswordHashingInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/cryptography/PasswordHashingInl.java) | |
| 181 | 11 | Invalid SHA algorithm type. |
| **[vangav_m](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/vangav_m)** | | |
| | [VangavMSolutionJson](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/vangav_m/json_client/VangavMJsonClientMain.java) | |
| 211 | 1 | Vangav M client built-in in Vangav Backend supports JAVA_JAR solutions' type only. Refer for other clients on [vangav](www.vangav.com) for clients that support other languages. |
| 211 | 2 | Can't have duplicate multi-range-input prefix. |
| 211 | 3 | Duplicate input name. |
| 211 | 4 | Input's range is too narrow. |
| 211 | 5 | Multi-range-inputs have no corresponding input-names. |
| 211 | 6 | Finite input doesn't belong to solution's inputs. |
| 211 | 7 | Can't have duplicate multi-range-output prefix. |
| 211 | 8 | Duplicate output name. |
| 211 | 9 | Output's range is too narrow. |
| 211 | 10 | Multi-range-outputs have no corresponding output-names. |
| 211 | 11 | Relation's input name doesn't belong to the solution's inputs. |
| 211 | 12 | Relation's output name doesn't belong to the solution's outputs. |
| 211 | 13 | Relation's relative-weight is greater than the maximum possible value. |
| 211 | 14 | Relative relation's input doesn't belong to the solution's inputs. |
| 211 | 15 | A relative relation can't have the same key for max_output and min_output. |
| 211 | 16 | Relative relation's max_output doesn't belong to any of the solution's outputs. |
| 211 | 17 | Relative relation's min_output doesn't belong to any of the solution's outputs. |
