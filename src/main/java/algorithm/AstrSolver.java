package algorithm;

import elements.Tracker;
import org.javatuples.Pair;

import java.util.*;

/**
 * A* search algorithm implementation.
 */
public class AstrSolver implements Solver {
    private final Type heuristic;
    private final Tracker tracker = new Tracker("ast");
    private String path;


    /**
     * Enumeration representing two types of possible heuristic:
     * 1) Manhattan
     * 2) Hamming
     */
    public enum Type {
        MANHATTAN,
        HAMMING;
    }
    public AstrSolver(Type heuristic) {
        this.heuristic = heuristic;
    }

    /**
     * Reconstructs a path from start puzzle to end puzzle.
     * @param cameFromPLusLetter Map, key: a puzzle, Value: a letter that describes its predecessor (one of: l r u d).
     * @param current End puzzle, the solved one.
     * @param cameFrom Map, key: puzzle, Value: its predecessor puzzle.
     * @return String representing a path form start puzzle to end puzzle (the solution), example: "lrrul"
     */
    private String reconstructPath( Map<List<List<Integer>>, Character> cameFromPLusLetter,
                                    List<List<Integer>> current,
                                    Map<List<List<Integer>>, List<List<Integer>>> cameFrom) {
        StringBuilder path = new StringBuilder();
        Character lastChar = cameFromPLusLetter.get(current);
        String lastStep = (lastChar == null) ? "" : lastChar.toString();
        path.insert(0,lastStep);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            Character character = cameFromPLusLetter.get(current); // find out what letter corresponds to this relation
            String predecessor = (character == null) ? "" : character.toString();
            path.insert(0, predecessor);
        }
        return path.toString();
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
     * Solves provided puzzle.
     * @param matrix puzzle to solve.
     */
    @Override
    public void solve(List<List<Integer>> matrix) {
        tracker.start();
        Map<List<List<Integer>>, List<List<Integer>>> cameFrom = new HashMap<>();
        Map<List<List<Integer>>, Character> cameFromPlusLetter = new HashMap<>();
        Map<List<List<Integer>>, Integer> gScore = new HashMap<>(); // map of gScores
        Map<List<List<Integer>>, Integer> fScore = new HashMap<>();
        Queue<List<List<Integer>>>  openSet = // queue of matrices
                new PriorityQueue<>(Comparator.comparingInt(fScore::get)); // order by matrix gScore
        gScore.put(matrix, 0);
        tracker.incrementUniqueNodes(); // first node is noticed but not processed yet
        openSet.add(matrix);
        cameFrom.put(matrix,null);
        fScore.put(matrix,endHeuristic(matrix));

        while (!openSet.isEmpty()) {
            List<List<Integer>> current = openSet.poll();
            tracker.incrementProcessedNodes();
            if (Helper.isFinished(current)) {
                this.path = reconstructPath(cameFromPlusLetter,current,cameFrom);
                tracker.stop();
                tracker.setSolution(this.path);
                return;
            }
            LinkedHashMap<Character, List<List<Integer>>> entries = Helper.getNext(current, "drul");
            for (var entry : entries.entrySet()) {
                List<List<Integer>> neighbour = entry.getValue();
                if (!gScore.containsKey(neighbour)) {
                    gScore.put(neighbour,Integer.MAX_VALUE);
                    tracker.incrementUniqueNodes();
                }
                Character letter = entry.getKey();
                int tentative_score = gScore.get(current) + 2; // heuristic(current,neighbour) = 2
                if (tentative_score < gScore.get(neighbour)) {
                    cameFromPlusLetter.put(neighbour,letter);
                    cameFrom.put(neighbour,current);
                    gScore.put(neighbour,tentative_score);
                    fScore.put(neighbour,tentative_score + endHeuristic(neighbour));
                    if (!openSet.contains(neighbour)) {
                        openSet.add(neighbour);
                    }
                }
            }
        }
        tracker.stop();
    }

    /**
     * Calculates the heuristic between two provided puzzles.
     * @param a puzzle one.
     * @param b puzzle two.
     * @return int value of heuristic (minimal value: 0)
     */
    private int heuristic(List<List<Integer>> a, List<List<Integer>> b) {
        if (this.heuristic == Type.HAMMING) {
            int hummingDist = 0;
            for (int y = 0; y < a.size(); y++) {
                for (int x = 0; x < a.get(0).size(); x++) {
                    if (a.get(y).get(x).intValue() != b.get(y).get(x).intValue()) {
                        hummingDist++;
                    }
                }
            }
            return hummingDist;
        } else {
            int manhattan = 0;

            Map<Integer, Pair<Integer, Integer>> coordsA = getCoordinateMapping(a);
            Map<Integer, Pair<Integer, Integer>> coordsB = getCoordinateMapping(b);

            for (int i = 0; i < a.size() * a.get(0).size(); i++) {
                int xDiff = Math.abs(coordsA.get(i).getValue1() - coordsB.get(i).getValue1());
                int yDiff = Math.abs(coordsA.get(i).getValue0() - coordsB.get(i).getValue0());
                manhattan += (xDiff + yDiff);
            }
            return manhattan;
        }

    }

    /**
     * Calculates the heuristic between provided puzzle and solved puzzle.
     * @param matrix puzzle.
     * @return int value of heuristic (minimal value: 0)
     */
    private int endHeuristic(List<List<Integer>> matrix) {
        if (this.heuristic == Type.HAMMING) {
            int k = 0;
            int hummingDist = 0;
            for (var row : matrix) {
                for (var col : row) {
                    if (col != k) {
                        hummingDist++;
                    }
                    k++;
                }
            }
            return hummingDist;
        } else {
            int manhattan = 0;
            int cols = matrix.get(0).size();
            for (int y = 0; y < matrix.size(); y++) {
                for (int x = 0; x < matrix.get(0).size(); x++) {
                    int value = matrix.get(y).get(x);
                    int xSupposed = value % cols;
                    int ySupposed = value / cols;
                    int xDiff = Math.abs(xSupposed - x);
                    int yDiff = Math.abs(ySupposed - y);
                    manhattan += (xDiff + yDiff);
                }
            }
            return manhattan;
        }

    }

    /**
     * Maps all values that are in the puzzle (0-15) to the pair of coordinates.
     * @param a puzzle to map.
     * @return Map, Key: Integer value (0-15), Value: a pair of coordinates (x,y)
     */
    private Map<Integer, Pair<Integer,Integer>> getCoordinateMapping(List<List<Integer>> a) {
        Map<Integer, Pair<Integer, Integer>> coordsMapping = new HashMap<>();

        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < a.get(0).size(); j++) {
                Integer value = a.get(i).get(j);
                coordsMapping.put(value, new Pair<>(i,j));
            }
        }
        return coordsMapping;
    }

    /**
     * Getter of path.
     * @return String representing the solution/path of letters from start to finish.
     */
    public String getPath() {
        return path;
    }
}
