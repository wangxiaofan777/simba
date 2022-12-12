package com.wxf.config.kerberos;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.security.UserGroupInformation;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Kerberos认证配置
 *
 * @author WangMaoSong
 * @date 2022/9/27 15:55
 */
@Data
@Slf4j
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "kerberos")
public class KerberosConfiguration implements Serializable {

    private static final long serialVersionUID = 4492205518054600095L;


    private static final String HADOOP_SECURITY_AUTHENTICATION = "hadoop.security.authentication";
    private static final String JAVA_SECURITY_KRB5_CONF = "java.security.krb5.conf";

    /**
     * 是否开启认证
     */
    private Boolean auth;

    /**
     * 认证方式
     */
    private String authentication;

    /**
     * principle
     */
    private String principal;

    /**
     * keytab
     */
    private String keytab;

    /**
     * Krb5配置文件
     */
    private String krb5Conf;

    /**
     * 刷新间隔
     */
    private Duration interval = Duration.ofMinutes(30);

    /**
     * 登录
     *
     * @param configuration 配置
     */
    public synchronized void login(org.apache.hadoop.conf.Configuration configuration) {
        if (auth) {
            System.setProperty(JAVA_SECURITY_KRB5_CONF, krb5Conf);

            configuration.set(HADOOP_SECURITY_AUTHENTICATION, authentication);
            UserGroupInformation.setConfiguration(configuration);
            try {
                log.info("========================================== kerberos logining... ========================================== ");
                // 登录
                UserGroupInformation.loginUserFromKeytab(principal, keytab);

                // 刷新Kerberos认证
                refresh();
                log.info("========================================== kerberos login successful... ========================================== ");
            } catch (IOException e) {
                log.error("========================================== kerberos authentication failed... ========================================== ", e);
            }
        }
    }

    /**
     * 重新登录
     */
    public void refresh() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            try {
                log.info("============================== kerberos refresh start =======================================");
                Thread.currentThread().setDaemon(true);
                UserGroupInformation.getCurrentUser().reloginFromKeytab();
                log.info("============================== kerberos refresh end =======================================");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 1, interval.getSeconds(), TimeUnit.SECONDS);
    }
}
