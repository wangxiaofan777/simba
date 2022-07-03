package com.wxf;

import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.java.io.TextInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.FileProcessingMode;

/**
 * 读取TXT文件
 */
public class ReadTexFile {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        String filePath = "/Users/wangmaosong/data/1.txt";
        TextInputFormat textInputFormat = new TextInputFormat(new Path(filePath));
        DataStreamSource<String> dataStreamSource = env.readFile(textInputFormat, filePath, FileProcessingMode.PROCESS_CONTINUOUSLY, 10000, BasicTypeInfo.STRING_TYPE_INFO);
        dataStreamSource.print();
        env.execute();
    }
}
