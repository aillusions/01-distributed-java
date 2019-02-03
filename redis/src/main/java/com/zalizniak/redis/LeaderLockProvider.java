package com.zalizniak.redis;


import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.SimpleLock;

import java.util.Optional;

public class LeaderLockProvider implements LockProvider {

    private CustomLeaderCandidate candidate;

    public LeaderLockProvider(CustomLeaderCandidate candidate) {
        this.candidate = candidate;
    }

    public Optional<SimpleLock> lock(LockConfiguration lockConfiguration) {
        return candidate.isLeader() ? Optional.of(new LeaderLock()) : Optional.empty();
    }

    private static final class LeaderLock implements SimpleLock {

        public void unlock() {

        }
    }
}
