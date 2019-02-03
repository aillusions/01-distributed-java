package com.zalizniak.activemq.jms;

import com.zalizniak.activemq.jms.model.EnqueuedMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AmqJmsSender {

    public static final String JMS_QUEUE = "dj_activemq_queue";
    @Autowired
    private JmsTemplate jmsTemplate;

    public void enqueueMessage(EnqueuedMessageDto msg) {
        jmsTemplate.convertAndSend(JMS_QUEUE, msg);
    }

    @Scheduled(fixedDelay = (long) (500))
    public void run() {
        enqueueMessage(new EnqueuedMessageDto(System.currentTimeMillis(), DateTime.now()));
    }
}
