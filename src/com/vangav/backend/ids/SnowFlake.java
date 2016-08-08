/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * no license, I know you already got more than enough to worry about
 * keep going, never give up
 * */

/**
 * Community
 * Facebook Group: Vangav Open Source - Backend
 *   fb.com/groups/575834775932682/
 * Facebook Page: Vangav
 *   fb.com/vangav.f
 * 
 * Third party communities for Vangav Backend
 *   - play framework
 *   - cassandra
 *   - datastax
 *   
 * Tag your question online (e.g.: stack overflow, etc ...) with
 *   #vangav_backend
 *   to easier find questions/answers online
 * */

package com.vangav.backend.ids;

import java.net.InetAddress;
import java.net.NetworkInterface;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * SnowFlake was developed by twitter as a way to have distributed universal
 *   sequential ID generation - thanks twitter
 * SnowFlake is a way to generate universally unique sequential IDs represented
 *   by 64-bit positive numbers (JAVA long)
 * The ID's 64-bits are divided as follows
 *   - 41 bits for time stamp (works well till 2080) (millisecond precision)
 *   - 10 bits for machine ID (machine's mac address) (up to 1024 instances)
 *   - 12 bits for sequence (for IDs generated within the same time stamp)
 *       (allows for 4096 IDs per millisecond per instance)
 * Can be distributed on multiple machines with the following constrains:
 *   a) one instance per machine (using machine's mac address as instance ID,
 *        otherwise modify the way the instance's ID is set to allow for
 *        multiple instances per machine)
 *   b) all machines running SnowFlake must use the same NTP time server so
 *        that the time stamp part in IDs stays in sync
 * Collision resistant. In case of generating all possible 4096 IDs within one
 *   millisecond, it waits till the next millisecond to generate the next ID.
 */
public class SnowFlake {

  private final long sequenceBits = 12;
  private final long machineIdBits = 10L;
  private final long maxMachineId = -1L ^ (-1L << machineIdBits);

  private final long machineIdShift = sequenceBits;
  private final long timeStampLeftShift = sequenceBits + machineIdBits;

  private final long customEpoch = 1288834974657L;
  private final long machineId;
  private final long sequenceMax = 4096;

  private volatile long lastTimestamp = -1L;
  private volatile long sequence = 0L;

  private static  SnowFlake instance;

  /**
   * custom
   * @return static singleton instance of SnowFlake
   * @throws Exception
   */
  public static  SnowFlake i () throws Exception {
    
    if (instance == null) {
     
      instance = new SnowFlake();
    }
    
    return instance;
  }

  /**
   * Constructor SnowFlake
   * @return new SnowFlake Object
   * @throws Exception
   */
  private  SnowFlake () throws Exception {
    
    this.machineId = this.getMachineId();
    
    if (this.machineId > this.maxMachineId || this.machineId < 0) {

      throw new CodeException(
        "machineId > maxMachineId (maximum number of allowed instances is ["
        + this.maxMachineId
        + "] )",
        ExceptionClass.UNAUTHORIZED);
    }
  }

  /**
   * getNewId
   * @return new SnowFlake 64-bits ID
   * @throws Exception
   */
  public synchronized long getNewId () throws Exception {
    
    long timestamp = System.currentTimeMillis();
    
    if (timestamp < this.lastTimestamp) {
      
      throw new CodeException(
        "Clock moved backwards. Can't generate an out of sequence ID for "
        + "time stamp ["
        + (this.lastTimestamp - timestamp)
        + "milliseconds] "
        + "double check that instances is in sync with its NTP time server",
        ExceptionClass.UNAUTHORIZED);
    }
    
    if (this.lastTimestamp == timestamp) {
    
      this.sequence = (this.sequence + 1) % this.sequenceMax;
      
      if (this.sequence == 0) {
      
        timestamp = this.tilNextMillis(this.lastTimestamp);
      }
    } else {
      
      this.sequence = 0;
    }
    
    this.lastTimestamp = timestamp;
    
    Long id = ((timestamp - this.customEpoch) << this.timeStampLeftShift) |
              (this.machineId << this.machineIdShift) | this.sequence;
    
    return id;
  }

  /**
   * tilNextMillis
   * used to wait till the next millisecond to avoid IDs collision
   *   (i.e.: in case of trying to generate more than 4096 IDs within one
   *            millisecond)
   * @param lastTimestamp
   * @return next valid time stamp
   */
  private long tilNextMillis (long lastTimestamp) {
    
    long timestamp = System.currentTimeMillis();
    
    while (timestamp <= lastTimestamp) {
    
      timestamp = System.currentTimeMillis();
    }
    
    return timestamp;
  }

  /**
   * getMachineId
   * @return this machine's 10-bits ID
   * @throws Exception
   */
  private long getMachineId () throws Exception {

    InetAddress ip = InetAddress.getLocalHost();
    NetworkInterface network = NetworkInterface.getByInetAddress(ip);
    long id;
    
    if (network == null) {
    
      id = 1;
    } else {
      
      byte[] mac = network.getHardwareAddress();
      
      id = ((0x000000FF & (long) mac[mac.length - 1] ) |
            (0x0000FF00 & (((long) mac[mac.length - 2] ) << 8) ) ) >> 6;
    }
    
    return id;
  }
}
