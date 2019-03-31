import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class CircularSuffixArray {
    private final int[] arr;
    private final int lng;
    private final char[] charArray;

    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }

        lng = s.length();
        charArray = s.toCharArray();

        CircularString[] suffixArray = new CircularString[lng];

        for (int i = 0; i < lng; i++) {
            suffixArray[i] = new CircularString(i);
        }

        Arrays.sort(suffixArray);

        arr = new int[lng];

        for (int i = 0; i < lng; i++) {
            arr[i] = suffixArray[i].startIndex;
        }
    }

    // length of string
    public int length() {
        return lng;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= lng) {
            throw new IllegalArgumentException("index out of range.");
        }

        return arr[i];
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String st = in.readAll();

        CircularSuffixArray csa = new CircularSuffixArray(st);
        for (int i = 0; i < csa.length(); i++) {
            StdOut.print(csa.index(i) + " ");
        }

        StdOut.println();

        for (int i = 0; i < csa.length(); i++) {
            StdOut.print(st.charAt(csa.index(i)) + " ");
        }
        StdOut.println();
    }

    private final class CircularString implements  Comparable<CircularString> {
        private final int startIndex;

        private CircularString(int sIndex) {
            startIndex = sIndex;
        }

        private char charAt(int index) {
            if (index >= lng) {
                throw new IllegalArgumentException("Char index out of range.");
            }

            return charArray[(index + startIndex) % lng];
        }

        @Override
        public int compareTo(CircularString cString) {
            for (int i = 0; i < lng; i++) {
                char a = charAt(i);
                char b = cString.charAt(i);

                if (a < b) {
                    return -1;
                }

                if (a > b) {
                    return 1;
                }
            }

            return 0;
        }
    }
}
