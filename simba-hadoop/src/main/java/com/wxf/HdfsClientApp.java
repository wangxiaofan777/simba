package com.wxf;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.IOException;


/**
 * Hello world!
 */
public class HdfsClientApp {


    public static void main(String[] args) throws IOException {

        KerberosTooKit.login("admin/admin@HADOOP.COM", "config/admin.keytab");

        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(configuration);

        boolean exists = fileSystem.exists(new Path("/wms"));

        System.out.println(exists);

        fileSystem.setTimes(new Path("/wms/test"), 1641703415000L, 1673235181562L);

        RemoteIterator<LocatedFileStatus> locatedFileStatusRemoteIterator = fileSystem.listFiles(new Path("/wms"), true);


    }
}
