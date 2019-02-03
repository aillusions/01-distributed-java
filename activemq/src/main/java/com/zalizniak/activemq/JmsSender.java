package com.zalizniak.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JmsSender {

    public static final String JMS_QUEUE = "dj_activemq_queue";
    @Autowired
    private JmsTemplate jmsTemplate;

    public void enqueueMessage(EnqueuedMessageDto msg) {
        jmsTemplate.convertAndSend(JMS_QUEUE, msg);
    }
}
