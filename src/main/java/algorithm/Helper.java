package algorithm;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Class with static utility functions.
 */
public class Helper {

    /**
     * Check whether matrix is solved (the puzzle is solved).
     * @param matrix 2D list of Integers representing the puzzle.
     * @return true if puzzle is solved, false otherwise.
     */
    public static boolean isFinished(List<List<Integer>> matrix) {
        if (matrix == null) {
            return false;
        }
        int k = 0;
        for (List<Integer> integers : matrix) {
            for (int j = 0; j < matrix.get(0).size(); j++) {
                if (integers.get(j) != k) {
                    return false;
                }
                k++;
            }
        }
        return true;
    }


    /**
     * Generates next possible puzzles from provided puzzle.
     * Puzzled are ordered in accordance to policy. Example: "lurd" means left-up-right-down.
     * @param matrix source puzzle for which we want ot generate next possible puzzles.
     * @param policy defines the ordering of generated puzzles.
     * @return HashMap of character (one of 'l', 'r', 'u', 'd') and corresponding generated puzzle.
     */
    public static LinkedHashMap<Character, List<List<Integer>>> getNext(List<List<Integer>> matrix, String policy) {
        LinkedHashMap<Character, List<List<Integer>>> map = new LinkedHashMap<>(4);

        int zeroCoordinateX = 0;
        int zeroCoordinateY = 0;

        for (int k = 0; k < 4; k++) { // for each letter from policy of length 4

            List<List<Integer>> newMatrix = new ArrayList<>(); // create new empty puzzle
            int columns = matrix.get(0).size();
            for (int i = 0; i < matrix.size(); i++) {
                newMatrix.add(new ArrayList<>(columns));
                for (int n = 0; n < columns; n++) {
                    newMatrix.get(i).add(0);
                }
            }

            for (int i = 0; i < matrix.size(); i++) {
                for (int j = 0; j < matrix.get(0).size(); j++) {
                    Integer value = matrix.get(i).get(j);
                    newMatrix.get(i).set(j,value);
                    if (value == 0) {
                        zeroCoordinateX = j; // remember coordinate x of 0
                        zeroCoordinateY = i; // remember coordinate y of 0
                    }
                }
            }

            switch (policy.charAt(k)) {
                case 'r':
                    if (zeroCoordinateX + 1 < newMatrix.get(zeroCoordinateY).size()) {
                        Integer valueToSwap = newMatrix.get(zeroCoordinateY).get(zeroCoordinateX + 1);
                        newMatrix.get(zeroCoordinateY).set(zeroCoordinateX + 1, 0);
                        newMatrix.get(zeroCoordinateY).set(zeroCoordinateX, valueToSwap);
                    } else {
                        newMatrix = null;
                    }
                    break;
                case 'd':
                    if (zeroCoordinateY + 1 < newMatrix.size()) {
                        Integer valueToSwap = newMatrix.get(zeroCoordinateY + 1).get(zeroCoordinateX);
                        newMatrix.get(zeroCoordinateY + 1).set(zeroCoordinateX, 0);
                        newMatrix.get(zeroCoordinateY).set(zeroCoordinateX, valueToSwap);
                    } else {
                        newMatrix = null;
                    }
                    break;
                case 'u':
                    if (zeroCoordinateY - 1 >= 0) {
                        Integer valueToSwap = newMatrix.get(zeroCoordinateY - 1).get(zeroCoordinateX);
                        newMatrix.get(zeroCoordinateY - 1).set(zeroCoordinateX, 0);
                        newMatrix.get(zeroCoordinateY).set(zeroCoordinateX, valueToSwap);
                    } else {
                        newMatrix = null;
                    }
                    break;
                case 'l':
                    if (zeroCoordinateX - 1 >= 0) { // swap zero
                        Integer valueToSwap = newMatrix.get(zeroCoordinateY).get(zeroCoordinateX - 1);
                        newMatrix.get(zeroCoordinateY).set(zeroCoordinateX - 1, 0);
                        newMatrix.get(zeroCoordinateY).set(zeroCoordinateX, valueToSwap);
                    } else {
                        newMatrix = null;
                    }
                    break;
            }
            if (newMatrix != null) {
                map.put(policy.charAt(k),newMatrix);
            }
        }
        return map;
    }


}
