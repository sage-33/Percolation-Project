import edu.princeton.cs.algs4.WeightedQuickUnionUF;

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
 *         A {@link PercolationStats} performs a series of trials using the
 *         percolation class. is a 2D grid of open or closed sites and this
 *         class provides methods to access and open sites
 */
public class Percolation {
	private boolean[][] grid; // true = open | false = closed
	private int numOfOpenSites; // number of open sites in the grid
	private WeightedQuickUnionUF percolationChecker; // percolation object with 2 virtual sites
	private WeightedQuickUnionUF isFullChecker; // percolation object with 1 virtual site

	/**
	 * Constructs a Percolation object with a n * n grid.
	 * 
	 * @param n number of rows and cols
	 */
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		percolationChecker = new WeightedQuickUnionUF(n * n + 2); // plus to for the top and bottom virtual sites
		isFullChecker = new WeightedQuickUnionUF(n * n + 1); // doesn't have a bottom virtual site to prevent backwash
		grid = new boolean[n][n];
		numOfOpenSites = 0;
		// union the top site with the top row (percolation)
		for (int i = 1; i <= grid.length; i++) {
			percolationChecker.union(0, i);
		}
		// union the bottom virtual site with the bottom row (percolation)
		for (int i = 0; i < grid.length; i++) {
			percolationChecker.union(grid.length * grid.length + 1, grid.length * grid.length - i);
		}
		// union the top site with the top row (isFullUF)
		for (int i = 1; i <= grid.length; i++) {
			isFullChecker.union(0, i);
		}
	}

	/**
	 * Converts a site's position in a 2D array to a position in a 1D array.
	 * 
	 * @param row row index
	 * @param col column index
	 * @return 1D array index
	 */
	private int convert2DTo1D(int row, int col) {
		return (row * grid.length) + col + 1;
	}

	/**
	 * Opens a site a given (row, col).
	 * 
	 * @param row row index
	 * @param col column index
	 */
	public void open(int row, int col) {
		if (row > grid.length || row < 0 || col > grid.length || col < 0) {
			throw new IllegalArgumentException();
		}
		int gridRow = row - 1;
		int gridCol = col - 1;
		if (grid[gridRow][gridCol]) {
			return;
		}

		numOfOpenSites++;
		int currentPosition1D = convert2DTo1D(gridRow, gridCol);
		// open it in our grid(boolean grid, set to true)
		grid[gridRow][gridCol] = true;
		// open it in WeightedQuickUnion by union-ing it with the adjacent open sites

		// below
		if (gridRow + 1 < grid.length && grid[gridRow + 1][gridCol]) {
			percolationChecker.union(currentPosition1D, convert2DTo1D(gridRow + 1, gridCol));
			isFullChecker.union(currentPosition1D, convert2DTo1D(gridRow + 1, gridCol));
		}

		// above
		if (gridRow - 1 >= 0 && grid[gridRow - 1][gridCol]) {
			percolationChecker.union(currentPosition1D, convert2DTo1D(gridRow - 1, gridCol));
			isFullChecker.union(currentPosition1D, convert2DTo1D(gridRow - 1, gridCol));
		}

		// right
		if (gridCol + 1 < grid.length && grid[gridRow][gridCol + 1]) {
			percolationChecker.union(currentPosition1D, convert2DTo1D(gridRow, gridCol + 1));
			isFullChecker.union(currentPosition1D, convert2DTo1D(gridRow, gridCol + 1));
		}

		// left
		if (gridCol - 1 >= 0 && grid[gridRow][gridCol - 1]) {
			percolationChecker.union(currentPosition1D, convert2DTo1D(gridRow, gridCol - 1));
			isFullChecker.union(currentPosition1D, convert2DTo1D(gridRow, gridCol - 1));
		}

	}

	/**
	 * Returns <code/>true</code> if the site at (row, col) is open.
	 * 
	 * @param row row index
	 * @param col column index
	 * @return <code/>true</code> if the site at (row, col) is open
	 *         <code/>false</code> otherwise
	 */
	public boolean isOpen(int row, int col) {
		if (row > grid.length || row < 0 || col > grid.length || col < 0) {
			throw new IllegalArgumentException();
		}
		return grid[row - 1][col - 1];
	}

	/**
	 * Returns <code/>true</code> if the site is connected to the top virtual site.
	 * 
	 * @param row row index
	 * @param col column index
	 * @return <code/>true</code> if the site is connected to the top virtual site
	 *         <code/>false</code> otherwise
	 */
	public boolean isFull(int row, int col) {
		if (row > grid.length || row < 0 || col > grid.length || col < 0) {
			throw new IllegalArgumentException();
		}
		return isOpen(row, col) && (isFullChecker.find(0) == isFullChecker.find(convert2DTo1D(row - 1, col - 1)));
	}

	/**
	 * Returns the number of open sites
	 * 
	 * @return number of open sites
	 */
	public int numberOfOpenSites() {
		return numOfOpenSites;
	}

	/**
	 * Returns <code/>true</code> if the percolation object's top virtual site to
	 * the bottom virtual site.
	 * 
	 * @return <code/>true</code> if the percolation object's top virtual site to
	 *         the bottom virtual site <code/>false</code> otherwise
	 */
	public boolean percolates() {
		return numOfOpenSites > 0
				&& percolationChecker.find(0) == percolationChecker.find(grid.length * grid.length + 1);
	}
}