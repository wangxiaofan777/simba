package com.wxf.config.hbase;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * HBase配置
 *
 * @author WangMaoSong
 * @date 2022/9/27 15:24
 */
@Data
@Configuration
@ConfigurationProperties(prefix = HBaseProperties._PREFIX)
@ConditionalOnProperty(prefix = HBaseProperties._PREFIX, value = {"enabled"}, havingValue = "true")
public class HBaseProperties {

    public static final String _PREFIX = "hbase";

    private String zookeeperQuorum;

    private String zookeeperPort;

    private String zookeeperParent;

    private String regionServerPrincipal;

    private String masterPrincipal;

}
