
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that invokes Program.main() method with multiple different args[].
 */
public class Executor {

    public static void main(String[] args){

        try {
            Generator.generateAllFiles(); // generate about 420 files
        } catch (IOException e) {
            System.exit(-1);
        }

        List<String> algorithms = List.of("astr", "bfs", "dfs");
        List<String> policies = List.of("rdul","rdlu","drul","drlu","ludr","lurd","uldr","ulrd");
        List<String> heuristics = List.of("hamm", "manh");
        File[] files = new File("inputFiles").listFiles();

        List<String> filesList = new ArrayList<String>(420);
        assert files != null;
        for (File file : files) {
            if (file != null && file.isFile()) {
                filesList.add(file.getName());
            }
        }

        String[] tokens = new String[filesList.size() + 2];
        for (int i = 0; i < filesList.size(); i++) {
            tokens[i + 2] = filesList.get(i);
        }

        for (String algorithm : algorithms) {
            if (!algorithm.equals("astr")) {
                for (String policy : policies) {
                    System.out.println();
                    tokens[0] = algorithm;
                    tokens[1] = policy;
                    Program.main(tokens);
                }
            } else {
                for (String heuristic: heuristics) {
                    System.out.println();
                    tokens[0] = algorithm;
                    tokens[1] = heuristic;
                    Program.main(tokens);
                }
            }

        }


    }
}
