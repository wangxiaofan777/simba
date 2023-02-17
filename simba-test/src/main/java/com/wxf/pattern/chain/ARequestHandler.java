package com.wxf.pattern.chain;

/**
 * A请求Handler
 *
 * @author WangMaoSong
 * @date 2023/1/5 13:54
 */
public class ARequestHandler extends BaseRequestHandler {

    @Override
    public void doHandler(String msg) {
        System.out.println("A doHandler");
        if (next != null) {
            next.doHandler(msg);
        }
    }
}
