/*----------------------------------------------------------------
*  Author:        Hsin Han Liu
*  Written:       6/30/2014
*  Last updated:  6/30/2014
*
*----------------------------------------------------------------*/
public class Percolation {
	private WeightedQuickUnionUF uf,ufNobackwash;
	private int size, N;
	private boolean[][] openStatus;

	public Percolation(int N)              // create N-by-N grid, with all sites blocked
	{
		if (N  <= 0)
		   throw new IllegalArgumentException("N out of bounds");
		this.N = N;
		size = N * N + 2;
		uf = new WeightedQuickUnionUF(size);
		ufNobackwash = new WeightedQuickUnionUF(size);

		openStatus = new boolean[N][N];
	}
	public void open(int i, int j)         // open site (row i, column j) if it is not already
	{
		if (i <= 0 || i > N || j <= 0 || j > N)
		   throw new IndexOutOfBoundsException("row index i out of bounds");
		while (!isOpen(i, j)) {

			openStatus[i-1][j-1] = true;

			unite(i, j, i - 1, j);
			unite(i, j, i + 1, j);
			unite(i, j, i, j - 1);
			unite(i, j, i, j + 1);

			if (i == 1){
			   	uf.union(0 , j);
				ufNobackwash.union(0 , j);
			}
			if (i == N)
			   uf.union(N*N+1 , N*(N-1)+j);
		}
	}
	public boolean isOpen(int i, int j)    // is site (row i, column j) open?
	{
		if (i <= 0 || i > N || j <= 0 || j > N)
		   throw new IndexOutOfBoundsException("row index i out of bounds");
		return openStatus[i-1][j-1];
	}
	public boolean isFull(int i, int j)    // is site (row i, column j) full?
	{
		if (i <= 0 || i > N || j <= 0 || j > N)
		   throw new IndexOutOfBoundsException("row index i out of bounds");
		return ufNobackwash.connected(0, (i-1)*N + j);
	   
	}
	public boolean percolates()            // does the system percolate?
	{
		return uf.connected(N*N+1, 0);	   
	}

	private void unite(int i, int j, int m, int n) { // 1-based coordinates
		if (m > 0 && n > 0 && m <= N && n <= N && isOpen(m, n)) {
			uf.union((i-1)*N+j, (m-1)*N+n);
			ufNobackwash.union((i-1)*N+j, (m-1)*N+n);
		}
	}


/*public static void main(String[] args) {
    Percolation p = new Percolation(3);
    p.open(1, 3);
    p.open(2, 3);
    p.open(3, 3);
    p.open(3, 1);

    if (p.isFull(3, 1)) {
        StdOut.println("FFF");
    }

    p.open(2, 1);
    p.open(1, 1);

    if (p.percolates()) {
        StdOut.println("Yes");
    } else {
        StdOut.println("No");
    }
}*/

}