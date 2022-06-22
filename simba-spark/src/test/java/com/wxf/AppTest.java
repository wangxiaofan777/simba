package com.wxf;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void test01() throws IOException {
        System.setProperty("java.security.krb5.conf", "conf/krb5.conf");
        UserGroupInformation.loginUserFromKeytab("admin/admin@HADOOP.COM", "conf/root.keytab");
        Path path = new Path("hdfs://nameservice1/");
        System.out.println(path);
    }
}
