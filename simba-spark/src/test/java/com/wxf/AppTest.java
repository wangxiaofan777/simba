package com.wxf;

import org.apache.commons.io.FileSystemUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void test01() throws IOException {
        System.setProperty("java.security.krb5.conf", "D:\\workspace\\work2021\\simba\\simba-spark\\src\\main\\resources\\krb5.conf");
        UserGroupInformation.loginUserFromKeytab("admin/admin@HADOOP.COM", "D:\\workspace\\work2021\\simba\\simba-spark\\src\\main\\resources\\root.keytab");
        Path path = new Path("hdfs://nameservice1/");
        System.out.println(path);
    }
}
