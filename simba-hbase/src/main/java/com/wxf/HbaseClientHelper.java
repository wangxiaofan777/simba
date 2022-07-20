package com.wxf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.TableDescriptor;

import java.io.IOException;

/**
 * Hello world!
 */
public class HbaseClientHelper {

    private static volatile Connection connection = null;
    private static Configuration config = null;


    static {
        config = HBaseConfiguration.create();
        config.addResource(new Path(System.getenv("HBASE_CONF_DIR"), "hbase-site.xml"));
        config.addResource(new Path(System.getenv("HADOOP_CONF_DIR"), "core-site.xml"));
        init();
    }

    private HbaseClientHelper() {
    }

    /**
     * 初始化
     */
    private static Connection init() {
        try {
            return getConnection();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取连接
     *
     * @throws IOException 异常
     */
    public static Connection getConnection() throws IOException {
        if (connection == null) {
            synchronized (HbaseClientHelper.class) {
                if (connection == null) {
                    connection = ConnectionFactory.createConnection(config);
                }
            }
        }
        return connection;
    }


    /**
     * 获取admin
     *
     * @return admin
     * @throws IOException 异常
     */
    public static Admin admin() throws IOException {
        return connection.getAdmin();
    }


    public static void createTable(TableDescriptor desc) throws IOException {
        HbaseClientHelper.admin().createTable(desc);
    }


}
