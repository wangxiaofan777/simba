package com.wxf.pattern.chain;

/**
 * C请求Handler
 *
 * @author WangMaoSong
 * @date 2023/1/5 13:54
 */
public class CRequestHandler extends BaseRequestHandler {

    @Override
    public void doHandler(String msg) {
        System.out.println("C doHandler");
        if (next != null) {
            next.doHandler(msg);
        }
    }
}
