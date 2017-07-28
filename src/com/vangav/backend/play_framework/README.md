
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





















+ This package is responsible for:
  + For every Vangav Backend generated RESTful service, [controllers_generator](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/play_framework/controllers_generator) handles validating [config file](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json) as well as generating [Controller](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ControllerReverseGeoCode.java), [Handler](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java), [Request](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/RequestReverseGeoCode.java) and [Response](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ResponseReverseGeoCode.java) classes per-entry-point.
  + [ParentPlayHandler](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java) controls the sequential processing from reciving a request, returning a response and handling after-response operations (if any/enabled). Processing starts at [`handleRequestAsync`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L232) and ends in [`afterResponse`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/ParentPlayHandler.java#L612). ParentPlayHandler is also the parent class for all the entry points' [Handler](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java) classes.

### [controllers_generator](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/play_framework/controllers_generator)

| Class | Explanation |
| ----- | ----------- |
| [ControllersGeneratorConstantsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/controllers_generator/ControllersGeneratorConstantsInl.java) | Holds this sub-package's constants. |
| [ControllersParserInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/controllers_generator/ControllersParserInl.java) | Has inline static methods for parsing items out of a [controllers.json](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json) file. |
| [ControllersVerifierInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/controllers_generator/ControllersVerifierInl.java) | Is used to verify a [controllers.json](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json) file. |
| [ControllersGeneratorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/controllers_generator/ControllersGeneratorInl.java) | Handles the generation of [Controller](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ControllerReverseGeoCode.java), [Handler](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java), [Request](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/RequestReverseGeoCode.java) and [Response](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/ResponseReverseGeoCode.java) classes per-entry-point as defined in a [controllers.json](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json) file. |
| [ControllersJson](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/controllers_generator/json/ControllersJson.java), [ControllerJson](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/controllers_generator/json/ControllerJson.java), [RequestParamJson](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/controllers_generator/json/RequestParamJson.java) and [ResponseParamJson](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/controllers_generator/json/ResponseParamJson.java) | Maps [controllers.json](https://github.com/vangav/vos_geo_server/blob/master/generator_config/controllers.json) files. |

### [param](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/play_framework/param)

+ This sub-package handles representing, parsing and validating an incoming request's params.

| Class | Explanation |
| ----- | ----------- |
| [ParamType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamType.java) | Represents a param's type (e.g.: BOOLEAN, DOUBLE, LONGITUDE, DATE, ACCESS_TOKEN, PHONE_NUMBER, IOS_DEVICE_TOKEN, etc ...). |
| [ParamOptionality](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamOptionality.java) | Used to define a param as OPTIONAL or MANDATORY. |
| [ParamParsersInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamParsersInl.java) | Has inline static methods for parsin incoming JSON primitive types into param's non-JSON types. |
| [ParamValidatorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorInl.java) | Used to validate incoming request's params and throws bad request exceptions for mandatory invalid params. |
| [ParamValidatorProperties](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/param/ParamValidatorProperties.java) | Map [param_validator_properties.prop](https://github.com/vangav/vos_backend/blob/master/prop/param_validator_properties.prop) properties file. |

### [request](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/play_framework/request)

+ This sub-package represents requests' headers, body, response, state in addition to some info like thrown exceptions, start/end time, state, ...

+ [Request](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java) is the primary Object to be dealt with to implement the logic of a generated Vangav Backend REST Service in controllers' Handler classes like in [vos_calculate_sum](https://github.com/vangav/vos_calculate_sum/blob/master/app/com/vangav/vos_calculate_sum/controllers/calculate_sum/HandlerCalculateSum.java#L86) and [vos_geo_server](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L96). Here's an index for the public methods provided by [Request](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java) class:

| method | explanation |
| ------ | ----------- |
| [`getStartTime`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L130) | Returns the unix time stamp (milliseconds since epoch) when processing this request started. |
| [`getStartCalendar`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L139) | Returns a Calendar Object reflecting when processing this request started. |
| [`endRequest`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L150) | *Call this method only if you are sure you need it.* This method gets automatically called upon sending back the request's response which sets the request's [`endTime`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L73) and [`execTime`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L74). |
| [`getEndTime`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L160) | Returns the unix time stamp when the request's response was sent. Usually only useful to call in after-response methods such as [`afterProcessing`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/CommonPlayHandler.java#L83). |
| [`getExecTime`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L169) | Returns the number of milliseconds it took from receiving the request till sending back the response. |
| [`getRequestId`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L174) | Each request gets a UUID. Can be used in many ways (e.g.: logging, tracing exceptions, etc ...). |
| [`getRequestHeader`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L183) | Returns a [RequestHeader](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java) Object offering [`getRemoteAddress`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java#L88), [`getRequestType`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java#L97), [`getUserAgent`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java#L106), [`getHttpAccept`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java#L115) and [`getHeaders`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestHeader.java#L124). |
| [`getRequestJsonBody`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L192) | Returns a [RequestJsonBody](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java) Object (cast it to controller's Request Object) offering [`getUserId`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java#L103) which should be overriden per-controller's request in case the request has a user-uuid as one of its params and [`isValidParam`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java#L224) which is used to check if an optional param is valid or not. |
| [`getResponseBody`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L201) | Returns a [ResponseBody](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/response/ResponseBody.java) Object (cast it to controller's Response Object). Used to set response's params at the end of each controller-handler's [`processRequest`](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L96) method. |
| [`getDispatcher`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L210) | Returns a [Dispatcher](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java) Object offering [`addDispatchMessage`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/dispatcher/Dispatcher.java#L193) used to enqueue dispatch messages any time during the request processing (before and after response) and those messages get dispatched automatically at the end of processing a request. To use this functionality a service has to have a worker service too (just enter **`Y`** during service generation when asked to add a worker service. Then make sure to run this worker service as well as the main service. |
| [`getControllerName`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L219) | Returns the controller's name. Can be used for logging, analysis, etc ... |
| [`setUserId`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L231) | For example on new user signup this method is used to set the new user's uuid. |
| [`getUserId`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L241) | Used to get the uuid of the user issuing the request if the uuid was already set through overriding [`getUserId`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java#L103) or invoking [`setUserId`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L231), otherwise it returns the default [`uuid`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestJsonBody.java#L94).  |
| [`getState`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L277) | Invoking this method in any after-response method returns the request's state ([`OK`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestState.java#L59), [`BAD_REQUEST`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestState.java#L60), [`INTERNAL_SERVER_ERROR`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/RequestState.java#L61)). Can be used for logging, analysis, etc ... |
| [`getVangavExceptions`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L299) [`getTheLastVangavException`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L311) [`getExceptions`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L337) [`getTheLastException`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L348) [`getAllExceptionsAsString`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L364) | Are used to log/analyze/report problems. Usually combined with other methods like `getRequestId`, `getUserId`, `getState`, etc ... to track problems as they occur. |
| [`addVangavException`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L288) [`addException`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/play_framework/request/Request.java#L327) | Any thrown Exception gets automatically added to the request's exceptions through these methods, however calling these methods can be useful for caught non-fatal exceptions thrown by the service's logic written by you. Any exception thrown by Vangav Backend gets added to the request's exceptions automatically. |

