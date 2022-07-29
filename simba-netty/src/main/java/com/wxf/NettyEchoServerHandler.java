package com.wxf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author WangMaoSong
 * @date 2022/7/27 14:50
 */

// @ChannelHandler.Sharable：标注一个Handler实例可以呗多个通道安全的共享,如果
@ChannelHandler.Sharable
public class NettyEchoServerHandler extends ChannelInboundHandlerAdapter {

    public static final NettyEchoServerHandler INSTANCE = new NettyEchoServerHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf in = (ByteBuf) msg;
        System.out.println(in.hasArray() ? "堆内存" : "直接内存");
        int len = in.readableBytes();
        byte[] arr = new byte[len];
        in.getBytes(0, arr);

        System.out.println("server received: " + new String(arr, StandardCharsets.UTF_8));

        System.out.println("before return: " + in.refCnt());

        // 写回数据，异步任务
        ChannelFuture f = ctx.writeAndFlush(msg);
        f.addListener((ChannelFutureListener) channelFuture -> {
            System.out.println("fater return data: " + in.refCnt());
        });


    }
}
