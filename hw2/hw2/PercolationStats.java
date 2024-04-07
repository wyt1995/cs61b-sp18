package hw2;

import edu.princeton.cs.introcs.StdRandom;
import java.util.Arrays;

public class PercolationStats {
    private final int trialTime;
    private final int gridNumber;
    private double[] threshold;

    /**
     * Perform T independent experiments on an N-by-N grid.
     * @param N the size of the grid.
     * @param T the number of experiments.
     * @param pf used for making percolation grids.
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (T <= 0 || N <= 0) {
            throw new IllegalArgumentException("Number of trials must be greater than 0");
        }

        trialTime = T;
        gridNumber = N * N;
        threshold = new double[trialTime];
        for (int i = 0; i < trialTime; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                p.open(row, col);
            }
            threshold[i] = (double) p.numberOfOpenSites() / (double) gridNumber;
        }
    }

    /**
     * @return the sample mean of percolation threshold.
     */
    public double mean() {
        return Arrays.stream(threshold).reduce(0.0, Double::sum) / trialTime;
    }

    /**
     * @return the sample standard deviation of percolation threshold.
     */
    public double stddev() {
        double dev = 0.0;
        double mean = mean();
        for (int i = 0; i < trialTime; i++) {
            dev += Math.pow(threshold[i] - mean, 2);
        }
        return Math.sqrt(dev / trialTime);
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / gridNumber;
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / gridNumber;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: PercolationStats <N> <T>");
        }
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, t, new PercolationFactory());
        System.out.println("Mean: " + stats.mean());
        System.out.println("Standard deviation: " + stats.stddev());
        System.out.println(String.format("95%% confidence interval: [%.5f, %.5f]",
                stats.confidenceLow(), stats.confidenceHigh()));
    }
}
