# Research-15-puzzle
## _University project: research of algorithms A*, BFS, DFS used to solve "15 puzzle"_

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Puzzle called "15 puzzle" can be solved using path finding algorithms such as BFS, DFS, A*.
In this project I have implemented all of them and comapred their efficiency in finding the solution to the "15 puzzle".
This research file (Research-15-puzzle.pdf) is located inside project's main directory.


## How it works

- main method in Executor class is executed
- it executes main method inside Program class with different args
- 420 unique puzzles are generated and saved inside directory "inputFiles" (format: PuzzleXYZ.txt where XYX is a number from 0 to 419)
- 18 types of experiments are executed (described in Research-15-puzzle.pdf)
- information about each experiment is stored inside directories: outputFiles and solution

Example contents of file ./outputFiles/ast/Puzzle10.txt:
   
    Solution length        : 3
    Unique nodes           : 10
    Processed nodes        : 4
    Recursion depth        : 0
    Execution time         : 0.162
    
Example contents of file ./solution/ast/Puzzle10.txt:

    Solution length: 3
    Solution       : uul

Text "uul" means that in order to solve the puzzle you need to move "empty piece" of the puzzle three times:
- Up
- Up
- Left

## License

MIT
**Free Software**
