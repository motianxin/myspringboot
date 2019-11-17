/******************************************************************************
 *  Compilation:  javac MaxPQ.java
 *  Execution:    java MaxPQ < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/24pq/tinyPQ.txt
 *
 *  Generic max priority queue implementation with a binary heap.
 *  Can be used with a comparator instead of the natural order,
 *  but the generic Key type must still be Comparable.
 *
 *  % java MaxPQ < tinyPQ.txt 
 *  Q X P (6 left on pq)
 *
 *  We use a one-based array to simplify parent and child calculations.
 *
 *  Can be optimized by replacing full exchanges with half exchanges
 *  (ala insertion sort).
 *
 ******************************************************************************/

package edu.princeton.cs.algs4.sort;

import edu.princeton.cs.algs4.base.StdIn;
import edu.princeton.cs.algs4.base.StdOut;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code MaxPQ} class represents a priority queue of generic keys.
 * It supports the usual <em>insert</em> and <em>delete-the-maximum</em>
 * operations, along with methods for peeking at the maximum key,
 * testing if the priority queue is empty, and iterating through
 * the keys.
 * <p>
 * This implementation uses a binary heap.
 * The <em>insert</em> and <em>delete-the-maximum</em> operations take
 * logarithmic amortized time.
 * The <em>max</em>, <em>size</em>, and <em>is-empty</em> operations take constant time.
 * Construction takes time proportional to the specified capacity or the number of
 * items used to initialize the data structure.
 * <p>
 * For additional documentation, see <a href="https://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @param <Key> the generic type of key on this priority queue
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */

public class MaxPQ<Key> implements Iterable<Key> {
    private Key[] pq;                    // store items at indices 1 to n
    private int n;                       // number of items on priority queue
    private Comparator<Key> comparator;  // optional comparator

    /**
     * Initializes an empty priority queue with the given initial capacity.
     *
     * @param initCapacity the initial capacity of this priority queue
     */
    public MaxPQ(int initCapacity) {
        this.pq = (Key[]) new Object[initCapacity + 1];
        this.n = 0;
    }

    /**
     * Initializes an empty priority queue.
     */
    public MaxPQ() {
        this(1);
    }

    /**
     * Initializes an empty priority queue with the given initial capacity,
     * using the given comparator.
     *
     * @param initCapacity the initial capacity of this priority queue
     * @param comparator the order in which to compare the keys
     */
    public MaxPQ(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        this.pq = (Key[]) new Object[initCapacity + 1];
        this.n = 0;
    }

    /**
     * Initializes an empty priority queue using the given comparator.
     *
     * @param comparator the order in which to compare the keys
     */
    public MaxPQ(Comparator<Key> comparator) {
        this(1, comparator);
    }

    /**
     * Initializes a priority queue from the array of keys.
     * Takes time proportional to the number of keys, using sink-based heap construction.
     *
     * @param keys the array of keys
     */
    public MaxPQ(Key[] keys) {
        this.n = keys.length;
        this.pq = (Key[]) new Object[keys.length + 1];
        for (int i = 0; i < this.n; i++) {
            this.pq[i + 1] = keys[i];
        }
        for (int k = this.n / 2; k >= 1; k--) {
            sink(k);
        }
        assert isMaxHeap();
    }

    /**
     * Unit tests the {@code MaxPQ} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        MaxPQ<String> pq = new MaxPQ<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                pq.insert(item);
            } else if (!pq.isEmpty()) {
                StdOut.print(pq.delMax() + " ");
            }
        }
        StdOut.println("(" + pq.size() + " left on pq)");
    }

    /**
     * Returns true if this priority queue is empty.
     *
     * @return {@code true} if this priority queue is empty;
     * {@code false} otherwise
     */
    public boolean isEmpty() {
        return this.n == 0;
    }

    /**
     * Returns the number of keys on this priority queue.
     *
     * @return the number of keys on this priority queue
     */
    public int size() {
        return this.n;
    }

    /**
     * Returns a largest key on this priority queue.
     *
     * @return a largest key on this priority queue
     *
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        return this.pq[1];
    }

    // helper function to double the size of the heap array
    private void resize(int capacity) {
        assert capacity > this.n;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= this.n; i++) {
            temp[i] = this.pq[i];
        }
        this.pq = temp;
    }

    /**
     * Adds a new key to this priority queue.
     *
     * @param x the new key to add to this priority queue
     */
    public void insert(Key x) {

        // double size of array if necessary
        if (this.n == this.pq.length - 1) {
            resize(2 * this.pq.length);
        }

        // add x, and percolate it up to maintain heap invariant
        this.pq[++this.n] = x;
        swim(this.n);
        assert isMaxHeap();
    }

    /**
     * Removes and returns a largest key on this priority queue.
     *
     * @return a largest key on this priority queue
     *
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Key delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        Key max = this.pq[1];
        exch(1, this.n--);
        sink(1);
        this.pq[this.n + 1] = null;     // to avoid loiteing and help with garbage collection
        if ((this.n > 0) && (this.n == (this.pq.length - 1) / 4)) {
            resize(this.pq.length / 2);
        }
        assert isMaxHeap();
        return max;
    }

    /***************************************************************************
     * Helper functions to restore the heap invariant.
     ***************************************************************************/

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= this.n) {
            int j = 2 * k;
            if (j < this.n && less(j, j + 1)) {
                j++;
            }
            if (!less(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    /***************************************************************************
     * Helper functions for compares and swaps.
     ***************************************************************************/
    private boolean less(int i, int j) {
        if (this.comparator == null) {
            return ((Comparable<Key>) this.pq[i]).compareTo(this.pq[j]) < 0;
        } else {
            return this.comparator.compare(this.pq[i], this.pq[j]) < 0;
        }
    }

    private void exch(int i, int j) {
        Key swap = this.pq[i];
        this.pq[i] = this.pq[j];
        this.pq[j] = swap;
    }

    // is pq[1..N] a max heap?
    private boolean isMaxHeap() {
        return isMaxHeap(1);
    }


    /***************************************************************************
     * Iterator.
     ***************************************************************************/

    // is subtree of pq[1..n] rooted at k a max heap?
    private boolean isMaxHeap(int k) {
        if (k > this.n) {
            return true;
        }
        int left = 2 * k;
        int right = 2 * k + 1;
        if (left <= this.n && less(k, left)) {
            return false;
        }
        if (right <= this.n && less(k, right)) {
            return false;
        }
        return isMaxHeap(left) && isMaxHeap(right);
    }

    /**
     * Returns an iterator that iterates over the keys on this priority queue
     * in descending order.
     * The iterator doesn't implement {@code remove()} since it's optional.
     *
     * @return an iterator that iterates over the keys in descending order
     */
    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key> {

        // create a new pq
        private MaxPQ<Key> copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            if (MaxPQ.this.comparator == null) {
                this.copy = new MaxPQ<Key>(size());
            } else {
                this.copy = new MaxPQ<Key>(size(), MaxPQ.this.comparator);
            }
            for (int i = 1; i <= MaxPQ.this.n; i++) {
                this.copy.insert(MaxPQ.this.pq[i]);
            }
        }

        public boolean hasNext() {
            return !this.copy.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Key next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return this.copy.delMax();
        }
    }

}

/******************************************************************************
 *  Copyright 2002-2018, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
