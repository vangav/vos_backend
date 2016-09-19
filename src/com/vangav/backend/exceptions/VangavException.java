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

package com.vangav.backend.exceptions;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * VangavException is the parent exception class for all exceptions
 * */
public abstract class VangavException extends RuntimeException {

  /**
   * Generated serial version ID
   */
  private static final long serialVersionUID = 2633993578900209129L;

  /**
   * ExceptionType
   * exception types for child classes
   * */
  public enum ExceptionType {
    
    BAD_REQUEST_EXCEPTION,
    CODE_EXCEPTION,
    DEFAULT_EXCEPTION
  }
  
  /**
   * getExceptionType
   * @return ExceptionType
   * */
  public abstract ExceptionType getExceptionType ();

  /**
   * ExceptionClass
   * sub-type for an exception defining the logical source of the exception
   * this helps classifying the exception
   * */
  public enum ExceptionClass {
    
    ARGUMENT,
    AUTHENTICATION,
    COMMUNICATION,
    CONVERSION,
    CASSANDRA,
    DEVICE,
    DUPLICATE,
    EXPIRED,
    FILE,
    FORMATTING,
    GATEWAY,
    INITIALIZATION,
    INVALID,
    JSON,
    MISSING_ITEM,
    MISSING_STEP,
    PROPERTIES,
    THROTTLING,
    TYPE,
    UNAUTHORIZED,
    UNHANDLED,
    UNIMPLEMENTED,
    
    UNKNOWN_TYPE
  }
  
  /**
   * exceptionFactory
   * makes a new Vangav Exception sub-class depending on the ExceptionType
   * @param message: exception's message
   * @param exceptionType: used to decide which sub-class Vangav Exception
   *                         to create
   * @param exceptionClass: sub-type of the exception (e.g.: authentication,
   *                          etc ...)
   * @return new Object of Vangav Exception sub-class
   */
  public static VangavException exceptionFactory (
    final String message,
    final ExceptionType exceptionType,
    final ExceptionClass exceptionClass) {
    
    if (exceptionType == ExceptionType.BAD_REQUEST_EXCEPTION) {
      
      return new BadRequestException(message, exceptionClass);
    }
    
    return new CodeException(message, exceptionClass);
  }

  /**
   * exceptionFactory
   * makes a new Vangav Exception sub-class depending on the ExceptionType
   * @param code: exception's code
   * @param message: exception's message
   * @param exceptionType: used to decide which sub-class Vangav Exception
   *                         to create
   * @param exceptionClass: sub-type of the exception (e.g.: authentication,
   *                          etc ...)
   * @return new Object of Vangav Exception sub-class
   */
  public static VangavException exceptionFactory (
    final int code,
    final String message,
    final ExceptionType exceptionType,
    final ExceptionClass exceptionClass) {
    
    if (exceptionType == ExceptionType.BAD_REQUEST_EXCEPTION) {
      
      return new BadRequestException(code, message, exceptionClass);
    }
    
    return new CodeException(code, message, exceptionClass);
  }

  /**
   * exceptionFactory
   * makes a new Vangav Exception sub-class depending on the ExceptionType
   * @param code: exception's code
   * @param sub_code: exception's sub-code
   * @param message: exception's message
   * @param exceptionType: used to decide which sub-class Vangav Exception
   *                         to create
   * @param exceptionClass: sub-type of the exception (e.g.: authentication,
   *                          etc ...)
   * @return new Object of Vangav Exception sub-class
   */
  public static VangavException exceptionFactory (
    final int code,
    final int subCode,
    final String message,
    final ExceptionType exceptionType,
    final ExceptionClass exceptionClass) {
    
    if (exceptionType == ExceptionType.BAD_REQUEST_EXCEPTION) {
      
      return new BadRequestException(code, subCode, message, exceptionClass);
    }
    
    return new CodeException(code, subCode, message, exceptionClass);
  }
  
  protected static final int kDefaultCode = 0;
  protected static final int kDefaultSubCode = 0;
  
  private int code;
  private int subCode;
  private String message;
  private ExceptionClass exceptionClass;
  private String stackTrace;

  /**
   * Constructor VangavException
   * @param code
   * @param subCode
   * @param message: exception's message
   * @param exceptionClass: exception's class
   */
  protected VangavException (
    final int code,
    final int subCode,
    final String message,
    final ExceptionClass exceptionClass) {
    
    super(exceptionClass.toString() + ": " + message);
    
    this.code = code;
    this.subCode = subCode;
    this.message = message;
    this.exceptionClass = exceptionClass;
    
    StringBuffer stackTraceBuff = new StringBuffer();
    
    for (StackTraceElement ste : Thread.currentThread().getStackTrace() ) {

      stackTraceBuff.append(ste + System.lineSeparator() );
    }
    
    this.stackTrace = stackTraceBuff.toString();
  }
  
  /**
   * Constructor VangavException
   */
  protected VangavException () {
    
    this.code = kDefaultCode;
    this.subCode = kDefaultSubCode;
    this.message = "";
    this.exceptionClass = ExceptionClass.UNKNOWN_TYPE;
    
    StringBuffer stackTraceBuff = new StringBuffer();
    
    for (StackTraceElement ste : Thread.currentThread().getStackTrace() ) {

      stackTraceBuff.append(ste + System.lineSeparator() );
    }
    
    this.stackTrace = stackTraceBuff.toString();
  }
  
  /**
   * getCode
   * @return this exception's code
   */
  public final int getCode () {
    
    return this.code;
  }
  
  /**
   * getSubCode
   * @return this exception's sub_code
   */
  public final int getSubCode () {
    
    return this.subCode;
  }
  
  @Override
  public final String getMessage () {
    
    return this.message;
  }
  
  /**
   * getExceptionClass
   * @return ExceptionClass
   */
  public final ExceptionClass getExceptionClass () {
    
    return this.exceptionClass;
  }
  
  /**
   * getExceptionStackTrace
   * @return String representation of the exception's stack trace
   */
  public final String getExceptionStackTrace () {
    
    return this.stackTrace;
  }
  
  /**
   * getExceptionStackTrace
   * @param e: exception
   * @return param e's stack trace
   */
  public static final String getExceptionStackTrace (Exception e) {
    
    StringBuffer stackTraceBuff = new StringBuffer();
    
    for (StackTraceElement ste : Thread.currentThread().getStackTrace() ) {

      stackTraceBuff.append(ste + System.lineSeparator() );
    }
    
    return stackTraceBuff.toString();
  }
  
  @Override
  public String toString () {
    
    return
      "VangavException:"
      + "\nexception type: "
      + this.getExceptionType().toString()
      + "\ncode: "
      + this.code
      + "\nsub_code: "
      + this.subCode
      + "\nmessage: "
      + this.message
      + "\nexception class: "
      + this.exceptionClass.toString()
      + "\nstack track: "
      + this.stackTrace;
  }
}
