import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Programming Assignment 1: Percolation.
 */
public class Percolation {
    /** Grid size. */
    private final int size;

    /**
     * Union–find data type.
     * Solve percolation problem itself.
     * Contains virtual top and bottom sites for percolation check.
     */
    private final WeightedQuickUnionUF topAndBottomUnion;

    /**
     * Union–find data type.
     * Solve backwash problem.
     * Contains virtual top only (virtual bottom ignored)
     */
    private final WeightedQuickUnionUF topOnlyUnion;


    /** Number of open sites in grid. */
    private int numberOfOpenSites = 0;

    /** Represent percolation grid with open and closed sites in it. */
    private boolean[] pGrid;

    /**
     * Create n-by-n grid, with all sites blocked.
     * @param n size of n-by-n grid.
     */
    public Percolation(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n");
        }

        size = n;

        topAndBottomUnion = new WeightedQuickUnionUF(n * n + 2);
        topOnlyUnion = new WeightedQuickUnionUF(n * n + 2);
        pGrid = new boolean[n * n + 2];
    }

    /**
     * open site (row, col) if it is not open already.
     * @param row row in grid
     * @param col column in grid
     */
    public void open(final int row, final int col) {
        int index = coordinatesToIndex(row, col);
        if (!pGrid[index]) {
            pGrid[index] = true;
            numberOfOpenSites++;

            if (row == 1) {
                // This is a first row in a grid.
                // Connect site to virtual top.
                topAndBottomUnion.union(index, 0);
                topOnlyUnion.union(index, 0);
            }

            if (row == size) {
                // This is a last row in a grid.
                // Connect site to virtual bottom.
                topAndBottomUnion.union(index, 1);
            }

            if (row + 1 <= size && pGrid[coordinatesToIndex(row + 1, col)]) {
                topAndBottomUnion.union(index, coordinatesToIndex(row + 1, col));
                topOnlyUnion.union(index, coordinatesToIndex(row + 1, col));
            }

            if (row - 1 > 0 && pGrid[coordinatesToIndex(row - 1, col)]) {
                topAndBottomUnion.union(index, coordinatesToIndex(row - 1, col));
                topOnlyUnion.union(index, coordinatesToIndex(row - 1, col));
            }

            if (col + 1 <= size && pGrid[coordinatesToIndex(row, col + 1)]) {
                topAndBottomUnion.union(index, coordinatesToIndex(row, col + 1));
                topOnlyUnion.union(index, coordinatesToIndex(row, col + 1));
            }

            if (col - 1 > 0 && pGrid[coordinatesToIndex(row, col - 1)]) {
                topAndBottomUnion.union(index, coordinatesToIndex(row, col - 1));
                topOnlyUnion.union(index, coordinatesToIndex(row, col - 1));
            }
        }
    }

    /**
     * Check if site (row, col) open.
     * @param row row in grid
     * @param col column in grid
     * @return is open?.
     */
    public boolean isOpen(final int row, final int col) {
        return pGrid[coordinatesToIndex(row, col)];
    }

    /**
     * Check if site (row, col) full.
     * @param row row in grid
     * @param col column in grid
     * @return is full?
     */
    public boolean isFull(final int row, final int col) {
        // First quick check if site open, if yes then check it connect to virtual top.
        return isOpen(row, col) && topOnlyUnion.connected(0,  coordinatesToIndex(row, col));
    }

    /**
     * Get number of open sites.
     * @return number of open sites.
     */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    /**
     * Check if system percolate.
     * @return does the system percolate?
     */
    public boolean percolates() {
        return  topAndBottomUnion.connected(0,  1);
    }

    /**
     * Convert grid row and column indexes to one dimension array index.
     * @param row row in grid
     * @param col column in grid
     * @return  index of site in percolation grid
     */
    private int coordinatesToIndex(final int row, final int col) {
        boundaryCheck(row);
        boundaryCheck(col);

        return 2 + (row - 1) * size + (col - 1);
    }

    /**
     * Check boundary of percolation grid.
     * @param i index of site in percolation grid
     */
    private void boundaryCheck(final int i) {
        if (i < 1 || i > size) {
            throw new IllegalArgumentException();
        }
    }
}
