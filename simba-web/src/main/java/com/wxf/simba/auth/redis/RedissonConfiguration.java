package com.wxf.simba.auth.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson配置
 *
 * @author WangMaoSong
 * @date 2022/5/3 14:06
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedissonConfiguration {
}
