package com.wxf.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.IOException;
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
    public static void setBucketExpire(String name, Object value, long timeToLive, TimeUnit timeUnit) {
        redissonClient.getBucket(name).set(value, timeToLive, timeUnit);
    }

    public static Object getBucket(String name) {
        if (redissonClient.getBucket(name).isExists()) {
            return null;
        }
        return redissonClient.getBucket(name).get();
    }


    public static void test01() {
        System.out.println(config);
    }

    public static void main(String[] args) {
//        RedissonManager.setBucket("b1", new JSONObject(1).fluentPut("name", "wms").fluentPut("age", 30));

        System.out.println(RedissonManager.getBucket("b1"));
    }


    private void close() {
        redissonClient.shutdown();
    }
}
