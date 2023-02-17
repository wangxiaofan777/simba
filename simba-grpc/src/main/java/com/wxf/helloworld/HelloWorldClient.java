package com.wxf.helloworld;

import com.wxf.helloworld.proto.GreeterGrpc;
import com.wxf.helloworld.proto.HelloReply;
import com.wxf.helloworld.proto.HelloRequest;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * HelloWorld 客户端
 *
 * @author WangMaoSong
 * @date 2022/7/21 13:52
 */
public class HelloWorldClient {

    private static final Logger LOGGER = Logger.getLogger(HelloWorldClient.class.getName());

    private GreeterGrpc.GreeterBlockingStub blockingStub;

    public HelloWorldClient(Channel channel) {
        this.blockingStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void greet(String name) {
        LOGGER.info("receive greet from " + name + " ...");
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        try {
            HelloReply helloReply = this.blockingStub.sayHello(request);
            System.out.println(helloReply);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "RPC failed : {0}", e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:9999")
                .usePlaintext()
                .build();
        try {
            HelloWorldClient client = new HelloWorldClient(channel);
            client.greet("wms");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }


    }


}
