package com.zalizniak.CouchDb;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;

public class SofaRepository extends CouchDbRepositorySupport<Sofa> {

    public SofaRepository(CouchDbConnector db) {
        super(Sofa.class, db);
    }

}