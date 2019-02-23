package com.zalizniak.grpccommunication;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class GrpcClient {

    @Value("${grpc.port:6565}")
    private Integer grpcPort;

    private HelloServiceGrpc.HelloServiceBlockingStub serviceBlockingStub;

    @PostConstruct
    private void init() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", grpcPort)
                .usePlaintext()
                .build();

        serviceBlockingStub = com.zalizniak.grpccommunication.HelloServiceGrpc.newBlockingStub(channel);
    }

    public String sayHello(String firstName, String lastName) {

        com.zalizniak.grpccommunication.HelloResponse helloResponse = serviceBlockingStub.hello(HelloRequest.newBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .build());

        log.debug("Response received from server: " + helloResponse);

        //channel.shutdown();

        return helloResponse.getGreeting();
    }
}