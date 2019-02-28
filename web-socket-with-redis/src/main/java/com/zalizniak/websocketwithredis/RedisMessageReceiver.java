package com.zalizniak.websocketwithredis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalizniak.websocketwithredis.config.ApplicationProperties;
import com.zalizniak.websocketwithredis.model.ChatMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
public class RedisMessageReceiver {

    private final WebSocketMessageService webSocketMessageService;

    public RedisMessageReceiver(WebSocketMessageService webSocketMessageService) {
        this.webSocketMessageService = webSocketMessageService;
    }

    // Invoked when message is publish to "chat" channel
    public void receiveChatMessage(String message) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ChatMessageDto chatMessageDto = objectMapper.readValue(message, ChatMessageDto.class);
        log.info("Notification Message Received: " + chatMessageDto);
        webSocketMessageService.sendChatMessage(chatMessageDto);
    }

    @Slf4j
    @Service
    public static class WebSocketMessageService {

        private final ApplicationProperties applicationProperties;
        private final SimpMessagingTemplate template;

        private final String encodedAudio;

        public WebSocketMessageService(ApplicationProperties applicationProperties, SimpMessagingTemplate template) {
            this.applicationProperties = applicationProperties;
            this.template = template;

            try {

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                BufferedInputStream in = new BufferedInputStream(WebSocketMessageService
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

        @Async
        public void sendChatMessage(ChatMessageDto message) {
            log.info("In message: " + message.getObjectPointXY().x + " - " + message.getObjectPointXY().y);
            log.info("Sending " + (encodedAudio.length() / 1024) + " KB of data.");
            message.setSong(encodedAudio);
            template.convertAndSend(applicationProperties.getTopic().getMessage(), message);
        }

    }

}
