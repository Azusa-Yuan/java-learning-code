package Asuza.websocket.config;

import Asuza.websocket.controller.HttpAuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Azusa-Yuan
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */

@Configuration
@EnableWebSocket
public class websocketConfig implements WebSocketConfigurer {

    @Autowired
    HttpAuthHandler httpAuthHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(httpAuthHandler, "ws/")
                .setAllowedOrigins("*");
    }
}
