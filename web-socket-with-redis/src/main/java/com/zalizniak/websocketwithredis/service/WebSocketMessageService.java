package com.zalizniak.websocketwithredis.service;

import com.zalizniak.websocketwithredis.config.ApplicationProperties;
import com.zalizniak.websocketwithredis.config.MyListener;
import com.zalizniak.websocketwithredis.model.ChatMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;


@Slf4j
@Service
public class WebSocketMessageService {

    private final ApplicationProperties applicationProperties;
    private final SimpMessagingTemplate template;

    @Autowired
    MyListener myListener;

    public WebSocketMessageService(ApplicationProperties applicationProperties, SimpMessagingTemplate template) {
        this.applicationProperties = applicationProperties;
        this.template = template;
    }


    @Async
    public void sendChatMessage(ChatMessageDto message) throws IOException {
        //message.setSong(Arrays.copyOfRange(bytes, 0, 100_000));
        long folderName = message.getObjectPointXY().x + message.getObjectPointXY().y;
        message.setSong(convertAudioToBase64String(folderName));

        template.convertAndSend(applicationProperties.getTopic().getMessage(), message);
    }

    public String convertAudioToBase64String(long folderName) throws IOException {
        Map<Integer, String> map = myListener.getMap();
        //String songUrl = "d:\\media\\" +  "001s0N8QyZTd.128.mp3" + "\\10.mp3";// map.get((int) (long) folderName)  + "\\10.mp3";
        //String songUrl = "src\\main\\resources\\static\\NU_DISCO.mp3";


        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream in = new BufferedInputStream(/*new FileInputStream(songUrl)*/WebSocketMessageService.class.getClassLoader().getResourceAsStream("NU_DISCO.mp3"));

        int read;
        byte[] buff = new byte[1024];
        while ((read = in.read(buff)) > 0) {
            out.write(buff, 0, read);
        }
        out.flush();
        byte[] audioBytes = out.toByteArray();
        String encodedAudio = Base64.encodeBase64String(audioBytes);

        //File file = new File("src\\main\\resources\\static\\NU_DISCO.mp3");
        return encodedAudio;
    }
}
