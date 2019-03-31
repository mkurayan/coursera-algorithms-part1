import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int R = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] chars = new char[R];

        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) i;
        }

        while (!BinaryStdIn.isEmpty()) {
            char searchChar = BinaryStdIn.readChar();

            int index = 0;
            for (char cur = chars[index], prev; cur != searchChar;) {
                prev = cur;
                cur = chars[++index];

                chars[index] = prev;
            }

            chars[0] = searchChar;

            BinaryStdOut.write((char) index);
        }

        BinaryStdOut.flush();
    }


    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] chars = new char[R];

        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) i;
        }

        while (!BinaryStdIn.isEmpty()) {
            char index = BinaryStdIn.readChar();
            char ch = chars[index];

            for (char i = index; i > 0; i--) {
                chars[i] = chars[i - 1];
            }

            chars[0] = ch;

            BinaryStdOut.write(ch);
        }

        BinaryStdOut.flush();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            MoveToFront.encode();
        } else if (args[0].equals("+")) {
            MoveToFront.decode();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
