package com.zalizniak.sonarcodequality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class SonarCodeQualityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SonarCodeQualityApplication.class, args);
    }

    @Bean
    public String someBean() {
        int a = 1;

        if (false) {
            a = 1 / 0;
            throw new RuntimeException();
        }

        return null;
    }

}
