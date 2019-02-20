package com.zalizniak.grpcexceptions;

import com.zalizniak.grpcexceptions.error.EchoRequest;
import com.zalizniak.grpcexceptions.error.ErrorServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GrpcExceptionsApplicationTests {

    @Value("${grpc.port}")
    private Integer grpcPort;

    private ErrorServiceGrpc.ErrorServiceBlockingStub stub;

    @Before
    public void before() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", grpcPort)
                .usePlaintext(true)
                .build();

        stub = ErrorServiceGrpc.newBlockingStub(channel);
    }

    @Test()
    public void contextLoads() {

        final EchoRequest request = EchoRequest.getDefaultInstance();

        // Deadline exceeded
        // Server-side can listen to Cancellations
        try {
            stub.withDeadlineAfter(2, TimeUnit.SECONDS).deadlineExceeded(request);
        } catch (StatusRuntimeException e) {
            // Do not use Status.equals(...) - it's not well defined. Compare Code directly.
            if (e.getStatus().getCode() == Status.Code.DEADLINE_EXCEEDED) {
                log.error("Deadline exceeded!", e);
            }
        }

        // Server-side forgot to implement an operation
        try {
            stub.notImplemented(request);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.UNIMPLEMENTED) {
                log.error("Operation not implemented", e);
            }
        }

        // Server-side throw an NPE, but client wouldn't know
        try {
            stub.uncaughtExceptions(request);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.UNKNOWN) {
                log.error("Server threw an exception... Not sure which one!", e);
            }
        }

        // Server-side called observer.onNext(new CustomException())
        try {
            stub.customUnwrapException(request);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.UNKNOWN) {
                log.error("Server threw another exception... Not sure which one!", e);
            }
        }

        // Server-side wrapped the CustomException in a StatusRuntimeException
        try {
            stub.customException(request);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.INTERNAL) {
                log.error(e.getMessage(), e);
            }
        }

        // Server-side automatically wrapped the IllegalArgumentException via an interceptor
        try {
            stub.automaticallyWrappedException(request);
        } catch (StatusRuntimeException e) {
            log.error(e.getMessage(), e);
        }

    }

}
