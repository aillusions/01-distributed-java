package com.zalizniak.springbootadmin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScheduleService {

    @Scheduled(fixedDelay = 10_000)
    public void run(){
        log.info("Logged message");
    }
}
