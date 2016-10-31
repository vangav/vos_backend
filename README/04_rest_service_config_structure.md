# REST Service Config Structure

When using Vangav Backend to generate a REST service, adding config files is optional but highly recommended as it saves the majority of the cost needed to implement the REST service. Config consists of one mandatory **[controllers.json](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json)** file and zero-to-many database config files **[keyspace_name.keyspace](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace)** (one file per database keyspace).

+ All config files have a **JSON** structure, however in Vangav Backend JSON supports inline comments. A comment line is any lines starting with zero or more spaces followed by a #. **[Comment Example](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L2)**

### [controllers.json](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json) structure

+ Here's an empty template for controllers.json

```json
{
  "java_package": "",
  "check_source": false,
  "throttle": false,
  "validate_param": true,
  "authenticate": true,
  "after_response": true,
  "after_processing": true,
  "default_operations": false,
  "notifications": true,
  "analysis": false,
  "logging": false,

  "controllers": [
    {
      "is_preset": false,
      "name": "",
      "type": "POST",
      "request_params": [
        {
          "name": "",
          "type": "",
          "is_array": false,
          "optionality": "MANDATORY"
        }
      ],
      "response_type": "JSON",
      "response_params": [
        {
          "name": "",
          "type": "",
          "is_array": false
        }
      ]
    }
  ]
}
```

+ It starts with [`java_package`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L46) where the root java package for your service's code should be defined. e.g.: com.vangav

+ Followed by a set of booleans defining the which steps should request-processing goes through. Implementation of each of these functionalities goes in per-code-ref in the following tables, checking on whether to process each step or not happens in [ParentPlayHandler.java](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java) and these boolean values can be altered anytime from [request_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/request_properties.prop).

| boolean | code ref | definition |
| ------- | -------- | ---------- |
| [`check_source`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L47) | [`ref`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L62) | is used to turn on/off checking requests source. e.g.: if the service only accepts requests from mobile clients, a set of mac addresses, etc ... |
| [`throttle`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L48) | [`ref`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L69) | is used to switch on/off the detection and prevention of spammy behavior |
| [`validate_param`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L49) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L288) | is used to switch on/off validating requests' parameters. invalid mandatory params result in a 400 BAD_REQUEST while invalid optional params gets tracked and can be checked using [`isValidParam`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java#L224)|
| [`authenticate`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L50) | [`ref`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L76) | is used to switch on/off authenticating a request. e.g.: using the build-in OAuth2, Facebook Login, Google Login, etc ... |
| [`after_response`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L51) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L471) | is used as a service-wide on/off switch for all post-response processing (i.e.: all of the next switches are dismissed if this switch is off (false) ) |
| [`after_processing`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L52) | [`ref`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L83) |  is used to switch on/off after processing. Override this method per-controller-handler to do further processing after a request's response is sent back to the client. e.g.: use it for a blocking operation like push notifications which doesn't impact the service's core functionality in case of failure and shouldn't delay sending back the request's response |
| [`default_operations`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L53) | [`ref`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L90) |   is used to switch on/off default operations. Implement this method to override a service-wide after-response operation for all of the service's controllers. e.g.: update user's last-active-time |
| [`notifications`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L54) | [`ref`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L97) | is used to switch on/off sending push notifications |
| [`analysis`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L55) | [`ref`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L104) | is used to switch on/off doing analysis |
| [`logging`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L56) | [`ref`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L118) | is used to switch on/off doing loggin |

+ Followed by [`controllers`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L58) array where each elements defines one of the service's controllers (entry points).

