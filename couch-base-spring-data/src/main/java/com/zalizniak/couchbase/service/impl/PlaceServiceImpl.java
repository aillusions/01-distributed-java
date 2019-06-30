package com.zalizniak.couchbase.service.impl;

import java.util.Optional;

import com.zalizniak.couchbase.dao.PlaceCounterRepository;
import com.zalizniak.couchbase.dao.PlaceRepository;
import com.zalizniak.couchbase.dao.doc.PlaceDoc;
import com.zalizniak.couchbase.exception.ApiException;
import com.zalizniak.couchbase.exception.ErrorType;
import com.zalizniak.couchbase.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service that manages the creation of a Place to exemplify the use of multiple templates and
 * repositories.
 *
 * @author Charz++
 */

@Service
public class PlaceServiceImpl implements PlaceService {

  @Autowired
  private PlaceRepository placeRepo;

  @Autowired
  private PlaceCounterRepository placeCounterRepo;

  @Override
  public PlaceDoc findById(Long id) {
    final Optional<PlaceDoc> placeObj = placeRepo.findById(PlaceDoc.getKeyFor(id));
    return placeObj.orElseThrow(() -> new ApiException(ErrorType.PLACE_NOT_FOUND));
  }

  @Override
  public PlaceDoc create(PlaceDoc place) {
    place.setId(placeCounterRepo.counter()); // internally we set the key with that id
    return placeRepo.save(place);
  }

}
