package com.wxf.simba.auth;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Redisson测试
 *
 * @author WangMaoSong
 * @date 2022/5/3 14:37
 */
@SpringBootTest
public class RedissonTest {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private CacheManager cacheManager;

    @Test
    public void test01() {
        boolean add = redissonClient.getList("ceshi").add("wms");
        System.out.println(add);
    }

    @Test
    public void test02() {
        System.out.println(this.redisTemplate.boundListOps("ceshi").leftPop(1, TimeUnit.SECONDS));
    }

    @Test
    public void test03() {
        System.out.println(JSONObject.toJSONString(this.cacheManager.getCacheNames()));
    }
}
