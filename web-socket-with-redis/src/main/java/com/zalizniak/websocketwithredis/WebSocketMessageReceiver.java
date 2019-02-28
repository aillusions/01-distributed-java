package com.zalizniak.websocketwithredis;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalizniak.websocketwithredis.config.RedisConfig;
import com.zalizniak.websocketwithredis.model.ChatMessageDto;
import com.zalizniak.websocketwithredis.model.ObjectPointXY;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller // yes, Controller
public class WebSocketMessageReceiver {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @MessageMapping("/message")
    public void onWsMessageReceived(ObjectPointXY objectPointXY) throws JsonProcessingException {

        log.info("Received WebSocket Message : {}", objectPointXY);

        ChatMessageDto chatMessageDto = new ChatMessageDto(objectPointXY, null);
        ObjectMapper objectMapper = new ObjectMapper();
        String chatString = objectMapper.writeValueAsString(chatMessageDto);

        // Publish Message to Redis Channels
        stringRedisTemplate.convertAndSend(RedisConfig.REDIS_MESSAGING_CHANNEL, chatString);
    }
}
