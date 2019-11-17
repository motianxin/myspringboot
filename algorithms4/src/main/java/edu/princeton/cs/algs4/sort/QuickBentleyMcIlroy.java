/******************************************************************************
 *  Compilation:  javac QuickBentleyMcIlroy.java
 *  Execution:    java QuickBentleyMcIlroy < input.txt
 *  Dependencies: StdOut.java StdIn.java
 *  Data files:   https://algs4.cs.princeton.edu/23quicksort/tiny.txt
 *                https://algs4.cs.princeton.edu/23quicksort/words3.txt
 *
 *  Uses the Bentley-McIlroy 3-way partitioning scheme,
 *  chooses the partitioning element using Tukey's ninther,
 *  and cuts off to insertion sort.
 *
 *  Reference: Engineering a Sort Function by Jon L. Bentley
 *  and M. Douglas McIlroy. Softwae-Practice and Experience,
 *  Vol. 23 (11), 1249-1265 (November 1993).
 *
 ******************************************************************************/

package edu.princeton.cs.algs4.sort;

import edu.princeton.cs.algs4.base.StdIn;
import edu.princeton.cs.algs4.base.StdOut;

/**
 * The {@code QuickBentleyMcIlroy} class provides static methods for sorting
 * an array using an optimized version of quicksort (using Bentley-McIlroy
 * 3-way partitioning, Tukey's ninther, and cutoff to insertion sort).
 * <p>
 * For additional documentation,
 * see <a href="https://algs4.cs.princeton.edu/23quick">Section 2.3</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class QuickBentleyMcIlroy {

    // cutoff to insertion sort, must be >= 1
    private static final int INSERTION_SORT_CUTOFF = 8;

    // cutoff to median-of-3 partitioning
    private static final int MEDIAN_OF_3_CUTOFF = 40;

    // This class should not be instantiated.
    private QuickBentleyMcIlroy() {
    }

    /**
     * Rearranges the array in ascending order, using the natural order.
     *
     * @param a the array to be sorted
     */
    public static void sort(Comparable[] a) {
        QuickBentleyMcIlroy.sort(a, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        int n = hi - lo + 1;

        // cutoff to insertion sort
        if (n <= QuickBentleyMcIlroy.INSERTION_SORT_CUTOFF) {
            QuickBentleyMcIlroy.insertionSort(a, lo, hi);
            return;
        }

        // use median-of-3 as partitioning element
        else if (n <= QuickBentleyMcIlroy.MEDIAN_OF_3_CUTOFF) {
            int m = QuickBentleyMcIlroy.median3(a, lo, lo + n / 2, hi);
            QuickBentleyMcIlroy.exch(a, m, lo);
        }

        // use Tukey ninther as partitioning element
        else {
            int eps = n / 8;
            int mid = lo + n / 2;
            int m1 = QuickBentleyMcIlroy.median3(a, lo, lo + eps, lo + eps + eps);
            int m2 = QuickBentleyMcIlroy.median3(a, mid - eps, mid, mid + eps);
            int m3 = QuickBentleyMcIlroy.median3(a, hi - eps - eps, hi - eps, hi);
            int ninther = QuickBentleyMcIlroy.median3(a, m1, m2, m3);
            QuickBentleyMcIlroy.exch(a, ninther, lo);
        }

        // Bentley-McIlroy 3-way partitioning
        int i = lo, j = hi + 1;
        int p = lo, q = hi + 1;
        Comparable v = a[lo];
        while (true) {
            while (QuickBentleyMcIlroy.less(a[++i], v)) {
                if (i == hi) {
                    break;
                }
            }
            while (QuickBentleyMcIlroy.less(v, a[--j])) {
                if (j == lo) {
                    break;
                }
            }

            // pointers cross
            if (i == j && QuickBentleyMcIlroy.eq(a[i], v)) {
                QuickBentleyMcIlroy.exch(a, ++p, i);
            }
            if (i >= j) {
                break;
            }

            QuickBentleyMcIlroy.exch(a, i, j);
            if (QuickBentleyMcIlroy.eq(a[i], v)) {
                QuickBentleyMcIlroy.exch(a, ++p, i);
            }
            if (QuickBentleyMcIlroy.eq(a[j], v)) {
                QuickBentleyMcIlroy.exch(a, --q, j);
            }
        }


        i = j + 1;
        for (int k = lo; k <= p; k++) {
            QuickBentleyMcIlroy.exch(a, k, j--);
        }
        for (int k = hi; k >= q; k--) {
            QuickBentleyMcIlroy.exch(a, k, i++);
        }

        QuickBentleyMcIlroy.sort(a, lo, j);
        QuickBentleyMcIlroy.sort(a, i, hi);
    }


    // sort from a[lo] to a[hi] using insertion sort
    private static void insertionSort(Comparable[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && QuickBentleyMcIlroy.less(a[j], a[j - 1]); j--) {
                QuickBentleyMcIlroy.exch(a, j, j - 1);
            }
        }
    }


    // return the index of the median element among a[i], a[j], and a[k]
    private static int median3(Comparable[] a, int i, int j, int k) {
        return (QuickBentleyMcIlroy.less(a[i], a[j]) ? (QuickBentleyMcIlroy.less(a[j], a[k]) ? j : QuickBentleyMcIlroy.less(a[i], a[k]) ? k : i) : (QuickBentleyMcIlroy.less(a[k], a[j]) ? j : QuickBentleyMcIlroy.less(a[k], a[i]) ? k : i));
    }

    /***************************************************************************
     *  Helper sorting functions.
     ***************************************************************************/

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        if (v == w) {
            return false;    // optimization when reference equal
        }
        return v.compareTo(w) < 0;
    }

    // does v == w ?
    private static boolean eq(Comparable v, Comparable w) {
        if (v == w) {
            return true;    // optimization when reference equal
        }
        return v.compareTo(w) == 0;
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
            if (QuickBentleyMcIlroy.less(a[i], a[i - 1])) {
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
     * (using an optimized version of quicksort);
     * and prints them to standard output in ascending order.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        QuickBentleyMcIlroy.sort(a);
        assert QuickBentleyMcIlroy.isSorted(a);
        QuickBentleyMcIlroy.show(a);
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
