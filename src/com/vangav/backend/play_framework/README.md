
> **why?** play framework powers linkedin's 500,000,000 members backend; it makes it easy to build web applications

> vangav backend generated services generates 100% of the needed play-framwork-code and tunes it for high performance and big scale; generated services offers a multitude of features on top of play framework as explained in this tutorial

# play framework

## structure

| pkg/class | explanation |
| --------- | ----------- |
| [ParentPlayHandler](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java) | is the root class for handling all incoming requests to a vangav backend service where execution starts at [`handleRequestAsync`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L232) |
| **pkg: [param](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/play_framework/param)** | represents requests' params and handles their parsing and validation |
| [ParamType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java) | an enum representing supported request param types like [`ACCESS_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L89), [`EMAIL`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L92), [`PHONE_NUMBER`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L94), ... |
| [ParamOptionality](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamOptionality.java) | is an enum used to distinguish between mandatory and optional requests' params |
| [ParamValidatorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java) | has the inline static methods for validating requests' params |
| [ParamValidatorProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorProperties.java) | maps [param_validator_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/param_validator_properties.prop) properties file defining how some params get validated like a photo's size |
| [ParamParsersInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamParsersInl.java) | handles requests' params parsing like parsing an incoming date `string` into a `date` object |
| **pkg: [request](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/play_framework/request)** | represents incoming requests' objects (header, params, state, thrown exceptions, dispatch queue, ...) as well as handling building a response for each request |
| [Request](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java) | is the main class to deal with as explained in depth later in this tutorial; it holds objects from all other classes under this package |
| [RequestType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestType.java) | represents supported http request types: `GET`, `POST`, ... |
| [RequestState](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestState.java) | represents the response's http status (`OK`, `BAD_REQUEST`, ...) |
| [RequestProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestProperties.java) | maps [request_properties](https://github.com/vangav/vos_backend/blob/master/prop/request_properties.prop) properties file defining how a request gets processed as explained in depther later in this tutorial |
| [RequestHeader](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java) | represents a request's header |
| [RequestJsonBody](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java), [RequestJsonBodyGet](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBodyGet.java) and [RequestJsonBodyPost](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBodyPost.java) | represent a request's body content |
| [UAgentInfo](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/third_party/UAgentInfo.java) | extracts agent-info from a request's header (e.g.: browser type, device type, ...) |
| [ResponseBody](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBody.java), [ResponseBodyJson](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyJson.java), [ResponseBodyHtml](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyHtml.java) and [ResponseBodyFile](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyFile.java) | represents a response's body content |
| [ResponseBodyError](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java) and [ResponseBodyErrorProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyErrorProperties.java) | represents vangav backend's error responses and controls which parts of it get sent back to the requesting client |
| **pkg: [controllers_generator](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/play_framework/controllers_generator)** | represents generator config files and handles using those config file to generate the api part of vangav backend services; config and generation are explained in depther later in this tutorial |

## service generator config structure

> this section explains the config structure used to generate a vangav backend

+ using a config directory is optional but highly recommended when generating a new vangav backend as it save more than 90% of the time needed to implement the backend's code
+ a config directory (named after the new backend service) contains:
  + a mandatory [controllers.json](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json) even if it has no api controllers in it as it defines the service's genera strucutre in its first block as explained below
  + zero-to-many `.client_java` (e.g.: [vos_instagram_dash_board.client_java](https://github.com/vangav/vos_instagram_test/blob/master/generator_config/vos_instagram_dash_board.client_java)) files to generate as many clients as needed; these files are identical to the `controllers.json` files in structure where the first block defining the executing sequence isn't needed
+ all config files have a `json` structure where [inline comments](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L2) (lines starting with `#`) are allowed

### [controllers.json](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json) structure

> this section also explains `.client_java` files since it has the same structure as `controllers.json` where the difference comes in the code generated by vangav backend - these files just describe an api for which both of the server and client side code can be generated

+ here's an empty template for reference followed by an explanation for each part in it

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

+ it starts with [`java_package`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L46) where the root java package for your service's code should be defined, e.g.: `com.vangav`

+ followed by a set of booleans (check_source ... logging) whose values go into the generated [request_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/request_properties.prop); for each received request, [ParentPlayHandler.java](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java) checks each value to decide what to execute and what to skip

> whenever in doubt, set to true; that will make it easier to expand the service as time goes on

| boolean | code ref | explanation |
| ------- | -------- | ---------- |
| [`check_source`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L47) | [`checkSource`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L62) | is used to turn on/off checking requests' source; e.g.: if the service only accepts requests from mobile clients, a set of mac addresses, ... |
| [`throttle`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L48) | [`throttle`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L69) | is used to switch on/off the detection and prevention of spammy behavior |
| [`validate_param`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L49) | [`validate`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L288) | is used to switch on/off validating requests' parameters; invalid mandatory params result in a 400 BAD_REQUEST while invalid optional params gets tracked and can be checked using [`isValidParam`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java#L224) - more on how each param type gets validated in the following sections |
| [`authenticate`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L50) | [`authenticate`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L76) | is used to switch on/off authenticating a request. e.g.: using your own authentication mechanism or the built-in oauth2, facebook login, google login, ... |
| [`after_response`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L51) | [`afterResponse`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L471) | is used as a service-wide on/off switch for all post-response processing (i.e.: all of the next switches are dismissed if this switch is off (false) ) |
| [`after_processing`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L52) | [`afterProcessing`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L83) |  is used to switch on/off after processing; override this method per-controller-handler to do further processing after a request's response is sent back to the client - e.g.: use it for a blocking operation like push notifications which doesn't impact the service's core functionality in case of failure and shouldn't delay sending back the request's response |
| [`default_operations`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L53) | [`dispatchDefaultOperations`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L90) |   is used to switch on/off default operations; implement this method to override a service-wide after-response operation for all of the service's controllers - e.g.: update user's last-active-time |
| [`notifications`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L54) | [`dispatchPushNotifications`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L97) | is used to switch on/off sending push notifications |
| [`analysis`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L55) | [`dispatchAnalysis` and `dispatchDefaultAnalysis`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L104) | is used to switch on/off doing analysis |
| [`logging`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L56) | [`dispatchLogging` and `dispatchDefaultLogging`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L118) | is used to switch on/off doing logging |

+ followed by [`controllers`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L58) array where each element defines one of the service's controllers (api entry points)

| element | explanation |
| ------- | ----------- |
| [`is_preset`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L62) | keep it false, not currently in use; needed for a coming feature that allows adding predefined controllers including logic/database/... (e.g.: user management controllers - login/logout/reset/deactivate/...) |
| [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L63) | controller's name, recommended to be in `CamelCase` |
| [`type`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L64) | set it to `GET` or `POST` depending on the desired http request type |
| [`request_params`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L65) | optionally add request's params to this array, more on to setting this part below |
| [`response_type`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L79) | set it to `JSON`, `FILE` or `HTML` depending on the desired response type |
| [`response_params`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L80) | optionally add response's params in case of a `JSON` response_type to this array, more on to setting this part below |

+ each element in [`request_params`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L65) has the following sub-elements

| element | explanation |
| ------- | ----------- |
| [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L67) | request param's name (recomended to be `lowerCamelCase`) |
| [`type`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L68) | defines how a request param should be validated, more on this in the next section |
| [`is_array`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L69) | `true` or `false` to define if this is an array param; in `GET` requests arrays are `,` separated (e.g.: url.com/get_colors?fruits=apple,orange,mango |
| [`optionality`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L70) | `MANDATORY` or `OPTIONAL`; invalid `MANDATORY` params automatically result in a 400 BAD_REQUEST response and invalid `OPTIONAL` params are tracked and can be check using the request's [`isValidParam`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java#L224) method |

+ the following table lists possible [`type`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L68) values from [ParamType.java](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java), and how each type gets validated; any param with a null/emptu value is invalid regardless of its type

> this is a good section to bookmark to help pick request param types when configuring a new vangav backend service
  
  | type | validation method | validation |
  | ---- | ----------------- | ---------- |
  | [`BOOLEAN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L63) | [`validateBoolean`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L336) | true or false |
  | [`SHORT`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L65) | [`validateShort`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L352) | valid short value |
  | [`INT`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L66) | [`validateInt`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L376) | valid int value |
  | [`LONG`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L67) | [`validateLong`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L400) | valid long value |
  | [`FLOAT`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L68) | [`validateFloat`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L424) | valid float value |
  | [`DOUBLE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L69) | [`validateDouble`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L453) | valid double value |
  | [`LATITUDE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L71) | [`validateLatitude`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L482) | valid double >= -90.0 & <= 90.0 |
  | [`LONGITUDE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L72) | [`validateLongitude`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L508) | valid double >= -180.0 & <= 180.0 |
  | [`ALTITUDE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L73) | [`validateAltitude`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L534) | valid double |
  | [`ALPHA_NUMERIC`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L75) | [`validateAlpha...`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L556) | a-z, A-Z, 0-9 |
  | [`ALPHA_NUMERIC_SPACE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L76) | [`validateAlpha...`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L572) | a-z, A-Z, 0-9, spaces |
  | [`ALPHA_NUMERIC_DASH`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L77) | [`validateAlpha...`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L587) | a-z, A-Z, 0-9, dashes |
  | [`ALPHA_NUMERIC_UNDERSCORE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L78) | [`validateAlpha...`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L602) | a-z, A-Z, 0-9, underscores |
  | [`ALPHA_NUMERIC_SPACE_DASH`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L79) | [`validateAlpha...`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L617) | a-z, A-Z, 0-9, spaces, dashes |
  | [`ALPHA_NUMERIC_SPACE_UNDERSCORE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L80) | [`validateAlpha...`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L633) | a-z, A-Z, 0-9, spaces, underscores |
  | [`ALPHA_NUMERIC_DASH_UNDERSCORE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L81) | [`validateAlpha...`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L649) | a-z, A-Z, 0-9, dashes, underscores |
  | [`ALPHA_NUMERIC_SPACE_DASH_UNDERSCORE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L82) | [`validateAlpha...`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L665) | a-z, A-Z, 0-9, spaces, dashes, underscores |
  | [`NAME`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L83) | [`validateName`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L691) | unicode characters, spaces, dots, quotations; lengths >= 1 and <= 100 |
  | [`USER_NAME`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L84) | [`validateUserName`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L719) | a-z, A-Z, 0-9, dots, dashes, underscores; length >= 1 and <= 25 |
  | [`DATE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L85) | [`validateDate`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L744) | d/m/yy - dd/mm/yyyy |
  | [`UUID`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L86) | [`validateUuid`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L760) | validates [java.util.UUID](https://docs.oracle.com/javase/7/docs/api/java/util/UUID.html); this is the type of id used in all vangav services |
  | [`AUTH_CODE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L88) | [`validateAuthCode`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L789) | alpha-numeric and 32 characters long |
  | [`ACCESS_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L89) | [`validateAccessToken`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L810) | alpha-numeric and 32 characters long |
  | [`REFRESH_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L90) | [`validateRefreshToken`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L831) | alpha-numeric and 32 characters long |
  | [`EMAIL`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L92) | [`validateEmail`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L850) | valid email address |
  | [`PASSWORD`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L93) | [`validatePassword`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L872) | alpha numeric and at least 1 character long |
  | [`PHONE_NUMBER`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L94) | [`validatePhoneNumber`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L893) | valid phone number (optionally including country codes) |
  | [`FB_ID`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L96) | [`validateFbId`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L913) | facebook's numeric id with at least one digit |
  | [`FB_ACCESS_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L97) | [`validateFbAccessToken`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L934) | alpha-numeric |
  | [`DEVICE_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L99) | [`validateDeviceToken`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L948) | [`ANDROID_DEVICE_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L100) or [`IOS_DEVICE_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L101) |
  | [`ANDROID_DEVICE_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L100) | [`validateAndroidDeviceToken`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L972) | alpha-numeric-dash |
  | [`IOS_DEVICE_TOKEN`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L101) | [`validateIosDeviceToken`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L985) | alpha-numeric-dash |
  | [`PHOTO`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L103) | [`validatePhoto`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L1002) | base-64 encoded and size smaller than or equal to the one defined in [param_validator_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/param_validator_properties.prop#L55) |
  | [`CAPTION`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L104) | [`validateCaption`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L1022) | length smaller than or equal to the one defined in [param_validator_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/param_validator_properties.prop#L59) |
  | [`CHAT_MSG`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L105) | [`validateChatMsg`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L1040) | length smaller than or equal to the one defined in [param_validator_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/param_validator_properties.prop#L63) |
  | [`TRACKING_ID`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L107) | [`validateTrackingId`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L1056) | length smaller than or equal to 200; usage example: the server side adds it in the response as it came in the request for an async client to track which response matches which request |
  | [`NO_VALIDATION_TYPE`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java#L112) |  | always valid - use with caution :)) |
  
  each element in [`response_params`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L80) has the following sub-elements
  
  | element | explanantion |
  | ------- | ------------ |
  | [`name`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L82) | response param's name (recomended to be `lowerCamelCase`) |
  | [`type`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L83) | defines the primitive type of a response param as one of (`boolean`, `short`, `int`, `long`, `float`, `double`, `String`) |
  | [`is_array`](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json#L84) | `true` or `false` to define if this is an array param |
  
## generated service structure

> **why?** although you only need to know ~5% of what's explained in this section to generate a vangav backend service as shown in the `calculate sum` and `geo server` tutorials; understanding every bit of the generated service is useful as you get to scale and want to tune every bit in the generated service

> this section explains the building blocks generated or used by this package in a vangav backend generated service; it uses the [geo server](https://github.com/vangav/vos_geo_server) example, yet it's the same structure for all services generated using vangav backend

the following scripts are generated for every vangav backend service

| script | explanation |
| ------ | ----------- |
| [_run.sh](https://github.com/vangav/vos_geo_server/blob/master/_run.sh) | runs the service in dev mode; by default it runs on port 9000, to run on a different port use `./_run.sh a_different_port_number` e.g.: `./_run.sh 8765` |
| [_clean.sh](https://github.com/vangav/vos_geo_server/blob/master/_clean.sh) | cleans the service's build |
| [_compile.sh](https://github.com/vangav/vos_geo_server/blob/master/_compile.sh) | compiles the service |
| [_debug.sh](https://github.com/vangav/vos_geo_server/blob/master/_debug.sh) | runs the service in debug mode; debugging the code can happen in eclipse as explained in the [debugging](https://github.com/vangav/vos_backend/blob/master/README/08_debug.md) tutorial |
| [_dist.sh](https://github.com/vangav/vos_geo_server/blob/master/_dist.sh) | generates a production executable for the service to deploy it on the production server(s); deoployment is explained in the [deploy](https://github.com/vangav/vos_backend/blob/master/README/09_deploy.md) tutorial |
| [_eclipsify.sh](https://github.com/vangav/vos_geo_server/blob/master/_eclipsify.sh) | makes the service eclipse-ready; all generated services are eclipse-ready by default |

### [conf](https://github.com/vangav/vos_geo_server/tree/master/conf)

+ the conf directory contains all the service's configuration files
+ this directory gets packaged with the productions version after using the [_dist.sh](https://github.com/vangav/vos_geo_server/blob/master/_dist.sh) script
+ any additional config/data files must go under this directory

#### - [routes](https://github.com/vangav/vos_geo_server/blob/master/conf/routes) and [application.conf](https://github.com/vangav/vos_geo_server/blob/master/conf/application.conf)
+ used by play framework to define the servie's entry points and the service's configuration respectively
+ vangav backend's generated services automatically sets the values for these files

#### - [prop](https://github.com/vangav/vos_geo_server/tree/master/conf/prop)
+ holds all vangav backend's properties files
+ generated services autoamtically get a copy of the necessary properties files depending on the project's config
+ optionally copy additional properties files to the generated service to use the corresponding utility
+ following is the explanation of each of the properties files, inside each properties file is a detailed explanation of exactly how to set/use each property

| properties file | used in geo server | explanantion |
| --------------- | ------------------ | ------------ |
| [android_notification](https://github.com/vangav/vos_backend/blob/master/prop/android_notification_properties.prop) | no | anrdoid push notifications |
| [apple_notification](https://github.com/vangav/vos_backend/blob/master/prop/apple_notification_properties.prop) | no | apple push notifications |
| [cassandra](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/cassandra_properties.prop) | yes | cassandra's api, topology, ... |
| [dispatcher](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/dispatcher_properties.prop) | yes | dispatcher's workers topology |
| [facebook_graph_api_edge](https://github.com/vangav/vos_backend/blob/master/prop/facebook_graph_api_edge_properties.prop) | no | paging, page_limit, ... |
| [java_email](https://github.com/vangav/vos_backend/blob/master/prop/java_email_properties.prop) | no | smtp properties |
| [mail_gun_email](https://github.com/vangav/vos_backend/blob/master/prop/mail_gun_email_properties.prop) | no | mailgun api access values |
| [param_validator](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/param_validator_properties.prop) | yes | defines some of the request's params max_size like photos, captions, chat messages, ... |
| [request](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/request_properties.prop) | yes | switches on/off the sequential steps of a request's processing: check source device, throttle request, validate params, authenticate, after-response processing, default operations, notifications, analysis, logging; this makes it simple to control how a request is processed without touching the code |
| [response_error](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop) | yes | switches on/off what to send back to the client in case of an error response: type, code, sub-code, message, class, stack-trace, trace-id |
| [thread_pool](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/thread_pool_properties.prop) | yes | vangav backend has a thread pool per type of operation with an optimal default size (per machine's specs) for handling big scale; only set values for this properties file if you are sure |
| [twilio](https://github.com/vangav/vos_backend/blob/master/prop/twilio_properties.prop) | no | twilio api access values |

### [lib](https://github.com/vangav/vos_geo_server/tree/master/lib)

+ conatins all the third-party libs used by vangav backend
+ all the necessary libs are automatically copied to generated services
+ the optional use of some utilities (e.g.: email. twilio, ...) require manually copying a lib or more to your generated service's lib directory
+ refer to the lib section of the [project's contents tutorial](https://github.com/vangav/vos_backend/blob/master/README/01_project_contents.md#lib) for detailed information

### [generator_config](https://github.com/vangav/vos_geo_server/tree/master/generator_config)

+ generator_config includes the files used to generate a vangav backend service
+ `controllers.json` file is kept for reference and for future functionalities utilizing it
+ `.client_java` files are also kept for reference and for future functionalities utilizing it

### [public](https://github.com/vangav/vos_geo_server/tree/master/public)

+ public directory keeps the public files (images, java scripts and style sheets) needed for web pages (if any) provided by the service

### [app](https://github.com/vangav/vos_geo_server/tree/master/app)

+ app is the directory containing all the services source code
+ [Global.java](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java) extends play framework's `GlobalSettings` to override some functionalities that either run one time per service start/stop or on certain events like `beforeStart`, `onStart`, `onStop`, ...
  + [`beforeStart`](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java#L76) is used by vangav backend to load properties, connect to cassandra and prepare cassandra's perpared statements; that's also where other functionalities like loading geo services data and similar operations should take place
  + [`onStop`](https://github.com/vangav/vos_geo_server/blob/master/app/Global.java#L127) is used by vangav backend to shutdown the thread pools and disconnect from cassandra; that's also where free-resource operations should take place
+ [views](https://github.com/vangav/vos_geo_server/tree/master/app/views) is created by play framework to keep the service's html pages
+ [controllers](https://github.com/vangav/vos_geo_server/tree/master/app/controllers) is created by play framework; not used by vangav backend and to be left as is

### [app/com/vangav/vos_geo_server/controllers/](https://github.com/vangav/vos_geo_server/tree/master/app/com/vangav/vos_geo_server/controllers)

+ this directory contains all the generated controllers (api entry points) as defined in the project's config [controllers.json](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json)

+ [CommonPlayHandler.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java) is the parent class for all the controllers' handlers (e.g.: [HandlerReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java)) where the request-to-response logic is implemented

+ in vagav backend processing a request goes through sequential-optional steps; to enable one or more of these steps, they must be set to true in [request_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/request_properties.prop); [CommonPlayHandler.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java) class provides the ability to implement/override those sequential-optional steps in `CommonPlayHandler.java` or per-controller (e.g.: `HandlerReverseGeoCode.java`) as follows:

| step | explanantion |
| ---- | ------------ |
| [`checkSource`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L62) | is used to check the request's source; e.g.: if the service only accepts requests from mobile clients, a set of mac addresses, ... |
| [`throttle`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L69) | is used to track and prevent spammy behavior |
| [`authenticateRequest`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L76) | is used to authenticate a request using your own authentication mechanism or the built-in oauth2, facebook login, google login, ... |
| [`afterProcessing`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L83) | override this method per-controller-handler to do further processing after a request's response is sent back to the client; e.g.: use it for a blocking operation like push notifications which doesn't impact the service's core functionality in case of failure and shouldn't delay sending back the request's response |
| `dispatch...xxx` | methods starting with `dispatch` are processed after a request's response is returned and it's encouraged to dispatch the operations of these methods to the service's worker |
| [`dispatchDefaultOperations`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L90) | implement this method to override a service-wide after-response operation for all of the service's controllers; e.g.: update users' last-active-time |
| [`dispatchPushNotifications`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L97) | override this method per-controller to dispatch/process push notifications |
| [`dispatchAnalysis`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L104) | override this method per-controller to dispatch/process analysis |
| [`dispatchDefaultAnalysis`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L111) | implement to dispatch service-wide analysis for all controllers; e.g.: to keep track of request-to-response time |
| [`dispatchLogging`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L118) | override this method per-controller to dispatch/process logging |
| [`dispatchDefaultLogging`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L125) | implement to dispatch service-wide logging for all controllers; e.g.: log request/response/status-code |
| [`absorbUnhandledExceptions`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L136) | if vangav backend fails to handle an exception, it forwards the exception to this method to be absorbed and dealt with as needed. e.g.: send a notification email to system admin |

+ for each controller (api entry point), vangav backend generates a directory with four classes (e.g.: [reverse_geo_code](https://github.com/vangav/vos_geo_server/tree/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code)) as follows:

| class | explanation |
| ----- | ----------- |
| [ControllerReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ControllerReverseGeoCode.java) | represents the entry point for the controller; the [routes](https://github.com/vangav/vos_geo_server/blob/master/conf/routes#L14) config points to [getReverseGeoCoe](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ControllerReverseGeoCode.java#L63) method in this class - no need to edit this class |
| [RequestReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/RequestReverseGeoCode.java) | represents the controller's request; this class's parent class provides the method [`isValidParam`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java#L224) to check if an optional param is valid or not - configuring optional/mandatory request params is explained in the next [service generator config structure](https://github.com/vangav/vos_backend/blob/master/README/04_rest_service_config_structure.md) tutorial - each `Requestxxx` class overrides the method [`validate()`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/RequestReverseGeoCode.java#L105) where request's param validation takes place, inside this method add your own additional validation if needed |
| [ResponseReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ResponseReverseGeoCode.java) | represents the controller's response; each response class provides a [`set`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ResponseReverseGeoCode.java#L98) method to set the response's content at the [end](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L117) of the handler's `processRequest` method |
| [HandlerReverseGeoCode.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java) | is the class where a controller's request-to-response logic should be implemented; generated services include todo comments where the request-to-response logic should be implemented to make it easier to find :) [`processRequest`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L96) method is the main part where the controller's logic should be implemented, ending with calling the response's set method as explained above - optionally override more methods from [CommonPlayHandler.java](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java) as explained above |
  
+ in order to add a new controller after a service has already been generated, just copy an exsisting one and modify it then add it to the [routes](https://github.com/vangav/vos_geo_server/blob/master/conf/routes) file as explained in [expand "calculate sum" to "calculator" without regenerating the service](https://github.com/vangav/vos_backend/blob/master/README/00_expanding_calculate_sum_example.md#expand-calculate-sum-to-calculator-without-regenerating-the-service)

## [request](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java) object

+ request is the primary object used to implement the logic of a generated vangav backend service in controllers' handler classes like in [calculate sum](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/HandlerCalculateSum.java#L86) and [geo server](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L96)
+ here's an index of the public methods provided by [request](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java):

| method | explanation |
| ------ | ----------- |
| [`long getStartTime`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L130) | returns the unix time stamp (milliseconds since epoch) when processing this request started |
| [`Calendar getStartCalendar`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L139) | returns a [Calendar](https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html) object reflecting when processing this request started |
| [`void endRequest`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L150) | this method gets automatically called upon sending back the request's response which sets the request's [`endTime`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L73) and [`execTime`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L74) - *call this method only if you are sure you need to* |
| [`long getEndTime`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L160) | returns the unix time stamp when the request's response was sent; usually only useful to call in after-response methods such as [`afterProcessing`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L83) |
| [`int getExecTime`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L169) | returns the number of milliseconds it took from receiving the request till sending back the response |
| [`UUID getRequestId`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L174) | each request gets a new [`UUID`](https://docs.oracle.com/javase/7/docs/api/java/util/UUID.html); can be used in many ways (e.g.: logging, tracing exceptions, ...) |
| [`RequestHeader getRequestHeader`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L183) | returns a [RequestHeader](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java) object offering [`String getRemoteAddress`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java#L88), [`RequestType getRequestType`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java#L97) (`GET`, `POST`, ...), [`String getUserAgent`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java#L106), [`String getHttpAccept`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java#L115) and [`Map<String, String[]> getHeaders`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java#L124) |
| [`RequestJsonBody getRequestJsonBody`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L192) | every request (including `GET` requests) gets transformed into a [RequestJsonBody](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java) object; returns a [RequestJsonBody](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java) object (cast it to controller's request object - automatically done in generated handler classes) offering [`UUID getUserId`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java#L103) which should be overriden per-controller's request class in case the request has a user-uuid as one of its params and [`boolean isValidParam`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java#L224) which is used to check if an optional param is valid or not |
| [`ResponseBody getResponseBody`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L201) | returns a [ResponseBody](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBody.java) object (cast it to controller's response class - automatically added in generated handler classes); used to set response's params at the end of each controller-handler's [`processRequest`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L96) method |
| [`Dispatcher getDispatcher`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L210) | returns a [Dispatcher](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java) object offering [`addDispatchMessage`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java#L193) used to enqueue dispatch messages any time during the request processing (before and after response) and those messages get dispatched automatically at the end of processing a request; to use this functionality a service has to have a worker service too (just enter `y` during service generation when asked to add a worker service and make sure to run that worker service as well as the main service |
| [`String getControllerName`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L219) | returns the controller's name; can be used for logging, analysis, ... |
| [`void setUserId`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L231) | for example on new user signup or a login request, this method is used to set the user's uuid since the user id isn't one of the request's params |
| [`UUID getUserId`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L241) | used to get the uuid of the user issuing the request if the uuid was already set through overriding [`getUserId`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java#L103) or invoking [`setUserId`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L231), otherwise it returns the default [`uuid`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java#L94)  |
| [`RequestState getState`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L277) | invoking this method in any after-response method returns the request's state ([`OK`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestState.java#L59), [`BAD_REQUEST`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestState.java#L60), [`INTERNAL_SERVER_ERROR`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestState.java#L61)); can be used for logging, analysis, ... |
| [`ArrayList<VangavException> getVangavExceptions`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L299), [`VangavException getTheLastVangavException`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L311), [`ArrayList<Exception> getExceptions`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L337), [`Exception getTheLastException`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L348), [`String getAllExceptionsAsString`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L364) | are used to log/analyze/report problems; usually combined with other methods like `getRequestId`, `getUserId`, `getState`, ... to trace problems as they occur |
| [`void addVangavException`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L288), [`void addException`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L327) | any thrown exception gets automatically added to the request's exceptions through these methods; however calling these methods can be useful for caught non-fatal exceptions thrown by the service's logic written by you |

## [error response](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java)

> **why?** we do our best building bullet-proof services as well as educating members how to use them; but what happens when things go wrong (e.g.: a member missed a request param, put a string in place of an int, ...), when you want to intentionally return an error response in some circumstances (e.g.: detecting a spammy behavior, ...), ... - this section answers these questions :))

+ normally a vangav backend service will return the expected response (`json`, `html` or `file`) as defined per-controller with a status code of 200 (`HTTP_OK`)
+ other than 200 (`HTTP_OK`), vangav backend may also return 400 (`HTTP_BAD_REQUEST`) and 500 (`HTTP_INTERNAL_SERVER_ERROR`)
  + 400 (`HTTP_BAD_REQUEST`) in case the problem came from the client's request; e.g.: expired access token, wrong login, missing/invalid mandatory request param, ...
  + 500 (`HTTP_INTERNAL_SERVER_ERROR`) in case of a problem within the service itself; e.g.: database unreachable, a utility method invoked using bad arguments, ...
+ returned error response (for both 400 (`BAD_REQUEST_EXCEPTION`) and 500 (`CODE_EXCEPTION`/`DEFAULT_EXCEPTION`)) has the following json structure; by default all elements are returned to the client, each element can be switched on/off from [response_error_properties.prop](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop)

  | element_name | element_type | explanation |
  | ------------ | ------------ | ----------- |
  | [`error_type`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L65) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L215) | [`BAD_REQUEST_EXCEPTION`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L70), [`CODE_EXCEPTION`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L71) or [`DEFAULT_EXCEPTION`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L72); `DEFAULT_EXCEPTION` indicates a non-vangav-exception, i.e. an exception coming from the user's implemented logic code that should have been handled properley |
  | [`error_code`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L69) | [`int`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L217) | optionally set this value while throwing an exception; *values up to `299` are reserved for use by vangav backend functionalities/utilities* |
  | [`error_sub_code`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L73) | [`int`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L219) | optionally set this value while throwing an exception |
  | [`error_message`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L77) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L221) | exception's message; explains what caused the error |
  | [`error_class`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L81) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L223) | one of [`ExceptionClass`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java#L86) enum values, as defined per-thrown [VangavException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/VangavException.java) |
  | [`error_stack_trace`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L85) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L225) | exception's stack trace |
  | [`error_trace_id`](https://github.com/vangav/vos_geo_server/blob/master/conf/prop/response_error_properties.prop#L89) | [`String`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBodyError.java#L227) | binds the error's exception with the request causing it through the `request_id`; so the request causing the error can be pulled from the logs |
  
+ while implementing your vangav backend service's logic, throw a [BadRequestException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/BadRequestException.java) to return an error response with status code 400 (HTTP_BAD_REQUEST) and throw a [CodeException](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/exceptions/CodeException.java) to return an error response with status code 500 (HTTP_INTERNAL_SERVER_ERROR)
+ all request-processing (before and after response) exceptions are caught and handled in [ParentPlayHandler](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java) where request processing starts at [`handleRequestAsync`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L232) which gets called from controller's `Controller` class (e.g.: [`handlerReverseGeoCode.handleRequestAsync(request() );`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ControllerReverseGeoCode.java#L68))

+ an example for throwing a bad request exception from [ParamValidatorInl: `throwInvalidParam`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java#L1074)

```java
throw new BadRequestException(
  151,
  7,
  "Invalid param ["
    + name
    + "]",
  ExceptionClass.INVALID);
```

+ an example for throwing a code exception from [Dispatcher: `DispatcherRunnable`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java#L124)

```java
throw new CodeException(
  32,
  1,
  "propterty ["
    + DispatcherProperties.kWorkersTopology
    + "] isn't defined in properties file ["
    + DispatcherProperties.i().getName()
    + "]",
  ExceptionClass.PROPERTIES);
```

# exercise
+ what's a `.client_java` config file?
+ what's the difference between `after_response` and `after_processing`?
+ what are the classes generated per-controller?
+ how to get the request's param-values from the request object?

# next tutorial -> [properties](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/properties)
> handles loading and extracting data from properties files
