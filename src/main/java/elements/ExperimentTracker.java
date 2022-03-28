package elements;

import java.util.LinkedList;
import java.util.List;

/**
 * Class that aggregates multiple trackers and counts their statistics.
 */
public class ExperimentTracker {
    private final String algorithm;
    private final String policy;
    private final List<Tracker> trackerList = new LinkedList<>();


    public ExperimentTracker(String algorithm, String policy) {
        this.algorithm = algorithm;
        this.policy = policy;
    }

    /**
     * Getter of algorithm name.
     * @return String name (one of: "dfs", "bfs", "astr")
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * Getter of policy.
     * @return the policy.
     */
    public String getPolicy() {
        return policy;
    }

    /**
     * Adds tracker to the list of trackers.
     * @param tracker Tracker
     */
    public void addTracker(Tracker tracker) {
        if (tracker != null) {
            this.trackerList.add(tracker);
        }
    }

    /**
     * Average number of processed nodes across all trackers.
     * @return double average
     */
    public double avgProcessed() {
        double sum = 0;
        for (var elem :this.trackerList) {
            sum += elem.getProcessed();
        }
        return sum / this.trackerList.size();
    }

    /**
     * Average number of visited nodes across all trackers.
     * @return double average
     */
    public double avgVisited() {
        double sum = 0;
        for (var elem : this.trackerList) {
            sum += elem.getVisited();
        }
        return sum / this.trackerList.size();
    }

    /**
     * Average execution time across all trackers.
     * @return double average
     */
    public double avgTime() {
        double sum = 0;
        for (var elem :this.trackerList) {
            sum += elem.getExecutionTime();
        }
        return sum / this.trackerList.size();
    }

    /**
     * Average solution length across all trackers.
     * @return double average
     */
    public double avgSolutionLength() {
        double sum = 0;
        for (var elem :this.trackerList) {
            sum += elem.getSolutionLength();
        }
        return sum / this.trackerList.size();
    }

    /**
     * Average max depth across all trackers.
     * @return double average
     */
    public double avgMaxDepth() {
        double sum = 0;
        for (var elem :this.trackerList) {
            sum += elem.getMaxRecursionDepth();
        }
        return sum / this.trackerList.size();
    }




}
