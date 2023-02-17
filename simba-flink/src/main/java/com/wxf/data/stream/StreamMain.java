package com.wxf.data.stream;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author WangMaoSong
 * @date 2023/1/6 15:19
 */
public class StreamMain {

    private static final StreamExecutionEnvironment env;

    static {
        env = StreamExecutionEnvironment.getExecutionEnvironment();
    }

    public static void readTextFile() {
        DataStreamSource<String> text = env.readTextFile("file:///d:/tmp/1.csv");


        text.print();
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readTextFile();
    }


}
