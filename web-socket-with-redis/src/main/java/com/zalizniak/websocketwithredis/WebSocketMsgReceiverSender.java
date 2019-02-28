package com.zalizniak.websocketwithredis;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalizniak.websocketwithredis.config.ApplicationProperties;
import com.zalizniak.websocketwithredis.config.RedisConfig;
import com.zalizniak.websocketwithredis.model.ChatMessageDto;
import com.zalizniak.websocketwithredis.model.ObjectPointXY;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;

@Slf4j
@Controller // yes, Controller
public class WebSocketMsgReceiverSender {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/message")
    public void onWsMessageReceived(ObjectPointXY objectPointXY) throws JsonProcessingException {

        log.info("Received WebSocket Message : {}", objectPointXY);

        ChatMessageDto chatMessageDto = new ChatMessageDto(objectPointXY, null);
        ObjectMapper objectMapper = new ObjectMapper();
        String chatString = objectMapper.writeValueAsString(chatMessageDto);

        // Publish Message to Redis Channels
        stringRedisTemplate.convertAndSend(RedisConfig.REDIS_MESSAGING_CHANNEL, chatString);
    }

    @Async
    public void doSendWsMessage(ChatMessageDto message) {
        log.info("In message: " + message.getObjectPointXY().x + " - " + message.getObjectPointXY().y);
        log.info("Sending " + (encodedAudio.length() / 1024) + " KB of data.");
        message.setSong(encodedAudio);
        template.convertAndSend(applicationProperties.getTopic().getMessage(), message);
    }

    private final static String encodedAudio;

    static {

        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BufferedInputStream in = new BufferedInputStream(WebSocketMsgReceiverSender
                    .class
                    .getClassLoader()
                    .getResourceAsStream("NU_DISCO.mp3"));

            int read;
            byte[] buff = new byte[1024];
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
            out.flush();
            byte[] audioBytes = out.toByteArray();
            encodedAudio = Base64.encodeBase64String(audioBytes);
        } catch (Exception e) {
            throw new RuntimeException("Unable to read song file.", e);
        }
    }
}
