package read;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class with static helper function allowing to parse the puzzle from the file.
 */
public class InputReader {
    /**
     * Parses the puzzle from the txt file.
     * @param filename name of the file to parse.
     * @return 2D List of Integers representing the puzzle.
     * @throws FileNotFoundException thrown when file not found.
     */
    public static List<List<Integer>> read(String filename) throws FileNotFoundException {
        List<List<Integer>> matrix = null;
        File input = new File("inputFiles" + File.separator + filename);
        Scanner scanner = new Scanner(input);
        for (int i = 0;;i++) {
            if (!scanner.hasNextLine()) break;
            String data = scanner.nextLine();
            String[] tokens = data.split("\\s+");
            if (i == 0) {
                int y = Integer.parseInt(tokens[0]); // parse number of rows
                int x = Integer.parseInt(tokens[1]); // parse number of columns
                matrix = new ArrayList<>(); // prepare 2D ArrayList
                for (int k = 0; k < y; k++) {
                    ArrayList<Integer> toAdd = new ArrayList<>();
                    for (int z = 0; z < x; z++) {
                        toAdd.add(0);
                    }
                    matrix.add(toAdd);
                }
            } else {
                for (int j = 0; j < tokens.length; j++) { // insert values
                    int value = Integer.parseInt(tokens[j]);
                    matrix.get(i - 1).set(j,value);
                }
            }
        }
        return matrix;
    }
}
