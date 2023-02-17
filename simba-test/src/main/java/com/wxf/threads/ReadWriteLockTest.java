package com.wxf.threads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试
 *
 * @author WangMaoSong
 * @date 2023/1/9 17:59
 */
public class ReadWriteLockTest {

    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static final Lock readLock = readWriteLock.readLock();
    private static final Lock writeLock = readWriteLock.writeLock();

    public static void main(String[] args) {
        new Thread(ReadWriteLockTest::read, "读锁1").start();
        new Thread(ReadWriteLockTest::read, "读锁2").start();
        new Thread(ReadWriteLockTest::write, "写锁1").start();
        new Thread(ReadWriteLockTest::write, "写锁2").start();
    }

    public static void read() {
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到锁，正在读取");
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放锁");
        }

    }

    public static void write() {
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "得到锁，正在写入");
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writeLock.unlock();
        }
    }
}
