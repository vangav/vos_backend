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

package com.vangav.backend.public_apis.facebook.json.edges;

import com.vangav.backend.exceptions.CodeException;
import com.vangav.backend.exceptions.VangavException.ExceptionClass;
import com.vangav.backend.public_apis.facebook.json.edges.albums.Albums;
import com.vangav.backend.public_apis.facebook.json.edges.books.Books;
import com.vangav.backend.public_apis.facebook.json.edges.edge.FacebookGraphApiEdge;
import com.vangav.backend.public_apis.facebook.json.edges.family.Family;
import com.vangav.backend.public_apis.facebook.json.edges.feed.Feed;
import com.vangav.backend.public_apis.facebook.json.edges.friends.Friends;
import com.vangav.backend.public_apis.facebook.json.edges.games.Games;
import com.vangav.backend.public_apis.facebook.json.edges.invitable_friends.InvitableFriends;
import com.vangav.backend.public_apis.facebook.json.edges.likes.Likes;
import com.vangav.backend.public_apis.facebook.json.edges.movies.Movies;
import com.vangav.backend.public_apis.facebook.json.edges.music.Music;
import com.vangav.backend.public_apis.facebook.json.edges.permissions.Permissions;
import com.vangav.backend.public_apis.facebook.json.edges.photos.Photos;
import com.vangav.backend.public_apis.facebook.json.edges.taggable_friends.TaggableFriends;
import com.vangav.backend.public_apis.facebook.json.edges.tagged_places.TaggedPlaces;
import com.vangav.backend.public_apis.facebook.json.edges.television.Television;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * enum FacebookGraphApiEdgeType represents Facebook's Graph API handled
 *   user-edges
 * */
public enum FacebookGraphApiEdgeType {

  ALBUMS,
  BOOKS,
  FAMILY,
  FEED,
  FRIENDS,
  GAMES,
  INVITABLE_FRIENDS,
  LIKES,
  MOVIES,
  MUSIC,
  PERMISSIONS,
  PHOTOS,
  TAGGABLE_FRIENDS,
  TAGGED_PLACES,
  TELEVISION;
  
  /**
   * getName
   * @return string value for this type for Facebook Graph API query
   * @throws Exception
   */
  public String getName () throws Exception {
    
    return this.toString().toLowerCase();
  }
  
  /**
   * getNewEdgeInstance
   * @return the corresponding FacebookGraphApiEdge new Object to this type
   * @throws Exception
   */
  public FacebookGraphApiEdge getNewEdgeInstance () throws Exception {
    
    switch (this) {
      
      case ALBUMS:
        return new Albums();
      case BOOKS:
        return new Books();
      case FAMILY:
        return new Family();
      case FEED:
        return new Feed();
      case FRIENDS:
        return new Friends();
      case GAMES:
        return new Games();
      case INVITABLE_FRIENDS:
        return new InvitableFriends();
      case LIKES:
        return new Likes();
      case MOVIES:
        return new Movies();
      case MUSIC:
        return new Music();
      case PERMISSIONS:
        return new Permissions();
      case PHOTOS:
        return new Photos();
      case TAGGABLE_FRIENDS:
        return new TaggableFriends();
      case TAGGED_PLACES:
        return new TaggedPlaces();
      case TELEVISION:
        return new Television();

      default:
        throw new CodeException(
          153,
          19,
          "Unhandled type ["
            + this.toString()
            + "]",
          ExceptionClass.TYPE);
    }
  }
}
