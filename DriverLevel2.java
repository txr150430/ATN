package ShortestPath;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Thiagarajan Ramakrishnan
 */
public class DriverLevel2 {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        if (args.length > 0) {
            File inputFile = new File(args[0]);
            in = new Scanner(inputFile);
        } else {
            in = new Scanner(System.in);
        }
        Graph g = Graph.readGraph(in, true);
        
        //Logic to calculate if weight of all edge equal
        
        //Logic to find 
        int totalSum =0;
        int n = 0;
        n = Shortestpath.CheckBFS(g);
        if(n == 0){
        	n = Shortestpath.CheckDAG(g);
        }
        if(n == 0){
        	n = Shortestpath.checkDijkstra(g);
        }
        switch (n) {
            case 1:
                Shortestpath.BFSShortestPath(g);
                for (Vertex u : g) {
                        totalSum += u.totalpath;
                    
                }
                System.out.println(totalSum);
                Shortestpath.printPathlevel2(g);
                break;
            case 2:
                Shortestpath.DAGShortestPath(g);
                for (Vertex u : g) {
                    
                        totalSum += u.totalpath;
                    
                }
                System.out.println(totalSum);
                Shortestpath.printPathlevel2(g);
                break;
            case 3:
                Shortestpath.DijkstraShortestPath(g);
                for (Vertex u : g) {
                    
                        totalSum += u.totalpath;
                    
                }
                System.out.println(totalSum);
                Shortestpath.printPathlevel2(g);
                break;
            default:
                Shortestpath.BellmanFord2(g);
                for (Vertex u : g) {
                    
                        totalSum += u.totalpath;
                    
                }
                if(!g.cycle){
                System.out.println(totalSum);
                Shortestpath.printPathlevel2(g);
            	}
                break;
        }
    }
}