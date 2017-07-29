# data structures and algorithms

### [algorithms](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/data_structures_and_algorithms/algorithms)

| class | explanation |
| ----- | ----------- |
| [DuplicatesInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/data_structures_and_algorithms/algorithms/DuplicatesInl.java) | checks for and/or removes duplicates from data structures |
| [MatricesInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/data_structures_and_algorithms/algorithms/MatricesInl.java) | martix operations like rotate, search, traverse, ... |
| [StringsAndArraysInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/data_structures_and_algorithms/algorithms/StringsAndArraysInl.java) | strings and arrays operations like reverse-polish-notation, palindrome, binary-operations, longest-common-prefix, ... |
| [SubsetInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/data_structures_and_algorithms/algorithms/SubsetInl.java) | checks if a data structure is subset of another |
| [SubtractInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/data_structures_and_algorithms/algorithms/SubtractInl.java) | subtracts one data structure from another |

#### example from [StringsAndArraysInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/data_structures_and_algorithms/algorithms/StringsAndArraysInl.java)

+ using [simplifyPath](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/data_structures_and_algorithms/algorithms/StringsAndArraysInl.java#L474)

```java
  System.out.println(
    StringsAndArraysInl.simplifyPath("/a/./b/../../c/") );
  // prints --> /c
```

### [heap](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/data_structures_and_algorithms/heap)

| class | explanation |
| ----- | ----------- |
| [HeapNode](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/data_structures_and_algorithms/heap/HeapNode.java) | represents a heap's node |
| [MaxHeap](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/data_structures_and_algorithms/heap/MaxHeap.java) | represents a [max-heap](https://en.wikipedia.org/wiki/Min-max_heap) |

### [third_party/kd_tree/](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/data_structures_and_algorithms/third_party/kd_tree)

+ third party representation of [k-d trees](https://en.wikipedia.org/wiki/K-d_tree), developed by "Daniel Glasson" and distributed under [MIT License](https://opensource.org/licenses/MIT)

### [tuple](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/data_structures_and_algorithms/tuple)

has representations of [Pair](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/data_structures_and_algorithms/tuple/Pair.java), [Triple](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/data_structures_and_algorithms/tuple/Triple.java) and [Quadruple](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/data_structures_and_algorithms/tuple/Quadruple.java); all of them are generic and hashable (e.g.: can be used as keys in a hash table)
