package com.wxf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;

public class TestHbaseKerberos {


    public static void main(String[] args) {
        Configuration conf = new Configuration();
        /**
         * zookeeper地址
         */
        conf.set("hbase.zookeeper.quorum", "optimus30a187:2181,optimus30a188:2181,optimus30a186:2181");
        /**
         * zookeeper端口
         */
        conf.set("hbase.zookeeper.property.clientport", "2181");
        /**
         * hbase使用kerberos验证
         */
        conf.set("hbase.security.authentication", "kerberos");
        /**
         * hbase master节点的principal（验证实体）
         */
        conf.set("hbase.master.kerberos.principal", "admin/admin@HADOOP.COM");
        /**
         * 访问hbase集群的principal
         */
        conf.set("kerberos.principal", "admin/admin@HADOOP.COM");
        /**
         * 访问hbase集群的principal对应的keytab文件路径
         */
        conf.set("kerberos.keytab", "D:/workspace/work2021/simba/config/root.keytab");
        System.setProperty("java.security.krb5.conf", "D:/workspace/work2021/simba/config/krb5.conf");
        UserGroupInformation.setConfiguration(conf);
        try {
            //使用待验证的实体，调用loginUserFromKeytab api向hbase进行kerberos验证
            UserGroupInformation.loginUserFromKeytab("admin/admin@HADOOP.COM", "D:/workspace/work2021/simba/config/admin.keytab");
            /**
             * 验证通过后即可与hbase建立连接，对hbase进行操作
             */
            Connection connection = ConnectionFactory.createConnection(conf);
//            HBaseAdmin.available(conf);
            for (TableName tableName : connection.getAdmin().listTableNames()) {
                System.out.println(tableName.getName());
            }
            System.out.println();
//            System.out.println(connection.isClosed());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}