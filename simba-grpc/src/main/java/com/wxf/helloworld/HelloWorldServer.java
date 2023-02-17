package com.wxf.helloworld;

import com.wxf.helloworld.proto.GreeterGrpc;
import com.wxf.helloworld.proto.HelloReply;
import com.wxf.helloworld.proto.HelloRequest;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * HelloWorld 服务端
 *
 * @author WangMaoSong
 * @date 2022/7/21 13:02
 */
public class HelloWorldServer {
    private Server server;

    public static void main(String[] args) throws IOException {
        new HelloWorldServer().start();
    }


    /**
     * 启动服务
     *
     * @throws IOException e
     */
    public void start() throws IOException {
        server = ServerBuilder.forPort(9999)
                .addService(new GreeterImpl())
                .build()
                .start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                HelloWorldServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

    }

    /**
     * 终止服务
     *
     * @throws InterruptedException e
     */
    private void stop() throws InterruptedException {
        if (server != null) {
            this.server.awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * 接口实现
     */
    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        @Override
        public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
            try {
                String message = String.format("Hello %s", request.getName());
                //
                responseObserver.onNext(HelloReply.newBuilder().setMessage(message).build());
                // 执行完成
                responseObserver.onCompleted();
            } catch (Exception e) {
                // 异常
                responseObserver.onError(e);
            }
        }
    }
}
