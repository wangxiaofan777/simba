package com.wxf;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Admin启动类
 *
 * @author wangmaosong
 * @since 2022-06-01
 */
@EnableAdminServer
@SpringBootApplication
public class SimbaAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimbaAdminApplication.class, args);
    }
}
