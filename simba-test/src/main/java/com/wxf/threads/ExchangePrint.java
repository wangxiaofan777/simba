package com.wxf.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多个线程交替打印0-100之间的数字
 *
 * @author WangMaoSong
 * @date 2023/1/6 17:19
 */
public class ExchangePrint {

    public static void main(String[] args) {
       /* PrintNumber printNumber = new PrintNumber();

        new Thread(printNumber, "线程1").start();
        new Thread(printNumber, "线程2").start();*/


        ConditionNumber conditionNumber = new ConditionNumber();
        new Thread(conditionNumber, "线程3").start();
        new Thread(conditionNumber, "线程4").start();

    }


}

/**
 * notify()、notifyAll()、wait()三个方法必须放在同步代码块或者同步方法中
 */
class PrintNumber implements Runnable {

    private int i = 0;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {

                // 一旦执行该方法，就会唤醒被wait()的一个线程，如果是多个，则唤醒优先级高的线程
                notify();
                if (i < 100) {
                    i++;
                    System.out.println(Thread.currentThread().getName() + "---" + i);
                } else {
                    break;
                }

                try {
                    // 一旦执行该方法，当前线程会被阻塞，并释放同步监视器（锁）
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}


class ConditionNumber implements Runnable {
    private int i = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    @Override
    public void run() {
        while (true) {
            // 加锁必须要在try catch外面，并且中间不能有任何可能产生异常的代码，否则可能会导致无法解锁或者解除不存在的锁，此时会产生异常
            lock.lock();
            try {
                condition.signal();
                if (i < 100) {
                    i++;
                    System.out.println(Thread.currentThread().getName() + "---" + i);
                } else {
                    break;
                }
                condition.await();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}