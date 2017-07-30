
> **why?** authentication is important for protecting the privacy of users' info (e.g.: online banking, messages, ...) and cryptography is important for protecting users' info while stored (e.g.: passwords) or transferred to protected against hacks like "man in the middle attack"

# security

## [authentication](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security/authentication)

### [facebook auth](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security/authentication/facebook)

+ [FacebookAuthInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/facebook/FacebookAuthInl.java) is used to verify a user's facebook access token (facebook login) through using [`validateFacebookAccessToken`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/facebook/FacebookAuthInl.java#L83) method

+ usage template

```java
  FacebookAuthInl.validateFacebookAccessToken(
    facebookAccessToken, // user's facebook access token
    facebookAppId); // facebook app's id
  
  // throws exceptions in case of authentication failure
```

+ usage example from [instagram / HandlerLoginFacebook: `authenticateRequest`](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/login_facebook/HandlerLoginFacebook.java#L125)

```java
  // authenticate using Facebook Graph API
  FacebookAuthInl.validateFacebookAccessToken(
    requestLoginFacebook.fb_access_token,
    Constants.kFacebookAppId);
```

### [google auth](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security/authentication/google)

+ [GoogleAuthInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/google/GoogleAuthInl.java) is used to verify a user's google id token (google login) through using [`validateGoogleIdToken`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/google/GoogleAuthInl.java#L84) method

+ usage template

```java
  String userAppId =
    GoogleAuthInl.validateGoogleIdToken(
      googleIdToken,
      googleAppId);
  
  // throws exceptions in case of authentication failure
```

### [o auth 2](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security/authentication/o_auth_2)

+ if you are not familiar with `o auth 2`, here's a good tutorial [digital ocean: o auth 2](https://www.digitalocean.com/community/tutorials/an-introduction-to-oauth-2)

+ [OAuth2Tokens](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/o_auth_2/OAuth2Tokens.java) is used to generate: authentication code, access token and refresh token

+ usage example from [instagaram / HandlerLoginEmail: `processRequest`](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/controllers/login_email/HandlerLoginEmail.java#L181)

```java
  // generate new authentication tokens
  OAuth2Tokens oAuth2Tokens = new OAuth2Tokens();

  // insert into ig_auth.auth_codes
  AuthCodes.i().executeSyncInsert(
    userId,
    requestLoginEmail.device_token,
    oAuth2Tokens.getAuthorizationCode(), // auth code
    oAuth2Tokens.getAccessToken(),       // access token
    oAuth2Tokens.getRefreshToken(),      // refresh token
    ((int)Constants.kAuthCodeLifeTime.getAs(
      TimeUnitType.SECOND).getValue() ) );
```

### [transaction tokens](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/transaction_tokens/TransactionTokensGeneratorInl.java)

+ [TransactionTokensGeneratorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/authentication/transaction_tokens/TransactionTokensGeneratorInl.java) has inline static methods for generating transaction token pairs

+ pairs of <server token, client token> are generated where both the server and authentic client sides keep a copy of those pairs; a client token can be viewed as a one-time-use password

+ upon authentication (past the first layer authentication using a user-password), the server sends an unused server token and the client replies with the corresponding client token

+ upon exhausting all tokens, the server generates a new set of tokens and securely give the client a copy of those newly issued tokens

+ transaction tokens are used as a second layer of authentication usually for accessing highly sensitive tools/information like in online banking money transfer where some banks use a form of transaction tokens called TAN (Transaction Authentication Number) to authenticate every transfer

+ tokens can be generated in a json format (ready to be sent to the client) or a map object which can then be optionally transformed into json format

## [cryptography](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security/cryptography)

### structure

| class | explanation |
| ----- | ----------- |
| [AsymmetricEncryptionInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/cryptography/AsymmetricEncryptionInl.java) | has inline static method for asymmetric encryption (using public and private keys) like [rsa 1024](https://en.wikipedia.org/wiki/RSA_(cryptosystem)) |
| [PasswordHashingInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/cryptography/PasswordHashingInl.java) | is a one-way encryption method used to encrypt senstive data like passwords in a way that can't be decrypted; offers various hashing algorithms like [`saltedMd5`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/cryptography/PasswordHashingInl.java#L142), [`sha`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/cryptography/PasswordHashingInl.java#L234), [`pbkdf1`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/cryptography/PasswordHashingInl.java#L307), ... |
| [TwoWayEncryptionInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/security/cryptography/TwoWayEncryptionInl.java) | offers two-way encrypting (encrypt/decrypt using a password) like [aes](https://en.wikipedia.org/wiki/Advanced_Encryption_Standard) |

### usage templates

+ asymmetric encrypting using RSA-1024

```java
  // generate a pair of public and private keys
  Pair<PublicKey, PrivateKey> rsaKey =
    AsymmetricEncryptionInl.generateRsaKey();

  String toEncrypt = "Hello World!";

  // encrypt
  byte[] encrypted =
    AsymmetricEncryptionInl.encryptRsa(
      toEncrypt,
      rsaKey.getFirst() );

  System.out.println(
    "Encrypted:\n  "
    + Arrays.toString(encrypted) );
  // prints -->
  //   Encrypted:
  //     [124, 85, -15, 103, -23, 37, -124, 102, -76, -70, 80, -2, -15, 71,
  //      58, 89, 50, 31, -72, -84, -84, -28, 115, -5, -32, -30, 115, 14, 46,
  //      110, 121, 36, -11, 46, 14, 33, -11, 68, -61, 107, 4, 105, -66, 94,
  //      75, 77, 52, -113, -63, 10, 49, 125, 27, 27, 12, 6, -7, 53, -123,
  //      -81, -58, 75, -89, 54, -107, 1, 7, 60, 92, -128, -4, 47, 86, 106,
  //      96, -32, 50, 117, -109, 54, 27, -52, 52, -33, 70, 91, 94, -39, 69,
  //      65, 52, -57, -76, -126, 64, 102, 42, -25, -39, -2, 78, -76, -62,
  //      -89, 60, 91, -97, -121, 3, -22, -42, -87, 18, -62, -80, 27, 37, 83,
  //      49, 53, 17, 86, 98, 110, -30, -97, 12, 1]

  // decrypt
  System.out.println(
    "Decrypted:\n  "
    + AsymmetricEncryptionInl.decryptRsa(
        encrypted,
        rsaKey.getSecond() ) );
  // prints -->
  //   Decrypted:
  //     Hello World!
```

+ password hashing example using salted-MD5

```java
  // generate salt
  byte[] salt = PasswordHashingInl.getSalt();

  String password = "myPassword123";

  // generate salted-MD5
  System.out.println(
    "Hashed:\n  "
    + PasswordHashingInl.saltedMd5(password, salt).getFirst() );
  // prints -->
  //   Hashed:
  //     3de3fff81f98ce98fc11ad57c3fc2ba3
```

+ two-way encryption example using AES

```java
  String key = "myKey123";
  String secretMessage = "My secret message.";

  // encrypt
  String aesEncrypted =
    TwoWayEncryptionInl.encryptAes(
      key,
      secretMessage);

  System.out.println(
    "Encrypted:\n  "
    + aesEncrypted);
  // prints -->
  //   Encrypted:
  //     B66Oi3QAlzGsnrgE7g4MInDuaJ0ncl16tUEfppdECuI=

  // decrypt
  System.out.println(
    "Decrypted:\n  "
    + TwoWayEncryptionInl.decryptAes(
        key,
        aesEncrypted) );
  // prints -->
  //   Decrypted:
  //     My secret message.
```
