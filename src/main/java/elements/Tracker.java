package elements;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Class that tracks progress throughout solving the problem.
 */
public class Tracker {
    private int visited;
    private int processed;
    private int recursionDepth;
    private long startTime;
    private long endTime;
    private boolean isActive;
    private boolean foundSolution;
    private int solutionLength = -1; // default solution is -1 which means failure
    private String solution = "-1"; // default solution is -1 which means failure
    private final String algorithmName;

    /**
     * Getter of field algorithmName.
     * @return name of algorithm (one of: "bfs","dfs","astr").
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    public Tracker(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    /**
     * Stops the timer if tracker is still active.
     * Sets Tracker state to inactive.
     */
    public void stop() {
        if (isActive) {
            this.endTime = System.nanoTime();
            isActive = false;
        }
    }

    /**
     * Starts the timer if tracker is still inactive.
     */
    public void start() {
        if (!isActive) {
            this.startTime = System.nanoTime();
            isActive = true;
        }
    }

    /**
     * Calculates and returns algorithm execution time.
     * Three decimal places.
     * @return algorithm execution time in milliseconds.
     */
    public float getExecutionTime() {
        if (!isActive) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
            DecimalFormat decimalFormat = new DecimalFormat("0.000",symbols);
            float value = (this.endTime - this.startTime) / ((float) 1_000_000);
            return Float.parseFloat(decimalFormat.format(value));
        } else {
            return -1;
        }
    }

    /**
     * Getter of field solutionLength.
     * @return int length of solution.
     */
    public int getSolutionLength() {
        return this.solutionLength;
    }

    /**
     * increment number of unique puzzles generated during  algorithm execution.
     */
    public void incrementUniqueNodes() {
        visited++;
    }

    /**
     * increment number of processed puzzles during  algorithm execution.
     */
    public void incrementProcessedNodes() {
        processed++;
    }

    /**
     * Setter of "solution" class member.
     * @param solution The solution.
     */
    public void setSolution(String solution) {
        this.solution = solution;
        this.solutionLength = solution.length();
        this.foundSolution = true;
    }

    /**
     * Setts depth of recursion if provided depth is bigger than the one known before.
     * @param depth depth of recursion.
     */
    public void updateMaxDepth(int depth) {
        if (this.recursionDepth < depth) {
            this.recursionDepth = depth;
        }
    }

    /**
     * Getter of "visited" class member.
     */
    public int getVisited() {
        return visited;
    }

    /**
     * Getter of "processed" class member.
     */
    public int getProcessed() {
        return processed;
    }

    /**
     * Getter of "maxRecursionDepth" class member.
     */
    public int getMaxRecursionDepth() {
        return recursionDepth;
    }

    /**
     * Setter of "solution" class member.
     */
    public String getSolution() {
        return solution;
    }

    /**
     * Provides information whether solution is found.
     * @return true if valid solution ws found, false otherwise.
     */
    public boolean isFoundSolution() {
        return foundSolution;
    }

}
