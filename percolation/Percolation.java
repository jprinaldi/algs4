public class Percolation {
    private int N;
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF vuf;
    
    public Percolation(int N) {
        this.N = N;
        grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
        uf = new WeightedQuickUnionUF(N*N + 1);
        vuf = new WeightedQuickUnionUF(N*N + 2);
    }
    
    private int component(int i, int j) {
        return N*(i - 1) + j - 1;
    }
    
    public void open(int i, int j) {
        if (isOpen(i, j))
            return;
        grid[i - 1][j - 1] = true;
        
        if (i > 1 && isOpen(i - 1, j)) {
            uf.union(component(i, j), component(i - 1, j));
            vuf.union(component(i, j), component(i - 1, j));
        }
        
        if (i < N && isOpen(i + 1, j)) {
            uf.union(component(i, j), component(i + 1, j));
            vuf.union(component(i, j), component(i + 1, j));
        }
        
        if (j > 1 && isOpen(i, j - 1)) {
            uf.union(component(i, j), component(i, j - 1));
            vuf.union(component(i, j), component(i, j - 1));
        }
        
        if (j < N && isOpen(i, j + 1)) {
            uf.union(component(i, j), component(i, j + 1));
            vuf.union(component(i, j), component(i, j + 1));
        }
        
        if (i == 1) {
            uf.union(component(i, j), N*N);
            vuf.union(component(i, j), N*N);
        }
        
        if (i == N)
            vuf.union(component(i, j), N*N + 1);
    }
    
    public boolean isOpen(int i, int j) {
        if (i < 1 || j < 1 || i > N || j > N)
            throw new IndexOutOfBoundsException();
        return grid[i - 1][j - 1];
    }
    
    public boolean isFull(int i, int j) {
        if (i < 1 || j < 1 || i > N || j > N)
            throw new IndexOutOfBoundsException();
        return uf.connected(component(i, j), N*N);
    }
    
    public boolean percolates() {
        return vuf.connected(N*N, N*N + 1);
    }
}