package ShortestPath;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;



/**
 *
 * @author Thiagarajan ramakrishnan
 *
 */
public class Shortestpath {

    public static void intialize(Vertex u) {
        u.distance = Integer.MAX_VALUE;
        u.infinity = true;
        u.parent = null;
        u.seen = false;
        u.status = 0;
        u.count = 0;
    }

    public static ArrayList<Vertex> topologicalOrder1(Graph g) {
        ArrayList<Vertex> order = new ArrayList<>();
        Queue<Vertex> Q = new LinkedList<>();
        //Set all the vertices indegree
        for (Vertex u : g) {
            for (Edge v : u.revAdj) {
                u.indegree++;
            }
        }
        for (Vertex u : g) {
            u.seen = false;
            u.parent = null;
            u.distance = Integer.MAX_VALUE;
        }

        for (Vertex u : g) {
            if (u.indegree == 0) {
                Q.add(u);
            }
        }

        //Add the vertices with no incoming edges to queue until it gets empty
        int top = 1;
        while (!Q.isEmpty()) {
            Vertex u = Q.remove();
            u.top = top++;
            order.add(u);
            for (Edge e : u.Adj) {
                Vertex v = e.otherEnd(u);
                v.indegree--;
                if (v.indegree == 0) {
                    Q.add(v);
                }
            }
        }
        return order;
    }

    public static void DAGShortestPath(Graph g) {
        ArrayList<Vertex> result = new ArrayList<>();
        ArrayList<Vertex> copyresult = new ArrayList<>();
        for(Vertex v: g){
            intialize(v);
        }
        result = topologicalOrder1(g);
        copyresult = (ArrayList<Vertex>)result.clone();
        int totDistance = 0;
        Vertex src = g.verts.get(1);
        src.distance = 0;
        src.infinity = false;
        while(!result.isEmpty()) {
            Vertex u = result.remove(0);
            if(!u.infinity){
            	for (Edge e : u.Adj) {
                    Vertex v = e.otherEnd(u);
                    if (v.distance >= (u.distance + e.weight)) {
                    	if(v.infinity == false && v.distance > (u.distance + e.weight)){
                    		Edge edg = findEdge(v.parent,v);
                    		edg.mark = false;
                    	}
                        v.distance = u.distance + e.weight;
                        v.parent = u;
                        v.infinity = false;
                        e.mark = true;
                    }
                }
            }
            

        }
        calculateDistance(g, copyresult);
    }
    public static Edge findEdge(Vertex u, Vertex v){
    	for(Edge e : u.Adj){
    		Vertex vert = e.otherEnd(u);
    		if(vert.name == v.name)
    			return e;
    	}
    	return null;
    }
    public static void calculateDistance(Graph g, ArrayList<Vertex> result){
    	
    	int i=0;
    	int tot_dist = 0;
    	int size = result.size();
    	Vertex src = g.verts.get(1);
    	src.totalpath = 1;
    	for(Vertex u : result){
    		for(Edge e : u.Adj){
    			if(e.mark){
    			Vertex v = e.otherEnd(u);
    			v.totalpath += u.totalpath;
    		}
    		}
    	}
    }
    
