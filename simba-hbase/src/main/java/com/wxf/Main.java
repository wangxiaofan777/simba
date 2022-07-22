package com.wxf;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @author WangMaoSong
 * @date 2022/7/20 18:17
 */
public class Main {

    public static void main(String[] args) {
        Config config = ConfigFactory.load();
        Config hbaseConfig = config.getConfig("hbase");
        System.out.println(hbaseConfig.getString("a"));
        System.out.println(hbaseConfig.getString("b"));
        System.out.println(hbaseConfig.getString("c"));


    }
}
