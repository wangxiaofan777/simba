package com.wxf;

import com.wxf.source.IntegerSource;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * 滑动窗口测试
 *
 * @author WangMaoSong
 * @date 2023/1/4 20:52
 */
public class SlidingProcessingTimeWindowsTest {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        DataStreamSource<Integer> source = env.addSource(new IntegerSource());
        // 基于ProcessingTime的滑动窗口，窗口长度是3s，每次滑动2s，即统计最近3s的数据
        source.windowAll(SlidingProcessingTimeWindows.of(Time.seconds(3), Time.seconds(1)))
                .process(new ProcessAllWindowFunction<Integer, Integer, TimeWindow>() {
                    @Override
                    public void process(ProcessAllWindowFunction<Integer, Integer, TimeWindow>.Context context, Iterable<Integer> elements, Collector<Integer> out) throws Exception {
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
