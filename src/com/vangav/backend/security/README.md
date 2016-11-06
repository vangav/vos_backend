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

### [cryptography](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/security/cryptography)

+ Usage example
```java
// Asymmetric encrypting example using RSA-1024
    
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

// Password hashing example using salted-MD5
//   check PasswordHashingInl for more hashing algorithms

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

// two-way encryption example using AES

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


