import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/******************************************************************************
 * Name: Kevin Wayne Login: wayne Precept: P01
 *
 * Partner Name: N/A Partner Login: N/A Partner Precept: N/A
 *
 * Compilation: javac-algs4 Percolation.java Execution: java-algs4 Percolation
 * Dependencies: StdIn.java StdRandom.java WeightedQuickUnionUF.java
 *
 * Description: Modeling Percolation like a boss. woot. woot.
 ******************************************************************************/
/**
 * @author sagesilberman
 * 
 *         A {@link PercolationStats} performs a series of trials by inputing
 *         the results into a array. The values in the array are used to
 *         calculate mean, standard deviation, confidence high and low.
 */
public class PercolationStats {
	private double[] trialRuns; // stores the ratio of open sites to total sites from each trial's index

	/**
	 * Constructs a new {@link PercolationStats} with a n-by-n and trials number of
	 * trial
	 * 
	 * @param n      number of row and columns
	 * @param trials number of trials
	 */
	public PercolationStats(int n, int trials) {
		if (n < 1 || trials < 1) {
			throw new IllegalArgumentException("cannot create a 0-by-0 grid or can't have 0 trials");
		}
		trialRuns = new double[trials];
		for (int i = 0; i < trials; i++) {
			Percolation perc = new Percolation(n);
			while (!perc.percolates()) {
				perc.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
			}
			trialRuns[i] = (double) (perc.numberOfOpenSites()) / (n * n);
		}
	}

	/**
	 * Returns the mean of the values in the trialRuns array.
	 * 
	 * @return the mean of the values in the trialRuns array
	 */
	public double mean() {
		return StdStats.mean(trialRuns);
	}

	/**
	 * Returns the standard deviation of the values in the trialRuns array.
	 * 
	 * @return the standard deviation of the values in the trialRuns array
	 */
	public double stddev() {
		return StdStats.stddev(trialRuns);
	}

	/**
	 * Returns the confidence low of the values in the trialRuns array.
	 * 
	 * @return the confidence low of the values in the trialRuns array
	 */
	public double confidenceLo() {
		return mean() - ((1.96 * stddev()) / (Math.sqrt((double) trialRuns.length)));
	}

	/**
	 * Returns the confidence high of the values in the trialRuns array.
	 * 
	 * @return the confidence high of the values in the trialRuns array
	 */
	public double confidenceHi() {
		return mean() + ((1.96 * stddev()) / (Math.sqrt((double) trialRuns.length)));
	}

	/**
	 * Prints out the results of the all the methods.
	 * 
	 * @param args program input
	 */
	public static void main(String[] args) {
		// test client (described at
		// http://coursera.cs.princeton.edu/algs4/assignments/percolation.html)
		int n = Integer.parseInt(args[0]); // converts our first argument to an integer
		int trials = Integer.parseInt(args[1]);
		PercolationStats percStats = new PercolationStats(n, trials);
		System.out.println("mean = " + percStats.mean());
		System.out.println("stddev = " + percStats.stddev());
		System.out.println(
				"95% confidence interval = [" + percStats.confidenceLo() + ", " + percStats.confidenceHi() + "]");
	}
}