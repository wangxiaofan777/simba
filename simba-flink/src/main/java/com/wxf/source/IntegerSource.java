package com.wxf.source;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.concurrent.TimeUnit;

/**
 * Integer 数据源
 *
 * @author WangMaoSong
 * @date 2023/1/5 9:56
 */
public class IntegerSource implements SourceFunction<Integer> {

    private int i = 0;

    @Override
    public void run(SourceContext<Integer> ctx) throws Exception {
        while (true) {
            ctx.collect(i++);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Override
    public void cancel() {

    }
}
