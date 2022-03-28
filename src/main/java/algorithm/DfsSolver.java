package algorithm;

import elements.Tracker;

import java.util.*;

/**
 * Depth first search algorithm implementation.
 */
public class DfsSolver implements Solver {
    private final int MAX_DEPTH; // defaults to 20
    private final String policy;
    private final Tracker tracker = new Tracker("dfs");
    private String path = ""; // path to solution, unknown so far

    public DfsSolver(String policy) {
        this(policy,20);
    }

    public DfsSolver(String policy, int maxDepth) {
        this.policy = policy;
        this.MAX_DEPTH = maxDepth;
    }

    /**
     * Provides a tracker.
     * Tracker saves the information about:
     * - time taken to solve the problem.
     * - nodes visited etc.
     * @return Tracker
     */
    @Override
    public Tracker getTracker() {
        return tracker;
    }

    /**
     * Public solve method
     * @param matrix starting point matrix.
     */
    @Override
    public void solve(List<List<Integer>> matrix) {
        tracker.start(); // manually start tracking
        boolean success = this.solve(0,matrix,new HashSet<>());
        tracker.stop(); // you have to manually stop the tracker.
        if (success) {
            tracker.setSolution(this.path); // save solution inside the tracker
        }
        else {
            tracker.setSolution("DFS Failed");
        }

    }

    /**
     * Depth first search algorithm.
     * @param depth current recursion depth.
     * @param matrix matrix of Integers (the puzzle).
     * @param discovered Set of already discovered states (memoization).
     * @return true when provided puzzle is solved, false otherwise
     */
    private boolean solve(int depth, List<List<Integer>> matrix, Set<List<List<Integer>>> discovered) {
        tracker.updateMaxDepth(depth);
        tracker.incrementProcessedNodes();
        tracker.incrementUniqueNodes();
        discovered.add(matrix);

        if (Helper.isFinished(matrix)) {
            return true;
        } else if (depth == MAX_DEPTH) {
            return false;
        }
        LinkedHashMap<Character, List<List<Integer>>> nextNodes = Helper.getNext(matrix,policy);
        for (var entry : nextNodes.entrySet()) {
            Character key = entry.getKey();
            List<List<Integer>> node = entry.getValue();
            if (!discovered.contains(node) && solve(depth + 1, node, discovered)) {
                this.path = key + this.path;
                return true;
            }
        }

        return false;
    }

    /**
     * Getter of path.
     * @return String representing the solution/path of letters from start to finish.
     */
    public String getPath() {
        return path;
    }

}
