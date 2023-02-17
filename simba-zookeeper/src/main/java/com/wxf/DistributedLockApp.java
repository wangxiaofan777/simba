package com.wxf;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 */
public class DistributedLockApp {

    private static final String connectString = "10.50.30.186:2181,10.50.30.187:2181,10.50.30.188:2181";
    private static final String lockPath = "/wms/lock";
    private static final CuratorFramework client;

    static {
        client = CuratorFrameworkFactory.newClient(connectString, new ExponentialBackoffRetry(1000, 3));
        client.start();
    }

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            String threadName = "线程" + i;

            new Thread(() -> {
                try {
                    lock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, threadName).start();
        }


    }

    public static void lock() throws Exception {


        String threadName = Thread.currentThread().getName();


        System.out.println(threadName + ">>>>>>>>" + " locking");
        InterProcessMutex lock = new InterProcessMutex(client, lockPath);

        System.out.println(lock.isAcquiredInThisProcess());

        if (lock.acquire(3000, TimeUnit.SECONDS)) {
            try {
                System.out.println("-------------------------我是分割线-------------------------");

                System.out.println(threadName + ">>>>>>>>" + " acquire lock ...");
                TimeUnit.SECONDS.sleep(1);
                System.out.println(lock.isAcquiredInThisProcess());

            } finally {
                lock.release();
                System.out.println(threadName + ">>>>>>>>" + " releasing");
            }
        }
        System.out.println(lock.isAcquiredInThisProcess());

    }
}
