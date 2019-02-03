package com.zalizniak.mysql.lock;

import com.zalizniak.mysql.dao.TempEntry;
import com.zalizniak.mysql.dao.TempEntryDao;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DbLockTest extends TestCase {

    public static final long ID = 100L;
    @Autowired
    private TempEntryDao dao;

    @Autowired
    private DbLockWorker1 dbLockWorker1;

    @Autowired
    private DbLockWorker2 dbLockWorker2;

    @Test
    public void testLock() throws InterruptedException {

        if (!dao.findById(ID).isPresent()) {
            TempEntry newEntry = new TempEntry();
            newEntry.setTitle("");
            newEntry.setId(ID);
            newEntry.setDateCreated(new Date());
            dao.save(newEntry);
        }

        List<String> logCollector = Collections.synchronizedList(new LinkedList<>());

        dbLockWorker1.transactional(ID, logCollector);
        dbLockWorker2.transactional(ID, logCollector);

        Thread.sleep(4000);

        String expected = "[Worker1 started., Worker1 retrieved entity., Worker2 started., Worker1 saved., Worker1 exited., Worker2 retrieved entity: 123, Worker2 saved., Worker2 exited.]";
        log.info("logCollector: " + logCollector.toString());

        assertEquals(expected, logCollector.toString());
    }

}
