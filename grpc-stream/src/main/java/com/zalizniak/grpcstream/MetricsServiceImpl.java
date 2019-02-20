package com.zalizniak.grpcstream;


import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;

/**
 * Created by rayt on 5/16/16.
 */
@Slf4j
@GRpcService()
public class MetricsServiceImpl extends MetricsServiceGrpc.MetricsServiceImplBase {
    @Override
    public StreamObserver<StreamingExample.Metric> collect(StreamObserver<StreamingExample.Average> responseObserver) {
        return new StreamObserver<StreamingExample.Metric>() {
            private long sum = 0;
            private long count = 0;

            @Override
            public void onNext(StreamingExample.Metric value) {
                log.info("Server: onNext: " + value);
                sum += value.getMetric();
                count++;
            }

            @Override
            public void onError(Throwable t) {
                log.info("Server: onError: ", t);
                responseObserver.onError(t);
            }

            @Override
            public void onCompleted() {
                log.info("Server DONE");
                responseObserver.onNext(StreamingExample.Average.newBuilder()
                        .setVal(sum / count)
                        .build());
            }
        };
    }
}