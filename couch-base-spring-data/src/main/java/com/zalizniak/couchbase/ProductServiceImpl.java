package com.zalizniak.couchbase;

import java.util.Optional;

import com.zalizniak.couchbase.dao.doc.ProductDoc;
import com.zalizniak.couchbase.exception.ApiException;
import com.zalizniak.couchbase.exception.ErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.couchbase.config.BeanNames;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

/**
 * Service that manages the creation of a Product to exemplify the use of unique number (uuid) for
 * key generation.
 *
 * @author Charz++
 */

@Service
public class ProductServiceImpl implements ProductService {

  // add the qualifier in case you have multiple buckets in your configuration otherwise remove it
  @Autowired
  @Qualifier(BeanNames.COUCHBASE_TEMPLATE)
  private CouchbaseTemplate template;

  @Override
  public ProductDoc findById(String id) {
    final Optional<ProductDoc> productObj = Optional.ofNullable(template.findById(id, ProductDoc.class));
    return productObj.orElseThrow(() -> new ApiException(ErrorType.PRODUCT_NOT_FOUND));
  }

  @Override
  public void create(ProductDoc product) {
    template.insert(product);
  }

}
