package com.zalizniak.websocketwithredis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalizniak.websocketwithredis.model.ChatMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class RedisMessageReceiver {

    @Autowired
    private WebSocketMsgReceiverSender webSocketMsgReceiverSender;

    // Invoked when message is publish to "dj-ws-messaging-channel" channel
    public void receiveChatMessage(String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessageDto chatMessageDto = objectMapper.readValue(message, ChatMessageDto.class);
        log.info("Notification Message Received: " + chatMessageDto);
        webSocketMsgReceiverSender.doSendWsMessage(chatMessageDto);
    }
}
