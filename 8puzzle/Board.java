import edu.princeton.cs.algs4.Bag;

public class Board {
    private final int[][] blocks;
    private int m = -1;
    private int h = -1;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] b) {
        if (b == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < b.length; i++) {
            if (b[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        this.blocks = cloneArray(b);
    }

    // board dimension n
    public int dimension() {
        return blocks.length;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (m == -1) {
            m = 0;

            int n = dimension();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (blocks[i][j] == 0) {
                        continue;
                    }

                    int block =  blocks[i][j] - 1;

                    int bi = block / n;
                    int bj = block % n;

                    m += Math.abs(bi - i) + Math.abs(bj - j);
                }
            }
        }

        return m;
    }

    // number of blocks out of place
    public int hamming() {
        if (h == -1) {
            h = 0;

            int n = dimension();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (blocks[i][j] > 0 && blocks[i][j] != i * n + j + 1) {
                        h++;
                    }
                }
            }
        }

        return h;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        if (y == this) {
            return true;
        }

        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board b = (Board) y;

        if (b.dimension() != dimension()) {
            return false;
        }

        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (b.blocks[i][j] != blocks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public Board twin() {
        int[][] twin = cloneArray(blocks);

        int i = 0;
        if (twin[0][i] == 0) {
            i++;
        }

        int j = dimension() - 1;
        if (twin[dimension() - 1][j] == 0) {
            j--;
        }

        return exch(0, i, dimension() - 1, j);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int ie = -1;
        int je = -1;

        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (blocks[i][j] == 0) {
                    ie = i;
                    je = j;

                    break;
                }
            }

            if (ie != -1 && je != -1) {
                break;
            }
        }

        Bag<Board> bag = new Bag<>();

        if (ie > 0) {
            bag.add(exch(ie, je, ie - 1, je));
        }

        if (ie < dimension() - 1) {
            bag.add(exch(ie, je, ie + 1, je));
        }

        if (je > 0) {
            bag.add(exch(ie, je, ie, je - 1));
        }

        if (je < dimension() - 1) {
            bag.add(exch(ie, je, ie, je + 1));
        }

        return bag;
    }

    public String toString() {
        int n = dimension();

        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }

        return s.toString();
    }

    private Board exch(int is, int js, int it, int jt) {
        int[][] copy = cloneArray(blocks);

        int swap = copy[is][js];
        copy[is][js] = copy[it][jt];
        copy[it][jt] = swap;

        return new Board(copy);
    }

    private static int[][] cloneArray(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    /*
    public static void main(String[] args) {
        int[][] arr = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };

        Board b = new Board(arr);

        System.out.println(b.hamming());
        System.out.println(b.manhattan());

        System.out.println(b.twin().toString());

        System.out.println("neighbors: ");
        for(Board neighbor : b.neighbors()) {
            System.out.println(neighbor.toString());
        }
    }*/
}
