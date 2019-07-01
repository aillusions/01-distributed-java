package com.zalizniak.couchbase;

import com.zalizniak.couchbase.dao.doc.PlaceDoc;

/**
 * Interface for the Place Service Implementation
 *
 * @author Charz++
 */

public interface PlaceService {

  PlaceDoc findById(Long id);

  PlaceDoc create(PlaceDoc place);
}
