package algorithm;

import elements.Tracker;
import org.javatuples.Pair;

import java.util.*;

/**
 * Breadth-first search algorithm implementation.
 */
public class BfsSolver implements Solver {
    private final String policy;
    private final Tracker tracker = new Tracker("bfs");
    private String path = ""; // path to solution, unknown so far

    public BfsSolver(String policy) {
        this.policy = policy;
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
     * Solves provided puzzle and saves the solution in path variable.
     * @param matrix puzzle to solve.
     */
    @Override
    public void solve(List<List<Integer>> matrix) {
        tracker.start(); // you have to manually start tracker (timer).
        boolean success = this.solve(new Pair<>("", matrix));
        tracker.stop(); // you have to manually stop the tracker (timer).
        if (success) {
            tracker.setSolution(this.path);
        } else {
            tracker.setSolution("BFS failed"); // bfs failed
        }
    }

    /**
     * Solves provided puzzle.
     * @param stringMatrixPair String - puzzle pair.
     * @return true if solution found, false otherwise.
     */
    private boolean solve(Pair<String,List<List<Integer>>> stringMatrixPair) {
        tracker.updateMaxDepth(0);
        Set<List<List<Integer>>> visited = new HashSet<>(); // set of visited matrices
        // queue of paths, each path element is [String,Matrix] pair
        // Matrix is a List<List<Integer>>
        Queue<List <Pair<String,List<List<Integer>>>>> queue = new LinkedList<>();


        List<Pair<String,List<List<Integer>>>> path0 = new ArrayList<>(); // list containing pairs [String,Matrix], Matrix is a List<List<Integer>>
        path0.add(stringMatrixPair); // add pair [String,Matrix] to the empty path.
        visited.add(stringMatrixPair.getValue1()); // add [Matrix] to visited.
        tracker.incrementUniqueNodes(); // unique nodes = 1
        queue.offer(path0); // add path containing [String,Matrix] pairs to queue.

        while (!queue.isEmpty()) {

            List<Pair<String, List<List<Integer>>>> peekPath = queue.poll(); // poll path from queue.
            Pair<String, List<List<Integer>>> lastStringMatrixPair = peekPath.get(peekPath.size() - 1); // get last element of this path
            List<List<Integer>> currentMatrix = lastStringMatrixPair.getValue1();
            tracker.incrementProcessedNodes();
            if (Helper.isFinished(currentMatrix)) {
                // construct the solution, path of letters.
                StringBuilder solution = new StringBuilder();
                for (var pair: peekPath) {
                  solution.append(pair.getValue0());
                }
                this.path = solution.toString();
                return true;
            }

            // create list of map entries [String,Matrix], each matrix is a next node on graph.
            List<Map.Entry<Character, List<List<Integer>>>> entries =
                    new ArrayList<>(Helper.getNext(lastStringMatrixPair.getValue1(), policy).entrySet());

            for (var entry : entries) {
                Character letter = entry.getKey(); // letter, one of: [l,r,u,d]
                List<List<Integer>> matrix = entry.getValue();
                if (!visited.contains(matrix)) {
                    tracker.incrementUniqueNodes();
                    List<Pair<String,List<List<Integer>>>> newPath = new ArrayList<>(peekPath);
                    newPath.add(new Pair<>(letter + "",matrix)); // create new [letter, matrix] pair and add at the end of current path.
                    queue.offer(newPath); // add new path at the end of queue that contains other paths.
                }
            }
        }

        return false;
    }

}
