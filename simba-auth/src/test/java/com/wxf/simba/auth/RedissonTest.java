package com.wxf.simba.auth;

import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    public void test01() {
        boolean add = redissonClient.getList("ceshi").add("wms");
        System.out.println(add);
    }
}
