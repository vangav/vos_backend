
# compression

+ this package has all vangav backend's built-in compression/decompression utilities

+ usage example for [LempelZivWelchInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/compression/LempelZivWelchInl.java):
```java
  List<Integer> compressed =
    LempelZivWelchInl.compress("Hello World!");
  
  System.out.println(
    "Compressed: " + Arrays.toString(compressed.toArray() ) );
  // prints --> Compressed: [72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100, 33]
  
  System.out.println(
    "Decompressed: " + LempelZivWelchInl.decompress(compressed) );
  // prints --> Decompressed: Hello World!
```

+ usage example for [RunLengthEncodingInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/compression/RunLengthEncodingInl.java):
```java
  String compressed =
    RunLengthEncodingInl.compress("aaabccccZZZZZZZZZZ");
    
  System.out.println(
    "Compressed: " + compressed);
  // prints --> Compressed: 3a1b4c10Z
  
  System.out.println(
    "Decompressed: " + RunLengthEncodingInl.decompress(compressed) );
  // prints --> Decompressed: aaabccccZZZZZZZZZZ
```

# next tutorial -> [content: verifiction/formatting/generation](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/content)
> handles content (code, phriction-wiki, text, ...) checking, formatting and generation; vangav backend relies on this package to verify generation config and format generated code, scripts, wiki, ...

# share

[![facebook share](https://www.prekindle.com/images/social/facebook.png)](https://www.facebook.com/sharer/sharer.php?u=https%3A//github.com/vangav/vos_backend)  [![twitter share](http://www.howickbaptist.org.nz/wordpress/media/twitter-64-black.png)](https://twitter.com/home?status=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster%20%7C%20https%3A//github.com/vangav/vos_backend)  [![pinterest share](http://d7ab823tjbf2qywyt3grgq63.wpengine.netdna-cdn.com/wp-content/themes/velominati/images/share_icons/pinterest-black.png)](https://pinterest.com/pin/create/button/?url=https%3A//github.com/vangav/vos_backend&media=https%3A//scontent-mad1-1.xx.fbcdn.net/v/t31.0-8/20645143_1969408006608176_5289565717021239224_o.png?oh=acf20113a3673409d238924cfec648d2%26oe=5A3414B5&description=)  [![google plus share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/google.png)](https://plus.google.com/share?url=https%3A//github.com/vangav/vos_backend)  [![linkedin share](http://e-airllc.com/wp-content/themes/nebula/images/social_black/linkedin.png)](https://www.linkedin.com/shareArticle?mini=true&url=https%3A//github.com/vangav/vos_backend&title=vangav%20backend%20%7C%20build%20big%20tech%2010x%20faster&summary=&source=)

# free consulting

[![vangav's consultant](http://www.footballhighlights247.com/images/mobile-share/fb-messenger-64x64.png)](https://www.facebook.com/mustapha.abdallah)
