public class HeapSort {
    public static void sort(int[] arr) {
        int k = 1;
        while(arr.length > 2 * k + 1) {
            k = 2 * k + 1;
        }

        for(int i = k; i > 0; i--) {
            sink(arr, i, arr.length);
        }

        for(int i = arr.length; i > 0; i--) {
            exch(arr, 1, i);
            sink(arr, 1, i - 1);
        }
    }

    private static void exch(int[] arr, int i, int j) {
        int swap = arr[i - 1];
        arr[i - 1] = arr[j - 1];
        arr[j - 1] = swap;
    }

    private static boolean less(int[] arr, int i, int j) {
        return arr[i - 1] < arr[j - 1];
    }

    private static void sink(int arr[], int k, int length) {
        while(2 * k <= length) {
            int i = 2 * k;

            if(i + 1 <= length && less(arr, i+1, i)) {
                i++;
            }

            if(!less(arr, i, k)) {
                break;
            }

            exch(arr, k, i);
            k = i;
        }
    }

    public static void main(String[] args) {
        test(new int[] { 8, 1, 4 , 12, 7, 3, 2, 7, 6 , 7});
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
}
