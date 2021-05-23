Team Members:
Andres Daniel Uriegas(adu170030)
Dhara Patel(dxp190051)

Prerequisites

Before you continue, ensure you have met the following requirements:

* You have a IDE for Java.
* You are using JDK 15.
- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

BinarySearchTree class implements a BST that can return a topological sorting of the BST.
The methods used are:
a. contains(T x) - Finds if element x is contained in the BST.
b. get(T x) - Finds element x in BST and returns the value.
c. add(T x) - Adds new element to BST. If element already exists, it is replaced.
d. remove(T x) - Removes element in BST and rebalances accordingly.
e. splice() - Helper function that rebalances BST. Called by remove().
f. min() - returns leftmost leaf node of BST, aka. smallest element.
g. max() - returns rightmost leaf node of BST, aka. largest element.
h. toArray() - returns a comparable array made up of an in-order traversal of the BST

NOTE: Most functions have helper functions to execute recursive logic