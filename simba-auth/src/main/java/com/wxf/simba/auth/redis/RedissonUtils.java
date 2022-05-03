package com.wxf.simba.auth.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redisson客户端
 *
 * @author WangMaoSong
 * @date 2022/5/3 14:07
 */
@Component
public class RedissonUtils {

    @Bean
    public RedissonClient getSingleServer() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://10.50.30.179:6379")
                .setPassword("lygr@0907")
                .setDatabase(0)
        ;
        return Redisson.create(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

}
