/******************************************************************************
 *  Compilation:  javac ResizingArrayQueue.java
 *  Execution:    java ResizingArrayQueue < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/13stacks/tobe.txt  
 *
 *  Queue implementation with a resizing array.
 *
 *  % java ResizingArrayQueue < tobe.txt 
 *  to be or not to be (2 left on queue)
 *
 ******************************************************************************/

package edu.princeton.cs.algs4.find;

import edu.princeton.cs.algs4.base.StdIn;
import edu.princeton.cs.algs4.base.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code ResizingArrayQueue} class represents a first-in-first-out (FIFO)
 * queue of generic items.
 * It supports the usual <em>enqueue</em> and <em>dequeue</em>
 * operations, along with methods for peeking at the first item,
 * testing if the queue is empty, and iterating through
 * the items in FIFO order.
 * <p>
 * This implementation uses a resizing array, which double the underlying array
 * when it is full and halves the underlying array when it is one-quarter full.
 * The <em>enqueue</em> and <em>dequeue</em> operations take constant amortized time.
 * The <em>size</em>, <em>peek</em>, and <em>is-empty</em> operations takes
 * constant time in the worst case.
 * <p>
 * For additional documentation, see <a href="https://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class ResizingArrayQueue<Item> implements Iterable<Item> {
    private Item[] q;       // queue elements
    private int n;          // number of elements on queue
    private int first;      // index of first element of queue
    private int last;       // index of next available slot


    /**
     * Initializes an empty queue.
     */
    public ResizingArrayQueue() {
        this.q = (Item[]) new Object[2];
        this.n = 0;
        this.first = 0;
        this.last = 0;
    }

    /**
     * Unit tests the {@code ResizingArrayQueue} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        ResizingArrayQueue<String> queue = new ResizingArrayQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                queue.enqueue(item);
            } else if (!queue.isEmpty()) {
                StdOut.print(queue.dequeue() + " ");
            }
        }
        StdOut.println("(" + queue.size() + " left on queue)");
    }

    /**
     * Is this queue empty?
     *
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return this.n == 0;
    }

    /**
     * Returns the number of items in this queue.
     *
     * @return the number of items in this queue
     */
    public int size() {
        return this.n;
    }

    // resize the underlying array
    private void resize(int capacity) {
        assert capacity >= this.n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < this.n; i++) {
            temp[i] = this.q[(this.first + i) % this.q.length];
        }
        this.q = temp;
        this.first = 0;
        this.last = this.n;
    }

    /**
     * Adds the item to this queue.
     *
     * @param item the item to add
     */
    public void enqueue(Item item) {
        // double size of array if necessary and recopy to front of array
        if (this.n == this.q.length) {
            resize(2 * this.q.length);   // double size of array if necessary
        }
        this.q[this.last++] = item;                        // add item
        if (this.last == this.q.length) {
            this.last = 0;          // wrap-around
        }
        this.n++;
    }

    /**
     * Removes and returns the item on this queue that was least recently added.
     *
     * @return the item on this queue that was least recently added
     *
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        Item item = this.q[this.first];
        this.q[this.first] = null;                            // to avoid loitering
        this.n--;
        this.first++;
        if (this.first == this.q.length) {
            this.first = 0;           // wrap-around
        }
        // shrink size of array if necessary
        if (this.n > 0 && this.n == this.q.length / 4) {
            resize(this.q.length / 2);
        }
        return item;
    }

    /**
     * Returns the item least recently added to this queue.
     *
     * @return the item least recently added to this queue
     *
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        return this.q[this.first];
    }

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     *
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return this.i < ResizingArrayQueue.this.n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item =
                    ResizingArrayQueue.this.q[(this.i + ResizingArrayQueue.this.first) % ResizingArrayQueue.this.q.length];
            this.i++;
            return item;
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
