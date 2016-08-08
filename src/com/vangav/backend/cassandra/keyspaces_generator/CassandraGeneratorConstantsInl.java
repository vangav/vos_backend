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

package com.vangav.backend.cassandra.keyspaces_generator;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * GeneratorConstants contains constants shared by all generators
 *   (e.g.: keyspaces directory path, etc ...)
 * */
public class CassandraGeneratorConstantsInl {
  
  // disable default instantiation
  private CassandraGeneratorConstantsInl () {}

  public static final char kCassandraCommentPrefix = '#';
  public static final String kCassandraKeyspaceExt = ".keyspace";
  protected static final String kCassandraPackageName = "cassandra_keyspaces";
  protected static final String kCassandraAssetsDirName = "cassandra";
}
