package com.zalizniak.couchbasespringdata;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
public class Config extends AbstractCouchbaseConfiguration {

    @Override
    protected List<String> getBootstrapHosts() {
        return Collections.singletonList("127.0.0.1");
    }

    @Override
    protected String getBucketName() {
        return "users";
    }

    @Override
    protected String getUsername() {
        return "db-user";
    }

    @Override
    protected String getBucketPassword() {
        return "db-user-password";
    }
}