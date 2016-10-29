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

package com.vangav.backend.exceptions;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * BadRequestException represents exceptions caused by API calls
 * */
public class BadRequestException extends VangavException {

  /**
   * Generated serial version ID
   */
  private static final long serialVersionUID = -8910926874385881113L;

  /**
   * @return returns the exception type
   * */
  @Override
  public ExceptionType getExceptionType () {
    
    return ExceptionType.BAD_REQUEST_EXCEPTION;
  }
  
  /**
   * Constructor BadRequestException
   * @param message: exception's message
   * @param exceptionClass: defines the exact problem
   *          (e.g.: authentication, validation, etc ...)
   * @return new BadRequestException Object
   * */
  public BadRequestException (
    final String message,
    final ExceptionClass exceptionClass) {
    
    super(
      VangavException.kDefaultCode,
      VangavException.kDefaultSubCode,
      message,
      exceptionClass);
  }

  public BadRequestException (
    final int code,
    final String message,
    final ExceptionClass exceptionClass) {
    
    super(
      code,
      VangavException.kDefaultSubCode,
      message,
      exceptionClass);
  }

  public BadRequestException (
    final int code,
    final int subCode,
    final String message,
    final ExceptionClass exceptionClass) {
    
    super(
      code,
      subCode,
      message,
      exceptionClass);
  }
}
