# public_apis

+ This package handles requesting information for any service's public API.

### [facebook](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/public_apis/facebook)

+ This sub-package facilitates querying [Facebook Graph API](https://developers.facebook.com/docs/graph-api) for user-info (pictures, fields, edges, ...) synchronously/asynchronously.

+ Usage example
```java
// init Facebook Graph API
FacebookGraph facebookGraph =
  new FacebookGraph("fb_access_token"); // init with user's facebook access token
  
// following are examples for some of the avaialble operations
  
// get profile picture synchronously
String profilePictureSync = facebookGraph.getProfilePictureSync(512);

// get profile picture asynchronously

String profilePictureAsyncTrackingId =
  facebookGraph.getProfilePictureAsync(512);

// do other operations ...

String profilePictureAsync =
  facebookGraph.getProfilePictureAsync(profilePictureAsyncTrackingId);
  
// get fields synchronously

Map<
  FacebookGraphApiFieldType,
  Pair<FacebookApiResponseStatus, RestResponseJson> > fieldsSync =
  facebookGraph.getFieldsSync(
    FAVORITE_ATHLETES,
    BIRTHDAY);
    
if (fieldsSync.get(FAVORITE_ATHLETES).getFirst() == SUCCESS) {
  
  FavoriteAthletes favoriteAthletes =
    (FavoriteAthletes)fieldsSync.get(FAVORITE_ATHLETES).getSecond();
} else if (fieldsSync.get(FAVORITE_ATHLETES).getFirst() == BAD_REQUEST) {
  
  BadRequestResponse favoriteAthletesBadRequestResponse =
    (BadRequestResponse)fieldsSync.get(FAVORITE_ATHLETES).getSecond();
} else {

  ErrorResponse favoriteAthletesErrorResponse =
    (ErrorResponse)fieldsSync.get(FAVORITE_ATHLETES).getSecond();
}

// the same goes for BIRTHDAY field and any other fields

// getting edges is similar to getting fields
```

| Class/Package | Explanation |
| ------------- | ----------- |
| [FacebookGraph](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/FacebookGraph.java) | Is the main entry point to getting any user-info using Facebook Graph API. |
| [json](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/public_apis/facebook/json) | Has the JSON representation for all of the possible JSON responses from Facebook Graph API - fields, edges, bad request and error response. |
| [BadRequestResponse](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/json/BadRequestResponse.java) | Maps a bad request's response (http status code 400). |
| [ErrorResponse](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/json/ErrorResponse.java) | Maps every response for http status codes other than 200 (success) and 400 (bad request). |
| [FacebookGraphApiFieldType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/json/fields/FacebookGraphApiFieldType.java) | Is an enumeration of Facebook Graph API's fields. |
| [FacebookGraphApiField](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/json/fields/FacebookGraphApiField.java) | Is the parent class for all the fields' classes implemented under [fields](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/public_apis/facebook/json/fields) package. |
| [FacebookGraphApiEdgeType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/public_apis/facebook/json/edges/FacebookGraphApiEdgeType.java) | is an enumeration of Facebook Graph API's edges. |
| [edge](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/public_apis/facebook/json/edges/edge) | Is the parent class for all the edges' classes implemented under [edges](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/public_apis/facebook/json/edges) package. |

### [car2go](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/public_apis/car2go)

+ This sub-package facilitates querying [car2go API](https://github.com/car2go/openAPI) public functions synchronously/asynchronously.

+ Usage example
```java
// init car2go API
Car2GoApi car2GoApi = new Car2GoApi("my_api_key");

// e.g.: get vehicles synchronously
Map<LocationType, Pair<Boolean, RestResponseJson> > vehiclesSync =
  car2GoApi.getEdgeSync(
    EdgeType.VEHICLES,
    SEATTLE,
    ROMA);
```





