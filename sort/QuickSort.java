//import edu.princeton.cs.algs4.StdRandom;

public class QuickSort {

    public static void main(String[] args) {
        test(new int[] { 8, 1, 4 , 12, 7, 3, 2, 7, 6 });
        test(new int[] { 8, 1, 4 , 12, 7, 3, 2, 7});
        test(new int[] { 8, 1, 4 , 12, 7, 3, 2});
        test(new int[] { 8, 1, 4 , 12, 7, 3});
    }

    private static void test(int[] arr) {
        sort(arr);

        System.out.println("");
        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public static void sort(int[] arr) {
        // Shuffle array.
        //StdRandom.shuffle(a);

        // Median of three points.
        // ...

        sort(arr, 0, arr.length - 1);
    }

    private static void sort(int[] arr, int lo, int hi) {
        if(hi <= lo) {
            return;
        }

        int p = arr[lo];

        int i = lo + 1;
        int lt = lo;
        int gt = hi;

        while(i <= gt) {
            if(arr[i] < p) {
                exch(arr, lt++, i++);
            } else if(arr[i] > p) {
                exch(arr, i, gt--);
            } else {
                i++;
            }
        }

        sort(arr, lo, lt - 1);
        sort(arr, gt + 1, hi);
    }

    private static void exch(int[] arr, int a, int b) {
        int swap = arr[a];
        arr[a] = arr[b];
        arr[b] = swap;
    }
}
