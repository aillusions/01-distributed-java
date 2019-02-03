package com.zalizniak.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author aillusions
 */
@Service
public class TempEntryService {

    @Autowired
    private TempEntryDao dao;

    @Transactional
    public TempEntry addTempEntry(Long id, String title, double x, double y) {

        TempEntry entry = new TempEntry();
        entry.setId(id);
        entry.setDateCreated(new Date());
        entry.setTitle(title);


        entry = dao.save(entry);

        return entry;
    }
}
