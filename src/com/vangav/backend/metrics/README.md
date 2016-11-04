# metrics

This package is responsible for handling all metrics-related operations.

### [distance](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/metrics/distance)

[Distance](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/distance/Distance.java) represents a distance value in different units as defined in [DistanceUnitType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/distance/DistanceUnitType.java) (e.g.: KILOMETRE, YARD, NAUTICAL_MILE, ...). It also handles arithmetic-distance-operations, conversion, comparisons, ...

### [time](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/metrics/time)

+ Handles time-operations for time units as defined in [TimeUnitType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/TimeUnitType.java) from NANOSECOND to CENTURY.

+ [CalendarAndDateOperationsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/CalendarAndDateOperationsInl.java) is responsible for [java.util.Calendar](https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html) and [java.util.Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html).

+ [Period](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/Period.java) represents a period of time (e.g.: 3.7 MILLISECOND, 2 HOUR, 4.5 DECADE, ...). Also handles comparison, arithmetic, and conversion operations.

+ [TimeOperationsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/TimeOperationsInl.java) handles unix time operations.
