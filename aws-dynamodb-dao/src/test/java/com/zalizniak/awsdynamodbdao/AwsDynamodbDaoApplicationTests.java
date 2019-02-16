package com.zalizniak.awsdynamodbdao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

/**
 * -Daws.profile=alex_zalizniak_com_dev
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PropertyPlaceholderAutoConfiguration.class, AmazonDynamoDBConfig.class})
public class AwsDynamodbDaoApplicationTests {

    @Autowired
    private UserRepository repository;

    @Test
    public void sampleTestCase() {
        User gosling = new User(UUID.randomUUID().toString(), "James", "Gosling");
        repository.save(gosling);

        User hoeller = new User(UUID.randomUUID().toString(), "Juergen", "Hoeller");
        repository.save(hoeller);

        Page<User> resultPage = repository.findByLastName("Gosling", PageRequest.of(0, 100));
        List<User> result = resultPage.getContent();

        Assert.assertThat(result.size(), Matchers.greaterThanOrEqualTo(1));
        Assert.assertThat(result, Matchers.hasItem(gosling));
        log.info("Found in table: {}", result.get(0));
    }

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private DynamoDBMapper mapper;

    private boolean tableWasCreatedForTest;

    @Before
    public void init() throws Exception {
        CreateTableRequest ctr = mapper.generateCreateTableRequest(User.class)
                //.withBillingMode(BillingMode.PAY_PER_REQUEST);
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));

        tableWasCreatedForTest = TableUtils.createTableIfNotExists(amazonDynamoDB, ctr);
        if (tableWasCreatedForTest) {
            log.info("Created table {}", ctr.getTableName());
        }
        TableUtils.waitUntilActive(amazonDynamoDB, ctr.getTableName());
        log.info("Table {} is active", ctr.getTableName());
    }

    // @After
    // public void destroy() throws Exception {
    //     if (tableWasCreatedForTest) {
    //         DeleteTableRequest dtr = mapper.generateDeleteTableRequest(User.class);
    //         TableUtils.deleteTableIfExists(amazonDynamoDB, dtr);
    //         log.info("Deleted table {}", dtr.getTableName());
    //     }
    // }

}

