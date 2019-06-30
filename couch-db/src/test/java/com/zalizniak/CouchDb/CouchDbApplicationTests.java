package com.zalizniak.CouchDb;

import junit.framework.TestCase;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CouchDbApplicationTests {

    private CouchDbConnector db;

    @Before
    public void setUp() throws MalformedURLException {
        HttpClient httpClient = new StdHttpClient.Builder()
                .url("http://localhost:5984")
                .username("admin")
                .password("admin")
                .build();

        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);

        db = dbInstance.createConnector("baseball", true);
        db.createDatabaseIfNotExists();
    }

    @Test
    public void basic() {

        Sofa sofa = new Sofa();
        String uuid = UUID.randomUUID().toString();
        sofa.setId(uuid);
        sofa.setColor("red");

        db.create(sofa);

        TestCase.assertEquals("red", sofa.getColor());

        Sofa sofaRead = db.get(Sofa.class, uuid);
        sofaRead.setColor("blue");
        db.update(sofaRead);
    }

    @Test
    public void repository() {
        SofaRepository repo = new SofaRepository(db);

        Sofa sofa = new Sofa();
        String uuid = UUID.randomUUID().toString();
        sofa.setId(uuid);
        sofa.setColor("green");

        repo.add(sofa);

        // TODO why if uncommented - next line throws NullPointerException
        // java.lang.NullPointerException
        //	at java.io.FilterInputStream.read(FilterInputStream.java:133)
        //	at com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper.ensureLoaded(ByteSourceJsonBootstrapper.java:522)
        //	at com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper.detectEncoding(ByteSourceJsonBootstrapper.java:129)
        //	at com.fasterxml.jackson.core.json.ByteSourceJsonBootstrapper.constructParser(ByteSourceJsonBootstrapper.java:246)
        //	at com.fasterxml.jackson.core.JsonFactory._createParser(JsonFactory.java:1315)
        //	at com.fasterxml.jackson.core.JsonFactory.createParser(JsonFactory.java:820)
        //	at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3070)
        //	at org.ektorp.impl.StdCouchDbConnector$5.success(StdCouchDbConnector.java:244)
        //	at org.ektorp.http.RestTemplate.handleResponse(RestTemplate.java:126)
        //	at org.ektorp.http.RestTemplate.get(RestTemplate.java:22)
        //	at org.ektorp.impl.StdCouchDbConnector.get(StdCouchDbConnector.java:240)
        //	at org.ektorp.impl.StdCouchDbConnector.get(StdCouchDbConnector.java:231)
        //	at org.ektorp.support.CouchDbRepositorySupport.get(CouchDbRepositorySupport.java:148)
        //	at com.zalizniak.CouchDb.CouchDbApplicationTests.repository(CouchDbApplicationTests.java:70)
        //	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        //	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        //	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        //	at java.lang.reflect.Method.invoke(Method.java:498)
        //	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
        //	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
        //	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
        //	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
        // Assert.assertTrue(repo.contains(uuid));

        Sofa sofaRead = repo.get(uuid);

        sofaRead.setColor("white");
        repo.update(sofaRead);

        repo.remove(sofaRead);

        List<Sofa> sofas = repo.getAll();
        System.out.println("sofas: " + sofas.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void duplication() {
        SofaRepository repo = new SofaRepository(db);

        Sofa sofa = new Sofa();
        String uuid = UUID.randomUUID().toString();
        sofa.setId(uuid);
        sofa.setColor("green");

        repo.add(sofa);
        repo.add(sofa); // Throws
    }
}
