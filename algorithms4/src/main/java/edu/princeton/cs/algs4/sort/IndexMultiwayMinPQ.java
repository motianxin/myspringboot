/******************************************************************************
 *  Compilation: javac IndexMultiwayMinPQ.java
 *  Execution:
 *
 *  An inde  multiway heap.
 *
 ******************************************************************************/

package edu.princeton.cs.algs4.sort;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The IndexMultiwayMinPQ class represents an indexed priority queue of generic keys.
 * It supports the usual insert and delete-the-minimum operations,
 * along with delete and change-the-key methods.
 * In order to let the client refer to keys on the priority queue,
 * an integer between 0 and N-1 is associated with each key ; the client
 * uses this integer to specify which key to delete or change.
 * It also supports methods for peeking at the minimum key,
 * testing if the priority queue is empty, and iterating through
 * the keys.
 * <p>
 * This implementation uses a multiway heap along with an array to associate
 * keys with integers in the given range.
 * For simplified notations, logarithm in base d will be referred as log-d
 * The delete-the-minimum, delete, change-key and increase-key operations
 * take time proportional to d*log-d(n)
 * The insert and decrease-key take time proportional to log-d(n)
 * The is-empty, min-index, min-key, size, contains and key-of operations take constant time.
 * Construction takes time proportional to the specified capacity.
 * <p>
 * The arrays used in this structure have the first d indices empty,
 * it apparently helps with caching effects.
 *
 * @author Tristan Claverie
 */
public class IndexMultiwayMinPQ<Key> implements Iterable<Integer> {
    private final int d;                //Dimension of the heap
    private final Comparator<Key> comp; //Comparator over the keys
    private int n;                        //Number of keys currently in the queue
    private int nmax;                    //Maximum number of items in the queue
    private int[] pq;                    //Multiway heap
    private int[] qp;                    //Inverse of pq : qp[pq[i]] = pq[qp[i]] = i
    private Key[] keys;                    //keys[i] = priority of i


    /**
     * Initializes an empty indexed priority queue with indices between {@code 0} to {@code N-1}
     * Worst case is O(n)
     *
     * @param N number of keys in the priority queue, index from {@code 0} to {@code N-1}
     * @param D dimension of the heap
     *
     * @throws java.lang.IllegalArgumentException if {@code N < 0}
     * @throws java.lang.IllegalArgumentException if {@code D < 2}
     */
    public IndexMultiwayMinPQ(int N, int D) {
        if (N < 0) {
            throw new IllegalArgumentException("Maximum number of elements cannot be negative");
        }
        if (D < 2) {
            throw new IllegalArgumentException("Dimension should be 2 or over");
        }
        this.d = D;
        this.nmax = N;
        this.pq = new int[this.nmax + D];
        this.qp = new int[this.nmax + D];
        this.keys = (Key[]) new Comparable[this.nmax + D];
        for (int i = 0; i < this.nmax + D; this.qp[i++] = -1) {
            ;
        }
        this.comp = new MyComparator();
    }

    /**
     * Initializes an empty indexed priority queue with indices between {@code 0} to {@code N-1}
     * Worst case is O(n)
     *
     * @param N number of keys in the priority queue, index from {@code 0} to {@code N-1}
     * @param D dimension of the heap
     * @param C a Comparator over the keys
     *
     * @throws java.lang.IllegalArgumentException if {@code N < 0}
     * @throws java.lang.IllegalArgumentException if {@code D < 2}
     */
    public IndexMultiwayMinPQ(int N, Comparator<Key> C, int D) {
        if (N < 0) {
            throw new IllegalArgumentException("Maximum number of elements cannot be negative");
        }
        if (D < 2) {
            throw new IllegalArgumentException("Dimension should be 2 or over");
        }
        this.d = D;
        this.nmax = N;
        this.pq = new int[this.nmax + D];
        this.qp = new int[this.nmax + D];
        this.keys = (Key[]) new Comparable[this.nmax + D];
        for (int i = 0; i < this.nmax + D; this.qp[i++] = -1) {
            ;
        }
        this.comp = C;
    }

    /**
     * Whether the priority queue is empty
     * Worst case is O(1)
     *
     * @return true if the priority queue is empty, false if not
     */
    public boolean isEmpty() {
        return this.n == 0;
    }

    /**
     * Does the priority queue contains the index i ?
     * Worst case is O(1)
     *
     * @param i an index
     *
     * @return true if i is on the priority queue, false if not
     *
     * @throws java.lang.IllegalArgumentException if the specified index is invalid
     */
    public boolean contains(int i) {
        if (i < 0 || i >= this.nmax) {
            throw new IllegalArgumentException();
        }
        return this.qp[i + this.d] != -1;
    }

