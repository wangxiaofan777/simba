package com.wxf.pattern.chain;

/**
 * Base Handler
 *
 * @author WangMaoSong
 * @date 2023/1/5 13:50
 */
public interface RequestHandler {

    /**
     * 消费消息
     *
     * @param msg 消息
     */
    void doHandler(String msg);
}
