package com.wxf;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.api.annotation.RInject;

import java.util.concurrent.Callable;

/**
 * @author WangMaoSong
 * @date 2022/6/17 14:26
 */
public class CallableTask implements Callable<Long> {

    @RInject
    private RedissonClient redissonClient;

    @Override
    public Long call() throws Exception {
        RMap<String, Integer> myMap = redissonClient.getMap("myMap");
        Long result = 0L;

        for (Integer value : myMap.values()) {
            result += value;
        }
        return result;
    }
}
