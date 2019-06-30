package com.zalizniak.couchbase.dao;

import com.zalizniak.couchbase.dao.doc.PlaceDoc;
import org.springframework.data.repository.CrudRepository;

/**
 * Standard CRUD repository for Place doc + query methods
 * 
 * @author Charz++
 */

public interface PlaceRepository extends CrudRepository<PlaceDoc, String> {

}
