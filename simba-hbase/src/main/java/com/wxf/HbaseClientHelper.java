package com.wxf;

import com.wxf.config.HbaseConfig;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class HbaseClientHelper {

    private static final Logger logger = LoggerFactory.getLogger(HbaseClientHelper.class);

    private static volatile Connection connection = null;
    private static Configuration configuration = null;
    private static final HbaseConfig hbaseConfig;

    static {
        hbaseConfig = HbaseConfig.load();
        System.setProperty("java.security.krb5.conf", hbaseConfig.getKrb5());
        configuration = HBaseConfiguration.create();

        UserGroupInformation.setConfiguration(configuration);

        logger.info("hbase connection initializing....");
        init();
        logger.info("hbase connection init successful....");
    }

    private HbaseClientHelper() {
    }

    /**
     * 初始化
     */
    public static Connection init() {
        try {
            UserGroupInformation.loginUserFromKeytab(hbaseConfig.getPrincipal(), hbaseConfig.getKeytab());
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
     * @param tableName 表名
     * @param columns   列名
     * @throws IOException 异常
     */
    public static void createTable(String tableName, String... columns) throws IOException {
        Set<ColumnFamilyDescriptor> columnFamilyDescriptorSet = Arrays.stream(columns)
                .map(column -> ColumnFamilyDescriptorBuilder.newBuilder(column.getBytes(StandardCharsets.UTF_8)).build())
                .collect(Collectors.toSet());
        TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(TableName.valueOf(tableName)).setColumnFamilies(columnFamilyDescriptorSet).build();
        HbaseClientHelper.admin().createTable(tableDescriptor);
    }

    /**
     * 查看表
     *
     * @return 查询表信息
     * @throws IOException 异常
     */
    public static TableName[] listTableNames() throws IOException {
        return HbaseClientHelper.admin().listTableNames();
    }

    /**
     * 保存数据
     *
     * @param tableName 表名
     */
    public static void put(String tableName) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put("wms".getBytes(StandardCharsets.UTF_8));
        table.put(put);
    }


    public static void main(String[] args) throws IOException {
        for (TableName tableName : listTableNames()) {
            System.out.println(tableName.getNameAsString());
        }
//        createTable("wms", "t1", "t2", "t3");
        close();
//        put("test");
    }

}
