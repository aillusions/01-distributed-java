package com.zalizniak.CouchDb;

import junit.framework.TestCase;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CouchDbApplicationTests {

    @Test
    public void contextLoads() throws MalformedURLException {

        HttpClient httpClient = new StdHttpClient.Builder()
                .url("http://localhost:5984")
                .username("admin")
                .password("admin")
                .build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);

        CouchDbConnector db = dbInstance.createConnector("baseball", true);
        db.createDatabaseIfNotExists();

        Sofa sofa = new Sofa();
        String uuid = UUID.randomUUID().toString();
        sofa.setId(uuid);
        sofa.setColor("red");

        db.create(sofa);


        Sofa sofaRead = db.get(Sofa.class, uuid);

        TestCase.assertEquals("red", sofa.getColor());
    }

}
