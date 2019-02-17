package com.zalizniak.awsdynamodbsdk;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Slf4j
@Configuration
public class DynamoDbConf {

    public static final String tableName = "Movies";

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.dynamodb.region}")
    private String amazonDynamoDBRegion;


    @Bean()
    @Resource(name = "amazonDynamoDB")
    public DynamoDB dynamoDB(AmazonDynamoDB amazonDynamoDB) {
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        return dynamoDB;
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {

        if (StringUtils.isNotBlank(amazonDynamoDBEndpoint)) {

            // accesses a specific endpoint:
            return AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, amazonDynamoDBRegion))
                    .build();
        } else {

            // use the Amazon DynamoDB web service endpoint in region
            return AmazonDynamoDBClientBuilder.standard()
                    .withRegion(amazonDynamoDBRegion)
                    .build();
        }
    }
}
