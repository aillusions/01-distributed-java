package com.zalizniak.grpcexceptions;

import io.grpc.ServerInterceptor;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class MyConfig {
    @Bean
    @GRpcGlobalInterceptor
    public ServerInterceptor globalInterceptor() {
        return new CustomInterceptor(Arrays.asList(
                IllegalArgumentException.class
        ));
    }
}