    /**
     * Number of elements currently on the priority queue
     * Worst case is O(1)
     *
     * @return the number of elements on the priority queue
     */
    public int size() {
        return this.n;
    }

    /**
     * Associates a key with an index
     * Worst case is O(log-d(n))
     *
     * @param i an index
     * @param key a Key associated with i
     *
     * @throws java.lang.IllegalArgumentException if the specified index is invalid
     * @throws java.lang.IllegalArgumentException if the index is already in the queue
     */
    public void insert(int i, Key key) {
        if (i < 0 || i >= this.nmax) {
            throw new IllegalArgumentException();
        }
        if (contains(i)) {
            throw new IllegalArgumentException("Index already there");
        }
        this.keys[i + this.d] = key;
        this.pq[this.n + this.d] = i;
        this.qp[i + this.d] = this.n;
        swim(this.n++);
    }

    /**
     * Gets the index associated with the minimum key
     * Worst case is O(1)
     *
     * @return the index associated with the minimum key
     *
     * @throws java.util.NoSuchElementException if the priority queue is empty
     */
    public int minIndex() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return this.pq[this.d];
    }

    /**
     * Gets the minimum key currently in the queue
     * Worst case is O(1)
     *
     * @return the minimum key currently in the priority queue
     *
     * @throws java.util.NoSuchElementException if the priority queue is empty
     */
    public Key minKey() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return this.keys[this.pq[this.d] + this.d];
    }

    /**
     * Deletes the minimum key
     * Worst case is O(d*log-d(n))
     *
     * @return the index associated with the minimum key
     *
     * @throws java.util.NoSuchElementException if the priority queue is empty
     */
    public int delMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        int min = this.pq[this.d];
        exch(0, --this.n);
        sink(0);
        this.qp[min + this.d] = -1;
        this.keys[this.pq[this.n + this.d] + this.d] = null;
        this.pq[this.n + this.d] = -1;
        return min;
    }

    /**
     * Gets the key associated with index i
     * Worst case is O(1)
     *
     * @param i an index
     *
     * @return the key associated with index i
     *
     * @throws java.lang.IllegalArgumentException if the specified index is invalid
     * @throws java.lang.IllegalArgumentException if the index is not in the queue
     */
    public Key keyOf(int i) {
        if (i < 0 || i >= this.nmax) {
            throw new IllegalArgumentException();
        }
        if (!contains(i)) {
            throw new NoSuchElementException("Specified index is not in the queue");
        }
        return this.keys[i + this.d];
    }

    /**
     * Changes the key associated with index i to the given key
     * If the given key is greater, Worst case is O(d*log-d(n))
     * If the given key is lower,   Worst case is O(log-d(n))
     *
     * @param i an index
     * @param key the key to associate with i
     *
     * @throws java.lang.IllegalArgumentException if the specified index is invalid
     * @throws java.lang.IllegalArgumentException if the index has no key associated with
     */
    public void changeKey(int i, Key key) {
        if (i < 0 || i >= this.nmax) {
            throw new IllegalArgumentException();
        }
        if (!contains(i)) {
            throw new NoSuchElementException("Specified index is not in the queue");
        }
        Key tmp = this.keys[i + this.d];
        this.keys[i + this.d] = key;
        if (this.comp.compare(key, tmp) <= 0) {
            swim(this.qp[i + this.d]);
        } else {
            sink(this.qp[i + this.d]);
        }
    }

    /**
     * Decreases the key associated with index i to the given key
     * Worst case is O(log-d(n))
     *
     * @param i an index
     * @param key the key to associate with i
     *
     * @throws java.lang.IllegalArgumentException if the specified index is invalid
     * @throws java.util.NoSuchElementException if the index has no key associated with
     * @throws java.lang.IllegalArgumentException if the given key is greater than the current key
     */
    public void decreaseKey(int i, Key key) {
        if (i < 0 || i >= this.nmax) {
            throw new IllegalArgumentException();
        }
        if (!contains(i)) {
            throw new NoSuchElementException("Specified index is not in the queue");
        }
        if (this.comp.compare(this.keys[i + this.d], key) <= 0) {
            throw new IllegalArgumentException("Calling with this argument would not decrease the Key");
        }
        this.keys[i + this.d] = key;
        swim(this.qp[i + this.d]);
    }

    /**
     * Increases the key associated with index i to the given key
     * Worst case is O(d*log-d(n))
     *
     * @param i an index
     * @param key the key to associate with i
     *
     * @throws java.lang.IllegalArgumentException if the specified index is invalid
     * @throws java.util.NoSuchElementException if the index has no key associated with
     * @throws java.lang.IllegalArgumentException if the given key is lower than the current key
     */
    public void increaseKey(int i, Key key) {
        if (i < 0 || i >= this.nmax) {
            throw new IllegalArgumentException();
        }
        if (!contains(i)) {
            throw new NoSuchElementException("Specified index is not in the queue");
        }
        if (this.comp.compare(this.keys[i + this.d], key) >= 0) {
            throw new IllegalArgumentException("Calling with this argument would not increase the Key");
        }
        this.keys[i + this.d] = key;
        sink(this.qp[i + this.d]);
    }

    /**
     * Deletes the key associated to the given index
     * Worst case is O(d*log-d(n))
     *
     * @param i an index
     *
     * @throws java.lang.IllegalArgumentException if the specified index is invalid
     * @throws java.util.NoSuchElementException if the given index has no key associated with
     */
    public void delete(int i) {
        if (i < 0 || i >= this.nmax) {
            throw new IllegalArgumentException();
        }
        if (!contains(i)) {
            throw new NoSuchElementException("Specified index is not in the queue");
        }
        int idx = this.qp[i + this.d];
        exch(idx, --this.n);
        swim(idx);
        sink(idx);
        this.keys[i + this.d] = null;
        this.qp[i + this.d] = -1;
    }

    /***************************
     * General helper functions
     **************************/

    //Compares two keys
    private boolean greater(int i, int j) {
        return this.comp.compare(this.keys[this.pq[i + this.d] + this.d], this.keys[this.pq[j + this.d] + this.d]) > 0;
    }

    //Exchanges two keys
    private void exch(int x, int y) {
        int i = x + this.d, j = y + this.d;
        int swap = this.pq[i];
        this.pq[i] = this.pq[j];
        this.pq[j] = swap;
        this.qp[this.pq[i] + this.d] = x;
        this.qp[this.pq[j] + this.d] = y;
    }

    /***************************
     * Functions for moving upward or downward
     **************************/

    //Moves upward
    private void swim(int i) {
        if (i > 0 && greater((i - 1) / this.d, i)) {
            exch(i, (i - 1) / this.d);
            swim((i - 1) / this.d);
        }
    }

    //Moves downward
    private void sink(int i) {
        if (this.d * i + 1 >= this.n) {
            return;
        }
        int min = minChild(i);
        while (min < this.n && greater(i, min)) {
            exch(i, min);
            i = min;
            min = minChild(i);
        }
    }

    /***************************
     * Deletes the minimum child
     **************************/

    //Return the minimum child of i
    private int minChild(int i) {
        int loBound = this.d * i + 1, hiBound = this.d * i + this.d;
        int min = loBound;
        for (int cur = loBound; cur <= hiBound; cur++) {
            if (cur < this.n && greater(min, cur)) {
                min = cur;
            }
        }
        return min;
    }

    /***************************
     * Iterator
     **************************/

    /**
     * Gets an Iterator over the indexes in the priority queue in ascending order
     * The Iterator does not implement the remove() method
     * iterator() : Worst case is O(n)
     * next() : 	Worst case is O(d*log-d(n))
     * hasNext() : 	Worst case is O(1)
     *
     * @return an Iterator over the indexes in the priority queue in ascending order
     */

    public Iterator<Integer> iterator() {
        return new MyIterator();
    }

    //Constructs an Iterator over the indices in linear time
    private class MyIterator implements Iterator<Integer> {
        IndexMultiwayMinPQ<Key> clone;

        public MyIterator() {
            this.clone = new IndexMultiwayMinPQ<Key>(IndexMultiwayMinPQ.this.nmax, IndexMultiwayMinPQ.this.comp,
                    IndexMultiwayMinPQ.this.d);
            for (int i = 0; i < IndexMultiwayMinPQ.this.n; i++) {
                this.clone.insert(IndexMultiwayMinPQ.this.pq[i + IndexMultiwayMinPQ.this.d],
                        IndexMultiwayMinPQ.this.keys[IndexMultiwayMinPQ.this.pq[i + IndexMultiwayMinPQ.this.d] + IndexMultiwayMinPQ.this.d]);
            }
        }

        public boolean hasNext() {
            return !this.clone.isEmpty();
        }

        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return this.clone.delMin();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /***************************
     * Comparator
     **************************/

    //default Comparator
    private class MyComparator implements Comparator<Key> {
        @Override
        public int compare(Key key1, Key key2) {
            return ((Comparable<Key>) key1).compareTo(key2);
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
