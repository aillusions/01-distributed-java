package com.zalizniak.websocketwithredis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zalizniak.websocketwithredis.config.RedisConfig;
import com.zalizniak.websocketwithredis.model.RedisMessage;
import com.zalizniak.websocketwithredis.model.WebSocketOutboundMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
public class RedisMessageReceiverSender {

    @Autowired
    private WebSocketMsgReceiverSender webSocketMsgReceiverSender;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void onRedisMsgReceived(String redisMessageStr) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        RedisMessage redisMessage = objectMapper.readValue(redisMessageStr, RedisMessage.class);

        WebSocketOutboundMsg outboundMsg = new WebSocketOutboundMsg();
        outboundMsg.setRequestedX(redisMessage.getTransmittedX());
        outboundMsg.setRequestedY(redisMessage.getTransmittedY());
        outboundMsg.setSong(encodedAudio);

        webSocketMsgReceiverSender.doSendWsMessage(outboundMsg);
    }

    public void doSendRedisMessage(RedisMessage redisMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String chatString = objectMapper.writeValueAsString(redisMessage);
        stringRedisTemplate.convertAndSend(RedisConfig.REDIS_MESSAGING_CHANNEL, chatString);
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
