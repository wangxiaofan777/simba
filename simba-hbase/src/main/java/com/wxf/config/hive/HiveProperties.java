package com.wxf.config.hive;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Hive 配置
 *
 * @author WangMaoSong
 * @date 2022/10/21 18:37
 * @since 2.1.1
 */
@Data
@Configuration
@ConfigurationProperties(prefix = HiveProperties._PREFIX)
@ConditionalOnProperty(prefix = HiveProperties._PREFIX, value = {"enabled"}, havingValue = "true")
public class HiveProperties {

    public static final String _PREFIX = "hive";

    private String driverName;

    private String url;

    private String username;

    private String password;

    private Map<String, String> props;

}
