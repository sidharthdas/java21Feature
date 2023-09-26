package com.virtualThread;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class VirtualThreadMainClass {

    public static void main(String[] args) throws InterruptedException {


        // Thread creation

        Thread.ofVirtual()
                .start(() -> System.out.println("Virtual Thread : " + Thread.currentThread()));

        System.out.println("---------------------");

        Thread.ofPlatform()
                .start(() -> System.out.println("Virtual Thread : " + Thread.currentThread()));

        //------------------------------------------------------------------------------------
        // Check the usage of virtual thread of java 21

        var start = System.currentTimeMillis();
        var totalThread = 10000;
        var threads = IntStream.range(0, totalThread)
                .mapToObj(
                        thCount -> Thread.ofVirtual().unstarted(() -> {
                        })).toList();
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
        var end = System.currentTimeMillis();
        System.out.println("millis used to launch " + totalThread + "vthreads:" + (end - start) + "ms");


        //------------------------------------------------------------------------------------
        // Check when there is no work the thread doesnt stay ideal

        var threads2 = IntStream.range(0, totalThread)
                .mapToObj(
                        thCount -> Thread.ofVirtual().unstarted(() -> {
                            if(thCount == 0) {
                                System.out.println(Thread.currentThread());
                            }

                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            if(thCount == 0) {
                                System.out.println(Thread.currentThread());
                            }

                        })).toList();
        threads2.forEach(Thread::start);
        for (Thread thread : threads2) {
            thread.join();
        }

    }
}
