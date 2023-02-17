package com.wxf;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.nio.charset.StandardCharsets;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> textFile = env.readTextFile("/Users/wangmaosong/data/phzx2.csv", StandardCharsets.UTF_8.name());

        // 打印元素
        textFile.print();

        System.out.println(textFile.getId());

        // 执行
        JobExecutionResult jobExecutionResult = env.execute("App");

        System.out.println(jobExecutionResult);
    }
}
