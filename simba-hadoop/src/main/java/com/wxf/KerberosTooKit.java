package com.wxf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Kerberos工具类
 *
 * @author WangMaoSong
 * @date 2023/1/9 13:24
 */
public class KerberosTooKit {
    private static final String JAVA_SECURITY_KRB5_CONF = "java.security.krb5.conf";

    private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    static {
        System.setProperty(JAVA_SECURITY_KRB5_CONF, "D:\\workspace\\work2021\\simba\\config\\krb5.conf");

        Runtime.getRuntime().addShutdownHook(new Thread(scheduledExecutorService::shutdown));
    }


    /**
     * 登录
     *
     * @param user   用户
     * @param keytab keytab
     */
    public static void login(String user, String keytab) {
        Configuration configuration = new Configuration();
        UserGroupInformation.setConfiguration(configuration);
        try {
            UserGroupInformation.loginUserFromKeytab(user, keytab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 刷新认证
     *
     * @param delay    延迟时间
     * @param timeUnit 时间
     */
    public static void refresh(int delay, TimeUnit timeUnit) {
        scheduledExecutorService.schedule(() -> {
            try {
                UserGroupInformation.getCurrentUser().reloginFromKeytab();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }, delay, timeUnit);
    }


    /**
     * 获取当前用户
     *
     * @return 当前用户
     * @throws IOException 异常
     */
    public static UserGroupInformation getCurrentUser() throws IOException {
        return UserGroupInformation.getCurrentUser();
    }


    /**
     * 获取登录用户
     *
     * @return 登录用户
     * @throws IOException 异常
     */
    public static UserGroupInformation getLoginUser() throws IOException {
        return UserGroupInformation.getLoginUser();
    }
}
