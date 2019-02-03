package com.zalizniak.activemq.jms;

import com.zalizniak.activemq.jms.model.EnqueuedMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

import javax.jms.Message;

@Slf4j
@Service
public class AmqJmsListener {

    @Autowired
    private MessageConverter jacksonJmsMessageConverter;

    @JmsListener(destination = AmqJmsSender.JMS_QUEUE)
    public void receiveEnqueuedMsg(Message message) {
        try {
            EnqueuedMessageDto msg = (EnqueuedMessageDto) jacksonJmsMessageConverter.fromMessage(message);
            log.info("Received jms message: " + msg);
        } catch (Exception e) {
            log.error("Unable to receiveEnqueuedMsg", e);
        }
    }
}
