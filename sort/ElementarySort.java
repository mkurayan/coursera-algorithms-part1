public class ElementarySort {
    public static void main(String[] args) {
        int[] a = new int[] { 5, 9, 1, 4 , 18, 22, 8, 3, 2, 6, 7, 9, 9, 0};

        shellSort(a);

        printArray(a);
    }

    private static void printArray(int arr[]) {
        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    static void selectionSort(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for(int j = i + 1; j < arr.length; j++) {
                if(arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }

            exchange(arr, i, minIndex);
        }
    }

    static void insertionSort(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            int j = i;
            while(j > 0 && arr[j] < arr[j-1]) {
                exchange(arr, j, --j );
            }
        }
    }

    static void shellSort(int[] arr) {
        int step = 1;
        while (3 * step + 1 < arr.length) {
            step = 3 * step + 1;
        }

        while (step > 0) {
            for (int i = step; i < arr.length; i++) {
                int j = i;

                while (j - step >= 0 && arr[j] < arr[j - step]) {
                    exchange(arr, j, j = j - step);
                }
            }

            step = (step - 1) / 3;
        }
    }

    private static void exchange(int[] arr, int k, int n) {
        int swap = arr[k];
        arr[k] = arr[n];
        arr[n] = swap;
    }

}
