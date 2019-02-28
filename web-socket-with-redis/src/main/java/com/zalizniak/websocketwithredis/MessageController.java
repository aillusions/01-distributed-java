package com.zalizniak.websocketwithredis;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalizniak.websocketwithredis.model.ChatMessageDto;
import com.zalizniak.websocketwithredis.model.ObjectPointXY;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class MessageController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @MessageMapping("/message")
    public void sendWsChatMessage(ObjectPointXY objectPointXY) throws JsonProcessingException {
        log.info("Incoming WebSocket Message : {}", objectPointXY);

        publishMessageToRedis(objectPointXY);
    }

    @PostMapping("/message")
    //@ResponseBody
    public ResponseEntity<Map<String, String>> sendHttpChatHttpMessage(@RequestBody Map<String, String> message) throws JsonProcessingException {
        String httpMessage = message.get("message");
        log.info("Incoming HTTP Message : {}", httpMessage);
        //publishMessageToRedis(httpMessage);

        Map<String, String> response = new HashMap<>();
        response.put("response", "Message Sent Successfully over HTTP");

        return ResponseEntity.ok(response);
    }

    private void publishMessageToRedis(ObjectPointXY objectPointXY) throws JsonProcessingException {

        ChatMessageDto chatMessageDto = new ChatMessageDto(objectPointXY, null);
        ObjectMapper objectMapper = new ObjectMapper();
        String chatString = objectMapper.writeValueAsString(chatMessageDto);

        // Publish Message to Redis Channels
        stringRedisTemplate.convertAndSend("chat", chatString);
    }
}