| element | definition |
| ------- | ---------- |
| [`is_preset`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L62) | keep it false, not currently in use. needed for a coming feature that allows adding predefined controllers including logic/database/etc ... (e.g.: user management controllers - login/logout/reset/deactivate/...) |
| [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L63) | controller's name, recommended to be in CamelCase |
| [`type`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L64) | set it to `GET` or `POST` |
| [`request_params`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L65) | optionally add request's params to this array|
| [`response_type`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L79) | set it to `JSON`, `FILE` or `HTML` |
| [`response_params`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L80) | optionally add response's params in case of a `JSON` response_type |

  + Each element in [`request_params`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L65) has [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L67) (recomended to be lowerCamelCase), [`type`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L68) which defines how a param should be validated, [`is_array`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L69) to define if this is an array param or not (arrays in GET requests are comma ',' separated) and [`optionality`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L70) which can either be `MANDATORY` or `OPTIONAL`. The following table lists possible param types from [ParamType.java](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java) and describes how each of them is validated. Any param that's null or empty is invalid regardless of its type.
  
  | type | validation method | validation |
  | ---- | ----------------- | ---------- |
  | [`BOOLEAN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L63) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L334) | valid values (true, false) |
  | [`SHORT`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L65) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L350) | valid short value |
  | [`INT`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L66) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L374) | valid int value |
  | [`LONG`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L67) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L398) | valid long value |
  | [`FLOAT`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L68) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L422) | valid float value |
  | [`DOUBLE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L69) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L451) | valid double value |
  | [`LATITUDE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L71) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L480) | valid double >= -90.0 & <= 90.0 |
  | [`LONGITUDE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L72) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L506) | valid double >= -180.0 & <= 180.0 |
  | [`ALTITUDE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L73) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L532) | valid double |
  | [`ALPHA_NUMERIC`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L75) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L554) | |
  | [`ALPHA_NUMERIC_SPACE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L76) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L570) | |
  | [`ALPHA_NUMERIC_DASH`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L77) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L585) | |
  | [`ALPHA_NUMERIC_UNDERSCORE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L78) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L600) | |
  | [`ALPHA_NUMERIC_SPACE_DASH`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L79) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L615) | |
  | [`ALPHA_NUMERIC_SPACE_UNDERSCORE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L80) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L631) | |
  | [`ALPHA_NUMERIC_DASH_UNDERSCORE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L81) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L647) | |
  | [`ALPHA_NUMERIC_SPACE_DASH_UNDERSCORE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L82) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L663) | |
  | [`NAME`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L83) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L689) | unicode characters, spaces, dots, quotations. lengths >= 1 and <= 100 |
  | [`USER_NAME`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L84) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L717) | a-z, A-Z, 0-9, dots, dashes, underscores. length >= 1 and <= 25 |
  | [`DATE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L85) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L742) | d/m/yy - dd/mm/yyyy |
  | [`UUID`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L86) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L758) | validates java.util.UUID |
  | [`AUTH_CODE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L88) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L787) | alpha-numeric and 32 characters long |
  | [`ACCESS_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L89) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L808) | alpha-numeric and 32 characters long |
  | [`REFRESH_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L90) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L829) | alpha-numeric and 32 characters long |
  | [`EMAIL`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L92) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L848) | valid email address |
  | [`PASSWORD`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L93) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L870) | alpha numeric and at least 1 character long |
  | [`PHONE_NUMBER`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L94) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L891) | valid phone number (optionally including country codes) |
  | [`FB_ID`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L96) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L911) | Facebook's numeric ID with at least one digit |
  | [`FB_ACCESS_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L97) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L932) | alpha-numeric |
  | [`DEVICE_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L99) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L948) | ANDROID_DEVICE_TOKEN or IOS_DEVICE_TOKEN |
  | [`ANDROID_DEVICE_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L100) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L970) | alpha-numeric-dash |
  | [`IOS_DEVICE_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L101) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L983) | alpha-numeric-dash |
  | [`PHOTO`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L103) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L1000) | BASE-64 encoded and size smaller than or equal to the one defined in [param_validator_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/param_validator_properties.prop#L11) |
  | [`CAPTION`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L104) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L1020) | length smaller than or equal to the one defined in [param_validator_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/param_validator_properties.prop#L15) |
  | [`CHAT_MSG`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L105) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L1038) | length smaller than or equal to the one defined in [param_validator_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/param_validator_properties.prop#L19) |
  | [`TRACKING_ID`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L107) | [`ref`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L1056) | length smaller than or equal to 200 |
  | [`NO_VALIDATION_TYPE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L112) |  | always valid - use with caution :)) |
  
  + Each element in [`response_params`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L80) has [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L82) (recomended to be lowerCamelCase), [`type`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L83) which defines the primitive type of a response param as one of (`short`, `int`, `long`, `float`, `double`, `String`), [`is_array`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L84) to define if this is an array param or not.
  
### [gs_top.keyspace](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace) structure

+ Here's an empty template for gs_top.keyspace

```json
{
  "description": "",
  "name": "",
  "replications": [
    {
      "description": "used for dev environment",
      "name": "dev",
      "replication": "'class': 'SimpleStrategy', 'replication_factor' : 1"
    }
  ],
  "tables": [
    {
      "description": "",
      "name": "",
      "columns": [
        {
          "name": "",
          "type": ""
        }
      ],
      "partition_keys": [
        ""
      ],
      "secondary_keys": [
      ],
      "caching": "ALL",
      "order_by": [
      ],
      "queries": [
        {
          "description": "",
          "name": "",
          "prepared_statement": ""
        }
      ]
    }
  ]
}
```

+ Each keyspace_name.keyspace config file is structured as follows:
  + [`description`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L2): descripes the purpose of this keyspace. Shows in generated Java clients comment blocks and generated phriction-wiki.
  + [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L3): keyspace's name.
  + [`replications`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L4): is an array of replication methods for this keyspace (e.g.: one replication method for dev with one replica and another for prod with two replicas). CQL scripts (create, drop, ...) are generated per-keyspace per-replication-method. Each replication element is structured as follows:
    + [`description`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L6): descripes the purpose of this replication method (e.g.: for dev, for prod, etc ...). Shows in generated Java clients comment blocks and generated phriction-wiki.
    + [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L7): to identify a replication method and becomes part of the CQL script's file name.
    + [`replication`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L8): a valid Cassandra replication method (use ' instead of " to avoid confusion with JSON's ").
  + [`tables`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L11): is an array of the keyspace's tables. Each table element is structured as follows:
    + [`description`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L15): descripts the purpose of this table. Shows in generated Java clients comment blocks and generated phriction-wiki.
    + [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L16): table's name.
    + [`columns`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L17): is an array defining the table's columns. Each colum element is structured as follows:
      + [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L19): column's name.
      + [`type`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L20): column's type. Detailed list of possible types can be found [**here**](http://docs.datastax.com/en/cql/3.1/cql/cql_reference/cql_data_types_c.html). Here's a list of possible column types:
        + ascii
        + bigint
        + blob
        + boolean
        + counter
        + decimal
        + double
        + float
        + frozen
        + inet
        + int
        + list
        + map
        + set
        + text
        + timestamp
        + timeuuid
        + tuple
        + uuid
        + varchar
        + varint
    + [`partition_keys`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L27): is a String array of the table's partition key(s) (from column names).
    + [`secondary_keys`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L30): is a String array of the table's secondary key(s) (from column names).
    + [`caching`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L32): defines the table's caching as `ALL`, `KEYS_ONLY` or `NONE`.
    + [`order_by`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L33): optinally defines how the table should be ordered.
    + [`queries`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L35): is an array of the queries (prepared statements) to be used on this table. Each query element is structured as follows:
      + [`description`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L37): describes the purpose of this prepared statement. Shows in generated Java clients comment blocks and generated phriction-wiki.
      + [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L38): used to identify a query per-table and becomes part of the methods' names for this query ([`getQueryInsert`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/NameIndex.java#L212), [`getQueryDispatchableInsert`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/NameIndex.java#L226), [`getBoundStatementInsert`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/NameIndex.java#L243), [`executeAsyncInsert`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/NameIndex.java#L258), [`executeSyncInsert`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/cassandra_keyspaces/gs_top/NameIndex.java#L274)).
      + [`prepared_statement`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/gs_top.keyspace#L39): is the actual query's prepared statement. When writing a prepared statement beware to:
        + write keyspace_name.table_name in the query and not only the table_name
        + define the statements variables using `:variable_name` instead of `?`
