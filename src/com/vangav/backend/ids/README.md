# ids

### [SequentialIds](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SequentialIds.java)

+ Generates sequential IDs based on unix time and a counter.
+ Generates up to 100 million unique IDs per second.
+ Throws an exception on collision (collision resistant).
+ Generates unique IDs only per-instance.
+ Works well until year 4857.
+ Uses JAVA Long (19 digits) 11 digits for unix time and 8 digits for counter (e.g.: 0146619060100000017 --> unix time = 1466190601 and counter = 17).
+ NOTE: this class depends on system time, a change in the system time affects the generated ids and can potentially result in out-of-sequence ids for a period of time.

### [SnowFlake](https://github.com/vangav/vos_backend/blob/master/src/com/vangav/backend/ids/SnowFlake.java)

+ SnowFlake was developed by twitter as a way to have distributed universal sequential ID generation - thanks twitter.
+ SnowFlake is a way to generate universally unique sequential IDs represented by 64-bit positive numbers (JAVA long).
+ The ID's 64-bits are divided as follows:
  + 41 bits for time stamp (works well till 2080) (millisecond precision).
  + 10 bits for machine ID (machine's mac address) (up to 1024 instances).
  + - 12 bits for sequence (for IDs generated within the same time stamp).
+ Allows for 4096 IDs per millisecond per instance.
+ Can be distributed on multiple machines with the following constrains:
  + One instance per machine (using machine's mac address as instance ID, otherwise modify the way the instance's ID is set to allow for multiple instances per machine).
  + All machines running SnowFlake must use the same NTP time server so that the time stamp part in IDs stays in sync.
+ Collision resistant. In case of generating all possible 4096 IDs within one millisecond, it waits till the next millisecond to generate the next ID.
