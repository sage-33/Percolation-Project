import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import edu.princeton.cs.algs4.In;

public class PercolationStatsTest {

	private PercolationStats percStats200;

	@Before
	public void setUp() throws Exception {
		percStats200 = new PercolationStats(200, 100);
	}

	@Test
	public void testOpeningSites() {
// if sites are not opening then the mean will be zero, because 0 divided by
// anything is zero
		assertTrue(percStats200.mean() != 0);
	}

	@Test
	public void testMean() {
		assertEquals(percStats200.mean(), 0.59, 0.01);
	}

	@Test
	public void testStddev() {
		assertEquals(percStats200.stddev(), 0.009, 0.005);
	}

	@Test
	public void testConfidenceLo() {
		assertEquals(percStats200.confidenceLo(), 0.59, 0.01);
	}

	@Test
	public void testConfidenceHigh() {
		assertEquals(percStats200.confidenceHi(), 0.59, 0.01);
		assertTrue(percStats200.confidenceLo() < percStats200.confidenceHi());
	}
}
