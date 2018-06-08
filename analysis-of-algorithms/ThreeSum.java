/**
 * Interview Questions: Analysis of Algorithms
 *
 * 3-SUM in quadratic time.
 *
 * Design an algorithm for the 3-SUM problem that takes time proportional to n^2 in the worst case.
 * You may assume that you can sort the n integers in time proportional to n^2
 * or better.
 */
public class ThreeSum {
    public static void main(String[] args) {
        test(new int[] {-1, -1, 1}, 0);
        test(new int[] {-1, 0, 1}, 1);
        test(new int[] {-1, 0, 1, 1}, 2);
        test(new int[] {-4, -3, 0, 1, 1, 3, 4, 7}, 5);
    }

    private static void test(int[] arr, int expectedCount) {
        int count = find3Sum(arr);

        if(count != expectedCount) {
            System.out.format("Error! Current count: %d Expected count: %d \n", count, expectedCount);
        }
    }

    private static int find3Sum(int[] arr) {
        int count = 0;

        for (int i = 0; i < arr.length; i++) {
            int j = i + 1;
            int k = arr.length - 1;

            while (k > j) {
                int sum = arr[i] + arr[j] + arr[k];

                if (sum == 0) {
                    count++;
                }

                if (sum <= 0) {
                    j++;
                } else {
                    k--;
                }
            }
        }

        return count;
    }
}
