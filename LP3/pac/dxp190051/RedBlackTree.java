/**
 LP3 - RedBlackTree.java
 @author Umar Khalid (uxk150630)
 @author Dhara Patel (dxp190051)
 @author Reetish Chand (rxg190006)
 @author Rohan Vannala (rxv190003)
 */

package rxg190006;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    /**
     * Entry Class for RedBlackTree
     * @param <T>
     */
    static class Entry<T> extends BinarySearchTree.Entry<T> {
        boolean color;

        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            color = RED;
        }

        boolean isRed() {
            return color == RED;
        }

        boolean isBlack() {
            return color == BLACK;
        }
    }

    private Entry<T> NIL;

    /**
     * Public Constructor for RedBlackTree
     */
    public RedBlackTree() {
        super();
        NIL = new Entry(null,null,null);
        NIL.color = BLACK;
    }

    /**
     * Get Parent Entry from BinarySearchTree
     * @param x
     * @return
     */
    private Entry<T> getParent(Entry<T> x){
        return (Entry<T>)super.findParent(x);
    }

    /**
     * Get Uncle Entry from BinarySearchTree
     * @param x
     * @return
     */
    private Entry<T> getUncle(Entry<T> x){
        return (Entry<T>)super.findUncle(x);
    }

    /**
     * Get Sibling Entry according to Parent Entry
     * @param x
     * @return
     */
    private Entry<T> getSibling(Entry<T> x){
        if(x == root) {
            return null;
        }
        if(x.isLeftChild()) {
            return (Entry<T>)getParent(x).right;
        } else {
            return (Entry<T>) getParent(x).left;
        }
    }

    /**
     * Does a left rotation on x
     * @param x
     */
    protected void leftRotate(Entry<T> x){
        BinarySearchTree.Entry<T> rightChild = x.right;

        rightChild.left.isLeftChild = false;
        x.right = rightChild.left;
        addParentToMap(rightChild.left, x);

        BinarySearchTree.Entry<T> parent = findParent(x);
        boolean isLeftChildOfRoot = x.isLeftChild();

        x.isLeftChild = true;
        rightChild.left = x;

        addParentToMap(x, rightChild);

        if(x == root) {
            root = rightChild;
            return;
        }

        if(isLeftChildOfRoot){
            rightChild.isLeftChild = true;
            parent.left = rightChild;
            addParentToMap(rightChild, parent);
            addUncleToMap(rightChild, parent);
        }
        else{
            rightChild.isLeftChild = false;
            parent.right = rightChild;
            addParentToMap(rightChild, parent);
            addUncleToMap(rightChild, parent);

        }
    }

    /**
     * Does a right rotation on x
     * @param x
     */
    protected void rightRotate(Entry<T> x){
        BinarySearchTree.Entry<T> leftChild = x.left;

        leftChild.right.isLeftChild = true;
        x.left = leftChild.right;
        addParentToMap(leftChild.right,x);

        BinarySearchTree.Entry<T> parent = findParent(x);
        boolean isLeftChildOfRoot = x.isLeftChild();
        x.isLeftChild = false;
        leftChild.right = x;
        addParentToMap(x, leftChild);

        if(x == root){
            root = leftChild;
            return;
        }
        if(isLeftChildOfRoot) {
            leftChild.isLeftChild = true;
            parent.left = leftChild;
            addParentToMap(leftChild, parent);
            addUncleToMap(leftChild, parent);
        }
        else{
            leftChild.isLeftChild = false;
            parent.right = leftChild;
            addParentToMap(leftChild, parent);
            addUncleToMap(leftChild, parent);
        }
    }

    /**
     * Adds x to the tree
     * @param x
     * @return
     */
    public boolean add(T x) {
        Entry<T> cur = new Entry<T>(x, NIL, NIL);
        if(!super.add(cur)) {
            return false;
        }

        while(cur != root && getParent(cur).color != BLACK) {
            if(getParent(cur).isLeftChild()) {
                if(getUncle(cur).color == RED) { //CASE 1
                    getParent(cur).color = getUncle(cur).color = BLACK;
                    cur = getParent(getParent(cur));
                    cur.color = RED;
                } else {
                    if(cur.isRightChild()) { //CASE 2
                        cur = getParent(cur);
                        leftRotate(cur);
                    }
                    getParent(cur).color = BLACK; //CASE 3
                    if(getParent(getParent(cur)) != null) {
                        getParent(getParent(cur)).color = RED;
                        rightRotate(getParent(getParent(cur)));
                    }
                }
            } else {
                if(getUncle(cur).color == RED) { //CASE 1
                    getParent(cur).color = getUncle(cur).color = BLACK;
                    cur = getParent(getParent(cur));
                    cur.color = RED;
                } else {
                    if(cur.isLeftChild()) { //CASE 2
                        cur = getParent(cur);
                        rightRotate(cur);
                    }
                    getParent(cur).color = BLACK; //CASE 3
                    if(getParent(getParent(cur)) != null) {
                        getParent(getParent(cur)).color = RED;
                        leftRotate(getParent(getParent(cur)));
                    }
                }
            }
        }
        ((Entry<T>)root).color = BLACK;
        return true;
    }

    /**
     * Validates that the RedBlackTree is correct
     * @return
     */
    public boolean verifyRBT(){
        boolean rootNode, redParentNode, numOfBlackNodes;
        HashSet<Entry<T>> allLeafNodes = new HashSet<>();
        rootNode = ((Entry<T>)(root)).isBlack();
        Queue<Entry<T>> queue = new LinkedList<>();
        redParentNode = true;

        queue.add((Entry<T>)root);
        while(!queue.isEmpty() && redParentNode){
            Entry<T> rt = queue.remove();
            if(rt.isRed()) {
                redParentNode = ((Entry<T>)rt.left).isBlack();
                redParentNode = redParentNode && ((Entry<T>)rt.right).isBlack();
            }
            if(rt.left != null) {
                queue.add((Entry<T>)rt.left);
            }
            if(rt.right != null) {
                queue.add((Entry<T>)rt.right);
            }
            if(rt.left == null && rt.right == null) {
                allLeafNodes.add(rt);
            }
        }
        numOfBlackNodes = blackNodesHeight((Entry<T>)root) != -1;

        return rootNode && numOfBlackNodes;
    }

    /**
     * Gets the number of black nodes in path from root to leaf
     * @param root
     * @return
     */
    int blackNodesHeight(Entry<T> root)
    {
        if (root == null)
            return 1;

        int leftSideHeight = blackNodesHeight((Entry<T>)root.left);
        if (leftSideHeight == 0) {
            return leftSideHeight;
        }

        int rightSideHeight = blackNodesHeight((Entry<T>)root.right);
        if (rightSideHeight == 0) {
            return rightSideHeight;
        }

        if (leftSideHeight != rightSideHeight) {
            return 0;
        } else {
            return leftSideHeight + (root.isBlack() ? 1 : 0);
        }
    }

    protected void copy(BinarySearchTree.Entry<T> destinationNode, BinarySearchTree.Entry<T> sourceNode){
        super.copy(destinationNode, sourceNode);
        ((Entry<T>) destinationNode).color = ((Entry<T>) sourceNode).color;
    }

    /**
     * Removes an element from the tree
     * @param x
     * @return
     */
    public T remove(T x)
    {
        Entry<T> t = (Entry<T>)find(x);
        if(size == 0 || t.element.compareTo(x) != 0) {
            return null;
        }
        super.remove(t);
        Entry<T> cur = (Entry<T>)splicedChild;
        if(((Entry<T>)splicedEntry).color == BLACK) {
            fixUp(cur);
        }
        return x;
    }

    /**
     * After deleting an element from the tree, fixUp() fixes the tree so that it is still a proper RedBlackTree
     * @param cur
     */
    private void fixUp(Entry<T> cur){
        Entry<T> siblingEntry, parentEntry, leftOfSibling, rightOfSibling;
        while(cur != root && cur.isBlack()){
            if(cur.isLeftChild())
            {
                siblingEntry = getSibling(cur);
                if(siblingEntry.isRed()) { //CASE 1
                    siblingEntry.color = BLACK;
                    parentEntry = getParent(cur);
                    parentEntry.color = RED;
                    leftRotate(parentEntry);
                    siblingEntry = getSibling(cur);
                }
                if(siblingEntry == NIL) {
                    break;
                }
                leftOfSibling = (Entry<T>)siblingEntry.left;
                rightOfSibling = (Entry<T>)siblingEntry.right;

                if(leftOfSibling.isBlack() && rightOfSibling.isBlack()) { //CASE 2
                    siblingEntry.color = RED;
                    cur = getParent(cur);
                } else {
                    if(leftOfSibling != NIL && rightOfSibling.isBlack()) { //CASE 3
                        leftOfSibling.color = BLACK;
                        siblingEntry.color = RED;
                        rightRotate(siblingEntry);
                        siblingEntry = getSibling(cur);
                    }

                    if(siblingEntry == NIL) {
                        break;
                    }
                    rightOfSibling = (Entry<T>)siblingEntry.right;

                    //CASE 4
                    rightOfSibling.color = BLACK;
                    parentEntry = getParent(cur);
                    siblingEntry.color = parentEntry.color;
                    parentEntry.color = BLACK;
                    leftRotate(parentEntry);
                    cur = (Entry<T>)root;
                }
            }
            else
            {
                siblingEntry = getSibling(cur);

                if(siblingEntry.isRed()) { //CASE 1
                    siblingEntry.color = BLACK;
                    parentEntry = getParent(cur);
                    parentEntry.color = RED;
                    rightRotate(parentEntry);
                    siblingEntry = getSibling(cur);
                }
                if(siblingEntry == NIL) {
                    break;
                }
                leftOfSibling = (Entry<T>)siblingEntry.left;
                rightOfSibling = (Entry<T>)siblingEntry.right;

                if(leftOfSibling.isBlack() && rightOfSibling.isBlack()) { //CASE 2
                    siblingEntry.color = RED;
                    cur = getParent(cur);
                } else {
                    if(rightOfSibling != NIL && leftOfSibling.isBlack()) { //CASE 3
                        rightOfSibling.color = BLACK;
                        siblingEntry.color = RED;
                        leftRotate(siblingEntry);
                        siblingEntry = getSibling(cur);
                    }
                    if(siblingEntry == NIL) {
                        break;
                    }

                    //CASE 4
                    leftOfSibling = (Entry<T>)siblingEntry.left;
                    leftOfSibling.color = BLACK; // Case 4
                    parentEntry = getParent(cur);
                    siblingEntry.color = parentEntry.color;
                    parentEntry.color = BLACK;
                    rightRotate(parentEntry);
                    cur = (Entry<T>)root;
                }
            }
        }
    }
}