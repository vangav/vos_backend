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

package com.vangav.backend.data_structures_and_algorithms.heap;

/**
 * @author mustapha
 * fb.com/mustapha.abdallah
 */
/**
 * MaxHeap is an implementation of maximum heap
 * */
public class MaxHeap {

  private HeapNode[] heapArray;
  //size of array
  private int maxSize;
  //number of HeapNodes in array
  private int currentSize;

  /**
   * Constructor MaxHeap
   * @param maxSize: heap's maximum size
   * @return new MaxHeap Object
   * @throws Exception
   */
  public MaxHeap (int maxSize) throws Exception {
    
    this.maxSize = maxSize;
    this.currentSize = 0;
    this.heapArray = new HeapNode[maxSize];
  }

  /**
   * isEmpty
   * check's if the heap is empty
   * @return true if the heap is empty and false otherwise
   */
  public boolean isEmpty () {
    
    return this.currentSize == 0;
  }

  /**
   * insert
   * insert a new node into the heap
   * @param node: node to insert
   * @return true if insertion is a success and false if the heap is full
   * @throws Exception
   */
  public boolean insert (HeapNode node) throws Exception {
    
    if (this.currentSize == this.maxSize) {
     
      return false;
    }
    
    this.heapArray[this.currentSize] = node;
    trickleUp(this.currentSize ++);
    
    return true;
  } 

  /**
   * trickleUp
   * e.g.: a new node is inserted at the bottom of the heap by default then
   *   this method is called to move it up to its correct position in the heap
   * @param index
   * @throws Exception
   */
  private void trickleUp (int index) throws Exception {
    
    int parent = (index - 1) / 2;
    HeapNode bottom = this.heapArray[index];

    while (index > 0 && this.heapArray[parent].getValue() < bottom.getValue() ) {
      
      // move it down
      this.heapArray[index] = this.heapArray[parent];
      index = parent;
      parent = (parent - 1) / 2;
    }
    
    this.heapArray[index] = bottom;
  }

  /**
   * remove
   * deletes the node with the maximum key from the heap
   * @return deleted node
   * @throws Exception
   */
  public HeapNode remove () throws Exception {
    
    // (assumes non-empty list)
    HeapNode root = this.heapArray[0];
    this.heapArray[0] = this.heapArray[--this.currentSize];
    trickleDown(0);
    
    return root;
  }

  /**
   * trickleDown
   * e.g.: when a node is deleted, this method moves the nodes in the heap
   *   to retain the maximum heap structure after that node is deleted
   * @param index: the index of the node to be trickled
   */
  private void trickleDown (int index) {
    
    int largerChild;
    // save root
    HeapNode top = this.heapArray[index];
    
    // while HeapNode has at
    while (index < this.currentSize / 2) {
      
      // least one child,
      int leftChild = 2 * index + 1;
      int rightChild = leftChild + 1;
      
      // find larger child
      if (rightChild < this.currentSize &&
          this.heapArray[leftChild].getValue() <
          this.heapArray[rightChild].getValue() ) {
       
        largerChild = rightChild;
      } else {
      
        largerChild = leftChild;
      }
      
      // top >= largerChild?
      if (top.getValue() >= this.heapArray[largerChild].getValue() ) {
        
        break;
      }
      
      // shift child up
      this.heapArray[index] = this.heapArray[largerChild];
      index = largerChild; // go down
    }
    
    this.heapArray[index] = top; // root to index
  }

  /**
   * updateValue
   * update nodes[index]'s value
   * @param index: index of the node to update
   * @param newValue: new node's value
   * @return true if update was successful and false otherwise
   * @throws Exception
   */
  public boolean updateValue (int index, double newValue) throws Exception {
    
    if (index < 0 || index >= this.currentSize) {
     
      return false;
    }
    
    // remember old
    double oldValue = this.heapArray[index].getValue();
    // change to new
    this.heapArray[index].setValue(newValue);

    if (oldValue < newValue) {
      
      trickleUp(index); 
    } else {
     
      trickleDown(index);
    }
    
    return true;
  }

  @Override
  public String toString() {
    
    StringBuffer stringBuffer = new StringBuffer();
    
    stringBuffer.append("heapArray: "); // array format
    
    for (int m = 0; m < currentSize; m++) {
      
      if (this.heapArray[m] != null) {
      
        stringBuffer.append(this.heapArray[m].getValue() + " ");
      } else {
        
        stringBuffer.append("-- ");
      }
    }
    
    int nBlanks = 32;
    int itemsPerRow = 1;
    int column = 0;
    int j = 0; // current item

    // for each heap item
    while (this.currentSize > 0) {
      
      // first item in row?
      if (column == 0) {
        
        for (int k = 0; k < nBlanks; k ++) {
        
          // preceding blanks
          stringBuffer.append(' ');
        }
      }
      
      // display item
      stringBuffer.append(this.heapArray[j].getValue() );

      // done?
      if (++j == currentSize) {
       
        break;
      }

      // end of row?
      if (++column == itemsPerRow) {
        
        nBlanks /= 2; // half the blanks
        itemsPerRow *= 2; // twice the items
        column = 0; // start over on
        stringBuffer.append(System.lineSeparator() ); //    new row
      } else {
       
        // next item on row
        for (int k = 0; k < nBlanks * 2 - 2; k++) {
         
          stringBuffer.append(' '); // interim blanks
        }
      }
    }
    
    return stringBuffer.toString();
  }
}
