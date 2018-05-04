import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Perform a series of computational experiments.
 * Use Monte Carlo simulation to estimate the percolation threshold.
*/
public class PercolationStats {
    /** Percolation confidence interval const. */
    private static double constT = 1.96;

    /** Number of computational experiments. */
    private final int trials;

    /** Fractions of open sites in computational experiments. */
    private final double[] results;

    /** mean of percolation threshold. */
    private Double mean;

    /** standard deviation of percolation threshold. */
    private Double stddev;

    /**
     * Perform trials independent experiments on an n-by-n grid.
     * @param nSize grid size
     * @param nTrials number of trials (computational experiments)
     */
    public PercolationStats(final int nSize, final int nTrials)  {
        if (nSize <= 0) {
            throw new IllegalArgumentException("n");
        }

        if (nTrials <= 0) {
            throw new IllegalArgumentException("trials");
        }

        trials = nTrials;

        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(nSize);

            while (!p.percolates()) {
                p.open(
                    StdRandom.uniform(1, nSize + 1),
                    StdRandom.uniform(1, nSize + 1)
                );
            }

            // The fraction of open sites in computational experiment
            results[i] = ((double) p.numberOfOpenSites()) / (nSize * nSize);
        }
    }

    /**
     * Get mean of percolation threshold.
     * @return mean value.
     */
    public double mean() {
        if (mean == null) {
            mean = StdStats.mean(results);
        }
        return mean;
    }

    /**
     * Get standard deviation of percolation threshold.
     * @return standard deviation value.
     */
    public double stddev() {
        if (stddev == null) {
            stddev = StdStats.stddev(results);
        }
        return stddev;
    }

    /**
     * Low  endpoint of 95% confidence interval.
     * @return confidenceLo value.
     */
    public double confidenceLo() {
        return mean() - (constT * stddev()) / Math.sqrt(trials);
    }

    /**
     * High endpoint of 95% confidence interval.
     * @return confidenceHi value.
     */
    public double confidenceHi() {
        return mean() + (constT * stddev()) / Math.sqrt(trials);
    }

    /**
     * test client (described below).
     * @param args standard input params.
     */
    public static void main(final String[] args) {
        PercolationStats stats = new PercolationStats(
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));

        StdOut.printf("mean = %f\n", stats.mean());
        StdOut.printf("stddev = %f\n", stats.stddev());
        StdOut.printf("%%95 confidence interval = [%f, %f]\n",
                stats.confidenceLo(),
                stats.confidenceHi());
    }
}
