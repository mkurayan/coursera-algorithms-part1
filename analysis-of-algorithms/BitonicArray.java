/**
 * Interview Questions: Analysis of Algorithms
 *
 * Search in a bitonic array.
 *
 * An array is bitonic if it is comprised of an increasing sequence of integers
 * followed immediately by a decreasing sequence of integers.
 * Write a program that, given a bitonic array of 'n' distinct integer values,
 * determines whether a given integer is in the array.
 * */
public class BitonicArray {
    public static void main(String[] args) {
        int[] arr = {-8, -3, 1, 3, 4, 7, 12, 7, 5, 2, 0};

        test(arr, -8, 0);
        test(arr, -3, 1);
        test(arr, 7, 5);
        test(arr, 12, 6);
        test(arr, 0, 10);
    }

    private static void test(int[] arr, int element, int expectedIndex) {
        int index = find(arr, element);

        if(index != expectedIndex) {
            System.out.format("Error! Current index: %d Expected index: %d \n", index, expectedIndex);
        }
    }

    /**
     * Find element index in array, if not found return -1.
     * @param arr bitonic array.
     * @param element element ot search.
     * @return element index in array.
     */
    private static int find (int[] arr, int element) {
        int maxElementIndex = findMaxElementIndexInBitonicArray(arr);

        int elementIndex = binarySearch(
                arr,
                element,
                0,
                maxElementIndex,
                false);

        if (elementIndex != -1) {
            return elementIndex;
        }

        elementIndex =  binarySearch(
                arr,
                element,
                maxElementIndex,
                arr.length,
                true);

        if (elementIndex != -1) {
            return elementIndex;
        }

        return -1;
    }

    private static int findMaxElementIndexInBitonicArray(int[] arr) {
        int left = 0;
        int right = arr.length;

        while(right >= left) {
            int middle = left + (right - left) / 2;

            if (middle - 1 > 0 && arr[middle] < arr[middle - 1]) {
                right = middle;
            } else if (middle + 1 <= arr.length && arr[middle] < arr[middle + 1]) {
                left = middle;
            } else {
                return middle;
            }
        }

        return left;
    }

    private static int binarySearch(int[] arr, int element, int startIndex, int endIndex, boolean descOrder) {
        while (startIndex <= endIndex) {
            int middle = startIndex + (endIndex - startIndex) / 2;

            if (arr[middle] == element) {
                return middle;
            } else if ((!descOrder && arr[middle] > element) || (descOrder && arr[middle] < element)) {
                return binarySearch(arr, element, startIndex, middle - 1, descOrder);
            } else {
                return binarySearch(arr, element, middle + 1, endIndex, descOrder);
            }
        }

        return -1;
    }
}
