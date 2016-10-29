/**
 * "First, solve the problem. Then, write the code. -John Johnson"
 * "Or use Vangav M"
 * www.vangav.com
 * */

/**
 * MIT License
 *
 * Copyright (c) 2016 Vangav
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
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

package com.vangav.backend.system;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * SystemInfoInl: static inline methods for getting info about the system
 *   on which this process is running
 * */
public class SystemInfoInl {

  // disable default instantiation
  private SystemInfoInl () {}

  /**
   * enum OsType: Operating System type (Max, Unix, etc ...)
   */
  public enum OsType {

    UNKNOWN_TYPE,
    WINDOWS,
    MAC,
    UNIX,
    SOLARIS
  }
  
  /**
   * getOsType
   * @return Operating System type (Max, Unix, etc ...)
   * @throws Exception
   */
  public static OsType getOsType () throws Exception {
    
    String os = System.getProperty("os.name").toLowerCase();
    
    if (os.indexOf("win") >= 0) {
      
      return OsType.WINDOWS;
    } else if (os.indexOf("mac") >= 0) {
      
      return OsType.MAC;
    } else if (os.indexOf("nix") >= 0 ||
               os.indexOf("nux") >= 0 ||
               os.indexOf("aix") > 0 ) {
      
      return OsType.UNIX;
    } else if (os.indexOf("sunos") >= 0) {
      
      return OsType.SOLARIS;
    }
    
    return OsType.UNKNOWN_TYPE;
  }
  
  /**
   * getUserName
   * @return user name of the user under who's security context is JVM running
   * @throws Exception
   */
  public static String getUserName () throws Exception {
    
    return System.getProperty("user.name");
  }
  
  /**
   * getLocalMachineHostName
   * @return host name of the machine running this JVM
   * @throws Exception
   */
  public static String getLocalMachineHostName () throws Exception {
    
    return InetAddress.getLocalHost().getHostName();
  }
  
  /**
   * getMacAddress
   * @return this machine's mac address (e.g.: 00-26-B9-9B-61-BF)
   * @throws Exception
   */
  public static String getMacAddress () throws Exception {

    InetAddress ip = InetAddress.getLocalHost();

    NetworkInterface network = NetworkInterface.getByInetAddress(ip);

    byte[] mac = network.getHardwareAddress();

    StringBuilder sb = new StringBuilder();
    
    for (int i = 0; i < mac.length; i ++) {
    
      sb.append(
        String.format(
          "%02X%s",
          mac[i],
          (i < mac.length - 1) ? "-" : "") );
    }
    
    return sb.toString();
  }
  
  /**
   * getProcessorCoresCount
   * @return the number of processor cores
   * @throws Exception
   */
  public static int getProcessorCoresCount () throws Exception {
    
    return Runtime.getRuntime().availableProcessors();
  }
  
  /**
   * getCpuUsage
   * NOTE: takes few seconds before it works correctly
   *       it takes some time to initialize some of the objects/values
   * @return percentage of CPU usage with 1 decimal point precision
   * @throws Exception
   */
  public static double getCpuUsage () throws Exception {

    MBeanServer mbs =
      ManagementFactory.getPlatformMBeanServer();
    ObjectName name =
      ObjectName.getInstance("java.lang:type=OperatingSystem");
    AttributeList list =
      mbs.getAttributes(name, new String[]{"ProcessCpuLoad"} );

    if (list.isEmpty() ) {
     
      return Double.NaN;
    }

    Attribute att = (Attribute)list.get(0);
    Double value  = (Double)att.getValue();

    // usually takes a couple of seconds before we get real values
    if (value == -1.0) {
      
      return Double.NaN;
    }
    
    // returns a percentage value with 1 decimal point precision
    return ((int)(value * 1000) / 10.0);
  }
  
  /**
   * enum ByteMetric lists storage metric types (byte, kilobyte, megabyte, ...)
   * */
  public enum ByteMetric {
    
    BYTE,
    KILO_BYTE,
    MEGA_BYTE,
    GIGA_BYTE,
    TERA_BYTE,
    PETA_BYTE,
    EXA_BYTE,
    ZETTA_BYTE,
    YOTTA_BYTE;

    private static final long kByteMetricConversionFactor = 1024;
    private long convertFromBytes (long bytes) throws Exception {
      
      for (int i = 0; i < this.ordinal(); i ++) {
        
        bytes /= kByteMetricConversionFactor;
      }
      
      return bytes;
    }
  }
  
  /**
   * getTotalRam
   * @return total RAM available for the JVM in bytes
   * @throws Exception
   */
  public static long getTotalRam () throws Exception {
    
    return Runtime.getRuntime().totalMemory();
  }
  
  /**
   * getTotalRam
   * @param byteMetric
   * @return total RAM available for the JVM
   * @throws Exception
   */
  public static long getTotalRam (ByteMetric byteMetric) throws Exception {
    
    return byteMetric.convertFromBytes(Runtime.getRuntime().totalMemory() );
  }
  
  /**
   * getFreeRam
   * @return free RAM available for the JVM in bytes
   * @throws Exception
   */
  public static long getFreeRam () throws Exception {
    
    return Runtime.getRuntime().freeMemory();
  }
  
  /**
   * getFreeRam
   * @return free RAM available for the JVM
   * @throws Exception
   */
  public static long getFreeRam (ByteMetric byteMetric) throws Exception {
    
    return byteMetric.convertFromBytes(Runtime.getRuntime().freeMemory() );
  }
  
  /**
   * getTotalDisk
   * @return total disk space in bytes
   * @throws Exception
   */
  public static long getTotalDisk () throws Exception {
    
    return new File("/").getTotalSpace();
  }
  
  /**
   * getTotalDisk
   * @param byteMetric
   * @return total disk space
   * @throws Exception
   */
  public static long getTotalDisk (ByteMetric byteMetric) throws Exception {
    
    return byteMetric.convertFromBytes(new File("/").getTotalSpace() );
  }
  
  /**
   * getFreeDisk
   * @return free disk space in bytes
   * @throws Exception
   */
  public static long getFreeDisk () throws Exception {
    
    return new File("/").getFreeSpace();
  }
  
  /**
   * getFreeDisk
   * @param byteMetric
   * @return free disk space
   * @throws Exception
   */
  public static long getFreeDisk (ByteMetric byteMetric) throws Exception {
    
    return byteMetric.convertFromBytes(new File("/").getFreeSpace() );
  }
  
  /**
   * getUsableDisk
   * @return usable disk space in bytes
   * @throws Exception
   */
  public static long getUsableDisk () throws Exception {
    
    return new File("/").getUsableSpace();
  }
  
  /**
   * getUsableDisk
   * @param byteMetric
   * @return usable disk space
   * @throws Exception
   */
  public static long getUsableDisk (ByteMetric byteMetric) throws Exception {
    
    return byteMetric.convertFromBytes(new File("/").getUsableSpace() );
  }
}
