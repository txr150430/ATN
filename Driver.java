package matching;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException, Exception {
        Scanner in;
    boolean VERBOSE = false;

        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else { 
            in = new Scanner(System.in);
        }
    if (args.length > 1) {
        VERBOSE = true;
    }
        Graph g = Graph.readGraph(in, false);   // read undirected graph from stream "in"
    // Create your own class and call the function to find a maximum matching.
    int result = MaximalMatching.matching(g);
    if (result != 0) {
        System.out.println(result);
    }
    // if (VERBOSE) {
    //     Output the edges of M.
    // }
    }
}