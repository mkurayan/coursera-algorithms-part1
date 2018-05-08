/**
 * Interview Questions: Analysis of Algorithms
 *
 * 3-SUM in quadratic time.
 *
 * Design an algorithm for the 3-SUM problem that takes time proportional to n^2
 * in the worst case. You may assume that you can sort the n integers in time proportional to n^2
 * or better.
 */
public class ThreeSum {
    public static void main(String[] args) {
        // Input array, we assume that array already sorted.
        // If not, sort it first.
        // Arrays.sort(arr);
        int[] arr = { -4, -3, 0, 1, 1, 3, 4, 7,};

        int count = 0;

        for(int i = 0; i < arr.length; i++) {
            int j = i +1;
            int k = arr.length - 1;

            while(k > j) {
                int sum = arr[i] + arr[j] + arr[k];

                if(sum == 0) {
                    System.out.printf("%d, %d, %d \n", arr[i], arr[j], arr[k]);
                    count++;
                }

                if (sum <= 0) {
                    j++;
                }
                else {
                    k--;
                }
            }
        }

        System.out.println("count: " + count);
    }
}
