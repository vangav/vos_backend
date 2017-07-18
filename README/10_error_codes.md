# vangav backend error codes

+ vangav backend detects various types of errors; this tutorial lists all of vangav backend's error codes/sub-codes with reference to the code producing them for ease of tracing if you get one

+ when the service is running, these codes along with the exception class/type, message, stack trace and trace id are returned in an [error response](https://github.com/vangav/vos_backend/blob/master/README/06_error_response.md) json object

+ [code 0](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L189) is the default `VangavException` code (e.g.: when a thrown exception isn't given a code)
+ [code -1](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L86) is the default `ResponseBodyError` code (e.g.: when sending back error_code is set to `false` in [response_error_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/response_error_properties.prop#L69)).

> codes up to **299** are reserved for use by vangav backend, use codes **300** and up for your service's exceptions

+ each package has an error code as follows:

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

+ each exception (error) thrown by vangav backend has a code and a sub-code as follows:

| code | sub-code | explanation |
| ---- | -------- | ----------- |
| **[cassandra](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/cassandra)** | | |
| | [Cassandra](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/Cassandra.java) | |
| 21 | 1 | couldn't prepare prepared statement |
| 21 | 2 | couldn't execute query statement |
| | [CassandraProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/CassandraProperties.java) | |
| 21 | 3 | invalid multi-deployment topology |
| 21 | 4 | invalid deployment mode |
| | [CassandraVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/cassandra/keyspaces_generator/CassandraVerifierInl.java) | |
| 21 | 5 | can't have more than one keyspace with the same name |
| 21 | 6 | a keyspace must have at least one replication method |
| 21 | 7 | can't have more than one table with the same name |
| 21 | 8 | duplicate column-name within one table |
| 21 | 9 | a table's partition key must be one of its columns |
| 21 | 10 | a table's compound partition-keys can't have duplicate columns |
| 21 | 11 | a table's secondary key must be one of its columns |
| 21 | 12 | a table's compound secondary-keys can't have duplicate columns |
| **[compression](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/compression)** | | |
| | [LempelZivWelchInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/compression/LempelZivWelchInl.java) | |
| 22 | 1 | invalid compression |
| **[content](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content)** | | |
| | [CassandraCqlVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/CassandraCqlVerifierInl.java) | |
| 23 | 1 | a cql name must have a length between 1 and 32 (inclusive) |
| 23 | 2 | a cql name must start with a letter or an underscore |
| 23 | 3 | a cql name can consist only of letters, digits and underscores |
| | [JavaCodeVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/JavaCodeVerifierInl.java) | |
| 23 | 4 | a java identifier must start with a letter or an underscore |
| 23 | 5 | a java identifier can consist only of letters, digits and underscores |
| 23 | 6 | a java individual package name must start with a letter or an underscore |
| 23 | 7 | a java package name/path can consist only of letters, digits and underscores |
| | [StringVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/content/checking/StringVerifierInl.java) | |
| 23 | 8 | null string isn't one of the valid values |
| 23 | 9 | string isn't one of the valid values |
| 23 | 10 | string isn't one of the enum values |
| **[dispatcher](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/dispatcher)** | | |
| | [Dispatcher](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java) | |
| 32 | 1 | property workers_topology isn't defined in the dispatcher_properties.prop file |
| **[exceptions](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/exceptions)** | | |
| | [ArgumentsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/handlers/ArgumentsInl.java) | |
| 41 | 1 | wrong class type |
| 41 | 2 | object can't be null |
| 41 | 3 | string can't be empty |
| 41 | 4 | object array can't be empty |
| 41 | 5 | short array can't be empty |
| 41 | 6 | int array can't be empty |
| 41 | 7 | long array can't be empty |
| 41 | 8 | float array can't be empty |
| 41 | 9 | double array can't be empty |
| 41 | 10 | generic collection can't be empty |
| 41 | 11 | generic map can't be empty |
| 41 | 12 | int can't be out of range |
| 41 | 13 | int can't be less than minimum limit |
| 41 | 14 | long can't be less than minimum limit |
| 41 | 15 | double can't be NaN or infinite |
| 41 | 16 | invalid ip-v4 |
| **[geo](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo)** | | |
| | [EarthConstantsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/EarthConstantsInl.java) | |
| 61 | 1 | can't initialize earth's latitude range |
| 61 | 2 | can't initialize earth's longitude range |
| | [GeoGrid](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java) | |
| 61 | 3 | can't get distance between two GeoGrid objects with different configuration |
| 61 | 4 | can't get center-distance between two GeoGrid objects with different configuration |
| 61 | 5 | can't get the line segment connecting two GeoGrid objects with different configuration |
| 61 | 6 | can't get surrounding grids for an invalid grid |
| | [GeoGridsConfig](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGridsConfig.java) | |
| 61 | 7 | grid dimension, map width and map length must all have the same unit |
| 61 | 8 | grid dimension can't be greater than the map's width or length |
| 61 | 9 | invalid latitude range |
| 61 | 10 | invalid longitude range |
| 61 | 11 | latitude gaps can't be smaller than 1 |
| 61 | 12 | longitude gaps can't be smaller than 1 |
| **[ids](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/ids)** | | |
| | [SequentialIds](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SequentialIds.java) | |
| 81 | 1 | can't generate more than 100 million new ids within one second |
| | [SnowFlake](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SnowFlake.java) | |
| 81 | 2 | machineId can't be greater than maxMachineId |
| 81 | 3 | can't generate a new id because the clock moved backwards which would result in an out of sequence id |
| **[images](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/images)** | | |
| | [Image](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/images/Image.java) | |
| 82 | 1 | can't apply features, segment the image first |
| **[math](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/math)** | | |
| | [NumbersInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/math/NumbersInl.java) | |
| 121 | 1 | the number of digits in the input long exceeds the length of the output string |
| | [Range](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/math/Range.java) | |
| 121 | 2 | a range's max can't be smaller than its min |
| **[metrics](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/metrics)** | | |
| | [Distance](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/distance/Distance.java) | |
| 122 | 1 | invalid: subtracting double value from distance will lead to a negative distance |
| 122 | 2 | invalid: subtracting two distance objects will lead to a negative distance |
| | [DistanceConversionFactorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/distance/DistanceConversionFactorInl.java) | |
| 122 | 3 | invalid enum DistanceUnitType |
| 122 | 4 | invalid enum DistanceUnitType, no conversion factor |
| | [Period](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/Period.java) | |
| 122 | 5 | a period can't have a negative value |
| 122 | 6 | can't set a period with a negative value |
| 122 | 7 | invalid: subtracting double from period will lead to a negative period |
| 122 | 8 | invalid: subtracting two periods will lead to a negative period |
| | [TimeConversionFactorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/TimeConversionFactorInl.java) | |
| 122 | 9 | invalid enum TimeUnitType |
| 122 | 10 | invalid enum TimeUnitType, no conversion factor |
| **[networks](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/networks)** | | |
| | [JavaEmailSenderInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/networks/email/java_email/JavaEmailSenderInl.java) | |
| 131 | 1 | unknown ssl type |
| **[play_framework](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/play_framework)** | | |
| | [ControllersVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/controllers_generator/ControllersVerifierInl.java) | |
| 151 | 1 | can't have more than one controller with the same name |
| 151 | 2 | a controller can't have more than one request param with the same name |
| 151 | 3 | a controller can't have more than one response param with the same name |
| | [ParamParsersInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamParsersInl.java) | |
| 151 | 4 | date doesn't follow any of the supported formats |
| | [ParamType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java) | |
| 151 | 5 | unhandled ParamType |
| | [ParamValidatorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java) | |
| 151 | 6 | unhandled ParamType |
| 151 | 7 | invalid param |
| | [RequestJsonBody](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java) | |
| 151 | 8 | unhandled request type |
| | [RequestJsonBodyGet](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBodyGet.java) | |
| 151 | 9 | invalid call for fromJsonString |
| 151 | 10 | unhandled primitive type |
| | [RequestJsonBodyPost](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBodyPost.java) | |
| 151 | 11 | invalid call for fromQueryString |
| | [ResponseBody](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBody.java) | |
| 151 | 12 | unhandled ResponseBody type |
| **[properties](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/properties)** | | |
| | [PropertiesLoader](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/properties/PropertiesLoader.java) | |
| 152 | 1 | missing properties-file's value |
| **[public_apis](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/public_apis)** | | |
| | [Car2GoApi](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/car2go/Car2GoApi.java) | |
| 153 | 1 | invalid EdgeType |
| 153 | 2 | invalid EdgeType |
| 153 | 3 | invalid request-tracking-id |
| 153 | 4 | unhandled RequestType |
| | [LocationType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/car2go/LocationType.java) | |
| 153 | 5 | invalid LocationType |
| | [FacebookGraph](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/FacebookGraph.java) | |
| 153 | 6 | wrong facebook access token |
| 153 | 7 | facebook Graph API returned 400 BAD_REQUEST |
| 153 | 8 | invalid request-tracking-id |
| 153 | 9 | couldn't get user's profile picture, async |
| 153 | 10 | couldn't get user's profile picture, sync |
| 153 | 11 | unhandled RequestType for getting user's profile picture |
| 153 | 12 | invalid request-tracking-id |
| 153 | 13 | unhandled RequestType for getting user's pictures |
| 153 | 14 | invalid request-tracking-id |
| 153 | 15 | unhandled RequestType for get-fields |
| 153 | 16 | invalid request-tracking-id |
| 153 | 17 | unhandled RequestType for get-edges |
| | [FacebookGraphApiFieldType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/json/fields/FacebookGraphApiFieldType.java) | |
| 153 | 18 | unhandled field type |
| | [FacebookGraphApiEdgeType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/json/edges/FacebookGraphApiEdgeType.java) | |
| 153 | 19 | unhandled edge type |
| **[push_notifications](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/push_notifications)** | | |
| | [AndroidNotificationSender](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/android/AndroidNotificationSender.java) | |
| 154 | 1 | singleton instance can't be used because the values in android_notification_properties.prop aren't defined |
| | [AppleNotificationSender](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/push_notifications/apple/AppleNotificationSender.java) | |
| 154 | 2 | invalid ClientType |
| 154 | 3 | singleton instance can't be used because the values in apple_notification_properties.prop aren't defined |
| **[security](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security)** | | |
| | [FacebookAuthInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/facebook/FacebookAuthInl.java) | |
| 181 | 1 | error while communicating with Facebook Graph API. Didn't return 200 HTTP_OK. |
| 181 | 2 | facebook-auth returned null app id |
| 181 | 3 | facebook-auth returned different app id |
| 181 | 4 | error while communicating with facebook graph api |
| | [GoogleAuthInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/google/GoogleAuthInl.java) | |
| 181 | 5 | error while communicating with Google API. Got 500 INTERNAL_ERROR. |
| 181 | 6 | invalid google-id-token |
| 181 | 7 | google-auth return null app id |
| 181 | 8 | google-auth returned different app id |
| 181 | 9 | error while communicating with google api |
| | [TransactionTokensGeneratorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/transaction_tokens/TransactionTokensGeneratorInl.java) | |
| 181 | 10 | unhandled TokenType |
| | [PasswordHashingInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/cryptography/PasswordHashingInl.java) | |
| 181 | 11 | invalid sha algorithm type |
| **[vangav_m](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/vangav_m)** | | |
| | [VangavMSolutionJson](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/vangav_m/json_client/VangavMJsonClientMain.java) | |
| 211 | 1 | vangav mighty client built-in in vangav backend supports JAVA_JAR solutions' type only. Refer for other clients on [vangav](www.vangav.com) for clients that support other languages |
| 211 | 2 | can't have duplicate multi-range-input prefix |
| 211 | 3 | duplicate input name |
| 211 | 4 | input's range is too narrow |
| 211 | 5 | multi-range-inputs have no corresponding input-names |
| 211 | 6 | finite input doesn't belong to solution's inputs |
| 211 | 7 | can't have duplicate multi-range-output prefix |
| 211 | 8 | duplicate output name |
| 211 | 9 | output's range is too narrow |
| 211 | 10 | multi-range-outputs have no corresponding output-names |
| 211 | 11 | relation's input name doesn't belong to the solution's inputs |
| 211 | 12 | relation's output name doesn't belong to the solution's outputs |
| 211 | 13 | relation's relative-weight is greater than the maximum possible value |
| 211 | 14 | relative relation's input doesn't belong to the solution's inputs |
| 211 | 15 | a relative relation can't have the same key for max_output and min_output |
| 211 | 16 | relative relation's max_output doesn't belong to any of the solution's outputs |
| 211 | 17 | relative relation's min_output doesn't belong to any of the solution's outputs |
