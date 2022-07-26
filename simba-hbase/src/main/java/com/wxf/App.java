package com.wxf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.security.UserGroupInformation;

/**
 * @author WangMaoSong
 * @date 2022/7/26 16:17
 */
public class App {

    public static void main(String[] args) {

        System.setProperty("java.security.krb5.conf", "D:/workspace/work2021/simba/config/krb5.conf");

        Configuration configuration = HBaseConfiguration.create();

        System.out.println(configuration.get("hbase.rootdir"));

        configuration.set("hadoop.security.authentication", "Kerberos");
        configuration.set("hbase.security.authentication", "kerberos");
        configuration.set("kerberos.principal", "hbase/_HOST@CNTAIPING.COM");
        configuration.set("hbase.master.kerberos.principal", "hbase/_HOST@CNTAIPING.COM");
        configuration.set("hbase.regionserver.kerberos.principal", "hbase/_HOST@CNTAIPING.COM");
        UserGroupInformation.setConfiguration(configuration);

        try {

            UserGroupInformation.loginUserFromKeytab("admin/admin@HADOOP.COM", "D:/workspace/work2021/simba/config/admin.keytab");

            Connection connection = ConnectionFactory.createConnection(configuration);

            Table table = connection.getTable(TableName.valueOf("test"));

            System.out.println(table.getName());

            Scan scan = new Scan();

            ResultScanner rs = table.getScanner(scan);

            for (Result r : rs) {

                System.out.println(r.toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
