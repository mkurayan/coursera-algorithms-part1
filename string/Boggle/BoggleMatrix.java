import java.lang.reflect.Array;
import java.util.ArrayList;


public class BoggleMatrix {
    private final int r;
    private final int c;

    private final ArrayList<BoggleCell>[][] cells;

    public BoggleMatrix(int rows, int cols) {
        this.r = rows;
        this.c = cols;

        // Java is fun!!!
        ArrayList<BoggleCell> tmp = new ArrayList<>();
        cells = (ArrayList<BoggleCell>[][]) Array.newInstance(tmp.getClass(), rows, cols);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cells[row][col] = adjacentCells(row, col);
            }
        }
    }

    public int rows() {
        return r;
    }

    public int cols() {
        return c;
    }

    // Returns the cells adjacent to cell.
    public Iterable<BoggleCell> adj(int row, int col) {
        return cells[row][col];
    }

    private ArrayList<BoggleCell>  adjacentCells(int row, int col) {
        ArrayList<BoggleCell> adjCells = new ArrayList<>();

        addIfInRange(adjCells, row + 1, col);
        addIfInRange(adjCells, row - 1, col);
        addIfInRange(adjCells, row, col + 1);
        addIfInRange(adjCells, row, col - 1);
        addIfInRange(adjCells, row + 1, col + 1);
        addIfInRange(adjCells, row + 1, col - 1);
        addIfInRange(adjCells, row - 1, col + 1);
        addIfInRange(adjCells, row - 1, col - 1);

        return adjCells;
    }

    private void addIfInRange(ArrayList<BoggleCell> adjCells, int row, int col) {
        if ((0 <= row && row < r) && (0 <= col && col < c)) {
            adjCells.add(new BoggleCell(row, col));
        }
    }
}

