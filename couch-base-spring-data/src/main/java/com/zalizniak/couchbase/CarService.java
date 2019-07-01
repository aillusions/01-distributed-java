package com.zalizniak.couchbase;

import com.zalizniak.couchbase.dao.doc.CarDoc;

/**
 * Interface for the Car Service Implementation
 *
 * @author Charz++
 */

public interface CarService {

  CarDoc findByKey(Long number, String manufacturer);

  void create(CarDoc car);
}
