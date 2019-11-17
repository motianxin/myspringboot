package com.zhigang.myspringboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @program: Code
 * @Description 一句话描述
 * @Author admin
 * @Date 2019/6/29 23:23
 * @Version 3.2.2
 **/

@Slf4j
@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketServer {

    private static long onLineCount = 0l;

    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    private Session session;

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        for (WebSocketServer item : WebSocketServer.webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    private static synchronized void addCount() {
        WebSocketServer.onLineCount++;
    }

    private static synchronized void subOnlineCount() {
        WebSocketServer.onLineCount--;
    }

    public static synchronized long getOnlineCount() {
        return WebSocketServer.onLineCount;
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        WebSocketServer.webSocketSet.add(this);
        WebSocketServer.addCount();
        WebSocketServer.log.info("this time online count is {}", WebSocketServer.getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        WebSocketServer.webSocketSet.remove(this);  //从set中删除
        WebSocketServer.subOnlineCount();          //在线数减1
        WebSocketServer.log.info("有一连接关闭！当前在线人数为 {}", WebSocketServer.getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        WebSocketServer.log.info("来自客户端的消息:" + message);

        //群发消息
        for (WebSocketServer item : WebSocketServer.webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        WebSocketServer.log.info("onError", error);
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
}
