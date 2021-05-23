Prerequisites

Before you continue, ensure you have met the following requirements:

* You have a IDE for Java.
* You are using JDK 15.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 

DFS

The class DFS implements the Topological sorting for Directed Acyclic Graph.
The methods used are:
a. depthFirstSearch() - It checks if the node is visited or not, if not traverse through its edges.
b. traverseEdges() - It traverses the edges from the vertex u from v for all the vertices and edges. It then adds the vertex to the topological order list once there are no edges left.
c. topologicalOrder1() - Calls depthFirstSearch to create the topological order list