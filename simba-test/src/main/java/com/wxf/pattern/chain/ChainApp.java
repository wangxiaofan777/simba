package com.wxf.pattern.chain;

/**
 * @author WangMaoSong
 * @date 2023/1/5 13:57
 */
public class ChainApp {

    public static void main(String[] args) {
        BaseRequestHandler a = new ARequestHandler();
        BaseRequestHandler b = new BRequestHandler();
        BaseRequestHandler c = new CRequestHandler();
        a.next(b);
        b.next(c);

        a.doHandler("Hello World");

    }
}
