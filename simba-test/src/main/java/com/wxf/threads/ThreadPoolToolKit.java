package com.wxf.threads;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池工具类
 *
 * @author WangMaoSong
 * @date 2023/1/10 13:05
 */
public class ThreadPoolToolKit extends ThreadPoolExecutor {

    public ThreadPoolToolKit(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ThreadPoolToolKit(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ThreadPoolToolKit(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ThreadPoolToolKit(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /**
     * 执行任务
     *
     * @param task 任务
     */
    public void execute(Runnable task) {
        super.execute(task);
    }

    /**
     * 提交任务
     *
     * @param task 任务
     * @return 返回值
     */
    public Future<?> submit(Runnable task) {
        return super.submit(task);
    }

    /**
     * 提交任务
     *
     * @param task   任务
     * @param result 返回值类型
     * @param <T>    返回值
     * @return 返回值
     */
    public <T> Future<T> submit(Runnable task, T result) {
        return super.submit(task, result);
    }

    /**
     * 提交任务
     *
     * @param task 任务
     * @param <V>  返回值类型
     * @return 返回值
     */
    public <V> Future<V> submit(Callable<V> task) {
        return super.submit(task);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        print();
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        print();
        super.afterExecute(r, t);

    }

    @Override
    protected void terminated() {
        super.terminated();
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return super.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return super.isShutdown();
    }

    @Override
    public boolean isTerminating() {
        return super.isTerminating();
    }

    @Override
    public boolean isTerminated() {
        return super.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return super.awaitTermination(timeout, unit);
    }


    public void print() {

        System.out.println(Thread.currentThread().getName() + ">>>>>>>>>>" + "corePoolSize：" + getCorePoolSize()
                + ", activeCount：" + getActiveCount()
                + ", taskCount：" + getTaskCount()
                + ", largestPoolSize：" + getLargestPoolSize()
                + ", poolSize：" + getPoolSize()
                + ", completedTaskCount：" + getCompletedTaskCount()
                + ", maximumPoolSize：" + getMaximumPoolSize());
    }


    public static void main(String[] args) {
        ThreadPoolToolKit threadPoolToolKit = new ThreadPoolToolKit(10, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), new ThreadFactory() {
            private static final String THREAD_NAME_PREFIX = "thread-pool-";
            private final AtomicInteger count = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, THREAD_NAME_PREFIX + count.getAndIncrement());
            }
        }, new CallerRunsPolicy());

        for (int i = 0; i < 100; i++) {
            threadPoolToolKit.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        threadPoolToolKit.shutdown();
    }
}
