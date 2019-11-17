/******************************************************************************
 *  Compilation:  javac LinkedQueue.java
 *  Execution:    java LinkedQueue < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/13stacks/tobe.txt  
 *
 *  A generic queue, implemented using a singly linked list.
 *
 *  % java Queue < tobe.txt 
 *  to be or not to be (2 left on queue)
 *
 ******************************************************************************/

package edu.princeton.cs.algs4.base;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code LinkedQueue} class represents a first-in-first-out (FIFO)
 * queue of generic items.
 * It supports the usual <em>enqueue</em> and <em>dequeue</em>
 * operations, along with methods for peeking at the first item,
 * testing if the queue is empty, and iterating through
 * the items in FIFO order.
 * <p>
 * This implementation uses a singly linked list with a non-static nested class
 * for linked-list nodes.  See {@link Queue} for a version that uses a static nested class.
 * The <em>enqueue</em>, <em>dequeue</em>, <em>peek</em>, <em>size</em>, and <em>is-empty</em>
 * operations all take constant time in the worst case.
 * <p>
 * For additional documentation, see <a href="https://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class LinkedQueue<Item> implements Iterable<Item> {
    private int n;         // number of elements on queue
    private Node first;    // beginning of queue
    private Node last;     // end of queue

    /**
     * Initializes an empty queue.
     */
    public LinkedQueue() {
        this.first = null;
        this.last = null;
        this.n = 0;
        assert check();
    }

    /**
     * Unit tests the {@code LinkedQueue} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        LinkedQueue<String> queue = new LinkedQueue<String>();
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
        return this.first == null;
    }

    /**
     * Returns the number of items in this queue.
     *
     * @return the number of items in this queue
     */
    public int size() {
        return this.n;
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
        return this.first.item;
    }

    /**
     * Adds the item to this queue.
     *
     * @param item the item to add
     */
    public void enqueue(Item item) {
        Node oldlast = this.last;
        this.last = new Node();
        this.last.item = item;
        this.last.next = null;
        if (isEmpty()) {
            this.first = this.last;
        } else {
            oldlast.next = this.last;
        }
        this.n++;
        assert check();
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
        Item item = this.first.item;
        this.first = this.first.next;
        this.n--;
        if (isEmpty()) {
            this.last = null;   // to avoid loitering
        }
        assert check();
        return item;
    }

    /**
     * Returns a string representation of this queue.
     *
     * @return the sequence of items in FIFO order, separated by spaces
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item + " ");
        }
        return s.toString();
    }

    // check internal invariants
    private boolean check() {
        if (this.n < 0) {
            return false;
        } else if (this.n == 0) {
            if (this.first != null) {
                return false;
            }
            if (this.last != null) {
                return false;
            }
        } else if (this.n == 1) {
            if (this.first == null || this.last == null) {
                return false;
            }
            if (this.first != this.last) {
                return false;
            }
            if (this.first.next != null) {
                return false;
            }
        } else {
            if (this.first == null || this.last == null) {
                return false;
            }
            if (this.first == this.last) {
                return false;
            }
            if (this.first.next == null) {
                return false;
            }
            if (this.last.next != null) {
                return false;
            }

            // check internal consistency of instance variable n
            int numberOfNodes = 0;
            for (Node x = this.first; x != null && numberOfNodes <= this.n; x = x.next) {
                numberOfNodes++;
            }
            if (numberOfNodes != this.n) {
                return false;
            }

            // check internal consistency of instance variable last
            Node lastNode = this.first;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            if (this.last != lastNode) {
                return false;
            }
        }

        return true;
    }


    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     *
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = LinkedQueue.this.first;

        public boolean hasNext() {
            return this.current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = this.current.item;
            this.current = this.current.next;
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
