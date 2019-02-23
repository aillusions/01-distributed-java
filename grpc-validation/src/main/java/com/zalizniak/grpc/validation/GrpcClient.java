package com.zalizniak.grpc.validation;

import com.zalizniak.grpccommunication.HelloRequest;
import com.zalizniak.grpccommunication.HelloResponse;
import com.zalizniak.grpccommunication.HelloServiceGrpc;
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

        serviceBlockingStub = HelloServiceGrpc.newBlockingStub(channel);
    }

    public String sayHello(String firstName, String lastName) {

        HelloRequest request = HelloRequest.newBuilder()
                .setFirstName(firstName)
                .setLastName(lastName)
                .build();

        HelloResponse helloResponse = serviceBlockingStub.hello(request);

        log.debug("Response received from server: " + helloResponse);

        //channel.shutdown();

        return helloResponse.getGreeting();
    }
}