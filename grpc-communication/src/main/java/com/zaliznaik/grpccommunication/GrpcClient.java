package com.zaliznaik.grpccommunication;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.baeldung.grpc.HelloRequest;
import org.baeldung.grpc.HelloResponse;
import org.baeldung.grpc.HelloServiceGrpc;
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

        serviceBlockingStub = HelloServiceGrpc.newBlockingStub(channel);
    }

    public String sayHello(String firstName, String lastName) {

        HelloResponse helloResponse = serviceBlockingStub.hello(HelloRequest.newBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .build());

        log.info("Response received from server:\n" + helloResponse);

        //channel.shutdown();

        return helloResponse.getGreeting();
    }
}