    public static ArrayList<Vertex> modifiedtopologicalorder(Graph g) {
        ArrayList<Vertex> order = new ArrayList<>();
        Queue<Vertex> Q = new LinkedList<>();
        //Set all the vertices indegree
        for (Vertex u : g) {
            for (Edge v : u.revAdj) {
            	if(v.mark)
                u.indegree++;
            }
        }
        for (Vertex u : g) {
            u.seen = false;
        }

        for (Vertex u : g) {
            if (u.indegree == 0) {
                Q.add(u);
            }
        }

        //Add the vertices with no incoming edges to queue until it gets empty
        int top = 1;
        while (!Q.isEmpty()) {
            Vertex u = Q.remove();
            u.top = top++;
            order.add(u);
            for (Edge e : u.Adj) {
            	if(e.mark){
                Vertex v = e.otherEnd(u);
                v.indegree--;
                if (v.indegree == 0) {
                    Q.add(v);
                }
            	}
            }
        }
        return order;
    }

    
    public static void DijkstraShortestPath(Graph g) {
    	ArrayList<Vertex> order = new ArrayList<Vertex>();
        Vertex[] vertsArr = new Vertex[g.numNodes + 1];
        vertsArr[0] = null; // to avoid edge case condition
        int idx = 1;
        for (Vertex u : g) {

            intialize(u);
            u.index = idx;
            vertsArr[idx++] = u;

        }
        int totalSum = 0;

        // Source Vertex
        Vertex src = g.verts.get(1);
        src.distance = 0;
        src.infinity = false;

        PQ<Vertex> Q = new IndexedHeap<>((vertsArr), new Vertex(0));

        while (!Q.isEmpty()) {
            Vertex u = Q.remove();
            u.seen = true;
            for (Edge e : u.Adj) {
                Vertex v = e.otherEnd(u);
                if (v.distance >= (u.distance + e.weight) && !v.seen) {
                	if(v.infinity == false && v.distance > (u.distance + e.weight)){
                		Edge edg = findEdge(v.parent,v);
                		edg.mark = false;
                	}
                    v.infinity = false;
                    v.distance = u.distance + e.weight;
                    v.parent = u;
                    e.mark = true;
                    Q.decreaseKey(v);
                }
            }
        }
        order = modifiedtopologicalorder(g);
        calculateDistance(g, order);
    }
    
    //Bellman ford for Level 1
    public static void BellmanFord(Graph g) {

        boolean flag = BellmanFordUtil1(g);
        if (!flag) {
        	g.cycle = true;
            System.out.println("Unable to solve problem. Graph has a non positive cycle");
            return ;
        }
    }
    
    
    // Bellman Ford util for level 1
    public static boolean BellmanFordUtil1(Graph g) {
        Queue<Vertex> q = new LinkedList<>();

        for (Vertex u : g) {
           intialize(u);
        }
        Vertex src = g.verts.get(1);
        src.distance = 0;
        src.seen = true;
        src.infinity = false;
        q.add(src);

        while (!q.isEmpty()) {
            Vertex u = q.remove();
            u.seen = false;
            u.count += 1;
            if (u.count >= g.numNodes) {
                return false;
            }
            for (Edge e : u.Adj) {
                Vertex v = e.otherEnd(u);
                if (v.distance > (u.distance + e.weight)) {
                    v.distance = u.distance + e.weight;
                    v.parent = u;
                    if (!v.seen) {
                        q.add(v);
                        v.seen = true;
                        v.infinity = false;
                    }
                }
            }
        }
        return true;
    }
    
  //Bellman ford for Level 2
    public static void BellmanFord2(Graph g) {
    	ArrayList<Vertex> order = new ArrayList<Vertex>();
    	Stack<Vertex> cycle = new Stack<Vertex>();
        boolean flag = BellmanFordUtil(g);
        if (!flag) {
        	g.cycle = true;
            System.out.println("Unable to solve problem. Graph has a non positive cycle");
            Vertex src = g.verts.get(1);
            for(Vertex v :g){
            	if(v.count >= g.numNodes)
            		src = v;
            }
            for (Vertex u : g) {
                intialize(u);
            }
            cycle.push(src);
            printCycle(src,cycle);
            return;
        }
        order = modifiedtopologicalorder(g);
        calculateDistance(g, order);
    }
    
    public static void printCycle(Vertex u, Stack<Vertex> s){
		u.seen = true;
		u.status = 1;
		for(Edge e: u.Adj){
			if(e.mark){
			Vertex v = e.otherEnd(u);
			s.push(v);
			if(v.status == 1){
				printCycleUtil(s);
				break;
			}
			if(!v.seen){
				printCycle(v, s);
				}
			}
		}
		u.status = 2;
		s.pop();
	}
    
    public static void printCycleUtil(Stack<Vertex> s){
    	String str = "";
    	for(Vertex v :s){
    		
    		str = str + v+ "->";
    	}
    	str = str.substring(0, str.length()-2);
    	System.out.println(str);
    }
    
