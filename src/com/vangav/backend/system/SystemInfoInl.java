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

package com.vangav.backend.system;

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
    
      sb.append(String.format("%02X%s",
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
   * getTotalMemory
   * @return total memory available for the JVM
   * @throws Exception
   */
  public static long getTotalMemory () throws Exception {
    
    return Runtime.getRuntime().totalMemory();
  }
  
  /**
   * getFreeMemory
   * @return free memory available for the JVM
   * @throws Exception
   */
  public static long getFreeMemory () throws Exception {
    
    return Runtime.getRuntime().freeMemory();
  }
}
