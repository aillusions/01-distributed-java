package com.zalizniak.grpcstream;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rx.Observable;

import java.util.stream.Stream;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GrpcStreamApplicationTests {


    public static Observable<StreamingExample.Average> collect(MetricsServiceGrpc.MetricsServiceStub stub,
                                                               Observable<StreamingExample.Metric> metrics) {
        return Observable.create(new AverageOnSubscribe(metrics, stub));
    }

    @Test
    public void contextLoads() throws InterruptedException {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext(true).build();
        MetricsServiceGrpc.MetricsServiceStub stub = MetricsServiceGrpc.newStub(channel);

        //
        //
        //

        rx.Observable<Long> metrics = rx.Observable.from(new Long[]{1L, 2L, 3L, 4L, 5L, 6L, 7L});

        collect(stub, metrics
                .map(l -> StreamingExample.Metric.newBuilder().setMetric(l).build()))
                .subscribe(avg -> {
                    log.info("Client RX: " + avg);
                });

        //
        //
        //

        StreamObserver<StreamingExample.Metric> collect = stub.collect(new StreamObserver<StreamingExample.Average>() {
            @Override
            public void onNext(StreamingExample.Average value) {
                log.info("Client Average: " + value.getVal());
            }

            @Override
            public void onError(Throwable t) {
                log.error("Client Error: ", t);
            }

            @Override
            public void onCompleted() {
                log.info("Client DONE");
            }
        });

        Stream.of(1L, 2L, 3L, 4L).map(l -> StreamingExample.Metric.newBuilder().setMetric(l).build())
                .forEach(collect::onNext);
        collect.onCompleted();

        Thread.sleep(2000);
        //channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

}
