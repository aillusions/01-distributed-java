package com.zalizniak.awsdynamodbsdk;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Iterator;

@Configuration
@SpringBootApplication
public class AwsDynamodbSdkApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsDynamodbSdkApplication.class, args);
    }

    @Bean
    @Resource(name = "dynamoDB")
    CommandLineRunner commandLineRunner2(DynamoDB dynamoDB) {
        return args -> {

            Table table = dynamoDB.getTable(DynamoDbConf.tableName);

            JsonParser parser = new JsonFactory().createParser(DynamoDbConf.class.getClassLoader().getResourceAsStream("moviedata.json"));

            JsonNode rootNode = new ObjectMapper().readTree(parser);
            Iterator<JsonNode> iter = rootNode.iterator();

            ObjectNode currentNode;

            while (iter.hasNext()) {
                currentNode = (ObjectNode) iter.next();

                int year = currentNode.path("year").asInt();
                String title = currentNode.path("title").asText();

                try {
                    table.putItem(new Item().withPrimaryKey("year", year, "title", title).withJSON("info",
                            currentNode.path("info").toString()));
                    System.out.println("PutItem succeeded: " + year + " " + title);

                } catch (Exception e) {
                    System.err.println("Unable to add movie: " + year + " " + title);
                    System.err.println(e.getMessage());
                    break;
                }
            }
            parser.close();
        };
    }

    @Bean
    @Resource(name = "dynamoDB")
    CommandLineRunner commandLineRunner1(DynamoDB dynamoDB) {
        return args -> {
            String tableName = "Movies";

            try {
                System.out.println("Attempting to create table; please wait...");
                Table table = dynamoDB.createTable(tableName,
                        Arrays.asList(new KeySchemaElement("year", KeyType.HASH), // Partition
                                new KeySchemaElement("title", KeyType.RANGE)), // Sort key
                        Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
                                new AttributeDefinition("title", ScalarAttributeType.S)),
                        new ProvisionedThroughput(10L, 10L));
                table.waitForActive();
                System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

            } catch (Exception e) {
                System.err.println("Unable to create table: ");
                System.err.println(e.getMessage());
            }
        };
    }
}
