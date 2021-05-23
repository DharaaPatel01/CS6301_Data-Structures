package com.dxp190051;

import java.lang.reflect.Array;
import java.util.concurrent.ArrayBlockingQueue;

public class BlockedQueue<E> {

    E[] arr;
    int front, rear, size;

    public BlockedQueue(int n) {
        arr = (E[])new Object[n];
        front = 0;
        rear = 0;
        size = n;
    }

    public boolean offer(E x) {
        //Empty array case
        if (front == rear && arr[rear] == null) {
            arr[rear] = x;
            return true;
        }

        //Full array case
        if ((rear + 1) % size == front){
            return false;
        }

        rear = (rear + 1) % size;
        arr[rear] = x;
        return true;
    }

    public E poll() {
        if (isEmpty()) {
            return null;
        }
        //System.out.println("front: " + front + ", rear: " + rear);
        E tmp = arr[front];
        arr[front] = null;

        //Check if its not the only element in array
        if (front != rear) {
            front = (front + 1) % size;
        }
        return tmp;
    }

    public E peek() {
        return arr[front];
    }

    public int size() {
        if (isEmpty()){
            return 0;
        }

        if (rear > front) {
            return rear - front + 1;
        }

        return ((size - (front - rear)) % size) + 1;
    }

    public boolean isEmpty() {
        if (front == rear && arr[rear] == null) {
            return true;
        }
        return false;
    }

    public void clear() {
        for (int i = front; i <= front + rear + 1; i++) {
            arr[i % size] = null;
        }
        front = 0;
        rear = 0;
    }

    public void toArray(E[] x) {
        System.out.println("front: " + front + ", rear: " + rear);
        if (isEmpty()){
            return;
        }

        for (int i = front, j=0; i <= front + rear + 1; i++, j++) {
            System.out.println("arr[" + i % size + "]: " + arr[i % size]);
            x[j] = arr[i % size];
        }
    }
}
