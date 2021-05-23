package dxp190051;
/** Breadth-first search
 /**
 * BFSOddCycle implements a BFS using queue and stack to find the odd length cycle in the graph
 * It consists of following methods: oddCycle() , printOddCycle()
 * @author Andres Daniel Uriegas(adu170030)
 * @author Dhara Patel(dxp190051)
 * */

import dxp190051.Graph.Edge;
import dxp190051.Graph.Vertex;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BFSOddCycle{
    List<Vertex> resCycle = new ArrayList<>();

    /**
     * Method: oddCycle()
     * Finds odd length cycle in the graph and returns it
     * If no odd length cycle found then returns null
     * @param g: Graph
     * @return: List containing cycle
     * */
    public List<Vertex> oddCycle(Graph g) {
        BFSOO bfs = null;
        for (Vertex p : g) {
            //Traverse the unvisited vertices of the graph
            if (bfs == null || !bfs.getSeen(p)) {
                bfs = BFSOO.breadthFirstSearch(g, p);
                for (Vertex u : g) {
                    //Traverse the visited vertices of the graph
                    if (bfs.getSeen(u)) {
                        for (Edge e : g.outEdges(u)) {
                            Vertex v = e.otherEnd(u);
                            if (bfs.getDistance(u) == bfs.getDistance(v)) {
                                //List for parent of vertice u and their parent nodes
                                List<Vertex> cycleU  = new ArrayList<Vertex>();

                                //List for parent of vertice v and their parent nodes
                                List<Vertex> cycleV  = new ArrayList<>();

                                while (u != v) {
                                    cycleU.add(0, u);
                                    cycleV.add(v);
                                    u = bfs.getParent(u);
                                    v = bfs.getParent(v);
                                }
                                cycleU.add(0, u);
                                cycleV.add(v);

                                //Merge cycleU and cycleV to cycle
                                resCycle.addAll(cycleU);
                                resCycle.addAll(cycleV);

                                return resCycle;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Method: printOddCycle()
     * Prints the odd cycle
     * @param oddCycleList: List for odd cycle
     * @return
     * */
    public void printOddCycle(List<Vertex> oddCycleList){
        if(oddCycleList != null) {
            System.out.println("Odd-length Cycle: " + oddCycleList);
        }
        else{
            System.out.println("The graph is bipartite and no odd cycle exists.");
        }
    }

    public static void main(String[] args) throws Exception{
        String str = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 -7   6 7 -1   7 6 -1 1";
        str = "9 9  1 2 2  2 3 2  2 8 4  2 4 1  3 6 1  5 7 1  5 8 2  6 9 1  9 7 1 1";
        Scanner in;
        // If there is a command line argument, use it else use input from above string: str.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(str);

        // Read undirected graph from input
        Graph g = Graph.readGraph(in, false);
        g.printGraph(false);

        BFSOddCycle bfsCycle = new BFSOddCycle();
        List<Vertex> cycle = bfsCycle.oddCycle(g);

        // Prints odd cycle
        bfsCycle.printOddCycle(cycle);
    }
}

