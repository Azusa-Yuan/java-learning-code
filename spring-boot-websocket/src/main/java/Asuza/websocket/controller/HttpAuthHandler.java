package Asuza.websocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author Azusa-Yuan
 * @description websocket 处理器
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */

@Component
@Slf4j
public class HttpAuthHandler extends TextWebSocketHandler {

    /**
     * socket 建立连接成功
     * @param session websocket session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("成功建立了连接");
    }

    /**
     * socket 接收消息
     * @param session websocket session
     * @param message TextMessage
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("接收到消息：{}", payload);
    }
}
