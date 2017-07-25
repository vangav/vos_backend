
> **why?** geographical utilities are useful for many reasons like getting a coordinates country, city, ..., clustering the map into grids or geo hashes for indexing, ...

# geo

## structure

| class/pkg | explanation |
| --------- | ----------- |
| [EarthConstantsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/EarthConstantsInl.java) | represents earth's geo-constants (i.e.: radius, latitude-range, map dimensions, ...) |
| [geo_grids](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo/geo_grids) | divides a map into square grids of any size and provide various operations on those grids like: coordinates to grid, grid to coordinates, multi-level surrounding grids, ... |
| [reverse_geo_coding](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo/reverse_geo_coding) | given a latitude-longitude pair it finds: continent name, continent code, country name, country code, major city name and city name |
| [geo_hash](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo/third_party/geo_hash) | represents the [geohash](https://en.wikipedia.org/wiki/Geohash)  geocoding system invented by "Gustavo Niemeyer" |

## [geo_grids](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo/geo_grids)

### structure
    
| class | explanation |
| ----- | ----------- |
| [GeoCoordinates](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoCoordinates.java) | represents a geo-coordinate point (latitude, longitude and altitude) |
| [GeoGrid](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java) | represents a single grid in a geo-grids system and has all the functionalities provided by [geo_grids](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo/geo_grids) |
| [GeoGridId](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGridId.java) | represents a [GeoGrid](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java) id (can also be used a hash-key like in a HashMap) |
| [GeoGridIndex2D](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGridIndex2D.java) | represents the 2-D index of a [GeoGrid](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java) in a gro-grids system |
| [GeoGridsConfig](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGridsConfig.java) | represents a geo-grids system's config; by default uses earth's geo-system from [EarthConstantsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/EarthConstantsInl.java); this provides the flexibility to work on sub-earth maps (e.g.: the map of Paris) or other cosmic objects' maps (e.g: mars, earth's moon, ...); [`gridDimension`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGridsConfig.java#L160) defines each grid's side (e.g.: 4.5 metres, 1.2 km, 2 miles, 15 feet, ...) |












### [geo_grids](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo/geo_grids)

+ Any map can be divided into a matrix of Geo Grids.
+ Each grid can have any size (e.g.: 4.5 metres, 1.2 km, 2 miles, 15 feet, etc ...) through [gridDimension](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGridsConfig.java#L113)
+ A [GeoGrid](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java) offers some operations like:
  + [`Distance getDistance (GeoGrid geoGrid)`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java#L260)
  + [`LineSegment getLine (GeoGrid geoGrid)`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java#L308)
  + `getSurroundingGrids` offers all the variations of from-to levels using multiple methods
    + [`public GeoGrid[][] getSurroundingGrids ()`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java#L330)
    + [`GeoGrid[][] getSurroundingGrids (int levels)`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java#L352)
    + [`ArrayList<ArrayList<GeoGrid> > getSurroundingGridsLevels (int startLevel, int endLevel, boolean includeOutOfRangeGrids)`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java#L423)
    + [`ArrayList<GeoGrid> getSurroundingGridsLevel (int level, boolean includeOutOfRangeGrids)`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java#L473)

+ Usage example
```java
// init config
GeoGridsConfig geoGridsConfig =
  new GeoGridsConfig(
    "Earth",
    new Distance(
      123.456,
      DistanceUnitType.NAUTICAL_MILE).getAs(DistanceUnitType.METRE) );

// pick coordinates
GeoCoordinates geoCoordinates =
  new GeoCoordinates(
    29.9792,
    31.1342);

// init a geo grid
GeoGrid geoGrid =
  new GeoGrid(
    geoGridsConfig,
    geoCoordinates);

// print grid's id
System.out.println(
  "Geo Grid Id: " + geoGrid.getGeoGridId().getId() );
// prints --> Geo Grid Id: 10665

// get level-2 surrounding grids
ArrayList<GeoGrid> levelTwoGrids =
  geoGrid.getSurroundingGridsLevel(
    2,
    true);

// print level-2 surrounding grids
for (GeoGrid levelTwoGeoGrid : levelTwoGrids) {

  System.out.println(
    "  " + levelTwoGeoGrid.getGeoGridId().getId() );
}
// prints --> (shaped to visualize the level)
//   10305  10306  10307  10308  10309
//   10484                       10488
//   10663        [10665]        10667
//   10842                       10846
//   11021  11022  11023  11024  11025
```

### [reverse_geo_coding](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo/reverse_geo_coding)

+ Used to convert coordinates (latitude, longitude) into Continent, Country, Major City and City. Usage example can be found in [vos_geo_server](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L111), and here's another example.

```java
ReverseGeoCode reverseGeoCode =
  ReverseGeoCoding.i().getReverseGeoCode(
    29.9792,
    31.1342);

System.out.println(reverseGeoCode.toString() );
// prints -->
//   city (Al Jīzah)
//   major city(Gizeh)
//   country code(EG)
//   country(Egypt)
//   continent code(AF)
//   continent(Africa)
```

### [geo_hash](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo/third_party/geo_hash)

+ A [Geohash](https://en.wikipedia.org/wiki/Geohash) lib developed by "Silvio Heuberger" and distributed under Apache License 2.0.

+ One usage example is in [vos_geo_server](https://github.com/vangav/vos_geo_server/blob/master/app/com/vangav/vos_geo_server/controllers/reverse_geo_code/HandlerReverseGeoCode.java#L103)
```java
// get geo hash
String geoHash =
  GeoHash.geoHashStringWithCharacterPrecision(
    requestReverseGeoCode.latitude,
    requestReverseGeoCode.longitude,
    12);
```
