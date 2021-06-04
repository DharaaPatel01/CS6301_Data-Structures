/**
 * DFS.java
 * @author Reetish (rxg190006)
 * @author Rohan (rxv190003)
 * @author Dhara Patel (dxp190051)
 * @author Umar Khalid (uxk150630)
 *
 */
package rxg190006;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import rxg190006.Graph.Edge;
import rxg190006.Graph.Factory;
import rxg190006.Graph.GraphAlgorithm;
import rxg190006.Graph.Vertex;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {

    public static class DFSVertex implements Factory {
        int cno;
        public boolean visited;
        public Vertex parent;
        int first;

        public DFSVertex(Vertex u) {
            this.visited = false;
            this.parent = null;
            this.first = 0;
            this.cno = 0;
        }

        public DFSVertex make(Vertex u) {
            return new DFSVertex(u);
        }
    }
    public static boolean hasCycle;
    public static DFS depthFirstSearch(Graph graph) {
        DFS dfsGraph = new DFS(graph);
        dfsGraph.dfs();
        return dfsGraph;
    }
    public static void main(String[] args) throws Exception {
        String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1 0";
        Scanner in;
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        Graph graph = Graph.readGraph(in);
        graph.printGraph(false);

        DFS dfsGraph = new DFS(graph);
        int totalConnectedComponents = dfsGraph.connectedComponents();
        System.out.println("Total number of components: " + totalConnectedComponents + "\n vertex \t cno");
        for (Vertex vertex : graph) {
            System.out.println(vertex + "\t" + dfsGraph.cno(vertex));
        }
        System.out.println("");

        System.out.println("DFS Output:\n Node \t Top \t Parent \n");
        for (Vertex vertex : graph) {
            System.out.println(vertex + "\t" + dfsGraph.get(vertex).first + "\t" + dfsGraph.get(vertex).parent);
        }
        System.out.println("");
        System.out.println("The topological order of the graph is:\n");
        List<Vertex> toplogicalList = dfsGraph.topologicalOrder1();
        if(toplogicalList==null) {
            System.out.println("The given graph is cyclic and there is no topological order.");
        }else {
            for(Vertex vertex : toplogicalList) {
                System.out.print(vertex + "\t");
            }
            System.out.println("");
        }
    }

    public static List<Vertex> topologicalOrder1(Graph graph) {
        DFS dfsGraph = new DFS(graph);
        return dfsGraph.topologicalOrder1();
    }

    private LinkedList<Vertex> finalList;

    public int totalComponents;

    private int upperBound;

    public DFS(Graph graph) {
        super(graph, new DFSVertex(null));
        hasCycle = false;
        totalComponents=0;
    }

    public int cno(Vertex vertex) {
        return get(vertex).cno;
    }

    public int connectedComponents() {
        dfs();
        return totalComponents;
    }

    public void dfs() {
        upperBound = graph.size();
        for (Vertex vertex : graph) {
            get(vertex).visited = false;
            get(vertex).parent = null;
            get(vertex).first = 0;
        }
        finalList = new LinkedList<Vertex>();
        for (Vertex vertex : graph) {
            if (!get(vertex).visited) {
                get(vertex).cno = ++totalComponents;
                DFS_visit(vertex);
            }
        }
    }

    private void DFS_visit(Vertex vertex) {
        get(vertex).visited = true;
        for (Edge edge : graph.incident(vertex)) {
            Vertex vertex2 = edge.otherEnd(vertex);
            if (!get(vertex2).visited) {
                get(vertex2).parent = vertex;
                get(vertex2).cno = get(vertex).cno;
                DFS_visit(vertex2);
            } else {
                if (get(vertex2).first == 0) {
                    hasCycle = true;
                }
            }
        }
        get(vertex).first = upperBound--;
        finalList.addFirst(vertex);
    }

    public List<Vertex> topologicalOrder1() {
        dfs();
        if(hasCycle) {
            return null;
        }
        return finalList;
    }
}