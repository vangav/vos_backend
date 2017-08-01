
> **why?** our ability to intuitively come up with great solutions (algorithms) is orders of magnitude faster than the time it takes to turn them into executable algorithms; so we developed [vangav mighty](http://vangav.com/) which provides a natural way to explain a solution and it takes care of generating 100% of the code

# [vangav mighty](http://vangav.com/)

+ this package has the source code for the built-in utility [vangav_m_json_client.jar](https://github.com/vangav/vos_geo_server/tree/master/vangav_m) which gets added to each vangav backend service like in [instagram jobs](https://github.com/vangav/vos_instagram_jobs/tree/master/vangav_m)

## usage example

+ this section explains how vangav mighty is used to rank posts in [instagram jobs](https://github.com/vangav/vos_instagram_jobs) service

### 1. [describe the solution](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/VangavMPostsRank.mlang)

#### header

+ [solution type and name](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/VangavMPostsRank.mlang#L2)

```json
  "solution_type": "JAVA_JAR",
  "name": "VangavMPostsRank"
```

#### inputs

+ [post hour](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/VangavMPostsRank.mlang#L7]: since ranking happens every hour, we will give more recent ones a slight boost

```json
  {
    "name": "PostHour",
    "min": 0,
    "max": 23,
    "mid": 11.5
  }
```

+ [likes and comments](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/VangavMPostsRank.mlang#L13): more likes/comments = higher rank - `mid` is at 1% to get an even rank distribution as most of the users get ~1% of the likes/comments a celebrty gets

```json
  {
    "name": "PostLikesCount",
    "min": 0,
    "max": 1000,
    "mid": 10
  },
  {
    "name": "PostCommentsCount",
    "min": 0,
    "max": 1000,
    "mid": 10
  }
```

+ [user's registration date](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/VangavMPostsRank.mlang#L24): during the first 60 days after registration, a user's posts get a slight boost (the newer they are the more boost they get)

```json
  {
    "name": "UserRegisteredSinceDays",
    "min": 0,
    "max": 60,
    "mid": 30
  }
```

+ [user's activity and fame](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/VangavMPostsRank.mlang#L30): recently active users (more posts) and more famous (more follows, likes, ...) get a boost; last week's data is used to make it a fair game

```json
  {
    "name": "UserFollowCountLastWeek",
    "min": 0,
    "max": 1000,
    "mid": 10
  },
  {
    "name": "UserUnfollowCountLastWeek",
    "min": 0,
    "max": 1000,
    "mid": 10
  },
  {
    "name": "UserPostsCountLastWeek",
    "min": 0,
    "max": 100,
    "mid": 10
  },
  {
    "name": "UserLikesCountLastWeek",
    "min": 0,
    "max": 1000,
    "mid": 10
  },
  {
    "name": "UserCommentsCountLastWeek",
    "min": 0,
    "max": 1000,
    "mid": 10
  }
```






