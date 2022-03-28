import algorithm.Helper;
import org.javatuples.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Generates files and saves inside "./inputFiles"
 */
public class Generator {

    // Solved puzzle.
    private static final List<List<Integer>> solvedPuzzle =
            new ArrayList<>(
                    List.of(Arrays.asList(0, 1, 2, 3),
                            Arrays.asList(4, 5, 6, 7),
                            Arrays.asList(8, 9, 10, 11),
                            Arrays.asList(12, 13, 14, 15)));


    /**
     * Generate puzzles that are created by making n moves on solved puzzle.
     * @param n number of moves to make.
     * @return List of created puzzles.
     */
    public static List<List<List<Integer>>> generatePuzzles(int n) {
        List<Pair<String, List<List<Integer>>>> endpoints = new ArrayList<>();
        generate(n,endpoints); // 1 - 6 depth
        List<List<List<Integer>>> tmp = new ArrayList<>(endpoints.size());
        for (int i = 0; i < endpoints.size(); i++) {
            tmp.add(i,endpoints.get(i).getValue1());
        }
        return tmp;
    }


    /**
     * Generates files for every puzzle.
     * Save files inside: "./inputFiles"
     * @throws IOException thrown when IO problem occurs.
     */
    public static void generateAllFiles() throws IOException {
        int i = 0;
        for (var n: List.of(1,2,3,4,5,6,7)) { // depths from 1 to 7
            for (var puzzle : generatePuzzles(n)) {
                generateOneFile("Puzzle" + i + ".txt", puzzle);
                System.out.println("generated file: " + "Puzzle" + i + ".txt");
                i++;
            }
        }

    }

    /**
     * Generate puzzles that are created by making n moves on solved puzzle.
     * @param n number of moves to make.
     */
    private static void generate(int n, List<Pair<String, List<List<Integer>>>> endpoints) {
        if (n == 0) {
            endpoints.add(new Pair<>("", solvedPuzzle));
            //puzzleList.add(new Pair<>(" ", base));
        } else {
            generate(n - 1,endpoints);

            List<Pair<String, List<List<Integer>>>> newEndpoints = new LinkedList<>();
            for (var puzzle : endpoints) {
                LinkedHashMap<Character, List<List<Integer>>> nextNodes = Helper.getNext(puzzle.getValue1(), "lrud");
                for (var entry : nextNodes.entrySet()) {
                    Character letter = entry.getKey();
                    String path = puzzle.getValue0();
                    if (path.length() != 0 && negateCharacter(letter) == path.charAt(path.length() - 1)){ // it means we are going back, BAD!
                        continue;
                    }
                    List<List<Integer>> nextPuzzle = entry.getValue();
                    String pathToNextPuzzle = puzzle.getValue0() + letter;
                    Pair<String, List<List<Integer>>> nextPair = new Pair<>(pathToNextPuzzle, nextPuzzle);
                    newEndpoints.add(nextPair);
                    //puzzleList.add(nextPair);
                }
            }
            endpoints.clear();
            endpoints.addAll(newEndpoints);// point to new endpoints

        }
    }

    /**
     * Negates the letter.
     * left - right (l - r)
     * up - down (u - d)
     * @param input Character to negate.
     * @return Negated letter.
     */
    private static Character negateCharacter(Character input) {
        return switch (input) {
          case 'l' -> 'r';
          case 'r' -> 'l';
          case 'u' -> 'd';
          case 'd' -> 'u';
            default -> ' ';
        };
    }

    /**
     * Generates a particular file for a puzzle.
     * @param filename name of file.
     * @param puzzle puzzle.
     * @throws IOException thrown when IO problem occurs.
     */
    private static void generateOneFile(String filename, List<List<Integer>> puzzle)  throws IOException {
        File report = new File("inputFiles" + File.separator + filename);
        FileWriter fwReport = new FileWriter(report); // append data to report file
        PrintWriter pw = new PrintWriter(fwReport);
        int rows = puzzle.size();
        int columns = puzzle.get(0).size();
        pw.println(rows + " " + columns);
        for (List<Integer> integers : puzzle) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < columns; j++) {
                if (j == (columns - 1)) {
                    builder.append(integers.get(j));
                } else {
                    builder.append(integers.get(j)).append(" ");
                }
            }
            pw.println(builder);
        }
        pw.close();
    }

}