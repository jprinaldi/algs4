public class PercolationStats {
    private int T;
    private double[] thresholds;
    
    public PercolationStats(int N, int T) {
        if (N < 0 || T <= 0)
            throw new IllegalArgumentException();
        this.T = T;
        int i, j, openCount;
        thresholds = new double[T];
        for (int exp = 0; exp < T; exp++) {
            openCount = 0;
            Percolation p = new Percolation(N);
            while (!p.percolates()) {
                i = StdRandom.uniform(N) + 1;
                j = StdRandom.uniform(N) + 1;
                if (!p.isOpen(i, j)) {
                    p.open(i, j);
                    openCount++;
                }
            }
            thresholds[exp] = (double) openCount/(N*N);
        }
    }
    public double mean() {
        return StdStats.mean(thresholds);
    }
    public double stddev() {
        return StdStats.stddev(thresholds);
    }
    public double confidenceLo() {
        return this.mean() - 1.96*this.stddev()/Math.sqrt(T);
    }
    public double confidenceHi() {
        return this.mean() + 1.96*this.stddev()/Math.sqrt(T);
    }
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);
        System.out.printf("mean                    = %.16f\n", ps.mean());
        System.out.printf("stddev                  = %.16f\n", ps.stddev());
        System.out.printf("95%% confidence interval = %.16f %.16f\n",
                          ps.confidenceLo(), ps.confidenceHi());
    }
}