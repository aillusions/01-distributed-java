package com.zalizniak.mysql.lock;

import com.zalizniak.mysql.dao.TempEntry;
import com.zalizniak.mysql.dao.TempEntryDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class MysqlDbLockWorker1 {

    @Autowired
    private MysqlDbLockSubWorker1 mysqlDbLockSubWorker1;

    @Async
    public void transactional(Long id, List<String> logCollector) throws InterruptedException {
        mysqlDbLockSubWorker1.transactional(id, logCollector);
    }

    @Service
    public static class MysqlDbLockSubWorker1 {

        @Autowired
        private TempEntryDao dao;

        @Transactional(Transactional.TxType.REQUIRES_NEW)
        public void transactional(long id, List<String> logCollector) throws InterruptedException {

            Thread.sleep(1);
            logCollector.add("Worker1 started.");

            TempEntry entityForUpdate = dao.getOneForUpdate(id);
            logCollector.add("Worker1 retrieved entity.");

            Thread.sleep(2000);

            entityForUpdate.setTitle("123");
            dao.save(entityForUpdate);
            logCollector.add("Worker1 saved.");

            logCollector.add("Worker1 exited.");
        }
    }
}
