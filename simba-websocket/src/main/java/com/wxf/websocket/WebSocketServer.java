package com.wxf.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocket服务配置
 *
 * @author WangMaoSong
 * @date 2022/6/21 11:59
 */
@Slf4j
@Component
@ServerEndpoint("/api/websocket/{uid}")
public class WebSocketServer {

    /**
     * 在线数
     */
    private AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * 服务端Session，用于跟客户端发送消息
     */
    private Session session;

    /**
     * 接收用户ID
     */
    private String uid;

    private final CopyOnWriteArraySet<WebSocketServer> webSocketServers = new CopyOnWriteArraySet<>();


    /**
     * 创建连接
     *
     * @param session 会话
     * @param uid     用户ID
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("uid") String uid) {
        this.session = session;
        this.uid = uid;
        webSocketServers.add(this);
        onlineCount.addAndGet(1);
        sendMessage("connected server successfully");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("receive message {} from uid {}", message, uid);
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("send message {} to uid {} error ", message, uid, e);
        }
    }

    @OnClose
    public void onClose() {
        webSocketServers.remove(this);
        onlineCount.decrementAndGet();

        log.info("disconnected uid: {}", uid);

        log.info("current online person number: {}", onlineCount.get());
    }


    @OnError
    public void OnError(Session session, Throwable throwable) {
        log.error("on error", throwable);
    }

    /**
     * 发送消息
     *
     * @param message 消息
     */
    private void sendMessage(String message) {
        log.info("send message: {}", message);
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("send message {} error", message, e);
        }
    }

    /**
     * 群发消息
     *
     * @param message 消息
     */
    public void sendMessages(String message, @PathParam("uid") String uid) {
        this.webSocketServers.forEach(webSocketServer -> {
            if (webSocketServer.uid.equals(uid)) {
                webSocketServer.sendMessage(message);
            }
        });
    }

}
