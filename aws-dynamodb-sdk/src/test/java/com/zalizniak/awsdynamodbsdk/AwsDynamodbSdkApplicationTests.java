package com.zalizniak.awsdynamodbsdk;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DynamoDbConf.class})
public class AwsDynamodbSdkApplicationTests {

    @Autowired
    private DynamoDB dynamoDB;
    public static final int YEAR = 2015;
    public static final String TITLE = "The Big New Movie";

    @Test
    public void shouldCreareItem() {

        Table table = dynamoDB.getTable(DynamoDbConf.tableName);

        final Map<String, Object> infoMap = new HashMap<>();
        infoMap.put("plot", "Nothing happens at all.");
        infoMap.put("rating", 0);
        log.info("Adding a new item...");
        PutItemOutcome outcome = table
                .putItem(new Item().withPrimaryKey("year", YEAR, "title", TITLE).withMap("info", infoMap));

        log.info("PutItem succeeded:\n" + outcome.getPutItemResult());
    }

    @Test
    public void shouldGetItem() {
        Table table = dynamoDB.getTable(DynamoDbConf.tableName);

        GetItemSpec spec = new GetItemSpec().withPrimaryKey("year", YEAR, "title", TITLE);

        log.info("Attempting to read the item...");
        Item outcome = table.getItem(spec);
        log.info("GetItem succeeded: " + outcome);
    }

    @Test
    public void shouldUpdateItem() {
        Table table = dynamoDB.getTable(DynamoDbConf.tableName);

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("year", YEAR, "title", TITLE)
                .withUpdateExpression("set info.rating = :r, info.plot=:p, info.actors=:a")
                .withValueMap(new ValueMap().withNumber(":r", 5.5).withString(":p", "Everything happens all at once.")
                        .withList(":a", Arrays.asList("Larry", "Moe", "Curly")))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        log.info("Updating the item...");
        UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        log.info("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
    }

    @Test
    public void shouldIncrementAtomic() {
        Table table = dynamoDB.getTable(DynamoDbConf.tableName);

        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("year", YEAR, "title", TITLE)
                .withUpdateExpression("set info.rating = info.rating + :val")
                .withValueMap(new ValueMap().withNumber(":val", 1)).withReturnValues(ReturnValue.UPDATED_NEW);

        System.out.println("Incrementing an atomic counter...");
        UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
    }

    @Test(expected = ConditionalCheckFailedException.class)
    public void shouldUpdateWithCondition() {
        Table table = dynamoDB.getTable(DynamoDbConf.tableName);

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(new PrimaryKey("year", YEAR, "title", TITLE)).withUpdateExpression("remove info.actors[0]")
                .withConditionExpression("size(info.actors) > :num").withValueMap(new ValueMap().withNumber(":num", 3))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        System.out.println("Attempting a conditional update...");
        UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
    }

    @Test()
    public void shouldRemove() {
        Table table = dynamoDB.getTable(DynamoDbConf.tableName);

        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("year", 2015, "title", "The Big New Movie"));

        System.out.println("Attempting a conditional delete...");
        table.deleteItem(deleteItemSpec);
        System.out.println("DeleteItem succeeded");
    }
}
