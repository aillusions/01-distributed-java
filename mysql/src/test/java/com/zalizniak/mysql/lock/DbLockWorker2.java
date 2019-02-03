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
public class DbLockWorker2 {

    @Autowired
    private DbLockSubWorker2 dbLockSubWorker2;

    @Async
    public void transactional(Long id, List<String> logCollector) throws InterruptedException {
        dbLockSubWorker2.transactional(id, logCollector);
    }

    @Service
    public static class DbLockSubWorker2 {

        @Autowired
        private TempEntryDao dao;

        @Transactional(Transactional.TxType.REQUIRES_NEW)
        public void transactional(Long id, List<String> logCollector) throws InterruptedException {

            Thread.sleep(500);

            logCollector.add("Worker2 started.");

            TempEntry entityForUpdate = dao.getOneForUpdate(id);
            logCollector.add("Worker2 retrieved entity: " + entityForUpdate.getTitle());

            entityForUpdate.setTitle("456");
            dao.save(entityForUpdate);
            logCollector.add("Worker2 saved.");

            Thread.sleep(100);
            logCollector.add("Worker2 exited.");
        }
    }
}
