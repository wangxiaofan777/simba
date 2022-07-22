package com.wxf.config;

import com.google.gson.Gson;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * HBase配置
 *
 * @author WangMaoSong
 * @date 2022/7/22 17:25
 */
public class HbaseConfig {

    private static final Config CONFIG = ConfigFactory.load();

    /**
     * 配置文件路径
     */
    private String configPath;

    /**
     * principal
     */
    private String principal;

    /**
     * keytab
     */
    private String keytab;


    /**
     * 加載Hbase配置
     *
     * @return Hbase配置
     */
    public static HbaseConfig load() {
        HbaseConfig hBaseConfig = new HbaseConfig();
        Config config = CONFIG.getConfig("hbase");
        hBaseConfig.setConfigPath(config.getString("configPath"));
        hBaseConfig.setPrincipal(config.getString("principal"));
        hBaseConfig.setKeytab(config.getString("keytab"));
        return hBaseConfig;
    }

    public static void main(String[] args) {

        System.out.println(new Gson().toJson(load()));
    }


    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getKeytab() {
        return keytab;
    }

    public void setKeytab(String keytab) {
        this.keytab = keytab;
    }

}
