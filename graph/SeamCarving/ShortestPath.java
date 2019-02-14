import edu.princeton.cs.algs4.Stack;

public class ShortestPath {
    // distance of shortest top->v path
    private double[][] distTo;

    // edge to v = last edge on shortest path to the top.
    private Coordinate[][] pathTo;

    private final int height;
    private final int width;

    ShortestPath(int h, int w, double[][] matrix) {
        height = h;
        width = w;

        distTo = new double[height][width];
        pathTo = new Coordinate[height][width];

        for (int x = 0; x < width; x++) {
            distTo[0][x] = matrix[0][x];
            pathTo[0][x] = new Coordinate(0, x);
        }

        for (int y = 1; y < height; y++) {
            for (int x = 0; x < width; x++) {
                distTo[y][x] = Double.POSITIVE_INFINITY;
            }
        }

        for (int y = 0; y < height - 1; y++) {
            for (int x = 0; x < width; x++) {
                int yTo = y + 1;

                if (x - 1 >= 0) {
                    relax(matrix, x, y, x - 1, yTo);
                }

                relax(matrix, x, y, x, yTo);

                if (x + 1 < width) {
                    relax(matrix, x, y, x + 1, yTo);
                }
            }
        }
    }

    public int[] verticalSeam() {
        double minDist = Double.POSITIVE_INFINITY;
        int minX = 0;
        int minY = height - 1;
        for (int x = 0; x < width; x++) {
            if (distTo[minY][x] < minDist) {
                minDist = distTo[minY][x];
                minX = x;
            }
        }

        Stack<Coordinate> stack = new Stack<>();
        Coordinate path = new Coordinate(minX, minY);

        for (int i = minY; i >= 0; i--) {
            stack.push(path);
            path = pathTo[path.y][path.x];
        }

        int[] vSeam = new int[height];

        int index = 0;
        for (Coordinate c : stack) {
            vSeam[index++] = c.x;
        }

        return vSeam;
    }

    private void relax(double[][] e, int xFrom, int yFrom, int xTo, int yTo) {
        if (distTo[yTo][xTo] > distTo[yFrom][xFrom] + e[yTo][xTo]) {
            distTo[yTo][xTo] = distTo[yFrom][xFrom] + e[yTo][xTo];

            pathTo[yTo][xTo] = new Coordinate(xFrom, yFrom);
        }
    }

    private class Coordinate {
        private final int x;
        private final int y;

        Coordinate(int xCoord, int yCoord) {
            this.x = xCoord;
            this.y = yCoord;
        }
    }
}
