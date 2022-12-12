package com.wxf.config.hive;

import com.wxf.config.kerberos.KerberosConfiguration;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.hadoop.conf.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Hive连接
 *
 * @author WangMaoSong
 * @date 2022/10/22 13:15
 */
@Slf4j
@Component
@ConditionalOnBean(value = {HiveProperties.class})
public class HiveConfiguration {

    @Bean("hiveConnection")
    public Connection connection(@Qualifier("hiveDataSource") DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }

    @Bean("hiveDataSource")
    public DataSource dataSource(KerberosConfiguration kerberosConfiguration, HiveProperties hiveProperties) {
        // Kerberos认证
        Configuration configuration = new Configuration();
        kerberosConfiguration.login(configuration);

        // 创建连接池
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(hiveProperties.getDriverName());
        hikariDataSource.setJdbcUrl(hiveProperties.getUrl());
        hikariDataSource.setUsername(hikariDataSource.getUsername());
        hikariDataSource.setPassword(hikariDataSource.getPassword());
        Map<String, String> props = hiveProperties.getProps();
        if (MapUtils.isNotEmpty(props)) {
            props.forEach(hikariDataSource::addDataSourceProperty);
        }
        return hikariDataSource;
    }

    @Bean("hiveJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("hiveDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
