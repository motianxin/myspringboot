/******************************************************************************
 *  Compilation:  javac QuickX.java
 *  Execution:    java QuickX < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   https://algs4.cs.princeton.edu/23quicksort/tiny.txt
 *                https://algs4.cs.princeton.edu/23quicksort/words3.txt
 *
 *  Uses the Hoare's 2-way partitioning scheme, chooses the partitioning
 *  element using median-of-3, and cuts off to insertion sort.
 *
 ******************************************************************************/

package edu.princeton.cs.algs4.sort;

import edu.princeton.cs.algs4.base.StdIn;
import edu.princeton.cs.algs4.base.StdOut;

/**
 * The {@code QuickX} class provides static methods for sorting an array
 * using an optimized version of quicksort (using Hoare's 2-way partitioning
 * algorithm, median-of-3 to choose the partitioning element, and cutoff
 * to insertion sort).
 * <p>
 * For additional documentation,
 * see <a href="https://algs4.cs.princeton.edu/23quick">Section 2.3</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class QuickX {

    // cutoff to insertion sort, must be >= 1
    private static final int INSERTION_SORT_CUTOFF = 8;

    // This class should not be instantiated.
    private QuickX() {
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     *
     * @param a the array to be sorted
     */
    public static void sort(Comparable[] a) {
        // StdRandom.shuffle(a);
        QuickX.sort(a, 0, a.length - 1);
        assert QuickX.isSorted(a);
    }

    // quicksort the subarray from a[lo] to a[hi]
    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }

        // cutoff to insertion sort (Insertion.sort() uses half-open intervals)
        int n = hi - lo + 1;
        if (n <= QuickX.INSERTION_SORT_CUTOFF) {
            Insertion.sort(a, lo, hi + 1);
            return;
        }

        int j = QuickX.partition(a, lo, hi);
        QuickX.sort(a, lo, j - 1);
        QuickX.sort(a, j + 1, hi);
    }

    // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
    // and return the index j.
    private static int partition(Comparable[] a, int lo, int hi) {
        int n = hi - lo + 1;
        int m = QuickX.median3(a, lo, lo + n / 2, hi);
        QuickX.exch(a, m, lo);

        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];

        // a[lo] is unique largest element
        while (QuickX.less(a[++i], v)) {
            if (i == hi) {
                QuickX.exch(a, lo, hi);
                return hi;
            }
        }

        // a[lo] is unique smallest element
        while (QuickX.less(v, a[--j])) {
            if (j == lo + 1) {
                return lo;
            }
        }

        // the main loop
        while (i < j) {
            QuickX.exch(a, i, j);
            while (QuickX.less(a[++i], v)) {
                ;
            }
            while (QuickX.less(v, a[--j])) {
                ;
            }
        }

        // put partitioning item v at a[j]
        QuickX.exch(a, lo, j);

        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }

    // return the index of the median element among a[i], a[j], and a[k]
    private static int median3(Comparable[] a, int i, int j, int k) {
        return (QuickX.less(a[i], a[j]) ? (QuickX.less(a[j], a[k]) ? j : QuickX.less(a[i], a[k]) ? k : i) : (QuickX.less(a[k], a[j]) ? j : QuickX.less(a[k], a[i]) ? k : i));
    }

    /***************************************************************************
     *  Helper sorting functions.
     ***************************************************************************/

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }


    /***************************************************************************
     *  Check if array is sorted - useful for debugging.
     ***************************************************************************/
    private static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (QuickX.less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    /**
     * Reads in a sequence of strings from standard input; quicksorts them
     * (using an optimized version of 2-way quicksort);
     * and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        QuickX.sort(a);
        assert QuickX.isSorted(a);
        QuickX.show(a);
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
