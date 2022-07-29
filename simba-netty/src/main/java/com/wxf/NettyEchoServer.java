package com.wxf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;

/**
 * @author WangMaoSong
 * @date 2022/7/27 14:31
 */
public class NettyEchoServer {

    public void runServer() {
        NioEventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();

        // 反应轮询组
        b.group(bossLoopGroup, workerLoopGroup);
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(NettyEchoServerHandler.INSTANCE);
            }
        });

        b.clone();
        bossLoopGroup.shutdownGracefully();
        workerLoopGroup.shutdownGracefully();


    }
}
