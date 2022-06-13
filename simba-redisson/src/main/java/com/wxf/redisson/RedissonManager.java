package com.wxf.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Redisson管理器
 *
 * @author WangMaoSong
 * @date 2022/6/13 17:47
 */
@Slf4j
public class RedissonManager {

    private static Config config = null;

    private static RedissonClient redissonClient = null;


    static {
        try {
            config = Config.fromYAML(Files.newInputStream(Paths.get("redisson.yml"), StandardOpenOption.READ));

            if (log.isDebugEnabled()) {
                log.debug("load config from yaml, config: {}", config.toYAML());
            }

            redissonClient = Redisson.create(config);
        } catch (IOException e) {
            log.error("connect redisson client error", e);
        }
    }

    public static void test01() {
        System.out.println(config);
    }

    public static void main(String[] args) {
        RedissonManager.test01();

    }
}
