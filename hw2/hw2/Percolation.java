package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] grid;
    private final WeightedQuickUnionUF unionFind;
    private final int top;
    private final int bottom;
    private final int gridSize;
    private int openSites;
    private boolean percolates;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Number of grids must be positive");
        }

        // make an all-false grid
        this.grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }

        // cached attributes
        this.gridSize = N;
        this.openSites = 0;
        this.percolates = false;

        // establish a virtual union-find model
        this.unionFind = new WeightedQuickUnionUF(N * N + 2);
        this.top = 0;
        this.bottom = N * N + 1;
        for (int i = 0; i < N; i++) {
            unionFind.union(top, indexInUnion(0, i));
        }
        for (int i = 0; i < N; i++) {
            unionFind.union(bottom, indexInUnion(N - 1, i));
        }
    }

    /**
     * Check if the given operands are valid.
     * @param row the row index, should be between [0, N-1].
     * @param col the col index, should be between [0, N-1].
     * @throws IndexOutOfBoundsException if row or column is out of the grid.
     */
    private void checkArgs(int row, int col) throws IndexOutOfBoundsException {
        if (invalidRow(row) || invalidCol(col)) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    private boolean invalidRow(int row) {
        return row < 0 || row >= gridSize;
    }

    private boolean invalidCol(int col) {
        return col < 0 || col >= gridSize;
    }

    /**
     * Calculate the index in the union find interface given its indices in the gird.
     */
    private int indexInUnion(int row, int col) {
        return row * gridSize + col + 1;
    }

    /**
     * Open the site (row, col) if it is not open.
     */
    public void open(int row, int col) {
        checkArgs(row, col);

        // updates site if it is not open
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            openSites += 1;
            connectWithNeighbors(row, col);
        }

        // updates percolates if the number of open sites is greater than grid size
        if (openSites >= gridSize) {
            percolates = unionFind.connected(top, bottom);
        }
    }

    /**
     * Connect the given site (row, col) with its open neighbours.
     */
    private void connectWithNeighbors(int row, int col) {
        for (int r = row - 1; r <= row + 1; r ++) {
            if (invalidRow(r)) {
                continue;
            }
            for (int c = col - 1; c <= col + 1; c ++) {
                if (invalidCol(c) || isDiagonal(row, col, r, c)) {
                    continue;
                }
                if (isOpen(r, c)) {
                    unionFind.union(indexInUnion(row, col), indexInUnion(r, c));
                }
            }
        }
    }

    /**
     * Sites (r1, c1) and (r2, c2) are diagonal if the absolute differences of
     * their row and col are the same.
     * @return true if the two sites are diagonal, false otherwise.
     */
    private boolean isDiagonal(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) == Math.abs(c1 - c2);
    }

    /**
     * @return true if the site (row, col) is open, false otherwise.
     */
    public boolean isOpen(int row, int col) {
        checkArgs(row, col);
        return grid[row][col];
    }

    /**
     * A full site is an open site that can be connected to an open site in the top row
     * via a chain of neighboring (left, right, up, down) open sites.
     * @return true if the site (row, col) is full, false otherwise.
     */
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && unionFind.connected(top, indexInUnion(row, col));
    }

    /**
     * @return the number of open sites in the grid.
     */
    public int numberOfOpenSites() {
        return openSites;
    }

    /**
     * @return if the grid percolates.
     */
    public boolean percolates() {
        return percolates;
    }
}
