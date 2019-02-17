package com.zalizniak.awsdynamodbsdk;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.*;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
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
import java.util.Iterator;
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
    public void shouldCreateItem() {

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

        log.info("Incrementing an atomic counter...");
        UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        log.info("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
    }

    @Test(expected = ConditionalCheckFailedException.class)
    public void shouldUpdateWithCondition() {
        Table table = dynamoDB.getTable(DynamoDbConf.tableName);

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(new PrimaryKey("year", YEAR, "title", TITLE)).withUpdateExpression("remove info.actors[0]")
                .withConditionExpression("size(info.actors) > :num").withValueMap(new ValueMap().withNumber(":num", 3))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        log.info("Attempting a conditional update...");
        UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
        log.info("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
    }

    @Test()
    public void shouldRemove() {
        Table table = dynamoDB.getTable(DynamoDbConf.tableName);

        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("year", 2015, "title", "The Big New Movie"));

        log.info("Attempting a conditional delete...");
        table.deleteItem(deleteItemSpec);
        log.info("DeleteItem succeeded");
    }

    @Test()
    public void shouldQuery() {
        Table table = dynamoDB.getTable(DynamoDbConf.tableName);

        HashMap<String, String> nameMap = new HashMap<String, String>();
        nameMap.put("#yr", "year");

        HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":yyyy", 1985);

        QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#yr = :yyyy").withNameMap(nameMap)
                .withValueMap(valueMap);

        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        try {
            log.info("Movies from 1985");
            items = table.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                log.info(item.getNumber("year") + ": " + item.getString("title"));
            }
        } catch (Exception e) {
            System.err.println("Unable to query movies from 1985");
            System.err.println(e.getMessage());
        }

        valueMap.put(":yyyy", 1992);
        valueMap.put(":letter1", "A");
        valueMap.put(":letter2", "L");

        querySpec.withProjectionExpression("#yr, title, info.genres, info.actors[0]")
                .withKeyConditionExpression("#yr = :yyyy and title between :letter1 and :letter2").withNameMap(nameMap)
                .withValueMap(valueMap);

        log.info("Movies from 1992 - titles A-L, with genres and lead actor");
        items = table.query(querySpec);

        iterator = items.iterator();
        while (iterator.hasNext()) {
            item = iterator.next();
            log.info(item.getNumber("year") + ": " + item.getString("title") + " " + item.getMap("info"));
        }
    }

    @Test()
    public void shouldScan() {
        Table table = dynamoDB.getTable(DynamoDbConf.tableName);

        ScanSpec scanSpec = new ScanSpec().withProjectionExpression("#yr, title, info.rating")
                .withFilterExpression("#yr between :start_yr and :end_yr").withNameMap(new NameMap().with("#yr", "year"))
                .withValueMap(new ValueMap().withNumber(":start_yr", 1950).withNumber(":end_yr", 1959));

        ItemCollection<ScanOutcome> items = table.scan(scanSpec);

        Iterator<Item> iter = items.iterator();
        while (iter.hasNext()) {
            Item item = iter.next();
            log.info(item.toString());
        }
    }
}
