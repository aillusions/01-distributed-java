package com.zalizniak.redis.leader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.integration.leader.Context;
import org.springframework.integration.leader.event.OnGrantedEvent;
import org.springframework.integration.leader.event.OnRevokedEvent;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class LeadershipRegistrar {

    private Integer maxLeaderRoles = 2;

    private final List<Context> leaderContextRoles = Collections.synchronizedList(new LinkedList<>());

    @EventListener(OnGrantedEvent.class)
    public synchronized void start(OnGrantedEvent evt) {
        Context ctx = evt.getContext();
        leaderContextRoles.add(ctx);
        log.info("Leader election: onGranted for role: " + ctx.getRole() + ", leaderContextRoles: " + leaderContextRoles.size() + " of max " + maxLeaderRoles);
    }

    @EventListener(OnRevokedEvent.class)
    public synchronized void stop(OnRevokedEvent evt) {
        Context ctx = evt.getContext();
        leaderContextRoles.remove(ctx);
        log.info("Leader election: onRevoked for role: " + ctx.getRole() + ", leaderContextRoles: " + leaderContextRoles.size() + " of max " + maxLeaderRoles);
    }

    //@Scheduled(fixedDelay = 5 * 1000)
    public synchronized void run() {
        if (leaderContextRoles.size() > maxLeaderRoles) {

            for (int i = leaderContextRoles.size() - 1; i > -1; i--) {
                Context ctx = leaderContextRoles.get(i);
                if (!StringUtils.equalsIgnoreCase(ctx.getRole(), RedisElectionConfig.COORDINATOR_LEADER_ROLE)) {
                    ctx.yield();
                    log.info("Leader election: giving up on " + leaderContextRoles.size() + "-th role: " + ctx.getRole() + " with max " + maxLeaderRoles);
                    break;
                }
            }
        }
    }

    /**
     *
     */
    public synchronized boolean hasLeaderRole(String leaderRole) {
        for (Context ctx : leaderContextRoles) {
            if (StringUtils.equalsIgnoreCase(ctx.getRole(), leaderRole)) {
                return true;
            }
        }

        return false;
    }

}
