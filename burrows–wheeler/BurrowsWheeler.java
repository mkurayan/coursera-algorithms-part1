import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    private static final int R = 256;

    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();

        CircularSuffixArray csa = new CircularSuffixArray(s);

        char[] tranformedArray = new char[csa.length()];
        int startSymbolIndex = 0;

        for (int i = 0; i < s.length(); i++) {
            int index = csa.index(i);

            if (index == 0) {
                startSymbolIndex = i;
                tranformedArray[i] = s.charAt(s.length() - 1);
            } else {
                tranformedArray[i] = s.charAt(index - 1);
            }
        }

        BinaryStdOut.write(startSymbolIndex);

        for (char anOut : tranformedArray) {
            BinaryStdOut.write(anOut, 8);
        }

        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first;
        first = BinaryStdIn.readInt();
        char[] str = BinaryStdIn.readString().toCharArray();

        // index counting
        int[] count = new int[R + 1];
        for (int i = 0; i < str.length; i++) {
            count[str[i] + 1]++;
        }

        for (int i = 1; i < R + 1; i++) {
            count[i] += count[i - 1];
        }

        int[] next = new int[str.length];
        for (int i = 0; i < str.length; i++) {
            next[count[str[i]]++] = i;
        }

        for (int index = next[first], n = 0; n < str.length; index = next[index], n++) {
            BinaryStdOut.write(str[index]);
        }

        BinaryStdOut.flush();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        } else if (args[0].equals("+")) {
            inverseTransform();
        }
    }
}
