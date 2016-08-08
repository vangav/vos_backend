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

package com.vangav.backend.dispatcher;

import java.net.URLConnection;
import java.util.ArrayList;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.networks.rest.RestInl;
import com.vangav.backend.networks.rest.RestInl.RestCallType;
import com.vangav.backend.thread_pool.ThreadPool;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * Dispatcher is used to forward some of the work (like notifications, logging,
 *   etc ...) to other services to handle
 * Why is that useful? let's say there's a POST_PHOTO_PUBLIC request, the work
 *   done for that request can be:
 * 1- *Save photo in DataBase
 * 2- *Distribute photo to user's friends
 * 3- Distribute photo publicly to other users
 * 4- Push notify photo receiving users
 * 5- Save analysis data
 * 6- Save logs
 * 
 * 1 and 2 are the important steps that must happen before sending back
 *   response and must be finished super fast
 * 3 to 6 has low business impact and can take much more time
 * 
 * The dispatcher allows for allocating more resources to handle 1 and 2
 *   while allocating less resources to handle 3 to 6 so that usage spikes
 *   doesn't affect the user experience
 * */
public class Dispatcher {
  
  /**
   * DispatcherRunnable is the runnable class in which a group of dispatch
   *   messages (sharing the same url) get dispatched to corresponding worker
   *   service
   */
  private class DispatcherRunnable implements Runnable {
    
    private static final String kWorkerEntryPoint = "/Worker";

    private String dispatchMessages;
    private ArrayList<String> workersTopology;
    
    /**
     * Constructor DispatcherRunnable
     * @param url: worker service's url
     * @param dispatchMessages: messages to be dispatched
     * @return new DispatcherRunnable Object
     */
    public DispatcherRunnable (
      String dispatchMessages) {

      this.dispatchMessages = dispatchMessages;
      
      this.workersTopology = new ArrayList<String>();
      
      try {
      
        String propertiesWorkersTopology =
          DispatcherProperties.i().getStringPropterty(
            DispatcherProperties.kWorkersTopology);
        
        String[] topologyArr = propertiesWorkersTopology.split(",");
        
        for (String topologyEntry : topologyArr) {
          
          this.workersTopology.add(topologyEntry + kWorkerEntryPoint);
        }
      } catch (Exception e) {

        throw new CodeException(
          "propterty ["
            + DispatcherProperties.kWorkersTopology
            + "] isn't defined in properties file ["
            + DispatcherProperties.i().getName()
            + "]",
          ExceptionClass.PROPERTIES);
      }
    }
    
    /**
     * send the messages to be despatched in a POST http request to the
     *   worker service
     * NOTE: silent in case of failure, change as needed?
     * */
    @Override
    public void run () {
      
      for (String workerTopologyPoint : this.workersTopology) {
      
        try {
          
          URLConnection urlConnection = RestInl.restCall(
            workerTopologyPoint,
            RestCallType.POST,
            this.dispatchMessages);
          
          if (RestInl.isResponseStatusSuccess(urlConnection) == true) {
          
            break;
          }
        } catch (Exception e) {
          
        }
      }
    }
    
    @Override
    public String toString () {
      
      return
        "DispatcherRunnable:  dispatchMessages("
        + this.dispatchMessages
        + ")";
    }
  }
  
  // url -> list of dispatch messages
  private ArrayList<DispatchMessage> dispatchMessages;

  /**
   * Constructor Dispatcher
   * @return new Dispatcher Object
   */
  public Dispatcher () {
    
    this.dispatchMessages = new ArrayList<DispatchMessage>();
  }
  
  /**
   * addDispatchMessage
   * adds a new dispatch message to the dispatcher's buffer
   * @param url: url of the worker service that handles this type of
   *               dispatch message
   * @param dispatchMessage: the message to be dispatched
   * @throws Exception
   */
  public void addDispatchMessage (
    DispatchMessage dispatchMessage) throws Exception {
    
    this.dispatchMessages.add(dispatchMessage);
  }
  
  /**
   * dispatchMessages
   * dispatches all the dispatch messages in the dispatcher buffer to their
   *   corresponding worker services then clears the dispatcher buffer
   * @throws Exception
   */
  public void dispatchMessages () throws Exception {
    
    if (this.dispatchMessages.isEmpty() == true) {
      
      return;
    }
      
    ThreadPool.i().executeInDispatcherPool(
      new DispatcherRunnable(
        new DispatchMessages(this.dispatchMessages).toJsonString() ) );
    
    this.dispatchMessages.clear();
  }
  
  @Override
  public String toString () {
    
    return this.dispatchMessages.toString();
  }
}
