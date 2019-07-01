package com.zalizniak.couchbase;

import java.util.Optional;

import com.zalizniak.couchbase.dao.doc.CarDoc;
import com.zalizniak.couchbase.exception.ApiException;
import com.zalizniak.couchbase.exception.ErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.couchbase.config.BeanNames;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

/**
 * Service that manages the creation of a Car to exemplify the use of doc attributes for key
 * generation.
 *
 * @author Charz++
 */

@Service
public class CarServiceImpl implements CarService {

  // add the qualifier in case you have multiple buckets in your configuration otherwise remove it
  @Autowired
  @Qualifier(BeanNames.COUCHBASE_TEMPLATE)
  private CouchbaseTemplate template;

  @Override
  public CarDoc findByKey(Long number, String manufacturer) {
    final CarDoc carDoc = new CarDoc(number, manufacturer);
    final Optional<CarDoc> carObj =
        Optional.ofNullable(template.findById(template.getGeneratedId(carDoc), CarDoc.class));
    return carObj.orElseThrow(() -> new ApiException(ErrorType.CAR_NOT_FOUND));
  }

  @Override
  public void create(CarDoc car) {
    template.insert(car);
  }


}
