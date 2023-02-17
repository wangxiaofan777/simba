package com.wxf.pattern.chain;

/**
 * B请求Handler
 *
 * @author WangMaoSong
 * @date 2023/1/5 13:54
 */
public class BRequestHandler extends BaseRequestHandler {

    @Override
    public void doHandler(String msg) {
        System.out.println("B doHandler");
        if (next != null) {
            next.doHandler(msg);
        }
    }
}
