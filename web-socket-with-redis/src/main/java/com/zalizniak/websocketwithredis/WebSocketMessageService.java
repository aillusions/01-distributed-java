package com.zalizniak.websocketwithredis;

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
public class WebSocketMessageService {

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
    public void sendChatMessage(ChatMessageDto message) throws IOException {
        log.info("message: " + message.getObjectPointXY().x + " - " + message.getObjectPointXY().y);
        message.setSong(encodedAudio);
        template.convertAndSend(applicationProperties.getTopic().getMessage(), message);
    }

}
