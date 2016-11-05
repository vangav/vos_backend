# public_apis

+ This package handles requesting information for any service's public API.

### [facebook](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/public_apis/facebook)

+ Usage example
```java
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
