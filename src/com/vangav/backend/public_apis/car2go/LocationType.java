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
        return "San Diego";
      case WASHINGTON_DC:
        return "Washington DC";
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
        return "München";
      case TWIN_CITIES:
        return "Twin Cities";
      case COLUMBUS:
        return "Columbus";
      case ROMA:
        return "Roma";
      case FRANKFURT:
        return "Frankfurt";
      case NEW_YORK_CITY:
        return "New York City";
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
          "Invalid LocationType ["
            + this.toString()
            + "]",
          ExceptionClass.TYPE);
    }
  }
}
