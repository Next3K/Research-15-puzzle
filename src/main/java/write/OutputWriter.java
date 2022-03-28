package write;

import elements.ExperimentTracker;
import elements.Tracker;
import java.time.LocalDateTime;

import java.io.*;

/**
 * Class writing out data to fies.
 */
public class OutputWriter {

    /**
     * Writes tracker data to txt files.
     * @param filename name of the file.
     * @param tracker Tracker.
     * @throws IOException thrown when IO problem occurs.
     */
    public static void write(String filename, Tracker tracker)  throws IOException {
        File fileReport = new File(
                "outputFiles" + File.separator + tracker.getAlgorithmName() + File.separator +  filename);
        File  fileSolution = new File(
                "solution" + File.separator + tracker.getAlgorithmName() + File.separator +  filename);
        FileWriter fwReport = new FileWriter(fileReport);
        FileWriter fwSolution = new FileWriter(fileSolution);
        PrintWriter pw = new PrintWriter(fwReport);
        PrintWriter pwSolution = new PrintWriter(fwSolution);
        pw.println("Solution length        : " + tracker.getSolutionLength());
        pw.println("Unique nodes           : " + tracker.getVisited());
        pw.println("Processed nodes        : " + tracker.getProcessed());
        pw.println("Recursion depth        : " + tracker.getMaxRecursionDepth());
        pw.println("Execution time         : " + tracker.getExecutionTime());
        pw.close();
        if (tracker.isFoundSolution()) {
            pwSolution.println("Solution length: " + tracker.getSolutionLength());
            pwSolution.println("Solution       : " + tracker.getSolution());
        } else {
            pwSolution.println("-1");
        }
        pwSolution.close();

    }

    /**
     * Writes ExperimentTracker data to txt file.
     * @param tracker ExperimentTracker that holds necessary data.
     * @throws IOException  thrown when IO problem occurs.
     */
    public static void reportFinal(ExperimentTracker tracker)  throws IOException {
        File report = new File("report" );
        FileWriter fwReport = new FileWriter(report,true); // append data to report file
        PrintWriter pw = new PrintWriter(fwReport);
        pw.println("CURRENT TIME: " + LocalDateTime.now());
        pw.println();
        pw.println("Algorithm " + tracker.getAlgorithm() + ", policy: " + tracker.getPolicy());
        pw.println("Avg time (milliseconds): " + tracker.avgTime());
        pw.println("Avg Solution length    : " + tracker.avgSolutionLength());
        pw.println("Avg unique nodes       : " + tracker.avgVisited());
        pw.println("Avg Processed nodes    : " + tracker.avgProcessed());
        pw.println("Avg traversal depth    : " + tracker.avgMaxDepth());
        pw.println();
        pw.close();

    }

    /**
     * Clears the contents of the "report" file.
     * @throws IOException thrown when IO problem occurs.
     */
    public static void clearReportFile() throws IOException {
        File report = new File("report");
        FileWriter fwReport = new FileWriter(report,false);
        PrintWriter pw = new PrintWriter(fwReport);
        pw.println("");
        pw.close();
    }



}
