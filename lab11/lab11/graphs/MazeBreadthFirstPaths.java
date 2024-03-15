package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private int source;
    private int target;
    private Maze maze;
    private boolean targetFound = false;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        this.maze = m;
        this.source = maze.xyTo1D(sourceX, sourceY);
        this.target = maze.xyTo1D(targetX, targetY);
        this.distTo[source] = 0;
        this.edgeTo[source] = source;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> fringe = new ArrayDeque<>();
        fringe.add(source);
        marked[source] = true;
        announce();
        while (!fringe.isEmpty()) {
            int relaxed = fringe.remove();
            for (int v : maze.adj(relaxed)) {
                // stop iteration as long as target is found
                if (v == target) {
                    targetFound = true;
                    return;
                }

                // update distTo, edgeTo, and marked for every unvisited vertex
                if (!marked[v]) {
                    edgeTo[v] = relaxed;
                    distTo[v] = distTo[relaxed] + 1;
                    announce();
                    marked[v] = true;
                    fringe.add(v);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

