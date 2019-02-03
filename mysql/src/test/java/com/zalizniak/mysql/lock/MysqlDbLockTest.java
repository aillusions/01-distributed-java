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
public class MysqlDbLockTest extends TestCase {

    public static final long ID = 100L;
    @Autowired
    private TempEntryDao dao;

    @Autowired
    private MysqlDbLockWorker1 mysqlDbLockWorker1;

    @Autowired
    private MysqlDbLockWorker2 mysqlDbLockWorker2;

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

        mysqlDbLockWorker1.transactional(ID, logCollector);
        mysqlDbLockWorker2.transactional(ID, logCollector);

        Thread.sleep(4000);

        String expected = "[Worker1 started., Worker1 retrieved entity., Worker2 started., Worker1 saved., Worker1 exited., Worker2 retrieved entity: 123, Worker2 saved., Worker2 exited.]";
        String actual = logCollector.toString();
        log.info("logCollector: " + actual);

        assertEquals(expected, actual);
    }

}
