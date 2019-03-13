import java.util.HashSet;

public class BoggleSolver {
    private BoggleMatrix matrix;

    private static final int R = 26; // Radix
    private Node root;  // root of trie

    public BoggleSolver(String[] words) {
        if (words == null) {
            throw new IllegalArgumentException();
        }

        for (String word : words) {
            if (word == null) {
                throw new IllegalArgumentException("words collection contains null key");
            }

            root = add(root, word, 0);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) {
            throw new IllegalArgumentException();
        }

        boolean[][] visited = new boolean[board.rows()][board.cols()];
        HashSet<String> words = new HashSet<>();

        if (matrix == null || matrix.rows() != board.rows() || matrix.cols() != board.cols()) {
            matrix = new BoggleMatrix(board.rows(), board.cols());
        }

        for (int row = 0; row < board.rows(); row++) {
            for (int col = 0; col < board.cols(); col++) {
                dfs(board, words, visited, "", row, col, root);
            }
        }

        return words;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }

        Node n = get(root, word, 0);

        if (n == null || !n.isString) {
            return 0;
        }

        int wLength = word.length();

        if (wLength <= 2) {
            return 0;
        }

        if (wLength == 3 || wLength == 4) {
            return 1;
        }

        if (wLength == 5) {
            return 2;
        }

        if (wLength == 6) {
            return 3;
        }

        if (wLength == 7) {
            return 5;
        }

        // 8+ letters
        return 11;
    }

    private void dfs(BoggleBoard board, HashSet<String> words, boolean[][] visited, String word, int row, int col, Node x) {
        if (visited[row][col]) {
            return;
        }

        visited[row][col] = true;

        char letter = board.getLetter(row, col);
        Node next = x.next[letterToInt(letter)];
        word = word + letter;

        if (next != null && letter == 'Q') {
            // Special case for 'Qu' letter.
            next = next.next[letterToInt('U')];
            word = word + 'U';
        }

        if (next != null) {
            if (next.isString && word.length() > 2) {
                words.add(word);
            }

            for (BoggleCell cell: matrix.adj(row, col)) {
                dfs(board, words, visited, word, cell.row(), cell.col(), next);
            }
        }

        visited[row][col] = false;
    }

    // R-way trie node
    private static class Node {
        private Node[] next = new Node[R];
        private boolean isString;
    }

    private Node add(Node x, String key, int d) {
        if (x == null) {
            x = new Node();
        }

        if (d == key.length()) {
            x.isString = true;
        } else {
            int n = letterToInt(key.charAt(d));
            x.next[n] = add(x.next[n], key, d + 1);
        }
        return x;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }

        if (d == key.length()) {
            return x;
        }

        int n = letterToInt(key.charAt(d));
        return get(x.next[n], key, d + 1);
    }

    private int letterToInt(char c) {
        return c - 65;
    }
}
