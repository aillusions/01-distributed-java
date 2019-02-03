package com.zalizniak.redis.leader;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.leader.Context;
import org.springframework.integration.leader.DefaultCandidate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomLeaderCandidate extends DefaultCandidate {

    @Getter
    private boolean leader;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Override
    public void onGranted(Context ctx) {
        super.onGranted(ctx);
        log.info("Leader election: onGranted.");
        leader = true;
        eventPublisher.publishEvent(new LeaderElectionEvent(this, leader));
    }

    @Override
    public void onRevoked(Context ctx) {
        log.info("Leader election: onRevoked.");
        super.onRevoked(ctx);
        leader = false;
        eventPublisher.publishEvent(new LeaderElectionEvent(this, leader));
    }
}
