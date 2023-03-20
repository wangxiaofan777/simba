package com.wxf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyDIscardHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        try {
            log.info("收到消息，丢弃如下：");
            while (in.isReadable()) {
                System.out.println(in.readByte());
            }
            System.out.println();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
