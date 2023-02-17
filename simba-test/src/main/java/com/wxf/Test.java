package com.wxf;

import java.util.concurrent.locks.LockSupport;

public class Test {


    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            System.out.println(1);
        }, "1");
        Thread t2 = new Thread(() -> {
            System.out.println(2);
            LockSupport.park(1000);
        }, "2");
        Thread t3 = new Thread(() -> {
            System.out.println(3);
            LockSupport.park(1000);
        }, "3");

        t1.start();
        t2.start();
        t3.start();
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
        LockSupport.unpark(t3);
        System.out.println(Thread.currentThread().getName());
    }
}
