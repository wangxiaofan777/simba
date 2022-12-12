package com.wxf.config.hbase;

import com.wxf.config.kerberos.KerberosConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * HBASE连接
 *
 * @author WangMaoSong
 * @date 2022/9/27 15:30
 */
@Slf4j
@Component
@ConditionalOnBean(value = {HBaseProperties.class})
public class HBaseConfiguration {

    private static final String HBASE_SECURITY_AUTHENTICATION = "hbase.security.authentication";
    private static final String HBASE_SECURITY_AUTHORIZATION = "hbase.security.authorization";
    private static final String HBASE_MASTER_KERBEROS_PRINCIPAL = "hbase.master.kerberos.principal";
    private static final String HBASE_REGION_SERVER_KERBEROS_PRINCIPAL = "hbase.regionserver.kerberos.principal";


    @Bean
    public Connection connection(HBaseProperties hBaseProperties, KerberosConfiguration kerberosConfiguration) {
        Configuration configuration = org.apache.hadoop.hbase.HBaseConfiguration.create();
        configuration.set(HConstants.ZOOKEEPER_QUORUM, hBaseProperties.getZookeeperQuorum());
        configuration.set(HConstants.ZOOKEEPER_CLIENT_PORT, hBaseProperties.getZookeeperPort());
        configuration.set(HConstants.ZOOKEEPER_ZNODE_PARENT, hBaseProperties.getZookeeperParent());

        if (kerberosConfiguration.getAuth()) {
            configuration.set(HBASE_SECURITY_AUTHENTICATION, kerberosConfiguration.getAuthentication());
            configuration.set(HBASE_SECURITY_AUTHORIZATION, "true");
            configuration.set(HBASE_MASTER_KERBEROS_PRINCIPAL, hBaseProperties.getMasterPrincipal());
            configuration.set(HBASE_REGION_SERVER_KERBEROS_PRINCIPAL, hBaseProperties.getRegionServerPrincipal());

            // 登录Kerberos
            kerberosConfiguration.login(configuration);
        }

        try {
            return ConnectionFactory.createConnection(configuration);
        } catch (IOException e) {
            log.error("hbase connect failed ", e);
            throw new RuntimeException(e);
        }
    }
}
