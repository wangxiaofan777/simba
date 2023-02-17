package com.wxf;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CuratorTest {

    private static final String lockPath = "/extract/locks/test";

    private static final String connectionString = "10.50.30.186:2181,10.50.30.187:2181,10.50.30.188:2181";

    private RetryPolicy retryPolicy;

    private CuratorFramework client;
    private CuratorFramework client2;


    @Before
    public void init() {
        // 超时策略
        retryPolicy = new ExponentialBackoffRetry(60000, 3, 15000);

        // 创建客户端
        client = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
        client2 = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);

        // 创建会话
        client.start();
        client2.start();
    }

    /**
     * 共享锁
     *
     * @throws Exception
     */
    @Test
    public void shareLock() throws Exception {
        // 创建共享锁
        InterProcessSemaphoreMutex lock = new InterProcessSemaphoreMutex(client, lockPath);
        InterProcessSemaphoreMutex lock2 = new InterProcessSemaphoreMutex(client, lockPath);

        // 获取锁
        lock.acquire();


        // 测试是否可以重入
        // 锁已经被获取，所以返回 false
        Assert.assertFalse(lock.acquire(2, TimeUnit.SECONDS));

        // 释放锁
        lock.release();

        // lock2返回成功，因为锁已经释放
        Assert.assertTrue(lock2.acquire(2, TimeUnit.SECONDS));

        lock2.release();
    }


    /**
     * 共享可重入锁
     */
    @Test
    public void shareReentrantLock() throws Exception {
        InterProcessMutex lock = new InterProcessMutex(client, lockPath);
        InterProcessMutex lock2 = new InterProcessMutex(client2, lockPath);

        // 获取锁
        lock.acquire();
        try {
            // 二次获取锁
            lock.acquire();
            try {
                Assert.assertFalse(lock2.acquire(2, TimeUnit.SECONDS));
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // 第一次释放
                lock.release();
            }
        } finally {
            // 第二次释放
            lock.release();
        }

        // 等待锁释放后，lock2可获取到锁
        Assert.assertTrue(lock2.acquire(2, TimeUnit.SECONDS));
        lock2.release();
    }


    @Test
    public void shareReentrantReadWriteLock() throws Exception {
        InterProcessReadWriteLock lock = new InterProcessReadWriteLock(client, lockPath);
        InterProcessReadWriteLock lock2 = new InterProcessReadWriteLock(client2, lockPath);

        InterProcessMutex readLock = lock.readLock();

        InterProcessMutex writeLock = lock2.writeLock();
        /**
         * 读写锁测试对象
         */
        class ReadWriteLockTest {
            // 测试数据变更字段
            private Integer testData = 0;
            private Set<Thread> threadSet = new HashSet<>();

            // 写入数据
            private void write() throws Exception {
                writeLock.acquire();
                try {
                    Thread.sleep(10);
                    testData++;
                    System.out.println("写入数据 \t" + testData);
                } finally {
                    writeLock.release();
                }
            }

            // 读取数据
            private void read() throws Exception {
                readLock.acquire();
                try {
                    Thread.sleep(10);
                    System.out.println("读取数据 \t" + testData);
                } finally {
                    readLock.release();
                }
            }

            // 等待线程结束, 防止 test 方法调用完成后, 当前线程直接退出, 导致控制台无法输出信息
            public void waitThread() throws InterruptedException {
                for (Thread thread : threadSet) {
                    thread.join();
                }
            }

            // 创建线程方法
            private void createThread(int type) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (type == 1) {
                                write();
                            } else {
                                read();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                threadSet.add(thread);
                thread.start();
            }

            // 测试方法
            public void test() {
                for (int i = 0; i < 5; i++) {
                    createThread(1);
                }
                for (int i = 0; i < 5; i++) {
                    createThread(2);
                }
            }
        }

        ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();
        readWriteLockTest.test();
        readWriteLockTest.waitThread();
    }


    // 共享信号量
    @Test
    public void semaphore() throws Exception {
        // 创建一个信号量, Curator 以公平锁的方式进行实现
        InterProcessSemaphoreV2 semaphore = new InterProcessSemaphoreV2(client, lockPath, 6);
        // semaphore2 用于模拟其他客户端
        InterProcessSemaphoreV2 semaphore2 = new InterProcessSemaphoreV2(client2, lockPath, 6);

        // 获取一个许可
        Lease lease = semaphore.acquire();
        Assert.assertNotNull(lease);
        // semaphore.getParticipantNodes() 会返回当前参与信号量的节点列表, 俩个客户端所获取的信息相同
        Assert.assertEquals(semaphore.getParticipantNodes(), semaphore2.getParticipantNodes());

        // 超时获取一个许可
        Lease lease2 = semaphore2.acquire(2, TimeUnit.SECONDS);
        Assert.assertNotNull(lease2);
        Assert.assertEquals(semaphore.getParticipantNodes(), semaphore2.getParticipantNodes());

        // 获取多个许可, 参数为许可数量
        Collection<Lease> leases = semaphore.acquire(2);
        Assert.assertTrue(leases.size() == 2);
        Assert.assertEquals(semaphore.getParticipantNodes(), semaphore2.getParticipantNodes());

        // 超时获取多个许可, 第一个参数为许可数量
        Collection<Lease> leases2 = semaphore2.acquire(2, 2, TimeUnit.SECONDS);
        Assert.assertTrue(leases2.size() == 2);
        Assert.assertEquals(semaphore.getParticipantNodes(), semaphore2.getParticipantNodes());

        // 目前 semaphore 已经获取 3 个许可, semaphore2 也获取 3 个许可, 加起来为 6 个, 所以他们无法再进行许可获取
        Assert.assertNull(semaphore.acquire(2, TimeUnit.SECONDS));
        Assert.assertNull(semaphore2.acquire(2, TimeUnit.SECONDS));

        // 释放一个许可
        semaphore.returnLease(lease);
        semaphore2.returnLease(lease2);
        // 释放多个许可
        semaphore.returnAll(leases);
        semaphore2.returnAll(leases2);
    }


    @After
    public void close() {
        client.close();
        client2.close();
    }

}
