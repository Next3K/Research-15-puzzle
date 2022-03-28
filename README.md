# Research-15-puzzle
University project: research of algorithms A*, BFS, DFS used to solve "15 puzzle"

Puzzle called "15 puzzle" can be solved using path finding algorithms such as BFS, DFS, A*.
In this project I have implemented all of them and comapred their efficiency in finding the solution to the "15 puzzle".
This research is located inside Research-15-puzzle.pdf


How this program works?

1) main method in Executor class is executed
2) it executes main method inside Program class with different args.
3) 420 unique puzzles are generated and saved inside directory "inputFiles" (format: PuzzleXYZ.txt where XYX is a number from 0 to 419)
4) 18 types of experiments are executed (described in Research-15-puzzle.pdf)
5) information about each experiment is stored inside: 
      ./outputFiles/{name_of_algorithm}/PuzzleXYZ.txt
      ./solution/{name_of_algorithm}/PuzzleXYZ.txt
   name_of_algorithm can be: [bfs, dfs, ast]
   
   Example contents of files mentioned above:
   
   ./outputFiles/ast/Puzzle10.txt
   
    Solution length        : 3
    Unique nodes           : 10
    Processed nodes        : 4
    Recursion depth        : 0
    Execution time         : 0.162
    
    ./solution/ast/Puzzle10.txt
    
    Solution length: 3
    Solution       : uul
   
6) Solution "uul" means that you can solve the puzzle moving empty space inside the puzzle 3 times: up-up-left
7) Overall report about each one of 18 experiments is appended to ./report.txt file
