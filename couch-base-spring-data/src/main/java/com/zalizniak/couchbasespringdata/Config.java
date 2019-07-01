package com.zalizniak.couchbasespringdata;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.auditing.EnableCouchbaseAuditing;
import org.springframework.data.couchbase.repository.support.IndexManager;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collections;
import java.util.List;

@EnableCouchbaseAuditing //this activates auditing
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

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

   /* @Bean
    public ValidatingCouchbaseEventListener validationEventListener() {
        return new ValidatingCouchbaseEventListener(validator());
    }
    */

    @Bean
    public NaiveAuditorAware testAuditorAware() {
        return new NaiveAuditorAware();
    }

    @Override
    public IndexManager indexManager() {
        return new IndexManager(true, true, true);
    }
}