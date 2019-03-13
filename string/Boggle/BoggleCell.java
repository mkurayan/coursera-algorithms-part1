public class BoggleCell {
    private final int r;
    private final int c;

    public BoggleCell(int row, int col) {
       r = row;
       c = col;
    }

    public int row() {
        return r;
    }

    public int col() {
        return c;
    }
}
