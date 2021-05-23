/**
 * Starter code for SP5
 * The class DFS implements the Topological sorting for Directed Acyclic Graph.
 * @author Andres Daniel Uriegas(adu170030)
 * @author Dhara Patel(dxp190051)
 */

// change to your netid
package adu170030;

import adu170030.Graph.Vertex;
import adu170030.Graph.Edge;
import adu170030.Graph.GraphAlgorithm;
import adu170030.Graph.Factory;
import adu170030.Graph.Timer;

import java.io.File;
import java.util.*;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {

    private static List<Vertex> topList = new LinkedList<>();

    public static class DFSVertex implements Factory {
        int cno;
        boolean visited;
        boolean finished;

        /*
         * Constructor of DFSVertex
         * @param u: Vertex
         * @return null
        */
        public DFSVertex(Vertex u) {
            visited = false;
            finished = false;
        }

        /*
         * Method: make()
         * @param u: Vertex
         * @return DFSVertex
         */
        public DFSVertex make(Vertex u) {
            return new DFSVertex(u);
        }
    }

    /*
     * Constructor of DFS
     * @param g: Graph
     * @return null
     */
    public DFS(Graph g) {
        super(g, new DFSVertex(null));
    }

    /*
     * Method: depthFirstSearch()
     * @param g: Graph
     * It checks if the node is visited or not, if not traverse through its edges.
     * @return null
     */
    public static DFS depthFirstSearch(Graph g) {
        DFS dfs = new DFS(g);

        for (Vertex x : g) {
            if (!dfs.get(x).visited) {
                try {
                    dfs.traverseEdges(g, x);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return null;
                }
            }
        }
        return dfs;
    }

    /*
     * Method: traverseEdges()
     * @param g: Graph
     * @param g: Vertex
     * It traverses the edges from the vertex u from v for all the vertices and edges. It then adds the vertex to the topological
     * order list once there are no edges left.
     * @return null
     */
    private void traverseEdges(Graph g, Vertex u) throws Exception{
        get(u).visited = true;

        List<Edge> edges = g.adj(u).outEdges;
        for (int i = 0; i < edges.size(); i++){
            Vertex v = edges.get(i).toVertex();
            if (!get(v).visited) {
                traverseEdges(g, v);
            } else if (get(v).visited && !get(v).finished) {
                throw new Exception("There is a cycle.");
            } else if (get(v).finished) {
                continue;
            }
        }

        topList.add(0, u);
        get(u).finished = true;
    }

    // Member function to find topological order
    /*
     * Method: topologicalOrder1()
     * Calls depthFirstSearch to create the topological order list
     * @return topological ordered list
     */
    public List<Vertex> topologicalOrder1() {
        depthFirstSearch(g);
        return topList;
    }

    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    public int connectedComponents() {
        return 0;
    }

    // After running the connected components algorithm, the component no of each vertex can be queried.
    public int cno(Vertex u) {
        return get(u).cno;
    }

    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    /*
     * Method: topologicalOrder1()
     * param g: Graph
     * @return topological ordered list
     */
    public static List<Vertex> topologicalOrder1(Graph g) {
        if (g.directed) {
            DFS d = new DFS(g);
            return d.topologicalOrder1();
        }
        return null;
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
        return null;
    }

    public static void main(String[] args) throws Exception {
        String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1 0";
//        string = "11 12   1 2 2   1 3 3   2 4 5   3 4 4   3 6 6   4 5 1   1 5 7   6 7 1  6 8 1  8 9 1  8 10 1";
//        String string = "9 13 1 2 1 2 3 1 3 1 1 3 4 1 4 5 1 5 6 1 6 3 1 4 7 1 7 8 1 8 4 1 5 7 1 7 9 1 9 5 1";


        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readGraph(in, true);
        g.printGraph(false);

        List<Vertex> topologicList = topologicalOrder1(g);
        if (topologicList != null) System.out.println(topologicList.toString());
        else System.out.println("not a directed graph");
    }
}