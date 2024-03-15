package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int start;
    private Maze maze;
    private boolean hasCycle = false;

    public MazeCycles(Maze m) {
        super(m);
        this.maze = m;
        this.start = maze.xyTo1D(1, 1);
        distTo[start] = 0;
        edgeTo[start] = start;
    }

    @Override
    public void solve() {
        dfsCycle(start);
    }

    private void dfsCycle(int v) {
        marked[v] = true;
        announce();

        for (int u : maze.adj(v)) {
            if (marked[u] && !isParent(u, v)) {
                hasCycle = true;
                return;
            }
            if (!marked[u]) {
                edgeTo[u] = v;
                announce();
                distTo[u] = distTo[v] + 1;
                dfsCycle(u);
            }
        }
    }

    private boolean isParent(int visited, int curr) {
        return distTo[visited] == distTo[curr] - 1;
    }
}

