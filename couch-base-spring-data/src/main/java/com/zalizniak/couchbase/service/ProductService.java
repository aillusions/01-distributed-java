package com.zalizniak.couchbase.service;

import com.zalizniak.couchbase.dao.doc.ProductDoc;

/**
 * Interface for the Product Service Implementation
 *
 * @author Charz++
 */

public interface ProductService {

  ProductDoc findById(String id);

  void create(ProductDoc product);
}
