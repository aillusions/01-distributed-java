package com.zalizniak.hashicorpvault;

import org.springframework.boot.context.properties.ConfigurationProperties;

//  /secret/{application}/{profile}
//  /secret/{application}
//  /secret/{default-context}/{profile}
//  /secret/{default-context}
@ConfigurationProperties("example")
public class MyConfiguration {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
