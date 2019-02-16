package com.zalizniak.awsdynamodbdao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
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
    public void shouldCreateUser() {
        User newUser = new User(UUID.randomUUID().toString(), "James", "Gosling");
        User savedUser = repository.save(newUser);

        Assert.assertEquals(newUser, savedUser);
    }

    @Test
    public void shouldFindUser() {
        User hoeller = new User(UUID.randomUUID().toString(), "Juergen", "Hoeller");
        repository.save(hoeller);

        Page<User> resultPage = repository.findByFirstName("Juergen", PageRequest.of(0, 1));
        List<User> result = resultPage.getContent();

        Assert.assertThat(result.size(), Matchers.equalTo(1));
    }

    @Test
    public void shouldFailToFindWithPagerForList() {
        User hoeller1 = new User(UUID.randomUUID().toString(), "Juergen1", "Hoeller");
        repository.save(hoeller1);

        User hoeller2 = new User(UUID.randomUUID().toString(), "Juergen2", "Hoeller");
        repository.save(hoeller2);

        User hoeller3 = new User(UUID.randomUUID().toString(), "Juergen3", "Hoeller");
        repository.save(hoeller3);

        List<User> resultList = repository.findByLastName("Hoeller", PageRequest.of(0, 2));
        Assert.assertThat(resultList.size(), Matchers.greaterThanOrEqualTo(3));
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

