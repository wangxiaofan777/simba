package com.wxf;

/**
 * @author WangMaoSong
 * @date 2022/7/20 18:17
 */
public class Main {

    public static void main(String[] args) {
        System.setProperty("HBASE_CONF_DIR", "t1");
        System.setProperty("HADOOP_CONF_DIR", "t1");


        System.out.println(System.getenv("HBASE_CONF_DIR"));
        System.out.println(System.getenv("HADOOP_CONF_DIR"));

//        System.getenv().forEach((k, v) -> System.out.println(k + "----->" + v));
    }
}
