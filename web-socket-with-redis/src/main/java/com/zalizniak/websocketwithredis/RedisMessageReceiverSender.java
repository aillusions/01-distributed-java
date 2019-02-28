package com.zalizniak.websocketwithredis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalizniak.websocketwithredis.config.RedisConfig;
import com.zalizniak.websocketwithredis.model.ChatMessageDto;
import com.zalizniak.websocketwithredis.model.ObjectPointXY;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class RedisMessageReceiverSender {

    @Autowired
    private WebSocketMsgReceiverSender webSocketMsgReceiverSender;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void onRedisMsgReceived(String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessageDto chatMessageDto = objectMapper.readValue(message, ChatMessageDto.class);
        log.info("Notification Message Received: " + chatMessageDto);
        webSocketMsgReceiverSender.doSendWsMessage(chatMessageDto);
    }

    public void doSendRedisMessage(ObjectPointXY objectPointXY) throws JsonProcessingException {
        ChatMessageDto chatMessageDto = new ChatMessageDto(objectPointXY, null);
        ObjectMapper objectMapper = new ObjectMapper();
        String chatString = objectMapper.writeValueAsString(chatMessageDto);

        stringRedisTemplate.convertAndSend(RedisConfig.REDIS_MESSAGING_CHANNEL, chatString);
    }
}
