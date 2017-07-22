
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
