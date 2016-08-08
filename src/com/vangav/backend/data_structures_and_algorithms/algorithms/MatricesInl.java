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

package com.vangav.backend.data_structures_and_algorithms.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * MatricesInl: some algorithms related to matrices (inline static methods)
 * */
public class MatricesInl {

  // disable default instantiation
  private MatricesInl () {}

  /**
   * spiralOrder
   * @param matrix
   * @return
   *   e.g.:
   *   input:
   *   [
   *    [ 1, 2, 3 ],
   *    [ 4, 5, 6 ],
   *    [ 7, 8, 9 ]
   *   ]
   *   output:
   *   [1, 2, 3, 6, 9, 8, 7, 4, 5]
   * @throws Exception
   */
  public static List<Integer> snakeOrder (int[][] matrix) throws Exception {
    
    List<Integer> result = new ArrayList<Integer>();
    
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
     
      return result;
    }

    int m = matrix.length;
    int n = matrix[0].length;

    int left = 0;
    int right = n - 1;
    int top = 0;
    int bottom = m - 1;

    while (result.size() < m * n) {
      
      for (int j = left; j <= right; j ++) {
        
        result.add(matrix[top][j] );
      }
      
      top ++;

      for (int i = top; i <= bottom; i ++) {
        
        result.add(matrix[i][right] );
      }
      
      right --;

      // prevent duplicate row
      if (bottom < top) {
       
        break;
      }

      for (int j = right; j >= left; j --) {
        
        result.add(matrix[bottom][j] );
      }
      
      bottom --;

      // prevent duplicate column
      if (right < left) {
       
        break;
      }

      for (int i = bottom; i >= top; i --) {
        
        result.add(matrix[i][left] );
      }
      
      left ++;
    }

    return result;
  }
  
  /**
   * generateSpiral
   * @param n
   * @return
   *   e.g.:
   *   n = 4
   *   output:
   *   [
   *     [1,   2,  3, 4], 
   *     [12, 13, 14, 5], 
   *     [11, 16, 15, 6], 
   *     [10,  9,  8, 7]
   *   ]
   * @throws Exception
   */
  public static int[][] generateSpiral (int n) throws Exception {
    
    int total = n * n;
    int[][] result = new int[n][n];

    int x = 0;
    int y = 0;
    int step = 0;

    for (int i = 0; i < total;) {
    
      while (y + step < n) {
      
        i++;
        result[x][y] = i;
        y++;
      }
      
      y --;
      x ++;

      while (x + step < n) {
        
        i ++;
        result[x][y] = i;
        x ++;
      }
      
      x --;
      y --;

      while (y >= 0 + step) {
        
        i ++;
        result[x][y] = i;
        y --;
      }
      
      y ++;
      x --;
      step ++;

      while (x >= 0 + step) {
        
        i ++;
        result[x][y] = i;
        x --;
      }
      
      x ++;
      y ++;
    }

    return result;
  }

  /**
   * binarySearch
   * @param matrix: sorted matrix
   *          e.g.:
   *          [
   *            [1,   3,  5,  7],
   *            [10, 11, 16, 20],
   *            [23, 30, 34, 50]
   *          ]
   * @param target
   * @return true if target is in matrix and false otherwise
   * @throws Exception
   */
  public static boolean binarySearch (
    int[][] matrix,
    int target) throws Exception {
    
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
     
      return false;
    }

    int m = matrix.length;
    int n = matrix[0].length;

    int start = 0;
    int end = m * n - 1;

    while (start <= end) {
      
      int mid = (start + end) / 2;
      int midX = mid / n;
      int midY = mid % n;

      if (matrix[midX][midY] == target) {
       
        return true;
      }

      if (matrix[midX][midY] < target) {
        
        start = mid + 1;
      } else {
        
        end = mid - 1;
      }
    }

    return false;
  }

  /**
   * rotate
   * rotates matrix 90 degrees clockwise - in-place
   * @param matrix
   * @throws Exception
   */
  public static void rotate (int[][] matrix) throws Exception {
    
    int n = matrix.length;
    
    for (int i = 0; i < n / 2; i ++) {
    
      for (int j = 0; j < Math.ceil(((double) n) / 2.); j ++) {
      
        int temp = matrix[i][j];
        matrix[i][j] = matrix[n - 1 - j][i];
        matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
        matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
        matrix[j][n - 1 - i] = temp;
      }
    }
  }
  
  /**
   * wordSearch
   * searches for a word in a matrix where the word's letter can be adjacent
   *   horizontally or vertically
   * e.g.:
   * [
   *   ['A', 'B', 'C', 'E'],
   *   ['S', 'F', 'C', 'S'],
   *   ['A', 'D', 'E', 'E']
   * ]
   * word = "ABCCED", -> returns true,
   * word = "SEE",    -> returns true,
   * word = "ABCB",   -> returns false.
   * @param board
   * @param word
   * @return true is word is found and false otherwise
   * @throws Exception
   */
  public static boolean wordSearch (
    char[][] board,
    String word) throws Exception {
    
    int m = board.length;
    int n = board[0].length;

    boolean result = false;
    
    for (int i = 0; i < m; i ++) {
    
      for (int j = 0; j < n; j ++) {
      
        if (dfs(board, word, i, j, 0) ) {
        
          result = true;
        }
      }
    }

    return result;
  }

  /**
   * dfs
   * depth first search
   * helper method for wordSearch
   * @param board
   * @param word
   * @param i
   * @param j
   * @param k
   * @return true if word if found (starting at [i, j] ) and false otherwise
   * @throws Exception
   */
  private static boolean dfs (
    char[][] board,
    String word,
    int i,
    int j,
    int k) throws Exception {
    
    int m = board.length;
    int n = board[0].length;

    if (i < 0 || j < 0 || i >= m || j >= n) {
    
      return false;
    }

    if (board[i][j] == word.charAt(k) ) {
      
      char temp = board[i][j];
      board[i][j] = '#';
      
      if (k == word.length() - 1) {
      
        return true;
      } else if (dfs(board, word, i - 1, j, k + 1) ||
                 dfs(board, word, i + 1, j, k + 1) ||
                 dfs(board, word, i, j - 1, k + 1) ||
                 dfs(board, word, i, j + 1, k + 1) ) {
        
        return true;
      }
      board[i][j] = temp;
    }

    return false;
  }
}
