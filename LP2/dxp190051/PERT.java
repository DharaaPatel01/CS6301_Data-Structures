/**
 * PERT.java
 * @author Reetish
 * @author Rohan
 * @author Dhara Patel
 * @author Umar Khalid
 *
 */

package rxg190006;

import java.io.File;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import rxg190006.Graph.Edge;
import rxg190006.Graph.Factory;
import rxg190006.Graph.GraphAlgorithm;
import rxg190006.Graph.Vertex;

public class PERT extends GraphAlgorithm<PERT.PERTVertex> {
    public static class PERTVertex implements Factory {
        int bound;
        boolean isCritical;
        int leftCount;
        int rightCount;
        int timeElapsed;

        public PERTVertex(Vertex u) {
            this.bound = 0;
            this.leftCount = 0;
            this.isCritical = false;
        }

        @Override
        public PERTVertex make(Vertex vertex) {
            return new PERTVertex(vertex);
        }

    }

    public static void main(String[] args) throws Exception {
        String graph = "11 12   2 4 1   2 5 1   3 5 1   3 6 1   4 7 1   5 7 1   5 8 1   6 8 1   6 9 1   7 10 1   8 10 1   9 10 1      0 3 2 3 2 1 3 2 4 1 0";
        Scanner in;
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(graph);
        Graph g = Graph.readDirectedGraph(in);
        g.printGraph(false);

        PERT p = new PERT(g);
        for (Vertex u : g) {
            p.setDuration(u, in.nextInt());
        }
        // Run PERT algorithm. Returns null if g is not a DAG
        if (p.pert()) {
            System.out.println("Invalid graph: not a DAG");
        } else {
            System.out.println("Number of critical vertices: " + p.numCritical());
            System.out.println("u\tEC\tLC\tSlack\tCritical");
            for (Vertex u : g) {
                System.out.println(u + "\t" + p.ec(u) + "\t" + p.lc(u) + "\t" + p.slack(u) + "\t" + p.critical(u));
            }
        }
    }

    public static PERT pert(Graph g, int[] duration) {
        PERT pert = new PERT(g);
        for (Vertex u : g) {
            pert.setDuration(u, duration[u.getIndex()]);
            if (u.getName() != 1) {
                g.addEdge(pert.source.getIndex(), u.getIndex(), 0);
            }
            if (u.getName() != g.size()) {
                g.addEdge(u.getIndex(), pert.destination.getIndex(), 0);
            }

        }
        if (pert.pert()) {
            return pert;
        } else {
            return null;
        }
    }

    int criticalNodeCount;

    Vertex destination;

    Vertex source;

    int timeInSeconds;

    public PERT(Graph graph) {
        super(graph, new PERTVertex(null));
        this.timeInSeconds = 0;
        this.criticalNodeCount = 0;
        this.source = graph.getVertex(1);
        this.destination = graph.getVertex(graph.size());
    }

    public boolean critical(Vertex u) {
        return get(u).isCritical;
    }

    public int criticalPath() {
        return timeInSeconds;
    }

    public int ec(Vertex u) {
        return get(u).leftCount;
    }

    public int lc(Vertex u) {
        return get(u).rightCount;
    }

    public int numCritical() {
        return criticalNodeCount;
    }

    public boolean pert() {
        DFS dfsGraph = new DFS(graph);
        List<Vertex> sortedNodes = dfsGraph.topologicalOrder1();
        if (null == sortedNodes) {
            return false;
        }
        for (Vertex sortedVertex : sortedNodes) {
            for (Edge sortedEdge : graph.incident(sortedVertex)) {
                Vertex connectedVertex = sortedEdge.otherEnd(sortedVertex);
                if (get(connectedVertex).leftCount < get(sortedVertex).leftCount + get(connectedVertex).timeElapsed) {
                    get(connectedVertex).leftCount += get(connectedVertex).timeElapsed;
                }
            }
        }
        timeInSeconds = get(destination).leftCount;
        for (Vertex vertex : graph) {
            get(vertex).rightCount = timeInSeconds;
        }

        ListIterator<Vertex> sortedReverse = sortedNodes.listIterator(sortedNodes.size());
        while (sortedReverse.hasPrevious()) {
            Vertex reverseVertex = sortedReverse.previous();
            for (Edge reverseEdge : graph.incident(reverseVertex)) {
                Vertex vertex = reverseEdge.otherEnd(reverseVertex);
                if (get(vertex).rightCount - get(vertex).timeElapsed < get(reverseVertex).rightCount) {
                    get(reverseVertex).rightCount = get(vertex).rightCount - get(vertex).timeElapsed;
                }
            }
            get(reverseVertex).bound = get(reverseVertex).rightCount - get(reverseVertex).leftCount;
            if (get(reverseVertex).bound == 0) {
                get(reverseVertex).isCritical = true;
                criticalNodeCount = +1;
            }
        }
        return true;
    }

    public void setDuration(Vertex vertex, int timeElapsed) {
        get(vertex).timeElapsed = timeElapsed;
    }

    public int slack(Vertex u) {
        return get(u).bound;
    }
}
