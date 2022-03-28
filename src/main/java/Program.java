import algorithm.AstrSolver;
import algorithm.BfsSolver;
import algorithm.DfsSolver;
import algorithm.Solver;
import elements.ExperimentTracker;
import elements.Tracker;
import read.InputReader;
import write.OutputWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Main program class.
 * Reads provided args and executes corresponding experiment:
 *  arg[0] : name of algorithm (one of: "bfs", "dfs", "astr")
 *  arg[1] : name of algorithm policy ("hamm" or "manh" or combination of letters: 'l' 'u' 'r' 'd')
 *  args[2] - args[n] : (n - 2) names of files, each containing one puzzle.
 */
public class Program {
    public static void main(String[] args){

        //Read args.
        if (args.length < 2) System.exit(1);
        final String algorithm  = args[0].toLowerCase(); // choose algorithm
        final String policy  = args[1].toLowerCase(); // choose policy
        final String[] files = new String[args.length - 2];
        System.arraycopy(args,2,files,0,args.length - 2); // get list of files
        ExperimentTracker experimentTracker = new ExperimentTracker(algorithm,policy); // create experiment tracker

        // uncomment if you want to have "./report" file cleared
//        try {
//            OutputWriter.clearReportFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        for (String filename : files) {
            try {
                List<List<Integer>> inputMatrix = InputReader.read(filename); // parse the puzzle

                Solver solver = switch (algorithm) {
                    case "astr" -> new AstrSolver((policy.equals("hamm")) ? AstrSolver.Type.HAMMING : AstrSolver.Type.MANHATTAN);
                    case "bfs" -> new BfsSolver(policy);
                    case "dfs" -> new DfsSolver(policy);
                    default -> new DfsSolver("lurd");
                };

                solver.solve(inputMatrix); // solve the puzzle
                Tracker tracker = solver.getTracker();
                experimentTracker.addTracker(tracker); // add this tracker to ExperimentTracker
                try {
                    OutputWriter.write(filename,tracker); // write out report
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("(" + tracker.getAlgorithmName() + ")" + filename + ": " + tracker.getSolution() + " " + tracker.getExecutionTime() + " mili");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        try {
            OutputWriter.reportFinal(experimentTracker); // write out final report to "./report" file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
