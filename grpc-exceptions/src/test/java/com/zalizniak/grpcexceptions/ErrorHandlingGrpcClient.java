package com.zalizniak.grpcexceptions;


import com.zalizniak.grpcexceptions.error.EchoRequest;
import com.zalizniak.grpcexceptions.error.ErrorServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by rayt on 5/16/16.
 */
public class ErrorHandlingGrpcClient {
    private static final Logger logger = Logger.getLogger(ErrorHandlingGrpcClient.class.getName());

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext(true)
                .build();

        final EchoRequest request = EchoRequest.getDefaultInstance();

        ErrorServiceGrpc.ErrorServiceBlockingStub stub = ErrorServiceGrpc.newBlockingStub(channel);

        // Deadline exceeded
        // Server-side can listen to Cancellations
        try {
            stub.withDeadlineAfter(2, TimeUnit.SECONDS).deadlineExceeded(request);
        } catch (StatusRuntimeException e) {
            // Do not use Status.equals(...) - it's not well defined. Compare Code directly.
            if (e.getStatus().getCode() == Status.Code.DEADLINE_EXCEEDED) {
                logger.log(Level.SEVERE, "Deadline exceeded!", e);
            }
        }

        // Server-side forgot to implement an operation
        try {
            stub.notImplemented(request);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.UNIMPLEMENTED) {
                logger.log(Level.SEVERE, "Operation not implemented", e);
            }
        }

        // Server-side throw an NPE, but client wouldn't know
        try {
            stub.uncaughtExceptions(request);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.UNKNOWN) {
                logger.log(Level.SEVERE, "Server threw an exception... Not sure which one!", e);
            }
        }

        // Server-side called observer.onNext(new CustomException())
        try {
            stub.customUnwrapException(request);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.UNKNOWN) {
                logger.log(Level.SEVERE, "Server threw another exception... Not sure which one!", e);
            }
        }

        // Server-side wrapped the CustomException in a StatusRuntimeException
        try {
            stub.customException(request);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.INTERNAL) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }

        // Server-side automatically wrapped the IllegalArgumentException via an interceptor
        try {
            stub.automaticallyWrappedException(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        channel.shutdown();
    }
}