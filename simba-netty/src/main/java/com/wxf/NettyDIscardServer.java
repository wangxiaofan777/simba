package com.wxf;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyDIscardServer {

    private final int serverPort;

    ServerBootstrap b = new ServerBootstrap();

    public NettyDIscardServer(int serverPort) {
        this.serverPort = serverPort;
    }

    public void runServer() {
        // 创建反应器轮询组
        NioEventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerLoopGroup = new NioEventLoopGroup();


        try {
            // 1. 设置反应轮询组
            b.group(bossLoopGroup, workerLoopGroup);

            // 2.设置NIO通道类型
            b.channel(NioServerSocketChannel.class);
            // 3. 设置监听端口
            b.localAddress(serverPort);
            // 4. 设置通道参数
            b.option(ChannelOption.SO_KEEPALIVE, true);
            // 5. 装配子通道流水线
            b.childHandler(new ChannelInitializer<SocketChannel>() {

                // 有链接时会创建一个通道
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // 流水线的职责：负责管理通道中的处理器
                    // 向“子通道”（传输通道）流水线添加一个处理器
                    ch.pipeline().addLast(new NettyDIscardHandler());
                }
            });

            // 6. 开始绑定服务器
            // 通过调用sync同步方法阻塞知道绑定成功
            ChannelFuture channelFuture = b.bind().sync();

            log.info("服务链接成功，监听端口：" + channelFuture.channel().localAddress());

            // 7. 等待通道关闭的异步任务结束
            // 服务监听通道会一直等到通道关闭的异步任务结束
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 8. 优雅关闭EventLoopGroup 释放掉所有资源，包括创建的线程
            workerLoopGroup.shutdownGracefully();
            bossLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyDIscardServer nettyDIscardServer = new NettyDIscardServer(9999);
        nettyDIscardServer.runServer();
    }
}
