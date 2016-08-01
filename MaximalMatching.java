package matching;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class to find a maximum cardinality matching in a given bipartite graph
 * G=(V,E)
 *
 * @author Thiagarajan ramakrishnan
 */
public class MaximalMatching {

    /**
     * Helper method to initialize Graph's Vertex to default values
     * @param u : Vertex u of the given graph
     */
    private static void initialize(Vertex u) {
        u.distance = Integer.MAX_VALUE;
        u.parent = null;
        u.seen = false;
        u.mate = null;
    }

    /**
     * This method is used to find maximum cardinality matching in a given
     * bipartite graph
     * @param g : Graph g
     * @return Exception - On Failure Maximum matching - on Success
     * @throws Exception if Graph is not Bipartite
     */
    public static int matching(Graph g) throws Exception {
        Queue<Vertex> Q = new LinkedList<>();
        int count = 0;
        for (Vertex u : g) {
            initialize(u);
        }
        
        // Run BFS on every component
        for (Vertex src : g) {
            if (!src.seen) {
                count++;
                src.distance = 0;
                Q.add(src);
                src.seen = true;
                
                while (!Q.isEmpty()) {
                    Vertex u = Q.remove();
                    for (Edge e : u.Adj) {
                        Vertex v = e.otherEnd(u);
                        if (!v.seen) {
                            v.seen = true;
                            v.parent = u;
                            v.distance = u.distance + 1;
                            // Clasify Nodes as outer and inner nodes
                            if (u.isOuter == true) {
                                v.isOuter = false;
                            }
                            Q.add(v);
                        } else if (u.distance == v.distance) {
                            throw new Exception("Graph is not bipartite");
                        }
                    }
                }
            }
        }

        Timer time = new Timer();
        time.start();
        int msize = 0;
        
        // Step 3: find maximal matching using greedy algorithm
        for (Vertex u : g) {
            for (Edge e : u.Adj) {
                Vertex v = e.otherEnd(u);
                if (v.mate == null && u.mate == null) {
                    u.mate = v;
                    v.mate = u;
                    msize++;
                }
            }
        }
        
        // Step 4: find augmenting path, increase size of matching
        int prevMsize = msize;
        while (true) {
            Queue<Vertex> queue = new LinkedList<>();
            for (Vertex u : g) {
                u.seen = false;
                u.parent = null;
                if (u.mate == null && u.isOuter) {
                    u.seen = true;
                    queue.add(u);
                }
            }
            search:
            while (!queue.isEmpty()) {
                Vertex u = queue.remove();
                for (Edge e : u.Adj) {
                    Vertex v = e.otherEnd(u);
                    if (!v.seen) {
                        v.parent = u;
                        v.seen = true;
                        if (v.mate == null) {
                            processAugPath(v);
                            ++msize;
                            break search;
                        } else {
                            Vertex x = v.mate;
                            x.parent = v;
                            x.seen = true;
                            queue.add(x);
                        }
                    }
                }
            }
            
            //if no augment path found and queue is Empty then break out of Loop
            if (prevMsize != msize && !queue.isEmpty()) {
                prevMsize = msize;
            } else {
                break;
            }
            
        }
        System.out.println(time.end());
        return msize;
    }

    /**
     * This method is a helper function to increase size of 
     * matching, using an augmenting path
     * @param u :Vertex u
     */
    public static void processAugPath(Vertex u) {
        Vertex p = u.parent;
        Vertex x = p.parent;
        u.mate = p;
        p.mate = u;
        while (x != null) {
            Vertex nmx = x.parent;
            Vertex y = nmx.parent;
            x.mate = nmx;
            nmx.mate = x;
            x = y;
        }
    }

}