  //Bellman ford util for Level 2
    public static boolean BellmanFordUtil(Graph g) {
        Queue<Vertex> q = new LinkedList<>();

        for (Vertex u : g) {
            intialize(u);
        }
        Vertex src = g.verts.get(1);
        src.distance = 0;
        src.seen = true;
        src.infinity = false;
        q.add(src);

        while (!q.isEmpty()) {
            Vertex u = q.remove();
            u.seen = false;
            u.count += 1;
            if (u.count >= g.numNodes) {
                return false;
            }
            for (Edge e : u.Adj) {
                Vertex v = e.otherEnd(u);
                if (v.distance >= (u.distance + e.weight)) {
                	if(v.infinity == false && v.distance > (u.distance + e.weight)){
                		if(v.parent == null)
                			v.parent = u;
                		Edge edg = findEdge(v.parent,v);
                		edg.mark = false;
                	}
                    v.distance = u.distance + e.weight;
                    v.parent = u;
                    e.mark = true;
                    if (!v.seen) {
                        q.add(v);
                        v.seen = true;
                        v.infinity = false;
                    }
                }
            }
        }
        return true;
    }

    // BFS Shortest path
    public static void BFSShortestPath(Graph g) {
        Queue<Vertex> queue  = new LinkedList<>();
        ArrayList<Vertex> order = new ArrayList<Vertex>();
        for (Vertex u : g) {
            u.distance = Integer.MAX_VALUE;
            u.parent = null;
            u.seen = false;
            u.infinity = true;
        }
        
        Vertex src = g.verts.get(1);
        src.distance = 0;
        src.seen = true;
        src.infinity = false;
        queue.add(src);
        
        int totalSum = 0;
        while(!queue.isEmpty()){
            Vertex u = queue.remove();
            u.seen = true;
            for(Edge e:u.Adj){
                Vertex v = e.otherEnd(u);
                if(!v.seen && v.distance >= (u.distance + e.weight)){
                    v.infinity = false;
                    v.parent = u;
                    v.distance = u.distance + e.weight;
                    e.mark = true;
                    queue.add(v);
                }
            }
        }
        order = modifiedtopologicalorder(g);
        calculateDistance(g, order);
    }

    public static void printPath(Graph g) {
        if (g.numNodes > 100) {
            return;
        }
        for (Vertex v : g) {
            System.out.println(v.name + " "
                    + ((v.infinity) ? "INF" : v.distance) + " "
                    + ((v.parent != null) ? v.parent : "-"));
        }
    }
    
    //Print path level 2
    public static void printPathlevel2(Graph g) {
        if (g.numNodes > 100) {
            return;
        }
        for (Vertex v : g) {
            System.out.println(v.name + " "
                    + ((v.infinity) ? "INF" : v.distance) + " "
                    + v.totalpath);
        }
    }
    
    
  //Checks for BFS
  	public static int CheckBFS(Graph g){
  		int n=0;
  		Vertex v = g.verts.get(1);
  		Edge edge = v.Adj.get(0);
  		//Check for negative edges
  		if(edge.weight < 0)
  			return n;
  		Boolean flag = true;
  		for(Vertex u: g){
  			for(Edge e : u.Adj){
  				if(edge.weight != e.weight)
  					flag = false;
  			}
  		}
  		if(flag == true){
  			n=1;
  		}
  		return n;
  	}
  	
    
  //Checks for DAG
  	public static int CheckDAG(Graph g){
  		int n=0;
  		Boolean cycle = false;
  		Boolean isacyclic = true;
  		for(Vertex u: g){
  			if(!u.seen){
  				isacyclic = DFS(u);
  				if(!isacyclic)
  					cycle =true;
  			}
  		}
  		if(!cycle)
  			n=2;
  		return n;
  	}
  	
  	//Utility function for DFS used to check for cycles
  	
  	public static Boolean DFS(Vertex u){
  		u.seen = true;
  		u.status = 1;
  		for(Edge e:u.Adj){
  			Vertex v = e.otherEnd(u);
  			if(v.status == 1)
  				return false;
  			if(!v.seen){
  				v.parent = u;
  				if(DFS(v) == false)
  					return false;
  			}
  		}
  		u.status = 2;
  		return true;
  	}
  	
  	//If graph has only non negative weights
  	public static int checkDijkstra(Graph g){
  		int n=0;
  		for(Vertex u: g){
  			for(Edge e:u.Adj){
  				if(e.weight < 0)
  					return n;	
  			}
  		}
  		n=3;
  		return n;
  	}
    
}