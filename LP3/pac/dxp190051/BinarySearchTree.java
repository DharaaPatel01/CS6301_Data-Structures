/**
 LP3 - BinarySearchTree.java
 @author Umar Khalid (uxk150630)
 @author Dhara Patel (dxp190051)
 @author Reetish Chand (rxg190006)
 @author Rohan Vannala (rxv190003)
 */

package rxg190006;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;


public class BinarySearchTree<T extends Comparable<? super T>> {
    Entry<T> root;
    Map<Entry<T>,Entry<T>> parents;
    Map<Entry<T>,Entry<T>> uncles;
    Entry<T> splicedChild;
    Entry<T> splicedEntry;
    int size;
    Stack<Entry<T>> stack;

    /**
     * Class for Entry
     * @param <T>
     */
    static class Entry<T> {
        T element;
        Entry<T> left, right;
        boolean isLeftChild;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }

        public Entry(T x) {
            this(x,null,null);
        }

        public boolean isLeftChild(){
            return isLeftChild;
        }

        public boolean isRightChild(){
            return !isLeftChild;
        }
    }

    /**
     * Public Constructor for BinarySearchTree
     */
    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    /**
     * Gets the Parent Entry in the tree
     * @param childEntry
     * @return
     */
    protected Entry<T> findParent(Entry<T> childEntry){
        return parents.get(childEntry);
    }

    /**
     * Gets the Uncle Entry in the tree
     * @param x
     * @return
     */
    protected Entry<T> findUncle(Entry<T> x){
        return uncles.get(x);
    }

    /**
     * Adds parent entry to the Map
     * @param childEntry
     * @param parentEntry
     */
    protected void addParentToMap(Entry<T> childEntry, Entry<T> parentEntry){
        if(childEntry != null)
            parents.put(childEntry, parentEntry);
    }

    /**
     * Adds Uncle Entry to the Map
     * @param childEntry
     * @param parentEntry
     */
    void addUncleToMap(Entry<T> childEntry, Entry<T> parentEntry){
        if(childEntry != null)
        {
            Entry<T> grandParent = findParent(parentEntry);
            if(grandParent == null){
                return;
            }

            if(parentEntry.isLeftChild)
                uncles.put(childEntry, grandParent.right);
            else
                uncles.put(childEntry, grandParent.left);
        }
    }

    /**
     * Finds x in the BinarySearchTree
     * @param x
     * @return
     */
    protected Entry<T> find(T x){
        stack  = new Stack<>();
        parents= new HashMap<>();
        uncles= new HashMap<>();
        parents.put(root, null);
        uncles.put(root, null);
        stack.push(null);
        return find(root,x);
    }

    private Entry<T> find(Entry<T> t, T x){
        if(t == null || t.element.compareTo(x) == 0) {
            return t;
        }
        while(true) {
            if(x.compareTo(t.element) < 0) {
                if(t.left == null || t.left.element == null || t.element.compareTo(x) == 0) {
                    break;
                }
                stack.push(t);
                addParentToMap(t.left, t);
                addParentToMap(t.right, t);
                addUncleToMap(t.left, t);
                t = t.left;
            }
            else if(x.compareTo(t.element) == 0) {
                break;
            }
            else if(t.right == null || t.right.element == null) {
                    break;
            } else {
                stack.push(t);
                addParentToMap(t.right, t);
                addParentToMap(t.left, t);
                addUncleToMap(t.right, t);
                t = t.right;
            }
        }
        return t;
    }

    /**
     * Checks if x is in BinarySearchTree
     * @param x
     * @return
     */
    public boolean contains(T x) {
        Entry<T> t = find(x);
        if (t == null || x.compareTo(t.element) != 0) {
            return false;
        }
        return true;
    }

    /**
     * Gets x from BinarySearchTree
     * @param x
     * @return
     */
    public T get(T x) {
        Entry<T> t = find(x);
        if (t == null || x.compareTo(t.element) != 0) {
            return null;
        }
        return x;
    }

    /**
     * Adds x to BinarySearchTree
     * @param x
     * @return
     */
    public boolean add(T x) {
        return  add(new Entry<>(x));
    }

    protected boolean add(Entry<T> givenEntry) {
        if(size == 0) {
            stack  = new Stack<>();
            parents= new HashMap<>();
            uncles= new HashMap<>();
            parents.put(root, null);
            uncles.put(root, null);
            stack.push(null);
            root = givenEntry;
            size++; return true;
        } else {
            Entry<T> t = find(givenEntry.element);
            if(t.element.compareTo(givenEntry.element) == 0) {
                return false;
            }
            if(givenEntry.element.compareTo(t.element)<0) {
                givenEntry.isLeftChild = true;
                t.left = givenEntry;
            } else {
                givenEntry.isLeftChild = false;
                t.right = givenEntry;
            }
            size++;
            stack.push(t);
            addParentToMap(givenEntry, t);
            addUncleToMap(givenEntry, t);
            return true;
        }
    }

    /**
     * Removes x from BinarySearchTree
     * @param x
     * @return
     */
    public T remove(T x) {
        if(size == 0) {
            return null;
        }
        Entry<T> t = find(x);
        if(t.element.compareTo(x) != 0) {
            return null;
        }
        return remove(t);
    }

    public T remove(Entry<T> t) {
        T x = t.element;
        if(t.left == null || t.left.element == null || t.right == null || t.right.element == null) {
            splice(t);
        } else {
            stack.push(t);
            Entry<T> minRight = find(t.right,x);
            copy(t,minRight);

            addParentToMap(t.right, t);
            addParentToMap(t.left, t);

            splice(minRight);
        }
        size--;
        return x;
    }

    protected void copy(Entry<T> destination, Entry<T> source) {
        destination.element = source.element;
    }

    /**
     * Replaces t with the child of t
     * @param t
     */
    public void splice(Entry<T> t) {
        Entry<T> parent = stack.peek();
        Entry<T> child = ((t.left == null || t.left.element == null) ? t.right : t.left);
        splicedEntry = t;
        splicedChild = child;

        if (parent == null) {
            root = child;
        } else if (parent.left == t) {
            if(child != null) {
                child.isLeftChild = true;
            }
            parent.left = child;
            addParentToMap(child, parent);
        } else {
            if(child != null) {
                child.isLeftChild = false;
            }
            parent.right = child;
            addParentToMap(child, parent);
        }
    }

    /***
     * Finds the minimum value in the tree and returns it
     * @return min value in tree
     */
    public T min() {
        if (size == 0) {
            return null;
        }

        Entry<T> t = root;
        while (t.left != null) {
            t = t.left;
        }

        return t.element;
    }

    /***
     * Finds the maximum value in the tree and returns it
     * @return max value in tree
     */
    public T max() {
        if (size == 0) {
            return null;
        }

        Entry<T> t = root;
        while (t.right != null) {
            t = t.right;
        }

        return t.element;
    }

    /***
     * Create an array with the elements using in-order traversal of tree
     * @return in-order traversed array
     */
    public Comparable[] toArray() {
        Comparable[] arr = new Comparable[size];

        if (root == null) {
            return null;
        }

        Stack<Entry<T>> s = new Stack<Entry<T>>();
        Entry<T> t = root;
        int i = 0;

        while (t != null || s.size() > 0) {
            while (t != null) {
                s.push(t);
                t = t.left;
            }
            t = s.pop();

            arr[i] = t.element;
            i++;

            t = t.right;
        }
        return arr;
    }

    /**
     * Main Method for BinarySearchTree
     * @param args
     */
    public static void main(String[] args) {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int x = in.nextInt();
            if(x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if(x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for(int i = 0; i < t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                return;
            }
        }
    }

    /**
     * Methods to print the Tree
     */
    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    void printTree(Entry<T> node) {
        if(node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }
}

