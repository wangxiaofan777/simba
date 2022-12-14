package com.wxf.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.ExecutorOptions;
import org.redisson.api.RBucket;
import org.redisson.api.RExecutorService;
import org.redisson.api.RLiveObjectService;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.IOException;
import java.util.BitSet;
import java.util.concurrent.TimeUnit;

/**
 * Redisson管理器
 *
 * @author WangMaoSong
 * @date 2022/6/13 17:47
 */
@Slf4j
public class RedissonManager {

    private static final Config config = new Config();
    private static RedissonClient redissonClient = null;

    static {
        try {
            config.useMasterSlaveServers()
                    .setClientName("SINGLE")
                    .setMasterAddress("redis://10.50.30.177:6379")
                    .addSlaveAddress("redis://10.50.30.178:6379", "redis://10.50.30.179:6379")
                    .setPassword("lygr@0907")
                    .setUsername("admin")
                    .setDnsMonitoringInterval(1000);

            if (log.isDebugEnabled()) {
                log.debug("load config from yaml, config: {}", config.toYAML());
            }

            redissonClient = Redisson.create(config);
        } catch (IOException e) {
            log.error("connect redisson client error", e);
        }
    }


    /**
     * 设置桶对象
     *
     * @param name  键
     * @param value 对象
     */
    public static void setBucket(String name, Object value) {
        redissonClient.getBucket(name).set(value);
    }

    /**
     * 设置桶对象
     *
     * @param name       键
     * @param value      对象
     * @param timeToLive 存活时间
     * @param timeUnit   时间单位
     */
    public static void setBucket(String name, Object value, long timeToLive, TimeUnit timeUnit) {
        redissonClient.getBucket(name).set(value, timeToLive, timeUnit);
    }

    public static void trySet(String name, Object value) {
        RBucket<Object> bucket = redissonClient.getBucket(name);
        bucket.trySet(value);
    }

    public static void trySet(String name, Object value, long timeToLive, TimeUnit timeUnit) {
        RBucket<Object> bucket = redissonClient.getBucket(name);
        bucket.trySet(value, timeToLive, timeUnit);
    }

    public static void trySetAsync(String name, Object value) {
        RBucket<Object> bucket = redissonClient.getBucket(name);
        bucket.trySetAsync(value);
    }


    public static void trySetAsync(String name, Object value, long timeToLive, TimeUnit timeUnit) {
        RBucket<Object> bucket = redissonClient.getBucket(name);
        bucket.trySetAsync(value, timeToLive, timeUnit);
    }


    public static void compareAndSet(String name, Object value, Object newValue) {
        RBucket<Object> bucket = redissonClient.getBucket(name);
        bucket.compareAndSet(value, newValue);
    }


    public static void getAndSet(String name, Object value) {
        RBucket<Object> bucket = redissonClient.getBucket(name);
        bucket.getAndSet(value);
    }

    /**
     * 获取bucket对象
     *
     * @param name 键
     * @return bucket对象
     */
    public static Object getBucket(String name) {
        if (!redissonClient.getBucket(name).isExists()) {
            return null;
        }
        return redissonClient.getBucket(name).get();
    }


    /*  Binary Stream  */

    public static void setBinaryStream(String name, byte[] bytes) {
        redissonClient.getBinaryStream(name).set(bytes);
    }

    public static byte[] getBinaryStream(String name) {
        return redissonClient.getBinaryStream(name).get();
    }


    /* bitset  */
    public static void setBitSet(String name, BitSet bitSet) {
        redissonClient.getBitSet(name).set(bitSet);
    }

    public static BitSet getBitSet(String name) {
        return redissonClient.getBitSet(name).asBitSet();
    }


    /**
     * 获取分布式服务对象（RLO）
     *
     * @return 分布式服务对象
     */
    public static RLiveObjectService getLiveObjectService() {
        return redissonClient.getLiveObjectService();
    }


    public static RExecutorService rExecutorService(String name, ExecutorOptions options) {
        return redissonClient.getExecutorService(name, options);
    }

    public static RExecutorService rExecutorService(String name) {
        return redissonClient.getExecutorService(name);
    }


    public static void main(String[] args) {
//        RedissonManager.setBucket("b1", new JSONObject(1).fluentPut("name", "wms").fluentPut("age", 30));
//        RedissonManager.setBucket("b1", new JSONObject(1).fluentPut("name", "wms").fluentPut("age", 30), 10L, TimeUnit.MINUTES);

        System.out.println(RedissonManager.getBucket("b1"));


        close();
    }


    private static void close() {
        redissonClient.shutdown();
    }
}
