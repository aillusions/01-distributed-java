package com.zalizniak.grpc.validation;

import com.zalizniak.grpccommunication.HelloRequest;
import com.zalizniak.grpccommunication.HelloResponse;
import com.zalizniak.grpccommunication.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import org.lognet.springboot.grpc.GRpcService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@GRpcService
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void hello(@NotNull @Valid HelloRequest request, StreamObserver<HelloResponse> responseObserver) {

        String greeting = new StringBuilder()
                .append("Hello, ")
                .append(request.getFirstName())
                .append(" ")
                .append(request.getLastName())
                .toString();

        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(greeting)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}