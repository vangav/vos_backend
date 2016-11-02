# geo

+ This package contains geographical-related services/utilities.

+ [EarthConstantsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/EarthConstantsInl.java) represents Earth's geo-constants (i.e.: radius, latitude-range, etc ...).

### [geo_grids](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/geo/geo_grids)

+ Any map can be divided into a matrix of Geo Grids.
+ A map is by default Earth's map but can be anything through [GeoGridsConfig](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGridsConfig.java):
  + Earth's map
  + Any planet's map - yes works on Mars
  + Any sub-map (e.g.: Cairo, Paris, ...)
+ Each grid can have any size (e.g.: 4.5 metres, 1.2 km, 2 miles, 15 feet, etc ...) through [gridDimension](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGridsConfig.java#L113)
+ A [GeoGrid](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java) offers some operations like:
  + [`Distance getDistance (GeoGrid geoGrid)`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java#L260)
  + [`LineSegment getLine (GeoGrid geoGrid)`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java#L308)
  + `getSurroundingGrids` offers all the variations of from-to levels using multiple methods
    + [`public GeoGrid[][] getSurroundingGrids ()`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java#L330)
    + [`GeoGrid[][] getSurroundingGrids (int levels)`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java#L352)
    + [`ArrayList<ArrayList<GeoGrid> > getSurroundingGridsLevels (int startLevel, int endLevel, boolean includeOutOfRangeGrids)`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java#L423)
    + [`ArrayList<GeoGrid> getSurroundingGridsLevel (int level, boolean includeOutOfRangeGrids)`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java#L473)
    
| Class | Explanation |
| ----- | ----------- |
| [GeoCoordinates](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoCoordinates.java) | Represents a geo-coordinate point (latitude, longitude and altitude). |
| [GeoGrid](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java) | Represents a single grid in a geo-grids system. |
| [GeoGridId](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGridId.java) | Represents a [GeoGrid](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java) id (can also be used a hash-key like in a HashMap). |
| [GeoGridIndex2D](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGridIndex2D.java) | Represents the 2-D index of a [GeoGrid](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGrid.java) in a gro-grids system. |
| [GeoGridsConfig](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/geo_grids/GeoGridsConfig.java) | Represents a geo-grids system's config. By default uses Earth's geo-system from [EarthConstantsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/geo/EarthConstantsInl.java). |

