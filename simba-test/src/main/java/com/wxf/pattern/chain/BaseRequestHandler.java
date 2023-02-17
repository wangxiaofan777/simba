package com.wxf.pattern.chain;

/**
 * 抽象请求处理器
 *
 * @author WangMaoSong
 * @date 2023/1/5 13:52
 */
public abstract class BaseRequestHandler implements RequestHandler {

    protected RequestHandler next;

    public void next(RequestHandler next) {
        this.next = next;
    }
}
