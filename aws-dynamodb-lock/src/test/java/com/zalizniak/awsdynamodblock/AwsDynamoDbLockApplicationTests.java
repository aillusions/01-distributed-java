package com.zalizniak.awsdynamodblock;

import com.amazonaws.services.dynamodbv2.AcquireLockOptions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBLockClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBLockClientOptions;
import com.amazonaws.services.dynamodbv2.LockItem;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AwsDynamoDbLockApplicationTests {

    @Test
    public void contextLoads() throws IOException, InterruptedException {

        // Inject client configuration to the builder like the endpoint and signing region
        final DynamoDbClient dynamoDB = DynamoDbClient.builder()
                .region(Region.US_EAST_1).endpointOverride(URI.create("http://localhost:8000"))
                .build();
        // Whether or not to create a heartbeating background thread
        final boolean createHeartbeatBackgroundThread = true;
        //build the lock client
        final AmazonDynamoDBLockClient client = new AmazonDynamoDBLockClient(
                AmazonDynamoDBLockClientOptions.builder(dynamoDB, "lockTable")
                        .withTimeUnit(TimeUnit.SECONDS)
                        .withLeaseDuration(10L)
                        .withHeartbeatPeriod(3L)
                        .withCreateHeartbeatBackgroundThread(createHeartbeatBackgroundThread)
                        .build());
        //try to acquire a lock on the partition key "Moe"
        final Optional<LockItem> lockItem =
                client.tryAcquireLock(AcquireLockOptions.builder("Moe").build());
        if (lockItem.isPresent()) {
            log.info("Acquired lock! If I die, my lock will expire in 10 seconds.");
            log.info("Otherwise, I will hold it until I stop heartbeating.");
            client.releaseLock(lockItem.get());
        } else {
            log.info("Failed to acquire lock!");
        }
        client.close();
    }

}

