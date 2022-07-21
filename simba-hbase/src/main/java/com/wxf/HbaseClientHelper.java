package com.wxf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;

/**
 * Hello world!
 */
public class HbaseClientHelper {

    private static volatile Connection connection = null;
    private static Configuration configuration = null;


    static {

        System.setProperty("java.security.krb5.conf", "D:/workspace/work2021/simba/config/krb5.conf");
        configuration = HBaseConfiguration.create();
        configuration.addResource(new Path(System.getenv("HBASE_CONF_DIR"), "hbase-site.xml"));
        configuration.addResource(new Path(System.getenv("HADOOP_CONF_DIR"), "core-site.xml"));

        configuration.set("hadoop.security.authentication" , "Kerberos" );
        // 这个hbase.keytab也是从远程服务器上copy下来的, 里面存储的是密码相关信息
        // 这样我们就不需要交互式输入密码了
        configuration.set("keytab.file" , "D:/workspace/work2021/simba/config/root.keytab" );
        // 这个可以理解成用户名信息，也就是Principal
        configuration.set("kerberos.principal" , "admin/admin@HADOOP.COM" );

        UserGroupInformation.setConfiguration(configuration);
        try {
            UserGroupInformation.loginUserFromKeytab("admin/admin@HADOOP.COM", "D:/workspace/work2021/simba/config/root.keytab");
        } catch (IOException e) {
            e.printStackTrace();
        }
        init();
    }

    private HbaseClientHelper() {
    }

    /**
     * 初始化
     */
    public static Connection init() {
        try {
            return getConnection();
        } catch (IOException e) {
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
        for (TableName tableName : HbaseClientHelper.admin().listTableNames()) {
            System.out.println(tableName);
        }
    }


    public static void main(String[] args) throws IOException {

        listTableNames();
    }

}
