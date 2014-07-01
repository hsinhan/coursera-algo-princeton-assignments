public class PercolationStats {

private double[] thresholds;
private int T; 
public PercolationStats(int N, int T) {
	// perform T independent computational experiments on an N-by-N grid
	if (T <= 0 || N <= 0) {
		throw new IllegalArgumentException("N and T should be positive!");
	}
	this.T = T;
	thresholds = new double[T];
	for (int i = 0; i < T; i++) {
		Percolation p = new Percolation(N);
		double openSitesCount = 0;
		while (!p.percolates()) {
			int x = StdRandom.uniform(1, N + 1);
			int y = StdRandom.uniform(1, N + 1);
			if (!p.isOpen(x, y)) {
				p.open(x, y);
				openSitesCount++;
			}
		}
		thresholds[i] = openSitesCount / (N*N);
	}
}
	public double mean() {
	   return StdStats.mean(thresholds);
	}
	public double stddev() {
	   return StdStats.stddev(thresholds);
	}
	public double confidenceLo() {
	   return StdStats.mean(thresholds) - 1.96 * StdStats.stddev(thresholds) / Math.sqrt(this.T);
	}
	public double confidenceHi() {
	   return StdStats.mean(thresholds) + 1.96 * StdStats.stddev(thresholds) / Math.sqrt(this.T);
	}
	public static void main(String[] args) {

		PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

		StdOut.println("mean = " + stats.mean());

		StdOut.println("stddev = " + stats.stddev());

		StdOut.println("95% confidence interval = " + stats.confidenceLo() + ", "
				+ stats.confidenceHi());

	} 


}
