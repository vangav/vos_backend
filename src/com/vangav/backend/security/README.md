# security

### [authentication](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security/authentication)

+ [facebook/FacebookAuthInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/facebook/FacebookAuthInl.java) usage example
```java
FacebookAuthInl.validateFacebookAccessToken(
  facebookAccessToken, // user's facebook access token
  facebookAppId); // facebook app's id
  
// throws exceptions in case of authentication failure
```

+ [google/GoogleAuthInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/google/GoogleAuthInl.java) usage example
```java
String userAppId =
  GoogleAuthInl.validateGoogleIdToken(
    googleIdToken,
    googleAppId);
  
// throws exceptions in case of authentication failure
```

+ [o_auth_2/OAuth2Tokens](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/o_auth_2/OAuth2Tokens.java) generates an authentication code, an access token and a refresh token.

+ [transaction_tokens/TransactionTokensGeneratorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/transaction_tokens/TransactionTokensGeneratorInl.java) has inline methods for generating pairs of transaction tokens in the form of a map or a json object/


