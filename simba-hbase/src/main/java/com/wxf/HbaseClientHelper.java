package com.wxf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;

/**
 * Hello world!
 */
public class HbaseClientHelper {

    private static volatile Connection connection = null;
    private static Configuration configuration = null;


    static {
        configuration = HBaseConfiguration.create();
        configuration.addResource(new Path("D:/workspace/work2021/simba/config/hbase-site.xml"));
        configuration.addResource(new Path("D:/workspace/work2021/simba/config/core-site.xml"));
        configuration.addResource(new Path("D:/workspace/work2021/simba/config/hdfs-site.xml"));
        init();
    }

    private HbaseClientHelper() {
    }

    /**
     * 初始化
     */
    public static Connection init() {
        try {
            UserGroupInformation ugi = UserGroupInformation.loginUserFromKeytabAndReturnUGI("admin/admin@HADOOP.COM", "D:/workspace/work2021/simba/config/root.keytab");
            ugi.doAs((PrivilegedExceptionAction<Connection>) HbaseClientHelper::getConnection);
            return getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭连接
     */
    public static void close() {
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
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
                    connection = ConnectionFactory.createConnection(configuration);
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


    /**
     * 创建表
     *
     * @param desc 表描述
     * @throws IOException 异常
     */
    public static void createTable(TableDescriptor desc) throws IOException {
        HbaseClientHelper.admin().createTable(desc);
    }

    public static void listTableNames() throws IOException {
        Table test = connection.getTable(TableName.valueOf("test"));
        Admin admin = connection.getAdmin();

        System.out.println(Arrays.toString(admin.listTableNames()));

    }


    public static void main(String[] args) throws IOException {

        listTableNames();
    }

}
