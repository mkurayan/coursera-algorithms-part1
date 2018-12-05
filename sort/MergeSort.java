public class MergeSort {
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
        int[] arrCopy = new int [arr.length];
        for(int i = 0; i < arr.length; i++) {
            arrCopy[i] = arr[i];
        }

        sort(arrCopy, arr, 0, arr.length-1);
    }


    public static void sort(int[] source, int[] target, int startIndex, int endIndex) {

        if(endIndex > startIndex) {
            int middle = startIndex + (endIndex - startIndex) / 2;
            sort(target, source, startIndex, middle );
            sort(target, source, middle + 1, endIndex );

            merge(source, target, startIndex, middle, endIndex);
        }
    }

    private static void merge(int[] source, int[] target,  int startIndex, int middleIndex, int endIndex) {
        int k = startIndex;
        int m = middleIndex + 1;
        for(int i = startIndex; i <= endIndex; i++) {
            if(k > middleIndex) {
                target[i] = source[m++];
            } else if (m > endIndex) {
                target[i] = source[k++];
            } else if(source[m] > source[k]) {
                target[i] = source[k++];
            } else {
                target[i] = source[m++];
            }
        }
    }
}
