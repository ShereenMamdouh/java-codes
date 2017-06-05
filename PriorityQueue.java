/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sherin
 */


public interface PriorityQueue {
    
    void insert( Comparable x );
    Comparable findMin( );
    Comparable deleteMin( );
    boolean isEmpty( );
    void makeEmpty( );
    int size( );
     
    /**
     * Change the value of the item stored in the pairing heap.
     * This is considered an advanced operation and might not
     * be supported by all priority queues. A priority queue
     * will signal its intention to not support decreaseKey by
     * having insert return null consistently.
     * @param p any non-null Position returned by insert.
     * @param newVal the new value, which must be smaller
     *    than the currently stored value.
     * @throws IllegalArgumentException if p invalid.
     * @throws UnsupportedOperationException if appropriate.
     */
  //  void decreaseKey( Position p, Comparable newVal );
}


