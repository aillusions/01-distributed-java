package com.zalizniak.couchbasespringdata;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class NaiveAuditorAware implements AuditorAware<String> {

    private String auditor = "auditor-value";

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(auditor);
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }
}