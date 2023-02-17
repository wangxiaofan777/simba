package com.wxf;

import com.wxf.source.IntegerSource;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.util.Collector;

import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * 滑动窗口测试
 *
 * @author WangMaoSong
 * @date 2023/1/4 20:52
 */
public class SlidingCountWindowsTest {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        DataStreamSource<Integer> source = env.addSource(new IntegerSource());
        // 每个窗口5条数据，滑动2个数据
        source.countWindowAll(5, 2)
                .process(new ProcessAllWindowFunction<Integer, Integer, GlobalWindow>() {
                    @Override
                    public void process(ProcessAllWindowFunction<Integer, Integer, GlobalWindow>.Context context, Iterable<Integer> elements, Collector<Integer> out) throws Exception {
                        Iterator<Integer> it = elements.iterator();
                        int sum = 0;
                        while (it.hasNext()) {
                            Integer next = it.next();
                            sum += next;
                            System.out.println(String.format("元素：%s， 处理时间：%s", next, LocalDateTime.now()));
                        }
                        out.collect(sum);
                    }
                }).print();

        try {
            env.execute("SlidingProcessingTimeWindowsTest");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
