package com.zalizniak.websocketwithredis;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.zalizniak.websocketwithredis.config.ApplicationProperties;
import com.zalizniak.websocketwithredis.model.RedisMessage;
import com.zalizniak.websocketwithredis.model.WebSocketInboundMsg;
import com.zalizniak.websocketwithredis.model.WebSocketOutboundMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller // yes, Controller
public class WebSocketMsgReceiverSender {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private RedisMessageReceiverSender redisMessageReceiverSender;

    @MessageMapping("/message")
    public void onWsMessageReceived(WebSocketInboundMsg webSocketInboundMsg) throws JsonProcessingException {
        log.info("Received WebSocket Message : {}", webSocketInboundMsg);
        redisMessageReceiverSender.doSendRedisMessage(new RedisMessage(webSocketInboundMsg.getX(), webSocketInboundMsg.getY()));
    }

    public void doSendWsMessage(WebSocketOutboundMsg message) {
        long sizeKb = message.getSong().length() / 1024;
        log.info("Sending: " + sizeKb + " KB of data for x: " + message.getRequestedX() + " y: " + message.getRequestedY());
        template.convertAndSend(applicationProperties.getTopic().getMessage(), message);
    }
}
