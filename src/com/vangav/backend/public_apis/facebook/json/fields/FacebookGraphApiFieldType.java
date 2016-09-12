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

package com.vangav.backend.public_apis.facebook.json.fields;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.public_apis.facebook.json.fields.age_range.AgeRange;
import com.vangav.backend.public_apis.facebook.json.fields.cover.Cover;
import com.vangav.backend.public_apis.facebook.json.fields.currency.Currency;
import com.vangav.backend.public_apis.facebook.json.fields.devices.Devices;
import com.vangav.backend.public_apis.facebook.json.fields.education.Education;
import com.vangav.backend.public_apis.facebook.json.fields.favorite_athletes.FavoriteAthletes;
import com.vangav.backend.public_apis.facebook.json.fields.favorite_teams.FavoriteTeams;
import com.vangav.backend.public_apis.facebook.json.fields.hometown.Hometown;
import com.vangav.backend.public_apis.facebook.json.fields.languages.Languages;
import com.vangav.backend.public_apis.facebook.json.fields.location.Location;
import com.vangav.backend.public_apis.facebook.json.fields.sports.Sports;
import com.vangav.backend.public_apis.facebook.json.fields.work.Work;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * enum FacebookGraphApiFieldType represents Facebook's Graph API handled
 *   user-fields
 * */
public enum FacebookGraphApiFieldType {

  AGE_RANGE,
  COVER,
  CURRENCY,
  DEVICES,
  EDUCATION,
  FAVORITE_ATHLETES,
  FAVORITE_TEAMS,
  HOMETOWN,
  LANGUAGES,
  LOCATION,
  SPORTS,
  WORK,
  
  ABOUT,
  BIO,
  BIRTHDAY,
  EMAIL,
  FIRST_NAME,
  GENDER,
  ID,
  INTERESTED_IN,
  IS_VERIFIED,
  LAST_NAME,
  LINK,
  LOCALE,
  MEETING_FOR,
  MIDDLE_NAME,
  NAME,
  NAME_FORMAT,
  QUOTES,
  RELATIONSHIP_STATUS,
  THIRD_PARTY_ID,
  TIMEZONE,
  VERIFIED;
  
  /**
   * getName
   * @return string value for this type for Facebook Graph API query
   * @throws Exception
   */
  public String getName () throws Exception {
    
    return this.toString().toLowerCase();
  }
  
  /**
   * getNewFieldInstance
   * @return the corresponding FacebookGraphApiField new Object to this type
   * @throws Exception
   */
  public FacebookGraphApiField getNewFieldInstance () throws Exception {
    
    switch (this) {
      
      case AGE_RANGE:
        return new AgeRange();
      case COVER:
        return new Cover();
      case CURRENCY:
        return new Currency();
      case DEVICES:
        return new Devices();
      case EDUCATION:
        return new Education();
      case FAVORITE_ATHLETES:
        return new FavoriteAthletes();
      case FAVORITE_TEAMS:
        return new FavoriteTeams();
      case HOMETOWN:
        return new Hometown();
      case LANGUAGES:
        return new Languages();
      case LOCATION:
        return new Location();
      case SPORTS:
        return new Sports();
      case WORK:
        return new Work();
      case ABOUT:
        return new About();
      case BIO:
        return new Bio();
      case BIRTHDAY:
        return new Birthday();
      case EMAIL:
        return new Email();
      case FIRST_NAME:
        return new FirstName();
      case GENDER:
        return new Gender();
      case ID:
        return new Id();
      case INTERESTED_IN:
        return new InterestedIn();
      case IS_VERIFIED:
        return new IsVerified();
      case LAST_NAME:
        return new LastName();
      case LINK:
        return new Link();
      case LOCALE:
        return new Locale();
      case MEETING_FOR:
        return new MeetingFor();
      case MIDDLE_NAME:
        return new MiddleName();
      case NAME:
        return new Name();
      case NAME_FORMAT:
        return new NameFormat();
      case QUOTES:
        return new Quotes();
      case RELATIONSHIP_STATUS:
        return new RelationshipStatus();
      case THIRD_PARTY_ID:
        return new ThirdPartyId();
      case TIMEZONE:
        return new Timezone();
      case VERIFIED:
        return new Verified();

      default:
        throw new CodeException(
          "Unhandled type ["
            + this.toString()
            + "]",
          ExceptionClass.TYPE);
    }
  }
}
