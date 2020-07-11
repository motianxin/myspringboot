import javax.xml.ws.Holder;

import java.util.Arrays;

/**
 * <一句话功能描述>
 *
 * @Author 墨天心
 * @Since 2020/7/11 21:34
 */
public class MyAlgorithms {
    private final static Holder<Integer> deep = new Holder<Integer>(0);

    public static void main(String[] args) {
        int f = 0, g = 1;
        int length = 15;
        int[] a = new int[length];
        int[] b = new int[length];
        for (int i = 0; i < length; i++) {
            System.out.println(f);
            a[i] = f;
            System.out.println(Integer.toBinaryString(f));
            f = f + g;
            g = f - g;
            b[length - 1 - i] = i;
        }
        System.out.println(rank(55, a, 0, length - 1));
        for (int i = 0; i < length; i++) {
            b[i] = b[b[i]];
        }
        System.out.println(Arrays.toString(b));
    }

    private static int rank(int key, int[] a, int lo, int hi) {
        System.out.println(++deep.value);
        int mid = lo + (hi - lo) / 2;
        if (lo > hi) {
            return -1;
        } else if (a[mid] < key) {
            return rank(key, a, mid + 1, hi);
        } else if (a[mid] > key) {
            return rank(key, a, lo, mid - 1);
        } else {
            return mid;
        }
    }
}
