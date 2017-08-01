
> **why?** our ability to intuitively come up with great solutions (algorithms) is orders of magnitude faster than the time it takes to turn them into executable algorithms; so we developed [vangav mighty](http://vangav.com/) which provides a natural way to explain a solution and it takes care of generating 100% of the code

# [vangav mighty](http://vangav.com/)

+ this package has the source code for the built-in utility [vangav_m_json_client.jar](https://github.com/vangav/vos_geo_server/tree/master/vangav_m) which gets added to each vangav backend service like in [instagram jobs](https://github.com/vangav/vos_instagram_jobs/tree/master/vangav_m)

## usage example

+ this section explains how vangav mighty is used to rank posts in [instagram jobs](https://github.com/vangav/vos_instagram_jobs) service

### 1. [describe/update the solution(s)](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/VangavMPostsRank.mlang)

+ add/remove/edit `.mlang` solution files any time under a service's [vangav_m/soltions](https://github.com/vangav/vos_instagram_jobs/tree/master/vangav_m/solutions) directory

#### header

+ [solution type and name](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/VangavMPostsRank.mlang#L2)

```json
  "solution_type": "JAVA_JAR",
  "name": "VangavMPostsRank"
```

#### inputs

+ [post hour](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/VangavMPostsRank.mlang#L7): since ranking happens every hour, we will give more recent ones a slight boost

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

+ [user's activity and fame](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/VangavMPostsRank.mlang#L30): recently active users (more posts) and more famous (more follows, likes, ...) get a boost; last week's data is used to make it a fair game - `mid` is at 1% to get an even rank distribution as most of the users get ~1% of the likes/comments a celebrty gets

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

#### output

+ [post rank](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/VangavMPostsRank.mlang#L65): the double representing the rank of each post

```json
  "outputs": [
    {
      "name": "PostRank",
      "min": 0,
      "max": 1000,
      "mid": 500
    }
  ]
```

#### relations

+ [relations](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/VangavMPostsRank.mlang#L71): shows how each input is directly/inversely proportional with its output(s); notice how weights are set to define the relative effect each relation has on the final output (e.g.: comments' effect on the rank is `twice` that of post-hour's)

```json
  "relations": [
    {
      "input_name": "PostHour",
      "output_name": "PostRank",
      "relation": "DIRECTLY_PROPORTIONAL",
      "weight": 1.0
    },
    {
      "input_name": "PostLikesCount",
      "output_name": "PostRank",
      "relation": "DIRECTLY_PROPORTIONAL",
      "weight": 1.5
    },
    {
      "input_name": "PostCommentsCount",
      "output_name": "PostRank",
      "relation": "DIRECTLY_PROPORTIONAL",
      "weight": 2.0
    },
    {
      "input_name": "UserRegisteredSinceDays",
      "output_name": "PostRank",
      "relation": "INVERSELY_PROPORTIONAL",
      "weight": 0.5
    },
    {
      "input_name": "UserFollowCountLastWeek",
      "output_name": "PostRank",
      "relation": "DIRECTLY_PROPORTIONAL",
      "weight": 1.0
    },
    {
      "input_name": "UserUnfollowCountLastWeek",
      "output_name": "PostRank",
      "relation": "INVERSELY_PROPORTIONAL",
      "weight": 1.0
    },
    {
      "input_name": "UserPostsCountLastWeek",
      "output_name": "PostRank",
      "relation": "DIRECTLY_PROPORTIONAL",
      "weight": 1.0
    },
    {
      "input_name": "UserLikesCountLastWeek",
      "output_name": "PostRank",
      "relation": "DIRECTLY_PROPORTIONAL",
      "weight": 1.0
    },
    {
      "input_name": "UserCommentsCountLastWeek",
      "output_name": "PostRank",
      "relation": "DIRECTLY_PROPORTIONAL",
      "weight": 1.0
    }
  ]
```

+ visit [vangav mighty](http://vangav.com/) to learn more about the rest of the features: [`inputs_multi_range`](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/VangavMPostsRank.mlang#L4), [`finite_inputs`](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/VangavMPostsRank.mlang#L61), [`outputs_multi_range`](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/VangavMPostsRank.mlang#L62) and [`relative_relations`](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/VangavMPostsRank.mlang#L127)

### 2. [generate/re-generate solution(s)](https://github.com/vangav/vos_instagram_jobs/tree/master/vangav_m)

1. go to vangav mighty's directory `cd vangav_m`
2. run the generator `java -jar vangav_m_json_client.jar`; running this command will do the following:
    + load available solutions (`.mlang` files under `vangav_m/solutions`
    + verify loaded solutions and notify/cancel if verification failed
    + clear old generated solutions (by deleting generated solution directories like [posts rank](https://github.com/vangav/vos_instagram_jobs/tree/master/vangav_m/solutions/generated_solutions/vangav_m_VangavMPostsRank) and their `jars` from the service's [lib](https://github.com/vangav/vos_instagram_jobs/tree/master/lib) directory)
    + generate/re-generate solutions
    + extract generated solutions (copy generated `jars` to the service's [lib](https://github.com/vangav/vos_instagram_jobs/tree/master/lib) directory)
    + clear old class path links (for vangav mighty solutions only) in `.classpath`
    + add class path links (for all generated vangav mighty solutions) in `.classpath`
    + clear: deletes any intermediate files/directories/scripts used during the generation/linking process

### 3. [use generated solution(s)](https://github.com/vangav/vos_instagram_jobs/blob/master/app/com/vangav/vos_instagram_jobs/periodic_jobs/posts_rank/PostsRank.java#L452)

+ [import](https://github.com/vangav/vos_instagram_jobs/blob/master/app/com/vangav/vos_instagram_jobs/periodic_jobs/posts_rank/PostsRank.java#L56) generated solution(s)

```java
  import vangav_m.vangavmpostsrank.VangavMPostsRank;
```

+ [get instance](https://github.com/vangav/vos_instagram_jobs/blob/master/app/com/vangav/vos_instagram_jobs/periodic_jobs/posts_rank/PostsRank.java#L236)

```java
  // get a new vangavMPostsRank instance
  VangavMPostsRank vangavMPostsRank = new VangavMPostsRank();
  
  // alternatively use the singleton instance
  // VangavMPostsRank.instance(). ...
```

+ [process](https://github.com/vangav/vos_instagram_jobs/blob/master/app/com/vangav/vos_instagram_jobs/periodic_jobs/posts_rank/PostsRank.java#L452) inputs into outputs

```java
  // calculate post's rank
       
  // 1- set input value(s)
  vangavMPostsRank.setPostHour(
    postHour);
  vangavMPostsRank.setPostLikesCount(
    postLikesCount);
  vangavMPostsRank.setPostCommentsCount(
    postCommentsCount);
  vangavMPostsRank.setUserRegisteredSinceDays(
    userRegisteredSinceDays);
  vangavMPostsRank.setUserFollowCountLastWeek(
    userFollowCountLastWeek);
  vangavMPostsRank.setUserUnfollowCountLastWeek(
    userUnfollowCountLastWeek);
  vangavMPostsRank.setUserPostsCountLastWeek(
    userPostsCountLastWeek);
  vangavMPostsRank.setUserLikesCountLastWeek(
    userLikesCountLastWeek);
  vangavMPostsRank.setUserCommentsCountLastWeek(
    userCommentsCountLastWeek);

  // 2- process input(s) into output(s)
  vangavMPostsRank.process();

  // 3- get output value(s)
  postRank = vangavMPostsRank.getPostRank();
```

## [generated solution structure](https://github.com/vangav/vos_instagram_jobs/tree/master/vangav_m/solutions/generated_solutions/vangav_m_VangavMPostsRank)

### structure

| file/directory | explanation |
| -------------- | ----------- |
| [vangav_m_VangavMPostsRank.jar](https://github.com/vangav/vos_instagram_jobs/tree/master/vangav_m/solutions/generated_solutions/vangav_m_VangavMPostsRank) | is the `lib` file containing the generated solution; gets linked and imported to use the solution as explained above in this tutorial |
| [vangav_m_VangavMPostsRank.README](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/generated_solutions/vangav_m_VangavMPostsRank/vangav_m_VangavMPostsRank.README) | represents a mini-tutorial specific to each generated solution; explained in depth later in this tutorial |
| [mlang](https://github.com/vangav/vos_instagram_jobs/tree/master/vangav_m/solutions/generated_solutions/vangav_m_VangavMPostsRank/mlang) | contains the `.mlang` file used to generate this solution; kept for reference |
| [example](https://github.com/vangav/vos_instagram_jobs/tree/master/vangav_m/solutions/generated_solutions/vangav_m_VangavMPostsRank/example) | contains a ready-to-run example for using the generated solution to further explain how to exactly use the generated solution; explained in depth later in this tutorial |

### [vangav_m_VangavMPostsRank.README](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/generated_solutions/vangav_m_VangavMPostsRank/vangav_m_VangavMPostsRank.README)

#### [solution info](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/generated_solutions/vangav_m_VangavMPostsRank/vangav_m_VangavMPostsRank.README#L13)

+ explains how to link/use the generated solution and has the solution's id for support requests

```
  /**
   * Solution: VangavMPostsRank
   * Solution UUID: e7ede357-712c-446b-9178-622309e272b6
   * REFER to example/VangavMPostsRankExample.java for a functional example of how to use this solution
   * Usage:
   * 1- link vangav_m_VangavMPostsRank.jar to your project
   *    in eclipse:
   *    a- copy vangav_m_VangavMPostsRank.jar to your project's lib directory
   *    b- right click project -> proerties -> Java Build Path
   *    c- libraries tab -> Add JARS...
   * 2- import vangav_m.vangavmpostsrank.*;
   * 3- set inputs --> VangavMPostsRank.setInputName(double value);
   * 4- repeat (3) for as many inputs needed for a processing step
   * 5- process --> VangavMPostsRank.process();
   * 6- get outputs --> double output = VangavMPostsRank.getOutputName();
   *      returns output's value or Double.NaN otherwise
   * */
```

#### [solution's api](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/generated_solutions/vangav_m_VangavMPostsRank/vangav_m_VangavMPostsRank.README#L32)

+ lists all the api methods provided by the generated solution

```
  /**
   * API:-

   * Static instance getter (recommended unless technically infeasible):
   * public static VangavMPostsRank instance ();

   * Constructor (for cases like using the same solution in different threads):
   * public VangavMPostsRank ();

   * Input Setters (multi-range input setters followed by single-range input setters):
   * -- single-range input setters
   * public void setPostHour (double value);
   * public void setPostLikesCount (double value);
   * public void setPostCommentsCount (double value);
   * public void setUserRegisteredSinceDays (double value);
   * public void setUserFollowCountLastWeek (double value);
   * public void setUserUnfollowCountLastWeek (double value);
   * public void setUserPostsCountLastWeek (double value);
   * public void setUserLikesCountLastWeek (double value);
   * public void setUserCommentsCountLastWeek (double value);


   * Process inputs into outputs:
   * public static void process();

   * Output Getters: (multi-range output getters followed by single-range output getters)
   * -- single-range output getters
   * public double getPostRank ();

   * */
```

### [example](https://github.com/vangav/vos_instagram_jobs/tree/master/vangav_m/solutions/generated_solutions/vangav_m_VangavMPostsRank/example)

| file | explanation |
| ---- | ----------- |
| [ExampleManifest.txt](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/generated_solutions/vangav_m_VangavMPostsRank/example/ExampleManifest.txt) | used when compiling the example |
| [compileExample.sh](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/generated_solutions/vangav_m_VangavMPostsRank/example/compileExample.sh) | used to compile the example into an executable `jar` |
| [VangavMPostsRankExample.java](https://github.com/vangav/vos_instagram_jobs/blob/master/vangav_m/solutions/generated_solutions/vangav_m_VangavMPostsRank/example/VangavMPostsRankExample.java) | contains a working example as well as the exact steps to follow to compile and run it |

# next

+ visit [vangav mighty](http://vangav.com/) for deeper explanation, more clients/languagrs, interactive examples, a multitude of configurable presets
