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

package com.vangav.backend.public_apis.car2go;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * enum LocationType represents car2go'2 API locations
 * */
public enum LocationType {

  AUSTIN,
  HAMBURG,
  VANCOUVER,
  AMSTERDAM,
  WIEN,
  SAN_DIEGO,
  WASHINGTON_DC,
  PORTLAND,
  BERLIN,
  TORONTO,
  CALGARY,
  STUTTGART,
  RHEINLAND,
  MILANO,
  SEATTLE,
  DENVER,
  MONTREAL,
  MÜNCHEN,
  TWIN_CITIES,
  COLUMBUS,
  ROMA,
  FRANKFURT,
  NEW_YORK_CITY,
  MADRID,
  FIRENZE,
  STOCKHOLM,
  TORINO;
  
  /**
   * getName
   * @return string value for this type for car2go API query
   * @throws Exception
   */
  public String getName () throws Exception {
    
    switch (this) {
      
      case AUSTIN:
        return "Austin";
      case HAMBURG:
        return "Hamburg";
      case VANCOUVER:
        return "Vancouver";
      case AMSTERDAM:
        return "Amsterdam";
      case WIEN:
        return "Wien";
      case SAN_DIEGO:
        return "SanDiego";
      case WASHINGTON_DC:
        return "WashingtonDC";
      case PORTLAND:
        return "Portland";
      case BERLIN:
        return "Berlin";
      case TORINO:
        return "Torino";
      case CALGARY:
        return "Calgary";
      case STUTTGART:
        return "Stuttgart";
      case RHEINLAND:
        return "Rheinland";
      case MILANO:
        return "Milano";
      case SEATTLE:
        return "Seattle";
      case DENVER:
        return "Denver";
      case MONTREAL:
        return "Montreal";
      case MÜNCHEN:
        return "Muenchen";
      case TWIN_CITIES:
        return "TwinCities";
      case COLUMBUS:
        return "Columbus";
      case ROMA:
        return "Roma";
      case FRANKFURT:
        return "Frankfurt";
      case NEW_YORK_CITY:
        return "NewYorkCity";
      case MADRID:
        return "Madrid";
      case FIRENZE:
        return "Firenze";
      case STOCKHOLM:
        return "Stockholm";
      case TORONTO:
        return "Toronto";
      default:
        throw new CodeException(
          153,
          5,
          "Invalid LocationType ["
            + this.toString()
            + "]",
          ExceptionClass.TYPE);
    }
  }
  
  /**
   * getLocationId
   * @return location's id
   * @throws Exception
   */
  public int getLocationId () throws Exception {
    
    switch (this) {
      
      case AUSTIN:
        return 2;
      case HAMBURG:
        return 3;
      case VANCOUVER:
        return 4;
      case AMSTERDAM:
        return 5;
      case WIEN:
        return 7;
      case SAN_DIEGO:
        return 8;
      case WASHINGTON_DC:
        return 9;
      case PORTLAND:
        return 11;
      case BERLIN:
        return 12;
      case TORINO:
        return 44;
      case CALGARY:
        return 15;
      case STUTTGART:
        return 18;
      case RHEINLAND:
        return 19;
      case MILANO:
        return 20;
      case SEATTLE:
        return 21;
      case DENVER:
        return 24;
      case MONTREAL:
        return 25;
      case MÜNCHEN:
        return 26;
      case TWIN_CITIES:
        return 29;
      case COLUMBUS:
        return 30;
      case ROMA:
        return 31;
      case FRANKFURT:
        return 33;
      case NEW_YORK_CITY:
        return 34;
      case MADRID:
        return 36;
      case FIRENZE:
        return 37;
      case STOCKHOLM:
        return 39;
      case TORONTO:
        return 13;
      default:
        throw new CodeException(
          153,
          5,
          "Invalid LocationType ["
            + this.toString()
            + "]",
          ExceptionClass.TYPE);
    }
  }
}
