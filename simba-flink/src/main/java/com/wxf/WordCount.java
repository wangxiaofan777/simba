package com.wxf;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * WordCount
 *
 * @author wangmaosong
 * @since 2022-07-03
 */
public class WordCount {

    private static final String[] WORDS = {
            "Hello, World",
            "Hello, World",
            "Hello, World",
    };

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> text = env.fromElements(WORDS);
        SingleOutputStreamOperator<Tuple2<String, Integer>> word = text.flatMap((FlatMapFunction<String, Tuple2<String, Integer>>) (value, out) -> {
            String[] tokens = value.toLowerCase().split(",");
            for (String token : tokens) {
                if ((token.length() > 0)) {
                    out.collect(new Tuple2<>(token, 1));
                }
            }
        });

        SingleOutputStreamOperator<Tuple2<String, Integer>> counts = word.keyBy("f0").sum(1);
        counts.print();

        env.execute();
    }
}
