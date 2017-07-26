
> **why?** metrics like distance and time are important for various reasons like: distance in commuting apps, geo-grid dimensions, time till an authentication token expires, ...

# metrics

this package is responsible for handling all metrics-related operations

## [distance](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/metrics/distance)

### structure

| class | explanation |
| ----- | ----------- |
| [Distance](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/distance/Distance.java) | is the main class represting a distance in multiple units and provides various distance and arithmetic operations |
| [DistanceUnitType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/distance/DistanceUnitType.java) | is an enum representing all supported distance units like: `METRE`, `MILE`, `INCH`, ... |
| [DistanceConversionFactorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/distance/DistanceConversionFactorInl.java) | is a look-up-table for the conversion factors between all [DistanceUnitType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/distance/DistanceUnitType.java) |

### usage example

+ from [instagram / Constants](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/common/Constants.java#L260)

```java
  kGeoGridsConfig =
    new GeoGridsConfig(
      "vos_instagram",
      new Distance(
        kGeoGridDimensionMetres,
        DistanceUnitType.METRE) );
```

## [time](https://github.com/vangav/vos_backend/tree/master/src/com/vangav/backend/metrics/time)

### structure

| class | explanation |
| ----- | ----------- |
| [Period](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/Period.java) | represents a period of time in multiple units (e.g.: 4.2 milli seconds, 5.4 weeks, 0.2 century, ...) and provides various period and arithmetic operations |
| [TimeUnitType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/TimeUnitType.java) | is an enum representing all supported time units like: `NANOSECOND`, `DAY`, `DECADE`, ... |
| [TimeConversionFactorInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/TimeConversionFactorInl.java) | is a look-up-table for the conversion factors between all [TimeUnitType](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/TimeUnitType.java) |
| [TimeOperationsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/TimeOperationsInl.java) | has inline static methods for time-related operations like [`getElapsedUnixTime`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/TimeOperationsInl.java#L75) |
| [CalendarAndDateOperationsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/CalendarAndDateOperationsInl.java) | has inline static methods for [Calendar](https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html) and [Date](https://docs.oracle.com/javase/8/docs/api/java/util/Date.html) operations like: [`getFormattedTimes`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/CalendarAndDateOperationsInl.java#L308), [`getMonthCalendarRange`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/CalendarAndDateOperationsInl.java#L474), [`getDatesFromTo`](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/CalendarAndDateOperationsInl.java#L737), ... |
| [RoundedOffCalendarInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/RoundedOffCalendarInl.java) | has inline static methods for rounding off calendars (e.g.: round to yesterday @ 00:00, round to next monday, ...); useful in various scenarios like periodic jobs fetching data indexed per-da, per-hour, ... |

### usage examples

+ initializing a [Period](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/Period.java) in [instagram / Constants](https://github.com/vangav/vos_instagram/blob/master/app/com/vangav/vos_instagram/common/Constants.java#L107)

```java
  /**
   * kAccessTokenLifeTime represents the life time of an access token before
   *   it expires and has to be refreshed
   */
  public static final Period kAccessTokenLifeTime;
  static {
    
    try {
      
      kAccessTokenLifeTime =
        new Period(
          ConstantsProperties.i().getDoubleProperty(
            ConstantsProperties.kAccessTokenLifeTimeValue),
          TimeUnitType.valueOf(
            ConstantsProperties.i().getStringPropterty(
              ConstantsProperties.kAccessTokenLifeTimeUnit) ) );
    } catch (Exception e) {
      
      throw new CodeException(
        300,
        2,
        "Couldn't initialize kAccessTokenLifeTime: "
          + VangavException.getExceptionStackTrace(e),
        ExceptionClass.INITIALIZATION);
    }
  }
```

+ using [CalendarAndDateOperationsInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/CalendarAndDateOperationsInl.java) in [instagram jobs / PostsRank](https://github.com/vangav/vos_instagram_jobs/blob/master/app/com/vangav/vos_instagram_jobs/periodic_jobs/posts_rank/PostsRank.java#L270)

```java
  // get post's hour
  postHour =
    (double)CalendarAndDateOperationsInl.getCalendarFromUnixTime(
      currPostTime).get(Calendar.HOUR_OF_DAY);
```

+ using [RoundedOffCalendarInl](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/metrics/time/RoundedOffCalendarInl.java) in [instagram jobs / RestJobs](https://github.com/vangav/vos_instagram_jobs/blob/master/app/com/vangav/vos_instagram_jobs/periodic_jobs/rest_jobs/RestJobs.java#L90) to sync the priodic job to run on the exact start of each hour

```java
  /**
   * Constructor - RestJobs
   * @return new RestJobs Object
   * @throws Exception
   */
  public RestJobs () throws Exception {

    super(
      "rest_jobs",
      PeriodicJob.Type.ASYNC,
      RoundedOffCalendarInl.getRoundedCalendar( // sync by the exact hour
        RoundingType.PAST,
        RoundingFactor.HOUR_OF_DAY),
      new Period(
        1.0,
        TimeUnitType.HOUR) );
  }
```
