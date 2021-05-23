package com.dxp190051;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println(4%5);
        BlockedQueue<Integer> blockedQueue = new BlockedQueue<>(5);
        System.out.println("Size: " + blockedQueue.size());

        blockedQueue.offer(100);
        blockedQueue.offer(90);
        blockedQueue.offer(80);
        blockedQueue.offer(70);
        blockedQueue.offer(60);
        System.out.println("1. Size: " + blockedQueue.size());

        System.out.println("Poll: " + blockedQueue.poll());
        System.out.println("Poll: " + blockedQueue.poll());
        System.out.println("Poll: " + blockedQueue.poll());
        System.out.println("Poll: " + blockedQueue.poll());
        System.out.println("Poll: " + blockedQueue.poll());

        blockedQueue.offer(50);
        System.out.println("2. Size: " + blockedQueue.size());

        System.out.println("Poll: " + blockedQueue.poll());

        blockedQueue.offer(40);
        blockedQueue.offer(30);
        blockedQueue.offer(20);
        blockedQueue.offer(10);
        blockedQueue.offer(0);
        System.out.println("Poll: " + blockedQueue.poll());
        System.out.println("Poll: " + blockedQueue.poll());
        System.out.println("Poll: " + blockedQueue.poll());
        System.out.println("Poll: " + blockedQueue.poll());
        System.out.println("Poll: " + blockedQueue.poll());
        System.out.println("Peek: " + blockedQueue.peek());
        System.out.println("3. Size: " + blockedQueue.size());

        //
        // clear() method works as desired
        // commented out for the sake of testing alternate case for copying queue
        //
        //blockedQueue.clear();

        Integer[] x = new Integer[10];
        blockedQueue.toArray(x);
        System.out.println("Copy array : " + Arrays.toString(x));
    }
}
