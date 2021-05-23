/**
 * BinarySearchTree class implements a BST that can return a topological sorting of the BST.
 *
 * @author Andres Daniel Uriegas(adu170030)
 * @author Dhara Patel(dxp190051)
 *
 * The methods used are:
 * contains(T x), get(T x), add(T x), remove(T x), splice(), min(), max(), toArray()
 **/

package adu170030;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }

        /**
         * Method: children()
         * Finds number of non-null children
         * @return: integer number of children
         * */
        private int children() {
            if (left != null && right != null) {
                return 2;
            } else if (left == null && right == null) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    Entry<T> root;
    int size;

    public BinarySearchTree() {
        root = null;
        size = 0;
    }


    /**
     * Method: contains()
     * Finds if x is contained in BST
     * @param x: element to search
     * @return: boolean true if x is contained in BSt, false otherwise
     * */
    public boolean contains(T x) {
        return contains(x, root);
    }

    private boolean contains(T x, Entry<T> node) {
        if (node == null) return false;

        int compared = x.compareTo(node.element);
        if (compared == 0) {
            return true;
        } else if (compared < 0) {
            return contains(x, node.left);
        } else { // (compared > 0)
            return contains(x, node.right);
        }
    }

    /**
     * Method: get()
     * Retrieves the given element from the BST
     * @param x: element to get
     * @return: Element in tree that is equal to x is returned, null otherwise
     * */
    public T get(T x) {
        return get(x, root);
    }

    private T get(T x, Entry<T> node) {
        if (node == null) return null;

        int compared = x.compareTo(node.element);
        if (compared == 0) {
            return node.element;
        } else if (compared < 0) {
            return get(x, node.left);
        } else { // (compared > 0)
            return get(x, node.right);
        }
    }

    /**
     * Method: add()
     * Add x to BST
     * If tree contains a node with same key, replace element by x
     * @param x: element to add
     * @return: true if x is a new element added to tree
     * */
    public boolean add(T x) {
        if (root == null) {
            root = new Entry<>(x, null, null);
            size++;
            return true;
        }

        return add(x, root);
    }

    private boolean add(T x, Entry<T> node) {
        int compared = x.compareTo(node.element);
        if (compared == 0) {
            node = new Entry<>(x, null, null);
            return false;
        } else if (compared < 0) {
            if (node.left == null){
                node.left = new Entry<>(x, null, null);
            } else {
                return add(x, node.left);
            }
        } else { // (compared > 0)
            if (node.right == null){
                node.right = new Entry<>(x, null, null);
            } else {
                return add(x, node.right);
            }
        }
        size++;
        return true;
    }

    /**
     * Method: remove()
     * Remove x from tree
     * @param x: element to remove
     * @return: x if found, otherwise return null
     * */
    public T remove(T x) {
        if (root == null) return null;

        int compared = x.compareTo(root.element);
        if (compared == 0) {
            // Cannot call splice for root node case
            int children = root.children();
            T removed = root.element;

            switch (children) {
                case 0: root = null;
                        break;

                case 1: if (root.left != null) {
                            root = root.left;
                        } else {
                            root = root.right;
                        }
                        break;

                case 2: root.right.left = root.left;
                        root = root.right;
                        break;
            }
            size--;
            return removed;

        } else if (compared < 0) {
            return remove(x, root, root.left);
        } else { // (compared > 0)
            return remove(x, root, root.right);
        }
    }

    private T remove(T x, Entry<T> parent, Entry<T> node) {
        if (node == null) return null;

        int compared = x.compareTo(node.element);
        if (compared == 0) {
            return splice(x, parent, node);
        } else if (compared < 0) {
            return remove(x, node, node.left);
        } else { // (compared > 0)
            return remove(x, node, node.right);
        }
    }

    /**
     * Method: splice()
     * Remove x from tree and rebalance accordingly
     * @param x: element to remove
     * @param parent: parent of node to be removed
     * @param node: node to be removed from BST
     * @return: x if found, otherwise return null
     * */
    private T splice(T x, Entry<T> parent, Entry<T> node) {
        int children = node.children();
        T removed = node.element;

        switch (children) {
            case 0: if (parent.left == node) {
                        parent.left = null;
                    } else {
                        parent.right = null;
                    }
                    break;

            case 1: if (parent.left == node) {
                        if (node.left != null) {
                            parent.left = node.left;
                        } else {
                            parent.left = node.right;
                        }
                    } else {
                        if (node.left != null) {
                            parent.right = node.left;
                        } else {
                            parent.right = node.right;
                        }
                    }
                    break;

            case 2: Entry<T> successorParent = node;
                    Entry<T> successor = node.right;
                    while (successor.left != null) {
                        successorParent = successor;
                        successor = successor.left;
                    }

                    if (successor.right != null) {
                        successorParent.left = successor.right;
                    }
                    successorParent.left = null;
                    successor.right = node.right;

                    if (parent.left == node) {
                        parent.left = successor;
                    } else {
                        parent.right = successor;
                    }
                    successor.left = node.left;
                    break;
        }

        size--;
        return removed;
    }

    /**
     * Method: min()
     * Finds smallest element in BST
     * @return: element of the smallest node
     * */
    public T min() {
        if (root == null) return null;

        Entry<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.element;
    }

    /**
     * Method: max()
     * Finds largest element in BST
     * @return: element of the largest node
     * */
    public T max() {
        if (root == null) return null;

        Entry<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.element;
    }

    // TODO: Create an array with the elements using in-order traversal of tree
    /**
     * Method: toArray()
     * Create an array with the elements using in-order traversal of BST
     * @return: array of BST in in-order traversal
     * */
    public Comparable[] toArray() {
        Comparable[] arr = new Comparable[size];
        int index = 0;
        index = toArray(arr, index, root.left);
        arr[index] = root.element;
        index++;
        index = toArray(arr, index, root.right);
        return arr;
    }

    private int toArray(Comparable[] arr, int index, Entry<T> node) {
        if (node == null) { return index; }

        if (node.left == null) {
            arr[index] = node.element;
            index++;
        } else {
            index = toArray(arr, index, node.left);
            arr[index] = node.element;
            index++;
            return toArray(arr, index, node.right);
        }

        if (node.right != null) {
            index = toArray(arr, index, node.right);
        }
        return index;
    }


// Start of Optional problem 2

    /** Optional problem 2: Iterate elements in sorted order of keys
     Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator() {
        return null;
    }

    // Optional problem 2.  Find largest key that is no bigger than x.  Return null if there is no such key.
    public T floor(T x) {
        return null;
    }

    // Optional problem 2.  Find smallest key that is no smaller than x.  Return null if there is no such key.
    public T ceiling(T x) {
        return null;
    }

    // Optional problem 2.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
    public T predecessor(T x) {
        return null;
    }

    // Optional problem 2.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
    public T successor(T x) {
        return null;
    }

// End of Optional problem 2

    public static void main(String[] args) {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            int x = in.nextInt();
            if (x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if (x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for (int i = 0; i < t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                return;
            }
        }
    }


    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }

}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 

*/
