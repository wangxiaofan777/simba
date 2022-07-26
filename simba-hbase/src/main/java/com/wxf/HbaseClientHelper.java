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
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.ResultScanner;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class HbaseClientHelper {

    private static final Logger logger = LoggerFactory.getLogger(HbaseClientHelper.class);

    private static volatile Connection connection = null;
    private static final Configuration configuration;
    private static final HbaseConfig hbaseConfig;
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);

    private static Admin ADMIN;

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
    public static void init() {
        try {
            UserGroupInformation.loginUserFromKeytab(hbaseConfig.getPrincipal(), hbaseConfig.getKeytab());
            getConnection();
            ADMIN = connection.getAdmin();
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
    public static void getConnection() throws IOException {
        if (connection == null) {
            synchronized (HbaseClientHelper.class) {
                if (connection == null) {
                    connection = ConnectionFactory.createConnection(configuration, EXECUTOR_SERVICE);
                }
            }
        }
    }


    /**
     * 创建表
     *
     * @param tableName 表名
     * @param columns   列名
     * @throws IOException 异常
     */
    public static void createTable(String tableName, String... columns) throws IOException {
        if (ADMIN.tableExists(TableName.valueOf(tableName))) {
            logger.info("table {} does not exist return", tableName);
            return;
        }

        logger.info("create hbase table {} columns {}", tableName, columns);
        Set<ColumnFamilyDescriptor> columnFamilyDescriptorSet = Arrays.stream(columns)
                .map(column -> ColumnFamilyDescriptorBuilder.newBuilder(column.getBytes(StandardCharsets.UTF_8)).build())
                .collect(Collectors.toSet());
        TableDescriptor tableDescriptor = TableDescriptorBuilder
                .newBuilder(TableName.valueOf(tableName))
                .setColumnFamilies(columnFamilyDescriptorSet)
                .build();
        ADMIN.createTable(tableDescriptor);
        logger.info("create hbase table {} success", tableName);
    }

    /**
     * 查看表
     *
     * @return 查询表信息
     * @throws IOException 异常
     */
    public static TableName[] listTableNames() throws IOException {
        return ADMIN.listTableNames();
    }

    /**
     * 保存数据
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @param colFamily 列族
     * @param column    列名
     * @param value     值
     * @throws IOException 异常
     */
    public static void insertRow(String tableName, String rowKey, String colFamily, String column, String value) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(rowKey.getBytes());
        put.addColumn(colFamily.getBytes(), column.getBytes(), value.getBytes());
        table.put(put);
    }

    /**
     * 刪除数据
     *
     * @param tableName 表名
     * @param rowKey    rowKey
     * @param colFamily 列族
     * @param column    列名
     * @throws IOException 异常
     */
    public static void deleteRow(String tableName, String rowKey, String colFamily, String column) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Delete delete = new Delete(rowKey.getBytes());
        delete.addColumn(colFamily.getBytes(), column.getBytes());
        table.delete(delete);
    }

    /**
     * 查询
     *
     * @param tableName 表名
     * @param colFamily 列名
     * @return 返回值
     * @throws IOException 异常
     */
    public static ResultScanner selectRows(String tableName, String colFamily) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        return table.getScanner(colFamily.getBytes());
    }


    /**
     * 删除表
     *
     * @param tableName 表名
     * @throws IOException 异常
     */
    public static void dropTable(String tableName) throws IOException {
        TableName tb = TableName.valueOf(tableName);
        logger.info("disable table {}", tableName);
        ADMIN.disableTable(tb);
        logger.info("delete table {}", tableName);
        ADMIN.deleteTable(tb);
    }


    public static void main(String[] args) throws IOException {
        /*for (TableName tableName : listTableNames()) {
            System.out.println(tableName.getNameAsString());
        }*/
        createTable("wms", "t1", "t2", "t3");
        insertRow("wms", "r1", "t1", "c1", "v1");
//        deleteRow("wms", "r1", "t1", "c1");
//        dropTable("wms");
        close();
//        put("test");
    }

}
