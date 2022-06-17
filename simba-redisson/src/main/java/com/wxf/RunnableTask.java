package com.wxf;

import com.wxf.redisson.RedissonManager;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RExecutorFuture;
import org.redisson.api.RExecutorService;
import org.redisson.api.RedissonClient;
import org.redisson.api.annotation.RInject;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @author WangMaoSong
 * @date 2022/6/17 14:30
 */
public class RunnableTask implements Runnable {

    @RInject
    private RedissonClient redissonClient;

    private Long param;

    public RunnableTask(Long param) {
        this.param = param;
    }

    @Override
    public void run() {
        RAtomicLong atomic = redissonClient.getAtomicLong("myAtomic");
        atomic.addAndGet(param);
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RExecutorService service = RedissonManager.rExecutorService("ceshi");
        RExecutorFuture<Long> future = service.submit((Callable<Long> & Serializable) () -> {
            System.out.println("cehsi");
            return 0L;
        });

        // 阻塞执行
        Long aLong = future.get();

        // 取消
        future.cancel(true);


    }
}
