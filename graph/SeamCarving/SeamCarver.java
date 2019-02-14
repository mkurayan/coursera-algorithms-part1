import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private static final int BORDER_ENERGY = 1000;

    private int width;
    private int height;
    private int[][] pixels;
    private double[][] energy;

    private boolean isTransposed = false;

    public SeamCarver(Picture picture)  {
        if (picture == null) {
            throw new IllegalArgumentException();
        }

        width = picture.width();
        height = picture.height();

        pixels = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[y][x] = picture.getRGB(x, y);
            }
        }

        energy = new double[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width(); x++) {
                energy[y][x] = energy(pixels, height, width, x, y);
            }
        }
    }

    // current picture
    public Picture picture() {
        if (isTransposed) {
            transposeImage();
        }

        Picture pict = new Picture(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pict.setRGB(x, y, pixels[y][x]);
            }
        }

        return pict;
    }

    // width of current picture
    public int width() {
        return isTransposed ? height : width;
    }

    // height of current picture
    public int height() {
        return isTransposed ? width : height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        int row = isTransposed ? x : y;
        int column = isTransposed ? y : x;

        validateYIndex(row);
        validateXIndex(column);

        return energy[row][column];
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        if (isTransposed) {
            transposeImage();
        }

        return new ShortestPath(height, width, energy).verticalSeam();
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        if (!isTransposed) {
            transposeImage();
        }

        return new ShortestPath(height, width, energy).verticalSeam();
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (isTransposed) {
            transposeImage();
        }

        removeSeam(seam);
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (!isTransposed) {
            transposeImage();
        }

        removeSeam(seam);
    }

    private void removeSeam(int[] seam) {
        if (seam == null) {
            throw new IllegalArgumentException("seam argument is null");
        }

        if (seam.length != height) {
            throw new IllegalArgumentException("Invalid seam length.");
        }

        for (int i = 0; i < seam.length; i++) {
            validateXIndex(seam[i]);
        }

        validateSeamContinuity(seam);

        // Reduce pixels and energy
        for (int y = 0; y < height; y++) {
            System.arraycopy(pixels[y], seam[y] + 1, pixels[y],  seam[y], width - 1 - seam[y]);
            System.arraycopy(energy[y], seam[y] + 1, energy[y],  seam[y], width - 1 - seam[y]);
        }

        width--;

        // Recalculate energy for pixels along the seam.
        for (int y = 0; y < height; y++) {
            int x = seam[y];

            if (x < width) {
                energy[y][x] = energy(pixels, height, width, x, y);
            }

            if (x - 1 > 0) {
                energy[y][x - 1] = energy(pixels, height, width, x - 1, y);
            }
        }
    }

    private void transposeImage() {
        int transposedHeight = width;
        int transposedWidth = height;

        double[][] transposedEnergy = new double[transposedHeight][transposedWidth];
        int[][] transposedPixels = new int[transposedHeight][transposedWidth];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                transposedEnergy[x][y] = energy[y][x];
                transposedPixels[x][y] = pixels[y][x];
            }
        }

        width = transposedWidth;
        height = transposedHeight;

        pixels = transposedPixels;
        energy = transposedEnergy;

        isTransposed = !isTransposed;
    }


    private void validateSeamContinuity(int[] seam) {
        for (int i = 1; i < seam.length; i++) {
            if (Math.abs(seam[i - 1] - seam[i]) > 1) {
                throw new IllegalArgumentException("Seam breaks at index: " + i);
            }
        }
    }

    private void validateYIndex(int y) {
        if (y < 0 || y >= height) {
            throw new IllegalArgumentException("index must be between 0 and " + (height - 1) + ": " + y);
        }
    }

    private void validateXIndex(int x) {
        if (x < 0 || x >= width) {
            throw new IllegalArgumentException("index must be between 0 and " + (width - 1) + ": " + x);
        }
    }

    private static double energy(int[][] pixels, int height, int width, int x, int y) {
        if (y == 0 || y == height - 1) {
            return BORDER_ENERGY;
        }

        if (x == 0 || x == width - 1) {
            return BORDER_ENERGY;
        }

        return Math.sqrt(squareOfGradient(pixels[y][x - 1], pixels[y][x + 1]) + squareOfGradient(pixels[y - 1][x], pixels[y + 1][x]));
    }

    private static double squareOfGradient(int rgb1,  int rgb2) {
        int r1 = (rgb1 >> 16) & 0xFF;
        int g1 = (rgb1 >>  8) & 0xFF;
        int b1 = (rgb1 >>  0) & 0xFF;

        int r2 = (rgb2 >> 16) & 0xFF;
        int g2 = (rgb2 >>  8) & 0xFF;
        int b2 = (rgb2 >>  0) & 0xFF;

        return Math.pow(r1 - r2, 2) + Math.pow(g1 - g2, 2) + Math.pow(b1 - b2, 2);
    }
}
