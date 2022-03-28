package algorithm;

import elements.Tracker;

import java.util.List;

public interface Solver {

    /**
     * Solves the provided puzzle
     * @param matrix puzzle to solve.
     */
     void solve(List<List<Integer>> matrix);

    /**
     * Provides Tracker.
     * @return Tracker that gathers information about the particular solver.
     */
    Tracker getTracker();

}
